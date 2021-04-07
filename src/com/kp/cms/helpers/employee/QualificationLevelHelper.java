package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.forms.employee.QualificationLevelForm;
import com.kp.cms.to.employee.QualificationLevelTO;

public class QualificationLevelHelper {
	private static final Log log = LogFactory.getLog(QualificationLevelHelper.class);
	public static volatile QualificationLevelHelper qualificationLevelHelper=null;
	
	public static QualificationLevelHelper getInstance(){
		if(qualificationLevelHelper == null){
			qualificationLevelHelper =new QualificationLevelHelper();
			return qualificationLevelHelper;
		}
		return qualificationLevelHelper;
	}

	/**
	 * @param levelBOs
	 * @return
	 */
	public List<QualificationLevelTO> copyQualificationLevelBOsToTOs(List<QualificationLevelBO> levelBOs)throws Exception {
		List<QualificationLevelTO> levelTOs=new ArrayList<QualificationLevelTO>();
		Iterator<QualificationLevelBO> iterator=levelBOs.iterator();
		QualificationLevelTO qualificationLevelTO=null;
		while (iterator.hasNext()) {
			qualificationLevelTO =new QualificationLevelTO();
			QualificationLevelBO qualificationLevelBO = (QualificationLevelBO) iterator.next();
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
			if(qualificationLevelBO.isPhdQualification()){
				qualificationLevelTO.setPhdQualification("Yes");
			}else{
				qualificationLevelTO.setPhdQualification("No");
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
	public QualificationLevelBO copyDataFromFormToBO(QualificationLevelForm qualificationLevelForm, String mode)throws Exception {
		QualificationLevelBO qualificationLevelBO=new QualificationLevelBO();
		if(mode.equalsIgnoreCase("Add")){
			qualificationLevelBO.setId(qualificationLevelForm.getId());
			qualificationLevelBO.setName(qualificationLevelForm.getName());
			qualificationLevelBO.setDisplayOrder(qualificationLevelForm.getDisplayOrder());
			qualificationLevelBO.setFixedDisplay(qualificationLevelForm.isFixedDisplay());
			qualificationLevelBO.setPhdQualification(qualificationLevelForm.isPhdQualification());
			qualificationLevelBO.setIsActive(true);
			qualificationLevelBO.setCreatedBy(qualificationLevelForm.getUserId());
			qualificationLevelBO.setCreatedDate(new Date());
			qualificationLevelBO.setModifiedBy(qualificationLevelForm.getUserId());
			qualificationLevelBO.setLastModifiedDate(new Date());
		}else if(mode.equalsIgnoreCase("Edit")){
			qualificationLevelBO.setId(qualificationLevelForm.getId());
			qualificationLevelBO.setName(qualificationLevelForm.getName());
			qualificationLevelBO.setDisplayOrder(qualificationLevelForm.getDisplayOrder());
			qualificationLevelBO.setFixedDisplay(qualificationLevelForm.isFixedDisplay());
			qualificationLevelBO.setPhdQualification(qualificationLevelForm.isPhdQualification());
			qualificationLevelBO.setIsActive(true);
			qualificationLevelBO.setModifiedBy(qualificationLevelForm.getUserId());
			qualificationLevelBO.setLastModifiedDate(new Date());
		}
		return qualificationLevelBO;
	}

	/**
	 * @param bos
	 * @return
	 */
	public QualificationLevelTO copyQualificationLevelBOsToTOs1(
			QualificationLevelBO bos) {
		QualificationLevelTO levelTO=new QualificationLevelTO();
		levelTO.setId(bos.getId());
		levelTO.setName(bos.getName());
		if(bos.isFixedDisplay() == null){
			levelTO.setFixedDisplay("No");
		}	
		else if(bos.isFixedDisplay() !=null && bos.isFixedDisplay()){
			levelTO.setFixedDisplay("Yes");
		}
		else if(bos.isFixedDisplay() !=null && !bos.isFixedDisplay()){
			levelTO.setFixedDisplay("No");
		}
		if(bos.isPhdQualification()!=null && bos.isPhdQualification()){
			levelTO.setPhdQualification("Yes");
		}else{
			levelTO.setPhdQualification("No");
		}
		if(bos.getDisplayOrder()!=null)
		{
		levelTO.setDisplayOrder(bos.getDisplayOrder());
		}
		else
		{
			levelTO.setDisplayOrder(0);
		}
		return levelTO;
	}
}
