package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlComplaint;
import com.kp.cms.bo.admin.HlComplaintType;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.hostel.ComplaintsForm;
import com.kp.cms.to.hostel.ComplaintsTO;
import com.kp.cms.transactions.hostel.IComplaintsTransaction;
import com.kp.cms.transactionsimpl.hostel.ComplaintsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ComplaintsHandler {
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final Log log = LogFactory.getLog(ComplaintsHandler.class);
	public static volatile ComplaintsHandler complaintsHandler = null;

	public static ComplaintsHandler getInstance() {
		if (complaintsHandler == null) {
			 complaintsHandler = new ComplaintsHandler();
			 return complaintsHandler;
		}
		 return complaintsHandler;
	}
	
	IComplaintsTransaction complaintsTransaction = new ComplaintsTransactionImpl(); 
	
	public List<ComplaintsTO> getComplaintsList() throws Exception{
		 log.info("entering of getComplaintsList in ComplaintsHandler class..");
		 List<ComplaintsTO> complaintsList = new ArrayList<ComplaintsTO>();
		 List<HlComplaintType> list = complaintsTransaction.getComplaintsList();
		 if(list != null && list.size() != 0){
			 Iterator<HlComplaintType> iterator = list.iterator();
			 ComplaintsTO complaintsTO;
			 
			while (iterator.hasNext())  {
				
				 HlComplaintType complaintType = (HlComplaintType) iterator.next();
				
				 complaintsTO = new ComplaintsTO();
				 complaintsTO.setId(complaintType.getId());
				 complaintsTO.setType(complaintType.getType());
				 
				 complaintsTO.setIsActive(complaintType.getIsActive());
				
				 complaintsList.add(complaintsTO);
			}
		}
		 log.info("exit of getComplaintsList in ComplaintsHandler class..");
		 return complaintsList;
	}
	
	public boolean saveComplaintDetails(ComplaintsForm complaintsForm,HlApplicationForm hlApplicationFormBo) throws Exception {
		 log.info("entering of saveComplaintDetails in ComplaintsHandler class...");
		 
		 HlComplaint complaint = new HlComplaint();
		 HlComplaintType complaintType = new HlComplaintType();
		 
		 if(complaintsForm.getComplaintType() != null){
			
			  complaintType.setId(Integer.parseInt(complaintsForm.getComplaintType()));
		}
		 complaint.setHlComplaintType(complaintType);
		
		 complaint.setDescription(complaintsForm.getDescription());
		if(complaintsForm.getTitle() != null){
			 complaint.setSubject(complaintsForm.getTitle());
		}
		
		 complaint.setDate(CommonUtil.ConvertStringToDate(complaintsForm.getLogDate()));
		 complaint.setCreatedBy(complaintsForm.getUserId());
		 complaint.setCreatedDate(new Date());
		 complaint.setIsActive(Boolean.TRUE);
		 HlApplicationForm h1 =new HlApplicationForm();
		 h1.setId(hlApplicationFormBo.getId());
		 complaint.setHlApplicationForm(h1);
		HlHostel h = new HlHostel();
		h.setId(Integer.parseInt(complaintsForm.getHostelName()));
		complaint.setHlHostel(h); 
		HlStatus s = new HlStatus();
		s.setId(4);
		complaint.setHlStatus(s);
		 boolean isAdded = complaintsTransaction.saveComplaintDetails(complaint);
		 log.info("exit of saveComplaintDetails in ComplaintsHandler class...");
		
		  return isAdded;
	}
	
	public List<ComplaintsTO> getComplaintsDetails()throws Exception
	{
		log.info("entering getComplaintsDetails of ComplaintsHandler");
		List<ComplaintsTO> complaintsList = new ArrayList<ComplaintsTO>();
		List<HlComplaint> list = complaintsTransaction.getComplaintsDetails();
		if(list != null && list.size()!= 0)
		{
			Iterator<HlComplaint> iterator = list.iterator();
			ComplaintsTO complaintsTO;
			while(iterator.hasNext())
			{
				HlComplaint complaintList = (HlComplaint)iterator.next();
				complaintsTO = new ComplaintsTO();
				if(complaintList.getHlHostel() != null)
				complaintsTO.setHostelName(complaintList.getHlHostel().getName());
				if(complaintList.getHlApplicationForm()!= null)
				complaintsTO.setRequisitionNo(Integer.toString(complaintList.getHlApplicationForm().getRequisitionNo()));
				if(complaintList.getHlComplaintType()!= null)
				complaintsTO.setComplaintTypeName(complaintList.getHlComplaintType().getType());
				complaintsTO.setLogDate(CommonUtil.formatSqlDate1(complaintList.getDate().toString()));
				complaintsTO.setId(complaintList.getId());
				complaintsList.add(complaintsTO);
			}
		}
		return complaintsList;
	
	}
	
	public boolean deleteComplaintsDetails(int id, String userId)
			throws Exception {
		log.info("inside deleteComplaintsDetails of ComplaintsDetailsHandler");
			if(complaintsTransaction != null)
			{
				return complaintsTransaction.deleteComplaintsDetails(id, userId);
			}
		log.info("existing deleteComplaintsDetails of ComplaintsDetailsHandler");
		return false;

		}
	
	public boolean duplicateStudentCheck(ComplaintsForm complaintsForm)throws Exception
	{
	 return complaintsTransaction.duplicateStudentCheck(complaintsForm);
	}
}