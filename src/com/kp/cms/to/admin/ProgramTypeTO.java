package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.List;

/**
 * Manages Transaction related to Program Type.
 * @author prashanth.mh
 */
public class ProgramTypeTO implements Serializable{
	private int programTypeId;
	private String programTypeName;
	private List<ProgramTO> programs;
	private String ageFrom;
	private String ageTo;
	private Boolean isOpen;
	
	public int getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(int programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getProgramTypeName() {
		return programTypeName;
	}

	public void setProgramTypeName(String programTypeName) {
		this.programTypeName = programTypeName;
	}

	public List<ProgramTO> getPrograms() {
		return programs;
	}

	public void setPrograms(List<ProgramTO> programs) {
		this.programs = programs;
	}

	public String getAgeFrom() {
		return ageFrom;
	}

	public String getAgeTo() {
		return ageTo;
	}

	public void setAgeFrom(String ageFrom) {
		this.ageFrom = ageFrom;
	}

	public void setAgeTo(String ageTo) {
		this.ageTo = ageTo;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	
}
