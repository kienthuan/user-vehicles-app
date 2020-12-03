package app.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.dao.User;

public interface UserRepo extends JpaRepository<User, Long> {
	public Optional<User> findByUserCode(String userCode);
	public List<User> findByUserCodeIn(Collection<String> userCodesList);
	public Optional<User> findByEmail(String email);
	public Optional<User> findByEmailAndPassword(String email, String password);
}
