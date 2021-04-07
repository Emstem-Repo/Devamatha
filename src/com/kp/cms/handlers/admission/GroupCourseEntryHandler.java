package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CCGroup;
import com.kp.cms.bo.admin.CCGroupCourse;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.forms.admission.GroupCourseEntryForm;
import com.kp.cms.helpers.admission.GroupCourseEntryHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.PropertyUtil;

public class GroupCourseEntryHandler {
	/**
	 * Singleton object of GroupCourseEntryHandler
	 */
	private static volatile GroupCourseEntryHandler groupCourseEntryHandler = null;
	private static final Log log = LogFactory.getLog(GroupCourseEntryHandler.class);
	private GroupCourseEntryHandler() {
		
	}
	/**
	 * return singleton object of GroupCourseEntryHandler.
	 * @return
	 */
	public static GroupCourseEntryHandler getInstance() {
		if (groupCourseEntryHandler == null) {
			groupCourseEntryHandler = new GroupCourseEntryHandler();
		}
		return groupCourseEntryHandler;
	}
	/**
	 * @param groupCourseEntryForm
	 * @throws Exception
	 */
	public void getListOfCourse(GroupCourseEntryForm groupCourseEntryForm) throws Exception {
		log.info("Entered into getListOfCourse");
		String groupCourseQuery=" from CCGroupCourse c where c.isActive=1 and c.ccGroup.id="+groupCourseEntryForm.getGroupId();
		INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
		List<CCGroupCourse> bolist=txn.getDataForQuery(groupCourseQuery);
		String groupCourseOther="select c.course.id from CCGroupCourse c where c.isActive=1 and c.ccGroup.id!="+groupCourseEntryForm.getGroupId();
		List<Integer> courseIds=txn.getDataForQuery(groupCourseOther);
		StringBuffer query=new StringBuffer("from Course c where c.isActive=1 and (c.onlyForApplication is null or c.onlyForApplication=0)");
		if(groupCourseEntryForm.getProgramTypeIds()!=null && groupCourseEntryForm.getProgramTypeIds().length>0){
			query.append(" and c.program.programType.id in (");
			for (int i = 0; i < groupCourseEntryForm.getProgramTypeIds().length; i++) {
				if(i==0)
					query.append(groupCourseEntryForm.getProgramTypeIds()[i]);
				else
					query.append(","+groupCourseEntryForm.getProgramTypeIds()[i]);
			}
			query.append(")");
		}
		
		List<Course> courseList=txn.getDataForQuery(query.toString());
		GroupCourseEntryHelper.getInstance().convertBoListtoToList(bolist,courseList,groupCourseEntryForm,courseIds);
		
		log.info("Exit from getListOfCourse");
		
	}
	/**
	 * @param groupCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean addGroupCourse(GroupCourseEntryForm groupCourseEntryForm) throws Exception {
		List<CourseTO> list=groupCourseEntryForm.getCourseList();
		List<CCGroupCourse> bos=new ArrayList<CCGroupCourse>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			CourseTO to= (CourseTO) iterator.next();
			if(to.getChecked1()!=null && !to.getChecked1().isEmpty() && to.getChecked1().equalsIgnoreCase("on")){
				CCGroupCourse bo=new CCGroupCourse();
				Course course=new Course();
				course.setId(to.getId());
				bo.setCourse(course);
				CCGroup group=new CCGroup();
				group.setId(Integer.parseInt(groupCourseEntryForm.getGroupId()));
				bo.setCcGroup(group);
				bo.setCreatedBy(groupCourseEntryForm.getUserId());
				bo.setModifiedBy(groupCourseEntryForm.getUserId());
				bo.setCreatedDate(new Date());
				bo.setLastModifiedDate(new Date());
				bo.setIsActive(true);
				bos.add(bo);
			}
			
		}
		return PropertyUtil.getInstance().saveList(bos);
	}
	/**
	 * @param groupCourseEntryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean deleteGroupCourse(GroupCourseEntryForm groupCourseEntryForm, String mode) throws Exception {
		return PropertyUtil.getInstance().deleteOrReactivate("CCGroupCourse",groupCourseEntryForm.getId(), "delete",groupCourseEntryForm.getUserId());
	}
}
