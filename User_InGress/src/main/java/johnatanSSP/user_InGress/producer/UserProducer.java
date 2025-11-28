package johnatanSSP.user_InGress.producer;

import johnatanSSP.user_InGress.domain.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;
    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserCreatedEvent (UserModel userModel){
        rabbitTemplate.convertAndSend("user.created", event);
    }
    
}
