package com.kp.cms.transactionsimpl.sap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.sap.SapMarksUploadEntryForm;
import com.kp.cms.handlers.sap.SapMarksUploadEntryHandler;
import com.kp.cms.to.sap.SapMarksUploadEntryTO;
import com.kp.cms.transactions.sap.ISapMarksUploadEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SapMarksUploadEntryTransactionImpl implements ISapMarksUploadEntryTransaction {
	  
	private static final Log log = LogFactory.getLog(SapMarksUploadEntryTransactionImpl.class);
	private static volatile SapMarksUploadEntryTransactionImpl sapMarksUploadEntryTransactionImpl = null;
	private SapMarksUploadEntryTransactionImpl(){
		
	}
	public static SapMarksUploadEntryTransactionImpl getInstance() {
		if (sapMarksUploadEntryTransactionImpl == null) {
			sapMarksUploadEntryTransactionImpl = new SapMarksUploadEntryTransactionImpl();
		}
		return sapMarksUploadEntryTransactionImpl;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.sap.ISapMarksUploadEntryTransaction#getStudentDetailsByRegNo(java.lang.String)
	 */
	@Override
	public Student getStudentDetailsByRegNo(String regNo) throws Exception {
		Student student=null;
		try
		{
			log.info("entered getStudentDetailsByRegNo method");
			Session session=HibernateUtil.getSession();
			String Query="from Student student where student.registerNo='"+regNo+"' and student.isActive=1";
			student=(Student) session.createQuery(Query).uniqueResult();
			return student;
		}
		catch (Exception e) {
			log.info("entered getStudentDetailsByRegNo method exception");
			throw new BusinessException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.sap.ISapMarksUploadEntryTransaction#checkSapMarksAlreadyExists(java.util.List, com.kp.cms.forms.sap.SapMarksUploadEntryForm)
	 */
	@Override
	public List<MarksEntryDetails> checkSapMarksAlreadyExists(List<SapMarksUploadEntryTO> marksUploadEntryTOList,
			SapMarksUploadEntryForm marksUploadEntryForm) throws Exception {
		List<MarksEntryDetails> entryDetailsList=new ArrayList<MarksEntryDetails>();
		for (SapMarksUploadEntryTO sapMarksUploadEntryTO : marksUploadEntryTOList) {
			MarksEntryDetails entryDetails=null;
			String query="from MarksEntryDetails md where md.subject.id= " +marksUploadEntryForm.getSubjectId()+
			" and md.marksEntry.exam.id= " +marksUploadEntryForm.getExamId()+
			" and md.marksEntry.classes.id= " +sapMarksUploadEntryTO.getClassId()+
			" and md.marksEntry.student.id='"+sapMarksUploadEntryTO.getStudentId()+"'";
			Session session = null;
	     try {
	    	 session = HibernateUtil.getSession();
	    	 Query selectedCandidatesQuery=session.createQuery(query);
	    	 entryDetails = (MarksEntryDetails) selectedCandidatesQuery.uniqueResult();
	    	 if(entryDetails!=null){
	    		 entryDetailsList.add(entryDetails);
	    	 }
	     	} catch (Exception e) {
	     		throw  new ApplicationException(e);
	     	} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
		return entryDetailsList;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.sap.ISapMarksUploadEntryTransaction#saveSapMarksUploaded(java.util.List, com.kp.cms.forms.sap.SapMarksUploadEntryForm)
	 */
	@Override
	public boolean saveSapMarksUploaded(List<SapMarksUploadEntryTO> marksUploadEntryTOList,SapMarksUploadEntryForm marksEntryForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			log.info("entered saveSapMarksUploaded method");
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<SapMarksUploadEntryTO> itr=marksUploadEntryTOList.iterator();
			while (itr.hasNext()) {
				SapMarksUploadEntryTO to = (SapMarksUploadEntryTO) itr.next();
					MarksEntry marksEntry=null;
					String query="from MarksEntry m where m.exam.id="+marksEntryForm.getExamId()+" and m.student.id="+to.getStudentId()+" and m.classes.id="+to.getClassId();
					List<MarksEntry> marksEntrys=session.createQuery(query).list();
					if(marksEntrys==null || marksEntrys.isEmpty()){
						marksEntry=new MarksEntry();
						Student student=new Student();
						student.setId(Integer.parseInt(to.getStudentId()));
						marksEntry.setStudent(student);
						ExamDefinitionBO exam=new ExamDefinitionBO();
						exam.setId(Integer.parseInt(marksEntryForm.getExamId()));
						marksEntry.setExam(exam);
						Classes classes=new Classes();
						classes.setId(to.getClassId());
						marksEntry.setClasses(classes);
						marksEntry.setCreatedBy(marksEntryForm.getUserId());
						marksEntry.setCreatedDate(new Date());
						marksEntry.setModifiedBy(marksEntryForm.getUserId());
						marksEntry.setLastModifiedDate(new Date());
						Set<MarksEntryDetails> marksEntryDetails=new HashSet<MarksEntryDetails>();
						MarksEntryDetails detail=new MarksEntryDetails();
						Subject subject=new Subject();
						subject.setId(Integer.parseInt(marksEntryForm.getSubjectId()));
						detail.setSubject(subject);
						detail.setTheoryMarks(to.getMarks());
						detail.setCreatedBy(marksEntryForm.getUserId());
						detail.setCreatedDate(new Date());
						detail.setModifiedBy(marksEntryForm.getUserId());
						detail.setLastModifiedDate(new Date());
						detail.setIsPracticalSecured(false);
						detail.setIsTheorySecured(false);
						marksEntryDetails.add(detail);
						marksEntry.setMarksDetails(marksEntryDetails);
						session.save(marksEntry);
					}else{
						Iterator<MarksEntry> marksitr=marksEntrys.iterator();
						if (marksitr.hasNext()) {
							 marksEntry = (MarksEntry) marksitr.next();
						}
						MarksEntryDetails detail=(MarksEntryDetails)session.createQuery("from MarksEntryDetails m where m.marksEntry.id="+marksEntry.getId()+"" +
								" and m.subject.id="+marksEntryForm.getSubjectId()).uniqueResult();
						if(detail==null){
							detail=new MarksEntryDetails();
							Subject subject=new Subject();
							subject.setId(Integer.parseInt(marksEntryForm.getSubjectId()));
							detail.setSubject(subject);
							detail.setCreatedBy(marksEntryForm.getUserId());
							detail.setCreatedDate(new Date());
						}
						detail.setMarksEntry(marksEntry);
						detail.setTheoryMarks(to.getMarks());
						detail.setIsTheorySecured(false);
						detail.setIsPracticalSecured(false);
						detail.setModifiedBy(marksEntryForm.getUserId());
						detail.setLastModifiedDate(new Date());
						marksEntry.setModifiedBy(marksEntryForm.getUserId());
						marksEntry.setLastModifiedDate(new Date());
						session.saveOrUpdate(detail);
						session.update(marksEntry);
					}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
	}

	@Override
	public Object[] getExamSettingsForCheckingMarks() throws Exception {
		Object[] object=null;
		try
		{
			log.info("entered getExamSettingsForCheckingMarks method");
			Session session=HibernateUtil.getSession();
			String Query="select settings.absentCodeMarkEntry,settings.notProcedCodeMarkEntry from ExamSettingsBO settings where settings.isActive=1";
			object=(Object[]) session.createQuery(Query).uniqueResult();
			return object;
			
		}
		catch (Exception e) {
			log.info("entered getStudentDetailsByRegNo method exception");
			throw new BusinessException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.sap.ISapMarksUploadEntryTransaction#getStudentCourseDetailsByExamId(java.lang.String)
	 */
	@Override
	public List<Integer> getStudentCourseDetailsByExamId(String examId)
			throws Exception {
		List<Integer> studentCourseList=null;
		try
		{
			log.info("entered getStudentCourseDetailsByExamId method");
			Session session=HibernateUtil.getSession();
			String Query="select course.course.id from CourseSchemeDetails course where course.examDefinition.id='"+examId+"' and course.isActive=1 ";
			studentCourseList= session.createQuery(Query).list();
			return studentCourseList;
			
		}
		catch (Exception e) {
			log.info("entered getStudentCourseDetailsByExamId method exception");
			throw new BusinessException(e.getMessage());
		}
	}

}
