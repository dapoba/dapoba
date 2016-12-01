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
//���ε��� ������ ��ο� ���� ũ�⸦ ������. ��δ� ���Ƿ� ������. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\file")
public class File_manage extends HttpServlet {
	public String []file_list=new String[5];
	public String[]filename=new String [5];
	private String[]file_extension=new String[5];
	database db= new database();//�����ͺ��̽� ���
	Account customer=new Account();
	
	public void select_file(String[] print_file,HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException
	{
		int i=0;
		//OptionŬ���� ����
		Option file_option=new Option();
		while(true)
		{
			//���� �̸��� ������� ���̻� list�� ���� ���̹Ƿ� while���� ����������.
			if(print_file[i]==null)
				break;
			//option��ü�� ���ϸ���Ʈ�� �Ű������� ���� ���� ����Ʈ�� �����Ѵ�. 
			file_option.fileName[i]=print_file[i];
			i++;//���� ���� �̸����� �̵���.
		}
		file_option.doGet(request, response);
	}
	
	/*
	 * �Ű������� ���� fileName�� ����� ���ϵ��� �����ͺ��̽��� �����ϴ� �޼ҵ��Դϴ�.
	 */
	private void store_file(String[] file_list) throws SQLException
	{
		String file_path="";
		for(int i=0;i<5;i++)
		{
			file_path="C:\\file\\"+file_list[i];
			if(!file_list[i].equals(" "))
			{
				int pdfPng=extractPagesAsImage(file_path, 300, "");//������ pdf������ ����� ����=>�μ� ������ �ʿ�
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
	 * pdf������ ����� ���ִ� �޼ҵ��Դϴ�.
	 * ���¼ҽ��� ���������� ����մϴ�.
	 */
	public static int extractPagesAsImage(String sourceFile, int resolution, String password) {
        boolean result = false;

        int pdfPageCn = 0;
        PDDocument pdfDoc = null;
        try {
            //PDF���� ���� ���
            pdfDoc = PDDocument.load(new File(sourceFile));
            pdfPageCn = pdfDoc.getNumberOfPages();

        } catch (IOException ioe) {
        	
        }
        return pdfPageCn;
    }
	
	/*
	 * PartŬ������ ��ü part���� ������ �̸��� �������� �޼ҵ��
	 * ���¼ҽ��� ���������� ����մϴ�.
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
		String button_value="";
		String print_file[]=new String[5];
		button_value=request.getParameter("select_button");
		//�ʱ�ȭ
		Part part1 = null;
		Part part2 = null;
		Part part3 = null;
		Part part4 = null;
		Part part5 = null;
		response.setContentType("text/html; charset=EUC-KR");
		int fileEmpty=0;
		if(button_value.equals("upload"))
		{
		//file.jsp���� ������ ������ ���� ���� ������ ���� �����´�.
		//���ܰ� �߻����� ��� ����ó���� �����޽��� ���
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
		
		//���� �κ��� ���ϸ��� �����Ѵ�.
		file_list[0]=getFilename(part1);
		file_list[1]=getFilename(part2);
		file_list[2]=getFilename(part3);
		file_list[3]=getFilename(part4);
		file_list[4]=getFilename(part5);
		
		//�� �κ��� ���ϵ��� ó���Ѵ�.
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
			else fileEmpty++;//� ���ϵ� ���õ��� �ʾҴ��� Ȯ���ϱ� ���� flag����
		}
		
		//� ���ϵ� ���õ��� �ʾ��� ��� ���� �߻���Ŵ
		//�����޽��� �����
		if(fileEmpty==5)
			try {
				errorMessage(response);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//���ܹ߻�
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errorMessage(e,response);
		}
		
		//��� ������ �������� ��� ���ܹ߻�
		//�����޼��� ���
		if((part1.getSize()==0)&&(part2.getSize()==0)&&(part3.getSize()==0)&&(part4.getSize()==0)&&(part5.getSize()==0))
			try {
				errorMessage(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorMessage(e,response);
			}
		else
		{
			//������ ���ϵ��� �����ͺ��̽��� �����ϴ� ���� 
			try {
				store_file(file_list);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("<script type='text/javascript'>");
			//�����԰� Ȩ���� �ΰ� ���� 
			out.print("location.href='fileConfirm.jsp';");//���ư� ������ ����
			out.println("</script>");
			out.close();
		}
		}//���� ȭ������ ���ư��� 
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
