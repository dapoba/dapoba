#pragma once
#include<iostream>
#include<string>

using namespace std;

class Order;
class Printer;
class Milege_Coin;
class Cabinet;
class Account;

class File
{
public:
	string filename;	//파일의 이름
	string file_list[100];	//파일 이름의 리스트
	void delete_file();	//파일 삭제 함수
	string * select_file();	//파일 선택 함수 
							//	Option option;	//옵션 객체

private:
	string file_extension;	//파일의 확장자
	void store_file();	//파일 저장 함수 
};

class Option
{
public:
	//	File optioned_file;
	int print_place;
	//	File select_file_option(File file);
	bool print_bill(string bill);
private:
	int page_division;
	bool color;
	int select_print_place(File file);
};
class Account
{
protected:
	string Name;
	string Birth;
	string E_mail;
	string Address[40];
	int Phone_number;
	string Password;
public:
	string ID;
	string temporal_password;
	void Login();
	string issue_temporal_password();
	bool sign_in();
	string get_user_id();
	int get_user_password();
	Account get_customer_account();
	string find_id();
	bool check_login_info(string id, int password);
	int find_password();
private:
	void check_id_duplication(string id);
};
class Milege_Coin
{
public:
	int Milege;
	int Coin;
	bool use_milege(int milege);
	void use_coin(int coin);
	int charge_coin();
	bool confirm_payment();
private:
	void save_milege();
};
class Customer : public Account
{
public:
	void logout();
	void print_mypage();
private:
	File own_file;
	Milege_Coin own_milege_coin;
	void modify_info();
};

class Cabinet
{
public:
	int cabinet_number;
	int cabinet_state[100];
	int input_verification_number();
private:
	int assign_cabinet(int veri_num);
};

class Manager_Interface
{
public:
	Cabinet cabinet;
	void manage_printer();
	void manage_cabinet();
private:
	void show_cabinet_num(int num);
};
class Sub_Manager : public Account
{
public:
	bool manage_printer(Printer printer);
	void confim_cabinet(Cabinet cabinet);
};

class System_Manager : public Account
{
private:
	bool delete_customer_info(string id);
	bool modify_customer_info(string id, Account account);
	bool add_customer_info(string id, Account account);
};

class Printer
{
public:
	File Result_file;
	int verification_number;
	File * get_order_file;
	int * get_printer_states(Option option);
	bool check_availability(Printer printer);
	bool print_bill(string bill);
private:
	int Waiting_queue[100];
	bool print_file(Order order);
};

class Order
{
public:
	int Verification_number;
	File order_detail;
	string bill;
	void issue_bill(Milege_Coin milege_coin);
	void Issue_verification_number();
	void sent_result_file_to_printer(File file);
	void store_order_list();
	void reservation_file();
	int rand();
	string * get_ordered_list();
};


class Customer_Interface
{
public:
	void select_menu();
	void print_menu();
	void comfirm_order_detail(File file);
	void check_payment(Milege_Coin milege_coin);
	void print_modified_inf(Account account);
private:
	int select;
	void show_file_list(string file_list);
	void print_ordered_list(string file_list);
};

class Verification_number
{
public:
	int verification_num;
	bool check_duplication(int num);
};


class User_Interface
{
private:
	int select;
	void print_login_fail(bool signal);
	void print_success_info(Account account);
	void print_temporal_password(int password);
	void print_user_id(string id);
public:
	void print_main_menu();
	void confirm_login();
	void get_user_info(Account account);
};

class Cash
{
public:
	string Account_number;
	string bank_name;
	string account_holder_name;
	bool get_account(string id);
};

class Credit_Card
{
public:
	bool complete_signal;
	void authorized_card(Credit_Card credit_card, int number);
private:
	int Credit_card_number;
	string type;
	string expdate;
	int Credit_card_password;
	bool check_balance(int num);
	bool confirm_info(Credit_Card credit_card);
};
