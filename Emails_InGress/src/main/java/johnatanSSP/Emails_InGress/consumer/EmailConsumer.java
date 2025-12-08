package johnatanSSP.Emails_InGress.consumer;

import johnatanSSP.Emails_InGress.domain.EmailModel;
import johnatanSSP.Emails_InGress.dto.EmailDto;
import johnatanSSP.Emails_InGress.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    @Autowired
    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email_queue")
    public void listenEmailQueue(@Payload EmailDto emailDto) {
//        System.out.println(emailDto.emailTo());
        var email = new EmailModel();
        BeanUtils.copyProperties(emailDto, email);
        // delegate to service which will persist and send the email
        emailService.sendEmail(email);
    }
}
