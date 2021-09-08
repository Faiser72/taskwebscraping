package com.aspiresys.task1.model.dao.user;

import java.util.List;

public interface EmployeeDao {

	int save(Object object);

	List<?> getAll(String employeeBean);

	Object getById(String employeeBean, int id);

	boolean update(Object object);

}
