package com.kp.cms.to.usermanagement;

import java.io.Serializable;
import java.util.List;

public class ModuleTO implements Serializable{
	private String name;
	private Boolean isActive;
	private String toolTip;
	private Integer position;
	private int id;
	private boolean choosed;
	private int menuCount;
	private boolean chooseTemp;
	private String createdBy;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String tempIsActive;
	
	
	public boolean isChooseTemp() {
		return chooseTemp;
	}
	public void setChooseTemp(boolean chooseTemp) {
		this.chooseTemp = chooseTemp;
	}
	private List<MenusTO> menusTO;
	
	public List<MenusTO> getMenusTO() {
		return menusTO;
	}
	public void setMenusTO(List<MenusTO> menusTO) {
		this.menusTO = menusTO;
	}
	public String getName() {
		return name;
	}
	public int getMenuCount() {
		return menuCount;
	}
	public void setMenuCount(int menuCount) {
		this.menuCount = menuCount;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isChoosed() {
		return choosed;
	}
	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getTempIsActive() {
		return tempIsActive;
	}
	public void setTempIsActive(String tempIsActive) {
		this.tempIsActive = tempIsActive;
	}
	

}
