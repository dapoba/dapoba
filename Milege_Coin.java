public class Milege_Coin {

	public int Milege;
	public int Coin;
	public Milege_Coin(String id){
		database.database_use();
		int[] milege_coin = database.database_get_milege_coin(id);
		Milege = milege_coin[1];
		Coin = milege_coin[0];
	}
	public boolean use_milege(String id, int page)
	{
		int milege = page*10;
		if(Milege >= milege){
			Milege -= milege;
			database.database_use();
			//DB���� ���ϸ��� ����
			database.database_milege_coin(id, "Milege", Milege);
			return true;
		}
		else{
			System.out.println("���ϸ����� �����մϴ�.\n");
			System.out.println("��� ������ ���ϸ��� : " + Milege +"p");
			System.out.println("����� ���ϸ����� �ٽ� �Է����ּ��� : ");
			return false;
		}
	}
	public void use_coin(String id, int page)
	{
		int coin = page * 10;
		if(coin == 0){
			System.out.println("������ �Ϸ�Ǿ����ϴ�.\n");
		}
		if(Coin >= coin){
			Coin -= coin;
			database.database_use();
			//DB���� ���� ����
			database.database_milege_coin(id, "Coin", Coin);
			System.out.println("������ �Ϸ�Ǿ����ϴ�.\n");
			save_milege(id, coin);
		}
		else{
			System.out.println("������ �����մϴ�.\n");
			System.out.println("������ �������ּ���\n");
			charge_coin();
		}
	}
	public int charge_coin()
	{
	  return 1;	
	}
	public boolean confirm_payment()
	{
		return true;
	}
	//��
	private void save_milege(String id, int coin)
	{
		int milege = coin/5;
		Milege += milege;
		database.database_use();
		//DB���� ���ϸ��� ����
		database.database_milege_coin(id, "Milege", Milege);
		System.out.println(milege+"p �����Ǿ� �� "+Coin+"����, "+Milege+"p�� �ֽ��ϴ�.\n");
	}
}
