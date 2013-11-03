package fr.esiea.ebooks.util.error;

/**
 * @author Dispa Cécile
 */

public class DBConnectionNotFound extends RuntimeException
{
    public DBConnectionNotFound(String message)
    {
        super(message);
    }

    public DBConnectionNotFound(Throwable th)
    {
        super(th);
    }
}
