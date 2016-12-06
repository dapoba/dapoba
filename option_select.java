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
//업로드할 파일의 경로와 제한 크기를 지정함. 경로는 임의로 지정함. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\file")
public class option_select extends HttpServlet {
	 public String color="";//1이라면 컬러, 0이면 흑백임
     public String seperate=""; //기본은 1임. 
     public String cover="";//1이면 표지 뽑고 아니면 뽑지 않기 
     public String time="";//예약 시간 
     public String loc="";//뽑을 위치의 고유 번호
     public String fileName[]=new String[5];
     public boolean check_flag=false;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public option_select() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    //지정한 장소에서 프린터를 할 수 있는지의 여부에 따라 경고창을 띄운다.
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
    			out.println("alert('예약이 꽉 찼습니다. : \n');");
    			out.println("history.back();");
    			out.println("</script>");
    		}
    	else loc=location;//option 객체의 멤버변수에 location 저장함.*/
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
		//한시간전에 예약하지 않는다면 다음과 같은 메시지 출력
		//예약은 한시간 이상 전에 예약을 해야 성립된다. 
    	if(!check_time(time))
    	{
    		String message="The time is not available";
    		print_error(response,message);
    	}
    	else
    	{
    		//표지를 추가한다는 옵션을 선택했을 경우
    		if(cover.equals("1"))
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
		System.out.println(fileName[0]);
		response.sendRedirect("option_select.jsp");
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
	
		button_value=request.getParameter("check_button");
		
		System.out.print(button_value);
		String option[]=new String[5];
		PrintWriter out = null;
	    //값 받아오기
	   // option[0]=color;
	    //option[1]=seperate;
	    //option[2]=cover;
	    //option[3]=time;
	    //지정된 장소가 가능한지 확인
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
				//지정된 장소가 가능하다면 다음 option선택을 처리함.
			{
				color=request.getParameter("option_color_or_black");//1흑백, 2이면 컬러
				seperate=request.getParameter("option_split"); //기본은 1임. 
				cover=request.getParameter("option_cover");//1이면 표지 뽑고 0이면 표지없음 
				time=request.getParameter("select");//예약 시간 
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
