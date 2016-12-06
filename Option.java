import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
/**
 * Servlet implementation class Option
 */
@WebServlet("/Option")
public class Option extends HttpServlet {
       public String color="";//1�̶�� �÷�, 0�̸� �����
       public String seperate=""; //�⺻�� 1��. 
       public String cover="";//1�̸� ǥ�� �̰� �ƴϸ� ���� �ʱ� 
       public String time="";//���� �ð� 
       public String loc="";//���� ��ġ�� ���� ��ȣ
       public String fileName[]=new String[5];
       public boolean check_flag=false;
       //Order send_order=new Order();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Option() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //������ ��ҿ��� �����͸� �� �� �ִ����� ���ο� ���� ���â�� ����.
    public void select_place(String location,HttpServletResponse response) throws IOException
    {
    	PrintWriter out = null;
		out = response.getWriter();
		check_flag=true;
		out.println("df");
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
    	PrintWriter out = null;
		out = response.getWriter();
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
    		out.println("<script type='text/javascript'>");
			out.println("alert('�ѽð� ���� �������ּ���. : \n');");
			out.println("history.back();");
			out.println("</script>");
			return;
    	}
    	else
    	{
    		//ǥ���� �߰��Ѵٴ� �ɼ��� �������� ���
    		if(cover.equals("option_add_cover"))
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
		response.sendRedirect("option_select.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("EUC-KR");
		response.setContentType("text/html; charset=EUC-KR");
		PrintWriter out = null;
		out = response.getWriter();
		String location="";
	    //������ ��Ұ� �������� Ȯ��
		out.print(request.getParameter("option_place"));
		
	    String re="";
	    re=request.getParameter("bt");
	    out.print(re);
	    
	    if(request.getParameter("bt")==null)
	    	{out.print("ddddd");}//select_place(location, response);}
	    else out.print("df");
	    /*
		if(check_flag==true)
		//������ ��Ұ� �����ϴٸ� ���� option������ ó����.
			select_option(option,response);
		else 
		{
			out.println("<script type='text/javascript'>");
			out.println("alert('������ ��Ұ� �������� üũ���ּ��� : \n');");
			out.println("history.back();");
			out.println("</script>");
			response.sendRedirect("option_select.jsp");
		}*/
	}
}
