package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamDefinitionProgramBO;
import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.ExamInternalExamDetailsBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.exam.ExamDefinitionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.exam.ExamCourseSchemeDetailsTO;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.to.exam.ExamSchemeTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamDefinationImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamDefinationHelper {
	/**
	 * Dec 23, 2009 Created By 9Elements Team
	 */

	public List<ExamDefinitionTO> convertBOToTO(List<ExamDefinitionBO> listBO) throws Exception {
		ArrayList<ExamDefinitionTO> listTO = new ArrayList<ExamDefinitionTO>();
		ExamDefinitionTO eTO;
		int month = 0;
		for (ExamDefinitionBO eBO : listBO) {

			// retrieve examdefinitionPogramSet

			Set<ExamDefinitionProgramBO> programDefinitionSet = eBO
					.getExamDefinitionProgramBOSet();
			Iterator<ExamDefinitionProgramBO> itr = programDefinitionSet
					.iterator();
			while (itr.hasNext()) {
				eTO = new ExamDefinitionTO();
				ExamDefinitionProgramBO eDPBO = (ExamDefinitionProgramBO) itr
						.next();
				if (eDPBO.getDelIsActive() == true) {
					eTO.setProgram(eDPBO.getExamProgramUtilBO()
							.getProgramName());
					eTO.setProgramType(eDPBO.getExamProgramUtilBO()
							.getProgramType().getProgramType());

					eTO.setExamDefId_progId(eBO.getId() + "_"
							+ eDPBO.getProgramId());

					eTO.setId(eBO.getId());
					eTO
							.setAcademicYear(Integer.toString(eBO
									.getAcademicYear()));
					eTO.setExamName(eBO.getName());
					eTO.setExamCode(eBO.getExamCode());
					eTO.setExamType(eBO.getExamTypeUtilBO().getName());
					eTO.setIsCurrent(eBO.getIsCurrent() == true ? "YES" : "NO");
					month = Integer.parseInt(eBO.getMonth()) + 1;
					eTO.setMonth(CommonUtil.getMonthForNumber(month));
					eTO.setYear(Integer.toString(eBO.getYear()));

					eTO.setInternalExamNameList(new ExamDefinationImpl()
							.getDetails_InternalName(eBO.getId()));

					listTO.add(eTO);
				}
			}

		}
		Collections.sort(listTO);
		return listTO;
	}

	public List<KeyValueTO> convertBOToTO_ExamType(
			ArrayList<ExamTypeUtilBO> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamTypeUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public List<KeyValueTO> convertBOToTO_InternalExam(List listBO) throws Exception {

		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();

		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			int id = (row[0] == null ? null : Integer.parseInt(row[0]
					.toString()));
			listTO.add(new KeyValueTO(id, row[1].toString()));

		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public List<KeyValueTO> convertBOToTO_ProgramType(
			ArrayList<ExamProgramTypeUtilBO> listBO) throws Exception {

		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamProgramTypeUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getProgramType()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public List<KeyValueTO> convertBOToTO_Program(
			ArrayList<ExamProgramUtilBO> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamProgramUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getProgramID(), bo.getProgramName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public String convertBOToString_ProgramType(
			ArrayList<ExamProgramTypeUtilBO> listPT) throws Exception {
		StringBuilder prgType = new StringBuilder();
		for (ExamProgramTypeUtilBO bo : listPT) {
			prgType.append(bo.getProgramType()).append(",");
		}
		prgType.setCharAt(prgType.length()-1, ' ');
		
		return prgType.toString().trim();
	}

	public String convertBOToString_Program(ArrayList<ExamProgramUtilBO> listP) throws Exception {
		StringBuilder prg = new StringBuilder();
		for (ExamProgramUtilBO bo : listP) {
			prg.append(bo.getProgramName()).append(",");
		}
		prg.setCharAt(prg.length()-1, ' ');
		
		return prg.toString().trim();
	}

	public ArrayList<String> convertBOToList_ProgramType(
			ArrayList<ExamProgramTypeUtilBO> listPT) throws Exception {
		ArrayList<String> prgType = new ArrayList<String>();
		for (ExamProgramTypeUtilBO bo : listPT) {
			prgType.add(bo.getProgramType());
		}
		Collections.sort(prgType);
		return prgType;
	}

	public ArrayList<String> convertBOToList_Program(
			ArrayList<ExamProgramUtilBO> listP) throws Exception {
		ArrayList<String> prg = new ArrayList<String>();
		for (ExamProgramUtilBO bo : listP) {
			prg.add(bo.getProgramName());
		}
		Collections.sort(prg);
		return prg;
	}

	public ExamDefinitionBO converFormToBO_ExamDef(ExamDefinitionForm objForm) throws Exception {

		boolean flag = false;
		if ("on".equalsIgnoreCase(objForm.getCheckedActive())
				|| "true".equalsIgnoreCase(objForm.getCheckedActive())) {
			flag = true;
		}

		return new ExamDefinitionBO(objForm.getId(), objForm.getExamName(),
				objForm.getExamCode(), Integer.parseInt(objForm
						.getAcademicYear()), objForm.getExamTypeId(), objForm
						.getMonth(), Integer.parseInt(objForm.getYear()),
				objForm.getJoiningBatchYear(), objForm.getMaxFailedSubjects(),
				"on".equalsIgnoreCase(objForm.getCurrent()) ? true : false,
				objForm.getInternalExamTypeId(), objForm.getUserId(), flag, (objForm.getRegularExamIdForRedo() != null && !objForm.getRegularExamIdForRedo().isEmpty()) ? Integer.parseInt(objForm.getRegularExamIdForRedo()) : null);

	}

	public ArrayList<ExamDefinitionProgramBO> converFormToBO_ExamDefProgram(
			int examDefId, String[] programIds, boolean isActive) throws Exception {
		// int examDefnId, int programId
		ArrayList<ExamDefinitionProgramBO> list = new ArrayList<ExamDefinitionProgramBO>();
		for (int i = 0; i < programIds.length; i++) {

			int programId = Integer.parseInt(programIds[i].trim());
			list
					.add(new ExamDefinitionProgramBO(examDefId, programId,
							isActive));
		}
		//Collections.sort(list,new KeyValueTOComparator());
		return list;

	}

	public ArrayList<ExamExamCourseSchemeDetailsBO> converFormToBO_ChkMarks(
			String courseSchemeSelected, int examId, boolean isActive) throws Exception {
		ArrayList<ExamExamCourseSchemeDetailsBO> courseSchemeList = new ArrayList<ExamExamCourseSchemeDetailsBO>();
		String[] str = courseSchemeSelected.split(",");
		
			for (int i = 0; i < str.length; i++) {
				if(!str[i].isEmpty()){
				String[] str1 = str[i].split("_");
				courseSchemeList.add(new ExamExamCourseSchemeDetailsBO(examId,
						Integer.parseInt(str1[0]), Integer.parseInt(str1[1]),
						Integer.parseInt(str1[2]), Integer.parseInt(str1[3]),
						isActive));

				}
			}
		//Collections.sort(list,new ExamExamCourseSchemeDetailsBOComparator());
		return courseSchemeList;
	}

	// public ExamDefListTO convert_CourseScheme(String courseSchemeSelected) {
	//
	// ExamDefListTO listTo = new ExamDefListTO();
	// ArrayList<Integer> courseIdList = new ArrayList<Integer>();
	// ArrayList<Integer> schemeNoList = new ArrayList<Integer>();
	// ArrayList<Integer> programIdList = new ArrayList<Integer>();
	// ArrayList<Integer> courseSchemeIdList = new ArrayList<Integer>();
	// String[] str = courseSchemeSelected.split(",");
	// for (int i = 0; i < str.length; i++) {
	// String[] str1 = str[i].split("_");
	// if (!courseIdList.contains(Integer.parseInt(str1[0])))
	// courseIdList.add(Integer.parseInt(str1[0]));
	// if (!schemeNoList.contains(Integer.parseInt(str1[1])))
	// schemeNoList.add(Integer.parseInt(str1[1]));
	// if (!courseSchemeIdList.contains(Integer.parseInt(str1[2])))
	// courseSchemeIdList.add(Integer.parseInt(str1[2]));
	// if (!programIdList.contains(Integer.parseInt(str1[3])))
	// programIdList.add(Integer.parseInt(str1[3]));
	//
	// }
	// listTo.setCourseIdList(courseIdList);
	// listTo.setCourseSchemeIdList(courseSchemeIdList);
	// listTo.setProgramIdList(programIdList);
	// listTo.setSchemeIdList(schemeNoList);
	// return listTo;
	// }

	public ArrayList<ExamInternalExamDetailsBO> converFormToBO_IntExam(
			String[] internalExamId, int examId) throws Exception {
		ArrayList<ExamInternalExamDetailsBO> intExamList = new ArrayList<ExamInternalExamDetailsBO>();
		for (int i = 0; i < internalExamId.length; i++) {
			if (!internalExamId[i].isEmpty()) {
				intExamList.add(new ExamInternalExamDetailsBO(examId, Integer
						.parseInt(internalExamId[i].trim())));
			}
		}
		return intExamList;
	}

	public List<ExamCourseSchemeDetailsTO> covertBOToTO_CourseScheme(
			ArrayList<CurriculumSchemeUtilBO> listEBO) throws Exception {
		ArrayList<ExamCourseSchemeDetailsTO> eTOlist = new ArrayList<ExamCourseSchemeDetailsTO>();
		ArrayList<ExamSchemeTO> exTOList;

		for (CurriculumSchemeUtilBO cBO : listEBO) {
			ExamCourseSchemeDetailsTO eTO = new ExamCourseSchemeDetailsTO();
			String scheme = cBO.getCourseSchemeUtilBO().getName();
			int no = cBO.getNoScheme();
			eTO.setCourseId(cBO.getExamCourseUtilBO().getCourseID());
			eTO.setCourse(cBO.getExamCourseUtilBO().getCourseName());
			eTO.setSchemeCount(Integer.toString(no));
			eTO.setSchemeId(cBO.getCourseSchemeId());
			eTO.setSchemeName(scheme);
			eTO.setProgramId(cBO.getCourseSchemeUtilBO().getExamProgramUtilBO()
					.getProgramID());
			no++;
			exTOList = new ArrayList<ExamSchemeTO>();
			for (int i = 1; i < no; i++) {
				exTOList.add(new ExamSchemeTO(
						scheme + (i < 9 ? " 0" : " ") + i, i, "off"));
			}
			eTO.setListDisplay(exTOList);
			eTOlist.add(eTO);
		}
		Collections.sort(eTOlist);
		return eTOlist;
	}

	public ExamDefinitionForm createForm(ExamDefinitionForm objForm,
			ExamDefinitionBO objBO) throws Exception {

		String[] ids = objForm.getExamDefId_progId().split("_");
		ArrayList<String> prgType = new ArrayList<String>();
		ArrayList<String> prg = new ArrayList<String>();
		int programId = Integer.parseInt(ids[1]);

		objForm.setId(objBO.getId());
		Set<ExamDefinitionProgramBO> programDefinitionSet = objBO
				.getExamDefinitionProgramBOSet();
		Iterator<ExamDefinitionProgramBO> itr = programDefinitionSet.iterator();
		while (itr.hasNext()) {
			ExamDefinitionProgramBO eDPBO = (ExamDefinitionProgramBO) itr
					.next();
			if (programId == eDPBO.getProgramId()) {
				objForm.setListProgramTypes(eDPBO.getExamProgramUtilBO()
						.getProgramType().getProgramType());

				prgType.add(eDPBO.getExamProgramUtilBO().getProgramType()
						.getProgramType());
				prg.add(eDPBO.getExamProgramUtilBO().getProgramName());
				objForm.setListPrograms(eDPBO.getExamProgramUtilBO()
						.getProgramName());
				objForm.setPrgTypeList(prgType);
				objForm.setPrgList(prg);
				objForm.setProgramIds(Integer.toString(eDPBO.getProgramId()));
				objForm.setCheckedActiveDummy(eDPBO.getIsActive());
			}
		}
		objForm.setCheckedActive(null);

		objForm.setAcademicYear(Integer.toString(objBO.getAcademicYear()));
		objForm.setExamName(objBO.getName());
		objForm.setExamCode(objBO.getExamCode());
		objForm.setExamTypeId(objBO.getExamTypeUtilBO().getId());
		objForm.setMonth(objBO.getMonth());
		objForm.setYear(Integer.toString(objBO.getYear()));
		objForm.setJoiningBatchYear(objBO.getExamForJoiningBatch());
		objForm.setMaxFailedSubjects(objBO.getMaxNoFailedSub());
		objForm.setCurrentDummy(objBO.getIsCurrent());

		if (objBO.getInternalExamTypeId() == null) {
			objForm.setInternalExamTypeId(-1);
		} else {
			objForm.setInternalExamTypeId(objBO.getInternalExamTypeId());
		}

		objForm.setOrigExamCode(objBO.getExamCode());
		objForm.setOrigExamName(objBO.getName());
		objForm.setOrigExamTypeId(objBO.getExamTypeUtilBO().getId());
		objForm.setOrigMonth(objBO.getMonth());
		objForm.setOrigYear(Integer.toString(objBO.getYear()));
		
		if(objForm.getExamTypeId() == 5) {	//	only redo
			Map<Integer, String> examMap = new HashMap<Integer, String>();
			examMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear("1",Integer.parseInt(objForm.getAcademicYear()));
			objForm.setRegularExams(examMap);
			if(objBO.getRegularExamId() != null) {
				objForm.setRegularExamIdForRedo(String.valueOf(objBO.getRegularExamId()));
			}
		}		

		return objForm;
	}

	public List<ExamCourseSchemeDetailsTO> covertBOToTO_CourseScheme_Updatable(
			ArrayList<CurriculumSchemeUtilBO> listCSUBO,
			ArrayList<ExamExamCourseSchemeDetailsBO> listEECSD) throws Exception {
		ArrayList<ExamCourseSchemeDetailsTO> eTOlist = new ArrayList<ExamCourseSchemeDetailsTO>();
		ArrayList<ExamSchemeTO> exTOList;
		boolean tickFound = false;
		int courseID;
		for (CurriculumSchemeUtilBO cBO : listCSUBO) {
			ExamCourseSchemeDetailsTO eTO = new ExamCourseSchemeDetailsTO();
			String scheme = cBO.getCourseSchemeUtilBO().getName();
			int no = cBO.getNoScheme();
			courseID = cBO.getExamCourseUtilBO().getCourseID();
			eTO.setCourseId(courseID);
			eTO.setCourse(cBO.getExamCourseUtilBO().getCourseName());
			eTO.setSchemeCount(Integer.toString(no));
			eTO.setSchemeId(cBO.getCourseSchemeId());
			eTO.setSchemeName(scheme);
			eTO.setProgramId(cBO.getCourseSchemeUtilBO().getExamProgramUtilBO()
					.getProgramID());
			no++;
			exTOList = new ArrayList<ExamSchemeTO>();
			for (int i = 1; i < no; i++) {
				tickFound = false;
				for (int j = 0; !tickFound && j < listEECSD.size(); j++) {
					ExamExamCourseSchemeDetailsBO eCBO = listEECSD.get(j);
					if (courseID == eCBO.getCourseId()
							&& i == eCBO.getSchemeNo()
							&& cBO.getCourseSchemeId()==eCBO.getCourseSchemeId()
							&&eCBO.getIsActive()) {
						tickFound = true;
					}
				}
				if (tickFound) {

					exTOList.add(new ExamSchemeTO(scheme + (i < 9 ? " 0" : " ")
							+ i, i, "on"));
				} else {

					exTOList.add(new ExamSchemeTO(scheme + (i < 9 ? " 0" : " ")
							+ i, i, "off"));
				}

			}
			eTO.setListDisplay(exTOList);
			eTOlist.add(eTO);
		}
		Collections.sort(eTOlist);
		return eTOlist;
	}

	public Map<Integer, String> convertListToMap(
			List<KeyValueTO> internalExamListByAcademicYear) {

		Map<Integer, String> map = new HashMap<Integer, String>();

		for (KeyValueTO TO : internalExamListByAcademicYear) {
			map.put(TO.getId(), TO.getDisplay());

		}
		map=(HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

}
