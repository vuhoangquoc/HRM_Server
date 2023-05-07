package com.intern.hrmanagementapi.repo;

import com.intern.hrmanagementapi.entity.DepartmentEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, UUID> {

  List<DepartmentEntity> findAllByUserId(UUID userId);

  Optional<DepartmentEntity> findByIdAndUserId(UUID id, UUID userId);
}
