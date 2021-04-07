package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamExamCourseSchemeDetailsBO;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.exam.ExamDefinitionForm;
import com.kp.cms.handlers.employee.EmpResumeSubmissionHandler;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.helpers.exam.ExamDefinationHelper;
import com.kp.cms.to.exam.ExamCourseSchemeDetailsTO;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamDefinationImpl;

@SuppressWarnings("unchecked")
public class ExamDefinitionHandler {
	private static final Log log = LogFactory.getLog(ExamDefinitionHandler.class);
	private static volatile ExamDefinitionHandler instance=null;
	
	public static ExamDefinitionHandler getInstance(){
		log.info("Start getInstance of ExamDefinitionHandler");
		if(instance==null){
			instance=new ExamDefinitionHandler();
		}
		log.info("End getInstance of ExamDefinitionHandler");
		return instance;
	}

	ExamDefinationImpl impl = new ExamDefinationImpl();
	ExamDefinationHelper helper = new ExamDefinationHelper();
	

	public List<ExamDefinitionTO> getListExamDefinitionBO_main(String academicYear) throws Exception {
		List<ExamDefinitionBO> lBO = new ArrayList(impl
				.select_ExamDefn(ExamDefinitionBO.class,academicYear ));
		return helper.convertBOToTO(lBO);
	}

	public List<ExamDefinitionTO> getListExamDefinitionBO_mainPrgType(String academicYear, ArrayList<ExamProgramTypeUtilBO> listPT) throws Exception {
		List<ExamDefinitionBO> lBO = new ArrayList(impl
				.select_ExamDefnPrgType(ExamDefinitionBO.class,academicYear,listPT));
		return helper.convertBOToTO(lBO);
	}
	
	public void deleteExamDefinition(String examDefId_progId, String userId) throws Exception {
		String[] ids = examDefId_progId.split("_");
		int id = Integer.parseInt(ids[0]);
		int programId = Integer.parseInt(ids[1]);
		impl.delete(id, programId, userId, true, true);
	}

	public List<KeyValueTO> getExamTypeList() throws Exception {
		ArrayList<ExamTypeUtilBO> listBO = new ArrayList(impl.select_ExamType());
		return helper.convertBOToTO_ExamType(listBO);
	}

	public Map<Integer, String> getInternalExamListByAcademicYear(
			int academicYear) {
		return helper.convertListToMap(impl
				.getInternalExamListByAcademicYear(academicYear));
	}

	public List<KeyValueTO> getInternalExamList(ArrayList<Integer> listOfProgram) throws Exception {

		return impl.getInternalExamName(listOfProgram);

	}

	public List<KeyValueTO> getInternalExamTypeList() throws Exception {
		return helper
				.convertBOToTO_InternalExam(impl.select_InternalExamType());

	}

	public List<KeyValueTO> getProgramTypeList() throws Exception {
		ArrayList<ExamProgramTypeUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamProgramTypeUtilBO.class));
		return helper.convertBOToTO_ProgramType(listBO);
	}

	public List<KeyValueTO> getProgramList(String[] pt) throws Exception {
		ArrayList<Integer> pids = new ArrayList<Integer>();

		for (int i = 0; i < pt.length; i++) {
			pids.add(Integer.parseInt(pt[i]));

		}
		ArrayList<ExamProgramUtilBO> listBO = new ArrayList(impl
				.select_ActiveOnly_Program_byPTypes(pids));
		return helper.convertBOToTO_Program(listBO);
	}

	public ExamDefinitionForm getProgramProgramType(String[] prgType,
			String[] prg, ExamDefinitionForm objForm) throws Exception {
		StringBuilder programid =new StringBuilder();

		ArrayList<Integer> listProgramTypes = new ArrayList<Integer>();
		ArrayList<Integer> listPrograms = new ArrayList<Integer>();

		for (int x = 0; x < prgType.length; x++) {
			listProgramTypes.add(Integer.parseInt(prgType[x]));
		}

		for (int y = 0; y < prg.length; y++) {
			listPrograms.add(Integer.parseInt(prg[y]));
			programid.append((prg[y])).append(",");

		}
		programid.setCharAt(programid.length()-1, ' ');
		objForm.setProgramIds(programid.toString().trim());

		ArrayList<ExamProgramTypeUtilBO> listPT = impl
				.getNamesForProgramTypeIds(listProgramTypes);
		ArrayList<ExamProgramUtilBO> listP = impl
				.getNamesForProgramIds(listPrograms);
		objForm.setListProgramTypes(helper
				.convertBOToString_ProgramType(listPT));
		objForm.setListPrograms(helper.convertBOToString_Program(listP));

		objForm.setPrgTypeList(helper.convertBOToList_ProgramType(listPT));
		objForm.setPrgList(helper.convertBOToList_Program(listP));
		return objForm;
	}

	public List<ExamCourseSchemeDetailsTO> getCourseSchemeList(String program) throws Exception {

		return helper.covertBOToTO_CourseScheme(impl
				.select_CourseScheme(program));

	}

	private List<ExamCourseSchemeDetailsTO> getCourseSchemeList_Updatable(
			String programId, String examId) throws Exception {
		return helper.covertBOToTO_CourseScheme_Updatable(impl
				.select_CourseScheme(programId), impl
				.select_ExamCourseSchemeDetails(Integer.parseInt(examId)));
	}

	public void add(ExamDefinitionForm objForm) throws Exception {

		// if (null != objForm.getCheckedActive()

		// || objForm.getCheckedActive().equalsIgnoreCase("on")) {
		String[] strPrg = objForm.getProgramIds().split(",");
		List<Integer> progIds = new ArrayList<Integer>();
		for (String pIds : strPrg) {

			progIds.add((pIds != null && pIds.trim().length() > 0 ? Integer
					.parseInt(pIds.trim()) : 0));
		}
		impl.isExamDuplicated(objForm.getExamCode(),objForm.getExamName(),0);// duplicate check through complete list

		//		
		// impl.isDuplicated(objForm.getJoiningBatchYear(), objForm.getMonth(),
		// Integer.parseInt(objForm.getAcademicYear()), objForm
		// .getExamTypeId(), progIds);
		
		//Code Commented By Balaji
		
		/*ArrayList<Integer> examIdList = impl.isDuplicated(
				objForm.getExamCode(), objForm.getJoiningBatchYear(), objForm
						.getMonth(), Integer
						.parseInt(objForm.getAcademicYear()), objForm
						.getExamTypeId(), Integer.parseInt(objForm.getYear()));

		if (examIdList.size() > 0) {
			for (Integer examId : examIdList) {

				String[] str = objForm.getCourseSchemeSelected().split(",");
				int courseId, schemeNo, courseSchemeId, programId;
				for (int i = 0; i < str.length; i++) {
					String[] str1 = str[i].split("_");
					courseId = Integer.parseInt(str1[0]);
					schemeNo = Integer.parseInt(str1[1]);
					courseSchemeId = Integer.parseInt(str1[2]);
					programId = Integer.parseInt(str1[3]);

					impl.isDuplicated_CourseSchemeCombo(courseId, schemeNo,
							courseSchemeId, programId, examId);
				}
			}

		}*/

		if (null != objForm.getCurrent()
				&& objForm.getCurrent().equalsIgnoreCase("on")) {
			impl.removeIsCurrentForOtherExams();
		}

		// Insert Main Table
		int examId = impl.insert(helper.converFormToBO_ExamDef(objForm));
		// insert into examDefn-program
		impl.insert_List(helper.converFormToBO_ExamDefProgram(examId, strPrg,
				"on".equalsIgnoreCase(objForm.getCheckedActive()) ? true
						: false));
		// Insert Course-scheme_no check marks

		ArrayList<ExamExamCourseSchemeDetailsBO> listE = helper
				.converFormToBO_ChkMarks(objForm.getCourseSchemeSelected(),
						examId, "on".equalsIgnoreCase(objForm
								.getCheckedActive()) ? true : false);
		impl.insert_List(listE);

		// Insert Internal Exam Id link
		if (null != objForm.getInternalExamId())
			impl.insert_List(helper.converFormToBO_IntExam(objForm
					.getInternalExamId(), examId));

	}

	public ExamDefinitionForm getUpdatableForm(ExamDefinitionForm objForm)
			throws Exception {

		String[] ids = objForm.getExamDefId_progId().split("_");

		objForm.setId(Integer.parseInt(ids[0]));

		objForm = helper.createForm(objForm, impl
				.updatableForm(objForm.getId()));
		objForm.setExamTypeList(getExamTypeList());

		ArrayList<Integer> internalExamId = impl.getDetails_Internal(objForm
				.getId());
		String[] internalExamId_str = new String[internalExamId.size()];
		for (int i = 0; i < internalExamId.size(); i++) {
			internalExamId_str[i] = internalExamId.get(i).toString();
		}

		objForm.setInternalExamId(internalExamId_str);
		objForm.setMapInternalExam(getInternalExamListByAcademicYear(Integer
				.parseInt(objForm.getAcademicYear())));
		objForm.setInternalExamTypeList(getInternalExamTypeList());

		objForm.setExamCourseSchemeList(getCourseSchemeList_Updatable(ids[1], ids[0]));
		objForm.setExamCourseSchemeListSize(objForm.getExamCourseSchemeList()
				.size());

		return objForm;
	}

	public void update(ExamDefinitionForm objForm) throws Exception {

		duplicateCheck(objForm);

		String[] strPrg = objForm.getProgramIds().split(",");
		int examId = objForm.getId();
		impl.update(helper.converFormToBO_ExamDef(objForm));
		ArrayList<Integer> list_prgId = new ArrayList<Integer>();
		for (int i = 0; i < strPrg.length; i++) {

			list_prgId.add(Integer.parseInt(strPrg[i]));
		}
		boolean isActiveFlag = false;

		if ("on".equalsIgnoreCase(objForm.getCheckedActive())
				|| "true".equalsIgnoreCase(objForm.getCheckedActive())) {
			isActiveFlag = true;
		}
		// if isactive is unchecked then set isActive as false in examdefprg
		if (!isActiveFlag) {

			impl.delete(examId, Integer.parseInt(strPrg[0]), objForm
					.getUserId(), false, isActiveFlag);

		}
		// if isactive is checked then set isActive as true in examdefprg

		else {

			for (Integer prgId : list_prgId) {
				impl.updateDefPrg(examId, prgId, objForm.getUserId());
			}

		}

		// // insert into examDefn-program

		// delete the old values and insert new Course-scheme_no check
		// marks

		// get list of examexamcourseSchemeid which is in

		ArrayList<ExamExamCourseSchemeDetailsBO> examCSDetailsList = helper.converFormToBO_ChkMarks(objForm.getCourseSchemeSelected(),examId,isActiveFlag);
		ArrayList<ExamExamCourseSchemeDetailsBO> examCSDeleteList = helper.converFormToBO_ChkMarks(objForm.getCourseSchemeUnSelected(),examId,isActiveFlag);
		ArrayList<Integer> csIds = impl.getCSIds(examCSDeleteList);
		
		
		// Code is commented by Balaji
		impl.delete_perm(examId, 2, csIds);

		impl.delete_perm(examId, 1, csIds);
//		impl.insert_List(examCSDetailsList);
		
		//code has written by Balaji
		ExamDefinationImpl definationImpl=new ExamDefinationImpl();
		definationImpl.insertData(examCSDetailsList);
		
		impl.delete_perm(examId, 0, list_prgId);
		// Insert Internal Exam Id link
		if (null != objForm.getInternalExamId())
			impl.insert_List(helper.converFormToBO_IntExam(objForm
					.getInternalExamId(), examId));

	}

	private void duplicateCheck(ExamDefinitionForm objForm) throws Exception {

		String[] strPrg = objForm.getProgramIds().split(",");

		ArrayList<Integer> list_prgId = new ArrayList<Integer>();
		for (int i = 0; i < strPrg.length; i++) {

			list_prgId.add(Integer.parseInt(strPrg[i]));
		}
		if (null != objForm.getCurrent()
				&& objForm.getCurrent().equalsIgnoreCase("on")) {
			impl.removeIsCurrentForOtherExams();
		}
		if (!(objForm.getExamCode().equalsIgnoreCase(objForm.getOrigExamCode())) || !(objForm.getExamName().equalsIgnoreCase(objForm.getOrigExamName()))) {
			impl.isExamDuplicated(objForm.getExamCode(),objForm.getExamName(),objForm.getId()); // duplicate check except that exam
		}
		
		// Code COmmented By Balaji
		/*if (!(// objForm.getExamName().equalsIgnoreCase(objForm.getOrigExamName())&&
		objForm.getExamCode().equalsIgnoreCase(objForm.getOrigExamCode())
				&& objForm.getMonth().equalsIgnoreCase(objForm.getOrigMonth())
				&& objForm.getYear().equalsIgnoreCase(objForm.getOrigYear()) && objForm
				.getExamTypeId() == objForm.getOrigExamTypeId())) {

			String academicYear = (objForm.getAcademicYear() != null
					&& objForm.getAcademicYear().trim().length() > 0 ? objForm
					.getAcademicYear().substring(0, 4) : "");

			ArrayList<Integer> examIdList = impl.isDuplicated(objForm
					.getExamCode(), objForm.getJoiningBatchYear(), objForm
					.getMonth(), Integer.parseInt(academicYear), objForm
					.getExamTypeId(), Integer.parseInt(objForm.getYear()));

			if (examIdList.size() > 0) {
				for (Integer examId : examIdList) {

					String[] str = objForm.getCourseSchemeSelected().split(",");
					int courseId, schemeNo, courseSchemeId, programId;
					for (int i = 0; i < str.length; i++) {
						String[] str1 = str[i].split("_");
						courseId = Integer.parseInt(str1[0]);
						schemeNo = Integer.parseInt(str1[1]);
						courseSchemeId = Integer.parseInt(str1[2]);
						programId = Integer.parseInt(str1[3]);

						impl.isDuplicated_CourseSchemeCombo(courseId, schemeNo,
								courseSchemeId, programId, examId);
					}
				}

			}
		}
*/
	}

	public void reActivate(int id, String userId) throws Exception {

		impl.reActivate(id, userId);
	}

	

}
