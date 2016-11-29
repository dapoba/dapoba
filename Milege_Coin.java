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
				if(bankname.equals("shinhan"))
					bankname = "��������";
				else if(bankname.equals("kukmin"))
					bankname = "��������";
				else if(bankname.equals("woori"))
					bankname = "�츮����";
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
				Credit_Card credit = new Credit_Card(Integer.parseInt(card[0]), card[1], card[2], Integer.parseInt(card[3]), chargedCoin * 2);
				if(credit.authorized_card() == 1){	//���� �۵�
					charge_coin();
					out.println("<script> alert('������ �Ϸ�Ǿ����ϴ�.');location.href='main.html';</script>");
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
			//order���� ������������ �Ѿ�� ��
			else if(name.equals("complete_order")){
				File = value;
				request.setAttribute("coin", Coin);
				request.setAttribute("milege", Milege);
				request.setAttribute("page", Page);	
				request.setAttribute("total", Page * 10);
				rd = getServletContext().getRequestDispatcher("/payment-info.jsp");
				rd.forward(request,response);
			}
			//payment-info.jsp���� ����� ���� ���� ���ϸ��� ���� �Է¹޴� ���
			//������ ����ϴ� ���
			else if(name.equals("useCoin")){
				if(use_coin(Integer.parseInt(value)));	//���� �Ϸ��� ���
					
				else{	//������ ������ ���
					out.println("<script> alert('�ܾ��� �����մϴ�. ������ �������ּ���');location.href='charge-coin.jsp';</script>");
					PrintWriter wr = response.getWriter();
					wr.flush();
					wr.close();
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
			}
			//������ ��� ����
			else if(name.equals("Bill")){
				if(value.equals("yes")){
					issue_bill();
				}
				sendSMS();
				out.println("<script> alert('������ �Ϸ��Ͽ����ϴ�.');location.href='payment-card.jsp';</script>");
				PrintWriter wr = response.getWriter();
				wr.flush();
				wr.close();
			}
			//main�Ǵ� ��ܹٿ��� "���� / ���ϸ���"�� ���� ���
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
		//��� 10point ����
		int milege = point;
		if(Milege >= milege){
			Milege -= milege;
			usedMilege = milege;
			database.database_use();
			//DB���� ���ϸ��� ����
			database.database_milege_coin(ID, "Milege", Milege);
			//DB���� ���ϸ��� ��볻�� ����
			database.database_history_insert(ID, "milege", Milege , File);
			return true;
		}
		else{//���ϸ����� ������ ���
			return false;
		}
	}
	public boolean use_coin(int coinNo)
	{
		//��� 10coin����
		int coin = coinNo * 10;
		if(coin == 0) return true;	//���ϸ����θ� ������ ���
		else if(Coin >= coin){
			Coin -= coin;
			usedCoin = coin;
			database.database_use();
			//DB���� ���� ����
			database.database_milege_coin(ID, "Coin", Coin);
			//DB���� ���� �������� ����
			database.database_history_insert(ID, "coin", coin , File);
			save_milege(coin);
			return true;
		}
		else{	//������ ������ ���
			return false;
		}
	}
	public void charge_coin()
	{
		Coin += chargedCoin;
		database.database_use();
		//DB���� ���� ����
		database.database_milege_coin(ID,  "Coin", Coin);	
		//DB���� ���� �������� �߰�
		database.database_history_insert(ID, "coin", chargedCoin , "NULL");
	}
	
	private void save_milege(int coin)
	{
		//10coin�� 2point����
		int milege = coin/5;
		Milege += milege;
		database.database_use();
		//DB���� ���ϸ��� ����
		database.database_milege_coin(ID, "Milege", Milege);
		//DB���� ���ϸ��� �������� �߰�
		database.database_history_insert(ID, "milege", milege , File);
	}
	public void issue_bill(){
		Document document = new Document();
        //pdf���� ����
        final String 
          DESTINATION_PATH = "C:\\Users\\Hyojin\\workspace\\Bill_" + ID +".pdf"
        , FONT_PATH = "font/nanumgothic.TTF";
        
        final File FONTFILE = new File(FONT_PATH);
        
        //���� ��¥ ����
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
                                "����Ͻ� ���� : " + String.valueOf(usedCoin) +", ����Ͻ� ���ϸ��� : " +String.valueOf(usedMilege)
                              , new Font(unicode, 12)
                           )
                        );
            document.add(
                    new Paragraph
                    (
                         "�ܿ� ���� : " + String.valueOf(Coin) +", �ܿ� ���ϸ��� : " +String.valueOf(Milege)
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
