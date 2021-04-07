package com.kp.cms.transactionsimpl.examallotment;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentPool;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentSettingsPoolWise;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ExamRoomAllotmentPoolTxnImpl implements IExamRoomAllotmentPoolTransaction {
	
	private static volatile ExamRoomAllotmentPoolTxnImpl examRoomAllotmentPoolTxImpl=null;
	private static final Log log = LogFactory.getLog(ExamRoomAllotmentPoolTxnImpl.class);
	 
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public static ExamRoomAllotmentPoolTxnImpl getInstance(){
		if(examRoomAllotmentPoolTxImpl == null){
			examRoomAllotmentPoolTxImpl=new ExamRoomAllotmentPoolTxnImpl();
		}
		return examRoomAllotmentPoolTxImpl;
	}
	
	public ExamRoomAllotmentPoolTxnImpl() {

	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#addPoolNameDetails(com.kp.cms.bo.examallotment.ExamRoomAllotmentPool, java.lang.String)
	 */
	@Override
	public boolean addPoolNameDetails(ExamRoomAllotmentPool examRoomAllotmentPool, String mode) {
		Session session=null;
		Transaction transaction=null;
		boolean addOrUpdate=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
			    session.save(examRoomAllotmentPool);
			    addOrUpdate=true;
			}else{
				session.merge(examRoomAllotmentPool);
			    addOrUpdate=true;
			}
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
		}
		return addOrUpdate;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#duplicateCheckPool(com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm, org.apache.struts.action.ActionErrors, javax.servlet.http.HttpSession)
	 */
	@Override
	public boolean duplicateCheckPool(ExamRoomAllotmentPoolForm allotmentPoolForm,ActionErrors errors,HttpSession hSession)
			throws Exception {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from ExamRoomAllotmentPool pool where pool.poolName='"+allotmentPoolForm.getPoolName()+"'";
			Query query=session.createQuery(quer);
			ExamRoomAllotmentPool allotmentPool=(ExamRoomAllotmentPool)query.uniqueResult();
			if(allotmentPool!=null && !allotmentPool.toString().isEmpty()){
				if(allotmentPoolForm.getId()!=0){
			      if(allotmentPool.getId()==allotmentPoolForm.getId()){
				    flag=false;
			      }else if(allotmentPool.getIsActive()){
				     flag=true;
			         errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.name.exist"));
			       }
			      else{
					   flag=true;
					   allotmentPoolForm.setId(allotmentPool.getId());
					   throw new ReActivateException(allotmentPool.getId());
				   }
				}else if(allotmentPool.getIsActive()){
					flag=true;
			        errors.add("error", new ActionError("knowledgepro.exam.allotment.pool.name.exist"));
				}
				else{
					  flag=true;
					  allotmentPoolForm.setId(allotmentPool.getId());
					  throw new ReActivateException(allotmentPool.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.POOL_NAME_DETAILS_REACTIVATE));
				//saveErrors(request, errors);
				hSession.setAttribute("ReactivateId", allotmentPoolForm.getId());
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#getPoolDetails()
	 */
	@Override
	public List<ExamRoomAllotmentPool> getPoolDetails() throws Exception {
		Session session = null;
		List<ExamRoomAllotmentPool> roomAllotmentPoolsList=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from ExamRoomAllotmentPool pool where pool.isActive=1";
			Query query = session.createQuery(quer);
			roomAllotmentPoolsList = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return roomAllotmentPoolsList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#reactivatePoolDetails(com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm)
	 */
	@Override
	public boolean reactivatePoolDetails(
			ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		log.info("Entering into reactivateVenueDetails");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session=null;
		Transaction transaction=null;
		boolean reactivate=false;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from ExamRoomAllotmentPool pool where pool.id="+allotmentPoolForm.getId();
		   Query query=session.createQuery(quer);
		   ExamRoomAllotmentPool allotmentPool=(ExamRoomAllotmentPool) query.uniqueResult();
		   if(allotmentPool!=null){
		   allotmentPool.setIsActive(true);
		   allotmentPool.setModifiedBy(allotmentPoolForm.getUserId());
		   allotmentPool.setLastModifiedDate(new Date());
		   session.update(allotmentPool);
		   transaction.commit();
		   reactivate=true;
		   }
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return reactivate;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#getPoolDetailsById(int)
	 */
	@Override
	public ExamRoomAllotmentPool getPoolDetailsById(int id) throws Exception {
		Session session = null;
		ExamRoomAllotmentPool roomAllotmentPool=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from ExamRoomAllotmentPool pool where pool.isActive=1 and pool.id="+id;
			Query query = session.createQuery(quer);
			roomAllotmentPool = (ExamRoomAllotmentPool) query.uniqueResult();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return roomAllotmentPool;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#deletePoolDetailsById(int)
	 */
	@Override
	public boolean deletePoolDetailsById(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        boolean flag = false;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from ExamRoomAllotmentPool pool where pool.id="+id+"and pool.isActive=1";
      	    ExamRoomAllotmentPool allotmentPool=(ExamRoomAllotmentPool) session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	    if(allotmentPool!=null){
      	    allotmentPool.setIsActive(false);
      	    session.update(allotmentPool);
      	    transaction.commit();
      	    flag = true;
      	    }
          }catch(Exception e){
        	  flag = false;
      	    if (transaction != null)
      		   transaction.rollback();
      	    
      	log.debug("Error during deleting Venue Details...", e);
          }
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#getCourseListInSpecialisationBySchemeNoAndMidOrEnd(com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm)
	 */
	@Override
	public List<Integer> getCourseListInSpecialisationBySchemeNoAndMidOrEnd(ExamRoomAllotmentPoolForm allotmentPoolForm)
			throws Exception {
		Session session = null;
		List<Integer> courseIdList=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "select special.course.id from ExamRoomAllotmentSpecialization special where special.isActive=1 and special.schemeNo='"+allotmentPoolForm.getSemesterNo()+"' and special.midOrEndSem='"+allotmentPoolForm.getMidOrEndSem()+"'";
			Query query = session.createQuery(quer);
			courseIdList = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return courseIdList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#getCourseListOfPoolBySchemeNoAndMidOrEnd(com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm)
	 */
	@Override
	public List<Integer> getCourseListOfPoolBySchemeNoAndMidOrEnd(ExamRoomAllotmentPoolForm allotmentPoolForm)
			throws Exception {
		Session session = null;
		List<Integer> courseIdList=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "select poolWise.course.id from ExamRoomAllotmentSettingsPoolWise poolWise where poolWise.isActive=1 and poolWise.schemeNo='"+allotmentPoolForm.getSemesterNo()+"' and poolWise.midOrEndSem='"+allotmentPoolForm.getMidOrEndSem()+"'";
			Query query = session.createQuery(quer);
			courseIdList = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return courseIdList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#addExamRoomAllotMentPoolWise(java.util.List)
	 */
	@Override
	public boolean addExamRoomAllotMentPoolWise(
			List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWiseList)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean saved=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!allotmentSettingsPoolWiseList.isEmpty()){
			for (ExamRoomAllotmentSettingsPoolWise examRoomAllotmentSettingsPoolWise : allotmentSettingsPoolWiseList) {
			    session.save(examRoomAllotmentSettingsPoolWise);
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
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#getExamRoomAllotPoolWiseList(java.lang.String)
	 */
	@Override
	public List<ExamRoomAllotmentSettingsPoolWise> getExamRoomAllotPoolWiseList(String midOrEndSem)
			throws Exception {
		Session session = null;
		List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWises=null;
		try{
			session = HibernateUtil.getSession();
			String quer= " from ExamRoomAllotmentSettingsPoolWise poolWise where poolWise.isActive=1 and poolWise.midOrEndSem='"+midOrEndSem+"' order by poolWise.schemeNo,poolWise.allotmentPool.poolName";
			Query query = session.createQuery(quer);
			allotmentSettingsPoolWises = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return allotmentSettingsPoolWises;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#getRoomAllotPoolWoseListForUpdate(com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm)
	 */
	@Override
	public List<ExamRoomAllotmentSettingsPoolWise> getRoomAllotPoolWoseListForUpdate(
			ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		Session session = null;
		List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWiseList=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from ExamRoomAllotmentSettingsPoolWise poolWise where poolWise.isActive=1 and poolWise.schemeNo='"+allotmentPoolForm.getSemesterNo()+"' and poolWise.midOrEndSem='"+allotmentPoolForm.getMidOrEndSem()+"' and poolWise.allotmentPool.id="+allotmentPoolForm.getPoolName();
			Query query = session.createQuery(quer);
			allotmentSettingsPoolWiseList = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return allotmentSettingsPoolWiseList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#updateRoomAllotMentPoolWise(java.util.List)
	 */
	@Override
	public boolean updateRoomAllotMentPoolWise(
			List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWises)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isUpdated=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(!allotmentSettingsPoolWises.isEmpty()){
			for (ExamRoomAllotmentSettingsPoolWise examRoomAllotmentSettingsPoolWise : allotmentSettingsPoolWises) {
			    session.saveOrUpdate(examRoomAllotmentSettingsPoolWise);
			}
			 isUpdated=true;
			transaction.commit();
			}
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
		}
		return isUpdated;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction#getCOurseIdFromOtherPool(com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm, java.util.List)
	 */
	@Override
	public List<Integer> getCOurseIdFromOtherPool(
			ExamRoomAllotmentPoolForm allotmentPoolForm, List<Integer> courseIdList)
			throws Exception {
		
		Session session = null;
		List<Integer> otherPoolCourseIdList=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "select pool.course.id from ExamRoomAllotmentSettingsPoolWise pool where pool.isActive=1 "+ 
                         " and pool.schemeNo='"+allotmentPoolForm.getSemesterNo()+"' and pool.midOrEndSem='"+allotmentPoolForm.getMidOrEndSem()+"' and pool.course.id not in(:courseIDs)";
			Query query = session.createQuery(quer);
			query.setParameterList("courseIDs", courseIdList);
			otherPoolCourseIdList = query.list();
			
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
			throw new ApplicationException();
		}
		return otherPoolCourseIdList;
	}

}
