package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.to.admission.AssignSubjectGroupHistoryTO;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;

public class AssignSubjectGroupHistoryHelper {
	private static final Log log = LogFactory .getLog(AssignSubjectGroupHistoryHelper.class);
 private static volatile AssignSubjectGroupHistoryHelper subjectGroupHistoryHelper = null;
 public static AssignSubjectGroupHistoryHelper getInstance(){
	 if(subjectGroupHistoryHelper == null){
		 subjectGroupHistoryHelper = new AssignSubjectGroupHistoryHelper();
		 return subjectGroupHistoryHelper;
	 }
	 return subjectGroupHistoryHelper;
 }
/**
 * @param classDetailsBOs
 * @param existMap
 * @return
 * @throws Exception
 */
public List<AssignSubjectGroupHistoryTO> populatePreviousClassBoToTO( List<StudentPreviousClassHistory> classDetailsBOs,Map<Integer,Integer> existMap)throws Exception {
	List<AssignSubjectGroupHistoryTO> groupHistoryTOs = new ArrayList<AssignSubjectGroupHistoryTO>();
	if(classDetailsBOs!=null){
		Iterator<StudentPreviousClassHistory> iterator= classDetailsBOs.iterator();
		while (iterator.hasNext()) {
			StudentPreviousClassHistory studentPreviousClassDetails= (StudentPreviousClassHistory) iterator .next();
			AssignSubjectGroupHistoryTO historyTO = new AssignSubjectGroupHistoryTO();
			historyTO.setId(studentPreviousClassDetails.getId());
			if(studentPreviousClassDetails.getId()!=0){
				historyTO.setStudentId(studentPreviousClassDetails.getStudent().getId());
			}
			if(existMap.containsKey(studentPreviousClassDetails.getStudent().getId())){
				historyTO.setStudentId(studentPreviousClassDetails.getStudent().getId());
				historyTO.setTempChecked("on");
			}
			if(studentPreviousClassDetails.getStudent().getRegisterNo()!=null && !studentPreviousClassDetails.getStudent().getRegisterNo().isEmpty()){
				historyTO.setRegNo(studentPreviousClassDetails.getStudent().getRegisterNo());
			}
			historyTO.setSemNo(studentPreviousClassDetails.getSchemeNo());
			historyTO.setStudentName(studentPreviousClassDetails.getStudent().getAdmAppln().getPersonalData().getFirstName());
			if(studentPreviousClassDetails.getStudent().getAdmAppln().getPersonalData()!=null && !studentPreviousClassDetails.getStudent().getAdmAppln().getPersonalData().toString().isEmpty()){
				if(studentPreviousClassDetails.getStudent().getAdmAppln().getPersonalData().getSecondLanguage()!=null && !studentPreviousClassDetails.getStudent().getAdmAppln().getPersonalData().getSecondLanguage().isEmpty()){
					historyTO.setSecondLanguage(studentPreviousClassDetails.getStudent().getAdmAppln().getPersonalData().getSecondLanguage());
				}
			}
			groupHistoryTOs.add(historyTO);
		}
	}
	Collections.sort(groupHistoryTOs);
	return groupHistoryTOs;
}
/**
 * @param subjectGroupmMap
 * @return
 * @throws Exception
 */
public List<SubjectGroupDetailsTo> convertBOToTO( Map<Integer, SubjectGroup> subjectGroupMap)throws Exception {
	List<SubjectGroupDetailsTo> list=new ArrayList<SubjectGroupDetailsTo>();
	if(subjectGroupMap !=null){
		Iterator<SubjectGroup> it=subjectGroupMap.values().iterator();
		while (it.hasNext()) {
			SubjectGroup subjectGroup = (SubjectGroup) it.next();
			SubjectGroupDetailsTo subjectGroupList=new SubjectGroupDetailsTo();
			subjectGroupList.setSubjectGroupId(subjectGroup.getId());
			subjectGroupList.setName(subjectGroup.getName());
			if(subjectGroup.getIsCommonSubGrp()){
				subjectGroupList.setCommonSubjectGroup("Common Subject Group");
				subjectGroupList.setSubjectGroupName(subjectGroupList.getName().concat("("+subjectGroupList.getCommonSubjectGroup()+")"));
			}else{
				subjectGroupList.setSubjectGroupName(subjectGroupList.getName());
			}
			list.add(subjectGroupList);
		}
	}
	return list;
}
/**
 * @param utilBOs
 * @return
 */
public List<SubjectGroupDetailsTo> populateSubGrpDetailsToTO( List<SubjectGroup> utilBOs) {
	List<SubjectGroupDetailsTo> subjectDetailsTos=new ArrayList<SubjectGroupDetailsTo>();
	if(utilBOs!=null){
		Iterator<SubjectGroup> iterator = utilBOs.iterator();
		while (iterator.hasNext()) {
			SubjectGroup subjectGroup = (SubjectGroup) iterator .next();
			SubjectGroupDetailsTo subjectGroupList=new SubjectGroupDetailsTo();
			subjectGroupList.setSubjectGroupId(subjectGroup.getId());
			subjectGroupList.setName(subjectGroup.getName());
			if(subjectGroup.getIsCommonSubGrp()){
				subjectGroupList.setCommonSubjectGroup("Common Subject Group");
				subjectGroupList.setSubjectGroupName(subjectGroupList.getName().concat("("+subjectGroupList.getCommonSubjectGroup()+")"));
			}else{
				subjectGroupList.setSubjectGroupName(subjectGroupList.getName());
			}
			subjectDetailsTos.add(subjectGroupList);
		}
	}
	return subjectDetailsTos;
}
}
