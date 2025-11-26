package johnatanSSP.Emails_InGress.configuration;

//import org.springframework.amqp.rabbit.annotation.Queue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMq {
    private final String queueName = "email_queue";


    public Queue queue() {
        return new Queue(queueName,true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();

        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
