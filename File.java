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
		//���� ����ųĴ� UI����
		//�ش� ���� DB���� �����
		System.out.println("���� "+filename+"�� ����ðڽ��ϱ�?");
		//����� �����Ѵٸ� ���� ����
		//�ƴ϶�� �Լ� ����
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
			System.out.println("���� ������ �Ϸ�Ǿ����ϴ�.");
			return ;
		}
		else return;
	}
	public String[] select_file() throws SQLException
	{
		//UI���� �˻�â���� ���ϸ���Ʈ�� ����
		//���� Ȩ�������� ������. store_file()ȣ��, ���� �ϳ� �ϳ��� ���� store_fileȣ���ؾ���.
		//������ �̸� ����Ʈ ��ȯ��. 
		File file[]=new File[20];
		//UI ���� Ž��â���� ���� ����Ʈ�� �޾ƿԴٰ� ������. 
		//filename�� ���� �̸� ������. 
		int i=0;
		while(true)
		{
			file_list[i]="���� �̸� �����ϱ�";
			i++;
			//���̻� ������ ������ ���� ������ �Լ��ؼ� while�� ����. 
			//UI���� �� �� �κ� ������ ������. 
			if(file_list[i]==null)
				break;
		}
		i=0;
		//file_extension�� Ȯ���� ������. 
		while(file[i].filename!=null)
		{
			file[i].store_file();
			i++;
		}
		
		return file_list;
	}
	
	private void store_file() throws SQLException
	{
		//DB�� ������ �̸�, Ȯ���� ���� ������. 
		//����Ϸ� UI�����. 
		db.database_use();
		//sql�� �ٽ� �ۼ�=>file���̺� �ۼ�
		String sql="insert (into my_table (id, first_name, email) values " + "(1, \"woongjin\", \"finalboogi@naver.com\")"; 		
		db.stmt.execute(sql);
		System.out.println("������ �Ϸ�Ǿ����ϴ�.");
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
		
		//out.print("�ۼ��� " +author+"<br>");
		out.print("���ϸ� " + fileName+"<br>");
		out.print("����ũ�� "+part.getSize()+"<br>");
		//���� ȭ������ ���ư��� 
	}
	
}
