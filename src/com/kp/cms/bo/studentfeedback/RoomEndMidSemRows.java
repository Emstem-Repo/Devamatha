package com.kp.cms.bo.studentfeedback;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.to.admin.EmployeeTO;

// Referenced classes of package com.kp.cms.bo.studentfeedback:
//            EvaStudentFeedbackGroup

public class RoomEndMidSemRows implements Serializable,Comparable<RoomEndMidSemRows>
{

    private int id;
    private RoomMaster roomMasterId;
    private String endMidSem;
    private Integer noOfSetInDesk;
    private String columnNumber;
    private String noOfRows;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;

    public RoomEndMidSemRows()
    {
    }

    public RoomEndMidSemRows(int id,RoomMaster roomMasterId,String endMidSem,String noOfRows,Integer noOfSetInDesk, String createdBy, 
    		Date createdDate, String modifiedBy, String columnNumber, Date lastModifiedDate, Boolean isActive)
    {
        this.id = id;
        this.roomMasterId = roomMasterId;
        this.endMidSem = endMidSem;
        this.columnNumber = columnNumber;
        this.noOfRows = noOfRows;
        this.noOfSetInDesk=noOfSetInDesk;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.isActive = isActive;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RoomMaster getRoomMasterId() {
		return roomMasterId;
	}

	public void setRoomMasterId(RoomMaster roomMasterId) {
		this.roomMasterId = roomMasterId;
	}

	public String getEndMidSem() {
		return endMidSem;
	}

	public void setEndMidSem(String endMidSem) {
		this.endMidSem = endMidSem;
	}

	public String getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(String columnNumber) {
		this.columnNumber = columnNumber;
	}

	public String getNoOfRows() {
		return noOfRows;
	}

	public Integer getNoOfSetInDesk() {
		return noOfSetInDesk;
	}

	public void setNoOfSetInDesk(Integer noOfSetInDesk) {
		this.noOfSetInDesk = noOfSetInDesk;
	}

	public void setNoOfRows(String noOfRows) {
		this.noOfRows = noOfRows;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public int compareTo(RoomEndMidSemRows arg0) {
		if(arg0 instanceof RoomEndMidSemRows && arg0.getColumnNumber()!=null ){
			return this.getColumnNumber().compareTo(arg0.columnNumber);
	}else
		return 0;
	}

}
