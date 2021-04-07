package com.kp.cms.to.studentfeedback;

import java.io.Serializable;



public class BlockBoTo implements Serializable
{

    private int id;
    private String locationId;
    private String locationName;
    private String blockName;
    private int empLocationId;
    private String empLocationName;
    private String blockOrderNO;
    
    
	public String getBlockOrderNO() {
		return blockOrderNO;
	}
	public void setBlockOrderNO(String blockOrderNO) {
		this.blockOrderNO = blockOrderNO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getEmpLocationId() {
		return empLocationId;
	}
	public void setEmpLocationId(int empLocationId) {
		this.empLocationId = empLocationId;
	}
	public String getEmpLocationName() {
		return empLocationName;
	}
	public void setEmpLocationName(String empLocationName) {
		this.empLocationName = empLocationName;
	}
	
}
