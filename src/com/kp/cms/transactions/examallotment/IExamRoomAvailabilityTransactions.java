package com.kp.cms.transactions.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.examallotment.ExamRoomAvailability;

public interface IExamRoomAvailabilityTransactions {

	public Map<Integer, String> getWorkLocationDetails(String getWorkLocationQuery)throws Exception;

	public Map<Integer,Integer> getExamRoomAvailabilityRoomList(String hqlQuery1)throws Exception;

	public boolean saveList(List<ExamRoomAvailability> roomAvailabilitiesList)throws Exception;

}
