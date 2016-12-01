public class Order extends Account{

	public File order_detail;
	
	//프린트로 넘김
	public void sent_result_file_to_printer(File file)
	{
		//큐로 보내기
		//확장자명, 파일명 보내기
	}
	
	public void reservation_file()
	{
		
	}
	
	//파일에서 받아옴, 결제로 넘김
	public void send_option(Option option){
		order_detail = option.fileName;
		Milege_Coin mc = new Milege_Coin();
		mc.get_Milege_Coin(super.ID, String.valueOf(super.phone_number), option);
		RequestDispatcher rd;
		rd = getServeltContext().getRequestDispatcer("/payment-info.jsp");
		rd = forward(request, response);
	}
}