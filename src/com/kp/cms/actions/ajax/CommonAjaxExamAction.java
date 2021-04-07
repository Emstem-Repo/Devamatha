package com.kp.cms.actions.ajax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.AdminHallTicketHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamSecuredMarksVerificationHandler;
import com.kp.cms.handlers.exam.ExamStudentMarksCorrectionHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.ValuationScheduleHandler;
import com.kp.cms.to.exam.ExamSecuredMarksEntryTO;
import com.kp.cms.to.exam.ExamSecuredMarksVerificationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.print.SamplePrinting;

public class CommonAjaxExamAction extends DispatchAction {
	/**
	 * Jan 26, 2010 Created By 9Elements
	 */
	public ActionForward getSchemeValuesBySchemeId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int fromSchemeId = 0, toSchemeId = 0;
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();

		try {
			fromSchemeId = Integer.parseInt(baseActionForm.getFromScheme());
			toSchemeId = Integer.parseInt(baseActionForm.getToScheme());
			if (fromSchemeId < toSchemeId)
				for (int i = 1; i < toSchemeId; i++)
					schemeMap.put(Integer.valueOf(i), Integer.toString(i));

		} catch (Exception e) {
			log.debug(e.getMessage());

		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSchemeNoByCourseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int cid;
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null
				&& baseActionForm.getCourseId().length() != 0) {
			try {
				cid = Integer.parseInt(baseActionForm.getCourseId());
				// The below map contains key as id and value as name of state.
				schemeMap = CommonAjaxHandler.getInstance()
						.getSchemeNoByCourseId(cid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getExamNameByExamTypeId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String currentExamId = "0";
		String dateTimeValue = "0";
		int examTypeId = 0;
		Map<Integer, String> examNameMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamTypeId() != 0) {
			try {
				examTypeId = baseActionForm.getExamTypeId();

				examNameMap = CommonAjaxHandler.getInstance()
						.getExamNameByExamTypeId(examTypeId);
				currentExamId = CommonAjaxHandler.getInstance().getCurrentExam(
						examTypeId);
				if (currentExamId != null && currentExamId.length() > 0) {
					dateTimeValue = CommonAjaxHandler.getInstance()
							.getDateTimeByExamId(
									Integer.parseInt(currentExamId));

				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		request.setAttribute("currentExam", currentExamId);
		request.setAttribute("dateTimeValue", dateTimeValue);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examNameMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examNameMap);
		return mapping.findForward("ajaxResponseForASToRoom");
	}

	public ActionForward getExamNamesByExamTypeId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String currentExamId = "0";
		int examTypeId = 0;
		Map<Integer, String> examNameMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamTypeId() != 0) {
			try {
				examTypeId = baseActionForm.getExamTypeId();

				examNameMap = CommonAjaxHandler.getInstance()
						.getExamNameByExamTypeId(examTypeId);
				currentExamId = CommonAjaxHandler.getInstance().getCurrentExam(
						examTypeId);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("subjectType", currentExamId);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examNameMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examNameMap);
		return mapping.findForward("ajaxResponseForSubjectType");
	}

	public ActionForward getCoursesByProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		Map<Integer, String> coursesMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				pid = Integer.parseInt(baseActionForm.getProgramTypeId());
				// The below map contains key as id and value as name of state.
				coursesMap = CommonAjaxHandler.getInstance()
						.getCourseByProgramType(pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), coursesMap);
		request.setAttribute(CMSConstants.OPTION_MAP, coursesMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	/*
	 * This function is uses in Exam module like Assignment/Overall marks,
	 */
	public ActionForward getCoursesByAcademicYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int year;
		Map<Integer, String> coursesMap = new HashMap<Integer, String>();
		if (baseActionForm.getAcademicYear() != null
				&& baseActionForm.getAcademicYear().length() != 0) {
			try {
				year = Integer.parseInt(baseActionForm.getAcademicYear());
				// The below map contains key as id and value as name of state.
				coursesMap = CommonAjaxHandler.getInstance()
						.getCoursesByAcademicYear(year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), coursesMap);
		request.setAttribute(CMSConstants.OPTION_MAP, coursesMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSchemeByCourseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int cid;
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null
				&& baseActionForm.getCourseId().length() != 0) {
			try {
				cid = Integer.parseInt(baseActionForm.getCourseId());
				// The below map contains key as id and value as name of state.
				/*
				 * schemeMap = CommonAjaxHandler.getInstance()
				 * .getSchemeByCourseId(cid);
				 */
				schemeMap = CommonAjaxHandler.getInstance()
						.getSchemeNoByCourseId(cid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getProgramsByPType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		int pid;
		Map<Integer, String> programMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				pid = Integer.parseInt(baseActionForm.getProgramTypeId());
				programMap = CommonAjaxHandler.getInstance()
						.getProgramsByPType(pid);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getProgramsByPTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> programMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				programMap = CommonAjaxHandler.getInstance()
						.getProgramsByPTypes(baseActionForm.getProgramTypeId());
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getClasesByExamName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String examName = "";
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName() != null
				&& baseActionForm.getExamName().length() != 0) {
			try {
				examName = baseActionForm.getExamName();
				classMap = CommonAjaxHandler.getInstance().getClasesByExamName(
						examName);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getProgramesByDepartmentId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String deptId = "";
		Map<Integer, String> programeMap = new HashMap<Integer, String>();
		if (baseActionForm.getProgramId() != null
				&& baseActionForm.getProgramId().length() != 0) {
			try {
				deptId = baseActionForm.getProgramId();
				programeMap = CommonAjaxHandler.getInstance().getProgramesByDepartmentId(
						deptId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, programeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	
	public ActionForward getClasesByJoingBatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String joiningBatch = "";
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getJoiningBatch() != null
				&& baseActionForm.getJoiningBatch().length() != 0) {
			try {
				joiningBatch = baseActionForm.getJoiningBatch();
				classMap = CommonAjaxHandler.getInstance()
						.getClasesByJoingBatch(joiningBatch);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSchemeNameByCourseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;

		String courseId = "";
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null
				&& baseActionForm.getCourseId().length() != 0) {
			try {
				courseId = baseActionForm.getCourseId();

				schemeMap = CommonAjaxHandler.getInstance()
						.getSchemesByCourseId(courseId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getCourseByExamName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		String examName = "";
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName() != null
				&& baseActionForm.getExamName().length() != 0) {
			try {
				examName = baseActionForm.getExamName();

				schemeMap = CommonAjaxHandler.getInstance()
						.getCourseByExamName(examName);
				schemeMap=CommonUtil.sortMapByValue(schemeMap);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSectionByCourseIdSchemeId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;

		String courseId = "";
		String schemeId = "";
		String academicYear = "";
		String schemeNo = "";

		Map<Integer, String> sectionMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null

		&& baseActionForm.getCourseId().length() != 0
				&& baseActionForm.getSchemeId() != null

				&& baseActionForm.getSchemeId().length() != 0) {
			try {

				courseId = baseActionForm.getCourseId();
				academicYear = baseActionForm.getAcademicYear();
				schemeId = baseActionForm.getSchemeId().split("_")[0];
				schemeNo = baseActionForm.getSchemeId().split("_")[1];
				sectionMap = CommonAjaxHandler.getInstance()
						.getSectionByCourseIdSchemeId(courseId, schemeId,
								schemeNo, academicYear);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), sectionMap);
		request.setAttribute(CMSConstants.OPTION_MAP, sectionMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getProgramNameByAcademicYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ExamUniversityRegisterNumberEntryHandler handler = new
		// ExamUniversityRegisterNumberEntryHandler();

		BaseActionForm baseActionForm = (BaseActionForm) form;

		String academicYear = baseActionForm.getAcademicYear();

		Map<Integer, String> programMap = new HashMap<Integer, String>();
		if (baseActionForm.getAcademicYear().length() != 0
				&& baseActionForm.getAcademicYear() != null) {
			try {

				programMap = CommonAjaxHandler.getInstance()
						.getProgramByAcademicYear(
								Integer.parseInt(academicYear));

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), programMap);
		request.setAttribute(CMSConstants.OPTION_MAP, programMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSchemeNameByCourseIdAcademicYear(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;

		int academicYear = Integer.parseInt(baseActionForm.getAcademicYear());
		int courseId = Integer.parseInt(baseActionForm.getCourseId());

		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		if (baseActionForm.getAcademicYear().length() != 0
				&& baseActionForm.getAcademicYear() != null) {
			try {
				schemeMap = CommonAjaxHandler.getInstance()
						.getSchemeNoByCourseIdAcademicyear(courseId,
								academicYear);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSchemeNo_SchemeIDByCourseIdAcademicId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<String, String> schemeMap = new HashMap<String, String>();
		if (baseActionForm.getAcademicYear().length() != 0
				&& baseActionForm.getAcademicYear() != null) {
			try {
				int academicYear = Integer.parseInt(baseActionForm
						.getAcademicYear());
				int courseId = Integer.parseInt(baseActionForm.getCourseId());
				schemeMap = CommonAjaxHandler.getInstance()
						.getSchemeNo_SchemeIDByCourseIdAcademicId(courseId,
								academicYear);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsByCourse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		int courseId = Integer.parseInt(baseActionForm.getCourseId());
		int schemeId = Integer.parseInt(baseActionForm.getSchemeId());
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		try {

			schemeMap = CommonAjaxHandler.getInstance().getSubjectsByCourse(
					courseId, schemeId, 0);

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getExamNameByExamType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String examType = baseActionForm.getExamType();
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		String currentExam = null;
		try {
			examMap = CommonAjaxHandler.getInstance().getExamNameByExamType(
					examType);
			currentExam = examhandler.getCurrentExamName(examType);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (currentExam != null) {
			baseActionForm.setExamTypeId(Integer.parseInt(currentExam));
			request.setAttribute("subjectType", currentExam);
		} else {
			baseActionForm.setExamTypeId(0);
			request.setAttribute("subjectType", 0);
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForSubjectType");
	}

	public ActionForward getCourseByExamNameRegNoRollNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName().length() != 0
				&& baseActionForm.getExamName() != null) {
			int examId = Integer.parseInt(baseActionForm.getExamName());
			String regNo = baseActionForm.getRegNo();
			String rollNo = baseActionForm.getRollNo();
			try {
				courseMap = CommonAjaxHandler.getInstance()
						.getCourseByExamNameRegNoRollNo(examId, regNo, rollNo);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getClassByExamNameRegNoRollNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName().length() != 0
				&& baseActionForm.getExamName() != null) {
			int examId = Integer.parseInt(baseActionForm.getExamName());
			String regNo = baseActionForm.getRegNo();
			String rollNo = baseActionForm.getRollNo();
			try {
				courseMap = CommonAjaxHandler.getInstance()
						.getClassByExamNameRegNoRollNo(examId, regNo, rollNo);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	// public ActionForward getSubjectsByCourseScheme(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// BaseActionForm baseActionForm = (BaseActionForm) form;
	// Map<Integer, String> courseMap = new HashMap<Integer, String>();
	// int courseId = Integer.parseInt(baseActionForm.getCourseId());
	// int schemeId = Integer.parseInt(baseActionForm.getSchemeId());
	// try {
	//
	// courseMap = CommonAjaxHandler.getInstance().getSubjectsByCourse(
	// courseId, schemeId);
	// } catch (Exception e) {
	// log.debug(e.getMessage());
	// }
	//
	// if (baseActionForm.getPropertyName() != null)
	// baseActionForm.getCollectionMap().put(
	// baseActionForm.getPropertyName(), courseMap);
	// request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
	// return mapping.findForward("ajaxResponseForOptions");
	// }

	public ActionForward getSubjectsByCourseScheme(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		int courseId = Integer.parseInt(baseActionForm.getCourseId());
		if (baseActionForm.getSchemeId() != null
				&& baseActionForm.getSchemeId().trim().length() > 0) {
			String schemeSplit[] = baseActionForm.getSchemeId().split("_");
			int schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			try {

				courseMap = CommonAjaxHandler.getInstance()
						.getSubjectsByCourse(courseId, schemeId, schemeNo);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsByCourseSchemeExamId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		int courseId = Integer.parseInt(baseActionForm.getCourseId());
		if (baseActionForm.getSchemeId() != null
				&& baseActionForm.getSchemeId().trim().length() > 0) {
			String schemeSplit[] = baseActionForm.getSchemeId().split("_");
			int schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			try {
				Integer examId = null;
				if (baseActionForm.getExamName() != null
						&& baseActionForm.getExamName().trim().length() > 0) {
					examId = Integer.parseInt(baseActionForm.getExamName());
				}

				courseMap = CommonAjaxHandler.getInstance()
						.getSubjectsByCourseSchemeExamId(courseId, schemeId,
								schemeNo, examId);
				courseMap=CommonUtil.sortMapByValue(courseMap);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsByCourseSchemeExamIdJBY(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		int courseId = Integer.parseInt(baseActionForm.getCourseId());
		if (baseActionForm.getSchemeId() != null
				&& baseActionForm.getSchemeId().trim().length() > 0) {
			String schemeSplit[] = baseActionForm.getSchemeId().split("_");
			int schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			try {
				Integer examId = null;
				Integer jby = null;
				if (baseActionForm.getExamName() != null
						&& baseActionForm.getExamName().trim().length() > 0) {
					examId = Integer.parseInt(baseActionForm.getExamName());
				}
				if (baseActionForm.getJoiningBatch() != null
						&& baseActionForm.getJoiningBatch().trim().length() > 0) {
					jby = Integer.parseInt(baseActionForm.getJoiningBatch());
				}

				courseMap = CommonAjaxHandler.getInstance()
						.getSubjectsByCourseSchemeExamIdJBY(courseId, schemeId,
								schemeNo, examId, jby);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getClasesByAcademicYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		int academicYear = Integer.parseInt(baseActionForm.getAcademicYear());

		try {

			classMap = CommonAjaxHandler.getInstance().getClasesByAcademicYear(
					academicYear);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getClassCodeByExamName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		int examName = Integer.parseInt(baseActionForm.getExamName());
		if (baseActionForm.getExamName() != null
				&& baseActionForm.getExamName().length() != 0) {
			try {
				classMap = CommonAjaxHandler.getInstance()
						.getClassCodeByExamName(examName);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getCoursesByProgramTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		String programTypeId = baseActionForm.getProgramTypeId();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				classMap = CommonAjaxHandler.getInstance()
						.getCoursesByProgramTypes(programTypeId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getInternalComponentsByClasses(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		int value = 0;
		int examId = Integer.parseInt(baseActionForm.getExamName());
		if (baseActionForm.getExamName() != null
				&& baseActionForm.getExamName().length() != 0) {
			try {
				value = CommonAjaxHandler.getInstance()
						.getInternalComponentsByClasses(examId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	public ActionForward getSchemeNoByExamIdCourseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<String, String> schemeMap = new HashMap<String, String>();
		if (baseActionForm.getExamName() != null
				&& baseActionForm.getExamName().length() != 0
				&& baseActionForm.getCourseId() != null
				&& baseActionForm.getCourseId().length() != 0) {
			try {
				int examId = Integer.parseInt(baseActionForm.getExamName());
				int courseId = Integer.parseInt(baseActionForm.getCourseId());
				schemeMap = CommonAjaxHandler.getInstance()
						.getSchemeNoByExamIdCourseId(examId, courseId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), schemeMap);
		
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsByCourseIdSchemeNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();
		String sheme = baseActionForm.getSchemeId();
		String[] str = sheme.split("_");
		int shemeId = 0;
		int shemeNo = 0;
		for (int x = 0; x < str.length; x = +2) {
			shemeId = Integer.parseInt(str[0]);
			shemeNo = Integer.parseInt(str[1]);

		}

		int courseId = Integer.parseInt(baseActionForm.getCourseId());
		if (baseActionForm.getCourseId() != null
				&& baseActionForm.getCourseId().length() != 0
				&& baseActionForm.getSchemeId() != null
				&& baseActionForm.getSchemeId().length() != 0) {
			try {
				subjectMap = CommonAjaxHandler.getInstance()
						.getSubjectsByCourse(courseId, shemeId, shemeNo);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getSubjectsTypeBySubjectId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String value = "";
		int subjectId = Integer.parseInt(baseActionForm.getSubjectId());
		if (baseActionForm.getSubjectId() != null
				&& baseActionForm.getSubjectId().length() != 0) {
			try {
				value = CommonAjaxHandler.getInstance()
						.getSubjectsTypeBySubjectId(subjectId);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	public ActionForward getSubjectsTypeBySubjectIdAndCollection(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String value = "";
		Map<String, String> subjectMap = new HashMap<String, String>();
		int subjectId = Integer.parseInt(baseActionForm.getSubjectId());
		if (baseActionForm.getSubjectId() != null
				&& baseActionForm.getSubjectId().length() != 0) {
			try {
				value = CommonAjaxHandler.getInstance()
						.getSubjectsTypeBySubjectId(subjectId);
				if (value.equalsIgnoreCase("T")) {
					subjectMap.put("T", "Theory");
				}
				if (value.equalsIgnoreCase("P")) {
					subjectMap.put("P", "Practical");
				}
				if (value.equalsIgnoreCase("B")) {
					subjectMap.put("T", "Theory");
					subjectMap.put("P", "Practical");
					value = "t";
				}

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("subjectType", value);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);
		return mapping.findForward("ajaxResponseForSubjectType");
	}

	public ActionForward getTypeByAssignmentOverall(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		String assignmentOverall = baseActionForm.getAssignmentOverallType();
		if (baseActionForm.getAssignmentOverallType() != null
				&& baseActionForm.getAssignmentOverallType().length() != 0) {
			try {
				typeMap = CommonAjaxHandler.getInstance()
						.getTypeByAssignmentOverall(assignmentOverall);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), typeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, typeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getExamNameByAcademicYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		String academicYear = baseActionForm.getAcademicYear();
		if (baseActionForm.getAcademicYear() != null
				&& baseActionForm.getAcademicYear().length() != 0) {
			try {
				classMap = CommonAjaxHandler.getInstance()
						.getExamNameByAcademicYear(academicYear);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	// public ActionForward getRoomNoByExamName(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// BaseActionForm baseActionForm = (BaseActionForm) form;
	// Map<Integer, String> roomMap = new HashMap<Integer, String>();
	// String examName = baseActionForm.getExamName();
	// if (baseActionForm.getExamName() != null
	// && baseActionForm.getExamName().length() != 0) {
	// try {
	// roomMap = CommonAjaxHandler.getInstance().getRoomNoByExamName(
	// examName);
	// } catch (Exception e) {
	// log.debug(e.getMessage());
	// }
	// }
	// if (baseActionForm.getPropertyName() != null)
	// baseActionForm.getCollectionMap().put(
	// baseActionForm.getPropertyName(), roomMap);
	// request.setAttribute(CMSConstants.OPTION_MAP, roomMap);
	// return mapping.findForward("ajaxResponseForOptions");
	// }

	/*
	 * 
	 * 
	 * 
	 * public ActionForward getSchemeByCourseId(ActionMapping mapping,
	 * ActionForm form, HttpServletRequest request, HttpServletResponse
	 * response) throws Exception {
	 * 
	 * BaseActionForm baseActionForm = (BaseActionForm) form; int cid;
	 * Map<Integer, String> schemeMap = new HashMap<Integer, String>(); if
	 * (baseActionForm.getCourseId() != null &&
	 * baseActionForm.getCourseId().length() != 0) { try { cid =
	 * Integer.parseInt(baseActionForm.getCourseId()); // The below map contains
	 * key as id and value as name of state. / schemeMap =
	 * CommonAjaxHandler.getInstance() .getSchemeByCourseId(cid);
	 */
	/*
	 * schemeMap = CommonAjaxHandler.getInstance() .getSchemeNoByCourse(cid); }
	 * catch (Exception e) { log.debug(e.getMessage()); } } if
	 * (baseActionForm.getPropertyName() != null)
	 * baseActionForm.getCollectionMap().put( baseActionForm.getPropertyName(),
	 * schemeMap); request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
	 * return mapping.findForward("ajaxResponseForOptions"); }
	 */

	public ActionForward getAgreementNameByClassId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> mapAgree = new HashMap<Integer, String>();
		ArrayList<Integer> listClassId = new ArrayList<Integer>();

		String[] classIds = baseActionForm.getClassCodeIds().split(",");

		for (int i = 0; i < classIds.length; i++) {
			if (classIds[i] != null && classIds[i].length() > 0)
				listClassId.add(Integer.parseInt(classIds[i]));

		}

		try {
			if (classIds != null) {
				mapAgree = CommonAjaxHandler.getInstance()
						.getAgreementNameByClassId(listClassId);
			}
		} catch (Exception e) {

			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), mapAgree);
		request.setAttribute(CMSConstants.OPTION_MAP, mapAgree);

		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getFooterNameByClassId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> mapFooter = new HashMap<Integer, String>();

		ArrayList<Integer> listClassId = new ArrayList<Integer>();

		String classIds[] = baseActionForm.getClassCodeIds().split(",");
		for (int i = 0; i < classIds.length; i++) {
			if (classIds[i] != null && classIds[i].length() > 0)
				listClassId.add(Integer.parseInt(classIds[i]));

		}
		try {
			if (classIds != null) {
				mapFooter = CommonAjaxHandler.getInstance()
						.getFooterNameByClassId(listClassId);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), mapFooter);
		request.setAttribute(CMSConstants.OPTION_MAP, mapFooter);
		return mapping.findForward("ajaxResponseForOptions");
	}

	// ExamPublishHallTicket
	public ActionForward getFooterNameByProgramId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> mapFooter = new HashMap<Integer, String>();

		String programTypeID = baseActionForm.getProgramTypeId();

		try {
			if (programTypeID != null) {
				mapFooter = CommonAjaxHandler.getInstance()
						.getFooterNameByClassId(programTypeID);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), mapFooter);
		request.setAttribute(CMSConstants.OPTION_MAP, mapFooter);
		return mapping.findForward("ajaxResponseForOptions");
	}

	// ExamPublishHallTicket
	public ActionForward getAgreementNameByProgramId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> mapAgree = new HashMap<Integer, String>();

		String programTypeID = baseActionForm.getProgramTypeId();

		try {
			if (programTypeID != null) {

				mapAgree = CommonAjaxHandler.getInstance()
						.getAgreementNameByProgramTypeId(programTypeID);
			}
		} catch (Exception e) {

			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), mapAgree);
		request.setAttribute(CMSConstants.OPTION_MAP, mapAgree);

		return mapping.findForward("ajaxResponseForOptions");
	}

	// exam Student Correction
	public ActionForward getMarkTypeByExamTypeId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<String, String> markTypeMap = new HashMap<String, String>();
		String examType = baseActionForm.getExamType();

		try {
			if (examType.equalsIgnoreCase("Internal")) {
				markTypeMap = new ExamGenHandler().getInternalExamType();
			} else {
				markTypeMap.put("Internal overAll", "Internal overAll");
				markTypeMap.put("Regular overAll", "Regular overAll");
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), markTypeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, markTypeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getInternalExamByAcademicYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> mapInternalExam = new HashMap<Integer, String>();

		String academicYear = baseActionForm.getAcademicYear();

		try {
			if (academicYear != null) {

				mapInternalExam = CommonAjaxHandler.getInstance()
						.getInternalExamByAcademicYear(academicYear);
			}
		} catch (Exception e) {

			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), mapInternalExam);
		request.setAttribute(CMSConstants.OPTION_MAP, mapInternalExam);

		return mapping.findForward("ajaxResponseForOptions");
	}

	// EXAM SECURED MARKS ENTRY
	public ActionForward getStudentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baForm = (BaseActionForm) form;
		ExamSecuredMarksEntryHandler securedHandler = new ExamSecuredMarksEntryHandler();
		String regNo = baForm.getAppRegRollno();
		String examId = baForm.getExamName();
		String subjectId = baForm.getSubjectId();
		String subjectType = baForm.getSubjectType();
		String isPreviousExam = baForm.getIsPreviousExam();
		String schemeNo = baForm.getSchemeNo();
		String value = "$$$###";
		int flag = 0;
		String examType = baForm.getExamType();
		try {
			String rollReg = null;
			rollReg = CommonAjaxHandler.getInstance().getDecryptRegNo(regNo);
			Integer intExamId = null;
			Integer intSubjectId = null;
			Integer intEvaluatorId = null;
			Integer intAnswerScriptId = null;
			if (examId != null)
				intExamId = Integer.parseInt(baForm.getExamName());
			if (subjectId != null)
				intSubjectId = Integer.parseInt(baForm.getSubjectId());
			if (baForm.getEvaluatorId()!=null  && !baForm.getEvaluatorId().equals("null") && baForm.getEvaluatorId().trim().length() > 0)
				intEvaluatorId = Integer.parseInt(baForm.getEvaluatorId());
			if (baForm.getAnswerScriptId() != null && !baForm.getAnswerScriptId().equals("null") && baForm.getAnswerScriptId().trim().length() > 0)
				intAnswerScriptId = Integer
						.parseInt(baForm.getAnswerScriptId());
			ExamSecuredMarksEntryTO val = securedHandler
					.get_securedMarkDetails(intExamId, intSubjectId,
							intEvaluatorId, intAnswerScriptId, rollReg,
							subjectType, isPreviousExam, examType, schemeNo);

			if (val != null) {
				if (subjectType.equalsIgnoreCase("theory")
						&& val.getTheoryMarks() != null)
					value = val.getTheoryMarks();
				else if (subjectType.equalsIgnoreCase("practical")
						&& val.getPracticalMarks() != null) {
					value = val.getPracticalMarks();
				}
				flag = 1;
			}
			if (flag == 1 && value.equals("$$$###")) {
				value = "present";
			}

		} catch (Exception e) {

			log.debug(e.getMessage());
		}

		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	// EXAM SECURED MARKS ENTRY
	public ActionForward getMarksDifference(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baForm = (BaseActionForm) form;
		ExamSecuredMarksEntryHandler securedHandler = new ExamSecuredMarksEntryHandler();
		String regNo = baForm.getAppRegRollno();
		String examId = baForm.getExamName();
		String subjectId = baForm.getSubjectId();
		String evaluatorId = baForm.getEvaluatorId();
		String answerScriptId = baForm.getAnswerScriptId();
		String subjectType = baForm.getSubjectType();
		String marks = baForm.getMarks();
		String value = "$$$###";
		String isPreviousExam = baForm.getIsPreviousExam();
		String supSchemeNo = baForm.getSchemeNo();
		
		try {
			String rollReg = null;
			rollReg = CommonAjaxHandler.getInstance().getDecryptRegNo(regNo);
			Integer intExamId = null;
			Integer intSubjectId = null;
			Integer intEvaluatorId = null;
			Integer intAnswerScriptId = null;
			String examType = baForm.getExamType();
			if (examId != null)
				intExamId = Integer.parseInt(baForm.getExamName());
			if (subjectId != null)
				intSubjectId = Integer.parseInt(baForm.getSubjectId());
			if (evaluatorId != null && !evaluatorId.equals("null") && evaluatorId.trim().length() > 0)
				intEvaluatorId = Integer.parseInt(baForm.getEvaluatorId());
			if (answerScriptId != null && !answerScriptId.equals("null") && answerScriptId.trim().length() > 0)
				intAnswerScriptId = Integer.parseInt(baForm.getAnswerScriptId());
			
			ExamSecuredMarksEntryTO val = null;

			val = securedHandler.get_securedMarkDifference(examType, intExamId,
					intSubjectId, intEvaluatorId, intAnswerScriptId, rollReg,
					subjectType, marks, isPreviousExam, supSchemeNo);

			if (val != null) {
				if (val.getErrorType() != null
						&& val.getErrorType().equals("Duplicate Entry")) {
					value = "duplicate";
				}
				if (val.getErrorType() != null
						&& val.getErrorType().equals("Max Marks Not allowed")) {
					value = "max_" + val.getMaxMarks();
				}
				if (val.getErrorType() != null
						&& val.getErrorType().equals("Special Character")) {
					value = "spl";
				}
				if(val.getErrorType()!=null && val.getErrorType().equalsIgnoreCase("No Definition"))
				{
					value="noDefinition";
				}
				String prevME = "";
				if (val.getPreviousEvaluatorMarks() != null) {
					prevME = val.getPreviousEvaluatorMarks();
				}
				if (!CommonUtil.checkForEmpty(val.getErrorType())) {
					value = prevME + "_" + val.getMistake() + "_"
							+ val.getRetest() + "_" + val.getMarksEntryId()
							+ "_" + val.getDetailId();
				}
			}

		} catch (Exception e) {

			log.debug(e.getMessage());
			e.printStackTrace();
		}

		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	// Exam Secured Marks Verification
	public ActionForward getDecryptRegNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baForm = (BaseActionForm) form;
		ExamSecuredMarksVerificationHandler securedHandler = new ExamSecuredMarksVerificationHandler();
		String regNo = baForm.getAppRegRollno();
		String examId = baForm.getExamName();
		String subjectId = baForm.getSubjectId();
		String evaluatorId = baForm.getEvaluatorId();
		String answerScriptId = baForm.getAnswerScriptId();
		String value = "$$$###";

		try {
			String rollReg = null;
			String rollOrReg = null;
			if (baForm.getType().equalsIgnoreCase("password")) {
				rollReg = CommonAjaxHandler.getInstance()
						.getDecryptRegNo(regNo);
				rollOrReg = rollReg;
			} else {
				rollReg = regNo;
				rollOrReg = "";
			}
			Integer intExamId = null;
			Integer intSubjectId = null;
			Integer intEvaluatorId = null;
			Integer intAnswerScriptId = null;
			if (examId != null)
				intExamId = Integer.parseInt(baForm.getExamName());
			if (subjectId != null)
				intSubjectId = Integer.parseInt(baForm.getSubjectId());
			if (evaluatorId != null && evaluatorId.trim().length() > 0)
				intEvaluatorId = Integer.parseInt(baForm.getEvaluatorId());
			if (answerScriptId != null && answerScriptId.trim().length() > 0)
				intAnswerScriptId = Integer
						.parseInt(baForm.getAnswerScriptId());
			ExamSecuredMarksVerificationTO val = securedHandler
					.get_securedMarkDetails(intExamId, intSubjectId,
							intEvaluatorId, intAnswerScriptId, rollReg);
			ExamSecuredMarksVerificationTO val1 = securedHandler
			.get_securedMarkDetails2(intExamId, intSubjectId,
					intEvaluatorId, intAnswerScriptId, rollReg);
			if(val1 != null){
				value = "$$$$####";
			}
			if (val != null) {

				value = rollOrReg + "_" + val.getTheoryMarks() + "_"
						+ val.getPracticalMarks() + "_" + val.getMistake()
						+ "_" + val.getRetest() + "_" + val.getStudentName()
						+ "_" + val.getMarksEntryId() + "_" + val.getDetailId();
			}

		} catch (Exception e) {

			log.debug(e.getMessage());
		}

		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}
	
	//
	public ActionForward getDecryptRegNo1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baForm = (BaseActionForm) form;
		ExamSecuredMarksVerificationHandler securedHandler = new ExamSecuredMarksVerificationHandler();
		String regNo = baForm.getAppRegRollno();
		String examId = baForm.getExamName();
		String subjectId = baForm.getSubjectId();
		String evaluatorId = baForm.getEvaluatorId();
		String answerScriptId = baForm.getAnswerScriptId();
		String value = "$$$$$#####";
		HttpSession session=request.getSession(true);  
		session.setAttribute("className", null);
		try {
			String rollReg = null;
			String rollOrReg = null;
			if (baForm.getType().equalsIgnoreCase("password")) {
				rollReg = CommonAjaxHandler.getInstance()
						.getDecryptRegNo(regNo);
				rollOrReg = rollReg;
			} else {
				rollReg = regNo;
				rollOrReg = "";
			}
			Integer intExamId = null;
			Integer intSubjectId = null;
			Integer intEvaluatorId = null;
			Integer intAnswerScriptId = null;
			int schemeNo=0;
			if(baForm.getSchemeNo()!=null && !baForm.getSchemeNo().trim().isEmpty() && baForm.getSchemeNo().trim().length()>0 && StringUtils.isNumeric(baForm.getSchemeNo()))
				schemeNo=Integer.parseInt(baForm.getSchemeNo());
			
			if (examId != null)
				intExamId = Integer.parseInt(baForm.getExamName());
			if (subjectId != null)
				intSubjectId = Integer.parseInt(baForm.getSubjectId());
			if (evaluatorId != null && evaluatorId.trim().length() > 0)
				intEvaluatorId = Integer.parseInt(baForm.getEvaluatorId());
			if (answerScriptId != null && answerScriptId.trim().length() > 0)
				intAnswerScriptId = Integer .parseInt(baForm.getAnswerScriptId());
			
			ExamSecuredMarksVerificationTO val = securedHandler .get_securedMarkDetails1(intExamId, intSubjectId, intEvaluatorId, intAnswerScriptId, rollReg,baForm.getExamType(),schemeNo,baForm.getSubjectType());
			ExamSecuredMarksVerificationTO val1 = securedHandler
			.getSecuredMarkDetails(intExamId, intSubjectId, intEvaluatorId, intAnswerScriptId, rollReg,baForm.getExamType(),schemeNo);
			if(val1 != null){
				value = "$$$$####";
			}
			if (val != null && val1 == null) {
				value = rollOrReg + "_" + val.getTheoryMarks() + "_"
						+ val.getPracticalMarks() + "_" + val.getMistake()
						+ "_" + val.getRetest() + "_" + val.getStudentName()
						+ "_" + val.getMarksEntryId() + "_" + val.getDetailId()
						+"_" + val.getStudentId();
				session.setAttribute("className", val.getClassId());
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	//Exam Marks Verification For Printing
	public ActionForward printerConnection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String value = " ";
		BaseActionForm baForm = (BaseActionForm) form;
		ExamSecuredMarksVerificationHandler securedHandler = new ExamSecuredMarksVerificationHandler();
		String regNo = baForm.getAppRegRollno();
		int schemeNo=0;
		if(baForm.getSchemeNo()!=null && !baForm.getSchemeNo().trim().isEmpty() && baForm.getSchemeNo().trim().length()>0 && StringUtils.isNumeric(baForm.getSchemeNo()))
			schemeNo=Integer.parseInt(baForm.getSchemeNo());
		ExamSecuredMarksVerificationTO val = securedHandler .get_securedMarkDetails3(regNo,schemeNo);
		SamplePrinting sample = new SamplePrinting();
		sample.samplePrint(val.getRegNo(),val.getStudentName());
		//HtmlPrinter.printHtml(val.getRegNo());
		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}
	
	
	// EXAM SECURED MARKS ENTRY
	public ActionForward getDecryptRegNoForSecuredEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baForm = (BaseActionForm) form;
		ExamSecuredMarksEntryHandler securedHandler = new ExamSecuredMarksEntryHandler();
		String regNo = baForm.getAppRegRollno();
		String examId = baForm.getExamName();
		String subjectId = baForm.getSubjectId();
		String evaluatorId = baForm.getEvaluatorId();
		String answerScriptId = baForm.getAnswerScriptId();
		String subjectType = baForm.getSubjectType();
		String value = "$$$###";

		try {
			String rollReg = null;
			String rollOrReg = null;

			rollReg = CommonAjaxHandler.getInstance().getDecryptRegNo(regNo);

			Integer intExamId = null;
			Integer intSubjectId = null;
			Integer intEvaluatorId = null;
			Integer intAnswerScriptId = null;
			if (examId != null)
				intExamId = Integer.parseInt(baForm.getExamName());
			if (subjectId != null)
				intSubjectId = Integer.parseInt(baForm.getSubjectId());
			if (evaluatorId != null && evaluatorId.trim().length() > 0)
				intEvaluatorId = Integer.parseInt(baForm.getEvaluatorId());
			if (answerScriptId != null && answerScriptId.trim().length() > 0)
				intAnswerScriptId = Integer
						.parseInt(baForm.getAnswerScriptId());
			ExamSecuredMarksEntryTO val = securedHandler.get_view_details(
					intExamId, intSubjectId, intEvaluatorId, intAnswerScriptId,
					rollReg, subjectType);

			if (val != null) {

				value = val.getTheoryMarks() + "_" + val.getPracticalMarks()
						+ "_" + val.getPreviousEvaluatorMarks() + "_"
						+ val.getMistake() + "_" + val.getRetest() + "_"
						+ val.getMarksEntryId() + "_" + val.getDetailId();
			}

		} catch (Exception e) {

			log.debug(e.getMessage());
		}

		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	// EXAM SECURED MARKS ENTRY
	public ActionForward getEvaluatorTypeBySubject(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HashMap<Integer, String> evaluatorTypeMap = new HashMap<Integer, String>();
		String subjectType = baseActionForm.getSubjectType();
		ExamSecuredMarksEntryHandler securedhandler = new ExamSecuredMarksEntryHandler();
		if (baseActionForm.getSubjectId() != null
				&& baseActionForm.getSubjectId().length() != 0) {
			try {
				int examId = Integer.parseInt(baseActionForm.getExamName());

				int subjectId = Integer.parseInt(baseActionForm.getSubjectId());
				int subjectTypeId = 0;
				if (subjectType.equalsIgnoreCase("t"))
					subjectTypeId = 1;
				else if (subjectType.equalsIgnoreCase("p"))
					subjectTypeId = 0;
				evaluatorTypeMap = securedhandler.getEvaluatorType(subjectId,
						subjectTypeId, examId);

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), evaluatorTypeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, evaluatorTypeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	// EXAM SECURED MARKS ENTRY
	public ActionForward get_answerScript_type(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HashMap<Integer, String> answerScriptType = new HashMap<Integer, String>();
		String subjectType = baseActionForm.getSubjectType();
		ExamSecuredMarksEntryHandler securedhandler = new ExamSecuredMarksEntryHandler();
		if (baseActionForm.getSubjectId() != null
				&& baseActionForm.getSubjectId().length() != 0) {
			try {
				int subjectId = Integer.parseInt(baseActionForm.getSubjectId());
				int examId = Integer.parseInt(baseActionForm.getExamName());
				int subjectTypeId = 0;
				if (subjectType.equalsIgnoreCase("t"))
					subjectTypeId = 1;
				else if (subjectType.equalsIgnoreCase("p"))
					subjectTypeId = 0;
				answerScriptType = securedhandler.get_answerScript_type(
						subjectId, subjectTypeId, examId);

			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), answerScriptType);
		request.setAttribute(CMSConstants.OPTION_MAP, answerScriptType);
		return mapping.findForward("ajaxResponseForOptions");
	}

	// Exam Assign Students To Room
	public ActionForward getSubjectNameByClassIds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> subjectMap = new HashMap<Integer, String>();

		String classId = baseActionForm.getClassId();
		String date = baseActionForm.getDate();
		String time = baseActionForm.getTime();
		String examName = baseActionForm.getExamName();

		try {
			if (classId != null) {

				subjectMap = CommonAjaxHandler
						.getInstance()
						.getSubjectNameByClassIds(classId, date, time, examName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), subjectMap);
		request.setAttribute(CMSConstants.OPTION_MAP, subjectMap);

		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getDateTimeByExamId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String dateTimeValue = null;
		String examName = baseActionForm.getExamName();
		try {
			if (examName != null) {

				dateTimeValue = CommonAjaxHandler.getInstance()
						.getDateTimeByExamId(Integer.parseInt(examName));
			}
		} catch (Exception e) {

			log.debug(e.getMessage());
		}

		// request.setAttribute("dateTimeValue", dateTimeValue);
		// return mapping.findForward("ajaxResponseFordateTime");

		request.setAttribute("noofcandidates", dateTimeValue);
		return mapping.findForward("ajaxResponseForInterview");

	}

	public ActionForward getAnswerScriptTypeMap(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HashMap<Integer, String> answerScriptType = new HashMap<Integer, String>();
		ExamMarksEntryHandler securedhandler = new ExamMarksEntryHandler();

		try {
			Integer courseId = null, schemeNo = null, subjectId = null, subjectTypeId = null, examName = null;
			if (CommonUtil.checkForEmpty(baseActionForm.getSubjectId())) {
				subjectId = Integer.parseInt(baseActionForm.getSubjectId());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getSubjectType())) {
				subjectTypeId = Integer.parseInt(baseActionForm
						.getSubjectType());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getCourseId())) {
				courseId = Integer.parseInt(baseActionForm.getCourseId());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getSchemeId())) {

				String schemes[] = baseActionForm.getSchemeId().split("_");
				if(courseId == null){
					schemeNo = Integer.parseInt(schemes[0]);
				}
				else{
					schemeNo = Integer.parseInt(schemes[1]);
				}
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getExamName())) {

				examName = Integer.parseInt(baseActionForm.getExamName());

			}

			answerScriptType = securedhandler.getListanswerScriptType(courseId,
					schemeNo, subjectId, subjectTypeId, examName);

		} catch (Exception e) {

			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), answerScriptType);
		request.setAttribute(CMSConstants.OPTION_MAP, answerScriptType);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getEvaluatorTypeMap(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HashMap<Integer, String> evaluatorList = new HashMap<Integer, String>();
		ExamMarksEntryHandler securedhandler = new ExamMarksEntryHandler();

		try {
			Integer courseId = null, schemeNo = null, subjectId = null, subjectTypeId = null, examName = null;
			if (CommonUtil.checkForEmpty(baseActionForm.getSubjectId())) {
				subjectId = Integer.parseInt(baseActionForm.getSubjectId());
			}
			if (baseActionForm.getSubjectType() != null
					&& baseActionForm.getSubjectType().trim().length() > 0) {
				subjectTypeId = Integer.parseInt(baseActionForm
						.getSubjectType());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getCourseId())) {
				courseId = Integer.parseInt(baseActionForm.getCourseId());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getSchemeId())) {

				String schemes[] = baseActionForm.getSchemeId().split("_");
				if(courseId == null){
					schemeNo = Integer.parseInt(schemes[0]);
				}
				else{
					schemeNo = Integer.parseInt(schemes[1]);
				}
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getExamName())) {

				examName = Integer.parseInt(baseActionForm.getExamName());

			}
			evaluatorList = securedhandler.get_evaluatorList_ruleSettings(
					courseId, schemeNo, subjectId, subjectTypeId, examName);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), evaluatorList);
		request.setAttribute(CMSConstants.OPTION_MAP, evaluatorList);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getMaxTheoryMarcks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm objForm = (BaseActionForm) form;
		int value = 0;
		BigDecimal marcks = new BigDecimal(objForm.getMarksForReg());
		int courseId = Integer.parseInt(objForm.getCourseId());
		int schemeNo = Integer.parseInt(objForm.getSchemeNo());
		int subjectId = Integer.parseInt(objForm.getSubjectId());
		int studentId = Integer.parseInt(objForm.getBaseStudentId());
		String assignmentOverall = objForm.getAssignmentOverallType();
		String subjectType = objForm.getSubjectType();
		String type = objForm.getType();

		if (objForm.getMarksForReg() != null
				&& objForm.getMarksForReg().length() != 0) {
			try {
				value = CommonAjaxHandler.getInstance().getMaxTheoryMarcks(
						marcks, courseId, schemeNo, subjectId, subjectType,
						studentId, assignmentOverall, type);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	public ActionForward getMaxPracticalMarcks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm objForm = (BaseActionForm) form;
		int value = 0;
		int marcks = Integer.parseInt(objForm.getMarksForReg());
		int courseId = Integer.parseInt(objForm.getCourseId());
		int schemeNo = Integer.parseInt(objForm.getSchemeNo());
		int subjectId = Integer.parseInt(objForm.getSubjectId());
		String subjectType = objForm.getSubjectType();
		String assignmentOverall = objForm.getAssignmentOverallType();
		if (objForm.getMarksForReg() != null
				&& objForm.getMarksForReg().length() != 0) {
			try {
				value = CommonAjaxHandler.getInstance().getMaxPracticalMarcks(
						marcks, courseId, schemeNo, subjectId, subjectType,
						assignmentOverall);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}

	public ActionForward getEvaluatorTypeMapForStudentCorrection(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HashMap<Integer, String> evaluatorList = new HashMap<Integer, String>();
		ExamStudentMarksCorrectionHandler correctionhandler = new ExamStudentMarksCorrectionHandler();

		try {
			Integer courseId = null, schemeNo = null, subjectId = null;
			if (CommonUtil.checkForEmpty(baseActionForm.getSubjectId())) {
				subjectId = Integer.parseInt(baseActionForm.getSubjectId());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getCourseId())) {
				courseId = Integer.parseInt(baseActionForm.getCourseId());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getSchemeId())) {

				String schemes[] = baseActionForm.getSchemeId().split("_");
				schemeNo = Integer.parseInt(schemes[1]);
			}

			evaluatorList = correctionhandler
					.get_evaluatorList_fromRuleSettings(courseId, schemeNo,
							subjectId);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), evaluatorList);
		request.setAttribute(CMSConstants.OPTION_MAP, evaluatorList);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getAnswerScriptTypeMapForStudentCorrection(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		HashMap<Integer, String> answerScriptType = new HashMap<Integer, String>();
		ExamStudentMarksCorrectionHandler correctionhandler = new ExamStudentMarksCorrectionHandler();

		try {
			/*Integer courseId = null,
					schemeNo = null,
					subjectId = null;
			if (CommonUtil.checkForEmpty(baseActionForm.getSubjectId())) {
				subjectId = Integer.parseInt(baseActionForm.getSubjectId());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getCourseId())) {
				courseId = Integer.parseInt(baseActionForm.getCourseId());
			}
			if (CommonUtil.checkForEmpty(baseActionForm.getSchemeId())) {

				String schemes[] = baseActionForm.getSchemeId().split("_");

				schemeNo = Integer.parseInt(schemes[1]);
			}*/

			answerScriptType = correctionhandler.getListanswerScriptType();

		} catch (Exception e) {

			log.debug(e.getMessage());
		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), answerScriptType);
		request.setAttribute(CMSConstants.OPTION_MAP, answerScriptType);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getExamDateTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm objform = (BaseActionForm) form;
		int examId = 0;
		examId = Integer.parseInt(objform.getExamName());
		String dateTimeValue = "0";
		if (objform.getExamName() != null && objform.getExamName().length() > 0) {
			try {
				dateTimeValue = CommonAjaxHandler.getInstance()
						.getDateTimeByExamId(examId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("noofcandidates", dateTimeValue);
		return mapping.findForward("ajaxResponseForInterview");
	}

	public ActionForward getExamDateTimeByExamId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm objform = (BaseActionForm) form;
		int examId = 0;
		examId = Integer.parseInt(objform.getExamName());
		String dateTimeValue = "0";
		if (objform.getExamName() != null && objform.getExamName().length() > 0) {
			try {
				dateTimeValue = CommonAjaxHandler.getInstance()
						.getExamDateTimeByExamId(examId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute("noofcandidates", dateTimeValue);
		return mapping.findForward("ajaxResponseForInterview");
	}

	public ActionForward getSubjectCodeName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		ArrayList<KeyValueTO> listValues = new ArrayList<KeyValueTO>();
		try {

			if (baseActionForm.getSchemeNo() != null
					&& baseActionForm.getSchemeNo().length() > 0) {
				String sCodeName = baseActionForm.getSchemeNo();
				listValues = new CommonAjaxExamHandler()
						.getSubjectCodeName(sCodeName, baseActionForm.getExamTypeId());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		//if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), listValues);
		request.setAttribute(CMSConstants.OPTION_MAP, listValues);
		return mapping.findForward("ajaxResponseForListOptions");
	}

	public ActionForward getExamNameByProcessType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		HashMap<Integer, String> examMap = new HashMap<Integer, String>();
		ExamGenHandler handler = new ExamGenHandler();
		if (baseActionForm.getExamType() != null
				&& baseActionForm.getExamType().length() > 0 && baseActionForm.getAcademicYear()!=null && baseActionForm.getAcademicYear().length()>0) {

			examMap = handler.getExamNameByProcessType(baseActionForm
					.getExamType(),baseActionForm.getAcademicYear());

		}

		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForOptions");
	}

	public ActionForward getCoursesByProgramTypes1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		ArrayList<KeyValueTO> listValues = new ArrayList<KeyValueTO>();
		String programTypeId = baseActionForm.getProgramTypeId();
		if (baseActionForm.getProgramTypeId() != null
				&& baseActionForm.getProgramTypeId().length() != 0) {
			try {
				listValues = CommonAjaxHandler.getInstance()
						.getCoursesByProgramTypes1(programTypeId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), listValues);
		request.setAttribute(CMSConstants.OPTION_MAP, listValues);
		return mapping.findForward("ajaxResponseForListOptions");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExamNameByExamTypeWithoutCurrentExam(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String examType = baseActionForm.getExamType();
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		try {
			examMap = CommonAjaxHandler.getInstance().getExamNameByExamType(
					examType);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		baseActionForm.setExamTypeId(0);
		request.setAttribute("subjectType", 0);
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForSubjectType");
	}
	
	
	public ActionForward getSchemeNoByAcademicYear(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<String, String> schemeMap = new HashMap<String, String>();
		if (baseActionForm.getAcademicYear().length() != 0
				&& baseActionForm.getAcademicYear() != null) {
			try {
				String academicYear = baseActionForm.getAcademicYear();
				String courseId = baseActionForm.getCourseId();
				CommonAjaxExamHandler handler = new CommonAjaxExamHandler();
				schemeMap = handler.getSchemeNoByAcademicYear(courseId,
								academicYear);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getProgramsByExamName(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		BaseActionForm baseActionForm = (BaseActionForm) form;
		String examName = "";
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName() != null && baseActionForm.getExamName().length() != 0) 
		{
			try
			{
				examName = baseActionForm.getExamName();
				schemeMap = CommonAjaxHandler.getInstance().getProgramsByExamName(examName);
			}
			catch (Exception e) 
			{
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getSubjectsByProgram(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Integer programId=null;
		Map<Integer, String> schemeMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName() != null && baseActionForm.getExamName().length() != 0) 
		{
			try
			{
				programId =Integer.parseInt(baseActionForm.getProgramId());
				schemeMap = CommonAjaxHandler.getInstance().getSubjectsByProgram(programId);
			}
			catch (Exception e) 
			{
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), schemeMap);
		request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	public ActionForward getSectionByExamIdCourseIdSchemeId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BaseActionForm baseActionForm = (BaseActionForm) form;

		String courseId = "";
		String schemeId = "";
		String schemeNo = "";

		Map<Integer, String> sectionMap = new HashMap<Integer, String>();
		if (baseActionForm.getCourseId() != null

		&& baseActionForm.getCourseId().length() != 0
				&& baseActionForm.getSchemeId() != null

				&& baseActionForm.getSchemeId().length() != 0) {
			try {

				courseId = baseActionForm.getCourseId();
				int examId  = 0;
				if(baseActionForm.getExamName()!= null){
					examId = Integer.parseInt(baseActionForm.getExamName());
				}
				Integer academicYear = CommonAjaxHandler.getInstance().getAcademicYearByExam(examId);
				if(academicYear ==null){
					academicYear = 0;
				}
				schemeId = baseActionForm.getSchemeId().split("_")[0];
				schemeNo = baseActionForm.getSchemeId().split("_")[1];
				sectionMap = CommonAjaxHandler.getInstance()
						.getSectionByCourseIdSchemeId(courseId, schemeId,
								schemeNo, academicYear.toString());

			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), sectionMap);
		request.setAttribute(CMSConstants.OPTION_MAP, sectionMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	// Exam Secured Marks Verification
	public ActionForward getDecryptRegNo2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baForm = (BaseActionForm) form;
		//ExamSecuredMarksVerificationHandler securedHandler = new ExamSecuredMarksVerificationHandler();
		String regNo = baForm.getAppRegRollno();
		String value = "$$$###";

		try {
			String rollReg = null;
			String rollOrReg = null;
			if (baForm.getType().equalsIgnoreCase("password")) {
				rollReg = CommonAjaxHandler.getInstance()
						.getDecryptRegNo(regNo);
				rollOrReg = rollReg;
			} else {
				rollReg = regNo;
				rollOrReg = "";
			}
			if (rollReg != null) {

				value = rollOrReg ;
			}
			

		} catch (Exception e) {

			log.debug(e.getMessage());
		}

		request.setAttribute("noofcandidates", value);
		return mapping.findForward("ajaxResponseForInterview");
	}
	public ActionForward getClassCodeByExamNameWithYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		int examName = Integer.parseInt(baseActionForm.getExamName());
		if (baseActionForm.getExamName() != null
				&& baseActionForm.getExamName().length() != 0) {
			try {
				classMap = CommonAjaxHandler.getInstance()
						.getClassCodeByExamNameWithYear(examName);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getSortedSubjectList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		ArrayList<KeyValueTO> listValues = new ArrayList<KeyValueTO>();
		try{
			if(baseActionForm.getSubjectName()!= null && !baseActionForm.getSubjectName().isEmpty()){
				listValues = ExamValidationDetailsHandler.getInstance().getSortedSubjectList(baseActionForm.getSubjectName(),baseActionForm.getSchemeNo(),baseActionForm.getExamTypeId());
				request.setAttribute(CMSConstants.OPTION_MAP, listValues);
				
			}
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), listValues);
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapping.findForward("ajaxResponseForListOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExamNameByExamTypeYearWise(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String examType = baseActionForm.getExamType();
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		String currentExam = null;
		try {
			examMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType(examType,baseActionForm.getYear());
			currentExam = examhandler.getCurrentExamName(examType);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (currentExam != null) {
			baseActionForm.setExamTypeId(Integer.parseInt(currentExam));
			request.setAttribute("subjectType", currentExam);
		} else {
			baseActionForm.setExamTypeId(0);
			request.setAttribute("subjectType", 0);
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForSubjectType");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuatorNames(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		String valuatorName = baseActionForm.getValuatorName();
		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		String currentExam = null;
		try {
			valuatorMap = ExamValidationDetailsHandler.getInstance().getValuatorNameList(valuatorName,baseActionForm.getSubjectId());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), valuatorMap);
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExternalEmployeeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ExamValidationDetailsHandler.getInstance().getOtherEmployeeList(baseActionForm.getSubjectId());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getSubjectsCodeNameByCourseSchemeExamId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> courseMap = new HashMap<Integer, String>();
		int courseId=0;
		if (baseActionForm.getCourseId() != null
				&& baseActionForm.getCourseId().length() > 0) {
		 courseId = Integer.parseInt(baseActionForm.getCourseId());
		}
		if (baseActionForm.getSchemeId() != null
				&& baseActionForm.getSchemeId().trim().length() > 0) {
			String schemeSplit[] = baseActionForm.getSchemeId().split("_");
			int schemeNo = Integer.parseInt(schemeSplit[1]);
			int schemeId = Integer.parseInt(schemeSplit[0]);
			try {
				Integer examId = null;
				String sCodeName="";
				Integer academicYear=null;
				if (baseActionForm.getExamName() != null
						&& baseActionForm.getExamName().trim().length() > 0) {
					examId = Integer.parseInt(baseActionForm.getExamName());
				}
				if (baseActionForm.getSchemeNo() != null
						&& baseActionForm.getSchemeNo().length() > 0) {
					 sCodeName = baseActionForm.getSchemeNo();
				}
				if (baseActionForm.getYear() != null
						&& baseActionForm.getYear().length() > 0) {
					 academicYear =Integer.parseInt(baseActionForm.getYear()) ;
				}
				courseMap = CommonAjaxHandler.getInstance()
						.getSubjectsCodeNameByCourseSchemeExamId(sCodeName,courseId,schemeId,schemeNo, examId,academicYear);
				courseMap=CommonUtil.sortMapByValue(courseMap);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		//if (baseActionForm.getPropertyName() != null)
			//baseActionForm.getCollectionMap().put(
					//baseActionForm.getPropertyName(), courseMap);
		request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getSubjectFromRevaluationOrRetotaling(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		ArrayList<KeyValueTO> listValues = new ArrayList<KeyValueTO>();
		try {

			if (baseActionForm.getSchemeNo() != null
					&& baseActionForm.getSchemeNo().length() > 0) {
				String sCodeName = baseActionForm.getSchemeNo();
				listValues = new CommonAjaxExamHandler()
						.getSubjectFromRevaluationOrRetotaling(sCodeName, baseActionForm.getExamTypeId());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		//if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), listValues);
		request.setAttribute(CMSConstants.OPTION_MAP, listValues);
		return mapping.findForward("ajaxResponseForListOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectCodeNameNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			map = NewSecuredMarksEntryHandler.getInstance().getSubjects(baseActionForm.getExamName(),baseActionForm.getSchemeNo(),baseActionForm.getExamType(),baseActionForm.getYear());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, map);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuatorsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ExamValidationDetailsHandler.getInstance().getValuatorNameListBySubjectDept(baseActionForm.getSubjectId());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExternalValuatorsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ExamValidationDetailsHandler.getInstance().getOtherEmployeeList(baseActionForm.getSubjectId());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuatorsScheduleList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ValuationScheduleHandler.getInstance().getValuatorNameListBySubjectDept(baseActionForm.getSubjectId());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExternalValuatorsScheduleList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ValuationScheduleHandler.getInstance().getOtherEmployeeList(baseActionForm.getSubjectId());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuatorsAllList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ValuationScheduleHandler.getInstance().getValuatorsAllList();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExternalValuatorsScheduleAllList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ValuationScheduleHandler.getInstance().getOtherEmployeeAllList();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuatorsListFromExamValuationDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ExamValidationDetailsHandler.getInstance().getValuatorNameListBySubjectDeptFromValuationScheduleDetails(baseActionForm.getSubjectId(),baseActionForm.getExamid());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getExternalValuatorsListFromExamValuationDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> valuatorMap = new HashMap<Integer, String>();
		try {
			valuatorMap = ExamValidationDetailsHandler.getInstance().getOtherEmployeeListFromValuationScheduleDetails(baseActionForm.getSubjectId(),baseActionForm.getExamid());
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		request.setAttribute(CMSConstants.OPTION_MAP, valuatorMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getClasesByExamNameNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;

		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName() != null && baseActionForm.getExamName().length() != 0) {
			try {
				classMap = AdminHallTicketHandler.getInstance().getClasesByExamName(baseActionForm.getExamName(),baseActionForm.getYear());
				if(classMap != null && !classMap.isEmpty()){
					classMap = CommonUtil.sortMapByValue(classMap);
				}
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	//pavani
	public ActionForward getClassesByCourseAndYear1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> rejoinClassMap = new HashMap<Integer, String>();
		try {
			int courseId = 0;
			int year = 0;
			if(baseActionForm.getCourseId()!= null && baseActionForm.getYear()!= null){
				courseId = Integer.parseInt(baseActionForm.getCourseId());
				year = Integer.parseInt(baseActionForm.getYear());
			}
			rejoinClassMap = CommonAjaxHandler.getInstance().getClassesBySelectedCourse1(courseId, year);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			
		if (baseActionForm.getPropertyName() != null) {
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), rejoinClassMap);
		}
		request.setAttribute(CMSConstants.OPTION_MAP, rejoinClassMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getExamNameByAcademicYearAndExamType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> examMap = new HashMap<Integer, String>();
		String academicYear = baseActionForm.getAcademicYear();
		String examType = baseActionForm.getExamType();
		if (baseActionForm.getAcademicYear() != null
				&& baseActionForm.getAcademicYear().length() != 0) {
			try {
				examMap = CommonAjaxHandler.getInstance()
						.getExamNameByAcademicYearAndExamType(academicYear,examType);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), examMap);
		request.setAttribute(CMSConstants.OPTION_MAP, examMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
	public ActionForward getClassNameByExamName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseActionForm baseActionForm = (BaseActionForm) form;
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if (baseActionForm.getExamName().length() != 0
				&& baseActionForm.getExamName() != null) {
			int examId = Integer.parseInt(baseActionForm.getExamName());
			try {
				
				classMap = CommonAjaxHandler.getInstance()
						.getClassNameByExamName(examId);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
		if (baseActionForm.getPropertyName() != null)
			baseActionForm.getCollectionMap().put(
					baseActionForm.getPropertyName(), classMap);
		request.setAttribute(CMSConstants.OPTION_MAP, classMap);
		return mapping.findForward("ajaxResponseForOptions");
	}
	
			
		
		public ActionForward getClassNameByExamNameForSupplementary(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			if (baseActionForm.getExamName().length() != 0
					&& baseActionForm.getExamName() != null) {
				int examId = Integer.parseInt(baseActionForm.getExamName());
				try {
					
	
					classMap = CommonAjaxHandler.getInstance()
							.getClassNameByExamNameForSupplementary(examId);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			if (baseActionForm.getPropertyName() != null)
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), classMap);
			request.setAttribute(CMSConstants.OPTION_MAP, classMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		public ActionForward getCoursesByProgram(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<Integer, String> classMap = new HashMap<Integer, String>();
			String programTypeId = baseActionForm.getProgramIdList().toString();
			if (baseActionForm.getProgramTypeId() != null
					&& baseActionForm.getProgramTypeId().length() != 0) {
				try {
					classMap = CommonAjaxHandler.getInstance()
							.getCoursesByProgramTypes(programTypeId);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			if (baseActionForm.getPropertyName() != null)
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), classMap);
			request.setAttribute(CMSConstants.OPTION_MAP, classMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		
		public ActionForward getSchemeNoByExamId(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			Map<String, String> schemeMap = new HashMap<String, String>();
			if (baseActionForm.getExamName() != null
					&& baseActionForm.getExamName().length() != 0
					) {
				try {
					int examId = Integer.parseInt(baseActionForm.getExamName());
					
					schemeMap = CommonAjaxHandler.getInstance()
							.getSchemeNoByExamId(examId);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}

			if (baseActionForm.getPropertyName() != null)
				baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), schemeMap);
			
			request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		public ActionForward getProgramesByActivityId(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;

			String deptId = "";
			Map<Integer, String> programeMap = new HashMap<Integer, String>();
			if (baseActionForm.getActivityId() != null
					&& baseActionForm.getActivityId().length() != 0) {
				try {
					deptId = baseActionForm.getActivityId();
					programeMap = CommonAjaxHandler.getInstance().getProgramesByActivityId(deptId);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			if (baseActionForm.getPropertyName() != null)
				baseActionForm.getCollectionMap().put(
						baseActionForm.getPropertyName(), programeMap);
			request.setAttribute(CMSConstants.OPTION_MAP, programeMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		public ActionForward getSchemeNoByCourseIdByTeacher(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			HttpSession session = request.getSession();
			String teacherId = session.getAttribute("uid").toString();
			Map<String, String> schemeMap = new HashMap<String, String>();
			if (baseActionForm.getCourseId() != null
					&& baseActionForm.getCourseId().length() != 0) {
				try {
					
					int courseId = Integer.parseInt(baseActionForm.getCourseId());
					int year = Integer.parseInt(baseActionForm.getYear());
					schemeMap = CommonAjaxExamHandler.getInstance()
							.getSchemeNoByCourseIdByTeacher(courseId, teacherId,year);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}

			if (baseActionForm.getPropertyName() != null)
				baseActionForm.getCollectionMap().put(baseActionForm.getPropertyName(), schemeMap);
			
			request.setAttribute(CMSConstants.OPTION_MAP, schemeMap);
			return mapping.findForward("ajaxResponseForOptions");
		}
		public ActionForward getSubjectsCodeNameByCourseSchemeIdByTeacher(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			BaseActionForm baseActionForm = (BaseActionForm) form;
			
			HttpSession session = request.getSession();
			String teacherId = session.getAttribute("uid").toString();
			
			Map<Integer, String> courseMap = new HashMap<Integer, String>();
			int courseId=0;
			if (baseActionForm.getCourseId() != null
					&& baseActionForm.getCourseId().length() > 0) {
			 courseId = Integer.parseInt(baseActionForm.getCourseId());
			}
			if (baseActionForm.getSchemeId() != null
					&& baseActionForm.getSchemeId().trim().length() > 0) {
				String schemeSplit[] = baseActionForm.getSchemeId().split("_");
				int schemeNo = Integer.parseInt(schemeSplit[1]);
				int schemeId = Integer.parseInt(schemeSplit[0]);
				try {
					
					String sCodeName="";
					
					if (baseActionForm.getSchemeNo() != null
							&& baseActionForm.getSchemeNo().length() > 0) {
						 sCodeName = baseActionForm.getSchemeNo();
					}
					courseMap = CommonAjaxHandler.getInstance()
							.getSubjectsCodeNameByCourseSchemeIdByTeacher(sCodeName,courseId,schemeId,schemeNo,teacherId);
					courseMap=CommonUtil.sortMapByValue(courseMap);
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
			//if (baseActionForm.getPropertyName() != null)
				//baseActionForm.getCollectionMap().put(
						//baseActionForm.getPropertyName(), courseMap);
			request.setAttribute(CMSConstants.OPTION_MAP, courseMap);
			return mapping.findForward("ajaxResponseForOptions");
		}

}
