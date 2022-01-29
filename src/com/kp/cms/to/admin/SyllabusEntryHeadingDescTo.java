package com.kp.cms.to.admin;

public class SyllabusEntryHeadingDescTo implements Comparable<SyllabusEntryHeadingDescTo>{
	private int id;
	private String heading;
	private String description;
	private String headingTemplate;
	private String descriptionTemplate;
	private String tempHead;
	private Integer headingNO;
	
	
	
	public Integer getHeadingNO() {
		return headingNO;
	}
	public void setHeadingNO(Integer headingNO) {
		this.headingNO = headingNO;
	}
	public String getTempHead() {
		return tempHead;
	}
	public void setTempHead(String tempHead) {
		this.tempHead = tempHead;
	}
	public String getHeadingTemplate() {
		return headingTemplate;
	}
	public void setHeadingTemplate(String headingTemplate) {
		this.headingTemplate = headingTemplate;
	}
	public String getDescriptionTemplate() {
		return descriptionTemplate;
	}
	public void setDescriptionTemplate(String descriptionTemplate) {
		this.descriptionTemplate = descriptionTemplate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int compareTo(SyllabusEntryHeadingDescTo arg0) {
		/*if(arg0 instanceof SyllabusEntryHeadingDescTo && arg0.getHeadingNO()!=null ){
			return this.getHeadingNO().compareTo(arg0.getHeadingNO());
	}else
		return 0;
	}*/
		if(arg0!=null && this!=null ){
			if (arg0.getHeadingNO() != null && this.getHeadingNO()!=null){
				if(this.getHeadingNO() >  arg0.getHeadingNO())
					return 1;
				else if(this.getHeadingNO() <  arg0.getHeadingNO()){
					return -1;
				}else
					return 0;
		    }
		}
		return 0;
	
	}
}
