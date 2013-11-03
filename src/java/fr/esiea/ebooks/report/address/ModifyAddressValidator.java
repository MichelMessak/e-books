package fr.esiea.ebooks.report.address;

import fr.esiea.ebooks.model.Address;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validador for modify an user
 * @author Dispa Cécile
 */

public class ModifyAddressValidator implements Validator
{

    public void validate(Object target, Errors errors)
    {

       

    }


    public boolean supports(Class type)
    {
        return Address.class.isAssignableFrom(type);
    }

}
