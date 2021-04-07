package com.kp.cms.handlers.examallotment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentPool;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentSettingsPoolWise;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentPoolForm;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.helpers.examallotment.ExamRoomAllotmentPoolHelper;
import com.kp.cms.to.examallotment.ExamRoomAllotSettingPoolWiseTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentPoolTo;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentPoolTransaction;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentPoolTxnImpl;

public class ExamRoomAllotmentPoolHandler {
	
	private static volatile ExamRoomAllotmentPoolHandler examRoomAllotmentPoolHandler=null;
	 
	/**
	 * @return
	 */
	public static ExamRoomAllotmentPoolHandler getInstance(){
		if(examRoomAllotmentPoolHandler == null){
			examRoomAllotmentPoolHandler=new ExamRoomAllotmentPoolHandler();
		}
		return examRoomAllotmentPoolHandler;
	}
	
	public ExamRoomAllotmentPoolHandler() {

	}

	/**
	 * @param allotmentPoolForm
	 * @param errors
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	public boolean duplicateCheck(ExamRoomAllotmentPoolForm allotmentPoolForm,ActionErrors errors, HttpSession session) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
     	return transaction.duplicateCheckPool(allotmentPoolForm,errors,session);
	}

	/**
	 * @param allotmentPoolForm
	 * @param mode
	 * @return
	 * @throws Exception 
	 */
	public boolean addPoolNameDetails(ExamRoomAllotmentPoolForm allotmentPoolForm, String mode) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		ExamRoomAllotmentPool examRoomAllotmentPool=ExamRoomAllotmentPoolHelper.getInstance().convertFormToBO(allotmentPoolForm,mode);
		return transaction.addPoolNameDetails(examRoomAllotmentPool,mode);
	}

	/**
	 * @param allotmentPoolForm
	 * @return
	 * @throws Exception 
	 */
	public boolean reactivatePoolDetails(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		return transaction.reactivatePoolDetails(allotmentPoolForm);
	}

	/**
	 * @param allotmentPoolForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentPoolTo> getPoolDetails(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		List<ExamRoomAllotmentPool> roomAllotmentPools=transaction.getPoolDetails();
		return ExamRoomAllotmentPoolHelper.getInstance().convertBOtoTO(roomAllotmentPools);
	}

	/**
	 * @param allotmentPoolForm
	 * @throws Exception
	 */
	public void editPoolDetails(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		ExamRoomAllotmentPool allotmentPool =transaction.getPoolDetailsById(allotmentPoolForm.getId());
		ExamRoomAllotmentPoolHelper.getInstance().setBOtoForm(allotmentPoolForm,allotmentPool);
	}

	/**
	 * @param allotmentPoolForm
	 * @return
	 * @throws Exception
	 */
	public boolean deletePoolDetails(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		return transaction.deletePoolDetailsById(allotmentPoolForm.getId());
	}

	/**
	 * @param allotmentPoolForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamRoomPoolDetails(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		List<ExamRoomAllotmentPool> examRoomAllotmentPools=transaction.getPoolDetails();
		getExamRoomAllotMentPoolWiseList(allotmentPoolForm,"M");
		return ExamRoomAllotmentPoolHelper.getInstance().convertAllotRoomPoolListToMap(examRoomAllotmentPools);
	}

	/**
	 * @param allotmentPoolForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseDetailsMap(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		 IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		 List<Integer> courseIdOfSpecialization= transaction.getCourseListInSpecialisationBySchemeNoAndMidOrEnd(allotmentPoolForm);
		 List<Integer> courseIdOfPoolWise =transaction.getCourseListOfPoolBySchemeNoAndMidOrEnd(allotmentPoolForm);
		 Map<Integer, String> courseMap=HolidaysHandler.getInstance().courseMap();
		 getExamRoomAllotMentPoolWiseList(allotmentPoolForm,allotmentPoolForm.getMidOrEndSem());
		return ExamRoomAllotmentPoolHelper.getInstance().getFinalCourseMap(courseMap,courseIdOfSpecialization,courseIdOfPoolWise);
	}

	/**
	 * @param allotmentPoolForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addExamRoomAllotMentPoolWise(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		 IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		 List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWiseList=ExamRoomAllotmentPoolHelper.getInstance().convertPoolWiseFormToBO(allotmentPoolForm);
	    return transaction.addExamRoomAllotMentPoolWise(allotmentSettingsPoolWiseList);
	}

	/**
	 * @param allotmentPoolForm
	 * @throws Exception
	 */
	public void editPoolWiseSettingsDetails(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
		IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
		Map<Integer, String> selectedCourseMap=new HashMap<Integer, String>();
		 Map<Integer, String> courseMap=HolidaysHandler.getInstance().courseMap();
		 List<Integer> courseIdOfSpecialization= transaction.getCourseListInSpecialisationBySchemeNoAndMidOrEnd(allotmentPoolForm);
		String selectedCourses=allotmentPoolForm.getSelectedCourses().substring(1, allotmentPoolForm.getSelectedCourses().length()-1);
		String[] courseIDs=selectedCourses.split(", ");
		List<Integer> courseIdList=new ArrayList<Integer>();
		for (int i = 0; i < courseIDs.length; i++) {
			if(courseMap.containsKey(Integer.parseInt(courseIDs[i]))){
				String courseName=courseMap.get(Integer.parseInt(courseIDs[i]));
				selectedCourseMap.put(Integer.parseInt(courseIDs[i]), courseName);
				courseMap.remove(Integer.parseInt(courseIDs[i]));
			}
			courseIdList.add(Integer.parseInt(courseIDs[i]));
		}
		List<Integer> otherPoolCourseIdList=null;
		if(!courseIdList.isEmpty()){
			 otherPoolCourseIdList= transaction.getCOurseIdFromOtherPool(allotmentPoolForm,courseIdList);
		}
		if(courseIdOfSpecialization!=null && !courseIdOfSpecialization.isEmpty()){
		    for (Integer courseId : courseIdOfSpecialization) {
				if(courseMap.containsKey(courseId)){
					courseMap.remove(courseId);
				}
			}	
		}
		if(otherPoolCourseIdList!=null && !otherPoolCourseIdList.isEmpty()){
			for (Integer otherPoolCourseId : otherPoolCourseIdList) {
				if(courseMap.containsKey(otherPoolCourseId)){
					courseMap.remove(otherPoolCourseId);
				}
			}
		}
		allotmentPoolForm.setCourseMap(courseMap);
		allotmentPoolForm.setSelectedCourseMap(selectedCourseMap);
		allotmentPoolForm.setIsDisable(true);
	}
   /**
 * @param allotmentPoolForm
 * @throws Exception 
 */
public void getExamRoomAllotMentPoolWiseList(ExamRoomAllotmentPoolForm allotmentPoolForm,String midOrEndSem) throws Exception{
	   IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
	   List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWises=transaction.getExamRoomAllotPoolWiseList(midOrEndSem);
		Map<String, Map<Integer, Map<String, ExamRoomAllotSettingPoolWiseTO>>> midOrEndMap=ExamRoomAllotmentPoolHelper.getInstance().convertAllotRoomPoolWiseBOtoTO(allotmentSettingsPoolWises);
		if(!midOrEndMap.isEmpty()){
			allotmentPoolForm.setMidOrEndMap(midOrEndMap);	
		}else{
			allotmentPoolForm.setMidOrEndMap(null);
		}
   }

/**
 * @param allotmentPoolForm
 * @param mode
 * @return
 * @throws Exception
 */
public boolean updateExamRoomAllotMentPoolWise(ExamRoomAllotmentPoolForm allotmentPoolForm,String mode) throws Exception {
	IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
	List<ExamRoomAllotmentSettingsPoolWise> settingsPoolWiseList= transaction.getRoomAllotPoolWoseListForUpdate(allotmentPoolForm);
	List<ExamRoomAllotmentSettingsPoolWise> allotmentSettingsPoolWises=ExamRoomAllotmentPoolHelper.getInstance().updateConvertFormToBo(settingsPoolWiseList,allotmentPoolForm,mode);
    return	transaction.updateRoomAllotMentPoolWise(allotmentSettingsPoolWises);
}
/*
*//**
 * @param allotmentPoolForm
 * @return
 * @throws Exception
 *//*
public boolean deleteExamRoomAllotMentPoolWise(ExamRoomAllotmentPoolForm allotmentPoolForm) throws Exception {
	IExamRoomAllotmentPoolTransaction transaction=ExamRoomAllotmentPoolTxnImpl.getInstance();
	List<ExamRoomAllotmentSettingsPoolWise> settingsPoolWiseList= transaction.getRoomAllotPoolWoseListForUpdate(allotmentPoolForm);
	return false;
}*/
}
