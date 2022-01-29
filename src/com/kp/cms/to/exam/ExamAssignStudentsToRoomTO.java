package com.kp.cms.to.exam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamAssignStudentsToRoomTO implements Serializable,Comparable<ExamAssignStudentsToRoomTO> {

	private int id;

	private int roomId;
	private String roomNo;
	private int examId;
	private String examName;
	private int roomCapacity;
	private int alloted;
	private String date;
	private String time;

	private String firstName;
	private String lastName;

	private String className;
	private int classId;
	private String subject;
	private int subjectId;
	private String registerNo;
	private String rollNo;
	private String name;
	private boolean isCheckedDummy;
	private String isChecked;

	public ExamAssignStudentsToRoomTO(int id, int classId, String className,
			int subjectId, String subject, String registerNo, String rollNo,
			String name, boolean isCheckedDummy, String isChecked) {
		super();
		this.id = id;
		this.className = className;
		this.subject = subject;
		this.registerNo = registerNo;
		this.rollNo = rollNo;
		this.name = name;
		this.isCheckedDummy = isCheckedDummy;
		this.classId = classId;
		this.subjectId = subjectId;

	}

	public ExamAssignStudentsToRoomTO(int id, int classId, String className,
			String registerNo, String rollNo, String name,
			boolean isCheckedDummy, String isChecked) {
		super();
		this.id = id;
		this.className = className;
		this.registerNo = registerNo;
		this.rollNo = rollNo;
		this.name = name;
		this.isCheckedDummy = isCheckedDummy;
		this.classId = classId;

	}

	public ExamAssignStudentsToRoomTO(int id, int classId, String className,
			int subjectId, String subject, String registerNo, String rollNo,
			String name) {
		super();
		this.id = id;
		this.classId = classId;
		this.className = className;
		this.subjectId = subjectId;
		this.subject = subject;
		this.registerNo = registerNo;
		this.rollNo = rollNo;
		this.name = name;
		this.isCheckedDummy = false;

	}

	public ExamAssignStudentsToRoomTO(int id, String className, String subject,
			String registerNo, String rollNo, String name,
			boolean isCheckedDummy) {
		super();
		this.id = id;
		this.className = className;
		this.subject = subject;
		this.registerNo = registerNo;
		this.rollNo = rollNo;
		this.name = name;
		this.isCheckedDummy = isCheckedDummy;
	}

	public ExamAssignStudentsToRoomTO(int id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public ExamAssignStudentsToRoomTO(int roomId, String roomNo, int examId,
			String examName, int roomCapacity, int alloted, String date,
			String time) {
		super();
		this.roomId = roomId;
		this.roomNo = roomNo;
		this.examId = examId;
		this.examName = examName;
		this.roomCapacity = roomCapacity;
		this.alloted = alloted;
		this.date = date;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public int getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public int getAlloted() {
		return alloted;
	}

	public void setAlloted(int alloted) {
		this.alloted = alloted;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsCheckedDummy(boolean isCheckedDummy) {
		this.isCheckedDummy = isCheckedDummy;
	}

	public boolean getIsCheckedDummy() {
		return isCheckedDummy;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getClassId() {
		return classId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	@Override
	public int compareTo(ExamAssignStudentsToRoomTO arg0) {
		if(arg0!=null && this!=null && arg0.getExamName()!=null
				 && this.getExamName()!=null){
			return this.getExamName().compareTo(arg0.getExamName());
		}else
		return 0;
	}

}
