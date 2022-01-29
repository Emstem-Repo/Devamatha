package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.GenerateProcess;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.helpers.admission.AdmissionStatusHelper;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.transactions.admission.IGenerateProcessTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class GenerateProcessTransactionImpl implements
		IGenerateProcessTransaction {
	
	/**
	 * Singleton object of generateProcessTransactionImpl
	 */
	private static volatile GenerateProcessTransactionImpl generateProcessTransactionImpl = null;
	private static final Log log = LogFactory.getLog(GenerateProcessTransactionImpl.class);
	private GenerateProcessTransactionImpl() {
		
	}
	/**
	 * return singleton object of generateProcessTransactionImpl.
	 * @return
	 */
	public static GenerateProcessTransactionImpl getInstance() {
		if (generateProcessTransactionImpl == null) {
			generateProcessTransactionImpl = new GenerateProcessTransactionImpl();
		}
		return generateProcessTransactionImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IGenerateProcessTransaction#getCandidates(java.lang.String)
	 */
	@Override
	public List<AdmAppln> getCandidates(String query) throws Exception {
		Session session = null;
		List<AdmAppln> selectedCandidatesList = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery(query);
			selectedCandidatesList = selectedCandidatesQuery.list();
			return selectedCandidatesList;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IGenerateProcessTransaction#getTemplateDescriptions(java.lang.String, int, int)
	 */
	@Override
	public Map<Integer, String> getTemplateDescriptions(String templateName,
			int courseId, int programId) throws Exception{
		Map<Integer,String> studentMap=new HashMap<Integer, String>();
		Session session = null;
		List<GroupTemplate> student = null;
		try {
			session = HibernateUtil.getSession();
			if(courseId!=0){
			Query selectedCandidatesQuery=session.createQuery("from GroupTemplate g where g.templateName ='"+templateName+"' and g.course.id="+courseId);
			student = selectedCandidatesQuery.list();
			}else{
				Query selectedCandidatesQuery=session.createQuery("from GroupTemplate g where g.templateName ='"+templateName+"' and g.program.id="+programId);
				student = selectedCandidatesQuery.list();
			}
			
			if(student!=null && !student.isEmpty()){
				Iterator<GroupTemplate> itr=student.iterator();
				while (itr.hasNext()) {
					GroupTemplate groupTemplate = (GroupTemplate) itr.next();
					if(groupTemplate.getCourse()!=null)
					studentMap.put(groupTemplate.getCourse().getId(),groupTemplate.getTemplateDescription());
				}
			}
			return studentMap;
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IGenerateProcessTransaction#saveDataToDatabase(java.util.List)
	 */
	@Override
	public boolean saveDataToDatabase(List<AdmissionStatusTO> selectedCandidatesList,String user) throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		AdmissionStatusTO to;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<AdmissionStatusTO> tcIterator = selectedCandidatesList.iterator();
			int count = 0;
			
			while(tcIterator.hasNext()){
				GenerateProcess bo=null;
				to = tcIterator.next();
				bo=(GenerateProcess)session.createQuery("from GenerateProcess g where g.admAppln.id="+to.getId()).uniqueResult();
				if(bo==null){
					bo=new GenerateProcess();
					bo.setCreatedBy(user);
					bo.setCreatedDate(new Date());
				}
				bo.setModifiedBy(user);
				bo.setLastModifiedDate(new Date());
				bo.setApplnNo(to.getApplicationNo());
				bo.setEmail(to.getEmail());
				bo.setStatus(to.getAdmStatus());
				AdmAppln admAppln=new AdmAppln();
				admAppln.setId(to.getId());
				bo.setAdmAppln(admAppln);
				bo.setInterviewComments(to.getInterviewStatus());
				bo.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(to.getDateOfBirth(), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE)));
				bo.setTemplateDescription(to.getFinalTemplate());
				bo.setIsInterviewSelected(to.getIsInterviewSelected());
				bo.setInterviewDate(to.getInterviewDate());
				bo.setInterviewTime(to.getInterviewTime());
				session.saveOrUpdate(bo);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * Passing application no. and gets the admission status from AdmAppln table
	 */
	
	public List<AdmissionStatusTO> getDetailsAdmAppln(String applicationNo)throws Exception {
		log.info("Inside of getDetailsAdmAppln of AdmissionStatusTransactionImpl");
		Session session = null;
		List<AdmAppln> admAppln;
		List<AdmissionStatusTO> statusTo=new ArrayList<AdmissionStatusTO>();
		try {
			//session =InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from AdmAppln adm where adm.applnNo= " + applicationNo +  
					" and appliedYear = (select max(appliedYear) from AdmAppln sub where sub.applnNo = " + applicationNo + ")");
			admAppln = query.list();
			if(!admAppln.isEmpty())
			{
				Iterator<AdmAppln> itr=admAppln.iterator();
				while (itr.hasNext()) {
					AdmAppln admAppln2 = (AdmAppln) itr.next();
					GenerateProcess bo=null;
					bo=(GenerateProcess)session.createQuery("from GenerateProcess g where g.admAppln.id="+admAppln2.getId()).uniqueResult();
					if(bo!=null){
					AdmissionStatusTO to=new AdmissionStatusTO();
					to.setApplicationNo(bo.getApplnNo());
					to.setEmail(bo.getEmail());
					to.setAdmStatus(bo.getStatus());
					to.setId(admAppln2.getId());
					to.setInterviewStatus(bo.getInterviewComments());
					to.setDateOfBirth(CommonUtil.getStringDate(bo.getDateOfBirth()));
					to.setFinalTemplate(bo.getTemplateDescription());
					to.setIsInterviewSelected(bo.getIsInterviewSelected());
					to.setIsSelected(bo.getInterviewComments());
					to.setAppliedYear(admAppln2.getAppliedYear());
					statusTo.add(to);
					}
				}
			}			
		} catch (Exception e) {
		log.error("Exception ocured in getDetailsAdmAppln of AdmissionStatusTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}
		log.info("End of getDetailsAdmAppln of AdmissionStatusTransactionImpl");
		return statusTo;
	}
	@Override
	public void setPhoto(HttpServletRequest request, int id) throws Exception {
		Session session = null;
		ApplnDoc a = null;
		try {
			session = HibernateUtil.getSession();
			a=(ApplnDoc)session.createQuery("from ApplnDoc a where a.isPhoto=1 and a.admAppln.id="+id).uniqueResult();
			HttpSession s=request.getSession();
			if(a!=null){
				s.setAttribute(AdmissionStatusHelper.PHOTOBYTES,a.getDocument());
			}else{
				s.setAttribute("PhotoBytes",null);
				
			}
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public List getAdmAppln(String query1, List<Integer> studentIds)
			throws Exception {
		Session session = null;
		List hallTicketList = null;
		try {
			session = HibernateUtil.getSession();
			 hallTicketList=session.createQuery(query1).setParameterList("ids", studentIds).list();
			return hallTicketList;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
}
