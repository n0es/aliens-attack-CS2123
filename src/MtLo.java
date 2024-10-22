public class MtLo<T> implements ILo<T> {
    MtLo() {
    }

    public ConsLo<T> add(T item) {
        return new ConsLo<T>(item, this);
    }

    public T get(int index) {
        throw new RuntimeException("Index out of bounds");
    }

    public int size() {
        return 0;
    }

    public ILo<T> filter(IPredicate<T> pred) {
        return this;
    }

    public boolean any(IPredicate<T> pred) {
        return false;
    }

    public boolean all(IPredicate<T> pred) {
        return true;
    }

    public <R> ILo<R> map(IFunc<T, R> func) {
        return new MtLo<R>();
    }

    public <Y> Y foldr(IFunc2<T, Y, Y> func, Y base) {
        return base;
    }
}
