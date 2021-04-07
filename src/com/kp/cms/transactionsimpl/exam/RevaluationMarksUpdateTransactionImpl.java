package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.RevaluationMarksUpdateForm;
import com.kp.cms.handlers.admission.UploadInterviewSelectionHandler;
import com.kp.cms.to.exam.RevaluationMarksUpdateTo;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.IRevaluationMarksUpdateTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class RevaluationMarksUpdateTransactionImpl implements IRevaluationMarksUpdateTransaction{
	private static final Log log = LogFactory.getLog(RevaluationMarksUpdateTransactionImpl.class);
	private static volatile RevaluationMarksUpdateTransactionImpl revaluationMarksUpdateTransactionImpl = null;
		
		/**
		 * @return
		 */
		public static RevaluationMarksUpdateTransactionImpl getInstance() {
			if (revaluationMarksUpdateTransactionImpl == null) {
				revaluationMarksUpdateTransactionImpl = new RevaluationMarksUpdateTransactionImpl();
			}
			return revaluationMarksUpdateTransactionImpl;
		}
		
		public List<Object> getStudentsNewMarksList(RevaluationMarksUpdateForm form) throws Exception {
			Session session = null;
			List list = null;
			try {
				session = HibernateUtil.getSession();
				
				String str="select student.id as studentId,student.register_no as registerNo,"+
						" student.adm_appln_id as admApplnId,classes.name as className,"+
						" classes.id as classId,personal_data.first_name as studentName,"+
						" details.theory_marks as theoryMarks,EXAM_marks_entry.evaluator_type_id as evaluatorTypeId,"+
						" appdetails.mark_1 as marks1,appdetails.mark_2 as marks2,"+
						" appdetails.marks as marks,subject.id as subjectId,"+
						" subject.name as subjectName,subject.code as subjectCode,"+
						" app.id as appId,EXAM_marks_entry.id as examMarksEntryId,"+
						" details.id as marksEntryDetailsId,classes.course_id as courseId,"+
						" cd.semester_year_no as schemeNumber,appdetails.third_evl_marks as thirdEvlMarks,+" +
						" details.graced_mark,appdetails.mark_3 as marks3"+
						" from Exam_revaluation_application app"+
						" JOIN Exam_revaluation_app_details appdetails ON appdetails.exam_rev_app_id = app.id"+
						" JOIN EXAM_definition ON app.exam_id = EXAM_definition.id"+
						" JOIN classes ON app.class_id = classes.id"+
						" JOIN student ON app.student_id = student.id"+
						" JOIN class_schemewise ON class_schemewise.class_id = classes.id"+
						" JOIN curriculum_scheme_duration cd ON class_schemewise.curriculum_scheme_duration_id = cd.id"+
						" JOIN subject ON appdetails.subject_id = subject.id"+
						" JOIN adm_appln ON student.adm_appln_id = adm_appln.id"+
						" JOIN personal_data ON adm_appln.personal_data_id = personal_data.id"+
						" JOIN EXAM_marks_entry  ON EXAM_marks_entry.exam_id = EXAM_definition.id AND EXAM_marks_entry.student_id = student.id "+
						" AND EXAM_marks_entry.class_id = classes.id"+
						" JOIN EXAM_marks_entry_details details ON details.marks_entry_id = EXAM_marks_entry.id AND details.subject_id = subject.id"+
						" WHERE appdetails.type="+ "'" + form.getRevaluation() + "'"+ 
						" AND EXAM_definition.id="+ form.getExamId() +
						" AND  app.is_active=1";
				
				if(form.getOption().equalsIgnoreCase("Updated")){
					str=str+" AND appdetails.is_updated=1  order by subject.code";
				}else if(form.getOption().equalsIgnoreCase("NotUpdated")){
					str=str+" AND appdetails.third_evaluation=0 AND (appdetails.is_updated=0 or appdetails.is_updated is null)  order by subject.code";
				}else if(form.getOption().equalsIgnoreCase("Requested_for_thirdEvaluation")){
					str=str+" AND appdetails.third_evaluation=1 AND (appdetails.is_updated=0 or appdetails.is_updated is null) order by subject.code";
				}
				Query query=session.createSQLQuery(str);
				 List<Object>  List = query.list();
				return List;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}
		
		public List<MarksEntry> getOldMarksList(int classid,int studentId,RevaluationMarksUpdateForm form) throws Exception {
			Session session = null;
			List list = null;
			try {
				session = HibernateUtil.getSession();
			//	if(classid=0)
				Query query=session.createQuery(" select e from MarksEntry e "+
												" where  e.exam.id="+form.getExamId()+
												" and e.classes.id="+classid+
												" and e.student.id="+studentId);
				 List<MarksEntry>  List = query.list();
				return List;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}
		public boolean thirdEvaluation(RevaluationMarksUpdateForm form) throws Exception {
			Transaction tx=null;
			Session session = null;
			try {
				boolean isSaved=false;
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				Query query=session.createQuery(" select e from ExamRevaluationAppDetails e"+
												" where e.examRevApp.student.id="+form.getStudentid()+"and e.examRevApp.classes.id="+form.getClassid()+
												" and e.examRevApp.id="+form.getExamRevaluationAppId()+"and e.examRevApp.exam.id="+form.getExamId()+
												" and e.subject.id="+form.getSubjectid());
				ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query.uniqueResult();
				if(bo !=null){
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(form.getUserId());
					bo.setThirdEvaluation(true);
					session.update(bo);
					isSaved=true;
							}
				tx.commit();
				session.close();
				return isSaved;
			} catch (Exception e) {
				if(tx!=null){
					tx.rollback();
				}
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} 
			
		}
		
		public boolean saveMarksEntryCorrection(List<MarksEntryCorrectionDetails> boList) throws Exception {
			log.debug("inside saveMarksEntryCorrection");
			Session session = null;
			Transaction transaction = null;
			MarksEntryCorrectionDetails tcLChecklist;
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				Iterator<MarksEntryCorrectionDetails> tcIterator = boList.iterator();
				int count = 0;
				while(tcIterator.hasNext()){
					tcLChecklist = tcIterator.next();
					session.save(tcLChecklist);
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				}
				transaction.commit();
				session.flush();
				//session.close();
				log.debug("leaving saveMarksEntryCorrection");
				return true;
			} catch (ConstraintViolationException e) {
				transaction.rollback();
				log.error("Error in saveMarksEntryCorrection impl...", e);
				throw new BusinessException(e);
			} catch (Exception e) {
				transaction.rollback();
				log.error("Error in saveMarksEntryCorrection impl...", e);
				throw new ApplicationException(e);
			}
		}

		public boolean updateModifiedMarksForRetotaling(RevaluationMarksUpdateForm form) throws Exception {
			log.debug("inside updateModifiedMarks");
			Session session = null;
			Transaction transaction = null;
			boolean isUpdated=false;
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				int count = 0;
					
						if((form.getOldMarks()!=null && !form.getOldMarks().isEmpty())&&(form.getNewMarks()!=null && !form.getNewMarks().isEmpty()) && form.getExamMarksEntryIdForNoEvl() > 0 ){
							
							if(form.getExamRevaluationAppId() > 0){
								Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
										" where e.examRevApp.student.id="+form.getStudentid()+"and e.examRevApp.classes.id="+form.getClassid()+
										" and e.examRevApp.id="+form.getExamRevaluationAppId()+"and e.examRevApp.exam.id="+form.getExamId()+
										" and e.subject.id="+form.getSubjectid());
								ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
								if(bo !=null){
									bo.setIsUpdated(true);
									bo.setLastModifiedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									session.update(bo);
								    
								if(Integer.parseInt(form.getOldMarks()) != Integer.parseInt(form.getNewMarks())){
										
									MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, form.getExamMarksEntryDetailsIdForNoEvl());
									marksEntryDetails.setModifiedBy(form.getUserId());
									marksEntryDetails.setLastModifiedDate(new Date());
									marksEntryDetails.setTheoryMarks(form.getNewMarks());
									MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,form.getExamMarksEntryIdForNoEvl());
									session.update(marksEntry);
									session.update(marksEntryDetails);
									
									String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
												 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
									if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
										str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
									}
									Query query = session.createQuery(str);
									ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
									if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
										examVerifyBo.setVmarks(form.getNewMarks());
										examVerifyBo.setModifiedBy(form.getUserId());
										examVerifyBo.setLastModifiedDate(new Date());
										session.update(examVerifyBo);
										}
										isUpdated=true;
									}
								}
							}
							
						}
						
						if((form.getOldMark1()!=null && !form.getOldMark1().isEmpty())&&(form.getNewMark1()!=null && !form.getNewMark1().isEmpty()) && form.getExamMarksEntryId() > 0 ){
							
							
							if(form.getExamRevaluationAppIdForEvL1() > 0){
								Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
										" where e.examRevApp.student.id="+form.getStudentid()+"and e.examRevApp.classes.id="+form.getClassid()+
										" and e.examRevApp.id="+form.getExamRevaluationAppIdForEvL1()+"and e.examRevApp.exam.id="+form.getExamId()+
										" and e.subject.id="+form.getSubjectid());
								ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
								if(bo !=null){
									bo.setIsUpdated(true);
									bo.setLastModifiedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									session.update(bo);
								  if(Integer.parseInt(form.getOldMark1()) != Integer.parseInt(form.getNewMark1()) || Integer.parseInt(form.getOldMark2()) != Integer.parseInt(form.getNewMark2()))  {
									  
									MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, form.getExamMarksEntryDetailsId());
									marksEntryDetails.setModifiedBy(form.getUserId());
									marksEntryDetails.setLastModifiedDate(new Date());
									marksEntryDetails.setTheoryMarks(form.getNewMark1());
									MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,form.getExamMarksEntryId());
									session.update(marksEntry);
									session.update(marksEntryDetails);
									
									String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
												 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
									if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
										str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
									}
									Query query = session.createQuery(str);
									ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
									if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
										examVerifyBo.setVmarks(form.getNewMark1());
										examVerifyBo.setModifiedBy(form.getUserId());
										examVerifyBo.setLastModifiedDate(new Date());
										session.update(examVerifyBo);
									}
									isUpdated=true;
								  }
								}
							}
							
						}
						
						if((form.getOldMark2()!=null && !form.getOldMark2().isEmpty())&&((form.getNewMark2()!=null && !form.getNewMark2().isEmpty())) && form.getExamMarksEntryIdForSecondEvl2() > 0 ){
							
							if(form.getExamRevaluationAppIdForEvL2() > 0){
								Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
										" where e.examRevApp.student.id="+form.getStudentid()+"and e.examRevApp.classes.id="+form.getClassid()+
										" and e.examRevApp.id="+form.getExamRevaluationAppIdForEvL2()+"and e.examRevApp.exam.id="+form.getExamId()+
										" and e.subject.id="+form.getSubjectid());
								ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
								if(bo !=null){
									bo.setIsUpdated(true);
									bo.setLastModifiedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									session.update(bo);
									
								if(Integer.parseInt(form.getOldMark1()) != Integer.parseInt(form.getNewMark1()) || Integer.parseInt(form.getOldMark2()) != Integer.parseInt(form.getNewMark2()))  {
									
									MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, form.getExamMarksEntryDetailsIdForSecondEvl2());
									marksEntryDetails.setModifiedBy(form.getUserId());
									marksEntryDetails.setLastModifiedDate(new Date());
									marksEntryDetails.setTheoryMarks(form.getNewMark2());
									MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,form.getExamMarksEntryIdForSecondEvl2());
									session.update(marksEntry);
									session.update(marksEntryDetails);
									
									String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
												 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
									if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
										str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
									}
									Query query = session.createQuery(str);
									ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
									if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
										examVerifyBo.setVmarks(form.getNewMark2());
										examVerifyBo.setModifiedBy(form.getUserId());
										examVerifyBo.setLastModifiedDate(new Date());
										session.update(examVerifyBo);
									}
									isUpdated=true;
								  }
								}
							}
							
						}
						
					
						//new
						if((form.getOldMark3()!=null && !form.getOldMark3().isEmpty())&&((form.getNewMark3()!=null && !form.getNewMark3().isEmpty())) && form.getExamMarksEntryIdForThirdEvl3() > 0 ){
							
							if(form.getExamRevaluationAppIdForEvL3() > 0){
								Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
										" where e.examRevApp.student.id="+form.getStudentid()+"and e.examRevApp.classes.id="+form.getClassid()+
										" and e.examRevApp.id="+form.getExamRevaluationAppIdForEvL3()+"and e.examRevApp.exam.id="+form.getExamId()+
										" and e.subject.id="+form.getSubjectid());
								ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
								if(bo !=null){
									bo.setIsUpdated(true);
									bo.setLastModifiedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									session.update(bo);
									
								if(Integer.parseInt(form.getOldMark1()) != Integer.parseInt(form.getNewMark1()) || Integer.parseInt(form.getOldMark2()) != Integer.parseInt(form.getNewMark2()) || Integer.parseInt(form.getOldMark3()) != Integer.parseInt(form.getNewMark3()))  {
									
									MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, form.getExamMarksEntryDetailsIdForThirdEvl3());
									marksEntryDetails.setModifiedBy(form.getUserId());
									marksEntryDetails.setLastModifiedDate(new Date());
									marksEntryDetails.setTheoryMarks(form.getNewMark3());
									MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,form.getExamMarksEntryIdForThirdEvl3());
									session.update(marksEntry);
									session.update(marksEntryDetails);
									
									String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
												 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
									if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
										str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
									}
									Query query = session.createQuery(str);
									ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
									if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
										examVerifyBo.setVmarks(form.getNewMark3());
										examVerifyBo.setModifiedBy(form.getUserId());
										examVerifyBo.setLastModifiedDate(new Date());
										session.update(examVerifyBo);
									}
									isUpdated=true;
								  }
								}
							}
							
						}
				
						
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				
				transaction.commit();
				session.flush();
				//session.close();
				log.debug("leaving updateModifiedMarks");
			} catch (ConstraintViolationException e) {
				transaction.rollback();
				log.error("Error in updateModifiedMarks impl...", e);
				throw new BusinessException(e);
			} catch (Exception e) {
				transaction.rollback();
				log.error("Error in updateModifiedMarks impl...", e);
				throw new ApplicationException(e);
			}
		
			
			return isUpdated;
		}
		
		
		public boolean updateModifiedMarksForRevaluation(RevaluationMarksUpdateForm form) throws Exception {
			log.debug("inside updateModifiedMarks");
			Session session = null;
			Transaction transaction = null;
			boolean isUpdated=false;
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				int count = 0;
					
						if((form.getOldMarks()!=null && !form.getOldMarks().isEmpty())&&(form.getAvgMarks()!=null && !form.getAvgMarks().isEmpty()) && form.getExamMarksEntryIdForNoEvl() > 0 ){
							if(form.getExamRevaluationAppId() > 0){
									Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
											" where e.examRevApp.student.id="+form.getStudentid()+"and e.examRevApp.classes.id="+form.getClassid()+
											" and e.examRevApp.id="+form.getExamRevaluationAppId()+"and e.examRevApp.exam.id="+form.getExamId()+
											" and e.subject.id="+form.getSubjectid());
									ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
									if(bo !=null){
										bo.setIsUpdated(true);
										bo.setLastModifiedDate(new Date());
										bo.setModifiedBy(form.getUserId());
										session.update(bo);
									
									if(Integer.parseInt(form.getOldMarks()) != Integer.parseInt(form.getAvgMarks())){
										MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, form.getExamMarksEntryDetailsIdForNoEvl());
										marksEntryDetails.setModifiedBy(form.getUserId());
										marksEntryDetails.setLastModifiedDate(new Date());
										marksEntryDetails.setTheoryMarks(form.getAvgMarks());
										MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,form.getExamMarksEntryIdForNoEvl());
										session.update(marksEntry);
										session.update(marksEntryDetails);
										
										String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
													 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
										if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
											str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
										}
										Query query = session.createQuery(str);
										ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
										if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
											examVerifyBo.setVmarks(form.getAvgMarks());
											examVerifyBo.setModifiedBy(form.getUserId());
											examVerifyBo.setLastModifiedDate(new Date());
											session.update(examVerifyBo);
										}
										isUpdated=true;
									}
								  }
								}
						}
					
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				
				transaction.commit();
				session.flush();
				//session.close();
				log.debug("leaving updateModifiedMarks");
			} catch (ConstraintViolationException e) {
				transaction.rollback();
				log.error("Error in updateModifiedMarks impl...", e);
				throw new BusinessException(e);
			} catch (Exception e) {
				transaction.rollback();
				log.error("Error in updateModifiedMarks impl...", e);
				throw new ApplicationException(e);
			}
			return isUpdated;
		}
		
		public Double getMaxMarkOfSubject(RevaluationMarksUpdateForm form) throws Exception {
			Session session = null;
			Double maxMarks=null;
			String subType="";
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			try{
				session = HibernateUtil.getSession();
				String query="select s.theoryEseEnteredMaxMark, s.academicYear from SubjectRuleSettings s" +
						" where s.course.id="+form.getCourseid()+
						" and s.schemeNo=" +form.getSchemeNumber()+
						" and s.subject.id=" +form.getSubjectid()+
						"and  s.isActive=1";
				if(form.getExamType().equals("Supplementary")){
					query=query+" and s.academicYear>=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+form.getExamId()+") ";
				}else{
					query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+form.getExamId()+") ";
				}
				
				List<Object[]> list=session.createQuery(query).list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
							if(objects[0]!=null){
								maxMarks=new Double(objects[0].toString());
								if(objects[1]!=null && objects[1].toString()!=null)
									map.put(Integer.parseInt(objects[1].toString()), maxMarks);
							}
						}
					}
				form.setMaxMarksMap(map);
			} catch (Exception e) {
				log.error("Error while retrieving ExamAbscentCode.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
			return maxMarks;
		}
		
		public boolean updateModifiedMarksForRetotalingForUpdateAll(RevaluationMarksUpdateForm form) throws Exception {
			log.debug("inside updateModifiedMarks");
			Session session = null;
			Transaction transaction = null;
			boolean isUpdated=false;
			try {
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				int count = 0;
				Iterator<RevaluationMarksUpdateTo> iterator = form.getStudentDetailsList().iterator();
				while (iterator.hasNext()) {
					RevaluationMarksUpdateTo to = (RevaluationMarksUpdateTo) iterator.next();
					if(to.getMarks()!=null && !to.getMarks().isEmpty()){
						if(!to.getIsUpdated()){
						if((to.getOldMarks()!=null && !to.getOldMarks().isEmpty())&&(to.getNewMarks()!=null && !to.getNewMarks().isEmpty()) && to.getExamMarksEntryIdForNoEvl() > 0 ){
							
							if(to.getExamRevaluationAppId() > 0){
								Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
										" where e.examRevApp.student.id="+to.getStudentId()+"and e.examRevApp.classes.id="+to.getClassId()+
										" and e.examRevApp.id="+to.getExamRevaluationAppId()+"and e.examRevApp.exam.id="+form.getExamId()+
										" and e.subject.id="+to.getSubjectId());
								ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
								if(bo !=null){
									bo.setIsUpdated(true);
									bo.setLastModifiedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									session.update(bo);
								    
								if( Integer.parseInt(to.getOldMarks()) != Integer.parseInt(to.getNewMarks())){
								
									MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getExamMarksEntryDetailsIdForNoEvl());
									marksEntryDetails.setModifiedBy(form.getUserId());
									marksEntryDetails.setLastModifiedDate(new Date());
									marksEntryDetails.setTheoryMarks(to.getNewMarks());
									MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,to.getExamMarksEntryIdForNoEvl());
									session.update(marksEntry);
									session.update(marksEntryDetails);
									
									String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
												 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
									if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
										str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
									}
									Query query = session.createQuery(str);
									ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
									if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
										examVerifyBo.setVmarks(to.getNewMarks());
										examVerifyBo.setModifiedBy(form.getUserId());
										examVerifyBo.setLastModifiedDate(new Date());
										session.update(examVerifyBo);
									}
									isUpdated=true;
								  }
								}
							}
							
						}
						
						if((to.getOldMark1()!=null && !to.getOldMark1().isEmpty())&&(to.getNewMark1()!=null && !to.getNewMark1().isEmpty()) 
								&& (to.getOldMark2()!=null && !to.getOldMark2().isEmpty()) && (to.getNewMark2()!=null && !to.getNewMark2().isEmpty())){
							
						if((to.getOldMark1()!=null && !to.getOldMark1().isEmpty())&&(to.getNewMark1()!=null && !to.getNewMark1().isEmpty()) && to.getExamMarksEntryId() > 0 ){
							
							
							if(to.getExamRevaluationAppIdForEvL1() > 0){
								Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
										" where e.examRevApp.student.id="+to.getStudentId()+"and e.examRevApp.classes.id="+to.getClassId()+
										" and e.examRevApp.id="+to.getExamRevaluationAppIdForEvL1()+"and e.examRevApp.exam.id="+form.getExamId()+
										" and e.subject.id="+to.getSubjectId());
								ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
								if(bo !=null){
									bo.setIsUpdated(true);
									bo.setLastModifiedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									session.update(bo);
								    
									
							if( (Integer.parseInt(to.getOldMark1()) != Integer.parseInt(to.getNewMark1()))  ||  (Integer.parseInt(to.getOldMark2()) != Integer.parseInt(to.getNewMark2()))){	
												
									MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getExamMarksEntryDetailsId());
									marksEntryDetails.setModifiedBy(form.getUserId());
									marksEntryDetails.setLastModifiedDate(new Date());
									marksEntryDetails.setTheoryMarks(to.getNewMark1());
									MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,to.getExamMarksEntryId());
									session.update(marksEntry);
									session.update(marksEntryDetails);
									
									String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
												 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
									if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
										str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
									}
									Query query = session.createQuery(str);
									ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
									if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
										examVerifyBo.setVmarks(to.getNewMark1());
										examVerifyBo.setModifiedBy(form.getUserId());
										examVerifyBo.setLastModifiedDate(new Date());
										session.update(examVerifyBo);
									}
									isUpdated=true;
							      }
								}
							}
							
						}
						
						if((to.getOldMark2()!=null && !to.getOldMark2().isEmpty())&&((to.getNewMark2()!=null && !to.getNewMark2().isEmpty())) && to.getExamMarksEntryIdForSecondEvl2() > 0 ){
							
							if(to.getExamRevaluationAppIdForEvL2() > 0){
								Query query1=session.createQuery(" select e from ExamRevaluationAppDetails e"+
										" where e.examRevApp.student.id="+to.getStudentId()+"and e.examRevApp.classes.id="+to.getClassId()+
										" and e.examRevApp.id="+to.getExamRevaluationAppIdForEvL2()+"and e.examRevApp.exam.id="+form.getExamId()+
										" and e.subject.id="+to.getSubjectId());
								ExamRevaluationAppDetails bo= (ExamRevaluationAppDetails)query1.uniqueResult();
								if(bo !=null){
									bo.setIsUpdated(true);
									bo.setLastModifiedDate(new Date());
									bo.setModifiedBy(form.getUserId());
									session.update(bo);
								    
							if( (Integer.parseInt(to.getOldMark1()) != Integer.parseInt(to.getNewMark1()))  ||  (Integer.parseInt(to.getOldMark2()) != Integer.parseInt(to.getNewMark2()))){	
											
									MarksEntryDetails marksEntryDetails=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getExamMarksEntryDetailsIdForSecondEvl2());
									marksEntryDetails.setModifiedBy(form.getUserId());
									marksEntryDetails.setLastModifiedDate(new Date());
									marksEntryDetails.setTheoryMarks(to.getNewMark2());
									MarksEntry marksEntry=(MarksEntry)session.get(MarksEntry.class,to.getExamMarksEntryIdForSecondEvl2());
									session.update(marksEntry);
									session.update(marksEntryDetails);
									
									String str = "from ExamMarksVerificationEntryBO exam where exam.examId = "+marksEntry.getExam().getId()+
												 " and exam.studentId="+marksEntry.getStudent().getId()+" and exam.subjectId="+marksEntryDetails.getSubject().getId();
									if(marksEntry.getEvaluatorType()!=null && !marksEntry.getEvaluatorType().toString().isEmpty()){
										str = str + " and exam.evaluatorTypeId="+marksEntry.getEvaluatorType();
									}
									Query query = session.createQuery(str);
									ExamMarksVerificationEntryBO examVerifyBo = (ExamMarksVerificationEntryBO) query.uniqueResult();
									if(examVerifyBo!=null && !examVerifyBo.toString().isEmpty()){
										examVerifyBo.setVmarks(to.getNewMark2());
										examVerifyBo.setModifiedBy(form.getUserId());
										examVerifyBo.setLastModifiedDate(new Date());
										session.update(examVerifyBo);
									}
									isUpdated=true;
									}
								}
							}
							
						}
					}
						// update Re-Valuation Application status
						String query = "from ExamRevaluationAppDetails e where e.isActive=1 and e.examRevApp.isActive=1 " +
										" and e.examRevApp.student.id="+to.getStudentId()+
										" and e.subject.id="+to.getSubjectId()+
										" and e.examRevApp.exam.id="+form.getExamId()+
										" and e.examRevApp.classes.id="+to.getClassId();

						ExamRevaluationAppDetails revaluation = (ExamRevaluationAppDetails) session.createQuery(query).uniqueResult();
						if(revaluation != null){
							boolean runProcess = false;
							if(to.getOldMarks() != null && !to.getOldMarks().isEmpty()
									&& to.getNewMarks() != null && !to.getNewMarks().isEmpty()){
								if(Integer.parseInt(to.getOldMarks())!=Integer.parseInt(to.getNewMarks())){
									runProcess = true;
								}
							}
							if(!runProcess && to.getOldMark1() != null && !to.getOldMark1().isEmpty()
									&& to.getNewMark1() != null && !to.getNewMark1().isEmpty()){
								if(Integer.parseInt(to.getOldMark1())!=Integer.parseInt(to.getNewMark1())){
									runProcess = true;
								}
							}
							if(!runProcess && to.getOldMark2() != null && !to.getOldMark2().isEmpty()
									&& to.getNewMark2() != null && !to.getNewMark2().isEmpty()){
								if(Integer.parseInt(to.getOldMark2())!=Integer.parseInt(to.getNewMark2())){
									runProcess = true;
								}
							}
							if(!runProcess){
								revaluation.setStatus("No change in marks");
							}else{
								revaluation.setStatus("Marks changed and updated");
							}
							String mobileNo="";
							if(revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1()!=null 
									&& !revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
								if(revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || 
										revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
										|| revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || 
										revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
									mobileNo = "91";
								else
									mobileNo=revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo1();
							}else{
								mobileNo="91";
							}
							if(revaluation.getExamRevApp() != null && revaluation.getExamRevApp().getStudent() != null && 
									revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null && !revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
								mobileNo=mobileNo+revaluation.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2();
							}
							revaluation.setModifiedBy(form.getUserId());
							revaluation.setLastModifiedDate(new Date());
							session.update(revaluation);
							if(mobileNo.length()==12){
								UploadInterviewSelectionHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.SMS_REVALUATION_STATUS_TEMPLATE,null);
							}
						}
					}
				}
						
			}	
					if(++count % 20 == 0){
						session.flush();
						session.clear();
					}
				
				transaction.commit();
				session.flush();
				//session.close();
				log.debug("leaving updateModifiedMarks");
			} catch (ConstraintViolationException e) {
				transaction.rollback();
				log.error("Error in updateModifiedMarks impl...", e);
				throw new BusinessException(e);
			} catch (Exception e) {
				transaction.rollback();
				log.error("Error in updateModifiedMarks impl...", e);
				throw new ApplicationException(e);
			}
		
			
			return isUpdated;
		}

		@Override
		public StudentFinalMarkDetails getRegularFinalMarkDetailsBo(
				RevaluationMarksUpdateForm revaluationMarksUpdateForm)
				throws Exception {
			Session session = null;
			StudentFinalMarkDetails details = null;
			try{
				session = HibernateUtil.getSession();
				String query = " from StudentFinalMarkDetails s where s.exam.id="+revaluationMarksUpdateForm.getExamId()+
								" s.student.id="+revaluationMarksUpdateForm.getStudentid()+
								" s.classes.id="+revaluationMarksUpdateForm.getClassid()+
								" s.subject.id="+revaluationMarksUpdateForm.getSubjectid();
				details = (StudentFinalMarkDetails) session.createQuery(query).uniqueResult();
			}catch (Exception e) {
				log.error("Error while retrieving ExamAbscentCode.." +e);
				if(session != null)
					session.close();
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			return details;
		}

		@Override
		public void saveStudentFinalMarks(StudentFinalMarkDetails bo)
				throws Exception {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getDataByQuery(java.lang.String)
		 */
		public List getDataByQuery(String query) throws Exception {
			Session session = null;
			List selectedCandidatesList = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(query);
				selectedCandidatesList = selectedCandidatesQuery.list();
				return selectedCandidatesList;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}

		@Override
		public Map<Integer, List<StudentMarksTO>> getStudentRegularMarks(
				int studentId, List<Integer> subjectIdList, int examId,
				int classId,RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception {
			Map<Integer,List<StudentMarksTO>> stuMarksMap=new HashMap<Integer, List<StudentMarksTO>>();
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				/*List<Object[]> list =session.createQuery("select md.subject.id,max(md.theoryMarks),max(md.practicalMarks),md.marksEntry.evaluatorType,md.marksEntry.answerScript" +
						" from MarksEntryDetails md " +
						" where md.marksEntry.student.id=" +studentId+
						" and md.marksEntry.exam.id=" +examId+
						" and (md.marksEntry.classes.id is null or md.marksEntry.classes.id=" +classId +
						" ) and md.subject.id in (:subList)" +
						" and md.subject.isActive=1" +
						" group by md.marksEntry.evaluatorType,md.marksEntry.answerScript, md.subject.id" +
						" order by md.subject.id").setParameterList("subList",subjectIdList).list();*/
				
				String sql = "select subject.id,max(ed.theory_marks),max(ed.practical_marks), " +
						" entry.evaluator_type_id,entry.answer_script_type,ed.is_theory_secured" +
						" ,verification.vmarks,subject.name,student.register_no,subject.code,EXAM_definition.academic_year as year" +
						" from EXAM_marks_entry_details ed " +
						" JOIN EXAM_marks_entry entry ON ed.marks_entry_id = entry.id " +
						" JOIN classes ON entry.class_id = classes.id " +
						" JOIN student ON entry.student_id = student.id " +
						" JOIN subject ON ed.subject_id = subject.id " +
						" JOIN EXAM_definition ON entry.exam_id = EXAM_definition.id " +
						" LEFT JOIN EXAM_marks_verification_details verification ON " +
						" (verification.exam_id = EXAM_definition.id AND verification.student_id = student.id AND verification.subject_id = subject.id " +
						" AND if(verification.evaluator_type_id is null,0,verification.evaluator_type_id) = if(entry.evaluator_type_id is null,0,entry.evaluator_type_id) " +
						" AND if(verification.answer_script_type is null,0,verification.answer_script_type) = if(entry.answer_script_type is null,0,entry.answer_script_type)) " +
						" WHERE student.id=" +studentId+
						" AND subject.id in(:subList) " +
						" AND EXAM_definition.id=" +examId+
						" AND (classes.id is null OR classes.id =" +classId +
						" ) GROUP BY entry.evaluator_type_id,entry.answer_script_type,subject.id " +
						" ORDER BY subject.id";
				
				List<Object[]> list = session.createSQLQuery(sql).setParameterList("subList",subjectIdList).list();			
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					String errMsg=revaluationMarksUpdateForm.getErrorMessage();
					while (itr.hasNext()) {
						Object[] bo =itr .next();
						List<StudentMarksTO> markList=null;
						if(stuMarksMap.containsKey(Integer.parseInt(bo[0].toString()))){
							markList=stuMarksMap.remove(Integer.parseInt(bo[0].toString()));
						}else{
							markList=new ArrayList<StudentMarksTO>();
						}
						StudentMarksTO to=new StudentMarksTO();
						if(bo[1]!=null && !bo[1].toString().trim().isEmpty()){
							to.setTheoryMarks(bo[1].toString().trim());
						}
						if(bo[2]!=null && !bo[2].toString().trim().isEmpty())
							to.setPracticalMarks(bo[2].toString().trim());
						if(bo[3]!=null)
							to.setEvaId(bo[3].toString());
						if(bo[4]!=null)
							to.setAnsId(bo[4].toString());
						if(bo[1]!=null && !bo[1].toString().trim().isEmpty() && !bo[1].toString().equalsIgnoreCase("AA") && !bo[1].toString().equalsIgnoreCase("NP")){
							if(bo[10] != null && !bo[10].toString().isEmpty() && Integer.parseInt(bo[10].toString())>=2012){
								to.setTheoryMarks(bo[1].toString().trim());
								if(bo[2]!=null && !bo[2].toString().trim().isEmpty())
									to.setPracticalMarks(bo[2].toString().trim());
								if(bo[3]!=null)
									to.setEvaId(bo[3].toString());
								if(bo[4]!=null)
									to.setAnsId(bo[4].toString());
								if(bo[5]!=null && !bo[5].toString().trim().isEmpty()){
									if(bo[5].toString().equalsIgnoreCase("true")){
										if(bo[6]!=null && !bo[6].toString().trim().isEmpty()){
											if(to.getTheoryMarks() != null && !to.getTheoryMarks().equalsIgnoreCase(bo[6].toString().trim())){
												if(errMsg == null || errMsg.isEmpty()){
													errMsg = "Could not run the OverAll Process. There are Marks Mismatch for the student ";
												}
												errMsg = errMsg + ", " +bo[8].toString() + " - "+bo[7].toString()+" ("+bo[9].toString()+")";
											}
										}else if(to.getTheoryMarks() != null && bo[6] == null){
											if(errMsg == null || errMsg.isEmpty()){
												errMsg = "Could not run the OverAll Process. There are Marks Mismatch for the student ";
											}
											errMsg = errMsg + ", " +bo[8].toString() + " - "+bo[7].toString()+" ("+bo[9].toString()+")";
										}
									}
								}
							}
							
						}
						markList.add(to);
						stuMarksMap.put(Integer.parseInt(bo[0].toString()),markList);
					}
					revaluationMarksUpdateForm.setErrorMessage(errMsg);
				}
				return stuMarksMap;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException();
			} finally {
				if (session != null) {
					session.flush();
				}
			}
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#saveRegularOverAll(java.util.List)
		 */
		@Override
		public boolean saveRegularOverAll(List<StudentFinalMarkDetails> boList)
				throws Exception {
			log.debug("inside addTermsConditionCheckList");
			Session session = null;
			Transaction transaction = null;
			StudentFinalMarkDetails bo;
			try {
				session = InitSessionFactory.getInstance().openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				Iterator<StudentFinalMarkDetails> tcIterator = boList.iterator();
				int count = 0;
				while(tcIterator.hasNext()){
					bo = tcIterator.next();
					StudentFinalMarkDetails bo1=(StudentFinalMarkDetails)session.createQuery("from StudentFinalMarkDetails s where s.student.id=" +bo.getStudent().getId()+
							" and s.classes.id=" +bo.getClasses().getId()+
							" and s.exam.id= " +bo.getExam().getId()+
							"and s.subject.id="+bo.getSubject().getId()).uniqueResult();
					if(bo1!=null){
						bo1.setLastModifiedDate(new Date());
						bo1.setModifiedBy(bo.getModifiedBy());
						bo1.setStudentTheoryMarks(bo.getStudentTheoryMarks());
						bo1.setStudentPracticalMarks(bo.getStudentPracticalMarks());
						bo1.setPassOrFail(bo.getPassOrFail());
						bo1.setSubjectPracticalMark(bo.getSubjectPracticalMark());
						bo1.setSubjectTheoryMark(bo.getSubjectTheoryMark());
						session.update(bo1);
					}else{
						session.save(bo);
					}
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

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.exam.IRevaluationMarksUpdateTransaction#getExamRevaluationAppDetails(com.kp.cms.forms.exam.RevaluationMarksUpdateForm)
		 */
		@Override
		public ExamRevaluationAppDetails getExamRevaluationAppDetails(RevaluationMarksUpdateForm revaluationMarksUpdateForm)
				throws Exception {
			Session session = null;
			ExamRevaluationAppDetails details = null;
			try{
				session = HibernateUtil.getSession();
				String query = "from ExamRevaluationAppDetails e where e.isActive=1 and e.examRevApp.isActive=1 and e.examRevApp.student.id="+revaluationMarksUpdateForm.getStudentid()+
								" and e.subject.id="+revaluationMarksUpdateForm.getSubjectid()+
								" and e.examRevApp.exam.id="+revaluationMarksUpdateForm.getExamId()+
								" and e.examRevApp.classes.id="+revaluationMarksUpdateForm.getClassid();
				details = (ExamRevaluationAppDetails)session.createQuery(query).uniqueResult();
			}catch (Exception e) {
				log.error("Error while retrieving getExamRevaluationAppDetails" +e);
				if (session != null) {
					session.flush();
				}
				throw  new ApplicationException();
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			return details;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.exam.IRevaluationMarksUpdateTransaction#updateRevaluationApllicationStatus(com.kp.cms.bo.exam.ExamRevaluationAppDetails)
		 */
		@Override
		public void updateRevaluationApllicationStatus(ExamRevaluationAppDetails revaluation) throws Exception {
			Session session = null;
			Transaction tx = null;
			try{
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				session.save(revaluation);
				tx.commit();
				session.flush();
			}catch (Exception e) {
				if(tx != null){
					tx.rollback();
				}
				if(session != null){
					session.flush();
				}
			}finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			
			
		}

		@Override
		public List getDataByQueryForAllClasses(String query,
				List<Integer> classList) throws Exception {
			Session session = null;
			List selectedCandidatesList = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(query).setParameterList("classesList", classList);
				selectedCandidatesList = selectedCandidatesQuery.list();
				return selectedCandidatesList;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.exam.INewUpdateProccessTransaction#getDataByQuery(java.lang.String)
		 */
		public List getDataByQuery(String query,List<Integer> classList) throws Exception {
			Session session = null;
			List selectedCandidatesList = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery(query).setParameterList("classesList", classList);
				selectedCandidatesList = selectedCandidatesQuery.list();
				return selectedCandidatesList;
			} catch (Exception e) {
				log.error("Error while retrieving selected candidates.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}
		
		public Double getSubjectMaxMark(RevaluationMarksUpdateForm form,int courseId,int schemeNo,int subjectId) throws Exception {
			Session session = null;
			Double maxMarks=null;
			//String subType="";
			//Map<Integer, Double> map = new HashMap<Integer, Double>();
			try{
				session = HibernateUtil.getSession();
				String query="select s.theoryEseEnteredMaxMark, s.academicYear from SubjectRuleSettings s" +
						" where s.course.id="+courseId+
						" and s.schemeNo=" +schemeNo+
						" and s.subject.id=" +subjectId+
						"and  s.isActive=1";
				if(form.getExamType().equals("Supplementary")){
					query=query+" and s.academicYear>=(select e.examForJoiningBatch from ExamDefinitionBO e where e.id="+form.getExamId()+") ";
				}else{
					query=query+" and s.academicYear=(select e.academicYear from ExamDefinitionBO e where e.id="+form.getExamId()+") ";
				}
				
				List<Object[]> list=session.createQuery(query).list();
				if(list!=null && !list.isEmpty()){
					Iterator<Object[]> itr=list.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
							if(objects[0]!=null){
								maxMarks=new Double(objects[0].toString());
								/*if(objects[1]!=null && objects[1].toString()!=null)
									map.put(Integer.parseInt(objects[1].toString()), maxMarks);*/
							}
						}
					}
				//form.setMaxMarksMap(map);
			} catch (Exception e) {
				log.error("Error while retrieving ExamAbscentCode.." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
			return maxMarks;
		}
}
