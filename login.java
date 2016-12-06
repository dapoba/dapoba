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
//업로드할 파일의 경로와 제한 크기를 지정함. 경로는 임의로 지정함. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\Users\\HyunA\\Desktop")
public class login extends HttpServlet {
	public String []file_list=new String[5];
	public String[]filename=new String [5];
	private String[]file_extension=new String[5];
	database db= new database();//데이터베이스 사용
	Account customer=new Account();
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
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

	/*
	 * 각 예외상황에 대해 알맞게 메시지를 출력하는 메소드입니다.
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
		//예외메시지를 자바스크립트를 이용해 alert메시지로 출력
		out.println("<script type='text/javascript'>");
		out.println("alert('에러 발생 : \n'"+e+"');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
	
	/*
	 * 파일이 존재하지 않을 경우의 에러메시지를 출력해주는 메소드입니다.
	 */
	protected void errorMessage(HttpServletResponse response) throws IOException
	{
		PrintWriter out = null;
		out = response.getWriter();
		out.println("<script type='text/javascript'>");
		out.println("alert('해당 파일이 존재하지 않습니다.');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
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
		//초기화
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
			//로그인 성공.
		
			else if(ID_check==false){
				System.out.println("id fail");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('id fail');");
				out.println("location.href= 'login.jsp';");
				out.println("</script>");
			}
				//login 함수의 flag가 1은 입력받은 아이디가 DB에 없다는 뜻(id 오류).
		
			else{
				System.out.println("password fail");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('password fail');");
				out.println("location.href= 'login.jsp';");
				out.println("</script>");
			}
				//login 함수의 flag가 2는 입력받은 아이디는 존재하지만 비밀번호가 다르다는 뜻.
		
			} catch(ClassNotFoundException | SQLException e) { e.printStackTrace(); 
		}	
	}
}
