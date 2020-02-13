package etk.exceptions;

import java.util.concurrent.CompletionException;

/**
 *
 * @author Pablo JS dos Santos
 */
public class ExceptionUtils {
    public static boolean isCausedBy(Throwable throwable, Class cause) {
        return lookup(throwable, cause) != null;
    }

    public static <T> T lookup(Throwable throwable, Class<T> clazz) {
        if (throwable == null || clazz.isInstance(throwable)) {
            return (T) throwable;
        }

        return lookup(throwable.getCause(), clazz);
    }

    public static void throwAgain(Throwable throwable) {
        throwable = skipCompletionException(throwable);
        RuntimeException runtimeException = lookup(throwable, RuntimeException.class);
        throw runtimeException;
    }

    public static Throwable skipCompletionException(Throwable throwable) {
        if (throwable instanceof CompletionException) {
            CompletionException completionException = (CompletionException) throwable;
            return completionException.getCause();
        }

        return throwable;
    }
}
