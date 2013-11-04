package fr.esiea.ebooks.report.generic;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.esiea.ebooks.data.Report;
import fr.esiea.ebooks.model.Contact;
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
        String task = null;

        if (report.getURL().contains("Contact"))
            task = "Contact";
        else
            task = "Address";
        
        Document document = new Document();

			PdfWriter.getInstance(document, response.getOutputStream());

                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition", "inline; filename"+task +"=.pdf");
			document.open();

                        PdfPTable header = new PdfPTable(report.getColumnCount());
                        PdfPTable table = new PdfPTable(report.getColumnCount());
                             table.setComplete(false);
                    for (int i = 0;i<report.getColumnCount();i++){
                        header.addCell(report.getColumnNames()[i]);
                    }
                        document.add(header);

                        if (task.equals("Contact")){

                            for (int i = 0;i<contactList.size();i++){
                                table.addCell(contactList.getContact(i).getFirstName());
                                table.addCell(contactList.getContact(i).getLastName());
                                table.addCell(contactList.getContact(i).getBirthday());
                                table.addCell(contactList.getContact(i).getEmail());
                                table.addCell(String.valueOf(contactList.getContact(i).isActif()));
                            }
                       }

                       else {
                            Contact contact = report.getContact();
                            for (int i = 0;i<contact.getAllAdress().size();i++){
                                table.addCell(contact.getAdress(i).getNumber());
                                table.addCell(contact.getAdress(i).getStreet());
                                table.addCell(contact.getAdress(i).getPostalCode());
                                table.addCell(contact.getAdress(i).getCity());
                            }

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
