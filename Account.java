import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.regex.*;

public class Account {
	protected String name;
	protected int birth;
	protected String e_mail;
	protected String address[];
	protected int phone_number;
	protected String password;
	public String ID;
	public String temporal_password;

	
	public Account(Connction conn, String table){
		this.conn = conn;
		this.table = table;
		try{
			this.stmt = conn.createStatement();
		} catch(SQLException e) {
			// TODO Auto_generated cathc block
			e.printStackTrace();
		}
	}
	
public Server{ 
	String driver = "com.mysql.jdbc.Driver"; 
	String url = "jdbc:mysql://127.0.0.1:3306/?user=root"; 
	String userId = "root"; 
	String passwd = "1234"; 
	String table = 'dapoba_db';
	Connection conn; 
	Statement stmt; 
	ResultSet res; 
	try { Class.forName(driver); // Driver Loading 
	conn = DriverManager.getConnection(url, userId, passwd); // Connection 
	} 
	catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); } 
} 

	
	
	//name�� setter�� getter�Լ�.
	public void set_name(String name)
	{
		this.name = name;
	}
	public String get_name()
	{
		String name = this.name;
		return name;
	}
	
	//birth�� setter�� getter�Լ�.
	public void set_birth(int birth)
	{
		this.birth = birth;
	}
	public int get_birth()
	{
		int birth = this.birth;
		return birth;
	}
	
	//e_mail�� setter�� getter�Լ�.
	public void set_e_mail(String e_mail){
		this.e_mail = e_mail;
	}
	public String get_e_mail(){
		String e_mail = this.e_mail;
		return e_mail;
	}
	
	//phone_number�� setter�� getter�Լ�.
	public void set_phone_number(int phone_number)
	{
		this.phone_number = phone_number;
	}
	public int get_phone_number()
	{
		int phone_number = this.phone_number;
		return phone_number;
	}
	
	//password�� setter�� getter�Լ�.
	public void set_password(String password)
	{
		this.password = password;
	}
	public String get_password()
	{
		String password = this.password;
		return password;
	}
	
	//ID�� setter�� getter�Լ�.
	public void set_ID(String ID)
	{
		this.ID = ID;
	}
	public String get_ID()
	{
		String ID = this.ID;
		return ID;
	}
	
	//account�� getter�Լ�.
	public Account get_customer_account()
	{
		Account a= new Account();
		return a;
	}
	
	/***********************************************************************************************
	*Function : public void Login()
	*Output : null
	*Input : String ID, String Password
	*Procedure : �α��� �Լ�
	*Date : 2016.11.15
	*Author's name : ������
	************************************************************************************************/
	public int Login(String ID, String Password){
		boolean ID_check = false;
		boolean Password_check = false;
		StringBuilder sb = new StringBuilder();
		
		//ID check
		String sql = sb.append("select * from" + table + "where")
				.append(" ID = ")
				.append(ID)
				.append(";").toString();
		try{
			ResultSet rs = stmt.excuteQuery(sql);
			if(rs.next()!= true)//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
				ID_check true;
			else
				ID_check false;
					
		} catch (SQLException e)
		{
			//TODO Aito-generated catch block
			e.printStackTrace();
		}
		//Password Check
		String sql = sb.append("select * from" + table + "where")
				.append(" password = ")
				.append(password)
				.append(";").toString();
		try{
			ResultSet rs = stmt.excuteQuery(sql);
			if(rs.next()!= true)//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
				Password_check true;
			else
				Password_check false;
					
		} catch (SQLException e)
		{
			//TODO Aito-generated catch block
			e.printStackTrace();
		}
		
		if (ID_check==true&&Password_check==true)
			return 0;//login �Լ��� flag�� 0�� �α����� �������̶�� ��.
		else if(ID_check==false)
			return 1;//login �Լ��� flag�� 1�� �Է¹��� ���̵� DB�� ���ٴ� ��(id ����).
		else
			return 2;//login �Լ��� flag�� 2�� �Է¹��� ���̵�� ���������� ��й�ȣ�� �ٸ��ٴ� ��.
		}
	
	/***********************************************************************************************
	*Function : public boolean check_id_duplication()
	*Output : boolean(�ߺ��� ���� ��� �� �� ��ȯ)
	*Input : String ID
	*Procedure : ���̵� �ߺ� �˻� �Լ�
	*Date : 2016.11.08
	*Author's name : ������
	************************************************************************************************/
	public boolean check_id_duplication(String ID)
	{
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("select * from" + table + "where")
				.append(" ID = ")
				.append(ID)
				.append(";").toString();
		try{
			ResultSet rs = stmt.excuteQuery(sql);
			if(rs.next()!= true)//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
				return true;
			else
				return false;
					
		} catch (SQLException e)
		{
			//TODO Aito-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/***********************************************************************************************
	*Function : public boolean check_password()
	*Output : boolean
	*Input : String Password
	*Procedure : ��й�ȣ ��ȿ�� �˻� �Լ�
	*Date : 2016.11.22
	*Author's name : ������
	************************************************************************************************/
	public boolean check_password(String Password){

		Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
		//����, ����, Ư�������� �������� Ȯ��
		Matcher m = p.matcher(Password);
		if (m.find()){
		    return true;//��й�ȣ ��ȿ�� Ȯ��.
		}else{
		  	return false;//��й�ȣ ������ �������� ����.
		}

	}
	
	
	/***********************************************************************************************
	*Function : public int sign_in()
	*Output : int
	*Input : String ID, String Password, String name, int Birth, String E-mail, int Phone_number
	*Procedure : ȸ������ �Լ�
	*Date : 2016.11.22
	*Author's name : ������
	************************************************************************************************/
	public int sign_in(String ID, String Password, String name, int Birth, String E-mail, int Phone_number)
	{
		int message=0;// 1�̸� ���̵� �ߺ�, 2�̸� ��й�ȣ ����, 0�̸� ���� ����(commit)
		
		if(check_id_duplication(String ID)!=true){ //���̵� ��ȿ���� ������ 1�� �޼��� ���.
			message=1;
			return message
		}
		if(check_password(Password)==true){ //��й�ȣ�� ��ȿ���� ������ 2�� �޼��� ���.
			message=2;
			return message
		}
		
		//�Էµ� �����͸� db�� �ø��� ������ ����.
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("inser into " + table + "(ID, Password, Name, Birth, E-mail, Phone_number) values(")
				.append("'" + ID + "',")
				.append("'" + Password + "',")
				.append("'" + name + "',")
				.append(Birth + ",")
				.append("'" + E-mail + "',")
				.append(name)
				.append(");")
				.toString();
		try{
			stmt.excuteUpdate(sql);//������ ����
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ȸ�� ���Խ� ȸ���� ���̵�� ���� ���丮�� 
		String folderName = ID.toString()
		File dir = new File("C:\Users\AndrewYi\Documents\dapoba", folderName);
		// ������ ������ ����     
		if(!dir.exists()){
			dir.mkdir();
		  }
		
		return message;//���̵� ���� �Ϸ�.
	}
	
	/***********************************************************************************************
	*Function : public String find_id()
	*Output : String
	*Input : String name, int Phone_number
	*Procedure : ���̵� ã�� �Լ�
	*Date : 2016.11.22
	*Author's name : ������
	************************************************************************************************/
	public String find_id(String name, int Phone_number)
	{
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("select ID from" + table + "where (name like '" + name + "') and (phone_number like " + Phone_number + ");" ).toString();

	
		try{
			ResultSet rs = stmt.excuteQuery(sql);
			if(rs.next()!= true)//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
				return rs.getString("ID");
			
			else
				return null;
					
		} catch (SQLException e)
		{
			//TODO Aito-generated catch block
			e.printStackTrace();
		}
	}
	
	/***********************************************************************************************
	*Function : public String find_password()
	*Output : String
	*Input :String ID, String name, int Phone_number
	*Procedure : ��й�ȣ ã�� �Լ�
	*Date : 2016.11.22
	*Author's name : ������
	************************************************************************************************/
	public String find_password(String ID, String name, int Phone_number)
	{
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("select Password from" + table + "where (ID like '"+ ID +"') and (name like '" + name + "') and (phone_number like " + Phone_number + ");" ).toString();

	
		try{
			ResultSet rs = stmt.excuteQuery(sql);
			if(rs.next()!= true)//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
				return rs.getString("ID");
			
			else
				return null;
					
		} catch (SQLException e)
		{
			//TODO Aito-generated catch block
			e.printStackTrace();
		}
	}
	
	public Account get_customer_account()
	{
		Account a= new Account();
		return a;
	}
}


