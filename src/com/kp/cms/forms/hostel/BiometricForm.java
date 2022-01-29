package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.to.hostel.HostelTO;

public class BiometricForm extends BaseActionForm{
	private List<HostelTO> hostelList = new ArrayList<HostelTO>();
	private Map<Integer, String> unitMap;
	private Map<Integer, String> blockMap;
	private String block;
	private String unit;
	private String machineId;
	private String machineIp;
	private String machineName;
	private int id;
	private String worklocation;
	private boolean flag;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private List<BiometricDetailsTO> biometricList;
	
	public List<BiometricDetailsTO> getBiometricList() {
		return biometricList;
	}
	public void setBiometricList(List<BiometricDetailsTO> biometricList) {
		this.biometricList = biometricList;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public String getMachineIp() {
		return machineIp;
	}
	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public Map<Integer, String> getUnitMap() {
		return unitMap;
	}
	public void setUnitMap(Map<Integer, String> unitMap) {
		this.unitMap = unitMap;
	}
	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}
	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public void reset(){
		this.unit=null;
		this.block=null;
		this.machineId=null;
		this.machineIp=null;
		this.machineName=null;
		this.flag=false;
		this.worklocation=null;
	}
	public String getWorklocation() {
		return worklocation;
	}
	public void setWorklocation(String worklocation) {
		this.worklocation = worklocation;
	}
}
