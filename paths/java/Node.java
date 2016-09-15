//
//	Create a data structure "Node", containing a T type value, a integer key, and a handle
//
public class Node<T> {
	T value;
	int key;
	Handle handle = new Handle();
	
	//constructor
	public Node(int key, T value) {
		this.key = key;
		this.value = value;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public Handle getHandle() {
		return this.handle;
	}
}
