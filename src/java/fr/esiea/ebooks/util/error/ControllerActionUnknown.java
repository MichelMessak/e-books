package fr.esiea.ebooks.util.error;

/**
 *
 * @author Michel Messak
 *
 */

public class ControllerActionUnknown extends RuntimeException
{
    public ControllerActionUnknown(String message)
    {
        super(message);
    }
}
