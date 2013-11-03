package fr.esiea.ebooks.report.contact;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller for adding an enterprise
 * @author Dispa Cécile
 */
public class AddContactController extends SimpleFormController {

    ContactsList contactList = ContactsList.getInstance();
    public AddContactController() {
        setCommandClass(Contact.class);
        setCommandName("addContactForm");
    }

    @Override
    public boolean isFormSubmission(HttpServletRequest request) {
        Object isSubmit = request.getParameter("isSubmit");
        if (isSubmit == null) {
            return super.isFormSubmission(request);
        }
        return ("true".equals(isSubmit));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        try {

        Contact contact = (Contact) command;
        String birth = request.getParameter("birthday");
        String[] result = birth.split("/");
        contact.setActif(true);
        contact.createID();
        contact.setBirthday(new Date(Integer.parseInt(result[0])-1900, Integer.parseInt(result[1])-1, Integer.parseInt(result[2])));
        contactList.addContact(contact);

            return new ModelAndView(getSuccessView(), getCommandName(), contact);
        } catch (Exception ex) {
            errors.rejectValue("firstName", "addUser", ex.getMessage());
            return this.showForm(request, response, errors);
        }
    }
}
