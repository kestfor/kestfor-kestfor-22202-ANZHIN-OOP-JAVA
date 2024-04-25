package factory.Warehouses;
import threadpool.BlockingQueue;
import java.util.Objects;

public class Warehouse<T> extends BlockingQueue<T> {

    protected int capacity;

    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentSize() {
        return queue.size();
    }

    public boolean isFull() {
        return queue.size() == capacity;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    synchronized public T get() {
        notify();
        return super.get();
    }

    @Override
    synchronized public void put(T el) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        super.put(el);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Warehouse<?> warehouse = (Warehouse<?>) object;
        return capacity == warehouse.capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity);
    }

}
