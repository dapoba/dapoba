package test;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class cabinet
 */
@WebServlet("/cabinet")
public class cabinet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	database db= new database();
	public int cabinet_number=0;
	public int cabinet_state[]=new int [100];
	public int input_verification_number;
	int assign_cabinet(int veri_num, int printer_loc) throws SQLException
	{
		int i=0;
		//DB에서 cabinet의 상태를 가져옴
		//DB에서 하나하나 검사하다가 0인 부분이 있다면 for문을 나오게 함. 
		cabinet_number=i;
		db.database_use();
		String sql="select cabinet_number from cabinet_tbl where cabinet_state = 0 and printer_loc = "+printer_loc;
		db.rs=db.stmt.executeQuery(sql);
		cabinet_number=db.rs.getInt("cabinet_number");
		if(db.rs==null)
		{
			//모두 full이면 배정받을 사물함이 없음을 출력함-> 사물함을 관리자가 강제로 비우라는 메세지를 출력함. return -1하고 끝남. 
			System.out.println("빈 사물함이 없습니다. 사물함을 비우세요!");
			return -1;
		}
		//DB에 veri_num을 저장함. 
		//DB에서 그 번호의 상태를 1으로 바꿈. 
		sql="update cabinet set cabinet_state = "+1+" and Order_Account_Order_ID = "+veri_num+"where cabinet_number = "+ cabinet_number+"and Printer_loc"+printer_loc;
		db.stmt.executeQuery(sql);
		//하나라도 empty라면 그냥 번호 빠른 순으로 배정하도록함. 
		//해당 번호를 return해줌. 
		db.stmt.close(); 
		db.conn.close(); // close
		return cabinet_number;  
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cabinet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect("cabinet.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}
}
