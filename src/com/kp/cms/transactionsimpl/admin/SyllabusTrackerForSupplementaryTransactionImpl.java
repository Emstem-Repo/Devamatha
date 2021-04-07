package com.kp.cms.transactionsimpl.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.SyllabusTrackerForSupplementaryForm;
import com.kp.cms.transactions.admin.ISyllabusTrackerForSupplementaryTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;


public class SyllabusTrackerForSupplementaryTransactionImpl implements ISyllabusTrackerForSupplementaryTransaction{
	private static final Log log = LogFactory.getLog(SyllabusTrackerForSupplementaryTransactionImpl.class);	
	public static volatile SyllabusTrackerForSupplementaryTransactionImpl syllabusTrackerForSupplementaryTransactionImpl = null;
	
	public static SyllabusTrackerForSupplementaryTransactionImpl getInstance() {
		if (syllabusTrackerForSupplementaryTransactionImpl == null) {
			syllabusTrackerForSupplementaryTransactionImpl = new SyllabusTrackerForSupplementaryTransactionImpl();
		}
		return syllabusTrackerForSupplementaryTransactionImpl;
	}

	public Map<String, String> gettingDeanaryList() throws Exception {
		Session session=null;
		Map<String, String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select DISTINCT(p.stream) from Program p"+
					" where p.isActive=1 and (p.stream is not null and p.stream !='') order by p.stream");
			 
			 List<String> objects = query.list();
				if(objects!=null && !objects.isEmpty()){
					Iterator<String> iterator = objects.iterator();
					while (iterator.hasNext()) {
						String str = (String) iterator.next(); 
						if(str!=null && !str.isEmpty()){
							map.put(str, str);
						}
					}
				}
			
		}
        catch (RuntimeException runtime) {
			
			throw new ApplicationException();
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}
	
	public List<Object[]> getStudentDetails(SyllabusTrackerForSupplementaryForm form){

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			
			List<Object[]> objArray =null;
			
			String QUERY="select csd.semesterYearNo,csd.endDate,s.code,s.name,c.id,cus.year,std.registerNo"+
						" from StudentSupplementaryImprovementApplication e "+
						" inner join e.classes cls "+
						" inner join cls.classSchemewises csw "+
						" inner join csw.curriculumSchemeDuration csd "+
						" inner join csd.curriculumScheme cus "+
						" inner join cls.course c "+
						" inner join c.program p "+
						" inner join e.subject s "+
						" inner join e.student std "+
						" where (std.registerNo is not null and std.registerNo !='') and e.examDefinition.id="+form.getExamId()+
						" and p.stream='"+form.getDeanery()+"'";
			
						if(form.getProgram()!=0){
							QUERY=QUERY+" and p.id="+form.getProgram();
						}
			
						if(form.getExamResult().equalsIgnoreCase("Applied")){
							if(form.getExamType().equalsIgnoreCase("Theory")){
								QUERY=QUERY+" and (e.isAppearedTheory=1)";
							}else if(form.getExamType().equalsIgnoreCase("Practical")){
								QUERY=QUERY+" and ( e.isAppearedPractical=1)";
							}else {
								QUERY=QUERY+" and (e.isAppearedTheory=1 or e.isAppearedPractical=1)";
							}
						}else if(form.getExamResult().equalsIgnoreCase("Failed")){
							if(form.getExamType().equalsIgnoreCase("Theory")){
								QUERY=QUERY+" and (e.isFailedTheory=1 )";
							}else if(form.getExamType().equalsIgnoreCase("Practical")){
								QUERY=QUERY+" and ( e.isFailedPractical=1)";
							}else{
								QUERY=QUERY+" and (e.isFailedTheory=1 or e.isFailedPractical=1)";
							}
							
						}
						if((form.getFromDate()!=null && !form.getFromDate().isEmpty())&& (form.getToDate()!=null && !form.getToDate().isEmpty())){
							QUERY=QUERY+" and (e.lastModifiedDate BETWEEN '"+CommonUtil.ConvertStringToSQLDate(form.getFromDate())+"' and '"+CommonUtil.ConvertStringToSQLDate(form.getToDate())+"')";
						}
			
			Query query=session.createQuery(QUERY);
			objArray=(List<Object[]>) query.list();
			session.flush();
			return objArray;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return null;
		}

	}
	
	
	public List<Object[]> getCurrentStudyingBatchjoiningYear(SyllabusTrackerForSupplementaryForm form) throws Exception {
		Session session = null;
		Calendar now = Calendar.getInstance();
		int currentYear=now.get(Calendar.YEAR);
		   Date date = new Date();
		 java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		 List<Object[]> objArray=null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("select csw.year,csd.id from CurriculumSchemeDuration csd "+
												" inner join csd.curriculumScheme csw "+
												" where csw.course.id="+form.getCourseid()+
												" and csd.academicYear <="+currentYear+" and csd.semesterYearNo="+form.getSemNo()+
												" and csd.startDate <='"+sqlDate+"'"+
												" order by csd.startDate desc");
			query.setMaxResults(1);
			objArray=(List<Object[]>) query.list();
			session.flush();
			return objArray;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return objArray;
		}
	}

	
	public String getSubjectName(int csdId,SyllabusTrackerForSupplementaryForm form) throws Exception {
		Session session = null;
		 String subjectName=null;
		try {
			session = HibernateUtil.getSession();
			if(form.getPaperCode()!=null && !form.getPaperCode().isEmpty() && csdId!=0){
			Query query=session.createQuery("select s.name from CurriculumSchemeSubject css "+
												" inner join css.subjectGroup.subjectGroupSubjectses sgs "+
												" inner join sgs.subject s "+
												" where css.curriculumSchemeDuration.id="+csdId+
												" and s.code='"+form.getPaperCode()+"'");
			
			subjectName=(String)query.uniqueResult();
			session.flush();
			}
			return subjectName;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				session.close();
			}
			return subjectName;
		}
	}
	
}
