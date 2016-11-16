import java.sql.DriverManager;
import java.sql.SQLException;

public class Milege_Coin {

	public int Milege;
	public int Coin;
	public Milege_Coin(){
		database db = new database();
		db.database_use();
		try{
			Class.forName(db.driver);
			db.conn = DriverManager.getConnection(db.url, db.userId, db.passwd);// Connection 
			String sql = "Use dapoba_database";
			db.stmt = db.conn.createStatement();
			db.stmt.execute(sql);
			sql = "select * from Milege/Coin where ID = " + Account.ID;
			db.rs = db.stmt.executeQuery(sql);
			Milege = db.rs.getInt("Mileage");
			Coin = db.rs.getInt("Coin");
			db.stmt.close(); 
			db.conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	public boolean use_milege(int page)
	{
		int milege = page*10;
		if(Milege >= milege){
			Milege -= milege;
			database.database_use();
			//DB���� ���ϸ��� ����
			database.database_milege(Account.ID, Milege);
			return true;
		}
		else{
			System.out.println("���ϸ����� �����մϴ�.\n");
			System.out.println("��� ������ ���ϸ��� : " + Milege +"p");
			System.out.println("����� ���ϸ����� �ٽ� �Է����ּ��� : ");
			return false;
		}
	}
	public void use_coin(int page)
	{
		int coin = page * 10;
		if(coin == 0){
			System.out.println("������ �Ϸ�Ǿ����ϴ�.\n");
		}
		if(Coin >= coin){
			Coin -= coin;
			database.database_use();
			//DB���� ���� ����
			database.database_coin(Account.ID, Coin);
			System.out.println("������ �Ϸ�Ǿ����ϴ�.\n");
			save_milege(coin);
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
	private void save_milege(int coin)
	{
		int milege = coin/5;
		Milege += milege;
		database.database_use();
		//DB���� ���ϸ��� ����
		database.database_milege(Account.ID, Milege);
		System.out.println(milege+"p �����Ǿ� �� "+Coin+"����, "+Milege+"p�� �ֽ��ϴ�.\n");
	}
}
