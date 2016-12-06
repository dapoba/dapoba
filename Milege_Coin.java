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
//업로드할 파일의 경로와 제한 크기를 지정함. 경로는 임의로 지정함. 
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
		//장당 10point 차감
		int milege = point;
		if(Milege >= milege){
			Milege -= milege;
			usedMilege = milege;
			checkMilege = 1;
			return true;
		}
		else{//마일리지가 부족한 경우
			checkMilege = 0;
			return false;
		}
	}
	public boolean use_coin(int coinNo)
	{
		//장당 10coin차감
		int coin = coinNo;
		if(coin == 0) return true;	//마일리지로만 결제한 경우
		else if((Coin >= coin) && (checkMilege == 1)){
			Coin -= coin;
			usedCoin = coin;
			database db = new database();
			//DB에서 코인 차감
			db.database_milege_coin(ID, "Coin", Coin);
			String fileName = db.database_get_fileList();
			//DB에서 코인 차감내역 저장
			db.database_history_insert(ID, "coin", -usedCoin , fileName);
			
			//DB에서 마일리지 변경
			db.database_milege_coin(ID, "Mileage", Milege);
			//DB에서 마일리지 사용내역 저장
			db.database_history_insert(ID, "milege", -usedMilege , fileName);
			save_milege(coin);
			return true;
		}
		else{	//코인이 부족한 경우
			return false;
		}
	}
	public void charge_coin(String id)
	{
		Coin += chargedCoin;
		database db = new database();
		//DB에서 코인 충전
		db.database_milege_coin(id,  "Coin", Coin);	
		//DB에서 코인 충전내역 추가
		db.database_history_insert(id, "coin", chargedCoin , "NULL");
	}
	
	private void save_milege(int coin)
	{
		//10coin당 2point적립
		int milege = coin/5;
		Milege += milege;
		database db = new database();
		//DB에서 마일리지 변경
		db.database_milege_coin(ID, "Mileage", Milege);
		String fileName = db.database_get_fileList();
		//DB에서 마일리지 적립내역 추가
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
     * 지정한 파일을 서버에 업로드 하는 부분입니다.
     * store_file()함수를 호출하여 sql에 저장합니다.
     * 아래에 내용은 오픈소스를 참고하여 작성함을 명시합니다.- 참고서적 : 쾌도난마 JSP2.2 & Servlet 3.0따라하기
     */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fileName=request.getParameter("file_name");
		String DownloadPath="C:\\Users\\HyunA\\Desktop\\";//파일의 위치 지정
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
	 * 각 예외상황에 대해 알맞게 메시지를 출력하는 메소드입니다.
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
		//예외메시지를 자바스크립트를 이용해 alert메시지로 출력
		out.println("<script type='text/javascript'>");
		out.println("alert('에러 발생 : \n'"+e+"');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
	
	/*
	 * 파일이 존재하지 않을 경우의 에러메시지를 출력해주는 메소드입니다.
	 */
	protected void errorMessage(HttpServletResponse response) throws IOException
	{
		PrintWriter out = null;
		out = response.getWriter();
		out.println("<script type='text/javascript'>");
		out.println("alert('해당 파일이 존재하지 않습니다.');");
		out.println("history.back();");
		out.println("</script>");
		out.close();
	}
	
	/**
	 * file.jsp에서 받아온 파일을 업로드하기 위한 메소드입니다.(최대 5개 파일 업로드 가능)
	 * 각 예외처리에 대해서 오류메시지를 출력하는 기능이 포함되어 있습니다.
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
		//charge-coin.jsp에서 결제수단 선택에 대한 동작
		if(name.equals("pay_method")){
			if(value.equals("cash")){
				//무통장입금 선택 : 무통장 입금 페이지로 이동
				rd = getServletContext().getRequestDispatcher("/payment-cash.jsp");
				rd.forward(request, response);
			}
			else if(value.equals("card")){
				//카드결제 선택 : 카드결제 페이지로 이동
				rd = getServletContext().getRequestDispatcher("/payment-card.jsp");
				rd.forward(request, response);
			}
		}
		//charge-coin.jsp에서 충전할 coin수 선택에 대한 동작
		else if(name.equals("coin")){
			chargedCoin = Integer.parseInt(value);
		}
		//payment-cash.jsp에서 선택한 은행명에 대한 동작
		else if(name.equals("cashBank")){
			Cash cash = new Cash();
			cash.get_account(value);
			String bankname = cash.bank_name;
			if(value.equals("shinhan"))
				bankname = "신한은행";
			else if(value.equals("kukmin"))
				bankname = "국민은행";
			else if(value.equals("woori"))
				bankname = "우리은행";
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
		//payment-card.jsp에서 카드정보 및 잔액 확인
		//카드 번호일 경우
		else if(name.equals("cardNo")){
			card[0] = value;
		}
		//카드사일 경우
		else if(name.equals("cardBank")){
			card[1] = value;
		}
		//카드 유효기간일 경우
		else if(name.equals("cardDate")){
			card[2] = value;
			Credit_Card credit = new Credit_Card(card[0], card[1], card[2], Integer.parseInt(card[3]), chargedCoin * 2);
			if(credit.authorized_card() == 1){	//정상 작동
				charge_coin(ID);
				out.println("<script> alert('결제가 완료되었습니다.');location.href='main.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			else if(credit.authorized_card() == 2){ //잔액부족
				out.println("<script> alert('잔액이 부족합니다.');location.href='payment-card.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			else if(credit.authorized_card() == 3){	//정보 오기입
				out.println("<script> alert('카드 정보가 없습니다. 다시 입력해주세요.');location.href='payment-card.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
		}
		//카드 비밀번호일 경우
		else if(name.equals("cardPW")){
			card[3] = value;
		}
		/*//order이후 결제페이지로 넘어올 때
		else if(name.equals("complete_order")){
			fileList = request.getParameterValues("fileList");
			int Page = database.database_page_total();
			int point = Page * 10;	//흑백, 면당 1페이지 인쇄시
			if(Integer.parseInt(option.color) == 1)	//컬러인 경우 금액 5배증가
				point *= 5;
			point = Integer.parseInt(option.seperate);	//분할페이지에 따른 금액 차감
			request.setAttribute("coin", Coin);
			request.setAttribute("milege", Milege);
			request.setAttribute("page", Page);	
			request.setAttribute("total", point);
			rd = getServletContext().getRequestDispatcher("/payment-info.jsp");
			rd.forward(request,response);
		}*/
		//payment-info.jsp에서 사용할 코인 수와 마일리지 수를 입력받는 경우
		//코인을 사용하는 경우
		else if(name.equals("useCoin")){
			if(use_coin(Integer.parseInt(value)) && chk == 1);	//결제 완료한 경우
				
			else{	//코인이 부족한 경우
				out.println("<script> alert('잔액이 부족합니다. 코인을 충전해주세요');location.href='charge-coin.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
				chk = 0;
			}
		}
		//마일리지를 사용하는 경우
		else if(name.equals("useMilege")){
			if(!use_milege(Integer.parseInt(value))){	//마일리지가 부족한 경우
				out.println("<script> alert('마일리지가 부족합니다. 다시 입력해주세요');location.href='payment-info.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			else chk = 1;
		}
		//영수증 출력 여부
		else if(name.equals("Bill")){
			if(value.equals("yes") && chk == 1){
				//파일 리스트 맨 끝에 영수증 붙이기
				bill b = new bill();
				b.issue_bill("test1", Coin, usedCoin, Milege, usedMilege);
				database.database_bill_insert("Bill_test1", "pdf", "1", "execute");
			}
			//sendSMS();
			out.println("<script> alert('결제를 완료하였습니다.');location.href='main.jsp';</script>");
			RunQueuing rq = new RunQueuing();
			rq.Queuingjobs();
			PrintWriter wr = response.getWriter();
			wr.flush();
			wr.close();
		}
		//main또는 상단바에서 "코인 / 마일리지"를 누른 경우
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
		/*//관리자가 입금 확인을 한 경우
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
		//입금 확인을 누른 경우
		else if(name.equals("confirmPayment")){
			charge_coin(value);
			confirm_payment(value);
			out.println("<script> alert('입금 확인을 완료하였습니다.\n 코인을 충전하였습니다.');location.href='manage-payment.jsp';</script>");
			PrintWriter wr = response.getWriter();
			wr.flush();
			wr.close();
		}
	}
	}
}
