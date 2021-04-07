package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.forms.hostel.ViewRequisitionsForm;
import com.kp.cms.to.hostel.HostelAllocationReportTO;
import com.kp.cms.to.hostel.VRequisitionsTO;

public class AllocationReportHelper {

	
	
	
	public static List<HostelAllocationReportTO> copyAllocationBOToTO(List<Object[]> requisitionBO) {
		
		List<HostelAllocationReportTO> hostelAllocationList = new ArrayList<HostelAllocationReportTO>();
		HostelAllocationReportTO allocationTO; 
		
		if(requisitionBO!=null && !requisitionBO.isEmpty()){	
			Iterator<Object[]> requisitionsIterator = requisitionBO.iterator();
				while (requisitionsIterator.hasNext()) {
					Object[] obj = (Object[]) requisitionsIterator.next();
					allocationTO = new HostelAllocationReportTO();
					if(obj[0] != null){
					allocationTO.setHostelName((obj[0].toString()));
					}
		
					if(obj[1] != null){
						allocationTO.setAllocatedRoom(obj[1].toString());
					}
						if(obj[2] != null){
							allocationTO.setAllocationDate(obj[2].toString());
					}
					if(obj[3] != null){
						allocationTO.setRoomType(obj[3].toString());
					}
						hostelAllocationList.add(allocationTO);
				
				}
		
		}
		return hostelAllocationList;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
