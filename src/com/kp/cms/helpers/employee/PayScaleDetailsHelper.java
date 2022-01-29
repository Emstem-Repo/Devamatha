package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.forms.employee.PayScaleDetailsForm;

import com.kp.cms.to.employee.PayScaleTO;

public class PayScaleDetailsHelper {
	private static final Log log = LogFactory.getLog(PayScaleDetailsHelper.class);
	public static volatile PayScaleDetailsHelper payScaleHelper = null;
    private PayScaleDetailsHelper(){
    	
    }
	public static PayScaleDetailsHelper getInstance() {
		if (payScaleHelper == null) {
			payScaleHelper = new PayScaleDetailsHelper();
			return payScaleHelper;
		}
		return payScaleHelper;
	}
	public PayScaleBO convertFormTOBO(PayScaleDetailsForm payScaleForm){
		PayScaleBO payScale=new PayScaleBO();
		payScale.setPayScale(payScaleForm.getPayScale());
		payScale.setScale(payScaleForm.getScale());
		payScale.setCreatedBy(payScaleForm.getUserId());
		payScale.setCreatedDate(new Date());
		payScale.setIsActive(true);
		
		if(payScaleForm.getTeachingStaff()!=null && !payScaleForm.getTeachingStaff().isEmpty()){
			String Value= payScaleForm.getTeachingStaff();
			if(Value.equals("1"))
				payScale.setTeachingStaff(true);
			else
				payScale.setTeachingStaff(false);
		}
	return payScale;
	}
    public List<PayScaleTO> convertBosToTOs(List<PayScaleBO> payScaleBo){
    	List<PayScaleTO> payScaleToList=new ArrayList<PayScaleTO>();
		Iterator itr=payScaleBo.iterator();
		while(itr.hasNext()){
			PayScaleBO payscale=(PayScaleBO)itr.next();
			PayScaleTO payscaleTo=new PayScaleTO();
			payscaleTo.setId(payscale.getId());
			payscaleTo.setPayScale(payscale.getPayScale());
			payscaleTo.setScale(payscale.getScale());
			if(payscale.getTeachingStaff()!=null ){
				
				String Value=String.valueOf(payscale.getTeachingStaff());
				if(Value.equals("true"))
					payscaleTo.setTeachingStaff("Teaching");
				else
					payscaleTo.setTeachingStaff("Non-Teaching");
				 
			}
			payScaleToList.add(payscaleTo);
		}
		return payScaleToList;
	}
    public void setBotoForm(PayScaleDetailsForm payScaleForm,PayScaleBO payScale){
    	if(payScale!=null){
    		payScaleForm.setPayScale(payScale.getPayScale());
    		payScaleForm.setScale(payScale.getScale());
    		if(payScale.getTeachingStaff()!=null ){
				
				String Value=String.valueOf(payScale.getTeachingStaff());
				if(Value.equals("true"))
					payScaleForm.setTeachingStaff("1");
				else
					payScaleForm.setTeachingStaff("0");
				 
			}
    		
    		payScaleForm.setOrigPayscale(payScale.getPayScale());
    		payScaleForm.setOrigScale(payScale.getScale());
    		
    		if(payScale.getTeachingStaff()!=null ){
				
				String Value=String.valueOf(payScale.getTeachingStaff());
				if(Value.equals("true"))
					payScaleForm.setOrigteachingStaff("1");
				else
					payScaleForm.setOrigteachingStaff("0");
				 
			}
    		
    	}
    }
    public PayScaleBO convertFormToBo(PayScaleDetailsForm payScaleForm){
    	PayScaleBO payScaleBo=new PayScaleBO();
    	payScaleBo.setId(payScaleForm.getId());
    	//payScaleBo.setPayScale(employeeResumeForm.getPayScale());
    	payScaleBo.setPayScale(payScaleForm.getPayScale());
    	payScaleBo.setScale(payScaleForm.getScale());
    	payScaleBo.setLastModifiedDate(new Date());
    	payScaleBo.setModifiedBy(payScaleForm.getUserId());
    	payScaleBo.setIsActive(true);
    	if(payScaleForm.getTeachingStaff()!=null && !payScaleForm.getTeachingStaff().isEmpty()){
			String Value= payScaleForm.getTeachingStaff();
			if(Value.equals("1"))
				payScaleBo.setTeachingStaff(true);
			else
				payScaleBo.setTeachingStaff(false);
		}
    	return payScaleBo;
    }
}
