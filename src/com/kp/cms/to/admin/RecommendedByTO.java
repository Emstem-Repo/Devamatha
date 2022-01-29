package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author kshirod.k
 * A TO class for RecommendedBy
 *
 */
@SuppressWarnings("serial")
public class RecommendedByTO implements Serializable{
	
	private int id;
	private String code;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String phone;
	private String comments;
	private CountryTO countryTO;
	private StateTO stateTO;
	private String createdBy;
	private String modifiedBy;
	
	private Date createdDate;
	private Date lastModifiedDate;
	private String line1;
	private String line2;
	private String isActive;
	private String cDate;
	private String lDate;
	
	public RecommendedByTO(){
		
	}
	public RecommendedByTO(int id, String code, String name,
			String addressLine1, String addressLine2, String city,
			String phone, String comments, CountryTO countryTO,
			StateTO stateTO, String createdBy, String modifiedBy,
			Date createdDate, Date lastModifiedDate, String line1,
			String line2, String isActive, String cDate, String lDate) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.phone = phone;
		this.comments = comments;
		this.countryTO = countryTO;
		this.stateTO = stateTO;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.line1 = line1;
		this.line2 = line2;
		this.isActive = isActive;
		this.cDate = cDate;
		this.lDate = lDate;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public CountryTO getCountryTO() {
		return countryTO;
	}
	public void setCountryTO(CountryTO countryTO) {
		this.countryTO = countryTO;
	}
	public StateTO getStateTO() {
		return stateTO;
	}
	public void setStateTO(StateTO stateTO) {
		this.stateTO = stateTO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}
	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}