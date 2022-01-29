package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.exam.StudentSubjectGroupHistory;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.PreviousClassDetailsTO;

public class StudentClassAndSubjectDetailsHelper {
	/**
	 * Singleton object of StudentClassAndSubjectDetailsHelper
	 */
	private static volatile StudentClassAndSubjectDetailsHelper studentClassAndSubjectDetailsHelper = null;
	private static final Log log = LogFactory.getLog(StudentClassAndSubjectDetailsHelper.class);
	private StudentClassAndSubjectDetailsHelper() {
		
	}
	/**
	 * return singleton object of StudentClassAndSubjectDetailsHelper.
	 * @return
	 */
	public static StudentClassAndSubjectDetailsHelper getInstance() {
		if (studentClassAndSubjectDetailsHelper == null) {
			studentClassAndSubjectDetailsHelper = new StudentClassAndSubjectDetailsHelper();
		}
		return studentClassAndSubjectDetailsHelper;
	}
	/**
	 * converting studentBO obtained from database to required StudentTO
	 * @param stuBo
	 * @return
	 * @throws Exception
	 */
	public StudentTO convertStuBOToStuTO(Student stuBo) throws Exception{
		StudentTO to=null;
		SubjectTO subTo=null;
		Map<String, List<SubjectTO>> subMap= new HashMap<String, List<SubjectTO>>();
		
		if(stuBo!=null){
			to=new StudentTO();	
			if(stuBo.getAdmAppln()!=null && stuBo.getAdmAppln().getPersonalData()!=null 
			&& stuBo.getAdmAppln().getPersonalData().getFirstName()!=null && !stuBo.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
				to.setStudentName(stuBo.getAdmAppln().getPersonalData().getFirstName());
			}
			if(stuBo.getClassSchemewise()!=null && stuBo.getClassSchemewise().getClasses()!=null ){
			to.setClassName(stuBo.getClassSchemewise().getClasses().getName());
			}
			if(stuBo.getAdmAppln().getApplicantSubjectGroups()!=null && !stuBo.getAdmAppln().getApplicantSubjectGroups().isEmpty()){
				Set<ApplicantSubjectGroup> setApplnSub= stuBo.getAdmAppln().getApplicantSubjectGroups();
				Iterator<ApplicantSubjectGroup> itr = setApplnSub.iterator();
				while (itr.hasNext()) {
					List<SubjectTO> subjectList= new ArrayList<SubjectTO>();
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) itr.next();
					Set<SubjectGroupSubjects> subjectsSet= applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses();
					Iterator<SubjectGroupSubjects> subItr= subjectsSet.iterator();
					while (subItr.hasNext()) {
						SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr.next();
						if(subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()){
							subTo= new SubjectTO();
							subTo.setSubjectName(subjectGroupSubjects.getSubject().getName());
						subjectList.add(subTo);
						}
					}
					subMap.put(applicantSubjectGroup.getSubjectGroup().getName(),subjectList);
				}
				to.setSubjectsMap(subMap);
			}
			
			if(stuBo.getStudentPreviousClassesHistory()!=null && !stuBo.getStudentPreviousClassesHistory().isEmpty()){
				Map<PreviousClassDetailsTO, Map<String, List<SubjectTO>>> previousDetailsMap = new HashMap<PreviousClassDetailsTO, Map<String,List<SubjectTO>>>();
				PreviousClassDetailsTO previousClassTO;
				SubjectTO preSubTO;
				
				Set<StudentPreviousClassHistory> stuPreClassesSet = stuBo.getStudentPreviousClassesHistory();
				Iterator<StudentPreviousClassHistory> itrPreClasses= stuPreClassesSet.iterator();
				while (itrPreClasses.hasNext()) {
					Map<String, List<SubjectTO>> previousSubMap=new HashMap<String, List<SubjectTO>>();
					StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrPreClasses.next();
					previousClassTO=new PreviousClassDetailsTO();
					
					previousClassTO.setClassName(studentPreviousClassHistory.getClasses()!=null?studentPreviousClassHistory.getClasses().getName():"");
					previousClassTO.setSchemeNo(studentPreviousClassHistory.getSchemeNo().toString());
					
					if(stuBo.getStudentSubjectGroupHistory()!=null && !stuBo.getStudentSubjectGroupHistory().isEmpty()){
						Set<StudentSubjectGroupHistory> preSubGrpSet= stuBo.getStudentSubjectGroupHistory();
						Iterator<StudentSubjectGroupHistory> itrSubGrp= preSubGrpSet.iterator();
						while (itrSubGrp.hasNext()) {
							StudentSubjectGroupHistory studentSubjectGroupHistory = (StudentSubjectGroupHistory) itrSubGrp.next();
							if(studentSubjectGroupHistory.getSchemeNo().intValue()==studentPreviousClassHistory.getSchemeNo().intValue()){
								List<SubjectTO> preSubList= new ArrayList<SubjectTO>();
								
								if(studentSubjectGroupHistory.getSubjectGroup()!=null && studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses()!=null 
										&& !studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses().isEmpty() && studentSubjectGroupHistory.getSubjectGroup().getIsActive()){
									Set<SubjectGroupSubjects> subGrpSub= studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses();
									Iterator<SubjectGroupSubjects> subGrpSubIter= subGrpSub.iterator();
									while (subGrpSubIter.hasNext()) {
										SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subGrpSubIter.next();
										if(subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()){
											preSubTO= new SubjectTO();
											preSubTO.setSubjectName(subjectGroupSubjects.getSubject().getName());
											preSubTO.setCode(subjectGroupSubjects.getSubject().getCode());
											preSubList.add(preSubTO);
										}
									}
									
									previousSubMap.put(studentSubjectGroupHistory.getSubjectGroup().getName(), preSubList);
								}
								
							}
						}
					}
					
					previousDetailsMap.put(previousClassTO, previousSubMap);
				}
				to.setPreviousClassDetailsMap(previousDetailsMap);
				to.setPreviousHistoryAvailable(true);
			}
			
			
		}
		return to;
	}
	
	
	public StudentTO convertBOsToTOs(Student stuBo){
		StudentTO to=null;
		SubjectTO subTo=null;
		Map<String, List<SubjectTO>> subMap= new HashMap<String, List<SubjectTO>>();
		
		if(stuBo!=null){
			to=new StudentTO();	
			if(stuBo.getAdmAppln()!=null && stuBo.getAdmAppln().getPersonalData()!=null 
			&& stuBo.getAdmAppln().getPersonalData().getFirstName()!=null && !stuBo.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
				to.setStudentName(stuBo.getAdmAppln().getPersonalData().getFirstName());
			}
			if(stuBo.getClassSchemewise()!=null && stuBo.getClassSchemewise().getClasses()!=null ){
			to.setClassName(stuBo.getClassSchemewise().getClasses().getName());
			}
			if(stuBo.getAdmAppln().getApplicantSubjectGroups()!=null && !stuBo.getAdmAppln().getApplicantSubjectGroups().isEmpty()){
				Set<ApplicantSubjectGroup> setApplnSub= stuBo.getAdmAppln().getApplicantSubjectGroups();
				Iterator<ApplicantSubjectGroup> itr = setApplnSub.iterator();
				while (itr.hasNext()) {
					List<SubjectTO> subjectList= new ArrayList<SubjectTO>();
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) itr.next();
					Set<SubjectGroupSubjects> subjectsSet= applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses();
					Iterator<SubjectGroupSubjects> subItr= subjectsSet.iterator();
					while (subItr.hasNext()) {
						SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr.next();
						if(subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()){
							subTo= new SubjectTO();
							subTo.setSubjectName(subjectGroupSubjects.getSubject().getName());
						subjectList.add(subTo);
						}
					}
					subMap.put(applicantSubjectGroup.getSubjectGroup().getName(),subjectList);
				}
				to.setSubjectsMap(subMap);
			}
		}
		
		if(stuBo.getStudentPreviousClassesHistory()!=null && !stuBo.getStudentPreviousClassesHistory().isEmpty()){
			Map<String,PreviousClassDetailsTO> stuHistoryMap= new HashMap<String, PreviousClassDetailsTO>();
			
			Set<StudentPreviousClassHistory> stuClassHistory =stuBo.getStudentPreviousClassesHistory();
			Iterator<StudentPreviousClassHistory> itrClass = stuClassHistory.iterator();
			while (itrClass.hasNext()) {
				StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
				PreviousClassDetailsTO preClassTO= new PreviousClassDetailsTO();
				preClassTO.setClassName(studentPreviousClassHistory.getClasses()!=null?studentPreviousClassHistory.getClasses().getName():"");
				preClassTO.setSchemeNo(studentPreviousClassHistory.getSchemeNo()!=null?studentPreviousClassHistory.getSchemeNo().toString():"");
				stuHistoryMap.put((studentPreviousClassHistory.getSchemeNo()!=null?(studentPreviousClassHistory.getSchemeNo()).toString():""), preClassTO);
			}			
			
			if(stuBo.getStudentSubjectGroupHistory()!=null && !stuBo.getStudentSubjectGroupHistory().isEmpty()){
				Set<StudentSubjectGroupHistory> subHistorySet=stuBo.getStudentSubjectGroupHistory();
				Iterator<StudentSubjectGroupHistory> itrSubGrp=subHistorySet.iterator();
				while (itrSubGrp.hasNext()) {
					StudentSubjectGroupHistory studentSubjectGroupHistory = (StudentSubjectGroupHistory) itrSubGrp.next();
					if(stuHistoryMap.containsKey(studentSubjectGroupHistory.getSchemeNo().toString())){
						PreviousClassDetailsTO oldPreClassTO= stuHistoryMap.remove(studentSubjectGroupHistory.getSchemeNo().toString());
						oldPreClassTO.setSubjectGroupName(studentSubjectGroupHistory.getSubjectGroup().getName());
						Map<String,List<SubjectTO>> previousSubjectMap=new HashMap<String, List<SubjectTO>>();
						if(oldPreClassTO.getPreviousSubjectGrpMap()!=null && !oldPreClassTO.getPreviousSubjectGrpMap().isEmpty())
							previousSubjectMap=oldPreClassTO.getPreviousSubjectGrpMap();
					
						if(studentSubjectGroupHistory.getSubjectGroup()!=null && studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses()!=null
								&& !studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses().isEmpty()){
							Set<SubjectGroupSubjects> subGrpSubSet = studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses();
							Iterator<SubjectGroupSubjects> itrSubGrpSubjects=subGrpSubSet.iterator();
							List<SubjectTO> previousSubTOList= new ArrayList<SubjectTO>();
							while (itrSubGrpSubjects.hasNext()) {
								SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) itrSubGrpSubjects.next();
								if(subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()){
								SubjectTO preSubTo=new SubjectTO();
								preSubTo.setSubjectName(subjectGroupSubjects.getSubject().getName());
								previousSubTOList.add(preSubTo);
								}
							}
							previousSubjectMap.put(studentSubjectGroupHistory.getSubjectGroup().getName(), previousSubTOList);
						}
						oldPreClassTO.setPreviousSubjectGrpMap(previousSubjectMap);
						stuHistoryMap.put(studentSubjectGroupHistory.getSchemeNo().toString(), oldPreClassTO);
						

					}
				}
			}
			to.setStudentHistoryMap(stuHistoryMap);
			List<PreviousClassDetailsTO> displayList = new ArrayList<PreviousClassDetailsTO>(stuHistoryMap.values());
			Collections.sort(displayList);
			to.setPreviousDetailsList(displayList);
			to.setPreviousHistoryAvailable(true);
		}
		
		
		return to;
	}
	
}
