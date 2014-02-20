CREATE OR REPLACE package body CRM_32_CHE.che_test_pkg
as
    function f_test return number
    as
    LANGUAGE JAVA
        NAME 'che_test.test() return integer';


    function f_return_string return varchar2
    as
    LANGUAGE JAVA
        NAME 'che_test.return_string() return String';

    function f_return_date return date
    as
    LANGUAGE JAVA
        NAME 'che_test.return_date() return Timestamp';

    function f_return_number return number
    as
    LANGUAGE JAVA
        NAME 'che_test.return_num() return BigDecimal';

    procedure pass(p_in in number, p_out out number)
    as
    language java
        name 'che_test.pass_number (java.math.BigDecimal, java.math.BigDecimal[])';

    procedure pass(p_in in varchar2, p_out out varchar2)
    as
    language java
       name 'che_test.pass_string(java.lang.String, java.lang.String[])';

    procedure pass(p_in in date, p_out out date)
    as
    language java
       name 'che_test.pass_date (java.sql.Timestamp, java.sql.Timestamp[])';

    procedure array_test( p_in_array in number_table,  p_out_array out number_table )
    as
    language java
       name 'che_test.pass_num_array (oracle.sql.ARRAY, oracle.sql.ARRAY[])';


   procedure struct_test( p_in_struct in T_ATTR_VALUE ) 
   as
    language java
       name 'che_test.pass_struct (oracle.sql.STRUCT)';

    procedure p2 
    as
    begin
        dbms_output.put_line(' OK :) ');
    end; 

end;
/