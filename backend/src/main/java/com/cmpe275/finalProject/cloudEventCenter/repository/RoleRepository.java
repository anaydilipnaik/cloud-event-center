package com.cmpe275.finalProject.cloudEventCenter.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmpe275.finalProject.cloudEventCenter.model.ERole;
import com.cmpe275.finalProject.cloudEventCenter.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
