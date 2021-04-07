package com.kp.cms.transactionsimpl.phd;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PhdDocumentSubmissionScheduleTransactionImpl implements IPhdDocumentSubmissionSchedule{
	private static final Log log = LogFactory .getLog(PhdDocumentSubmissionScheduleTransactionImpl.class);
 private static volatile PhdDocumentSubmissionScheduleTransactionImpl transactionImpl = null;
 private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
 public static PhdDocumentSubmissionScheduleTransactionImpl getInstance(){
	 if(transactionImpl == null){
		 transactionImpl = new PhdDocumentSubmissionScheduleTransactionImpl();
		 return transactionImpl;
	 }
	 return transactionImpl;
 }
/* (non-Javadoc)
 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#getDocumentSubmissionScheduleList(com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm)
 */
@Override
	public List<Object[]> getDocumentSubmissionScheduleList(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm)
			throws Exception {
		log.info("call of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
		Session session = null;
		ArrayList<Integer> courseId = new ArrayList<Integer>();
		if(documentSubmissionScheduleForm.getSelectedcourseId()!=null){
		String[] co=documentSubmissionScheduleForm.getSelectedcourseId();
		for (int i = 0; i <co.length; i++) {
			if(!co[i].isEmpty()){
			courseId.add(Integer.parseInt(co[i]));
			}
		     }
		}
		List<Object[]> documentSubmission =null;
		try{
			session = HibernateUtil.getSession(); 
			String str= " select course.name,student.register_no,personal_data.first_name," +
						" DATE_FORMAT(min(fee_payment.fee_paid_date),'%d/%m/%Y') as fee_paid_date," +
						" student.id,course.id as id1,phd_document_submission_schedules.student_id , " +
						" max(phd_document_submission_schedules.is_active) as is_active, " +
						" phd_student_info.student_id as yt,phd_student_info.viva_voce_date,phd_student_info.title" +
			           " from student "+
                       " inner join adm_appln ON student.adm_appln_id = adm_appln.id "+
                       " and student.is_admitted=1"+
                       " and adm_appln.is_cancelled=0"+
                       " inner join course ON adm_appln.selected_course_id = course.id"+
                       " inner join personal_data ON adm_appln.personal_data_id = personal_data.id"+
                       " inner join program ON course.program_id = program.id"+
                       " inner join program_type ON program.program_type_id = program_type.id"+
                       " inner join fee_payment on fee_payment.student_id = student.id"+
                       " and fee_payment.is_challan_canceled=0"+
                       " and fee_payment.is_fee_paid=1"+
                       " left join phd_document_submission_schedules on phd_document_submission_schedules.course_id = course.id"+
                       " and phd_document_submission_schedules.student_id = student.id" +
                       " and phd_document_submission_schedules.is_active=1"+
                       " left join phd_student_info on phd_student_info.student_id = student.id" +
                       " and phd_student_info.is_active=1"+
                       " where adm_appln.applied_year="+documentSubmissionScheduleForm.getYear()+
                       " and  program_type.id="+documentSubmissionScheduleForm.getProgramTypeId() +
                       " and student.is_hide=0"+
                       " and student.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                       " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))";
			
			if(courseId==null || courseId.isEmpty()){
				str=str+" group by student.register_no,course.id"
				       +" order by student.register_no,course.id";
			}     
			if(courseId!=null && !courseId.isEmpty()){
				str=str+" and course.id in (:courses)"+
				      " group by student.register_no,course.id"+
				       " order by student.register_no,course.id";
			}
			Query query = session.createSQLQuery(str);
			if(courseId!=null && !courseId.isEmpty()){
			query.setParameterList("courses", courseId);
			}
			documentSubmission= query.list();
			log.info("end of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
			return documentSubmission;
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
		 }
		
		
}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#getCoursesByProgramTypes(java.lang.String)
	 */
	@Override
	public List<Object[]> getCoursesByProgramTypes(String programTypeId)throws Exception {
		Session session=null;
		List<Object[]> courseList=null;
		try {
			session=HibernateUtil.getSession();
			String str=" select cou.name,cou.id,pro.name from Course cou"+
			           " join cou.program pro"+
			           " join pro.programType ptype"+
			           " where ptype.id='"+programTypeId+"'"+
			           " and cou.isActive=1"+
			           " order by pro.name";
			Query query=session.createQuery(str);
			courseList=query.list();
			return courseList;
		} catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(e);
		 }
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#getDocumentDetailsList()
	 */
	@Override
	public List<DocumentDetailsBO> getDocumentDetailsList() throws Exception {
		Session session=null;
		List<DocumentDetailsBO> documentDetailsList = null;
		try{
		String query="from DocumentDetailsBO documentbo where documentbo.isActive=1"+
		             " order by documentbo.submissionOrder";
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(query);
		documentDetailsList=query2.list();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return documentDetailsList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#addSubCategory(java.util.List, java.lang.String)
	 */
	@Override
	public boolean addSubCategory(List<PhdDocumentSubmissionSchedule> documentDetails, String mode) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=true;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			Iterator<PhdDocumentSubmissionSchedule> itr=documentDetails.iterator();
			while (itr.hasNext()) {
			PhdDocumentSubmissionSchedule object = (PhdDocumentSubmissionSchedule) itr.next();
			transaction.begin();
			java.sql.Date currentDate = CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(object.getAssignDate()),PhdDocumentSubmissionScheduleTransactionImpl.SQL_DATEFORMAT,PhdDocumentSubmissionScheduleTransactionImpl.FROM_DATEFORMAT));
			int sId=object.getStudentId().getId();
			int dId=object.getDocumentId().getId();
			String str=" from PhdDocumentSubmissionSchedule documentbo "+
            " where documentbo.documentId.id="+dId+
            " and documentbo.studentId.id="+sId+
            " and documentbo.assignDate='"+currentDate+"'";
            Query query= session.createQuery(str);
            PhdDocumentSubmissionSchedule document=(PhdDocumentSubmissionSchedule) query.uniqueResult();
            if(document!=null){
            	object.setId(document.getId());
            }
           if(document==null){
        	   if(object.getId()!=0){
        		   session.merge(object);
        	   }
        	   else if(mode.equalsIgnoreCase("Add")){
					session.save(object);
				}
				else if(mode.equalsIgnoreCase("Edit")){
					session.merge(object);
				}
			}else {
				session.merge(object);
			}
			transaction.commit();
		}}catch(Exception exception){
			if (transaction != null)
				flag=false;
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
		
	
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#getStudentDetailsById(int)
	 */
	@Override
	public List<PhdDocumentSubmissionSchedule> getStudentDetailsById(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception {
		Session session=null;
		List<PhdDocumentSubmissionSchedule> documentSubmission=null;
		try{
			session=HibernateUtil.getSession();
			String str="from PhdDocumentSubmissionSchedule subCategory " 
			+" where subCategory.studentId.id="+documentSubmissionScheduleForm.getId()
			+" and subCategory.isActive=1"
			+" order by subCategory.documentId,subCategory.studentId";
			Query query=session.createQuery(str);
			documentSubmission=query.list();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
		}
		return documentSubmission;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#getDocumentDetails(com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm)
	 */
	@Override
	public DocumentDetailsBO getDocumentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm)	throws Exception {
		Session session=null;
		DocumentDetailsBO document=null;
		try{
		session=HibernateUtil.getSession();
		String str=" from DocumentDetailsBO documentbo where documentbo.isActive=1"+
		             " and documentbo.id="+documentSubmissionScheduleForm.getDocumentId()+
		             " order by documentbo.submissionOrder";
		Query query= session.createQuery(str);
		document=(DocumentDetailsBO) query.uniqueResult();
		session.close();
		}
		catch (Exception e) {
			session.flush();
			session.close();
		}
		return document;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#getDocumentDetailsBo(com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm)
	 */
	@Override
	public List<DocumentDetailsBO> getDocumentDetailsBo(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) {
		Session session=null;
		List<DocumentDetailsBO> documentDetailsList = null;
		try{
		String query="from DocumentDetailsBO documentbo where documentbo.isActive=1"+
		             " and documentbo.id="+documentSubmissionScheduleForm.getDocumentId()+
                     " order by documentbo.submissionOrder";
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(query);
		documentDetailsList=query2.list();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return documentDetailsList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionSchedule#getDocumentList(com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm)
	 */
	@Override
	public List<DocumentDetailsBO> getDocumentList(	PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception {
		log.info("call of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
		Session session = null;
		ArrayList<Integer> courseId = new ArrayList<Integer>();
		if(documentSubmissionScheduleForm.getSelectedcourseId()!=null){
		String[] co=documentSubmissionScheduleForm.getSelectedcourseId();
		for (int i = 0; i <co.length; i++) {
			if(!co[i].isEmpty()){
				courseId.add(Integer.parseInt(co[i]));
				}
		     }
		}
		List<DocumentDetailsBO> documentSubmission =null;
		try{
			session = HibernateUtil.getSession(); 
			String str= " select doc "+
			           " from PhdDocumentSubmissionSchedule phddocu"+
			           " join phddocu.documentId doc"+
                       " join phddocu.studentId stu"+
                       " join stu.admAppln adm"+
                       " join adm.courseBySelectedCourseId cou"+
                       " join cou.program pro"+
                       " join pro.programType ptype"+
                       " join stu.feePayments fee"+
                       " where adm.appliedYear="+documentSubmissionScheduleForm.getYear()+
                       " and ptype.id="+documentSubmissionScheduleForm.getProgramTypeId()+
                       " and stu.isAdmitted=1"+
                       " and adm.isCancelled=0"+
                       " and fee.isCancelChallan=0"+
                       " and fee.isFeePaid=1"+
                       " and phddocu.isActive=1";
			
			if(courseId==null || courseId.isEmpty()){
				str=str+" group by doc.id ";
			}     
			if(courseId!=null && !courseId.isEmpty()){
				str=str+" and cou.id in (:courses)"+
				       " group by doc.id";
			}
			Query query = session.createQuery(str);
			if(courseId!=null && !courseId.isEmpty()){
			query.setParameterList("courses", courseId);
			}
			documentSubmission= query.list();
			session.flush();
			session.close();
			log.info("end of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
			return documentSubmission;
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
		 }
		
       }
	@Override
	public List<Object[]> getDocumentSubmissionSchedule(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm)
			throws Exception {
		log.info("call of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
		Session session = null;
		ArrayList<Integer> courseId = new ArrayList<Integer>();
		if(documentSubmissionScheduleForm.getSelectedcourseId()!=null){
		String[] co=documentSubmissionScheduleForm.getSelectedcourseId();
		for (int i = 0; i <co.length; i++) {
			if(!co[i].isEmpty()){
				courseId.add(Integer.parseInt(co[i]));
				}
		     }
		}
		List<Object[]> documentSubmission =null;
		try{
			session = HibernateUtil.getSession(); 
			String str= " select course.name,student.register_no,personal_data.first_name,DATE_FORMAT(min(fee_payment.fee_paid_date),'%d/%m/%Y') as fee_paid_date" +
					",student.id,course.id as id1,phd_document_submission_schedules.student_id ,max(phd_document_submission_schedules.is_active) as is_active ," +
					" phd_student_info.student_id as yt,phd_student_info.viva_voce_date,phd_student_info.title " +
			           " from student "+
                       " inner join adm_appln ON student.adm_appln_id = adm_appln.id "+
                       " and student.is_admitted=1"+
                       " and adm_appln.is_cancelled=0"+
                       " inner join course ON adm_appln.selected_course_id = course.id"+
                       " inner join personal_data ON adm_appln.personal_data_id = personal_data.id"+
                       " inner join program ON course.program_id = program.id"+
                       " inner join program_type ON program.program_type_id = program_type.id"+
                       " inner join fee_payment on fee_payment.student_id = student.id"+
                       " and fee_payment.is_challan_canceled=0"+
                       " and fee_payment.is_fee_paid=1"+
                       " left join phd_document_submission_schedules on phd_document_submission_schedules.course_id = course.id"+
                       " and phd_document_submission_schedules.student_id = student.id"+
                       " and phd_document_submission_schedules.is_active=1"+
                       " left join phd_student_info on phd_student_info.student_id = student.id" +
                       " and phd_student_info.is_active=1"+
                       " where adm_appln.applied_year="+documentSubmissionScheduleForm.getYear()+
                       " and  program_type.id="+documentSubmissionScheduleForm.getProgramTypeId() +
                       " and student.is_hide=0"+
                       " and student.id not in (select student_id from EXAM_student_detention_rejoin_details "+
                       " where ((detain=1) or(discontinued=1)) and ((rejoin=0) or (rejoin is null)))";
			
			if(courseId==null || courseId.isEmpty()){
				str=str+" group by student.register_no,course.id"
				       +" order by student.register_no,course.id";
			}     
			if(courseId!=null && !courseId.isEmpty()){
				str=str+" and course.id in (:courses)"+
				      " group by student.register_no,course.id"+
				       " order by student.register_no,course.id";
			}
			Query query = session.createSQLQuery(str);
			if(courseId!=null && !courseId.isEmpty()){
			query.setParameterList("courses", courseId);
			}
			documentSubmission= query.list();
			session.flush();
			log.info("end of getDocumentSubmissionScheduleList method in PhdDocumentSubmissionScheduleTransactionImpl class.");
			return documentSubmission;
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
		 }
		
		
}
	@Override
	public boolean deletePhdDocumentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm,String mode) throws Exception {
		Session session=null;
        Transaction transaction=null;
        List<PhdDocumentSubmissionSchedule> documentSubmission=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	   session=HibernateUtil.getSession();
      	   String str="";
      	   if(mode.equalsIgnoreCase("documentList")){
			str="from PhdDocumentSubmissionSchedule subCategory where subCategory.isActive=1 and subCategory.id="+documentSubmissionScheduleForm.getId();
      	   }else{
      	   str="from PhdDocumentSubmissionSchedule subCategory where subCategory.isActive=1 and subCategory.studentId.id="+documentSubmissionScheduleForm.getId();
      	   }
			Query query=session.createQuery(str);
			documentSubmission=query.list();
      	    transaction=session.beginTransaction();
      	    Iterator<PhdDocumentSubmissionSchedule> document=documentSubmission.iterator();
      	    while (document.hasNext()) {
				PhdDocumentSubmissionSchedule phdDocument = (PhdDocumentSubmissionSchedule) document.next();
				phdDocument.setIsActive(false);
				session.update(phdDocument);
			}
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting ValuatorCharges data...", e);
      	   }
       return true;
		}
	@Override
	public List<PhdDocumentSubmissionSchedule> getDocumentSubmissionByReg(PhdDocumentSubmissionScheduleForm objForm) throws Exception {
		Session session=null;
        Transaction transaction=null;
        List<PhdDocumentSubmissionSchedule> documentSubmited=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    session=HibernateUtil.getSession();
			String str=" from PhdDocumentSubmissionSchedule document where document.studentId.registerNo='"+objForm.getRegisterNo()+"'"
			+" and document.isActive=1"
			+" order by document.documentId.id";;
			Query query=session.createQuery(str);
			documentSubmited=query.list();
			return documentSubmited;
          }catch(Exception e){
			 if( session != null){
				 session.flush();
			 }
			 throw new ApplicationException(e);
		 }
	}
	@Override
	public boolean addDocumentList(List<PhdDocumentSubmissionSchedule> documentList) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			Iterator<PhdDocumentSubmissionSchedule> itr=documentList.iterator();
			while (itr.hasNext()) {
			PhdDocumentSubmissionSchedule object = (PhdDocumentSubmissionSchedule) itr.next();
			transaction.begin();
			session.update(object);
			transaction.commit();
		}}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;
	}
	@Override
	public List<PhdDocumentSubmissionSchedule> getPendingDocumentSearch(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception {
		log.info("call of getPendingDocumentSearch method in PhdDocumentSubmissionScheduleTransactionImpl class.");
		Session session = null;
		ArrayList<Integer> courseId = new ArrayList<Integer>();
		if(documentSubmissionScheduleForm.getSelectedcourseId()!=null){
		String[] co=documentSubmissionScheduleForm.getSelectedcourseId();
		for (int i = 0; i <co.length; i++) {
			if(!co[i].isEmpty()){
			courseId.add(Integer.parseInt(co[i]));
			}
		     }
		}
		List<PhdDocumentSubmissionSchedule> pendingDocument =null;
		try{
			session = HibernateUtil.getSession(); 
			String str= " from PhdDocumentSubmissionSchedule documet " +
                       " where documet.submited=0 "+
                       " and documet.isActive=1 "+
                       " and documet.assignDate <='"+CommonUtil.ConvertStringToSQLDate(documentSubmissionScheduleForm.getStartDate())+"'"+
                       " and documet.studentId.admAppln.appliedYear="+documentSubmissionScheduleForm.getYear();
			
			if(courseId!=null && !courseId.isEmpty()){
				str=str+" and documet.studentId.admAppln.courseBySelectedCourseId.id in (:courses)" +
						" order by documet.documentId.id,documet.studentId.registerNo" ;
			}if(courseId==null || courseId.isEmpty()){
				str=str+" order by documet.documentId.id,documet.studentId.registerNo" ;
			}
			Query query = session.createQuery(str);
			if(courseId!=null && !courseId.isEmpty()){
			query.setParameterList("courses", courseId);
			}
			pendingDocument= query.list();
			log.info("end of getPendingDocumentSearch method in PhdDocumentSubmissionScheduleTransactionImpl class.");
			return pendingDocument;
		}catch (Exception exception) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(exception);
		 }
		
		
}
	
	}
