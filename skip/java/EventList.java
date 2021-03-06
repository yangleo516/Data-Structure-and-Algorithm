//
// EVENTLIST.JAVA
// Skeleton code for your EventList collection type.
//
import java.util.*;

class EventList {
    
    Random randseq;
    Event head = new Event((int)Double.NEGATIVE_INFINITY,"");		//Create head for Eventlist
    Event tail = new Event((int)Double.POSITIVE_INFINITY,"");		//Create tail for Eventlist
    public int num = 1;												//Set the head's height as 1
    
    ////////////////////////////////////////////////////////////////////
    // Here's a suitable geometric random number generator for choosing
    // pillar heights.  We use Java's ability to generate random booleans
    // to simulate coin flips.
    ////////////////////////////////////////////////////////////////////
    
    int randomHeight()
    {
	int v = 1;
	while (randseq.nextBoolean()) { v++; }
	return v;
    }
    
    //
    // Constructor
    //
    public EventList()
    {
    	randseq = new Random(58243); 					// You may seed the PRNG however you like.
//   	head.next = new Event[2000];					//Part one, fix head's height to be 2000
//   	tail.prev = new Event[2000];					//Part one, fix tail's height to be 2000
    	head.next = new Event[num];  					//Initialize skip list
//    	tail.prev = new Event[num];
    	head.next[0] = tail;							// head's next points tail
//    	tail.prev[0] = head;							// tail's prev points head
    	/*for(int l = 0;l<2000;l++){					// Part one, each level of head points tail
    		head.next[l] = tail;						
    		tail.prev[l] = head;
    	}*/
    	head.link = null;								// Initialized liked list for a node.
    	tail.link = null;
    }

    
    //
    // Add an Event to the list.
    //
    public void insert(Event e)
    {
    	int t = randomHeight();						
    	e.next = new Event[t];						//e's pillar gets a random height
//    	e.prev = new Event[t];
    	Event[] oldNext = head.next;				// Copy head's pillar into a new one
//		Event[] oldPrev = tail.prev;				
    	while(head.next.length < t){				// Double pillars' height dynamically,
    		num = num * 2;							// until it is at least height t.
    		head.next = new Event[num];
//    		tail.prev = new Event[num];		
    	}
    	
    	rePoint(head.next,oldNext);					// Call "rePoint" method to re-point, then do
    												// insertion
    	int l = head.next.length-1;					
    	Event x = head;
    	Event y = head;
    	
    	Event target = findYear(e.year);	//Call "findYear" method to see if e has already existed.
    	
    	if(target != null){					// link nodes with same year, if there is already a node
    		e.link = target.link;			// with same year
			target.link = e;
    	}
    	else{								// if e doesn't exist, then insert it into the skip list.
    		while(l >= 0){
        		y = x.next[l];
        		if(y.year < e.year){
        			x = y;
        		}
        		else{
        			if(l < t){
        					x.next[l] = e;
            				e.next[l] = y;
//            				e.prev[l] = x;
//            				y.prev[l] = e;
        			}
        			l--;
        		}
        	}
        }
    }
    
    /**
     * Make head's and tail's pillar re-point their original nodes.
     * @param newNext	next array of new head
     * @param oldNext	copy of next array from old head
     */
    public void rePoint(Event[] newNext, Event[] oldNext){
    	for(int l=0; l<newNext.length;l++){				
    		if(l>=oldNext.length){
    			newNext[l] = tail;
//    			newPrev[l] = head;								//Part one & Part two(a)
    		}
    		else{
    			newNext[l] = oldNext[l];
//    			newPrev[l] = oldPrev[l];						//Part one & Part two(a)
    		}
    	}
    }
    
    //
    // Remove all Events in the list with the specified year.
    //
    public void remove(int year)
    {
    	Event x = findYear(year);						// Call "findYear" method to find the node
    													//  with year in the skip list 
    	Event h1 = head;
    	Event h2 = head;
    	int l = num-1;
    	if(x!=null){														
    		while(l >= 0){											
    			h2 = h1.next[l];						
    			if(h2.year < x.year){
    				h1 = h2;
    			}
    			else{
    				if(h2.year == x.year){				// remove node x in linked skip list
    					h1.next[l] = x.next[l];			
    				}
    				l--;
    			}
    		}
    	}													//Part one	
    	/*if(x != null){									// Remove node
    		for(int l = x.next.length-1; l>=0; l--){		// Remove x from double linked skip list
        		x.prev[l].next[l] = x.next[l];
        		x.next[l].prev[l] = x.prev[l];
        	}
    	}*/
    	Event y = x;										//Unlink nodes from a certain chain.
    	while(x != null){
    		y = x.link;
    		x = null;
    		x = y;
    	}
    }
    
    /**
     * Find a node with a certain year
     * @param year 
     * @return An event object (node) with a certain year
     */
    public Event findYear(int year){
    	int l = head.next.length-1;
    	Event x = head;
    	Event y = head;
    	while(l >= 0){
    		y = x.next[l];
    		if(y.year == year){
    			return y;
    		}
    		else 
    			if(y.year < year){
    				x = y;
    			}
    			else l--;
    	}
    	return null;
    }
    
    //
    // Find all events with greatest year <= input year
    //
    public Event [] findMostRecent(int year)
    {
    	Event y = findYear(year);			// Call "findYaer" method to locate the node with the 
    										// given year
    	if(y == null){
    		y = findLatestYear(year);		// Call "findLatestYear" method to find <= the given year
    		if(y==null){					// If it doesn't exist, return null
    			return null;
    		}
    	}
    	
    	int num = findRangeHelper(y.year, y.year);	// Call findRangeHelper to get the number of 
    												// nodes which are between "first" and "last"
    	Event[] latest = new Event[num];
    	int i = 0;
    	while(y!=null){								// Traverse and push the most recent nodes
        	latest[i] = y;							// into "latest" array.
        	i = i + 1;
        	y = y.link;
        }
    	return latest;
    }
    
    /**
     * Find the latest year <= the given year
     * @param year
     * @return	the latest year <= the given year
     */
    public Event findLatestYear(int year){
    	int l = head.next.length-1;
    	Event x = head;
    	Event y = head;
    	while(l >= 0){
    		y = x.next[l];
    		if(y.year < year){
    			while(y.next[0].year < year){
    				y = y.next[0];
    			}
    			return y;
    		}
    		else l--;
    	}
    	return null;
    }
   
    //
    // Find all Events within the specific range of years (inclusive).
    //
    public Event[] findRange(int first, int last)
    {
    	int num = findRangeHelper(first,last);		// Call findRangeHelper to get the number of 
													// nodes which are between "first" and "last"
    	if(num == 0){								// return null if there does not exist such nodes
    		return null;
    	}
    	
    	Event x = findYear(first);
    	if(x==null){							    // Find a node with year >= "first"
    		x = findLatestYear(first);
    		if(x==null){
    			x = head.next[0];
    		}
    		else x = x.next[0];
    	}
    	
    	Event[] range = new Event[num];
    	int i = 0;
    												
    	while(x.year <= last){						
    		Event k = x;							
    		while(k != null){					// k traverses the chain where nodes with same year
    			range[i] = k;
    			i++;
    			k = k.link;
    		}
    		x = x.next[0];							// Do list walk to find the rest of the range
    	}
    	return range;
    }
    
    /**
     * @param first the left-most node index in the range
     * @param last	the right-most node index in the range
     * @return number of nodes within the range
     */
    public int findRangeHelper(int first, int last){
    	int counter = 0;
    	
    	Event x = findYear(first);						
    	if(x==null){							// If x is null,find the node with year >= "first"
    		x = findLatestYear(first);
    		if(x==null){
    			x = head.next[0];
    		}
    		else x = x.next[0];
    	}
    	
    	while(x.year <= last){
    		Event k = x;
    		while(k != null){
    			counter++;                     // counter # of nodes
    			k = k.link;						// k traverses the chain where nodes with same year.
    		}
    		x = x.next[0];
    	}
    	
    	return counter;
    }
}
