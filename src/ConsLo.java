public class ConsLo<T> implements ILo<T> {
    T first;
    ILo<T> rest;

    ConsLo(T first, ILo<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public ConsLo<T> add(T item) {
        return new ConsLo<T>(item, this);
    }

    public T get(int index) {
        if (index == 0) {
            return this.first;
        } else {
            return this.rest.get(index - 1);
        }
    }

    public int size() {
        return 1 + this.rest.size();
    }

    public ILo<T> filter(IPredicate<T> pred) {
        if (pred.apply(this.first)) {
            return new ConsLo<T>(this.first, this.rest.filter(pred));
        } else {
            return this.rest.filter(pred);
        }
    }

    public boolean any(IPredicate<T> pred) {
        return pred.apply(this.first) || this.rest.any(pred);
    }

    public boolean all(IPredicate<T> pred) {
        return pred.apply(this.first) && this.rest.all(pred);
    }

    public <R> ILo<R> map(IFunc<T, R> func) {
        return new ConsLo<R>(func.apply(this.first), this.rest.map(func));
    }

    public <Y> Y foldr(IFunc2<T, Y, Y> func, Y base) {
        return func.apply(this.first, this.rest.foldr(func, base));
    }
}
