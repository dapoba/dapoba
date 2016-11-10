
public class Account {
	protected String name;
	protected String birth;
	protected String e_mail;
	protected String address[];
	protected int phone_number;
	protected String password;
	public String ID;
	public String temporal_password;
	public void Login()
	{
		
	}
	public String issue_temporal_password()
	{
		String a = null;
		return a;
	}
	public boolean sign_in()
	{
		return true;
	}
	public String get_user_id()
	{
		String a=null;
		return a;
	}
	public int get_user_password()
	{
		return 1;
	}
	public Account get_customer_account()
	{
		Account a= new Account();
		return a;
	}
	public String find_id()
	{
		String a=null;
		return a;
	}
	public boolean check_login_info(String id, int password)
	{
		return true;
	}
	public int find_password()
	{
		return 1;
	}
	
}
