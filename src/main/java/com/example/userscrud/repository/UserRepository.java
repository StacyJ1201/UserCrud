package com.example.userscrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.userscrud.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
	  User findByEmailAddress(String emailAddress);

	@Query(value = "DELETE FROM user WHERE email = ?1", nativeQuery = true)
	User deleteByEmail(String email);

	void deleteByName(String name);

	List<User> findByName(String name);
}
