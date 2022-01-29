package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.forms.admin.AssignPeersGroupsForm;
import com.kp.cms.helpers.admin.AssignPeersGroupsHelper;
import com.kp.cms.to.admin.AssignPeersGroupsTo;
import com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction;
import com.kp.cms.transactionsimpl.admin.AssignPeersGroupsTransactionImpl;

public class AssignPeersGroupsHandlers {
	public static volatile AssignPeersGroupsHandlers handlers = null;
	private AssignPeersGroupsHandlers(){
		
	}
	public static AssignPeersGroupsHandlers getInstance(){
		if(handlers == null){
			handlers = new AssignPeersGroupsHandlers();
			return handlers;
		}
		return handlers;
	}
	IAssignPeersGroupsTransaction transaction = AssignPeersGroupsTransactionImpl.getInstance();
	/**
	 * @param assignPeersGroupsForm
	 * @param session 
	 * @return
	 * @throws Exception
	 */
	public boolean addFacultyByDepartmentToGroups( AssignPeersGroupsForm assignPeersGroupsForm, HttpSession session) throws Exception{
		List<AssignPeersGroups> assignPeersGroups = AssignPeersGroupsHelper.getInstance().convertFormToBO(assignPeersGroupsForm);
		boolean isAdded = transaction.saveFaculty(assignPeersGroups);
		return isAdded;
	}
	/**
	 * @param assignPeersGroupsForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkFacultyDuplicatesForAdd( AssignPeersGroupsForm assignPeersGroupsForm) throws Exception{
		boolean isDuplicate = false;
		List<String> duplicateList = new ArrayList<String>();
		//here we are getting empids are userids.
		String[] facultyIds = assignPeersGroupsForm.getEmpIds();       
		//
		for(int i = 0;i<facultyIds.length;i++){
			String facId = facultyIds[i];
			 isDuplicate = transaction.checkFacultyDuplicatesForAdd(facId,duplicateList,assignPeersGroupsForm);
		}
		return isDuplicate;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<AssignPeersGroupsTo> getAssignPeersToGroups()throws Exception {
		List<AssignPeersGroups> assignPeersGroupsList = transaction.getAssignPeersToGroups();
		List<AssignPeersGroupsTo> peersGroupList = AssignPeersGroupsHelper.getInstance().convertPeersGrpsBoTOTo(assignPeersGroupsList);
		return peersGroupList;
	}
	
	
	/**
	 * @param assignPeersGroupsForm
	 * @param session 
	 */
	public void editAssignPeersGroups( AssignPeersGroupsForm assignPeersGroupsForm, HttpSession session)throws Exception { 
		
		List<AssignPeersGroups> assignPeersGroupsBo = transaction.editAssignPeersGrps(assignPeersGroupsForm);
		assignPeersGroupsForm.setAssignPeersIds(null);
		if(assignPeersGroupsBo!=null && !assignPeersGroupsBo.isEmpty()){
			String[] empIds = new String[assignPeersGroupsBo.size()];
			Map<Integer,Integer> assignPeersGroupIds = new HashMap<Integer,Integer>();
			for (int i = 0; i < assignPeersGroupsBo.size(); i++) {
				AssignPeersGroups assignPeersGroups = (AssignPeersGroups)assignPeersGroupsBo.get(i);
				if(assignPeersGroups.getUsers()!=null && !assignPeersGroups.getUsers().toString().isEmpty()){
					String userId = String.valueOf(assignPeersGroups.getUsers().getId());
					empIds[i] = userId;
					assignPeersGroupIds.put(Integer.parseInt(userId),assignPeersGroups.getId());
				}
			}
			assignPeersGroupsForm.setEmpIds(empIds);
			assignPeersGroupsForm.setAssignPeersIds(assignPeersGroupIds);
			session.setAttribute("EMPIDS", empIds);
		}
		
	}
	/**
	 * @param assignPeersGroupsForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAssignPeersToGroups( AssignPeersGroupsForm assignPeersGroupsForm) throws Exception{
		boolean isDelete = transaction.deleteAssignPeersToGroups(assignPeersGroupsForm);
		return isDelete;
	}
	/**
	 * @param assignPeersGroupsForm
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	public boolean updateFacultyByDepartmentToGroups( AssignPeersGroupsForm assignPeersGroupsForm, HttpSession session) throws Exception {
		List<AssignPeersGroups> assignPeersGroups = AssignPeersGroupsHelper.getInstance().convertFormToBO1(assignPeersGroupsForm);
		boolean isAdded = transaction.updateFaculty(assignPeersGroups);
		return isAdded;
	}
	/**
	 * @param assignPeersGroupsForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkFacultyDuplicatesForUpdate( AssignPeersGroupsForm assignPeersGroupsForm)throws Exception {
		boolean isDuplicate = false;
		List<String> duplicateList = new ArrayList<String>();
		//here we are getting empids are userids.
		String[] facultyIds = assignPeersGroupsForm.getEmpIds();       
		//
		for(int i = 0;i<facultyIds.length;i++){
			String facId = facultyIds[i];
			 isDuplicate = transaction.checkFacultyDuplicatesForForUpdate(facId,duplicateList,assignPeersGroupsForm);
		}
		return isDuplicate;
	}

}
