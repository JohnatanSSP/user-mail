package johnatanSSP.user_InGress.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class UserProducer {

    final RabbitTemplate rabbitTemplate;
    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
}
