package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EventLoactionBiometricDetailsTo;

public class EventLocationBiometricDetailsForm extends BaseActionForm{
	private int id;
	private String machineId;
	private String machineIp;
	private String machineName;
	private String origMachineId;
	private String origMachineIp;
	private String origMachineName;
	private String eventLocation;
	private String origEventLocation;
	private List<EventLoactionBiometricDetailsTo> biometricList;
	private Map<String,String> eventLocationMap;
	private String duperrorMsg;
	private boolean flag;
	private String worklocation;
	private String orgWorklocation;
	public Map<String, String> getEventLocationMap() {
		return eventLocationMap;
	}
	public void setEventLocationMap(Map<String, String> eventLocationMap) {
		this.eventLocationMap = eventLocationMap;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getOrigMachineId() {
		return origMachineId;
	}
	public void setOrigMachineId(String origMachineId) {
		this.origMachineId = origMachineId;
	}
	public String getOrigMachineIp() {
		return origMachineIp;
	}
	public void setOrigMachineIp(String origMachineIp) {
		this.origMachineIp = origMachineIp;
	}
	public String getOrigMachineName() {
		return origMachineName;
	}
	public void setOrigMachineName(String origMachineName) {
		this.origMachineName = origMachineName;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getOrigEventLocation() {
		return origEventLocation;
	}
	public void setOrigEventLocation(String origEventLocation) {
		this.origEventLocation = origEventLocation;
	}
	public List<EventLoactionBiometricDetailsTo> getBiometricList() {
		return biometricList;
	}
	public void setBiometricList(List<EventLoactionBiometricDetailsTo> biometricList) {
		this.biometricList = biometricList;
	}
	public void reset(){
		this.machineId=null;
		this.machineIp=null;
		this.machineName=null;
		this.id=0;
		this.eventLocation=null;
		this.flag=false;
		this.worklocation=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getDuperrorMsg() {
		return duperrorMsg;
	}
	public void setDuperrorMsg(String duperrorMsg) {
		this.duperrorMsg = duperrorMsg;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getWorklocation() {
		return worklocation;
	}
	public void setWorklocation(String worklocation) {
		this.worklocation = worklocation;
	}
	public String getOrgWorklocation() {
		return orgWorklocation;
	}
	public void setOrgWorklocation(String orgWorklocation) {
		this.orgWorklocation = orgWorklocation;
	}
	
}
