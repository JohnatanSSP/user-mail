package johnatanSSP.Emails_InGress.consumer;

import johnatanSSP.Emails_InGress.domain.EmailModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @RabbitListener(queues = "email_queue")
    public void listenEmailQueue(@Payload String menssage) {
        System.out.println("Listening email queue" + menssage);
    }
}
