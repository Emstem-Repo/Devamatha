package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.SubjectGroupSubjectsTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.SubjectTO;

public class ListofSubjectGroupHelper {
	private static final Log log = LogFactory.getLog(ListofSubjectGroupHelper.class);
	private static volatile ListofSubjectGroupHelper listofSubjectGroupHelper;
	public static ListofSubjectGroupHelper getInstance() {
		if (listofSubjectGroupHelper == null) {
			listofSubjectGroupHelper = new ListofSubjectGroupHelper();
			return listofSubjectGroupHelper;
		}
		return listofSubjectGroupHelper;
	}

	/**
	 * 
	 * @param subjectGroupList
	 * @return
	 * @throws Exception
	 */
	public List<SubjectGroupTO> copyBosToTO(List<SubjectGroup> subjectGroupList) throws Exception {
		log.debug("inside copyBosToTO");
		List<SubjectGroupTO> subGroupToList = new ArrayList<SubjectGroupTO>();
		Iterator<SubjectGroup> subGroupItr = subjectGroupList.iterator();
		while (subGroupItr.hasNext()){
			SubjectGroup subjectGroup = subGroupItr.next();
			SubjectGroupTO subjectGroupTO = new SubjectGroupTO();
			if(subjectGroup.getName()!=null){
				subjectGroupTO.setName(subjectGroup.getName());
			}
			if(subjectGroup.getCourse()!=null && subjectGroup.getCourse().getId()!=0){
				if(subjectGroup.getCourse().getName()!=null){
					CourseTO courseTO = new CourseTO();
					courseTO.setName(subjectGroup.getCourse().getName());
					subjectGroupTO.setCourseTO(courseTO);
				}
			}
			if(subjectGroup.getSubjectGroupSubjectses()!=null && !subjectGroup.getSubjectGroupSubjectses().isEmpty()){
				Set<SubjectGroupSubjects> subjectGoupSubjectSet = subjectGroup.getSubjectGroupSubjectses();
				List<SubjectGroupSubjectsTO>subjectGoupSubjectTOList = new ArrayList<SubjectGroupSubjectsTO>();
				Iterator<SubjectGroupSubjects> iterator = subjectGoupSubjectSet.iterator();
				while (iterator.hasNext()) {
					SubjectGroupSubjects subjectGroupSubjects = iterator.next();
					SubjectGroupSubjectsTO subjectGroupSubjectsTO = new SubjectGroupSubjectsTO();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getId()!=0){
						SubjectTO subjectTO = new SubjectTO();
						subjectTO.setName(subjectGroupSubjects.getSubject().getName());
						subjectTO.setCode(subjectGroupSubjects.getSubject().getCode());
						subjectGroupSubjectsTO.setSubjectTo(subjectTO);
					}
					subjectGoupSubjectTOList.add(subjectGroupSubjectsTO);
				}
				Collections.sort(subjectGoupSubjectTOList);
				subjectGroupTO.setSubjectGroupSubjectsTOList(subjectGoupSubjectTOList);
			}
			subGroupToList.add(subjectGroupTO);
		}
		log.debug("exit copyBosToTO");
		return subGroupToList;
	}
}
