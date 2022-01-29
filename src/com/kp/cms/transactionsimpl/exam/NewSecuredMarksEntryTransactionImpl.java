package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewSecuredMarksEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class NewSecuredMarksEntryTransactionImpl implements
		INewSecuredMarksEntryTransaction {
	/**
	 * Singleton object of NewSecuredMarksEntryTransactionImpl
	 */
	private static volatile NewSecuredMarksEntryTransactionImpl newSecuredMarksEntryTransactionImpl = null;
	private static final Log log = LogFactory.getLog(NewSecuredMarksEntryTransactionImpl.class);
	private NewSecuredMarksEntryTransactionImpl() {
		
	}
	/**
	 * return singleton object of NewSecuredMarksEntryTransactionImpl.
	 * @return
	 */
	public static NewSecuredMarksEntryTransactionImpl getInstance() {
		if (newSecuredMarksEntryTransactionImpl == null) {
			newSecuredMarksEntryTransactionImpl = new NewSecuredMarksEntryTransactionImpl();
		}
		return newSecuredMarksEntryTransactionImpl;
	}

	/**
	 * gets income ID for CSV Matching DATA
	 * @param data
	 * @return
	 */
	public String getPropertyData(int id, String boName, boolean isActive,String property)throws Exception{
		String result= "";
		Session session = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
			 String query="";
			 if(isActive){
				 if(boName.equals("ExamDefinitionBO")){
					 query="select bo."+property+" from "+boName+" bo where bo.id=:id and bo.delIsActive=1";
				 }else if(boName.equals("ExamTypeUtilBO")){
					 query="select bo."+property+" from "+boName+" bo where bo.id=:id";
				 }else{
					 query="select bo."+property+" from "+boName+" bo where bo.id=:id and bo.isActive=1";
				 }
			 }else{
				 if(id>0)
					 query="select bo."+property+" from "+boName+" bo where bo.id=:id";
				 else
					 query="select bo."+property+" from "+boName+" bo ";
			 }
			 Query qr = session.createQuery(query);
			 if(id>0)
			 qr.setInteger("id",id);
			 
			 String obj=String.valueOf(qr.uniqueResult());
			 
			 if(obj!=null && obj!=null){
				 result=obj;
			 } 
		 }catch (Exception e) {
			 throw e; 
		 }finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.INewSecuredMarksEntryTransaction#saveMarks(com.kp.cms.forms.exam.NewSecuredMarksEntryForm)
	 */
	@Override
	public boolean saveMarks(NewSecuredMarksEntryForm newSecuredMarksEntryForm)	throws Exception {
		log.debug("inside addTermsConditionCheckList");
		Session session = null;
		Transaction transaction = null;
		List<StudentMarksTO> list=newSecuredMarksEntryForm.getMainList();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<StudentMarksTO> itr=list.iterator();
			while (itr.hasNext()) {
				StudentMarksTO to = (StudentMarksTO) itr.next();
				if(to.getId()>0){
					MarksEntryDetails detailBo=(MarksEntryDetails)session.get(MarksEntryDetails.class, to.getId());
					if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
						if(to.getPracticalMarks()!=null)
						detailBo.setPracticalMarks(to.getPracticalMarks().trim());
						detailBo.setIsPracticalSecured(true);
					}
					if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
						if(to.getTheoryMarks()!=null)
						detailBo.setTheoryMarks(to.getTheoryMarks().trim());
						detailBo.setIsTheorySecured(true);
					}
					detailBo.setModifiedBy(newSecuredMarksEntryForm.getUserId());
					detailBo.setLastModifiedDate(new Date());
					if(to.getRetest()!=null && to.getRetest().equalsIgnoreCase("on"))
						detailBo.setIsRetest(true);
					//code added by mehaboob 
					if(to.getOldMarks()!=null && !to.getOldMarks().isEmpty() && to.getOldMarks().equalsIgnoreCase("AA")){
					if(newSecuredMarksEntryForm.getExamType().equalsIgnoreCase("Internal") && newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
						int midSemLessWeightage=0;
						int reducingMarks=0;
						int afterReducingMarks=0;
						if(newSecuredMarksEntryForm.getStudentIdList()!=null && !newSecuredMarksEntryForm.getStudentIdList().isEmpty()){
						if(!newSecuredMarksEntryForm.getStudentIdList().contains(to.getStudentId())){
					             midSemLessWeightage=CMSConstants.MID_SEM_LESS_WEIGHTAGE;
					             reducingMarks=(newSecuredMarksEntryForm.getMaxMarks()*midSemLessWeightage)/100;
					             if(to.getTheoryMarks()!=null)
					             afterReducingMarks=Integer.parseInt(to.getTheoryMarks().trim())-reducingMarks;
					         if(afterReducingMarks<0){
					        	 detailBo.setTheoryMarks("00");
					        	 if(to.getTheoryMarks()!=null)
					        	 detailBo.setOriginalMarks(to.getTheoryMarks().trim());
					         }else{
					        	 if(afterReducingMarks<10){
					        		 detailBo.setTheoryMarks("0"+String.valueOf(afterReducingMarks));
					        	 }else{
					        		 detailBo.setTheoryMarks(String.valueOf(afterReducingMarks));	 
					        	 }
					        	 if(to.getTheoryMarks()!=null)
					        	 detailBo.setOriginalMarks(to.getTheoryMarks().trim());
					         }
						}else{
							if(to.getTheoryMarks()!=null){
							detailBo.setTheoryMarks(to.getTheoryMarks().trim());
							detailBo.setOriginalMarks(to.getTheoryMarks().trim());
							}
						}
						}else{
							     midSemLessWeightage=CMSConstants.MID_SEM_LESS_WEIGHTAGE;
					             reducingMarks=(newSecuredMarksEntryForm.getMaxMarks()*midSemLessWeightage)/100;
					             if(to.getTheoryMarks()!=null)
					             afterReducingMarks=Integer.parseInt(to.getTheoryMarks().trim())-reducingMarks;
					         if(afterReducingMarks<0){
					        	 detailBo.setTheoryMarks("00");
					        	 if(to.getTheoryMarks()!=null)
					        	 detailBo.setOriginalMarks(to.getTheoryMarks().trim());
					         }else{
					        	 if(afterReducingMarks<10){
					        		 detailBo.setTheoryMarks("0"+String.valueOf(afterReducingMarks));	 
					        	 }else{
					        		 detailBo.setTheoryMarks(String.valueOf(afterReducingMarks));
					        	 }
					        	 if(to.getTheoryMarks()!=null)
					        	 detailBo.setOriginalMarks(to.getTheoryMarks().trim());
					         }
						}
					}
					}
					//end code
					session.update(detailBo);
				}else{
					if((newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t") && to.getTheoryMarks()!=null && !to.getTheoryMarks().isEmpty()) || (newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p") && to.getPracticalMarks()!=null && !to.getPracticalMarks().isEmpty())){
						MarksEntry marksEntry=null;
						String query="from MarksEntry m where m.exam.id="+newSecuredMarksEntryForm.getExamId()+" and m.student.id="+to.getStudentId()+" and m.classes.id="+to.getClassId();
						if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty())
							query=query+" and m.evaluatorType="+newSecuredMarksEntryForm.getEvaluatorType();
						else
							query=query+" and m.evaluatorType is null";
						if(newSecuredMarksEntryForm.getAnswerScriptType()!=null && !newSecuredMarksEntryForm.getAnswerScriptType().isEmpty())
							query=query+" and m.answerScript="+newSecuredMarksEntryForm.getAnswerScriptType();
						else
							query=query+" and m.answerScript is null";
						List<MarksEntry> marksEntrys=session.createQuery(query).list();
						if(marksEntrys==null || marksEntrys.isEmpty()){
							marksEntry=new MarksEntry();
							Student student=new Student();
							student.setId(to.getStudentId());
							marksEntry.setStudent(student);
							ExamDefinitionBO exam=new ExamDefinitionBO();
							exam.setId(Integer.parseInt(newSecuredMarksEntryForm.getExamId()));
							marksEntry.setExam(exam);
							if(newSecuredMarksEntryForm.getEvaluatorType()!=null && !newSecuredMarksEntryForm.getEvaluatorType().isEmpty()){
								marksEntry.setEvaluatorType(Integer.parseInt(newSecuredMarksEntryForm.getEvaluatorType()));
							}
							if(newSecuredMarksEntryForm.getAnswerScriptType()!=null && !newSecuredMarksEntryForm.getAnswerScriptType().isEmpty()){
								marksEntry.setAnswerScript(Integer.parseInt(newSecuredMarksEntryForm.getAnswerScriptType()));
							}
							Classes classes=new Classes();
							classes.setId(to.getClassId());
							marksEntry.setClasses(classes);
							marksEntry.setCreatedBy(newSecuredMarksEntryForm.getUserId());
							marksEntry.setCreatedDate(new Date());
							marksEntry.setModifiedBy(newSecuredMarksEntryForm.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							Set<MarksEntryDetails> marksEntryDetails=new HashSet<MarksEntryDetails>();
							
							MarksEntryDetails detail=new MarksEntryDetails();
							Subject subject=new Subject();
							subject.setId(Integer.parseInt(newSecuredMarksEntryForm.getSubjectId()));
							detail.setSubject(subject);
							if(to.getTheoryMarks()!=null)
							detail.setTheoryMarks(to.getTheoryMarks().trim());
							if(to.getPracticalMarks()!=null)
							detail.setPracticalMarks(to.getPracticalMarks().trim());
							detail.setCreatedBy(newSecuredMarksEntryForm.getUserId());
							detail.setCreatedDate(new Date());
							detail.setModifiedBy(newSecuredMarksEntryForm.getUserId());
							detail.setLastModifiedDate(new Date());
							if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
								if(to.getTheoryMarks()!=null)
								detail.setTheoryMarks(to.getTheoryMarks().trim());
								detail.setIsTheorySecured(true);
							}
							if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
								if(to.getPracticalMarks()!=null)
								detail.setPracticalMarks(to.getPracticalMarks().trim());
								detail.setIsPracticalSecured(true);
							}
							//code added by chandra
							detail.setIsGracing(false);
							marksEntryDetails.add(detail);
							marksEntry.setMarksDetails(marksEntryDetails);
							session.save(marksEntry);
						}else{
							Iterator<MarksEntry> marksitr=marksEntrys.iterator();
							if (marksitr.hasNext()) {
								 marksEntry = (MarksEntry) marksitr.next();
							}
							MarksEntryDetails detail=(MarksEntryDetails)session.createQuery("from MarksEntryDetails m where m.marksEntry.id="+marksEntry.getId()+"" +
									" and m.subject.id="+newSecuredMarksEntryForm.getSubjectId()).uniqueResult();
							if(detail==null){
								detail=new MarksEntryDetails();
								Subject subject=new Subject();
								subject.setId(Integer.parseInt(newSecuredMarksEntryForm.getSubjectId()));
								detail.setSubject(subject);
								detail.setCreatedBy(newSecuredMarksEntryForm.getUserId());
								detail.setCreatedDate(new Date());
								//code added by chandra
								detail.setIsGracing(false);
							}
							detail.setMarksEntry(marksEntry);
							
							if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
								if(to.getTheoryMarks()!=null)
								detail.setTheoryMarks(to.getTheoryMarks().trim());
								detail.setIsTheorySecured(true);
							}
							if(newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("p")){
								if(to.getPracticalMarks()!=null)
								detail.setPracticalMarks(to.getPracticalMarks().trim());
								detail.setIsPracticalSecured(true);
							}
							//code added by mehaboob 
							if(to.getOldMarks()!=null && !to.getOldMarks().isEmpty() && to.getOldMarks().equalsIgnoreCase("AA")){
							if(newSecuredMarksEntryForm.getExamType().equalsIgnoreCase("Internal") && newSecuredMarksEntryForm.getSubjectType().equalsIgnoreCase("t")){
								int midSemLessWeightage=0;
								int reducingMarks=0;
								int afterReducingMarks=0;
								if(newSecuredMarksEntryForm.getStudentIdList()!=null && !newSecuredMarksEntryForm.getStudentIdList().isEmpty()){
								if(!newSecuredMarksEntryForm.getStudentIdList().contains(to.getStudentId())){
							             midSemLessWeightage=CMSConstants.MID_SEM_LESS_WEIGHTAGE;
							             reducingMarks=(newSecuredMarksEntryForm.getMaxMarks()*midSemLessWeightage)/100;
							             if(to.getTheoryMarks()!=null)
							             afterReducingMarks=Integer.parseInt(to.getTheoryMarks().trim())-reducingMarks;
							         if(afterReducingMarks<0){
							        	 detail.setTheoryMarks("0");
							        	 if(to.getTheoryMarks()!=null)
							        	 detail.setOriginalMarks(to.getTheoryMarks().trim());
							         }else{
							        	 if(afterReducingMarks<10){
							        		 detail.setTheoryMarks("0"+String.valueOf(afterReducingMarks));	 
							        	 }else{
							        		 detail.setTheoryMarks(String.valueOf(afterReducingMarks));
							        	 }
							        	 if(to.getTheoryMarks()!=null)
							        	 detail.setOriginalMarks(to.getTheoryMarks().trim());
							         }
								}else{
									if(to.getTheoryMarks()!=null){
									detail.setTheoryMarks(to.getTheoryMarks().trim());
									detail.setOriginalMarks(to.getTheoryMarks().trim());
									}
								}
								}else{
									     midSemLessWeightage=CMSConstants.MID_SEM_LESS_WEIGHTAGE;
							             reducingMarks=(newSecuredMarksEntryForm.getMaxMarks()*midSemLessWeightage)/100;
							             if(to.getTheoryMarks()!=null)
							             afterReducingMarks=Integer.parseInt(to.getTheoryMarks().trim())-reducingMarks;
							         if(afterReducingMarks<0){
							        	 detail.setTheoryMarks("00");
							        	 if(to.getTheoryMarks()!=null)
							        	 detail.setOriginalMarks(to.getTheoryMarks().trim());
							         }else{
							        	 if(afterReducingMarks<10){
							        		 detail.setTheoryMarks("0"+String.valueOf(afterReducingMarks));	 
							        	 }else{
							        		 detail.setTheoryMarks(String.valueOf(afterReducingMarks));
							        	 }
							        	 if(to.getTheoryMarks()!=null)
							        	 detail.setOriginalMarks(to.getTheoryMarks().trim());
							         }
								}
							}
							}
							//end code
							detail.setModifiedBy(newSecuredMarksEntryForm.getUserId());
							detail.setLastModifiedDate(new Date());
							marksEntry.setModifiedBy(newSecuredMarksEntryForm.getUserId());
							marksEntry.setLastModifiedDate(new Date());
							session.saveOrUpdate(detail);
							session.update(marksEntry);
						}
					}
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
}
