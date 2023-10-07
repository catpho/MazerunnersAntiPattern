package com.mazerunners.maze;

import java.util.LinkedList;
import java.util.List;

/**
 * Generic Implementation of a Stack containing push(), pop(), peek(), and isEmpty() methods.
 */
public class CharStack<E> {

    private List<E> list;
    public int nextValue;

    // Constructor initializing type of list and nextValue
    public CharStack() {
        list = new LinkedList<>();
        nextValue = 0;
    }

    // Places x ontop of the stack and increments nextValue
    public void push(E x) {
        list.add(0,x);
        nextValue++;
    }

    // Removes and returns the stack's head value and decrements nextValue
    public E pop() {
        nextValue--;
        return list.remove(0);
    }

    // Returns value at top of stack
    public E peek() {
        return list.get(0);
    }

    // Returns true if stack is empty, otherwise false
    public boolean isEmpty(){
        return list.isEmpty();
    }
}
