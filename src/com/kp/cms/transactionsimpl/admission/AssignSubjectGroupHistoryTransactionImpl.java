package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.exam.StudentSubjectGroupHistory;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm;
import com.kp.cms.forms.attendance.SubjectGroupDetailsForm;
import com.kp.cms.to.admission.AssignSubjectGroupHistoryTO;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;
import com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn;
import com.kp.cms.utilities.HibernateUtil;

public class AssignSubjectGroupHistoryTransactionImpl implements IAssignSubjectGroupHistoryTxn{
	private static final Log log = LogFactory .getLog(AssignSubjectGroupHistoryTransactionImpl.class);
 private static volatile AssignSubjectGroupHistoryTransactionImpl transactionImpl = null;
 public static AssignSubjectGroupHistoryTransactionImpl getInstance(){
	 if(transactionImpl == null){
		 transactionImpl = new AssignSubjectGroupHistoryTransactionImpl();
		 return transactionImpl;
	 }
	 return transactionImpl;
 }
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn#getClassSchemwises(com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm)
 */
@Override
public ClassSchemewise getClassSchemwises( AssignSubjectGroupHistoryForm assignSubGrpHistory) throws Exception {
	log.info("call of getClassSchemwises method in AssignSubjectGroupHistoryTransactionImpl class.");
	Session session= null;
	ClassSchemewise schemewise;
	try{
		session=HibernateUtil.getSession();
		String str= "from ClassSchemewise csw where csw.id="+assignSubGrpHistory.getClassSchemeWiseId()+ " and csw.classes.isActive = 1";
		Query query = session.createQuery(str);
		schemewise= (ClassSchemewise) query.uniqueResult();
		session.flush();
		
	}catch (Exception exception) {
		 if( session != null){
			 session.flush();
			 //session.close();
		 }
		 throw new ApplicationException(exception);
	 }
	log.info("end of getClassSchemwises method in AssignSubjectGroupHistoryTransactionImpl class.");
	return schemewise;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn#getStudentPreviousClassDetails(com.kp.cms.bo.admin.ClassSchemewise, com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm)
 */
@Override
public List<StudentPreviousClassHistory> getStudentPreviousClassDetails( ClassSchemewise classSchemewises, AssignSubjectGroupHistoryForm assignSubGrpHistory) 
	throws Exception {
	log.info("call of getStudentPreviousClassDetails method in AssignSubjectGroupHistoryTransactionImpl class.");
	Session session = null;
	List<StudentPreviousClassHistory> previousClassHistories;
	try{
		session= HibernateUtil.getSession();
		String str= "from StudentPreviousClassHistory stuPreClassHty where stuPreClassHty.classes.id="+classSchemewises.getClasses().getId()+
		"and stuPreClassHty.schemeNo="+classSchemewises.getCurriculumSchemeDuration().getSemesterYearNo()+"and stuPreClassHty.academicYear="+assignSubGrpHistory.getYear() +
		" and stuPreClassHty.student.admAppln.isCancelled=0";
		Query query = session.createQuery(str);
		previousClassHistories = query.list();
		session.flush();
		
	}catch (Exception exception) {
		 if( session != null){
			 session.flush();
			 //session.close();
		 }
		 throw new ApplicationException(exception);
	 }
	log.info("end of getStudentPreviousClassDetails method in AssignSubjectGroupHistoryTransactionImpl class.");
	return previousClassHistories;
}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn#getSubjectGroups(com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm)
 */
@SuppressWarnings("unchecked")
@Override
public Map<Integer, SubjectGroup> getSubjectGroups( AssignSubjectGroupHistoryForm assignSubGrpHistory) throws Exception {
	log.info("call of getSubjectGroups method in AssignSubjectGroupHistoryTransactionImpl class.");
	Map<Integer,SubjectGroup> subjectGroup=new HashMap<Integer, SubjectGroup>();
	Session session=null;
	@SuppressWarnings("unused")
	Transaction transaction=null;
	try{
		session=HibernateUtil.getSession();
		//transaction=session.beginTransaction();
		String str="select sg.id,cs.subjectGroup from ClassSchemewise c join c.curriculumSchemeDuration.curriculumSchemeSubjects cs join cs.subjectGroup sg where c.id=" +assignSubGrpHistory.getClassSchemeWiseId()+
          "and c.curriculumSchemeDuration.academicYear=" +assignSubGrpHistory.getYear();
		Query query=session.createQuery(str);
		List<Object[]> list=query.list();
		Iterator<Object[]> it=list.iterator();
		while (it.hasNext()) {
			Object[] objects = (Object[]) it.next();
			SubjectGroup subjectGroups=(SubjectGroup)objects[1];
			subjectGroup.put(Integer.parseInt(objects[0].toString()),subjectGroups);
		}
		session.flush();
		 log.info("end of getSubjectGroups method in AssignSubjectGroupHistoryTransactionImpl class.");

		
	}catch (Exception exception) {
		
		 if( session != null){
			 session.flush();
			 //session.close();
		 }
		
}
	 return subjectGroup; 
	
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn#getSubjectGroupsList(com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm)
 */
@Override
public List<SubjectGroup> getSubjectGroupsList( AssignSubjectGroupHistoryForm assignSubGrpHistory,ClassSchemewise classSchemewises) throws Exception {
	log.info("call of getSubjectGroupsList method in AssignSubjectGroupHistoryTransactionImpl class.");
	Session session = null;
	try{
		session = HibernateUtil.getSession(); 
		String str= "select stuSubGrpHsty.subjectGroup from StudentSubjectGroupHistory stuSubGrpHsty join stuSubGrpHsty.student.studentPreviousClassesHistory preClassHsty where preClassHsty.classes.id="
			+classSchemewises.getClasses().getId()+"and stuSubGrpHsty.schemeNo="+classSchemewises.getCurriculumSchemeDuration().getSemesterYearNo()+
			 "and preClassHsty.academicYear="+assignSubGrpHistory.getYear()+" group by stuSubGrpHsty.subjectGroup.id ";
		Query query = session.createQuery(str);
		List<SubjectGroup> subjectGrouplist= query.list();
		session.flush();
		session.close();
		log.info("end of getSubjectGroupsList method in AssignSubjectGroupHistoryTransactionImpl class.");
		return subjectGrouplist;
	}catch (Exception exception) {
		 if( session != null){
			 session.flush();
			 //session.close();
		 }
		 throw new ApplicationException(exception);
	 }
	
	
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn#addStudentSubjectGroup(com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm)
 */
@Override
public boolean addStudentSubjectGroup( AssignSubjectGroupHistoryForm assignSubGrpHistoryForm) throws Exception {
	log.info("call of addStudentSubjectGroup method in AssignSubjectGroupHistoryTransactionImpl class.");
	Session session = null;
	Transaction tx= null;
	boolean isAdded=false;
	try{
		session=HibernateUtil.getSession();
		tx=session.beginTransaction();
		List<AssignSubjectGroupHistoryTO> list= assignSubGrpHistoryForm.getStudentDetailsList();
		if(list!=null && !list.isEmpty()){
			Iterator<AssignSubjectGroupHistoryTO> iterator= list.iterator();
			int count=0;
			List<String> subList= new ArrayList<String>();
			while (iterator.hasNext()) {
				AssignSubjectGroupHistoryTO assignSubjectGroupHistoryTO = (AssignSubjectGroupHistoryTO) iterator .next();
				if(assignSubjectGroupHistoryTO.getChecked()!=null && assignSubjectGroupHistoryTO.getChecked().equalsIgnoreCase("on")){
					Student student= new Student();
					SubjectGroup subjectGroup = new SubjectGroup();
					student.setId(assignSubjectGroupHistoryTO.getStudentId());
					subjectGroup.setId(Integer.parseInt(assignSubGrpHistoryForm.getSubjectGroupId()));
					int semNo=assignSubjectGroupHistoryTO.getSemNo();
					if(!isValidateSecondLanguage(assignSubjectGroupHistoryTO,assignSubGrpHistoryForm,subList)){
					if(!isDuplicateEntry(student,subjectGroup,semNo)){
						StudentSubjectGroupHistory stuSubGrpHsty= new StudentSubjectGroupHistory();
						stuSubGrpHsty.setStudent(student);
						stuSubGrpHsty.setSubjectGroup(subjectGroup);
						stuSubGrpHsty.setSchemeNo(semNo);
						stuSubGrpHsty.setCreatedBy(assignSubGrpHistoryForm.getUserId());
						stuSubGrpHsty.setModifiedBy(assignSubGrpHistoryForm.getUserId());
						stuSubGrpHsty.setLastModifiedDate(new Date());
						stuSubGrpHsty.setCreatedDate(new Date());
						session.save(stuSubGrpHsty);
						if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
						isAdded=true;
					}
					}
				}else if(assignSubjectGroupHistoryTO.getTempChecked()!=null && assignSubjectGroupHistoryTO.getTempChecked().equalsIgnoreCase("on") && assignSubjectGroupHistoryTO.getChecked()==null){
					String str= "from StudentSubjectGroupHistory preSubGrp where preSubGrp.student.id="+assignSubjectGroupHistoryTO.getStudentId()+"and preSubGrp.subjectGroup.id="+assignSubGrpHistoryForm.getSubjectGroupId()+
					"and preSubGrp.schemeNo="+assignSubjectGroupHistoryTO.getSemNo();
					Query query= session.createQuery(str);
					StudentSubjectGroupHistory groupHistory=(StudentSubjectGroupHistory) query.uniqueResult();
					session.delete(groupHistory);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
					isAdded=true;
				}
				
			}
			tx.commit();
		}
		// setting null value to the checked property 
		List<AssignSubjectGroupHistoryTO> tos =new ArrayList<AssignSubjectGroupHistoryTO>();
		Iterator<AssignSubjectGroupHistoryTO> iterator= list.iterator();
		while (iterator.hasNext()) {
			AssignSubjectGroupHistoryTO assignSubjectGroupHistoryTO = (AssignSubjectGroupHistoryTO) iterator .next();
			if(assignSubjectGroupHistoryTO.getChecked()!=null && assignSubjectGroupHistoryTO.getChecked().equalsIgnoreCase("on")){
				assignSubjectGroupHistoryTO.setChecked(null);
			}
			tos.add(assignSubjectGroupHistoryTO);
		}
		assignSubGrpHistoryForm.setStudentDetailsList(tos);
		//
	}catch (Exception exception) {
		if (tx != null) {
			tx.rollback();
		}

	} finally {
		if (session != null) {
			session.flush();
			// session.close();
		}
	}
	log
			.info("end of addStudentSubjectGroup method in AssignSubjectGroupHistoryTransactionImpl class.");
	return isAdded;
}

/**
 * @param student
 * @param subjectGroup
 * @param semNo
 * @return
 * @throws ApplicationException
 */
private boolean isDuplicateEntry(Student student, SubjectGroup subjectGroup,int semNo) throws ApplicationException {
	log.info("call of isDuplicateEntry method in AssignSubjectGroupHistoryTransactionImpl class.");
	Session session= null;
	boolean isDuplicate= false;
	try{
		session=HibernateUtil.getSession();
		String str= "from StudentSubjectGroupHistory stuSubGrpHty where stuSubGrpHty.student.id="+student.getId()+"and stuSubGrpHty.subjectGroup.id="+subjectGroup.getId()+"and stuSubGrpHty.schemeNo="+semNo;
		Query query= session.createQuery(str);
		List<StudentSubjectGroupHistory> groupHistories=query.list();
		if(groupHistories!=null && !groupHistories.isEmpty()){
			isDuplicate=true;
		}
	}finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
	}log.info("end of isDuplicateEntry method in AssignSubjectGroupHistoryTransactionImpl class.");
	return isDuplicate;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.admission.IAssignSubjectGroupHistoryTxn#getEditSubjectGroups(com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm)
 */
@Override
public Map<Integer, Integer> getEditSubjectGroups( AssignSubjectGroupHistoryForm assignSubGrpHistoryForm) throws Exception {
	log.info("call of getEditSubjectGroups method in AssignSubjectGroupHistoryTransactionImpl class.");
	Session session=null;
	Map<Integer,Integer> subMap=new HashMap<Integer, Integer>();
	try{
		session = HibernateUtil.getSession();
		String str="select subGrp.student.id,subGrp.id from StudentSubjectGroupHistory subGrp where subGrp.subjectGroup.id="+assignSubGrpHistoryForm.getSubjectGroupId();
		Query query =session.createQuery(str);
		List<Object[]> list=query.list();
		Iterator<Object[]> it=list.iterator();
		while (it.hasNext()) {
			Object[] objects = (Object[]) it.next();
			subMap.put(Integer.parseInt(objects[0].toString()),Integer.parseInt(objects[1].toString()));
		}
			session.flush();
			 log.info("end of getEditSubjectGroups method in AssignSubjectGroupHistoryTransactionImpl class.");
			return subMap;
	}catch (Exception exception) {
		 if( session != null){
			 session.flush();
			 //session.close();
		 }
		 throw new ApplicationException(exception);
			}
		}
	/**
	 * @param subjectGroupDetailsTo
	 * @param subjectGroupDetailsForm
	 * @param subList
	 * @return
	 * @throws Exception
	 */
	private boolean isValidateSecondLanguage(AssignSubjectGroupHistoryTO assignSubjectGroupHistoryTO,AssignSubjectGroupHistoryForm assignSubGrpHistoryForm,
		List<String> subList) throws Exception{
	Session session = null;
	Transaction tx = null;
	String secondLanguage = "";
	boolean isvalid = false;
	try{
		session = HibernateUtil.getSession();
		if(assignSubjectGroupHistoryTO.getSecondLanguage()!=null && !assignSubjectGroupHistoryTO.getSecondLanguage().isEmpty()){
			String str= "from SubjectGroup subGrp where subGrp.id=" +assignSubGrpHistoryForm.getSubjectGroupId();
			Query query = session.createQuery(str);
			SubjectGroup subjectGroup = (SubjectGroup) query.uniqueResult();
			if(subjectGroup!=null && !subjectGroup.toString().isEmpty()){
				if(!subjectGroup.getIsCommonSubGrp()){
					if(subjectGroup.getSecondLanguageId()!=null && subjectGroup.getSecondLanguageId()!=0){
						if(subjectGroup.getExamSecondLanguageMasterBO()!=null && subjectGroup.getExamSecondLanguageMasterBO().getName()!=null){
							secondLanguage = subjectGroup.getExamSecondLanguageMasterBO().getName();
							if(secondLanguage!=null && !secondLanguage.isEmpty() && !secondLanguage.equalsIgnoreCase(assignSubjectGroupHistoryTO.getSecondLanguage())){
								subList.add(assignSubjectGroupHistoryTO.getRegNo());
								isvalid = true;
							}
							assignSubGrpHistoryForm.setList(subList);
						}
					}
				}
			}
		}
	}catch (Exception exception) {
		
		if (tx != null) {
			tx.rollback();
		}
	}
	finally {
		if (session != null) {
			session.flush();
		}
	}
	return isvalid;
}
	}
