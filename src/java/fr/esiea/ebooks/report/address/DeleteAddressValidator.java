package fr.esiea.ebooks.report.address;

import fr.esiea.ebooks.model.Address;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validato for deleting an address
 * @author Michel Messak
 */

public class DeleteAddressValidator implements Validator
{
    public void validate(Object target, Errors errors)
    {

      
    }

   

    public boolean supports(Class type)
    {
        return Address.class.isAssignableFrom(type);
    }

}
