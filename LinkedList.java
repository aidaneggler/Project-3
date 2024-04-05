public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head;
    private boolean isSorted;

    public LinkedList() {
        head = null;
        isSorted = true;
    }

    public boolean add(T element) { //adds to the end of list?
        if (head == null) {
            head = new Node<>(element);
        } //if list is empty then add element
        else {
            Node<T> curr = head; //else iterate til the last node and add
            while (curr.getNext() != null) {
                curr = curr.getNext();
            }
            curr.setNext(new Node<>(element));
        }
        isSorted = false; //list not sorted after adding something
        return true;
    }

    public boolean add(int index, T element) { // add at certain index
        if (index == 0) { // if index 0 add to start of list
            Node<T> newNode = new Node<>(element); // make new node and update head to point to newNode
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> curr = head;
            for (int i = 0; i < index - 1; i++) { //get at certain index
                curr = curr.getNext();
            }
            Node<T> newNode = new Node<>(element);
            newNode.setNext(curr.getNext());
            curr.setNext(newNode);
        }
        isSorted = false;
        return true;
    }

    public void clear() { //setting head to null would clear list
        head = null;
        isSorted = true; //sorted bc empty
    }

    public T get(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.getNext();
        }
        return curr.getData();
    }

    public int indexOf(T element) { //use binary if sorted and linear if unsorted
        if (head == null) {
            return -1;
        }
        if (isSorted) { // if it is sorted do a binary search to increase efficieny of the sort
            int low = 0;
            int high = size() - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                T midElement = get(mid);
                if (midElement.equals(element))
                    return mid;//if at mid return that index
                if (midElement.compareTo(element) < 0)
                    low = mid + 1; //search right
                else
                    high = mid - 1; //search left
            }
            return -1;
        } else { //linear search for unsorted
            Node<T> curr = head;
            int index = 0;
            while (curr != null) {
                if (curr.getData().equals(element))
                    return index;
                curr = curr.getNext();
                index++;
            }
            return -1;
        }
    }


    public boolean isEmpty() { //if head is null list is empty
        return (head == null);
    }

    public int size() { //counts all except null
        int count = 0;
        Node<T> curr = head;
        while (curr != null) {
            count++;
            curr = curr.getNext();
        }
        return count;
    }

    public void sort() { // implements bubble sort if its not already sorted
        if (!isSorted) {
            int size = size();
            if (size <= 1)
                return;
            T temp;
            for (int i = 0; i < size - 1; i++) {
                Node<T> curr = head;

                for (int j = 0; j < size - i - 1; j++) {
                    Node<T> nextNode = curr.getNext();

                    if (curr.getData().compareTo(nextNode.getData()) > 0) {
                        temp = curr.getData();
                        curr.setData(nextNode.getData());
                        nextNode.setData(temp);
                    }
                    curr = curr.getNext();
                }
            }
            isSorted = true;
        }
    }

    public T remove(int index) {
        if( index < 0 || index >= size())
            return null;
        T removedElem;

        if(index == 0) { //removing head node
            removedElem = head.getData();
            head = head.getNext(); //sets next head
        } else { // remove non head node
            Node<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            removedElem = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
        }
        isSorted = false; // unsorted bc removed an element
        return removedElem;
    }

    public void removeDuplicates() {
        if(head == null)
            return; // if list empty return nothing
        Node<T> curr = head;
        while(curr != null) {
            Node<T> temp = curr;
            if(curr.getData().equals(temp.getNext().getData())) {
                temp.setNext(temp.getNext().getNext());
            } else {
                temp = temp.getNext();
            }
        }
        curr = curr.getNext();
    }

    public void reverse() {
        if (head == null || head.getNext() == null) {
            return;
        }
        Node<T> prev = null;
        Node<T> curr = head;
        Node<T> next = null;
        while (curr != null) {
            next = curr.getNext(); // Store reference to the next node
            curr.setNext(prev);    // Reverse the pointer of the current node to point to the previous node
            prev = curr;            // Move previous pointer to current node
            curr = next;            // Move current pointer to next node
        }
    }

    public void exclusiveOr(List<T> otherList) {
        if (otherList == null)
            return;
        LinkedList<T> other = (LinkedList<T>) otherList; //cast to LinkedList
        //sort and remove dupes
        this.sort();
        this.removeDuplicates();
        other.sort();
        other.removeDuplicates();

        //merger into one list
        LinkedList<T> result = new LinkedList<>(); //merged list
        Node<T> curr1 = this.head;
        Node<T> curr2 = other.head;

        while (curr1 != null || curr2 != null) {
            if (curr1 == null) {
                // Add remaining elements from other list
                result.add(curr2.getData());
                curr2 = curr2.getNext();
            } else if (curr2 == null) {
                // Add remaining elements from this list
                result.add(curr1.getData());
                curr1 = curr1.getNext();
            } else {
                int comparison = curr1.getData().compareTo(curr2.getData());
                if (comparison < 0) {
                    // Add element from this list
                    result.add(curr1.getData());
                    curr1 = curr1.getNext();
                } else if (comparison > 0) {
                    // Add element from other list
                    result.add(curr2.getData());
                    curr2 = curr2.getNext();
                } else {
                    // Both lists contain the same element, move to next elements
                    curr1 = curr1.getNext();
                    curr2 = curr2.getNext();
                }
            }
        }
    }

    public T getMin() {
        if(head == null)
            return null; // if empty return null

        if(isSorted) {
            return head.getData(); //if sorted first will be smallest/min
        } // would be too complex to call the sorting algorith rather than just iterate through the list
        T min = head.getData();
        Node<T> curr = head.getNext();
        while(curr != null) { // iterate over to find min rather than sorting algo
            if(curr.getData().compareTo(min) < 0)
                min = curr.getData();
            curr = curr.getNext();
        }
        return min;
    }
    public T getMax() {
        if(head == null)
            return null; // if empty return null

        if(isSorted) {
            return head.getData(); //if sorted first will be smallest/min
        } // would be too complex to call the sorting algorith rather than just iterate through the list
        T max = head.getData();
        Node<T> curr = head.getNext();
        while(curr != null) { // iterate over to find min rather than sorting algo
            if(curr.getData().compareTo(max) > 0)
                max = curr.getData();
            curr = curr.getNext();
        }
        return max;
    }
    public String toString() {
        String result = "";
        Node<T> current = head;
        while (current != null) {
            result += current.getData() + "\n";
            current = current.getNext();
        }
        //remove last newline character if it exists
        if (!result.isEmpty()) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
    public boolean isSorted() {
        return isSorted;
    }
}

