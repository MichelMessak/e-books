package fr.esiea.ebooks.util.error;

/**
 *
 * @author Michel Messak
 *
 */

public class DataMissingException extends RuntimeException
{
    public DataMissingException(String message)
    {
        super(message);
    }

    public DataMissingException(Throwable th)
    {
        super(th);
    }
}
