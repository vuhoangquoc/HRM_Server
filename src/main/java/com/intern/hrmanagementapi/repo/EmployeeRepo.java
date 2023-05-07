package com.intern.hrmanagementapi.repo;

import com.intern.hrmanagementapi.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, UUID> {

  @Query("SELECT e FROM EmployeeEntity e WHERE e.firstName LIKE %:name% OR e.lastName LIKE %:name%")
  List<EmployeeEntity> findByName(@Param("name") String name);

  List<EmployeeEntity> findAllByOrderByFirstNameAsc();

  @Query("SELECT e FROM EmployeeEntity e WHERE e.firstName LIKE %:name% OR e.lastName LIKE %:name%")
  Page<EmployeeEntity> findByNameContainingIgnoreCase(@Param("name") String name,
      Pageable pageable);

//    Page<EmployeeEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
//    default Page<EmployeeEntity> findEmployees(String name, int pageNumber, int pageSize) {
//        Specification<EmployeeEntity> spec = (root, query, criteriaBuilder) -> {
//            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%" + name + "%");
//            Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), "%" + name + "%");
//            return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
//        };
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        return findAll(spec, pageable);
//    }

  Page<EmployeeEntity> findAll(Specification<EmployeeEntity> spec, Pageable pageable);

  List<EmployeeEntity> findAllByUserId(UUID userId);

  Optional<EmployeeEntity> findByIdAndUserId(UUID id, UUID userId);

}
