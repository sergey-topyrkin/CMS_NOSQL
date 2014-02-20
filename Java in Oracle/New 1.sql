/*
select che_test_pkg.f_test  from dual

select che_test_pkg.f_return_string  from dual

select che_test_pkg.f_return_date  from dual

select che_test_pkg.f_return_number  from dual
*/



DECLARE
   p_in    NUMBER := 10;
   p_out   NUMBER;
BEGIN
   DBMS_OUTPUT.put_line (p_in || ' ** ' || p_out);
   che_test_pkg.pass (p_in, p_out);
   DBMS_OUTPUT.put_line (p_in || ' ** ' || p_out);
END;


DECLARE
   p_in    date := sysdate;
   p_out   date;
BEGIN
   DBMS_OUTPUT.put_line (p_in || ' ** ' || p_out);
   che_test_pkg.pass (p_in, p_out);
   DBMS_OUTPUT.put_line (p_in || ' ** ' || p_out);
END;


DECLARE
   p_in    varchar2(100) := 'Vasya Вася';
   p_out  varchar2(100);
BEGIN
   DBMS_OUTPUT.put_line (p_in || ' ** ' || p_out);
   che_test_pkg.pass (p_in, p_out);
   DBMS_OUTPUT.put_line (p_in || ' ** ' || p_out);
END;


DECLARE
   p_in  number_table := number_table();
   --p_out  number_table := number_table();
   p_out  number_table;
BEGIN

    p_in.extend();
    p_in(1) := 10;
    p_in.extend();
    p_in(2) := 20;
    p_in.extend();
    p_in(3) := 30;

   DBMS_OUTPUT.put_line (p_in(1) || '   ' || p_in(2)|| '   ' || p_in(3));
   che_test_pkg.array_test (p_in, p_out);
   
   for x in p_out.first .. p_out.last loop
        DBMS_OUTPUT.put_line (p_out(x));     
   end loop;
END;

DECLARE
   p_in  T_ATTR_VALUE; 
   
   entity_id number := 20;
   etyp_id number := 30;
   adat_id number := 40;
   value varchar2(50) := '10';
   
BEGIN
    
    p_in := T_ATTR_VALUE( entity_id, etyp_id, adat_id, value );

   DBMS_OUTPUT.put_line ( p_in.ENTITY_ID || ' ' || p_in.ETYP_ID || ' ' || p_in.ADAT_ID || ' '  || p_in.VALUE );
   
   che_test_pkg.struct_test (p_in );
END;