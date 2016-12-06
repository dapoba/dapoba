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
//업로드할 파일의 경로와 제한 크기를 지정함. 경로는 임의로 지정함. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\Users\\HyunA\\Desktop")
public class signUp extends HttpServlet {
	public String []file_list=new String[5];
	public String[]filename=new String [5];
	private String[]file_extension=new String[5];
	database db= new database();//데이터베이스 사용
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
     * 지정한 파일을 서버에 업로드 하는 부분입니다.
     * store_file()함수를 호출하여 sql에 저장합니다.
     * 아래에 내용은 오픈소스를 참고하여 작성함을 명시합니다.- 참고서적 : 쾌도난마 JSP2.2 & Servlet 3.0따라하기
     */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName=request.getParameter("file_name");
		String DownloadPath="C:\\file\\";//파일의 위치 지정
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
	 * file.jsp에서 받아온 파일을 업로드하기 위한 메소드입니다.(최대 5개 파일 업로드 가능)
	 * 각 예외처리에 대해서 오류메시지를 출력하는 기능이 포함되어 있습니다.
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("EUC-KR");
		//MYsql과 연동부분
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
					//선언부
					//************************************************************//	
					Class.forName(driver); // Driver Loading 
					conn = DriverManager.getConnection(url, userId, passwd);// Connection 
					stmt = conn.createStatement();
					int resultFlag=0;// 1이면 아이디 중복, 2이면 비밀번호 오류, 0이면 문제 없음(결과 플래그)
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
					//아이디 중복 검사.
					//************************************************************//
						sql = "select id from " + table + " where ID like '"+ID+"'; ";
			
					//***********************************************************//		
						try{
							System.out.println(sql);
							ResultSet rs = stmt.executeQuery(sql);
						
						//아이디 중복이 있다면 오류를 출력하고, 회원가입 페이지로 다시 돌아감.
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
								//비밀번호 유효성 검사.
								//************************************************************//
								java.util.regex.Pattern pattern = java.util.regex.Pattern.compile
								("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}");
								// 숫자 필요         소문자 필요       대문자 필요      특수문자 필요         띄어쓰기안됨  8글자 이상
								java.util.regex.Matcher matcher = pattern.matcher(Password);
								validPW = matcher.matches();
								System.out.println(Password);
								//************************************************************//
							}
						}
							catch(Exception e)
							{
								
							}
								//비밀번호가 유효하지 않다면 회원가입 페이지로 다시 돌아감.
								if(validPW!=true){ 
									String message="password invalid.";
									print_error(response, message);
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
									out.println("<script type=\"text/javascript\">");
									out.println("alert('Succeful Sign_up!');");
									out.println("location.href=\"login.jsp\";");
									out.println("</script>");
								}
								
					
						//아이디가 중복이 아니라면 비밀번호 유효성을 검사함.
				}
				catch(Exception e)
				{
					
				}
	}
}
