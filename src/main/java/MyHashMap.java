import java.util.Arrays;
import java.util.LinkedList;

public class MyHashMap {
    private LinkedList[] storage;
    private int[] primeNumbers;
    private int primeNumbersNextIndex = 0;
    private int capacity;
    private int countEntries = 0;
    private double loadFactor = 0.75;

    public MyHashMap() {
        primeNumbers = new int[]{31, 127, 233, 547, 1019, 2053, 4099, 8191, 16069, 32051};
        capacity = primeNumbers[primeNumbersNextIndex++];
        storage = new LinkedList[capacity];
        Arrays.fill(storage, new LinkedList());
    }


    public void put(int key, int value) {
        if((double) (countEntries + 1) / capacity > loadFactor){
            MyHashMap expanded = new MyHashMap();
            expanded.primeNumbers = this.primeNumbers;
            expanded.primeNumbersNextIndex = this.primeNumbersNextIndex;
            expanded.capacity = expanded.primeNumbers[expanded.primeNumbersNextIndex++];
            expanded.storage = new LinkedList[expanded.capacity];
            Arrays.fill(expanded.storage, new LinkedList());


            for (LinkedList bucket: this.storage) {
                while (!bucket.isEmpty()){
                    MapEntry entry = (MapEntry) bucket.remove();
                    expanded.put(entry.key, entry.value);
                }
            }
            this.primeNumbersNextIndex = expanded.primeNumbersNextIndex;
            this.storage = expanded.storage;
            this.capacity = expanded.capacity;
        }

        MapEntry entry = new MapEntry(key, value);
        int entryBucket = hash(key);
        if(this.get(key) == -1){
            this.storage[entryBucket].add(entry);
            countEntries++;
        }else{
            this.remove(key);
            this.storage[entryBucket].add(entry);
        }
    }

    public int get(int key) {
        int entryBucket = hash(key);
        LinkedList<MapEntry> bucket = (LinkedList<MapEntry>) this.storage[entryBucket];
        for (MapEntry entry: bucket) {
            if(entry.key == key){
                return entry.value;
            }
        }
        return -1;
    }

    public void remove(int key) {
        if(this.get(key) != Integer.MIN_VALUE){
            MapEntry entryToDelete = new MapEntry(key, this.get(key));
            int entryBucket = hash(key);
            LinkedList<MapEntry> bucket = (LinkedList<MapEntry>) this.storage[entryBucket];
            bucket.remove(entryToDelete);
            countEntries--;
        }
    }

    private int hash(int key){
        return key % capacity;
    }
}

class MapEntry {
    int key;
    int value;

    public MapEntry(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public MapEntry() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapEntry entry = (MapEntry) o;

        if (key != entry.key) return false;
        return value == entry.value;
    }

    @Override
    public int hashCode() {
        int result = key;
        result = 31 * result + value;
        return result;
    }
}