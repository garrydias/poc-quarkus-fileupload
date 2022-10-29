package tech.calindra.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.runtime.util.StringUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class ApiException extends RuntimeException {
    private final String friendlyMessage;
    private final HttpResponseStatus httpStatus;

    public ApiException(String friendlyMessage, String message) {
        super(message);
        this.friendlyMessage = friendlyMessage;
        this.httpStatus = INTERNAL_SERVER_ERROR;
    }

    public ApiException(String friendlyMessage, String message, Throwable e) {
        this(friendlyMessage, message);
        initCause(e);
    }

    public ApiException(HttpResponseStatus httpStatus, String friendlyMessage) {
        super(friendlyMessage);
        this.friendlyMessage = friendlyMessage;
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpResponseStatus httpStatus, String friendlyMessage, String message) {
        super(message);
        this.friendlyMessage = friendlyMessage;
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpResponseStatus httpStatus, String friendlyMessage, String message, Throwable e) {
        super(message);
        initCause(e);
        this.friendlyMessage = friendlyMessage;
        this.httpStatus = httpStatus;
    }

    public static ApiException badRequest(String friendlyMessage) {
        return new ApiException(BAD_REQUEST, friendlyMessage, friendlyMessage);
    }

    public static ApiException notFound(String friendlyMessage) {
        return new ApiException(NOT_FOUND, friendlyMessage, friendlyMessage);
    }

    public static ApiException notFound(String message, String friendlyMessage) {
        return new ApiException(NOT_FOUND, hasText(message) ? message: friendlyMessage, friendlyMessage);
    }

    public static ApiException notFound(String message, String friendlyMessage, Throwable e) {
        return new ApiException(NOT_FOUND, hasText(message) ? message: friendlyMessage, friendlyMessage, e);
    }

    public static ApiException conflict(String message, String friendlyMessage, Throwable e) {
        return new ApiException(UNPROCESSABLE_ENTITY, hasText(message) ? message: friendlyMessage, friendlyMessage, e);
    }

    public static ApiException conflict(String message, String friendlyMessage) {
        return new ApiException(UNPROCESSABLE_ENTITY, hasText(message) ? message: friendlyMessage, friendlyMessage);
    }

    public static ApiException unprocessable(String message, String friendlyMessage, Throwable e) {
        return new ApiException(UNPROCESSABLE_ENTITY, hasText(message) ? message: friendlyMessage, friendlyMessage, e);
    }

    public static ApiException unprocessable(String message, String friendlyMessage) {
        return new ApiException(UNPROCESSABLE_ENTITY, hasText(message) ? message: friendlyMessage, friendlyMessage);
    }

    public static ApiException internalError(String message, String friendlyMessage) {
        return new ApiException(INTERNAL_SERVER_ERROR, hasText(message) ? message: friendlyMessage, friendlyMessage);
    }

    public static ApiException internalError(String message, String friendlyMessage, Throwable cause) {
        return new ApiException(INTERNAL_SERVER_ERROR, hasText(message) ? message: friendlyMessage, friendlyMessage, cause);
    }

    public static ApiException notImplemented(String friendlyMessage) {
        return new ApiException(NOT_IMPLEMENTED, friendlyMessage);
    }

    private static boolean hasText(String message) {
        return !StringUtil.isNullOrEmpty(message);
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }

    public HttpResponseStatus getHttpStatus() {
        return httpStatus;
    }
}
