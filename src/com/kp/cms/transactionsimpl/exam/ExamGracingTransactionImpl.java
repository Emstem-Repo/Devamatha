package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ExamGracingBO;
import com.kp.cms.bo.exam.ExamGracingProcessBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ExamGracingForm;
import com.kp.cms.to.exam.ExamGracingTo;
import com.kp.cms.transactions.exam.IExamGracingTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ExamGracingTransactionImpl implements IExamGracingTransaction{

	@Override
	public List<ExamGracingBO> getStudentList(ExamGracingForm graceForm) throws ApplicationException {
		Session session = null;
		List<ExamGracingBO> list = new LinkedList<ExamGracingBO>();
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery("from ExamGracingBO bo where bo.academicYear=:academic and bo.student.admAppln.appliedYear=:applied and bo.processed=0");
			q.setString("academic",graceForm.getAcademicYear());
			q.setInteger("applied",Integer.parseInt(graceForm.getAppliedYear()));
			list = q.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return list;
	}

	@Override
	public List<ConsolidateMarksCard> getConsolidatedMarks(
			ExamGracingTo gracingTo) throws ApplicationException {
		Session session = null;
		Transaction txn = null;
		List<ConsolidateMarksCard> list = null;
		try {
			session = HibernateUtil.getSession();
			String s = "from ConsolidateMarksCard bo where bo.student.id=:studentId and "+
			" bo.subType!="+"'Practical' and"+
			" bo.classes.id in( select cs.classes.id from ClassSchemewise cs where cs.curriculumSchemeDuration.academicYear=:academic "+
			" and cs.curriculumSchemeDuration.curriculumScheme.course.id=:courseId and cs.curriculumSchemeDuration.curriculumScheme.year=:applied)";
			if(gracingTo.getSemester()>0){
				s = s+" and bo.termNumber=:term"; 
			}
			Query q = session.createQuery(s);
			q.setString("academic",gracingTo.getAcademicYear());
			q.setInteger("applied",Integer.parseInt(gracingTo.getAppliedYear()));
			q.setInteger("studentId",gracingTo.getStudentId());
			q.setInteger("courseId",gracingTo.getCourseId());
			if(gracingTo.getSemester()>0){
			  q.setInteger("term",gracingTo.getSemester());				
			}
			list = q.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return list;
	}

	@Override
	public boolean saveGracing(List<ExamGracingProcessBO> markBoList,ExamGracingForm graceForm)
			throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isSaved = false;
		Set<Integer> graceIds = new HashSet<Integer>();
		graceIds.add(0);
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			for(ExamGracingProcessBO bo :markBoList){
				session.save(bo);
				graceIds.add(bo.getGracingBo().getId());
			}
			txn.commit();
			isSaved=true;
		} catch (Exception e) {
			e.printStackTrace();
			txn.rollback();
			session.flush();
			throw new ApplicationException(e);
		}
		finally{
			if(session!=null)
				session.flush();
		}
		graceForm.setGracingIds(graceIds);
		return isSaved;
	}

	@Override
	public Student getStudent(ExamGracingForm graceForm) throws Exception {
		Session session = null;
		Student bo  = null;
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery("from Student bo where bo.registerNo=:reg");
			q.setString("reg",graceForm.getRegisterNumber());
			bo = (Student) q.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally{
		}
		return bo;
	}

	@Override
	public ExamGracingBO getGracingBo(ExamGracingForm graceForm)
			throws Exception {
		Session session = null;
		ExamGracingBO bo  = null;
		Integer studentId = 0;
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery("from ExamGracingBO bo where bo.student.id=:studentId and bo.academicYear=:academic and (bo.semester=0 or bo.semester=:sem)");
			q.setString("academic",graceForm.getAcademicYear());
			q.setInteger("sem",Integer.parseInt(graceForm.getSchemeId()));
			q.setInteger("studentId", Integer.parseInt(graceForm.getStudentId()));
			bo = (ExamGracingBO) q.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return bo;
	}

	@Override
	public boolean getAssignGracing(ExamGracingForm graceForm,ExamGracingBO bo) throws Exception {
		Session session = null;
		Transaction txn = null;
		boolean isSaved = false;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			session.saveOrUpdate(bo);
			isSaved = true;
			txn.commit();
			isSaved=true;
		} catch (Exception e) {
			txn.rollback();
			session.flush();
			throw new ApplicationException(e);
		}
		finally{
			if(session!=null)
				session.flush();
		}
		return isSaved;
	}

	@Override
	public List<ExamGracingBO> getProcessedStudentList(ExamGracingForm graceForm)
			throws Exception {
		Session session = null;
		List<ExamGracingBO> list = null;
		List<ExamGracingBO> list2 = new ArrayList<ExamGracingBO>();
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery("from ExamGracingBO bo where bo.id in(:ids)");
			q.setParameterList("ids",graceForm.getGracingIds());
			list = q.list();
			if(list!=null && list.size()>0){
				for(ExamGracingBO bo : list){
					bo.setProcessed(true);
					list2.add(bo);
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return list2;
	}

	@Override
	public void setSupplyId(ExamGracingTo gracingTo) throws Exception {
		Session session = null;
		List<Integer> list = null;
		try {
			session = HibernateUtil.getSession();
			String s = "select bo.classes.id from ConsolidateMarksCard bo where bo.student.id=:studentId and "+
			" bo.classes.id in( select cs.classes.id from ClassSchemewise cs where cs.curriculumSchemeDuration.academicYear=:academic "+
			" and cs.curriculumSchemeDuration.curriculumScheme.course.id=:courseId and cs.curriculumSchemeDuration.curriculumScheme.year=:applied)";
			if(gracingTo.getSemester()>0){
				s = s+" and bo.termNumber=:term"; 
			}
			s=s+" group by bo.classes.id";
			Query q = session.createQuery(s);
			q.setString("academic",gracingTo.getAcademicYear());
			q.setInteger("applied",Integer.parseInt(gracingTo.getAppliedYear()));
			q.setInteger("studentId",gracingTo.getStudentId());
			q.setInteger("courseId",gracingTo.getCourseId());
			if(gracingTo.getSemester()>0){
			  q.setInteger("term",gracingTo.getSemester());				
			}
			list = q.list();
			Map<Integer, Integer> suplyids = new HashMap<Integer, Integer>();
			if(list!=null && list.size()>0){
				for(Integer i : list){
					Integer suplyId = null;
					Query q2 = session.createQuery("select min(m.examDefinitionBO.id) from ExamStudentFinalMarkDetailsBO m where m.classUtilBO.id="+i+" and m.examDefinitionBO.examTypeUtilBO.id=6");
					suplyId =  (Integer) q2.uniqueResult();
					if(suplyId!=null)
						suplyids.put(i, suplyId);
					else
						suplyids.put(i, 0);
				}
			}
			gracingTo.setSuppplyIds(suplyids);
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		
	}
   
}
