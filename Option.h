#include"File.h"


class Option
{
public:
	File optioned_file;
	int print_place;
	File select_file_option(File file);
	bool print_bill(string bill);
private:
	int page_division;
	bool color;
	int select_print_place(File file);
};

/*
File Option::select_file_option(File file)
{
	return file;
}
bool Option::print_bill(string bill)
{
	return 1;
}
int Option::select_print_place(File file)
{
	return 1;
}
*/