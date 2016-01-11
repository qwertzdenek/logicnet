package pia.cloud;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * String vocabulary. Every string is stored only once. Strings are copied to get rid of potential unnecessary data. When the word (string) is not referenced outside 
 * the vocabulary then it is removed.
 * @author Miloslav Konopik
 *
 */
public class Vocabulary {
	public static final String UNKNOWN = "<UNKNOWN>";
	
    private Map<String, WeakReference<String>> vocabulary;

    /**
     * Creates a new vocalbulary. 
     */
    public Vocabulary() {
        this.vocabulary = new HashMap<>();
    }

    /**
     * Adds a word to the vocabulary and returns a singleton reference.
     * @param word an input word
     * @return a singleton reference to the input word.
     */
    public String add(String word) {
        WeakReference<String> objectReference = vocabulary.get(word);
        
        if (objectReference == null) {
            word = new String(word); // copy the string to remove word context created by the split function.
        	
        	objectReference = new WeakReference<String>(word);
            vocabulary.put(objectReference.get(), objectReference);
        }
        
        return objectReference.get();
    }

    /**
     * Adds words to the vocabulary and returns an array of singleton references.
     * @param word input words
     * @return an array of singleton references.
     */
    public String[] add(String[] words) {
        String[] outputWords = new String[words.length];
        for (int i = 0; i < words.length; i++)
        	outputWords[i] = add(words[i]);
        
        return outputWords;
    }
    
    /**
     * Looks for a word in the vocabulary and returns either the word or the UNKNOWN token.
     * @param the input word.
     * @return the word from the vocabulary or the UNKNOWN token when the word is not found.
     */
    public String get(String word) {
        WeakReference<String> objectReference = vocabulary.get(word);
        
        if (objectReference == null) {
            return UNKNOWN;
        } else {
        	return objectReference.get();
        }
    }
    
    /**
     * Looks for words in the vocabulary and returns the words or UNKNOWN tokens.
     * @param the input words.
     * @return the words or UNKNOWN tokens when the word are not found.
     */
    public String[] get(String[] words) {
        String[] outputWords = new String[words.length];
        for (int i = 0; i < words.length; i++)
            words[i] = get(words[i]);
        
        return outputWords;
    }

    public int size() {
        return this.vocabulary.size();
    }

    public Set<String> getVocabulary() {
        return vocabulary.keySet();
    }
}
