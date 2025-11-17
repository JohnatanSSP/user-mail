package johnatanSSP.Emails_InGress.repositorie;

import johnatanSSP.Emails_InGress.domain.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {

}
