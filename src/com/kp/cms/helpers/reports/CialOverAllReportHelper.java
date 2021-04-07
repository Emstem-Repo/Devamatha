package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.to.reports.CiaOverAllReportTO;

public class CialOverAllReportHelper {
	private static final Log log = LogFactory.getLog(CialOverAllReportHelper.class);
	private static volatile CialOverAllReportHelper cialOverAllReportHelper = null;
	

	public static CialOverAllReportHelper getInstance() {
		if (cialOverAllReportHelper == null) {
			cialOverAllReportHelper = new CialOverAllReportHelper();
		}
		return cialOverAllReportHelper;
	}
	
	public List<CiaOverAllReportTO> convertBoListToTOList(List<ExamStudentOverallInternalMarkDetailsBO> overallBOList) throws Exception{
		log.info("Entering into convertBoListToTOList in helper");
		CiaOverAllReportTO ciaOverAllReportTO;
		Iterator<ExamStudentOverallInternalMarkDetailsBO> itr = overallBOList.iterator();
		List<CiaOverAllReportTO> overallTOList = new ArrayList<CiaOverAllReportTO>();
		while (itr.hasNext()) {
			ExamStudentOverallInternalMarkDetailsBO examStudentOverallInternalMarkDetailsBO = (ExamStudentOverallInternalMarkDetailsBO) itr
					.next();
			ciaOverAllReportTO = new CiaOverAllReportTO();
			ciaOverAllReportTO.setSubjectName(examStudentOverallInternalMarkDetailsBO.getSubjectUtilBO().getName() + "(" + examStudentOverallInternalMarkDetailsBO.getSubjectUtilBO().getCode() + ")");
			
			double theoryMarkTotal = 0;
			if(examStudentOverallInternalMarkDetailsBO.getTheoryTotalSubInternalMarks()!= null && !examStudentOverallInternalMarkDetailsBO.getTheoryTotalSubInternalMarks().trim().isEmpty()){
				theoryMarkTotal = Double.parseDouble(examStudentOverallInternalMarkDetailsBO.getTheoryTotalSubInternalMarks());
			}
			if(examStudentOverallInternalMarkDetailsBO.getTheoryTotalAttendenceMarks()!= null && !examStudentOverallInternalMarkDetailsBO.getTheoryTotalAttendenceMarks().trim().isEmpty()){
				theoryMarkTotal = theoryMarkTotal + Double.parseDouble(examStudentOverallInternalMarkDetailsBO.getTheoryTotalAttendenceMarks());
			}
			double practicalMarkTotal = 0;
			if(examStudentOverallInternalMarkDetailsBO.getPracticalTotalSubInternalMarks()!= null && !examStudentOverallInternalMarkDetailsBO.getPracticalTotalSubInternalMarks().trim().isEmpty()){
				practicalMarkTotal = Double.parseDouble(examStudentOverallInternalMarkDetailsBO.getPracticalTotalSubInternalMarks());
			}
			if(examStudentOverallInternalMarkDetailsBO.getPracticalTotalAttendenceMarks()!= null && !examStudentOverallInternalMarkDetailsBO.getPracticalTotalAttendenceMarks().trim().isEmpty()){
				practicalMarkTotal = practicalMarkTotal + Double.parseDouble(examStudentOverallInternalMarkDetailsBO.getPracticalTotalAttendenceMarks());
			}
			ciaOverAllReportTO.setTheoryMarks(Double.toString(theoryMarkTotal));
			ciaOverAllReportTO.setPracticalMarks(Double.toString(practicalMarkTotal));
			overallTOList.add(ciaOverAllReportTO);
		}
		return overallTOList;
	
		
	}
}
