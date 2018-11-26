package dictreenary;

import java.util.ArrayList;

//confused about the format of this, previously trees r just a whole bunch of linked nodes so idk what to put in construdtor
//this is not like a dictree that calls dictrees recervisely??
//is it stored in an arraylist..????
//also does a letter go to the right or the left it is a duplicate spelling word 
//I dont understand what normalize word is
//each letter is its own terenary tree node 

public class Dictreenary implements DictreenaryInterface {

    // Fields
    // -----------------------------------------------------------
    TTNode root;

    // Constructor
    // -----------------------------------------------------------
    Dictreenary() {
    }

    // Methods
    // -----------------------------------------------------------

    public boolean isEmpty() {
        return root == null;
    }

    /*
     * Adds the given word toAdd to the Dictreenary by the procedure specified in
     * the Ternary Tree section above. Note that for this simplified Dictreenary,
     * the order in which words are added to the Ternary Tree may influence the
     * output of the getSuggestedTerm method detailed below. This is fine.
     * Furthermore, the order in which words are added can influence the efficiency
     * of each operation if the tree becomes too linear. This, although not
     * desirable, is fine given the time and difficulty expectations of the
     * assignment. Handle in normalized format
     */
    // edge case if you already have it
    public void addWord(String toAdd) {
        // this recurses
        toAdd = normalizeWord(toAdd);
        if (isEmpty()) {
            root = buildDown(toAdd);
        } else {
            attachFrom(toAdd, root);
        }
    }

    /*
     * Returns true if the given query String exists within the Dictreenary, false
     * otherwise.
     */
    public boolean hasWord(String query) {
        query = normalizeWord(query);
        TTNode location = find(query.charAt(0), root);
        while (location != null) {
            if (query.length() == 1) {
                if (location.wordEnd) {
                    return true;
                }
                if (!location.wordEnd) {
                    return false;
                }
            }
            query = query.substring(1);
            location = find(query.charAt(0), location.mid);
        }
        return false;
    }

    /*
     * Attempts to provide a spelling correction to the given query String. For
     * example, if "act" is a word that has been added to the Dictreenary, then
     * spellCheck("atc") will return "act" because "atc" can be transformed into
     * "act" by swapping the adjacent 't' and 'c'. Edge cases: [Multiple
     * Corrections] In the event that the given query String can be corrected into a
     * number of viable words in the Dictreenary, return the one that amounted from
     * the earliest (i.e., closest to the front of the word) swap. [Correctly
     * Spelled Word] In the event that the given query String is a word already in
     * the Dictreenary, simply return that word. [No Viable Corrections] In the
     * event that the given query String cannot be corrected into any word in the
     * Dictreenary, return null. Handle in normalized format
     */
    public String spellCheck(String query) {
        query = normalizeWord(query);
        if (hasWord(query)) {
            return query;
        }
        for (int i = 0; i < query.length() - 1; i++) {
            char[] ogQuery = query.toCharArray();
            String currentSwap = swapper(ogQuery, i);
            System.out.println(currentSwap);
            if (hasWord(currentSwap)) {
                return currentSwap;
            }
        }
        return null;
    }

    /*
     * Returns an ArrayList of Strings consisting of the alphabetically sorted words
     * within this Dictreenary. Alphabetic sorting is the same as how a dictionary
     * sorts its entries, so for example, "ass" is considered a predecessor to "at"
     * even though it has more letters. See the unit tests below for examples of
     * this behavior. Handle in normalized format
     */
    public ArrayList<String> getSortedWords() {
        ArrayList<String> result = new ArrayList<String>();
        return getAllWords(root, "", result);
    }

    // Helper Methods
    // -----------------------------------------------------------

    /*
     * Throws IllegalArgumentException(); when s is null or empty. Used to normalize
     * arguments to all of the assignment methods, as well as how the words are
     * stored.
     */
    private String normalizeWord(String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }

    /*
     * Returns: int less than 0 if c1 is alphabetically less than c2 0 if c1 is
     * equal to c2 int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars(char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }

    // [!] Add your own helper methods here!
    
    private ArrayList<String> getAllWords(TTNode current, String result, ArrayList<String> resultArray) {
        if (current != null) {
            getAllWords(current.left, result, resultArray);
            result = result + Character.toString(current.letter);
            if (current.wordEnd) {
                resultArray.add(result);
            }
            getAllWords(current.mid, result, resultArray);
            result = result.substring(0, result.length() - 1);
            getAllWords(current.right, result, resultArray);
        }
        return resultArray;
    }

    private String swapper(char[] c, int pos) {
        if (c.length == 1) {
            return Character.toString(c[0]);
        } else {
            char tempPos = c[pos];
            char tempNext = c[pos + 1];
            c[pos + 1] = tempPos;
            c[pos] = tempNext;
            String result = new String(c);
            return result;
        }
    }
    
    private TTNode buildDown(String s) {
        TTNode top = new TTNode(s.charAt(0), s.length() == 1);
        TTNode prev = null;
        for (int i = 1; i < s.length(); i++) {
            TTNode current = new TTNode(s.charAt(i), s.length() == (i + 1));
            if (i == 1) {
                top.mid = current;
                prev = current;
            } else {
                prev.mid = current;
                prev = current;
            }
        }
        return top;
    }

    private TTNode find(char c, TTNode given) {
        TTNode current = given;
        while (current != null) {
            char currentC = current.letter;
            if (currentC == c) {
                return current;
            }
            int compare = compareChars(c, currentC);
            current = (compare < 0) ? current.left : current.right;
        }
        return current;
    }

    // this will recurse
    private void attachFrom(String toAdd, TTNode current) {
        TTNode location = find(toAdd.charAt(0), current);
        if (location != null) {
            toAdd = toAdd.substring(1);
            if (location.mid == null) {
                location.mid = buildDown(toAdd);
                return;
            } else if (toAdd.length() == 0) {
                location.wordEnd = true;
            } else {
                attachFrom(toAdd, location.mid);
            }
        } else {
            compareRecursion(toAdd, current);
        }
    }

    private void compareRecursion(String toAdd, TTNode current) {
        int compare = compareChars(toAdd.charAt(0), current.letter);
        if (compare < 0) {
            if (current.left != null) {
                compareRecursion(toAdd, current.left);
            } else {
                current.left = buildDown(toAdd);
                return;
            }
        } else {
            if (current.right != null) {
                compareRecursion(toAdd, current.right);
            } else {
                current.right = buildDown(toAdd);
                return;
            }
        }
    }

    // TTNode Internal Storage
    // -----------------------------------------------------------

    /*
     * Internal storage of Dictreenary words as represented using a Ternary Tree
     * with TTNodes
     */
    private class TTNode {

        boolean wordEnd;
        char letter;
        TTNode left, mid, right;

        TTNode(char c, boolean w) {
            letter = c;
            wordEnd = w;
        }

    }

}
