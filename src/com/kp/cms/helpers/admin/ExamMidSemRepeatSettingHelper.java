package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.admin.ExamMidSemRepeatSettingForm;
import com.kp.cms.to.admin.ExamMidSemRepeatSettingTo;
import com.kp.cms.transactions.admin.IExamMidSemRepeatSettingTransaction;
import com.kp.cms.transactionsimpl.admin.ExamMidSemRepeatSettingTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamMidSemRepeatSettingHelper {

	IExamMidSemRepeatSettingTransaction transaction = ExamMidSemRepeatSettingTxnImpl.getInstance();
    public static volatile ExamMidSemRepeatSettingHelper examMidSemRepeatSettingHelper = null;
    
    
    /**
     * @return
     */
    public static ExamMidSemRepeatSettingHelper getInstance() {
		if (examMidSemRepeatSettingHelper == null) {
			examMidSemRepeatSettingHelper = new ExamMidSemRepeatSettingHelper();
			return examMidSemRepeatSettingHelper;
		}
		return examMidSemRepeatSettingHelper;
	}


	/**
	 * @param settingForm
	 * @param examTypeList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> convertBoToMap(ExamMidSemRepeatSettingForm settingForm,List<ExamTypeUtilBO> examTypeList)throws Exception {
          Map<Integer, String> examTypeMap=new HashMap<Integer, String>();   
		    if(examTypeList!=null && !examTypeList.isEmpty()){
            	 for (ExamTypeUtilBO examTypeUtilBO : examTypeList) {
					examTypeMap.put(examTypeUtilBO.getId(), examTypeUtilBO.getName());
					if(examTypeUtilBO.getName().equalsIgnoreCase("Regular")){
						settingForm.setExamType(String.valueOf(examTypeUtilBO.getId()));
					}
				}
             }
			return examTypeMap;
	}


	/**
	 * @param settingForm
	 * @return
	 * @throws Exception
	 */
	public ExamMidsemRepeatSetting convertFormToBo(ExamMidSemRepeatSettingForm settingForm,String mode) throws Exception{
		ExamMidsemRepeatSetting repeatSetting=new ExamMidsemRepeatSetting();
		repeatSetting.setApplnStartDate(CommonUtil.ConvertStringToDate(settingForm.getApplicationOpenFrom()));
		repeatSetting.setApplnEndDate(CommonUtil.ConvertStringToDate(settingForm.getApplicationOpenTill()));
		repeatSetting.setFeesEndDate(CommonUtil.ConvertStringToDate(settingForm.getFeePaymentTill()));
		ExamDefinitionBO examDefinitionBO=new ExamDefinitionBO();
		examDefinitionBO.setId(Integer.parseInt(settingForm.getExamName()));
		repeatSetting.setMidSemExamId(examDefinitionBO);
		repeatSetting.setFeesPerSubject(new BigDecimal(settingForm.getFeesPerSubject()));
		if(mode.equalsIgnoreCase("Save")){
		repeatSetting.setCreatedBy(settingForm.getUserId());
		repeatSetting.setCreatedDate(new Date());
		}else{
			repeatSetting.setModifiedBy(settingForm.getUserId());
			repeatSetting.setLastModifiedDate(new Date());
			repeatSetting.setId(settingForm.getRepeatSettingId());
		}
		repeatSetting.setIsActive(true);
		return repeatSetting;
	}
	
	/**
	 * @param settingList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, ExamMidSemRepeatSettingTo> convertRepeatSettingBoToTo(List<ExamMidsemRepeatSetting> settingList)throws Exception {
		Map<Integer, ExamMidSemRepeatSettingTo> midSemRepeatSettingMap=new HashMap<Integer, ExamMidSemRepeatSettingTo>();
         if(settingList!=null && !settingList.isEmpty()){
        	for (ExamMidsemRepeatSetting examMidsemRepeatSetting : settingList) {
				ExamMidSemRepeatSettingTo to=new ExamMidSemRepeatSettingTo();
				to.setApplicationStartDate(CommonUtil.formatDates(examMidsemRepeatSetting.getApplnStartDate()));
				to.setApplicationEndDate(CommonUtil.formatDates(examMidsemRepeatSetting.getApplnEndDate()));
				to.setFeePaymentEndDate(CommonUtil.formatDates(examMidsemRepeatSetting.getFeesEndDate()));
				String[] fees=String.valueOf(examMidsemRepeatSetting.getFeesPerSubject()).split("\\.");
				to.setFeesPerSubject(fees[0]);
				to.setExamName(examMidsemRepeatSetting.getMidSemExamId().getName());
				to.setExamType(String.valueOf(examMidsemRepeatSetting.getMidSemExamId().getExamTypeID()));
				to.setExamId(examMidsemRepeatSetting.getMidSemExamId().getId());
				to.setAcademicYear(String.valueOf(examMidsemRepeatSetting.getMidSemExamId().getAcademicYear()));
        		to.setRepeatSettingId(examMidsemRepeatSetting.getId());
        		midSemRepeatSettingMap.put(examMidsemRepeatSetting.getId(), to);
			} 
         }
		return midSemRepeatSettingMap;
	}


	/**
	 * @param settingForm
	 */
	public void convertToToForm(ExamMidSemRepeatSettingForm settingForm)throws Exception {
         ExamMidSemRepeatSettingTo to=settingForm.getRepeatSettingMap().get(settingForm.getRepeatSettingId());
         settingForm.setAcademicYear(to.getAcademicYear());
         settingForm.setOrigAcademicYear(to.getAcademicYear());
         settingForm.setExamType(to.getExamType());
         settingForm.setOrigExamType(to.getExamType());
         settingForm.setExamName(String.valueOf(to.getExamId()));
         settingForm.setOrigExamName(String.valueOf(to.getExamId()));
         settingForm.setApplicationOpenFrom(to.getApplicationStartDate());
         settingForm.setOrigApplicationStartDate(to.getApplicationStartDate());
         settingForm.setApplicationOpenTill(to.getApplicationEndDate());
         settingForm.setOrigApplicationEndDate(to.getApplicationEndDate());
         settingForm.setFeePaymentTill(to.getFeePaymentEndDate());
         settingForm.setOrigFeePaymentEndDate(to.getFeePaymentEndDate());
         settingForm.setFeesPerSubject(to.getFeesPerSubject());
         settingForm.setOrigFeePerSubject(to.getFeesPerSubject());
         settingForm.setDisableFields("Yes");
         
	}
}
