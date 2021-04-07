package com.kp.cms.handlers.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.admin.ExamMidSemRepeatSettingForm;
import com.kp.cms.helpers.admin.ExamMidSemRepeatSettingHelper;
import com.kp.cms.to.admin.ExamMidSemRepeatSettingTo;
import com.kp.cms.transactions.admin.IExamMidSemRepeatSettingTransaction;
import com.kp.cms.transactionsimpl.admin.ExamMidSemRepeatSettingTxnImpl;

public class ExamMidSemRepeatSettingHandler {
   
	IExamMidSemRepeatSettingTransaction transaction = ExamMidSemRepeatSettingTxnImpl.getInstance();
    public static volatile ExamMidSemRepeatSettingHandler examMidSemRepeatSettingHandler = null;
    
    
    /**
     * @return
     */
    public static ExamMidSemRepeatSettingHandler getInstance() {
		if (examMidSemRepeatSettingHandler == null) {
			examMidSemRepeatSettingHandler = new ExamMidSemRepeatSettingHandler();
			return examMidSemRepeatSettingHandler;
		}
		return examMidSemRepeatSettingHandler;
	}


	/**
	 * @param settingForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamTypeList(ExamMidSemRepeatSettingForm settingForm) throws Exception {
		List<ExamTypeUtilBO> examTypeList=transaction.getExamTypeList();
		 return ExamMidSemRepeatSettingHelper.getInstance().convertBoToMap(settingForm,examTypeList);
		
	}


	/**
	 * @param examTypeId
	 * @return
	 * @throws Exception
	 */
	public String getCurrentExamName(int examTypeId) throws Exception {
		return transaction.getCurrentExamNameByExamType(examTypeId);
	}


	/**
	 * @param settingForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveOrUpdateMidSemRepeatSetting(ExamMidSemRepeatSettingForm settingForm,String mode) throws Exception {
		ExamMidsemRepeatSetting repeatSetting=ExamMidSemRepeatSettingHelper.getInstance().convertFormToBo(settingForm,mode);
		return transaction.saveOrUpdateMidSemRepeatSetting(repeatSetting,mode);
	}



	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, ExamMidSemRepeatSettingTo> getMidSemRepeatSettingList() throws Exception {
		List<ExamMidsemRepeatSetting> settingList=transaction.getMidSemRepeatSettingList();
		return ExamMidSemRepeatSettingHelper.getInstance().convertRepeatSettingBoToTo(settingList);
	}


	/**
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheckRepeatSetting(ExamMidSemRepeatSettingForm settingForm)throws Exception {
		return transaction.duplicatCheckRepeatSetting(settingForm);
		
	}


	/**
	 * @param settingForm
	 */
	public void editMidSemRepeatSetting(ExamMidSemRepeatSettingForm settingForm)throws Exception {
          ExamMidSemRepeatSettingHelper.getInstance().convertToToForm(settingForm);		
	}


	/**
	 * @param repeatSettingId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteMidSemRepeatSetting(ExamMidSemRepeatSettingForm settingForm) throws Exception {
		ExamMidsemRepeatSetting repeatSetting=transaction.getMidSemRepeatSettingById(settingForm.getRepeatSettingId());
		boolean isDeleted=false;
		if(repeatSetting!=null){
			repeatSetting.setIsActive(false);
			repeatSetting.setModifiedBy(settingForm.getUserId());
			repeatSetting.setLastModifiedDate(new Date());
			isDeleted=transaction.saveOrUpdateMidSemRepeatSetting(repeatSetting, "Delete");
		}
		return isDeleted;
	}
	
	
}
