import oracle.kv.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Anatoly.Cherkasov on 06.02.14.
 * Класс для работы с хранилищами
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
     * @param key Ключ строки
     * @param value Значение строки
     */
    public void addRow(String key, byte[] value){
        store.put(Key.fromString(key), Value.createValue(value));
        //System.out.println("StorageRow inserted");
    }

    /**
     * Добавление строки в Хранилище
     * @param storageRow Строка Хранилища
     */
    public void addRow(StorageRow storageRow){
        store.put(Key.fromString(storageRow.getKey()), Value.createValue(storageRow.getValue()));
        //System.out.println("StorageRow inserted");
    }

    /**
     * Добавление коллекции строк в Хранилище
     * @param storageRowList Коллекция объектов Строка Хранилища
     */
    public void addRowList(ArrayList<StorageRow> storageRowList){
        for (StorageRow x : storageRowList){
            this.addRow(x);
        }
    }


    /**
     * Удаление строки из Хранилища
     * @param key Ключ целиком по которому будет удалено значение. Ключ передается в форме /major/-/minor
     */
    public void deleteRow(String key) {
        store.delete(Key.fromString(key));
        System.out.println("Row deleted");
    }

    /**
     * Удаление строки из Хранилища
     * @param storageRow Объект-строка, из которой будет взять ключ. По этому ключу будет выполнено удаление
     */
    public void deleteRow(StorageRow storageRow) {
        store.delete(Key.fromString(storageRow.getKey()));
        System.out.println("Row deleted");
    }

    /**
     * Удаление коллекции строк из Хранилища
     * @param storageRowList Коллекция объектов Строка Хранилища
     */
    public void deleteRowList(ArrayList<StorageRow> storageRowList){
        for (StorageRow x : storageRowList){
            this.deleteRow(x);
        }
    }

    /**
     * Получение одного значения из Хранилища по полному ключу
     * @param key Ключ по которому будем получать значение.  Ключ передается в форме /major/-/minor
     * @return Значение в виде массива байт для этого ключа
     */
    public byte[] getRowValue(String key) throws NullPointerException
    {
        byte[] val = null;
        ValueVersion valueVersion = store.get(Key.fromString(key));
        Value value = valueVersion.getValue();
        val = value.getValue();
        return val;
    }

    /**
     * Получение одного значения из Хранилища по полному ключу
     * @param key Ключ по которому будем получать значение.  Ключ передается в форме /major/-/minor
     * @return Значение в виде объекта типа StorageRow
     */
    public StorageRow getRow(String key) throws NullPointerException
    {
        byte[] val = null;
        ValueVersion valueVersion = store.get(Key.fromString(key));
        Value value = valueVersion.getValue();
        val = value.getValue();
        return new StorageRow(key, val);
    }


    /**
     * Получаем список значений по части major ключа. Для полного ключа этот метод не работает.
     * @param keyPart Часть major-части ключа. Передается в форме /major/xxx
     * @return Массив объектов типа StorageRow
     */
    private ArrayList<StorageRow> getRowListPartKey(String keyPart) {
        Key parentKey = Key.fromString(keyPart);
        ArrayList<StorageRow> result = new ArrayList<StorageRow>();
        try {
            Iterator<KeyValueVersion> records = store.storeIterator(Direction.UNORDERED, 0, parentKey, null, Depth.PARENT_AND_DESCENDANTS);
            while (records.hasNext()) {
                Key recordKey = records.next().getKey();
                /* ValueVersion vv = store.get(newKey);
                   Value v = vv.getValue(); */
                Value val = store.get(recordKey).getValue();
                String data = new String(val.getValue());
                // Сохраним значения в массиве
                result.add(new StorageRow(recordKey.toString(), val.getValue()));
            }
        } catch (RequestTimeoutException re) {
            System.out.println(re.getTimeoutMs());
        } catch (FaultException fe) {
        }
        return result;
    }

    /**
     * Получаем список значений по целому major ключу. Для части ключа этот метод не работает.
     * @param key Целая major-часть ключа. Передается в форме /major/xxx/
     * @return Массив объектов типа StorageRow
     */
    private ArrayList<StorageRow> getRowListFullKey(String key) {
        Key parentKey = Key.fromString(key);
        ArrayList<StorageRow> result = new ArrayList<StorageRow>();
        try {
            Iterator<KeyValueVersion> records = store.multiGetIterator(Direction.FORWARD, 0, parentKey, null, Depth.PARENT_AND_DESCENDANTS);
            while (records.hasNext()) {
                Key recordKey = records.next().getKey();
                /* ValueVersion vv = store.get(newKey);
                   Value v = vv.getValue(); */
                Value val = store.get(recordKey).getValue();
                String data = new String(val.getValue());
                // Сохраним значения в массиве
                result.add(new StorageRow(recordKey.toString(), val.getValue()));
            }
        } catch (RequestTimeoutException re) {
            System.out.println(re.getTimeoutMs());
        } catch (FaultException fe) {
        }
        return result;
    }

    /**
     * Получаем список значений по major ключу.
     * Если передается только часть ключа то fullKey установить в false.
     * Если передается целый ключ то fullKey установить в true.
     * @param key Ключ или часть ключа
     * @param fullKey Признак того что передается целый ключ или его часть
     * @return Массив объектов типа StorageRow
     */
    public ArrayList<StorageRow> getRowList(String key, boolean fullKey) {
        if (fullKey) {
            return getRowListFullKey(key);
        }
        else {
            return getRowListPartKey(key);
        }
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
