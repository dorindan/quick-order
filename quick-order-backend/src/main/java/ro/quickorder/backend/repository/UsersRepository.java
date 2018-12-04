package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Users;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    public List<Users> findAll();
    public Users findAllByUsername(String username);

    public Users findFirstByUsername(String username);
}
