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

import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.TeacherToGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.TeacherToGroupForm;
import com.kp.cms.transactions.admin.ITeacherToGroupTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class TeacherToGroupImpl  implements ITeacherToGroupTransaction
{

    private static final Log log = LogFactory.getLog(TeacherToGroupImpl.class);
    public static volatile TeacherToGroupImpl teacherToGroupImpl = null;

    public static TeacherToGroupImpl getInstance()
    {
        if(teacherToGroupImpl == null)
        {
        	teacherToGroupImpl = new TeacherToGroupImpl();
            return teacherToGroupImpl;
        } else
        {
            return teacherToGroupImpl;
        }
    }

     public List<TeacherToGroup> getteacherGroupList() throws Exception {
        log.info("call of getteacherGroupList in TeacherToGroupImpl class.");
        Session session = null;
        List<TeacherToGroup> teacherToGroup = null;
        try
        {
            session=InitSessionFactory.getInstance().openSession();
            Query query = session.createQuery("from TeacherToGroup sub where sub.isActive=1");
            teacherToGroup = query.list();
        }
        catch(Exception e)
        {
            log.error("Unable to getFeedBackQusestionList", e);
            throw e;
        }
        log.info("end of getteacherGroupList in TeacherToGroupImpl class.");
        return teacherToGroup;
    }

    public boolean duplicateCheck(TeacherToGroupForm teacherToGroupForm, ActionErrors errors, HttpSession ssession)
        throws Exception{
        Session session = null;
        boolean flag = false;
        TeacherToGroup teacherToGroup;
       try
        {
            session = HibernateUtil.getSession();
            String quer = "from TeacherToGroup a where a.rolesId.id=:role and a.usersId.id=:user";
            Query query = session.createQuery(quer);
            query.setString("role", teacherToGroupForm.getRoleId());
            query.setString("user", teacherToGroupForm.getUsersId());
            teacherToGroup = (TeacherToGroup)query.uniqueResult();
            if(teacherToGroup != null && !teacherToGroup.toString().isEmpty())
            {
                if(teacherToGroupForm.getId() != 0)
                {   
                       	  if(teacherToGroupForm.getId() ==teacherToGroup.getId())
         	               {
                       		   if(!teacherToGroup.getIsActive().booleanValue()){
                       			     flag = true;
                                     teacherToGroupForm.setId(teacherToGroup.getId());
                                     throw new ReActivateException(teacherToGroup.getId());
                       		  }else{
         		               flag = false;
                       		  }
         	              }else if(teacherToGroup.getIsActive().booleanValue())
         	              {
         	            	 flag = true;
         	            	 errors.add("error", new ActionError("knowledgepro.teachertogroup.exit"));
         	              }else
                              {
                               flag = true;
                               teacherToGroupForm.setId(teacherToGroup.getId());
                              throw new ReActivateException(teacherToGroup.getId());
                              }
                }else if(teacherToGroup.getIsActive()){
					flag=true;
					errors.add("error", new ActionError("knowledgepro.teachertogroup.exit"));
				}else{
					  flag=true;
					  teacherToGroup.setId(teacherToGroup.getId());
					  teacherToGroupForm.setId(teacherToGroup.getId());
					  throw new ReActivateException(teacherToGroup.getId());
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
                errors.add("error", new ActionError("knowledgepro.teachertogroup.reactivate"));
                ssession.setAttribute("ReactivateId", Integer.valueOf(teacherToGroupForm.getId()));
            }
        }
        return flag;
    }

    public boolean addteacherToGroup(TeacherToGroup teacherToGroup, String mode)
        throws Exception{
        Session session;
        Transaction transaction;
        session = null;
        transaction = null;
        try{
			session=InitSessionFactory.getInstance().openSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(teacherToGroup);
			}
			else if(mode.equalsIgnoreCase("Edit")){
				session.update(teacherToGroup);
			}
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}finally{
			session.flush();
			session.close();
		}
        return true;
    }

    public TeacherToGroup getteacherToGroupById(int id)
        throws Exception
    {
        Session session = null;
        TeacherToGroup teacherToGroup = null;
        try
        {
            session = HibernateUtil.getSession();
            String str = (new StringBuilder("from TeacherToGroup a where a.id=")).append(id).toString();
            Query query = session.createQuery(str);
            teacherToGroup = (TeacherToGroup)query.uniqueResult();
        }
        catch(Exception e)
        {
            log.error("Error during getting getteacherToGroupById by id...", e);
            session.flush();
            session.close();
        }
        return teacherToGroup;
    }

    public boolean deleteTeacherToGroup(int id)
        throws Exception
    {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = InitSessionFactory.getInstance().openSession();
            String str = (new StringBuilder("from TeacherToGroup a where a.id=")).append(id).toString();
            TeacherToGroup teacherToGroup = (TeacherToGroup)session.createQuery(str).uniqueResult();
            transaction = session.beginTransaction();
            teacherToGroup.setIsActive(false);
            session.update(teacherToGroup);
            transaction.commit();
            session.close();
        }
        catch(Exception e)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
            log.debug("Error during deleting deleteTeacherToGroup data...", e);
        }
        return true;
    }

    public boolean reActivateTeacherToGroup(TeacherToGroupForm teacherToGroupForm)
        throws Exception{
    	log.info("Entering into reActivateTeacherToGroup of TeacherToGroupImpl");
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            String quer ="from TeacherToGroup a where a.id="+teacherToGroupForm.getId();
            Query query = session.createQuery(quer);
            TeacherToGroup leave = (TeacherToGroup)query.uniqueResult();
            leave.setIsActive(Boolean.valueOf(true));
            leave.setModifiedBy(teacherToGroupForm.getUserId());
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
        log.info("call of getgetRoles in TeacherToGroupImpl class.");
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
        log.info("end of getCertificateList in TeacherToGroupImpl class.");
        return rolesList;
    
	}


	@Override
	public List<Object[]> getroles() throws Exception {
		List<Object[]> rolesList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select r.id,r.name from Roles r where r.isActive=1";
			Query quer=session.createQuery(query);
			rolesList=quer.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return rolesList;
	}

	@Override
	public List<Object[]> getusers() throws Exception {
		List<Object[]> usersList=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String query="select u.id,u.userName from Users u left join u.employee e  with (e.active=1 and e.isActive=1) where  u.isActive=1 and u.active=1 and u.isTeachingStaff=1 and u.userName is not null";
			Query quer=session.createQuery(query);
			usersList=quer.list();
		}catch(Exception e){
			log.error("Error during getting courses..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		return usersList;
	}
}
