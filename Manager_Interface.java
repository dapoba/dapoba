import java.sql.SQLException;
import java.util.Scanner;

public class Manager_Interface {
	database db= new database();
	public Cabinet cabinet;
	public void manage_printer()
	{
		//프린터의 상태를 가져옴.
		//큐에 있는 내용을 삭제할 수있음. 
	}
	public void manage_cabinet() throws SQLException
	{
		cabinet = new Cabinet();
		Scanner in=new Scanner(System.in);
		//UI로 인증번호를 입력하라고 함. 
		int veri_num;
		int printer_loc;
		System.out.println("캐비넷의 장소를 선택하세요!");
		printer_loc=in.nextInt();
		
		System.out.println("인증번호를 입력하세요.");
		veri_num=in.nextInt();
		//입력한 인증번호를 매개변수로 넣어 호출함. 
		cabinet.assign_cabinet(veri_num,printer_loc);
	}
	private void show_cabinet_num(int num) throws SQLException
	{
		//DB에서 캐비넷 상태를 가져옴. 
		//캐비넷의 상태를 출력함. 
		db.database_use();
		String sql="select cabinet_number from cabinet where Order_Account_Order_ID = "+ num;
		db.rs=db.stmt.executeQuery(sql);
		System.out.println("cabinet_number: " + db.rs.getInt("cabinet_number")); // Data get 
		
	}
}
