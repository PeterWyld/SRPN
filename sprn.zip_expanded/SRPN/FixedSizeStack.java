import java.util.Stack;

/**
 * 
 * This Class is the same as a stack except that a maximum size can be defined
 * during instantiation. (default is 10)
 * @param <E>
 */

public class FixedSizeStack<E> extends Stack<E>{

	private int maxSize = 10;
	private static final long serialVersionUID = 9077603627564690101L;
	
	public FixedSizeStack(int maxSize) {
		super();
		this.maxSize = maxSize;
	}
	
	public FixedSizeStack() {
		super();
	}
	
	@Override
    public E push(E item) {
		int len = size();
		if (len <= maxSize) {
			addElement(item);
		} else {
			throw new StackOverflowError();
		}

        return item;
    }
	
}
