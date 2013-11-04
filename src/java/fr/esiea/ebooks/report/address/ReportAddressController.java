package fr.esiea.ebooks.report.address;

import fr.esiea.ebooks.data.Report;
import fr.esiea.ebooks.util.error.ControllerActionUnknown;
import fr.esiea.ebooks.util.error.DataMissingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller to visualize all the address from a specific contact
 */
public class ReportAddressController implements Controller {


    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String ajaxRequest = request.getParameter("AjaxRequestChildTasks");

        if(ajaxRequest!=null)
        {
                   String user = request.getParameter("user");
                     String action = request.getParameter("action");

                     ModelAndView mv = new ModelAndView("askTask");
                           mv.addObject("correct", "correct");
                           mv.addObject("user", user);
                           mv.addObject("action", action);

                           return mv;
        }

        String[] fields = new String[]{"Numéro","Rue","Code Postal","Ville"};
        String[] widths = {"15%", "35%", "15%", "25%","5%","5%"};
        String[] aligns = {"center", "center","center", "Center", "center", "center"};
        Boolean[] visible = {  true,true, true, true, true, true};
        Boolean[] sort = {true, true, true,true,  false,  false};
        Boolean[] search = {true, true ,true, true, false, false};
//Extra column for the modify and delete option
        String[] colExtras = new String[]{
            "<form action=\"deleteAddress.form\" method=\"post\" name=\"{0}\">" +
                    "<input type=\"image\" id=\"{0}Delete\" src=\"images/deleteIcon.png\" style=\"width: 20px; display:none;\" title=\"Supprimer un utilisateur\"/>" +
                    "<input type=\"hidden\" name=\"number\" value=\"{1}\"/>" +
                    "<input type=\"hidden\" name=\"street\" value=\"{2}\"/>" +
                    "<input type=\"hidden\" name=\"postalCode\" value=\"{3}\"/>" +
                    "<input type=\"hidden\" name=\"city\" value=\"{4}\"/>" +
                    "<input type=\"hidden\" name=\"IDContact\" value=\"{5}\"/>" +
                    "<input type=\"hidden\" name=\"ID\" value=\"{6}\"/>" +
                    "<input type=\"hidden\" name=\"isSubmit\" value=\"false\"/>" +
            "</form>"+
            "<input id=\"deleteContact\" type=\"image\" src=\"images/deleteIcon.png\" onclick=\"sendRequest('reportContact.do', 'deleteAddress.form','{0}','delete')\" style=\"width: 20px;\" title=\"Supprimer un utilisateur\"/>",

             "<form action=\"modifyAddress.form\" method=\"post\" name=\"{0}\" id=\"{0}\">" +
                    "<input type=\"image\" id=\"{0}Modify\" src=\"images/editIcon.png\" style=\"width: 20px; display:none;\" title=\"Modifier un Contact\"/>" +
                     "<input type=\"hidden\" name=\"number\" value=\"{1}\"/>" +
                    "<input type=\"hidden\" name=\"street\" value=\"{2}\"/>" +
                    "<input type=\"hidden\" name=\"postalCode\" value=\"{3}\"/>" +
                    "<input type=\"hidden\" name=\"city\" value=\"{4}\"/>" +
                    "<input type=\"hidden\" name=\"IDContact\" value=\"{5}\"/>" +
                    "<input type=\"hidden\" name=\"ID\" value=\"{6}\"/>" +
                    "<input type=\"hidden\" name=\"isSubmit\" value=\"false\"/>" +
           "</form>"+
                    "<input type=\"image\" src=\"images/editIcon.png\" onclick=\"sendRequest('reportContact.do', 'modifyAddress.form' ,'{0}','modify')\" style=\"width: 20px;\" title=\"Modifier un Contact\"/>",

            
        };

        try {
            Report report = null;

            request.getAttributeNames();

            //If is the first calling for this task
            if (Report.isTaskCall(request)) {

                //Create the report 
                report = new Report(fields,request.getRequestURL().toString());
                report.setColumnAlignments(aligns);
                report.setColumnWidths(widths);
                report.setColumnExtras(colExtras);
                report.setColumnVisibles(visible);
                report.setColumnSortables(sort);
                report.setColumnSearchables(search);

                request.getSession().setAttribute("report", report);

                request.getAttribute("ID");
                report.ExecuteAddressForTotals(request,request.getParameter("ID"));

                ModelAndView mv = new ModelAndView("contact");
                mv.addObject("data", report);
                mv.addObject("ID", report.getID());
                mv.addObject("title", "Carnet d'adresse");

                //Return the report into the specific JPS
                return mv;
            }

            //If is the ajax call for refreshing the view
            else if (Report.isAjaxCall(request))
            {
                try
                {
                    report = (Report) request.getSession().getAttribute("report");
                    if (report == null) {
                        throw new DataMissingException("Les données n'ont pas pu être récupéré");
                    }
                    //Set the new view
                    report.configureDatatableParametersAddress(request);
                    ModelAndView mv = new ModelAndView("pager");
                    mv.addObject("data", report);
                    mv.addObject("ID", report.getID());
                    return mv;
                }
                catch(Exception ex)
                {
                    ModelAndView mv = new ModelAndView("pager");
                    return mv;
                }

            }
            else {
                throw new ControllerActionUnknown("Appel à la consultation des addresses inconnu");
            }
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            return mv;
        }
    }
}
