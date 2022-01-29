package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.ViewMyAttendanceLeaveForm;
import com.kp.cms.helpers.attendance.ViewMyAttendanceLeaveHelper;
import com.kp.cms.to.attendance.ViewMyAttendanceLeaveTo;
import com.kp.cms.transactions.attandance.IViewMyAttendanceLeaveTransaction;
import com.kp.cms.transactionsimpl.attendance.ViewMyAttendanceLeaveTxnImpl;

public class ViewMyAttendanceLeaveHandler {
	public static volatile ViewMyAttendanceLeaveHandler attendanceLeaveHandler = null;
	public static ViewMyAttendanceLeaveHandler getInstance(){
		if(attendanceLeaveHandler == null){
			attendanceLeaveHandler = new ViewMyAttendanceLeaveHandler();
			return attendanceLeaveHandler;
		}
		return attendanceLeaveHandler;
	}
	IViewMyAttendanceLeaveTransaction transaction = ViewMyAttendanceLeaveTxnImpl.getInstance();
	/**
	 * @param attendanceLeaveForm
	 * @param attendanceLeaveDate
	 * @return
	 * @throws Exception
	 */
	public Map<String, ViewMyAttendanceLeaveTo> getMyAttendanceLeave( ViewMyAttendanceLeaveForm attendanceLeaveForm,
			Date attendanceLeaveDate) throws Exception {
		List<Attendance> attendance = transaction.getSearchMyAttendanceLeave(attendanceLeaveForm,attendanceLeaveDate);
		Map<String, ViewMyAttendanceLeaveTo> attendanceLeaveMap= ViewMyAttendanceLeaveHelper.getInstance().populateBOToTO(attendance,attendanceLeaveForm);
		return attendanceLeaveMap;
	}
	/**
	 * @param attendanceLeaveForm
	 */
	public void printMyAttnLeave(ViewMyAttendanceLeaveForm attendanceLeaveForm,ActionErrors errors)throws Exception {
		Map<String,ViewMyAttendanceLeaveTo> printLeaveMap = attendanceLeaveForm.getAttendanceLeaveMap();
		List<ViewMyAttendanceLeaveTo> list = new ArrayList<ViewMyAttendanceLeaveTo>();
		ViewMyAttendanceLeaveTo leaveTo = null;
		if(printLeaveMap!=null && !printLeaveMap.isEmpty()){
			Iterator<Entry<String, ViewMyAttendanceLeaveTo>> iterator = printLeaveMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<java.lang.String, com.kp.cms.to.attendance.ViewMyAttendanceLeaveTo> entry = (Map.Entry<java.lang.String, com.kp.cms.to.attendance.ViewMyAttendanceLeaveTo>) iterator
						.next();
				 leaveTo = entry.getValue();
				if(leaveTo!=null){
					ViewMyAttendanceLeaveTo attendanceLeaveTo = new ViewMyAttendanceLeaveTo();
					if(leaveTo.getChecked()!=null){
						if(!leaveTo.getChecked().isEmpty()){
							if(leaveTo.getChecked().equalsIgnoreCase("on")){
								attendanceLeaveTo.setClassName(leaveTo.getClassName());
								attendanceLeaveTo.setPeriodName(leaveTo.getPeriodName());
								attendanceLeaveTo.setSubjectName(leaveTo.getSubjectName());
								attendanceLeaveTo.setStudentRegNo(leaveTo.getStudentRegNo());
								list.add(attendanceLeaveTo);
							}
						}
					}
				}
			}
			if(list!=null && !list.isEmpty()){
				attendanceLeaveForm.setLeaveTo(list);
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.select.atleastone"));
			}
		}
	}
	/**
	 * @param attendanceLeaveForm 
	 * @param attendanceLeaveForm
	 * @return
	 */
	public Map<String, ViewMyAttendanceLeaveTo> getSearchEmpAttendanceLeave(Date date,String teacherName, ViewMyAttendanceLeaveForm attendanceLeaveForm) throws Exception{
		List<Attendance> attendance = transaction.getSearchEmpAttnLeave(date,teacherName);
		Map<String, ViewMyAttendanceLeaveTo> attendanceLeaveMap= ViewMyAttendanceLeaveHelper.getInstance().populateBOToTO(attendance,attendanceLeaveForm);
		return attendanceLeaveMap;
	}
}
