package com.example.springexcel.service;

import com.example.springexcel.entity.Employee;
import com.example.springexcel.helper.ExcelHelper;
import com.example.springexcel.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private ExcelHelper excelHelper;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public void save(MultipartFile file) throws IOException {
        try {
            List<Employee> employeeList = excelHelper.excelToEmployee(file.getInputStream());
            employeeRepository.saveAll(employeeList);
        }
        catch (RuntimeException e){
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
