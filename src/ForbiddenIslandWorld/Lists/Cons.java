package ForbiddenIslandWorld.Lists;

import java.util.Iterator;

import ForbiddenIslandWorld.Lists.IList;
import ForbiddenIslandWorld.Lists.IListIterator;
import ForbiddenIslandWorld.Predicates.IPred;

//Represents a non-empty list of type T
public class Cons<T> implements IList<T> {
  T first;
  IList<T> rest;

  // constructor
  public Cons(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // filter out elements that don't satisfy the predicate
  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new Cons<>(this.first, this.rest.filter(pred));
    } else {
      return this.rest.filter(pred);
    }
  }

  // returns true if the list is not empty
  public boolean isCons() {
    return true;
  }

  // casts to Cons<T>
  public Cons<T> asCons() {
    return new Cons<T>(this.first, this.rest);
  }

  // adds the item to the list
  public IList<T> add(T t) {
    return new Cons<T>(t, this);
  }

  // to iterate on the list
  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }

  // return the length of this list
  public int length() {
    return 1 + this.rest.length();
  }

  // deletes the item at the index
  public IList<T> delete(int index) {
    if (index == 0) {
      return this.rest;
    } else {
      return new Cons<T>(this.first, this.rest.delete(index - 1));
    }
  }

  // returns item at the index
  public T indexItem(int index) {
    if (index == 0) {
      return this.first;
    } else {
      return this.rest.indexItem(index - 1);
    }
  }

}