package com.kp.cms.handlers.attendance;

import java.io.File;
import java.util.List;

import com.kp.cms.bo.admin.PucApprovedLeaves;
import com.kp.cms.bo.admin.PucClassHeld;
import com.kp.cms.bo.admin.PucDefineClassSubject;
import com.kp.cms.forms.attendance.PucAttendanceForm;
import com.kp.cms.helpers.attendance.PucAttendanceHelper;
import com.kp.cms.transactions.attandance.IPucAttendanceTransaction;
import com.kp.cms.transactionsimpl.attendance.PucAttendanceTxnImpl;

public class PucAttendanceHandler {
	private static volatile PucAttendanceHandler pucAttHelper = null;
	 public static PucAttendanceHandler getInstance(){
		 if(pucAttHelper == null){
			 pucAttHelper = new PucAttendanceHandler();
			 return pucAttHelper;
		 }
		return pucAttHelper;
	 }
	 
	 public boolean uploadClassHeld(File file,PucAttendanceForm pucAttendanceForm)throws Exception{
		 IPucAttendanceTransaction transaction=PucAttendanceTxnImpl.getInstance();
		 List<PucClassHeld> classHeldList=PucAttendanceHelper.getInstance().convertExcelTOBOForClassHeld(file, pucAttendanceForm);
		 boolean isAdd=transaction.addPucClassHeld(classHeldList, pucAttendanceForm);
		 return isAdd;
		 
	 }
	 public boolean uploadDefineClassSubjects(File file,PucAttendanceForm pucAttendanceForm)throws Exception{
		 IPucAttendanceTransaction transaction=PucAttendanceTxnImpl.getInstance();
		 List<PucDefineClassSubject> defineClassSubject=PucAttendanceHelper.getInstance().convertExcelTOBOForDefineClassSubjects(file, pucAttendanceForm);
		 boolean isAdd=transaction.addPucClassSubjectDefine(defineClassSubject, pucAttendanceForm);
		 return isAdd;
		 
	 }
	 
	 public boolean uploadApprovedLeaves(File file,PucAttendanceForm pucAttendanceForm)throws Exception{
		 IPucAttendanceTransaction transaction=PucAttendanceTxnImpl.getInstance();
		 List<PucApprovedLeaves> approvedLeavesList=PucAttendanceHelper.getInstance().convertExcelTOBOForApprovedLeaves(file, pucAttendanceForm);
		 boolean isAdd=transaction.addPucApprovedLeaves(approvedLeavesList, pucAttendanceForm);
		 return isAdd;
		 
	 }
}
