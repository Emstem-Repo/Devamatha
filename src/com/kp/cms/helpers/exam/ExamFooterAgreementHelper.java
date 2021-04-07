package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamFooterAgreementBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.forms.exam.ExamFooterAgreementForm;
import com.kp.cms.to.exam.ExamFooterAgreementTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;

public class ExamFooterAgreementHelper {

	public ArrayList<ExamFooterAgreementTO> convertBOtoTO(
			List<ExamFooterAgreementBO> listBO) throws Exception {
		ArrayList<ExamFooterAgreementTO> returnList = new ArrayList<ExamFooterAgreementTO>();
		ExamFooterAgreementTO eTO;
		Iterator<ExamFooterAgreementBO> itr = listBO.iterator();
		while (itr.hasNext()) {
			ExamFooterAgreementBO eBO = (ExamFooterAgreementBO) itr.next();
			eTO = new ExamFooterAgreementTO();
			eTO.setId(eBO.getId());
			eTO.setName(eBO.getName());
			if (eBO.getIsAgreement() == 1) {
				eTO.setType("Agreement");
			} else {
				eTO.setType("Footer");
			}
			if (eBO.getDate() != null) {
				eTO.setDate(CommonUtil.formatSqlDate(eBO.getDate().toString()));
			}
			String nameClass = eBO.getExamProgramTypeUtilBO().getProgramType() ;
					
			eTO.setClassCode(nameClass);
			eTO.setHallTicketorMarksCard(eBO.getHallTktOrMarksCard());
			if(eBO.getHallTktOrMarksCard()!= null && !eBO.getHallTktOrMarksCard().trim().isEmpty()){
				if(eBO.getHallTktOrMarksCard().equalsIgnoreCase("H")){
					eTO.setHallTicketorMarksCard("Hall Ticket");
				}
				else if(eBO.getHallTktOrMarksCard().equalsIgnoreCase("M")){
					eTO.setHallTicketorMarksCard("Marks Card");
				}
				//raghu
				else{
					eTO.setHallTicketorMarksCard("Application");
				}
			}
			else{
				eTO.setHallTicketorMarksCard("");
			}
			if(eBO.getAcademicYear() != null){
				eTO.setYear(eBO.getAcademicYear());
			}
			returnList.add(eTO);
		}

		return returnList;
	}
	
	public List<KeyValueTO> convertBOToTO_ProgramType(
			ArrayList<ExamProgramTypeUtilBO> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamProgramTypeUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getProgramType()));
		}
		Collections.sort(listTO);
		return listTO;
	}

	public Map<Integer, String> convertBOToMap(List<ClassUtilBO> listBO) throws Exception {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator<ClassUtilBO> itr = listBO.iterator();
		String name = "";
		while (itr.hasNext()) {
			ClassUtilBO classUtilBO = (ClassUtilBO) itr.next();
			name = classUtilBO.getName() + "-" + classUtilBO.getSectionName();
			map.put(classUtilBO.getId(), name);
		}
	//	map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public ExamFooterAgreementBO convertFormToBO(int id,
			String name, String typeId, String programID, String desc,
			String date, String userId, String hallTicketOrmarksCard, String academicYear) throws Exception {
			
			ExamFooterAgreementBO bo;
			bo = new ExamFooterAgreementBO();
			if (id > 0) {
				bo.setId(id);
			}
			bo.setName(name);
			if (typeId.equals("A")) {
				bo.setIsAgreement(1);
				bo.setIsFooter(0);
			} else {
				bo.setIsAgreement(0);
				bo.setIsFooter(1);
			}
			bo.setDate(CommonUtil.ConvertStringToSQLDate(date));
			bo.setUserAdd(userId);
			bo.setProgramTypeId(Integer.parseInt(programID));
			bo.setDescription(desc);
			if(hallTicketOrmarksCard.equalsIgnoreCase("1")){
				bo.setHallTktOrMarksCard("H");
			}
			else if(hallTicketOrmarksCard.equalsIgnoreCase("2")){
				bo.setHallTktOrMarksCard("M");
			}
			//raghu
			else{
				bo.setHallTktOrMarksCard("E");
			}
			if(academicYear != null && !academicYear.trim().isEmpty())
				bo.setAcademicYear(academicYear);
		

		return bo;
	}

	public ExamFooterAgreementForm convertBOToForm(ExamFooterAgreementBO objBO,
			ExamFooterAgreementForm objform) throws Exception {
		objform.setId(objBO.getId());
		objform.setName(objBO.getName());

		if (objBO.getIsAgreement() == 1) {
			objform.setTypeId("A");
		}
		if (objBO.getIsFooter() == 1) {
			objform.setTypeId("F");
		}
		if (objBO.getDate() != null) {
			objform.setDate(CommonUtil
					.formatSqlDate(objBO.getDate().toString()));
		}
		if(objBO.getHallTktOrMarksCard()!= null && !objBO.getHallTktOrMarksCard().trim().isEmpty()){
			if(objBO.getHallTktOrMarksCard().equalsIgnoreCase("H")){
				objform.setHalTcktOrMarksCard("1");
			}
			else if(objBO.getHallTktOrMarksCard().equalsIgnoreCase("M")){
				objform.setHalTcktOrMarksCard("2");
			}
			//raghu
			else if(objBO.getHallTktOrMarksCard().equalsIgnoreCase("E")){
				objform.setHalTcktOrMarksCard("3");
			}
		}else{
			objform.setHalTcktOrMarksCard("");
		}
	
		String programid = Integer.toString(objBO.getProgramTypeId());
		objform.setSelectedProgramType(programid);

		Map<Integer, String> mapSelectedClass = new HashMap<Integer, String>();
		String programName = objBO.getExamProgramTypeUtilBO().getProgramType();
				
		mapSelectedClass.put(objBO.getProgramTypeId(), programName);
		objform.setMapSelectedClass(mapSelectedClass);
		objform.setTemplateDescription(objBO.getDescription());
		if(objBO.getAcademicYear() != null){
			objform.setAcademicYear(objBO.getAcademicYear());
		}else{
			objform.setAcademicYear("");
		}
			
		return objform;
	}
}
