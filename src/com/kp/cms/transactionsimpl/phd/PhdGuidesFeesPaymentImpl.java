package com.kp.cms.transactionsimpl.phd;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.PhdGuideRemunerations;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;
import com.kp.cms.to.phd.PhdStudentReminderationTO;
import com.kp.cms.transactions.phd.IPhdGuidesFeesPaymentTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


public class PhdGuidesFeesPaymentImpl implements IPhdGuidesFeesPaymentTransactions {
	private static Log log = LogFactory.getLog(PhdGuidesFeesPaymentImpl.class);
	public static volatile PhdGuidesFeesPaymentImpl examCceFactorImpl = null;
	public static PhdGuidesFeesPaymentImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PhdGuidesFeesPaymentImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	@Override
	public List<PhdGuideRemunerations> searchGuidesFeesPayment(Date startDate,Date endDate) throws Exception {
		Session session = null;
		List<PhdGuideRemunerations> guideList;
		try {
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery(" from PhdGuideRemunerations phd where phd.isActive=1" +
			 		" and phd.generatedDate between :startDate and :endDate" ); 
			 query.setDate("startDate", startDate);
			 query.setDate("endDate",endDate);
			 guideList = query.list();
			 return guideList;
		 } catch (Exception e) {
			 throw e;
		 }
	}
	
	@Override
	public boolean saveGuideFeesPayment(PhdStudentReminderationForm objForm) throws Exception{
		Session session = null;
		Transaction transaction=null;
		
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<PhdStudentReminderationTO>  itr= objForm.getGuideFeesPatmentList().iterator();
			while (itr.hasNext()) {
				PhdStudentReminderationTO guideTO = (PhdStudentReminderationTO) itr.next();
				
				String str=(" from PhdGuideRemunerations phd where phd.isActive=1 and phd.id="+guideTO.getId());
				Query query=session.createQuery(str);
				PhdGuideRemunerations documentsub=(PhdGuideRemunerations) query.uniqueResult();
				if(documentsub!=null){
				if(guideTO.getChecked()!=null && guideTO.getChecked().equalsIgnoreCase("on")){
					documentsub.setIsPaid(true);
				}else{
					documentsub.setIsPaid(false);
				}
				if(guideTO.getPaidDate()!=null && !guideTO.getPaidDate().isEmpty()){
					documentsub.setPaidDate(CommonUtil.ConvertStringToDate(guideTO.getPaidDate()));
				}else{
					documentsub.setPaidDate(null);
				}if(guideTO.getPaidMode()!=null && !guideTO.getPaidMode().isEmpty()){
					documentsub.setPaidMode(guideTO.getPaidMode());
				}else{
					documentsub.setPaidMode(null);
				}
				if(guideTO.getOtherRemarks()!=null && !guideTO.getOtherRemarks().isEmpty()){
					documentsub.setOtherRemarks(guideTO.getOtherRemarks());
				}else{
					documentsub.setOtherRemarks(null);
				}
				session.update(documentsub);
				}
			}
			transaction.commit(); 
			return true;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		finally{
			if(session!=null)
				session.close();
		}
		return true;
	}
	@Override
	public List<Object[]> getGuideDetailsById(PhdStudentReminderationForm objForm) throws Exception {
		Session session = null;
		List<Object[]> guideList;
		String str="";
		try {
			 session = HibernateUtil.getSession();
			   str= " select guid.id,student.id as sId,student.register_no,personal_data.first_name,personal_data.middle_name,personal_data.last_name,course.name,course.id as cd," +
			 		" doc.document_name,doc.id as di,DATE_FORMAT(phd_document_submission_schedules.submitted_date,'%d/%m/%Y') as dt," +
			 		" guide.name as gname,guide.id as gid,eguide.first_name as egname,eguide.id as empid," +
			 		" coguide.name as cnname,coguide.id as cid,ecoguide.first_name as empconame,ecoguide.id as empcid," +
			 		" guid.conveyance_charges,guid.other_charges,guid.description,guid.total_charges,crmu.amount,crmu.id as cmid,guid.voucher_no,DATE_FORMAT(guid.generated_date,'%d/%m/%Y') as generatedt," +
			 		" guid.is_paid,DATE_FORMAT(guid.paid_date,'%d/%m/%Y') as gfdt,guid.paid_mode,guid.paid_remarks" +
			 		" from phd_guide_remuneration guid" +
			 		" inner join phd_guide_remuneration_details crmu on crmu.guide_remuneration_id = guid.id" +
			 		" inner join phd_document_details doc on crmu.document_id = doc.id" +
			 		" inner join student ON guid.student_id = student.id" +
			 		" inner join adm_appln ON student.adm_appln_id = adm_appln.id" +
			 		" inner join personal_data ON adm_appln.personal_data_id = personal_data.id" +
			 		" inner join course ON adm_appln.selected_course_id = course.id" +
			 		" inner join phd_document_submission_schedules on phd_document_submission_schedules.course_id = course.id" +
			 		" and phd_document_submission_schedules.student_id = student.id" +
			 		" and phd_document_submission_schedules.document_id = doc.id" +
			 		" left join phd_panel_members guide On guid.guide_id = guide.id" +
			 		" left join phd_panel_members coguide On guid.co_guide_id = coguide.id" +
			 		" left join employee eguide On guid.internal_guide = eguide.id" +
			 		" left join employee ecoguide On guid.internal_CoGuide = ecoguide.id" +
			 		" where phd_document_submission_schedules.is_active=1" +
                    " and guid.voucher_no="+objForm.getId()+
                    " and phd_document_submission_schedules.submitted=1" +
			 		" and phd_document_submission_schedules.guides_fee=1" +
                    " and student.is_hide=0"+
                    " and student.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                    " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))" +
			 		" order by student.register_no,doc.id"; 

			 Query query = session.createSQLQuery(str);
			 guideList = query.list();
			 return guideList;
		 } catch (Exception e) {
			 throw e;
		 }
	}
	@Override
	public boolean updateGuideRemenderation(List<PhdGuideRemunerations> guideBo) throws Exception {
		Session session = null;
		Transaction transaction=null;
		Iterator<PhdGuideRemunerations> itr=guideBo.iterator();
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			while (itr.hasNext()) {
				PhdGuideRemunerations guideRemunerations = (PhdGuideRemunerations) itr.next();
				session.update(guideRemunerations);
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

}
