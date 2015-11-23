package icp.a05;

import icp.tools.Vocabulary;

import java.util.List;

public interface TopicModel {

    /**
     * vrati list nejpravdepodobnejsich slov pro kazdy topic
     *
     * @param maxWords pocet nejpravdepodobnejsich slov v topiku (velikost listu)
     * @return
     */
    public List<String>[] getTopicWords(int maxWords);

    /**
     * spocita aktualni perplexitu modelu na trenovacich datech
     *
     * @return
     */
    public double getPerplexity();

    /**
     * vrati slovnik
     *
     * @return
     */
    public Vocabulary getVocabulary();

}
