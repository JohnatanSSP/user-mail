package johnatanSSP.Emails_InGress.dto;

import java.util.UUID;

public record EmailDto(
        UUID userID,
        String emailTo,
        String emailSubject,
        String emailBody
) {
}
