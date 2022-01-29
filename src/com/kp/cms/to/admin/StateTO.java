package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * This is a TransferObject Class for State POJO class
 * will use this class to display at UI.
 */
public class StateTO implements Serializable,Comparable<StateTO>{
        
	private int id;
	private CountryTO countryTo;
	private String name;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;	
	private String isActive;
	
	private String cDate;
	private String lDate;
	private String countryName;
	private int countryId;
	private String bankStateId;
	private List<DistrictTO> districtList;
	
	
	public List<DistrictTO> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictTO> districtList) {
		this.districtList = districtList;
	}

	public String getCDate() {
		return cDate;
	}

	public void setCDate(String date) {
		cDate = date;
	}

	public String getLDate() {
		return lDate;
	}

	public void setLDate(String date) {
		lDate = date;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	private Set cities = new HashSet(0);

	public StateTO() {
	}
  
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public CountryTO getCountryTo() {
		return countryTo;
	}

	public void setCountryTo(CountryTO countryTo) {
		this.countryTo = countryTo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getCities() {
		return this.cities;
	}

	public void setCities(Set cities) {
		this.cities = cities;
	}

	@Override
	public int compareTo(StateTO arg0) {
		if(arg0!=null && this!=null && arg0.getName()!=null && this.getName()!=null){
			return this.getName().compareTo(arg0.getName());
		}
		return 0;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getBankStateId() {
		return bankStateId;
	}

	public void setBankStateId(String bankStateId) {
		this.bankStateId = bankStateId;
	}
}
