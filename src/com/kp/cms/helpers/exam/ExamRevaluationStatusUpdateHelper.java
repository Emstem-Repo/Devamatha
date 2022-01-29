package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.forms.exam.ExamRevaluationStatusUpdateForm;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;

public class ExamRevaluationStatusUpdateHelper {
	/**
	 * Singleton object of ExamRevaluationStatusUpdateHelper
	 */
	private static volatile ExamRevaluationStatusUpdateHelper examRevaluationStatusUpdateHelper = null;
	private static final Log log = LogFactory.getLog(ExamRevaluationStatusUpdateHelper.class);
	private ExamRevaluationStatusUpdateHelper() {
		
	}
	/**
	 * return singleton object of ExamRevaluationStatusUpdateHelper.
	 * @return
	 */
	public static ExamRevaluationStatusUpdateHelper getInstance() {
		if (examRevaluationStatusUpdateHelper == null) {
			examRevaluationStatusUpdateHelper = new ExamRevaluationStatusUpdateHelper();
		}
		return examRevaluationStatusUpdateHelper;
	}
	/**
	 * @param revaluationAppDetailsBo
	 * @return
	 * @throws Exception
	 */
	public List<ExamRevaluationApplicationTO> convertBOtoTO(List<ExamRevaluationAppDetails> revaluationAppDetailsBo,ExamRevaluationStatusUpdateForm examRevaluationStatusUpdateForm) throws Exception {
		List<ExamRevaluationApplicationTO> revaluationAppTOList=new ArrayList<ExamRevaluationApplicationTO>();
		if(revaluationAppDetailsBo!=null && !revaluationAppDetailsBo.isEmpty()){
			Iterator<ExamRevaluationAppDetails> iterator=revaluationAppDetailsBo.iterator();
			while (iterator.hasNext()) {
				ExamRevaluationAppDetails examRevaluationAppDetails = (ExamRevaluationAppDetails) iterator.next();
				ExamRevaluationApplicationTO to=new ExamRevaluationApplicationTO();
				to.setId(examRevaluationAppDetails.getId());
				to.setExamName(examRevaluationAppDetails.getExamRevApp().getExam().getName());
				to.setSubjectCode(examRevaluationAppDetails.getSubject().getCode());
				to.setSubjectName(examRevaluationAppDetails.getSubject().getName());
				to.setStatus(examRevaluationAppDetails.getStatus()!=null?examRevaluationAppDetails.getStatus():"");
				examRevaluationStatusUpdateForm.setContactEmail(examRevaluationAppDetails.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getEmail());
				examRevaluationStatusUpdateForm.setStudentName(examRevaluationAppDetails.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getFirstName());
				if(examRevaluationAppDetails.getExamRevApp().getStudent().getClassSchemewise()!=null)
				examRevaluationStatusUpdateForm.setSemType(examRevaluationAppDetails.getExamRevApp().getStudent().getClassSchemewise().getCurriculumSchemeDuration().getCurriculumScheme().getCourseScheme().getName());
				if(examRevaluationAppDetails.getExamRevApp().getClasses().getTermNumber()!=null && examRevaluationAppDetails.getExamRevApp().getClasses().getTermNumber()>0)
				examRevaluationStatusUpdateForm.setTermNo(examRevaluationAppDetails.getExamRevApp().getClasses().getTermNumber().toString());
				examRevaluationStatusUpdateForm.setMobileNo(examRevaluationAppDetails.getExamRevApp().getStudent().getAdmAppln().getPersonalData().getMobileNo2());
				
				revaluationAppTOList.add(to);
			}
		}
		return revaluationAppTOList;
	}
}
