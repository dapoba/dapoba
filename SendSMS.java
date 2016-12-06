import java.io.*;
public class SendSMS {
	public SendSMS(String number, String content){

        SMS sms = new SMS();

        sms.appversion("TEST/1.0");
        sms.charset("utf8");
        sms.setuser("dapoba", "thrhd123");		// coolsms id, password

        SmsMessagePdu pdu = new SmsMessagePdu();
        pdu.type = "SMS";
        pdu.destinationAddress = number;		// ������ ��ȣ
        pdu.scAddress = "01028294298"	;       // �߽��� ��ȣ          
        pdu.text = content;					    // ���� �޼��� ����
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
