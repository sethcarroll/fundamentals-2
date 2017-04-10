package ForbiddenIslandWorld.Lists;

import java.util.Iterator;

// to represent an iterator for ILists
public class IListIterator<T> implements Iterator<T> {
  IList<T> items;

  public IListIterator(IList<T> items) {
    this.items = items;
  }

  // returns true if the IList is not empty
  public boolean hasNext() {
    return this.items.isCons();
  }

  // Get the next value in this sequence
  // EFFECT: Advance the iterator to the subsequent value
  public T next() {
    Cons<T> itemsAsCons = this.items.asCons();
    T answer = itemsAsCons.first;
    this.items = itemsAsCons.rest;
    return answer;
  }

  // ignore
  public void remove() {
    throw new RuntimeException("Don't do this!");
  }

}