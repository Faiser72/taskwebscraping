package com.aspiresys.task1.beans.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPLOYEE_ID")
	private int employeeId;

	@Column(name = "ACTIVE_FLAG")
	private int activeFlag;

	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "DOB")
	private String dob;

	@Column(name = "AGE")
	private String age;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "EMAIL_ID")
	private String emailId;

}
