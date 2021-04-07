package com.kp.cms.transactionsimpl.phd;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdGuideRemunerations;
import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;
import com.kp.cms.to.phd.PhdStudentReminderationTO;
import com.kp.cms.transactions.phd.IPhdStudentRemindetationTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


public class PhdStudentReminderationImpl implements IPhdStudentRemindetationTransactions {
	private static Log log = LogFactory.getLog(PhdStudentReminderationImpl.class);
	public static volatile PhdStudentReminderationImpl examCceFactorImpl = null;
	public static PhdStudentReminderationImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PhdStudentReminderationImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdStudentRemindetationTransactions#studentDetailsSearch(java.util.Date, java.util.Date)
	 */
	@Override
	public List<Object[]> studentDetailsSearch(Date startDate, Date endDate)throws Exception {
		Session session = null;
		List<Object[]> guideList;
		String str="";
		try {
			 session = HibernateUtil.getSession();
			   str= " select student.id,student.register_no,personal_data.first_name,personal_data.middle_name,personal_data.last_name,course.name,course.id as b," +
			 		" guide.name as gname,guide.id as gid,eguide.first_name as egname,eguide.id as empid,coguide.name as cnname,coguide.id as cid,ecoguide.first_name as empconame,ecoguide.id as empcid," +
			 		" phd_document_submission_schedules.guide_fee_generated_date as dt " +
			 		" from student" +
			 		" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
			 		" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
			 		" inner join course ON adm_appln.selected_course_id = course.id" +
			 		" inner join phd_document_submission_schedules on phd_document_submission_schedules.course_id = course.id" +
			 		" and phd_document_submission_schedules.student_id = student.id" +
			 		" inner join phd_document_details ON phd_document_submission_schedules.document_id = phd_document_details.id" +
			 		" inner join phd_student_info on phd_student_info.student_id = student.id" +
			 		" left join phd_panel_members guide On phd_student_info.guide_id = guide.id" +
			 		" left join phd_panel_members coguide On phd_student_info.co_guide_id = coguide.id" +
			 		" left join employee eguide On phd_student_info.internal_guide = eguide.id" +
			 		" left join employee ecoguide On phd_student_info.internal_CoGuide = ecoguide.id" +
                    " where phd_document_submission_schedules.is_active=1" +
                    " and phd_document_submission_schedules.submitted=1" +
			 		" and phd_document_submission_schedules.guides_fee=1" +
                    " and student.is_hide=0 " +
                    " and phd_document_submission_schedules.guide_fee_generated_date is null"+
                    " and student.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                    " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))";
			
			 if(startDate!= null && endDate!=null){
			 str=str +" and phd_document_submission_schedules.submitted_date between :startDate and :endDate " +
			 		 " group by student.register_no" +
			 		 " order by student.register_no,phd_document_details.id";
			 }else{
				 str=str +"  group by student.register_no " +
				 		" order by student.register_no,phd_document_details.id";
			 }
			 Query query = session.createSQLQuery(str);
			 if(startDate!= null && endDate!=null){
				 query.setDate("startDate",startDate);
				 query.setDate("endDate",endDate);
				 }
			 guideList = query.list();
			 session.flush();
			 session.close();
			 return guideList;
		 } catch (Exception e) {
			 throw e;
		 }
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdStudentRemindetationTransactions#generateStudentDetails(com.kp.cms.forms.phd.PhdStudentReminderationForm)
	 */
	@Override
	public List<Object[]> generateStudentDetails(PhdStudentReminderationForm objForm) throws Exception {
		Session session = null;
		List<Object[]> guideList;
		String str="";
		try {
			 session = HibernateUtil.getSession();
			   str= " select doc.document_name,doc.id,round(if(coguide.name is not null or ecoguide.first_name is not null,(doc.guides_fee/2),doc.guides_fee),2) as guides_fee," +
			 		" guide.name as gname,guide.id as gid,eguide.first_name as egname,eguide.id as empid,coguide.name as cnname," +
			 		" coguide.id as cid,ecoguide.first_name as empconame,ecoguide.id as empcid,DATE_FORMAT(phd_document_submission_schedules.submitted_date,'%d/%m/%Y') as dt," +
			 		" student.id as sId,student.register_no,personal_data.first_name,personal_data.middle_name,personal_data.last_name,course.name,course.id as cd," +
			 		" DATE_FORMAT(phd_document_submission_schedules.assign_date,'%d/%m/%Y') as adt" +
			 		" from student" +
			 		" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
			 		" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
			 		" inner join course ON adm_appln.selected_course_id = course.id" +
			 		" inner join phd_document_submission_schedules on phd_document_submission_schedules.course_id = course.id" +
			 		" and phd_document_submission_schedules.student_id = student.id" +
			 		" inner join phd_document_details doc ON phd_document_submission_schedules.document_id = doc.id" +
			 		" inner join phd_student_info on phd_student_info.student_id = student.id" +
			 		" left join phd_panel_members guide On phd_student_info.guide_id = guide.id" +
			 		" left join phd_panel_members coguide On phd_student_info.co_guide_id = coguide.id" +
			 		" left join employee eguide On phd_student_info.internal_guide = eguide.id" +
			 		" left join employee ecoguide On phd_student_info.internal_CoGuide = ecoguide.id" +
                    " where phd_document_submission_schedules.is_active=1" +
                    " and phd_document_submission_schedules.student_id="+objForm.getStudentId()+
                    " and phd_document_submission_schedules.submitted=1" +
			 		" and phd_document_submission_schedules.guides_fee=1" +
                    " and student.is_hide=0"+
                    " and student.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                    " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))" +
                    " and doc.id not in (select phd_guide_remuneration_details.document_id FROM phd_guide_remuneration_details " +
                    " INNER JOIN phd_guide_remuneration ON phd_guide_remuneration_details.guide_remuneration_id =phd_guide_remuneration.id" +
                    " where phd_guide_remuneration.student_id="+objForm.getStudentId()+
                    " group by phd_guide_remuneration_details.document_id)" +
                    " group by doc.id"+
			 		" order by student.register_no,doc.id"; 

			   Query query = session.createSQLQuery(str);
			 guideList = query.list();
			 session.flush();
			 session.close();
			 return guideList;
		 } catch (Exception e) {
			 throw e;
		 }
	}
	@Override
	public boolean saveGuideRemenderation(List<PhdGuideRemunerations> guideBo)	throws Exception {
		Session session = null;
		Transaction transaction=null;
		Iterator<PhdGuideRemunerations> itr=guideBo.iterator();
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			while (itr.hasNext()) {
				PhdGuideRemunerations guideRemunerations = (PhdGuideRemunerations) itr.next();
				session.save(guideRemunerations);
			}
			transaction.commit(); 
			return true;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
			session.close();
			return false;
		}
		finally{
			session.flush();
			session.close();
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdStudentRemindetationTransactions#getPhdVoucherNumber()
	 */
	@Override
	public PhdVoucherNumber getPhdVoucherNumber() throws Exception {
		Session session=null;
		PhdVoucherNumber guideList = null;
		try{
		String query=" from PhdVoucherNumber guidebo where guidebo.isActive=1 and guidebo.currentYear=1 ";
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(query);
		guideList=(PhdVoucherNumber) query2.uniqueResult();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return guideList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdStudentRemindetationTransactions#setUpdateVoucherNo(com.kp.cms.forms.phd.PhdStudentReminderationForm)
	 */
	@Override
	public void setUpdateVoucherNo(PhdStudentReminderationForm objForm)	throws Exception {
		Session session = null;
		Transaction transaction=null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			String str=(" from PhdVoucherNumber guidebo where guidebo.isActive=1 and guidebo.currentYear=1 ");
			Query query=session.createQuery(str);
			PhdVoucherNumber valuation=(PhdVoucherNumber) query.uniqueResult();
			if(valuation!=null){
			valuation.setCurrentNo(objForm.getGeneratedNo());
			session.update(valuation);
		   }
		transaction.commit(); 
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		finally{
			if(session!=null)
				session.flush();
				session.close();
		}
	}
	@Override
	public void setUpdateGeneratedDate(PhdStudentReminderationForm objForm) throws Exception {
		Session session = null;
		Transaction transaction=null;
		Iterator<PhdStudentReminderationTO> itr=objForm.getGuideDetailList().iterator();
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			while (itr.hasNext()) {
				PhdStudentReminderationTO scheduleTO = (PhdStudentReminderationTO) itr.next();
				String str=(" from PhdDocumentSubmissionSchedule phd where phd.isActive=1 and phd.studentId.id='"+scheduleTO.getStudentId()+"'"+
						" and phd.courseId.id='"+scheduleTO.getCourseId()+"'"+
						" and phd.documentId.id='"+scheduleTO.getDocumentId()+"'"+
					    " and phd.submittedDate='"+CommonUtil.ConvertStringToSQLDate(scheduleTO.getSubmissionDate())+"'");
				Query query=session.createQuery(str);
				PhdDocumentSubmissionSchedule documentsub=(PhdDocumentSubmissionSchedule) query.uniqueResult();
				if(documentsub!=null){
				documentsub.setGuideFeeGenerated(new Date());
				session.update(documentsub);
				}
				
			}
			transaction.commit(); 
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		finally{
			if(session!=null)
				session.flush();
				session.close();
			
		}
	}

	
}
