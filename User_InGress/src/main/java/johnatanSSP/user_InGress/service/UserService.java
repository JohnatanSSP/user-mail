package johnatanSSP.user_InGress.service;

import jakarta.transaction.Transactional;
import johnatanSSP.user_InGress.domain.UserModel;
import johnatanSSP.user_InGress.producer.UserProducer;
import johnatanSSP.user_InGress.repositorie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private final UserRepository Repository;

    @Autowired
    private final UserProducer Producer;

    public UserService(UserRepository repository, UserProducer producer) {
        Repository = repository;
        Producer = producer;
    }

    @Transactional
    public UserModel saveAndPublish(UserModel user) {
        user = Repository.save(user);
        Producer.publishEvent(user);
        return user;
    }

    public List<UserModel> getAllUsers() {
        return Repository.findAll();
    }

    @Transactional
    public void deleteById(UUID id) {
        Repository.deleteById(id);
    }
}
