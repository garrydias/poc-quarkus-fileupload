package tech.calindra;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class Routing extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:processFile")
                .setHeader(Exchange.FILE_NAME, simple("${body.filepath}"))
                .setBody(simple("${body.file}"))
                .to("file:received");

        from("file:received?fileName=folder.hash")
                .to("direct:compile");

        from("direct:compile")
                .log("Successfully Compiled");
    }
}
