package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.InternalRedoFees;
import com.kp.cms.bo.exam.RegularExamFees;
import com.kp.cms.bo.exam.RevaluationExamFees;
import com.kp.cms.bo.exam.SupplementaryFees;
import com.kp.cms.to.exam.InternalRedoFeesTO;
import com.kp.cms.to.exam.RegularExamFeesTo;
import com.kp.cms.to.exam.RevaluationExamFeesTo;
import com.kp.cms.to.exam.SupplementaryFeesTo;

public class SupplementaryFeesHelper {
	/**
	 * Singleton object of SupplementaryFeesHelper
	 */
	private static volatile SupplementaryFeesHelper supplementaryFeesHelper = null;
	private static final Log log = LogFactory.getLog(SupplementaryFeesHelper.class);
	private SupplementaryFeesHelper() {
		
	}
	/**
	 * return singleton object of SupplementaryFeesHelper.
	 * @return
	 */
	public static SupplementaryFeesHelper getInstance() {
		if (supplementaryFeesHelper == null) {
			supplementaryFeesHelper = new SupplementaryFeesHelper();
		}
		return supplementaryFeesHelper;
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public List<SupplementaryFeesTo> convertBotoToList( List<SupplementaryFees> boList) throws Exception {
		List<SupplementaryFeesTo> toList=new ArrayList<SupplementaryFeesTo>();
		SupplementaryFeesTo to=null;
		for (SupplementaryFees bo : boList) {
			to=new SupplementaryFeesTo();
			to.setId(bo.getId());
			to.setClassName(bo.getClasses().getName());
			if(bo.getPaperFees()!=null)
			to.setPaperFees(bo.getPaperFees().doubleValue());
			if(bo.getRegistrationFees()!=null)
				to.setRegistrationFees(bo.getRegistrationFees().doubleValue());
			if(bo.getApplicationFees()!=null)
			to.setApplicationFees(bo.getApplicationFees().doubleValue());
			if(bo.getCvCampFees()!=null)
			to.setCvCampFees(bo.getCvCampFees().doubleValue());
			if(bo.getMarksListFees()!=null)
			to.setMarksListFees(bo.getMarksListFees().doubleValue());
			to.setAcademicYear(bo.getAcademicYear());
			if(bo.getReligionSection()!=null)
			to.setSectionName(bo.getReligionSection().getName());
			toList.add(to);
		}
		return toList;
	}
	
	public List<RegularExamFeesTo> convertBotoToListRegular( List<RegularExamFees> boList) throws Exception {
		List<RegularExamFeesTo> toList=new ArrayList<RegularExamFeesTo>();
		RegularExamFeesTo to=null;
		for (RegularExamFees bo : boList) {
			to=new RegularExamFeesTo();
			to.setId(bo.getId());
			to.setClassName(bo.getClasses().getName());
			if(bo.getTheoryFees()!=null)
			to.setTheoryFees(bo.getTheoryFees().doubleValue());
			if(bo.getPracticalFees()!=null)
			to.setPracticalFees(bo.getPracticalFees().doubleValue());
			if(bo.getApplicationFees()!=null)
			to.setApplicationFees(bo.getApplicationFees().doubleValue());
			if(bo.getCvCampFees()!=null)
			to.setCvCampFees(bo.getCvCampFees().doubleValue());
			if(bo.getMarksListFees()!=null)
			to.setMarksListFees(bo.getMarksListFees().doubleValue());
			if(bo.getOnlineServiceChargeFees()!=null)
			to.setOnlineServiceChargeFees(bo.getOnlineServiceChargeFees().doubleValue());
			if(bo.getExamFees()!=null)
				to.setExamFees(bo.getExamFees().doubleValue());
			to.setAcademicYear(bo.getAcademicYear());
			if(bo.getReligionSection()!=null)
			to.setSectionName(bo.getReligionSection().getName());
			toList.add(to);
		}
		return toList;
	}
	
	public List<RevaluationExamFeesTo> convertBotoToListRevaluation( List<RevaluationExamFees> boList) throws Exception {
		List<RevaluationExamFeesTo> toList=new ArrayList<RevaluationExamFeesTo>();
		RevaluationExamFeesTo to=null;
		for (RevaluationExamFees bo : boList) {
			to=new RevaluationExamFeesTo();
			to.setId(bo.getId());
			to.setCourseName(bo.getCourse().getName());
			if(bo.getRevaluationFees()!=null)
			to.setRevaluationFees(bo.getRevaluationFees().doubleValue());
			if(bo.getScrutinyFees()!=null)
			to.setScrutinyFees(bo.getScrutinyFees().doubleValue());
	
			if(bo.getApplicationFees()!=null)
			to.setApplicationFees(bo.getApplicationFees().doubleValue());
			
			if(bo.getChallengeValuationFees()!=null)
				to.setChallengeValuationFees(bo.getChallengeValuationFees().doubleValue());
	
		
			to.setAcademicYear(bo.getAcademicYear());
			toList.add(to);
		}
		return toList;
	}

	public List<InternalRedoFeesTO> convertBotoToListInternalRedo( List<InternalRedoFees> boList) throws Exception {
		List<InternalRedoFeesTO> toList=new ArrayList<InternalRedoFeesTO>();
		InternalRedoFeesTO to=null;
		for (InternalRedoFees bo : boList) {
			to=new InternalRedoFeesTO();
			to.setId(bo.getId());
			to.setClassName(bo.getClasses().getName());
			if(bo.getPaperFees()!=null)
			to.setPaperFees(bo.getPaperFees().doubleValue());
			if(bo.getRegistrationFees()!=null)
				to.setRegistrationFees(bo.getRegistrationFees().doubleValue());
			if(bo.getApplicationFees()!=null)
			to.setApplicationFees(bo.getApplicationFees().doubleValue());
			if(bo.getCvCampFees()!=null)
			to.setCvCampFees(bo.getCvCampFees().doubleValue());
			if(bo.getMarksListFees()!=null)
			to.setMarksListFees(bo.getMarksListFees().doubleValue());
			to.setAcademicYear(bo.getAcademicYear());
			if(bo.getReligionSection()!=null)
			to.setSectionName(bo.getReligionSection().getName());
			toList.add(to);
		}
		return toList;
	}
}
