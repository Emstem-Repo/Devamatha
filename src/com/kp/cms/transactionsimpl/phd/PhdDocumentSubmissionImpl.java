package com.kp.cms.transactionsimpl.phd;

import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.PhdConference;
import com.kp.cms.bo.phd.PhdDocumentSubmissionBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.bo.phd.PhdResearchDescription;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.bo.phd.PhdStudentPanelMember;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionForm;
import com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class PhdDocumentSubmissionImpl implements IPhdDocumentSubmissionTransactions {
	private static Log log = LogFactory.getLog(PhdDocumentSubmissionImpl.class);
	public static volatile PhdDocumentSubmissionImpl documentSubmissionImpl = null;
	public static PhdDocumentSubmissionImpl getInstance() {
		if (documentSubmissionImpl == null) {
			documentSubmissionImpl = new PhdDocumentSubmissionImpl();
			return documentSubmissionImpl;
		}
		return documentSubmissionImpl;
	}
	@Override
	public boolean addPhdDocumentSubmission(PhdDocumentSubmissionBO synopsisBO,PhdDocumentSubmissionForm objForm,ActionErrors errors, HttpSession sessions) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=true;
		try{
			
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(synopsisBO);
		    transaction.commit();
		    }
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
    @Override
	public List<Object[]> getStudentDetails(PhdDocumentSubmissionForm objForm,ActionErrors errors) throws Exception {
		Session session=null;
		List<Object[]> list=null;
		try {
			session=HibernateUtil.getSession();
			String student=" select per.first_name,per.middle_name,per.last_name,s.id,adm_appln.applied_year,co.name,co.id as co, "+
			               " submission.guide_id,submission.co_guide_id,DATE_FORMAT(submission.aggrement_signed_on,'%d/%m/%Y') as dt,submission.id as sub," +
			               " DATE_FORMAT(submission.viva_voce_date,'%d/%m/%Y') as da,submission.title,phd_discipline.id as di,submission.internal_guide,submission.internal_CoGuide"+
                           " from student s"+
                           " inner join adm_appln ON s.adm_appln_id = adm_appln.id"+
                           " and s.is_admitted=1"+
                           " and adm_appln.is_cancelled=0"+
                           " inner join personal_data per ON adm_appln.personal_data_id = per.id"+
                           " inner join course co on adm_appln.course_id = co.id"+
                           " left join phd_student_info submission on submission.student_id= s.id"+
                           " and submission.is_active=1" +
                           " left join phd_discipline on submission.discipline=phd_discipline.id" +
                           " and phd_discipline.is_active=1" +
                           " left join phd_guide_details guide on submission.guide_id = guide.id" +
                           " left join phd_guide_details coguide on submission.co_guide_id = coguide.id"+
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdStudyAggrementTransactions#getGuideDetails()
	 */
	@Override
	public List<PhdEmployee> getGuideDetails() throws Exception {
		Session session=null;
		List<PhdEmployee> guideList = null;
		try{
		session=HibernateUtil.getSession();
		String strr="from PhdEmployee guidebo where guidebo.isActive=1";
		Query query= session.createQuery(strr);
		guideList=query.list();
		}
		catch (Exception e) {
			session.flush();
			session.close();
		}
		return guideList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions#getCoGuideDetails()
	 */
	@Override
	public List<PhdEmployee> getCoGuideDetails() throws Exception {
		Session session=null;
		List<PhdEmployee> guideList = null;
		try{
		session=HibernateUtil.getSession();
		String strr="from PhdEmployee guidebo where guidebo.isActive=1";
		Query query= session.createQuery(strr);
		guideList=query.list();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return guideList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions#guideDetailsSearch(java.util.Date, java.util.Date)
	 */
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions#getDocumentSubmissionByReg(com.kp.cms.forms.phd.PhdDocumentSubmissionForm)
	 */
	@Override
	public List<PhdDocumentSubmissionSchedule> getDocumentSubmissionByReg(PhdDocumentSubmissionForm objForm) throws Exception {
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions#getResearchPublication()
	 */
	@Override
	public List<PhdResearchPublication> getResearchPublication()throws Exception {
		Session session=null;
		List<PhdResearchPublication> guideList = null;
		try{
		String query="from PhdResearchPublication guidebo where guidebo.isActive=1";
		session=HibernateUtil.getSession();
		Query que= session.createQuery(query);
		guideList=que.list();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return guideList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions#getPanelNamebyId(com.kp.cms.forms.phd.PhdDocumentSubmissionForm)
	 */
	@Override
	public String getPanelNameById(int id,PhdDocumentSubmissionForm objForm,String mode)throws Exception {
		Session session=null;
		String name="";
		String Strr="";
		try{
		if(objForm.isPanelGuideDisplay() && mode.equalsIgnoreCase("synopsis")){
			Strr="select guidebo.firstName from Employee guidebo where guidebo.isActive=1 and guidebo.id="+id;
		}else if(objForm.isFinalGuideDisplay() && mode.equalsIgnoreCase("viva")){
			Strr="select guidebo.firstName from Employee guidebo where guidebo.isActive=1 and guidebo.id="+id;
		}else{
			Strr="select guidebo.name from PhdEmployee guidebo where guidebo.isActive=1 and guidebo.id="+id;
		}
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(Strr);
		name=(String) query2.uniqueResult();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return name;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions#guideShipMap()
	 */
	@Override
	public Map<String, String> guideShipMap() throws Exception {
		Session session=null;
		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from DisciplineBo r where r.isActive=true");
			List<DisciplineBo> list=query.list();
			if(list!=null){
				Iterator<DisciplineBo> iterator=list.iterator();
				while(iterator.hasNext()){
					DisciplineBo discipline=iterator.next();
					if(discipline!=null && discipline.getName()!=null && !discipline.getName().isEmpty() && discipline.getId()>0)
					map.put(String.valueOf(discipline.getId()),discipline.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
}
	@Override
	public PhdDocumentSubmissionBO getSubmissionDetails(PhdDocumentSubmissionForm objForm, ActionErrors errors)throws Exception {
		Session session=null;
        Transaction transaction=null;
       PhdDocumentSubmissionBO documentSubmited=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    session=HibernateUtil.getSession();
			String str=" from PhdDocumentSubmissionBO document where document.studentId.registerNo='"+objForm.getRegisterNo()+"'"
			+" and document.isActive=1";
			Query query=session.createQuery(str);
			documentSubmited=(PhdDocumentSubmissionBO) query.uniqueResult();
			return documentSubmited;
          }catch(Exception e){
			 if( session != null){
				 session.flush();
			 }
			 throw new ApplicationException(e);
		 }
	}
	@Override
	public boolean addDocumentList(
			List<PhdDocumentSubmissionSchedule> documentList) throws Exception {
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
	public boolean deletesynopsisList(int id, String userId,String mode) throws Exception {
    	log.info("call of deletesynopsisList in PhdStudentPanelMember class.");
        Session session = null;
        boolean isDeleted = false;
        Transaction transaction = null;
        String str="";
        try{
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            if(mode.equalsIgnoreCase("Synopsis")){
            str = (new StringBuilder("from PhdStudentPanelMember a where a.synopsisPanel=1 and a.id=")).append(id).toString();
            }else{
            	str = (new StringBuilder("from PhdStudentPanelMember a where a.vivaPanel=1 and a.id=")).append(id).toString();
            }
            PhdStudentPanelMember studentPanelMember = (PhdStudentPanelMember)session.createQuery(str).uniqueResult();
            session.delete(studentPanelMember);
            transaction.commit();
            isDeleted = true;
        }
        catch(Exception e)
        {
            isDeleted = false;
            log.error("Unable to delete FeedBackGroup", e);
        }finally {
    		if (session != null) {
    			session.flush();
    			session.close();
    		}
    	}
        log.info("end of deleteFeedBackGroup in PhdStudentPanelMember class.");
        return isDeleted;
    }
	@Override
	public boolean deleteResearchPublication(PhdDocumentSubmissionForm objForm)	throws Exception {
    	log.info("call of deletesynopsisList in PhdStudentPanelMember class.");
        Session session = null;
        boolean isDeleted = false;
        Transaction transaction = null;
        String str="";
        try{
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            str = (new StringBuilder("from PhdResearchDescription a where a.id=")).append(objForm.getId()).toString();
            PhdResearchDescription studentPanelMember = (PhdResearchDescription)session.createQuery(str).uniqueResult();
            session.delete(studentPanelMember);
            transaction.commit();
            isDeleted = true;
        }
        catch(Exception e)
        {
            isDeleted = false;
            log.error("Unable to delete FeedBackGroup", e);
        }finally {
    		if (session != null) {
    			session.flush();
    			session.close();
    		}
    	}
        log.info("end of deleteFeedBackGroup in PhdStudentPanelMember class.");
        return isDeleted;
    }
	@Override
	public List<PhdInternalGuideBo> setInternalGuideDetails() throws Exception {
		Session session=null;
		List<PhdInternalGuideBo> guideList = null;
		try{
		String query="from PhdInternalGuideBo guidebo where guidebo.isActive=1";
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(query);
		guideList=query2.list();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return guideList;
	}
	@Override
	public List<PhdInternalGuideBo> setInternalCoGuideDetails()	throws Exception {
		Session session=null;
		List<PhdInternalGuideBo> guideList = null;
		try{
		String query="from PhdInternalGuideBo guidebo where guidebo.isActive=1";
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(query);
		guideList=query2.list();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return guideList;
	}
	@Override
	public String getPanelNameEmployeeById(int id,PhdDocumentSubmissionForm objForm) throws Exception {
		Session session=null;
		String name="";
		String Strr="";
		try{
			Strr="select guidebo.firstName from Employee guidebo where guidebo.isActive=1 and guidebo.id="+id;
		session=HibernateUtil.getSession();
		Query query2= session.createQuery(Strr);
		name=(String) query2.uniqueResult();
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
		return name;
	}
	@Override
	public void validateNoofGuides(PhdDocumentSubmissionForm objForm,ActionErrors errors) throws Exception {
		Session session=null;
		String guideQuery="";
		String guideintQuery="";
		String coGuideQuery="";
		String coGuideintQuery="";
		String guideQueryOut="";
		String guideintQueryOut="";
		String coGuideQueryOut="";
		String coGuideintQueryOut="";
		
		String maxQuery="";
		
		int maxOccupay=0;
		
		int guideOccupay=0;
		int guideintOccupay=0;
		int coGuideOccupay=0;
		int coGuideintOccupay=0;
		
		int guideOccupayOut=0;
		int guideintOccupayOut=0;
		int coGuideOccupayOut=0;
		int coGuideintOccupayOut=0;
		
		BigInteger guideOccupay1=null;
		BigInteger coGuideOccupay1=null;
		BigInteger coGuideintOccupay1=null;
		BigInteger guideintOccupay1=null;
		try{
			session=HibernateUtil.getSession();
			
			maxQuery="select guide.maxGuidesAssign from PhdSettingBO guide where guide.isActive=1";
			Query query1= session.createQuery(maxQuery);
			maxOccupay=(Integer) query1.uniqueResult();
		if(maxOccupay>0){	
			if(objForm.isGuideDisplay()){
				if(!(objForm.getTempIntGuideId().equalsIgnoreCase(objForm.getGuideId()))){
				guideintQuery=" select count(guide.internal_guide)+(select count(guide.internal_CoGuide) from phd_student_info guide where guide.is_active=1 and guide.internal_CoGuide="+objForm.getGuideId()+ 
				" and guide.viva_voce_date is null and guide.title is null )" +
				" from phd_student_info guide where guide.is_active=1 and guide.internal_guide="+objForm.getGuideId()+
				" and guide.viva_voce_date is null " +
				" and guide.title is null ";
				Query query= session.createSQLQuery(guideintQuery);
				if((BigInteger)query.uniqueResult()!=null)
				guideintOccupay1=(BigInteger)query.uniqueResult();
				guideintOccupay=Integer.parseInt(guideintOccupay1.toString());
				
				
				guideintQueryOut="select guide.noPhdScolarOutside from PhdInternalGuideBo guide where guide.isActive=1 and guide.employeeId.id="+objForm.getGuideId();
				Query queryOut= session.createQuery(guideintQueryOut);
				if((Integer)queryOut.uniqueResult()!=null)
				guideintOccupayOut=(Integer)queryOut.uniqueResult();
				
				 if((guideintOccupay+guideintOccupayOut)>=maxOccupay){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.internal.guide.maxassign",maxOccupay));
					    }
				
				}	
			}else{
				if(!(objForm.getTempGuideId().equalsIgnoreCase(objForm.getGuideId()))){
				guideQuery="select count(guide.guide_id)+(select count(guide.co_guide_id) from phd_student_info guide where guide.is_active=1 and guide.co_guide_id="+objForm.getGuideId() + 
				" and guide.viva_voce_date is null and guide.title is null )" +
				" from phd_student_info guide where guide.is_active=1 and guide.guide_id="+objForm.getGuideId()+
				" and guide.viva_voce_date is null "+
				" and guide.title is null ";
				Query query= session.createSQLQuery(guideQuery);
				if((BigInteger)query.uniqueResult()!=null)
				guideOccupay1=(BigInteger)query.uniqueResult();
				guideOccupay=Integer.parseInt(guideOccupay1.toString());
				
				guideQueryOut="select guide.noPhdScolarOutside from PhdEmployee guide where guide.isActive=1 and guide.id="+objForm.getGuideId();
				Query queryOut= session.createQuery(guideQueryOut);
				if((Integer)queryOut.uniqueResult()!=null)
				guideOccupayOut=(Integer)queryOut.uniqueResult();
				
				if((guideOccupay+guideOccupayOut)>=maxOccupay){
					   errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.guid.maxassign",maxOccupay));
				     }
				
				}
			}
			if(objForm.isCoGuideDisplay())
			{
				if(!(objForm.getTempIntCoGuideId().equalsIgnoreCase(objForm.getCoGuideId()))){
				coGuideintQuery="select count(guide.internal_guide)+(select count(guide.internal_CoGuide) from phd_student_info guide where guide.is_active=1 and guide.internal_CoGuide="+objForm.getCoGuideId()+ 
				" and guide.viva_voce_date is null and guide.title is null )" +
				" from phd_student_info guide where guide.is_active=1 and guide.internal_guide="+objForm.getCoGuideId()+
				" and guide.viva_voce_date is null " +
				" and guide.title is null ";
				Query query= session.createSQLQuery(coGuideintQuery);
				if((BigInteger)query.uniqueResult()!=null)
				coGuideintOccupay1=(BigInteger)query.uniqueResult();
				coGuideintOccupay=Integer.parseInt(coGuideintOccupay1.toString());
				
				coGuideintQueryOut="select guide.noPhdScolarOutside from PhdInternalGuideBo guide where guide.isActive=1 and guide.employeeId.id="+objForm.getGuideId();
				Query queryOut= session.createQuery(coGuideintQueryOut);
				if((Integer)queryOut.uniqueResult()!=null)
				coGuideintOccupayOut=(Integer)queryOut.uniqueResult();
				
				if((coGuideintOccupay+coGuideintOccupayOut)>=maxOccupay){
					  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.internal.coguide.maxassign",maxOccupay));
				     }
				
				}
			}else{
				if(!(objForm.getTempCoGuideId().equalsIgnoreCase(objForm.getCoGuideId()))){
				coGuideQuery="select count(guide.guide_id)+(select count(guide.co_guide_id) from phd_student_info guide where guide.is_active=1 and guide.co_guide_id="+objForm.getCoGuideId() + 
				" and guide.viva_voce_date is null and guide.title is null )" +
				" from phd_student_info guide where guide.is_active=1 and guide.guide_id="+objForm.getCoGuideId()+
				" and guide.viva_voce_date is null "+
				" and guide.title is null ";
				Query query= session.createSQLQuery(coGuideQuery);
				if((BigInteger)query.uniqueResult()!=null)
				coGuideOccupay1=(BigInteger)query.uniqueResult();
				coGuideOccupay=Integer.parseInt(coGuideOccupay1.toString());
				
				coGuideQueryOut="select guide.noPhdScolarOutside from PhdEmployee guide where guide.isActive=1 and guide.id="+objForm.getGuideId();
				Query queryOut= session.createQuery(coGuideQueryOut);
				if((Integer)queryOut.uniqueResult()!=null)
				coGuideOccupayOut=(Integer)queryOut.uniqueResult();
				
				 if((coGuideOccupay+coGuideOccupayOut)>=maxOccupay){
					  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.coguid.maxassign",maxOccupay));
				       }
				 
			       }
		  	}
		}else{
			 errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.maxassign.notsettiing.setting",maxOccupay));
		}
		session.close();
		}
		catch (Exception e) {

			session.flush();
			session.close();
		}
	}
	@Override
	public boolean deleteConference(PhdDocumentSubmissionForm objForm)throws Exception {
        Session session = null;
        boolean isDeleted = false;
        Transaction transaction = null;
        String str="";
        try{
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            transaction.begin();
            str = (new StringBuilder("from PhdConference a where a.id=")).append(objForm.getId()).toString();
            PhdConference phdConference = (PhdConference)session.createQuery(str).uniqueResult();
            session.delete(phdConference);
            transaction.commit();
            isDeleted = true;
        }
        catch(Exception e)
        {
            isDeleted = false;
            log.error("Unable to delete FeedBackGroup", e);
        }finally {
    		if (session != null) {
    			session.flush();
    			session.close();
    		}
    	}
        return isDeleted;
    }
	
}
