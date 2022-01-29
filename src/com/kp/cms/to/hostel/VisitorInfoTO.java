package com.kp.cms.to.hostel;

import java.io.Serializable;

public class VisitorInfoTO implements Serializable
{

    private String hostelType;
    private String roomType;
    private String applNo;
    private String studentName;
    private int courseId;
    private int year;
    private int studentId;
    private int hostelApplnId;
    private boolean selected;
    private int roomId;
    private String roomNo;
    private String floorNo;
    private int empId;
    private int hostelId;

    public String getHostelType()
    {
        return hostelType;
    }

    public void setHostelType(String hostelType)
    {
        this.hostelType = hostelType;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    public String getApplNo()
    {
        return applNo;
    }

    public void setApplNo(String applNo)
    {
        this.applNo = applNo;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public int getCourseId()
    {
        return courseId;
    }

    public void setCourseId(int courseId)
    {
        this.courseId = courseId;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public int getHostelApplnId()
    {
        return hostelApplnId;
    }

    public void setHostelApplnId(int hostelApplnId)
    {
        this.hostelApplnId = hostelApplnId;
    }


    
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getRoomId()
    {
        return roomId;
    }

    public void setRoomId(int roomId)
    {
        this.roomId = roomId;
    }

    public String getRoomNo()
    {
        return roomNo;
    }

    public void setRoomNo(String roomNo)
    {
        this.roomNo = roomNo;
    }

    public String getFloorNo()
    {
        return floorNo;
    }

    public void setFloorNo(String floorNo)
    {
        this.floorNo = floorNo;
    }

    public int getEmpId()
    {
        return empId;
    }

    public void setEmpId(int empId)
    {
        this.empId = empId;
    }

    public int getHostelId()
    {
        return hostelId;
    }

    public void setHostelId(int hostelId)
    {
        this.hostelId = hostelId;
    }
}
