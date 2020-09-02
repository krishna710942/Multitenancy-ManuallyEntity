package com.oodles.green.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oodles.green.tenant.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);
}
