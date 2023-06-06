package com.example.springexcel.service;

import com.example.springexcel.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    void save(MultipartFile file) throws IOException;

    List<Employee> getAllEmployees();
}
