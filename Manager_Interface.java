import java.util.Scanner;

public class Manager_Interface {

	public Cabinet cabinet;
	public void manage_printer()
	{
		//�������� ���¸� ������.
		//ť�� �ִ� ������ ������ ������. 
	}
	public void manage_cabinet()
	{
		cabinet = new Cabinet();
		Scanner in=new Scanner(System.in);
		//UI�� ������ȣ�� �Է��϶�� ��. 
		int veri_num;
		System.out.println("������ȣ�� �Է��ϼ���.");
		veri_num=in.nextInt();
		//�Է��� ������ȣ�� �Ű������� �־� ȣ����. 
		cabinet.assign_cabinet(veri_num);
	}
	private void show_cabinet_num(int num)
	{
		//DB���� ĳ��� ���¸� ������. 
		//ĳ����� ���¸� �����. 
		int i=0;
		for(i=0;i<100;i++)
		{
			System.out.println(cabinet.cabinet_state[i]);
		}
		
	}
}
