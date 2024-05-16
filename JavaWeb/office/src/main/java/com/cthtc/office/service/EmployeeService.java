package com.cthtc.office.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cthtc.office.entity.EmployeeEntity;
import com.cthtc.office.model.Employee;
import com.cthtc.office.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getAllEmployees() {
		try {
			List<EmployeeEntity> employees = employeeRepository.findAll();
			List<Employee> customEmployees = new ArrayList<>();
			
			employees.stream().forEach(e-> {
				Employee employee = new Employee();
				BeanUtils.copyProperties(e, employee);
				customEmployees.add(employee);
			});
			return customEmployees;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String addEmployee(EmployeeEntity employee) {
		try {
			if(!employeeRepository.existsByFirstNameAndLastName(employee.firstName, employee.lastName)) {
				employeeRepository.save(employee);
				return "Employee added successfully";
			} else {
				return "This employee already exists in the database";
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String removeEmployee(EmployeeEntity employee) {
		try {
			if(employeeRepository.existsByFirstNameAndLastName(employee.firstName, employee.lastName)) {
				employeeRepository.delete(employee);
				return "Employee deleted successfully";
			} else {
				return "This employee does not exists";
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String updateEmployee(EmployeeEntity employee) {
		try {
			if(employeeRepository.existsById(employee.getId())) {
				employeeRepository.save(employee);
				return "Employee updated successfully";
			} else {
				return "This employee does not exists";
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
