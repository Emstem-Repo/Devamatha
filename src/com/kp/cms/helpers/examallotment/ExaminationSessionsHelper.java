package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.forms.examallotment.ExaminationSessionsForm;
import com.kp.cms.to.examallotment.ExaminationSessionsTo;

public class ExaminationSessionsHelper {
	public static volatile ExaminationSessionsHelper examinationSessionsHelper=null;
	//private constructor
	private ExaminationSessionsHelper(){
		
	}
	//singleton object
	public static ExaminationSessionsHelper getInstance(){
		if(examinationSessionsHelper==null){
			examinationSessionsHelper=new ExaminationSessionsHelper();
			return examinationSessionsHelper;
		}
		return examinationSessionsHelper;
	}
	public ExaminationSessions convertFormToBo(
			ExaminationSessionsForm examinationSessionsForm) throws Exception{
		ExaminationSessions examinationSessions=new ExaminationSessions();
		examinationSessions.setSession(examinationSessionsForm.getSession());
		examinationSessions.setSessionDescription(examinationSessionsForm.getSessionDesc());
		examinationSessions.setOrderNO(Integer.parseInt(examinationSessionsForm.getOrderNo()));
		examinationSessions.setTimings(examinationSessionsForm.getTimings());
		examinationSessions.setIsActive(true);
		examinationSessions.setCreatedDate(new Date());
		examinationSessions.setLastModifiedDate(new Date());
		examinationSessions.setCreatedBy(examinationSessionsForm.getUserId());
		examinationSessions.setModifiedBy(examinationSessionsForm.getUserId());
		return examinationSessions;
	}
	public List<ExaminationSessionsTo> convertBosToTos(
			List<ExaminationSessions> list) {
		List<ExaminationSessionsTo> listOfTos=new ArrayList<ExaminationSessionsTo>();
		ExaminationSessionsTo examinationSessionsTo=null;
		for (ExaminationSessions examinationSessions : list) {
			examinationSessionsTo=new ExaminationSessionsTo();
			examinationSessionsTo.setId(examinationSessions.getId());
			examinationSessionsTo.setSession(examinationSessions.getSession());
			examinationSessionsTo.setSessionDesc(examinationSessions.getSessionDescription());
			examinationSessionsTo.setOrderNo(String.valueOf(examinationSessions.getOrderNO()));
			examinationSessionsTo.setTimings(examinationSessions.getTimings());
			listOfTos.add(examinationSessionsTo);
		}
		return listOfTos;
	}
}
