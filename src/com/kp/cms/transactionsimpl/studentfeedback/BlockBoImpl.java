package com.kp.cms.transactionsimpl.studentfeedback;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.studentfeedback.BlockBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.studentfeedback.BlockBoForm;
import com.kp.cms.transactions.studentfeedback.IBlockBoTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class BlockBoImpl
    implements IBlockBoTransaction
{

    private static final Log log = LogFactory.getLog(BlockBoImpl.class);
    public static volatile BlockBoImpl blockBoImpl = null;

    public static BlockBoImpl getInstance()
    {
        if(blockBoImpl == null)
        {
        	blockBoImpl = new BlockBoImpl();
            return blockBoImpl;
        } else
        {
            return blockBoImpl;
        }
    }

    public List<EmployeeWorkLocationBO> getEmpLocation()
        throws Exception{
        Session session = null;
        try {
			session = HibernateUtil.getSession();
			String str = "from EmployeeWorkLocationBO where isActive=1";
			List<EmployeeWorkLocationBO> location = session.createQuery(str).list();
			session.flush();
			return location;
        }catch (Exception e) {
			if (session != null){
				session.flush();
			}	
			throw  new ApplicationException(e);
        }
  }

    public List<BlockBo> getBlockBoList()throws Exception{
        Session session = null;
        List<BlockBo> blockList;
        try
        {
        	session = InitSessionFactory.getInstance().openSession();
            Query query = session.createQuery("from BlockBo sub where sub.isActive=1");
            blockList = query.list();
         }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        return blockList;
    }

    public boolean duplicateCheck(BlockBoForm blockBoForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        BlockBo blockBo;
        try
        {
            session = HibernateUtil.getSession();
            String quer = "from BlockBo a where a.blockName=:blockName and a.locationId.id=:locationId";
            Query query = session.createQuery(quer);
            query.setString("blockName", blockBoForm.getBlockName());
            query.setString("locationId", blockBoForm.getLocationId());
            blockBo = (BlockBo)query.uniqueResult();
            if(blockBo != null && !blockBo.toString().isEmpty())
            {
                if(blockBoForm.getId() != 0)
                {   
                       	  if(blockBoForm.getId() == blockBo.getId())
         	               {
         		               flag = false;
         	                }
                          else if(blockBo.getIsActive())
                	      {  
                        	 flag = true;
                             errors.add("error", new ActionError("knowledgepro.admin.EmpJobTitle.name.exists"));
                	           
                         }else if(!blockBo.getIsActive())
                         {
                        flag = true;
                        blockBoForm.setId(blockBo.getId());
                        throw new ReActivateException(blockBo.getId());
                    }
                 }else{
                	 flag = true;
                     errors.add("error", new ActionError("knowledgepro.admin.EmpJobTitle.name.exists"));
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
            errors.add("error", new ActionError("knowledgepro.admin.EmpJobTitle.name.exists"));
            if(e instanceof ReActivateException)
            {
                errors.add("error", new ActionError("knowledgepro.studentFeedBack.reactivate"));
                ssession.setAttribute("ReactivateId", Integer.valueOf(blockBoForm.getId()));
            }
        }
        return flag;
    }

    public boolean addBlockBo(BlockBo blockBo)throws Exception{
    	log.info("call of addDesignationEntry in designationOrderTransactionImpl class.");
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
    	try {
    		session = HibernateUtil.getSession();
    		transaction = session.beginTransaction();
    		transaction.begin();
    		session.save(blockBo);
    		transaction.commit();
    		
    		isAdded = true;
    	} catch (Exception e) {
    		isAdded = false;
    		log.error("Unable to addDesignationEntry" , e);
    		throw new ApplicationException(e);
    	} finally {
    		if (session != null) {
    			session.flush();
    		    session.close();
    		}
    	}
    	log.info("end of addDesignationEntry in designationOrderTransactionImpl class.");
    	return isAdded;
}

    public BlockBo getBlockBoById(int id) throws Exception
    {
        Session session = null;
        BlockBo blockBo = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from BlockBo a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            blockBo = (BlockBo)query.uniqueResult();
        }
        catch (Exception e) {
    		log.error("Unable to editDesignationEntry", e);
    	} finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return blockBo;
    }

	@Override
	public boolean updateBlockBo(BlockBo blockBo)throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(blockBo);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return isUpdated;
}
	
	
    public boolean deleteBlockBo(int id) throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from BlockBo a where a.id=")).append(id).toString();
            BlockBo blockBo = (BlockBo)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            session.delete(blockBo);
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
