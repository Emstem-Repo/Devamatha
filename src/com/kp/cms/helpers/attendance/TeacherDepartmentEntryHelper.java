package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.TeacherDepartment;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.UsersUtilBO;
import com.kp.cms.forms.attendance.TeacherDepartmentEntryForm;
import com.kp.cms.to.attendance.TeacherDepartmentTO;
import com.kp.cms.transactions.attandance.ITeacherDepartmentEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.TeacherDepartmentEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class TeacherDepartmentEntryHelper {
	private static final Log log = LogFactory
	.getLog(TeacherDepartmentEntryHelper.class);
public static volatile TeacherDepartmentEntryHelper objHelper = null;

public static TeacherDepartmentEntryHelper getInstance() {
if (objHelper == null) {
	objHelper = new TeacherDepartmentEntryHelper();
	return objHelper;
}
return objHelper;
}

public static TeacherDepartment convertFormToBoForAdd(TeacherDepartmentEntryForm teacherDepartmentForm){
	TeacherDepartment teacherDepartment=new TeacherDepartment();
	Users users=new Users();
	Department department=new Department();
	users.setId(Integer.parseInt(teacherDepartmentForm.getTeacher()));
	department.setId(Integer.parseInt(teacherDepartmentForm.getDepartment()));
	teacherDepartment.setDepartmentId(department);
	teacherDepartment.setTeacherId(users);
	
	return teacherDepartment;
}
public static List<TeacherDepartmentTO> getTeacherDepartments(List<TeacherDepartment> teacherDepartmentList){
	List<TeacherDepartmentTO> toList=new ArrayList<TeacherDepartmentTO>();
	Iterator itr=teacherDepartmentList.iterator();
	
	while(itr.hasNext()){
		TeacherDepartment tDepartment=(TeacherDepartment)itr.next();
		TeacherDepartmentTO to=new TeacherDepartmentTO();
		to.setId(tDepartment.getId());
		to.setDepartmentName(tDepartment.getDepartmentId().getName());
		to.setTeacherName(tDepartment.getTeacherId().getUserName());
		toList.add(to);
	}
	return toList;
		}
@SuppressWarnings("unchecked")
public static Map<Integer, String> getTeacherDepartmentsName(List<Object[]> teacherDepartmentList)throws Exception {
	Map<Integer, String> teacherMap = new LinkedHashMap<Integer, String>();
	//List<TeacherDepartmentTO> toList=new ArrayList<TeacherDepartmentTO>();
	Iterator<Object[]> itr=teacherDepartmentList.iterator();
	while(itr.hasNext()){
		Object[] tDepartment=(Object[])itr.next();
		TeacherDepartmentTO to=new TeacherDepartmentTO();
		//if(tDepartment[2].toString().trim() != null && !tDepartment[2].toString().isEmpty())
	//	{
		to.setId(Integer.parseInt(tDepartment[0].toString()));
		int userIdEmpl=(Integer.parseInt(tDepartment[0].toString()));
			if (tDepartment[1] != null ) 
			{
				if(!tDepartment[1].toString().isEmpty())
				{
					to.setDepartmentName(tDepartment[1].toString());
						
				}
			}
			else if (tDepartment[1] == null)
			{
				// new code by mary.....
			
				ITeacherDepartmentEntryTransaction transaction=new TeacherDepartmentEntryTransactionImpl();
				List<Object[]> objectList=transaction.getTeacherDepartmentsNameFromUser(userIdEmpl);
				TeacherDepartmentTO toUser=new TeacherDepartmentTO();
				// Map<Integer, String> teacherUserMap=new LinkedHashMap<Integer, String>();
				 Iterator<Object[]> itrUser=objectList.iterator();
					while(itrUser.hasNext()){
						Object[] UDepartment=(Object[])itrUser.next();
						
						int UserId=(Integer.parseInt(UDepartment[0].toString()));
						toUser.setId(Integer.parseInt(UDepartment[0].toString()));
						if (UDepartment[1] != null ) 
							{
								if(!UDepartment[1].toString().isEmpty())
								{
									toUser.setDepartmentName(UDepartment[1].toString());
										
								}
							}
							else
							{	//Added by Dilip
								ITeacherDepartmentEntryTransaction transaction1=new TeacherDepartmentEntryTransactionImpl();
								List<Object[]> objectList1=transaction1.getTeacherDepartmentsNameFromGuest(UserId);
								TeacherDepartmentTO toUser1=new TeacherDepartmentTO();
								 Iterator<Object[]> itrUser1=objectList1.iterator();
									while(itrUser1.hasNext()){
										Object[] UDepartment1=(Object[])itrUser1.next();
										toUser1.setId(Integer.parseInt(UDepartment1[0].toString()));
										if (UDepartment1[1] != null )
										{
											toUser1.setDepartmentName(UDepartment1[1].toString());
										}
										else
										{
												toUser1.setDepartmentName("-");
										}
									}
									toUser.setDepartmentName(toUser1.getDepartmentName());	
									break;
							}
				 //code ends.....
					
					}
					to.setDepartmentName(toUser.getDepartmentName());	
			}
			if(tDepartment[2]!=null){
				to.setTeacherName(tDepartment[2].toString());
			}else if (tDepartment[3]!=null) {
				to.setTeacherName(tDepartment[3].toString());
			}
			if(tDepartment[2] != null || tDepartment[3] != null){
			to.setDeptTeacherName(to.getTeacherName().concat("  ("+to.getDepartmentName()+")"));
			teacherMap.put(to.getId(), to.getDeptTeacherName());
			}
			
			
			
		}
	
	teacherMap = (Map<Integer, String>) CommonUtil.sortMapByValue(teacherMap);
	return teacherMap;
		}
public static void setBotoForm(TeacherDepartmentEntryForm teacherDepartmentForm,TeacherDepartment tDepartment){
	if(tDepartment!=null){
		teacherDepartmentForm.setTeacher(Integer.toString(tDepartment.getTeacherId().getId()));
		teacherDepartmentForm.setDepartment(Integer.toString(tDepartment.getDepartmentId().getId()));
		
	}
}
public TeacherDepartment convertFormToBo(TeacherDepartmentEntryForm teacherDepartmentForm){
	TeacherDepartment teacherDep=new TeacherDepartment();
	Users users=new Users();
	if(teacherDepartmentForm.getTeacher()!=null)
	users.setId(Integer.parseInt(teacherDepartmentForm.getTeacher()));
	Department department=new Department();
	if(teacherDepartmentForm.getDepartment()!=null)
	department.setId(Integer.parseInt(teacherDepartmentForm.getDepartment()));
	teacherDep.setTeacherId(users);
	teacherDep.setDepartmentId(department);
	teacherDep.setId(teacherDepartmentForm.getId());
	return teacherDep;
}


public static Map<Integer, String> getTeacherDepartmentsNameFilter(List<Object[]> teacherDepartmentList, List<Object[]> teacherDepartmentList2)throws Exception {
	Map<Integer, String> teacherMap = new LinkedHashMap<Integer, String>();
	//List<TeacherDepartmentTO> toList=new ArrayList<TeacherDepartmentTO>();
	Iterator<Object[]> itr=teacherDepartmentList.iterator();
	while(itr.hasNext()){
		Object[] tDepartment=(Object[])itr.next();
		TeacherDepartmentTO to=new TeacherDepartmentTO();
		
		to.setId(Integer.parseInt(tDepartment[0].toString()));
		//int userIdEmpl=(Integer.parseInt(tDepartment[0].toString()));
			if (tDepartment[1] != null ) 
			{
				if(!tDepartment[1].toString().isEmpty())
				{
					to.setDepartmentName(tDepartment[1].toString());
						
				}
			}
			else if (tDepartment[1] == null)
			{
					
			to.setDepartmentName("-");
				
			}
	
			to.setTeacherName(tDepartment[2].toString());
			to.setDeptTeacherName(to.getTeacherName().concat("  ("+to.getDepartmentName()+")"));
			teacherMap.put(to.getId(), to.getDeptTeacherName());
	}
		if(teacherDepartmentList2 != null)
			{
			Iterator<Object[]> itr1=teacherDepartmentList2.iterator();
			while(itr1.hasNext()){
				Object[] tDepartment1=(Object[])itr1.next();
				TeacherDepartmentTO to1=new TeacherDepartmentTO();
				
				to1.setId(Integer.parseInt(tDepartment1[0].toString()));
			//	int userIdEmpl1=(Integer.parseInt(tDepartment1[0].toString()));
					if (tDepartment1[1] != null ) 
					{
						if(!tDepartment1[1].toString().isEmpty())
						{
							to1.setDepartmentName(tDepartment1[1].toString());
								
						}
					}
					else if (tDepartment1[1] == null)
					{
							
					to1.setDepartmentName("-");
						
					}
					
					to1.setTeacherName(tDepartment1[2].toString());
					to1.setDeptTeacherName(to1.getTeacherName().concat("  ("+to1.getDepartmentName()+")"));
					teacherMap.put(to1.getId(), to1.getDeptTeacherName());
		}
		}
	teacherMap = (Map<Integer, String>) CommonUtil.sortMapByValue(teacherMap);
	return teacherMap;
		}

}
