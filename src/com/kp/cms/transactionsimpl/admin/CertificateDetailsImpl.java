package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AssignCertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsRoles;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Roles;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.transactions.admin.ICertificateDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CertificateDetailsImpl  implements ICertificateDetailsTransaction
{

    private static final Log log = LogFactory.getLog(CertificateDetailsImpl.class);
    public static volatile CertificateDetailsImpl certificateDetailsImpl = null;

    public static CertificateDetailsImpl getInstance()
    {
        if(certificateDetailsImpl == null)
        {
        	certificateDetailsImpl = new CertificateDetailsImpl();
            return certificateDetailsImpl;
        } else
        {
            return certificateDetailsImpl;
        }
    }

    public List<CertificateDetails> getCertificateList()
        throws Exception{
        Session session = null;
        List<CertificateDetails> certificateList;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from CertificateDetails sub where sub.isActive=1");
            certificateList = query.list();
        }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        return certificateList;
    }

    public boolean duplicateCheck(CertificateDetailsForm certificateDetailsForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        CertificateDetails certificateDetails;
       try
        {
            session = HibernateUtil.getSession();
            String quer = "from CertificateDetails a where a.certificateName=:name and a.fees=:fees";
            Query query = session.createQuery(quer);
            query.setString("name", certificateDetailsForm.getCertificateName());
            query.setString("fees", certificateDetailsForm.getFees());
            certificateDetails = (CertificateDetails)query.uniqueResult();
            if(certificateDetails != null && !certificateDetails.toString().isEmpty())
            {
                if(certificateDetailsForm.getId() != 0)
                {   
                       	  if(certificateDetailsForm.getId() ==certificateDetails.getId())
         	               {
         		               flag = false;
         	              }else if(certificateDetails.getIsActive().booleanValue())
         	              {
         	            	 flag = true;
         	            	 errors.add("error", new ActionError("knowledgepro.certificatedetails.exit"));
         	              }else
                              {
                               flag = true;
                               certificateDetailsForm.setId(certificateDetails.getId());
                              throw new ReActivateException(certificateDetails.getId());
                              }
                }else if(certificateDetails.getIsActive()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.certificatedetails.exit"));
				}else{
					  flag=true;
					  certificateDetails.setId(certificateDetails.getId());
					  certificateDetailsForm.setId(certificateDetails.getId());
					  throw new ReActivateException(certificateDetails.getId());
				     }
            } else
            {
                flag = false;
            }
        }
        catch(Exception e)
        {
            log.debug("Reactivate Exception", e);
            flag = true;
            if(e instanceof ReActivateException)
            {
                errors.add("error", new ActionError("knowledgepro.certificatedetails.reactivate"));
                ssession.setAttribute("ReactivateId", Integer.valueOf(certificateDetailsForm.getId()));
            }
        }
        return flag;
    }

    public boolean addcertificateDetails(CertificateDetails certificateDetails, String mode)
        throws Exception{
        Session session;
        Transaction transaction;
        session = null;
        transaction = null;
        try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(certificateDetails);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(certificateDetails);
			}
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}finally{
			if(session!=null){
			   session.flush();
			   session.close();
			}
		}
        return true;
    }

    public CertificateDetails getcertificateDetailsById(int id)
        throws Exception
    {
        Session session = null;
        CertificateDetails certificateDetails = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from CertificateDetails a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            certificateDetails = (CertificateDetails)query.uniqueResult();
        }
        catch(Exception e)
        {
            log.error("Error during getting getcertificateDetailsById by id...", e);
        }
        return certificateDetails;
    }

    public boolean deleteCertificateDetails(int id)
        throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from CertificateDetails a where a.id=")).append(id).toString();
            CertificateDetails certificateDetails = (CertificateDetails)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            certificateDetails.setIsActive(false);
            session.update(certificateDetails);
            transaction.commit();
            session.close();
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
            log.debug("Error during deleting deleteCertificateDetails data...", e);
        }
        return true;
    }

    public boolean reActivateCertificateDetails(CertificateDetailsForm certificateDetailsForm)
        throws Exception{
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String quer ="from CertificateDetails a where a.isActive=1 and a.id="+certificateDetailsForm.getId();
            Query query = session.createQuery(quer);
            CertificateDetails leave = (CertificateDetails)query.uniqueResult();
            leave.setIsActive(Boolean.valueOf(true));
            leave.setModifiedBy(certificateDetailsForm.getUserId());
            leave.setLastModifiedDate(new Date());
            session.update(leave);
            transaction.commit();
        }
        catch(Exception e)
        {
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
    }

	@Override
	public List<Roles> getgetRoles() throws Exception {
        Session session = null;
        List<Roles> rolesList;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from Roles role where role.isActive=1");
            rolesList = query.list();
        }
        catch(Exception e)
        {
            log.error("Unable to getgetRoles", e);
            throw e;
        }
        log.info("end of getCertificateList in CertificateDetailsImpl class.");
        return rolesList;
    
	}

	public boolean addCertificateDetails(List<CertificateDetailsRoles> certificateDetailsRoles)throws Exception {
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
        try{
        session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		String str="from CertificateDetailsRoles cer where cer.isActive=1";
		Query query = session.createQuery(str);
		List<CertificateDetailsRoles> leaveList = query.list();
		if(certificateDetailsRoles!=null){
		Iterator<CertificateDetailsRoles> iterator = certificateDetailsRoles.iterator();
		while (iterator.hasNext()) {
			boolean isNotPresent=true;
			CertificateDetailsRoles certificateDetailsRoles2 = (CertificateDetailsRoles) iterator.next();
			for(CertificateDetailsRoles bo:leaveList){
				if(bo.getCertificateId().getId()==certificateDetailsRoles2.getCertificateId().getId() && bo.getCertificateRolesId().getId()==certificateDetailsRoles2.getCertificateRolesId().getId()){
					if(!certificateDetailsRoles2.getIsActive()){
						certificateDetailsRoles2.setIsActive(false);
						isNotPresent=true;
						break;
						
					}else{
						isNotPresent=false;
						break;
					}
					
				}
			}
			if(isNotPresent && !certificateDetailsRoles2.getIsActive()){
				 String quer = "from CertificateDetailsRoles a where a.id="+certificateDetailsRoles2.getId();
		         Query queryy = session.createQuery(quer);
		         CertificateDetailsRoles certificateDetails = (CertificateDetailsRoles)queryy.uniqueResult();
				session.delete(certificateDetails);
			}else if(isNotPresent){
				session.save(certificateDetailsRoles2);
			}
		}}		
		if(certificateDetailsRoles!=null && !certificateDetailsRoles.isEmpty()){
		transaction.commit();
		isAdded = true;
			}
			else{
				isAdded = false;
			}
		}
        catch(Exception e){
            isAdded = false;
            log.error("Unable to addCertificateDetails", e);
            throw new ApplicationException(e);
        } finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return isAdded;
    }
	
	public boolean addCertificateDetailsPurpose(List<AssignCertificateRequestPurpose> certificateReqPurpose)throws Exception {
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
        try{
        session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		String str="from AssignCertificateRequestPurpose cer where cer.isActive=1";
		Query query = session.createQuery(str);
		List<AssignCertificateRequestPurpose> list = query.list();
		if(certificateReqPurpose!=null){
		Iterator<AssignCertificateRequestPurpose> iterator = certificateReqPurpose.iterator();
		while (iterator.hasNext()) {
			boolean isNotPresent=true;
			AssignCertificateRequestPurpose purpose2 = (AssignCertificateRequestPurpose) iterator.next();
			for(AssignCertificateRequestPurpose bo:list){
				if(bo.getCertificateId()!=null)
				{
				if(bo.getCertificateId().getId()==purpose2.getCertificateId().getId() && bo.getCertificatePurposeId().getId()==purpose2.getCertificatePurposeId().getId()){
					if(!purpose2.getIsActive()){
						purpose2.setIsActive(false);
						isNotPresent=true;
						break;
						
					}else{
						isNotPresent=false;
						break;
					}
					
				}}
			}
			if(isNotPresent && !purpose2.getIsActive()){
				 String quer = "from AssignCertificateRequestPurpose a where a.id="+purpose2.getId();
		         Query queryy = session.createQuery(quer);
		         AssignCertificateRequestPurpose certificateDetails = (AssignCertificateRequestPurpose)queryy.uniqueResult();
				session.delete(certificateDetails);
			}else if(isNotPresent){
				session.save(purpose2);
			}
		}	}	
		if(certificateReqPurpose!=null && !certificateReqPurpose.isEmpty()){
		transaction.commit();
		isAdded = true;
			}
			else{
				isAdded = false;
			}
		}
        catch(Exception e){
            isAdded = false;
            log.error("Unable to addCertificateDetails", e);
            throw new ApplicationException(e);
        } finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return isAdded;
    }
	
	
	public boolean addCertificateDetailsTemplate(CertificateDetailsTemplate certificateTemplate)throws Exception {
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
        try{
        session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		transaction.begin();
		String str="from CertificateDetailsTemplate cer where cer.isActive=1";
		Query query = session.createQuery(str);
		List<CertificateDetailsTemplate> list = query.list();
			boolean isNotPresent=true;
			for(CertificateDetailsTemplate bo:list){
				if(bo.getCertificateId().getId()==certificateTemplate.getCertificateId().getId() && bo.getCertificateId().getCertificateName().equalsIgnoreCase(certificateTemplate.getTemplateName())){
					if(!certificateTemplate.getIsActive()){
						certificateTemplate.setIsActive(false);
						isNotPresent=true;
						break;
						
					}else{
						isNotPresent=false;
						break;
					}
				}
			}
			if(isNotPresent && !certificateTemplate.getIsActive()){
				 String quer = "from CertificateDetailsTemplate a where a.id="+certificateTemplate.getId();
		         Query queryy = session.createQuery(quer);
		         CertificateDetailsTemplate certificateDetails = (CertificateDetailsTemplate)queryy.uniqueResult();
				session.delete(certificateDetails);
			}else if(isNotPresent){
				session.save(certificateTemplate);
			}
				
		if(certificateTemplate!=null){
		transaction.commit();
		isAdded = true;
			}
			else{
				isAdded = false;
			}
		}
        catch(Exception e){
            isAdded = false;
            log.error("Unable to addCertificateDetails", e);
            throw new ApplicationException(e);
        } finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return isAdded;
    }
	
	@Override
	public Map<Integer,Integer> getAssignRoles(CertificateDetailsForm certificateDetailsForm) throws Exception {
        Session session = null;
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
       // List<CertificateDetailsRoles> AssignList = new ArrayList<CertificateDetailsRoles>();
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("select sub.certificateRolesId.id,sub.id from CertificateDetailsRoles sub where sub.isActive=1 and sub.certificateId="+certificateDetailsForm.getCertificateDetailsId());
           List<Object[]> list = query.list();
            if(list!=null && !list.isEmpty()){
            	Iterator<Object[]> iterator = list.iterator();
            	while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects[0].toString()), Integer.parseInt(objects[1].toString()));
					}
				}
            }
        }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        
        return map;
    }
	
	@Override
	public List<CertificateRequestPurpose> getPurpose() throws Exception {
        Session session = null;
        List<CertificateRequestPurpose> purposeList;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from CertificateRequestPurpose role where role.isActive=1");
            purposeList = query.list();
        }
        catch(Exception e)
        {
            log.error("Unable to getPurpose", e);
            throw e;
        }
        return purposeList;
    
	}
	@Override
	public Map<Integer,Integer> getAssignPurpose(CertificateDetailsForm certificateDetailsForm) throws Exception {
        Session session = null;
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
       // List<CertificateDetailsRoles> AssignList = new ArrayList<CertificateDetailsRoles>();
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("select sub.certificatePurposeId.id,sub.id from AssignCertificateRequestPurpose sub where sub.isActive=1 and sub.certificateId="+certificateDetailsForm.getCertificateDetailsId());
           List<Object[]> list = query.list();
            if(list!=null && !list.isEmpty()){
            	Iterator<Object[]> iterator = list.iterator();
            	while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects[0].toString()), Integer.parseInt(objects[1].toString()));
					}
				}
            }
        }
        catch(Exception e)
        {
            log.error("Unable to save AssignPurpose", e);
            throw e;
        }
        
        return map;
    }
	
	public String getCertificateName (int certId)throws Exception
	{
		
		Session session=null;
		String certName="";
		
		try{
			session=HibernateUtil.getSession();
			String quer="select d.certificateName from CertificateDetails d where d.isActive=1 and d.id="+certId;
			Query query=session.createQuery(quer);
			certName=query.uniqueResult().toString();
			
		}catch(Exception e){
			log.error("Error while getting CertificateDetails id.." +e);
		}
		return certName;
	}
	public List<CertificateDetailsTemplate> getGroupTemplates(String templateName) throws Exception {
		Session session = null;
		List<CertificateDetailsTemplate> cert = null;
		String sqlQuery="";
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 if (templateName != null && !templateName.isEmpty()) {
				 sqlQuery="from CertificateDetailsTemplate t where t.templateName='"+ templateName +"' and t.isActive=1";
			} 
			 Query query=session.createQuery(sqlQuery);
			 cert =  query.list();
			 
			 return cert;
		 } catch (Exception e) {
			 log.debug("Txn Impl : Leaving getGroupTemplates with Exception"+e.getMessage());
			 throw e;
		 }finally{
			if (session != null) {
				session.flush();
				//session.close();
			}
		 }
	}
		 
		 
		 public boolean saveGroupTemplate(CertificateDetailsTemplate groupTemplate) throws Exception {
				Session session = null; 
				Transaction tx = null;
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					 session = HibernateUtil.getSession();
					 
					 tx = session.beginTransaction();
					 tx.begin();
					 session.saveOrUpdate(groupTemplate);
					 tx.commit();
			    	 return true;
				 } catch (ConstraintViolationException e) {
					 tx.rollback();
					 log.debug("Txn Impl : Leaving saveTemplate with Exception"+e.getMessage());
					 throw e;				 
				 } catch (Exception e) {
					 if(tx!=null)
					       tx.rollback();
					 log.debug("Txn Impl : Leaving saveTemplate with Exception");
					 throw e;				 
				 }finally{
					if (session != null) {
						session.flush();
						session.close();
					}
				 } 
			}
			
			
				
			
			@Override
			public boolean deleteGroupTemplate(CertificateDetailsTemplate groupTemplate) throws Exception {
				Session session = null; 
				Transaction tx = null;
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					 session = HibernateUtil.getSession();
					 tx = session.beginTransaction();
					 tx.begin();
					 session.delete(groupTemplate);
					 tx.commit();
			    	 return true;
				 } catch (ConstraintViolationException e) {
					 tx.rollback();
					 log.debug("Txn Impl : Leaving saveTemplate with Exception"+e.getMessage());
					 throw e;				 
				 } catch (Exception e) {
					 if(tx!=null)
					     tx.rollback();
					 log.debug("Txn Impl : Leaving saveTemplate with Exception");
					 throw e;				 
				 }finally{
					if (session != null) {
						session.flush();
						session.close();
					}
				 }  
			}

			
			
			/* (non-Javadoc)
			 * @see com.kp.cms.transactions.admin.ITemplatePassword#getDuplicateCheckList(int, java.lang.String)
			 */
			@Override
			public List<CertificateDetailsTemplate> checkDuplicate(String templateName) throws Exception {
				Session session = null;
				List<CertificateDetailsTemplate> list = null;
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					session = HibernateUtil.getSession();
					
						Query query = session.createQuery("from CertificateDetailsTemplate groupTemplate where groupTemplate.templateName = :templateName");
						query.setString("templateName", templateName);
						list = query.list();
					 return list;
				} catch (Exception e) {
					log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
					throw e;
				}finally{
					if (session != null) {
						session.flush();
						//session.close();
					}
				} 
			}
			@Override
			public List<CertificateDetailsTemplate> checkDuplicateCertificateTemplate(String certName) throws Exception {
				Session session = null;
				List<CertificateDetailsTemplate> list = null;
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					session = HibernateUtil.getSession();
					 if (certName != null) {
						Query query = session.createQuery("from CertificateDetailsTemplate cd where cd.templateName=:certName");
						query.setString("certName", certName);
						list = query.list();
					 }
					 
					 return list;
				} catch (Exception e) {
					log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
					throw e;
				}finally{
					if (session != null) {
						session.flush();
						//session.close();
					}
				} 
			}
			
			
			
			
			@Override
			public List<CertificateDetailsTemplate> getGroupTemplate( String templateName)
					throws Exception {
				Session session = null;
				List<CertificateDetailsTemplate> list = null;
				try {
					 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
					 session = HibernateUtil.getSession();
					
					
						Query query = session.createQuery("from CertificateDetailsTemplate groupTemplate where groupTemplate.templateName = :templateName");
						query.setString("templateName", templateName);
						list = query.list();
					 return list;
				} catch (Exception e) {
					log.debug("Txn Impl : Leaving getTemplates with Exception"+e.getMessage());
					throw e;
				}finally{
					if (session != null) {
						session.flush();
						//session.close();
					}
				} 
			}
			@SuppressWarnings("unchecked")
			public List<CertificateDetailsTemplate> getDuplicateCheckList(String templateName)
					throws Exception {
				Session session = null;
				List<CertificateDetailsTemplate> list = null;
				try {
					// SessionFactory sessionFactory = InitSessionFactory.getInstance();
					session = HibernateUtil.getSession();

					Query query = session.createQuery("from CertificateDetailsTemplate groupTemplate where groupTemplate.templateName = :templateName");

					query.setString("templateName", templateName);
					list = query.list();
					return list;
				} catch (Exception e) {
					log.debug("Txn Impl : Leaving getTemplates with Exception"
							+ e.getMessage());
					throw e;
				} finally {
					if (session != null) {
						session.flush();
						// session.close();
					}
				}
			}
	}

	
	
	

