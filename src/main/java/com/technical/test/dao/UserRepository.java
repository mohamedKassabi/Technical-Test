package com.technical.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technical.test.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserId(Long id);

}
