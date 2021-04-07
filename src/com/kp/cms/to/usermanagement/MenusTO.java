package com.kp.cms.to.usermanagement;

import java.io.Serializable;

public class MenusTO implements Serializable {

	private int id;
	private ModuleTO moduleTO;
	private String name;
	private int position;
	private String url;
	private boolean newtab;
	private Boolean isActive;
	private boolean choosed;
	private boolean chooseTemp;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String lastModifiedDate;
	private String tempIsActive;
	
	
	public boolean isChooseTemp() {
		return chooseTemp;
	}
	public void setChooseTemp(boolean chooseTemp) {
		this.chooseTemp = chooseTemp;
	}
	
	public boolean isChoosed() {
		return choosed;
	}
	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ModuleTO getModuleTO() {
		return moduleTO;
	}
	public void setModuleTO(ModuleTO moduleTO) {
		this.moduleTO = moduleTO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	public boolean isNewtab() {
		return newtab;
	}
	public void setNewtab(boolean newtab) {
		this.newtab = newtab;
	}
	
}
