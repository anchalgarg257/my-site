package com.adobe.aem.guides.nirvana.core.services;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = EmailService.class)
public class EmailService {

    @Reference
    private MessageGatewayService messageGatewayService;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public Boolean sendEmail(final String emailFrom, final String emailToRecipients, final String ccRecipientEmail, final String emailSubject, final String emailMessage) {

        try {
            MessageGateway<Email> messageGateway;

            Email email = new SimpleEmail();

            email.addTo(emailToRecipients);
            email.addCc(ccRecipientEmail);
            email.setSubject(emailSubject);
            email.setFrom(emailFrom);
            email.setMsg(emailMessage);


            //Inject a MessageGateway Service and send the message
            messageGateway = messageGatewayService.getGateway(Email.class);

            // Check the logs to see that messageGateway is not null
            messageGateway.send((Email) email);
            return true;
        } catch (Exception e) {
            logger.info("Mail is not sent", e);
            return false;
        }

    }

}

