package com.aspiresys.task1.controller.userController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aspiresys.task1.beans.response.Task1Response;
import com.aspiresys.task1.beans.user.Employee;
import com.aspiresys.task1.model.service.user.EmployeeService;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

//	Add Employee 
	@PostMapping(path = "/addEmployee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response addEmployee(@RequestBody Employee employeeBean, Task1Response response) {
		employeeBean.setActiveFlag(1);
		int id = employeeService.save(employeeBean);
		if (id != 0) {
			response.setObject(employeeBean);
			response.setMessage("Saved Successfully");
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
			response.setMessage("Please Try again");
		}
		return response;
	}

//	Get all employees
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/employeesList", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response getAllEmployees(Task1Response response) {
		List<Employee> employeeList = (List<Employee>) employeeService.getAll("Employee");
		if (employeeList.size() > 0) {
			response.setListObject(employeeList);
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
			response.setMessage("List is empty");
		}
		return response;
	}

//	Get employee by employee id
	@GetMapping(path = "/getEmployeeDetails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response getEmployeeById(@PathVariable int id, Task1Response response) {
		Employee employeeBean = (Employee) employeeService.getById("Employee", id);
		if (employeeBean != null) {
			response.setSuccess(true);
			response.setObject(employeeBean);
		} else {
			response.setSuccess(false);
			response.setObject("Employee Not Exist");
		}
		return response;
	}

//	Delete Employee
	@PutMapping(path = "/deleteEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response deleteEmployee(@RequestParam("employeeId") int employeeId, Task1Response response) {
		Employee employeeBean = (Employee) employeeService.getById("Employee", employeeId);
		if (employeeBean != null) {
			employeeBean.setActiveFlag(0);
			if (employeeService.update(employeeBean)) {
				response.setSuccess(true);
				response.setMessage("Deleted Successfully");
			} else {
				response.setSuccess(false);
				response.setMessage("Deletion Failed");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("This Employee Not Exist");
		}
		return response;
	}

//	update employee details
	@PutMapping(path = "/updateEmployeeDetails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task1Response updateEmployeeDetails(@RequestBody Employee employeeBean, Task1Response response) {
		Employee employeeDetails = (Employee) employeeService.getById("Employee", employeeBean.getEmployeeId());
		if (employeeDetails != null) {
			employeeDetails.setAge(employeeBean.getAge());
			employeeDetails.setDob(employeeBean.getDob());
			employeeDetails.setEmailId(employeeBean.getEmailId());
			employeeDetails.setEmployeeName(employeeBean.getEmployeeName());
			employeeDetails.setGender(employeeBean.getGender());
			employeeDetails.setMobileNo(employeeBean.getMobileNo());
			if (employeeService.update(employeeDetails)) {
				response.setSuccess(true);
				response.setMessage("Updated Successfully");
			} else {
				response.setSuccess(false);
				response.setMessage("Failed to Update");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("This Employee  Not Exist");
		}

		return response;
	}
}
