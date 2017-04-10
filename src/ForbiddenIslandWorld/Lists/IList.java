package ForbiddenIslandWorld.Lists;

import ForbiddenIslandWorld.Predicates.IPred;

// to represent a list
public interface IList<T> extends Iterable<T> {
  // filter out elements that don't satisfy the predicate
  IList<T> filter(IPred<T> pred);

  // returns true if the list is not empty
  boolean isCons();

  // casts the list to a Cons<T>
  Cons<T> asCons();

  // adds an item to the list
  IList<T> add(T t);

  // returns the length of the list
  int length();

  // removes the specified item from the list at the index
  IList<T> delete(int index);

  // returms item at index
  T indexItem(int index);
}