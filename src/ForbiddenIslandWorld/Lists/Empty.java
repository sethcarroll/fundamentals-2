package ForbiddenIslandWorld.Lists;

import java.util.Iterator;

import ForbiddenIslandWorld.Predicates.IPred;

//Represents an empty list of type T
public class Empty<T> implements IList<T> {
  //filter out elements that don't satisfy the predicate
  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  // returns true if the list is not empty
  public boolean isCons() {
    return false;
  }

  // casts to a Cons<T>
  public Cons<T> asCons() {
    throw new RuntimeException("Not applicable for empty");
  }

  // iterate through an empty list
  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }

  // return the length of this list
  public int length() {
    return 0;
  }

  // adds the item to the list
  public IList<T> add(T t) {
    return new Cons<T>(t, this);
  }

  // deletes an item from the list
  public IList<T> delete(int index) {
    return this;
  }

  // returns item at index
  public T indexItem(int index) {
    throw new IndexOutOfBoundsException("Index out of bounds");
  }
}