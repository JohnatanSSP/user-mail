package johnatanSSP.user_InGress.producer;

import johnatanSSP.user_InGress.domain.UserModel;
import johnatanSSP.user_InGress.dto.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;
    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private final String routingKey = "email_queue";

    public void publishEvent (UserModel userModel){
        var emailDto = new EmailDto();

        emailDto.setUserID(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setEmailSubject("Welcome to ingress!");
        emailDto.setEmailBody("Hello!!! " + userModel.getName() + " , Welcome to your plataform for ingress!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
    
}
