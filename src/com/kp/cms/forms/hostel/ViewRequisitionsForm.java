package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlRoomTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RequisitionsTo;
import com.kp.cms.to.hostel.VRequisitionsTO;

public class ViewRequisitionsForm  extends BaseActionForm{
  private static final long serialVersionUID = 1L;
   private String status;
  private String startDate;
  private String endDate;
  private String roomtype;
  private String status1;
  private List<HlRoomTO> roomList;
  private String approve;	
  private List<VRequisitionsTO> requisitionsList; 
  private RequisitionsTo showRequisitionsList; 
  private List<HostelTO> hostelList;
  private List<RequisitionsTo> reqList; 
  private int hlAppId;
  private String viewRequisitionsOf;
  
  private String hostelId1;
  
  public String getHostelId1() {
	return hostelId1;
}

public void setHostelId1(String hostelId1) {
	this.hostelId1 = hostelId1;
}

private RequisitionsTo rto;
  
  
  
	public String getStatus1() {
	return status1;
}

public void setStatus1(String status1) {
	this.status1 = status1;
}

	public RequisitionsTo getRto() {
	return rto;
}

public void setRto(RequisitionsTo rto) {
	this.rto = rto;
}

	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	
	public List<RequisitionsTo> getReqList() {
		return reqList;
	}
	
	public void setReqList(List<RequisitionsTo> reqList) {
		this.reqList = reqList;
	}
	
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	
	public String getApprove() {
		return approve;
	}
	
	public RequisitionsTo getShowRequisitionsList() {
		return showRequisitionsList;
	}
	
	public void setShowRequisitionsList(RequisitionsTo showRequisitionsList) {
		this.showRequisitionsList = showRequisitionsList;
	}
	
	public void setApprove(String approve) {
		this.approve = approve;
	}
	
	public List<VRequisitionsTO> getRequisitionsList() {
		return requisitionsList;
	}
	
	public void setRequisitionsList(List<VRequisitionsTO> requisitionsList) {
		this.requisitionsList = requisitionsList;
	}
	
	public List<HlRoomTO> getRoomList() {
		return roomList;
	}
	
	public void setRoomList(List<HlRoomTO> roomList) {
		this.roomList = roomList;
	}
	
	
	  public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRoomtype() {
		return roomtype;
	}
	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}
	public void resetFields(ActionMapping mapping, HttpServletRequest request) {
		reset(mapping, request);
	 this.startDate=null;
	 this.endDate=null;
	 this.roomtype="";
	 this.status="";
	 this.status1="";
	 this.hostelList=null;
	 this.hostelId1=null;
	 super.setHostelId(null);
	 this.viewRequisitionsOf=null;
	}
	public int getHlAppId() {
		return hlAppId;
	}
	public void setHlAppId(int hlAppId) {
		this.hlAppId = hlAppId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public String getViewRequisitionsOf() {
		return viewRequisitionsOf;
	}

	public void setViewRequisitionsOf(String viewRequisitionsOf) {
		this.viewRequisitionsOf = viewRequisitionsOf;
	}
	
	
}
