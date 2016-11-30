package dsa;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormEx
 */
@WebServlet("/Log_In")
public class Log_In extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log_In() {
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
		//***************************************************************//
		String driver = "com.mysql.jdbc.Driver"; 
		String url = "jdbc:mysql://127.0.0.1:3306/dapoba_db?autoReconnect=true&useSSL=false"; 
		String userId = "root"; 
		String passwd = "1234"; 
		String table = "account";
		Connection conn; 
		Statement stmt; 
		//**************************************************************//
		try { 
			Class.forName(driver); // Driver Loading 
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			stmt = conn.createStatement();
			boolean ID_check = false;
			boolean Password_check = false;
			StringBuilder sb = new StringBuilder();
			StringBuilder sb1 = new StringBuilder();
		
			String ID = request.getParameter("id");
			String password = request.getParameter("pw");
			
		
			//ID check
			String sql = sb.append("select * from " + table + " where")
					.append(" ID = '")
					.append(ID)
					.append("';").toString();
			System.out.println(sql);
			try{
				ResultSet rs = stmt.executeQuery(sql);//sercer.java랑 비교해볼것

				if(rs.next()== true)//중복이 없다면 참 값 반환, 있다면 거짓 값 반환.
					ID_check = true;
				else
					ID_check = false;
				
			} catch (SQLException e){
				//TODO Aito-generated catch block
				e.printStackTrace();
			}
			//Password Check

			sql = sb1.append("select * from " + table + " where")
					.append(" Password = '")
					.append(password)
					.append("';").toString();
			System.out.println(sql);
			try{
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next()== true)//중복이 없다면 참 값 반환, 있다면 거짓 값 반환.
					Password_check = true;
				else
					Password_check =  false;
				
			} catch (SQLException e){
				//TODO Aito-generated catch block
				e.printStackTrace();
			}
		
			if (ID_check==true && Password_check==true){
				System.out.println("success");
				response.sendRedirect("main.html");

			}
				//response.sendRedirect("main.html");
			//로그인 성공.
		
			else if(ID_check==false)
				System.out.println("id fail");
				//login 함수의 flag가 1은 입력받은 아이디가 DB에 없다는 뜻(id 오류).
		
			else
				System.out.println("password fail");
				//login 함수의 flag가 2는 입력받은 아이디는 존재하지만 비밀번호가 다르다는 뜻.
		
			} catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); 
		}	
	}
}
