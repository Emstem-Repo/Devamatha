package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.TeacherDepartment;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.UsersUtilBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.attendance.TeacherDepartmentEntryForm;
import com.kp.cms.helpers.attendance.TeacherDepartmentEntryHelper;
import com.kp.cms.to.attendance.TeacherDepartmentTO;
import com.kp.cms.transactions.attandance.ITeacherDepartmentEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.TeacherDepartmentEntryTransactionImpl;
import com.kp.cms.utilities.InitSessionFactory;

public class TeacherDepartmentEntryHandler {
	private static Log log = LogFactory.getLog(TeacherDepartmentEntryHandler.class);
	public static volatile TeacherDepartmentEntryHandler teacherDepartmentEntryHandler = null;

	public static TeacherDepartmentEntryHandler getInstance() {
		if (teacherDepartmentEntryHandler == null) {
			teacherDepartmentEntryHandler = new TeacherDepartmentEntryHandler();
			return teacherDepartmentEntryHandler;
		}
		return teacherDepartmentEntryHandler;
	}
	
	public boolean addTeacherDepartment(TeacherDepartmentEntryForm teacherDepartmentForm) throws Exception {
		TeacherDepartment teacherDepartment=TeacherDepartmentEntryHelper.convertFormToBoForAdd(teacherDepartmentForm);
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		
		boolean isAdded=transaction.addTeacherDepartment(teacherDepartment);
		
		return isAdded;
	}
	public List<TeacherDepartmentTO> getTeacherDepartments()throws Exception{
		
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		List<TeacherDepartment> objectList=transaction.getTeacherDepartments();
		List<TeacherDepartmentTO> toList=TeacherDepartmentEntryHelper.getTeacherDepartments(objectList);
		return toList;
	}
public  Map<Integer, String> getTeacherDepartmentsName()throws Exception{
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		List<Object[]> objectList=transaction.getTeacherDepartmentsName();
		 Map<Integer, String> teacherMap=TeacherDepartmentEntryHelper.getTeacherDepartmentsName(objectList);
		return teacherMap;
	}
	public boolean checkDuplicate(TeacherDepartmentEntryForm teacherDepartmentForm)throws Exception{
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		boolean isDuplicate=transaction.checkDuplicate(teacherDepartmentForm);
		return isDuplicate;
	}
	public void editTeacherDepartmentEntry(TeacherDepartmentEntryForm teacherDepartmentForm)throws Exception{
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		TeacherDepartment teacherDepartment=transaction.getTeacherDepartmentById(teacherDepartmentForm.getId());
		TeacherDepartmentEntryHelper.setBotoForm(teacherDepartmentForm, teacherDepartment);
	}
	public boolean updateTeacherDepartment(TeacherDepartmentEntryForm teacherDepartmentForm)throws Exception{
		TeacherDepartment teacherDep=TeacherDepartmentEntryHelper.getInstance().convertFormToBo(teacherDepartmentForm);
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		boolean isUpdated=transaction.updateTeacherDepartment(teacherDep);
		return isUpdated;
	}
	public boolean deleteTeacherDepartment(TeacherDepartmentEntryForm teacherDepartmentForm)throws Exception{
		
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		boolean isDeleted=transaction.deleteTeacherDepartment(teacherDepartmentForm.getId());
		return isDeleted;
	}
	/**
	 * @param deptNmae
	 * @return
	 */
	public Map<Integer, String> getFilteredTeacherDepartmentsName(
			String deptNmae) throws Exception{
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		List<Object[]> objectList=transaction.getSearchedTeacherDepartmentsName(deptNmae);
		List<Object[]> objectList2=transaction.getSearchedTeacherDepartmentsNameFrmUsers(deptNmae);
		 Map<Integer, String> teacherMap=TeacherDepartmentEntryHelper.getTeacherDepartmentsNameFilter(objectList, objectList2);
		return teacherMap;
	}
	public  Map<Integer, String> getTeacherDepartmentsNameNew(List<Integer> list)throws Exception{
		ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
		List<Object[]> objectList=transaction.getTeacherDepartmentsNameNew(list);
		 Map<Integer, String> teacherMap=TeacherDepartmentEntryHelper.getTeacherDepartmentsName(objectList);
		return teacherMap;
	}
	
}
