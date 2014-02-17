import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anatoly.Cherkasov on 06.02.14.
 * Класс повторяет структуру строки из таблицы CMS_ATTR_VALUE
 */
public class CmsAttrValueRow implements Serializable {

    /*Поля из таблицы с сохранением имен полей для лучшей идентификации*/
    private int atvl_id;
    private int atvl_atvl_id;
    private int adat_adat_id;
    private int etyp_etyp_id;
    private int entity_id;
    private String value_as_text;
    private String string_value;
    private int number_value;
    private int oper_oper_id;
    private int inhs_inhs_id;
    private Date start_date_utc;
    private Date end_date_utc;
    private Date date_value_utc;
    private Date migration_date;

    /**
     * Конструктор для класса с наполнением всех полей. Типы параметров совпадают с типами полей
     * @param atvl_id
     * @param atvl_atvl_id
     * @param adat_adat_id
     * @param etyp_etyp_id
     * @param entity_id
     * @param value_as_text
     * @param string_value
     * @param number_value
     * @param oper_oper_id
     * @param inhs_inhs_id
     * @param start_date_utc
     * @param end_date_utc
     * @param date_value_utc
     * @param migration_date
     */
    public CmsAttrValueRow(int atvl_id, int atvl_atvl_id, int adat_adat_id, int etyp_etyp_id, int entity_id, String value_as_text,
                            String string_value, int number_value, int oper_oper_id, int inhs_inhs_id, Date start_date_utc, Date end_date_utc,
                            Date date_value_utc, Date migration_date) {
        this.atvl_id = atvl_id;
        this.atvl_atvl_id = atvl_atvl_id;
        this.adat_adat_id = adat_adat_id;
        this.etyp_etyp_id = etyp_etyp_id;
        this.entity_id = entity_id;
        this.value_as_text = value_as_text;
        this.string_value = string_value;
        this.number_value = number_value;
        this.oper_oper_id = oper_oper_id;
        this.inhs_inhs_id = inhs_inhs_id;
        this.start_date_utc = start_date_utc;
        this.end_date_utc = end_date_utc;
        this.date_value_utc = date_value_utc;
        this.migration_date = migration_date;
    }

    /**
     * Конструктор для класса с наполнением всех полей. Все типы параметров - строки, для загрузки из файла
     * @param atvl_id
     * @param atvl_atvl_id
     * @param adat_adat_id
     * @param etyp_etyp_id
     * @param entity_id
     * @param value_as_text
     * @param string_value
     * @param number_value
     * @param oper_oper_id
     * @param inhs_inhs_id
     * @param start_date_utc
     * @param end_date_utc
     * @param date_value_utc
     * @param migration_date
     */
    public CmsAttrValueRow(String atvl_id, String atvl_atvl_id, String adat_adat_id, String etyp_etyp_id, String entity_id, String value_as_text,
                           String string_value, String number_value, String oper_oper_id, String inhs_inhs_id, String start_date_utc, String end_date_utc,
                           String date_value_utc, String migration_date) throws ParseException {
        if (!atvl_id.equals("null"))
            this.atvl_id = Integer.parseInt(atvl_id);
        if (!atvl_atvl_id.equals("null"))
            this.atvl_atvl_id = Integer.parseInt(atvl_atvl_id);
        if (!adat_adat_id.equals("null"))
            this.adat_adat_id = Integer.parseInt(adat_adat_id);
        if (!etyp_etyp_id.equals("null"))
            this.etyp_etyp_id = Integer.parseInt(etyp_etyp_id);
        if (!entity_id.equals("null"))
            this.entity_id = Integer.parseInt(entity_id);

        this.value_as_text = value_as_text;
        this.string_value = string_value;

        if (!(number_value.equals("null") || number_value.equals("")))
            this.number_value = Integer.parseInt(number_value);
        if (!oper_oper_id.equals("null"))
            this.oper_oper_id = Integer.parseInt(oper_oper_id);
        if (!inhs_inhs_id.equals("null"))
            this.inhs_inhs_id = Integer.parseInt(inhs_inhs_id);
        if (!start_date_utc.equals("null")){
            if (start_date_utc.length() == 10)
                this.start_date_utc = new SimpleDateFormat("d.M.y").parse(start_date_utc);
            else
                this.start_date_utc = new SimpleDateFormat("d.M.y H:m:s").parse(start_date_utc);
        }
        if (!end_date_utc.equals("null")){
            if (end_date_utc.length() == 10)
                this.migration_date = new SimpleDateFormat("d.M.y").parse(end_date_utc);
            else
                this.end_date_utc = new SimpleDateFormat("d.M.y H:m:s").parse(end_date_utc);
        }
        if (!date_value_utc.equals("null")){
            if (date_value_utc.length() == 10)
                this.migration_date = new SimpleDateFormat("d.M.y").parse(date_value_utc);
            else
                this.date_value_utc = new SimpleDateFormat("d.M.y H:m:s").parse(date_value_utc);
        }
        if (!migration_date.equals("null")){
            if (migration_date.length() == 10)
                this.migration_date = new SimpleDateFormat("d.M.y").parse(migration_date);
            else
                this.migration_date = new SimpleDateFormat("d.M.y H:m:s").parse(migration_date);
        }
    }

    @Override
    public String toString() {
        return "Значения полей строки: \n"+
                "   atvl_id " + atvl_id + "\n" +
                "   atvl_atvl_id " + atvl_atvl_id + "\n" +
                "   adat_adat_id " + adat_adat_id + "\n" +
                "   etyp_etyp_id " + etyp_etyp_id + "\n" +
                "   entity_id " + entity_id + "\n" +
                "   value_as_text " + value_as_text + "\n" +
                "   string_value " + string_value + "\n" +
                "   number_value " + number_value + "\n" +
                "   oper_oper_id " + oper_oper_id + "\n" +
                "   inhs_inhs_id " + inhs_inhs_id + "\n" +
                "   start_date_utc " + start_date_utc + "\n" +
                "   end_date_utc " + end_date_utc + "\n" +
                "   date_value_utc " + date_value_utc + "\n" +
                "   migration_date " + migration_date;
    }

    /* Ниже идут геттеры и сеттеры для приватных полей
           От правил именования пришлось немного отступить */

    public int get_atvl_id() {
        return atvl_id;
    }
    public void set_atvl_id(int atvl_id) {
        this.atvl_id = atvl_id;
    }
    //
    //
    public int get_atvl_atvl_id() {
        return atvl_atvl_id;
    }
    public void set_atvl_atvl_id(int atvl_atvl_id) {
        this.atvl_atvl_id = atvl_atvl_id;
    }
    //
    //
    public int get_adat_adat_id() {
        return adat_adat_id;
    }
    public void set_adat_adat_id(int adat_adat_id) {
        this.adat_adat_id = adat_adat_id;
    }
    //
    //
    public int get_etyp_etyp_id() {
        return etyp_etyp_id;
    }
    public void set_etyp_etyp_id(int etyp_etyp_id) {
        this.etyp_etyp_id = etyp_etyp_id;
    }
    //
    //
    public int get_entity_id() {
        return entity_id;
    }
    public void set_entity_id(int entity_id) {
        this.entity_id = entity_id;
    }
    //
    //
    public String get_value_as_text() {
        return value_as_text;
    }
    public void set_value_as_text(String value_as_text) {
        this.value_as_text = value_as_text;
    }
    //
    //
    public String get_string_value() {
        return string_value;
    }
    public void set_string_value(String string_value) {
        this.string_value = string_value;
    }
    //
    //
    public int get_number_value() {
        return number_value;
    }
    public void set_number_value(int number_value) {
        this.number_value = number_value;
    }
    //
    //
    public int get_oper_oper_id() {
        return oper_oper_id;
    }
    public void set_oper_oper_id(int oper_oper_id) {
        this.oper_oper_id = oper_oper_id;
    }
    //
    //
    public int get_inhs_inhs_id() {
        return inhs_inhs_id;
    }
    public void set_inhs_inhs_id(int inhs_inhs_id) {
        this.inhs_inhs_id = inhs_inhs_id;
    }
    //
    //
    public Date get_start_date_utc() {
        return start_date_utc;
    }
    public void set_start_date_utc(Date start_date_utc) {
        this.start_date_utc = start_date_utc;
    }
    //
    //
    public Date get_end_date_utc() {
        return end_date_utc;
    }
    public void set_end_date_utc(Date end_date_utc) {
        this.end_date_utc = end_date_utc;
    }
    //
    //
    public Date get_date_value_utc() {
        return date_value_utc;
    }
    public void set_date_value_utc(Date date_value_utc) {
        this.date_value_utc = date_value_utc;
    }
    //
    //
    public Date get_migration_date() {
        return migration_date;
    }
    public void set_migration_date(Date migration_date) {
        this.migration_date = migration_date;
    }


}
