
class Cabinet {

	public int cabinet_number=0;
	public int cabinet_state[]=new int [100];
	public int input_verification_number;
	int assign_cabinet(int veri_num)
	{
		int cabinet_state[]=new int [100];
		int i=0;
		//DB에서 cabinet의 상태를 가져옴
		//DB에서 하나하나 검사하다가 0인 부분이 있다면 for문을 나오게 함. 
		for(i=0;i<100;i++)
		{
			if(cabinet_state[i]==0)
				{
					cabinet_number=i;
					//DB에 veri_num을 저장함. 
					//DB에서 그 번호의 상태를 1으로 바꿈. 
					break;
				}
		}
		if(i==100)
		{
			//모두 full이면 배정받을 사물함이 없음을 출력함-> 사물함을 관리자가 강제로 비우라는 메세지를 출력함. return -1하고 끝남. 
			System.out.println("빈 사물함이 없습니다. 사물함을 비우세요!");
			return -1;
		}
		//하나라도 empty라면 그냥 번호 빠른 순으로 배정하도록함. 
		//해당 번호를 return해줌. 
		return cabinet_number;
	}
}
