package fr.esiea.ebooks.report.generic;

import fr.esiea.ebooks.data.Report;
import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Excel view
 * @author Michel Messak
 */
public class ReportExcelController implements Controller {

        ContactsList contactList = ContactsList.getInstance();

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
        HttpSession se = request.getSession(false);
        Report report = (Report) se.getAttribute("report");
            response.setContentType("application/vnd.ms-excel");

        String task = null;

        if (report.getURL().contains("Contact"))
            task = "Contact";
        else
            task = "Address";


                            Contact contact = report.getContact();
            response.setHeader("Content-disposition", "inline; filename="+contact+"_"+task+".xls");
            HSSFWorkbook wb = new HSSFWorkbook();

            HSSFSheet sheet = wb.createSheet();
            int rownum = 0;

            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            Cell cell = null;


            for (int i = 0; i < report.getColumnCount(); i++) {
                cell = row.createCell(cellnum++);
                cell.setCellValue(report.getColumnNames()[i]);
            }

            if (task.equals("Contact")){

                            for (int i = 0;i<contactList.size();i++){

                                row = sheet.createRow(rownum++);
                                cellnum = 0;

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contactList.getContact(i).getFirstName());

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contactList.getContact(i).getLastName());

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contactList.getContact(i).getBirthday());

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contactList.getContact(i).getEmail());

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contactList.getContact(i).isActif());
                            }
                       }

                       else {
                            for (int i = 0;i<contact.getAllAdress().size();i++){

                                row = sheet.createRow(rownum++);
                                cellnum = 0;

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contact.getAdress(i).getNumber());

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contact.getAdress(i).getStreet());

                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contact.getAdress(i).getPostalCode());
                                
                                cell = row.createCell(cellnum++);
                                cell.setCellValue(contact.getAdress(i).getCity());

                            }

                       }
                      

/*
                                    while (rs.next()) {
                        row = sheet.createRow(rownum++);
                        cellnum = 0;
                        for (int i = 1; i <= report.getColumnCount(); i++) {
                            cell = row.createCell(cellnum++);

                            if (rs.getObject(i) == null) {
                                cell.setCellValue("");
                            } else {
                                cell.setCellValue(rs.getObject(i).toString());
                            }
                        }
                    }
               
            */
            wb.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }
}
