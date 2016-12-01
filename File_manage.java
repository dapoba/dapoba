package test;
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
import java.sql.SQLException;
import java.util.Scanner;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Servlet implementation class File
 */
@WebServlet("/FileManage")
//업로드할 파일의 경로와 제한 크기를 지정함. 경로는 임의로 지정함. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\file")
public class File_manage extends HttpServlet {
	public String []file_list=new String[5];
	public String[]filename=new String [5];
	private String[]file_extension=new String[5];
	database db= new database();//데이터베이스 사용
	Account customer=new Account();
	
	public void select_file(String[] print_file,HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException
	{
		int i=0;
		//Option클래스 생성
		Option file_option=new Option();
		while(true)
		{
			//파일 이름이 비었으면 더이상 list가 없는 것이므로 while문을 빠져나간다.
			if(print_file[i]==null)
				break;
			//option객체의 파일리스트에 매개변수로 받은 파일 리스트를 저장한다. 
			file_option.fileName[i]=print_file[i];
			i++;//다음 파일 이름으로 이동함.
		}
		file_option.doGet(request, response);
	}
	
	/*
	 * 매개변수로 받은 fileName에 저장된 파일들을 데이터베이스에 저장하는 메소드입니다.
	 */
	private void store_file(String[] file_list) throws SQLException
	{
		String file_path="";
		for(int i=0;i<5;i++)
		{
			file_path="C:\\file\\"+file_list[i];
			if(!file_list[i].equals(" "))
			{
				int pdfPng=extractPagesAsImage(file_path, 300, "");//저장할 pdf파일의 장수를 저장=>인쇄 결제시 필요
				String[] file_name=file_list[i].split(".");
				filename[i]=file_name[0];
				file_extension[i]=file_name[1];
				System.out.println(pdfPng+file_name[0]+file_name[1]);
				db.database_use();
				String sql="insert into dapoba_db.file (Filename, File_extension, File_page_no, Account_ID) values " + "('"+file_name[0]+"' ,'"+file_name[1]+"', '"+pdfPng+"', 'test1');"; 		
				db.stmt.execute(sql);
				db.stmt.close(); 
				db.conn.close(); // close
			}
		}
		
	}
	
	/*
	 * pdf파일의 장수를 세주는 메소드입니다.
	 * 오픈소스를 참고했음을 명시합니다.
	 */
	public static int extractPagesAsImage(String sourceFile, int resolution, String password) {
        boolean result = false;

        int pdfPageCn = 0;
        PDDocument pdfDoc = null;
        try {
            //PDF파일 정보 취득
            pdfDoc = PDDocument.load(new File(sourceFile));
            pdfPageCn = pdfDoc.getNumberOfPages();

        } catch (IOException ioe) {
        	
        }
        return pdfPageCn;
    }
	
	/*
	 * Part클래스의 객체 part에서 파일의 이름을 가져오는 메소드로
	 * 오픈소스를 참고했음을 명시합니다.
	 */
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
    public File_manage() {
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
		String button_value="";
		String print_file[]=new String[5];
		button_value=request.getParameter("select_button");
		//초기화
		Part part1 = null;
		Part part2 = null;
		Part part3 = null;
		Part part4 = null;
		Part part5 = null;
		response.setContentType("text/html; charset=EUC-KR");
		int fileEmpty=0;
		if(button_value.equals("upload"))
		{
		//file.jsp에서 선택한 각각의 파일 명을 저장을 위해 가져온다.
		//예외가 발생했을 경우 예외처리와 오류메시지 출력
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
		
		//각각 부분의 파일명을 저장한다.
		file_list[0]=getFilename(part1);
		file_list[1]=getFilename(part2);
		file_list[2]=getFilename(part3);
		file_list[3]=getFilename(part4);
		file_list[4]=getFilename(part5);
		
		//각 부분의 파일들을 처리한다.
		for(int i=0;i<5;i++)
		{
			if(file_list[i]!=null&&!file_list[i].isEmpty())
				{
					try {
						switch(i)
						{
						case 0 :
							part1.write(file_list[0]);
						case 1:
							part2.write(file_list[1]);
						case 2 :
							part3.write(file_list[2]);
						case 3:
							part4.write(file_list[3]);
						case 4:
							part5.write(file_list[4]);
						}
						//part1.write(file_list[i]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			else fileEmpty++;//어떤 파일도 선택되지 않았는지 확인하기 위한 flag역할
		}
		
		//어떤 파일도 선택되지 않았을 경우 예외 발생시킴
		//오류메시지 출력함
		if(fileEmpty==5)
			try {
				errorMessage(response);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//예외발생
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errorMessage(e,response);
		}
		
		//모든 파일이 빈파일일 경우 예외발생
		//오류메세지 출력
		if((part1.getSize()==0)&&(part2.getSize()==0)&&(part3.getSize()==0)&&(part4.getSize()==0)&&(part5.getSize()==0))
			try {
				errorMessage(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorMessage(e,response);
			}
		else
		{
			//선택한 파일들을 데이터베이스에 저장하는 문장 
			try {
				store_file(file_list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("<script type='text/javascript'>");
			//문서함과 홈으로 두개 가기 
			out.print("location.href='fileConfirm.jsp';");//돌아갈 페이지 지정
			out.println("</script>");
			out.close();
		}
		}//이전 화면으로 돌아가기 
		else if(button_value.equals("print"))
		{
			print_file=request.getParameterValues("file_select");
			try {
				select_file(print_file, request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
