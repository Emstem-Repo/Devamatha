package com.kp.cms.handlers.attendance;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.attendance.TeacherClassEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.helpers.attendance.TeacherClassEntryHelper;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.transactions.attandance.ITeacherClassEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.TeacherClassEntryImpl;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class TeacherClassEntryHandler {
	private static Log log = LogFactory.getLog(TeacherClassEntryHandler.class);
	public static volatile TeacherClassEntryHandler teacherClassEntryHandler = null;

	public static TeacherClassEntryHandler getInstance() {
		if (teacherClassEntryHandler == null) {
			teacherClassEntryHandler = new TeacherClassEntryHandler();
			return teacherClassEntryHandler;
		}
		return teacherClassEntryHandler;
	}

	public boolean add(TeacherClassEntryForm form) throws Exception {
		log.debug("inside handler add");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			String selectedClasses[] = form.getSelectedClasses();
			Set<Integer> classesIdsSet = new HashSet<Integer>();
			for (int i = 0; i < selectedClasses.length; i++) {
				if(isNumeric(selectedClasses[i])){
				 classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
				}
			}
			if(classesIdsSet!=null){
					Iterator<Integer> itr=classesIdsSet.iterator();
			    	while (itr.hasNext()) {
			    		Integer classesId = (Integer) itr.next();
			    		
			    		Query query = session.createQuery("from TeacherClassSubject tcs where tcs.isActive=0 "+
			    											" and tcs.teacherId.isActive=1  and tcs.subject="+form.getSubjectId()+
			           										" and tcs.teacherId="+form.getTeachers()+
			           										" and tcs.classId.id="+classesId);
								
			    		TeacherClassSubject bo=(TeacherClassSubject)query.uniqueResult();		
						if(bo!=null){
							bo.setIsActive(true);
							bo.setLastModifiedDate(new Date());
							bo.setModifiedBy(form.getUserId());
							if(form.getNumericCode()!=null && !form.getNumericCode().isEmpty()){
								bo.setNumericCode(form.getNumericCode());
							}
							session.update(bo);
							
						}else{
							TeacherClassSubject tcs = new TeacherClassSubject();
							ClassSchemewise cs = new ClassSchemewise();
							cs.setId(classesId);
							tcs.setClassId(cs);
							Subject subject = new Subject();
							subject.setId(Integer.parseInt(form.getSubjectId()));
							tcs.setSubject(subject);
							Users users = new Users();
							users.setId(Integer.parseInt(form.getTeachers()));
							tcs.setTeacherId(users);
							tcs.setYear(form.getYear());
							tcs.setIsActive(true);
							tcs.setCreatedBy(form.getUserId());
							tcs.setCreatedDate(new Date());
							tcs.setModifiedBy(form.getUserId());
							tcs.setLastModifiedDate(new Date());
							if(form.getNumericCode()!=null && !form.getNumericCode().isEmpty()){
								tcs.setNumericCode(form.getNumericCode());
							}
							//session = InitSessionFactory.getInstance().openSession();
							
							session.save(tcs);
						}
			    	}
					transaction.commit();
			}
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.debug("Error during saving teacher class data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving teacher class data...", e);
			throw new ApplicationException(e);
		}
	}

	public boolean delete(TeacherClassEntryForm form) throws Exception {
		log.debug("inside handler delete");
		Session session = null;
		Transaction transaction = null;
		try {
			TeacherClassSubject tcs = new TeacherClassSubject();
			tcs.setId(form.getId());
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.delete(tcs);
			transaction.commit();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.debug("Error during deleting teacher class data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.debug("Error during deleting teacher class data...", e);
			throw new ApplicationException(e);
		}
	}

	public List<TeacherClassEntryTo> getDetails(int currentYear, TeacherClassEntryForm teacherForm)
			throws Exception {
		log.debug("inside handler getDetails");
		ITeacherClassEntryTransaction objITeacherClassEntry = TeacherClassEntryImpl
				.getInstance();
		List<Object> List1=	objITeacherClassEntry.getDetails(currentYear,teacherForm);
		List<Object> List2= objITeacherClassEntry.getUserDetails(currentYear, teacherForm);
		List<Object> List3= objITeacherClassEntry.getGuestDetails(currentYear, teacherForm);
		List<TeacherClassEntryTo> activityList = TeacherClassEntryHelper.getInstance().convertBOToTo(List1, List2, List3);
					//List<Object> List1=	objITeacherClassEntry.getDetails(currentYear));
		

		log.debug("leaving handler getDetails");
		return activityList;
	}

	public boolean deleteTeacherClassEntry(int id,Boolean activate,TeacherClassEntryForm teacherClassEntryForm) throws Exception {
		log.debug("inside handler getDetails");
		ITeacherClassEntryTransaction objITeacherClassEntry = TeacherClassEntryImpl
				.getInstance();
		log.debug("leaving handler getDetails");
		return objITeacherClassEntry.deleteTeacherClassEntry(id,activate,teacherClassEntryForm);

	}

	public TeacherClassEntryForm editTeacherClassEntry(
			TeacherClassEntryForm objForm, int id) throws Exception {
		ITeacherClassEntryTransaction objITeacherClassEntry = TeacherClassEntryImpl
				.getInstance();
		List list = objITeacherClassEntry.getTeacherClassDetails(id);
		// tcs.year, tcs.teacherId.employee.id, tcs.subject.id, tcs.classId.id
		Iterator itr = list.iterator();
		int year = 0;
		int employeeId = 0;
		int subjectId = 0;
		int classId = 0;
		String numericCode="";
		while (itr.hasNext()) {
			Object[] object = (Object[]) itr.next();

			year = (object[0] != null
					&& object[0].toString().trim().length() > 0 ? Integer
					.parseInt(object[0].toString()) : 0);

			employeeId = (object[1] != null
					&& object[1].toString().trim().length() > 0 ? Integer
					.parseInt(object[1].toString()) : 0);

			subjectId = (object[2] != null
					&& object[2].toString().trim().length() > 0 ? Integer
					.parseInt(object[2].toString()) : 0);

			classId = (object[3] != null
					&& object[3].toString().trim().length() > 0 ? Integer
					.parseInt(object[3].toString()) : 0);
			if(object[4]!=null){
				numericCode=object[4].toString();
			}

		}

		objForm.setYear(Integer.toString(year));

		 Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
		objForm.setClassMap(classMap);
		String classesId=String.valueOf(classId);
		String[] classes=classesId.split(",");
		Set<Integer> classesIdsSet = new HashSet<Integer>();
		for (int i = 0; i < classes.length; i++) {
			classesIdsSet.add(Integer.parseInt(classes[i]));
		}
		objForm.setSelectedClasses(classes);
		objForm.setNumericCode(numericCode);
		// ** to get subjects
		
		Map<Integer, String> subjectMap = new LinkedHashMap<Integer, String>();
		/*ClassSchemewise classSchemewise = CommonAjaxHandler.getInstance()
				.getDetailsonClassSchemewiseIdNew(classId);
		if (classSchemewise.getCurriculumSchemeDuration().getAcademicYear() != null
				&& classSchemewise.getClasses().getCourse().getId() != 0
				&& classSchemewise.getClasses().getTermNumber() != 0) {
			int year1 = classSchemewise.getCurriculumSchemeDuration()
					.getAcademicYear();
			int courseId = classSchemewise.getClasses().getCourse().getId();
			int term = classSchemewise.getClasses().getTermNumber();
			subjectMap = CommonAjaxHandler.getInstance()
					.getSubjectByCourseIdTermYear(courseId, year1, term);
		}*/
		subjectMap=CommonAjaxHandler.getInstance().getCommonSubjectsByClass(classesIdsSet);
		objForm.setSubjectMap(subjectMap);
		objForm.setSubjectId(Integer.toString(subjectId));

		//teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
		 Map<Integer, String> teachersMap = TeacherClassEntryHandler.getInstance().getTeacherDepartmentNames();
		objForm.setTeachersMap(teachersMap);
		objForm.setTeachers(Integer.toString(employeeId));
		objForm.setId(id);
		return objForm;
	}

	public boolean update(TeacherClassEntryForm objForm) throws Exception{
		boolean flag=false;
		TeacherClassSubject tcs = new TeacherClassSubject();
		tcs.setId(objForm.getId());
		
		String selectedClasses[] = objForm.getSelectedClasses();
		Set<Integer> classesIdsSet = new HashSet<Integer>();
		for (int i = 0; i < selectedClasses.length; i++) {
			classesIdsSet.add(Integer.parseInt(selectedClasses[i]));
		}
		
		ClassSchemewise cs = new ClassSchemewise();
		cs.setId(Integer.parseInt(selectedClasses[0]));
		tcs.setClassId(cs);
		
		Subject subject = new Subject();
		subject.setId(Integer.parseInt(objForm.getSubjectId()));
		tcs.setSubject(subject);
		
		Users users = new Users();
		users.setId(Integer.parseInt(objForm.getTeachers()));
		tcs.setTeacherId(users);
		tcs.setYear(objForm.getYear());
		tcs.setIsActive(true);
		tcs.setCreatedBy(objForm.getUserId());
		tcs.setCreatedDate(new Date());
		tcs.setModifiedBy(objForm.getUserId());
		tcs.setLastModifiedDate(new Date());
		if(objForm.getNumericCode()!=null && !objForm.getNumericCode().isEmpty()){
			tcs.setNumericCode(objForm.getNumericCode());
		}
		ITeacherClassEntryTransaction objITeacherClassEntry = TeacherClassEntryImpl
		.getInstance();
		flag=objITeacherClassEntry.update(tcs);
		
		return flag;
	}

	public List<TeacherClassSubject> checkDuplicateNumericCode(TeacherClassEntryForm teacherForm) throws Exception{
		String query=TeacherClassEntryHelper.getInstance().getDuplicateCheckQuery(teacherForm);
		ITeacherClassEntryTransaction transaction = new TeacherClassEntryImpl();
		return transaction.getDuplicateList(query);
	}

	public boolean checkDuplicateCodeInList(List<TeacherClassSubject> duplicateList,TeacherClassEntryForm teacherForm) throws Exception {
	    boolean flag=false;
	    if(duplicateList!=null && !duplicateList.isEmpty()){
	    	Iterator<TeacherClassSubject> itr=duplicateList.iterator();
	    	while (itr.hasNext()) {
				TeacherClassSubject teacherClassSubject = (TeacherClassSubject) itr.next();
				if(teacherClassSubject.getTeacherId().getId()!=Integer.parseInt(teacherForm.getTeachers()) || teacherClassSubject.getSubject().getId()!=Integer.parseInt(teacherForm.getSubjectId())){
					throw new DuplicateException("duplicateCode");
				}else{
					flag=true;
				}
			}
	    }
		return flag;
	}

	/**
	 * @param teacherForm
	 * @throws Exception
	 */
	public void checkDuplicate(TeacherClassEntryForm teacherForm) throws Exception {
		
		String query=TeacherClassEntryHelper.getInstance().getDuplicateEntryQuery(teacherForm);
		ITeacherClassEntryTransaction transaction = new TeacherClassEntryImpl();
		boolean flag= transaction.getDuplicates(teacherForm);	
		if(flag){
			throw new DuplicateException("duplicateEntry");	
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getTeacherDepartmentNames() throws Exception {
		ITeacherClassEntryTransaction transaction = new TeacherClassEntryImpl();
		List<Object[]> list=transaction.getTeacherDepartmentNames();
		 Map<Integer, String> teacherMap = TeacherClassEntryHelper.getInstance().getTeacherDepartmentsName(list);
		return teacherMap;
	}

	/**
	 * @param teacherForm
	 * @throws Exception
	 */
	public void checkReactivate(TeacherClassEntryForm teacherForm)throws Exception {
		TeacherClassSubject teacherClassSubject =null;
		ITeacherClassEntryTransaction transaction = new TeacherClassEntryImpl();
		teacherClassSubject = transaction.getReactivate(teacherForm);
		if(teacherClassSubject!=null && !teacherClassSubject.getIsActive()){
			teacherForm.setId(teacherClassSubject.getId());
			throw new ReActivateException();
		}
	}
	
	public  boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}
