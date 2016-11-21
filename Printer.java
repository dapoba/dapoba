package test;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUIFactory;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
/**
 * Servlet implementation class Printer
 */
@WebServlet("/Printer")
public class Printer extends HttpServlet {

	/**
	protected PrintService[] m_services;
	protected PrintService m_serviceDef = null;
	m_services = PrinterJob.lookupPrintSErvices();
	m_serviceDef=PrintServiceLookup.lookupDefaultPrintService();
	**/
	public static void main(String[] args)
	{
		new Printer().info();
		try{
			PrinterJob job=PrinterJob.getPrinterJob();
			PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
			job.print(aset);
		}
		catch(PrinterException ex)
		{
			
		}
	}
	public void info()
	{
		PrintService svc=PrintServiceLookup.lookupDefaultPrintService();
		info(svc);
		PrintService[] services=PrintServiceLookup.lookupPrintServices(null, null);
		/**
		 * 
		 * for(int i=0;i<services.length;i++)
		 * {
		 * PrintService service=services[i];
		 * info(service);
		 * }
		 */
		PrintRequestAttributeSet attrs=new HashPrintRequestAttributeSet();//null이 아니어야함. 
		attrs.add(MediaSizeName.TABLOID);
		attrs.add(MediaSizeName.TABLOID); // paper size  http://java.sun.com/j2se/1.5.0/docs/api/javax/print/attribute/standard/MediaSizeName.html
		attrs.add(Chromaticity.MONOCHROME); // 흑백인쇄 http://java.sun.com/j2se/1.5.0/docs/api/javax/print/attribute/standard/Chromaticity.html#MONOCHROME
		attrs.add(Sides.DUPLEX); // 양면인쇄  http://java.sun.com/j2se/1.5.0/docs/api/javax/print/attribute/standard/Sides.html#DUPLEX

		//PrintService selection = ServiceUI.printDialog(  null, 100, 100, services, svc, null, attrs);
		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
		DocPrintJob printJob = defaultService.createPrintJob();
	}
	void info(PrintService s)
	{
	 System.out.println(s.getName() + " :: "+s.getClass().getName());
	 PrintServiceAttributeSet set = s.getAttributes();
	 showAttributes(set);
	 ServiceUIFactory fac = s.getServiceUIFactory();
	 System.out.println("fac: "+fac); // null
    }

	void showAttributes(PrintServiceAttributeSet set)
	{
		System.out.println("set: "+set); // javax.print.attribute.AttributeSetUtilities$UnmodifiablePrintServiceAttributeSet@85028e19
		Attribute[] attrs = set.toArray();
		for (int i=0;i<attrs.length;i++)
		{
			Attribute attr = attrs[i];
			System.out.println("attr: "+attr.getName()+" "+attr.toString()+" "+attr.getCategory());
		}
		/*
		 *  attr: printer-is-accepting-jobs accepting-jobs
	        attr: printer-name Samsung CLP-300 Series
	        attr: color-supported supported
	        attr: queued-job-count 0
		 * */
	}
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Printer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
