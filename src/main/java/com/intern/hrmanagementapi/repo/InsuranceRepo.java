package com.intern.hrmanagementapi.repo;

import com.intern.hrmanagementapi.entity.InsuranceEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepo extends JpaRepository<InsuranceEntity, UUID> {

}
