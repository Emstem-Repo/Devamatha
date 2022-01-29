package com.kp.cms.bo.admission;

// Generated Jan 12, 2009 6:38:21 PM by Hibernate Tools 3.2.0.b9

import java.util.HashSet;
import java.util.Set;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;

/**
 * Weightage generated by hbm2java
 */
public class Weightage implements java.io.Serializable {

	private int id;
	private String name;
	private Program program;
	private Course course;
	private String desc;
	private Set weightageDefinitions = new HashSet(0);

	public Weightage() {
	}

	public Weightage(int id) {
		this.id = id;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Weightage(int id, String name, Program program, Course course,
			String desc, Set weightageDefinitions) {
		this.id = id;
		this.name = name;
		this.program = program;
		this.course = course;
		this.desc = desc;
		this.weightageDefinitions = weightageDefinitions;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set getWeightageDefinitions() {
		return this.weightageDefinitions;
	}

	public void setWeightageDefinitions(Set weightageDefinitions) {
		this.weightageDefinitions = weightageDefinitions;
	}

}
