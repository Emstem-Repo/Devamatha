package com.kp.cms.handlers.attendance;



import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceCondonationForm;
import com.kp.cms.helpers.attendance.AttendanceCondonationHelper;
import com.kp.cms.transactions.attandance.IAttendanceCondonationTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceCondonationTransactionImpl;

public class AttendanceCondonationHandler  {
	
	public static volatile AttendanceCondonationHandler self = null;
	
	public static AttendanceCondonationHandler getInstance(){
		if(self == null){
			self = new AttendanceCondonationHandler();
		}
		return self;
		
	}

	public List getSearchedStudent(AttendanceCondonationForm stform) throws Exception {
		
	    List studentBo = null;
	    List studentTo = null;
	    int mode = 0;
		
		IAttendanceCondonationTransaction txn = AttendanceCondonationTransactionImpl.getInstance();
		AttendanceCondonationHelper helper = AttendanceCondonationHelper.getInstance();
		
		//
		studentBo = txn.getCurrentClassStudents(stform,mode);
		
		if(studentBo==null || studentBo.size()==0){
			mode = 1;
			studentBo = txn.getCurrentClassStudents(stform,mode);	
		
		
		
		}
		studentTo = helper.convertBotoTo(studentBo,mode,stform);
	
		
	    
		
		
		
		
		
		return studentTo;
	}

	public List getStudentToEdit(AttendanceCondonationForm stform) throws ApplicationException {
		List studentBo = null;
	    List studentTo = null;
	    IAttendanceCondonationTransaction txn = AttendanceCondonationTransactionImpl.getInstance();
		AttendanceCondonationHelper helper = AttendanceCondonationHelper.getInstance();
		
		studentBo = txn.getEditStudent(stform);
		studentTo = helper.convertBotoToEdit(studentBo,stform.getClassId());
	
		return studentTo;
	}

	public boolean saveCondonation(AttendanceCondonationForm stform) throws ApplicationException {
		boolean isSaved = false;
		List studentBo = null;
		IAttendanceCondonationTransaction txn = AttendanceCondonationTransactionImpl.getInstance();
		AttendanceCondonationHelper helper = AttendanceCondonationHelper.getInstance();
		
		studentBo = helper.convertTotoBo(stform);
		isSaved = txn.SaveStudents(studentBo);
		
		return isSaved;
	}

}
