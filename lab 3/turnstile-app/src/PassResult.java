/** Result of an attempt to pass through the turnstile. */
public final class PassResult {
    private final boolean allowed;
    private final String message;

    public PassResult(boolean allowed, String message) {
        this.allowed = allowed;
        this.message = message;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return (allowed ? "ACCESS GRANTED" : "ACCESS DENIED") + " - " + message;
    }
}
