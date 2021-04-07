package com.kp.cms.transactionsimpl.exam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.bo.exam.OpenInternalMark;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.OpenInternalExamForm;
import com.kp.cms.handlers.exam.OpenInternalExamHandler;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.IOpenInternalExamImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


@SuppressWarnings("unchecked")
public class OpenInternalExamImpl implements IOpenInternalExamImpl {
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
private static volatile OpenInternalExamImpl instance=null;
	
	public static OpenInternalExamImpl getInstance(){
		if(instance==null){
			instance=new OpenInternalExamImpl();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IOpenInternalExamImpl#saveExam(com.kp.cms.bo.exam.OpenInternalMark)
	 */
	public boolean saveExam(OpenInternalMark openInternalMark, String mode) throws Exception{
		boolean save=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if(openInternalMark != null){
				if(mode.equalsIgnoreCase("add")){
					String query = "from OpenInternalMark m where m.isActive=1 " +
									" and m.exam.id="+openInternalMark.getExam().getId()+
									" and m.startDate='" +openInternalMark.getStartDate()+"'"+
									" and m.endDate='" +openInternalMark.getEndDate()+"'"+
									" and m.theoryPractical='"+openInternalMark.getTheoryPractical()+
									"' and m.programType.id="+openInternalMark.getProgramType().getId();
					OpenInternalMark openInternalMark2 = (OpenInternalMark)session.createQuery(query).uniqueResult();
					if(openInternalMark2 == null){
						session.save(openInternalMark);
					}else{
						openInternalMark2.setLastModifiedDate(new Date());
						openInternalMark2.setModifiedBy(openInternalMark.getModifiedBy());
						openInternalMark2.setStartDate(openInternalMark.getStartDate());
						openInternalMark2.setEndDate(openInternalMark.getEndDate());
						Set<OpenInternalExamForClasses> set = openInternalMark2.getClassesSet();
						set.addAll(openInternalMark.getClassesSet());
						openInternalMark2.setClassesSet(set);
						session.update(openInternalMark2);
					}
				}else{
					session.update(openInternalMark);
				}
			}
			tx.commit();
			session.flush();
			session.close();
			save=true;
		}catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return save;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IOpenInternalExamImpl#getExamList()
	 */
	public List<OpenInternalMark> getExamList() throws Exception{
		List<OpenInternalMark> list=new ArrayList<OpenInternalMark>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			list = session.createQuery("from OpenInternalMark m where m.isActive=1").list();
			session.flush();
		}catch ( Exception e) {
			if(session != null){
				session.flush();
			}
		}
		return list;
	}

	/**
	 * @param objForm
	 * @param session2 
	 * @param duplicate 
	 * @param programType 
	 * @return
	 * @throws Exception
	 */
	public String checkDuplicate(OpenInternalExamForm objForm, String msg, HttpSession session2) throws Exception{
		Session session = null;
		String[] stayClass = objForm.getStayClass();
		String[] classId=stayClass[0].split(",");
		
		try{
			session = HibernateUtil.getSession();
			for (int i = 0; i < classId.length; i++) {
				Object[] objects = null;
				String query = "select cls.classes.name,m.endDate from OpenInternalMark m " +
								" join m.classesSet cls where m.isActive=1" +
//								" and m.startDate='" +CommonUtil.ConvertStringToSQLDate(objForm.getStartDate())+"'"+
								" and m.endDate>='" +CommonUtil.ConvertStringToSQLDate(objForm.getStartDate())+"'"+
								" and cls.isActive=1 and m.exam.id="+objForm.getExamId()+
								" and m.theoryPractical='"+objForm.getTheoryPractical()+"'"+
								" and m.programType.id="+Integer.parseInt(objForm.getNewProgramTypeId());
				if(classId[i] != null){
					query = query + " and cls.classes.id="+classId[i];
				}
				if(objForm.getId() != 0){
					query = query + " and m.id !="+objForm.getId();
				}
				objects =  (Object[]) session.createQuery(query).uniqueResult();
				if(msg == null || msg.isEmpty()){
					 if(objects!=null && !objects.toString().isEmpty()){
						 msg = objects[0].toString();
						 if(session2.getAttribute("endDate") == null){
							 String date = Timestamp.valueOf(objects[1].toString()).toString();
							 Date newDate = CommonUtil.ConvertsqlStringToDate(date);
							 session2.setAttribute("endDate", CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(newDate), OpenInternalExamImpl.SQL_DATEFORMAT,	OpenInternalExamImpl.FROM_DATEFORMAT));
						 }
					 }
				}else{
					if(objects!=null && !objects.toString().isEmpty()){
						if(!msg.contains(objects[0].toString())){
							msg = msg+","+objects[0].toString();
						}
						if(session2 == null || session2.toString().isEmpty()){
							session2.setAttribute("endDate", objects[1].toString());
						}
					}
				}
			}
			System.out.println();
			session.flush();
		}catch ( Exception e) {
			if(session != null){
				session.flush();
			}
			throw new Exception();
		}
		return msg;
	}
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains(" "))
	{
	return fileName.substring(0, fileName.lastIndexOf(" "));
	}
	return fileName;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OpenInternalMark getExam(int id) throws Exception{
		OpenInternalMark mark = null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			mark = (OpenInternalMark)session.createQuery("from OpenInternalMark m where m.isActive=1 and m.id="+id).uniqueResult();
			session.flush();
		}catch ( Exception e) {
			if(session != null){
				session.flush();
			}
		}
		return mark;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExam(int id) throws Exception{
		boolean delete=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			OpenInternalMark mark = (OpenInternalMark)session.createQuery("from OpenInternalMark m where m.id="+id).uniqueResult();
			mark.setIsActive(false);
			session.update(mark);
			tx.commit();
			session.flush();
			session.close();
			delete=true;
		}catch (Exception e) {
			if(tx != null){
				tx.rollback();
			}
			if(session != null){
				session.flush();
				session.close();
			}
		}
		return delete;
	}

	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ProgramType> getProgramTypeList() throws Exception{
		List<ProgramType> list=new ArrayList<ProgramType>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			list = session.createQuery("from ProgramType p where p.isActive=1").list();
			session.flush();
		}catch ( Exception e) {
			if(session != null){
				session.flush();
			}
		}
		return list;
	}

	public List<KeyValueTO> getClassValuesByCourseIdNew(int examNameId, int programTypeId, List<KeyValueTO> list) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("select classes.id, classes.name, curriculum_scheme_duration.academic_year from EXAM_exam_course_scheme_details" +
				" inner join EXAM_definition ON EXAM_exam_course_scheme_details.exam_id = EXAM_definition.id" +
				" inner join course ON EXAM_exam_course_scheme_details.course_id = course.id" +
				" inner join curriculum_scheme on curriculum_scheme.course_id = course.id" +
				" inner join curriculum_scheme_duration on curriculum_scheme_duration.curriculum_scheme_id = curriculum_scheme.id" +
				" and curriculum_scheme_duration.semester_year_no = EXAM_exam_course_scheme_details.scheme_no" +
				" inner join class_schemewise on class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id" +
				" inner join classes ON class_schemewise.class_id = classes.id" +
				" inner join program ON course.program_id = program.id" +
				" inner join program_type ON program.program_type_id = program_type.id" +
				" where EXAM_definition.id="+examNameId+
				" and classes.is_active=1 and program.is_active=1 and course.is_active=1 and classes.is_active=1 and program_type.is_active=1");
				if(programTypeId!=0){
					stringBuilder.append(" and program_type.id="+programTypeId);
				}
			//Added By Manu
				stringBuilder.append(" and EXAM_definition.academic_year=curriculum_scheme_duration.academic_year ");
			//	stringBuilder.append(" and curriculum_scheme_duration.academic_year= (select exam_for_joining_batch from EXAM_definition where id="+examNameId+" )");
			Query query = session.createSQLQuery(stringBuilder.toString());
			Iterator<Object[]> itr = query.list().iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[1] != null) {
					String year = "";
					if(obj[2]!= null ){
						year = " (" + obj[2].toString().substring(0, 4) + ")";
					}
					list.add(new KeyValueTO(Integer.parseInt(obj[0].toString()),
							obj[1].toString()+year));
				}
		
			}
			return list;
	}
}
