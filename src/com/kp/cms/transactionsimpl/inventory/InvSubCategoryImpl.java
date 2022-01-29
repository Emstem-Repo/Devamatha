package com.kp.cms.transactionsimpl.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvSubCategoryBo;
import com.kp.cms.bo.exam.ValuatorChargesBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.InvSubCategoryForm;
import com.kp.cms.transactions.inventory.IInvSubCategoryTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class InvSubCategoryImpl implements IInvSubCategoryTransaction{
	

	private static final Log log=LogFactory.getLog(InvSubCategoryImpl.class);
	public static volatile InvSubCategoryImpl invSubCategoryImpl=null;
	public static InvSubCategoryImpl getInstance()
	{
		if(invSubCategoryImpl==null)
		{
			invSubCategoryImpl=new InvSubCategoryImpl();
			return invSubCategoryImpl;
		}
		return invSubCategoryImpl;

    }
	@Override
	public List<InvItemCategory> getCategory() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String programHibernateQuery = "from InvItemCategory where isActive=1";
			List<InvItemCategory> category = session.createQuery(programHibernateQuery).list();
//			session.flush();
			//session.close();
			return category;
		} catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
	}
	@Override
	public List<InvSubCategoryBo> getSubCategoryList() throws Exception {
		log.info("call of getSubCategoryList in InvSubCategoryImpl class.");
		Session session=null;
		List<InvSubCategoryBo> SubCategoryList=null;
		try{
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from InvSubCategoryBo subCategoryBo where subCategoryBo.isActive=1");
			 SubCategoryList=query.list();
//			session.close();
		}catch(Exception e){
			log.error("Unable to getValuatorChargeList",e);
			 throw e;
		}
		log.info("end of getValuatorChargeList in ValuatorChargesImpl class.");
		return SubCategoryList;
	}
	@Override
	public boolean addSubCategory(InvSubCategoryBo subCategoryBo, String mode)throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(subCategoryBo);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.merge(subCategoryBo);
			}
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
	@Override
	public InvSubCategoryBo getSubcategoryById(int id) throws Exception {
		Session session=null;
		InvSubCategoryBo subCategoryBo=null;
		try{
			session=HibernateUtil.getSession();
			String str="from InvSubCategoryBo subCategory where subCategory.id="+id;
			Query query=session.createQuery(str);
			subCategoryBo=(InvSubCategoryBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
		}
		return subCategoryBo;
	}
	@Override
	public boolean deleteSubCategory(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from InvSubCategoryBo subcategory where subcategory.id="+id;
      	  InvSubCategoryBo subcategoryBo=(InvSubCategoryBo)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	  subcategoryBo.setIsActive(false);
      	    session.update(subcategoryBo);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting ValuatorCharges data...", e);
      	   }
       return true;
		}
	@Override
	public boolean reactivateSubCategory(InvSubCategoryForm invSubCategoryForm)	throws Exception {
		log.info("Entering into ValuatorChargesImpl of reactivateDocExamType");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session=null;
		Transaction transaction=null;
		try{
		   session=HibernateUtil.getSession();
		   transaction=session.beginTransaction();
		   String quer="from InvSubCategoryBo subcategory where subcategory.id="+invSubCategoryForm.getId();
		   Query query=session.createQuery(quer);
		   InvSubCategoryBo leave=(InvSubCategoryBo)query.uniqueResult();
		   leave.setIsActive(true);
		   leave.setModifiedBy(invSubCategoryForm.getUserId());
		   leave.setLastModifiedDate(new Date());
		   session.update(leave);
		   transaction.commit();
		}catch(Exception e){
			log.debug("Exception" + e.getMessage());
			return false;
		}finally{
			if(session!=null)
				session.close();
		}
		return true;
}
	@Override
	public boolean duplicateCheck(InvSubCategoryForm invSubCategoryForm,String invItemCategory, ActionErrors errors, HttpSession hsession) {
		Session session=null;
		boolean flag=false;
		InvSubCategoryBo subCategory;
		try{
			session=HibernateUtil.getSession();
			String quer="from InvSubCategoryBo subcategory where subcategory.invItemCategory=:itemcategory and subcategory.subCategoryName=:subcategory";
			Query query=session.createQuery(quer);
			query.setString("itemcategory", invSubCategoryForm.getInvItemCategory());
			query.setString("subcategory", invSubCategoryForm.getSubCategoryName());
			subCategory=(InvSubCategoryBo)query.uniqueResult();
			if(subCategory!=null && !subCategory.toString().isEmpty()){
				if(invSubCategoryForm.getId()!=0){
			      if(subCategory.getId()==invSubCategoryForm.getId()){
				    flag=false;
			      }else if(subCategory.getIsActive()){
				     flag=true;
				     errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
			       }
			      else{
					   flag=true;
					   invSubCategoryForm.setInvItemCategory(String.valueOf(subCategory.getInvItemCategory().getId()));
					   invSubCategoryForm.setId(subCategory.getId());
					   throw new ReActivateException(subCategory.getId());
				   }
				}else if(subCategory.getIsActive()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.inventory.subcategory.nameexit"));
				}
				else{
					  flag=true;
					  subCategory.setId(subCategory.getId());
					  invSubCategoryForm.setId(subCategory.getId());
					  throw new ReActivateException(subCategory.getId());
				 } 
			}else
				flag=false;
				
		}catch(Exception e){
			log.debug("Reactivate Exception", e);
			if(e instanceof ReActivateException){
				errors.add("error", new ActionError(CMSConstants.INVENTORY_NAME_REACTIVATE));
				//saveErrors(request, errors);
				hsession.setAttribute("ReactivateId", invSubCategoryForm.getId());
			}
		}
		return flag;
	}
	
	
}
