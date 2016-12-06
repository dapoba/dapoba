import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Servlet implementation class File
 */
@WebServlet("/findID")
//���ε��� ������ ��ο� ���� ũ�⸦ ������. ��δ� ���Ƿ� ������. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\Users\\HyunA\\Desktop\\Eclipse\\SE\\WebContent\\file_storage")
public class findID extends HttpServlet {
	public String []file_list=new String[5];
	public String[]filename=new String [5];
	private String[]file_extension=new String[5];
	database db= new database();//�����ͺ��̽� ���
	Account customer=new Account();
	

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public findID() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    /*
     * ������ ������ ������ ���ε� �ϴ� �κ��Դϴ�.
     * store_file()�Լ��� ȣ���Ͽ� sql�� �����մϴ�.
     * �Ʒ��� ������ ���¼ҽ��� �����Ͽ� �ۼ����� ����մϴ�.- ������ : �赵���� JSP2.2 & Servlet 3.0�����ϱ�
     */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * file.jsp���� �޾ƿ� ������ ���ε��ϱ� ���� �޼ҵ��Դϴ�.(�ִ� 5�� ���� ���ε� ����)
	 * �� ����ó���� ���ؼ� �����޽����� ����ϴ� ����� ���ԵǾ� �ֽ��ϴ�.
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//***************************************************************//
		String driver = "com.mysql.jdbc.Driver"; 
		String url = "jdbc:mysql://localhost:3306/?user=root"; 
		String userId = "root"; 
		String passwd = "1234"; 
		String table = "dapoba_db.account";
		Connection conn; 
		Statement stmt; 
		PrintWriter out=null;
		out=response.getWriter();
		//**************************************************************//
		try { 
			Class.forName(driver); // Driver Loading 
			conn = DriverManager.getConnection(url, userId, passwd);// Connection 
			stmt = conn.createStatement();

			StringBuilder sb = new StringBuilder();
		
			String name = request.getParameter("name");
			String Phone_number = request.getParameter("phone_number");
			System.out.println(name);
			System.out.println(Phone_number);
			String sql = sb.append("select ID from " + table + " where (name like '" + name + "') and (phone_number like '" + Phone_number + "');" ).toString();
		
			System.out.println(sql);
			try{
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next()== true){//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
					
					String pass=rs.getString("ID");
					System.out.println("go to login page");
					out.println("<script type=\"text/javascript\">");
					out.println("alert(\""+pass+"\");");
					out.println("location.href= 'login.jsp';");
					out.println("</script>");
					}
				
				else
					System.out.println("ID does not exist");
						
			} catch (SQLException e)
			{
				//TODO Aito-generated catch block
				e.printStackTrace();
			}
			
		
			} catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); 
		}	
	}
}
