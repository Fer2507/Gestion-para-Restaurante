package itch.delacruz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import itch.delacruz.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{

}
