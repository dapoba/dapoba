import java.sql.SQLException;
import java.util.Scanner;

public class Manager_Interface {
	database db= new database();
	public Cabinet cabinet;
	public void manage_printer()
	{
		//�������� ���¸� ������.
		//ť�� �ִ� ������ ������ ������. 
	}
	public void manage_cabinet() throws SQLException
	{
		cabinet = new Cabinet();
		Scanner in=new Scanner(System.in);
		//UI�� ������ȣ�� �Է��϶�� ��. 
		int veri_num;
		int printer_loc;
		System.out.println("ĳ����� ��Ҹ� �����ϼ���!");
		printer_loc=in.nextInt();
		
		System.out.println("������ȣ�� �Է��ϼ���.");
		veri_num=in.nextInt();
		//�Է��� ������ȣ�� �Ű������� �־� ȣ����. 
		cabinet.assign_cabinet(veri_num,printer_loc);
	}
	private void show_cabinet_num(int num) throws SQLException
	{
		//DB���� ĳ��� ���¸� ������. 
		//ĳ����� ���¸� �����. 
		db.database_use();
		String sql="select cabinet_number from cabinet where Order_Account_Order_ID = "+ num;
		db.rs=db.stmt.executeQuery(sql);
		System.out.println("cabinet_number: " + db.rs.getInt("cabinet_number")); // Data get 
		
	}
}
