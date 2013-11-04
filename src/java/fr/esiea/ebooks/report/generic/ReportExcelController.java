package fr.esiea.ebooks.report.generic;

import fr.esiea.ebooks.data.Report;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
        HttpSession se = request.getSession(false);
        Report report = (Report) se.getAttribute("report");
            response.setContentType("application/vnd.ms-excel");

            response.setHeader("Content-disposition", "inline; filename=report.xls");
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
/*
                try {
                    conn = PoolConnection.getPoolConnection();
                    if (conn == null) {
                        throw new DBConnectionNotFound("Aucune connexion disponible");
                    }

                    rs = statement.executeQuery();

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
                } catch (Exception ex) {
                    throw ex;
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (conn != null) {
                        conn.close();
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
