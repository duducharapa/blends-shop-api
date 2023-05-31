package com.charapadev.blendsshop.modules.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByAccessCode(String accessCode);

}