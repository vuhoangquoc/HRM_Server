package com.intern.hrmanagementapi.service;

import com.intern.hrmanagementapi.constant.MessageConst;
import com.intern.hrmanagementapi.entity.EmployeeEntity;
import com.intern.hrmanagementapi.entity.SalaryEntity;
import com.intern.hrmanagementapi.entity.UserEntity;
import com.intern.hrmanagementapi.exception.ObjectException;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.SalaryAddRequestDto;
import com.intern.hrmanagementapi.model.SalaryUpdateRequestDto;
import com.intern.hrmanagementapi.repo.EmployeeRepo;
import com.intern.hrmanagementapi.repo.SalaryRepo;
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
public class SalaryService {

  @Autowired
  private final SalaryRepo salaryRepo;
  @Autowired
  private final EmployeeRepo employeeRepo;
  @Autowired
  private final UserRepo userRepo;


  public DataResponseDto getAll() {
    List<SalaryEntity> response = salaryRepo.findAll();
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  public DataResponseDto getById(UUID id) {
    SalaryEntity response = salaryRepo.findById(id).orElseThrow(
        () -> new ObjectException("Salary is not exist", HttpStatus.BAD_REQUEST, null));
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  @SneakyThrows
  public DataResponseDto add(SalaryAddRequestDto req) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();
    UUID employeeId = req.getEmployeeId();

    EmployeeEntity existEmployee = employeeRepo.findByIdAndUserId(employeeId, loggingUser.getId())
        .orElseThrow(
            () -> new ObjectException(String.format("Employee with id %s is not exist", employeeId),
                HttpStatus.BAD_REQUEST, null));

    SalaryEntity newSalary = SalaryEntity.builder().employeeId(req.getEmployeeId())
        .salary(req.getSalary()).bonus(req.getBonus()).allowance(req.getAllowance())
        .startDate(DateUtil.stringToDate(req.getStartDate()))
        .endDate(DateUtil.stringToDate(req.getEndDate())).build();

    salaryRepo.save(newSalary);
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.Department.ADD_SUCCESS,
        newSalary);
  }

  @SneakyThrows
  public DataResponseDto updateById(UUID id, SalaryUpdateRequestDto req) {
    SalaryEntity updatedSalary = salaryRepo.findById(id).orElseThrow(
        () -> new ObjectException("Salary is not exist", HttpStatus.BAD_REQUEST, null));

    updatedSalary.setSalary(req.getSalary());
    updatedSalary.setAllowance(req.getAllowance());
    updatedSalary.setBonus(req.getBonus());
    updatedSalary.setStartDate(DateUtil.stringToDate(req.getStartDate()));
    updatedSalary.setEndDate(DateUtil.stringToDate(req.getEndDate()));
    salaryRepo.save(updatedSalary);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedSalary);
  }

  public DataResponseDto deleteById(UUID id) {
    SalaryEntity updatedSalary = salaryRepo.findById(id).orElseThrow(
        () -> new ObjectException("Salary is not exist", HttpStatus.BAD_REQUEST, null));

    salaryRepo.deleteById(id);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedSalary);
  }
}
