package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseGradeDefnBO;
import com.kp.cms.bo.exam.ExamSubDefinitionCourseWiseBO;
import com.kp.cms.bo.exam.ExamSubjectSectionMasterBO;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ExamSubjectDefCourseForm;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.to.exam.ExamSubCoursewiseGradeDefnTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseDisplayTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;

public class SubjectDefinitionCertificateCourseHelper {


	
	public ArrayList<ExamSubDefinitionCourseWiseTO> convertBOToTO(
			List<ExamSubDefinitionCourseWiseBO> listBO) {
		ArrayList<ExamSubDefinitionCourseWiseTO> listTO = new ArrayList<ExamSubDefinitionCourseWiseTO>();
		ExamSubDefinitionCourseWiseTO eTO;

		Iterator it = listBO.iterator();
		while (it.hasNext()) {
			Object[] row = (Object[]) it.next();
			eTO = new ExamSubDefinitionCourseWiseTO();
			eTO.setSubjectId(Integer.parseInt(row[0].toString()));
			eTO.setSubjectName(row[1].toString());
			eTO.setSubjectCode(row[2].toString());
			if(row[3]!= null){
				eTO.setSubjectOrder(row[3].toString());
			}
			if(row[4]!=null){
				eTO.setTheoryCredit(row[4].toString());
			}
			if(row[5]!=null){
				eTO.setSubjectSection(row[5].toString());
			}
			if(row[6]!=null){
				eTO.setPracticalCredit(row[6].toString());
			}
			listTO.add(eTO);
		}
		Collections.sort(listTO);
		return listTO;
	}

	public ExamSubDefinitionCourseWiseTO createBOToTO(SubjectUtilBO sbo) {

		ExamSubDefinitionCourseWiseTO to = null;
		if (sbo != null) {
			to = new ExamSubDefinitionCourseWiseTO();
			to.setSubjectCode(sbo.getCode());
			to.setSubjectName(sbo.getName());
		
			to.setSubjectId(sbo.getId());
			return to;

		}
		
		return new ExamSubDefinitionCourseWiseTO();

	}

	public ArrayList<KeyValueTO> convertBOToTO_SubjectSection_List(
			List<ExamSubjectSectionMasterBO> listBO) {
		ArrayList<KeyValueTO> l = new ArrayList<KeyValueTO>();
		Iterator<ExamSubjectSectionMasterBO> itr = listBO.iterator();
		while (itr.hasNext()) {
			ExamSubjectSectionMasterBO bo = (ExamSubjectSectionMasterBO) itr
					.next();
			l.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(l,new KeyValueTOComparator());
		return l;

	}


	public ArrayList<ExamSubCoursewiseGradeDefnTO> convertBOToTO(
			ArrayList<ExamSubCoursewiseGradeDefnBO> listBO) {
		ArrayList<ExamSubCoursewiseGradeDefnTO> listTO = new ArrayList<ExamSubCoursewiseGradeDefnTO>();
		ExamGenHandler handler = new ExamGenHandler();
		for (ExamSubCoursewiseGradeDefnBO bo : listBO) {

			int subjectId = bo.getSubjectId();

			ExamSubCoursewiseGradeDefnTO eTO = new ExamSubCoursewiseGradeDefnTO(bo.getStartPrcntgGrade()
					.toString(), bo.getEndPrcntgGrade().toString(), bo
					.getGrade(), bo.getGradeInterpretation(), bo
					.getResultClass(), bo.getGradePoint().toString(),
					subjectId,

					handler.getSubjectName(subjectId), bo.getId());

			listTO.add(eTO);
		}
		Collections.sort(listTO);
		return listTO;
	}

	// ---------Define Attendance---------------

	public ExamSubjectDefCourseForm createFormObjcetAttendance(
			ExamSubjectDefCourseForm form,
			ExamSubCoursewiseAttendanceMarksBO objBO) {
		form.setId(objBO.getId().toString());
		form.setSubId(Integer.toString(objBO.getSubjectId()));
		form.setStartPercentage(objBO.getFromPrcntgAttndnc().toString());
		form.setEndPercentage(objBO.getToPrcntgAttndnc().toString());
		form.setMarks(objBO.getAttendanceMarks().toString());

		form.setOrgSubid(objBO.getSubjectId());
		form.setOrgStartPercentage(objBO.getFromPrcntgAttndnc().toString());
		form.setOrgEndPercentage(objBO.getToPrcntgAttndnc().toString());
		form.setOrgMarks(objBO.getAttendanceMarks().toString());

		return form;
	}

	public ArrayList<ExamSubDefinitionCourseWiseDisplayTO> convertBOToDisplayTO(
			List<ExamSubDefinitionCourseWiseBO> listBO) {

		ArrayList<ExamSubDefinitionCourseWiseDisplayTO> listTO = new ArrayList<ExamSubDefinitionCourseWiseDisplayTO>();
		ExamSubDefinitionCourseWiseDisplayTO to = null;

		Iterator it = listBO.iterator();
		while (it.hasNext()) {
			Object[] row = (Object[]) it.next();
			to = new ExamSubDefinitionCourseWiseDisplayTO();
			String value = (Integer.parseInt(row[12].toString().trim()) > 0) ? "on"
					: "off";
			to.setDontAddTotMarkClsDecln(value);

			value = (Integer.parseInt(row[13].toString()) > 0) ? "on" : "off";
			to.setDontShowSubType(value);

			value = (Integer.parseInt(row[14].toString()) > 0) ? "on" : "off";
			to.setDontShowMaxMarks(value);

			value = (Integer.parseInt(row[15].toString()) > 0) ? "on" : "off";
			to.setDontShowAttMarks(value);

			value = (Integer.parseInt(row[16].toString()) > 0) ? "on" : "off";
			to.setDontShowMinMarks(value);

			value = (Integer.parseInt(row[17].toString()) > 0) ? "on" : "off";
			to.setDontConsiderFailureTotalResult(value);

			value = (Integer.parseInt(row[18].toString()) > 0) ? "on" : "off";
			to.setShowInternalFinalMarkAdded(value);

			value = (Integer.parseInt(row[19].toString()) > 0) ? "on" : "off";
			to.setShowOnlyGrade(value);

			to.setSubjectId(Integer.parseInt(row[0].toString()));

			to.setSubjectName(row[1].toString());
			to.setSubjectCode(row[2].toString());
			to.setId(Integer.parseInt(row[3].toString()));
			to.setSubjectOrder(Integer.parseInt(row[4].toString()));
			to.setUniversitySubjectCode(row[5].toString());
			to.setSubjectSectionId(Integer.parseInt(row[6].toString()));
			to.setSectionName(row[7].toString());
			to.setTheoryHours(Integer.parseInt(row[8].toString()));
			to.setTheoryCredit(Integer.parseInt(row[9].toString()));
			to.setPracticalHours(Integer.parseInt(row[10].toString()));

			to.setPracticalCredit(Integer.parseInt(row[11].toString()));
			listTO.add(to);

		}
		Collections.sort(listTO);
		return listTO;
	}

	public List<ExamSubDefinitionCourseWiseTO> convertBosToTOs(List<CertificateCourse> cerBoList) {
		ArrayList<ExamSubDefinitionCourseWiseTO> cerTolist=new ArrayList<ExamSubDefinitionCourseWiseTO>();
		Iterator<CertificateCourse> itr=cerBoList.iterator();
		while (itr.hasNext()) {
			CertificateCourse cerBo = (CertificateCourse) itr.next();
			ExamSubDefinitionCourseWiseTO cerTo=new ExamSubDefinitionCourseWiseTO();
			
			if(cerBo.getId()>0){
				cerTo.setId(cerBo.getId());				
			}
			if(cerBo.getId()>0){
				cerTo.setCerCourseId(Integer.toString(cerBo.getId()));				
			}
			if(cerBo.getCertificateCourseName()!=null && !cerBo.getCertificateCourseName().isEmpty()){
				cerTo.setCerCourseName(cerBo.getCertificateCourseName());				
			}
			if(cerBo.getSubject()!=null){
				cerTo.setSubjectId(cerBo.getSubject().getId());				
			}
			if(cerBo.getSubject()!=null){
				cerTo.setSubjectName(cerBo.getSubject().getCode()+"-"+cerBo.getSubject().getName());				
			}
			cerTolist.add(cerTo);
			
		}
		return cerTolist;
	}

}
