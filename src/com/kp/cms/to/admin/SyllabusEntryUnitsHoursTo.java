package com.kp.cms.to.admin;

import java.util.List;

import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;

public class SyllabusEntryUnitsHoursTo implements Comparable<SyllabusEntryUnitsHoursTo>{
	private int id;
	private String units;
	private String teachingHours;
	private String teachingHoursTemplate;
	private List<SyllabusEntryHeadingDescTo> syllabusEntryHeadingDescTos;
	private Integer position;
	private boolean headingsFlag;
	private Integer unitNo;
	private String checked;
	
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Integer getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}
	public boolean isHeadingsFlag() {
		return headingsFlag;
	}
	public void setHeadingsFlag(boolean headingsFlag) {
		this.headingsFlag = headingsFlag;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public List<SyllabusEntryHeadingDescTo> getSyllabusEntryHeadingDescTos() {
		return syllabusEntryHeadingDescTos;
	}
	public void setSyllabusEntryHeadingDescTos(
			List<SyllabusEntryHeadingDescTo> syllabusEntryHeadingDescTos) {
		this.syllabusEntryHeadingDescTos = syllabusEntryHeadingDescTos;
	}
	public String getTeachingHoursTemplate() {
		return teachingHoursTemplate;
	}
	public void setTeachingHoursTemplate(String teachingHoursTemplate) {
		this.teachingHoursTemplate = teachingHoursTemplate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getTeachingHours() {
		return teachingHours;
	}
	public void setTeachingHours(String teachingHours) {
		this.teachingHours = teachingHours;
	}
	@Override
	public int compareTo(SyllabusEntryUnitsHoursTo arg0) {
		/*if(arg0 instanceof SyllabusEntryUnitsHoursTo && arg0.getUnitNo()!=null ){
			return this.getUnitNo().compareTo(arg0.getUnitNo());
	}else
		return 0;
	}*/
		if(arg0!=null && this!=null ){
			if (arg0.getUnitNo() != null && this.getUnitNo()!=null){
				if(this.getUnitNo() >  arg0.getUnitNo())
					return 1;
				else if(this.getUnitNo() <  arg0.getUnitNo()){
					return -1;
				}else
					return 0;
			}
		}
		return 0;
	}
}
