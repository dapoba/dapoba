import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cash {
	public String account_number;
	public String bank_name;
	public String account_holder_name;
	public void get_account(String bankName)
	{
		String driver = "com.mysql.jdbc.Driver"; 
		  String url = "jdbc:mysql://localhost:3306/?user=root";
		  String userId = "root";
		  String passwd = "1234";
		  Connection conn;
		  Statement stmt;
		  ResultSet rs;
		  
		  try {
		   Class.forName(driver); // Driver Loading
		   conn = DriverManager.getConnection(url, userId, passwd); // Connection
		   		   
		   stmt = conn.createStatement();
			String sql = "USE Dapoba_db";
			
			stmt.execute(sql);
			sql = "select * from Cash where bank_name = " + "\'"+bankName+"\'";
			rs = stmt.executeQuery(sql);
			
			rs.next();
			account_number = rs.getString("Account_number");
			bank_name = rs.getString("bank_name");
			account_holder_name = rs.getString("account_holder_name");
			 stmt.close(); 
			conn.close(); // close
		  } catch(ClassNotFoundException | SQLException e) {
		   if(bankName.equals("shinhan")){
			   account_number = "110-293-575778";
			   bank_name = bankName;
			   account_holder_name = "NamHyojin";
		   }
		   else if(bankName.equals("kukmin")){
			   account_number = "243702-04-125540";
			   bank_name = bankName;
			   account_holder_name = "NamHyojin";
		   }
		   else if(bankName.equals("woori")){
			   account_number = "1002-554-400497";
			   bank_name = bankName;
			   account_holder_name = "NamHyojin";
		   }
		 }
	}
}
