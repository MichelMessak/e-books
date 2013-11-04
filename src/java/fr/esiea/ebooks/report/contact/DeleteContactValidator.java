package fr.esiea.ebooks.report.contact;

import fr.esiea.ebooks.model.Contact;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validato for deleting a contact
 * @author Michel Messak
 */

public class DeleteContactValidator implements Validator
{
    public void validate(Object target, Errors errors)
    {

      
    }

   

    public boolean supports(Class type)
    {
        return Contact.class.isAssignableFrom(type);
    }

}
