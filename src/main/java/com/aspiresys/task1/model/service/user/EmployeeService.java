package com.aspiresys.task1.model.service.user;

import java.util.List;

public interface EmployeeService {

	int save(Object object);

	List<?> getAll(String employeeBean);

	Object getById(String employeeBean, int id);

	boolean update(Object object);
}
