package com.kp.cms.transactionsimpl.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.transactions.phd.IPhdStudyAggrementTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class PhdStudyAggrementImpl implements IPhdStudyAggrementTransactions {
	private static Log log = LogFactory.getLog(PhdStudyAggrementImpl.class);
	public static volatile PhdStudyAggrementImpl examCceFactorImpl = null;
	public static PhdStudyAggrementImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PhdStudyAggrementImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	@Override
	public boolean addStudyAggrement(PhdStudyAggrementBO synopsisBO,PhdStudyAggrementForm objForm,ActionErrors errors, HttpSession sessions) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=true;
		try{
			
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(synopsisBO.getGuideId()!=null || synopsisBO.getCoGuideId()!=null){
		   String str=" from PhdStudyAggrementBO study where study.studentId.id="+synopsisBO.getStudentId().getId();
		   Query qury=session.createQuery(str);
		   PhdStudyAggrementBO agreemet=(PhdStudyAggrementBO) qury.uniqueResult();
		   if(agreemet!=null){
			   synopsisBO.setId(agreemet.getId());
		   }
		 if(agreemet!=null && agreemet.getIsActive()){
				session.merge(synopsisBO);
			}
		 else if(agreemet!=null && !agreemet.getIsActive()){
			 objForm.setId(synopsisBO.getId());
			 throw new ReActivateException(synopsisBO.getId());
		   }
			else if(agreemet!=null && objForm.getId()!=synopsisBO.getId()){
				session.save(synopsisBO);
			  }
			else{
				session.merge(synopsisBO);
			}
		transaction.commit();
		}}
		   catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			if(exception instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.PHD_STUDY_AGREEMENT));
				flag=false;
				sessions.setAttribute("ReactivateId", objForm.getId());
				
			}
			log.debug("Error during saving data...", exception);
			
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
		
	
	}
    /**
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteStudyAggrement(int id)
    throws Exception
{
    Session session = null;
    Transaction transaction = null;
    try
    {
        session = InitSessionFactory.getInstance().openSession();
        String str = (new StringBuilder("from PhdStudyAggrementBO a where a.id=")).append(id).toString();
        PhdStudyAggrementBO examCceFactor = (PhdStudyAggrementBO)session.createQuery(str).uniqueResult();
        transaction = session.beginTransaction();
        examCceFactor.setIsActive(false);
        session.update(examCceFactor);
        transaction.commit();
        session.close();
    }
    catch(Exception e)
    {
        if(transaction != null)
        {
            transaction.rollback();
        }
        log.debug("Error during deleting deleteFeedBackQuestion data...", e);
    }
    return true;
   }
	@Override
	public List<Object[]> getStudentDetails(PhdStudyAggrementForm objForm,ActionErrors errors) throws Exception {
		Session session=null;
		List<Object[]> list=null;
		try {
			session=HibernateUtil.getSession();
			String student=" select per.first_name,per.middle_name,per.last_name,s.id,adm_appln.applied_year,co.name,co.id as co, "+
			               " study.guide_id,guide.empanelment_no,study.co_guide_id,coguide.empanelment_no as no,DATE_FORMAT(study.aggrement_signed_on,'%d/%m/%Y') as dt,study.id as i"+
                           " from student s"+
                           " inner join adm_appln ON s.adm_appln_id = adm_appln.id"+
                           " and s.is_admitted=1"+
                           " and adm_appln.is_cancelled=0"+
                           " inner join personal_data per ON adm_appln.personal_data_id = per.id"+
                           " inner join course co on adm_appln.course_id = co.id"+
                           " left join phd_study_agreement study on study.course_id = co.id"+
                           " and study.student_id = s.id"+
                           " and study.is_active=1" +
                           " left join phd_guide_details guide on study.guide_id = guide.id" +
                           " left join phd_guide_details coguide on study.co_guide_id = coguide.id"+
                           " where s.register_no='"+objForm.getRegisterNo()+"'"+
                           " and s.is_hide=0"+
                           " and s.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                           " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))";
		Query query=session.createSQLQuery(student);
		list=query.list();
		if(list.isEmpty() && !objForm.getRegisterNo().isEmpty()){
		 errors.add("error", new ActionError("knowledgepro.phd.StudyAggrement.valid"));
		}if(list.isEmpty() && objForm.getRegisterNo().isEmpty()){
		 errors.add("error", new ActionError("knowledgepro.phd.StudyAggrement.notBlank"));
		}
		return list;	
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
		
	}
	@Override
	public List<PhdStudyAggrementBO> getPhdStudyAggrement(PhdStudyAggrementForm objForm) throws Exception {
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery(" select phd from PhdStudyAggrementBO phd"
					                       +" join phd.studentId s"
					                       +" where s.registerNo='"+objForm.getRegisterNo()+"'"
			                               +" and phd.isActive=1");
					                      
			List<PhdStudyAggrementBO> list=query.list();
			log.info("end of getPhdSynopsisdefence in PhdStudyAggrementImpl class.");
		    return list;
		} catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}

	@Override
	public boolean reactivateStudyAggrement(PhdStudyAggrementForm objForm)throws Exception {
		log.info("Entering into ValuatorChargesImpl of reactivateDocExamType");
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from PhdStudyAggrementBO subcategory where subcategory.id="+objForm.getId();
		   Query query=session.createQuery(quer);
		   PhdStudyAggrementBO leave=(PhdStudyAggrementBO)query.uniqueResult();
		   leave.setIsActive(true);
		   leave.setModifiedBy(objForm.getUserId());
		   leave.setLastModifiedDate(new Date());
		   session.update(leave);
		   transaction.commit();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdStudyAggrementTransactions#getGuideDetails()
	 */
	@Override
	public List<PhdGuideDetailsBO> getGuideDetails() throws Exception {
		Session session=null;
		List<PhdGuideDetailsBO> guideList = null;
		try{
		String query="from PhdGuideDetailsBO guidebo where guidebo.isActive=1";
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(query);
		guideList=query2.list();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return guideList;
	}
	@Override
	public List<Object[]> guideDetailsSearch(Date startDate, Date endDate)throws Exception {
		Session session = null;
		List<Object[]> guideList;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createSQLQuery(" select student.register_no,personal_data.first_name,personal_data.middle_name,personal_data.last_name,course.name," +
			 		" phd_document_details.document_name,DATE_FORMAT(phd_document_submission_schedules.submitted_date,'%d/%m/%Y') as submitted_date,guide.name as sd,coguide.name as df," +
			 		" student.id ,course.id as b,phd_document_details.id as c,phd_document_submission_schedules.guide_fee_generated_date as dt " +
			 		" from student" +
			 		" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
			 		" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
			 		" inner join course ON adm_appln.course_id = course.id" +
			 		" inner join phd_document_submission_schedules on phd_document_submission_schedules.course_id = course.id" +
			 		" and phd_document_submission_schedules.student_id = student.id" +
			 		" inner join phd_document_details ON phd_document_submission_schedules.document_id = phd_document_details.id" +
			 		" left join phd_study_agreement on phd_study_agreement.course_id = course.id" +
			 		" and phd_study_agreement.student_id = student.id" +
			 		" and phd_study_agreement.is_active=1" +
			 		" left join phd_guide_details guide on phd_study_agreement.guide_id = guide.id" +
                    " left join phd_guide_details coguide on phd_study_agreement.co_guide_id = coguide.id"+
			 		" where phd_document_submission_schedules.is_active=1" +
			 		" and phd_document_submission_schedules.guides_fee=1" +
			 		" and phd_document_submission_schedules.submitted_date between :startDate and :endDate" +
                    " and student.is_hide=0"+
                    " and student.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                    " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))"+
			 		" order by student.register_no,phd_document_details.id"); 
			 query.setDate("startDate", startDate);
			 query.setDate("endDate",endDate);
			 guideList = query.list();
			 session.flush();
			 session.close();
			 return guideList;
		 } catch (Exception e) {
			 throw e;
		 }
	}
	@Override
	public void setUpdateGeneratedDate(PhdDocumentSubmissionScheduleForm objForm) throws Exception {
		Session session = null;
		Transaction transaction=null;
		Iterator<PhdDocumentSubmissionScheduleTO> itr=objForm.getStudentDetailsList().iterator();
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			while (itr.hasNext()) {
				PhdDocumentSubmissionScheduleTO scheduleTO = (PhdDocumentSubmissionScheduleTO) itr.next();
				if(scheduleTO.getChecked()!=null){
				String str=(" from PhdDocumentSubmissionSchedule phd where phd.studentId.id='"+scheduleTO.getStudentId()+"'"+
						" and phd.courseId.id='"+scheduleTO.getCourseId()+"'"+
						" and phd.documentId.id='"+scheduleTO.getDocumentId()+"'"+
						" and submittedDate='"+CommonUtil.ConvertStringToSQLDate(scheduleTO.getSubmittedDate())+"'");
				Query query=session.createQuery(str);
				PhdDocumentSubmissionSchedule documentsub=(PhdDocumentSubmissionSchedule) query.uniqueResult();
				if(documentsub!=null){
				documentsub.setGuideFeeGenerated(new Date());
				session.update(documentsub);
				}
				}
			}
			transaction.commit(); 
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		finally{
			if(session!=null)
				session.close();
		}
	}
	
}
