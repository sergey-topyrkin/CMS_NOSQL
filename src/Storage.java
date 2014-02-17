import oracle.kv.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by Anatoly.Cherkasov on 14.02.14.
 */
public class Storage {


    private KVStore store;

    private String storeHost;
    private int storePort;
    private String storeName;

    /**
     * Конструктор открывает соедние с хранилищем
     * @param storeHost Хост на котором расположено хранилище
     * @param storePort Порт к которому прицеплено хранилище
     * @param storeName Имя хранилища
     */
    Storage(String storeHost, int storePort, String storeName){
        this.storeHost = storeHost;
        this.storePort = storePort;
        this.storeName = storeName;

        KVStoreConfig kconfig = new KVStoreConfig(storeName, storeHost + ":" + storePort);
        store = KVStoreFactory.getStore(kconfig);
        System.out.println("Storage Opened");
    }

    /**
     * Добавление строки в Хранилище
     * @param keyMajorPart Major часть ключа
     * @param keyMinorPart Minor часть ключа
     */
    public void addRow(String keyMajorPart, String keyMinorPart){
        store.put(Key.createKey(keyMajorPart,keyMinorPart), Value.EMPTY_VALUE);
    }

    /**
     * Добавление строки в Хранилище
     * @param key ключ
     */
    public void addRow(Key key){
        store.put(key, Value.EMPTY_VALUE);
        System.out.println("Row added");
    }

    /**
     * Добавление коллекции строк в Хранилище
     * @param keyList Коллекция ключей
     */
    public void addRowList(ArrayList<Key> keyList){
        for (Key k : keyList){
            this.addRow(k);
        }
        System.out.println("Rows added");
    }

    /**
     * Удаление строки из Хранилища
     * @param keyMajorPart Major часть ключа
     * @param keyMinorPart Minor часть ключа
     */
    public void deleteRow(String keyMajorPart, String keyMinorPart) {

        store.delete(Key.createKey(keyMajorPart, keyMinorPart));
        System.out.println("Row deleted");
    }

    /**
     * Удаление строки из Хранилища
     * @param key ключ
     */
    public void deleteRow(Key key) {
        store.delete(key);
        System.out.println("Row deleted");
    }

    /**
     * Удаление строки из Хранилища
     * @param key ключ
     */
    public void deleteRow(String key) {
        store.delete(Key.fromString(key));
        System.out.println("Row deleted");
    }

    /**
     * Удаление строки из Хранилища
     * @param keyList Коллекция ключей
     */
    public void deleteRowList(ArrayList<Key> keyList) {
        for (Key k : keyList){
            this.deleteRow(k);
        }
        System.out.println("Rows deleted");
    }

    /**
     * Удаление строки из Хранилища
     * @param majorKeyFull Major часть ключа. Полностью.
     * @param prefix диапазон на основе префикса
     */
    public int deleteRowsByRange(String majorKeyFull, String prefix) {
        int cnt;
        cnt = store.multiDelete(Key.fromString(majorKeyFull), new KeyRange(prefix), Depth.PARENT_AND_DESCENDANTS );
        System.out.println("Rows deleted: " + cnt);
        return cnt;
    }

    /**
     * Получение строки из Хранилища
     * @param majorKeyFull Major часть ключа. Полностью.
     * @param prefix диапазон на основе префикса
     */
    public ArrayList<String> getRowsByRange(String majorKeyFull, String prefix) {
        ArrayList<String> keys = new ArrayList<String>();
        SortedSet<Key> keySortedSet = store.multiGetKeys(Key.fromString(majorKeyFull), new KeyRange(prefix), Depth.PARENT_AND_DESCENDANTS);

        for (Key k : keySortedSet){
            keys.add( k.getFullPath().toString() );
            System.out.println("[" + k.getFullPath() + "]");
        }
        System.out.println("ok " + keySortedSet.size());
        return keys;
    }

    /**
     * Получение строки из Хранилища
     * @param majorKeyFull Major часть ключа. Полностью.
     * @param startRange начало диапазона
     * @param endRange конец диапазона
     */
    public ArrayList<String> getRowsByRange(String majorKeyFull, String startRange, String endRange) {
        ArrayList<String> keys = new ArrayList<String>();
        SortedSet<Key> keySortedSet = store.multiGetKeys(Key.fromString(majorKeyFull), new KeyRange(startRange, true, endRange, true), Depth.PARENT_AND_DESCENDANTS);

        for (Key k : keySortedSet){
            keys.add( k.getFullPath().toString() );
            System.out.println("["+ k.getFullPath() +"]");
        }
        System.out.println("ok " + keySortedSet.size());
        return keys;
    }

    /**
     * Получение значения из ключа из Хранилища
     * Функция под конкретную задачу. Возвращаем коллекцию из последних кусочков ключа.
     * @param majorKeyFull Major часть ключа. Полностью.
     * @param valueTagName Имя значения, котрое мы хотим достать из ключа
     */
    public ArrayList<String> getRowValuesByRange(String majorKeyFull, String valueTagName) {
        ArrayList<String> keys = new ArrayList<String>();
        SortedSet<Key> keySortedSet = store.multiGetKeys(Key.fromString(majorKeyFull), null, Depth.PARENT_AND_DESCENDANTS);

        for (Key k : keySortedSet){
            List<String> minorKey = k.getMinorPath();
            int valueIndex = minorKey.indexOf(valueTagName);
            if (valueIndex >= 0){
                keys.add( minorKey.get(valueIndex+1));
                System.out.println(valueTagName + ": " + minorKey.get(valueIndex+1));
            }
        }
        System.out.println("ok " + keys.size());
        return keys;
    }

    /**
     * Получение строки из Хранилища
     * @param majorKeyFull Major часть ключа. Полностью.
     * @param startRange начало диапазона
     * @param endRange конец диапазона
     */
    public ArrayList<String> getRowValuesByRange(String majorKeyFull, String startRange, String endRange) {
        ArrayList<String> keys = new ArrayList<String>();
        SortedSet<Key> keySortedSet = store.multiGetKeys(Key.fromString(majorKeyFull), new KeyRange(startRange, true, endRange, true), Depth.PARENT_AND_DESCENDANTS);

        for (Key k : keySortedSet){
            keys.add( k.getFullPath().toString() );
            System.out.println("["+ k.getFullPath() +"]");
        }
        System.out.println("ok " + keySortedSet.size());
        return keys;
    }





    /**
     * Закрываем соединение с хранилищем
     */
    public void close(){
        store.close();
        System.out.println("Storage Closed");
    }



    /**
     * Ниже идут геттеры и сеттеры для приватных полей
     */
    public String getStoreHost() {
        return storeHost;
    }

    public int getStorePort() {
        return storePort;
    }

    public String getStoreName() {
        return storeName;
    }

}
