public class Order extends Account{

	public File order_detail;
	
	//����Ʈ�� �ѱ�
	public void sent_result_file_to_printer(File file)
	{
		//ť�� ������
		//Ȯ���ڸ�, ���ϸ� ������
	}
	
	public void reservation_file()
	{
		
	}
	
	//���Ͽ��� �޾ƿ�, ������ �ѱ�
	public void send_option(Option option){
		order_detail = option.fileName;
		Milege_Coin mc = new Milege_Coin();
		mc.get_Milege_Coin(super.ID, String.valueOf(super.phone_number), option);
		RequestDispatcher rd;
		rd = getServeltContext().getRequestDispatcer("/payment-info.jsp");
		rd = forward(request, response);
	}
}