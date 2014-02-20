CREATE OR REPLACE package CRM_32_CHE.che_test_pkg
as

    procedure p2;
    
    function f_test return number;
    
    function f_return_string return varchar2;
    
    function f_return_date return date;
    
    function f_return_number return number;
    
    procedure pass(p_in in number, p_out out number);
    procedure pass(p_in in date, p_out out date);
    procedure pass(p_in in varchar2, p_out out varchar2);
    
    procedure array_test( p_in_array in number_table,  p_out_array out number_table );
    
    procedure struct_test( p_in_struct in T_ATTR_VALUE );
    
    
end;
/