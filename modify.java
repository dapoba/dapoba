import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement;
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
@WebServlet("/modify")
public class modify extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modify() {
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
			String ID = request.getParameter("id");
			String Password = request.getParameter("pw");
			String name = request.getParameter("name");
			String Birth = request.getParameter("birth");
			String Email1 = request.getParameter("email1");
			String Email2 = request.getParameter("email2");
			String Phone_number = request.getParameter("phone_number");
			StringBuilder sb = new StringBuilder();
			PrintWriter out=null;
			out=response.getWriter();
			//************************************************************//

			//입력된 데이터를 data base에 업데이트하는 쿼리문 생성.
			//***********************************************************//
			String sql = sb.append("update into " + table + "(ID, Password, Name, Birth, Email, Phone_number, Authority) values(")
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
						
			//로그인 페이로 이동
			System.out.println("go to login page");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Succeful update!');");
			out.println("location='mypage.jsp';");
			out.println("</script>");
		} catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); }	
	}
}
