package fr.esiea.ebooks.report.address;

import fr.esiea.ebooks.model.Address;
import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller fr modify an User
 * @author Micchel Messak
 */
public class ModifyAddressController extends SimpleFormController {

    ContactsList contactList = ContactsList.getInstance();

    public ModifyAddressController() {
        setCommandClass(Address.class);
        setCommandName("modifyAddressForm");
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
        
        Address address = (Address)command;

        for (int i=0;i<contactList.size();i++)
             if(contactList.getContact(i).getID().equals(address.getIDContact()))
                 for (int j = 0; j<contactList.getContact(i).getAllAdress().size();j++)
                     if(contactList.getContact(i).getAdress(j).getID().equals(address.getID())){
                         contactList.getContact(i).getAdress(j).setNumber(address.getNumber());
                            contactList.getContact(i).getAdress(j).setStreet(address.getStreet());
                                contactList.getContact(i).getAdress(j).setPostalCode(address.getPostalCode());
                                    contactList.getContact(i).getAdress(j).setCity(address.getCity());
                     }

            return new ModelAndView(getSuccessView(), getCommandName(), address);

    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

         Address address = new Address();
        address.setNumber(request.getParameter("number"));
        address.setStreet(request.getParameter("street"));
        address.setPostalCode(request.getParameter("postalCode"));
        address.setCity(request.getParameter("city"));
        address.setIDContact(request.getParameter("IDContact"));
        address.setID(request.getParameter("ID"));

        request.setAttribute("title", "Supprimer une addresse");
        return address;
    }

   
}
