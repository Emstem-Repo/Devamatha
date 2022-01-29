package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.forms.admin.SyllabusEntryForm;
import com.kp.cms.helpers.admin.SyllabusEntryHelper;
import com.kp.cms.to.admin.SyllabusEntryDupTo;
import com.kp.cms.to.admin.SyllabusEntryGeneralTo;
import com.kp.cms.to.admin.SyllabusEntryHeadingDescTo;
import com.kp.cms.to.admin.SyllabusEntryTo;
import com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo;
import com.kp.cms.transactions.admin.ISyllabusEntryTrans;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SyllabusEntryTransImpl;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.attendance.AcademicyearTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class SyllabusEntryHandler {
	ISyllabusEntryTrans transaction=SyllabusEntryTransImpl.getInstance();
	SyllabusEntryHelper syllabusEntryHelper=SyllabusEntryHelper.getInstance();
	public static volatile SyllabusEntryHandler syllabusEntryHandler=null;
	//private constructor
	private SyllabusEntryHandler(){
		
	}
	//singleton object
	public static SyllabusEntryHandler getInstance(){
		if(syllabusEntryHandler==null){
			syllabusEntryHandler=new SyllabusEntryHandler();
			return syllabusEntryHandler;
		}
		return syllabusEntryHandler;
	}
	
	public boolean adminSave(SyllabusEntryForm syllabusEntryForm, HttpServletRequest request) throws Exception{
		boolean flag=false;
		SyllabusEntry syllabusEntry=syllabusEntryHelper.convertFormToAdminSaveBo(syllabusEntryForm,request);
		if(syllabusEntryForm.getId()>0){
			flag=transaction.update(syllabusEntry);
			}else{
				flag=transaction.save(syllabusEntry);
			}
		return flag;
	}
	
	public void addMoreHeadingsAndDescription(
			SyllabusEntryForm syllabusEntryForm) throws Exception{
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
		List<SyllabusEntryUnitsHoursTo> list1=new ArrayList<SyllabusEntryUnitsHoursTo>();
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(null);
		for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : list) {
			if(syllabusEntryUnitsHoursTo.getPosition().intValue()==syllabusEntryForm.getNum().intValue()){
				List<SyllabusEntryHeadingDescTo> list2=syllabusEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos();
				SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo=new SyllabusEntryHeadingDescTo();
				syllabusEntryHeadingDescTo.setHeadingTemplate("Heading");
				syllabusEntryHeadingDescTo.setDescriptionTemplate("Description");
				syllabusEntryHeadingDescTo.setHeadingNO(list2.size()+1);
				list2.add(syllabusEntryHeadingDescTo);
				Collections.sort(list2);
				syllabusEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(list2);
				if(list2.size()>1){
					syllabusEntryUnitsHoursTo.setHeadingsFlag(true);
					syllabusEntryForm.setHeadingsFocus("headings_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryForm.setTempHeadingsFocus("head_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryForm.setPosition("U_"+syllabusEntryUnitsHoursTo.getPosition());
				}else{
					syllabusEntryUnitsHoursTo.setHeadingsFlag(false);
				}
				list1.add(syllabusEntryUnitsHoursTo);
			}else{
				list1.add(syllabusEntryUnitsHoursTo);
			}
		}
		Collections.sort(list1);
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(list1);
		syllabusEntryForm.setUnitOrHead("head");
	}
	public void removeMoreHeadingsAndDescription(
			SyllabusEntryForm syllabusEntryForm) throws Exception{
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
		List<SyllabusEntryUnitsHoursTo> list1=new ArrayList<SyllabusEntryUnitsHoursTo>();
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(null);
		for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : list) {
			if(syllabusEntryUnitsHoursTo.getPosition().intValue()==syllabusEntryForm.getNum().intValue()){
				List<SyllabusEntryHeadingDescTo> list2=syllabusEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos();
				list2.remove(list2.size()-1);
				syllabusEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(list2);
				if(list2.size()>1){
					syllabusEntryForm.setHeadingsFocus("headings_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryForm.setTempHeadingsFocus("head_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryUnitsHoursTo.setHeadingsFlag(true);
				}else{
					syllabusEntryUnitsHoursTo.setHeadingsFlag(false);
				}
				list1.add(syllabusEntryUnitsHoursTo);
			}else{
				list1.add(syllabusEntryUnitsHoursTo);
			}
		}
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(list1);
		syllabusEntryForm.setUnitOrHead("head");
	}
	public void addMoreUnitsAndHours(SyllabusEntryForm syllabusEntryForm) throws Exception{
		//start units and hours
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
		SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
		syllabusEntryUnitsHoursTo.setUnitNo(list.size()+1);
		syllabusEntryUnitsHoursTo.setUnits("Unit-"+syllabusEntryUnitsHoursTo.getUnitNo());
		syllabusEntryUnitsHoursTo.setPosition(list.size()+1);
		syllabusEntryUnitsHoursTo.setTeachingHoursTemplate("Teaching Hours");
		//start headings and description
		List<SyllabusEntryHeadingDescTo> list2=new ArrayList<SyllabusEntryHeadingDescTo>();
			SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo=new SyllabusEntryHeadingDescTo();
			syllabusEntryHeadingDescTo.setHeadingTemplate("Heading");
			syllabusEntryHeadingDescTo.setDescriptionTemplate("Description");
			syllabusEntryHeadingDescTo.setHeadingNO(list2.size()+1);
			list2.add(syllabusEntryHeadingDescTo);
			Collections.sort(list2);
		syllabusEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(list2);
		//end heading and descriptions
		list.add(syllabusEntryUnitsHoursTo);
		//end units and hours
		Collections.sort(list);
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(list);
		syllabusEntryForm.setUnitOrHead("unit");
	}
	public void removeMoreUnitsAndHours(SyllabusEntryForm syllabusEntryForm) throws Exception{
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForm.getSyllabusEntryUnitsHoursTos();
		Map<Integer,SyllabusEntryUnitsHoursTo> map=new HashMap<Integer, SyllabusEntryUnitsHoursTo>();
		//select which are checked
		for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : list) {
			if(syllabusEntryUnitsHoursTo.getChecked()!=null && syllabusEntryUnitsHoursTo.getChecked().equalsIgnoreCase("on")){
				continue;
			}else{
				map.put(syllabusEntryUnitsHoursTo.getUnitNo(), syllabusEntryUnitsHoursTo);
			}
		}
		CommonUtil.sortMapByKey(map);
		if(map!=null && !map.isEmpty()){
			List<SyllabusEntryUnitsHoursTo> syllabusEntryUnitsHoursTos=new ArrayList<SyllabusEntryUnitsHoursTo>();
			int i=1;
			SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo=null;
			for (Map.Entry<Integer, SyllabusEntryUnitsHoursTo> entry : map.entrySet()) {
				syllabusEntryUnitsHoursTo=entry.getValue();
				syllabusEntryUnitsHoursTo.setUnitNo(i);
				syllabusEntryUnitsHoursTo.setUnits("Unit-"+i);
				i++;
				syllabusEntryUnitsHoursTos.add(syllabusEntryUnitsHoursTo);
			}
			syllabusEntryForm.setSyllabusEntryUnitsHoursTos(syllabusEntryUnitsHoursTos);
			syllabusEntryForm.setUnitOrHead("unit");
		}
	}
	@SuppressWarnings("unchecked")
	public void getAllMaps(SyllabusEntryForm syllabusEntryForm) throws Exception{
		Map<Integer,String> deptMap=CommonAjaxImpl.getInstance().getDepartments();
		syllabusEntryForm.setDepartmentMap(deptMap);
		AcademicyearTransactionImpl academicyearTransactionImpl=new AcademicyearTransactionImpl();
		List<AcademicYear> list=academicyearTransactionImpl.getAllAcademicYear();
		Map<String,String> yearMap=new HashMap<String, String>();
		for (AcademicYear academicYear : list) {
			yearMap.put(String.valueOf(academicYear.getYear()), String.valueOf(academicYear.getYear()));
		}
		syllabusEntryForm.setBatchMap(CommonUtil.sortMapByValue(yearMap));
	}
	public void getsubjectCodeAndName(SyllabusEntryForm syllabusEntryForm) throws Exception{
		Subject subject=transaction.getSubject(syllabusEntryForm.getSubjectId());
		syllabusEntryForm.setSubjectCode(subject.getCode());
		syllabusEntryForm.setSubjectName(subject.getName());
		syllabusEntryForm.setParentDepartment(subject.getDepartment().getName());
		if(subject.getIsSecondLanguage() && subject.getIsSecondLanguage()!=null){
			syllabusEntryForm.setSecondLanguage("Yes");
		}else{
			syllabusEntryForm.setSecondLanguage("No");
		}
		if(subject.getIsTheoryPractical()!=null){
			if(subject.getIsTheoryPractical().equalsIgnoreCase("T")){
				syllabusEntryForm.setTheoryOrPractical("Theory");
			}else if(subject.getIsTheoryPractical().equalsIgnoreCase("P")){
				syllabusEntryForm.setTheoryOrPractical("Practical");
			}else{
				syllabusEntryForm.setTheoryOrPractical("Theory and Practical");
			}
		}
		if(subject.getQuestionbyrequired()!=null && subject.getQuestionbyrequired()){
			syllabusEntryForm.setQuestionBankRequired("Yes");
		}else{
			syllabusEntryForm.setQuestionBankRequired("No");
		}
	}
	public boolean adminSaveDraft(SyllabusEntryForm syllabusEntryForm, HttpServletRequest request) throws Exception{
		boolean flag=false;
		SyllabusEntry syllabusEntry=syllabusEntryHelper.convertFormToAdminSaveDraftBo(syllabusEntryForm,request);
		flag=transaction.update(syllabusEntry);
		return flag;
	}
	/**
	 * to check the status of syllabus entry by year and subject
	 * @param academicYear
	 * @param subjectId
	 * @return
	 * @throws Exception
	 */
	public String getSyllabusEntryStatus(String academicYear, String subjectId) throws Exception{
		String status=null;
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(academicYear),Integer.parseInt(subjectId));
		if(syllabusEntry!=null){
			if((syllabusEntry.getSendForApproval()==null || !syllabusEntry.getSendForApproval())
					&& (syllabusEntry.getApproved()==null || !syllabusEntry.getApproved())
					&& (syllabusEntry.getFinalApproval()==null || !syllabusEntry.getFinalApproval())
					&& (syllabusEntry.getHodReject()==null || !syllabusEntry.getHodReject())
					&& (syllabusEntry.getFinalReject()==null || !syllabusEntry.getFinalReject())){
				status="Inprogress";
			}else if(syllabusEntry.getApproved()!=null && syllabusEntry.getApproved()){
				status="Approved BY HOD";
			}else if(syllabusEntry.getSendForApproval()!=null && syllabusEntry.getSendForApproval()){
				status="Completed";
			}else if(syllabusEntry.getFinalApproval()!=null && syllabusEntry.getFinalApproval()){
				status="Approved";
			}else if(syllabusEntry.getHodReject()!=null && syllabusEntry.getHodReject()){
				status="HOD Rejected";
			}else if(syllabusEntry.getFinalReject()!=null && syllabusEntry.getFinalReject()){
				status="Rejected";
			}
		}else {
			status="Pending";
		}
		return status;
	}
	public void setDataWhichIsInProgress(SyllabusEntryForm syllabusEntryForm) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForm.getBatchYear()),Integer.parseInt(syllabusEntryForm.getSubjectId()));
		if(syllabusEntry!=null){
			syllabusEntryHelper.setToFormWhichIsInProgress(syllabusEntry,syllabusEntryForm);
		}
		
	}
	public List<SyllabusEntryDupTo> getsubjectAndStatus(
			SyllabusEntryForm syllabusEntryForm) throws Exception{
		List<SyllabusEntryDupTo> list=null;
		Map<Integer,String> subjectMap=transaction.getSubjectMap(Integer.parseInt(syllabusEntryForm.getDepartmentId()),
				Integer.parseInt(syllabusEntryForm.getSemisterId()), Integer.parseInt(syllabusEntryForm.getBatchYear()));
		if(subjectMap!=null && !subjectMap.isEmpty()){
			Set<Integer> subjIds=subjectMap.keySet();
			List<Integer> subjetcIds=new ArrayList<Integer>();
			for (Integer integer : subjIds) {
				subjetcIds.add(integer);
			}
			List<SyllabusEntry> syllabusEntries=transaction.getSyllabusEntries(subjetcIds,syllabusEntryForm.getBatchYear());
			list=syllabusEntryHelper.setBosToTos(syllabusEntries,subjectMap,syllabusEntryForm.getSemisterId());
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public void getSubjectsAndStatusForTeacherOtherThanLanguages(
			SyllabusEntryForm syllabusEntryForm) throws Exception{
		Map<String,List<SyllabusEntryTo>> map=null;
			StringBuilder stringBuilder=new StringBuilder();
			if(syllabusEntryForm.getType()!=null && syllabusEntryForm.getType().equalsIgnoreCase("languages")){
				//for languages link
				stringBuilder.append("select c.name as cname,s.id as subId,s.name as subname,s.code as subcode,cd.semesterYearNo as sem,d.id as deptId " +
						" from CurriculumSchemeSubject css  join css.curriculumSchemeDuration cd " +
						" join cd.curriculumScheme cs join css.subjectGroup sg join sg.subjectGroupSubjectses sgs " +
						" join sgs.subject s join cs.course c join s.department d where sg.isActive=1 " +
						" and sgs.isActive=1 and s.isActive=1 and cs.year="+Integer.parseInt(syllabusEntryForm.getBatchYear())+" and s.isCertificateCourse=0 " +
						" and s.isSecondLanguage=1 and s.id!=902 and d.id="+Integer.parseInt(syllabusEntryForm.getDepartmentId())+" group by s.id order by c.name,cd.semesterYearNo,s.code");
				Map<Integer,Map<String,SyllabusEntryGeneralTo>> subjectMap=transaction.getlanguageSubjects(stringBuilder.toString());
				if(subjectMap!=null && !subjectMap.isEmpty()){
					Set<Integer> subjIds=subjectMap.keySet();
					List<Integer> subjetcIds=new ArrayList<Integer>();
					for (Integer integer : subjIds) { 
						subjetcIds.add(integer);
					}
					List<SyllabusEntry> syllabusEntries=transaction.getSyllabusEntries(subjetcIds,syllabusEntryForm.getBatchYear());
					map=syllabusEntryHelper.setBosToMap(syllabusEntries,subjectMap,Integer.parseInt(syllabusEntryForm.getDepartmentId()));
				}
			}else{
				// for subjects link
				stringBuilder.append("select c.name as cname,s.id as subId,s.name as subname,s.code as subcode,cd.semesterYearNo as sem,d.id as deptId " +
						" from CurriculumSchemeSubject css  join css.curriculumSchemeDuration cd " +
						" join cd.curriculumScheme cs join css.subjectGroup sg join sg.subjectGroupSubjectses sgs " +
						" join sgs.subject s join cs.course c join s.department d where sg.isActive=1 " +
						" and sgs.isActive=1 and s.isActive=1 and cs.year="+Integer.parseInt(syllabusEntryForm.getBatchYear())+" and s.isCertificateCourse=0 " +
						" and s.isSecondLanguage=0 and s.id!=902 and c.id in (:courseIds) group by c.id,s.id order by c.name,cd.semesterYearNo,s.code");
				List<Integer> courseIds=transaction.getcourseIdsFormCourseDept(syllabusEntryForm.getDepartmentId());
				if(courseIds!=null && !courseIds.isEmpty()){
					Map<Integer,Map<String,SyllabusEntryGeneralTo>> subjectMap=transaction.getSubjects(stringBuilder.toString(),courseIds);
					if(subjectMap!=null && !subjectMap.isEmpty()){
						Set<Integer> subjIds=subjectMap.keySet();
						List<Integer> subjetcIds=new ArrayList<Integer>();
						for (Integer integer : subjIds) { 
							subjetcIds.add(integer);
						}
						List<SyllabusEntry> syllabusEntries=transaction.getSyllabusEntries(subjetcIds,syllabusEntryForm.getBatchYear());
						map=syllabusEntryHelper.setBosToMap(syllabusEntries,subjectMap,Integer.parseInt(syllabusEntryForm.getDepartmentId()));
					}
				}
			}
			if(map!=null){
				syllabusEntryForm.setSubjectMapBySem(CommonUtil.sortMapByKey(map));
			}
	}
	public void previousSyllabusEntryBySubjectAndYear(
			SyllabusEntryForm syllabusEntryForm) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForm.getPreviousYear()),Integer.parseInt(syllabusEntryForm.getPreviousYearSubjectId()));
		if(syllabusEntry!=null){
			syllabusEntryHelper.setToFormPreviousSyllabusEntry(syllabusEntry,syllabusEntryForm);
		}
	}
	public void setDataForPreview(SyllabusEntryForm syllabusEntryForm) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForm.getBatchYear()),Integer.parseInt(syllabusEntryForm.getSubjectId()));
		if(syllabusEntry!=null){
			syllabusEntryHelper.setToFormForPreview(syllabusEntry,syllabusEntryForm);
		}
		if(syllabusEntryForm.getDepartmentId()!=null){
			Department department=DepartmentEntryTransactionImpl.getInstance().getDepartmentdetails(Integer.parseInt(syllabusEntryForm.getDepartmentId()));
			syllabusEntryForm.setDepartmentName(department.getName());
		}
		
	}
}
