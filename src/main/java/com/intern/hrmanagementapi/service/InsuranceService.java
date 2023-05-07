package com.intern.hrmanagementapi.service;

import com.intern.hrmanagementapi.constant.MessageConst;
import com.intern.hrmanagementapi.entity.EmployeeEntity;
import com.intern.hrmanagementapi.entity.InsuranceEntity;
import com.intern.hrmanagementapi.entity.UserEntity;
import com.intern.hrmanagementapi.exception.ObjectException;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.InsuranceAddRequestDto;
import com.intern.hrmanagementapi.model.InsuranceUpdateRequestDto;
import com.intern.hrmanagementapi.repo.EmployeeRepo;
import com.intern.hrmanagementapi.repo.InsuranceRepo;
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
public class InsuranceService {

  @Autowired
  private final InsuranceRepo insuranceRepo;
  @Autowired
  private final EmployeeRepo employeeRepo;
  @Autowired
  private final UserRepo userRepo;


  public DataResponseDto getAll() {
    List<InsuranceEntity> response = insuranceRepo.findAll();
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  public DataResponseDto getById(UUID id) {
    InsuranceEntity response = insuranceRepo.findById(id).orElseThrow(
        () -> new ObjectException("Insurance is not exist", HttpStatus.BAD_REQUEST, null));
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  @SneakyThrows
  public DataResponseDto add(InsuranceAddRequestDto req) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();
    UUID employeeId = req.getEmployeeId();

    EmployeeEntity existEmployee = employeeRepo.findByIdAndUserId(employeeId, loggingUser.getId())
        .orElseThrow(
            () -> new ObjectException(String.format("Employee with id %s is not exist", employeeId),
                HttpStatus.BAD_REQUEST, null));

    InsuranceEntity newInsurance = InsuranceEntity.builder().employeeId(req.getEmployeeId())
        .number(req.getNumber()).issuedDate(DateUtil.stringToDate(req.getIssuedDate()))
        .issuedPlace(req.getIssuedPlace()).build();
    insuranceRepo.save(newInsurance);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.Department.ADD_SUCCESS,
        newInsurance);
  }

  @SneakyThrows
  public DataResponseDto updateById(UUID id, InsuranceUpdateRequestDto req) {
    InsuranceEntity updatedInsurance = insuranceRepo.findById(id).orElseThrow(
        () -> new ObjectException("Insurance is not exist", HttpStatus.BAD_REQUEST, null));

    updatedInsurance.setNumber(req.getNumber());
    updatedInsurance.setIssuedDate(DateUtil.stringToDate(req.getIssuedDate()));
    updatedInsurance.setIssuedPlace(req.getIssuedPlace());
    insuranceRepo.save(updatedInsurance);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedInsurance);
  }

  public DataResponseDto deleteById(UUID id) {
    InsuranceEntity deletedInsurance = insuranceRepo.findById(id).orElseThrow(
        () -> new ObjectException("Insurance is not exist", HttpStatus.BAD_REQUEST, null));

    insuranceRepo.deleteById(id);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, deletedInsurance);
  }
}
