
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement; 
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
	public Cash database_cash(String bankName){
		String driver = "com.mysql.jdbc.Driver"; 
		  String url = "jdbc:mysql://localhost:3306/?user=root";
		  String userId = "root";
		  String passwd = "1234";
		  Connection conn;
		  Statement stmt;
		  ResultSet rs;
		Cash c = new Cash();
		try{
			Class.forName(driver); // Driver Loading
		   conn = DriverManager.getConnection(url, userId, passwd); // Connection
		   		   
		   stmt = conn.createStatement();
			String sql = "USE Dapoba_db";
			
			stmt.execute(sql);
			
			sql = "select * from Cash where bank_name = " + "\'"+bankName+"\'";
			rs = stmt.executeQuery(sql);
			rs.next();
			c.account_holder_name = rs.getString("account_holder_name");
			c.account_number = rs.getString("account_number");
			c.bank_name = rs.getString("bank_name");
			System.out.println(c.account_holder_name);
			System.out.println(c.account_number);
			System.out.println(c.bank_name);
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
		return c;
	}
	public void database_milege_coin(String id, String type, int payment){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			
			String sql = "USE Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "update Mileage_Coin set "+type+" = \'"+String.valueOf(payment)+"\' where ID = "+"\'"+id+"\'";
			stmt.executeUpdate(sql);
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
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from Mileage_Coin where id = " + "\'"+ id+"\'";
			rs = stmt.executeQuery(sql);
			rs.next();
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
			String sql = "Use Dapoba_db";
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
	static boolean database_card_info(String cardNum, String bankName, String date, int password){
		boolean flag = false;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "USE Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from Credit_Card where Credit_card_number = \'" + cardNum + "\'";
			rs = stmt.executeQuery(sql);
			rs.next();
			if(rs.getString("Type") == null)
				flag = false;
			else{
				if(bankName.equals(rs.getString("Type")) && date.equals(rs.getString("ExpDate")) && password == rs.getInt("Credit_card_password"))
					flag = true;
				else
					flag = false;
			}
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) {
			if(bankName.equals("shinhan")){
				if(cardNum.equals("1234432156788765") && date.equals("0220") && password==0000)
					flag = true;
			}
			else if(bankName.equals("kukmin")){
				if(cardNum.equals("2345543267899876") && date.equals("1220") && password==1234)
					flag = true;
			}
			else if(bankName.equals("woori")){
				if(cardNum.equals("3456654378900987") && date.equals("0418") && password == 1004)
					flag = true;
			}
			else flag = false;
		}
	
		return flag;
	}
	static boolean database_card_balance(String cardNum, int money){
		boolean flag;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "USE Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select Balance from Credit_Card where Credit_card_number = \'" + cardNum + "\'";
			rs = stmt.executeQuery(sql);
			rs.next();
			int balance = rs.getInt("Balance");
			if(money <= balance){
				flag = true;
				balance -= money;
				sql = "update Credit_Card set Balance = \'"+String.valueOf(balance)+"\' where Credit_card_number = \'"+String.valueOf(cardNum) + "\'";
				stmt.executeUpdate(sql);
			}
			else
				flag = false;
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { 
			if(cardNum.equals("1234432156788765") && money <= 300000)
				flag = true;
			else if (cardNum.equals("2345543267899876") && money <= 1000)
				flag = true;
			else if(cardNum.equals("3456654378900987") && money <= 0)
				flag = true;
			else  flag = false; 
		}
	
		return flag;
	}
	public void database_history_insert(String id, String type, int transaction, String file){
		try{
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd kk:mm:ss");
			String date = dayTime.format(new Date(time));

			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			if(type.equals("coin")){
				if (transaction >0)
					sql = "insert into history values (\'" + id + "\', \'"+ date + "\', \'+" +String.valueOf(transaction) +"\', \'"+0+"\', \'" +file+"\')";
				else
					sql = "insert into history values (\'" + id + "\', \'"+ date + "\', \'" +String.valueOf(transaction) +"\', \'"+0+"\', \'" +file+"\')";
			}
			else if(type.equals("milege")){
				if(transaction > 0)
					sql = "insert into history values (\'" + id + "\', \'" + date + "\', \'"+0+"\', \'+" + String.valueOf(transaction) + "\', \'"+file +"\')";
				else
					sql = "insert into history values (\'" + id + "\', \'" + date + "\', \'"+0+"\', \'" + String.valueOf(transaction) + "\', \'"+file +"\')";
			}
			stmt.execute(sql);			
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
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select *from history where Account_ID = " + "\'"+ id +"\'";
			rs = stmt.executeQuery(sql);
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
	static String[][] database_payment_list_print(){
		try{
			String[][] payList = new String[10][4]; 
			int i = 0;
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select *from cashList";
			rs = stmt.executeQuery(sql);
			while(rs.next()) 
			{ 
				payList[i][0] = rs.getString("ID");
				payList[i][1] = rs.getString("Name");
				payList[i][2] = rs.getString("deposit");
				payList[i][3] = rs.getString("money");
				i++;
			} 
			stmt.close(); 
			conn.close(); // close
			return payList;
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return null;}
	}
	static void database_payment_confirm(String id){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			//입금 확인이라면 confirm = 0
			sql = "update cashList set deposit =0 Where ID = "+"\'"+id+"\'";
			stmt.executeUpdate(sql);			
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	static void database_payment_insert(String id, String name, String money){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			//입금 미확인이라면 confirm = 1
			sql = "insert into cashList values ('" + id + "', '"+ name + "', " + "'1'" +", '"+money+"')";
			stmt.execute(sql);			
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	
	static int database_page_total(){
		try{
			int pageNum = 0;
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from File where Account_ID = 'execute'";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				pageNum += rs.getInt("File_page_no");
			}
			stmt.close(); 
			conn.close(); // close
			return pageNum;
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return 0;}
	}
	static void database_bill_insert(String Filename, String exetension, String page, String id){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "insert into File values ('" + Filename + "', '"+ exetension + "', " + "'1'" +", '"+id+"')";
			stmt.execute(sql);			
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	String database_get_fileList(){
		int count = 0;
		String fileList = null;
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from File where Account_ID = 'execute'";
			rs = stmt.executeQuery(sql);
			rs.next();
			fileList = rs.getString("Filename") + "." + rs.getString("File_extension");
			while(rs.next()){
				count++;
			}
			if(count != 0)
				fileList += "+" + String.valueOf(count)+"papers";
			stmt.close(); 
			conn.close(); // close
			return fileList;
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return fileList;}
	}
	static void database_update_info(String PW, String Name, String Birth, String Email, String Phone_number){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			
			String sql = "USE Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "update Account set Password = '" + PW + "', Name = '" + Name + "', Birth = '" + Birth + "', Email = '" + Email + "', Phone_number = '" + Phone_number +"' Where ID = 'test1'";
			stmt.executeUpdate(sql);
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	static String database_get_info(){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			
			String sql = "USE Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from Account where ID = 'test1'";
			rs = stmt.executeQuery(sql);
			rs.next();
			String PW = rs.getString("Password");
			stmt.close(); 
			conn.close(); // close
			return PW;
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return null;}
	}
	static void database_delete_info(){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			
			String sql = "USE Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "delete from Account where ID = 'test1'";
			stmt.execute(sql);
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace();}
	}
	static void database_option_insert(String Color, String Seperate, String Cover, String Time, String Location){
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			String sql = "Use Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			//입금 미확인이라면 confirm = 1
			sql = "insert into option_select values ('test1', '"+ Color + "', '"+Seperate+"', '" + Cover +"', '" + Time +"', '" + Location+"')";
			stmt.execute(sql);			
			stmt.close(); 
			conn.close(); // close
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	static option_select database_get_option(){
		option_select os = new option_select();
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			
			String sql = "USE Dapoba_db";
			stmt = conn.createStatement();
			stmt.execute(sql);
			sql = "select * from option_select where ID = 'test1'";
			rs = stmt.executeQuery(sql);
			rs.next();
			os.color = rs.getString("Color");
			os.cover = rs.getString("Cover");
			os.seperate = rs.getString("Seperate");
			os.time = rs.getString("Time");
			os.loc = rs.getString("Location");
			stmt.close(); 
			conn.close(); // close
			return os;
		}
		catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return null;}
	}
	
	static String[] database_get_fileName(){
		try{
	         String[] fileList = new String[5];
	         int i = 0;
	         Class.forName(driver);
	         conn = DriverManager.getConnection(url, userId, passwd);// Connection 
	         String sql = "Use Dapoba_db";
	         stmt = conn.createStatement();
	         stmt.execute(sql);
	         sql = "select * from File where Account_ID = 'execute'";
	         rs = stmt.executeQuery(sql);
	         while(rs.next()){
	            fileList[i] = rs.getString("Filename") + "." + rs.getString("File_extension");
	            i++;
	         }
	         
	         stmt.close(); 
	         conn.close(); // close
	         return fileList;
	      }
	      catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); return null;}
	}
}
