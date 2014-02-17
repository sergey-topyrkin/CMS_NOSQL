import oracle.kv.Key;

import java.util.ArrayList;

/**
 * Created by Anatoly.Cherkasov on 06.02.14.
 */

public class Start {


    /*
     * Считаем файл и из него получим массив CmsAttrValueRow
     * @param filepath Путь к файлу
     * @return Массив CmsAttrValueRow
     *
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
    */


    public static void main(String[] args) {

        /**
         * Координаты для связи с хранилищем
         */
        String storeHost = "localhost";
        int storePort = 5000;
        String storeName = "kvstore" ;

        //
        // Откроем соединение с хранилищем
        Storage kv_wo_value = new Storage(storeHost, storePort, storeName);

        /*
        // Вот так раскрывается ключ
        //---------------------------------/cms_attr_value/etyp_id/entity_id/adat_id/-/value/XXXX

        // Простые операции с одиночными ключами. Добавить. Получить. Удалить.
        {
            ArrayList<String> keys;
            System.out.println("");
            System.out.println("ALL in key. Test 1. Add single attr.");
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/100/150/-/value/single_txt"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/100/150/-/oper_id/123"));
            //
            // получение ключа по полному ключу (бредовый вариант)
            System.out.println("Try 1.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/150/-/value/single_txt", "");
            // получение ключа по части ключа и некоторому диапазону 1
            System.out.println("Try 2.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/150/-/value", "single_txt");
            // получение ключа по части ключа и некоторому диапазону 2
            System.out.println("Try 3.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/150", "value");
            //
            System.out.println("Try 4.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/150", "o", "w");
            //
            System.out.println("Try 5.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/150", "");
        }
        //
        //
        {
            ArrayList<String> keys;
            System.out.println("");
            System.out.println("ALL in key. Test 2. Add multy attr");
            // Множественное значение у атрибута
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/143/-/value/400"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/143/-/value/500"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/145/-/value/2012:12:31:14:59:59"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/143/-/oper_id/123"));
            // Получим теперь множественное значение
            System.out.println("Try 1.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/123/143", "value");
            //
            System.out.println("Try 2.");
            keys = kv_wo_value.getRowValuesByRange("/che/key_wo_value/cms_attr_value/12/123/143", "value");
            keys = kv_wo_value.getRowValuesByRange("/che/key_wo_value/cms_attr_value/12/123/143", "oper_id");
        }
        //
        //
        {
            ArrayList<String> keys;
            System.out.println("");
            System.out.println("ALL in key. Test 3. Add multy attr. Del multy attr.");
            // Множественное значение у атрибута
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/145/-/value/200"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/145/-/value/300"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/147/-/value/2012:12:31:14:59:59"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/145/-/oper_id/123"));
            // Получим теперь множественное значение
            System.out.println("Try 1.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/130/145", "");
            //
            // Удалим значения
            kv_wo_value.deleteRowsByRange("/che/key_wo_value/cms_attr_value/12/130/145", "value");
            //
            // Посмотрим что осталось
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/130/145", "");
        }
        */

        // Вот другой вариант хранения ключа.
        // Такой вариант предложен в связи с тем что функция multiGetKeys хочет на вход получить полный major key. А в случае списка допов мя не сможем дать полный major ключ
        //---------------------------------/cms_attr_value/etyp_id/entity_id/-/adat_id/value/XXXX
        //---------------------------------/cms_attr_value/etyp_id/entity_id/-/adat_id/oper_id/YYYY

        // Простые операции с одиночными ключами. Добавить. Получить. Удалить.
        {
            ArrayList<String> keys;
            System.out.println("");
            System.out.println("ALL in key. Test 1. Add single attr.");
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/100/-/150/value/single_txt"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/100/-/150/oper_id/123"));
            //
            // получение ключа по полному ключу (бредовый вариант)
            System.out.println("Try 1.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/-/150/value/single_txt", "");
            // получение ключа по части ключа и некоторому диапазону 1 (тоже бредовый вариант)
            System.out.println("Try 2.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/-/150/value", "single_txt");
            // получение ключа по части ключа и некоторому диапазону 2
            System.out.println("Try 3.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/-/150", "value");
            //
            System.out.println("Try 4.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/-/150", "o", "w");
            //
            System.out.println("Try 5.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100/-/150", "");
            //
            System.out.println("Try 6.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100", "150");
            //
            System.out.println("Try 7.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/100", "150");
        }
        //

        //
        {
            ArrayList<String> keys;
            System.out.println("");
            System.out.println("ALL in key. Test 2. Add multy attr");
            // Множественное значение у атрибута
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/-/143/value/400"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/-/143/value/500"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/-/145/value/2012:12:31:14:59:59"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/123/-/143/oper_id/123"));
            // Получим теперь множественное значение
            System.out.println("Try 1.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/123/-/143", "value");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/123/-/145", "value");
            //
            System.out.println("Try 2.");
            keys = kv_wo_value.getRowValuesByRange("/che/key_wo_value/cms_attr_value/12/123/-/143", "value");
            keys = kv_wo_value.getRowValuesByRange("/che/key_wo_value/cms_attr_value/12/123/-/143", "oper_id");
            //
            System.out.println("Try 3.");
            keys = kv_wo_value.getRowValuesByRange("/che/key_wo_value/cms_attr_value/12/123", "value");
        }
        //
        //
        {
            ArrayList<String> keys;
            System.out.println("");
            System.out.println("ALL in key. Test 3. Add multy attr. Del multy attr.");
            // Множественное значение у атрибута
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/-/145/value/200"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/-/145/value/300"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/-/147/value/2012:12:31:14:59:59"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/-/145/oper_id/123"));
            kv_wo_value.addRow(Key.fromString("/che/key_wo_value/cms_attr_value/12/130/-/147/oper_id/123"));
            // Получим теперь множественное значение
            System.out.println("Try 1.");
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/130", "");
            keys = kv_wo_value.getRowValuesByRange("/che/key_wo_value/cms_attr_value/12/130", "value");
            //
            // Удалим значения
            kv_wo_value.deleteRowsByRange("/che/key_wo_value/cms_attr_value/12/130/-/145", "value");
            //
            // Посмотрим что осталось
            keys = kv_wo_value.getRowsByRange("/che/key_wo_value/cms_attr_value/12/130", "");
            keys = kv_wo_value.getRowValuesByRange("/che/key_wo_value/cms_attr_value/12/130", "value");
        }


        kv_wo_value.close();


    }
}
