package dsa;

public class Account {
	private String name;
	private int birth;
	private String e_mail;
	private String address[];
	private int phone_number;
	private String password;
	private String ID;
	private String temporal_password;
	
	//name's getter and setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//birth's getter and setter
	public int getBirth() {
		return birth;
	}
	public void setBirth(int birth) {
		this.birth = birth;
	}
	//e_mail's getter and setter
	public String getE_mail() {
		return e_mail;
	}
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	//address's getter and setter
	public String[] getAddress() {
		return address;
	}
	public void setAddress(String[] address) {
		this.address = address;
	}
	//phone_number's getter and setter
	public int getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(int phone_number) {
		this.phone_number = phone_number;
	}
	//password's getter and setter
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	//iD's getter and setter
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	//temporal_password's getter and setter
	public String getTemporal_password() {
		return temporal_password;
	}
	public void setTemporal_password(String temporal_password) {
		this.temporal_password = temporal_password;
	}

}
