package fr.esiea.ebooks.report.address;

import fr.esiea.ebooks.data.Report;
import fr.esiea.ebooks.model.Address;
import fr.esiea.ebooks.model.Contact;
import fr.esiea.ebooks.model.ContactsList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * Controller for adding an enterprise
 * @author Dispa Cécile
 */
public class AddAddressController extends SimpleFormController {

    ContactsList contactList = ContactsList.getInstance();
    
    public AddAddressController() {
        setCommandClass(Address.class);
        setCommandName("addAddressForm");
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

        Address address = (Address) command;

                    Report report = (Report) request.getSession().getAttribute("report");
                    for (int i=0;i<contactList.size();i++)
                        if(contactList.getContact(i).getID().equals(report.getID()))
                            contactList.getContact(i).setAddress(new Address(address.getNumber(), address.getStreet(),
                                    address.getPostalCode(), address.getCity(),report.getID()));

                    ModelAndView mv = new ModelAndView(getSuccessView(), getCommandName(), address);
             
            return mv;
        } catch (Exception ex) {
            errors.rejectValue("firstName", "addUser", ex.getMessage());
            return this.showForm(request, response, errors);
        }
    }
}
