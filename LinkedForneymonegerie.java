//Bridget O'Connor

package linked_forneymonegerie;

import java.util.NoSuchElementException;

public class LinkedForneymonegerie implements LinkedForneymonegerieInterface {

    // Fields
    // -----------------------------------------------------------
    private ForneymonType head, butt;
    private int size, typeSize, modCount;

    // Constructor
    // -----------------------------------------------------------
    LinkedForneymonegerie() {
        head = null;
        butt = null;
        size = 0;
        typeSize = 0;
        modCount = 0;
    }

    // Methods
    // -----------------------------------------------------------

    public boolean empty() {
        return head == null && size == 0;
    }

    public int size() {
        return size;
    }

    public int typeSize() {
        return typeSize;
    }

    public boolean collect(String toAdd) {
        return insertForneymon(toAdd, 1);
    }

    public boolean release(String toRemove) {
        return removeForneymon(toRemove, 1);
    }

    public void releaseType(String toNuke) {
        removeForneymon(toNuke, find(toNuke).count);
    }

    public int countType(String toCount) {
        ForneymonType find = find(toCount);
        return (find != null) ? find.count : 0;
    }

    public boolean contains(String toCheck) {
        return find(toCheck) != null;
    }

    public String rarestType() {
        String rarest = null;
        int lowestCount = 0;
        ForneymonType current = head;
        while (current != null) {
            if (current.count <= lowestCount || rarest == null) {
                lowestCount = current.count;
                rarest = current.type;
            }
            current = current.next;
        }
        return rarest;
    }

    public LinkedForneymonegerie clone() {
        LinkedForneymonegerie clone = new LinkedForneymonegerie();
        ForneymonType thisCurrent = head;
        while (thisCurrent != null) {
            clone.insertForneymon(thisCurrent.type, thisCurrent.count);
            thisCurrent = thisCurrent.next;
        }
        return clone;
    }

    public void trade(LinkedForneymonegerie other) {
        LinkedForneymonegerie tempThis = this.clone();
        LinkedForneymonegerie tempOther = other.clone();
        other.modCount++;
        this.modCount++;
        this.head = tempOther.head;
        this.butt = tempOther.butt;
        this.size = tempOther.size;
        this.typeSize = tempOther.typeSize;
        other.head = tempThis.head;
        other.butt = tempThis.butt;
        other.size = tempThis.size;
        other.typeSize = tempThis.typeSize;
    }

    @Override
    public String toString() {
        String result = "[";
        for (ForneymonType current = head; current != null; current = current.next) {
            result += " \"" + current.type + "\"" + ": " + current.count + ",";
        }
        result = result.substring(0, result.length() - 1);
        result += " ]";
        return result;

    }

    public LinkedForneymonegerie.Iterator getIterator() {
        if (empty()) {
            throw new IllegalStateException();
        }
        return new Iterator(this);
    }

    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------

    public static LinkedForneymonegerie diffMon(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        LinkedForneymonegerie result = y1.clone();
        for (ForneymonType current = y2.head; current != null; current = current.next) {
            result.removeForneymon(current.type, current.count);
        }
        return result;
    }

    public static boolean sameCollection(LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        return diffMon(y1, y2).empty() && y1.size == y2.size && y1.typeSize == y2.typeSize;
    }

    // Private helper methods
    // -----------------------------------------------------------

    private ForneymonType find(String s) {
        ForneymonType find = null;
        for (ForneymonType current = head; current != null; current = current.next) {
            if ((current.type).equals(s)) {
                find = current;
                break;
            }
        }
        return find;
    }

    private boolean insertForneymon(String text, int count) {
        modCount++;
        ForneymonType find = find(text);
        boolean result;
        if (empty()) {
            head = new ForneymonType(text, count);
            butt = head;
            typeSize++;
            result = true;
        } else if (find == null) {
            if (butt == head) {
                butt = new ForneymonType(text, count);
                butt.prev = head;
                head.next = butt;
            } else {
                ForneymonType heldButt = butt;
                butt = new ForneymonType(text, count);
                butt.prev = heldButt;
                heldButt.next = butt;
            }
            typeSize++;
            result = true;
        } else {
            find.count += count;
            result = false;
        }
        size += count;
        return result;
    }

    private boolean removeForneymon(String text, int count) {
        ForneymonType find = find(text);
        if (find == null) {
            return false;
        } else {
            modCount++;
            int newCount = find.count - count;
            if (newCount <= 0) {
                deleteForneymonType(find);
                typeSize--;
                size -= find.count;
            } else {
                find.count = newCount;
                size -= count;
            }
            return true;
        }
    }

    private void deleteForneymonType(ForneymonType find) {
        if (find != null) {
            if (butt == head) {
                head = null;
                butt = null;
            } else if (find == butt) {
                butt = butt.prev;
                butt.next = null;
            } else if (find == head) {
                head = head.next;
                head.prev = null;
            } else {
                ForneymonType prev = find.prev;
                ForneymonType next = find.next;
                prev.next = find.next;
                next.prev = find.prev;
            }
        }
    }

    // Inner Classes
    // -----------------------------------------------------------

    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        int currentForneymon;
        int itModCount;

        Iterator(LinkedForneymonegerie y) {
            owner = y;
            current = y.head;
            itModCount = y.modCount;
            currentForneymon = head.count;

        }

        public boolean hasNext() {
            if (isValid() && current != null) {
                if (currentForneymon - 1 > 0) {
                    return true;
                } else {
                    return current.next != null;
                }
            } else {
                return false;
            }

        }

        public boolean hasPrev() {
            if (isValid() && current != null) {
                if (currentForneymon + 1 < current.count) {
                    return true;
                } else {
                    return current.prev != null;
                }
            } else {
                return false;
            }
        }

        public boolean isValid() {
            return owner.modCount == itModCount;

        }

        public String getType() {
            if (!isValid() || current == null) {
                return null;
            } else {
                return current.type;
            }
        }

        public void next() {
            if (!isValid()) {
                throw new IllegalStateException();
            }
            if (currentForneymon - 1 > 0) {
                currentForneymon -= 1;
            } else {
                if (current.next == null) {
                    throw new NoSuchElementException();
                } else {
                    current = current.next;
                    currentForneymon = current.count;
                }
            }
        }

        public void prev() {
            if (!isValid()) {
                throw new IllegalStateException();
            }
            if (currentForneymon + 1 < current.count) {
                currentForneymon += 1;
            } else {
                if (current.prev == null) {
                    throw new NoSuchElementException();
                } else {
                    current = current.prev;
                    currentForneymon = 0;
                }
            }
        }

        public void replaceAll(String toReplaceWith) {
            if (!isValid()) {
                throw new IllegalStateException();
            }
            ForneymonType location = find(toReplaceWith);
            if (location == current) {
            } 
            else if (location != null) {
                location.count += current.count;
                String Heldtype = current.type;
                current = location;
                releaseType(Heldtype);
                itModCount++;
            } else { 
                insertForneymon(toReplaceWith, current.count);
                releaseType(current.type);
                current = find(toReplaceWith);
                itModCount += 2;
                ;
            }
        }

    }

    private class ForneymonType {
        ForneymonType next, prev;
        String type;
        int count;

        ForneymonType(String t, int c) {
            type = t;
            count = c;
        }
    }

}