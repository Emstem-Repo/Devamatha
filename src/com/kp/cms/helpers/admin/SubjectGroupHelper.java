package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.forms.admin.SubjectGroupEntryForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectGroupSubjectsTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;
import com.kp.cms.utilities.SubjectGroupDetailsComparator;
import com.kp.cms.utilities.SubjectGroupNameComparator;

public class SubjectGroupHelper {
	public static volatile SubjectGroupHelper subjectGroupHelper = null;

	public static SubjectGroupHelper getInstance() {
		if (subjectGroupHelper == null) {
			subjectGroupHelper = new SubjectGroupHelper();
			return subjectGroupHelper;
		}
		return subjectGroupHelper;
	}

	/**
	 * 
	 * @param list1
	 * @return A list of all the subjectgroup details and converts
	 *         SUbjectGroupBO to SubjectGroupTO
	 */
	public List<SubjectGroupTO> populateSubjectGroupBOtoTo(
			List<SubjectGroup> list1) {
		List<SubjectGroupTO> subjectGroupList = new ArrayList<SubjectGroupTO>();
		Set<Integer> subsSet=new HashSet<Integer>();
		SubjectGroup subjectGroup;
		SubjectGroupTO subjectGroupTO;
		Iterator<SubjectGroup> iterator = list1.iterator();
		while (iterator.hasNext()) {
			subjectGroupTO = new SubjectGroupTO();
			subjectGroup = iterator.next();
			subjectGroupTO.setName(subjectGroup.getName());
			subjectGroupTO.setId(subjectGroup.getId());
			subjectGroupTO.setIsCommonSubGrp(subjectGroup.getIsCommonSubGrp());
			if(!subsSet.contains(subjectGroup.getId())){
				subjectGroupList.add(subjectGroupTO);
				subsSet.add(subjectGroup.getId());
			}

		}
		Collections.sort(subjectGroupList, new SubjectGroupNameComparator());
		return subjectGroupList;
	}

	/**
	 * This method is used to convert Subject BO to TO.
	 * 
	 * @param sublist
	 * @return list of type TO.
	 */

	public List<SubjectTO> getSubjectDetails(List<Subject> sublist) {
		List<SubjectTO> subjectList = new ArrayList<SubjectTO>();

		Iterator<Subject> iterator = sublist.iterator();
		SubjectTO subjectTO = null;
		while (iterator.hasNext()) {
			Subject subject = (Subject) iterator.next();
			subjectTO = new SubjectTO();
			subjectTO.setId(subject.getId());
			subjectTO.setName(subject.getName());
			subjectTO.setCode(subject.getCode());
			if(subject.getIsTheoryPractical()!=null){
				String theoryOrPractical=null;
				if(subject.getIsTheoryPractical().equalsIgnoreCase("T")){
				theoryOrPractical="Thoery";
					}else if(subject.getIsTheoryPractical().equalsIgnoreCase("P")){
				theoryOrPractical="Practical";
					}else if(subject.getIsTheoryPractical().equalsIgnoreCase("B")){
				theoryOrPractical="Both";
				}
				subjectTO.setTheoryPractical(theoryOrPractical);
			}
			subjectList.add(subjectTO);
		}
			
		Collections.sort(subjectList,new SubjectGroupDetailsComparator());
		return subjectList;
	}

	/**
	 * This method is used to convert BO to TO
	 * 
	 * @param subGrouplist
	 * @return list of type SubjectGroupTO.
	 */

	public List<SubjectGroupTO> convertBOtoTO(List<SubjectGroup> subGrouplist) {
		List<SubjectGroupTO> list = new ArrayList<SubjectGroupTO>();
		Iterator<SubjectGroup> iterator = subGrouplist.iterator();

		SubjectGroupTO subjectGroupTO;

		CourseTO courseTo;
		ProgramTypeTO programTypeTo;
		ProgramTO programTo;
		SubjectTO subjectTo;
		SubjectGroupSubjectsTO groupSubjectsTO;
		Set<SubjectGroupSubjectsTO> subjectGroupSubjectsTO;
		while (iterator.hasNext()) {
			SubjectGroup subjectGroup = (SubjectGroup) iterator.next();
			courseTo = new CourseTO();
			programTypeTo = new ProgramTypeTO();
			programTo = new ProgramTO();

			programTypeTo.setProgramTypeId(subjectGroup.getCourse()
					.getProgram().getProgramType().getId());
			if (subjectGroup.getCourse().getProgram().getProgramType()
					.getName() != null
					&& subjectGroup.getCourse().getProgram().getProgramType()
							.getName().length() < 30) {
				programTypeTo.setProgramTypeName(subjectGroup.getCourse()
						.getProgram().getProgramType().getName());
			} else {
				programTypeTo.setProgramTypeName(splitString(subjectGroup
						.getCourse().getProgram().getProgramType().getName()));
			}

			programTo.setId(subjectGroup.getCourse().getProgram().getId());
			if (subjectGroup.getCourse().getProgram().getName() != null
					&& subjectGroup.getCourse().getProgram().getName().length() < 30) {
				programTo.setName(subjectGroup.getCourse().getProgram()
						.getName());
			} else {
				programTo.setName(splitString(subjectGroup.getCourse()
						.getProgram().getName()));
			}
			programTo.setProgramTypeTo(programTypeTo);

			courseTo.setId(subjectGroup.getCourse().getId());
			if (subjectGroup.getCourse().getName() != null
					&& subjectGroup.getCourse().getName().length() < 30) {
				courseTo.setName(subjectGroup.getCourse().getName());
			} else {
				courseTo
						.setName(splitString(subjectGroup.getCourse().getName()));
			}

			courseTo.setProgramTo(programTo);
			subjectGroupTO = new SubjectGroupTO();
			subjectGroupTO.setCourseTO(courseTo);
			subjectGroupTO.setId(subjectGroup.getId());
			if (subjectGroup.getName() != null
					&& subjectGroup.getName().length() < 30) {
				subjectGroupTO.setName(subjectGroup.getName());
			} else {
				subjectGroupTO.setName(splitString(subjectGroup.getName()));
			}
			//*********
			if(subjectGroup.getCourse() != null && subjectGroup.getCourse().getProgram() != null && subjectGroup.getCourse().getProgram().getProgramType() != null && subjectGroup.getCourse().getProgram().getProgramType().getName() != null){
				if(!subjectGroup.getCourse().getProgram().getProgramType().getName().isEmpty()){
					subjectGroupTO.setProgramTypeName(subjectGroup.getCourse().getProgram().getProgramType().getName());
				}
			}
			if(subjectGroup.getCourse() != null && subjectGroup.getCourse().getProgram() != null && subjectGroup.getCourse().getProgram().getProgramType() != null && subjectGroup.getCourse().getProgram().getProgramType().getId() != 0){
				subjectGroupTO.setProgramTypeId(subjectGroup.getCourse().getProgram().getProgramType().getId());
			}
			if(subjectGroup.getCourse() != null && subjectGroup.getCourse().getProgram() != null && subjectGroup.getCourse().getProgram().getName() != null ){
				subjectGroupTO.setProgramName(subjectGroup.getCourse().getProgram().getName());
			}
			if(subjectGroup.getCourse() != null && subjectGroup.getCourse().getName() != null ){
				subjectGroupTO.setCourseName(subjectGroup.getCourse().getName());
			}
			if(subjectGroup.getCourse() != null && subjectGroup.getCourse().getProgram() != null && subjectGroup.getCourse().getProgram().getId() != 0 ){
				subjectGroupTO.setProgramId(subjectGroup.getCourse().getProgram().getId());
			}
			if(subjectGroup.getCourse() != null && subjectGroup.getCourse().getId()!=0 ){
				subjectGroupTO.setCourseId(subjectGroup.getCourse().getId());
			}
			//***********
			// Getting SubjectGroupSubjects BO from SubjectGroup BO
			Set<SubjectGroupSubjects> subjectGroupSubjectSet = subjectGroup
					.getSubjectGroupSubjectses();
			subjectGroupSubjectsTO = new HashSet<SubjectGroupSubjectsTO>();
			Iterator<SubjectGroupSubjects> iterator1 = subjectGroupSubjectSet
					.iterator();
			groupSubjectsTO = new SubjectGroupSubjectsTO();
			subjectTo = new SubjectTO();
			String appendedString = "";
			StringBuffer buffer = new StringBuffer();
			String temp = "";
			int count = 0;
			while (iterator1.hasNext()) {
				SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) iterator1
						.next();

				if (subjectGroupSubjects.getSubject() != null
						&& subjectGroupSubjects.getIsActive() == true) {
					groupSubjectsTO.setId(subjectGroupSubjects.getId());

					if (count < 3) {
						appendedString = buffer.append(
								subjectGroupSubjects.getSubject().getCode())
								.append("(").append(
										subjectGroupSubjects.getSubject()
												.getName()).append(")").append(
										",").toString();
						// appendedString = appendedString
						// + subjectGroupSubjects.getSubject().getName() + "("
						// + subjectGroupSubjects.getSubject().getCode() + ")"
						// + ",";
					} else {
						appendedString = buffer.append("\n").append(
								subjectGroupSubjects.getSubject().getCode())
								.append("(").append(
										subjectGroupSubjects.getSubject()
												.getName()).append(")").append(
										",").toString();
						// appendedString = appendedString + "\n"
						// + subjectGroupSubjects.getSubject().getName() + "("
						// + subjectGroupSubjects.getSubject().getCode() + ")"
						// + ",";
						count = 0;
					}
					count++;
				}

				groupSubjectsTO.setSubjectTo(subjectTo);
				subjectGroupSubjectsTO.add(groupSubjectsTO);
				
			}

			// setting subjectTO to SubjectGroupSubjectsTO
			subjectGroupTO.setSubjectGroupSubjectsTO(groupSubjectsTO);
			// setting SubjectGroupSubjectsTO to SubjectGroupTO
			if (appendedString.endsWith(",")) {
				temp = StringUtils.chop(appendedString);
			}
			subjectGroupTO.setSubjectNames(temp); // removing last comma from
			// given string.
			subjectGroupTO.setSubjectGroupSubjectsetTO(subjectGroupSubjectsTO);
			list.add(subjectGroupTO);
		}
		Collections.sort(list,new SubjectGroupNameComparator());
		return list;
	}

	/**
	 * This method is used to split the string.
	 * 
	 * @param value
	 * @return string value.
	 */

	public String splitString(String value) {
		StringBuffer appendedvalue = new StringBuffer();
		int length = value.length();
		String[] temp = new String[length];
		int begindex = 0, endindex = 30;
		int count = 0;

		while (true) {
			if (endindex < length) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + 30;
				endindex = endindex + 30;
				appendedvalue.append(temp[count]).append(" ");
						
			} else {
				if (count == 0) {
					temp[count] = value.substring(0, length);
				}
				temp[count] = value.substring(begindex, length);
				appendedvalue.append(temp[count]);
				break;
			}
			count++;
		}
		return appendedvalue.toString();
	}

	/**
	 * This method is used to convert form to BO
	 * 
	 * @param subjectGroupEntryForm
	 * @return SubjectGroup BO instance.
	 */

	public SubjectGroup convertFormtoBO(
			SubjectGroupEntryForm subjectGroupEntryForm) {

		Course course;
		Program program;
		ProgramType programType;
		SubjectGroup subjectGroup = new SubjectGroup();

		course = new Course();
		course.setId(Integer.parseInt(subjectGroupEntryForm.getCourseId()));
		program = new Program();
		program.setId(Integer.parseInt(subjectGroupEntryForm.getProgramId()));

		programType = new ProgramType();
		programType.setId(Integer.parseInt(subjectGroupEntryForm
				.getProgramTypeId()));

		program.setProgramType(programType);
		course.setProgram(program);
		subjectGroup.setCourse(course);
		subjectGroup.setIsActive(true);
		subjectGroup.setCreatedBy(subjectGroupEntryForm.getUserId());
		subjectGroup.setModifiedBy(subjectGroupEntryForm.getUserId());
		subjectGroup.setCreatedDate(new Date());
		subjectGroup.setName(subjectGroupEntryForm.getSubjectGroupName());
		try {
			if (subjectGroupEntryForm.getCommonSubjectGroup() != null) {
				subjectGroup.setIsCommonSubGrp("on"
						.equalsIgnoreCase(subjectGroupEntryForm
								.getCommonSubjectGroup()) ? true : false);
			} else {
				subjectGroup.setIsCommonSubGrp(false);
			}

			if (subjectGroupEntryForm.getSecondLanguageId() > 0) {
				subjectGroup.setSecondLanguageId(subjectGroupEntryForm
						.getSecondLanguageId());
			}

		} catch (Exception e) {
		}

		// getting the multiple selected values from form.
		String movedSubjects[] = subjectGroupEntryForm
				.getMovedSubjectsTORight();
		// int st[] = new int[movedSubjects.length];
		// for(int i=0;i<movedSubjects.length;i++) {
		// if(map.containsValue(movedSubjects[i])) {
		// Iterator keyvalues = map.entrySet().iterator();
		// for (int j = 0; j < movedSubjects.length; j++)
		// {
		// Map.Entry entry = (Map.Entry) keyvalues.next();
		// Object key = entry.getKey();
		// Object value = entry.getValue();
		// st[j] = (Integer)key;
		// }
		// }
		// }
		// creating set of type SubjectGroupSubjects.
		Set<SubjectGroupSubjects> subjectGroupSubjectsSet = new HashSet<SubjectGroupSubjects>();
		Subject subject;
		SubjectGroupSubjects subjectGroupSubjects;
		for (int x = 0; x < movedSubjects.length; x++) {
			subjectGroupSubjects = new SubjectGroupSubjects();
			subject = new Subject();

			subject.setId(Integer.parseInt(movedSubjects[x]));
			subject.setCreatedDate(new Date());
			subjectGroupSubjects
					.setCreatedBy(subjectGroupEntryForm.getUserId());
			subjectGroupSubjects.setModifiedBy(subjectGroupEntryForm
					.getUserId());
			subjectGroupSubjects.setCreatedDate(new Date());
			subjectGroupSubjects.setIsActive(true);
			subjectGroupSubjects.setSubject(subject);

			subjectGroupSubjects.setSubjectGroup(subjectGroup);
			// adding SubjectGroupSubjects instance to Set type.
			subjectGroupSubjectsSet.add(subjectGroupSubjects);
		}
		// adding SubjectGroupSubjects set type to SubjectGroup BO instance.
		subjectGroup.setSubjectGroupSubjectses(subjectGroupSubjectsSet);

		return subjectGroup;
	}

	/**
	 * This method is used for editing and populating to UI.
	 * 
	 * @param subjectGroup
	 * @param subjectGroupEntryForm
	 */

	public void editSubjectGroupEntry(SubjectGroup subjectGroup,
			SubjectGroupEntryForm subjectGroupEntryForm) {

		subjectGroupEntryForm.setProgramTypeId(String.valueOf((subjectGroup
				.getCourse().getProgram().getProgramType().getId())));
		subjectGroupEntryForm.setProgramId(String.valueOf(subjectGroup
				.getCourse().getProgram().getId()));
		subjectGroupEntryForm.setCourseId(String.valueOf(subjectGroup
				.getCourse().getId()));
		subjectGroupEntryForm.setSubjectGroupName(subjectGroup.getName());
		
		
		subjectGroupEntryForm.setSecondLanguageId(subjectGroup.getSecondLanguageId());
		
		
	subjectGroupEntryForm.setCommonSubjectGroup((true==subjectGroup.getIsCommonSubGrp())? "on" :"off");
		
		
		
		Set<SubjectGroupSubjects> subjectGroupSet = subjectGroup
				.getSubjectGroupSubjectses();
		
		
		Map<Integer,Integer> groupdIdMap=new HashMap<Integer, Integer>();
		String[] temp = new String[subjectGroupSet.size()];
		int count = 0;
		Iterator<SubjectGroupSubjects> iterator = subjectGroupSet.iterator();
		while (iterator.hasNext()) {
			SubjectGroupSubjects subjectGroupSubjects = (SubjectGroupSubjects) iterator
					.next();
			temp[count] = Integer.toString(subjectGroupSubjects.getId());
			groupdIdMap.put(subjectGroupSubjects.getSubject().getId(),subjectGroupSubjects.getId());
			count++;
		}
		subjectGroupEntryForm.setGroupid(temp);
		subjectGroupEntryForm.setGroupMapId(groupdIdMap);
		
		Set<Integer> subjectIds = new HashSet<Integer>();

		Iterator<SubjectGroupSubjects> iterator1 = subjectGroup
				.getSubjectGroupSubjectses().iterator();
		while (iterator1.hasNext()) {
			SubjectGroupSubjects subjectGroupSubjects = iterator1.next();
			if (subjectGroupSubjects.getSubject() != null
					&& subjectGroupSubjects.getIsActive() == true) {
				subjectIds.add(subjectGroupSubjects.getSubject().getId());
			}
		}
		Object[] objArray = subjectIds.toArray();
		String array[] = new String[objArray.length];

		Map<Integer, String> map2 = new HashMap<Integer, String>();
		Map<Integer, String> fullMap = subjectGroupEntryForm.getMapIds();
		// setting the map values.
		Map<Integer, String> map = fullMap;
		for (int i = 0; i < array.length; i++) {
			array[i] = objArray[i].toString();
			if (map != null && map.containsKey(Integer.parseInt(array[i]))) {
				map2.put(Integer.parseInt(array[i]), (String) map.get(Integer
						.parseInt(array[i])));
				map.remove(Integer.parseInt(array[i]));
			}
		}
		subjectGroupEntryForm.setSubjectsMap(map);
		subjectGroupEntryForm.setMovedSubjectsTORight(array);
		subjectGroupEntryForm.setSelectedSubjectsMap(map2);
		subjectGroupEntryForm.setOldselectedSubjects(array);
	}

	/**
	 *This method is for updated the record to database.
	 * 
	 * @param subjectGroupEntryForm
	 * @return
	 */

	public SubjectGroup updateFormtoBO(
			SubjectGroupEntryForm subjectGroupEntryForm) {

		Course course;
		Program program;
		ProgramType programType;
		SubjectGroup subjectGroup = new SubjectGroup();
		course = new Course();
		course.setId(Integer.parseInt(subjectGroupEntryForm.getCourseId()));
		program = new Program();
		program.setId(Integer.parseInt(subjectGroupEntryForm.getProgramId()));

		programType = new ProgramType();
		programType.setId(Integer.parseInt(subjectGroupEntryForm
				.getProgramTypeId()));

		program.setProgramType(programType);
		course.setProgram(program);
		subjectGroup.setCourse(course);
		subjectGroup.setIsActive(true);
		subjectGroup.setModifiedBy(subjectGroupEntryForm.getUserId());
		subjectGroup.setLastModifiedDate(new Date());
		subjectGroup.setName(subjectGroupEntryForm.getSubjectGroupName());
//		
		try {
			if (subjectGroupEntryForm.getCommonSubjectGroup() != null) {
				subjectGroup.setIsCommonSubGrp("on"
						.equalsIgnoreCase(subjectGroupEntryForm
								.getCommonSubjectGroup()) ? true : false);
			} else {
				subjectGroup.setIsCommonSubGrp(false);
			}

			if (subjectGroupEntryForm.getSecondLanguageId() > 0) {
				subjectGroup.setSecondLanguageId(subjectGroupEntryForm
						.getSecondLanguageId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

//		subjectGroup
//				.setIsCommonSubGrp("on".equalsIgnoreCase(subjectGroupEntryForm
//						.getCommonSubjectGroup()) ? true : false);
//		subjectGroup.setSecondLanguageId(subjectGroupEntryForm
//				.getSecondLanguageId());

		// getting the multiple selected values from form.
		String selectedSubjects[] = subjectGroupEntryForm
				.getMovedSubjectsTORight();
		// int st[] = new int[selectedSubjects.length];
		// for(int i=0;i<selectedSubjects.length;i++) {
		// if(map.containsKey(Integer.parseInt(selectedSubjects[i]))) {
		// Iterator keyvalues = map.entrySet().iterator();
		// for (int j = 0; j < i; j++)
		// {
		// Map.Entry entry = (Map.Entry) keyvalues.next();
		// Object key = entry.getKey();
		// Object value = entry.getValue();
		// st[j] = (Integer)key;
		// }
		// }
		// }
		Map<Integer,Integer> groupIdMap=subjectGroupEntryForm.getGroupMapId();
		// creating set of type SubjectGroupSubjects.
		Set<SubjectGroupSubjects> subjectGroupSubjectsSet = new HashSet<SubjectGroupSubjects>();
		Subject subject;
		ArrayList oldlist = new ArrayList();
		SubjectGroupSubjects subjectGroupSubjects;
		String[] oldids = subjectGroupEntryForm.getOldselectedSubjects();
		for (int i = 0; i < oldids.length; i++) {
			oldlist.add(Integer.parseInt(oldids[i]));
		}
		int count = 0;

		for (int x = 0; x < selectedSubjects.length; x++) {
			subjectGroupSubjects = new SubjectGroupSubjects();
			subject = new Subject();
			//Code Commented By Balaji
//			if (count < subjectgroupsubjectid.length) {
//				if (subjectgroupsubjectid[count] != null) {
//					subjectGroupSubjects.setId(Integer
//							.parseInt(subjectgroupsubjectid[count]));
//				}
//			}
			if(groupIdMap.containsKey(Integer.parseInt(selectedSubjects[x]))){
				subjectGroupSubjects.setId(groupIdMap.remove(Integer.parseInt(selectedSubjects[x])));
			}else{
				subjectGroupSubjects.setCreatedBy(subjectGroupEntryForm.getUserId());
				subjectGroupSubjects.setCreatedDate(new Date());
			}
//			alist.add(selectedSubjects[x]);
//			if (oldlist.contains(selectedSubjects[x])) {
//				// oldlist.indexOf(Integer.parseInt(selectedSubjects[x]));
//				oldlist.remove(oldlist.indexOf(selectedSubjects[x]));
//			}
			subject.setId(Integer.parseInt(selectedSubjects[x]));
			subjectGroupSubjects.setLastModifiedDate(new Date());
			subjectGroupSubjects.setModifiedBy(subjectGroupEntryForm
					.getUserId());
			subjectGroupSubjects.setSubject(subject);
			subjectGroupSubjects.setIsActive(true);
			count++;
			subjectGroupSubjects.setSubjectGroup(subjectGroup);
			// adding SubjectGroupSubjects instance to Set type.
			subjectGroupSubjectsSet.add(subjectGroupSubjects);
		}

		if(!groupIdMap.isEmpty()){
			 Iterator it = groupIdMap.entrySet().iterator();
			    while (it.hasNext()) {
			        Map.Entry pairs = (Map.Entry)it.next();
			        subject = new Subject();
					subject.setId(Integer.parseInt(pairs.getKey().toString()));
					subjectGroupSubjects = new SubjectGroupSubjects();
					subjectGroupSubjects.setId(Integer.parseInt(pairs.getValue().toString()));
					subjectGroupSubjects.setIsActive(false);
					subjectGroupSubjects.setLastModifiedDate(new Date());
					subjectGroupSubjects.setModifiedBy(subjectGroupEntryForm
							.getUserId());
					subjectGroupSubjects.setSubject(subject);
					subjectGroupSubjectsSet.add(subjectGroupSubjects);
			    }
		}
		// Code Commented By Balaji
//		if (oldids.length > selectedSubjects.length) {
//			int[] nonassigned = new int[oldlist.size()];
//			int assigned = 0;
//			for (int count2 = 0; count2 < oldlist.size(); count2++) {
//				/*
//				 * for(int count3=0;count3<alist.size();count3++) {
//				 * if(Integer.parseInt
//				 * (oldids[count2])!=(Integer)alist.get(count3)) {
//				 * nonassigned[assigned]=Integer.parseInt(oldids[count2]);
//				 * assigned++;
//				 * 
//				 * } }
//				 */
//				nonassigned[assigned] = ((Integer) oldlist.get(count2))
//						.intValue();
//				assigned++;
//			}
//			for (int count1 = 0; count1 < oldids.length
//					- selectedSubjects.length; count1++) {
//				subject = new Subject();
//
//				subject.setId(nonassigned[count1]);
//				subjectGroupSubjects = new SubjectGroupSubjects();
//				subjectGroupSubjects.setId(Integer
//						.parseInt(subjectgroupsubjectid[count]));
//				subjectGroupSubjects.setIsActive(false);
//				subjectGroupSubjects.setLastModifiedDate(new Date());
//				subjectGroupSubjects.setModifiedBy(subjectGroupEntryForm
//						.getUserId());
//				subjectGroupSubjects.setSubject(subject);
//				subjectGroupSubjectsSet.add(subjectGroupSubjects);
//				count++;
//			}
//		}
		// adding SubjectGroupSubjects set type to SubjectGroup BO instance.
		subjectGroup.setSubjectGroupSubjectses(subjectGroupSubjectsSet);

		return subjectGroup;
	}

	// Added by Shwetha 9Elements
	// To get second language list
	public ArrayList<KeyValueTO> convertBOtoTO_SecondLanguage(
			ArrayList<ExamSecondLanguageMasterBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamSecondLanguageMasterBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}
}