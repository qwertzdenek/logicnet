package pia.beans;

import pia.cloud.CountMap;
import pia.cloud.Tokenizer;
import pia.cloud.Word;
import pia.dao.AccountDao;
import pia.dao.JPADAO;
import pia.dao.PostDao;
import pia.data.Account;
import pia.data.Post;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@Singleton
public class WordCloudService {
    private static final int RESULT_COUNT = 32;

    private Map<Account, CountMap<String>> accountHistogram;

    @Inject
    @JPADAO
    AccountDao ad;

    @Inject
    @JPADAO
    PostDao pd;

    @PostConstruct
    private void init() {
        accountHistogram = new HashMap<>();
        for (Account a : ad.listAll()) {
            CountMap<String> wordMap = new CountMap<>((int) ad.accountCount());
            accountHistogram.put(a, wordMap);
            for (Post p : pd.getLatestPosts(a)) {
                List<String> tokens = Tokenizer.tokenize(p.getContent());

                for (String token : tokens) {
                    wordMap.inc(token, 1);
                }
            }
        }
    }

    public void addPost(Account a, Post p) {
        CountMap<String> wordMap =  accountHistogram.get(a);

        List<String> tokens = Tokenizer.tokenize(p.getContent());

        for (String token : tokens) {
            wordMap.inc(token, 1);
        }
    }

    public Word[] mostFrequentWords(Account a) {
        CountMap<String> wordMap = accountHistogram.get(a);
        CountMap<Integer> countMap = new CountMap<>(wordMap.size());

        if (wordMap.size() < RESULT_COUNT)
            return null;

        // counting array
        for (Map.Entry<String, Integer> entry : wordMap.getEntrySet()) {
            countMap.inc(entry.getValue(), 1);
        }

        // partial sums
        CountMap<Integer> partialSum = new CountMap<>(countMap.size());
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : countMap.getEntrySet()) {
            sum += entry.getValue();
            partialSum.set(entry.getKey(), sum);
        }

        // reconstruct
        Word[] finalPos = new Word[RESULT_COUNT];
        int posDiff = wordMap.size() - RESULT_COUNT;
        for (Map.Entry<String, Integer> entry : wordMap.getEntrySet()) {
            String word = entry.getKey();
            int pos = partialSum.get(entry.getValue()) - posDiff - 1;
            if (pos >= 0) {
                finalPos[pos] = new Word(word, wordMap.get(word));
            }
            partialSum.dec(entry.getValue(), 1);
        }

        return finalPos;
    }
}
