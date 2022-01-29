package com.kp.cms.transactionsimpl.exam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.exam.IUploadPreviousClassTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

	public class UploadPreviousClassTransImpl implements IUploadPreviousClassTransaction{
		private static final Log log = LogFactory.getLog(UploadAddlBiodataInfoTransImpl.class);
		
		private static volatile UploadAddlBiodataInfoTransImpl exportAddlBiodataInfoTransImpl = null;

		public static UploadAddlBiodataInfoTransImpl getInstance() {
			
			if (exportAddlBiodataInfoTransImpl == null) {
				exportAddlBiodataInfoTransImpl = new UploadAddlBiodataInfoTransImpl();
			}
			return exportAddlBiodataInfoTransImpl;
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IExportPreviousClassTransaction#getStudentDetails()
		 */
		public Map<String,StudentTO> getStudentDetails() throws Exception{
			Session session  = null;
			try{
				session=HibernateUtil.getSession();
			List<Student> studentList = session.createQuery("from Student s where s.registerNo is not null"+
					" and s.registerNo !='' and s.isActive=1 and s.admAppln.isCancelled=0").list();
			Iterator<Student> iterator = studentList.iterator();
			StudentTO studentTO;
			Map<String,StudentTO> studentMap = new HashMap<String, StudentTO>();
			while (iterator.hasNext()) {
				Student student = (Student) iterator.next();
				studentTO = new StudentTO();
				studentTO.setId(student.getId());
				studentTO.setRegisterNo(student.getRegisterNo());
				if(student.getClassSchemewise()!=null){
				studentTO.setAppliedYear(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear());
				studentTO.setClassName(student.getClassSchemewise().getClasses().getName());
				studentTO.setClassId(student.getClassSchemewise().getClasses().getId());
				}
				studentMap.put(student.getRegisterNo(), studentTO);
			}
			return studentMap;
			}catch (Exception e) {
				throw new ApplicationException(e);
			}finally{
				if(session!=null)
				session.flush();
			}
		}
		
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admission.IExportPreviousClassTransaction#addUploadedData(java.util.List)
		 */
		public boolean addUploadedData(List<ExamStudentPreviousClassDetailsBO> studentPreviousClassBOList) throws Exception{
			Session session  = null;
			Transaction transaction =null;
			try{
				session=InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				Iterator<ExamStudentPreviousClassDetailsBO> iterator = studentPreviousClassBOList.iterator();
				while (iterator.hasNext()) {
					ExamStudentPreviousClassDetailsBO examStudentpreClassBO = (ExamStudentPreviousClassDetailsBO) iterator.next();
					ExamStudentPreviousClassDetailsBO dupPreClassBO = (ExamStudentPreviousClassDetailsBO) session.createQuery("from ExamStudentPreviousClassDetailsBO pc where pc.studentId="+examStudentpreClassBO.getStudentId()+" and pc.classId="+examStudentpreClassBO.getClassId()).uniqueResult();
					if(dupPreClassBO == null)
					session.save(examStudentpreClassBO);
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
		
		public Map<String, Integer> getclassMap() throws Exception {
			Map<String,Integer> studentMap=new HashMap<String, Integer>();
			Session session = null;
			List<Object[]> student = null;
			try {
				session = HibernateUtil.getSession();
				Query selectedCandidatesQuery=session.createQuery("select schemewise.classes.name,schemewise.curriculumSchemeDuration.academicYear,schemewise.classes.id" +
						" from ClassSchemewise schemewise where schemewise.classes.course.isActive = 1 and schemewise.classes.isActive = 1 ");
				student = selectedCandidatesQuery.list();
				if(student!=null && !student.isEmpty()){
					Iterator<Object[]> itr=student.iterator();
					while (itr.hasNext()) {
						Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null && objects[1]!=null && objects[2]!=null){
							studentMap.put(objects[0].toString().trim().toLowerCase()+"_"+objects[1].toString(),Integer.parseInt(objects[2].toString()));
						}
					}
				}
				return studentMap;
			} catch (Exception e) {
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
				}
			}
			
		}
}
