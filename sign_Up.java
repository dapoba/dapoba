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
		
		
		//MYsql�� �����κ�
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
			//�����
			//************************************************************//	
			Class.forName(driver); // Driver Loading 
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			stmt = conn.createStatement();
			int resultFlag=0;// 1�̸� ���̵� �ߺ�, 2�̸� ��й�ȣ ����, 0�̸� ���� ����(��� �÷���)
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
						
			//���̵� �ߺ� �˻�.
			//************************************************************//
			System.out.println("id check");
			String sql = sb1.append("select * from " + table + " where ID like '"+ID+"'; ").toString();
	
			//***********************************************************//		
			try{
				System.out.println(sql);
				ResultSet rs = stmt.executeQuery(sql);
				
				//���̵� �ߺ��� �ִٸ� ������ ����ϰ�, ȸ������ �������� �ٽ� ���ư�.
				if(rs.next()== true){
					System.out.println("id duplicated");
					out.println("<script type=\"text/javascript\">");
					out.println("alert('id duplicated.');");
					//out.println("location='sign-up.jsp';");
					out.println("</script>");
				}
				
				//���̵� �ߺ��� �ƴ϶�� ��й�ȣ ��ȿ���� �˻���.
				else{
					System.out.println("password check");
					//��й�ȣ ��ȿ�� �˻�.
					//************************************************************//
					java.util.regex.Pattern pattern = java.util.regex.Pattern.compile
					("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
					// ���� �ʿ�         �ҹ��� �ʿ�       �빮�� �ʿ�      Ư������ �ʿ�         ����ȵ�  8���� �̻�
					java.util.regex.Matcher matcher = pattern.matcher(Password);
					validPW = matcher.matches();
					System.out.println(Password);
					//************************************************************//
					
					//��й�ȣ�� ��ȿ���� �ʴٸ� ȸ������ �������� �ٽ� ���ư�.
					if(validPW!=true){ 
						System.out.println("password invalid");
						out.println("<script type=\"text/javascript\">");
						out.println("alert('password invalid.');");
						//out.println("location='sign-up.jsp';");
						out.println("</script>");
					}
					
					//ȸ�����Կ� ������ �Էµ� �����͸� db�� �÷��ְ� �α��� �������� �̵�.
					else{
						System.out.println("sign up succes 1");
						//�Էµ� �����͸� data base�� �ø��� ������ ����.
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
							stmt.executeUpdate(sql);//������ ����
						} catch (SQLException e) {
							//TODO Auto-generated catch block
							e.printStackTrace();
						}
						//***********************************************************//
						
						//ȸ�� ���Խ� ȸ���� ���̵�� ���� ���丮�� ������ ����
						System.out.println("folder making...");
						String folderName = ID.toString();
						File dir = new File("C:Users.AndrewYi.Documents.dapoba", folderName);
						dir.mkdir();

						
						//�α��� ���̷� �̵�
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
