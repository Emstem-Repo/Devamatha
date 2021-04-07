package com.kp.cms.transactionsimpl.exam;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.forms.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.handlers.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseHandler;
import com.kp.cms.to.exam.RevaluationOrRetotalingMarksEntryForSubjectWiseTo;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl implements IRevaluationOrRetotalingMarksEntryForSubjectWiseTransaction{
	private static volatile RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl revaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl = null;
	private static final Log log = LogFactory.getLog(RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl.class);
	
	/**
	 * @return
	 */
	public static RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl getInstance() {
		if (revaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl == null) {
			revaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl = new RevaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl();
		}
		return revaluationOrRetotalingMarksEntryForSubjectWiseTransactionImpl;
	}
	
	public List<Object> getStudentDetailsList(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			
			String str="select ed.examRevApp.student.id,ed.examRevApp.student.registerNo,"+
						" ed.examRevApp.classes.name,ed.examRevApp.classes.id,"+
						" ed.examRevApp.id,ed.id,"+
						" ed.examRevApp.classes.course.id,cw.curriculumSchemeDuration.semesterYearNo,"+
						" cw.curriculumSchemeDuration.academicYear,ed.marks,"+
						" ed.mark1,ed.mark2,ed.thirdEvlMarks"+
						" from ExamRevaluationAppDetails ed" +
					  	" inner join ed.examRevApp.classes.classSchemewises cw"+
					  	" where ed.examRevApp.exam.id="+form.getExamId()+
						" and ed.subject.id="+form.getSubjectId();
			
			
			Query query=session.createQuery(str);
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
	
	
	public Double getMaxMarkOfSubject(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form) throws Exception {
		Session session = null;
		Double maxMarks=null;
		String subType="";
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		try{
			session = HibernateUtil.getSession();
			String query="select s.theoryEseEnteredMaxMark, s.academicYear from SubjectRuleSettings s" +
					" where s.course.id="+form.getCourse()+
					" and s.schemeNo=" +form.getSchemeNo()+
					" and s.subject.id=" +form.getSubjectId()+
					"and s.academicYear="+form.getYear()+"and s.isActive=1";
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
	
	
	public boolean saveMarks(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form)	throws Exception {
		log.debug("inside saveMarks");
		Session session = null;
		Transaction transaction = null;
		boolean saveMarks=false;
		List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> list=form.getMainList();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> itr=list.iterator();
			while (itr.hasNext()) {
				RevaluationOrRetotalingMarksEntryForSubjectWiseTo to = (RevaluationOrRetotalingMarksEntryForSubjectWiseTo) itr.next();
				if(to.getId()>0){
					ExamRevaluationAppDetails detailBo=(ExamRevaluationAppDetails)session.get(ExamRevaluationAppDetails.class, to.getId());
					detailBo.setModifiedBy(form.getUserId());
					detailBo.setLastModifiedDate(new Date());
					if(form.getEvaluatorType().equalsIgnoreCase("1")){
						detailBo.setMark1(to.getTheoryMarks());
					}else if(form.getEvaluatorType().equalsIgnoreCase("2")){
						detailBo.setMark2(to.getTheoryMarks());
					}else if(form.getEvaluatorType() == null || form.getEvaluatorType().isEmpty()){
						detailBo.setMarks(to.getTheoryMarks());
					}
					session.update(detailBo);
					saveMarks=true;
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving saveMarks");
			return saveMarks;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in saveMarks impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in saveMarks impl...", e);
			throw new ApplicationException(e);
		}
	}
	public Double getMaxMarkOfSubjectValidation(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form,int couseId,int schemeNo) throws Exception {
		Session session = null;
		Double maxMarks=null;
		String subType="";
		try{
			session = HibernateUtil.getSession();
			String query="select s.theoryEseEnteredMaxMark, s.academicYear from SubjectRuleSettings s" +
					" where s.course.id="+couseId+
					" and s.schemeNo=" +schemeNo+
					" and s.subject.id=" +form.getSubjectId()+
					"and s.academicYear="+form.getYear()+"and s.isActive=1";
			List<Object[]> list=session.createQuery(query).list();
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] objects = (Object[]) itr.next();
						if(objects[0]!=null){
							maxMarks=new Double(objects[0].toString());
						}
					}
				}
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
	
	
	public boolean saveMarksThirdEvaluationMarks(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form)	throws Exception {
		log.debug("inside saveMarks");
		Session session = null;
		Transaction transaction = null;
		boolean saveMarks=false;
		List<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> list=form.getMainList();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			int count = 0;
			Iterator<RevaluationOrRetotalingMarksEntryForSubjectWiseTo> itr=list.iterator();
			while (itr.hasNext()) {
				RevaluationOrRetotalingMarksEntryForSubjectWiseTo to = (RevaluationOrRetotalingMarksEntryForSubjectWiseTo) itr.next();
				if(to.getId()>0){
					ExamRevaluationAppDetails detailBo=(ExamRevaluationAppDetails)session.get(ExamRevaluationAppDetails.class, to.getId());
					detailBo.setModifiedBy(form.getUserId());
					detailBo.setLastModifiedDate(new Date());
						detailBo.setThirdEvlMarks(to.getTheoryMarks());
					session.update(detailBo);
					saveMarks=true;
				}
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving saveMarks");
			return saveMarks;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in saveMarks impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in saveMarks impl...", e);
			throw new ApplicationException(e);
		}
	}
	
	public boolean isStudentAppliedForThirdEvl(RevaluationOrRetotalingMarksEntryForSubjectWiseForm form,int exanRevaluationAppId) throws Exception {
		Session session = null;
		boolean isAppliedForThirdEvl=false;
		try {
			session = HibernateUtil.getSession();
			if(form.getSubjectId()!=null && !form.getSubjectId().isEmpty()){
			String str=" select  thirdEvaluation from ExamRevaluationAppDetails ed where examRevApp.id="+exanRevaluationAppId +
						" and ed.subject.id="+form.getSubjectId();
			Query query=session.createQuery(str);
			boolean thirdEvaluation= (Boolean)query.uniqueResult();
			if(thirdEvaluation){
				isAppliedForThirdEvl=true;
				}
			}
			return isAppliedForThirdEvl;
		} catch (Exception e) {
			log.error("Error while retrieving selected candidates.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
	}
	
}
