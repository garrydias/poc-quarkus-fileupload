package tech.calindra.fileupload;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.MultipartForm;
import tech.calindra.commom.FilesUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@RequestScoped
@Path("fileupload")
public class FileuploadController {

    @Inject
    Logger LOGGER;

    @EndpointInject("direct:processFile")
    ProducerTemplate producerTemplate;

    /**
     * ../../../../received = $projectDir/received
     * ./received = $projectDir/build/classes/java/main/received
     */
    @ConfigProperty(name = "fileupload.entrypoint", defaultValue = "./received")
//    @ConfigProperty(name = "fileupload.entrypoint", defaultValue = "../../../../received")
    String entryPoint;

    @Inject
    FileuploadValidator validator;

    /**
     *
     * @return Void pois desejamos reduzir os bytes trafegados em razao da performance.
     */
    @POST
    @Path("1")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public void upload(@MultipartForm FormData formData, @HeaderParam("email") String email) throws IOException {
        this.validator.assertIsValid(formData);

        java.nio.file.Path source = Paths.get(formData.file.getPath());
        java.nio.file.Path target = Paths.get(entryPoint, formData.filepath);

        LOGGER.debugv("Receiving file: {0}", Files.readString(source));

        java.nio.file.Path destinationPath = FilesUtil.mv(source, target);

        LOGGER.infov("File successfully copied from {0} to {1}", source.toString(), destinationPath.toRealPath().toString());
    }

    @POST
    @Path("2")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public void upload2(@MultipartForm FormData formData, @HeaderParam("email") String email) throws IOException {
        this.validator.assertIsValid(formData);

        String receivedFilePath = formData.getFilepath();

        LOGGER.debugv("Receiving file: {0}", receivedFilePath);

        producerTemplate.sendBodyAndHeaders(formData, Map.of("email", email));

        LOGGER.infov("File successfully received: {0}", receivedFilePath);
    }

}
