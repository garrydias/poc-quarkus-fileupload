package tech.calindra.commom;

public record ApiErrorVO (
        String message,
        String friendlyMessage,
        String code
){}