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

import java.util.Date;
public class bill {
	public void issue_bill(String ID, int Coin, int usedCoin, int Milege, int usedMilege){
		Document document = new Document();
        //pdf���� ����
        final String 
          DESTINATION_PATH = "C:\\Users\\HyunA\\Desktop\\Bill_" + ID +".pdf"
        , FONT_PATH = "font/nanumgothic.TTF";
        
        final File FONTFILE = new File(FONT_PATH);
        
        //���� ��¥ ����
        long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String date = "2016-12-05 13:51:29";
		
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
                                "����Ͻ� ���� : " + String.valueOf(usedCoin) +"coin, ����Ͻ� ���ϸ��� : " +String.valueOf(usedMilege)+"p\n"
                              , new Font(unicode, 12)
                           )
                        );
            document.add(
                    new Paragraph
                    (
                         "�ܿ� ���� : " + String.valueOf(Coin) +"coin, �ܿ� ���ϸ��� : " +String.valueOf(Milege) + "p\n"
                       , new Font(unicode, 12)
                    )
                 );
        } catch(DocumentException de) {
            de.printStackTrace();
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }finally{
            document.close();
        }
	}
}
