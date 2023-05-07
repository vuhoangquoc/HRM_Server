package com.intern.hrmanagementapi.service;

import com.intern.hrmanagementapi.constant.MessageConst;
import com.intern.hrmanagementapi.entity.ContractEntity;
import com.intern.hrmanagementapi.entity.EmployeeEntity;
import com.intern.hrmanagementapi.entity.UserEntity;
import com.intern.hrmanagementapi.exception.ObjectException;
import com.intern.hrmanagementapi.model.ContractAddRequestDto;
import com.intern.hrmanagementapi.model.ContractUpdateRequestDto;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.repo.ContractRepo;
import com.intern.hrmanagementapi.repo.EmployeeRepo;
import com.intern.hrmanagementapi.repo.UserRepo;
import com.intern.hrmanagementapi.util.DateUtil;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

  @Autowired
  private final ContractRepo contractRepo;
  @Autowired
  private final EmployeeRepo employeeRepo;
  @Autowired
  private final UserRepo userRepo;


  public DataResponseDto getAll() {
    List<ContractEntity> response = contractRepo.findAll();
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  public DataResponseDto getById(UUID id) {
    ContractEntity response = contractRepo.findById(id).orElseThrow(
        () -> new ObjectException("Contract is not exist", HttpStatus.BAD_REQUEST, null));
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  @SneakyThrows
  public DataResponseDto add(ContractAddRequestDto req) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();
    UUID employeeId = req.getEmployeeId();

    EmployeeEntity existEmployee = employeeRepo.findByIdAndUserId(employeeId, loggingUser.getId())
        .orElseThrow(
            () -> new ObjectException(String.format("Employee with id %s is not exist", employeeId),
                HttpStatus.BAD_REQUEST, null));

    ContractEntity newContract = ContractEntity.builder().employeeId(req.getEmployeeId())
        .basicSalary(req.getBasicSalary()).signDate(DateUtil.stringToDate(req.getSignDate()))
        .expireDate(DateUtil.stringToDate(req.getExpireDate())).workingTime(req.getWorkingTime())
        .build();

    contractRepo.save(newContract);
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.Department.ADD_SUCCESS,
        newContract);
  }

  @SneakyThrows
  public DataResponseDto updateById(UUID id, ContractUpdateRequestDto req) {
    ContractEntity updatedContract = contractRepo.findById(id).orElseThrow(
        () -> new ObjectException("Contract is not exist", HttpStatus.BAD_REQUEST, null));

    updatedContract.setBasicSalary(req.getBasicSalary());
    updatedContract.setSignDate(DateUtil.stringToDate(req.getSignDate()));
    updatedContract.setExpireDate(DateUtil.stringToDate(req.getExpireDate()));
    updatedContract.setWorkingTime(req.getWorkingTime());

    contractRepo.save(updatedContract);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedContract);
  }

  public DataResponseDto deleteById(UUID id) {
    ContractEntity updatedContract = contractRepo.findById(id).orElseThrow(
        () -> new ObjectException("Contract is not exist", HttpStatus.BAD_REQUEST, null));

    contractRepo.deleteById(id);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedContract);
  }
}
