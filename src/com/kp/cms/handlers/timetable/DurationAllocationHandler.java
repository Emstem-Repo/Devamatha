package com.kp.cms.handlers.timetable;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseScheme;
import com.kp.cms.bo.admin.DurationAllocationBo;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.timetable.DurationAllocationHelper;
import com.kp.cms.to.timetable.DurationAllocationTo;
import com.kp.cms.transactionsimpl.timetable.DurationAllocationImpl;

public class DurationAllocationHandler extends ExamGenHandler {

	/**
	 * @Date 03 Nov 2009 This handler class for Cash Collection Management
	 * 
	 */
	private static final Log log = LogFactory
			.getLog(DurationAllocationHandler.class);

	private static volatile DurationAllocationHandler durationAllocation;
	private static DurationAllocationHelper helper = DurationAllocationHelper
			.getInstance();
	private static DurationAllocationImpl impl = DurationAllocationImpl
			.getInstance();

	private DurationAllocationHandler() {

	}

	public static DurationAllocationHandler getinstance() {
		if (durationAllocation == null) {
			durationAllocation = new DurationAllocationHandler();
			return durationAllocation;
		}
		return durationAllocation;

	}

	public ArrayList<DurationAllocationTo> fetchData(int academicYear,
			int courseId, String userId) {
		ArrayList<Integer> listOfSubjectIds = impl
				.selectSubjectsByYearAndCourse(academicYear, courseId);
		return helper.convertBoListtoToList(impl
				.getAllSubjectsData(listOfSubjectIds), getSchemeNoByCourseId(
				courseId, academicYear));
	}

	public int addData(ArrayList<DurationAllocationTo> bottomGrid,
			int academicYr, int courseId, String userId) {
		DurationAllocationBo bo = null;
		ArrayList<DurationAllocationBo> insertList = new ArrayList<DurationAllocationBo>();
		ArrayList<DurationAllocationBo> updateList = new ArrayList<DurationAllocationBo>();
		Course course;
		Subject subject;
		CourseScheme courseScheme;
		for (DurationAllocationTo to : bottomGrid) {
			bo = new DurationAllocationBo();
			bo.setAcademicYear(academicYr);
			bo.setAcademicYearId(0);
			course = new Course();
			course.setId(courseId);
			bo.setCourseId(course);
			subject = new Subject();
			subject.setId(to.getSubjectId());
			bo.setSubjectId(subject);
			courseScheme = new CourseScheme();
			courseScheme.setId(to.getSchemeNo());
			bo.setCourseSchemeId(courseScheme);
			bo.setCreatedBy(userId);
			bo.setModifiedBy(userId);
			bo.setCreatedDate(to.getCreatedDate());
			bo.setLastModifiedDate(to.getLastModifiedDate());
			bo.setMinimumLectureHours(Integer.toString(to
					.getMinimumLectureHours()));
			bo.setMaximumLectureHours(Integer.toString(to
					.getMaximumLectureHours()));
			if (to.getId() != null) {
				bo.setId(to.getId());
				updateList.add(bo);
			} else {
				insertList.add(bo);
			}
		}
		impl.update_List(updateList);
		return impl.insert_List(insertList);
	}

}
