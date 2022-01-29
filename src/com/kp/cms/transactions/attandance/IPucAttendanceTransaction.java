package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.PucApprovedLeaves;
import com.kp.cms.bo.admin.PucClassHeld;
import com.kp.cms.bo.admin.PucDefineClassSubject;
import com.kp.cms.forms.attendance.PucAttendanceForm;

public interface IPucAttendanceTransaction {
	public boolean addPucClassHeld(List<PucClassHeld> list,PucAttendanceForm pucAttendanceForm)throws Exception;
	public boolean addPucClassSubjectDefine(List<PucDefineClassSubject> list,PucAttendanceForm pucAttendanceForm)throws Exception;
	public boolean addPucApprovedLeaves(List<PucApprovedLeaves> list,PucAttendanceForm pucAttendanceForm)throws Exception;
}
