package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;

public interface IExtendSupplyImprApplDateTransaction {

	List<PublishSupplementaryImpApplication> getExamsToExtend(int parseInt)throws Exception;

	List<PublishSupplementaryImpApplication>  getBosToUpdate(List<Integer> ids)throws Exception;

	boolean updateTheData(List<PublishSupplementaryImpApplication> pbApplications)throws Exception;
	
	public List<ExamPublishHallTicketMarksCardBO> getExamsToExtendRegular(int examId) throws Exception;
	
	public boolean updateTheDataRegular(List<ExamPublishHallTicketMarksCardBO> pbApplications)throws Exception;
	
	public List<ExamPublishHallTicketMarksCardBO>  getBosToUpdateRegular(List<Integer> ids) throws Exception;
	
	public List<ExamPublishHallTicketMarksCardBO> getExamsToExtendInternalRedo(int examId) throws Exception;

	public List<ExamPublishHallTicketMarksCardBO>  getBosToUpdateInternalRedo(List<Integer> ids) throws Exception;
}
