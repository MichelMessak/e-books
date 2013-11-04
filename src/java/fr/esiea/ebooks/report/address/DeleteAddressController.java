package fr.esiea.ebooks.report.address;

import fr.esiea.ebooks.model.Address;
import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller dor deleting an address
 * @author Michel Messak
 */
public class DeleteAddressController extends SimpleFormController {

    ContactsList contactList = ContactsList.getInstance();

    public DeleteAddressController() {
        setCommandClass(Contact.class);
        setCommandName("deleteAddressForm");
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
       

            Address address = (Address)command;

            //Find the correct address for deleting it into the specific contact

         for (int i=0;i<contactList.size();i++)
             if(contactList.getContact(i).getID().equals(address.getIDContact()))
                 for (int j = 0; j<contactList.getContact(i).getAllAdress().size();j++)
                     if(contactList.getContact(i).getAdress(j).getNumber().equals(address.getNumber()))
                        if(contactList.getContact(i).getAdress(j).getStreet().equals(address.getStreet()))
                            if(contactList.getContact(i).getAdress(j).getPostalCode().equals(address.getPostalCode()))
                                if(contactList.getContact(i).getAdress(j).getCity().equals(address.getCity()))
                                    contactList.getContact(i).getAllAdress().remove(j);
                                
             
            return new ModelAndView(getSuccessView(), getCommandName(),address );
        
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {

        //Create the object address

        Address address = new Address();
        address.setNumber(request.getParameter("number"));
        address.setStreet(request.getParameter("street"));
        address.setPostalCode(request.getParameter("postalCode"));
        address.setCity(request.getParameter("city"));
        address.setIDContact(request.getParameter("IDContact"));

        request.setAttribute("title", "Supprimer une addresse");
        return address;
    }

   
}
