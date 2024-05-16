package com.cthtc.office.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cthtc.office.entity.EmployeeEntity;
import com.cthtc.office.model.Employee;
import com.cthtc.office.service.EmployeeService;

@RestController
@CrossOrigin(origins ="*")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("getAllEmployees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@PostMapping("/admin/addEmployee")
	public String addEmployee(@RequestBody EmployeeEntity employee) {
		return employeeService.addEmployee(employee);
	}
	
	@PutMapping("/admin/updateEmployee")
	public String updateEmployee(@RequestBody EmployeeEntity employee) {
		return employeeService.updateEmployee(employee);
	}
	
	@DeleteMapping("/admin/removeEmployee")
	public String removeEmployee(@RequestBody EmployeeEntity employee) {
		return employeeService.removeEmployee(employee);
	}
}
