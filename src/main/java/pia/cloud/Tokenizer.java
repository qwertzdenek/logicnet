package pia.cloud;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenizer {
    public static List<String> tokenize(String document) {
        StringTokenizer tokenizer = new StringTokenizer(document, " \n\t,.:;#-_@/?![]'");
        ArrayList<String> resultList = new ArrayList<>();

        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            if (token.length() > 4 && !token.contains("htt") && !token.contains("RT"))
                resultList.add(token.toLowerCase());
        }

        return resultList;
    }
}
