package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ViewMyAttendanceLeaveForm;
import com.kp.cms.transactions.attandance.IViewMyAttendanceLeaveTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ViewMyAttendanceLeaveTxnImpl implements IViewMyAttendanceLeaveTransaction{
	public static volatile ViewMyAttendanceLeaveTxnImpl attendanceLeaveTxnImpl = null;
	public static ViewMyAttendanceLeaveTxnImpl getInstance(){
		if(attendanceLeaveTxnImpl == null){
			attendanceLeaveTxnImpl = new ViewMyAttendanceLeaveTxnImpl();
			return attendanceLeaveTxnImpl;
		}
		return attendanceLeaveTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IViewMyAttendanceLeaveTransaction#getSearchMyAttendanceLeave()
	 */
	@Override
	public List<Attendance> getSearchMyAttendanceLeave(ViewMyAttendanceLeaveForm attendanceLeaveForm, Date attendanceLeaveDate) throws Exception {
		Session session = null;
		List<Attendance> attendance;
		try{
			session = HibernateUtil.getSession();
			Integer userId = Integer.parseInt(attendanceLeaveForm.getUserId());
			String str = "select attn from Attendance attn join attn.attendanceInstructors instructor where attn.isCanceled = 0  and attn.attendanceDate='"+attendanceLeaveDate+"' and instructor.users.id="+userId;
			Query query = session.createQuery(str);
			attendance  =  query.list();
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
			 }
			 throw new ApplicationException(exception);
		 }
		return attendance;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IViewMyAttendanceLeaveTransaction#getSearchEmpAttnLeave(java.util.Date, java.lang.String)
	 */
	@Override
	public List<Attendance> getSearchEmpAttnLeave(Date date, String userId) throws Exception {
		Session session = null;
		List<Attendance> attendance;
		try{
			session = HibernateUtil.getSession();
			String str = "select attn from Attendance attn join attn.attendanceInstructors instructor where attn.isCanceled = 0  and attn.attendanceDate='"+date+"' and instructor.users.id="+userId;
			Query query = session.createQuery(str);
			attendance  =  query.list();
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
			 }
			 throw new ApplicationException(exception);
		 }
		return attendance;
	}
}
