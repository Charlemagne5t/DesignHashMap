public class Main {
    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        map.put(1, 1);
        map.put(2,2);
        map.put(2,1);
        System.out.println(map.get(2));


    }
}
