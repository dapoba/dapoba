import java.io.*;
public class SendSMS {
	public SendSMS(int phoneNumber, String content){
        SMS sms = new SMS();

        sms.appversion("TEST/1.0");
        sms.charset("utf8");
        sms.setuser("dapaba", "thrhd123");	// coolsms 아이디, 비밀번호

        SmsMessagePdu pdu = new SmsMessagePdu();
        pdu.type = "SMS";
        pdu.destinationAddress = String.valueOf(phoneNumber);
        pdu.scAddress = "01012341234";       // 발신자 번호          
        pdu.text = content;			    // 보낼 메세지 내용
        sms.add(pdu);

        try {
        	sms.connect();
        	sms.send();
        	sms.disconnect();
        } 
        catch (IOException e) {
            System.out.println(e.toString());
        }
        sms.printr();
        sms.emptyall();
    }
}
