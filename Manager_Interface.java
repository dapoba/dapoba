import java.util.Scanner;

public class Manager_Interface {

	public Cabinet cabinet;
	public void manage_printer()
	{
		//프린터의 상태를 가져옴.
		//큐에 있는 내용을 삭제할 수있음. 
	}
	public void manage_cabinet()
	{
		cabinet = new Cabinet();
		Scanner in=new Scanner(System.in);
		//UI로 인증번호를 입력하라고 함. 
		int veri_num;
		System.out.println("인증번호를 입력하세요.");
		veri_num=in.nextInt();
		//입력한 인증번호를 매개변수로 넣어 호출함. 
		cabinet.assign_cabinet(veri_num);
	}
	private void show_cabinet_num(int num)
	{
		//DB에서 캐비넷 상태를 가져옴. 
		//캐비넷의 상태를 출력함. 
		int i=0;
		for(i=0;i<100;i++)
		{
			System.out.println(cabinet.cabinet_state[i]);
		}
		
	}
}
