package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpJobType;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.bo.exam.SubjectAreaBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmpResumeSubmissionForm;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EmpResumeSubmissionTransaction implements IEmpResumeSubmissionTransaction {
	
	private static final Log log = LogFactory.getLog(EmpResumeSubmissionTransaction.class);
	
	private static volatile EmpResumeSubmissionTransaction instance=null;
	
	/**
	 * 
	 */
	private EmpResumeSubmissionTransaction(){
		
	}
	
	/**
	 * @return
	 */
	public static EmpResumeSubmissionTransaction getInstance(){
		log.info("Beginning getInstance of EmpResumeSubmissionTransaction");
		if(instance==null){
			instance=new EmpResumeSubmissionTransaction();
		}
		log.info("End of getInstance of EmpResumeSubmissionTransaction");
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getCountryMap()
	 */
	@Override
	public Map<String, String> getCountryMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Country c where c.isActive=true");
			List<Country> list=query.list();
			if(list!=null){
				Iterator<Country> iterator=list.iterator();
				while(iterator.hasNext()){
					Country country=iterator.next();
					map.put(String.valueOf(country.getId()),country.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getDepartmentMap()
	 */
	@Override
	public Map<String, String> getDepartmentMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Department d where d.isActive=true and d.isAcademic=true");
			List<Department> list=query.list();
			if(list!=null){
				Iterator<Department> iterator=list.iterator();
				while(iterator.hasNext()){
					Department department=iterator.next();
					map.put(String.valueOf(department.getId()),department.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getDesignationMap()
	 */
	@Override
	public Map<String, String> getDesignationMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Designation d where d.isActive=true");
			List<Designation> list=query.list();
			if(list!=null){
				Iterator<Designation> iterator=list.iterator();
				while(iterator.hasNext()){
					Designation designation=iterator.next();
					map.put(String.valueOf(designation.getId()),designation.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getNationalityMap()
	 */
	@Override
	public Map<String, String> getNationalityMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Nationality n where n.isActive=true");
			List<Nationality> list=query.list();
			if(list!=null){
				Iterator<Nationality> iterator=list.iterator();
				while(iterator.hasNext()){
					Nationality nationality=iterator.next();
					map.put(String.valueOf(nationality.getId()),nationality.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getQualificationLevelMap()
	 */
	@Override
	
	public Map<String, String> getQualificationLevelMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true and e.fixedDisplay=false order by e.displayOrder");
			List<EmpQualificationLevel> list=query.list();
			if(list!=null){
				Iterator<EmpQualificationLevel> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpQualificationLevel empQualificationLevel=iterator.next();
					map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getApplicationNumber()
	 */
	public String getApplicationNumber(String userid)throws Exception{
		String applicationNumber="";
		int application=0;
		Session session=null;
		Transaction tx=null;
		//EmployeeSettings employeeSettings=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("select e,max(e.currentApplicationNo) from EmployeeSettings e " +
					"where e.currentApplicationNo=(select max(es.currentApplicationNo) from EmployeeSettings es)");
			Object[] objects=(Object[]) query.uniqueResult();
			//employeeSettings=(EmployeeSettings)objects[0];
			String appNo=(String)objects[1];
			if(appNo!=null && !appNo.isEmpty()){
				application=Integer.parseInt(appNo)+1;
			}else{
				application++;
			}
			applicationNumber=String.valueOf(application);
			/*tx=session.beginTransaction();
			tx.begin();
			if(employeeSettings!=null && application>0){
				employeeSettings.setCurrentApplicationNo(applicationNumber);
				employeeSettings.setModifiedBy(userid);
				employeeSettings.setLastModifiedDate(new Date());
			}
			session.saveOrUpdate(employeeSettings);
			tx.commit();*/
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		
		return applicationNumber;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getSubjectAreaMap()
	 */
	@Override
	public Map<String, String> getSubjectAreaMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from SubjectAreaBO s where s.isActive=true");
			List<SubjectAreaBO> list=query.list();
			if(list!=null){
				Iterator<SubjectAreaBO> iterator=list.iterator();
				while(iterator.hasNext()){
					SubjectAreaBO subjectAreaBO=iterator.next();
					map.put(String.valueOf(subjectAreaBO.getId()),subjectAreaBO.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	@Override
	public List<EmpQualificationLevelTo> getQualificationFixedMap() throws Exception {
		Session session=null;
		List<EmpQualificationLevelTo> qualificationTo=new ArrayList<EmpQualificationLevelTo>();
//		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true and e.fixedDisplay=true order by e.displayOrder");
			List<EmpQualificationLevel> list=query.list();
			if(list!=null){
				Iterator<EmpQualificationLevel> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpQualificationLevel empQualificationLevel=iterator.next();
					EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
					empQualificationLevelTo.setEducationId(String.valueOf(empQualificationLevel.getId()));
					empQualificationLevelTo.setQualification(empQualificationLevel.getName());
//					map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
					qualificationTo.add(empQualificationLevelTo);
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return qualificationTo;
	}
	
	

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#saveEmpResume(com.kp.cms.bo.admin.EmpOnlineResume)
	 */
	@Override
	public boolean saveEmpResume(EmpOnlineResume empOnlineResume, EmpResumeSubmissionForm empResumeSubmissionForm )throws Exception {
		Session session=null;
		boolean flag=false;
		String applicationNumber="";
		int application=0;
		Transaction txn=null;
		EmployeeSettings employeeSettings=null;
		try{
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
			txn=session.beginTransaction();
				session.save(empOnlineResume);
			//	EmployeeSettings employeeSettings=(EmployeeSettings)session.createQuery("from EmployeeSettings e").uniqueResult();
				Query query=session.createQuery("select e from EmployeeSettings e ");
				query.setLockMode("e", LockMode.UPGRADE);
				employeeSettings=(EmployeeSettings) query.uniqueResult();
				String appNo=employeeSettings.getCurrentApplicationNo();
				if(appNo!=null && !appNo.isEmpty()){
					application=Integer.parseInt(appNo)+1;
				}else{
					application++;
				}
				boolean isExist=false;
				do{
					List<EmpOnlineResume> bos=session.createQuery("from EmpOnlineResume a where a.applicationNo="+application).list();
					if(bos!=null && !bos.isEmpty()){
						isExist=true;
						application=application+1;
					}else{
						isExist=false;
					}
				}while(isExist);
				
				applicationNumber = String.valueOf(application);
				employeeSettings.setCurrentApplicationNo(String.valueOf(application));
				employeeSettings.setModifiedBy(empResumeSubmissionForm.getUserId());
				employeeSettings.setLastModifiedDate(new Date());
				session.update(employeeSettings);
				txn.commit();
				Transaction txn2=session.beginTransaction();
				empOnlineResume.setApplicationNo(applicationNumber);
				session.update(empOnlineResume);
				txn2.commit();
				flag=true;
				empResumeSubmissionForm.setApplicationNO(applicationNumber);
				session.flush();
				session.close();
				
		} catch (Exception e) {
			log.error("ERROR IN FINAL SAVE", e);
			if (session.isOpen()){
				txn.rollback();
			session.flush();
			session.close();
			}
			throw new ApplicationException(e);
		}
		return flag;
	}

	@Override
	public Map<String, String> getJobType() throws Exception {
		Session session=null;
		Map<String,String> jobTypeMap=new TreeMap<String, String>();
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpJobType e where e.isActive=true");
			List<EmpJobType> empJobList=query.list();
			if(empJobList!=null){
				Iterator<EmpJobType> iterator=empJobList.iterator();
				while(iterator.hasNext()){
					EmpJobType emp=iterator.next();
					if(emp!=null && emp.getName()!=null && !emp.getName().isEmpty() && emp.getId()>0){
						jobTypeMap.put(String.valueOf(emp.getId()),emp.getName());
					}
				}
			}
		} catch (Exception e) {
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		jobTypeMap = (Map<String, String>) CommonUtil.sortMapByValue(jobTypeMap);
		return jobTypeMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpResumeSubmissionTransaction#getQualificationMap()
	 */
	@Override
	public Map<String, String> getQualificationMap() throws Exception {
		Session session=null;
		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true order by e.displayOrder");
			List<EmpQualificationLevel> list=query.list();
			if(list!=null){
				Iterator<EmpQualificationLevel> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpQualificationLevel empQualificationLevel=iterator.next();
					map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}

	@Override
	public Map<Integer, String> getEmployeeSubjectMap() throws Exception {
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmployeeSubject s where s.isActive=true");
			List<EmployeeSubject> list=query.list();
			if(list!=null){
				Iterator<EmployeeSubject> iterator=list.iterator();
				while(iterator.hasNext()){
					EmployeeSubject employeeSubject=iterator.next();
					map.put(employeeSubject.getId(),employeeSubject.getSubjectName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}


}
