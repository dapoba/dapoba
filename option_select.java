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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Servlet implementation class File
 */
@WebServlet("/option_select")
//���ε��� ������ ��ο� ���� ũ�⸦ ������. ��δ� ���Ƿ� ������. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\file")
public class option_select extends HttpServlet {
	 public String color="";//1�̶�� �÷�, 0�̸� �����
     public String seperate=""; //�⺻�� 1��. 
     public String cover="";//1�̸� ǥ�� �̰� �ƴϸ� ���� �ʱ� 
     public String time="";//���� �ð� 
     public String loc="";//���� ��ġ�� ���� ��ȣ
     public String fileName[]=new String[5];
     public boolean check_flag=false;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public option_select() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    //������ ��ҿ��� �����͸� �� �� �ִ����� ���ο� ���� ���â�� ����.
    public void select_place(String location,HttpServletResponse response) throws IOException
    {
    	PrintWriter out = null;
		out = response.getWriter();
		check_flag=true;
		boolean avail=true;
		//out.print("ddddddd");
		if(avail==true)
		{
			String message="Available place";
			print_error(response, message);
		}
		else
		{
			String message="Not available place";
			print_error(response, message);
		}
    	//RunQueuing.Algorithm printer_queue=new RunQueuing.Algorithm();
    	/*if(RunQueuing.Algorithm.checkavailability(Integer.parseInt(location))!=true)
    		{
    			out.println("<script type='text/javascript'>");
    			out.println("alert('������ �� á���ϴ�. : \n');");
    			out.println("history.back();");
    			out.println("</script>");
    		}
    	else loc=location;//option ��ü�� ��������� location ������.*/
    }
    public void select_option(String option[],HttpServletResponse response) throws IOException
    {
    	Option send_option=new Option();
    	send_option.color=this.color;
    	send_option.seperate=this.seperate;
    	send_option.cover=this.cover;
    	send_option.time=this.time;
    	send_option.loc=this.loc;
    	long Currenttime=System.currentTimeMillis();
    	SimpleDateFormat dayTime=new SimpleDateFormat("yyyy-mm-dd kk:mm:ss");
    	String date=dayTime.format(new Date(Currenttime));
    	String tmp1[]=date.split(" ");
		String tmp2[]=tmp1[1].split(":");
		//�ѽð����� �������� �ʴ´ٸ� ������ ���� �޽��� ���
		//������ �ѽð� �̻� ���� ������ �ؾ� �����ȴ�. 
    	if(!check_time(time))
    	{
    		String message="The time is not available";
    		print_error(response,message);
    	}
    	else
    	{
    		//ǥ���� �߰��Ѵٴ� �ɼ��� �������� ���
    		if(cover.equals("1"))
    		{
    			int i=0;
    			while(fileName[i]!=null)
    				i++;
    			fileName[i]="cover.pdf";//������ ������ Ŀ�������� ���ϸ��� ������
    		}
    		//send_order.send_option(send_option);
    	}
    }
    public boolean check_time(String time)
    {
    	long Currenttime=System.currentTimeMillis();
    	SimpleDateFormat dayTime=new SimpleDateFormat("yyyy-mm-dd kk:mm:ss");
    	String date=dayTime.format(new Date(Currenttime));
    	String tmp1[]=date.split(" ");
		String tmp2[]=tmp1[1].split(":");
		if(Integer.parseInt(tmp2[0])>Integer.parseInt(time)-2)
			return false;
		else return true;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//option�����ϴ� jsp���� ������. 
		System.out.println(fileName[0]);
		response.sendRedirect("option_select.jsp");
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
	
		button_value=request.getParameter("check_button");
		
		System.out.print(button_value);
		String option[]=new String[5];
		PrintWriter out = null;
	    //�� �޾ƿ���
	   // option[0]=color;
	    //option[1]=seperate;
	    //option[2]=cover;
	    //option[3]=time;
	    //������ ��Ұ� �������� Ȯ��
		if(button_value.equals("check"))
			select_place(request.getParameter("option_place"),response);
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
		}
		if(button_value.equals("pay"))
		{
			//System.out.println(fileName[0]);
			database.database_use();
			try {
				database.stmt=database.conn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sql="Use dapoba_db"; 
			try {
				database.stmt.execute(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select filename, file_extension from dapoba_db.file where Account_id='execute';"; 		
			if(sql!=null)
				try {
					int i=0;
					database.rs=database.stmt.executeQuery(sql);
					while(database.rs.next())
					{
						
						fileName[i]=database.rs.getString("filename")+"."+database.rs.getString("file_extension");
						System.out.println(fileName[i]);		
						if(i>4)
							break;
						i++;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(fileName[0]==null)
				{
					String message="There is no selected file";
					print_error(response, message);
					return;
				}
			if(check_flag)
				//������ ��Ұ� �����ϴٸ� ���� option������ ó����.
			{
				color=request.getParameter("option_color_or_black");//1���, 2�̸� �÷�
				seperate=request.getParameter("option_split"); //�⺻�� 1��. 
				cover=request.getParameter("option_cover");//1�̸� ǥ�� �̰� 0�̸� ǥ������ 
				time=request.getParameter("select");//���� �ð� 
				select_option(option,response);
				database.database_option_insert(color, seperate, cover, time, "1");
			}
			else 
			{
				String message="Please check availability of the print";
				print_error(response, message);
			}
		}
	}
	
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
}
