package johnatanSSP.Emails_InGress.configuration;

//import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMq {
    private final String queueName = "email_queue";


    public Queue queue() {
        return new Queue(queueName,true);
    }
}
