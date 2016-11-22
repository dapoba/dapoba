import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement; 
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
			} 
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	
		return flag;
	}
	
	
	
	
	
	
	static void database_func()  
	{ 
		
		try
		{ 
			Class.forName(driver); // Driver Loading 
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "CREATE DATABASE my_database"; // SQL ¿€º∫ 
			stmt = conn.createStatement(); 	// Statement 
			stmt.execute(sql); 
			sql = "USE my_database"; 
			stmt.execute(sql); 
			sql = "create table my_table ( id int, first_name varchar(20), email varchar(30) )"; 
			stmt.execute(sql); 
			sql = "insert into my_table (id, first_name, email) values " + "(1, \"woongjin\", \"finalboogi@naver.com\"), " + "(2, \"woongjin2\", \"finalboogi2@naver.com\"), " + "(3, \"woongjin3\", \"finalboogi3@naver.com\")"; 
			stmt.execute(sql); 
			sql = "select * from my_table";
			rs = stmt.executeQuery(sql);// ResultSet 
			while(rs.next()) 
			{ 
				System.out.println("id: " + rs.getInt("id") + "\nfirst_name: " + rs.getString("first_name") + "\nemail: " + rs.getString("email") + "\n"); // Data get 
			} 
			stmt.close(); 
			conn.close(); // close
		} 
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	} 
}
