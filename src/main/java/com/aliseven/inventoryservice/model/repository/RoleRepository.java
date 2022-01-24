package com.aliseven.inventoryservice.model.repository;


import com.aliseven.inventoryservice.model.entity.RoleEntity;
import com.aliseven.inventoryservice.model.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleType name);
}