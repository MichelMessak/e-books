package fr.esiea.ebooks.report.contact;

import fr.esiea.ebooks.model.Contact;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validador for modify a contact
 * @author Dispa Cécile
 */

public class ModifyContactValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {

       

    }


    public boolean supports(Class type)
    {
        return Contact.class.isAssignableFrom(type);
    }

}
