package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamAssignExaminerDutiesBO;
import com.kp.cms.bo.exam.ExamAssignStudentsRoomBO;
import com.kp.cms.bo.exam.ExamAssignStudentsRoomStudentListBO;
import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.forms.exam.ExamAssignStudentsToRoomForm;
import com.kp.cms.helpers.exam.ExamAssignStudentsToRoomHelper;
import com.kp.cms.to.exam.ExamAssignStuInvTO;
import com.kp.cms.to.exam.ExamAssignStudentsToRoomTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamAssignStudentsToRoomImpl;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamAssignStudentsToRoomHandler extends ExamGenHandler {

	ExamAssignStudentsToRoomHelper helper = new ExamAssignStudentsToRoomHelper();
	ExamAssignStudentsToRoomImpl impl = new ExamAssignStudentsToRoomImpl();

	public List<KeyValueTO> getRoomNames() throws Exception {
		List<ExamRoomMasterBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamRoomMasterBO.class));
		return helper.convertBOToTO_Room_List(listBO);
	}

	public Map<Integer, String> getExamTypeList() throws Exception {
		return getExamType();
	}

	public ArrayList<ExamAssignStudentsToRoomTO> getDetails(int examTypeId,
			int examId, String date, String hr, String min, int roomNo) throws Exception {

		String timeStr = hr + ":" + min;
		String examName = getExamNameList(examId);
		ArrayList<ExamAssignStudentsToRoomTO> list = helper
				.convertBOToTO_Room_ListValues(impl.selectRoomDetails(roomNo,
						examId, CommonUtil.ConvertStringToSQLDateTime(date
								+ " " + hr + ":" + min + ":00")), examTypeId,
						examId, examName, date, timeStr);
		return list;
	}

	public ExamAssignStudentsToRoomForm add(ExamAssignStudentsToRoomForm objform)
			throws Exception {
		int examTypeId = Integer.parseInt(objform.getExamType());
		int examId = Integer.parseInt(objform.getExamNameId());
		String date = objform.getDate();
		String hr = objform.getHr();
		String min = objform.getMin();
		int roomId = Integer.parseInt(objform.getRoomId());
		String roomNo = getRoomNoByRoomId(roomId);
		objform.setExamType(Integer.toString(examTypeId));
		objform.setExamNameId(Integer.toString(examId));
		objform.setDate(date);
		objform.setHr(hr);
		objform.setMin(min);
		objform.setRoomId(Integer.toString(roomId));
		objform.setNonEligible(objform.getNonEligible());
		objform.setDisplayOrder(objform.getDisplayOrder());

		objform.setListClassNameMap(getClassCodeByExamName(examId));
		// objform.setListClassNameMap(getClassesByTimetable(examId,
		// CommonUtil.ConvertStringToSQLDateTime(date + " " + hr + ":"
		// + min + ":00")));

		List<ExamAssignStudentsToRoomTO> list = helper
				.convertBOToTO_Examinars_List(impl.selectExaminars(examId,
						roomNo, CommonUtil.ConvertStringToSQLDateTime(date
								+ " " + hr + ":" + min + ":00")));
		objform.setListExaminarSize(list.size());

		objform.setListExaminars(list);

		List<ExamAssignStudentsToRoomTO> list1 = helper
				.convertBOToTO_Students_List(impl.getStudentList(examId,
						CommonUtil.ConvertStringToSQLDateTime(date + " " + hr
								+ ":" + min + ":00"),
						objform.getDisplayOrder(), roomId),examId);
		objform.setListStudentsSize(list1.size());

		objform.setListStudents(list1);

		String examName = getExamNameList(examId);
		objform.setExamName(examName);

		objform.setTime(hr + ":" + min);
		objform.setRoomNo(roomNo);

		List listvalues = impl.getRommDetails(roomId);

		Iterator itr = listvalues.iterator();
		int roomCapacity = 0;
		int alloted = impl.getRoomAlloted(examId, roomId,
				CommonUtil.ConvertStringToSQLDateTime(date + " " + hr + ":"
						+ min + ":00"));
		int internalExamCapacity = 0;
		String comments = "";
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] != null) {
				roomCapacity = Integer.parseInt(row[0].toString());
			}

			if (row[2] != null) {
				internalExamCapacity = Integer.parseInt(row[2].toString());
			}

			if (row[3] != null) {
				comments = row[3].toString();
			}
		}
		int roomCap = 0;
		if (examTypeId == 5 || examTypeId == 6) {
			roomCap = internalExamCapacity;

		} else {
			roomCap = roomCapacity;
		}

		int available = roomCap - alloted;

		objform.setRoomCapacity(roomCap);
		objform.setAlloted(alloted);
		objform.setAvailable(available);
		objform.setComments(comments);

		return objform;

	}

	// public Map<Integer, String> getClassCodeByTimeTable(Integer examId,
	// Timestamp convertStringToSQLDateTime) {
	// ExamGenHelper h = new ExamGenHelper();
	// ArrayList<ExamClassTO> list = h.convertTOTO(impl
	// .getCourseIdSchemeNo(examId));
	// List<KeyValueTO> listValues = new ArrayList();
	// for (ExamClassTO examClassTO : list) {
	// impl.getClassCodeByTimeTable(examClassTO.getCourseId(),
	// examClassTO.getCourseSchemeId(), examClassTO.getSchemeNo(),
	// listValues, examId);
	//
	// }
	// return h.convertTOClassMap(listValues);
	//
	// }

	public String getDate(String date, String hr, String min) throws Exception {
		String dateValue = CommonUtil.ConvertStringToDateFormat(date + " " + hr
				+ ":" + min + ":00", "dd/MM/yyyy hh:mm:ss",
				"yyyy-MM-dd k:mm:ss");
		if (Integer.parseInt(hr) > 12) {
			dateValue = dateValue + ".1";
		} else {
			dateValue = dateValue + ".0";
		}
		return dateValue;

	}

	public boolean isDateTimeValid(Date formatDate, int examId) throws Exception {
		return impl.isDateTimeValid(formatDate, examId);
	}

	public Map<Integer, String> getSubjectNameByClassIds(String classId,
			String date, String time, String examName) {

		Date newdate = CommonUtil.ConvertStringToSQLDateTime(date + " " + time
				+ ":00");

		ArrayList<Integer> list = new ArrayList<Integer>();
		String[] clas = classId.split(",");
		for (String string : clas) {
			list.add(Integer.parseInt(string));
		}
		return helper.convertBOToTO(impl.getSubjects(
				Integer.parseInt(examName), list, newdate));

	}

	public void deleteExaminars(int id) throws Exception {
		impl.delete_AssignExaminer(id);
		impl.deleteExaminars(id);
	}

	public void deleteStudents(int id, int alloted, int roomId) throws Exception {
		impl.deleteStudents(id);
	}

	public List<ExamAssignStuInvTO> getInvigilatorList(int examid, Date date,
			String roomNo) throws Exception {
		return helper.convertBOToTOInvList(impl.getInvigilatorList(examid,
				date, roomNo), examid);
	}

	public List<ExamAssignStudentsToRoomTO> getStudentsList(int examId,
			Date date, String displayOrder, int roomId) throws Exception {
		return helper.convertBOToTO_Students_List(impl.getStudentList(examId,
				date, displayOrder, roomId),examId);
	}

	public List<ExamAssignStudentsToRoomTO> getStudentList_forSubjects(
			ArrayList<Integer> listSubjects, int noOfStudents,
			String nonElegibleStudent, ArrayList<Integer> classIds,
			String displayOrder, Integer examId, String date, String hr,
			String min) throws Exception {

		return helper.convertBOToTO_Students_forSubjects(impl
				.getStudentList_forSubjects(listSubjects, nonElegibleStudent,
						classIds, displayOrder, examId, CommonUtil
								.ConvertStringToSQLDateTime(date + " " + hr
										+ ":" + min + ":00")), noOfStudents,classIds);
	}

	public int getRoomAvalableDetails(int roomId, int examTypeId) throws Exception {
		List listvalues = impl.getRommDetails(roomId);
		Iterator itr = listvalues.iterator();
		int roomCapacity = 0;
		int alloted = 0;
		int internalExamCapacity = 0;
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] != null) {
				roomCapacity = Integer.parseInt(row[0].toString());
			}
			if (row[1] != null) {
				alloted = Integer.parseInt(row[1].toString());
			}
			if (row[2] != null) {
				internalExamCapacity = Integer.parseInt(row[2].toString());
			}
		}
		int roomCap = 0;
		if (examTypeId == 5 || examTypeId == 6) {
			roomCap = internalExamCapacity;
		} else {
			roomCap = roomCapacity;
		}
		int available = roomCap - alloted;
		return available;
	}

	public void updateMarkAttendance(List<ExamAssignStuInvTO> listInvigilator,
			List<ExamAssignStudentsToRoomTO> listStudents) throws Exception {
		ArrayList<ExamAssignStudentsRoomStudentListBO> listStudent = new ArrayList<ExamAssignStudentsRoomStudentListBO>();
		ArrayList<ExamAssignExaminerDutiesBO> listInvgilator = new ArrayList<ExamAssignExaminerDutiesBO>();
		for (ExamAssignStuInvTO objInvTo : listInvigilator) {
			int isPresent = 0;
			if (objInvTo.getIsChecked() != null
					&& objInvTo.getIsChecked().contains("on")) {
				isPresent = 1;
			}
			listInvgilator.add(new ExamAssignExaminerDutiesBO(objInvTo.getId(),
					isPresent));
		}
		for (ExamAssignStudentsToRoomTO objStudentTo : listStudents) {
			int id = 0;
			if (objStudentTo.getIsChecked() != null
					&& objStudentTo.getIsChecked().contains("on")) {
				id = 1;
			}
			listStudent.add(new ExamAssignStudentsRoomStudentListBO(
					Integer.valueOf(objStudentTo.getId()), id));
		}
		impl.updateInvigilator(listInvgilator);
		impl.updateStudent(listStudent);
	}

	public boolean addStudentsToRoom(
			List<ExamAssignStudentsToRoomTO> listStudents, int examId,
			int roomId, String date, Date dateFormat, String userId,
			int allorted) throws Exception {
		int countStudent = 0;
		boolean flag = false;
		for (ExamAssignStudentsToRoomTO objTo : listStudents) {
			if (objTo.getIsChecked() != null
					&& objTo.getIsChecked().contains("on")) {
				flag = true;
				int id = 0;

				id = impl.isDuplicated(examId, objTo.getSubjectId(), objTo
						.getClassId(), roomId, date);
				if (id == 0) {
					ExamAssignStudentsRoomBO objbo = new ExamAssignStudentsRoomBO();
					objbo.setExamId(examId);
					objbo.setSubjectId(objTo.getSubjectId());
					objbo.setClassId(objTo.getClassId());
					objbo.setRoomId(roomId);
					objbo.setDateTime(dateFormat);
					objbo.setIsActive(true);
					objbo.setCreatedBy(userId);
					objbo.setCreatedDate(new Date());
					objbo.setModifiedBy(userId);
					objbo.setLastModifiedDate(new Date());

					id = impl.insert(objbo);

					ExamAssignStudentsRoomStudentListBO abo = new ExamAssignStudentsRoomStudentListBO();
					abo.setAssignStuRoomId(id);
					abo.setStudentId(objTo.getId());
					abo.setIsPresent(0);
					abo.setIsActive(true);
					abo.setCreatedBy(userId);
					abo.setCreatedDate(new Date());
					abo.setModifiedBy(userId);
					abo.setLastModifiedDate(new Date());
					impl.insert(abo);
					countStudent = countStudent + 1;

				} else {
					ExamAssignStudentsRoomStudentListBO abo = new ExamAssignStudentsRoomStudentListBO();
					abo.setAssignStuRoomId(id);
					abo.setStudentId(objTo.getId());
					abo.setIsPresent(0);
					abo.setIsActive(true);
					abo.setCreatedBy(userId);
					abo.setCreatedDate(new Date());
					abo.setModifiedBy(userId);
					abo.setLastModifiedDate(new Date());
					impl.insert(abo);
					countStudent = countStudent + 1;

				}
			}

		}
		//allorted = allorted + countStudent;
		return flag;
	}

	public void deleteRooms(int roomId) throws Exception {
		ArrayList<Integer> list = helper.convertToList(impl.getRoomIds(roomId));
		String roomNo = getRoomNoByRoomId(roomId);
		impl.deleteStudents(list);
		impl.deleteRooms(roomId);
		impl.deleteAssinedExaminars(roomNo);
	}

	public String getDateTimeByExamId(int examId){
		String date = "";
		date = impl.getDateTimeByExamId(examId);

		date = CommonUtil.formatSqlDateTimeToString(date);

		return date;
	}

	public String getRoomNoByRoomId(int roomno) throws Exception {
		return impl.getRoomNoByRoomId(roomno);
	}

	public Map<Integer, String> getExamNameyExamTypeId(int examTypeId) throws Exception {
		return helper.convertBoToToExam(impl.getExamNameById(examTypeId));
	}

	public String getCurrentExam(int examtypeId) {
		return impl.getCurrentExam(examtypeId);
	}

	public int getRoomAlloted(int examId, int roomId, Date date) throws Exception {
		return impl.getRoomAlloted(examId, roomId, date);
	}

	/**
	 * code Written By Balaji
	 */
	public List<ExamAssignStudentsToRoomTO> getStudentListForExam(
			ArrayList<Integer> listSubjects, int noOfStudents, String nonEligible,
			ArrayList<Integer> listClass, String displayOrder, int examId,
			String date, String hr, String min,String type) throws Exception{
		return helper.convertBOToTO_Students_forSubjects(impl
				.getStudentListForExamSupplementary(listSubjects, nonEligible,
						listClass, displayOrder, examId, CommonUtil
								.ConvertStringToSQLDateTime(date + " " + hr
										+ ":" + min + ":00"),type), noOfStudents,listClass);
	}

	public HashMap<Integer, String> getRegularExamTypeList() throws Exception{
		return getRegularExamType();
		}
}
