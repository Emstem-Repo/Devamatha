package com.kp.cms.helpers.admission;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.ApplicationStatusUpdate;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ApplicationStatusUpdateForm;
import com.kp.cms.forms.employee.EmpLeaveAllotmentForm;
import com.kp.cms.to.admission.ApplicationStatusUpdateTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.transactions.admission.IApplicationStatusUpdateTxn;
import com.kp.cms.transactionsimpl.admission.ApplicationStatusUpdateTxnImpl;

public class ApplicationStatusUpdateHelper {
	private static final Log log = LogFactory.getLog(ApplicationStatusUpdateHelper.class);
	public static volatile ApplicationStatusUpdateHelper statusHelper=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static ApplicationStatusUpdateHelper getInstance(){
		if(statusHelper==null){
			statusHelper= new ApplicationStatusUpdateHelper();}
		return statusHelper;
	}
	public Map<Integer,String> setToMap(List<ApplicationStatus> status){
        Map<Integer,String> statusMap=new HashMap<Integer,String>();
		Iterator itr=status.iterator();
        while(itr.hasNext()){
        	ApplicationStatus applicationStatus=(ApplicationStatus)itr.next();
        	if(applicationStatus.getShortName()!=null && !applicationStatus.getShortName().isEmpty())
        	statusMap.put(applicationStatus.getId(), applicationStatus.getShortName());
        }
		return statusMap;
	}
	public List<ApplicationStatusUpdateTO> convertBosToTOs(List<ApplicationStatusUpdate> applicationStatusUpdate){
    	List<ApplicationStatusUpdateTO> statusUpdateTO=new ArrayList<ApplicationStatusUpdateTO>();
		Iterator itr=applicationStatusUpdate.iterator();
		while(itr.hasNext()){
			ApplicationStatusUpdate statusUpdate=(ApplicationStatusUpdate)itr.next();
			ApplicationStatusUpdateTO statusTO=new ApplicationStatusUpdateTO();
			statusTO.setApplicationNo(String.valueOf(statusUpdate.getAdmApplnNO().getApplnNo()));
			statusTO.setApplicationStatus(statusUpdate.getApplicationStatus().getShortName());
			statusUpdateTO.add(statusTO);
		}
		return statusUpdateTO;
	}
	public ApplicationStatusUpdate convertFormTOBO(ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception{
		ApplicationStatusUpdate statusUpdate=new ApplicationStatusUpdate();
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		//Map<String,Integer> admApplnMap=transaction.getAdmApplnMap();
		AdmAppln appln=new AdmAppln();
		//if(admApplnMap!=null && admApplnMap.isEmpty()){
		//appln.setId(admApplnMap.get(applicationStatusUpdateForm.getApplicationNo()));
		//statusUpdate.setAdmApplnNO(appln);
		/*}else*/
		if(applicationStatusUpdateForm.getApplicationNo()!=null && !applicationStatusUpdateForm.getApplicationNo().isEmpty()){
			int admApplnId=transaction.getAdmApplnId(applicationStatusUpdateForm.getApplicationNo());
			if(admApplnId>0){
				appln.setId(admApplnId);
				statusUpdate.setAdmApplnNO(appln);
			}else{
				throw new BusinessException("NoRecordsFound");
			}
		}
		ApplicationStatus status=new ApplicationStatus();
		status.setId(Integer.parseInt(applicationStatusUpdateForm.getApplicationStatus()));
		statusUpdate.setApplicationStatus(status);
		statusUpdate.setCreatedBy(applicationStatusUpdateForm.getUserId());
		statusUpdate.setCreatedDate(new Date());
		return statusUpdate;
	}
	public List<ApplicationStatusUpdate> parseOMRData(InputStream inStream,Map<String,String> applicationStatusMap,ApplicationStatusUpdateForm applicationStatusUpdateForm){
		List<ApplicationStatusUpdate> statusUpdateList=new ArrayList<ApplicationStatusUpdate>();
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		List<Integer> applnNos=new ArrayList<Integer>();
		List<Integer> applnNosUnavailable=new ArrayList<Integer>();
		List<Integer> applnStatusNotAvailable=new ArrayList<Integer>();
		try{
		LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(inStream));
		 while(parser.getLine()!=null){
			 boolean isDuplicate=false;
			 ApplicationStatusUpdate statusUpdate=new ApplicationStatusUpdate();
			 AdmAppln appln=new AdmAppln();
			 if(parser.getValueByLabel("Application NO")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Application NO"))){
				 appln.setId(transaction.getAdmApplnId(parser.getValueByLabel("Application NO")));
				 statusUpdate.setAdmApplnNO(appln);
			 }if(parser.getValueByLabel("Application Status")!=null && !StringUtils.isEmpty(parser.getValueByLabel("Application Status"))){
				 ApplicationStatus status=new ApplicationStatus();
				 if(applicationStatusMap!=null && applicationStatusMap.containsKey(parser.getValueByLabel("Application Status")))
				 status.setId(Integer.parseInt(applicationStatusMap.get(parser.getValueByLabel("Application Status"))));
				 statusUpdate.setApplicationStatus(status);
			 }
			 statusUpdate.setCreatedBy(applicationStatusUpdateForm.getUserId());
			 statusUpdate.setCreatedDate(new Date());
			 if(statusUpdate!=null && !statusUpdate.toString().isEmpty()){
				 applicationStatusUpdateForm.setApplicationStatus(String.valueOf(statusUpdate.getApplicationStatus().getId()));
				 isDuplicate=transaction.duplicateCheck(applicationStatusUpdateForm, statusUpdate.getAdmApplnNO().getId());
			 }
			 if(!isDuplicate && statusUpdate.getApplicationStatus().getId()!=0 && statusUpdate.getAdmApplnNO().getId()!=0){
			 statusUpdateList.add(statusUpdate);
			 }else if(isDuplicate){
				 applnNos.add(Integer.parseInt(parser.getValueByLabel("Application NO")));
			 }else if(statusUpdate.getAdmApplnNO().getId()==0){
				 applnNosUnavailable.add(Integer.parseInt(parser.getValueByLabel("Application NO")));
			 }else if(statusUpdate.getApplicationStatus().getId()==0){
				 applnStatusNotAvailable.add(Integer.parseInt(parser.getValueByLabel("Application NO")));
			 }
		 }
		 applicationStatusUpdateForm.setApplnNos(applnNos);
		 applicationStatusUpdateForm.setApplnNosUnavailable(applnNosUnavailable);
		 applicationStatusUpdateForm.setApplnStatusUnavailable(applnStatusNotAvailable);
		}catch(Exception e){
			log.error(e);
		}
		return statusUpdateList;
	}
}
