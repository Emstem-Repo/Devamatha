package com.kp.cms.handlers.timetable;

import com.kp.cms.forms.timetable.ClassWiseTimeTableViewForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.timetable.ClassWiseTimeTableViewHelper;
import com.kp.cms.transactionsimpl.timetable.ClassWiseTimeTableViewImpl;

public class ClassWiseTimeTableViewHandler extends ExamGenHandler{

	private static volatile ClassWiseTimeTableViewHandler classWiseHandler;
	private static ClassWiseTimeTableViewHelper helper = ClassWiseTimeTableViewHelper.getInstance();
	private static ClassWiseTimeTableViewImpl impl = ClassWiseTimeTableViewImpl.getInstance();
	
	public static ClassWiseTimeTableViewHandler getinstance() {
		if (classWiseHandler == null) {
			classWiseHandler = new ClassWiseTimeTableViewHandler();
			return classWiseHandler;
		}
		return classWiseHandler;

	}
	
	public void fetchData(ClassWiseTimeTableViewForm formObj)
	{
		helper.convertBoListToTimeTableViewToList(impl.getClassTimeTable(Integer.parseInt(formObj.getAcademicClass()),formObj.getAcademicYr()),formObj);
	}
}
