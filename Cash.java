
public class Cash {

	public String account_number;
	public String bank_name;
	public String account_holder_name;
	public boolean get_account(String bankName)
	{
		Cash c;
		database.database_use();
		//bankName�� ���� �����̸��� ���� tupleã��
		//Account_number, bank_name, account_holder_name�ֱ�
		c = database.database_cash(bankName);
		
		System.out.println("�Ա� ���� : "+c.account_number+"("+c.bank_name+")"+", ������ : "+c.account_holder_name);
		return true;
	}
}
