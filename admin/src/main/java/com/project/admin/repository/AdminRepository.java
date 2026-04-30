package com.project.admin.repository;

import com.project.admin.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin ,Long> {

    boolean existsByLoginId(String loginId);

    Optional<Admin> findByLoginId(String loginId);

}
