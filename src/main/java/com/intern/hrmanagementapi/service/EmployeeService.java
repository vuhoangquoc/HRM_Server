package com.intern.hrmanagementapi.service;

import com.intern.hrmanagementapi.entity.EmployeeEntity;
import com.intern.hrmanagementapi.entity.UserEntity;
import com.intern.hrmanagementapi.exception.ObjectException;
import com.intern.hrmanagementapi.model.EmployeeRequestDto;
import com.intern.hrmanagementapi.repo.EmployeeRepo;
import com.intern.hrmanagementapi.repo.UserRepo;
import com.intern.hrmanagementapi.util.DateUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  @Autowired
  private final EmployeeRepo employeeRepo;
  @Autowired
  private final UserRepo userRepo;


  /**
   * Save a new EmployeeEntity object to the database.
   *
   * @param req the EmployeeEntity object to be saved
   * @return the saved EmployeeEntity object
   */
  public EmployeeEntity saveEmployee(EmployeeRequestDto req) throws ParseException {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).orElse(null);

    EmployeeEntity newEmployee = EmployeeEntity.builder().firstName(req.getFirstName())
        .lastName(req.getLastName()).dob(DateUtil.stringToDate(req.getDob()))
        .address(req.getAddress()).email(req.getEmail()).positionId(req.getPositionId())
        .departmentId(req.getDepartmentId()).gender(req.getGender()).createDate(new Date())
        .user(loggingUser).build();

    return employeeRepo.save(newEmployee);
  }

  /**
   * Save a list of employees to the database.
   *
   * @param employees The list of employees to save.
   * @return The list of saved employees.
   */
  public List<EmployeeEntity> saveEmployees(List<EmployeeEntity> employees) {
    return employeeRepo.saveAll(employees);
  }

  /**
   * Get a list of all employees.
   *
   * @return A list of all employees in the database.
   */
  public List<EmployeeEntity> getAllEmployees() {
    return employeeRepo.findAll();
  }

  public List<EmployeeEntity> getAllEmployeesByUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();
    return employeeRepo.findAllByUserId(loggingUser.getId());
  }

  /**
   * Get an employee by id
   *
   * @param id the id of the employee to retrieve
   * @return the employee with the specified id or null if not found
   */
  public EmployeeEntity getEmployeeById(UUID id) {
    return employeeRepo.findById(id).orElse(null);
  }

  public EmployeeEntity getEmployeeByIdAndUserId(UUID id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    EmployeeEntity emp = employeeRepo.findByIdAndUserId(id, loggingUser.getId())
        .orElseThrow(() -> new ObjectException("Employee not exist", HttpStatus.BAD_REQUEST, null));
    return emp;
  }

  /**
   * Deletes the employee with the given ID from the database.
   *
   * @param id the ID of the employee to be deleted
   */
  public void deleteEmployee(UUID id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    EmployeeEntity e = employeeRepo.findByIdAndUserId(id, loggingUser.getId()).orElseThrow(
        () -> new ObjectException("Employee is not exist", HttpStatus.BAD_REQUEST, null));
    employeeRepo.deleteById(id);
  }


  /**
   * Updates the information of an existing employee.
   *
   * @param req The employee object to be updated.
   * @return The updated employee object.
   */
  @SneakyThrows
  public EmployeeEntity updateEmployee(UUID id, EmployeeRequestDto req) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    EmployeeEntity existingEmployee = employeeRepo.findByIdAndUserId(id, loggingUser.getId())
        .orElseThrow(
            () -> new ObjectException("Employee is not exist", HttpStatus.BAD_REQUEST, null));

    existingEmployee.setFirstName(req.getFirstName());
    existingEmployee.setLastName(req.getLastName());
    existingEmployee.setGender(req.getGender());
    existingEmployee.setAddress(req.getAddress());
    existingEmployee.setDob(DateUtil.stringToDate(req.getDob()));
    existingEmployee.setUpdateDate(new Date());
    existingEmployee.setDepartmentId(req.getDepartmentId());
    existingEmployee.setPositionId(req.getPositionId());
    return employeeRepo.save(existingEmployee);
  }

  /**
   * Retrieves a paginated list of employees sorted by a given field.
   *
   * @param orderBy    the field to sort by (optional)
   * @param pageNumber the page number to retrieve
   * @param pageSize   the number of employees per page
   * @return a Page object containing the requested employees
   */
  public Page<EmployeeEntity> getEmployeesByPageAndSort(String orderBy, int pageNumber,
      int pageSize) {
    Pageable pageable;
    if (orderBy != null) {
      pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
    } else {
      pageable = PageRequest.of(pageNumber, pageSize);
    }
    return employeeRepo.findAll(pageable);
  }

  /**
   *
   * Retrieves a page of employees matching the given name, sorted by name, and with pagination.
   *
   * @param name The name to search for.
   * @param pageNumber The page number to retrieve (starting from 0).
   * @param pageSize The number of elements to retrieve per page.
   * @return A page of employees matching the given name.
   */

//    public Page<EmployeeEntity> getEmployeeByName(String name, int pageNumber, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        return repository.findByNameContainingIgnoreCase(name, pageable);
//    }
//    public Page<EmployeeEntity> getEmployeeByName(String name, int pageNumber, int pageSize) {
//        Specification<EmployeeEntity> spec = (root, query, criteriaBuilder) -> {
//            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%" + name + "%");
//            Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), "%" + name + "%");
//            return criteriaBuilder.or(firstNamePredicate, lastNamePredicate);
//        };
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        return repository.findAll(spec, pageable);
//    }

//    public Page<EmployeeEntity> searchOperatingSystem(SearchRequest request) {
//        SearchSpecification<EmployeeEntity> specification = new SearchSpecification<>(request);
//        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
//        return repository.findAll(specification, pageable);

//    }
}
