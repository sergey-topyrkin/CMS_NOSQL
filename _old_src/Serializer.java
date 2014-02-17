import java.io.*;

/**
 * Created by Anatoly.Cherkasov on 06.02.14.
 * Класс - сериализатор / десериализатор объектов
 */
public class Serializer {
    /**
     * Сериализация объекта
     * @param obj Объект для сериализации
     * @return Массив байтов
     */
    public static byte serialize(Object obj)[]
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(obj);

            return bos.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Десериализация объекта
     * @param serialized Набор байтов для десериализации
     * @return Объект восстановленный из байтов
     * @throws IOException
     */
    public static Object unserialize(byte[] serialized)
    {
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
