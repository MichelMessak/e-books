package fr.esiea.ebooks.report.contact;

import fr.esiea.ebooks.data.Report;
import fr.esiea.ebooks.util.error.ControllerActionUnknown;
import fr.esiea.ebooks.util.error.DataMissingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ReportContactController implements Controller {

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

        String[] fields = new String[]{"Prénom","Nom","Date de Naissance","Email","Actif"};
        String[] widths = {"15%", "15%", "15%", "30%", "10%","5%","5%","5%"};
        String[] aligns = {"center", "center", "center","center", "Center", "center", "center", "center"};
        Boolean[] visible = {true, true, true,true, true, true, true, true};
        Boolean[] sort = {true, true, true, true,true,  false,  false,  false};
        Boolean[] search = {true, true, true,true, true, false, false, false};

        String[] colExtras = new String[]{
            "<form action=\"deleteContact.form\" method=\"post\" name=\"{0}\">" +
                    "<input type=\"image\" id=\"{0}Delete\" src=\"images/deleteIcon.png\" style=\"width: 20px; display:none;\" title=\"Supprimer un utilisateur\"/>" +
                    "<input type=\"hidden\" name=\"firstName\" value=\"{0}\"/>" +
                    "<input type=\"hidden\" name=\"lastName\" value=\"{1}\"/>" +
                    "<input type=\"hidden\" name=\"birthday\" value=\"{2}\"/>" +
                    "<input type=\"hidden\" name=\"email\" value=\"{3}\"/>" +
                    "<input type=\"hidden\" name=\"actif\" value=\"{4}\"/>" +
                   // "<input type=\"hidden\" name=\"actif\" value=\"{5}\"/>" +
                    "<input type=\"hidden\" name=\"isSubmit\" value=\"false\"/>" +
            "</form>"+
            "<input id=\"deleteContact\" type=\"image\" src=\"images/deleteIcon.png\" onclick=\"sendRequest('reportContact.do', 'deleteContact.form','{0}','delete')\" style=\"width: 20px;\" title=\"Supprimer un utilisateur\"/>",
            
             "<form action=\"modifyContact.form\" method=\"post\" name=\"{0}\" id=\"{0}\">" +
                    "<input type=\"image\" id=\"{0}Modify\" src=\"images/editIcon.png\" style=\"width: 20px; display:none;\" title=\"Modifier un Contact\"/>" +
                    "<input type=\"hidden\" name=\"firstName\" value=\"{0}\"/>" +
                    "<input type=\"hidden\" name=\"lastName\" value=\"{1}\"/>" +
                    "<input type=\"hidden\" name=\"birthday\" value=\"{2}\"/>" +
                    "<input type=\"hidden\" name=\"email\" value=\"{3}\"/>" +
                    "<input type=\"hidden\" name=\"actif\" value=\"{4}\"/>" +
                    "<input type=\"hidden\" name=\"ID\" value=\"{5}\"/>" +
                    "<input type=\"hidden\" name=\"isSubmit\" value=\"false\"/>" +
           "</form>"+
                    "<input type=\"image\" src=\"images/editIcon.png\" onclick=\"sendRequest('reportContact.do', 'modifyContact.form' ,'{0}','modify')\" style=\"width: 20px;\" title=\"Modifier un Contact\"/>",
        
            "<form action=\"reportAddress.do?isFilterSubmit=Consult\" method=\"post\">" +
                    "<input type=\"hidden\" name=\"ID\" value=\"{5}\"/>" +
                    "<input type=\"image\" src=\"images/files_open_E.png\" style=\"width: 20px; height: 20px;\" title=\"Document\"/>" +
            "</form>"

        };

        try {
            Report report = null;

            request.getAttributeNames();

            if (Report.isFilterCall(request)) {

                report = new Report(fields, request.getRequestURL().toString());
                report.setColumnAlignments(aligns);
                report.setColumnWidths(widths);
                report.setColumnExtras(colExtras);
                report.setColumnVisibles(visible);
                report.setColumnSortables(sort);
                report.setColumnSearchables(search);

                 request.getSession().setAttribute("report", report);

                report.ExecuteQuery(request);

                ModelAndView mv = new ModelAndView("enterprise");
                mv.addObject("data", report);

                mv.addObject("title", "Carnet d'adresse");

                return mv;
            }

            else if (Report.isAjaxCall(request))
            {
                try
                {
                    report = (Report) request.getSession().getAttribute("report");
                    if (report == null) {
                        throw new DataMissingException("Les données n'ont pas pu être récupéré");
                    }

                    report.configureDatatableParametersDocuments(request);
                    ModelAndView mv = new ModelAndView("pager");
                    mv.addObject("data", report);
                    return mv;
                }
                catch(Exception ex)
                {
                    ModelAndView mv = new ModelAndView("pager");
                    return mv;
                }

            }
            else {
                throw new ControllerActionUnknown("Appel à la consultation des documents inconnu");
            }
        } catch (Exception ex) {
            ModelAndView mv = new ModelAndView("error");
            mv.addObject("exception", ex);
            return mv;
        }
    }
}
