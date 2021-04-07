package com.kp.cms.handlers.phd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.forms.phd.PhdInternalGuideForm;
import com.kp.cms.helpers.phd.PhdInternalGuideHelper;
import com.kp.cms.to.phd.PhdInternalGuideTO;
import com.kp.cms.transactions.phd.IPhdInternalGuideTransactions;
import com.kp.cms.transactionsimpl.phd.PhdInternalGuideImpl;

public class PhdInternalGuideHandler {
	private static final Log log = LogFactory.getLog(PhdInternalGuideHandler.class);
	private static volatile PhdInternalGuideHandler examCceFactorHandler = null;

	public static PhdInternalGuideHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new PhdInternalGuideHandler();
		}
		return examCceFactorHandler;
	}
	IPhdInternalGuideTransactions trancations=new PhdInternalGuideImpl();

	

	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void initializaData(PhdInternalGuideForm objForm) throws Exception{
		 Map<String,String> employeeMap;
		 Map<String,String> guideShipMap=trancations.guideShipMap();
		 if(guideShipMap!=null){
			 objForm.setGuideShipMap(guideShipMap);
			
		 }
		 Map<String,String> departmentMap=trancations.getDepartmentMap();
		 if(departmentMap!=null){
			 objForm.setDepartmentMap(departmentMap);
        }
		 if(objForm.getDepartmentId()!=null && !objForm.getDepartmentId().isEmpty()){
			 employeeMap=trancations.employeMap(objForm.getDepartmentId());
			 if(employeeMap!=null){
				 objForm.setEmployeeMap(employeeMap);
	      	}
		 }else{
		 employeeMap=trancations.employeeMap();
		 if(employeeMap!=null){
			 objForm.setEmployeeMap(employeeMap);
      	}
	}
		 departmentMap=trancations.getDepartmentMap();
		 if(departmentMap!=null){
			 objForm.setDepartmentMap(departmentMap);
        }
	}
	/**
	 * @param objForm
	 * @return
	 */
	public List<PhdInternalGuideTO> setInternalGuideDetails(PhdInternalGuideForm objForm) throws Exception{
		List<PhdInternalGuideBo> internalBOs=trancations.getInternalGuideDetails(objForm);
		List<PhdInternalGuideTO> guideTO=PhdInternalGuideHelper.getInstance().convertBOsTo(internalBOs);
		return guideTO;
		}
    /**
     * @param objForm
     * @param errors
     * @return
     * @throws Exception
     */
    public boolean addPhdEmployee(PhdInternalGuideForm objForm, ActionErrors errors) throws Exception{
    	PhdInternalGuideBo guideBo=PhdInternalGuideHelper.getInstance().guideFormToBO(objForm);
    	boolean isAdded = trancations.addPhdEmployee(guideBo,objForm,errors);
        return isAdded;
    }
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void editPhdemployee(PhdInternalGuideForm objForm) throws Exception{
		PhdInternalGuideBo guideBO = trancations.getGuideDetailsById(objForm.getId());
		PhdInternalGuideHelper.getInstance().setDataBoToForm(objForm, guideBO);
		Map<String,String> employeeMap=trancations.employeMap(objForm.getDepartmentId());
		 if(employeeMap!=null){
			 objForm.setEmployeeMap(employeeMap);
      	}
	}
	public boolean deletePhdemployee(PhdInternalGuideForm objForm) throws Exception{
	      boolean isDeleted = trancations.deletePhdemployee(objForm.getId());
	      return isDeleted;
	  }
	
}
