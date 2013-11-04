package fr.esiea.ebooks.control.security;

import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Controller for the Authentication
 * @author Michel Messak
 */
public class AuthController implements Controller {

    ContactsList contactList = ContactsList.getInstance();

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        
        // Create a list of contact to test
for (int i = 0;i<contactList.size();i++)
        contactList.deleteContact(i);

                     contactList.addContact(new Contact("Messak", "Michel", "michel.messak@gmail.com", new Date (91, 05, 10), true));
                        contactList.getContact(0).setAddress("36 bis", "Rue Balard", "75015", "Paris");
                        contactList.getContact(0).setAddress("74 bis", "Avenue Maurice-Thorez", "94200", "Ivry-sur-Seine");
                    contactList.addContact(new Contact("Dispa", "Cécile", "dispas@et.esiea.fr", new Date (88, 05, 24), true));
                    contactList.addContact(new Contact("Béjuit", "Thomas", "bejuit@et.esiea.fr", new Date (91, 0, 18), true));
                        contactList.getContact(2).setAddress("18", "Rue de Verdun", "94260", "Fresnes");

                    // Redirect to the Contact Report Controller
                    ModelAndView mv = new ModelAndView("redirect:reportContact.do");
                    mv.addObject("isTaskSubmit", "Consult");

                    return mv;
                } 

}
