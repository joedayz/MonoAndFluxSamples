package pe.joedayz.training.springwebfluxmono.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.joedayz.training.springwebfluxmono.entity.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}