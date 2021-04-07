package com.kp.cms.to.admission;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UGCoursesTO;

public class PgAdmSubjectForRankTo {

    private int id;
	
    private boolean isactive;
    private ProgramTypeTO programTypeTO;
    private ProgramTO programTO;
    private CourseTO courseTO;
    private UGCoursesTO uGCoursesTO;
	
	public boolean isIsactive() {
		return isactive;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public ProgramTO getProgramTO() {
		return programTO;
	}
	public void setProgramTO(ProgramTO programTO) {
		this.programTO = programTO;
	}
	public CourseTO getCourseTO() {
		return courseTO;
	}
	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}
	public UGCoursesTO getuGCoursesTO() {
		return uGCoursesTO;
	}
	public void setuGCoursesTO(UGCoursesTO uGCoursesTO) {
		this.uGCoursesTO = uGCoursesTO;
	}
	public ProgramTypeTO getProgramTypeTO() {
		return programTypeTO;
	}
	public void setProgramTypeTO(ProgramTypeTO programTypeTO) {
		this.programTypeTO = programTypeTO;
	}
}
