
public class Cash {

	public String account_number;
	public String bank_name;
	public String account_holder_name;
	public boolean get_account(String bankName)
	{
		Cash c;
		database.database_use();
		//bankName과 같은 은행이름을 갖는 tuple찾기
		//Account_number, bank_name, account_holder_name넣기
		c = database.database_cash(bankName);
		
		System.out.println("입금 계좌 : "+c.account_number+"("+c.bank_name+")"+", 예금주 : "+c.account_holder_name);
		return true;
	}
}
