package icp.a05;

import java.util.ArrayList;
import java.util.Random;

public class Document {
    private ArrayList<String> words;
    private Random rnd;

    public int[] wordTopics;
    public int[] topicCounts;
    public int[] wordIDs;

    public Document(ArrayList<String> words) {
        this.words = words;
        rnd = new Random();
        rnd.setSeed(words.hashCode());

        wordTopics = new int[words.size()];
        topicCounts = new int[MyTopicModel.K];
        wordIDs = new int[words.size()];
    }

    public int size() {
        return words.size();
    }

    public void assignTopic(int word, int topic) {
        wordTopics[word] = topic;
        topicCounts[topic]++;
    }

    public int assignRandomTopic(int word) {
        int topic = Math.abs(rnd.nextInt()) % MyTopicModel.K;
        wordTopics[word] = topic;
        topicCounts[topic]++;

        return topic;
    }

    public String getWordByPos(int pos) {
        if (pos < 0 || pos >= words.size())
            throw new IllegalArgumentException("pos key (" + pos + ") must range between 0 and " + (words.size() - 1));
        return words.get(pos);
    }
}
