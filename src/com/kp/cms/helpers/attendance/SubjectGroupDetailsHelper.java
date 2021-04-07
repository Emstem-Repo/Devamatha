package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.forms.attendance.SubjectGroupDetailsForm;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.transactionsimpl.admission.StudentEditTransactionImpl;

public class SubjectGroupDetailsHelper {
	private static final Log log = LogFactory
	.getLog(StudentEditTransactionImpl.class);
	public static SubjectGroupDetailsHelper subjectGroupDetailsHelper=null;
	static{
		subjectGroupDetailsHelper=new SubjectGroupDetailsHelper();
	}
	public static SubjectGroupDetailsHelper getInstance(){
		return subjectGroupDetailsHelper;
	}
	
	/**This method is used to convert BO to TO
	 * @param studentDetails
	 * @param studentWiseSpecializationMap 
	 * @return
	 */
	public List<SubjectGroupDetailsTo> convertBoToTO(List<Student> studentDetails,Map<Integer,Integer> existMap, Map<Integer, String> studentWiseSpecializationMap){
	List<SubjectGroupDetailsTo> subjectGroupDetailsToList=new ArrayList<SubjectGroupDetailsTo>();
	try{
		Iterator<Student> it= studentDetails.iterator();
		while(it.hasNext()){
			SubjectGroupDetailsTo subjectGroupDetailsTo=new SubjectGroupDetailsTo();
			Student student=it.next();
			if(existMap.containsKey(student.getId())){
				subjectGroupDetailsTo.setAppSubId(existMap.get(student.getId()));
				subjectGroupDetailsTo.setTempChecked("on");
			}
			if(studentWiseSpecializationMap.containsKey(student.getId())){
				subjectGroupDetailsTo.setSpecializationName(studentWiseSpecializationMap.get(student.getId()));
			}
			subjectGroupDetailsTo.setStudentId(student.getId());
			subjectGroupDetailsTo.setRegisterNo(student.getRegisterNo());
			subjectGroupDetailsTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			subjectGroupDetailsTo.setAdmAppln(student.getAdmAppln().getId());
			if(student.getAdmAppln().getPersonalData()!=null && !student.getAdmAppln().getPersonalData().toString().isEmpty()){
				if(student.getAdmAppln().getPersonalData().getSecondLanguage()!=null && !student.getAdmAppln().getPersonalData().getSecondLanguage().isEmpty()){
					subjectGroupDetailsTo.setSecondlanguage(student.getAdmAppln().getPersonalData().getSecondLanguage());
				}
			}
			subjectGroupDetailsToList.add(subjectGroupDetailsTo);
			Collections.sort(subjectGroupDetailsToList);
		}
	}catch (Exception exception) {
		log.error("Error while converting  student details...", exception);
	}
	
	return subjectGroupDetailsToList;
	}
	
	
	/**This method is used to convert BO to TO
	 * @param appGroup
	 * @return
	 * @throws Exception
	 */
	public static List<SubjectGroupDetailsTo> convertBoToTo(
			List<ApplicantSubjectGroup> appGroup) throws Exception {
		List<SubjectGroupDetailsTo> subjectGroupToList = new ArrayList<SubjectGroupDetailsTo>();
		if (appGroup != null) {
			java.util.Iterator<ApplicantSubjectGroup> appGroupIterator = appGroup.iterator();
			while (appGroupIterator.hasNext()) {
				ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appGroupIterator.next();
				SubjectGroupDetailsTo subjectGroupList=new SubjectGroupDetailsTo();
				subjectGroupList.setSubjectGroupName(applicantSubjectGroup.getSubjectGroup().getName());
				subjectGroupToList.add(subjectGroupList);
			}
		}
		return subjectGroupToList;
	}
	
	/**
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public static List<SubjectGroupDetailsTo> convertBoToTo1(
			List<SubjectGroup> subjectGroup) throws Exception {
		List<SubjectGroupDetailsTo> subjectGroupToList = new ArrayList<SubjectGroupDetailsTo>();
		if (subjectGroup != null) {
			java.util.Iterator<SubjectGroup> subjectgroupIterator = subjectGroup.iterator();
			while (subjectgroupIterator.hasNext()) {
				SubjectGroup subjectGroupName=(SubjectGroup) subjectgroupIterator.next();
				SubjectGroupDetailsTo subjectGroupList=new SubjectGroupDetailsTo();
				subjectGroupList.setSubjectGroupId(subjectGroupName.getId());
				subjectGroupList.setName(subjectGroupName.getName());
				if(subjectGroupName.getIsCommonSubGrp()){
					subjectGroupList.setCommonSubjectGroup("Common Subject Group");
					subjectGroupList.setSubjectGroupName(subjectGroupList.getName().concat("("+subjectGroupList.getCommonSubjectGroup()+")"));
				}else{
					subjectGroupList.setSubjectGroupName(subjectGroupList.getName());
				}
				subjectGroupToList.add(subjectGroupList);
			}
		}
		return subjectGroupToList;
	}

	public static  List<SubjectGroupDetailsTo> convertBOToTO(
			 Map<Integer,SubjectGroup> getsubjectGroups, SubjectGroupDetailsForm subjectGroupDetailsForm)throws Exception {
		List<SubjectGroupDetailsTo> subjectGroupMap=new ArrayList<SubjectGroupDetailsTo>();
		if(getsubjectGroups !=null){
			Iterator<SubjectGroup> it=getsubjectGroups.values().iterator();
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
				subjectGroupMap.add(subjectGroupList);
			}
		}
		return subjectGroupMap;
	}

	/**
	 * @param specializationBOs
	 * @return
	 * @throws Exception
	 */
	public static List<ExamSpecializationTO> convertExamSpecializationBOToTO( List<ExamSpecializationBO> specializationBOs) throws Exception{
		List<ExamSpecializationTO> list = new ArrayList<ExamSpecializationTO>();
		if(specializationBOs!=null){
			Iterator<ExamSpecializationBO> iterator = specializationBOs.iterator();
			while (iterator.hasNext()) {
				ExamSpecializationBO examSpecializationBO = (ExamSpecializationBO) iterator .next();
				ExamSpecializationTO specializationTO = new ExamSpecializationTO();
				specializationTO.setId(examSpecializationBO.getId());
				specializationTO.setName(examSpecializationBO.getName());
				list.add(specializationTO);
			}
		}
		return list;
	}

}
