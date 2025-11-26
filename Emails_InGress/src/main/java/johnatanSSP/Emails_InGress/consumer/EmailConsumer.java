package johnatanSSP.Emails_InGress.consumer;

import johnatanSSP.Emails_InGress.domain.EmailModel;
import johnatanSSP.Emails_InGress.dto.EmailDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "email_queue")
    public void listenEmailQueue(@Payload EmailDto emailDto) {

        System.out.println("Listening email queue" + emailDto.emailTo());
    }
}
