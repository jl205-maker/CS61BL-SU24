import java.util.Iterator;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {


    /* Instance Variables*/
    LinkedList<Entry<K, V>>[] baseArr;
    int size;
    int maxCapacity;
    final int DEFAULT_CAPACITY = 16;
    double loadFactor;
    final double DEFAULT_LOADFACTOR = 0.75;

    /* Constructor */
    public HashMap(int capacity, double loadFactor) {
        this.maxCapacity = capacity;
        this.baseArr = new LinkedList[maxCapacity];
        for (int i = 0; i <maxCapacity; i ++) {
            baseArr[i] = new LinkedList<>();
        }
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public HashMap(int capacity) {
        this(capacity, 0.75);
    }

    public HashMap() {
        this(16, 0.75);
    }


    /* Map61BL Interface methods */

    @Override
    public void clear() {
        for (int i = 0; i < maxCapacity; i++) {
            baseArr[i].clear();
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int keyIndex = Math.floorMod(key.hashCode(), maxCapacity);
        for (Entry<K, V> entry : baseArr[keyIndex]) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int keyIndex = Math.floorMod(key.hashCode(), maxCapacity);
        for (Entry<K, V> entry : baseArr[keyIndex]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int keyIndex = Math.floorMod(key.hashCode(), maxCapacity);
        for (Entry<K, V> entry : baseArr[keyIndex]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        baseArr[keyIndex].add(new Entry<>(key, value));
        size++;
        if ((double) size / maxCapacity > loadFactor) {
            resize();
        }
    }

    public int capacity() {
        return maxCapacity;
    }

    private void resize() {
        int newCapacity = maxCapacity * 2;
        LinkedList<Entry<K, V>>[] newBaseArr = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBaseArr[i] = new LinkedList<>();
        }

        for (int i = 0; i < maxCapacity; i++) {
            for (Entry<K, V> entry : baseArr[i]) {
                int keyIndex = Math.floorMod(entry.key.hashCode(), newCapacity);
                newBaseArr[keyIndex].add(entry);
            }
        }

        baseArr = newBaseArr;
        maxCapacity = newCapacity;
    }

    @Override
    public V remove(K key) {
        int keyIndex = Math.floorMod(key.hashCode(), maxCapacity);
        Iterator<Entry<K, V>> entryIterator = baseArr[keyIndex].iterator();
        while (iterator().hasNext()) {
            Entry<K, V> entry = entryIterator.next();
            if (entry.key.equals(key)) {
                V value = entry.value;
                entryIterator.remove();
                size --;
                return value;
            }
        }
       return null;
    }

    @Override
    public boolean remove(K key, V value) {
        int keyIndex = Math.floorMod(key.hashCode(), maxCapacity);
        Iterator<Entry<K, V>> iterator = baseArr[keyIndex].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.key.equals(key) && entry.value.equals(value)) {
                iterator.remove();
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int bucketIndex = 0;
            private Iterator<Entry<K, V>> bucketIterator = baseArr[bucketIndex].iterator();

            @Override
            public boolean hasNext() {
                while (!bucketIterator.hasNext() && bucketIndex < maxCapacity - 1) {
                    bucketIndex++;
                    bucketIterator = baseArr[bucketIndex].iterator();
                }
                return bucketIterator.hasNext();
            }

            @Override
            public K next() {
                return bucketIterator.next().key;
            }
        };
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry<K, V> other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
