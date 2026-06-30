package com.pizzaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzaria.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);
}
