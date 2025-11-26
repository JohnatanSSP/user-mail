package johnatanSSP.user_InGress.service;

import jakarta.transaction.Transactional;
import johnatanSSP.user_InGress.domain.UserModel;
import johnatanSSP.user_InGress.repositorie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository Repository;

    public UserService(UserRepository repository) {
        Repository = repository;
    }

    @Transactional
    public UserModel save(UserModel user) {
        return Repository.save(user);
    }
}
