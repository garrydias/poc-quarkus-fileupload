package tech.calindra.commom;

import org.jboss.logging.Logger;
import tech.calindra.exception.ApiException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.text.MessageFormat;
import java.util.UUID;

import static java.lang.String.format;

@Provider
public class ApiErrorHandler implements ExceptionMapper<Exception> {

    public static final String API_EXEPTION_MSG = "Falha processando a requisicao";
    public static final String INTERNAL_ERROR_MSG = "Erro interno";
    @Inject
    Logger LOGGER;

    @Override
    public Response toResponse(Exception e) {

        if (e instanceof ApiException instance) {
            return toResponse(instance);
        }

        String errorId = UUID.randomUUID().toString();
        String message = MessageFormat.format("{0}: {1} ", INTERNAL_ERROR_MSG, errorId);
        LOGGER.error(message, e);

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ApiErrorVO(
                        message,
                        INTERNAL_ERROR_MSG,
                        Response.Status.INTERNAL_SERVER_ERROR.toString()
                ))
                .build();
    }

    public Response toResponse(ApiException e) {

        String message = format("%s: %s", API_EXEPTION_MSG, e.getMessage());

        LOGGER.error(message, e);

        return Response
                .status(e.getHttpStatus().code())
                .entity(new ApiErrorVO(
                        message,
                        e.getFriendlyMessage(),
                        e.getHttpStatus().toString()
                ))
                .build();
    }
}
