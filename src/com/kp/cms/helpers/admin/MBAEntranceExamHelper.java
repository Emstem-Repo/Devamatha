package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.forms.admin.MBAEntranceExamForm;
import com.kp.cms.to.admin.MBAEntranceExamTO;

public class MBAEntranceExamHelper {

	private static volatile MBAEntranceExamHelper obj;
	
	private MBAEntranceExamHelper() {
		
	}
	
	public static MBAEntranceExamHelper getInstance() {
		if(obj == null) {
			obj = new MBAEntranceExamHelper();
		}
		return obj;
	}
	
	public List<MBAEntranceExamTO> convertBOToTO(List<MBAEntranceExam> mbaEntranceExams) throws Exception {
		Iterator<MBAEntranceExam> it = mbaEntranceExams.iterator();
		List<MBAEntranceExamTO> mbaEntranceExamTOs = new ArrayList<MBAEntranceExamTO>();
		
		while(it.hasNext()) {
			MBAEntranceExamTO objTO = new MBAEntranceExamTO();
			MBAEntranceExam objBO = it.next();
			objTO.setId(objBO.getId());
			objTO.setName(objBO.getName());
			mbaEntranceExamTOs.add(objTO);
		}
		return mbaEntranceExamTOs;
	}
	
	public MBAEntranceExam convertFormToBO(MBAEntranceExamForm mbaEntranceExamForm) throws Exception {
		MBAEntranceExam mbaEntranceExam = new MBAEntranceExam();
		mbaEntranceExam.setId(mbaEntranceExamForm.getId());
		mbaEntranceExam.setName(mbaEntranceExamForm.getName());
		mbaEntranceExam.setIsActive(true);
		
		return mbaEntranceExam;
	}
	
	public Map<Integer, String> getMap(List<MBAEntranceExam> mbaEntranceExams) throws Exception {
		Map<Integer, String> map = new HashMap<Integer, String>();
		Iterator<MBAEntranceExam> it = mbaEntranceExams.iterator();
		while(it.hasNext()) {
			MBAEntranceExam mbaEntranceExam = it.next();
			map.put(mbaEntranceExam.getId(), mbaEntranceExam.getName());
		}
		return map;
	}
}
