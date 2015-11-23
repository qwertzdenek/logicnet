package icp.a05;

import icp.tools.BasicDataProvider;
import icp.tools.DataProvider;
import icp.tools.Vocabulary;

import java.io.*;
import java.util.*;

public class MyTopicModel implements TopicModel {
    public static int K = 50;
    public static double alpha = 0.5;
    public static double beta = 0.1;

    private class Word implements Comparable<Word> {
        public String word;
        public double prob;

        public Word(String word, double prob) {
            this.word = word;
            this.prob = prob;
        }

        @Override
        public int compareTo(Word o) {
            return (int) Math.signum(prob - o.prob);
        }
    }

    private static ArrayList<Document> corpus;
    private Vocabulary voc;
    private int corpusSize;

    private double betaSum;
    private double alphaSum;

    private int[][] wordTopicCounts;
    private int[] topicCounts;

    public MyTopicModel() {
        // load it without frequent words
        corpus = new ArrayList<Document>();
        voc = new Vocabulary();
        corpusSize = 0;

        DataProvider provider;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/stopwords.txt"), "UTF-8"));
            HashSet<String> sw = new HashSet<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                sw.add(line);
            }
            reader.close();

            provider = new BasicDataProvider("data/train.txt");

            String[] sentence = null;
            while ((sentence = provider.next()) != null) {
                ArrayList<String> actDoc = new ArrayList<String>();
                for (String word : sentence) {
                    if (!(sw.contains(word.toLowerCase()) || word.matches("\\d+"))) {
                        actDoc.add(word);
                        voc.add(word);
                        corpusSize++;
                    }
                }
                Document document = new Document(actDoc);
                int i = 0;
                for (String string : actDoc) {
                    document.wordIDs[i++] = voc.getWordKey(string);
                }
                corpus.add(document);
            }
            provider.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Neplatné kódování souboru");
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Soubor nenalezen");
            return;
        } catch (IOException e) {
            System.out.println("IO error");
            return;
        }

        alphaSum = K * alpha;
        betaSum = voc.size() * beta;

        wordTopicCounts = new int[voc.size()][K];
        topicCounts = new int[K];

        // initialize
        for (Document document : corpus) {
            for (int word = 0; word < document.size(); word++) {
                int topic = document.assignRandomTopic(word);
                wordTopicCounts[document.wordIDs[word]][topic]++;
                topicCounts[topic]++;
            }
        }
    }

    private void gibbsIteration() {
        for (Document doc : corpus) {
            for (int i = 0; i < doc.size(); i++) {
                int word = doc.wordIDs[i];
                int topic = doc.wordTopics[i];

                wordTopicCounts[word][topic]--;
                topicCounts[topic]--;
                doc.topicCounts[topic]--;

                double[] scores = new double[K];
                for (int k = 0; k < K; k++) {
                    double prob1 = (wordTopicCounts[word][k] + beta) / (topicCounts[k] + betaSum);
                    double prob2 = (doc.topicCounts[k] + alpha) / (doc.size() + alphaSum);

                    scores[k] = prob1 * prob2;
                }

                // přičtu nový topic
                int newTopic = sample(scores);
                wordTopicCounts[word][newTopic]++;
                topicCounts[newTopic]++;
                doc.topicCounts[newTopic]++;
                doc.wordTopics[i] = newTopic;
            }
        }
    }

    @Override
    public List<String>[] getTopicWords(int maxWords) {
        List<String>[] topicWords = (List<String>[]) new ArrayList[K];
        for (int k = 0; k < K; k++) {
            PriorityQueue<Word> priority = new PriorityQueue<Word>(maxWords);
            for (int i = 0; i < voc.size(); i++) {
                priority.add(new Word(voc.getWordByKey(i), wordTopicProb(i, k)));
                if (priority.size() > maxWords)
                    priority.poll();
            }

            topicWords[k] = new ArrayList<String>();
            for (Word w : priority) {
                topicWords[k].add(w.word);
            }
        }

        return topicWords;
    }

    @Override
    public Vocabulary getVocabulary() {
        return voc;
    }

    private int sample(double[] scores) {
        Random rnd = new Random();
        double sum = 0;
        for (double score : scores)
            sum += score;
        double sampleScore = rnd.nextDouble() * sum;
        int sample = -1;
        while (sampleScore > 0.0) {
            sample++;
            sampleScore -= scores[sample];
        }
        return sample;
    }

    private double wordTopicProb(int word, int topic) {
        return (wordTopicCounts[word][topic] + beta)
                / (topicCounts[topic] + betaSum);
    }

    private double topicProb(int topic, Document doc) {
        return (doc.topicCounts[topic] + alpha) / (doc.size() + alphaSum);
    }

    private double wordProb(int word, Document doc) {
        double sum = 0.0;

        for (int k = 0; k < K; k++) {
            sum += wordTopicProb(word, k) * topicProb(k, doc);
        }

        return sum;
    }

    public double getEntropy() {
        double entropy = 0.0;
        for (Document doc : corpus) {
            for (int i = 0; i < doc.size(); i++) {
                entropy += Math.log(wordProb(doc.wordIDs[i], doc));
            }
        }

        return -entropy / corpusSize;
    }

    @Override
    public double getPerplexity() {
        return Math.exp(getEntropy());
    }

    public void train() {
        for (int i = 0; i < 128; i++) {
            gibbsIteration();
            System.out.println("\n" + (i + 1) + ". iterace = " + getPerplexity());
            List<String>[] results = getTopicWords(5);
            int k = 0;
            for (List<String> list : results) {
                System.out.print((k + 1) + ":");
                for (String string : list) {
                    System.out.print(" " + string);
                }
                System.out.println();
                k++;
            }
        }
    }

    public void test() {
        double sum = 0.0;
        for (String word : voc.getVocabulary()) {
            sum += wordTopicProb(voc.getWordKey(word), 0);
        }
        System.out.println("sum_w P(w|z = 0) = " + sum);

        sum = 0;
        Document doc = corpus.get(15);
        for (int k = 0; k < K; k++) {
            sum += topicProb(k, doc);
        }
        System.out.println("sum_z P(z|d = 0) = " + sum);

        sum = 0;
        for (int i = 0; i < voc.size(); i++) {
            sum += wordProb(i, doc);
        }
        System.out.println("sum_w P(w|d = 0) = " + sum);
    }
}
