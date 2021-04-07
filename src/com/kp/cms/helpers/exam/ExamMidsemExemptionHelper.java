package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidsemExemption;
import com.kp.cms.bo.exam.ExamMidsemExemptionDetails;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.exam.StudentSubjectGroupHistory;
import com.kp.cms.forms.exam.ExamMidsemExemptionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.PreviousClassDetailsTO;

/**
 * @author dIlIp
 *
 */
public class ExamMidsemExemptionHelper {
	
	private static final Log log = LogFactory.getLog(ExamMidsemExemptionHelper.class);
	private static volatile ExamMidsemExemptionHelper examMidsemExemptionHelper = null;
	
	private ExamMidsemExemptionHelper() {
		
	}
	
	/**
	 * @return
	 */
	public static ExamMidsemExemptionHelper getInstance() {

		if (examMidsemExemptionHelper == null) {
			examMidsemExemptionHelper = new ExamMidsemExemptionHelper();
		}
		return examMidsemExemptionHelper;
	}

		
	/**
	 * @param stuBo
	 * @param exemptionForm
	 * @param map
	 * @return
	 */
	public StudentTO convertBOsToTOs(Student stuBo, ExamMidsemExemptionForm exemptionForm, Map<Integer, SubjectTO> map){
		StudentTO to=null;
		SubjectTO subTo=null;
		String pre=null;
		
		if(stuBo!=null){
			to=new StudentTO();
			to.setId(stuBo.getId());
			if(stuBo.getAdmAppln()!=null && stuBo.getAdmAppln().getPersonalData()!=null 
			&& stuBo.getAdmAppln().getPersonalData().getFirstName()!=null && !stuBo.getAdmAppln().getPersonalData().getFirstName().isEmpty())
				to.setStudentName(stuBo.getAdmAppln().getPersonalData().getFirstName());
			
			if(stuBo.getClassSchemewise()!=null && stuBo.getClassSchemewise().getClasses()!=null ){
				if(Integer.parseInt(exemptionForm.getClassId()) == stuBo.getClassSchemewise().getClasses().getId()){
				to.setClassName(stuBo.getClassSchemewise().getClasses().getName());
				pre = "CURRENT";
				}
			else{
				Set<StudentPreviousClassHistory> stuClassHistory =stuBo.getStudentPreviousClassesHistory();
				Iterator<StudentPreviousClassHistory> itrClass = stuClassHistory.iterator();
				while (itrClass.hasNext()) {
					StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
					if(Integer.parseInt(exemptionForm.getClassId()) == studentPreviousClassHistory.getClasses().getId()){
						to.setClassName(studentPreviousClassHistory.getClasses().getName());
						pre = "PREVIOUS";
						break;
					}
				}
			}
			}
			if(pre.equalsIgnoreCase("CURRENT")){
			
			if(stuBo.getAdmAppln().getApplicantSubjectGroups()!=null && !stuBo.getAdmAppln().getApplicantSubjectGroups().isEmpty()){
				Set<ApplicantSubjectGroup> setApplnSub= stuBo.getAdmAppln().getApplicantSubjectGroups();
				Iterator<ApplicantSubjectGroup> itr = setApplnSub.iterator();
				List<SubjectTO> subjectList= new ArrayList<SubjectTO>();
				while (itr.hasNext()) {
//					List<SubjectTO> subjectList= new ArrayList<SubjectTO>();
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) itr.next();
					Set<SubjectGroupSubjects> subjectsSet= applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses();
					Iterator<SubjectGroupSubjects> subItr= subjectsSet.iterator();
					while (subItr.hasNext()) {
						SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) subItr.next();
						if(subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()){
							if(map.containsKey(subjectGroupSubjects.getSubject().getId())){
								SubjectTO to1 = map.get(subjectGroupSubjects.getSubject().getId());
								to1.setTempChecked1("on");
								subjectList.add(to1);
							}
							else{
								subTo= new SubjectTO();
								subTo.setId(subjectGroupSubjects.getSubject().getId());
								subTo.setSubjectName(subjectGroupSubjects.getSubject().getName());
								subTo.setCode(subjectGroupSubjects.getSubject().getCode());
								subjectList.add(subTo);
							}
							

						}
					}
//					to.setSubjectList(subjectList);
				}
				to.setSubjectList(subjectList);
			}
			}
			else{
				
				if(stuBo.getStudentPreviousClassesHistory()!=null && !stuBo.getStudentPreviousClassesHistory().isEmpty()){
					Map<String,PreviousClassDetailsTO> stuHistoryMap= new HashMap<String, PreviousClassDetailsTO>();
					
					Set<StudentPreviousClassHistory> stuClassHistory =stuBo.getStudentPreviousClassesHistory();
					Iterator<StudentPreviousClassHistory> itrClass = stuClassHistory.iterator();
					while (itrClass.hasNext()) {
						StudentPreviousClassHistory studentPreviousClassHistory = (StudentPreviousClassHistory) itrClass.next();
						PreviousClassDetailsTO preClassTO= new PreviousClassDetailsTO();
						if(Integer.parseInt(exemptionForm.getClassId()) == studentPreviousClassHistory.getClasses().getId()){
						preClassTO.setClassName(studentPreviousClassHistory.getClasses()!=null?studentPreviousClassHistory.getClasses().getName():"");
						preClassTO.setSchemeNo(studentPreviousClassHistory.getSchemeNo()!=null?studentPreviousClassHistory.getSchemeNo().toString():"");
						stuHistoryMap.put((studentPreviousClassHistory.getSchemeNo()!=null?(studentPreviousClassHistory.getSchemeNo()).toString():""), preClassTO);
						}
					}			
					
					if(stuBo.getStudentSubjectGroupHistory()!=null && !stuBo.getStudentSubjectGroupHistory().isEmpty()){
						
						List<SubjectTO> previousSubTOList= new ArrayList<SubjectTO>();
						Set<StudentSubjectGroupHistory> subHistorySet=stuBo.getStudentSubjectGroupHistory();
						Iterator<StudentSubjectGroupHistory> itrSubGrp=subHistorySet.iterator();
						while (itrSubGrp.hasNext()) {
							StudentSubjectGroupHistory studentSubjectGroupHistory = (StudentSubjectGroupHistory) itrSubGrp.next();
							if(stuHistoryMap.containsKey(studentSubjectGroupHistory.getSchemeNo().toString())){
								//PreviousClassDetailsTO oldPreClassTO= stuHistoryMap.remove(studentSubjectGroupHistory.getSchemeNo().toString());
								//oldPreClassTO.setSubjectGroupName(studentSubjectGroupHistory.getSubjectGroup().getName());
							
								if(studentSubjectGroupHistory.getSubjectGroup()!=null && studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses()!=null
										&& !studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses().isEmpty()){
									Set<SubjectGroupSubjects> subGrpSubSet = studentSubjectGroupHistory.getSubjectGroup().getSubjectGroupSubjectses();
									Iterator<SubjectGroupSubjects> itrSubGrpSubjects=subGrpSubSet.iterator();
									while (itrSubGrpSubjects.hasNext()) {
										SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) itrSubGrpSubjects.next();
										if(subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()){
											
										if(map.containsKey(subjectGroupSubjects.getSubject().getId())){
											SubjectTO to1 = map.get(subjectGroupSubjects.getSubject().getId());
											to1.setTempChecked1("on");
											previousSubTOList.add(to1);
											
										}
										else{
											SubjectTO preSubTo=new SubjectTO();
											preSubTo.setId(subjectGroupSubjects.getSubject().getId());
											preSubTo.setSubjectName(subjectGroupSubjects.getSubject().getName());
											preSubTo.setCode(subjectGroupSubjects.getSubject().getCode());
											previousSubTOList.add(preSubTo);
										}
									}
									}
									
								}
								
							}
						}
						to.setSubjectList(previousSubTOList);
					}
					
				}
				
			}
			
		}
		
		return to;
	}
	
	/**
	 * @param exemptionForm
	 * @return
	 */
	public ExamMidsemExemption convertFormToBO(ExamMidsemExemptionForm exemptionForm) {
		
		ExamMidsemExemption exemptionBO = new ExamMidsemExemption();
		Set<ExamMidsemExemptionDetails> exemptionDetails=new HashSet<ExamMidsemExemptionDetails>();
		
		if(exemptionForm.getExamMidsemExemptionId()>0){
			exemptionBO.setId(exemptionForm.getExamMidsemExemptionId());
		}
		ExamDefinitionBO examDefBO = new ExamDefinitionBO();
		examDefBO.setId(Integer.parseInt(exemptionForm.getExamId()));
		exemptionBO.setExamId(examDefBO);
		
		Student stuBo = new Student();
		stuBo.setId(exemptionForm.getStudent().getId());
		exemptionBO.setStudentId(stuBo);
		
		Classes classBO = new Classes();
		classBO.setId(Integer.parseInt(exemptionForm.getClassId()));
		exemptionBO.setClassId(classBO);
		
		if(exemptionForm.getReason()!=null && !exemptionForm.getReason().isEmpty())
			exemptionBO.setReason(exemptionForm.getReason());
		
		if(exemptionForm.getStudent()!=null && exemptionForm.getStudent().getSubjectList()!=null){
			Iterator<SubjectTO> subTOItr = exemptionForm.getStudent().getSubjectList().iterator();
			while(subTOItr.hasNext()){
				ExamMidsemExemptionDetails details = new ExamMidsemExemptionDetails();
				SubjectTO subTO = subTOItr.next();
				if(subTO.getChecked1()!=null && subTO.getChecked1().equalsIgnoreCase("on")){
					if(subTO.getExamMidsemExemptionDetailsId()!=null && Integer.parseInt(subTO.getExamMidsemExemptionDetailsId())>0){
						details.setId(Integer.parseInt(subTO.getExamMidsemExemptionDetailsId()));
						Subject sub = new Subject();
						sub.setId(subTO.getId());					
						details.setSubject(sub);
						details.setExamMidsemExemption(exemptionBO);
						details.setModifiedBy(exemptionForm.getUserId());
						details.setLastModifiedDate(new Date());
						details.setIsActive(true);
					}
					else{
						Subject sub = new Subject();
						sub.setId(subTO.getId());					
						details.setSubject(sub);
						details.setExamMidsemExemption(exemptionBO);
						details.setIsActive(true);
						details.setCreatedBy(exemptionForm.getUserId());
						details.setCreatedDate(new Date());
						details.setModifiedBy(exemptionForm.getUserId());
						details.setLastModifiedDate(new Date());
						
					}
					exemptionDetails.add(details);
				}else if(subTO.getTempChecked1()!=null && subTO.getTempChecked1().equalsIgnoreCase("on") 
						&& (subTO.getChecked1()==null)){
					
						details.setId(Integer.parseInt(subTO.getExamMidsemExemptionDetailsId()));
						Subject sub = new Subject();
						sub.setId(subTO.getId());					
						details.setSubject(sub);
						details.setExamMidsemExemption(exemptionBO);
						details.setModifiedBy(exemptionForm.getUserId());
						details.setLastModifiedDate(new Date());
						details.setIsActive(false);
						
						exemptionDetails.add(details);
					}
			}
			
			exemptionBO.setExamMidsemExemptionDetails(exemptionDetails);
		}
		if(exemptionForm.getExamMidsemExemptionId()==0){
			exemptionBO.setCreatedBy(exemptionForm.getUserId());
			exemptionBO.setCreatedDate(new Date());
			
		}
		exemptionBO.setModifiedBy(exemptionForm.getUserId());
		exemptionBO.setLastModifiedDate(new Date());
		exemptionBO.setIsActive(true);
		return exemptionBO;
	}

	/**
	 * @param exemption
	 * @param exemptionForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, SubjectTO> convertBOTOMap(ExamMidsemExemption exemption, ExamMidsemExemptionForm exemptionForm) throws Exception{
		Map<Integer, SubjectTO> subMap = new HashMap<Integer, SubjectTO>();
		exemptionForm.setExamMidsemExemptionId(exemption.getId());
		exemptionForm.setReason(exemption.getReason());
		if(exemption.getExamMidsemExemptionDetails()!=null ){

				Set<ExamMidsemExemptionDetails> exemptionDetails = exemption.getExamMidsemExemptionDetails();
				Iterator<ExamMidsemExemptionDetails> iterator = exemptionDetails.iterator();
				while (iterator.hasNext()) {
					ExamMidsemExemptionDetails examMidsemExemptionDetails = (ExamMidsemExemptionDetails) iterator.next();
					if(examMidsemExemptionDetails.getIsActive()){
					SubjectTO subjectTO = new SubjectTO();
					subjectTO.setId(examMidsemExemptionDetails.getSubject().getId());
					subjectTO.setSubjectName(examMidsemExemptionDetails.getSubject().getName());
					subjectTO.setCode(examMidsemExemptionDetails.getSubject().getCode());
					subjectTO.setExamMidsemExemptionDetailsId(String.valueOf(examMidsemExemptionDetails.getId()));
					subMap.put(examMidsemExemptionDetails.getSubject().getId(), subjectTO);
					}
				}
			
		}
		return subMap;
	}
	
	

}
