import java.util.concurrent.ConcurrentMap;

public class ArrayList<T extends Comparable<T>> implements List<T> {
    private static final int initCap = 2;
    private T[] array;
    private int size;
    private boolean isSorted;

    public ArrayList() {
        this.array = (T[]) new Comparable[size];
        this.size = 0;
        this.isSorted = true;
    }

    public boolean add(T element) {
        if (size == array.length)
            growArray(); //copy and grow if
        array[size] = element;
        size++;
        isSorted = false; //not sorted after something is added
        return true;
    }

    public void growArray() { //helper method to grow array if size = length
        int newCap = array.length * 2;
        T[] newArray = (T[]) new Comparable[newCap];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public boolean add(int index, T element) {
        if (index < 0 || index > size || element == null) //checks if within bounds of array
            return false;
        if (size == array.length)
            growArray();
        for (int i = size; i > index; i--) { // move everything to right once to make space for inserted
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
        isSorted = false;
        return true;
    }

    public void clear() { //set all elements to null;
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        isSorted = true;
        size = 0;
        array = (T[]) new Comparable[initCap];
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            return null;
        return array[index];
    }

    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (array[i] != null && array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() { //return if size == 0
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void sort() {
        if(isSorted || size <= 1)
            return;
        for (int i = 0; i < size - 1; i++) { // implement bubble sort algorithm
            for (int j = 0; j < size - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        isSorted = true;
    }

    public T remove(int index) {
        if(index < 0 || index >= size)
            return null;
        T removedElem = array[index];
        for(int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
        isSorted = false;
        return removedElem;
    }

    public void removeDuplicates() {
        if(size <= 1)
            return;
        sort(); //get duplicate elements together
        int uIndex = 0;
        int dIndex = 1;
        while(dIndex < size) { // if current and prev are diff, copy current to unique index
            if(!array[uIndex].equals(array[dIndex])) {
                array[++uIndex] = array[dIndex];
            }
            dIndex++;
        }
        size = uIndex + 1;
        isSorted = false;
    }

    public void reverse() {
        int start = 0;
        int end = size - 1;
        while(start < end) {
            T temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end++;
        }
    }

}


