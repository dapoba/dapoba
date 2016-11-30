package test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Finishings;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.NumberUp;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.SheetCollate;
import javax.print.attribute.standard.Sides;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
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
 * �ش� Ŭ������ �ܺ� Jar������ Ȱ���߽��ϴ�.
 */

@WebServlet("/print")

public class Print extends HttpServlet {

	String print_file_path;
	public void print_pdf(Option option){
		try{
			int i=0;
			while(option.fileName[i]!=null)
			{
				print_file_path="C\\file\\"+option.fileName[i];
				PDFPrint pdfPrint=new PDFPrint(print_file_path,null);//������ ������ �μⰡ �ȴ�. 
				pdfPrint.print("IP�ּ�", new PrintSettings());//���� IP�� ����� �� ����. 
				i++;
			}
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
    public Print() {
        super();
    }


	/*
	 * jpg,png���� �̹������� �μ⸦ �����ϴ� �޼ҵ�
	 * ���¼ҽ��� �̿������� ����մϴ�.
	 */
	public void print_image(Option option) throws IOException {
	    // These are values we'll set from the command-line arguments
	    boolean query = false;
	    String printerName = "EPSON444744 (L365 Series)";
	    String inputFileName = "";
	    String outputFileName = null;
	    String outputFileType = null;
	    PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
	    String args[]=new String[4];
	    //attributes.add(new NumberUp(2));
	    // Loop through the arguments
	    //�ɼǼ����ϴ� �κ�
	    int j=0;
	    while(option.fileName[j]!=null)
	    {
	    for (int i = 0; i < args.length; i++) {
	      if (args[i].equals("-q"))
	        query = true; // Is this is query?
	      else if (args[i].equals("-p")) // Specific printer name
	        printerName = args[++i];
	      else if (args[i].equals("-i")) // The file to print
	        inputFileName = args[++i];
	      else if (args[i].equals("-ps")) { // Print it to this file
	        // Sun's Java 1.4 implementation only supports PostScript
	        // output. Other implementations might offer PDF, for example.
	        outputFileName = args[++i];
	        outputFileType = "application/postscript";
	      }
	      // The rest of the arguments represent common printing attributes
	      else if (args[i].equals("-color")) // Request a color printer
	        attributes.add(Chromaticity.COLOR);
	      else if (args[i].equals("-landscape")) // Request landscape mode
	        attributes.add(OrientationRequested.LANDSCAPE);
	      else if (args[i].equals("-letter")) // US Letter-size paper
	        attributes.add(MediaSizeName.NA_LETTER);
	      else if (args[i].equals("-a4")) // European A4 paper
	        attributes.add(MediaSizeName.ISO_A4);
	      else if (args[i].equals("-staple")) // Request stapling
	        attributes.add(Finishings.STAPLE);
	      else if (args[i].equals("-collate")) // Collate multiple copies
	        attributes.add(SheetCollate.COLLATED);
	      else if (args[i].equals("-duplex")) // Request 2-sided
	        attributes.add(Sides.DUPLEX);
	      else if (args[i].equals("-2")) // 2 pages to a sheet
	        attributes.add(new NumberUp(2));
	      else if (args[i].equals("-copies")) // how many copies
	        attributes.add(new Copies(Integer.parseInt(args[++i])));
	      else {
	        System.out.println("Unknown argument: " + args[i]);
	        System.exit(1);
	      }
	    }

	    if (query) {
	      if (printerName == null)
	        queryServices(attributes);
	      
	    } 
	    else if (outputFileName != null)
	      printToFile(outputFileName, outputFileType, inputFileName, attributes);
	    
	    else
	      print(printerName, inputFileName, attributes);
	    j++;
	    }
	  }

	  public static void queryServices(PrintRequestAttributeSet attributes) {
	    //���������� ��� ����Ʈ ���� ã�´�.
	    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attributes);

	    for (int i = 0; i < services.length; i++) {
	      // ����Ʈ ���� �̸�. 
	      System.out.print(services[i].getName());
	      DocFlavor[] flavors = services[i].getSupportedDocFlavors();
	      for (int j = 0; j < flavors.length; j++) {
	        String repclass = flavors[j].getRepresentationClassName();
	        if (!repclass.equals("java.io.InputStream"))
	          continue;
	        System.out.println("\t" + flavors[j].getMimeType());
	      }
	    }
	  }

	  public static void print(String printerName, String filename, PrintRequestAttributeSet attributes)
	      throws IOException {
	    PrintService service = getNamedPrinter(printerName, attributes);
	    if (service == null) {
	       return;
	    }
	    // Print the file to that printer. See method definition below
	    printToService(service, filename, attributes);
	  }

	  // Print to an output file instead of a printer
	  public static void printToFile(String outputFileName, String outputFileType,
	      String inputFileName, PrintRequestAttributeSet attributes) throws IOException {

	    StreamPrintServiceFactory[] factories = StreamPrintServiceFactory
	        .lookupStreamPrintServiceFactories(null, outputFileType);

	    // output type �� ���� ��� ����
	    if (factories.length == 0) 
	      return;
	    
	    // output file�� ����
	    FileOutputStream out = new FileOutputStream(outputFileName);
	    StreamPrintService service = factories[0].getPrintService(out);
	    printToService(service, inputFileName, attributes);
	    out.close();
	  }
	  
	  public static void printToService(PrintService service, String filename,
	      PrintRequestAttributeSet attributes) throws IOException {
	    // Figure out what type of file we're printing
	    DocFlavor flavor = getFlavorFromFilename(filename);
	    // Open the file
	    InputStream in = new FileInputStream(filename);
	    // Create a Doc object to print from the file and flavor.
	    Doc doc = new SimpleDoc(in, flavor, null);
	    // Create a print job from the service
	    DocPrintJob job = service.createPrintJob();

	    // printer job�� ���Ͽ� ������ �߻����� ��츦 ó���Ѵ�.
	    //�޽����� ����Ѵ�.
	    job.addPrintJobListener(new PrintJobAdapter() {
	      public void printJobCompleted(PrintJobEvent e) {
	        System.exit(0);
	      }

	      public void printDataTransferCompleted(PrintJobEvent e) {
	        System.out.println("Document transfered to printer");
	      }

	      public void printJobRequiresAttention(PrintJobEvent e) {
	        System.out.println("Print job requires attention");
	        System.out.println("Check printer: out of paper?");
	      }

	      public void printJobFailed(PrintJobEvent e) {
	        System.out.println("Print job failed");
	        System.exit(1);
	      }
	    });

	    // ������ ������ ����Ѵ�.
	    try {
	      job.print(doc, attributes);
	    } catch (PrintException e) {
	      System.out.println(e);
	      System.exit(1);
	    }
	  }
	  
	  public static PrintService getNamedPrinter(String name, PrintRequestAttributeSet attrs) {
	    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attrs);
	    if (services.length > 0) {
	      if (name == null)
	        return services[0];
	      else {
	        for (int i = 0; i < services.length; i++) {
	          if (services[i].getName().equals(name))
	            return services[i];
	        }
	      }
	    }
	    return null;
	  }

	  // �� Ȯ���ڿ� ���� DocFlavor�� �����Ѵ�.
	  //���⼭�� jpg, png, txt�� Ȯ���ڸ��� �����Ѵ�
	  //pdf�� �������� �����Ƿ� pdf������ ��쿣 print_pdf()�Լ��� �̿��Ͽ� �μ��Ͽ����Ѵ�.
	  public static DocFlavor getFlavorFromFilename(String filename) {
	    String extension = filename.substring(filename.lastIndexOf('.') + 1);
	    extension = extension.toLowerCase();
	    if (extension.equals("gif"))
	      return DocFlavor.INPUT_STREAM.GIF;
	    else if (extension.equals("jpeg"))
	      return DocFlavor.INPUT_STREAM.JPEG;
	    else if (extension.equals("jpg"))
	      return DocFlavor.INPUT_STREAM.JPEG;
	    else if (extension.equals("png"))
	      return DocFlavor.INPUT_STREAM.PNG;
	    else
	      return DocFlavor.INPUT_STREAM.AUTOSENSE;
	  }

}