import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Anatoly.Cherkasov on 06.02.14.
 */

public class Example {


    /**
     * Считаем файл и из него получим массив CmsAttrValueRow
     * @param filepath Путь к файлу
     * @return Массив CmsAttrValueRow
     */
    public static ArrayList<CmsAttrValueRow> loadFromFile(String filepath) {

        String str;
        String[] strVar;

        ArrayList<CmsAttrValueRow> result = new ArrayList<CmsAttrValueRow>();

        try {
            // Открываем файл
            FileReader fr = new FileReader(filepath);
            // Передаем поток вычитывателю
            BufferedReader br = new BufferedReader(fr);
            // Считываем строку. Разбираем ее и делаем из нее объект типа CmsAttrValueRow
            while ((str = br.readLine()) != null) {
                strVar = str.split(";");
                result.add(new CmsAttrValueRow(strVar[0], strVar[1], strVar[2], strVar[3], strVar[4], strVar[5], strVar[6], strVar[7], strVar[8], strVar[9], strVar[10], strVar[11], strVar[12], strVar[13]));
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {

        /**
         * Координаты для связи с хранилищем
         */
        String storeHost = "localhost";
        int storePort = 5000;
        String storeName = "kvstore" ;

        //
        // Режим работы. Нужен что бы при тестировании переключать варианты заливки и вытаскивания.
        // single       - работаем с одиночными строками
        // collection   - работаем с коллекциями объектов
        // fromFile     - работаем со строками из файла
        //String mode = "single";
        //String mode = "collection";
        String mode = "fromFile"; String filePath = "d:/TEMP/values_list_2.txt";
        //
        // Откроем соединение с хранилищем
        Storage kv = new Storage(storeHost, storePort, storeName);

        //

        if (mode.equals("single")) {
            // Если предпологаем работать с одиночными записями
            //
            {
                System.out.println("Single. Test 1. Add and get single row ");
                //
                String key = "/che/-/single/test1";
                String value = "test1";
                // Добавляем строку в хранилище
                kv.addRow(key, value.getBytes());
                //
                // Получаем по ключу значение
                System.out.println(key + " : " + new String( kv.getRowValue(key)));
                System.out.println("Single. Test 1 - Done.");
            }

            {
                System.out.println("Single. Test 2. Add and get single row using class StorageRow");
                //
                String key = "/che/-/single/test2";
                String value = "test2";
                // Создаем строку для хранилища
                StorageRow newRow = new StorageRow(key, value.getBytes());
                // Добавляем строку в хранилище
                kv.addRow(newRow);
                //
                // Получаем по ключу значение
                StorageRow rowFromStorage = kv.getRow(key);
                System.out.println(rowFromStorage.getKey() + " : " + new String( rowFromStorage.getValue()));
                System.out.println("Single. Test 2 - Done.");
            }

            {
                System.out.println("Single. Test 3. Add single row, delete single row by key and check that deleted row does not exists.");
                //
                String key = "/che/-/single/test3";
                String value = "test3";
                // Создаем строку для хранилища
                StorageRow newRow = new StorageRow(key, value.getBytes());
                // Добавляем строку в хранилище
                kv.addRow(newRow);
                //
                // Удаляем строку по ключу
                kv.deleteRow(key);
                //
                // Пытаемся получить строку
                try {
                    StorageRow rowFromStorage = kv.getRow(key);
                    System.out.println(rowFromStorage.getKey() + " : " + new String( rowFromStorage.getValue()));
                } catch (NullPointerException e){
                    System.out.println("Строка не найдена");
                }

                System.out.println("Single. Test 3 - Done.");
            }

            {
                System.out.println("Single. Test 4. Add single row, delete single row by Row object and check that deleted row does not exists.");
                //
                String key = "/che/-/single/test4";
                String value = "test4";
                // Создаем строку для хранилища
                StorageRow newRow = new StorageRow(key, value.getBytes());
                // Добавляем строку в хранилище
                kv.addRow(newRow);
                //
                // Удаляем строку при помощи объекта
                kv.deleteRow(newRow);
                //
                // Пытаемся получить строку
                try {
                    StorageRow rowFromStorage = kv.getRow(key);
                    System.out.println(rowFromStorage.getKey() + " : " + new String( rowFromStorage.getValue()));
                } catch (NullPointerException e){
                    System.out.println("Строка не найдена");
                }
                System.out.println("Single. Test 4 - Done.");
            }
        }
        else if (mode.equals("collection")){
            // Работа с коллекциями объектов
            {
                System.out.println("Collection. Test 1. Add and get row list ");
                //
                // Создаем набор из ключей и значений
                String key1 = "/che/-/collection/test1/first";
                String value1 = "test1/first";
                //
                String key2 = "/che/-/collection/test1/second";
                String value2 = "test1/second";
                //
                String key3 = "/che/-/collection/test1/third";
                String value3 = "test1/third";
                //
                // Преобразуем их в коллекцию строк
                ArrayList<StorageRow> newStorageRowList = new ArrayList<StorageRow>();
                newStorageRowList.add(new StorageRow(key1, value1.getBytes()));
                newStorageRowList.add(new StorageRow(key2, value2.getBytes()));
                newStorageRowList.add(new StorageRow(key3, value3.getBytes()));
                //
                // Добавляем коллецкию строк в хранилище
                kv.addRowList(newStorageRowList);
                //
                // Получаем по части ключа значения (major - full, minor - part)
                ArrayList<StorageRow> fromStorageRowList = kv.getRowList("/che/-/collection/test1",true);
                for ( StorageRow x : fromStorageRowList){
                    System.out.println(x.getKey() + " : " + new String( x.getValue() ));
                }
                System.out.println("Collection. Test 1 - Done.");
            }

            {
                System.out.println("Collection. Test 2. Add row list, delete row list by part of key and check that deleted collection does not exists. ");
                //
                // Создаем набор из ключей и значений
                String key1 = "/che/-/collection/test2/first";
                String value1 = "test2/first";
                //
                String key2 = "/che/-/collection/test2/second";
                String value2 = "test2/second";
                //
                String key3 = "/che/-/collection/test2/third";
                String value3 = "test2/third";
                //
                // Преобразуем их в коллекцию строк
                ArrayList<StorageRow> newStorageRowList = new ArrayList<StorageRow>();
                newStorageRowList.add(new StorageRow(key1, value1.getBytes()));
                newStorageRowList.add(new StorageRow(key2, value2.getBytes()));
                newStorageRowList.add(new StorageRow(key3, value3.getBytes()));
                //
                // Добавляем коллецкию строк в хранилище
                kv.addRowList(newStorageRowList);
                //
                // Удаляем коллецкию строк из хранилища
                kv.deleteRow("/che/-/collection/test2");
                //
                // Получаем по части ключа значения (major - full, minor - part)
                ArrayList<StorageRow> fromStorageRowList = kv.getRowList("/che/-/collection/test2",true);
                for ( StorageRow x : fromStorageRowList){
                    System.out.println(x.getKey() + " : " + new String( x.getValue() ));
                }
                System.out.println("Collection. Test 2 - Done.");
            }

            {
                System.out.println("Collection. Test 3. Add row list, delete row list by row list and check that deleted collection does not exists. ");
                //
                // Создаем набор из ключей и значений
                String key1 = "/che/-/collection/test3/first";
                String value1 = "test3/first";
                //
                String key2 = "/che/-/collection/test3/second";
                String value2 = "test3/second";
                //
                String key3 = "/che/-/collection/test3/third";
                String value3 = "test3/third";
                //
                // Преобразуем их в коллекцию строк
                ArrayList<StorageRow> newStorageRowList = new ArrayList<StorageRow>();
                newStorageRowList.add(new StorageRow(key1, value1.getBytes()));
                newStorageRowList.add(new StorageRow(key2, value2.getBytes()));
                newStorageRowList.add(new StorageRow(key3, value3.getBytes()));
                //
                // Добавляем коллецкию строк в хранилище
                kv.addRowList(newStorageRowList);
                //
                // Удаляем коллецкию строк из хранилища
                kv.deleteRowList(newStorageRowList);
                //
                // Получаем по части ключа значения (major - full, minor - part)
                ArrayList<StorageRow> fromStorageRowList = kv.getRowList("/che/-/collection/test3",true);
                for ( StorageRow x : fromStorageRowList){
                    System.out.println(x.getKey() + " : " + new String( x.getValue() ));
                }
                System.out.println("Collection. Test 3 - Done.");
            }
        }
        else if (mode.equals("fromFile")){
            // Работа с коллекциями объектов загружаемыми из файла
            {
                long sTime = new Date().getTime();
                System.out.println("File. Test 1. Add and get row list ");

                // Загрузим строки из файла в коллекцию
                System.out.println("Start Load ");
                ArrayList<CmsAttrValueRow> cmsAttrValueRowList = loadFromFile(filePath);
                ArrayList<StorageRow> storageRowList = new ArrayList<StorageRow>();
                //
                // Каждый элемент массива сериализуем и наполним коллекцию строк
                for (CmsAttrValueRow x : cmsAttrValueRowList) {
                    storageRowList.add(new StorageRow("/che/cms_attr_value/"+x.get_entity_id()+"/"+x.get_etyp_etyp_id()+"/-/"+ x.get_adat_adat_id()+"/"+ x.get_atvl_id(), Serializer.serialize(x)));
                }
                //
                // Вставим коллекцию строк в Хранилище
                kv.addRowList(storageRowList);
                long fTime = new Date().getTime();
                System.out.println("File. Test 1. Load. Done in " + ((fTime-sTime)/1000%60) + " sec");
                //
                // Теперь сделаем случайную выборку строк
                // Сгенерим 10 случайных ключей и для каждого из них достанем данные
                String key;
                ArrayList<StorageRow> newRowList;
                sTime = new Date().getTime();

                for (int i = 0 ; i<300;i++){
                    // Сгенерим ключ
                    key = "/che/cms_attr_value/" + (2000000 + (int)(Math.random()*2500000)) + "/12";
                    // Достанем сроки
                    newRowList = kv.getRowList(key,true);
                    if (!newRowList.isEmpty()){
                        System.out.println("find " + key);
                    }
                    // Получим информацию из строк
                    for (StorageRow x : newRowList){
                        System.out.println(" [" + x.getKey() + "] " + (CmsAttrValueRow) Serializer.unserialize(x.getValue()));
                    }
                }
                fTime = new Date().getTime();
                System.out.println("File. Test 1. Search. Done in " + ((fTime-sTime)/1000%60) + " sec");
                System.out.println("Collection. Test 1 - Done.");
            }

        }

        //Закроем соединение с хранилищем
        kv.close();

    }
}
