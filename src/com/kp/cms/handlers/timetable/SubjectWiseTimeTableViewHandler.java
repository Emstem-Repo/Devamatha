package com.kp.cms.handlers.timetable;

import com.kp.cms.forms.timetable.SubjectWiseTimeTableViewForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.timetable.SubjectWiseTimeTableViewHelper;
import com.kp.cms.transactionsimpl.timetable.SubjectWiseTimeTableViewImpl;

public class SubjectWiseTimeTableViewHandler extends ExamGenHandler{

	private static volatile SubjectWiseTimeTableViewHandler classWiseHandler;
	private static SubjectWiseTimeTableViewHelper helper = SubjectWiseTimeTableViewHelper.getInstance();
	private static SubjectWiseTimeTableViewImpl impl = SubjectWiseTimeTableViewImpl.getInstance();
	
	public static SubjectWiseTimeTableViewHandler getinstance() {
		if (classWiseHandler == null) {
			classWiseHandler = new SubjectWiseTimeTableViewHandler();
			return classWiseHandler;
		}
		return classWiseHandler;

	}
	
	public void fetchData(SubjectWiseTimeTableViewForm formObj)
	{
		helper.convertBoListToTimeTableViewToList(impl.getSubjectWiseTimeTable(Integer.parseInt(formObj.getSubject()),formObj.getAcademicYr()),formObj);
	}
}
