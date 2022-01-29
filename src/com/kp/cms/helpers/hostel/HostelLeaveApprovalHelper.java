package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Array;

import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.forms.hostel.HostelLeaveApprovalForm;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;
import com.kp.cms.utilities.CommonUtil;

public class HostelLeaveApprovalHelper {
	private static final Log log = LogFactory.getLog(HostelLeaveApprovalHelper.class);
	private static volatile HostelLeaveApprovalHelper helper = null;
	public static HostelLeaveApprovalHelper getInstance(){
		if(helper == null){
			helper = new HostelLeaveApprovalHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param hlLeavesList
	 * @param objForm 
	 * @param objForm 
	 * @return
	 * @throws Exception
	 */
	public  Map<String,List<HostelLeaveApprovalTo>> convertBOToMap( List<Object[]> hlLeavesList, HostelLeaveApprovalForm objForm) throws Exception{
		Map<String,List<HostelLeaveApprovalTo>> leaveApprovalMap = new HashMap<String, List<HostelLeaveApprovalTo>>();
		List<String> regNoList = new ArrayList<String>();
		if(hlLeavesList!=null && !hlLeavesList.isEmpty()){
			Iterator<Object[]> iterator = hlLeavesList.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(!leaveApprovalMap.containsKey(objects[2].toString())){
					HostelLeaveApprovalTo approvalTo = new HostelLeaveApprovalTo();
					List<HostelLeaveApprovalTo> hostelLeaveApprovalTosList = new ArrayList<HostelLeaveApprovalTo>();
					approvalTo.setId(Integer.parseInt(objects[0].toString()));
					approvalTo.setHlAdmissionId(Integer.parseInt(objects[1].toString()));
					if(objects[2]!=null){
						approvalTo.setRegisterNo(objects[2].toString());
					}
					if(objects[3]!=null){
						approvalTo.setClassName(objects[3].toString());
					}
					if(objects[4]!=null){
						approvalTo.setName(objects[4].toString());
					}
					
					if(objects[5]!=null){
						if(objects[6]!=null){
							approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(objects[5].toString())+" "+"("+objects[6].toString()+")");
						}else{
							approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(objects[5].toString()));
						}
					}
					if(objects[7]!=null){
						if(objects[8]!=null){
							approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(objects[7].toString())+" "+"("+objects[8].toString()+")");
						}else{
							approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(objects[7].toString()));
						}
					}
					if(objects[9]!=null){
						approvalTo.setEmailId(objects[9].toString());
					}
					if(objects[10]!=null){
						approvalTo.setMobileNo(objects[10].toString());
					}
					regNoList.add(approvalTo.getRegisterNo());
					hostelLeaveApprovalTosList.add(approvalTo);
					leaveApprovalMap.put(approvalTo.getRegisterNo(), hostelLeaveApprovalTosList);
				}else{
					List<HostelLeaveApprovalTo> tos = leaveApprovalMap.get(objects[2].toString());
					if(tos!=null && !tos.isEmpty()){
						HostelLeaveApprovalTo approvalTo = new HostelLeaveApprovalTo();
						approvalTo.setId(Integer.parseInt(objects[0].toString()));
						approvalTo.setHlAdmissionId(Integer.parseInt(objects[1].toString()));
						if(objects[2]!=null){
							approvalTo.setRegisterNo(objects[2].toString());
						}
						if(objects[3]!=null){
							approvalTo.setClassName(objects[3].toString());
						}
						if(objects[4]!=null){
							approvalTo.setName(objects[4].toString());
						}
						
						if(objects[5]!=null){
							if(objects[6]!=null){
								approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(objects[5].toString())+" "+"("+objects[6].toString()+")");
							}else{
								approvalTo.setDateAndTimeFrom(CommonUtil.formatSqlDate1(objects[5].toString()));
							}
						}
						if(objects[7]!=null){
							if(objects[8]!=null){
								approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(objects[7].toString())+" "+"("+objects[8].toString()+")");
							}else{
								approvalTo.setDateAndTimeTo(CommonUtil.formatSqlDate1(objects[7].toString()));
							}
						}
						if(objects[9]!=null){
							approvalTo.setEmailId(objects[9].toString());
						}
						if(objects[10]!=null){
							approvalTo.setMobileNo(objects[10].toString());
						}
						regNoList.add(approvalTo.getRegisterNo());
						tos.add(approvalTo);
						leaveApprovalMap.put(approvalTo.getRegisterNo(), tos);
					}
				}
			}
			objForm.setRegNoList(regNoList);
		}
		return leaveApprovalMap;
	}
	/**
	 * @param mapList
	 * @param previousLeaveDetailsMap
	 * @return
	 */
	public List<HostelLeaveApprovalTo> convertMapListToTOList( Map<String, List<HostelLeaveApprovalTo>> mapList, 
			Map<String, List<HostelLeaveApprovalTo>> previousLeaveDetailsMap) throws Exception{
		List<HostelLeaveApprovalTo> leaveDetailsList = new ArrayList<HostelLeaveApprovalTo>();
		if(mapList!=null && !mapList.isEmpty()){
			Iterator<Map.Entry<String,List<HostelLeaveApprovalTo>>> iterator = mapList.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<java.lang.String, java.util.List<com.kp.cms.to.hostel.HostelLeaveApprovalTo>> entry = (Map.Entry<java.lang.String, java.util.List<com.kp.cms.to.hostel.HostelLeaveApprovalTo>>) iterator
						.next();
				List<HostelLeaveApprovalTo> list = entry.getValue();
				if(list!=null && !list.isEmpty()){
					Iterator<HostelLeaveApprovalTo> iterator2 = list.iterator();
					while (iterator2.hasNext()) {
						HostelLeaveApprovalTo hostelLeaveApprovalTo = (HostelLeaveApprovalTo) iterator2 .next();
						if(previousLeaveDetailsMap.containsKey(hostelLeaveApprovalTo.getRegisterNo())){
							List<HostelLeaveApprovalTo> approvalToList = previousLeaveDetailsMap.get(hostelLeaveApprovalTo.getRegisterNo());
							if(approvalToList!=null && !approvalToList.isEmpty()){
								Iterator<HostelLeaveApprovalTo> iterator3 = approvalToList.iterator();
								int noOfApplicationCount =0;
								int noOfApprovalCount =0;
								int noOfRejectCount = 0;
								int noOfCancelledCount = 0;
								while (iterator3.hasNext()) {
									HostelLeaveApprovalTo to = (HostelLeaveApprovalTo) iterator3 .next();
									noOfApplicationCount++;
									if(to.getNoOfLeaveApproval()!=0){
										noOfApprovalCount++;
									}
									if(to.getNoOfLeaveRejected()!=0){
										noOfRejectCount++;
									}
									if(to.getNoOfLeaveCancelled()!=0){
										noOfCancelledCount++;
									}
								}
								hostelLeaveApprovalTo.setNoOfLeaveApplications(noOfApplicationCount);
								hostelLeaveApprovalTo.setNoOfLeaveApproval(noOfApprovalCount);
								hostelLeaveApprovalTo.setNoOfLeaveRejected(noOfRejectCount);
								hostelLeaveApprovalTo.setNoOfLeaveCancelled(noOfCancelledCount);
								leaveDetailsList.add(hostelLeaveApprovalTo);
							}
						}else{
							leaveDetailsList.add(hostelLeaveApprovalTo);
						}
					}
				} 
			}
		}
		return leaveDetailsList;
	}
	
}
