package com.kp.cms.handlers.exam;


/**
 * Jan 7, 2010 Created By 9Elements
 */
//public class ExamExamEligibilitySetUpHandler {
//	ExamEligibilitySetupImpl impl = new ExamEligibilitySetupImpl();

//	public Map<Integer, String> getClases() {
//
//		HashMap<Integer, String> mapCourse = new HashMap<Integer, String>();
//
//		mapCourse.put(1, "BSC 1 Year");
//		mapCourse.put(2, "BSC 2 Year");
//		mapCourse.put(3, "BSC 3 Year");
//		mapCourse.put(4, "BA 1 Year");
//		mapCourse.put(5, "BA 2 Year");
//		return mapCourse;
//
//	}
//}

//	public List<KeyValueTO> getListExamtype() {
//		ArrayList<KeyValueTO> list = new ArrayList<KeyValueTO>();
//		KeyValueTO obj = null;
//		for (int i = 0; i < 6; i++) {
//			if (i == 0) {
//				obj = new KeyValueTO(i + 1, "Regular");
//			}
//			if (i == 1) {
//				obj = new KeyValueTO(i + 1, "Supplementary");
//			}
//			if (i == 2) {
//				obj = new KeyValueTO(i + 1, "Regular &amp; Supplementary");
//			}
//			if (i == 3) {
//				obj = new KeyValueTO(i + 1, "Special Supplementary");
//			}
//			if (i == 4) {
//				obj = new KeyValueTO(i + 1, "Internal");
//			}
//			if (i == 5) {
//				obj = new KeyValueTO(i + 1, "Internal Retest");
//			}
//			list.add(obj);
//		}
//		return list;
//	}

	// public List<ExamExamEligibilityTO> getListcheckEligibility() {
	//
	// ArrayList<ExamExamEligibilityTO> list = new
	// ArrayList<ExamExamEligibilityTO>();
	// ExamExamEligibilityTO obj = null;
	// for (int i = 0; i < 4; i++) {
	// if (i == 0) {
	// obj = new ExamExamEligibilityTO(i + 1, "No Eligibility Check",
	// "off");
	// }
	// if (i == 1) {
	// obj = new ExamExamEligibilityTO(i + 1, "Exam Fees", "off");
	// }
	// if (i == 2) {
	// obj = new ExamExamEligibilityTO(i + 1, "Attendance", "off");
	// }
	// if (i == 3) {
	// obj = new ExamExamEligibilityTO(i + 1, "Course Fees", "off");
	// }
	//
	// list.add(obj);
	// }
	// return list;
	//
	// }

//	public List<ExamExamEligibilityTO> getListAdditionalEligibility() {
//		ArrayList<ExamExamEligibilityTO> list = new ArrayList<ExamExamEligibilityTO>();
//		ExamExamEligibilityTO obj = null;
//		for (int i = 0; i < 8; i++) {
//			if (i == 0) {
//				obj = new ExamExamEligibilityTO(i + 1, "Library Due", 0);
//			}
//			if (i == 1) {
//				obj = new ExamExamEligibilityTO(i + 1, "Lab Due ", 1);
//			}
//			if (i == 2) {
//				obj = new ExamExamEligibilityTO(i + 1, "AttendanceDue", 0);
//			}
//			if (i == 3) {
//				obj = new ExamExamEligibilityTO(i + 1, "Course Fees Due", 1);
//			}
//
//			if (i == 4) {
//				obj = new ExamExamEligibilityTO(i + 1, "aa", 0);
//			}
//			if (i == 5) {
//				obj = new ExamExamEligibilityTO(i + 1, "bbb ", 1);
//			}
//			if (i == 6) {
//				obj = new ExamExamEligibilityTO(i + 1, "cc", 0);
//			}
//			if (i == 7) {
//				obj = new ExamExamEligibilityTO(i + 1, "dd", 1);
//			}
//
//			list.add(obj);
//		}
//		return list;
//	}

//	public List<ExamExamEligibilitySetUpTO> getListExamEligibilitySetUp() {
//		ArrayList<ExamExamEligibilitySetUpTO> list = new ArrayList<ExamExamEligibilitySetUpTO>();
//		ExamExamEligibilitySetUpTO obj = null;
//		for (int i = 0; i < 4; i++) {
//			if (i == 0) {
//				obj = new ExamExamEligibilitySetUpTO(i + 1, "BSc 1 year",
//						i + 5, "Regular", i + 10);
//			}
//			if (i == 1) {
//				obj = new ExamExamEligibilitySetUpTO(i + 1, "BSc 2 year",
//						i + 5, "Regular", i + 10);
//			}
//			if (i == 2) {
//				obj = new ExamExamEligibilitySetUpTO(i + 1, "BSc 3 year",
//						i + 5, "Regular", i + 10);
//			}
//			if (i == 3) {
//				obj = new ExamExamEligibilitySetUpTO(i + 1, "BA I yeare",
//						i + 5, "Regular", i + 10);
//			}
//
//			list.add(obj);
//		}
//		return list;
//	}
//
//	public ExamExamEligibilitySetUpForm getUpdatableForm(
//			ExamExamEligibilitySetUpForm objform, String mode, String classId,
//			String examTypeID) {
//		// objform = helper.createFormObjcet(objform,
//		// impl.loadRoomMaster(form.getId()));
//		objform.setClassId(classId);
//		objform.setExamtypeId(examTypeID);
//		objform.setNoEligibilityCheck("on");
//		objform.setExamFees("on");
//		objform.setAttendance("off");
//		objform.setCourseFees("off");
//		objform.setListAdditionalEligibility(getListAdditionalEligibility());
//		return objform;
//	}
//
//	public void delete(int examEligibilityId, String userId) {
//
//	}
//
//	public void addCheckEligibility(ArrayList<Integer> listClasses,
//			int examTypeId, int isNoEligibilityChecked, int isExamFeesChecked,
//			int isAttendanceChecked, int isCourseFeesChecked, String userId)
//			throws Exception {
//		ExamEligibilitySetupBO objBO = null;
//		for (Integer i : listClasses) {
//			objBO = new ExamEligibilitySetupBO(i.intValue(), examTypeId,
//					isNoEligibilityChecked, isExamFeesChecked,
//					isAttendanceChecked, isCourseFeesChecked, userId);
//
//			impl.isDuplicated(0, i.intValue(), examTypeId, isAttendanceChecked,
//					isCourseFeesChecked, isExamFeesChecked,
//					isNoEligibilityChecked);
//			impl.insert(objBO);
//
//		}
//	}
//
//	public static void main(String[] args) {
//		ExamExamEligibilitySetUpHandler h = new ExamExamEligibilitySetUpHandler();
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		;
//		for (int i = 0; i < 5; i++) {
//
//			list.add(i);
//		}
//
//		try {
//
//			h.addCheckEligibility(list, 1, 1, 1, 1, 1, "4");
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//	}
//
//	public void add(String classId, int examTypeId, int noEligibilityCheck,
//			int examFees, int attendance, int courseFees,
//			List<Integer> listAddElig, String userId) throws Exception {
//
//		ExamEligibilitySetupBO objEligiblity = null;
//		ExamEligibilitySetupAdditionalEligibilityBO objAddElig = null;
//		String[] listClassIds = classId.split(",");
//		for (String classValues : listClassIds) {
//			Integer.parseInt(classValues);
//			objEligiblity = new ExamEligibilitySetupBO(Integer
//					.parseInt(classValues), examTypeId, noEligibilityCheck,
//					examFees, attendance, courseFees, userId);
//			int id = 0;
//			// id=impl.insert(objEligiblity);
//			for (Integer addEligiblity : listAddElig) {
//				objAddElig = new ExamEligibilitySetupAdditionalEligibilityBO(
//						id, addEligiblity, userId);
//				// impl.insert(objAddElig);
//			}
//		}
//	}
//
//	public void upadate(String classId, int examTypeId, int noEligibilityCheck,
//			int examFees, int attendance, int courseFees,
//			List<Integer> listAddElig, String userId)throws Exception {
//
//	}
//
//}
