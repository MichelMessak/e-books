package fr.esiea.ebooks.report.generic;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.esiea.ebooks.data.Report;
import fr.esiea.ebooks.model.ContactsList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * PDF view
 * @author Michel Messak
 */
public class ReportPDFController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ContactsList contactList = ContactsList.getInstance();

        try {

        HttpSession se = request.getSession(false);
        Report report = (Report) se.getAttribute("report");
        
        Document document = new Document();

			PdfWriter.getInstance(document, response.getOutputStream());

                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename=report.pdf");
			document.open();

                        PdfPTable header = new PdfPTable(report.getColumnCount());
                        PdfPTable table = new PdfPTable(report.getColumnCount());
                             table.setComplete(false);
                    for (int i = 0;i<report.getColumnCount();i++){
                        header.addCell(report.getColumnNames()[i]);
                    }
                        document.add(header);

                      try{
                          
                       /* for (int i = 0;i<contactList.get){

                            for (int i = 1;i<=report.getColumnCount();i++){
                                if (rs.getObject(i) == null)
                                    table.addCell("");
                                else
                                    table.addCell(rs.getObject(i).toString());
                            }
                        }*/

                      }
                              catch (Exception ex)
                                {
                                    throw ex;
                                }
                                
                      
                     table.setComplete(true);
                     document.add(table);
                     document.close();

        }
        catch(Exception e) {
        }
                     return null;
    }
}
