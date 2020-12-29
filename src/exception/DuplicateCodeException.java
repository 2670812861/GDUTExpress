package exception;

/**
 * @Author: Àîè÷ºÀ
 * @Description:
 * @Date Created in 2020-12-10 20:38
 */
public class DuplicateCodeException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DuplicateCodeException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DuplicateCodeException(String message) {
        super(message);
    }
}
