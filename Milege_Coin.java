import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.FontSelector;
import com.lowagie.text.pdf.PdfWriter;

public class Milege_Coin extends HttpServlet{
	public int Milege;
	public int Coin;
	private int chargedCoin;
	private int usedCoin;
	private int usedMilege;
	public int Page;
	public String ID;
	public String phoneNum;
	public String File;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		response.setContentType("text/html; charset = utf-8");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd;
		Enumeration names = request.getParameterNames();
		String card[] =new String[4];
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
				if(bankname.equals("shinhan"))
					bankname = "신한은행";
				else if(bankname.equals("kukmin"))
					bankname = "국민은행";
				else if(bankname.equals("woori"))
					bankname = "우리은행";
				String holder = cash.account_holder_name;
				String account = cash.account_number;
				String money = String.valueOf(chargedCoin * 2);
		
				request.setAttribute("bank",bankname);
				request.setAttribute("holder",holder);
				request.setAttribute("accountNo", account);
				request.setAttribute("money", money);
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
				Credit_Card credit = new Credit_Card(Integer.parseInt(card[0]), card[1], card[2], Integer.parseInt(card[3]), chargedCoin * 2);
				if(credit.authorized_card() == 1){	//정상 작동
					charge_coin();
					out.println("<script> alert('결제가 완료되었습니다.');location.href='main.html';</script>");
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
			//order이후 결제페이지로 넘어올 때
			else if(name.equals("complete_order")){
				File = value;
				request.setAttribute("coin", Coin);
				request.setAttribute("milege", Milege);
				request.setAttribute("page", Page);	
				request.setAttribute("total", Page * 10);
				rd = getServletContext().getRequestDispatcher("/payment-info.jsp");
				rd.forward(request,response);
			}
			//payment-info.jsp에서 사용할 코인 수와 마일리지 수를 입력받는 경우
			//코인을 사용하는 경우
			else if(name.equals("useCoin")){
				if(use_coin(Integer.parseInt(value)));	//결제 완료한 경우
					
				else{	//코인이 부족한 경우
					out.println("<script> alert('잔액이 부족합니다. 코인을 충전해주세요');location.href='charge-coin.jsp';</script>");
					PrintWriter wr = response.getWriter();
					wr.flush();
					wr.close();
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
			}
			//영수증 출력 여부
			else if(name.equals("Bill")){
				if(value.equals("yes")){
					issue_bill();
				}
				sendSMS();
				out.println("<script> alert('결제를 완료하였습니다.');location.href='payment-card.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			//main또는 상단바에서 "코인 / 마일리지"를 누른 경우
			else if(name.equals("pushedCoinMilege")){
				String history[][] = new String[10][4];
				history = print_history();
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
		}
	}
	
	public void get_Milege_Coin(String id){
		database.database_use();
		int[] milege_coin = database.database_get_milege_coin(id);
		Milege = milege_coin[1];
		Coin = milege_coin[0];
		ID = id;
	}
	
	public boolean use_milege(int point)
	{
		//장당 10point 차감
		int milege = point;
		if(Milege >= milege){
			Milege -= milege;
			usedMilege = milege;
			database.database_use();
			//DB에서 마일리지 변경
			database.database_milege_coin(ID, "Milege", Milege);
			//DB에서 마일리지 사용내역 저장
			database.database_history_insert(ID, "milege", Milege , File);
			return true;
		}
		else{//마일리지가 부족한 경우
			return false;
		}
	}
	public boolean use_coin(int coinNo)
	{
		//장당 10coin차감
		int coin = coinNo * 10;
		if(coin == 0) return true;	//마일리지로만 결제한 경우
		else if(Coin >= coin){
			Coin -= coin;
			usedCoin = coin;
			database.database_use();
			//DB에서 코인 차감
			database.database_milege_coin(ID, "Coin", Coin);
			//DB에서 코인 차감내역 저장
			database.database_history_insert(ID, "coin", coin , File);
			save_milege(coin);
			return true;
		}
		else{	//코인이 부족한 경우
			return false;
		}
	}
	public void charge_coin()
	{
		Coin += chargedCoin;
		database.database_use();
		//DB에서 코인 충전
		database.database_milege_coin(ID,  "Coin", Coin);	
		//DB에서 코인 충전내역 추가
		database.database_history_insert(ID, "coin", chargedCoin , "NULL");
	}
	
	private void save_milege(int coin)
	{
		//10coin당 2point적립
		int milege = coin/5;
		Milege += milege;
		database.database_use();
		//DB에서 마일리지 변경
		database.database_milege_coin(ID, "Milege", Milege);
		//DB에서 마일리지 적립내역 추가
		database.database_history_insert(ID, "milege", milege , File);
	}
	public void issue_bill(){
		Document document = new Document();
        //pdf파일 생성
        final String 
          DESTINATION_PATH = "C:\\Users\\Hyojin\\workspace\\Bill_" + ID +".pdf"
        , FONT_PATH = "font/nanumgothic.TTF";
        
        final File FONTFILE = new File(FONT_PATH);
        
        //결제 날짜 저장
        long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String date = dayTime.format(new Date(time));
		
        try
        {
            PdfWriter writer = PdfWriter.getInstance
                             (
                               document
                             , new FileOutputStream(DESTINATION_PATH)
                             );
            
            document.addAuthor(ID);
            document.addTitle("issue_bill");
            
            document.open();
            writer.getAcroForm().setNeedAppearances(true);
            
             BaseFont unicode = BaseFont.createFont
                                (
                                    FONTFILE.getAbsolutePath()
                                  , BaseFont.IDENTITY_H
                                  , BaseFont.EMBEDDED
                                );
             FontSelector fs = new FontSelector();
             fs.addFont(new Font(unicode));
            
            document.add(
                            new Paragraph
                            (  
                                date
                              , new Font(unicode, 12)
                            ) 
                        );
            document.add(
                           new Paragraph
                           (
                                "사용하신 코인 : " + String.valueOf(usedCoin) +", 사용하신 마일리지 : " +String.valueOf(usedMilege)
                              , new Font(unicode, 12)
                           )
                        );
            document.add(
                    new Paragraph
                    (
                         "잔여 코인 : " + String.valueOf(Coin) +", 잔여 마일리지 : " +String.valueOf(Milege)
                       , new Font(unicode, 12)
                    )
                 );
        } catch(DocumentException de) {
            de.printStackTrace();
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
	}
	private void sendSMS(){
		Verification_number number = new Verification_number();
		number.issue_verification_number();
		String bill = "Reservation complete\n"+"remaining coin : " +String.valueOf(Coin)+ "mileage : " + String.valueOf(Milege)+"\n" + "varification num : " + String.valueOf(number.verification_num);
		SendSMS s = new SendSMS(phoneNum, bill);
	}
	private String[][] print_history(){
		String[][] history = new String[10][4];
		database.database_use();
		history = database.database_history_print(ID);
		return history;
	}
}
