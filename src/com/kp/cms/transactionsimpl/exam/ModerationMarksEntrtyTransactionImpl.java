package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.ModerationMarksEntryBo;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.ModerationMarksEntryForm;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.transactions.exam.ImoderationMarksEntryTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ModerationMarksEntrtyTransactionImpl implements ImoderationMarksEntryTransaction {

	private static volatile ModerationMarksEntrtyTransactionImpl moderationMarksEntrtyTransactionImpl = null;
	
	public static ModerationMarksEntrtyTransactionImpl getInstance() {
		if (moderationMarksEntrtyTransactionImpl == null) {
			moderationMarksEntrtyTransactionImpl = new ModerationMarksEntrtyTransactionImpl();
		}
		return moderationMarksEntrtyTransactionImpl;
	}
	@Override
	public boolean checkFinalMark(ModerationMarksEntryForm moderationForm) throws ApplicationException {
		Session session = null;
		boolean check = false;
		try {
			session = HibernateUtil.getSession();
			Query q = session.createQuery("from ExamStudentFinalMarkDetailsBO bo where bo.examId=:exam and bo.classUtilBO.termNumber=:term");
			q.setParameter("exam", Integer.parseInt(moderationForm.getExamId()));
			q.setParameter("term", Integer.parseInt(moderationForm.getSchemeNo()));
			List list = q.list();
			if(list!=null && list.size()!=0){
				check = true;
			}
		} catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		return check;
	}

	@Override
	public List<StudentFinalMarkDetails> getStudentDetails(ModerationMarksEntryForm moderationForm) throws ApplicationException {
		Session session = null;
		List<StudentFinalMarkDetails> list = new LinkedList<StudentFinalMarkDetails>();
		try{
		session = HibernateUtil.getSession();
		String s = "select sfm from StudentFinalMarkDetails sfm join sfm.subject s join s.examSubDefinitionCourseWiseBOSet sdcs" +
		" where sfm.exam.id="+moderationForm.getExamId()+
		" and sfm.classes.termNumber="+ moderationForm.getSchemeNo()+" and sfm.student.registerNo='"+moderationForm.getRegNo()+"' group by s";
		Query q  = session.createQuery(s);
		list = q.list();	
		
		}catch (Exception e) {
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}

	@Override
	public Map<Integer, ModerationMarksEntryBo> getModerationList(ModerationMarksEntryForm modFrm) throws ApplicationException {
		Session session = null;
		Map<Integer,ModerationMarksEntryBo> returnMap = new HashMap<Integer, ModerationMarksEntryBo>();
		List<ModerationMarksEntryBo> boList = new ArrayList<ModerationMarksEntryBo>();
		try{
			session = HibernateUtil.getSession();
			String s = "from ModerationMarksEntryBo bo where bo.examDefinitionBO.id="+modFrm.getExamId()+
			" and bo.studentUtilBo.registerNo='"+modFrm.getRegNo()+"' and bo.classes.termNumber="+modFrm.getSchemeNo();
			if(modFrm.getEntryType().equalsIgnoreCase("R")){
			s= s+" and bo.revaluation=1 and bo.moderation=0";
			}else{
			s= s+" and bo.moderation=1 and bo.revaluation=0";	
			}
			Query q = session.createQuery(s);
			boList = q.list();
			if(boList!= null && boList.size()!=0){
		     Iterator<ModerationMarksEntryBo> itr = boList.iterator();
		     while (itr.hasNext()) {
				ModerationMarksEntryBo bo = (ModerationMarksEntryBo) itr.next();
				returnMap.put(bo.getSubjectUtilBO().getId(), bo);
				
			}
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);

		}finally {
			if (session != null) {
				session.flush();
			}
		}
		return returnMap;
	}

	
	public boolean saveMarks(List<ModerationMarksEntryBo> boList) throws Exception {
		Session session = null;
		Transaction transaction = null;
		ModerationMarksEntryBo tcLChecklist;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<ModerationMarksEntryBo> tcIterator = boList.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				tcLChecklist = tcIterator.next();
				session.saveOrUpdate(tcLChecklist);	
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
	public List<StudentFinalMarkDetails> getStudentDetailsForRevaluation(NewExamMarksEntryForm newExamMarksEntryForm,List stuList) throws ApplicationException {
		Session session = null;
		List<StudentFinalMarkDetails> list = new LinkedList<StudentFinalMarkDetails>();
		try{
		session = HibernateUtil.getSession();
		String s = "select sfm from StudentFinalMarkDetails sfm join sfm.subject s join s.examSubDefinitionCourseWiseBOSet sdcs" +
		" where sfm.exam.id="+newExamMarksEntryForm.getExamId()+" and sfm.student.id in (:studList)" +
		" and sfm.classes.termNumber="+ newExamMarksEntryForm.getSchemeNo()+" and sfm.subject.id="+newExamMarksEntryForm.getSubjectId()+
		"  and sdcs.academicYear="+newExamMarksEntryForm.getYear()+ "  and sdcs.courseId="+newExamMarksEntryForm.getCourseId();
		Query q  = session.createQuery(s).setParameterList("studList", stuList);
		list = q.list();	
		
		}catch (Exception e) {
			e.printStackTrace();
			throw  new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
			}
		}
		return list;
	}
	
	public Map<Integer, ModerationMarksEntryBo> getModerationList(NewExamMarksEntryForm newExamMarksEntryForm,List stuList) throws ApplicationException {
		Session session = null;
		Map<Integer,ModerationMarksEntryBo> returnMap = new HashMap<Integer, ModerationMarksEntryBo>();
		List<ModerationMarksEntryBo> boList = new ArrayList<ModerationMarksEntryBo>();
		try{
			session = HibernateUtil.getSession();
			String s = "from ModerationMarksEntryBo bo where bo.examDefinitionBO.id="+newExamMarksEntryForm.getExamId()+ "  and bo.studentUtilBo.id in (:studList)"+
			" and bo.subjectUtilBO.id="+newExamMarksEntryForm.getSubjectId()+" and bo.classes.termNumber="+newExamMarksEntryForm.getSchemeNo();
			if(newExamMarksEntryForm.getEntryType().equalsIgnoreCase("R")){
				s= s+" and bo.revaluation=1 and bo.moderation=0 and bo.challengeValuation=0 and bo.scrutiny=0";
			}
			if(newExamMarksEntryForm.getEntryType().equalsIgnoreCase("S")){
				s= s+" and bo.scrutiny=1 and bo.moderation=0 and bo.challengeValuation=0 and bo.revaluation=0";
			}
			if(newExamMarksEntryForm.getEntryType().equalsIgnoreCase("CV")){
				s= s+" and bo.challengeValuation=1 and bo.moderation=0 and bo.scrutiny=0 and bo.revaluation=0" ;
			}

			Query q = session.createQuery(s).setParameterList("studList", stuList); 
			boList = q.list();
			if(boList!= null && boList.size()!=0){
				Iterator<ModerationMarksEntryBo> itr = boList.iterator();
				while (itr.hasNext()) {
					ModerationMarksEntryBo bo = (ModerationMarksEntryBo) itr.next();
					returnMap.put(bo.getStudentUtilBo().getId(), bo);

				}
			}
		}catch (Exception e) {
			throw  new ApplicationException(e);

		}finally {
			if (session != null) {
				session.flush();
			}
		}
		return returnMap;
	}


}
