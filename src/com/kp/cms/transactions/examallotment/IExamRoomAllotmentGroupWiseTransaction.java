package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentGroupWise;

public interface IExamRoomAllotmentGroupWiseTransaction {

	public boolean addAllotmentGroupWiseCourses(List<ExamRoomAllotmentGroupWise> groupWiseList)throws Exception;

	public List<ExamRoomAllotmentGroupWise> getGroupWiseListByMidOrEndAndSchemeNo(String midOrEndSem,int schemeNo)throws Exception;
	
	public List<Integer> getCourseIdListByMidEndSemAndSchemeNo(String midOrEndSem,int schemeNo)throws Exception;

	public boolean updateOrDeleteGroupWise(List<ExamRoomAllotmentGroupWise> allotmentGroupWiseList)throws Exception;

}
