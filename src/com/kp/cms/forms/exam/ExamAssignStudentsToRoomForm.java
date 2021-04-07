package com.kp.cms.forms.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamAssignStuInvTO;
import com.kp.cms.to.exam.ExamAssignStudentsToRoomTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamAssignStudentsToRoomForm extends BaseActionForm {

	private int id;
	private String examNameId;
	private String date;;
	private int listExaminarSize;
	private int listStudentsSize;
	private String hr = "00";
	private String min = "00";
	private String roomNo;
	private String nonEligible = "off";
	private String displayOrder;
	private String time;
	private int roomCapacity;
	private int alloted;
	private int available;
	private String comments;
	private String noOfStudents;
	private String studentId;
	private String type;
	private String semester;

	private HashMap<Integer, String> listExamName;

	public String getNoOfStudents() {
		return noOfStudents;
	}

	public void setNoOfStudents(String noOfStudents) {
		this.noOfStudents = noOfStudents;
	}

	private HashMap<Integer, String> examTypeList;
	private List<KeyValueTO> listRoom;
	private List<ExamAssignStudentsToRoomTO> listRoomDetails;
	private List<ExamAssignStudentsToRoomTO> listExaminars;
	private List<ExamAssignStudentsToRoomTO> listStudents;
	private Map<Integer, String> listClassNameMap;
	private List<ExamAssignStuInvTO> listInvigilator;

	public void clearPage() {
		this.examNameId = "";
		this.date = null;
		this.nonEligible = "off";
		this.hr = "00";
		this.min = "00";

	}

	public void resetPage(ActionMapping mapping, HttpServletRequest request) {
		clear1();
		this.date = null;
		this.displayOrder = "";
		this.examNameId = "";
		this.nonEligible = "off";
		this.noOfStudents = "";

		this.hr = "00";
		this.min = "00";

	}

	public ExamAssignStudentsToRoomForm() {
		super();
		this.alloted = 0;
		this.available = 0;
		super.clear1();
		this.comments = null;
		this.date = null;
		this.displayOrder = null;
		this.examNameId = null;
		this.hr = "00";
		this.min = "00";
		this.noOfStudents = null;
		this.roomNo = null;
		this.studentId = null;
		this.time = null;
	}
	
	public void resetFields(){
		
		this.setExamType("");
		this.setDate("");
		this.setRoomId("");
		this.setDisplayOrder("regNo");
		this.setExamNameId("");
		this.setNonEligible("off");
		this.setNoOfStudents("");
		this.setHr("00");
		this.setMin("00");
		this.type=null;
		this.semester=null;
		
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNonEligible() {
		return nonEligible;
	}

	public void setNonEligible(String nonEligible) {
		this.nonEligible = nonEligible;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public void setExamTypeList(HashMap<Integer, String> examTypeList) {
		this.examTypeList = examTypeList;
	}

	public HashMap<Integer, String> getExamTypeList() {
		return examTypeList;
	}

	public void setListRoom(List<KeyValueTO> listRoom) {
		this.listRoom = listRoom;
	}

	public List<KeyValueTO> getListRoom() {
		return listRoom;
	}

	public String getExamNameId() {
		return examNameId;
	}

	public void setExamNameId(String examNameId) {
		this.examNameId = examNameId;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public void setListRoomDetails(
			List<ExamAssignStudentsToRoomTO> listRoomDetails) {
		this.listRoomDetails = listRoomDetails;
	}

	public List<ExamAssignStudentsToRoomTO> getListRoomDetails() {
		return listRoomDetails;
	}

	public void setListClassNameMap(Map<Integer, String> listClassNameMap) {
		this.listClassNameMap = listClassNameMap;
	}

	public Map<Integer, String> getListClassNameMap() {
		return listClassNameMap;
	}

	public void setListExaminars(List<ExamAssignStudentsToRoomTO> listExaminars) {
		this.listExaminars = listExaminars;
	}

	public List<ExamAssignStudentsToRoomTO> getListExaminars() {
		return listExaminars;
	}

	public void setListStudents(List<ExamAssignStudentsToRoomTO> listStudents) {
		this.listStudents = listStudents;
	}

	public List<ExamAssignStudentsToRoomTO> getListStudents() {
		return listStudents;
	}

	public void setListExaminarSize(int listExaminarSize) {
		this.listExaminarSize = listExaminarSize;
	}

	public int getListExaminarSize() {
		return listExaminarSize;
	}

	public void setListStudentsSize(int listStudentsSize) {
		this.listStudentsSize = listStudentsSize;
	}

	public int getListStudentsSize() {
		return listStudentsSize;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setListInvigilator(List<ExamAssignStuInvTO> listInvigilator) {
		this.listInvigilator = listInvigilator;
	}

	public List<ExamAssignStuInvTO> getListInvigilator() {
		return listInvigilator;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setListExamName(HashMap<Integer, String> listExamName) {
		this.listExamName = listExamName;
	}

	public HashMap<Integer, String> getListExamName() {
		return listExamName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

}
