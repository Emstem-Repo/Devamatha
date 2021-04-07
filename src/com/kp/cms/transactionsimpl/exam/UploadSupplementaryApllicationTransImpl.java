package com.kp.cms.transactionsimpl.exam;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.exam.IUploadSupplementaryApllicationTransaction;
import com.kp.cms.utilities.InitSessionFactory;

	public class UploadSupplementaryApllicationTransImpl implements IUploadSupplementaryApllicationTransaction{
		private static volatile UploadSupplementaryApllicationTransImpl exportSupplementaryApllicationTransImpl = null;
		private static final Log log = LogFactory.getLog(UploadSupplementaryApllicationTransImpl.class);
		public UploadSupplementaryApllicationTransImpl() {
			
		}
		public static UploadSupplementaryApllicationTransImpl getInstance() {
			if (exportSupplementaryApllicationTransImpl == null) {
				exportSupplementaryApllicationTransImpl = new UploadSupplementaryApllicationTransImpl();
			}
			return exportSupplementaryApllicationTransImpl;
		}
		
		public boolean saveSupplementaryDetails(List<ExamSupplementaryImprovementApplicationBO> examSupplementaryImprovementApplicationBOs) throws Exception{
			Session session  = null;
			Transaction transaction =null;
			try{
				session=InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				Iterator<ExamSupplementaryImprovementApplicationBO> iterator = examSupplementaryImprovementApplicationBOs.iterator();
				while (iterator.hasNext()) {
					ExamSupplementaryImprovementApplicationBO examSupplementaryBO = (ExamSupplementaryImprovementApplicationBO) iterator.next();
					ExamSupplementaryImprovementApplicationBO dupSupBO = (ExamSupplementaryImprovementApplicationBO) session.createQuery("from ExamSupplementaryImprovementApplicationBO examSup where examSup.studentId="+examSupplementaryBO.getStudentId()+" and examSup.subjectId="+examSupplementaryBO.getSubjectId()+" and examSup.examId="+examSupplementaryBO.getExamId()).uniqueResult();
					if(dupSupBO == null)
					session.save(examSupplementaryBO);
				}
				transaction.commit();
				return true;	
			}catch (Exception e) {
				throw new ApplicationException(e);
			}finally{
				if(session!=null)
				session.flush();
			}
			
		}
}
