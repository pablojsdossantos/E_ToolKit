package etk.jdbc.exceptions;

import etk.exceptions.UncheckedException;

/**
 *
 * @author Pablo JS dos Santos
 */
public class PersistenceException extends UncheckedException {
    public PersistenceException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
