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

	
	
	//name의 setter와 getter함수.
	public void set_name(String name)
	{
		this.name = name;
	}
	public String get_name()
	{
		String name = this.name;
		return name;
	}
	
	//birth의 setter와 getter함수.
	public void set_birth(int birth)
	{
		this.birth = birth;
	}
	public int get_birth()
	{
		int birth = this.birth;
		return birth;
	}
	
	//e_mail의 setter와 getter함수.
	public void set_e_mail(String e_mail){
		this.e_mail = e_mail;
	}
	public String get_e_mail(){
		String e_mail = this.e_mail;
		return e_mail;
	}
	
	//phone_number의 setter와 getter함수.
	public void set_phone_number(int phone_number)
	{
		this.phone_number = phone_number;
	}
	public int get_phone_number()
	{
		int phone_number = this.phone_number;
		return phone_number;
	}
	
	//password의 setter와 getter함수.
	public void set_password(String password)
	{
		this.password = password;
	}
	public String get_password()
	{
		String password = this.password;
		return password;
	}
	
	//ID의 setter와 getter함수.
	public void set_ID(String ID)
	{
		this.ID = ID;
	}
	public String get_ID()
	{
		String ID = this.ID;
		return ID;
	}
	
	//account의 getter함수.
	public Account get_customer_account()
	{
		Account a= new Account();
		return a;
	}
	
	/***********************************************************************************************
	*Function : public void Login()
	*Output : null
	*Input : String ID, String Password
	*Procedure : 로그인 함수
	*Date : 2016.11.15
	*Author's name : 이재혁
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
			if(rs.next()!= true)//중복이 없다면 참 값 반환, 있다면 거짓 값 반환.
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
			if(rs.next()!= true)//중복이 없다면 참 값 반환, 있다면 거짓 값 반환.
				Password_check true;
			else
				Password_check false;
					
		} catch (SQLException e)
		{
			//TODO Aito-generated catch block
			e.printStackTrace();
		}
		
		if (ID_check==true&&Password_check==true)
			return 0;//login 함수의 flag가 0은 로그인이 성공적이라는 뜻.
		else if(ID_check==false)
			return 1;//login 함수의 flag가 1은 입력받은 아이디가 DB에 없다는 뜻(id 오류).
		else
			return 2;//login 함수의 flag가 2는 입력받은 아이디는 존재하지만 비밀번호가 다르다는 뜻.
		}
	
	/***********************************************************************************************
	*Function : public boolean check_id_duplication()
	*Output : boolean(중복이 없을 경우 참 값 반환)
	*Input : String ID
	*Procedure : 아이디 중복 검사 함수
	*Date : 2016.11.08
	*Author's name : 이재혁
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
			if(rs.next()!= true)//중복이 없다면 참 값 반환, 있다면 거짓 값 반환.
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
	*Procedure : 비밀번호 유효성 검사 함수
	*Date : 2016.11.22
	*Author's name : 이재혁
	************************************************************************************************/
	public boolean check_password(String Password){

		Pattern p = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
		//문자, 숫자, 특수문자의 조합인지 확인
		Matcher m = p.matcher(Password);
		if (m.find()){
		    return true;//비밀번호 유효성 확인.
		}else{
		  	return false;//비밀번호 조합의 적합하지 않음.
		}

	}
	
	
	/***********************************************************************************************
	*Function : public int sign_in()
	*Output : int
	*Input : String ID, String Password, String name, int Birth, String E-mail, int Phone_number
	*Procedure : 회원가입 함수
	*Date : 2016.11.22
	*Author's name : 이재혁
	************************************************************************************************/
	public int sign_in(String ID, String Password, String name, int Birth, String E-mail, int Phone_number)
	{
		int message=0;// 1이면 아이디 중복, 2이면 비밀번호 오류, 0이면 문제 없음(commit)
		
		if(check_id_duplication(String ID)!=true){ //아이디가 유효하지 않으면 1번 메세지 출력.
			message=1;
			return message
		}
		if(check_password(Password)==true){ //비밀번호가 유효하지 않으면 2번 메세지 출력.
			message=2;
			return message
		}
		
		//입력된 데이터를 db에 올리는 쿼리문 생성.
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
			stmt.excuteUpdate(sql);//쿼리문 실행
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//회원 가입시 회원의 아이디로 서버 디렉토리에 
		String folderName = ID.toString()
		File dir = new File("C:\Users\AndrewYi\Documents\dapoba", folderName);
		// 폴더가 없으면 생성     
		if(!dir.exists()){
			dir.mkdir();
		  }
		
		return message;//아이디 생성 완료.
	}
	
	/***********************************************************************************************
	*Function : public String find_id()
	*Output : String
	*Input : String name, int Phone_number
	*Procedure : 아이디 찾는 함수
	*Date : 2016.11.22
	*Author's name : 이재혁
	************************************************************************************************/
	public String find_id(String name, int Phone_number)
	{
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("select ID from" + table + "where (name like '" + name + "') and (phone_number like " + Phone_number + ");" ).toString();

	
		try{
			ResultSet rs = stmt.excuteQuery(sql);
			if(rs.next()!= true)//중복이 없다면 참 값 반환, 있다면 거짓 값 반환.
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
	*Procedure : 비밀번호 찾는 함수
	*Date : 2016.11.22
	*Author's name : 이재혁
	************************************************************************************************/
	public String find_password(String ID, String name, int Phone_number)
	{
		StringBuilder sb = new StringBuilder();
		String sql = sb.append("select Password from" + table + "where (ID like '"+ ID +"') and (name like '" + name + "') and (phone_number like " + Phone_number + ");" ).toString();

	
		try{
			ResultSet rs = stmt.excuteQuery(sql);
			if(rs.next()!= true)//중복이 없다면 참 값 반환, 있다면 거짓 값 반환.
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


