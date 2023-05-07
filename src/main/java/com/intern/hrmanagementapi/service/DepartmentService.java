package com.intern.hrmanagementapi.service;

import com.intern.hrmanagementapi.constant.MessageConst;
import com.intern.hrmanagementapi.entity.DepartmentEntity;
import com.intern.hrmanagementapi.entity.UserEntity;
import com.intern.hrmanagementapi.exception.ObjectException;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.DepartmentRequestDto;
import com.intern.hrmanagementapi.repo.DepartmentRepo;
import com.intern.hrmanagementapi.repo.UserRepo;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

  @Autowired
  private final DepartmentRepo departmentRepo;
  @Autowired
  private final UserRepo userRepo;


  public DataResponseDto getAll() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    List<DepartmentEntity> response = departmentRepo.findAllByUserId(loggingUser.getId());
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  public DataResponseDto getById(UUID id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    DepartmentEntity response = departmentRepo.findByIdAndUserId(id, loggingUser.getId())
        .orElseThrow(
            () -> new ObjectException("Department is not exist", HttpStatus.BAD_REQUEST, null));
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  public DepartmentEntity getEntityById(UUID id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    DepartmentEntity response = departmentRepo.findByIdAndUserId(id, loggingUser.getId())
        .orElseThrow(
            () -> new ObjectException("Department is not exist", HttpStatus.BAD_REQUEST, null));
    return response;
  }

  public DataResponseDto add(DepartmentRequestDto req) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    DepartmentEntity newDepartment = DepartmentEntity.builder().name(req.getName())
        .user(loggingUser).description(req.getDescription()).createdAt(new Date()).build();
    departmentRepo.save(newDepartment);
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.Department.ADD_SUCCESS,
        newDepartment);
  }

  public DataResponseDto updateById(UUID id, DepartmentRequestDto newData) {
    DepartmentEntity updatedDepartment = departmentRepo.findById(id).orElseThrow(
        () -> new ObjectException("Department is not exist", HttpStatus.BAD_REQUEST, null));

    updatedDepartment.setName(newData.getName());
    updatedDepartment.setDescription(newData.getDescription());
    updatedDepartment.setUpdatedAt(new Date());
    departmentRepo.save(updatedDepartment);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedDepartment);
  }

  public DataResponseDto deleteById(UUID id) {
    DepartmentEntity updatedDepartment = departmentRepo.findById(id).orElseThrow(
        () -> new ObjectException("Department is not exist", HttpStatus.BAD_REQUEST, null));

    departmentRepo.deleteById(id);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedDepartment);
  }
}
