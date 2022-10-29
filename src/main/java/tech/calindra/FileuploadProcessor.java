package tech.calindra;

import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.support.DefaultMessage;

public class FileuploadProcessor {

    public void handle(Exchange in) throws InvalidPayloadException {
        DefaultMessage defaultMessage = in.getIn(DefaultMessage.class);
        System.out.println(defaultMessage.getMandatoryBody());
    }
}
