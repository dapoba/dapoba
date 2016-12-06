

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterException;
import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.PrintSettings;
import com.qoppa.pdfPrint.PDFPrint;


/**
 * Servlet implementation class print_pdf
 */
@WebServlet("/print_pdf")
public class print_pdf extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static void main(String args[])
	{
		try{
			PDFPrint pdfPrint=new PDFPrint("C:\\Users\\HyunA\\Desktop\\cover.pdf",null);
			pdfPrint.printToDefaultPrinter(new PrintSettings());
			PDFPrint pdfPrint=new PDFPrint("C:\\Users\\HyunA\\Desktop\\Bill_test1.pdf",null);
			pdfPrint.printToDefaultPrinter(new PrintSettings());
			PDFPrint pdfPrint=new PDFPrint("C:\\Users\\HyunA\\Desktop\\4WeekNote.pdf",null);
			pdfPrint.printToDefaultPrinter(new PrintSettings());
			PDFPrint pdfPrint=new PDFPrint("C:\\Users\\HyunA\\Desktop\\5WeekNote.pdf",null);
			pdfPrint.printToDefaultPrinter(new PrintSettings());
			PDFPrint pdfPrint=new PDFPrint("C:\\Users\\HyunA\\Desktop\\9WeekNote.pdf",null);
			pdfPrint.printToDefaultPrinter(new PrintSettings());

		}
		catch(PDFException e)
		{
			e.printStackTrace();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public print_pdf() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
