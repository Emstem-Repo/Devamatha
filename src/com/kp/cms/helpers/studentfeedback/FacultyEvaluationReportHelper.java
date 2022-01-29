package com.kp.cms.helpers.studentfeedback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.studentfeedback.EvaFacultyFeedbackQuestion;
import com.kp.cms.bo.studentfeedback.EvaStudentFeedbackQuestion;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.FacultyEvaluationReportTo;

public class FacultyEvaluationReportHelper {
	
	public static volatile FacultyEvaluationReportHelper facultyEvaluationReportHelper = null;
	public static FacultyEvaluationReportHelper getInstance()
    {
        if(facultyEvaluationReportHelper == null)
        {
        	facultyEvaluationReportHelper = new FacultyEvaluationReportHelper();
            return facultyEvaluationReportHelper;
        } else
        {
            return facultyEvaluationReportHelper;
        }
    }
	public FacultyEvaluationReportTo convertBotoTo(List<Object[]> details){
		FacultyEvaluationReportTo to = new FacultyEvaluationReportTo();
		if(details!=null)
		{
			Iterator<Object[]> itr = details.iterator();
			while(itr.hasNext())
			{
				Object[] data = itr.next();
				if(data[0]!=null)
					to.setSemester(Integer.parseInt(data[0].toString()));
				if(data[1]!=null)
					to.setProgramme(data[1].toString());
				if(data[2]!=null)
					to.setTeacherName(data[2].toString());
				if(data[3]!=null)
					to.setCourseName(data[3].toString());
				if(data[4]!=null)
					to.setClassName(data[4].toString());
				if(data[5]!=null)
					to.setSubject(data[5].toString());				
			}			
		}
		return to;
	}	
}
