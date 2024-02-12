package in.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.info.entity.User;


public interface UserRepo extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	
	public User findByEmailAndPwd(String email, String pwd);

}
