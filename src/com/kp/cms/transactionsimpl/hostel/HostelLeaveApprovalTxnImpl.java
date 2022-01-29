package com.kp.cms.transactionsimpl.hostel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelLeaveApprovalForm;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;
import com.kp.cms.transactions.hostel.IHostelLeaveApprovalTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class HostelLeaveApprovalTxnImpl implements IHostelLeaveApprovalTransaction{
	private static final Log log = LogFactory.getLog(HostelLeaveApprovalTxnImpl.class);
	private static volatile HostelLeaveApprovalTxnImpl impl= null;
	public static HostelLeaveApprovalTxnImpl getInstance(){
		if(impl == null){
			impl = new HostelLeaveApprovalTxnImpl();
			return impl;
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveApprovalTransaction#getLeaveApprovalDetails(com.kp.cms.forms.hostel.HostelLeaveApprovalForm)
	 */
	@Override
	public List<Object[]> getLeaveApprovalDetails(HostelLeaveApprovalForm objForm)
			throws Exception {
		Session session = null;
		List<Object[]> hlLeavesBo;
		try{
			session = HibernateUtil.getSession();
			String hqlQuery = " select hl.id,hl.hl_admission_id,student.register_no,classes.name,personal_data.first_name, " +
							  " hl.leave_from_date,hl.leave_from_session,hl.leave_to_date,hl.leave_to_session," +
							  " personal_data.email,personal_data.mobile_no_2 " +
							  " from hl_leave hl "+
							  " left join hl_admission ON hl.hl_admission_id = hl_admission.id "+
                              " left join course ON hl_admission.course_id = course.id "+
                              " left join hl_hostel ON hl_admission.hostel_id = hl_hostel.id "+
							  "	left join student ON hl_admission.student_id = student.id "+
							  "	left join adm_appln ON student.adm_appln_id = adm_appln.id "+
							  "	left join class_schemewise ON student.class_schemewise_id = class_schemewise.id "+
							  "	left join classes ON class_schemewise.class_id = classes.id  "+
							  "	left join personal_data ON adm_appln.personal_data_id = personal_data.id "+
							  " left join hl_room ON hl_admission.room_id = hl_room.id "+
							  "	where hl.is_active =1 ";
			if(objForm.getHostelId()!=null && !objForm.getHostelId().isEmpty()){
				hqlQuery = hqlQuery + " and hl_hostel.id = "+Integer.parseInt(objForm.getHostelId());
			}	
			if(objForm.getCourseId()!=null && !objForm.getCourseId().isEmpty()){
				hqlQuery = hqlQuery + " and course.id = "+Integer.parseInt(objForm.getCourseId());
			}
			if(objForm.getSemesterNo()!=null && !objForm.getSemesterNo().isEmpty()){
				hqlQuery = hqlQuery + " and classes.term_number = "+Integer.parseInt(objForm.getSemesterNo());
			}
			if(objForm.getFromDate()!=null && objForm.getToDate()!=null){ 
				Date fromDate = CommonUtil.ConvertStringToSQLDate(objForm.getFromDate());
				Date toDate = CommonUtil.ConvertStringToSQLDate(objForm.getToDate());
				hqlQuery = hqlQuery + " and (('"+fromDate+"'  between date_format(hl.leave_from_date,'%Y-%m-%d') and date_format(hl.leave_to_date,'%Y-%m-%d') )" +
				                      " or ('"+toDate+"'  between date_format(hl.leave_from_date,'%Y-%m-%d') and date_format(hl.leave_to_date,'%Y-%m-%d')))";
			}
			if(objForm.getStatus()!=null &&  !objForm.getStatus().isEmpty()){
				if(objForm.getStatus().equalsIgnoreCase("Pending")){
					hqlQuery = hqlQuery + " and (hl.isCanceled =0 or hl.isCanceled is null)" +
										" and (hl.is_rejected  is null or hl.is_rejected =0)" +
										" and (hl.is_approved  is null or hl.is_approved =0) ";
				}else if(objForm.getStatus().trim().equalsIgnoreCase("Rejected")){
					hqlQuery = hqlQuery + " and hl.is_rejected = 1";
				}else if(objForm.getStatus().trim().equalsIgnoreCase("Approved")){
					hqlQuery = hqlQuery + " and hl.is_approved = 1";
				}else if(objForm.getStatus().trim().equalsIgnoreCase("Cancelled")){
					hqlQuery = hqlQuery + " and hl.isCanceled = 1";
				}
			}
			if(objForm.getBlockId()!=null && !objForm.getBlockId().trim().isEmpty()){
				hqlQuery = hqlQuery + " and hl_room.hl_block_id="+Integer.parseInt(objForm.getBlockId());
			}
			if(objForm.getUnitId()!=null && !objForm.getUnitId().trim().isEmpty()){
				hqlQuery = hqlQuery + " and hl_room.hl_unit_id="+Integer.parseInt(objForm.getUnitId());
			}
			Query query = session.createSQLQuery(hqlQuery);
			hlLeavesBo = query.list();
		}catch (Exception e) {
			log.error("Error occured in getLeaveApprovalDetails of HostelLeaveApprovalTxnImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return hlLeavesBo;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveApprovalTransaction#getPreviousLeaveDetailsByRegisterNo(com.kp.cms.forms.hostel.HostelLeaveApprovalForm)
	 */
	@Override
	public Map<String, List<HostelLeaveApprovalTo>> getPreviousLeaveDetailsByRegisterNo( HostelLeaveApprovalForm objForm) throws Exception {
		Session session =null;
		Map<String, List<HostelLeaveApprovalTo>> previousLeaveDetailsMap = new HashMap<String, List<HostelLeaveApprovalTo>>();
		try{
			session = HibernateUtil.getSession();
			if(objForm.getRegNoList()!=null && !objForm.getRegNoList().isEmpty()){
				String sqlQuery = 	" select student.register_no," +
									" hl.leave_from_date,hl.leave_from_session,hl.leave_to_date,hl.leave_to_session," +
									" hl.isCanceled,hl.is_rejected,hl.is_approved,hl.id " +
									" from hl_leave hl "+
									" left join hl_admission ON hl.hl_admission_id = hl_admission.id "+
									" left join student ON hl_admission.student_id = student.id "+
									" where hl.is_active =1 and" +
									" student.register_no in (:regNoList)"+
									" and hl.leave_from_date<'"+CommonUtil.ConvertStringToSQLDate(objForm.getFromDate())+"'";
				Query query = session.createSQLQuery(sqlQuery);
				query.setParameterList("regNoList", objForm.getRegNoList());
				List<Object[]> objList = query.list();
				if(objList!=null && !objList.isEmpty()){
					Iterator<Object[]> iterator = objList.iterator();
					while (iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						if(obj[0]!=null){
							if(previousLeaveDetailsMap.containsKey(obj[0].toString())){
								List<HostelLeaveApprovalTo> list = previousLeaveDetailsMap.get(obj[0].toString());
								HostelLeaveApprovalTo approvalTo = new HostelLeaveApprovalTo();
								approvalTo.setRegisterNo(obj[0].toString());
								if(obj[1]!=null){
									if(obj[2]!=null){
										approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(obj[1].toString())+" ("+obj[2].toString()+")");
									}else{
										approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(obj[1].toString()));
									}
								}
								if(obj[3]!=null){
									if(obj[4]!=null){
										approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(obj[3].toString())+" ("+obj[4].toString()+")");
									}else{
										approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(obj[3].toString()));
									}
								}
								if(obj[5]!=null){
									if(obj[5].toString().equalsIgnoreCase("true")){
										approvalTo.setNoOfLeaveCancelled(1);
									}
								}
								if(obj[6]!=null){
									if(obj[6].toString().equalsIgnoreCase("true")){
										approvalTo.setNoOfLeaveRejected(1);
									}
								}
								if(obj[7]!=null){
									if(obj[7].toString().equalsIgnoreCase("true")){
										approvalTo.setNoOfLeaveApproval(1);
									}
								}
								list.add(approvalTo);
								previousLeaveDetailsMap.put(approvalTo.getRegisterNo(),list);
							}else{
								HostelLeaveApprovalTo approvalTo = new HostelLeaveApprovalTo();
								List<HostelLeaveApprovalTo> list = new ArrayList<HostelLeaveApprovalTo>();
								approvalTo.setRegisterNo(obj[0].toString());
								if(obj[1]!=null){
									if(obj[2]!=null){
										approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(obj[1].toString())+" ("+obj[2].toString()+")");
									}else{
										approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(obj[1].toString()));
									}
								}
								if(obj[3]!=null){
									if(obj[4]!=null){
										approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(obj[3].toString())+" ("+obj[4].toString()+")");
									}else{
										approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(obj[3].toString()));
									}
								}
								if(obj[5]!=null){
									if(obj[5].toString().equalsIgnoreCase("true")){
										approvalTo.setNoOfLeaveCancelled(1);
									}
								}
								if(obj[6]!=null){
									if(obj[6].toString().equalsIgnoreCase("true")){
										approvalTo.setNoOfLeaveRejected(1);
									}
								}
								if(obj[7]!=null){
									if(obj[7].toString().equalsIgnoreCase("true")){
										approvalTo.setNoOfLeaveApproval(1);
									}
								}
								list.add(approvalTo);
								previousLeaveDetailsMap.put(approvalTo.getRegisterNo(),list);
							}
						}
					}
				}
			}
		}catch (Exception e) {
			log.error("Error occured in getLeaveApprovalDetails of HostelLeaveApprovalTxnImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return previousLeaveDetailsMap;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelLeaveApprovalTransaction#setStatus(int)
	 */
	@Override
	public void setStatus(int id,HostelLeaveApprovalForm objForm,String mode) throws Exception {
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			HlLeave hlLeave = (HlLeave) session.get(HlLeave.class, id);
			if(mode.equalsIgnoreCase("Approve")){
				hlLeave.setIsApproved(true);
			}else if(mode.equalsIgnoreCase("Reject")){
				hlLeave.setIsRejected(true);
				hlLeave.setReasons(objForm.getRejectReason());
			}
			hlLeave.setModifiedBy(objForm.getUserId());
			hlLeave.setLastModifiedDate(new Date());
			session.update(hlLeave);
			tx.commit();
		}catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
}
