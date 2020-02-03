package etk.exceptions;

import java.util.Map;

/**
 *
 * @author Pablo JS dos Santos
 */
public class CheckedException extends Exception implements StandardException {
    private String code;
    private Map<String, String> issues;

    public CheckedException(String code, String message, Throwable cause) {
        this(code, message, null, cause);
    }

    public CheckedException(String code, String message) {
        this(code, message, null, null);
    }

    public CheckedException(String code, String message, Map<String, String> issues) {
        this(code, message, issues, null);
    }

    public CheckedException(String code) {
        this(code, null, null, null);
    }

    public CheckedException(String code, String message, Map<String, String> issues, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.issues = issues;
    }

    @Override
    public String getExceptionCode() {
        return this.code;
    }

    @Override
    public Map<String, String> getIssues() {
        return this.issues;
    }
}
