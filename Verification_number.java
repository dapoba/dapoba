import java.util.Random;
public class Verification_number {
public int verification_num;
	
	public void issue_verification_number(){
		//while(true){
			Random ran = new Random();
			verification_num = ran.nextInt(10000);
			
			//if(check_duplication(verificationNum))	//�ߺ�����
				//break;
		//}
	}
	
	public boolean check_duplication(int num)
	{
		boolean flag;
		database.database_use();
		//DB���� ������ȣ �ߺ�Ȯ��
		flag = database.database_check_verification(num);
		return flag;
	}
}
