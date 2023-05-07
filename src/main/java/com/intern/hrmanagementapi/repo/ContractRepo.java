package com.intern.hrmanagementapi.repo;

import com.intern.hrmanagementapi.entity.ContractEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ContractRepo extends JpaRepository<ContractEntity, UUID> {

}
