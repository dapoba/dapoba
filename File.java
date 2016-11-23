package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
		//예라고 선택한다면 삭제 진행
		//아니라면 함수 종료
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
			//file[i].store_file();
			i++;
		}
		
		return file_list;
	}
	
	private void store_file(String filename) throws SQLException
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void errorMessage(Exception e,HttpServletResponse response)
	{
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		out.println("<script type='text/javascript'>");
		out.println("alert('에러 발생 : \n'"+e+"');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
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
	 * @throws UnsupportedEncodingException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("EUC-KR");
		Part part1 = null;
		Part part2 = null;
		Part part3 = null;
		Part part4 = null;
		Part part5 = null;
		response.setContentType("text/html; charset=EUC-KR");
		int fileEmpty=0;
		String fileName[]=new String[5];
		try {
			part1 = request.getPart("filename1");
			part2 = request.getPart("filename2");
			part3= request.getPart("filename3");
			part4 = request.getPart("filename4");
			part5 = request.getPart("filename5");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			errorMessage(e,response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errorMessage(e,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			errorMessage(e,response);
		}
		fileName[0]=getFilename(part1);
		fileName[1]=getFilename(part2);
		fileName[2]=getFilename(part3);
		fileName[3]=getFilename(part4);
		fileName[4]=getFilename(part5);
		for(int i=0;i<5;i++)
		{
			if(fileName[i]!=null&&!fileName[i].isEmpty())
				{
					try {
						switch(i)
						{
						case 0 :
							part1.write(fileName[0]);
						case 1:
							part2.write(fileName[1]);
						case 2 :
							part3.write(fileName[2]);
						case 3:
							part4.write(fileName[3]);
						case 4:
							part5.write(fileName[4]);
						}
						//part1.write(fileName[i]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			else fileEmpty++;
		}
		
		if(fileEmpty==5)
			try {
				errorMessage(response);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		/*
		if(fileName!=null && !fileName.isEmpty())
		{
			try {
				part1.write(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorMessage(e,response);
			}
		}
		*/
		
		
		//String author=request.getParameter("theAuthor");
		//author=new String (author.getBytes("iso-8859-1"),"EUC-KR");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errorMessage(e,response);
		}
		//db에 들어가는 부분. 
		/*
		try {
			store_file(filename);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//out.print("작성자 " +author+"<br>");
		
		if((part1.getSize()==0)&&(part2.getSize()==0)&&(part3.getSize()==0)&&(part4.getSize()==0)&&(part5.getSize()==0))
			try {
				errorMessage(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorMessage(e,response);
			}
		else
		{
			out.println("<script type='text/javascript'>");
			//문서함과 홈으로 두개 가기 
			//두가지 경우의 버튼을 생성하는 html파일을 만들기 
			out.print("location.href='fileConfirm.html';");//돌아갈 페이지 지정
			out.println("</script>");
			out.close();
			//out.print("파일명 " + fileName+"<br>");
			//out.print("파일크기 "+part.getSize()+"<br>");
		}
		//이전 화면으로 돌아가기 
	}
	
}
