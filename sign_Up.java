package dsa;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormEx
 */
@WebServlet("/sign_Up")
public class sign_Up extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sign_Up() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//MYsql과 연동부분
		//****************q***********************************************//
		String driver = "com.mysql.jdbc.Driver"; 
		String url = "jdbc:mysql://127.0.0.1:3306/dapoba_db?autoReconnect=true&useSSL=false"; 
		String userId = "root"; 
		String passwd = "1234"; 
		String table = "account";
		Connection conn; 
		Statement stmt; 
		//**************************************************************//
		
		try { 
			//선언부
			//************************************************************//	
			Class.forName(driver); // Driver Loading 
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			stmt = conn.createStatement();
			int resultFlag=0;// 1이면 아이디 중복, 2이면 비밀번호 오류, 0이면 문제 없음(결과 플래그)
			boolean validPW=false;
			String ID = request.getParameter("id");
			String Password = request.getParameter("pw");
			String name = request.getParameter("name");
			String Birth = request.getParameter("birth");
			String Email1 = request.getParameter("email1");
			String Email2 = request.getParameter("email2");
			String Phone_number = request.getParameter("phone_number");
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			PrintWriter out=null;
			out=response.getWriter();
			//************************************************************//
						
			//아이디 중복 검사.
			//************************************************************//
			System.out.println("id check");
			String sql = sb1.append("select * from " + table + " where ID like '"+ID+"'; ").toString();
	
			//***********************************************************//		
			try{
				System.out.println(sql);
				ResultSet rs = stmt.executeQuery(sql);
				
				//아이디 중복이 있다면 오류를 출력하고, 회원가입 페이지로 다시 돌아감.
				if(rs.next()== true){
					System.out.println("id duplicated");
					out.println("<script type=\"text/javascript\">");
					out.println("alert('id duplicated.');");
					//out.println("location='sign-up.jsp';");
					out.println("</script>");
				}
				
				//아이디가 중복이 아니라면 비밀번호 유효성을 검사함.
				else{
					System.out.println("password check");
					//비밀번호 유효성 검사.
					//************************************************************//
					java.util.regex.Pattern pattern = java.util.regex.Pattern.compile
					("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
					// 숫자 필요         소문자 필요       대문자 필요      특수문자 필요         띄어쓰기안됨  8글자 이상
					java.util.regex.Matcher matcher = pattern.matcher(Password);
					validPW = matcher.matches();
					System.out.println(Password);
					//************************************************************//
					
					//비밀번호가 유효하지 않다면 회원가입 페이지로 다시 돌아감.
					if(validPW!=true){ 
						System.out.println("password invalid");
						out.println("<script type=\"text/javascript\">");
						out.println("alert('password invalid.');");
						//out.println("location='sign-up.jsp';");
						out.println("</script>");
					}
					
					//회원가입에 성공시 입력된 데이터를 db에 올려주고 로그인 페이지로 이동.
					else{
						System.out.println("sign up succes 1");
						//입력된 데이터를 data base에 올리는 쿼리문 생성.
						//***********************************************************//
						sql = sb2.append("insert into " + table + "(ID, Password, Name, Birth, Email, Phone_number, Authority) values(")
								.append("'" + ID + "',")
								.append("'" + Password + "',")
								.append("'" + name + "',")
								.append("'" + Birth + "',")
								.append("'" + Email1+"@"+Email2 + "',")
								.append("'" + Phone_number + "',")
								.append("'" + 1 + "'")
								.append(");")
								.toString();
						System.out.println(sql);
						try{
							stmt.executeUpdate(sql);//쿼리문 실행
						} catch (SQLException e) {
							//TODO Auto-generated catch block
							e.printStackTrace();
						}
						//***********************************************************//
						
						//회원 가입시 회원의 아이디로 서버 디렉토리에 폴더를 생성
						System.out.println("folder making...");
						String folderName = ID.toString();
						File dir = new File("C:Users.AndrewYi.Documents.dapoba", folderName);
						dir.mkdir();

						
						//로그인 페이로 이동
						System.out.println("go to login page");
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Succeful Sign_up!');");
						//out.println("location='login.jsp';");
						out.println("</script>");
					}
				}
			} catch (SQLException e){e.printStackTrace();}
		} catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }	
	}
}
