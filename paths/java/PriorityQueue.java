import java.util.ArrayList;

//
// PRIORITYQUEUE.JAVA
// A priority queue class supporting sundry operations needed for
// Dijkstra's algorithm.
//

class PriorityQueue<T> {
    public ArrayList<Node<T>> heap;						// Implement Priority Queue with an array
    // 
    // constructor
    public PriorityQueue()
    {
    	heap = new ArrayList<Node<T>>();			    // Initialize the heap
    	heap.add(null);									// Make 1st position occupied, so we do
    													//  something from the 2nd position. 
    }
    
    // Return true iff the queue is empty.
    //
    public boolean isEmpty()							//
    {
    	if(heap.size() == 1){
    		return true;
    	}
    	else return false;
    }
    
    // Insert a pair (key, value) into the queue, and return
    // a Handle to this pair so that we can find it later in
    // constant time.
    //
    Handle insert(int key, T value)
    {
    	Node<T> newNode = new Node<T>(key,value);			// Create a new node containing a key
    														// 		and a T type value.
    	heap.add(newNode);									// Add the new node into the heap
    	int i = heap.size()-1;
    	newNode.getHandle().setIndex(i);					// Initialize an index for the new node
    	int p;
    	while(i > 1){		
    			p = (int)(i/2);
    		if(key < heap.get(p).getKey()) {				
    			swap(heap, i, p);					// Swap the new node with its parent
    												// until the heap's property is maintained
    		}
    		i = p;								
    	}
    	Handle h = newNode.getHandle();
    	return h;
    }
    
    /**
     * Take in the heap, indexes for in two nodes which are swapped with each other
     * @param hp	the priority queue (heap)
     * @param i		index for one node
     * @param j		index for another node
     */
    public void swap (ArrayList<Node<T>> hp, int i, int j){
    	Node<T> child = hp.get(i);
    	Node<T> parent = hp.get(j);
    	heap.set(i, parent);								// When swap two node, swap their handle
    	heap.get(i).getHandle().setIndex(i);
    	heap.set(j, child);
    	heap.get(j).getHandle().setIndex(j);
     }
    
    // Return the smallest key in the queue.
    //
    public int min()
    {
    	if(!this.isEmpty()) {
    		return heap.get(1).getKey();
    	}
    	else return -1;
    }
    
    // Extract the (key, value) pair associated with the smallest
    // key in the queue and return its "value" object.
    //
    public T extractMin()
    {
    	if(heap.size() <= 1) {					// If there's no nodes in the heap, return nothing.
    		return null;
    	}
    	Node<T> root = heap.get(1);			// Get the root of the heap
    	root.getHandle().setIndex(-1);		// Mark the root as extracted by setting its index as -1
    	int last = heap.size()-1;
    	heap.set(1, heap.get(last));		// replace the root with the last node in the heap
    	heap.remove(last);
    	MinHeapify(1);						// Heapify from the root.
    	return root.getValue();
    }
    
    /**
     * Start from position i to the bottom, rebuild the array into a heap.
     * @param i 	start position
     */
    public void MinHeapify(int i) {
    	int l = 2 * i;							// left child
    	int r = 2 * i + 1;						// right child
    	int min;
    	
    	if(heap.size()==1) {					// If there's no node, do nothing.
    		return;
    	}
    	
    	int iKey = heap.get(i).getKey();
    	
    	//	 the following is to point the minimum node among i and its two children.
    	//
    	if(l <= heap.size()-1 && heap.get(l).getKey() < iKey) {
    		min = l;
    	}
    	else { min = i;}
    	int mKey = heap.get(min).getKey();
    	if(r <= heap.size()-1 && heap.get(r).getKey() < mKey) {
    		min = r;
    	}
    	
    	if(min != i) {
    		swap(heap, i, min);			// swap i with its smaller child
    		MinHeapify(min);			// maintain the heap.
    	}
    }
    
    // Look at the (key, value) pair referenced by Handle h.
    // If that pair is no longer in the queue, or its key
    // is <= newkey, do nothing and return false.  Otherwise,
    // replace "key" by "newkey", fixup the queue, and return
    // true.
    //
    public boolean decreaseKey(Handle h, int newkey)
    {
    	int i = h.getIndex();
    	if(h.getIndex() < 1){
    		return false;
    	}
    	if(handleGetKey(h) < newkey) {
    		return false;
    	}
    	heap.get(i).setKey(newkey);
    	
    	while(i>1 && heap.get((int)(i/2)).getKey() > newkey ) {   // if the pair's key is smaller than
    																// its parent's key, swap them		
    		swap(heap, i, (int)(i/2));
    		i = (int)(i/2);
    	}
    	return true;
    }
    
    
    // Get the key of the (key, value) pair associated with a 
    // given Handle. (This result is undefined if the handle no longer
    // refers to a pair in the queue.)
    //
    public int handleGetKey(Handle h)
    {
    	if(h.getIndex() == -1) {
    		return 0;
    	}
    	else{
    		int i = h.getIndex();
    		if(i<=heap.size()-1) {
    			return heap.get(i).getKey();
    		}
    		else return 0;
    	}
    }

    // Get the value object of the (key, value) pair associated with a 
    // given Handle. (This result is undefined if the handle no longer
    // refers to a pair in the queue.)
    //
    public T handleGetValue(Handle h)
    {
    	if(h.getIndex() == -1) {
    		return null;
    	}
    	else {
    		int i = h.getIndex();
    		return heap.get(i).getValue();
    	}
    }
    
    // Print every element of the queue in the order in which it appears
    // in the implementation (i.e. the array representing the heap).
    public String toString()
    {
    	String s = "";
    	for(int i=1; i<heap.size(); i++) {
    		s = s +"(" + heap.get(i).getKey() + " " +  heap.get(i).getValue()
    				+ ")" + "\n";
    	}
    	return s;
    }
}
