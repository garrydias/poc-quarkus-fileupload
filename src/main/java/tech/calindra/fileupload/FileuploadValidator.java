package tech.calindra.fileupload;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import tech.calindra.exception.ApiException;

import javax.enterprise.context.ApplicationScoped;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.quarkus.runtime.util.StringUtil.isNullOrEmpty;
import static java.util.Optional.ofNullable;

/**
 * Responsavel por validar o corpo da requisicao, retornando mensagens configuraveis pelo properties, o que nao
 * está disponivel quando usamos BeanValidation, que só permite mensagens hard coded.
 */
@ApplicationScoped
public class FileuploadValidator {

    @ConfigProperty(name = "messages.error.fileupload.missingFile", defaultValue = "missingFile")
    String missingFileErrorMsg;

    @ConfigProperty(name = "messages.error.fileupload.missingFormData", defaultValue = "missingFormData")
    String missingFormData;

    @ConfigProperty(name = "messages.error.fileupload.missingFilePathErrorMsg", defaultValue = "missingFilePathErrorMsg")
    String missingFilePathErrorMsg;

    @ConfigProperty(name = "messages.error.fileupload.missingWhyErrorMsg", defaultValue = "missingWhyErrorMsg")
    String missingWhyErrorMsg;

    public void assertIsValid(FormData incomingFormData) {
        ofNullable(incomingFormData)
                .orElseThrow(() -> new ApiException(BAD_REQUEST, missingFormData));

        ofNullable(incomingFormData.file)
                .orElseThrow(() -> new ApiException(BAD_REQUEST, missingFileErrorMsg));

        if(isNullOrEmpty(incomingFormData.filepath)) {
            throw new ApiException(BAD_REQUEST, missingFilePathErrorMsg);
        }

        if(isNullOrEmpty(incomingFormData.why)) {
            throw new ApiException(BAD_REQUEST, missingWhyErrorMsg);
        }
    }
}
