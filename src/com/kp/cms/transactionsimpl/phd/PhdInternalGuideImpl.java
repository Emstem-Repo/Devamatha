package com.kp.cms.transactionsimpl.phd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.PhdInternalGuideForm;
import com.kp.cms.transactions.phd.IPhdInternalGuideTransactions;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;


public class PhdInternalGuideImpl implements IPhdInternalGuideTransactions {
	private static Log log = LogFactory.getLog(PhdInternalGuideImpl.class);
	public static volatile PhdInternalGuideImpl examCceFactorImpl = null;
	public static PhdInternalGuideImpl getInstance() {
		if (examCceFactorImpl == null) {
			examCceFactorImpl = new PhdInternalGuideImpl();
			return examCceFactorImpl;
		}
		return examCceFactorImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions#guideShipMap()
	 */
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdInternalGuideTransactions#guideShipMap()
	 */
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
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdInternalGuideTransactions#employeeMap(java.lang.String)
	 */
	@Override
	public Map<String, String> employeeMap() throws Exception {
		Session session=null;
		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Employee e where e.isActive=true");
			List<Employee> list=query.list();
			if(list!=null){
				Iterator<Employee> iterator=list.iterator();
				while(iterator.hasNext()){
					Employee employee=iterator.next();
					if(employee!=null && employee.getFirstName()!=null && !employee.getFirstName().isEmpty() && employee.getId()>0)
					map.put(String.valueOf(employee.getId()),employee.getFirstName());
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
	 * @see com.kp.cms.transactions.phd.IPhdInternalGuideTransactions#employeeMap(java.lang.String)
	 */
	@Override
	public Map<String, String> employeMap(String departmentId) throws Exception {
		Session session=null;
		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Employee e where e.isActive=true and e.department.id="+departmentId);
			List<Employee> list=query.list();
			if(list!=null){
				Iterator<Employee> iterator=list.iterator();
				while(iterator.hasNext()){
					Employee employee=iterator.next();
					if(employee!=null && employee.getFirstName()!=null && !employee.getFirstName().isEmpty() && employee.getId()>0)
					map.put(String.valueOf(employee.getId()),employee.getFirstName());
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
	 * @see com.kp.cms.transactions.phd.IPhdInternalGuideTransactions#getDepartmentMap()
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
	 * @see com.kp.cms.transactions.phd.IPhdInternalGuideTransactions#getInternalGuideDetails(com.kp.cms.forms.phd.PhdInternalGuideForm)
	 */
	@Override
	public List<PhdInternalGuideBo> getInternalGuideDetails(PhdInternalGuideForm objForm) throws Exception {
		Session session=null;
		List<PhdInternalGuideBo> empList = null;
		try{
		session=HibernateUtil.getSession();
		String str="from PhdInternalGuideBo emp where emp.isActive=1";
		if(objForm.getDepartmentId()!=null && !objForm.getDepartmentId().isEmpty()){
			str=str+" and emp.employeeId.department.id="+objForm.getDepartmentId();
		}
		if(objForm.getEmployeeId()!=null && !objForm.getEmployeeId().isEmpty()){
			str=str+" and emp.employeeId.id="+objForm.getEmployeeId();
		}
		if(objForm.getDisciplineId()!=null && !objForm.getDisciplineId().isEmpty()){
			str=str+" and emp.disciplineId.id="+objForm.getDisciplineId();
		}
		if(objForm.getEmpanelmentNo()!=null && !objForm.getEmpanelmentNo().isEmpty()){
			str=str+" and emp.empanelmentNo='"+objForm.getEmpanelmentNo()+"'";
		}
		Query query= session.createQuery(str);
		empList=query.list();
		return empList;
		}catch (Exception e) {
			log.error("Error in getDetails...", e);
			session.flush();
			throw new ApplicationException(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdInternalGuideTransactions#addPhdEmployee(com.kp.cms.bo.phd.PhdInternalGuideBo, com.kp.cms.forms.phd.PhdInternalGuideForm, org.apache.struts.action.ActionErrors, javax.servlet.http.HttpSession)
	 */
	@Override
	public boolean addPhdEmployee(PhdInternalGuideBo guideBo,PhdInternalGuideForm objForm, ActionErrors errors) throws Exception {
		Session session=null;
		Transaction transaction=null;
		PhdInternalGuideBo empList = null;
		boolean flag=true;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			String str="from PhdInternalGuideBo emp where emp.employeeId.id="+objForm.getEmployeeId();
			if(objForm.getEmpanelmentNo()!=null && !objForm.getEmpanelmentNo().isEmpty()){
				str=str+" and emp.empanelmentNo='"+objForm.getEmpanelmentNo()+"'";
			}
			Query query= session.createQuery(str);
			empList=(PhdInternalGuideBo) query.uniqueResult();
			if(empList==null){
				if(objForm.getId()>0){
					session.merge(guideBo);
				}else{
				session.save(guideBo);
			}}else{
				 if(empList.getIsActive()){
					 if(guideBo.getId()==empList.getId()){
						 session.merge(guideBo); 
					 }else{
				       errors.add("error", new ActionError("knowledgepro.employee.leave.allotment.duplicate"));
					 } }else if(!empList.getIsActive()){
						 if(guideBo.getId()!=empList.getId()){
							 empList.setIsActive(true);
							 empList.setDateAward(CommonUtil.ConvertStringToDate(objForm.getDateOfAward()));
							 guideBo.setIsActive(false);
							 session.merge(empList); 
							 session.merge(guideBo);
						 }else{
				           guideBo.setId(empList.getId()); 
					       session.merge(guideBo);
						 } }
			}
		   transaction.commit();
		    }
		   catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdInternalGuideTransactions#getGuideDetailsById(int)
	 */
	@Override
	public PhdInternalGuideBo getGuideDetailsById(int id) throws Exception {
        Session session = null;
        PhdInternalGuideBo guideDetails = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from PhdInternalGuideBo a where a.id=")).append(id).toString()+" and a.isActive=1";
            Query query = session.createQuery(str);
            guideDetails = (PhdInternalGuideBo)query.uniqueResult();
            session.flush();
        }
        catch(Exception e)
        {
            log.error("Error during getting getGuideDetailsById by id...", e);
            session.flush();
            session.close();
        }
        return guideDetails;
    }

	@Override
	public boolean deletePhdemployee(int id) throws Exception {
	    Session session = null;
	    Transaction transaction = null;
	    try
	    {
	        session = InitSessionFactory.getInstance().openSession();
	        String str = (new StringBuilder("from PhdInternalGuideBo a where a.id=")).append(id).toString();
	        PhdInternalGuideBo guide = (PhdInternalGuideBo)session.createQuery(str).uniqueResult();
	        transaction = session.beginTransaction();
	        guide.setIsActive(false);
	        session.update(guide);
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

}
