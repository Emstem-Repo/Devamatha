package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.to.inventory.RequestVsIssueTO;

public class RequestVsIssueHelper {
	
	/**
	 * 
	 * @param requestIssueList
	 * @return
	 * @throws Exception
	 */
	public List<RequestVsIssueTO> convertBOstoTOs(List<Object[]> requestIssueList) throws Exception {
	
		List<RequestVsIssueTO> requestVsIssueTOList = new ArrayList<RequestVsIssueTO>();
		RequestVsIssueTO requestVsIssueTO = null;
		
		if(requestIssueList != null ){
			
			Iterator<Object[]> requestIssueIterator = requestIssueList.iterator();
			
			while (requestIssueIterator.hasNext()) {
				
				requestVsIssueTO = new RequestVsIssueTO();
				Object[] searchResults = (Object[]) requestIssueIterator.next();
				
				if(searchResults[0]!=null && searchResults[1]!=null){
					requestVsIssueTO.setNameWithCode(searchResults[1].toString()+"("+searchResults[0].toString()+")");
				}
				if(searchResults[2]!=null){
					requestVsIssueTO.setRequestedQuantity(searchResults[2].toString());
				}
				if(searchResults[3]!=null){
					requestVsIssueTO.setIssuedQuantity(searchResults[3].toString());
				}
				if(searchResults[4]!=null){
					requestVsIssueTO.setRequestedBy(searchResults[4].toString());
				}
				if(searchResults[5]!=null){
					requestVsIssueTO.setIssuedTo(searchResults[5].toString());
				}
				if(searchResults[6]!=null){
					requestVsIssueTO.setRequestedDate(searchResults[6].toString());
				}
				if(searchResults[7]!=null){
					requestVsIssueTO.setIssuedDate(searchResults[7].toString());
				}
				requestVsIssueTOList.add(requestVsIssueTO);
			}	
				
		}		
		return requestVsIssueTOList;
	}
}