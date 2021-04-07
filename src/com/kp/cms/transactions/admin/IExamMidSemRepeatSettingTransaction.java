package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.admin.ExamMidSemRepeatSettingForm;

public interface IExamMidSemRepeatSettingTransaction {

	public List<ExamTypeUtilBO> getExamTypeList()throws Exception;

	public String getCurrentExamNameByExamType(int examTypeId)throws Exception;

	public boolean saveOrUpdateMidSemRepeatSetting(ExamMidsemRepeatSetting repeatSetting,String mode)throws Exception;

	public List<ExamMidsemRepeatSetting> getMidSemRepeatSettingList()throws Exception;

	public boolean duplicatCheckRepeatSetting(ExamMidSemRepeatSettingForm settingForm)throws Exception;

	public ExamMidsemRepeatSetting getMidSemRepeatSettingById(int repeatSettingId)throws Exception;

}
