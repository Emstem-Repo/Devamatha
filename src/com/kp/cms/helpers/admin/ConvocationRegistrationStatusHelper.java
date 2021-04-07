package com.kp.cms.helpers.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.ConvocationSession;
import com.kp.cms.forms.admin.ConvocationRegistrationStatusForm;
import com.kp.cms.utilities.CommonUtil;


public class ConvocationRegistrationStatusHelper {
	private static volatile ConvocationRegistrationStatusHelper convocationRegistrationStatusHelper = null;
	private static final Log log = LogFactory.getLog(ConvocationRegistrationStatusHelper.class);
	private ConvocationRegistrationStatusHelper(){
		
	}
	/**
	 * @return
	 */
	public static ConvocationRegistrationStatusHelper getInstance() {
		if (convocationRegistrationStatusHelper == null) {
			convocationRegistrationStatusHelper = new ConvocationRegistrationStatusHelper();
		}
		return convocationRegistrationStatusHelper;
	}
	
	public void convertBotoTo(ConvocationRegistration bo,ConvocationRegistrationStatusForm form,ConvocationSession csession) throws Exception{
		if(bo!=null){
			form.setConvocationRegistrationId(bo.getId());
			if(bo.isParticipatingConvocation()){
				form.setStudentRegistration("Yes");
			}else{
				form.setStudentRegistration("No");
			}
			
			if(bo.isGuestPassIsRequired() && bo.getGuestPassCount()!=null && bo.getGuestPassCount()!=0 ){
				if(bo.getGuestPassCount()==1){
					form.setFlag1(true);
				}
				form.setGuestPassCount(String.valueOf(bo.getGuestPassCount()));
			}else{
				form.setFlag2(true);
				form.setGuestPassCount("Not Applied for Guest Pass");
			}
		
		
			if(bo.isGuestpassIssued()){
				form.setPassNo1(bo.getPassNo1());
				form.setPassNo2(bo.getPassNo2());
			}
		}
		if(csession!=null){
			if(csession.getDate()!=null ){
				form.setCdate(CommonUtil.formatSqlDate1(csession.getDate().toString()));
			}
			if(csession.getAmPM()!=null && !csession.getAmPM().isEmpty()){
				form.setTimeType(csession.getAmPM());
			}
		}
		
		
	}
	public ConvocationRegistration convertFormToBo(ConvocationRegistrationStatusForm form) throws Exception{
		ConvocationRegistration bo=new ConvocationRegistration();
		if(!form.getGuestPassCount().equalsIgnoreCase("Not Applied for Guest Pass")){
			bo.setId(form.getConvocationRegistrationId());
			if(form.getPassNo1()!=null && !form.getPassNo1().isEmpty())
			bo.setPassNo1(form.getPassNo1());
			if(form.getPassNo2()!=null && !form.getPassNo2().isEmpty())
			bo.setPassNo2(form.getPassNo2());
			bo.setModifiedBy(form.getUserId());
			bo.setGuestpassIssued(true);
		}
		return bo;	
	}

}
