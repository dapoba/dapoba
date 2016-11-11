
public class Printer {

	public File result_file;
	public int verification_number;
	public File get_order_file[]=new File[20];
	public int[] get_printer_states(Option option)
	{
		int a[]=new int [20];
		return a;
	}
	public boolean check_availability(Printer printer)
	{
		return true;
	}
	public boolean print_bill(String bill)
	{
		//UI로 영수증 출력함. 
		//형식 갖출것. 
		System.out.println(bill);
		return true;
	}
	private int waiting_queue[]=new int [100];
	private boolean print_file(Order order)
	{
		return true;
	}
}
