package com.kp.cms.transactionsimpl.employee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.handlers.employee.EventLoactionBiometricDetailsHandler;
import com.kp.cms.transactions.employee.IEventLoactionBiometricDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EventLoactionBiometricDetailsTransactionImpl implements IEventLoactionBiometricDetailsTransaction{

	private static final Log log = LogFactory.getLog(EventLoactionBiometricDetailsTransactionImpl.class);
	public static volatile EventLoactionBiometricDetailsTransactionImpl biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	/**
	 * @return
	 */
	public static EventLoactionBiometricDetailsTransactionImpl getInstance(){
		if(biometric==null){
			biometric= new EventLoactionBiometricDetailsTransactionImpl();}
		return biometric;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEventLoactionBiometricDetailsTransaction#duplicateCheck(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public boolean duplicateCheck(String machineId, String machineIp,
			String machineName,EventLocationBiometricDetailsForm form) throws Exception {
		Session session=null;
		boolean flag=false;
		machineIp="'"+machineIp+"'";
		try{
			session=HibernateUtil.getSession();
			
			String quer="from EventLoactionBiometricDetailsBo metric where metric.machineId= :id and metric.isActive=1";
			Query query=session.createQuery(quer);
			query.setString("id", machineId);
			
			EventLoactionBiometricDetailsBo biometric=(EventLoactionBiometricDetailsBo)query.uniqueResult();
			if(biometric!=null && !biometric.toString().isEmpty()){
					flag=true;
					form.setDuperrorMsg("MachineId already exists.");
					form.setFlag(true);
			}
			else
				   flag=false;
			if(flag==false){
				String quer1="from EventLoactionBiometricDetailsBo metric where   metric.isActive=1 and metric.machineIp="+machineIp;
				Query query1=session.createQuery(quer1);
				EventLoactionBiometricDetailsBo biometric1=(EventLoactionBiometricDetailsBo)query1.uniqueResult();
				if(biometric1!=null && !biometric1.toString().isEmpty()){
					flag=true;
					form.setDuperrorMsg("MachineIp already exists.");
					form.setFlag(true);
				}
				else
					   flag=false;
			}
				
		}catch(Exception e){
			log.error("Error during duplicate check..." , e);
			session.flush();
			session.close();
		}
		return flag;
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEventLoactionBiometricDetailsTransaction#getBiometricDetails()
	 */
	public List<EventLoactionBiometricDetailsBo> getBiometricDetails() throws Exception {
		Session session=null;
		List<EventLoactionBiometricDetailsBo> empBiometric=null;
		try{
			String query="from EventLoactionBiometricDetailsBo metric where metric.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			empBiometric=querry.list();
		}catch(Exception exception){
			log.error("Error during getting empBiometric list..." , exception);
		}
		finally{
			session.flush();
		}
		return empBiometric;
	}
	/**
	 * @param biometric
	 * @return
	 * @throws Exception
	 */
	public boolean addBiometricDetails(EventLoactionBiometricDetailsBo biometric)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(biometric);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;

}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEventLoactionBiometricDetailsTransaction#getBiometricDetailsById(int)
	 */
	public EventLoactionBiometricDetailsBo getBiometricDetailsById(int id) throws Exception {
		Session session=null;
		EventLoactionBiometricDetailsBo biometric=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EventLoactionBiometricDetailsBo metric where metric.id="+id;
			Query query=session.createQuery(str);
			biometric=(EventLoactionBiometricDetailsBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return biometric;
	
	}
	
	public boolean updateBiometricDetails(EventLoactionBiometricDetailsBo biometric)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(biometric);
			transaction.commit();
			flag=true;
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating BiometricDetails data...", e);
		}
		return flag;

	}
	@Override
		public boolean deleteBiometricDetails(int id) throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			    session=InitSessionFactory.getInstance().openSession();
			    String str="from EventLoactionBiometricDetailsBo metric where metric.id="+id;
			    EventLoactionBiometricDetailsBo biometric=(EventLoactionBiometricDetailsBo)session.createQuery(str).uniqueResult();
			    transaction=session.beginTransaction();
			  biometric.setIsActive(false);
			    session.update(biometric);
			    transaction.commit();
			    session.close();
		  }catch(Exception e){
			    if (transaction != null)
				   transaction.rollback();
			log.debug("Error during deleting biometricDetails data...", e);
		}
		return true;

	}
	
	public Map<String, String> getEventLocationData() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EventLocation e where e.isActive=1");
			List<EventLocation> list=query.list();
			if(list!=null){
				Iterator<EventLocation> iterator=list.iterator();
				while(iterator.hasNext()){
					EventLocation eventLocation=iterator.next();
					if(eventLocation.getId()!=0 && eventLocation.getName()!=null && !eventLocation.getName().isEmpty())
					map.put(String.valueOf(eventLocation.getId()),eventLocation.getName());
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
	
	
	
	public boolean duplicateCheckInUpdateMode(String machineId, String machineIp,
			String machineName,EventLocationBiometricDetailsForm form) throws Exception {
		Session session=null;
		boolean flag=false;
		
		try{
			session=HibernateUtil.getSession();
			if((!machineId.equalsIgnoreCase(form.getOrigMachineId())) && (!machineIp.equalsIgnoreCase(form.getOrigMachineIp()))){
				machineIp="'"+machineIp+"'";	
			String quer="from EventLoactionBiometricDetailsBo metric where metric.machineId= :id and metric.isActive=1";
			Query query=session.createQuery(quer);
			query.setString("id", machineId);
			
			EventLoactionBiometricDetailsBo biometric=(EventLoactionBiometricDetailsBo)query.uniqueResult();
			if(biometric!=null && !biometric.toString().isEmpty()){
					flag=true;
					form.setDuperrorMsg("MachineId already exists.");
					form.setFlag(true);
			}
			else
				   flag=false;
			if(flag==false){
				String quer1="from EventLoactionBiometricDetailsBo metric where   metric.isActive=1 and metric.machineIp="+machineIp;
				Query query1=session.createQuery(quer1);
				EventLoactionBiometricDetailsBo biometric1=(EventLoactionBiometricDetailsBo)query1.uniqueResult();
				if(biometric1!=null && !biometric1.toString().isEmpty()){
					flag=true;
					form.setDuperrorMsg("MachineIp already exists.");
					form.setFlag(true);
				}
				else
					   flag=false;
			}
		  }	else if((!machineId.equalsIgnoreCase(form.getOrigMachineId())) ){
			  String quer="from EventLoactionBiometricDetailsBo metric where metric.machineId= :id and metric.isActive=1";
				Query query=session.createQuery(quer);
				query.setString("id", machineId);
				EventLoactionBiometricDetailsBo biometric=(EventLoactionBiometricDetailsBo)query.uniqueResult();
			  if(biometric!=null && !biometric.toString().isEmpty()){
					flag=true;
					form.setDuperrorMsg("MachineId already exists.");
					form.setFlag(true);
			}
			else
				   flag=false;
		  }else if((!machineIp.equalsIgnoreCase(form.getOrigMachineIp()))){
			  machineIp="'"+machineIp+"'";
			  String quer1="from EventLoactionBiometricDetailsBo metric where   metric.isActive=1 and metric.machineIp="+machineIp;
				Query query1=session.createQuery(quer1);
				EventLoactionBiometricDetailsBo biometric1=(EventLoactionBiometricDetailsBo)query1.uniqueResult();
				if(biometric1!=null && !biometric1.toString().isEmpty()){
					flag=true;
					form.setDuperrorMsg("MachineIp already exists.");
					form.setFlag(true);
				}
				else
					   flag=false;
		  }
			
		}catch(Exception e){
			log.error("Error during duplicate check..." , e);
			session.flush();
			session.close();
		}
		return flag;
		
	}

}
