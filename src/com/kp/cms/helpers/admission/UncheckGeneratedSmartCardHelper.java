package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactionsimpl.admission.UncheckGeneratedSmartCardTransactionimpl;

public class UncheckGeneratedSmartCardHelper {
	/**
	 * Singleton object of UncheckGeneratedSmartCardHelper
	 */
	private static volatile UncheckGeneratedSmartCardHelper uncheckGeneratedSmartCardHelper = null;
	private static final Log log = LogFactory.getLog(UncheckGeneratedSmartCardHelper.class);
	private UncheckGeneratedSmartCardHelper() {
		
	}
	/**
	 * return singleton object of UncheckGeneratedSmartCardHelper.
	 * @return
	 */
	public static UncheckGeneratedSmartCardHelper getInstance() {
		if (uncheckGeneratedSmartCardHelper == null) {
			uncheckGeneratedSmartCardHelper = new UncheckGeneratedSmartCardHelper();
		}
		return uncheckGeneratedSmartCardHelper;
	}
	/**
	 * Converting the list of students BO to Students TO to set in the form 
	 * @param studentBo
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> convertBostoTOs(List<Student> studentBo) throws Exception {
		List<StudentTO> generatedStuList=new ArrayList<StudentTO>();
		if(studentBo!=null && !studentBo.isEmpty()){
		Iterator<Student> itr= studentBo.iterator();
		while (itr.hasNext()) {
			Student student = (Student) itr.next();
			StudentTO to= new StudentTO();
			to.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
			to.setRegisterNo(student.getRegisterNo());
			to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName()!=null?student.getAdmAppln().getPersonalData().getFirstName():""+
					student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():""+
					student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():"");
			to.setId(student.getId());
			generatedStuList.add(to);
		}
		
	}
		return generatedStuList;
	}
	/**
	 * Converting the EmployeeBO to EmployeeTO to set in the form
	 * @param emplBo
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeTO> convertEmpBostoTOs(List<Employee> emplBo) throws Exception{
		List<EmployeeTO> generatedEmpList=new ArrayList<EmployeeTO>();
		if(emplBo!=null && !emplBo.isEmpty()){
		Iterator<Employee> itr= emplBo.iterator();
		while (itr.hasNext()) {
			Employee employee = (Employee) itr.next();
			EmployeeTO to= new EmployeeTO();
			to.setDepartmentName(employee.getDepartment()!=null?employee.getDepartment().getName():"");
			to.setFingerPrintId(employee.getFingerPrintId()!=null?employee.getFingerPrintId():"");
			to.setName(employee.getFirstName()!=null?employee.getFirstName():""+employee.getMiddleName()!=null?(" "+employee.getMiddleName()):""
				+employee.getLastName()!=null?(" "+employee.getLastName()):"");
			to.setId(employee.getId());
			generatedEmpList.add(to);
		}
		
	}
		return generatedEmpList;
	}
}
