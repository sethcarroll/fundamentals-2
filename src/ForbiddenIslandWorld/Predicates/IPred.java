package ForbiddenIslandWorld.Predicates;

//Represents a predicate for type T
public interface IPred<T> {

  // Applies this predicate to the given t
  boolean apply(T t);
}