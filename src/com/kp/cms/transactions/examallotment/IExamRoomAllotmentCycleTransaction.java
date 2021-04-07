package com.kp.cms.transactions.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycleDetails;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentCycleForm;

public interface IExamRoomAllotmentCycleTransaction {

	public boolean saveRoomCycleBo(List<ExamRoomAllotmentCycleDetails> boList)throws Exception;

	public List<Course> getListOfData(String hqlQuery)throws Exception;

	public boolean deleteRoomCycle(int id, boolean activate,String userId)throws Exception;

	public ExamRoomAllotmentCycle getMidEndAndSessionByCycleId(int cycleId)throws Exception;

	public List<ExamRoomAllotmentCycleDetails> getRoomAllotCycleDetails()throws Exception;

	public boolean updateAllotmentCycleDetails( List<ExamRoomAllotmentCycleDetails> boList,ExamRoomAllotmentCycleForm objForm,String mode)throws Exception;

	public ExamRoomAllotmentCycle getMidSemSchemeNoByCycleId(String cycleId)throws Exception;

	public Map<Integer, String> getExaminationSessionMap() throws Exception;


}
