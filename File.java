package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

/**
 * Servlet implementation class File
 */
@WebServlet("/File")
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\file")
public class File extends HttpServlet {
	public String filename;
	private String file_extension;
	public String file_list[]=new String [100];
	database db= new database();
	public void delete_file() throws SQLException
	{
		//정말 지울거냐는 UI띄우기
		//해당 파일 DB에서 지우기
		System.out.println("정말 "+filename+"을 지우시겠습니까?");
		Scanner in=new Scanner(System.in);
		String answer=null;
		answer=in.nextLine();
		if(answer=="Y")
		{
			db.database_use();
			String sql="delete ";
			db.stmt.execute(sql);
			db.stmt.close(); 
			db.conn.close(); // close
			System.out.println("파일 삭제가 완료되었습니다.");
			return ;
		}
		else return;
	}
	public String[] select_file() throws SQLException
	{
		//UI파일 검색창에서 파일리스트들 선택
		//문서 홈페이지에 저장함. store_file()호출, 파일 하나 하나에 대한 store_file호출해야함.
		//파일의 이름 리스트 반환함. 
		File file[]=new File[20];
		//UI 파일 탐색창에서 파일 리스트를 받아왔다고 가정함. 
		//filename에 파일 이름 저장함. 
		int i=0;
		while(true)
		{
			file_list[i]="파일 이름 저장하기";
			i++;
			//더이상 선택한 파일이 없을 때까지 게속해서 while문 돌림. 
			//UI구현 후 이 부분 구현할 예정임. 
			if(file_list[i]==null)
				break;
		}
		i=0;
		//file_extension에 확장자 저장함. 
		while(file[i].filename!=null)
		{
			file[i].store_file();
			i++;
		}
		
		return file_list;
	}
	
	private void store_file() throws SQLException
	{
		//DB에 파일의 이름, 확장자 등을 저장함. 
		//저장완료 UI출력함. 
		db.database_use();
		//sql문 다시 작성=>file테이블 작성
		String sql="insert (into my_table (id, first_name, email) values " + "(1, \"woongjin\", \"finalboogi@naver.com\")"; 		
		db.stmt.execute(sql);
		System.out.println("저장이 완료되었습니다.");
		db.stmt.close(); 
		db.conn.close(); // close
	}
	
	private String getFilename(Part part)
	{
		String fileName=null;
		String contentDispositionHeader=part.getHeader("content-disposition");
		String [] elements=contentDispositionHeader.split(";");
		for(String element:elements)
		{
			if(element.trim().startsWith("filename")){
				fileName=element.substring(element.indexOf('=')+1);
				fileName=fileName.trim().replace("\"", "");
			}
		}
		return fileName;
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public File() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("EUC-KR");
		Part part=request.getPart("ex_filename");
		String fileName=getFilename(part);
		if(fileName!=null && !fileName.isEmpty())
		{
			part.write(fileName);
		}
		//String author=request.getParameter("theAuthor");
		//author=new String (author.getBytes("iso-8859-1"),"EUC-KR");
		response.setContentType("text/html); charset=EUC-KR");
		PrintWriter out= response.getWriter();
		
		//out.print("작성자 " +author+"<br>");
		out.print("파일명 " + fileName+"<br>");
		out.print("파일크기 "+part.getSize()+"<br>");
		//이전 화면으로 돌아가기 
	}
	
}
