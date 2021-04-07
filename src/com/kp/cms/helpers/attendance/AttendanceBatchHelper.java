package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.forms.attendance.AttendanceBatchForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.BatchStudentTO;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;

public class AttendanceBatchHelper {
	/**
	 * Singleton object of AttendanceBatchHelper
	 */
	private static volatile AttendanceBatchHelper attendanceBatchHelper = null;
	private static final Log log = LogFactory.getLog(AttendanceBatchHelper.class);
	private AttendanceBatchHelper() {
		
	}
	/**
	 * return singleton object of AttendanceBatchHelper.
	 * @return
	 */
	public static AttendanceBatchHelper getInstance() {
		if (attendanceBatchHelper == null) {
			attendanceBatchHelper = new AttendanceBatchHelper();
		}
		return attendanceBatchHelper;
	}
	/**
	 * 
	 * @param allBatchBODetailsList
	 * Used in view part. Displays all batch for the class and subject details in UI
	 * Used in edit practical batch section
	 * @return
	 */
	public List<CreatePracticalBatchTO> copyAllBatchBOToTO(List<Batch> allBatchBODetailsList,Set<Integer> classesIdsSet)throws Exception{
		log.info("Entering into copyAllBatchBOToTO of CreatePracticalBatchHelper");
		List<CreatePracticalBatchTO> batchTOList=new ArrayList<CreatePracticalBatchTO>();
		CreatePracticalBatchTO batchTO;
		BatchStudentTO batchStudentTO;
		StudentTO studentTO;
		ClassesTO classesTO;
		SubjectTO subjectTO;
		ClassSchemewiseTO classSchemewiseTO;
		Batch batch;
		BatchStudent batchStudent;
		Set<BatchStudent> batchStudentBOSet;
		if(allBatchBODetailsList !=null && !allBatchBODetailsList.isEmpty()){
			Iterator<Batch> iterator=allBatchBODetailsList.iterator();
			while (iterator.hasNext()) {
				batch = iterator.next();				
				batchTO = new CreatePracticalBatchTO();
				batchTO.setId(batch.getId());
				batchTO.setBatchName(batch.getBatchName()!=null ? batch.getBatchName():null);
				
//				classSchemewiseTO = new ClassSchemewiseTO();
//				if(batch.getClassSchemewise()!=null && batch.getClassSchemewise().getId()!=0){
//				classSchemewiseTO.setId(batch.getClassSchemewise().getId());
//				}
//				
//				classesTO = new ClassesTO();
//				if(batch.getClassSchemewise()!=null && batch.getClassSchemewise().getClasses()!=null && batch.getClassSchemewise().getClasses().getName()!=null){
//				classesTO.setClassName(batch.getClassSchemewise().getClasses().getName());
//				}
//				classSchemewiseTO.setClassesTo(classesTO);
//				batchTO.setClassSchemewiseTO(classSchemewiseTO);
				
				List<String> classNameList=new ArrayList<String>();
				studentTO=new StudentTO();
				batchStudentTO = new BatchStudentTO();
				subjectTO = new SubjectTO();
				if(batch.getSubject()!=null && batch.getSubject().getId()!=0){
				subjectTO.setId(batch.getSubject().getId());
				}
				if(batch.getSubject()!=null && batch.getSubject().getName()!=null){
				subjectTO.setName(batch.getSubject().getName());
				}
				batchTO.setSubjectTO(subjectTO);
				
				batchStudentBOSet = batch.getBatchStudents();
				List<String> list = new ArrayList<String>();	
				List<BatchStudentTO> batchStudentTOList = new ArrayList<BatchStudentTO>();
				if(batchStudentBOSet!=null && !batchStudentBOSet.isEmpty()){
					Iterator<BatchStudent> iterator1 = batchStudentBOSet.iterator();
						while (iterator1.hasNext()) {
						batchStudent = iterator1.next();
						if(batchStudent.getIsActive()!=null && batchStudent.getIsActive() && !batchStudent.getStudent().getAdmAppln().getIsCancelled() && (batchStudent.getStudent().getIsHide()==null || !batchStudent.getStudent().getIsHide())){
						batchStudentTO.setId(batchStudent.getId());
						if(classesIdsSet.contains(batchStudent.getClassSchemewise().getId())){
							if(!classNameList.contains(batchStudent.getClassSchemewise().getClasses().getName())){
								classNameList.add(batchStudent.getClassSchemewise().getClasses().getName());
							}
														
							if(batchStudent.getStudent()!=null && batchStudent.getStudent().getIsActive()!=null && 
							batchStudent.getStudent().getIsActive() && batchStudent.getStudent().getIsAdmitted()!=null
							&& batchStudent.getStudent().getIsAdmitted() && batchStudent.getStudent().getAdmAppln()!=null &&
							!batchStudent.getStudent().getAdmAppln().getIsCancelled()){
								//raghu
								if(batchStudent.getStudent().getRegisterNo()!=null){
									list.add(batchStudent.getStudent().getRegisterNo());
									
								}
								Collections.sort(list);
							}
						}
					}
				}
						Iterator<String> itr = list.iterator();
						String regNo = "";
						while(itr.hasNext()){
							String regNo1=itr.next();
							if(regNo.equals("")){
								regNo =regNo1;
							}
							else{
								regNo = regNo+", "+regNo1;
							}
						}
						studentTO.setRegisterNo(regNo);
						}
						batchStudentTO.setStudentTO(studentTO);
						batchStudentTOList.add(batchStudentTO);
				classSchemewiseTO = new ClassSchemewiseTO();
				classesTO = new ClassesTO();
				Iterator<String> classItr=classNameList.iterator();
				String className="";
				while (classItr.hasNext()) {
					if(className.isEmpty())
						className=classItr.next();
					else
						className=className+","+classItr.next();
				}
				classesTO.setClassName(className);
				classSchemewiseTO.setClassesTo(classesTO);
				batchTO.setClassSchemewiseTO(classSchemewiseTO);
				batchTO.setBatchStudentTOList(batchStudentTOList);
				batchTOList.add(batchTO);
			}
		}
		
		log.info("Leaving into copyAllBatchBOToTO of CreatePracticalBatchHelper");
		return batchTOList;
	}
	/**
	 * @param attendanceBatchForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentsByClassQuery(AttendanceBatchForm attendanceBatchForm) throws Exception {
		String[] classIds=attendanceBatchForm.getClassesId();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<classIds.length;i++){
			intType.append(classIds[i]);
			 if(i<(classIds.length-1)){
				 intType.append(",");
			 }
		}
		String query="from Student s where s.admAppln.isCancelled=0 and (s.isHide = 0 or s.isHide is null) and s.classSchemewise.classes.isActive = 1 and s.isActive = 1" +
					 " and s.classSchemewise.id in (" +intType+") ";
		if(attendanceBatchForm.getRegNoFrom()!=null && !attendanceBatchForm.getRegNoFrom().isEmpty()){
			query=query+" and s.registerNo >='"+attendanceBatchForm.getRegNoFrom()+"'";
		}
		if(attendanceBatchForm.getRegNoTo()!=null && !attendanceBatchForm.getRegNoTo().isEmpty()){
			query=query+" and s.registerNo <='"+attendanceBatchForm.getRegNoTo()+"'";
		}
		return query+" order by s.registerNo";
	}
	/**
	 * @param studentBoList
	 * @return
	 * @throws Exception
	 */
	public List<CreatePracticalBatchTO> convertBotoTO(List<Student> studentBoList,AttendanceBatchForm attendanceBatchForm,Map<Integer,Integer> existMap) throws Exception {
		boolean isReq=false;
		if(attendanceBatchForm.getSubjectId()!=null && !attendanceBatchForm.getSubjectId().isEmpty()){
			isReq=true;
		}
		int count=0;
		int count1=0;
		List<CreatePracticalBatchTO> toList=new ArrayList<CreatePracticalBatchTO>();
		if(studentBoList!=null && !studentBoList.isEmpty()){
			Iterator<Student> itr=studentBoList.iterator();
			while (itr.hasNext()) {
				Student student = itr.next();
				CreatePracticalBatchTO createPracticalBatchTO = new CreatePracticalBatchTO();
				boolean isCheck=false;
				//Get the subjectGroup List for the student
				Set<ApplicantSubjectGroup> subGrpList=null;
				if(student.getAdmAppln()!=null && student.getAdmAppln().getApplicantSubjectGroups()!=null){
				subGrpList = student.getAdmAppln().getApplicantSubjectGroups();
				}
				if(isReq){
					isCheck=checkSubjectPresentInGroup(Integer.parseInt(attendanceBatchForm.getSubjectId()),subGrpList);
					if(isCheck){
						count1++;
					}
				}else{
					isCheck=true;
					count1++;
					
				}
				
				if(isCheck){
					StudentTO studentTO=new StudentTO();
					studentTO.setId(student.getId());
					studentTO.setRegisterNo(student.getRegisterNo());
					studentTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					createPracticalBatchTO.setStudentTO(studentTO);
					ClassSchemewiseTO classSchemewiseTO=new ClassSchemewiseTO();
					classSchemewiseTO.setId(student.getClassSchemewise().getId());
					createPracticalBatchTO.setClassSchemewiseTO(classSchemewiseTO);
					createPracticalBatchTO.setCheckValue(false);
					if(existMap.containsKey(student.getId())){
						createPracticalBatchTO.setDummyCheckValue(true);
						createPracticalBatchTO.setTempCheckValue(true);
						createPracticalBatchTO.setId(existMap.get(student.getId()));
						count++;
					}else{
						createPracticalBatchTO.setDummyCheckValue(false);
						createPracticalBatchTO.setTempCheckValue(false);
					}
					toList.add(createPracticalBatchTO);
				}
			}
			if(count1==count){
				attendanceBatchForm.setCheckBoxSelectAll(true);
			}else
				attendanceBatchForm.setCheckBoxSelectAll(false);
		}
		return toList;
	}
	/**
	 * @param subjectId
	 * @param subGrpList
	 * @return
	 */
	private boolean checkSubjectPresentInGroup(int subjectId,
			Set<ApplicantSubjectGroup> subGrpList) {
	log.info("Entering into checkSubjectPresentInGroup of CreatePracticalBatchHandler");
		if(subGrpList.isEmpty()){
			return false;
		}
		boolean result = false;
		ApplicantSubjectGroup applicantSubjectGroup;
		SubjectGroupSubjects subjectGroupSubjects ;
		Iterator<ApplicantSubjectGroup> itr = subGrpList.iterator();
		label :while(itr.hasNext()) {
			applicantSubjectGroup = itr.next();
			if(applicantSubjectGroup.getSubjectGroup()!= null && applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses()!= null){
			Iterator<SubjectGroupSubjects> itr1 = applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses().iterator();
			while(itr1.hasNext()) {
				subjectGroupSubjects = itr1.next();
					if(subjectGroupSubjects.getSubject() != null && subjectGroupSubjects.getSubject().getId() == subjectId) {
						result = true;
						continue label;
					}					
			}
		}
		}
		log.info("Leaving into checkSubjectPresentInGroup of CreatePracticalBatchHandler");
		return result;		
	}
	
	
	/**
	 * @param attendanceBatchForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentsByClassQueryForEdit(AttendanceBatchForm attendanceBatchForm) throws Exception {
		String[] classIds=attendanceBatchForm.getClassesId();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<classIds.length;i++){
			intType.append(classIds[i]);
			 if(i<(classIds.length-1)){
				 intType.append(",");
			 }
		}
		String query="from Student s where s.admAppln.isCancelled=0 and (s.isHide = 0 or s.isHide is null) and s.classSchemewise.classes.isActive = 1 and s.isActive = 1" +
					 " and s.classSchemewise.id in (" +intType+") order by s.registerNo";
		return query;
	}
	/**
	 * @param attendanceBatchForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentsExistInAnotherBatch(AttendanceBatchForm attendanceBatchForm) throws Exception  {
		String[] classIds=attendanceBatchForm.getClassesId();
		StringBuilder intType =new StringBuilder();
		for(int i=0;i<classIds.length;i++){
			intType.append(classIds[i]);
			 if(i<(classIds.length-1)){
				 intType.append(",");
			 }
		}
		String query="select bs.student.id,bs.student.admAppln.personalData.firstName" +
				" from BatchStudent bs" +
				" where bs.isActive=1 and bs.batch.isActive=1" +
				" and bs.classSchemewise.id in ("+intType+")" ;
		if(attendanceBatchForm.getSubjectId()!=null && !attendanceBatchForm.getSubjectId().isEmpty()){
			query=query+" and bs.batch.subject.id="+attendanceBatchForm.getSubjectId();
		}
		if(attendanceBatchForm.getActivityAttendance()!=null && !attendanceBatchForm.getActivityAttendance().isEmpty()){
			query=query+" and bs.batch.activity.id="+attendanceBatchForm.getActivityAttendance();
		}
		if(attendanceBatchForm.getBatchId()!=null && !attendanceBatchForm.getBatchId().isEmpty()){
			query=query+" and bs.batch.id !="+attendanceBatchForm.getBatchId();
		}
		return query;
	}
}
