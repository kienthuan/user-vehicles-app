package thuan.ngo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import thuan.ngo.dao.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
