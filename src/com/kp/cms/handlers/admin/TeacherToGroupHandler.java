package com.kp.cms.handlers.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.TeacherToGroup;
import com.kp.cms.forms.admin.TeacherToGroupForm;
import com.kp.cms.helpers.admin.TeacherToGroupHelpers;
import com.kp.cms.to.admin.TeacherToGroupTos;
import com.kp.cms.transactions.admin.ITeacherToGroupTransaction;
import com.kp.cms.transactionsimpl.admin.TeacherToGroupImpl;

public class TeacherToGroupHandler
{

	private static final Log log=LogFactory.getLog(TeacherToGroupHandler.class);
	public static volatile TeacherToGroupHandler teacherToGroupHandler=null;
	
	public static TeacherToGroupHandler getInstance()
    {
        if(teacherToGroupHandler == null)
        {
        	teacherToGroupHandler = new TeacherToGroupHandler();
            return teacherToGroupHandler;
        } else
        {
            return teacherToGroupHandler;
        }
    }
	ITeacherToGroupTransaction transaction = TeacherToGroupImpl.getInstance();
   
    public boolean duplicateCheck(TeacherToGroupForm teacherToGroupForm, ActionErrors errors, HttpSession session)
        throws Exception
    {
        boolean duplicate = transaction.duplicateCheck(teacherToGroupForm, errors, session);
        return duplicate;
    }

    public boolean addteacherToGroup(TeacherToGroupForm teacherToGroupForm, String mode)
        throws Exception
    {
    	TeacherToGroup teacherToGroup = TeacherToGroupHelpers.getInstance().convertFormToBos(teacherToGroupForm);
        boolean isAdded = transaction.addteacherToGroup(teacherToGroup, mode);
        return isAdded;
    }

    public void editTeacherToGroup(TeacherToGroupForm teacherToGroupForm)
        throws Exception
    {
    	TeacherToGroup teacherToGroup = transaction.getteacherToGroupById(teacherToGroupForm.getId());
        TeacherToGroupHelpers.getInstance().setDataBoToForm(teacherToGroupForm, teacherToGroup);
    }

    public boolean updateteacherToGroup(TeacherToGroupForm teacherToGroupForm, String mode)
        throws Exception
    {
    	TeacherToGroup teacherToGroup = TeacherToGroupHelpers.getInstance().setFormToBo(teacherToGroupForm);
        boolean isUpdated = transaction.addteacherToGroup(teacherToGroup, mode);
        return isUpdated;
    }

    public boolean deleteTeacherToGroup(TeacherToGroupForm teacherToGroupForm)
        throws Exception
    {
        boolean isDeleted = transaction.deleteTeacherToGroup(teacherToGroupForm.getId());
        return isDeleted;
    }

    public boolean reActivateTeacherToGroup(TeacherToGroupForm teacherToGroupForm, String userId)
        throws Exception
    {
        return transaction.reActivateTeacherToGroup(teacherToGroupForm);
    }
	/**
	 * @return
	 * @throws Exception
	 */
	public List<TeacherToGroupTos> getteacherGroupList() throws Exception{
    List<TeacherToGroup> teacherToGroup = transaction.getteacherGroupList();
    List<TeacherToGroupTos> teacherToGroupList =TeacherToGroupHelpers.getInstance().convertBoToTos(teacherToGroup);
    return teacherToGroupList;}

	public Map<Integer, String> getroles() throws Exception {
		List<Object[]> list=transaction.getroles();
		 Map<Integer, String> rolesMap = TeacherToGroupHelpers.getInstance().getroles(list);
		return rolesMap;
	}

	public Map<Integer, String> getusers() throws Exception {
		List<Object[]> list=transaction.getusers();
		 Map<Integer, String> usersMap = TeacherToGroupHelpers.getInstance().getusers(list);
		return usersMap;
	}

}
