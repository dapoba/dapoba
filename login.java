import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
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
@WebServlet("/login")
//���ε��� ������ ��ο� ���� ũ�⸦ ������. ��δ� ���Ƿ� ������. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\Users\\HyunA\\Desktop")
public class login extends HttpServlet {
	public String []file_list=new String[5];
	public String[]filename=new String [5];
	private String[]file_extension=new String[5];
	database db= new database();//�����ͺ��̽� ���
	Account customer=new Account();
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
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
		String fileName=request.getParameter("file_name");
		String DownloadPath="C:\\file\\";//������ ��ġ ����
		String sFilePath=DownloadPath+"test1\\"+fileName;//String sFilePath= sDownloadPath+customer.get_id()+"\\"+fileName;
		byte b[]= new byte[4096];
		FileInputStream in = new FileInputStream(sFilePath);
		String sMimeType= getServletContext().getMimeType(sFilePath);
		if(sMimeType==null)
			sMimeType="application/octet-stream";
		response.setContentType(sMimeType);
		String sEncoding=new String(fileName.getBytes("euc-kr"),"8859_1");
		response.setHeader("Content-Disposition", "attachment; filename= "+ sEncoding);
		ServletOutputStream out=response.getOutputStream();
		int numRead;
		while((numRead=in.read(b,0,b.length))!=-1)
			out.write(b,0,numRead);
		out.flush();
		out.close();
		in.close();
	}

	/*
	 * �� ���ܻ�Ȳ�� ���� �˸°� �޽����� ����ϴ� �޼ҵ��Դϴ�.
	 */
	protected void errorMessage(Exception e,HttpServletResponse response)
	{
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//���ܸ޽����� �ڹٽ�ũ��Ʈ�� �̿��� alert�޽����� ���
		out.println("<script type='text/javascript'>");
		out.println("alert('���� �߻� : \n'"+e+"');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
	
	/*
	 * ������ �������� ���� ����� �����޽����� ������ִ� �޼ҵ��Դϴ�.
	 */
	protected void errorMessage(HttpServletResponse response) throws IOException
	{
		PrintWriter out = null;
		out = response.getWriter();
		out.println("<script type='text/javascript'>");
		out.println("alert('�ش� ������ �������� �ʽ��ϴ�.');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
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
		request.setCharacterEncoding("EUC-KR");
		//�ʱ�ȭ
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
				ResultSet rs = stmt.executeQuery(sql);//sercer.java�� ���غ���

				if(rs.next()== true)//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
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
				if(rs.next()== true)//�ߺ��� ���ٸ� �� �� ��ȯ, �ִٸ� ���� �� ��ȯ.
					Password_check = true;
				else
					Password_check =  false;
				
			} catch (SQLException e){
				//TODO Aito-generated catch block
				e.printStackTrace();
			}
		
			if (ID_check==true && Password_check==true){
				ServletContext application=this.getServletContext();
				application.setAttribute("userid", ID);
				if(ID.equals("manager1")||ID.equals("manager2")||ID.equals("manager3"))
					response.sendRedirect("admin.jsp");
				else{
					System.out.println("go to login page");
					out.println("<script type=\"text/javascript\">");
					out.println("location.href= 'main.jsp';");
					out.println("</script>");
				}
			//	response.sendRedirect("main.jsp");
				
			}
				//response.sendRedirect("main.html");
			//�α��� ����.
		
			else if(ID_check==false){
				System.out.println("id fail");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('id fail');");
				out.println("location.href= 'login.jsp';");
				out.println("</script>");
			}
				//login �Լ��� flag�� 1�� �Է¹��� ���̵� DB�� ���ٴ� ��(id ����).
		
			else{
				System.out.println("password fail");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('password fail');");
				out.println("location.href= 'login.jsp';");
				out.println("</script>");
			}
				//login �Լ��� flag�� 2�� �Է¹��� ���̵�� ���������� ��й�ȣ�� �ٸ��ٴ� ��.
		
			} catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); 
		}	
	}
}
