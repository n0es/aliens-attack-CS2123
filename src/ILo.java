public interface ILo<T> {
  // add the given item to the front of this list
  ConsLo<T> add(T item);

  // get the item at the given index
  T get(int index);

  // return the number of items in the list
  int size();

  //filter through the list by some predicate
  ILo<T> filter(IPredicate<T> pred);

  // filter through the list by some predicate
  <R> ILo<T> filter(IPredicate2<T, R> pred, R r);

  //return true if the predicate applies to any element of the list
  boolean any(IPredicate<T> pred);

  // return true if the predicate applies to any element of the list
  <R> boolean any(IPredicate2<T, R> pred, R r);

  //return true of the predicate applies to all elements of the list
  boolean all(IPredicate<T> pred);//return true of the predicate applies to all elements of the list

  // map the given function to all elements of the list
  <R> ILo<R> map(IFunc<T, R> func);

  // fold the given function over all elements of the list
  <Y> Y foldr(IFunc2<T, Y, Y> func, Y base);
}

