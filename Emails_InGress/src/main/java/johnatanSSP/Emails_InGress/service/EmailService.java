package johnatanSSP.Emails_InGress.service;

import johnatanSSP.Emails_InGress.domain.EmailModel;
import johnatanSSP.Emails_InGress.dto.EmailDto;
import johnatanSSP.Emails_InGress.enums.EmailStatus;
import johnatanSSP.Emails_InGress.repositorie.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender javaMailSender;

    @Autowired
    private final EmailRepository emailRepository;

    public EmailService(JavaMailSender javaMailSender, EmailRepository emailRepository) {
        this.javaMailSender = javaMailSender;
        this.emailRepository = emailRepository;
    }

    @Value("${EMAIL_USERNAME}")
    private String fromEmail;

    public void sendEmail(EmailModel emailModel) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromEmail);
            mailMessage.setSubject(emailModel.getEmailSubject());
            mailMessage.setText(emailModel.getEmailBody());
            mailMessage.setTo(emailModel.getEmailTo());
            javaMailSender.send(mailMessage);
            emailModel.setEmailStatus(EmailStatus.SENT);
        } catch (Exception e) {
            emailModel.setEmailStatus(EmailStatus.FAILED);
            System.out.println("Error sending email: " + e.getMessage());
        }
        emailRepository.save(emailModel);
    }
}
