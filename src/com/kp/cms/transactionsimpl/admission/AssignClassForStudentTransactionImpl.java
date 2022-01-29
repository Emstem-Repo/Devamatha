package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AssignClassForStudentForm;
import com.kp.cms.to.admission.AssingClassForStudentTO;
import com.kp.cms.transactions.admission.IAssignClassForStudentTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AssignClassForStudentTransactionImpl implements IAssignClassForStudentTransaction {

	/**
	 * Singleton object of AssignClassForStudentTransactionImpl
	 */
	private static volatile AssignClassForStudentTransactionImpl assignClassForStudentTransactionImpl = null;
	private static final Log log = LogFactory.getLog(AssignClassForStudentTransactionImpl.class);
	private AssignClassForStudentTransactionImpl() {
		
	}
	/**
	 * return singleton object of AssignClassForStudentTransactionImpl.
	 * @return
	 */
	public static AssignClassForStudentTransactionImpl getInstance() {
		if (assignClassForStudentTransactionImpl == null) {
			assignClassForStudentTransactionImpl = new AssignClassForStudentTransactionImpl();
		}
		return assignClassForStudentTransactionImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAssignClassForStudentTransaction#getProgramMap()
	 */
	@Override
	public Map<Integer, String> getProgramMap() throws Exception {
		
		
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query selectedCandidatesQuery=session.createQuery("from Program where isActive =1");
			List<Program> list = selectedCandidatesQuery.list();
			
			
			Map<Integer, String> programMap = new HashMap<Integer, String>();
			Iterator<Program> itr = list.iterator();
			Program program;
			while (itr.hasNext()) {
				program = (Program) itr.next();
				if (program.getIsActive()) {
					programMap.put(program.getId(), program.getName());
				}
			}
			programMap = CommonUtil.sortMapByValue(programMap); 
			// session.close();
			programMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(programMap);

			return programMap;
		} catch (Exception e) {
			log.error("Error while retrieving selected Programs.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		
		
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IAssignClassForStudentTransaction#getStudentList(java.lang.String)
	 */
	@Override
	public List<Student> getStudentList(String query) throws Exception {
		Session session = null;
		List<Student> tosList = new ArrayList<Student>();
		try {
			
			session = HibernateUtil.getSession();
			if(query != null && !query.isEmpty()){
				Query selectedCandidatesQuery=session.createQuery(query);
				tosList = selectedCandidatesQuery.list();
			}
			return tosList;
		} catch (Exception e) {
			log.error("Error while retrieving selected Programs.." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public Map<Integer, String> getClassMap(String year, String courseId) throws Exception{
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.classes from ClassSchemewise c where c.classes.isActive=1" +
											  " and c.classes.course.id=" +courseId+
											  " and c.curriculumSchemeDuration.academicYear='"+year+"'");
			List<Classes> classes = query.list();
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			Iterator<Classes> itr = classes.iterator();
			while (itr.hasNext()) {
				Classes classes2 = (Classes) itr.next();
				if(classes2.getId() != 0 && classes2.getName() != null){
					classMap.put(classes2.getId(), classes2.getName());
				}
			}
			classMap=CommonUtil.sortMapByValue(classMap);
			
			// session.close();
			
			return classMap;
		} catch (Exception e) {
			// session.close();
			log.debug("Exception" + e.getMessage());
		}
		return new HashMap<Integer, String>();
	}
	@Override
	public boolean assignClass(AssignClassForStudentForm assignClassForStudentForm) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			
			List<AssingClassForStudentTO> tos = assignClassForStudentForm.getAssingClassForStudentTOs();
			if(tos != null && !tos.isEmpty()){
				Iterator<AssingClassForStudentTO> iterator = tos.iterator();
				String[] classesArray = assignClassForStudentForm.getClasses();
				int count = 0;
				while (iterator.hasNext()) {
					AssingClassForStudentTO assingClassForStudentTO = (AssingClassForStudentTO) iterator.next();
					if(assingClassForStudentTO.getChecked() != null){
						if(assingClassForStudentTO.getChecked().equalsIgnoreCase("on")){
							if(assingClassForStudentTO.getStudentId() != null && !assingClassForStudentTO.getStudentId().isEmpty()){
								for (int i = 0; i < classesArray.length; i++) {
									if(count == i){
										String cls = classesArray[i];
										 Student student = (Student)session.createQuery("from Student s where s.id='"+assingClassForStudentTO.getStudentId()+"'").uniqueResult();
										if(student != null){
											Classes classes = new Classes();
											classes.setId(Integer.parseInt(cls));
											if(cls != null && !cls.isEmpty()){
												ClassSchemewise classSchemewise = (ClassSchemewise)session.createQuery("from ClassSchemewise c where c.classes.id='"+cls+"'").uniqueResult();
												classSchemewise.setClasses(classes );
												student.setClassSchemewise(classSchemewise);
												student.setId(Integer.parseInt(assingClassForStudentTO.getStudentId()));
												student.setLastModifiedDate(new Date());
												student.setModifiedBy(assignClassForStudentForm.getUserId());
												session.update(student);
											}
										}
									}
								}
								count=count+1;
								if(count ==classesArray.length){
									count = 0;
								}
							}
						}
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			return false;
		}
	}
	
}
