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
			//DB에서 마일리지 변경
			database.database_milege(Account.ID, Milege);
			return true;
		}
		else{
			System.out.println("마일리지가 부족합니다.\n");
			System.out.println("사용 가능한 마일리지 : " + Milege +"p");
			System.out.println("사용할 마일리지를 다시 입력해주세요 : ");
			return false;
		}
	}
	public void use_coin(int page)
	{
		int coin = page * 10;
		if(coin == 0){
			System.out.println("결제가 완료되었습니다.\n");
		}
		if(Coin >= coin){
			Coin -= coin;
			database.database_use();
			//DB에서 코인 차감
			database.database_coin(Account.ID, Coin);
			System.out.println("결제가 완료되었습니다.\n");
			save_milege(coin);
		}
		else{
			System.out.println("코인이 부족합니다.\n");
			System.out.println("코인을 충전해주세요\n");
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
	//나
	private void save_milege(int coin)
	{
		int milege = coin/5;
		Milege += milege;
		database.database_use();
		//DB에서 마일리지 변경
		database.database_milege(Account.ID, Milege);
		System.out.println(milege+"p 적립되어 총 "+Coin+"코인, "+Milege+"p가 있습니다.\n");
	}
}
