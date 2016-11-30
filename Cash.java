
public class Cash {

	public String account_number;
	public String bank_name;
	public String account_holder_name;
	public void get_account(String bankName)
	{
		Cash c;
		database.database_use();
		//bankName과 같은 은행이름을 갖는 tuple찾기
		//Account_number, bank_name, account_holder_name넣기
		c = database.database_cash(bankName);
		account_number = c.account_number;
		bank_name = c.bank_name;
		account_holder_name = c.account_holder_name;
	}
}
