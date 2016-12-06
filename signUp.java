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
@WebServlet("/signUp")
//���ε��� ������ ��ο� ���� ũ�⸦ ������. ��δ� ���Ƿ� ������. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\Users\\HyunA\\Desktop")
public class signUp extends HttpServlet {
	public String []file_list=new String[5];
	public String[]filename=new String [5];
	private String[]file_extension=new String[5];
	database db= new database();//�����ͺ��̽� ���
	Account customer=new Account();
	void print_error(HttpServletResponse response,String message) throws IOException
	{
		PrintWriter out = null;
		out = response.getWriter();
		out.println("<script type='text/javascript'>");
		out.println("alert('"+message+"');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public signUp() {
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
		//MYsql�� �����κ�
				//****************q***********************************************//
				String driver = "com.mysql.jdbc.Driver"; 
				String url = "jdbc:mysql://localhost:3306/?user=root"; 
				String userId = "root"; 
				String passwd = "1234"; 
				String table = "dapoba_db.account";
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
					String ID="";
					String Password =  "";
					String name =  "";
					String Birth = "";
					String Email1 =  "";
					String Email2 = "";
					String Phone_number = "";
					StringBuilder sb1 = new StringBuilder();
					StringBuilder sb2 = new StringBuilder();
					PrintWriter out=null;
					out=response.getWriter();
					String sql="";
					//************************************************************//
					
						ID = request.getParameter("id");
					//���̵� �ߺ� �˻�.
					//************************************************************//
						sql = "select id from " + table + " where ID like '"+ID+"'; ";
			
					//***********************************************************//		
						try{
							System.out.println(sql);
							ResultSet rs = stmt.executeQuery(sql);
						
						//���̵� �ߺ��� �ִٸ� ������ ����ϰ�, ȸ������ �������� �ٽ� ���ư�.
							if(rs.next()== true){
								String message="Id is duplicated";
								print_error(response, message);
							}
							else {
								Password = request.getParameter("pw");
								name = request.getParameter("name");
								Birth = request.getParameter("birth");
								Email1 = request.getParameter("email1");
								Email2 = request.getParameter("email2");
								Phone_number = request.getParameter("phone_number");
								System.out.println("password check");
								//��й�ȣ ��ȿ�� �˻�.
								//************************************************************//
								java.util.regex.Pattern pattern = java.util.regex.Pattern.compile
								("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}");
								// ���� �ʿ�         �ҹ��� �ʿ�       �빮�� �ʿ�      Ư������ �ʿ�         ����ȵ�  8���� �̻�
								java.util.regex.Matcher matcher = pattern.matcher(Password);
								validPW = matcher.matches();
								System.out.println(Password);
								//************************************************************//
							}
						}
							catch(Exception e)
							{
								
							}
								//��й�ȣ�� ��ȿ���� �ʴٸ� ȸ������ �������� �ٽ� ���ư�.
								if(validPW!=true){ 
									String message="password invalid.";
									print_error(response, message);
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
									out.println("<script type=\"text/javascript\">");
									out.println("alert('Succeful Sign_up!');");
									out.println("location.href=\"login.jsp\";");
									out.println("</script>");
								}
								
					
						//���̵� �ߺ��� �ƴ϶�� ��й�ȣ ��ȿ���� �˻���.
				}
				catch(Exception e)
				{
					
				}
	}
}
