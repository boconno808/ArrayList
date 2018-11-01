//Bridget O'Connor

package forneymonegerie;

public class Forneymonegerie implements ForneymonegerieInterface {

    // Fields
    // ----------------------------------------------------------
    private ForneymonType[] collection;
    private int size;
    private int typeSize;
    private static final int START_SIZE = 16;

    // Constructor
    // ----------------------------------------------------------
    Forneymonegerie() {
        collection = new ForneymonType[START_SIZE];
        size = 0;
        typeSize = 0;
    }

    // Methods
    // ----------------------------------------------------------

    public boolean empty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int typeSize() {
        return typeSize;
    }

    public boolean collect(String typeToAdd) {
        checkAndGrow();
        int location = duplicateLocate(typeToAdd);
        if (location == -1) {
            collection[typeSize] = new ForneymonType(typeToAdd, 1);
            typeSize++;
            size++;
            return true;
        } else {
            collection[location].countIncrease(1);
            size++;
            return false;
        }
    }

    public boolean release(String typeToRelease) {
        boolean result;
        int location = duplicateLocate(typeToRelease);
        if (location == -1) {
            result = false;
        } else {
            if (collection[location].count == 1) {
                releaseType(typeToRelease);
            } else {
                collection[location].countDecrease(1);
                size--;
            }
            result = true;
        }
        return result;
    }

    public void releaseType(String typeToNuke) {
        int location = duplicateLocate(typeToNuke);
        if (location != -1) {
            int numberToNuke = collection[location].count;
            shiftTypesLeft(location);
            size -= numberToNuke;
            typeSize -= 1;
        }
    }

    public int countType(String typeToCount) {
        int totalCount;
        int location = duplicateLocate(typeToCount);
        if (location != -1) {
            int index = location;
            totalCount = collection[index].count;
        } else {
            totalCount = 0;
        }
        return totalCount;
    }

    public boolean contains(String typeToCheck) {
        return duplicateLocate(typeToCheck) != -1;
    }

    public String nth(int n) {
        if (n >= size() || n < 0) {
            throw new IllegalArgumentException();
        }
        int currentSize = -1;
        int currentLocation = -1;
        do {
            currentLocation++;
            currentSize += allTypeAmounts(currentLocation);
        } while (n > currentSize);
        return collection[currentLocation].type;
    }

    public String rarestType() {
        String resultString;
        if (empty()) {
            resultString = null;
        } else {
            int indexOfRarest = typeSize() - 1;
            for (int j = typeSize() - 2; j >= 0; j--) {
                if (allTypeAmounts(j) < allTypeAmounts(indexOfRarest)) {
                    indexOfRarest = j;
                }
            }
            resultString = collection[indexOfRarest].type;
        }
        return resultString;
    }

    public Forneymonegerie clone() {
        Forneymonegerie clone = new Forneymonegerie();
        clone.collection = this.copyCollection(this.collection.length);
        clone.size = this.size();
        clone.typeSize = this.typeSize();
        return clone;
    }

    public void trade(Forneymonegerie other) {
        ForneymonType[] collectionForThis = other.copyCollection(other.collection.length);
        ForneymonType[] collectionForOther = copyCollection(this.collection.length);
        int sizeForThis = other.size;
        int sizeForOther = this.size;
        int typeSizeForThis = other.typeSize();
        int typeSizeForOther = this.typeSize();

        other.size = sizeForOther;
        other.typeSize = typeSizeForOther;
        other.collection = collectionForOther;
        this.size = sizeForThis;
        this.typeSize = typeSizeForThis;
        this.collection = collectionForThis;
    }

    public String toString() {
        String resultString = "[ ";
        for (int i = 0; i < typeSize - 1; i++) {
            resultString += "\"" + collection[i].type + "\"" + ": " + collection[i].count + ", ";
        }
        resultString += "\"" + collection[typeSize - 1].type + "\"" + ": " + collection[typeSize - 1].count + " ]";
        return resultString;
    }

    // Static methods
    // ----------------------------------------------------------
//Returns a *new* Forneymonegerie object consisting of all Forneymon (NOTE: not ForneymonTypes) from y1 that do NOT appear in y2.
    public static Forneymonegerie diffMon(Forneymonegerie y1, Forneymonegerie y2) {
        Forneymonegerie changeableY1 = y1.clone();
        for (int i = 0; i < y1.typeSize(); i++) {
            String currentType = y1.collection[i].type;
            if (y2.contains(currentType)) {
                int amountOfY2 = y2.countType(currentType);
                if (amountOfY2 >= y1.countType(currentType)) {
                    changeableY1.releaseType(currentType);
                } else {
                    for (int j = 0; j < amountOfY2; j++) {
                        changeableY1.release(currentType);
                    }
                }
            }
        }
        return changeableY1;
    }

    public static boolean sameCollection(Forneymonegerie y1, Forneymonegerie y2) {
        return y1.size() == y2.size() && y1.typeSize() == y2.typeSize() && diffMon(y1, y2).empty();
    }

    // Private helper methods
    // ----------------------------------------------------------

    private ForneymonType[] copyCollection(int desiredLength) {
        ForneymonType[] newCollection = new ForneymonType[desiredLength];
        for (int i = 0; i < collection.length; i++) {
            if (collection[i] != null) {
                newCollection[i] = new ForneymonType(collection[i].type, collection[i].count);
            } else {
                break;
            }
        }
        return newCollection;
    }

    private void checkAndGrow() {
        if (typeSize() < collection.length) {
            return;
        }
        collection = copyCollection(collection.length * 2);
    }

    /*
     * Returns the location of the duplicate in the collection returns -1 if it is a
     * unique Forneymon type
     */
    private int duplicateLocate(String toAdd) {
        int index = -1;
        for (int i = 0; i < typeSize(); i++) {
            if (collection[i].type.equals(toAdd)) {
                index = i;
            }
        }
        return index;
    }

    private void shiftTypesLeft(int index) {
        for (int i = index; i < typeSize() - 1; i++) {
            collection[i].type = collection[i + 1].type;
            collection[i].count = collection[i + 1].count;
        }
    }

    /*
     * Returns a int equal to the amount of Forneymon in a type at that index in the
     * collection
     */
    private int allTypeAmounts(int index) {
        int[] allTypeAmounts = new int[typeSize()];
        for (int i = 0; i < typeSize(); i++) {
            allTypeAmounts[i] = collection[i].count;
        }
        return allTypeAmounts[index];
    }

    // Private Classes
    // ----------------------------------------------------------
    private class ForneymonType {
        String type;
        int count;

        ForneymonType(String t, int c) {
            type = t;
            count = c;
        }

        private void countIncrease(int number) {
            count += number;
        }

        public void countDecrease(int number) {
            count -= number;
        }
    }

}
