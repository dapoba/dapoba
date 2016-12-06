import java.util.Random;
public class Verification_number {
public int verification_num;
	
	public void issue_verification_number(){
		//while(true){
			Random ran = new Random();
			verification_num = ran.nextInt(10000);
			
			//if(check_duplication(verificationNum))	//중복없음
				//break;
		//}
	}
	
	public boolean check_duplication(int num)
	{
		boolean flag;
		database.database_use();
		//DB에서 인증번호 중복확인
		flag = database.database_check_verification(num);
		return flag;
	}
}
