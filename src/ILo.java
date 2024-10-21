public interface ILo<T> {
    void add(T item);
    T get(int index);
    int size();
    ILo<T> filter(IPredicate<T> pred);
    boolean any(IPredicate<T> pred);
    boolean all(IPredicate<T> pred);
    <R> ILo<R> map(IFunc<T, R> func);
    <Y> Y foldr(IFunc2<T, Y, Y> func, Y base);
}

