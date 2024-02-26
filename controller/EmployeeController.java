package com.codemyth.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codemyth.model.Employee;
import com.codemyth.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    // POST Request: Create a new employee
    @PostMapping
    public ResponseEntity<String> createNewEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return new ResponseEntity<>("Employee Created in database", HttpStatus.CREATED);
    }

    // GET Request: Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> empList = new ArrayList<>();
        employeeRepository.findAll().forEach(empList::add);
        return new ResponseEntity<>(empList, HttpStatus.OK);
    }

    // GET Request: Get employee by ID
    @GetMapping("/{empid}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long empid) {
        java.util.Optional<Employee> emp = employeeRepository.findById(empid);
        return emp.map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    

    // DELETE Request: Delete employee by ID
    @DeleteMapping("/{empid}")
    public ResponseEntity<String> deleteEmployeeByEmpId(@PathVariable Long empid) {
        employeeRepository.deleteById(empid);
        return new ResponseEntity<>("Employee Deleted Successfully", HttpStatus.OK);
    }

    // DELETE Request: Delete all employees
    @DeleteMapping
    public ResponseEntity<String> deleteAllEmployee() {
        employeeRepository.deleteAll();
        return new ResponseEntity<>("All Employees deleted Successfully", HttpStatus.OK);
    }

    // GET Request: Get employee by city
    @GetMapping("/by-city")
    public ResponseEntity<Employee> getEmployeeByCity(@RequestParam("emp_city") String emp_city) {
        Employee emp = employeeRepository.findByEmpcity(emp_city);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

   
}
