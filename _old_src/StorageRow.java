/**
 * Created by Anatoly.Cherkasov on 06.02.14.
 * Класс для удобного хранения пар ключ значение. Позволяет скрыть реализацию key - value из NoSql
 */
public class StorageRow {
    private String key;
    private byte[] value;

    StorageRow(String key, byte[] value){
        this.key = key;
        this.value = value;
    }



    public byte[] getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
