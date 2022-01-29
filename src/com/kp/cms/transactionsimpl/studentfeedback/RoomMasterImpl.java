package com.kp.cms.transactionsimpl.studentfeedback;

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
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.studentfeedback.RoomMasterForm;
import com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class RoomMasterImpl
    implements IRoomMasterTransaction
{

    private static final Log log = LogFactory.getLog(RoomMasterImpl.class);
    public static volatile RoomMasterImpl roomMasterImpl = null;

    public static RoomMasterImpl getInstance()
    {
        if(roomMasterImpl == null)
        {
        	roomMasterImpl = new RoomMasterImpl();
            return roomMasterImpl;
        } else
        {
            return roomMasterImpl;
        }
    }

    /* (non-Javadoc)
     * @see com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction#getEmpLocation()
     */
    public List<EmployeeWorkLocationBO> getEmpLocation()throws Exception{
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

    /* (non-Javadoc)
     * @see com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction#getRoomMasterList()
     */
    public List<RoomMaster> getRoomMasterList(RoomMasterForm roomMasterForm)throws Exception{
        Session session = null;
        List<RoomMaster> blockList;
        try
        {
        	session = InitSessionFactory.getInstance().openSession();
            String str ="from RoomMaster sub where sub.isActive=1";
            if(roomMasterForm.getLocationId()!=null && !roomMasterForm.getLocationId().isEmpty()){
            str=str+" and sub.blockId.locationId.id="+roomMasterForm.getLocationId();
            } if(roomMasterForm.getBlockId()!=null && !roomMasterForm.getBlockId().isEmpty()){
            str=str+" and sub.blockId.id="+roomMasterForm.getBlockId();
            }if(roomMasterForm.getRoomNo()!=null && !roomMasterForm.getRoomNo().isEmpty()){
            str=str+" and sub.roomNo='"+roomMasterForm.getRoomNo()+"'";
            }if(roomMasterForm.getFloor()!=null && !roomMasterForm.getFloor().isEmpty()){
            str=str+" and sub.floor='"+roomMasterForm.getFloor()+"'";
            }if(roomMasterForm.getFloorName()!=null && !roomMasterForm.getFloorName().isEmpty()){
            str=str+" and sub.floorName='"+roomMasterForm.getFloorName()+"'";
            }
            Query query = session.createQuery(str);
            blockList = query.list();
         }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        return blockList;
    }

    /* (non-Javadoc)
     * @see com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction#duplicateCheck(com.kp.cms.forms.studentfeedback.RoomMasterForm, org.apache.struts.action.ActionErrors, javax.servlet.http.HttpSession)
     */
    public boolean duplicateCheck(RoomMasterForm roomMasterForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        RoomMaster roomMaster;
        try
        {
            session = HibernateUtil.getSession();
            String quer = "from RoomMaster a where a.blockId.id=:blockId and a.floor=:floor and a.floorName=:floorName and a.roomNo=:roomNo";
            Query query = session.createQuery(quer);
            query.setString("blockId", roomMasterForm.getBlockId());
            query.setString("floor", roomMasterForm.getFloor());
            query.setString("floorName", roomMasterForm.getFloorName());
            query.setString("roomNo", roomMasterForm.getRoomNo());
            roomMaster = (RoomMaster)query.uniqueResult();
            if(roomMaster != null && !roomMaster.toString().isEmpty())
            {
                if(roomMasterForm.getId() != 0)
                {   
                       	  if(roomMasterForm.getId() == roomMaster.getId())
         	               {
         		               flag = false;
         	                }
                          else if(roomMaster.getIsActive())
                	      {  
                        	 flag = true;
                             errors.add("error", new ActionError("knowledgepro.admin.EmpJobTitle.name.exists"));
                	           
                         }else if(!roomMaster.getIsActive())
                         {
                        flag = true;
                        roomMasterForm.setId(roomMaster.getId());
                        throw new ReActivateException(roomMaster.getId());
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
                ssession.setAttribute("ReactivateId", Integer.valueOf(roomMasterForm.getId()));
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction#addRoomMaster(com.kp.cms.bo.studentfeedback.RoomMaster)
     */
    public boolean addRoomMaster(RoomMaster roomMaster)throws Exception{
    	log.info("call of addDesignationEntry in designationOrderTransactionImpl class.");
    	Session session = null;
    	Transaction transaction = null;
    	boolean isAdded = false;
    	try {
    		session = HibernateUtil.getSession();
    		transaction = session.beginTransaction();
    		transaction.begin();
    		session.save(roomMaster);
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

    /* (non-Javadoc)
     * @see com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction#getRoomMasterById(int)
     */
    public RoomMaster getRoomMasterById(int id) throws Exception
    {
        Session session = null;
        RoomMaster roomMaster = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from RoomMaster a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            roomMaster = (RoomMaster)query.uniqueResult();
        }
        catch (Exception e) {
    		log.error("Unable to editDesignationEntry", e);
    	} finally {
    		if (session != null) {
    			session.flush();
    			//session.close();
    		}
    	}
        return roomMaster;
    }

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction#updateRoomMaster(com.kp.cms.bo.studentfeedback.RoomMaster)
	 */
	@Override
	public boolean updateRoomMaster(RoomMaster roomMaster)throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(roomMaster);
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
	
	
    /* (non-Javadoc)
     * @see com.kp.cms.transactions.studentfeedback.IRoomMasterTransaction#deleteRoomMaster(int)
     */
    public boolean deleteRoomMaster(int id) throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from RoomMaster a where a.id=")).append(id).toString();
            RoomMaster roomMaster = (RoomMaster)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            session.delete(roomMaster);
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
