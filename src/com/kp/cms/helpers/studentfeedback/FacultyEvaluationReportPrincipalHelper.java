package com.kp.cms.helpers.studentfeedback;

import java.util.Iterator;
import java.util.List;

import com.kp.cms.to.studentfeedback.FacultyEvaluationReportTo;

public class FacultyEvaluationReportPrincipalHelper {

	
	public static volatile FacultyEvaluationReportPrincipalHelper facultyEvaluationReportPrincipalHelper = null;
	public static FacultyEvaluationReportPrincipalHelper getInstance()
    {
        if(facultyEvaluationReportPrincipalHelper == null)
        {
        	facultyEvaluationReportPrincipalHelper = new FacultyEvaluationReportPrincipalHelper();
            return facultyEvaluationReportPrincipalHelper;
        } else
        {
            return facultyEvaluationReportPrincipalHelper;
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
