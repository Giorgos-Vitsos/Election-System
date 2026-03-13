package cs240_2024;

/**
 * This class represents a min-heap data structure used to manage a collection of 
 * Candidate objects. The heap ensures that the candidate with the minimum number of 
 * votes is always at the top (index 0) of the heap. The class provides methods to 
 * insert candidates, delete the candidate with the minimum votes, and elect candidates.
*/
public class minHeap {

    private Candidate[] elected;

    /**
     * The current size of the heap (i.e., the number of elements in the heap).
     */
    private int size;
    private int capacity;

    /**
     * Constructs a new minHeap with the specified capacity.
     * 
     * @param capacity The maximum number of candidates that can be stored in the heap.
     */
    public minHeap(int capacity) {
        this.capacity = capacity;
        elected = new Candidate[capacity];
        size = 0;
    }

    /**
     * Returns the index of the parent node of the node at the specified index.
     * 
     * @param i The index of the node whose parent index is to be found.
     * @return The index of the parent node.
     */
    private int parent(int i){
        return (i-1)/2;
    }

    /**
     * Returns the index of the left child of the node at the specified index.
     * 
     * @param i The index of the node whose left child index is to be found.
     * @return The index of the left child node.
     */
    private int lc(int i){
        return 2*i+1;
    }

    /**
     * Returns the index of the right child of the node at the specified index.
     * 
     * @param i The index of the node whose right child index is to be found.
     * @return The index of the right child node.
     */
    private int rc(int i){
        return 2*i+2;
    }

     /**
     * Inserts a candidate into the min-heap. If the heap is full, the insertion is ignored.
     * After insertion, the heap property is restored by calling heapifyUp.
     * 
     * @param candidate The candidate to be inserted into the heap.
     */
    public void HeapInsert(Candidate candidate) {
        if (size == capacity) {
            return;
        }
        elected[size] = candidate;
        heapifyUp(size);
        size++;
    }

     /**
     * Restores the heap property by "bubbling up" the candidate at the given index.
     * It compares the candidate with its parent and swaps them if the parent has more votes.
     * The process is repeated until the heap property is restored.
     * 
     * @param index The index of the candidate to move up the heap.
     */
    private void heapifyUp(int index){
        if(index!=0){
            int parent=parent(index);
            if(elected[parent].votes>elected[index].votes){
                swap(parent, index);
                heapifyUp(parent);
            }
        }
    }

    /**
     * Swaps the candidates at the specified indices in the heap.
     * 
     * @param a The index of the first candidate.
     * @param b The index of the second candidate.
     */
    private void swap(int a,int b){
        Candidate temp=elected[a];
        elected[a]=elected[b];
        elected[b]=temp;
    }

    /**
     * Removes the candidate with the minimum number of votes (root of the heap).
     * After removal, the heap property is restored by calling heapifyDown.
     */
    public void HeapDeleteMin(){
        if(size==0){
            return;
        }
        elected[0]=elected[size-1];//moves last candidate to the root
        size--;
        if(size>1){
            heapifyDown(0);
        }
    }

    /**
     * Restores the heap property by "bubbling down" the candidate at the given index.
     * It compares the candidate with its children and swaps it with the smaller child if needed.
     * The process is repeated until the heap property is restored.
     * 
     * @param index The index of the candidate to move down the heap.
     */
    private void heapifyDown(int index){
        int lc=lc(index);
        int rc=rc(index);
        int min=index;
        if(hasLC(index) && elected[lc(index)].votes<elected[min].votes){
            min=lc;
        }
        if(hasRC(index) && elected[rc(index)].votes<elected[min].votes){
            min=rc;
        }
        if(min!=index){
            swap(index, min);
            heapifyDown(min);
        }
    }

    /**
     * Checks if the node at the given index has a left child.
     * 
     * @param i The index of the node to check.
     * @return True if the node has a left child, false otherwise.
     */
    private boolean hasLC(int i){
        return lc(i)<size;
    }

    /**
     * Checks if the node at the given index has a right child.
     * 
     * @param i The index of the node to check.
     * @return True if the node has a right child, false otherwise.
     */
    private boolean hasRC(int i){
        return rc(i)<size;
    }

    /**
     * Elects all the candidates in the heap by setting their `isElected` flag to true 
     * and appending the candidate's details (ID, PID, votes) to the provided StringBuffer.
     * 
     * @param Mstring The StringBuffer to which the election details are appended.
     * @param pid The ID of the process conducting the election.
     */
    public void elect(StringBuffer Mstring,int pid){
        for(int i=0; i<capacity; i++){
            if(elected[i]!=null){
                elected[i].isElected = true;
                Mstring.append("  <" + elected[i].cid + "> <" + pid + "> <" + elected[i].votes + ">,\n");
            }
            
        }
    }
 
}
