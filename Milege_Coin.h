
#include<iostream>
class Milege_Coin
{
public :
	int Milege;
	int Coin;
	bool use_milege(int milege);
	void use_coin(int coin);
	int charge_coin();
	bool confirm_payment();
private: 
	void save_milege();
};
/*
bool Milege_Coin::use_milege(int milege)
{
	return 1;
}
void Milege_Coin::use_coin(int coin)
{

}
int Milege_Coin::charge_coin()
{
	return 1;
}
bool Milege_Coin::confirm_payment()
{
	return 1;
}

void Milege_Coin::save_milege()
{

}
*/