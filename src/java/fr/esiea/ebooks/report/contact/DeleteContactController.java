package fr.esiea.ebooks.report.contact;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller dor deleting an enterprise
 * @author Michel Messak
 */
public class DeleteContactController extends SimpleFormController {

    ContactsList contactList = ContactsList.getInstance();

    public DeleteContactController() {
        setCommandClass(Contact.class);
        setCommandName("deleteContactForm");
    }
    @Override
    public boolean isFormSubmission(HttpServletRequest request) {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("1".equals(isSubmit));
    }
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
       

            Contact contact = (Contact)command;
                    contactList.deleteContact(contactList.getContactPosition(contact));
                 
            return new ModelAndView(getSuccessView(), getCommandName(),contact );
        
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        Contact contact = new Contact();
        contact.setFirstName(request.getParameter("firstName"));
        contact.setLastName(request.getParameter("lastName"));

        String birth = request.getParameter("birthday");
        String[] result = birth.split("-");

        Date date = new Date(Integer.parseInt(result[2])-1900, Integer.parseInt(result[1])-1, Integer.parseInt(result[0]));
        contact.setBirthday(date);
        contact.setEmail(request.getParameter("email"));
        contact.setActif(Boolean.valueOf(request.getParameter("actif")));
        request.setAttribute("title", "Supprimer un contact");
        return contact;
    }

   
}
