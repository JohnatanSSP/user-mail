package johnatanSSP.Emails_InGress.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import johnatanSSP.Emails_InGress.enums.EmailStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_EMAIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel {

    private String emailID;
    private String userID;
    private String emailFrom;
    private String emailTo;
    private String emailSubject;
    @Column(columnDefinition = "BODY")
    private String emailBody;
    private LocalDateTime emailSentDate;
    private EmailStatus emailStatus;
}
