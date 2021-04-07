package com.kp.cms.handlers.exam;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.ExternalEvaluatorsDepartment;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.exam.AssignExternalToDepartmentForm;
import com.kp.cms.helpers.admin.CourseHelper;
import com.kp.cms.helpers.exam.AssignExternalToDepartmentHelper;
import com.kp.cms.to.exam.AssignExternalToDepartmentTO;
import com.kp.cms.transactions.admin.ICourseTransaction;
import com.kp.cms.transactions.exam.IAssignExternalToDepartmentTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.exam.AssignExternalToDepartmentTransactionImpl;

public class AssignExternalToDepartmentHandler {
	public static volatile AssignExternalToDepartmentHandler assignExternalToDepartmentHandler = null;
	private static final Log log = LogFactory.getLog(AssignExternalToDepartmentHandler.class);

	public static AssignExternalToDepartmentHandler getInstance() {
		if (assignExternalToDepartmentHandler == null) {
			assignExternalToDepartmentHandler = new AssignExternalToDepartmentHandler();
			return assignExternalToDepartmentHandler;
		}
		return assignExternalToDepartmentHandler;
	}
	public  List<AssignExternalToDepartmentTO> getDepartments(AssignExternalToDepartmentForm assignExternalToDepartmentForm) throws Exception{
		IAssignExternalToDepartmentTransaction iTransaction = AssignExternalToDepartmentTransactionImpl.getInstance();
		List<Department> deptList = iTransaction.getDepartment();
		List<AssignExternalToDepartmentTO> deptlist=AssignExternalToDepartmentHelper.getInstance().convertBotoTo(deptList);
	     return deptlist;
	}
	public  List<AssignExternalToDepartmentTO> getExternalDetails(AssignExternalToDepartmentForm assignExternalToDepartmentForm) throws Exception{
		IAssignExternalToDepartmentTransaction iTransaction = AssignExternalToDepartmentTransactionImpl.getInstance();
		List<ExamValuators> deptList = iTransaction.getExternalDetails();
		Map<Integer,Integer> evMap=iTransaction.getExternalEvaluatorsDepartmentList(assignExternalToDepartmentForm);
		List<AssignExternalToDepartmentTO> deptlist=AssignExternalToDepartmentHelper.getInstance().convertBotoToForExternalDetails(deptList,evMap);
	     return deptlist;
	}
	public boolean addAssignEvaluators(AssignExternalToDepartmentForm assignExternalToDepartmentForm) throws Exception{
		IAssignExternalToDepartmentTransaction iTransaction = AssignExternalToDepartmentTransactionImpl.getInstance();
		boolean isAdded=false;
    	List<ExternalEvaluatorsDepartment> bolist = AssignExternalToDepartmentHelper.getInstance().convertTotoBos(assignExternalToDepartmentForm);
    	if(!bolist.isEmpty()){
        isAdded = iTransaction.saveEvaluators(bolist);
    	 }
        return isAdded;
    }

}
