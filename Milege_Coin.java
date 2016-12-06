import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * Servlet implementation class File
 */
@WebServlet("/Milege_Coin")
//���ε��� ������ ��ο� ���� ũ�⸦ ������. ��δ� ���Ƿ� ������. 
@MultipartConfig(maxFileSize=1024*1024*2, location="C:\\Users\\HyunA\\Desktop")
public class Milege_Coin extends HttpServlet {
	public int Milege;
	public int Coin;
	private int chargedCoin;
	private int usedCoin;
	private int usedMilege;
	private int checkMilege;
	public String ID = "test1";
	public String Name = "dapoba";
	public String phoneNum = "01028294298";
	
	public void get_Milege_Coin(){
		database.database_use();
		int[] milege_coin = database.database_get_milege_coin(ID);
		Milege = milege_coin[1];
		Coin = milege_coin[0];
	}
	
	public boolean use_milege(int point)
	{
		get_Milege_Coin();
		//��� 10point ����
		int milege = point;
		if(Milege >= milege){
			Milege -= milege;
			usedMilege = milege;
			checkMilege = 1;
			return true;
		}
		else{//���ϸ����� ������ ���
			checkMilege = 0;
			return false;
		}
	}
	public boolean use_coin(int coinNo)
	{
		//��� 10coin����
		int coin = coinNo;
		if(coin == 0) return true;	//���ϸ����θ� ������ ���
		else if((Coin >= coin) && (checkMilege == 1)){
			Coin -= coin;
			usedCoin = coin;
			database db = new database();
			//DB���� ���� ����
			db.database_milege_coin(ID, "Coin", Coin);
			String fileName = db.database_get_fileList();
			//DB���� ���� �������� ����
			db.database_history_insert(ID, "coin", -usedCoin , fileName);
			
			//DB���� ���ϸ��� ����
			db.database_milege_coin(ID, "Mileage", Milege);
			//DB���� ���ϸ��� ��볻�� ����
			db.database_history_insert(ID, "milege", -usedMilege , fileName);
			save_milege(coin);
			return true;
		}
		else{	//������ ������ ���
			return false;
		}
	}
	public void charge_coin(String id)
	{
		Coin += chargedCoin;
		database db = new database();
		//DB���� ���� ����
		db.database_milege_coin(id,  "Coin", Coin);	
		//DB���� ���� �������� �߰�
		db.database_history_insert(id, "coin", chargedCoin , "NULL");
	}
	
	private void save_milege(int coin)
	{
		//10coin�� 2point����
		int milege = coin/5;
		Milege += milege;
		database db = new database();
		//DB���� ���ϸ��� ����
		db.database_milege_coin(ID, "Mileage", Milege);
		String fileName = db.database_get_fileList();
		//DB���� ���ϸ��� �������� �߰�
		db.database_history_insert(ID, "milege", milege , fileName);
	}
	public void sendSMS(){
		Verification_number number = new Verification_number();
		number.issue_verification_number();
		String bill = "Reservation complete\n"+"remaining coin : " +String.valueOf(Coin)+ "c, mileage : " + String.valueOf(Milege)+"p\n" + "varification num : " + String.valueOf(number.verification_num);
		SendSMS s = new SendSMS(phoneNum, bill);
		String sentNum = "verification nuber : " + String.valueOf(number.verification_num);
		SendSMS ss = new SendSMS("01092038078", sentNum);
	}
	private String[][] print_history(){
		String[][] history = new String[10][4];
		history = database.database_history_print(ID);
		return history;
	}
	private String[][] print_payment(){
		String[][] paymentList = new String[10][3];
		paymentList = database.database_payment_list_print();
		return paymentList;
	}
	private void confirm_payment(String id){
		database.database_payment_confirm(id);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Milege_Coin() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    /*
     * ������ ������ ������ ���ε� �ϴ� �κ��Դϴ�.
     * store_file()�Լ��� ȣ���Ͽ� sql�� �����մϴ�.
     * �Ʒ��� ������ ���¼ҽ��� �����Ͽ� �ۼ����� ����մϴ�.- ������ : �赵���� JSP2.2 & Servlet 3.0�����ϱ�
     */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName=request.getParameter("file_name");
		String DownloadPath="C:\\Users\\HyunA\\Desktop\\";//������ ��ġ ����
		String sFilePath=DownloadPath+fileName;//String sFilePath= sDownloadPath+customer.get_id()+"\\"+fileName;
		byte b[]= new byte[4096];
		FileInputStream in = new FileInputStream(sFilePath);
		String sMimeType= getServletContext().getMimeType(sFilePath);
		if(sMimeType==null)
			sMimeType="application/octet-stream";
		response.setContentType(sMimeType);
		String sEncoding=new String(fileName.getBytes("euc-kr"),"8859_1");
		response.setHeader("Content-Disposition", "attachment; filename= "+ sEncoding);
		ServletOutputStream out=response.getOutputStream();
		int numRead;
		while((numRead=in.read(b,0,b.length))!=-1)
			out.write(b,0,numRead);
		out.flush();
		out.close();
		in.close();
	}

	/*
	 * �� ���ܻ�Ȳ�� ���� �˸°� �޽����� ����ϴ� �޼ҵ��Դϴ�.
	 */
	protected void errorMessage(Exception e,HttpServletResponse response)
	{
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//���ܸ޽����� �ڹٽ�ũ��Ʈ�� �̿��� alert�޽����� ���
		out.println("<script type='text/javascript'>");
		out.println("alert('���� �߻� : \n'"+e+"');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
	
	/*
	 * ������ �������� ���� ����� �����޽����� ������ִ� �޼ҵ��Դϴ�.
	 */
	protected void errorMessage(HttpServletResponse response) throws IOException
	{
		PrintWriter out = null;
		out = response.getWriter();
		out.println("<script type='text/javascript'>");
		out.println("alert('�ش� ������ �������� �ʽ��ϴ�.');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
	
	/**
	 * file.jsp���� �޾ƿ� ������ ���ε��ϱ� ���� �޼ҵ��Դϴ�.(�ִ� 5�� ���� ���ε� ����)
	 * �� ����ó���� ���ؼ� �����޽����� ����ϴ� ����� ���ԵǾ� �ֽ��ϴ�.
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {response.setContentType("text/html; charset = utf-8");
	PrintWriter out = response.getWriter();
	RequestDispatcher rd;
	Enumeration names = request.getParameterNames();
	String card[] =new String[4];
	int chk = 0;
	while(names.hasMoreElements()){
		String name = (String)names.nextElement();
		String value = request.getParameter(name);
		//charge-coin.jsp���� �������� ���ÿ� ���� ����
		if(name.equals("pay_method")){
			if(value.equals("cash")){
				//�������Ա� ���� : ������ �Ա� �������� �̵�
				rd = getServletContext().getRequestDispatcher("/payment-cash.jsp");
				rd.forward(request, response);
			}
			else if(value.equals("card")){
				//ī����� ���� : ī����� �������� �̵�
				rd = getServletContext().getRequestDispatcher("/payment-card.jsp");
				rd.forward(request, response);
			}
		}
		//charge-coin.jsp���� ������ coin�� ���ÿ� ���� ����
		else if(name.equals("coin")){
			chargedCoin = Integer.parseInt(value);
		}
		//payment-cash.jsp���� ������ ����� ���� ����
		else if(name.equals("cashBank")){
			Cash cash = new Cash();
			cash.get_account(value);
			String bankname = cash.bank_name;
			if(value.equals("shinhan"))
				bankname = "��������";
			else if(value.equals("kukmin"))
				bankname = "��������";
			else if(value.equals("woori"))
				bankname = "�츮����";
			String holder = cash.account_holder_name;
			String account = cash.account_number;
			String money = String.valueOf(chargedCoin * 2);
	
			request.setAttribute("bank",bankname);
			request.setAttribute("holder",holder);
			request.setAttribute("accountNo", account);
			request.setAttribute("money", money);
			database.database_payment_insert(ID, "dapoba", money);
			rd = getServletContext().getRequestDispatcher("/payment-cash-info.jsp");
			rd.forward(request,response);
		}
		//payment-card.jsp���� ī������ �� �ܾ� Ȯ��
		//ī�� ��ȣ�� ���
		else if(name.equals("cardNo")){
			card[0] = value;
		}
		//ī����� ���
		else if(name.equals("cardBank")){
			card[1] = value;
		}
		//ī�� ��ȿ�Ⱓ�� ���
		else if(name.equals("cardDate")){
			card[2] = value;
			Credit_Card credit = new Credit_Card(card[0], card[1], card[2], Integer.parseInt(card[3]), chargedCoin * 2);
			if(credit.authorized_card() == 1){	//���� �۵�
				charge_coin(ID);
				out.println("<script> alert('������ �Ϸ�Ǿ����ϴ�.');location.href='main.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			else if(credit.authorized_card() == 2){ //�ܾ׺���
				out.println("<script> alert('�ܾ��� �����մϴ�.');location.href='payment-card.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			else if(credit.authorized_card() == 3){	//���� ������
				out.println("<script> alert('ī�� ������ �����ϴ�. �ٽ� �Է����ּ���.');location.href='payment-card.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
		}
		//ī�� ��й�ȣ�� ���
		else if(name.equals("cardPW")){
			card[3] = value;
		}
		/*//order���� ������������ �Ѿ�� ��
		else if(name.equals("complete_order")){
			fileList = request.getParameterValues("fileList");
			int Page = database.database_page_total();
			int point = Page * 10;	//���, ��� 1������ �μ��
			if(Integer.parseInt(option.color) == 1)	//�÷��� ��� �ݾ� 5������
				point *= 5;
			point = Integer.parseInt(option.seperate);	//������������ ���� �ݾ� ����
			request.setAttribute("coin", Coin);
			request.setAttribute("milege", Milege);
			request.setAttribute("page", Page);	
			request.setAttribute("total", point);
			rd = getServletContext().getRequestDispatcher("/payment-info.jsp");
			rd.forward(request,response);
		}*/
		//payment-info.jsp���� ����� ���� ���� ���ϸ��� ���� �Է¹޴� ���
		//������ ����ϴ� ���
		else if(name.equals("useCoin")){
			if(use_coin(Integer.parseInt(value)) && chk == 1);	//���� �Ϸ��� ���
				
			else{	//������ ������ ���
				out.println("<script> alert('�ܾ��� �����մϴ�. ������ �������ּ���');location.href='charge-coin.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
				chk = 0;
			}
		}
		//���ϸ����� ����ϴ� ���
		else if(name.equals("useMilege")){
			if(!use_milege(Integer.parseInt(value))){	//���ϸ����� ������ ���
				out.println("<script> alert('���ϸ����� �����մϴ�. �ٽ� �Է����ּ���');location.href='payment-info.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			else chk = 1;
		}
		//������ ��� ����
		else if(name.equals("Bill")){
			if(value.equals("yes") && chk == 1){
				//���� ����Ʈ �� ���� ������ ���̱�
				bill b = new bill();
				b.issue_bill("test1", Coin, usedCoin, Milege, usedMilege);
				database.database_bill_insert("Bill_test1", "pdf", "1", "execute");
			}
			//sendSMS();
			out.println("<script> alert('������ �Ϸ��Ͽ����ϴ�.');location.href='main.jsp';</script>");
			RunQueuing rq = new RunQueuing();
			rq.Queuingjobs();
			PrintWriter wr = response.getWriter();
			wr.flush();
			wr.close();
		}
		//main�Ǵ� ��ܹٿ��� "���� / ���ϸ���"�� ���� ���
		else if(name.equals("pushedCoinMilege")){
			System.out.println("......");
			String history[][] = new String[10][4];
			//history = print_history();
			Vector date = new Vector();
			Vector coinHistory = new Vector();
			Vector mileageHistory = new Vector();
			Vector fileName = new Vector();
			for(int i = 0; i < 10; i++){
				if((history[i][0] != null)&&(history[i][1] != null)&&(history[i][2] != null)&&(history[i][3] != null))
				date.add(history[i][0]);
				coinHistory.add(history[i][1]);
				mileageHistory.add(history[i][2]);
				fileName.add(history[i][3]);
			}
			request.setAttribute("date",date);
			request.setAttribute("coin",coinHistory);
			request.setAttribute("milege", mileageHistory);
			request.setAttribute("file", fileName);
			rd = getServletContext().getRequestDispatcher("/coin-mileage.jsp");
			rd.forward(request,response);
		}
		/*//�����ڰ� �Ա� Ȯ���� �� ���
		else if(name.equals("pushedPayment")){
			String payList[][] = new String[10][4];
			payList = print_payment();
			Vector id = new Vector();
			Vector Name = new Vector();
			Vector money = new Vector();
			Vector confirm = new Vector();
			for(int i = 0; i < 10; i++){
				if((payList[i][0] != null)&&(payList[i][1] != null)&&(payList[i][2] != null)&&(payList[i][3] != null))
				id.add(payList[i][0]);
				Name.add(payList[i][1]);
				confirm.add(payList[i][2]);
				money.add(payList[i][3]);
			}
			request.setAttribute("id",id);
			request.setAttribute("name",Name);
			request.setAttribute("confirm", confirm);
			request.setAttribute("money", money);
			rd = getServletContext().getRequestDispatcher("/coin-mileage.jsp");
			rd.forward(request,response);
		}*/
		//�Ա� Ȯ���� ���� ���
		else if(name.equals("confirmPayment")){
			charge_coin(value);
			confirm_payment(value);
			out.println("<script> alert('�Ա� Ȯ���� �Ϸ��Ͽ����ϴ�.\n ������ �����Ͽ����ϴ�.');location.href='manage-payment.jsp';</script>");
			PrintWriter wr = response.getWriter();
			wr.flush();
			wr.close();
		}
	}
	}
}
