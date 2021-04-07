package com.kp.cms.bo.studentfeedback;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// Referenced classes of package com.kp.cms.bo.studentfeedback:
//            EvaStudentFeedbackGroup

public class RoomMaster
    implements Serializable
{

    private int id;
    private BlockBo blockId;
    private String floor;
    private String floorName;
    private String roomNo;
    private String totalCapacity;
    private String endSemCapacity;
    private String endSemTotalColumn;
    private String endSemSeatInDesk;
    private String midSemCapacity;
    private String midSemTotalColumn;
    private String midSemSeatInDesk;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;
    private Set<RoomEndMidSemRows> endMidSemRows =new HashSet<RoomEndMidSemRows>();

    public RoomMaster()
    {
    }

    public RoomMaster(int id,BlockBo blockId,String floor,String roomNo,String totalCapacity,String endSemCapacity,String endSemTotalColumn,String endSemSeatInDesk,
   String midSemCapacity,String midSemTotalColumn,String midSemSeatInDesk, String createdBy, Date createdDate, String modifiedBy,Set<RoomEndMidSemRows> endMidSemRows, 
            Date lastModifiedDate, Boolean isActive,String floorName)
    {
        this.id = id;
        this.blockId = blockId;
        this.floor = floor;
        this.roomNo = roomNo;
        this.totalCapacity = totalCapacity;
        this.endSemCapacity = endSemCapacity;
        this.endSemTotalColumn = endSemTotalColumn;
        this.endSemSeatInDesk = endSemSeatInDesk;
        this.midSemCapacity = midSemCapacity;
        this.midSemTotalColumn = midSemTotalColumn;
        this.midSemSeatInDesk = midSemSeatInDesk;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.isActive = isActive;
        this.endMidSemRows=endMidSemRows;
        this.floorName=floorName;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BlockBo getBlockId() {
		return blockId;
	}

	public void setBlockId(BlockBo blockId) {
		this.blockId = blockId;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(String totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public String getEndSemCapacity() {
		return endSemCapacity;
	}

	public void setEndSemCapacity(String endSemCapacity) {
		this.endSemCapacity = endSemCapacity;
	}

	public String getEndSemTotalColumn() {
		return endSemTotalColumn;
	}

	public void setEndSemTotalColumn(String endSemTotalColumn) {
		this.endSemTotalColumn = endSemTotalColumn;
	}

	public String getEndSemSeatInDesk() {
		return endSemSeatInDesk;
	}

	public void setEndSemSeatInDesk(String endSemSeatInDesk) {
		this.endSemSeatInDesk = endSemSeatInDesk;
	}

	public String getMidSemCapacity() {
		return midSemCapacity;
	}

	public void setMidSemCapacity(String midSemCapacity) {
		this.midSemCapacity = midSemCapacity;
	}

	public String getMidSemTotalColumn() {
		return midSemTotalColumn;
	}

	public void setMidSemTotalColumn(String midSemTotalColumn) {
		this.midSemTotalColumn = midSemTotalColumn;
	}

	public String getMidSemSeatInDesk() {
		return midSemSeatInDesk;
	}

	public void setMidSemSeatInDesk(String midSemSeatInDesk) {
		this.midSemSeatInDesk = midSemSeatInDesk;
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

	public Set<RoomEndMidSemRows> getEndMidSemRows() {
		return endMidSemRows;
	}

	public void setEndMidSemRows(Set<RoomEndMidSemRows> endMidSemRows) {
		this.endMidSemRows = endMidSemRows;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

}
