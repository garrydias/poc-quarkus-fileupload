package tech.calindra.fileupload;


import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

//import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.MediaType;
import java.io.File;

public class FormData {

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
//    @NotBlank(message = "Missing 'filepath' property")
    public String filepath;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
//    @NotBlank(message = "Missing 'why' property")
    public String why;

    @RestForm("file")
//    @NotBlank(message = "Missing binary at 'file' property")
    public File file;

    public File getFile() {
        return file;
    }

    public String getFilepath() {
        return filepath;
    }
}