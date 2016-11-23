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
			//file[i].store_file();
			i++;
		}
		
		return file_list;
	}
	
	private void store_file(String filename) throws SQLException
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
		out.println("alert('���� �߻� : \n'"+e+"');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
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
		//db�� ���� �κ�. 
		/*
		try {
			store_file(filename);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//out.print("�ۼ��� " +author+"<br>");
		
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
			//�����԰� Ȩ���� �ΰ� ���� 
			//�ΰ��� ����� ��ư�� �����ϴ� html������ ����� 
			out.print("location.href='fileConfirm.html';");//���ư� ������ ����
			out.println("</script>");
			out.close();
			//out.print("���ϸ� " + fileName+"<br>");
			//out.print("����ũ�� "+part.getSize()+"<br>");
		}
		//���� ȭ������ ���ư��� 
	}
	
}
