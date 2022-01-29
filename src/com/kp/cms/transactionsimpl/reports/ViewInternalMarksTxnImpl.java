package com.kp.cms.transactionsimpl.reports;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.reports.IViewInternalMarksTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ViewInternalMarksTxnImpl implements IViewInternalMarksTxn{
	private static final Log log = LogFactory.getLog(ViewInternalMarksTxnImpl.class);
	public static volatile ViewInternalMarksTxnImpl self=null;
	public static ViewInternalMarksTxnImpl getInstance(){
		if(self==null){
			self= new ViewInternalMarksTxnImpl();
		}
		return self;
	}
	private ViewInternalMarksTxnImpl(){}
	
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getClassesByYear(int year, int teacherId) {
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TeacherClassSubject t "+
						"where t.teacherId.id="+teacherId+" and t.year="+year+" and t.isActive=1");
					
			        
			List<TeacherClassSubject> classList = query.list();
			Map<Integer, String> classMap = new LinkedHashMap<Integer, String>();
			if(classList!=null && !classList.isEmpty())
			{
				Iterator<TeacherClassSubject> itr = classList.iterator();
				TeacherClassSubject teacherclass;

				while (itr.hasNext()) {
					teacherclass = (TeacherClassSubject) itr.next();
				classMap.put(teacherclass.getClassId().getClasses().getId(), teacherclass.getClassId().getClasses().getName());
				}
			}
			session.flush();
			// session.close();
			classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
			return classMap;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}

		return new HashMap<Integer, String>();
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getSubjectByYear(int year, int classId, int teacherId) {
		try {
			Session session = null;
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from TeacherClassSubject t "+
					"where t.teacherId.id="+teacherId+" and t.year="+year+" and t.classId.classes.id="+classId+" and t.isActive=1");
				
		        
		List<TeacherClassSubject> classList = query.list();
		Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
		if(classList!=null && !classList.isEmpty())
		{
			Iterator<TeacherClassSubject> itr = classList.iterator();
			TeacherClassSubject teacherclass;
			while (itr.hasNext()) {
				teacherclass = (TeacherClassSubject) itr.next();
			subjectMap.put(teacherclass.getSubject().getId(), teacherclass.getSubject().getName());
			}
		}
		session.flush();
		subjectMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectMap);
		return subjectMap;
	} catch (Exception e) {
		log.debug("Exception" + e.getMessage());
	}
	return new HashMap<Integer, String>();
}
	
	public List<MarksEntryDetails> getStudentWiseExamMarkDetailsView(int subjectId, int classId, int year, int teacherId) throws ApplicationException {
		Session session = null;
		List<MarksEntryDetails> examMarksEntryDetailsBOList = null;
		try {
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("from MarksEntryDetails md where md.marksEntry.exam.examTypeID in (4,5) and  md.marksEntry.classes.id= " +classId +" and md.subject.id="+subjectId+" order by md.marksEntry.student.registerNo");
			 examMarksEntryDetailsBOList = studentQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return examMarksEntryDetailsBOList;
	}
}