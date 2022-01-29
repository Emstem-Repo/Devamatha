package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.forms.phd.PhdGuideDetailsForm;
import com.kp.cms.to.phd.PhdGuideDetailsTO;

public class PhdGuideDetailsHelper {
	private static final Log log = LogFactory.getLog(PhdGuideDetailsHelper.class);
	public static volatile PhdGuideDetailsHelper examCceFactorHelper = null;

	public static PhdGuideDetailsHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PhdGuideDetailsHelper();
		}
		return examCceFactorHelper;
	}


	public PhdGuideDetailsBO convertFormToBos(PhdGuideDetailsForm objForm) {		
		PhdGuideDetailsBO guideBO = new PhdGuideDetailsBO();
	try{
		guideBO.setId(objForm.getId());
		guideBO.setName(objForm.getName());
		guideBO.setEmpanelmentNo(objForm.getEmpanelmentNo());
		guideBO.setPhoneNo(objForm.getPhoneNo());
		guideBO.setMobileNo(objForm.getMobileNo());
		guideBO.setEmail(objForm.getEmail());
		guideBO.setAddressLine1(objForm.getAddressLine1());
		guideBO.setAddressLine2(objForm.getAddressLine2());
		guideBO.setAddressLine3(objForm.getAddressLine3());
		guideBO.setAddressLine4(objForm.getAddressLine4());
		guideBO.setPinCode(objForm.getPinCode());
		guideBO.setCreatedBy(objForm.getUserId());
		guideBO.setCreatedDate(new Date());
		guideBO.setLastModifiedDate(new Date());
		guideBO.setModifiedBy(objForm.getUserId());
		guideBO.setIsActive(Boolean.valueOf(true));
	}catch (Exception e) {
			e.printStackTrace();
		}
		return guideBO;
			
   }

	public void setDataBoToForm(PhdGuideDetailsForm objForm,PhdGuideDetailsBO guideBO) {
	  if(guideBO != null)
        {
		  objForm.setId(guideBO.getId());
		  objForm.setName(guideBO.getName());
		  objForm.setEmpanelmentNo(guideBO.getEmpanelmentNo());
		  objForm.setPhoneNo(guideBO.getPhoneNo());
		  objForm.setMobileNo(guideBO.getMobileNo());
		  objForm.setEmail(guideBO.getEmail());
		  objForm.setAddressLine1(guideBO.getAddressLine1());
		  objForm.setAddressLine2(guideBO.getAddressLine2());
		  objForm.setAddressLine3(guideBO.getAddressLine3());
		  objForm.setAddressLine4(guideBO.getAddressLine4());
		  objForm.setPinCode(guideBO.getPinCode());
        }
    }


	public PhdGuideDetailsBO convertToBos(PhdGuideDetailsForm objForm) {
		PhdGuideDetailsBO guiBo = new PhdGuideDetailsBO();
		try{
			guiBo.setId(objForm.getId());
			guiBo.setName(objForm.getName());
			guiBo.setEmpanelmentNo(objForm.getEmpanelmentNo());
			guiBo.setPhoneNo(objForm.getPhoneNo());
			guiBo.setMobileNo(objForm.getMobileNo());
			guiBo.setEmail(objForm.getEmail());
			guiBo.setAddressLine1(objForm.getAddressLine1());
			guiBo.setAddressLine2(objForm.getAddressLine2());
			guiBo.setAddressLine3(objForm.getAddressLine3());
			guiBo.setAddressLine4(objForm.getAddressLine4());
			guiBo.setPinCode(objForm.getPinCode());
			guiBo.setCreatedBy(objForm.getUserId());
			guiBo.setCreatedDate(new Date());
			guiBo.setLastModifiedDate(new Date());
			guiBo.setModifiedBy(objForm.getUserId());
			guiBo.setIsActive(Boolean.valueOf(true));
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return guiBo;
   }

	public List<PhdGuideDetailsTO> setdatatogrid(List<PhdGuideDetailsBO> phdGuide) {
		List<PhdGuideDetailsTO> listto=new ArrayList<PhdGuideDetailsTO>();
		Iterator<PhdGuideDetailsBO> itr=phdGuide.iterator();
		while (itr.hasNext()) {
			PhdGuideDetailsBO phdguideBO = (PhdGuideDetailsBO) itr.next();
			PhdGuideDetailsTO PhdduideTo=new PhdGuideDetailsTO();
			PhdduideTo.setId(phdguideBO.getId());
			if(phdguideBO.getName()!=null && !phdguideBO.getName().isEmpty()){
				PhdduideTo.setName(phdguideBO.getName());
			}if(phdguideBO.getEmpanelmentNo()!=null && !phdguideBO.getEmpanelmentNo().isEmpty()){
				PhdduideTo.setEmpanelmentNo(phdguideBO.getEmpanelmentNo());
			}if(phdguideBO.getPhoneNo()!=null && !phdguideBO.getPhoneNo().isEmpty()){
				PhdduideTo.setPhoneNo(phdguideBO.getPhoneNo());
			}if(phdguideBO.getMobileNo()!=null && !phdguideBO.getMobileNo().isEmpty()){
				PhdduideTo.setMobileNo(phdguideBO.getMobileNo());
			}if(phdguideBO.getEmail()!=null && !phdguideBO.getEmail().isEmpty()){
				PhdduideTo.setEmail(phdguideBO.getEmail());
			}if(phdguideBO.getAddressLine1()!=null && !phdguideBO.getAddressLine1().isEmpty()){
				PhdduideTo.setAddressLine1(phdguideBO.getAddressLine1());
			}
			listto.add(PhdduideTo);
			
		}
		return listto;
	}
}
