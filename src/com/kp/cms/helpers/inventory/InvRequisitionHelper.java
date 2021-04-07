package com.kp.cms.helpers.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.InvItem;
import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.bo.admin.InvRequestItem;
import com.kp.cms.forms.inventory.InvRequisitionForm;
import com.kp.cms.to.inventory.InvRequestTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.utilities.CommonUtil;

public class InvRequisitionHelper {

	/**
	 * @param invRequisitionForm
	 * @return
	 */
	public static InvRequest convertFormToTO(InvRequisitionForm invRequisitionForm) {
		InvRequest invRequest = new InvRequest();
		InvLocation invLocation = new InvLocation(); 
		
		if(invRequisitionForm.getInventoryId()!=null){
			invLocation.setId(Integer.parseInt(invRequisitionForm.getInventoryId()));
			invRequest.setInvLocation(invLocation);
			invRequest.setIsActive(Boolean.TRUE);
		}	
		if(invRequisitionForm.getInventoryId()!=null){
			invRequest.setCreatedBy(invRequisitionForm.getUserId());
			invRequest.setModifiedBy(invRequisitionForm.getUserId());
			invRequest.setCreatedDate(new Date());
			invRequest.setLastModifiedDate(new Date());
			invRequest.setStatus("Request");
			invRequest.setRequestDate(new Date());
			invRequest.setRequestedBy(invRequisitionForm.getUserName());
		}
		if(invRequisitionForm.getRequisitionNo()!=null && !invRequisitionForm.getRequisitionNo().isEmpty()){
			invRequest.setRequisitionNo(Integer.parseInt(invRequisitionForm.getRequisitionNo()));
		}
		if(invRequisitionForm.getDateOfDelivery()!=null){
			invRequest.setDeliveryDate(CommonUtil.ConvertStringToSQLDate(invRequisitionForm.getDateOfDelivery()));
		}
		if(invRequisitionForm.getDescription()!=null){
			invRequest.setDescription(invRequisitionForm.getDescription());
		}
		Set<InvRequestItem> requisitionItemSet = new HashSet<InvRequestItem>();
		
		if(invRequisitionForm.getTransferItemList()!=null && !invRequisitionForm.getTransferItemList().isEmpty()){
		Iterator<ItemTO>	requisitionItr = invRequisitionForm.getTransferItemList().iterator();
		while (requisitionItr.hasNext()) {
			ItemTO invRequestItemTO = (ItemTO) requisitionItr.next();
			InvRequestItem invRequestItem = new InvRequestItem();
			InvItem invItem = new InvItem();
			if(invRequestItemTO.getId()!=null && !invRequestItemTO.getId().isEmpty()){
				invRequestItem.setCreatedBy(invRequisitionForm.getUserId());
				invRequestItem.setModifiedBy(invRequisitionForm.getUserId());
				invRequestItem.setCreatedDate(new Date());
				invRequestItem.setLastModifiedDate(new Date());
				}
			
			if(invRequestItemTO.getId()!=null && !invRequestItemTO.getId().isEmpty()){
				invItem.setId(Integer.parseInt(invRequestItemTO.getId()));
				invRequestItem.setInvItem(invItem);
			}
			if(invRequestItemTO.getQuantityIssued()!=null && !invRequestItemTO.getQuantityIssued().isEmpty()){
				invRequestItem.setQuantity(new BigDecimal(invRequestItemTO.getQuantityIssued()));
			}
			if(invRequestItemTO.getId()!=null && invRequestItemTO.getQuantityIssued()!=null && !invRequestItemTO.getQuantityIssued().isEmpty() && !invRequestItemTO.getId().isEmpty()){
				invRequestItem.setIsActive(Boolean.TRUE); 
			}
			requisitionItemSet.add(invRequestItem);
		}
		invRequest.setInvRequestItems(requisitionItemSet);
		}
		return invRequest;
	}

	/**
	 * @param invRequestList
	 * @return
	 */
	public static List<InvRequestTO> convertBOTOTOList(List<InvRequest> invRequestList) {
		List<InvRequestTO> invRequestTOList = new ArrayList<InvRequestTO>();
		if(invRequestList!=null){
			Iterator<InvRequest> invRequestItr = invRequestList.iterator();
			while (invRequestItr.hasNext()) {				
				InvRequest invRequest = (InvRequest) invRequestItr.next();
				InvRequestTO invRequestTO = new InvRequestTO();
				if(invRequest.getId()!=0){
					invRequestTO.setReqId(String.valueOf(invRequest.getId()));
				}
				if(invRequest.getInvLocation()!=null && invRequest.getInvLocation().getName()!=null){
					invRequestTO.setDepartment(invRequest.getInvLocation().getName());
				}
				if(invRequest.getRequestedBy()!=null){
					invRequestTO.setRequestedBy(invRequest.getRequestedBy());
				}
				if(invRequest.getRequestDate()!=null){
					invRequestTO.setRaisedOn(CommonUtil.getStringDate(invRequest.getRequestDate()));
				}
				if(invRequest.getStatus()!=null){
					invRequestTO.setStatus(invRequest.getStatus());
				}
				if(invRequest.getComments()!=null){
					invRequestTO.setComments(invRequest.getComments());
				}
				invRequestTOList.add(invRequestTO);
			}		
		}		
		return invRequestTOList;
	}

	/**
	 * @param invRequisitionForm
	 * @return
	 */
	public static List<InvRequest> populateBOList(
			InvRequisitionForm invRequisitionForm) {
		List<InvRequest> invRequestList = new ArrayList<InvRequest>();
		if(invRequisitionForm.getRequistionApprovalList()!=null && !invRequisitionForm.getRequistionApprovalList().isEmpty()){
			Iterator<InvRequestTO> invRequestTOItr = invRequisitionForm.getRequistionApprovalList().iterator();
			while (invRequestTOItr.hasNext()) {
				InvRequestTO invRequestTO = (InvRequestTO) invRequestTOItr.next();
				InvRequest invRequest = new InvRequest();
				if(invRequestTO.getReqId()!=null){
					invRequest.setId(Integer.parseInt(invRequestTO.getReqId()));
				}
				if(invRequestTO.getComments()!=null){
					invRequest.setComments(invRequestTO.getComments());
				}
				if(invRequestTO.getStatus()!=null){
					invRequest.setStatus(invRequestTO.getStatus());
				}
				invRequestList.add(invRequest);
			}
		}		
		
		
		return invRequestList;
	}

	/**
	 * @param invRequest
	 * @return
	 */
	public static InvRequestTO populateTO(InvRequest invRequest) {
		InvRequestTO invRequestTO = new InvRequestTO();
		if(invRequest!=null){
			if(invRequest.getDeliveryDate()!=null){
				invRequestTO.setDeliveryDate(CommonUtil.getStringDate(invRequest.getDeliveryDate()));
			}
			if(invRequest.getStatus()!=null){
				invRequestTO.setCurrentStatus(invRequest.getStatus());
			}
			if(invRequest.getDescription()!=null){
				invRequestTO.setDescription(invRequest.getDescription());
			}
			if(invRequest.getRequisitionNo()!=null){
				invRequestTO.setRequistionNo(String.valueOf(invRequest.getRequisitionNo()));
			}
			List<ItemTO> itemTOList = new ArrayList<ItemTO>(); 
			if(invRequest.getInvRequestItems()!=null && !invRequest.getInvRequestItems().isEmpty()){
				Iterator<InvRequestItem> invRequestItemItr = invRequest.getInvRequestItems().iterator();
				while (invRequestItemItr.hasNext()) {
					InvRequestItem invRequestItem = (InvRequestItem) invRequestItemItr.next();
					ItemTO itemTO = new ItemTO();
					if(invRequestItem.getInvItem()!=null && invRequestItem.getInvItem().getName()!=null){
						itemTO.setName(invRequestItem.getInvItem().getName());
					}
					if(invRequestItem.getInvItem()!=null && invRequestItem.getInvItem().getInvUomByInvIssueUomId()!=null && invRequestItem.getInvItem().getInvUomByInvIssueUomId().getName()!=null){
						itemTO.setIssueUomName(invRequestItem.getInvItem().getInvUomByInvIssueUomId().getName());
					}
					if(invRequestItem.getQuantity()!=null){
						itemTO.setQuantityIssued(String.valueOf(invRequestItem.getQuantity()));
					}
					itemTOList.add(itemTO);
				}
			}
			invRequestTO.setItemTOList(itemTOList);
		}		
		return invRequestTO;
	}

	/**
	 * @param invRequestList
	 * @return
	 */
	public static List<InvRequestTO> inventoryLocationTO(
			List<InvRequest> invRequestList) {
		List<InvRequestTO> invRequestTOList = new ArrayList<InvRequestTO>();
		if(invRequestList!=null){
			Iterator<InvRequest> invRequestItr = invRequestList.iterator();
			while (invRequestItr.hasNext()) {				
				InvRequest invRequest = (InvRequest) invRequestItr.next();
				InvRequestTO invRequestTO = new InvRequestTO();
				if(invRequest.getId()!=0){
					invRequestTO.setReqId(String.valueOf(invRequest.getId()));
				}
				if(invRequest.getInvLocation()!=null && invRequest.getInvLocation().getName()!=null){
					invRequestTO.setDepartment(invRequest.getInvLocation().getName());
				}
				if(invRequest.getRequestedBy()!=null){
					invRequestTO.setRequestedBy(invRequest.getRequestedBy());
				}
				if(invRequest.getRequestDate()!=null){
					invRequestTO.setRaisedOn(CommonUtil.getStringDate(invRequest.getRequestDate()));
				}
				if(invRequest.getDeliveryDate()!=null){
					invRequestTO.setDeliveryDate(CommonUtil.getStringDate(invRequest.getDeliveryDate()));
				}
				if(invRequest.getStatus()!=null){
					invRequestTO.setCurrentStatus(invRequest.getStatus());
				}
				if(invRequest.getDescription()!=null){
					invRequestTO.setDescription(invRequest.getDescription());
				}
				if(invRequest.getRequisitionNo()!=null){
					invRequestTO.setRequistionNo(String.valueOf(invRequest.getRequisitionNo()));
				}
				if(invRequest.getComments()!=null){
					invRequestTO.setComments(invRequest.getComments());
				}
				List<ItemTO> itemTOList = new ArrayList<ItemTO>(); 
				if(invRequest.getInvRequestItems()!=null && !invRequest.getInvRequestItems().isEmpty()){
					Iterator<InvRequestItem> invRequestItemItr = invRequest.getInvRequestItems().iterator();
					while (invRequestItemItr.hasNext()) {
						InvRequestItem invRequestItem = (InvRequestItem) invRequestItemItr.next();
						ItemTO itemTO = new ItemTO();
						if(invRequestItem.getInvItem()!=null && invRequestItem.getInvItem().getName()!=null){
							itemTO.setName(invRequestItem.getInvItem().getName());
						}
						if(invRequestItem.getInvItem()!=null && invRequestItem.getInvItem().getInvUomByInvIssueUomId()!=null && invRequestItem.getInvItem().getInvUomByInvIssueUomId().getName()!=null){
							itemTO.setIssueUomName(invRequestItem.getInvItem().getInvUomByInvIssueUomId().getName());
						}
						if(invRequestItem.getQuantity()!=null){
							itemTO.setQuantityIssued(String.valueOf(invRequestItem.getQuantity()));
						}
						itemTOList.add(itemTO);
					}
				}
				invRequestTO.setItemTOList(itemTOList);

				invRequestTOList.add(invRequestTO);
			}		
		}		
		return invRequestTOList;
	}

}
