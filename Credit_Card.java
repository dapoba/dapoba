

public class Credit_Card {

	private String Credit_card_number;
	private String bank_name;
	private String expdate;
	private int Credit_card_password;
	private int payment;
	
	public Credit_Card(String cardNum, String bankName, String date, int password, int money){
		Credit_card_number = cardNum;
		bank_name = bankName;
		expdate = date;
		Credit_card_password = password;
		payment = money;
	}
	public int authorized_card()
	{
		return confirm_info();
	}
	
	private boolean check_balance()
	{
		if(database.database_card_balance(Credit_card_number, payment))
			return true;
		else
			return false;
	}
	
	private int confirm_info()
	{
		if(database.database_card_info(Credit_card_number, bank_name, expdate, Credit_card_password)){
			if(check_balance())
				return 1;
			else{
				System.out.println("�ܾ��� �����մϴ�.\n");
				return 2;
			}
		}
		else{
			System.out.println("��ϵ��� ���� ī���Դϴ�. ī���ȣ�� ��й�ȣ, ��ȿ�Ⱓ�� �ٽ� Ȯ�����ּ���.\n");
			return 3;
		}		
	}
}
