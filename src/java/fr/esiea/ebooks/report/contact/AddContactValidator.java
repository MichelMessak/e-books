package fr.esiea.ebooks.report.contact;

import fr.esiea.ebooks.model.Contact;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator of adding a contact
 * @author Dispa Cécile
 */

public class AddContactValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {

        ValidationUtils.rejectIfEmpty(errors, "firstName",
                "required.firstName", "Le prénom est requis.");
        ValidationUtils.rejectIfEmpty(errors, "lastName",
                "required.lastName", "Le nom est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email",
                "required.email", "L'email est requis.");
        

    }

    public boolean supports(Class type)
    {
        return Contact.class.isAssignableFrom(type);
    }
}
