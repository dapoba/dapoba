import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement; 

class Cabinet {
	database db= new database();
	public int cabinet_number=0;
	public int cabinet_state[]=new int [100];
	public int input_verification_number;
	int assign_cabinet(int veri_num, int printer_loc) throws SQLException
	{
		int i=0;
		//DB���� cabinet�� ���¸� ������
		//DB���� �ϳ��ϳ� �˻��ϴٰ� 0�� �κ��� �ִٸ� for���� ������ ��. 
		cabinet_number=i;
		db.database_use();
		String sql="select cabinet_number from cabinet_tbl where cabinet_state = 0 and printer_loc = "+printer_loc;
		db.rs=db.stmt.executeQuery(sql);
		cabinet_number=db.rs.getInt("cabinet_number");
		if(db.rs==null)
		{
			//��� full�̸� �������� �繰���� ������ �����-> �繰���� �����ڰ� ������ ����� �޼����� �����. return -1�ϰ� ����. 
			System.out.println("�� �繰���� �����ϴ�. �繰���� ��켼��!");
			return -1;
		}
		//DB�� veri_num�� ������. 
		//DB���� �� ��ȣ�� ���¸� 1���� �ٲ�. 
		sql="update cabinet set cabinet_state = "+1+" and Order_Account_Order_ID = "+veri_num+"where cabinet_number = "+ cabinet_number+"and Printer_loc"+printer_loc;
		db.stmt.executeQuery(sql);
		//�ϳ��� empty��� �׳� ��ȣ ���� ������ �����ϵ�����. 
		//�ش� ��ȣ�� return����. 
		return cabinet_number;
	}
	
}
