package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.SyllabusEntryGeneralTo;
import com.kp.cms.transactions.admin.ISyllabusEntryTrans;
import com.kp.cms.utilities.HibernateUtil;

public class SyllabusEntryTransImpl implements ISyllabusEntryTrans{
	public static volatile SyllabusEntryTransImpl syllabusEntryTransImpl=null;
	//private constructor
	private SyllabusEntryTransImpl(){
		
	}
	//singleton object
	public static SyllabusEntryTransImpl getInstance(){
		if(syllabusEntryTransImpl==null){
			syllabusEntryTransImpl=new SyllabusEntryTransImpl();
			return syllabusEntryTransImpl;
		}
		return syllabusEntryTransImpl;
	}
	@Override
	public boolean save(SyllabusEntry syllabusEntry) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(syllabusEntry);
			tx.commit();
			flag=true;
		}catch (Exception e) {
			if (tx != null)
				tx.rollback();
		}finally{
			if(session !=null){
				session.flush();
				session.close();
				}
		}
		return flag;
	}
	@Override
	public SyllabusEntry getRecord(int id) throws Exception {
		SyllabusEntry syllabusEntry=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from SyllabusEntry e where e.id="+id);
			syllabusEntry=(SyllabusEntry)hqlQuery.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return syllabusEntry;
	}
	@Override
	public boolean update(SyllabusEntry syllabusEntry) throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction =null;
		try{
			session = HibernateUtil.getSession();
			 transaction=session.beginTransaction();
				transaction.begin();
					session.merge(syllabusEntry);
				transaction.commit();
				flag=true;
		}catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}finally{
			if(session !=null){
				session.flush();
				session.close();
				}
		}
		return flag;
	}
	@Override
	public Subject getSubject(String subjectId) throws Exception {
		Subject subject=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from Subject e where e.id="+Integer.parseInt(subjectId));
			subject=(Subject)hqlQuery.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return subject;
	}
	@Override
	public SyllabusEntry getSyllabusEntryStatus(int year, int subjectId)
			throws Exception {
		SyllabusEntry syllabusEntry=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(" from SyllabusEntry e where e.isActive=1 and e.batchYear="+year+" and e.subject.id="+subjectId);
			syllabusEntry=(SyllabusEntry)hqlQuery.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return syllabusEntry;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer,String> getSubjectMap(int deptId, int schemeNo,
			int year) throws Exception {
		Map<Integer,String> subjectsMap = new HashMap<Integer, String>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select s from CurriculumSchemeSubject css " +
					" join css.curriculumSchemeDuration cd" +
					" join cd.curriculumScheme cs" +
					" join css.subjectGroup sg" +
					" join sg.subjectGroupSubjectses sgs" +
					" join sgs.subject s where sg.isActive=1 and sgs.isActive=1 and s.isActive=1" +
					" and cd.semesterYearNo="+schemeNo+" and cs.year='"+year+"'and s.department="+deptId +"order by s.code");
			List<Subject> list=query.list();
			if(list!=null && !list.isEmpty()){
				for (Subject subject : list) {
					subjectsMap.put(subject.getId(), subject.getName()+"("+subject.getCode()+")");
				}
			}
		} catch (Exception e) {
		}
		return subjectsMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SyllabusEntry> getSyllabusEntries(List<Integer> subjectIds,String year)
			throws Exception {
		List<SyllabusEntry> list=new ArrayList<SyllabusEntry>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from SyllabusEntry e where e.isActive=1 and e.batchYear="+Integer.parseInt(year)+" and e.subject.id in (:subjectIds)");
			query.setParameterList("subjectIds", subjectIds);
			list=query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer,Map<String,SyllabusEntryGeneralTo>> getSubjects(String query,List<Integer> courseIds)
			throws Exception {
		Map<Integer,Map<String,SyllabusEntryGeneralTo>> subjectsMap =new HashMap<Integer,Map<String,SyllabusEntryGeneralTo>>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(query);
			hqlQuery.setParameterList("courseIds", courseIds);
			List<Object[]> list=hqlQuery.list();
			if(list!=null && !list.isEmpty()){
				Map<String,SyllabusEntryGeneralTo> map=null;;
				SyllabusEntryGeneralTo syllabusEntryGeneralTo=null;
				for (Object[] objects : list) {
					syllabusEntryGeneralTo=new SyllabusEntryGeneralTo();
					if(subjectsMap.containsKey(Integer.parseInt(objects[1].toString()))){
						map=subjectsMap.get(Integer.parseInt(objects[1].toString()));
					}else{
						map=new HashMap<String, SyllabusEntryGeneralTo>();
					}
					syllabusEntryGeneralTo.setSubjectName(objects[2].toString());
					syllabusEntryGeneralTo.setSubjectCode(objects[3].toString());
					syllabusEntryGeneralTo.setSemester(objects[4].toString());
					syllabusEntryGeneralTo.setDeptId(Integer.parseInt(objects[5].toString()));
					map.put(objects[0].toString(), syllabusEntryGeneralTo);
					subjectsMap.put(Integer.parseInt(objects[1].toString()), map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectsMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getcourseIdsFormCourseDept(String departmentId)
			throws Exception {
		List<Integer> list=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select c.course.id from CourseToDepartment c where c.isActive=1 and c.department.id="+Integer.parseInt(departmentId));
			list=query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Map<String, SyllabusEntryGeneralTo>> getlanguageSubjects(
			String query) throws Exception {
		Map<Integer,Map<String,SyllabusEntryGeneralTo>> subjectsMap =new HashMap<Integer,Map<String,SyllabusEntryGeneralTo>>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query hqlQuery = session.createQuery(query);
			List<Object[]> list=hqlQuery.list();
			if(list!=null && !list.isEmpty()){
				Map<String,SyllabusEntryGeneralTo> map=null;
				SyllabusEntryGeneralTo syllabusEntryGeneralTo=null;
				for (Object[] objects : list) {
					syllabusEntryGeneralTo=new SyllabusEntryGeneralTo();
					if(subjectsMap.containsKey(Integer.parseInt(objects[1].toString()))){
						map=subjectsMap.get(Integer.parseInt(objects[1].toString()));
					}else{
						map=new HashMap<String, SyllabusEntryGeneralTo>();
					}
					syllabusEntryGeneralTo.setSubjectName(objects[2].toString());
					syllabusEntryGeneralTo.setSubjectCode(objects[3].toString());
					syllabusEntryGeneralTo.setSemester(objects[4].toString());
					syllabusEntryGeneralTo.setDeptId(Integer.parseInt(objects[5].toString()));
					map.put(objects[0].toString(), syllabusEntryGeneralTo);
					subjectsMap.put(Integer.parseInt(objects[1].toString()), map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjectsMap;
	}
}
