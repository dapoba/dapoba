import java.sql.SQLException;
import java.util.Scanner;
public class File {

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
			file[i].store_file();
			i++;
		}
		
		return file_list;
	}
	private void store_file() throws SQLException
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
}
