
class Cabinet {

	public int cabinet_number=0;
	public int cabinet_state[]=new int [100];
	public int input_verification_number;
	int assign_cabinet(int veri_num)
	{
		int cabinet_state[]=new int [100];
		int i=0;
		//DB���� cabinet�� ���¸� ������
		//DB���� �ϳ��ϳ� �˻��ϴٰ� 0�� �κ��� �ִٸ� for���� ������ ��. 
		for(i=0;i<100;i++)
		{
			if(cabinet_state[i]==0)
				{
					cabinet_number=i;
					//DB�� veri_num�� ������. 
					//DB���� �� ��ȣ�� ���¸� 1���� �ٲ�. 
					break;
				}
		}
		if(i==100)
		{
			//��� full�̸� �������� �繰���� ������ �����-> �繰���� �����ڰ� ������ ����� �޼����� �����. return -1�ϰ� ����. 
			System.out.println("�� �繰���� �����ϴ�. �繰���� ��켼��!");
			return -1;
		}
		//�ϳ��� empty��� �׳� ��ȣ ���� ������ �����ϵ�����. 
		//�ش� ��ȣ�� return����. 
		return cabinet_number;
	}
}
