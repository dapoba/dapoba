import java.io.*;
public class SendSMS {
	public SendSMS(String number, String content){

        SMS sms = new SMS();

        sms.appversion("TEST/1.0");
        sms.charset("utf8");
        sms.setuser("dapoba", "thrhd123");		// coolsms id, password

        SmsMessagePdu pdu = new SmsMessagePdu();
        pdu.type = "SMS";
        pdu.destinationAddress = number;		// 수신자 번호
        pdu.scAddress = "01028294298"	;       // 발신자 번호          
        pdu.text = content;					    // 보낼 메세지 내용
        sms.add(pdu);

        try {
            sms.connect();
            sms.send();
            sms.disconnect();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        sms.printr();
        sms.emptyall();
    }
}
