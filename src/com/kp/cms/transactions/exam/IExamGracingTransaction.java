package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ExamGracingBO;
import com.kp.cms.bo.exam.ExamGracingProcessBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ExamGracingForm;
import com.kp.cms.to.exam.ExamGracingTo;

public interface IExamGracingTransaction {

	List<ExamGracingBO> getStudentList(ExamGracingForm graceForm) throws ApplicationException;

	List<ConsolidateMarksCard> getConsolidatedMarks(ExamGracingTo gracingTo) throws ApplicationException;

	boolean saveGracing(List<ExamGracingProcessBO> markBoList, ExamGracingForm graceForm) throws Exception;

	Student getStudent(ExamGracingForm graceForm) throws Exception;

	ExamGracingBO getGracingBo(ExamGracingForm graceForm) throws Exception;

	boolean getAssignGracing(ExamGracingForm graceForm, ExamGracingBO bo) throws Exception;

	List<ExamGracingBO> getProcessedStudentList(ExamGracingForm graceForm) throws Exception;

	void setSupplyId(ExamGracingTo gracingTo)throws Exception;


}
