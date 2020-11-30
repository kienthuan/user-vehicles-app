package thuan.ngo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import thuan.ngo.dao.User;

public interface UserRepo extends JpaRepository<User, Long> {
	public Optional<User> findByEmailAndPassword(String email, String password);
}
