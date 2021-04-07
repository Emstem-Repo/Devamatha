package com.kp.cms.transactionsimpl.examallotment;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentGroupWise;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentGroupWiseTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class ExamRoomAllotmentGroupWiseTxnImpl implements IExamRoomAllotmentGroupWiseTransaction {


  public static volatile ExamRoomAllotmentGroupWiseTxnImpl allotmentGroupWiseTxnImpl=null;
	
  private static final Log log = LogFactory.getLog(ExamRoomAllotmentGroupWiseTxnImpl.class);
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public static ExamRoomAllotmentGroupWiseTxnImpl getInstance(){
		if(allotmentGroupWiseTxnImpl==null){
			allotmentGroupWiseTxnImpl=new ExamRoomAllotmentGroupWiseTxnImpl();
		}
		return allotmentGroupWiseTxnImpl;
	}
	
	public ExamRoomAllotmentGroupWiseTxnImpl() {
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentGroupWiseTransaction#addAllotmentGroupWiseCourses(java.util.List)
	 */
	@Override
	public boolean addAllotmentGroupWiseCourses(List<ExamRoomAllotmentGroupWise> groupWiseList) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean saved=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!groupWiseList.isEmpty()){
			for (ExamRoomAllotmentGroupWise allotmentGroupWise : groupWiseList) {
			    session.save(allotmentGroupWise);
			}
			 saved=true;
			transaction.commit();
			}
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return saved;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentGroupWiseTransaction#getGroupWiseListByMidOrEndAndSchemeNo(java.lang.String, int)
	 */
	@Override
	public List<ExamRoomAllotmentGroupWise> getGroupWiseListByMidOrEndAndSchemeNo(String midOrEndSem,int schemeNo) throws Exception {
		Session session = null;
		List<ExamRoomAllotmentGroupWise> groupWiseList=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from ExamRoomAllotmentGroupWise groupWise where groupWise.isActive=1 and groupWise.midOrEndSem='"+midOrEndSem+"' and groupWise.schemeNo="+schemeNo+" order by groupWise.schemeNo,groupWise.midOrEndSem";
			Query query = session.createQuery(quer);
			groupWiseList = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return groupWiseList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentGroupWiseTransaction#getCourseIdListByMidEndSemAndSchemeNo(java.lang.String, int)
	 */
	@Override
	public List<Integer> getCourseIdListByMidEndSemAndSchemeNo(String midOrEndSem, int schemeNo) throws Exception {
		Session session = null;
		List<Integer> courseIdList=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "select groupWise.course.id from ExamRoomAllotmentGroupWise groupWise where groupWise.isActive=1 and groupWise.midOrEndSem='"+midOrEndSem+"' and groupWise.schemeNo="+schemeNo;
			Query query = session.createQuery(quer);
			courseIdList = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return courseIdList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentGroupWiseTransaction#updateOrDeleteGroupWise(java.util.List)
	 */
	@Override
	public boolean updateOrDeleteGroupWise(List<ExamRoomAllotmentGroupWise> allotmentGroupWiseList)throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean updateOrDelete=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!allotmentGroupWiseList.isEmpty()){
			for (ExamRoomAllotmentGroupWise allotmentGroupWise : allotmentGroupWiseList) {
			    session.merge(allotmentGroupWise);
			}
			updateOrDelete=true;
			transaction.commit();
			}
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return updateOrDelete;
	}
	
	
	
}
