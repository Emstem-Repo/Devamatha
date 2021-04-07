package com.kp.cms.transactions.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.forms.admin.AssignPeersGroupsForm;

public interface IAssignPeersGroupsTransaction {

	public List<PeersEvaluationGroups> getPeersEvaluationGroups()throws  Exception;

	public boolean saveFaculty(List<AssignPeersGroups> assignPeersGroups)throws Exception;

	public boolean checkFacultyDuplicatesForAdd( String facId, List<String> duplicateList, AssignPeersGroupsForm assignPeersGroupsForm)throws Exception;

	public List<AssignPeersGroups> getAssignPeersToGroups()throws Exception;

	public List<AssignPeersGroups> editAssignPeersGrps(AssignPeersGroupsForm assignPeersGroupsForm)throws Exception;

	public boolean deleteAssignPeersToGroups( AssignPeersGroupsForm assignPeersGroupsForm)throws Exception;

	public boolean checkFacultyDuplicatesForForUpdate(String facId, List<String> duplicateList, AssignPeersGroupsForm assignPeersGroupsForm)throws Exception;

	public boolean updateFaculty(List<AssignPeersGroups> assignPeersGroups)throws Exception;


}
