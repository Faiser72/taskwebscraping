package com.aspiresys.task1.model.service.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiresys.task1.model.dao.user.EmployeeDao;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Transactional
	@Override
	public int save(Object object) {
		return employeeDao.save(object);
	}

	@Override
	public List<?> getAll(String employeeBean) {
		return employeeDao.getAll(employeeBean);
	}

	@Override
	public Object getById(String employeeBean, int id) {
		return employeeDao.getById(employeeBean, id);
	}

	@Transactional
	@Override
	public boolean update(Object object) {
		return employeeDao.update(object);
	}

}
