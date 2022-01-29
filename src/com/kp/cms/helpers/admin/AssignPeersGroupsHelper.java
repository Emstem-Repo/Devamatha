package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.admin.AssignPeersGroupsForm;
import com.kp.cms.to.admin.AssignPeersGroupsTo;


public class AssignPeersGroupsHelper {
	public static volatile AssignPeersGroupsHelper helper = null;
	private AssignPeersGroupsHelper(){
		
	}
	public static AssignPeersGroupsHelper getInstance(){
		if(helper == null){
			helper = new AssignPeersGroupsHelper();
			return helper ;
		}
		return helper;
	}
	/**
	 * @param assignPeersGroupsForm
	 * @return
	 * @throws Exception
	 */
	public List<AssignPeersGroups> convertFormToBO( AssignPeersGroupsForm assignPeersGroupsForm) throws Exception{
		List<AssignPeersGroups> list = new ArrayList<AssignPeersGroups>();
		AssignPeersGroups assignPeersGroups=null;
		String[] empIds = assignPeersGroupsForm.getEmpIds();
		for(int i=0;i<empIds.length;i++){
			String empId = empIds[i];
				assignPeersGroups= new AssignPeersGroups();
				if(assignPeersGroupsForm.getDepartmentId()!=null && !assignPeersGroupsForm.getDepartmentId().isEmpty()){
					Department department = new Department();
					department.setId(Integer.parseInt(assignPeersGroupsForm.getDepartmentId()));
					assignPeersGroups.setDepartment(department);
				}
				if(empId!=null && !empId.isEmpty()){
					Users users = new Users();
					users.setId(Integer.parseInt(empId));
					assignPeersGroups.setUsers(users);
				}
				if(assignPeersGroupsForm.getGroupId()!=null && !assignPeersGroupsForm.getGroupId().isEmpty()){
					PeersEvaluationGroups groups = new PeersEvaluationGroups();
					groups.setId(Integer.parseInt(assignPeersGroupsForm.getGroupId()));
					assignPeersGroups.setPeersEvaluationGroups(groups);
				}
				assignPeersGroups.setCreatedBy(assignPeersGroupsForm.getUserId());
				assignPeersGroups.setCreatedDate(new Date());
				assignPeersGroups.setIsActive(true);
			
			list.add(assignPeersGroups);
		}
		return list;
	}
	/**
	 * @param assignPeersGroupsTos
	 * @return
	 * @throws Exception
	 */
	public List<AssignPeersGroupsTo> convertPeersGrpsBoTOTo( List<AssignPeersGroups> assignPeersGroupsList) throws Exception{
		List<AssignPeersGroupsTo> peersGroupList =new ArrayList<AssignPeersGroupsTo>();
		if(assignPeersGroupsList!=null && !assignPeersGroupsList.isEmpty()){
			Iterator<AssignPeersGroups> iterator = assignPeersGroupsList.iterator();
			while (iterator.hasNext()) {
				AssignPeersGroups assignPeersGroups = (AssignPeersGroups) iterator .next();
				AssignPeersGroupsTo to = new AssignPeersGroupsTo();
				if(assignPeersGroups!=null && !assignPeersGroups.toString().isEmpty()){
					to.setId(assignPeersGroups.getId());
					to.setDepartmentId(String.valueOf(assignPeersGroups.getDepartment().getId()));
					to.setDepartmentName(assignPeersGroups.getDepartment().getName());
					to.setGroupId(assignPeersGroups.getPeersEvaluationGroups().getId());
					to.setGroupName(assignPeersGroups.getPeersEvaluationGroups().getName());
					peersGroupList.add(to);
				}
			}
			}
		return peersGroupList;
	}
	/**
	 * @param assignPeersGroupsForm
	 * @return
	 * @throws Exception
	 */
	public List<AssignPeersGroups> convertFormToBO1( AssignPeersGroupsForm assignPeersGroupsForm) throws Exception{
		List<AssignPeersGroups> list = new ArrayList<AssignPeersGroups>();
		Map<Integer,Integer> map = assignPeersGroupsForm.getAssignPeersIds();
		String[] empIds = assignPeersGroupsForm.getEmpIds();
		for(int i=0;i<empIds.length;i++){
			String empId = empIds[i];
				if(map.containsKey(Integer.parseInt(empId))){
					AssignPeersGroups assignPeersGroups =new AssignPeersGroups();
					int id =assignPeersGroupsForm.getAssignPeersIds().get(Integer.parseInt(empId));
					assignPeersGroups.setId(id);
					if(assignPeersGroupsForm.getDepartmentId()!=null && !assignPeersGroupsForm.getDepartmentId().isEmpty()){
						Department department = new Department();
						department.setId(Integer.parseInt(assignPeersGroupsForm.getDepartmentId()));
						assignPeersGroups.setDepartment(department);
					}
					if(empId!=null && !empId.isEmpty()){
						Users users = new Users();
						users.setId(Integer.parseInt(empId));
						assignPeersGroups.setUsers(users);
					}
					if(assignPeersGroupsForm.getGroupId()!=null && !assignPeersGroupsForm.getGroupId().isEmpty()){
						PeersEvaluationGroups groups = new PeersEvaluationGroups();
						groups.setId(Integer.parseInt(assignPeersGroupsForm.getGroupId()));
						assignPeersGroups.setPeersEvaluationGroups(groups);
					}
					assignPeersGroups.setLastModifiedDate(new Date());
					assignPeersGroups.setModifiedBy(assignPeersGroupsForm.getUserId());
					assignPeersGroups.setIsActive(true);
					map.remove(Integer.parseInt(empId));
					
				list.add(assignPeersGroups);
				}else{
					AssignPeersGroups assignPeersGroups1 =new AssignPeersGroups();
					if(assignPeersGroupsForm.getDepartmentId()!=null && !assignPeersGroupsForm.getDepartmentId().isEmpty()){
						Department department = new Department();
						department.setId(Integer.parseInt(assignPeersGroupsForm.getDepartmentId()));
						assignPeersGroups1.setDepartment(department);
					}
					if(empId!=null && !empId.isEmpty()){
						Users users = new Users();
						users.setId(Integer.parseInt(empId));
						assignPeersGroups1.setUsers(users);
					}
					if(assignPeersGroupsForm.getGroupId()!=null && !assignPeersGroupsForm.getGroupId().isEmpty()){
						PeersEvaluationGroups groups = new PeersEvaluationGroups();
						groups.setId(Integer.parseInt(assignPeersGroupsForm.getGroupId()));
						assignPeersGroups1.setPeersEvaluationGroups(groups);
					}
					assignPeersGroups1.setCreatedBy(assignPeersGroupsForm.getUserId());
					assignPeersGroups1.setCreatedDate(new Date());
					assignPeersGroups1.setIsActive(true);
					list.add(assignPeersGroups1);
				}
		}
		Map<Integer,Integer> map1 = map;
		Iterator<Map.Entry<Integer,Integer>> it = map1.entrySet().iterator();
		while (it.hasNext()) {
			AssignPeersGroups assignPeersGroups2 =new AssignPeersGroups();
			Map.Entry<Integer,Integer> pairs = it.next();
			int assignPeersId = Integer.parseInt(pairs.getValue().toString());
			assignPeersGroups2.setId(assignPeersId);
			//
			Department department = new Department();
			department.setId(Integer.parseInt(assignPeersGroupsForm.getDepartmentId()));
			assignPeersGroups2.setDepartment(department);
			//
			//
			int userId = Integer.parseInt(pairs.getKey().toString());
			Users users = new Users();
			users.setId(userId);
			assignPeersGroups2.setUsers(users);
			//
			PeersEvaluationGroups groups = new PeersEvaluationGroups();
			groups.setId(Integer.parseInt(assignPeersGroupsForm.getGroupId()));
			assignPeersGroups2.setPeersEvaluationGroups(groups);
			//
			
			assignPeersGroups2.setLastModifiedDate(new Date());
			assignPeersGroups2.setModifiedBy(assignPeersGroupsForm.getUserId());
			assignPeersGroups2.setIsActive(false);
			list.add(assignPeersGroups2);
		}
		return list;
	}
}
