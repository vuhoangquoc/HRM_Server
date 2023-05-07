package com.intern.hrmanagementapi.constant;

public class MessageConst {

  public static final String SUCCESS = "Success";

  public static final class Department {

    public static final String ADD_SUCCESS = "Create the department successfully";
  }

  public static final class File {

    public static final String UPLOAD_DONE = "Uploaded the file successfully";
    public static final String UPLOAD_FAILED = "Could not upload the file";
    public static final String NOT_EXIST = "File doesn't exist";
    public static final String UPLOAD_EMPTY = "File upload empty";
    public static final String ACCESS_DENIED = "No access to file";
    public static final String READ_ERROR = "Can not read file";
  }

  public static final class User {

    public static final String EXISTED = "User already exist";
    public static final String NOT_EXIST = "User is not exist";
    public static final String CHANGE_PW_OK = "Change password successfully";
    public static final String PW_INCORRECT = "Currrent password is incorrect";
  }

  public static final class Client {

    public static final String METHOD_NOT_ALLOWED = "Method is not supported";
    public static final String UNAUTHORIED = "Unauthorized";
  }

  public static final class Employee {

    public static final String EXISTED = "EmployeeEntity already exist";
    public static final String NOT_EXIST = "EmployeeEntity is not exist";
  }

  public static final class Server {

    public static final String ERROR = "SERVER ERROR";
  }

  public static final class Jwt {

    public static final String EXPIRED = "Token expired";
    public static final String INVALID_SIGNATURE = "Invalid JWT signature";
    public static final String INVALID = "Invalid JWT token";
    public static final String UNSUPPORTED = "Unsupported JWT token";
    public static final String EMPTY_CLAIMS = "JWT claims string is empty";
  }
}
