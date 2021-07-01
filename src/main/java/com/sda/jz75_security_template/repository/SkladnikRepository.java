package com.sda.jz75_security_template.repository;

import com.sda.jz75_security_template.model.Account;
import com.sda.jz75_security_template.model.Skladnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkladnikRepository extends JpaRepository<Skladnik, Long> {

}
