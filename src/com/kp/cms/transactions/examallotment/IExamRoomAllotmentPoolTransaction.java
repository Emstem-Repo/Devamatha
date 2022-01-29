package com.kp.cms.transactions.examallotment;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentPool;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentSettingsPoolWise;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm;

public interface IExamRoomAllotmentPoolTransaction {
	
	public boolean addPoolNameDetails(ExamRoomAllotmentPool examRoomAllotmentPool,String mode)throws Exception;
	
	public boolean duplicateCheckPool(ExamRoomAllotmentPoolForm examRoomAllotmentPoolForm,ActionErrors errors,HttpSession hSession)throws Exception;
    
	public List<ExamRoomAllotmentPool> getPoolDetails()throws Exception;
	
	public boolean reactivatePoolDetails(ExamRoomAllotmentPoolForm allotmentPoolForm)throws Exception;
	
	public ExamRoomAllotmentPool getPoolDetailsById(int id)throws Exception;
	
	public boolean deletePoolDetailsById(int id)throws Exception;
	
	public List<Integer> getCourseListInSpecialisationBySchemeNoAndMidOrEnd(ExamRoomAllotmentPoolForm allotmentPoolForm)throws Exception;
	
	public List<Integer> getCourseListOfPoolBySchemeNoAndMidOrEnd(ExamRoomAllotmentPoolForm allotmentPoolForm)throws Exception;
	
	public boolean addExamRoomAllotMentPoolWise(List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWiseList)throws Exception;
	
	public List<ExamRoomAllotmentSettingsPoolWise>  getExamRoomAllotPoolWiseList(String midOrEndSem)throws Exception;

	public List<ExamRoomAllotmentSettingsPoolWise> getRoomAllotPoolWoseListForUpdate(ExamRoomAllotmentPoolForm allotmentPoolForm)throws Exception;

	public boolean updateRoomAllotMentPoolWise(List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWises)throws Exception;

	public List<Integer> getCOurseIdFromOtherPool(ExamRoomAllotmentPoolForm allotmentPoolForm, List<Integer> courseIDs)throws Exception;

}
