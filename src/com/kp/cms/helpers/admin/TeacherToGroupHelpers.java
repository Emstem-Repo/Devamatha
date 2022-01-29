package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.TeacherToGroup;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.admin.TeacherToGroupForm;
import com.kp.cms.to.admin.TeacherToGroupTos;
import com.kp.cms.to.attendance.TeacherDepartmentTO;
import com.kp.cms.to.usermanagement.RolesTO;
import com.kp.cms.transactions.attandance.ITeacherDepartmentEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.TeacherDepartmentEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class TeacherToGroupHelpers
{

	private static final Log log=LogFactory.getLog(TeacherToGroupHelpers.class);
	public static volatile TeacherToGroupHelpers teacherToGroupHelpers = null;
   

    public static TeacherToGroupHelpers getInstance()
    {
        if(teacherToGroupHelpers == null)
        {
        	teacherToGroupHelpers = new TeacherToGroupHelpers();
            return teacherToGroupHelpers;
        } else
        {
            return teacherToGroupHelpers;
        }
    }
    /**
     * @param teacherToGroup
     * @return
     */
    public List<TeacherToGroupTos> convertBoToTos(List<TeacherToGroup> teacherToGroup)
    {
        List<TeacherToGroupTos> teacherList = new ArrayList<TeacherToGroupTos>();
        if(teacherToGroup != null)
        {
        	Iterator itr=teacherToGroup.iterator();
    		while (itr.hasNext()) {
    			TeacherToGroup teacherBoList = (TeacherToGroup)itr.next();
    			TeacherToGroupTos teacherTo= new TeacherToGroupTos();
    			teacherTo.setId(teacherBoList.getId());
    			teacherTo.setRolesName(teacherBoList.getRolesId().getName());
    			teacherTo.setUsersName(teacherBoList.getUsersId().getUserName());
    			teacherList.add(teacherTo);
            }

        }
        return teacherList;
    }

    /**
     * @param teacherToGroupForm
     * @return
     */
    public TeacherToGroup convertFormToBos(TeacherToGroupForm teacherToGroupForm)
    {
    	TeacherToGroup teacherToGroup = new TeacherToGroup();
    	Roles role=new Roles();
    	role.setId(Integer.parseInt(teacherToGroupForm.getRoleId()));
    	teacherToGroup.setRolesId(role);
    	Users user=new Users();
    	user.setId(Integer.parseInt(teacherToGroupForm.getUsersId()));
    	teacherToGroup.setUsersId(user);
    	teacherToGroup.setCreatedby(teacherToGroupForm.getUserId());
    	teacherToGroup.setCreatedDate(new Date());
    	teacherToGroup.setLastModifiedDate(new Date());
    	teacherToGroup.setModifiedBy(teacherToGroupForm.getUserId());
    	teacherToGroup.setIsActive(Boolean.valueOf(true));
        return teacherToGroup;
    }

    public void setDataBoToForm(TeacherToGroupForm teacherToGroupForm, TeacherToGroup teacherToGroup)
    {
        if(teacherToGroup != null)
        {
            teacherToGroupForm.setUsersId(String.valueOf(teacherToGroup.getUsersId().getId()));
            teacherToGroupForm.setRoleId(String.valueOf(teacherToGroup.getRolesId().getId()));
            teacherToGroupForm.setCreatedby(teacherToGroup.getCreatedby());
        }
    }

    public TeacherToGroup setFormToBo(TeacherToGroupForm teacherToGroupForm)
    {
    	TeacherToGroup teacherBo = new TeacherToGroup();
    	teacherBo.setId(teacherToGroupForm.getId());
    	Roles role=new Roles();
    	role.setId(Integer.parseInt(teacherToGroupForm.getRoleId()));
    	teacherBo.setRolesId(role);
    	Users user=new Users();
    	user.setId(Integer.parseInt(teacherToGroupForm.getUsersId()));
    	teacherBo.setUsersId(user);
    	teacherBo.setCreatedby(teacherToGroupForm.getCreatedby());
    	teacherBo.setLastModifiedDate(new Date());
    	teacherBo.setModifiedBy(teacherToGroupForm.getUserId());
    	teacherBo.setIsActive(Boolean.valueOf(true));
        return teacherBo;
    }

public Map<Integer, String> getroles(List<Object[]> list) {
	Map<Integer, String> roleMap = new LinkedHashMap<Integer, String>();
	if(list!=null){
		Iterator<Object[]> iterator=list.iterator();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			TeacherToGroupTos teacherToGroupTos=new TeacherToGroupTos();
			teacherToGroupTos.setRolesId(Integer.parseInt(objects[0].toString()));
			if(objects[1] !=null && !objects[1].toString().isEmpty())
			{
				teacherToGroupTos.setRolesName(objects[1].toString());
			}
			roleMap.put(teacherToGroupTos.getRolesId(),teacherToGroupTos.getRolesName());
		}
	}
	roleMap = (Map<Integer, String>) CommonUtil.sortMapByValue(roleMap);
	return roleMap;
}
public Map<Integer, String> getusers(List<Object[]> list) {
	Map<Integer, String> usersMap = new LinkedHashMap<Integer, String>();
	if(list!=null){
		Iterator<Object[]> iterator=list.iterator();
		while (iterator.hasNext()) {
			Object[] objects = (Object[]) iterator.next();
			TeacherToGroupTos teacherToGroupTos=new TeacherToGroupTos();
			teacherToGroupTos.setUserId(Integer.parseInt(objects[0].toString()));
			if(objects[1] !=null && !objects[1].toString().isEmpty())
			{
				teacherToGroupTos.setUsersName(objects[1].toString());
			}
			usersMap.put(teacherToGroupTos.getUserId(),teacherToGroupTos.getUsersName());
		}
	}
	usersMap = (Map<Integer, String>) CommonUtil.sortMapByValue(usersMap);
	return usersMap;
}

}
