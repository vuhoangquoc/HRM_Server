package com.intern.hrmanagementapi.service;

import com.intern.hrmanagementapi.constant.MessageConst;
import com.intern.hrmanagementapi.entity.PositionEntity;
import com.intern.hrmanagementapi.exception.ObjectException;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.DepartmentRequestDto;
import com.intern.hrmanagementapi.repo.PositionRepo;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {

  @Autowired
  private final PositionRepo positionRepo;


  public DataResponseDto getAll() {
    List<PositionEntity> response = positionRepo.findAll();
    
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  public DataResponseDto getById(UUID id) {
    PositionEntity response = positionRepo.findById(id).orElseThrow(
        () -> new ObjectException("Position is not exist", HttpStatus.BAD_REQUEST, null));
    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response);
  }

  public DataResponseDto add(DepartmentRequestDto req) {
    PositionEntity newDepartment = PositionEntity.builder().name(req.getName())
        .description(req.getDescription()).createdAt(new Date()).build();
    positionRepo.save(newDepartment);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.Department.ADD_SUCCESS,
        newDepartment);
  }

  public DataResponseDto updateById(UUID id, DepartmentRequestDto newData) {
    PositionEntity updatedPosition = positionRepo.findById(id).orElseThrow(
        () -> new ObjectException("Position is not exist", HttpStatus.BAD_REQUEST, null));

    updatedPosition.setName(newData.getName());
    updatedPosition.setDescription(newData.getDescription());
    updatedPosition.setUpdatedAt(new Date());
    positionRepo.save(updatedPosition);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedPosition);
  }

  public DataResponseDto deleteById(UUID id) {
    PositionEntity updatedDepartment = positionRepo.findById(id).orElseThrow(
        () -> new ObjectException("Position is not exist", HttpStatus.BAD_REQUEST, null));

    positionRepo.deleteById(id);

    return DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, updatedDepartment);
  }
}
