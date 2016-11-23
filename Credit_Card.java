
public class Credit_Card {

	public boolean complete_signal;
	private int Credit_card_number;
	private String bank_name;
	private String expdate;
	private int Credit_card_password;
	private int payment;
	
	public Credit_Card(int cardNum, String bankName, String date, int password, int money){
		Credit_card_number = cardNum;
		bank_name = bankName;
		expdate = date;
		Credit_card_password = password;
		payment = money;
	}
	public void authorized_card()
	{
		if(confirm_info()){
			System.out.println("결제가 완료되었습니다.\n");
		}
		else
			//결제수단 화면으로 돌아가기
			System.out.println("등록되지 않은 카드입니다. 카드번호와 비밀번호, 유효기간을 다시 확인해주세요\n");
		
	}
	
	private boolean check_balance()
	{
		database.database_use();
		if(database.database_card_balance(Credit_card_number, payment))
			return true;
		else
			return false;
	}
	private boolean confirm_info()
	{
		database.database_use();
		if(database.database_card_info(Credit_card_number, bank_name, expdate, Credit_card_password)){
			if(check_balance())
				return true;
			else{
				System.out.println("잔액이 부족합니다.\n");
				return false;
			}
		}
		else{
			System.out.println("등록되지 않은 카드입니다. 카드번호와 비밀번호, 유효기간을 다시 확인해주세요.\n");
			return false;
		}		
	}
}
