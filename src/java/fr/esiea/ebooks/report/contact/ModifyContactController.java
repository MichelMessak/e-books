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
 * Controller fr modify a Contact
 * @author Micchel Messak
 */
public class ModifyContactController extends SimpleFormController {

    ContactsList contactList = ContactsList.getInstance();

    public ModifyContactController() {
        setCommandClass(Contact.class);
        setCommandName("modifyContactForm");
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

        //Modify the specific contact
        Contact contact = (Contact)command;

        for (int i=0;i<contactList.size();i++)
            if (contactList.getContact(i).getID().equals(contact.getID())){
                contactList.getContact(i).setFirstName(contact.getFirstName());
                contactList.getContact(i).setLastName(contact.getLastName());

                String birth = contact.getBirthday();
                String[] result = birth.split("-");
                contactList.getContact(i).setBirthday(new Date(Integer.parseInt(result[2])-1900, Integer.parseInt(result[1])-1, Integer.parseInt(result[0])));
                contactList.getContact(i).setEmail(contact.getEmail());
                contactList.getContact(i).setActif(Boolean.valueOf(contact.isActif()));
            }

            return new ModelAndView(getSuccessView(), getCommandName(), contact);

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {


        //Recreate the contact to visualize it
        Contact contact = new Contact();
        contact.setFirstName(request.getParameter("firstName"));
        contact.setLastName(request.getParameter("lastName"));
        contact.setID(request.getParameter("ID"));
        String birth = request.getParameter("birthday");
        String[] result = birth.split("-");

        Date date = new Date(Integer.parseInt(result[2])-1900, Integer.parseInt(result[1])-1, Integer.parseInt(result[0]));
        contact.setBirthday(date);
        contact.setEmail(request.getParameter("email"));
        contact.setActif(Boolean.valueOf(request.getParameter("actif")));
        request.setAttribute("title", "Modifier un contact");
        return contact;
    }

   
}
