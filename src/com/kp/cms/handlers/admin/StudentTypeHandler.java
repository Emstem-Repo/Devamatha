package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.StudentType;
import com.kp.cms.helpers.admin.StudentTypeHelper;
import com.kp.cms.to.admin.StudentTypeTO;
import com.kp.cms.transactions.admin.IStudentTypeTransaction;
import com.kp.cms.transactionsimpl.admin.StudentTypeTransactionImplementation;

public class StudentTypeHandler {
	
	private static volatile StudentTypeHandler studentTypeHandler = null;

	public static StudentTypeHandler getInstance() {
		if (studentTypeHandler == null) {
			studentTypeHandler = new StudentTypeHandler();
		}
		return studentTypeHandler;
	}
	
	public List<StudentTypeTO> getStudentType() throws Exception 
	{
		
		IStudentTypeTransaction studentTypeIntf = new StudentTypeTransactionImplementation();
		List<StudentType> studentTypeBoList = studentTypeIntf.getStudentType();
		List<StudentTypeTO> getstudentTypeList = StudentTypeHelper
				.convertBOstoTos(studentTypeBoList);
		return getstudentTypeList;
	}
	
	
	public boolean addStudentType(String name, String desc) throws Exception
	{
		IStudentTypeTransaction studentTypeIntf = new StudentTypeTransactionImplementation();
		boolean isTypeAdded = false;
		if (studentTypeIntf != null) {
			isTypeAdded = studentTypeIntf.addStudentType(name,desc);
		}
		return isTypeAdded;
		
	}
	
	
	public boolean editStudentType(int id,String name, String desc) throws Exception
	{
		IStudentTypeTransaction studentTypeIntf = new StudentTypeTransactionImplementation();
		boolean isEditted = false;
		if (studentTypeIntf != null) {
			isEditted = studentTypeIntf.editStudentType(id,name,desc);
		}
		return isEditted;
		
	}
	
	public boolean deleteProgramType(int id,String name,String desc)throws Exception {
		IStudentTypeTransaction studentTypeIntf = new StudentTypeTransactionImplementation();
		boolean isDeleted = false;
		if (studentTypeIntf != null) {
			isDeleted = studentTypeIntf.deleteStudentType(id,name,desc);
		}
		return isDeleted;
		
	}
	
	public StudentType existanceCheck(String name)throws Exception
	{
		IStudentTypeTransaction studentTypeIntf = new StudentTypeTransactionImplementation();
		StudentType studentType = studentTypeIntf.existanceCheck(name);
		
		return studentType;
	}
	
	public boolean reActivateStudentType(String studentCategory) throws Exception{
		IStudentTypeTransaction studentTypeIntf = new StudentTypeTransactionImplementation();
		return studentTypeIntf.reActivateStudentType(studentCategory);
	}
}
