package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.University;
import com.kp.cms.forms.fee.FeeCriteriaForm;
import com.kp.cms.to.fee.FeeCriteriaTO;

public class FeeCriteriaHelper {
	private static final Log log = LogFactory.getLog(FeeCriteriaHelper.class);
	public static FeeCriteriaHelper feeCriteriaHelper = null;

	public static FeeCriteriaHelper getInstance() {
		if (feeCriteriaHelper == null) {
			feeCriteriaHelper = new FeeCriteriaHelper();
			return feeCriteriaHelper;
		}
		return feeCriteriaHelper;
	}
	/**
	 * 
	 * @param feeCriteriaForm
	 * @return
	 */
	public FeeCriteria copyFromFormTOBO(FeeCriteriaForm feeCriteriaForm){
		log.debug("copyFromFormTOBO in helper");
		FeeCriteria feeCriteria = new FeeCriteria();
		if(feeCriteriaForm.getInstituteID()!= null && !feeCriteriaForm.getInstituteID().trim().isEmpty()){
			College college = new College();
			college.setId(Integer.parseInt(feeCriteriaForm.getInstituteID()));
			feeCriteria.setCollege(college);
		}
		if(feeCriteriaForm.getNationalityID()!= null && !feeCriteriaForm.getNationalityID().trim().isEmpty()){
			Nationality nationality = new Nationality();
			nationality.setId(Integer.parseInt(feeCriteriaForm.getNationalityID()));
			feeCriteria.setNationality(nationality);
		}
		if(feeCriteriaForm.getUniversityId()!= null && !feeCriteriaForm.getUniversityId().trim().isEmpty()){
			University university = new University();
			university.setId(Integer.parseInt(feeCriteriaForm.getUniversityId()));
			feeCriteria.setUniversity(university);
		}
		if(feeCriteriaForm.getResidentCategoryId()!= null && !feeCriteriaForm.getResidentCategoryId().trim().isEmpty()){
			ResidentCategory residentCategory = new ResidentCategory();
			residentCategory.setId(Integer.parseInt(feeCriteriaForm.getResidentCategoryId()));
			feeCriteria.setResidentCategory(residentCategory);
		}
		if(feeCriteriaForm.getLanguage()!= null && !feeCriteriaForm.getLanguage().trim().isEmpty()){
			feeCriteria.setSecLanguage(feeCriteriaForm.getLanguage());
		}
		
		if(feeCriteriaForm.getAdditionalFeeGroup1()!= null && !feeCriteriaForm.getAdditionalFeeGroup1().trim().isEmpty()){
			FeeAdditional feeAdditional = new FeeAdditional();
			feeAdditional.setId(Integer.parseInt(feeCriteriaForm.getAdditionalFeeGroup1()));
			feeCriteria.setFeeAdditional1(feeAdditional);
		}
		if(feeCriteriaForm.getAdditionalFeeGroup2()!= null && !feeCriteriaForm.getAdditionalFeeGroup2().trim().isEmpty()){
			FeeAdditional feeAdditional = new FeeAdditional();
			feeAdditional.setId(Integer.parseInt(feeCriteriaForm.getAdditionalFeeGroup2()));
			feeCriteria.setFeeAdditional2(feeAdditional);
		}
		
		if(feeCriteriaForm.getAdmittedThroghId()!= null && !feeCriteriaForm.getAdmittedThroghId().trim().isEmpty()){
			AdmittedThrough admittedThrough = new AdmittedThrough();
			admittedThrough.setId(Integer.parseInt(feeCriteriaForm.getAdmittedThroghId()));
			feeCriteria.setAdmittedThrough(admittedThrough);
		}
		if(feeCriteriaForm.getLanguage()!= null && feeCriteriaForm.getLanguage().trim().isEmpty()){
			feeCriteria.setSecLanguage(feeCriteriaForm.getLanguage());
		}
	
		feeCriteria.setCreatedDate(new Date());
		feeCriteria.setLastModifiedDate(new Date());
		feeCriteria.setCreatedBy(feeCriteriaForm.getUserId());
		feeCriteria.setModifiedBy(feeCriteriaForm.getUserId());
		
		return feeCriteria;
		
	}
	/**
	 * 
	 * @param feeCriteriaList
	 * @return
	 */
	public List<FeeCriteriaTO> copyFeeCriteriaBOtoTos(List<FeeCriteria> feeCriteriaList) {
		List<FeeCriteriaTO> feeCriteriaTOList = new ArrayList<FeeCriteriaTO>();
		Iterator<FeeCriteria> i = feeCriteriaList.iterator();
		FeeCriteria feeCriteria;
		FeeCriteriaTO feeCriteriaTO;
		while (i.hasNext()) {
			feeCriteriaTO = new FeeCriteriaTO();
			feeCriteria = i.next();
			if(feeCriteria.getNationality()!= null){
				feeCriteriaTO.setNationality(feeCriteria.getNationality().getName());
			}
			if(feeCriteria.getCollege()!= null){
				feeCriteriaTO.setInstiute(feeCriteria.getCollege().getName());
			}
			if(feeCriteria.getUniversity()!= null){
				feeCriteriaTO.setUniversity(feeCriteria.getUniversity().getName());
			}
			if(feeCriteria.getResidentCategory()!= null){
				feeCriteriaTO.setResidentCategory(feeCriteria.getResidentCategory().getName());
			}
			if(feeCriteria.getAdmittedThrough()!= null){
				feeCriteriaTO.setAdmittedThrough(feeCriteria.getAdmittedThrough().getName());
			}
			if(feeCriteria.getSecLanguage()!= null){
				feeCriteriaTO.setSecLannguage(feeCriteria.getSecLanguage());
			}
			if(feeCriteria.getFeeAdditional1()!= null){
				feeCriteriaTO.setFeeAddlId1(feeCriteria.getFeeAdditional1().getFeeGroup().getName());
			}
			if(feeCriteria.getFeeAdditional2()!= null){
				feeCriteriaTO.setFeeAddlId2(feeCriteria.getFeeAdditional2().getFeeGroup().getName());
			}
			feeCriteriaTO.setId(feeCriteria.getId());
			feeCriteriaTOList.add(feeCriteriaTO);
		}
		return feeCriteriaTOList;
	}
		
}
