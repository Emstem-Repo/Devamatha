package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HlAdmissionForm;
import com.kp.cms.transactions.hostel.IHostelWaitingListViewTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelWaitingListViewTransactionImpl implements IHostelWaitingListViewTransaction {

	private static final Log log=LogFactory.getLog(HlAdmissionImpl.class);
	
	@Override
	public List<HlAdmissionBookingWaitingBo> SearchStudentsInHostel(
			HlAdmissionForm hlAdmissionForm) throws Exception {
		
		
		Session session=null;
		String str="";
		List<HlAdmissionBookingWaitingBo> list=null;
		try {
			session=HibernateUtil.getSession();
			str="select bo from HlAdmissionBookingWaitingBo bo where bo.isActive=1";
		    if(hlAdmissionForm.getYear()!=null && !hlAdmissionForm.getYear().isEmpty()){
		    	str=str+" and bo.academicYear='"+hlAdmissionForm.getYear()+"'";
		    }if(hlAdmissionForm.getHostelName()!=null && !hlAdmissionForm.getHostelName().isEmpty()){
				str=str+" and bo.hostelId='"+hlAdmissionForm.getHostelName()+"'";
			}if(hlAdmissionForm.getRoomTypeName()!=null && !hlAdmissionForm.getRoomTypeName().isEmpty()){
				str=str+" and bo.roomTypeId='"+hlAdmissionForm.getRoomTypeName()+"'";
			}
			str=str+" order by bo.waitingListPriorityNo,bo.roomTypeId ";
			list=session.createQuery(str).list();
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
		
			return list;
	}

	@Override
	public String getHostelNameById(int hostelId) throws Exception {
		Session session=null;
		String str="";
		String hostelName=null;
		try {
			session=HibernateUtil.getSession();
			str="select hostel.name from HlHostel hostel where hostel.id="+hostelId+"and hostel.isActive=1";
			hostelName=(String) session.createQuery(str).uniqueResult();
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
		
			return hostelName;
	}

	@Override
	public String getRoomTypeName(int roomTypeId) throws Exception {
		Session session=null;
		String str="";
		String roomTypeName=null;
		try {
			session=HibernateUtil.getSession();
			str="select room.name from HlRoomType room where room.id="+roomTypeId+" and room.isActive=1";
			roomTypeName=(String) session.createQuery(str).uniqueResult();
			}catch (Exception e) {
				if (session != null){
					session.flush();
					//session.close();
				}	
				throw  new ApplicationException(e);
			}
		
			return roomTypeName;
	}

	@Override
	public boolean intimatedToStudent(int id) throws Exception  {
		Session session=null;
		Transaction transaction=null;
		boolean updateWaitingList=false;
		HlAdmissionBookingWaitingBo bookingWaitingBo;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			Query query=session.createQuery("from HlAdmissionBookingWaitingBo bo where bo.id="+id+" and bo.isActive=1");
			bookingWaitingBo=(HlAdmissionBookingWaitingBo) query.uniqueResult();
			if(bookingWaitingBo!=null)
			{
			 bookingWaitingBo.setIntimatedDate(new Date());
			 session.merge(bookingWaitingBo);
			 transaction.commit();
			}
			updateWaitingList=true;
		}catch(Exception e){
			 log.error("Error in resetStudentInWaitingList...",e);
				 session.flush();
				 session.close();
				 throw  new ApplicationException(e);
		}
		return updateWaitingList;
	}

}
