package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniversityTO implements Serializable{
	private int id;
	private String name;
	private Integer addressId;
	private byte[] logo;
	private Set colleges = new HashSet(0);
	private List<CollegeTO> collegeTos=new ArrayList<CollegeTO>();
	private int docTypeId;
	private String docType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public Set getColleges() {
		return colleges;
	}
	public void setColleges(Set colleges) {
		this.colleges = colleges;
	}
	public List<CollegeTO> getCollegeTos() {
		return collegeTos;
	}
	public void setCollegeTos(List<CollegeTO> collegeTos) {
		this.collegeTos = collegeTos;
	}
	public int getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}

}
