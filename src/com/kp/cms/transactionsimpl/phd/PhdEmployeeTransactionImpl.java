package com.kp.cms.transactionsimpl.phd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.PhdEmployeeForms;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.transactions.phd.IPhdEmployeeTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PhdEmployeeTransactionImpl implements	IPhdEmployeeTransaction {
	private static final Log log = LogFactory
	.getLog(PhdEmployeeTransactionImpl.class);

public static volatile PhdEmployeeTransactionImpl obImpl = null;

public static PhdEmployeeTransactionImpl getInstance() {
if (obImpl == null) {
	obImpl = new PhdEmployeeTransactionImpl();
     }
      return obImpl;
     }

/* (non-Javadoc)
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getCountryMap()
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
				if(country.getId() !=0 && country.getName()!=null && !country.getName().isEmpty())
				map.put(String.valueOf(country.getId()),country.getName());
			}
		}
	}catch (Exception exception) {
		
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
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getStatesMap()
 */
@Override
public Map<String, String> getStatesMap() throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from State s where s.isActive=true");
		List<State> list=query.list();
		if(list!=null){
			Iterator<State> iterator=list.iterator();
			while(iterator.hasNext()){
				State state=iterator.next();
				if(state.getId() !=0 && state.getName()!=null && !state.getName().isEmpty())
				map.put(String.valueOf(state.getId()),state.getName());
			}
		}
	}catch (Exception exception) {
		
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
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getDepartmentMap()
 */
@Override
public Map<String, String> getDepartmentMap() throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from Department d where d.isActive=true");
		List<Department> list=query.list();
		if(list!=null){
			Iterator<Department> iterator=list.iterator();
			while(iterator.hasNext()){
				Department department=iterator.next();
				if(department.getId()!=0 && department.getName()!=null && !department.getName().isEmpty())
				map.put(String.valueOf(department.getId()),department.getName());
			}
		}
	}catch (Exception exception) {
		
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
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getDesignationMap()
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
				if(designation.getId()!=0 && designation.getName()!=null && !designation.getName().isEmpty())
				map.put(String.valueOf(designation.getId()),designation.getName());
			}
		}
	}catch (Exception exception) {
		
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
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getNationalityMap()
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
				if(nationality.getId()!=0 && nationality.getName()!=null && !nationality.getName().isEmpty())
				map.put(String.valueOf(nationality.getId()),nationality.getName());
			}
		}
	}catch (Exception exception) {
		
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
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getQualificationMap()
 */
@Override
public Map<String, String> getQualificationMap() throws Exception {
	Session session=null;
	Map<String,String> map=new LinkedHashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true and e.phdQualification=true order by e.displayOrder");
		List<EmpQualificationLevel> list=query.list();
		if(list!=null){
			Iterator<EmpQualificationLevel> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevel empQualificationLevel=iterator.next();
				if(empQualificationLevel!=null && empQualificationLevel.getName()!=null && !empQualificationLevel.getName().isEmpty() && empQualificationLevel.getId()>0)
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
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getReligionMap()
 */
@Override
public Map<String, String> getReligionMap() throws Exception {
	Session session=null;
	Map<String,String> map=new LinkedHashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from Religion r where r.isActive=true");
		List<Religion> list=query.list();
		if(list!=null){
			Iterator<Religion> iterator=list.iterator();
			while(iterator.hasNext()){
				Religion religion=iterator.next();
				if(religion!=null && religion.getName()!=null && !religion.getName().isEmpty() && religion.getId()>0)
				map.put(String.valueOf(religion.getId()),religion.getName());
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
public List<EmpQualificationLevelTo> getQualificationFixedMap()
		throws Exception {
	Session session=null;
	List<EmpQualificationLevelTo> qualificationTo=new ArrayList<EmpQualificationLevelTo>();
//	Map<String,String> map=new LinkedHashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true and e.fixedDisplay=true and e.phdQualification=true order by e.displayOrder");
		List<EmpQualificationLevel> list=query.list();
		if(list!=null){
			Iterator<EmpQualificationLevel> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevel empQualificationLevel=iterator.next();
				EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
				if(empQualificationLevel.getId()!=0 && empQualificationLevel.getName()!=null && !empQualificationLevel.getName().isEmpty())
				{
				empQualificationLevelTo.setEducationId(String.valueOf(empQualificationLevel.getId()));
				empQualificationLevelTo.setQualification(empQualificationLevel.getName());
				}
//				map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
				qualificationTo.add(empQualificationLevelTo);
			}
		}
	}catch (Exception exception) {
		
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	return qualificationTo;
}

@Override
public Map<String, String> guideShipMap() throws Exception {
	Session session=null;
	Map<String,String> map=new LinkedHashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from DisciplineBo r where r.isActive=true");
		List<DisciplineBo> list=query.list();
		if(list!=null){
			Iterator<DisciplineBo> iterator=list.iterator();
			while(iterator.hasNext()){
				DisciplineBo discipline=iterator.next();
				if(discipline!=null && discipline.getName()!=null && !discipline.getName().isEmpty() && discipline.getId()>0)
				map.put(String.valueOf(discipline.getId()),discipline.getName());
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
public boolean savePhdEmployee(PhdEmployee employee) throws Exception {
	Session session=null;
	boolean flag=false;
	Transaction tx=null;
	try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			session.saveOrUpdate(employee);
			tx.commit();
			flag=true;
	}catch(Exception e){
		tx.rollback();
		session.flush();
        session.close();
		throw new ApplicationException(e);
	}finally{
		if(session!=null){
			session.flush();
			 session.close();
		}
	}
	return flag;
}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#getPhdDetailsList(com.kp.cms.forms.phd.PhdEmployeeForms)
 */
@Override
public List<PhdEmployee> getPhdDetailsList(PhdEmployeeForms objform) throws Exception {
	log.info("call of getPhdDetailsList in PhdEmployeeTransactionImpl class.");
	Session session=null;
	List<PhdEmployee> phdBiList;
	try{
		 session = HibernateUtil.getSession();
		 String str="from PhdEmployee phdBo where phdBo.isActive=1";

		 if(objform.getGuideShipSearch()!=null && !objform.getGuideShipSearch().isEmpty()){
	         str=str+" and phdBo.subjectGuideship.id="+objform.getGuideShipSearch();
           }
		 if(objform.getNameSearch()!=null && !objform.getNameSearch().isEmpty()){
	         str=str+" and phdBo.name like '"+objform.getNameSearch()+"%'";
           }
		 Query query = session.createQuery(str);
		 phdBiList=query.list();
		
	}catch(Exception e){
		log.error("Unable to getPhdDetailsList",e);
		 throw e;
	}
	log.info("end of getPhdDetailsList in PhdEmployeeTransactionImpl class.");
	return phdBiList;
}

@Override
public PhdEmployee getPhdEmployeeById(int id) throws Exception {
    Session session = null;
    PhdEmployee phdEmpList = null;
    try
    {
        session = HibernateUtil.getSession();
        String str = (new StringBuilder("from PhdEmployee a where a.id=")).append(id).toString()+" and a.isActive=1";
        Query query = session.createQuery(str);
        phdEmpList = (PhdEmployee)query.uniqueResult();
        session.flush();
    }
    catch(Exception e)
    {
        log.error("Error during getting getPhdEmployeeById by id...", e);
        session.flush();
        session.close();
    }
    return phdEmpList;
}

/* (non-Javadoc)
 * @see com.kp.cms.transactions.phd.IPhdEmployeeTransaction#deletePhdemployee(int)
 */
@Override
public boolean deletePhdemployee(int id) throws Exception {
    Session session = null;
    Transaction transaction = null;
    try
    {
        session = InitSessionFactory.getInstance().openSession();
        String str = (new StringBuilder("from PhdEmployee a where a.id=")).append(id).toString();
        PhdEmployee phdEmp = (PhdEmployee)session.createQuery(str).uniqueResult();
        transaction = session.beginTransaction();
        phdEmp.setIsActive(false);
        session.update(phdEmp);
        transaction.commit();
        session.close();
    }
    catch(Exception e)
    {
        if(transaction != null)
        {
            transaction.rollback();
        }
        log.debug("Error during deleting deleteFeedBackQuestion data...", e);
    }
    return true;
   }

@Override
public Map<String, String> getQualificationLevelMap() throws Exception {
	Session session=null;
	Map<String,String> map=new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmpQualificationLevel e where e.isActive=true and e.phdQualification=true and e.fixedDisplay=false order by e.displayOrder");
		List<EmpQualificationLevel> list=query.list();
		if(list!=null){
			Iterator<EmpQualificationLevel> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevel empQualificationLevel=iterator.next();
				if(empQualificationLevel.getId()!=0 && empQualificationLevel.getName()!=null && !empQualificationLevel.getName().isEmpty())
				map.put(String.valueOf(empQualificationLevel.getId()),empQualificationLevel.getName());
			}
		}
	}catch (Exception exception) {
		
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	return map;
}


}
