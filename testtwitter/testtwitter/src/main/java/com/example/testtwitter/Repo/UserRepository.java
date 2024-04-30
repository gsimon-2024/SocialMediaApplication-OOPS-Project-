package com.example.testtwitter.Repo;

import com.example.testtwitter.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    @Query("SELECT u.name FROM User u WHERE u.id = :userId")
    String findUserNameById(@Param("userId") Long userId);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
