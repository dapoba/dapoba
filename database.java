import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date; 
public class database 
{ 
	static String driver = "com.mysql.jdbc.Driver"; 
	static String url = "jdbc:mysql://localhost:3306/?user=root"; 
	static String userId = "root"; 
	static String passwd = "1234"; 
	static Connection conn; 
	static Statement stmt;
	static ResultSet rs; 
	static void database_use()
	{
		try{
			Class.forName(driver); // Driver Loading 
			conn = DriverManager.getConnection(url, userId, passwd);// Connection
		}
		catch(ClassNotFoundException| SQLException e)
		{ e.printStackTrace(); }
	}
	static Cash database_cash(String bankName){
		Cash c = new Cash();
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from Cash where bank_name = " + bankName;
			rs = stmt.executeQuery(sql);
			c.account_holder_name = rs.getString("account_holder_name");
			c.account_number = rs.getString("account_number");
			c.bank_name = rs.getString("bank_name");
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
		return c;
	}
	static void database_milege_coin(String id, String type, int payment){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "update Mileage/Coin set"+type+" = "+String.valueOf(payment)+"where ID = "+id;
			rs = stmt.executeQuery(sql);
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	static int[] database_get_milege_coin(String id){
		try{
			int[] milege_coin = new int[2];
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from Mileage/Coin where id = " + id;
			rs = stmt.executeQuery(sql);
			milege_coin[0] = rs.getInt("Coin");
			milege_coin[1] = rs.getInt("Mileage");
			stmt.close(); 
			conn.close();// close
			return milege_coin;
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return null;}
	}
	static boolean database_check_verification(int num){
		boolean flag=true;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select verification_number from Cabinet";
			rs = stmt.executeQuery(sql);
			while(rs.next()) 
			{ 
				if(num == rs.getInt("verification_number"))
					flag = false; 
				if(num <1000)
					flag = false;
			} 
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	
		return flag;
	}
	static boolean database_card_info(int cardNum, String bankName, String date, int password){
		boolean flag;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from Credit_Card where Credit_card_number = " + String.valueOf(cardNum);
			rs = stmt.executeQuery(sql);
			
			if(rs.next() == false)
				flag = false;
			else{
				if(bankName == rs.getString("Type") && date == rs.getString("ExpDate") && password == rs.getInt("Credit_card_password"))
					flag = true;
				else
					flag = false;
			}
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); flag = false; }
	
		return flag;
	}
	static boolean database_card_balance(int cardNum, int money){
		boolean flag;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select Balance from Credit_Card where Credit_card_number = " + String.valueOf(cardNum);
			rs = stmt.executeQuery(sql);
			rs.next();
			int balance = rs.getInt("Balance");
			if(money <= balance){
				flag = true;
				balance -= money;
				sql = "update Credit_Card set Balance = "+String.valueOf(balance)+"where Credit_card_number = "+String.valueOf(cardNum);
				rs = stmt.executeQuery(sql);
			}
			else
				flag = false;
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); flag = false; }
	
		return flag;
	}
	static void database_history_insert(String id, String type, int transaction, String file){
		try{
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String date = dayTime.format(new Date(time));

			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			if(type.equals("coin"))
				sql = "insert into history values (" + id + ", "+ date + ", " +String.valueOf(transaction) +", "+0+", " +file+")";
			else if(type.equals("milege"))
				sql = "insert into history values (" + id + ", " + date + ", "+0+", " + String.valueOf(transaction) + ", "+file +")";
			rs = stmt.executeQuery(sql);			
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace();}
	}
	static String[][] database_history_print(String id){
		try{
			String[][] history = new String[10][4]; 
			int i = 0;
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use dapoba_database";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select *from history where Account_ID = " + id;
			rs = stmt.executeQuery(sql);
			rs.next();
			while(rs.next()) 
			{ 
				history[i][0] = rs.getString("date");
				history[i][1] = rs.getString("Coin_history");
				history[i][2] = rs.getString("Mileage_history");
				history[i][3] = rs.getString("File_name");
				i++;
			} 
			stmt.close(); 
			conn.close(); // close
			return history;
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return null;}
	}
}
