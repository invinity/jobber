package me.mattpitts.jobber.application.rest.x;

@lombok.Builder(builderClassName = "Builder")
@lombok.Data
public class ErrorDetail {
    private final String errorType;
    private final String message;

    public static class Builder {
        public Builder fromException(Throwable e) {
            return errorType(e.getClass().getSimpleName())
                .message(e.getMessage());
        }
    }
}
