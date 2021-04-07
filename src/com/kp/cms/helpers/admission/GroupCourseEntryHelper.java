package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CCGroupCourse;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.forms.admission.GroupCourseEntryForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.CCGroupCourseTo;
import com.kp.cms.to.admission.CCGroupTo;

public class GroupCourseEntryHelper {
	/**
	 * Singleton object of GroupCourseEntryHelper
	 */
	private static volatile GroupCourseEntryHelper groupCourseEntryHelper = null;
	private static final Log log = LogFactory.getLog(GroupCourseEntryHelper.class);
	private GroupCourseEntryHelper() {
		
	}
	/**
	 * return singleton object of GroupCourseEntryHelper.
	 * @return
	 */
	public static GroupCourseEntryHelper getInstance() {
		if (groupCourseEntryHelper == null) {
			groupCourseEntryHelper = new GroupCourseEntryHelper();
		}
		return groupCourseEntryHelper;
	}
	/**
	 * @param bolist
	 * @param courseList
	 * @param groupCourseEntryForm
	 * @throws Exception
	 */
	public void convertBoListtoToList(List<CCGroupCourse> bolist, List<Course> courseList,GroupCourseEntryForm groupCourseEntryForm,List<Integer> courseIds) throws Exception {
		Map<Integer,CCGroupCourseTo> map=new HashMap<Integer, CCGroupCourseTo>();
		if(bolist!=null && !bolist.isEmpty()){
			Iterator<CCGroupCourse> itr=bolist.iterator();
			CCGroupCourseTo to=null;
			while (itr.hasNext()) {
				CCGroupCourse bo =itr.next();
				if(bo.getIsActive()){
					to=new CCGroupCourseTo();
					to.setId(bo.getId());
					to.setCourseId(bo.getCourse().getId());
					to.setCourseName(bo.getCourse().getName());
					to.setGroupId(bo.getCcGroup().getId());
					to.setGroupName(bo.getCcGroup().getName());
					to.setProgramName(bo.getCourse().getProgram().getName());
					map.put(bo.getCourse().getId(),to);
				}
			}
		}
		List<CourseTO> courseToList=new ArrayList<CourseTO>();
		if(courseList!=null && !courseList.isEmpty()){
			Iterator<Course> itr=courseList.iterator();
			CourseTO to=null;
			while (itr.hasNext()) {
				Course bo=itr.next();
				if(!map.containsKey(bo.getId()) && !courseIds.contains(bo.getId())){
					to=new CourseTO();
					to.setId(bo.getId());
					to.setName(bo.getName());
					to.setProgramTypeCode(bo.getProgram().getProgramType().getName());
					to.setProgramCode(bo.getProgram().getName());
					courseToList.add(to);
				}
			}
		}
		Collections.sort(courseToList);
		groupCourseEntryForm.setCourseList(courseToList);
		List<CCGroupCourseTo> list=new ArrayList<CCGroupCourseTo>(map.values());
		Collections.sort(list);
		groupCourseEntryForm.setGroupCourseToList(list);
	}
}
