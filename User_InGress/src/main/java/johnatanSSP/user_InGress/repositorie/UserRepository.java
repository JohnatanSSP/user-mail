package johnatanSSP.user_InGress.repositorie;

import johnatanSSP.user_InGress.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    boolean existsByEmail(String email);

}
