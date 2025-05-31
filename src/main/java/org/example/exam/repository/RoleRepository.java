package org.example.exam.repository;

import org.example.exam.entity.Role;
import org.example.exam.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

}

