package com.kp.cms.transactionsimpl.exam;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IUploadExamInternalMarkSupplementaryTransaction;
import com.kp.cms.utilities.HibernateUtil;

	public class UploadExamInternalMarkSupplementaryTransImpl implements IUploadExamInternalMarkSupplementaryTransaction{
		private static final Log log = LogFactory.getLog(UploadExamInternalMarkSupplementaryTransImpl.class);
		private static volatile UploadExamInternalMarkSupplementaryTransImpl examInternalMarkSupplementaryTransImpl = null;
		public UploadExamInternalMarkSupplementaryTransImpl() {
			
		}
		public static UploadExamInternalMarkSupplementaryTransImpl getInstance() {
			if (examInternalMarkSupplementaryTransImpl == null) {
				examInternalMarkSupplementaryTransImpl = new UploadExamInternalMarkSupplementaryTransImpl();
			}
			return examInternalMarkSupplementaryTransImpl;
		}
		
		/* This method used to saveorupdate the uploaded data to database
		 * (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IUploadExamInternalMarkSupplementaryTransaction#saveExamInternalMarkSupplementaryDetailsBOList(java.util.List)
		 */
		public boolean saveExamInternalMarkSupplementaryDetailsBOList(List<ExamInternalMarkSupplementaryDetailsBO> list) throws Exception{
			Session session=null;
			Transaction transaction=null;
			try{
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				Iterator<ExamInternalMarkSupplementaryDetailsBO> iterator =list.iterator();
				while (iterator.hasNext()) {
					ExamInternalMarkSupplementaryDetailsBO examInternalMarkSupplementaryDetailsBO = (ExamInternalMarkSupplementaryDetailsBO) iterator.next();
					ExamInternalMarkSupplementaryDetailsBO oldDetails=(ExamInternalMarkSupplementaryDetailsBO)session.createQuery("from ExamInternalMarkSupplementaryDetailsBO e where e.classes.id="+examInternalMarkSupplementaryDetailsBO.getClasses().getId()+" and e.subject.id="+examInternalMarkSupplementaryDetailsBO.getSubject().getId()+" and e.student.id="+examInternalMarkSupplementaryDetailsBO.getStudent().getId()+" and e.examDefinitionBO.id="+examInternalMarkSupplementaryDetailsBO.getExamDefinitionBO().getId()).uniqueResult();
					if(oldDetails!=null){
						Classes classes = new Classes();
						classes.setId(examInternalMarkSupplementaryDetailsBO.getClasses().getId());
						oldDetails.setClasses(classes);
						ExamDefinitionBO examDefinitionBO = new ExamDefinitionBO();
						examDefinitionBO.setId(examInternalMarkSupplementaryDetailsBO.getExamDefinitionBO().getId());
						oldDetails.setExamDefinitionBO(examDefinitionBO);
						Student student = new Student();
						student.setId(examInternalMarkSupplementaryDetailsBO.getStudent().getId());
						oldDetails.setStudent(student);
						Subject subject = new Subject();
						subject.setId(examInternalMarkSupplementaryDetailsBO.getSubject().getId());
						oldDetails.setSubject(subject);
						oldDetails.setModifiedBy(examInternalMarkSupplementaryDetailsBO.getCreatedBy());
						oldDetails.setLastModifiedDate(examInternalMarkSupplementaryDetailsBO.getCreatedDate());
						oldDetails.setTheoryTotalSubInternalMarks(examInternalMarkSupplementaryDetailsBO.getTheoryTotalSubInternalMarks());
						oldDetails.setPracticalTotalSubInternalMarks(examInternalMarkSupplementaryDetailsBO.getPracticalTotalSubInternalMarks());
						oldDetails.setTheoryTotalAttendenceMarks(examInternalMarkSupplementaryDetailsBO.getTheoryTotalAttendenceMarks());
						oldDetails.setPracticalTotalAttendenceMarks(examInternalMarkSupplementaryDetailsBO.getPracticalTotalAttendenceMarks());
						oldDetails.setTheoryTotalMarks(examInternalMarkSupplementaryDetailsBO.getTheoryTotalMarks());
						oldDetails.setPracticalTotalMarks(examInternalMarkSupplementaryDetailsBO.getPracticalTotalMarks());
						oldDetails.setPassOrFail(examInternalMarkSupplementaryDetailsBO.getPassOrFail());
						session.update(oldDetails);
					}
					else
						session.save(examInternalMarkSupplementaryDetailsBO);
				}
				transaction.commit();
				return true;
			}catch (Exception e) {
				if(transaction!=null){
					transaction.rollback();
				}
				throw new ApplicationException(e);
			}finally{
				if(session!=null){
					session.flush();
				}
			}
		}
}
