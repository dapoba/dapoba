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
       public String color="";//1이라면 컬러, 0이면 흑백임
       public String seperate=""; //기본은 1임. 
       public String cover="";//1이면 표지 뽑고 아니면 뽑지 않기 
       public String time="";//예약 시간 
       public String loc="";//뽑을 위치의 고유 번호
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
    
    //지정한 장소에서 프린터를 할 수 있는지의 여부에 따라 경고창을 띄운다.
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
    			out.println("alert('예약이 꽉 찼습니다. : \n');");
    			out.println("history.back();");
    			out.println("</script>");
    		}
    	else loc=location;//option 객체의 멤버변수에 location 저장함.*/
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
		//한시간전에 예약하지 않는다면 다음과 같은 메시지 출력
		//예약은 한시간 이상 전에 예약을 해야 성립된다. 
    	if(!check_time(time))
    	{
    		out.println("<script type='text/javascript'>");
			out.println("alert('한시간 전에 예약해주세요. : \n');");
			out.println("history.back();");
			out.println("</script>");
			return;
    	}
    	else
    	{
    		//표지를 추가한다는 옵션을 선택했을 경우
    		if(cover.equals("option_add_cover"))
    		{
    			int i=0;
    			while(fileName[i]!=null)
    				i++;
    			fileName[i]="cover.pdf";//서버에 지정된 커버파일의 파일명을 저장함
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
		//option선택하는 jsp파일 실행함. 
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
	    //지정된 장소가 가능한지 확인
		out.print(request.getParameter("option_place"));
		
	    String re="";
	    re=request.getParameter("bt");
	    out.print(re);
	    
	    if(request.getParameter("bt")==null)
	    	{out.print("ddddd");}//select_place(location, response);}
	    else out.print("df");
	    /*
		if(check_flag==true)
		//지정된 장소가 가능하다면 다음 option선택을 처리함.
			select_option(option,response);
		else 
		{
			out.println("<script type='text/javascript'>");
			out.println("alert('지정한 장소가 가능한지 체크해주세요 : \n');");
			out.println("history.back();");
			out.println("</script>");
			response.sendRedirect("option_select.jsp");
		}*/
	}
}
