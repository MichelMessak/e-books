package fr.esiea.ebooks.report.address;

import fr.esiea.ebooks.model.Address;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator of addinf an enterprise
 * @author Dispa Cécile
 */

public class AddAddressValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {
        
        ValidationUtils.rejectIfEmpty(errors, "number",
                "required.number", "Le numéro est requis.");
        ValidationUtils.rejectIfEmpty(errors, "street",
                "required.street", "La rue est requis.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode",
                "required.postalCode", "Le code postal est requis.");

        ValidationUtils.rejectIfEmpty(errors, "city",
                "required.city", "La ville est requis.");
        

    }

    public boolean supports(Class type)
    {
        return Address.class.isAssignableFrom(type);
    }
}
