package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.bo.admin.SyllabusEntryProgramDetails;
import com.kp.cms.forms.admin.SyllabusEntryHodApprovalForm;
import com.kp.cms.helpers.admin.SyllabusEntryHelper;
import com.kp.cms.helpers.admin.SyllabusEntryHodHelper;
import com.kp.cms.to.admin.SyllabusEntryGeneralTo;
import com.kp.cms.to.admin.SyllabusEntryHeadingDescTo;
import com.kp.cms.to.admin.SyllabusEntryHodApprovalTo;
import com.kp.cms.to.admin.SyllabusEntryProgramDetailsTo;
import com.kp.cms.to.admin.SyllabusEntryTo;
import com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo;
import com.kp.cms.transactions.admin.ISyllabusEntryHodApprovalTrans;
import com.kp.cms.transactions.admin.ISyllabusEntryTrans;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SyllabusEntryHodApprovalTransImpl;
import com.kp.cms.transactionsimpl.admin.SyllabusEntryTransImpl;
import com.kp.cms.utilities.CommonUtil;


public class SyllabusEntryHodApprovalHandler {
	ISyllabusEntryHodApprovalTrans transactions=SyllabusEntryHodApprovalTransImpl.getInstance();
	ISyllabusEntryTrans transaction=SyllabusEntryTransImpl.getInstance();
	SyllabusEntryHelper syllabusEntryHelper=SyllabusEntryHelper.getInstance();
	SyllabusEntryHodHelper syllabusEntryHodHelper=SyllabusEntryHodHelper.getInstance();
	public static volatile SyllabusEntryHodApprovalHandler syllabusEntryForHodApprovalHandler=null;
	//private constructor
	private SyllabusEntryHodApprovalHandler(){
		
	}
	//singleton object
	public static SyllabusEntryHodApprovalHandler getInstance(){
		if(syllabusEntryForHodApprovalHandler==null){
			syllabusEntryForHodApprovalHandler=new SyllabusEntryHodApprovalHandler();
			return syllabusEntryForHodApprovalHandler;
		}
		return syllabusEntryForHodApprovalHandler;
	}
	/**
	 * check is syllabus entry is open for the batch
	 * @param syllabusEntryForHodApprovalForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkForSyllabusEntryLink(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
	 	Date currentDate=new Date();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(currentDate);
	    boolean flag=transactions.checkForSyllabusEntryOpen(syllabusEntryForHodApprovalForm,CommonUtil.ConvertStringToSQLDate(CommonUtil.formatDates(currentDate)));
	return flag;
	}
	/**
	 * get course with subjects by batch and department
	 * @param syllabusEntryHodApprovalForm
	 * @throws Exception
	 */
	
	public void getCourseWithSubjects(
			SyllabusEntryHodApprovalForm syllabusEntryHodApprovalForm) throws Exception{
		// start to get subjects by course 
		Map<String,List<SyllabusEntryTo>> map=null;
			StringBuilder stringBuilder=new StringBuilder();
			if(syllabusEntryHodApprovalForm.getType()!=null && syllabusEntryHodApprovalForm.getType().equalsIgnoreCase("languages")){
				//for languages link
				stringBuilder.append("select c.name as cname,s.id as subId,s.name as subname,s.code as subcode,cd.semesterYearNo as sem,d.id as deptId " +
						" from CurriculumSchemeSubject css  join css.curriculumSchemeDuration cd " +
						" join cd.curriculumScheme cs join css.subjectGroup sg join sg.subjectGroupSubjectses sgs " +
						" join sgs.subject s join cs.course c join s.department d where sg.isActive=1 " +
						" and sgs.isActive=1 and s.isActive=1 and cs.year="+Integer.parseInt(syllabusEntryHodApprovalForm.getBatchYear())+" and s.isCertificateCourse=0 " +
						" and s.isSecondLanguage=1 and s.id!=902 and d.id="+Integer.parseInt(syllabusEntryHodApprovalForm.getDepartmentId())+" group by s.id order by c.name,cd.semesterYearNo,s.code");
				Map<Integer,Map<String,SyllabusEntryGeneralTo>> subjectMap=transaction.getlanguageSubjects(stringBuilder.toString());
				if(subjectMap!=null && !subjectMap.isEmpty()){
					Set<Integer> subjIds=subjectMap.keySet();
					List<Integer> subjetcIds=new ArrayList<Integer>();
					for (Integer integer : subjIds) { 
						subjetcIds.add(integer);
					}
					List<SyllabusEntry> syllabusEntries=transaction.getSyllabusEntries(subjetcIds,syllabusEntryHodApprovalForm.getBatchYear());
					map=syllabusEntryHelper.setBosToMap(syllabusEntries,subjectMap,Integer.parseInt(syllabusEntryHodApprovalForm.getDepartmentId()));
				}
			}else{
				// for subjects link
				stringBuilder.append("select c.name as cname,s.id as subId,s.name as subname,s.code as subcode,cd.semesterYearNo as sem,d.id as deptId " +
						" from CurriculumSchemeSubject css  join css.curriculumSchemeDuration cd " +
						" join cd.curriculumScheme cs join css.subjectGroup sg join sg.subjectGroupSubjectses sgs " +
						" join sgs.subject s join cs.course c join s.department d where sg.isActive=1 " +
						" and sgs.isActive=1 and s.isActive=1 and cs.year="+Integer.parseInt(syllabusEntryHodApprovalForm.getBatchYear())+" and s.isCertificateCourse=0 " +
						" and s.isSecondLanguage=0 and s.id!=902 and c.id in (:courseIds) group by c.id,s.id order by c.name,cd.semesterYearNo,s.code");
				List<Integer> courseIds=transaction.getcourseIdsFormCourseDept(syllabusEntryHodApprovalForm.getDepartmentId());
				if(courseIds!=null && !courseIds.isEmpty()){
					Map<Integer,Map<String,SyllabusEntryGeneralTo>> subjectMap=transaction.getSubjects(stringBuilder.toString(),courseIds);
					if(subjectMap!=null && !subjectMap.isEmpty()){
						Set<Integer> subjIds=subjectMap.keySet();
						List<Integer> subjetcIds=new ArrayList<Integer>();
						for (Integer integer : subjIds) { 
							subjetcIds.add(integer);
						}
						List<SyllabusEntry> syllabusEntries=transaction.getSyllabusEntries(subjetcIds,syllabusEntryHodApprovalForm.getBatchYear());
						map=syllabusEntryHelper.setBosToMap(syllabusEntries,subjectMap,Integer.parseInt(syllabusEntryHodApprovalForm.getDepartmentId()));
					}
				}
			}
			//end to get subjects by course
			//start to put all subjects by course into one list
			if(map!=null){
				Map<String,Integer> courseMap=transactions.getCourseMap();
				List<SyllabusEntryProgramDetails> programDetails=transactions.getProgramDetailsByBatch(syllabusEntryHodApprovalForm.getBatchYear());
				Map<Integer,SyllabusEntryProgramDetailsTo> programDetailsMap=syllabusEntryHodHelper.getProgramDetailsMap(programDetails);
				List<SyllabusEntryHodApprovalTo> list=new ArrayList<SyllabusEntryHodApprovalTo>();
				SyllabusEntryHodApprovalTo syllabusEntryHodApprovalTo=null;
				SyllabusEntryProgramDetailsTo syllabusEntryProgramDetailsTo=null;
				for (Map.Entry<String, List<SyllabusEntryTo>> entry : map.entrySet()){
					syllabusEntryHodApprovalTo=new SyllabusEntryHodApprovalTo();
					//get key
					syllabusEntryHodApprovalTo.setCourseName(entry.getKey());
					if(courseMap.containsKey(entry.getKey())){
						syllabusEntryHodApprovalTo.setCourseId(String.valueOf(courseMap.get(entry.getKey())));
					}
					if(programDetailsMap.containsKey(Integer.parseInt(syllabusEntryHodApprovalTo.getCourseId()))){
						syllabusEntryProgramDetailsTo=programDetailsMap.get(Integer.parseInt(syllabusEntryHodApprovalTo.getCourseId()));
					}else{
						syllabusEntryProgramDetailsTo=new SyllabusEntryProgramDetailsTo();
					}
					//get value
					syllabusEntryHodApprovalTo.setSyllabusEntryTos(entry.getValue());
					//program details for course
					syllabusEntryHodApprovalTo.setProgramDetails("Programme Details");
					syllabusEntryHodApprovalTo.setProgramTo(syllabusEntryProgramDetailsTo);
					//end program details for course
					list.add(syllabusEntryHodApprovalTo);
				}
				Collections.sort(list);
				syllabusEntryHodApprovalForm.setList(list);
				syllabusEntryHodApprovalForm.setListSize(String.valueOf(list.size()));
			}
			//end to put all subjects by course into one list
	}
	public boolean syllabusEntryHodApprove(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm, HttpServletRequest request) throws Exception{
		boolean flag=false;
		//get the course which course subjects we need to approve
		Integer courseId=Integer.parseInt(syllabusEntryForHodApprovalForm.getCourseId());
		List<SyllabusEntryHodApprovalTo> list=syllabusEntryForHodApprovalForm.getList();
		List<SyllabusEntryTo> syllabusEntryTos=null;
		// I am getting the subjects which i need to approve by courseId
		for (SyllabusEntryHodApprovalTo syllabusEntryHodApprovalTo : list) {
			if(Integer.parseInt(syllabusEntryHodApprovalTo.getCourseId())==courseId){
				syllabusEntryTos=syllabusEntryHodApprovalTo.getSyllabusEntryTos();
			}
		}
		List<Integer> approveList=null;
		if(syllabusEntryTos!=null && !syllabusEntryTos.isEmpty()){
			//now I am checking which subject is approve with which check box is seleceted
			approveList=new ArrayList<Integer>();
			for (SyllabusEntryTo syllabusEntryTo : syllabusEntryTos) {
				if(syllabusEntryTo.getChecked()!=null && syllabusEntryTo.getChecked().equalsIgnoreCase("on")){
					approveList.add(syllabusEntryTo.getId());
				}
			}
		}
		if(approveList!=null && !approveList.isEmpty()){
			// get the syllabus Entry which we need to approve
			List<SyllabusEntry> list1=transactions.getSyllabusEntriesByIds(approveList);
			List<SyllabusEntry> list2=new ArrayList<SyllabusEntry>();
			for (SyllabusEntry syllabusEntry : list1) {
				syllabusEntry.setSendForApproval(false);
				syllabusEntry.setApproved(true);
				syllabusEntry.setFinalApproval(false);
				syllabusEntry.setHodReject(false);
				syllabusEntry.setFinalReject(false);
				syllabusEntry.setLastModifiedDate(new Date());
				syllabusEntry.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
				list2.add(syllabusEntry);
			}
			flag=transactions.updateByHod(list2);
		}else{
			request.setAttribute("approve","approve");
			throw new Exception();
		}
		return flag;
	}
	public boolean rejectSyllabusEntry(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm, HttpServletRequest request) throws Exception{
		boolean flag=false;
		//get the course which course subjects we need to approve
		Integer courseId=Integer.parseInt(syllabusEntryForHodApprovalForm.getCourseId());
		List<SyllabusEntryHodApprovalTo> list=syllabusEntryForHodApprovalForm.getList();
		List<SyllabusEntryTo> syllabusEntryTos=null;
		// I am getting the subjects which i need to approve by courseId
		for (SyllabusEntryHodApprovalTo syllabusEntryHodApprovalTo : list) {
			if(Integer.parseInt(syllabusEntryHodApprovalTo.getCourseId())==courseId){
				syllabusEntryTos=syllabusEntryHodApprovalTo.getSyllabusEntryTos();
			}
		}
		List<Integer> approveList=null;
		if(syllabusEntryTos!=null && !syllabusEntryTos.isEmpty()){
			//now I am checking which subject is approve with which check box is seleceted
			approveList=new ArrayList<Integer>();
			for (SyllabusEntryTo syllabusEntryTo : syllabusEntryTos) {
				if(syllabusEntryTo.getChecked()!=null && syllabusEntryTo.getChecked().equalsIgnoreCase("on")){
					approveList.add(syllabusEntryTo.getId());
				}
			}
		}
		if(approveList!=null && !approveList.isEmpty()){
			// get the syllabus Entry which we need to approve
			List<SyllabusEntry> list1=transactions.getSyllabusEntriesByIds(approveList);
			List<SyllabusEntry> list2=new ArrayList<SyllabusEntry>();
			for (SyllabusEntry syllabusEntry : list1) {
				syllabusEntry.setRejectReason(syllabusEntryForHodApprovalForm.getRejectReason());
				syllabusEntry.setSendForApproval(false);
				syllabusEntry.setApproved(false);
				syllabusEntry.setFinalApproval(false);
				syllabusEntry.setHodReject(true);
				syllabusEntry.setFinalReject(false);
				syllabusEntry.setLastModifiedDate(new Date());
				syllabusEntry.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
				list2.add(syllabusEntry);
			}
			flag=transactions.updateByHod(list2);
		}else{
			request.setAttribute("approve","approve");
			throw new Exception();
		}
		
		return flag;
	}
	public String getSyllabusEntryStatus(String batchYear, String subjectId) throws Exception{
		String status=null;
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(batchYear),Integer.parseInt(subjectId));
		if(syllabusEntry!=null){
			if((syllabusEntry.getSendForApproval()==null || !syllabusEntry.getSendForApproval())
					&& (syllabusEntry.getApproved()==null || !syllabusEntry.getApproved())
					&& (syllabusEntry.getHodReject()==null || !syllabusEntry.getHodReject())
					&& (syllabusEntry.getFinalReject()==null || !syllabusEntry.getFinalReject())){
				status="Inprogress";
			}else if(syllabusEntry.getApproved()!=null && syllabusEntry.getApproved()){
				status="Approved";
			}else if(syllabusEntry.getSendForApproval()!=null && syllabusEntry.getSendForApproval()){
				status="Completed";
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
	/**
	 * get syllabus entry which subject is in progress
	 * @param syllabusEntryForHodApprovalForm
	 * @throws Exception
	 */
	public void setDataWhichIsInProgress(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()),Integer.parseInt(syllabusEntryForHodApprovalForm.getSubjectId()));
		if(syllabusEntry!=null){
			syllabusEntryHodHelper.setToFormWhichIsInProgress(syllabusEntry,syllabusEntryForHodApprovalForm);
		}
		
	}
	/**
	 * get subject code and name by subjects
	 * @param syllabusEntryForHodApprovalForm
	 * @throws Exception
	 */
	public void getsubjectCodeAndName(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		Subject subject=transaction.getSubject(syllabusEntryForHodApprovalForm.getSubjectId());
		syllabusEntryForHodApprovalForm.setSubjectCode(subject.getCode());
		syllabusEntryForHodApprovalForm.setSubjectName(subject.getName());
		syllabusEntryForHodApprovalForm.setParentDepartment(subject.getDepartment().getName());
		if(subject.getIsSecondLanguage() && subject.getIsSecondLanguage()!=null){
			syllabusEntryForHodApprovalForm.setSecondLanguage("Yes");
		}else{
			syllabusEntryForHodApprovalForm.setSecondLanguage("No");
		}
		if(subject.getIsTheoryPractical()!=null){
			if(subject.getIsTheoryPractical().equalsIgnoreCase("T")){
				syllabusEntryForHodApprovalForm.setTheoryOrPractical("Theory");
			}else if(subject.getIsTheoryPractical().equalsIgnoreCase("P")){
				syllabusEntryForHodApprovalForm.setTheoryOrPractical("Practical");
			}else{
				syllabusEntryForHodApprovalForm.setTheoryOrPractical("Theory and Practical");
			}
		}
		if(subject.getQuestionbyrequired()!=null && subject.getQuestionbyrequired()){
			syllabusEntryForHodApprovalForm.setQuestionBankRequired("Yes");
		}else{
			syllabusEntryForHodApprovalForm.setQuestionBankRequired("No");
		}
	}
	/**
	 * more headings and description
	 * @param syllabusEntryForHodApprovalForm
	 * @throws Exception
	 */
	public void addMoreHeadingsAndDescription(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
		List<SyllabusEntryUnitsHoursTo> list1=new ArrayList<SyllabusEntryUnitsHoursTo>();
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(null);
		for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : list) {
			if(syllabusEntryUnitsHoursTo.getPosition().intValue()==syllabusEntryForHodApprovalForm.getNum().intValue()){
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
					syllabusEntryForHodApprovalForm.setHeadingsFocus("headings_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryForHodApprovalForm.setTempHeadingsFocus("head_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryForHodApprovalForm.setPosition("U_"+syllabusEntryUnitsHoursTo.getPosition());
				}else{
					syllabusEntryUnitsHoursTo.setHeadingsFlag(false);
				}
				list1.add(syllabusEntryUnitsHoursTo);
			}else{
				list1.add(syllabusEntryUnitsHoursTo);
			}
		}
		Collections.sort(list1);
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(list1);
		syllabusEntryForHodApprovalForm.setUnitOrHead("head");
	}
	/**
	 * remove more headings and description
	 * @param syllabusEntryForHodApprovalForm
	 * @throws Exception
	 */
	public void removeMoreHeadingsAndDescription(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
		List<SyllabusEntryUnitsHoursTo> list1=new ArrayList<SyllabusEntryUnitsHoursTo>();
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(null);
		for (SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo : list) {
			if(syllabusEntryUnitsHoursTo.getPosition().intValue()==syllabusEntryForHodApprovalForm.getNum().intValue()){
				List<SyllabusEntryHeadingDescTo> list2=syllabusEntryUnitsHoursTo.getSyllabusEntryHeadingDescTos();
				list2.remove(list2.size()-1);
				syllabusEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(list2);
				if(list2.size()>1){
					syllabusEntryForHodApprovalForm.setHeadingsFocus("headings_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryForHodApprovalForm.setTempHeadingsFocus("head_"+syllabusEntryUnitsHoursTo.getPosition().intValue()+"_"+(list2.size()-1));
					syllabusEntryUnitsHoursTo.setHeadingsFlag(true);
				}else{
					syllabusEntryUnitsHoursTo.setHeadingsFlag(false);
				}
				list1.add(syllabusEntryUnitsHoursTo);
			}else{
				list1.add(syllabusEntryUnitsHoursTo);
			}
		}
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(list1);
		syllabusEntryForHodApprovalForm.setUnitOrHead("head");
	}
	/**
	 * add more units
	 * @param syllabusEntryForHodApprovalForm
	 * @throws Exception
	 */
	public void addMoreUnitsAndHours(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		//start units and hours
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
		SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
		syllabusEntryUnitsHoursTo.setUnits("Unit-"+(list.size()+1));
		syllabusEntryUnitsHoursTo.setPosition(list.size()+1);
		syllabusEntryUnitsHoursTo.setUnitNo(list.size()+1);
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
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(list);
		syllabusEntryForHodApprovalForm.setUnitOrHead("unit");
	}
	/**
	 * remove units
	 * @param syllabusEntryForHodApprovalForm
	 * @throws Exception
	 */
	public void removeMoreUnitsAndHours(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		List<SyllabusEntryUnitsHoursTo> list=syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos();
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
			syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(syllabusEntryUnitsHoursTos);
			syllabusEntryForHodApprovalForm.setUnitOrHead("unit");
		}
	}
	/**
	 * save draft by hod
	 * @param syllabusEntryForHodApprovalForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean saveDraftBYHod(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
			HttpServletRequest request) throws Exception{
		boolean flag=false;
		SyllabusEntry syllabusEntry=syllabusEntryHodHelper.convertFormToAdminSaveDraftBo(syllabusEntryForHodApprovalForm,request);
		flag=transaction.update(syllabusEntry);
		return flag;
	}
	/**
	 * save data by hod
	 * @param syllabusEntryForHodApprovalForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean saveByHod(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
			HttpServletRequest request) throws Exception{
		boolean flag=false;
		SyllabusEntry syllabusEntry=syllabusEntryHodHelper.convertFormToAdminSaveBo(syllabusEntryForHodApprovalForm,request);
		if(syllabusEntryForHodApprovalForm.getId()>0){
			flag=transaction.update(syllabusEntry);
		}else{
			flag=transaction.save(syllabusEntry);
		}
		return flag;
	}
	/**
	 * save program details
	 * @param syllabusEntryForHodApprovalForm
	 * @param request 
	 * @throws Exception
	 */
	public boolean saveProgramDetails(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm, HttpServletRequest request) throws Exception{
		boolean flag=false;
		String course="";
		//get the course which program details we need to save
		Integer courseId=Integer.parseInt(syllabusEntryForHodApprovalForm.getCourseId());
		List<SyllabusEntryHodApprovalTo> list=syllabusEntryForHodApprovalForm.getList();
		SyllabusEntryProgramDetailsTo programTo=null;
		// I am getting program details by courseId
		for (SyllabusEntryHodApprovalTo syllabusEntryHodApprovalTo : list) {
			if(Integer.parseInt(syllabusEntryHodApprovalTo.getCourseId())==courseId){
				programTo=syllabusEntryHodApprovalTo.getProgramTo();
				course=syllabusEntryHodApprovalTo.getCourseName();
			}
		}
		if(programTo!=null){
			if(programTo.getAssesmentPattern()==null || programTo.getAssesmentPattern().trim().isEmpty()
					&& programTo.getExaminationAndAssesments()==null ||programTo.getExaminationAndAssesments().trim().isEmpty()
					&& programTo.getDepartmentOverview()==null || programTo.getDepartmentOverview().trim().isEmpty()
					&& programTo.getIntroductionProgramme()==null || programTo.getIntroductionProgramme().trim().isEmpty()
					&& programTo.getMissionStatement()==null || programTo.getMissionStatement().trim().isEmpty()
					&& programTo.getProgramObjective()==null || programTo.getProgramObjective().trim().isEmpty()){
				
				request.setAttribute("program",course);
				throw new Exception();
			}else {
				//start
				SyllabusEntryProgramDetails syllabusEntryProgramDetails=new SyllabusEntryProgramDetails();
				syllabusEntryProgramDetails.setBatchYear(Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()));
				syllabusEntryProgramDetails.setAssesmentPattern(programTo.getAssesmentPattern().trim());
				syllabusEntryProgramDetails.setExaminationAndAssesments(programTo.getExaminationAndAssesments().trim());
				syllabusEntryProgramDetails.setDepartmentOverview(programTo.getDepartmentOverview().trim());
				syllabusEntryProgramDetails.setIntroductionProgramme(programTo.getIntroductionProgramme().trim());
				syllabusEntryProgramDetails.setProgramObjective(programTo.getProgramObjective().trim());
				syllabusEntryProgramDetails.setMissionStatement(programTo.getMissionStatement().trim());
				Course course1=new Course();
				course1.setId(courseId);
				syllabusEntryProgramDetails.setCourseId(course1);
				syllabusEntryProgramDetails.setIsActive(true);
				syllabusEntryProgramDetails.setCreatedBy(syllabusEntryForHodApprovalForm.getUserId());
				syllabusEntryProgramDetails.setModifiedBy(syllabusEntryForHodApprovalForm.getUserId());
				syllabusEntryProgramDetails.setCreatedDate(new Date());
				syllabusEntryProgramDetails.setLastModifiedDate(new Date());
				//end
				flag=transactions.saveProgramDetails(syllabusEntryProgramDetails);
			}
		}
		return flag;
	
	}
	public void setDataForPreview(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		List<Integer> subjIds=transactions.getSubjectsByCOurse(Integer.parseInt(syllabusEntryForHodApprovalForm.getCourseId()),
				Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()));
		//get program details by course and batch
		SyllabusEntryProgramDetails syllabusEntryProgramDetails=transactions.getProgramDetailsByBatchAndCourse(Integer.parseInt(syllabusEntryForHodApprovalForm.getCourseId()),
				Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()));
		//set program details to form
		syllabusEntryHodHelper.convertProgramDetailsToForm(syllabusEntryForHodApprovalForm,syllabusEntryProgramDetails);
		//get the subjects for course from syllabus entry had done
		List<SyllabusEntry> list=transaction.getSyllabusEntries(subjIds,syllabusEntryForHodApprovalForm.getBatchYear());
		//set to form
		if(list!=null && !list.isEmpty()){
			syllabusEntryHodHelper.convertBosToTos(syllabusEntryForHodApprovalForm,list);
		}
		if(syllabusEntryForHodApprovalForm.getDepartmentId()!=null){
			Department department=DepartmentEntryTransactionImpl.getInstance().getDepartmentdetails(Integer.parseInt(syllabusEntryForHodApprovalForm.getDepartmentId()));
			syllabusEntryForHodApprovalForm.setDepartmentName(department.getName());
		}
	}
	public void setDataForPreviewForSubject(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{

		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForHodApprovalForm.getBatchYear()),Integer.parseInt(syllabusEntryForHodApprovalForm.getSubjectId()));
		if(syllabusEntry!=null){
			syllabusEntryHodHelper.setToFormForPreview(syllabusEntry,syllabusEntryForHodApprovalForm);
		}
		if(syllabusEntryForHodApprovalForm.getDepartmentId()!=null){
			Department department=DepartmentEntryTransactionImpl.getInstance().getDepartmentdetails(Integer.parseInt(syllabusEntryForHodApprovalForm.getDepartmentId()));
			syllabusEntryForHodApprovalForm.setDepartmentName(department.getName());
		}
		
	
	}
	public void previousSyllabusEntryBySubjectAndYear(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		SyllabusEntry syllabusEntry=transaction.getSyllabusEntryStatus(Integer.parseInt(syllabusEntryForHodApprovalForm.getPreviousYear()),Integer.parseInt(syllabusEntryForHodApprovalForm.getPreviousYearSubjectId()));
		if(syllabusEntry!=null){
			syllabusEntryHodHelper.setToFormPreviousSyllabusEntry(syllabusEntry,syllabusEntryForHodApprovalForm);
		}
	
	}
}
