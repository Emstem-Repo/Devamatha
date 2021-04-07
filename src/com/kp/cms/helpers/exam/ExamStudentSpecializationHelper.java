package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.to.exam.ExamStudentSpecializationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("unchecked")
public class ExamStudentSpecializationHelper extends ExamGenHelper {

	// To get the specialization list
	public ArrayList<KeyValueTO> convertBOToTO_SpecializationList(
			ArrayList<ExamSpecializationBO> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamSpecializationBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	// On - SEARCH
	public ArrayList<ExamStudentSpecializationTO> convertBOToTO_SpecializationStudent(
			ArrayList<ExamStudentBioDataBO> listBO) throws Exception {
		ExamStudentSpecializationTO to;
		ArrayList<ExamStudentSpecializationTO> retutnList = new ArrayList<ExamStudentSpecializationTO>();
		for (ExamStudentBioDataBO bo : listBO) {
			to = new ExamStudentSpecializationTO();
			to.setId(bo.getId());
			to.setRollNo(bo.getStudentUtilBO().getRollNo());
			to.setRegNo(bo.getStudentUtilBO().getRegisterNo());
			to.setAppNo(bo.getStudentUtilBO().getAdmApplnUtilBO().getApplnNo());
			String name = bo.getStudentUtilBO().getAdmApplnUtilBO()
					.getPersonalDataUtilBO().getFirstName();
			to.setStudentName(name);
			if(bo.getExamSpecializationBO()!= null){
				to.setSpecName(bo.getExamSpecializationBO().getName());
			}
			to.setStudentId(bo.getStudentId());
			//to.setSectionName(bo.getSectionName());
			to.setSectionName(bo.getStudentUtilBO().getClassSchemewiseUtilBO().getClassUtilBO().getSectionName());
			//to.setSchemeNo(Integer.parseInt(bo.getSchemeNo()));
			to.setSchemeNo(bo.getStudentUtilBO().getClassSchemewiseUtilBO().getCurriculumSchemeDurationUtilBO().getSemesterYearNo());
			retutnList.add(to);

		}
		Collections.sort(retutnList);
		return retutnList;
	}

	// On - ASSIGN
	public ArrayList<ExamStudentSpecializationTO> convertBOToTO_SpecializationNotAssignStudent(
			ArrayList<Object> listBO) throws Exception {
		ExamStudentSpecializationTO to;
		ArrayList<ExamStudentSpecializationTO> retutnList = new ArrayList<ExamStudentSpecializationTO>();
		Iterator itr = listBO.iterator();
		String rollNo = "";
		String regNo = "";
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[2] != null) {
				rollNo = row[2].toString();
			}
			if (row[3] != null) {
				regNo = row[3].toString();
			}
			String name = row[4].toString();
			if (row[5] != null) {
				name = name + " " + row[5].toString();
			}
			if (row[6] != null) {
				name = name + " " + row[6].toString();
			}
			to = new ExamStudentSpecializationTO(0, Integer.parseInt(row[1]
					.toString()), rollNo, regNo, Integer.parseInt(row[0]
					.toString()), name);

			retutnList.add(to);
		}
		Collections.sort(retutnList); 
		return retutnList;
	}

	
	//Code Written By Balaji
	public ArrayList<ExamStudentSpecializationTO> convertBOtoTO(
			ArrayList<StudentUtilBO> listBO) throws Exception {
		ExamStudentSpecializationTO to;
		ArrayList<ExamStudentSpecializationTO> retutnList = new ArrayList<ExamStudentSpecializationTO>();
		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			to=new ExamStudentSpecializationTO();
			StudentUtilBO bo = (StudentUtilBO) itr.next();
			to.setRollNo(bo.getRollNo());
			to.setRegNo(bo.getRegisterNo());
			to.setStudentId(bo.getId());
			String name =bo.getAdmApplnUtilBO().getPersonalDataUtilBO().getFirstName();
			if(bo.getAdmApplnUtilBO().getPersonalDataUtilBO().getMiddleName()!=null){
				name=name+" "+bo.getAdmApplnUtilBO().getPersonalDataUtilBO().getMiddleName();
			}
			if(bo.getAdmApplnUtilBO().getPersonalDataUtilBO().getLastName()!=null){
				name=name+" "+bo.getAdmApplnUtilBO().getPersonalDataUtilBO().getLastName();
			}
			to.setStudentName(name);
			Set<ExamStudentBioDataBO> studentBioDataSet=bo.getExamStudentBioDataBOSet();
			StringBuilder specializationBo=new StringBuilder();
			if(studentBioDataSet!=null && !studentBioDataSet.isEmpty()){
				Iterator<ExamStudentBioDataBO> itr1=studentBioDataSet.iterator();
				while (itr1.hasNext()) {
					ExamStudentBioDataBO examStudentBioDataBO = (ExamStudentBioDataBO) itr1.next();
					if(specializationBo.toString().isEmpty()){
						if(examStudentBioDataBO.getExamSpecializationBO()!=null)
						specializationBo.append(examStudentBioDataBO.getExamSpecializationBO().getName());
					}else{
						if(examStudentBioDataBO.getExamSpecializationBO()!=null)
						      specializationBo.append(",").append(examStudentBioDataBO.getExamSpecializationBO().getName());
					}
				}
			}
			to.setAppNo(bo.getAdmApplnUtilBO().getApplnNo());
			to.setSpecName(specializationBo.toString());
			retutnList.add(to);
		}
		Collections.sort(retutnList); 
		return retutnList;
	}
}
