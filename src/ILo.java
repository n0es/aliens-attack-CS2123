public interface ILo<T> {
    ConsLo<T> add(T item); //Add a new item to the list
    T get(int index); //find the index of a specific item in the list
    int size();//return the size of the list
    ILo<T> filter(IPredicate<T> pred);//filter through the list by some predicate
    <R> ILo<T> filter(IPredicate2<T, R> pred, R r);//filter through the list by some predicates
    boolean any(IPredicate<T> pred);//return true if the predicate applies to any element of the list
    <R> boolean any(IPredicate2<T, R> pred, R r);
    boolean all(IPredicate<T> pred);//return true of the predicate applies to all elements of the list
    <R> ILo<R> map(IFunc<T, R> func);
    <Y> Y foldr(IFunc2<T, Y, Y> func, Y base);
}

