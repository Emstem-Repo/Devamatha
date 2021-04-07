package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdSettingBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.forms.phd.PhdSettingForm;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.to.phd.PhdSettingTO;
import com.kp.cms.to.phd.PhdStudyAggrementTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdSettingHelper {
	private static final Log log = LogFactory.getLog(PhdSettingHelper.class);
	public static volatile PhdSettingHelper examCceFactorHelper = null;

	public static PhdSettingHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PhdSettingHelper();
		}
		return examCceFactorHelper;
	}
	/**
	 * @param objForm
	 * @param settingBO
	 */
	public void setDataBoToForm(PhdSettingForm objForm, PhdSettingBO settingBO) {
		  if(settingBO != null)
	        {
			  objForm.setId(settingBO.getId());
			  objForm.setReminderMailBefore(Integer.toString(settingBO.getReminderMailBefore()));
			  objForm.setDueMailAfter(Integer.toString(settingBO.getDueMailAfter()));
			  objForm.setMaxGuidesAssign(settingBO.getMaxGuidesAssign());
	        }
	    }
	/**
	 * @param objForm
	 * @return
	 */
	public PhdSettingBO convertFormToBos(PhdSettingForm objForm) {
		PhdSettingBO settingBo=new PhdSettingBO();
		
			if(objForm.getId()!=0){
				settingBo.setId(objForm.getId());
			}
			settingBo.setReminderMailBefore(Integer.parseInt(objForm.getReminderMailBefore()));
			settingBo.setDueMailAfter(Integer.parseInt(objForm.getDueMailAfter()));
			settingBo.setMaxGuidesAssign(objForm.getMaxGuidesAssign());
			settingBo.setCreatedBy(objForm.getUserId());
			settingBo.setCreatedDate(new Date());
			settingBo.setLastModifiedDate(new Date());
			settingBo.setModifiedBy(objForm.getUserId());
			settingBo.setIsActive(Boolean.valueOf(true));
			
			return settingBo;
			
   }
	
	/**
	 * @param settingdetails
	 * @return
	 */
	public List<PhdSettingTO> setdataToForm(List<PhdSettingBO> settingdetails) {
		List<PhdSettingTO> list=new ArrayList<PhdSettingTO>();
		Iterator<PhdSettingBO> itr=settingdetails.iterator();
		while (itr.hasNext()) {
			PhdSettingBO settingBO = (PhdSettingBO) itr.next();
			PhdSettingTO settingTo=new PhdSettingTO();
			
			settingTo.setId(settingBO.getId());
			if(settingBO.getReminderMailBefore()>0){
			settingTo.setReminderMailBefore(Integer.toString(settingBO.getReminderMailBefore()));
			}if(settingBO.getDueMailAfter()>0){
			settingTo.setDueMailAfter(Integer.toString(settingBO.getDueMailAfter()));
			}
			settingTo.setMaxGuidesAssign(settingBO.getMaxGuidesAssign());
			list.add(settingTo);
		}
		return list;
	}


}
