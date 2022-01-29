package com.kp.cms.bo.studentfeedback;

import java.io.Serializable;
import java.util.Date;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;

// Referenced classes of package com.kp.cms.bo.studentfeedback:
//            EvaStudentFeedbackGroup

public class BlockBo
    implements Serializable
{

    private int id;
    private EmployeeWorkLocationBO locationId;
    private String blockName;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date lastModifiedDate;
    private Boolean isActive;
    private Integer blockOrder;

    public BlockBo()
    {
    }

    public BlockBo(int id,EmployeeWorkLocationBO locationId,String blockName, String createdBy, Date createdDate, String modifiedBy, 
            Date lastModifiedDate, Boolean isActive, Integer blockOrder)
    {
        this.id = id;
        this.locationId = locationId;
        this.blockName = blockName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.isActive = isActive;
        this.blockOrder=blockOrder;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmployeeWorkLocationBO getLocationId() {
		return locationId;
	}

	public void setLocationId(EmployeeWorkLocationBO locationId) {
		this.locationId = locationId;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
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

	public Integer getBlockOrder() {
		return blockOrder;
	}

	public void setBlockOrder(Integer blockOrder) {
		this.blockOrder = blockOrder;
	}

}
