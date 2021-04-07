package com.kp.cms.transactionsimpl.exam;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.AdmApplnTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.transactions.exam.IUploadAddlBiodataInfoTrans;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

	public class UploadAddlBiodataInfoTransImpl implements IUploadAddlBiodataInfoTrans{
		private static final Log log = LogFactory.getLog(UploadAddlBiodataInfoTransImpl.class);
		
		private static volatile UploadAddlBiodataInfoTransImpl exportAddlBiodataInfoTransImpl = null;

		public static UploadAddlBiodataInfoTransImpl getInstance() {
			
			if (exportAddlBiodataInfoTransImpl == null) {
				exportAddlBiodataInfoTransImpl = new UploadAddlBiodataInfoTransImpl();
			}
			return exportAddlBiodataInfoTransImpl;
		}
		
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
				Course course=new Course();
				course.setId(student.getAdmAppln().getCourseBySelectedCourseId().getId());
				AdmApplnTO admApplnTO = new AdmApplnTO();
				admApplnTO.setId(student.getAdmAppln().getId());
				admApplnTO.setApplnNo(Integer.toString(student.getAdmAppln().getApplnNo()));
				admApplnTO.setCourse(course);
				studentTO.setAdmApplnTO(admApplnTO);
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
		
		public Map<String, ExamSpecializationTO> getSpecializationDetails() throws Exception{
			Session session  = null;
			try{
				session=HibernateUtil.getSession();
			List<ExamSpecializationBO> specializationList = session.createQuery("from ExamSpecializationBO e where e.name is not null").list();
			Iterator<ExamSpecializationBO> iterator = specializationList.iterator();
			ExamSpecializationTO specializationTO;
			Map<String,ExamSpecializationTO> specializationMap = new HashMap<String, ExamSpecializationTO>();
			while (iterator.hasNext()) {
				ExamSpecializationBO examSpecializationBO = (ExamSpecializationBO) iterator.next();
				specializationTO = new ExamSpecializationTO();
				specializationTO.setId(examSpecializationBO.getId());
				specializationTO.setName(examSpecializationBO.getName());
				specializationTO.setCourseId(examSpecializationBO.getCourseId());
				specializationMap.put(examSpecializationBO.getName(), specializationTO);
			}
			return specializationMap;
			}catch (Exception e) {
				throw new ApplicationException(e);
			}finally{
				if(session!=null)
				session.flush();
			}
		}
		
		public boolean addUploadedData(List<ExamStudentBioDataBO> studentBOList) throws Exception{
			Session session  = null;
			Transaction transaction =null;
			try{
				session=InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				Iterator<ExamStudentBioDataBO> iterator = studentBOList.iterator();
				while (iterator.hasNext()) {
					ExamStudentBioDataBO examStudentBioDataBO = (ExamStudentBioDataBO) iterator.next();
					ExamStudentBioDataBO student = (ExamStudentBioDataBO) session.createQuery("from ExamStudentBioDataBO s where s.studentId='"+examStudentBioDataBO.getStudentId()+"'").uniqueResult();
					if(student!=null){
						student.setSpecializationId(examStudentBioDataBO.getSpecializationId());
						student.setConsolidatedMarksCardNo(examStudentBioDataBO.getConsolidatedMarksCardNo());
						student.setCourseNameForMarksCard(examStudentBioDataBO.getCourseNameForMarksCard());
						session.update(student);
					}else
					session.save(examStudentBioDataBO);
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
