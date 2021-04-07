package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.forms.hostel.ViewReqStatusForm;
import com.kp.cms.to.hostel.VReqStatusTo;
import com.kp.cms.utilities.CommonUtil;
public class RequisitionStatusHelper {

// query to get know the requisition no belongs to student or Employee  
	public static String Query(ViewReqStatusForm viewReqStatusForm) {
		String query="from HlApplicationForm hl where hl.isActive=1 and hl.requisitionNo= "+"'"+viewReqStatusForm.getRequisitionno()+"'";
		return query;
	}
	// query to get know the a student or employee is paid the fee or not 
	public static String isfeepaidQuery(ViewReqStatusForm viewReqStatusForm) {
		String query="select hl.isHostelFeePaid from HlApplicationForm hl where hl.requisitionNo= "+"'"+viewReqStatusForm.getRequisitionno()+"'";
		return query;
	}
	public static String buildQuery(ViewReqStatusForm viewReqStatusForm, String isStaff, String isFeepaid) {
		 String query="select hl.appliedDate,"+
		 "hl.requisitionNo,"+
		 "hl.hlHostelByHlAppliedHostelId.name,"+
		  "hl.hlRoomTypeByHlAppliedRoomTypeId.name,"+
		  "hl.hlRoomTypeByHlApprovedRoomTypeId.name,"+
		  "hl.hlStatus.name,";
		  if(isStaff.equalsIgnoreCase("false"))
		  {
		 query= query+"hl.admAppln.applnNo," +
		 		"hl.admAppln.personalData.firstName,"+
		  " hl.admAppln.personalData.middleName,"+
		  " hl.admAppln.personalData.lastName ";
		  }else{
			  query= query+"hl.employee.id," +
			  		"hl.employee.firstName,"+
			  "hl.employee.middleName,"+
			  "hl.employee.lastName";
			  
		  }
		 
		  if(isFeepaid.equalsIgnoreCase("true")){
			  query=query+ " ,hf.amount from HlApplicationForm hl inner join hl.hlFeePayments hf where hl.id = hf.hlApplicationForm.id  and ";
					  }
		  else{
			  query=query+ " from HlApplicationForm hl where ";		  
					  }
		 
		 if(viewReqStatusForm.getRequisitionno() != null && viewReqStatusForm.getRequisitionno().length() > 0) 
		 {	
							String reqno = " hl.requisitionNo= "+"'"+viewReqStatusForm.getRequisitionno()+"'";
							query = query + reqno;
			}
		return query;
	}
	

	public static List<VReqStatusTo> copyRequisitionBOToTO(List<HlApplicationForm> requisitionBO) {
		List<VReqStatusTo> vRequStatusList = new ArrayList<VReqStatusTo>();
		VReqStatusTo vReqStatusTo; 
		
		if(requisitionBO!=null && !requisitionBO.isEmpty()){	
			Iterator<HlApplicationForm> requisitionsIterator = requisitionBO.iterator();
				while (requisitionsIterator.hasNext()) {
					HlApplicationForm hlApplicationForm = (HlApplicationForm) requisitionsIterator.next();
					vReqStatusTo = new VReqStatusTo();
					String name="";
					if(hlApplicationForm.getIsStaff()==null || !hlApplicationForm.getIsStaff()){
						if(hlApplicationForm.getAdmAppln()!=null){
							vReqStatusTo.setApplino(Integer.toString(hlApplicationForm.getAdmAppln().getApplnNo()));
							if(hlApplicationForm.getAdmAppln().getPersonalData().getFirstName()!=null){
								name=name+hlApplicationForm.getAdmAppln().getPersonalData().getFirstName();
							}
							if(hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName()!=null){
								name=name+hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName();
							}
							if(hlApplicationForm.getAdmAppln().getPersonalData().getLastName()!=null){
								name=name+hlApplicationForm.getAdmAppln().getPersonalData().getLastName();
							}
							vReqStatusTo.setName(name);
						}
					}else{
						if(hlApplicationForm.getEmployee()!=null){
							if(hlApplicationForm.getEmployee().getCode()!=null){
								vReqStatusTo.setApplino(hlApplicationForm.getEmployee().getCode());
							}
							if(hlApplicationForm.getEmployee().getFirstName()!=null){
								name=name+hlApplicationForm.getEmployee().getFirstName();
							}
						/*	if(hlApplicationForm.getEmployee().getMiddleName()!=null){
								name=name+hlApplicationForm.getEmployee().getMiddleName();
							}
							if(hlApplicationForm.getEmployee().getLastName()!=null){
								name=name+hlApplicationForm.getEmployee().getLastName();
							}*/
							vReqStatusTo.setName(name);
						}
					}
					
		
					if(hlApplicationForm.getAppliedDate() != null){
						vReqStatusTo.setAppliedDate(CommonUtil.formatSqlDate1(hlApplicationForm.getAppliedDate().toString()));
					}
				   if(hlApplicationForm.getRequisitionNo()!= null){
					vReqStatusTo.setReqno(Integer.toString(hlApplicationForm.getRequisitionNo()));
					}
					if(hlApplicationForm.getHlHostelByHlApprovedHostelId() != null){
						vReqStatusTo.setAppliedHostel(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName());
					}
					if(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId() != null){
						vReqStatusTo.setAppliedRoom(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName());
					}
					if(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId() != null){
						vReqStatusTo.setApprovedRoom(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId().getName());
					}
					if(hlApplicationForm.getHlStatus() != null){
						vReqStatusTo.setStatus(hlApplicationForm.getHlStatus().getStatusType());
					}
					if(hlApplicationForm.getIsHostelFeePaid()!=null)
					{
						vReqStatusTo.setFees("Paid");
					}
					else
					{
						vReqStatusTo.setFees("Not Paid");
					}
					vRequStatusList.add(vReqStatusTo);
				
				}
		
		}
		return vRequStatusList;
	}

}
