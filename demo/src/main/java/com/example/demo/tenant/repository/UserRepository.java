package com.example.demo.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.tenant.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);
}
