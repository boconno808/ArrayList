//Bridget O'Connor
//Lucille Njoo

package sentinal;

public class PhraseHash implements PhraseHashInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------

    private final static int BUCKET_START = 1000;
    private final static double LOAD_MAX = 0.7;
    private int size, longest;
    private String[] buckets;
    private final static int THICC_PRIME = 1301081;

    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------

    PhraseHash() {
        size = 0;
        longest = 0;
        buckets = new String[BUCKET_START];
    }

    // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void put(String s) {
        checkAndGrow();
        if (insert(s, buckets)) {
            size++;
        }
    }

    public String get(String s) {
        int hashValue = hash(s);
        int ogHash = hashValue;
        if (buckets[hashValue] == null) {
            return null;
        }
        if ((buckets[hashValue].equals(s))) {
            return s;
        }
        hashValue = moveOneUp(hashValue, buckets);
        while ((buckets[hashValue] != null) && (ogHash != hashValue)) {
            if ((buckets[hashValue].equals(s))) {
                break;
            }
            hashValue = moveOneUp(hashValue, buckets);
        }
        return (buckets[hashValue] != null && buckets[hashValue].equals(s)) ? s : null;
    }

    public int longestLength() {
        return longest;
    }

    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------

    private int hash(String s) {
        int hash = 1;
        for (int i = 0; i < s.length(); i++) {
            char character = s.charAt(i);
            int ascii = (int) character;
            hash *= ascii;
        }
        hash *= s.length() * THICC_PRIME;
        return (Math.abs(hash % buckets.length));
    }

    private void checkAndGrow() {
        if (isEmpty()) {
            return;
        }
        double s = size;
        double l = buckets.length;
        if (s / l >= LOAD_MAX) {
            String[] oldBuckets = buckets.clone();
            buckets = new String[oldBuckets.length * 2];
            for (int i = 0; i < oldBuckets.length; i++) {
                if (oldBuckets[i] == null) {
                    continue;
                }
                insert(oldBuckets[i], buckets);
            }
        }

    }

    private boolean insert(String s, String[] given) {
        int hashValue = hash(s);
        int ogHash = hashValue;
        if (isHashValueNull(hashValue, given, s)) {return true;}
        hashValue = moveOneUp(hashValue, given);
        while ((given[hashValue] != null) && (ogHash != hashValue)) {
            if ((given[hashValue].equals(s))) {
                return false;
            }
            hashValue = moveOneUp(hashValue, given);
        }
        return isHashValueNull(hashValue, given, s);
    }

    private int wordsInString(String currentPhrase) {
        currentPhrase.trim();
        String[] wordArray = currentPhrase.split(" ");
        return wordArray.length;
    }

    private boolean isHashValueNull(int hashValue, String[] given, String s) {
        if ((given[hashValue] == null)) {
            given[hashValue] = s;
            longest = wordsInString(s) > longest ? wordsInString(s) : longest;
            return true;
        } else {
            return false;
        }
    }

    private int moveOneUp(int hashValue, String[] given) {
        if (hashValue == given.length - 1) {
            hashValue = 0;
        } else {
            hashValue++;
        }
        return hashValue;
    }

}
