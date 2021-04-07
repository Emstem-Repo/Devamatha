package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.phd.PhdQualificationLevelBo;
import com.kp.cms.forms.phd.PhdQualificationLevelForm;
import com.kp.cms.to.phd.PhdQualificationLevelTo;

public class PhdQualificationLevelHelper {
public static volatile PhdQualificationLevelHelper phdQualificationLevelHelper=null;
	
	public static PhdQualificationLevelHelper getInstance(){
		if(phdQualificationLevelHelper == null){
			phdQualificationLevelHelper = new PhdQualificationLevelHelper();
			return phdQualificationLevelHelper;
		}
		return phdQualificationLevelHelper;
	}
	/**
	 * @param levelBOs
	 * @return
	 */
	public List<PhdQualificationLevelTo> copyQualificationLevelBOsToTOs(List<PhdQualificationLevelBo> levelBOs)throws Exception {
		List<PhdQualificationLevelTo> levelTOs=new ArrayList<PhdQualificationLevelTo>();
		Iterator<PhdQualificationLevelBo> iterator=levelBOs.iterator();
		PhdQualificationLevelTo qualificationLevelTO=null;
		while (iterator.hasNext()) {
			qualificationLevelTO =new PhdQualificationLevelTo();
			PhdQualificationLevelBo qualificationLevelBO = (PhdQualificationLevelBo) iterator.next();
			if(qualificationLevelBO.getId() !=0){
				qualificationLevelTO.setId(qualificationLevelBO.getId());
			}
			if(qualificationLevelBO.getName() !=null && !qualificationLevelBO.getName().isEmpty()){
			qualificationLevelTO.setName(qualificationLevelBO.getName());
			}
			if(qualificationLevelBO.getDisplayOrder() != null ){
				qualificationLevelTO.setDisplayOrder(qualificationLevelBO.getDisplayOrder());
			}
			else
			{
				qualificationLevelTO.setDisplayOrder(0);
			}
			if(qualificationLevelBO.isFixedDisplay() != null){
				if(qualificationLevelBO.isFixedDisplay()){
					qualificationLevelTO.setFixedDisplay("Yes");
				}
				if(!qualificationLevelBO.isFixedDisplay())
				{
					qualificationLevelTO.setFixedDisplay("NO");
				}
			}else
				{
					qualificationLevelTO.setFixedDisplay("NO");
				}
				
			levelTOs.add(qualificationLevelTO);
		}
		return levelTOs;
	}
	/**
	 * @param qualificationLevelForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public PhdQualificationLevelBo copyDataFromFormToBO(PhdQualificationLevelForm phdQualificationLevelForm, String mode)throws Exception {
		PhdQualificationLevelBo phdQualificationLevelBo=new PhdQualificationLevelBo();
		if(mode.equalsIgnoreCase("Add")){
			phdQualificationLevelBo.setId(phdQualificationLevelForm.getId());
			phdQualificationLevelBo.setName(phdQualificationLevelForm.getName());
			phdQualificationLevelBo.setDisplayOrder(phdQualificationLevelForm.getDisplayOrder());
			phdQualificationLevelBo.setFixedDisplay(phdQualificationLevelForm.isFixedDisplay());
			phdQualificationLevelBo.setIsActive(true);
			phdQualificationLevelBo.setCreatedBy(phdQualificationLevelForm.getUserId());
			phdQualificationLevelBo.setCreatedDate(new Date());
			phdQualificationLevelBo.setModifiedBy(phdQualificationLevelForm.getUserId());
			phdQualificationLevelBo.setLastModifiedDate(new Date());
		}else if(mode.equalsIgnoreCase("Edit")){
			phdQualificationLevelBo.setId(phdQualificationLevelForm.getId());
			phdQualificationLevelBo.setName(phdQualificationLevelForm.getName());
			phdQualificationLevelBo.setDisplayOrder(phdQualificationLevelForm.getDisplayOrder());
			phdQualificationLevelBo.setFixedDisplay(phdQualificationLevelForm.isFixedDisplay());
			phdQualificationLevelBo.setIsActive(true);
			phdQualificationLevelBo.setModifiedBy(phdQualificationLevelForm.getUserId());
			phdQualificationLevelBo.setLastModifiedDate(new Date());
		}
		return phdQualificationLevelBo;
	}
	/**
	 * @param bos
	 * @return
	 */
	public PhdQualificationLevelTo copyQualificationLevelBOsToTOs1(
			PhdQualificationLevelBo phdQualificationLevelBo) {
		PhdQualificationLevelTo levelTO=new PhdQualificationLevelTo();
		levelTO.setId(phdQualificationLevelBo.getId());
		levelTO.setName(phdQualificationLevelBo.getName());
		if(phdQualificationLevelBo.isFixedDisplay() == null){
			levelTO.setFixedDisplay("No");
		}	
		else if(phdQualificationLevelBo.isFixedDisplay() !=null && phdQualificationLevelBo.isFixedDisplay()){
			levelTO.setFixedDisplay("Yes");
		}
		else if(phdQualificationLevelBo.isFixedDisplay() !=null && !phdQualificationLevelBo.isFixedDisplay()){
			levelTO.setFixedDisplay("No");
		}
		if(phdQualificationLevelBo.getDisplayOrder()!=null)
		{
		levelTO.setDisplayOrder(phdQualificationLevelBo.getDisplayOrder());
		}
		else
		{
			levelTO.setDisplayOrder(0);
		}
		return levelTO;
	}
}
