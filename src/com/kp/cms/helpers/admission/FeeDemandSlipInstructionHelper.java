package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admission.DemandSlipInstruction;
import com.kp.cms.forms.admission.FeeDemandSlipInstructionForm;
import com.kp.cms.to.admission.DemandSlipInstructionTO;

	public class FeeDemandSlipInstructionHelper {
		private static final Log log = LogFactory.getLog(FeeDemandSlipInstructionHelper.class);
		private static volatile FeeDemandSlipInstructionHelper feSlipInstructionHelper = null;
	
		public static FeeDemandSlipInstructionHelper getInstance() {
			if (feSlipInstructionHelper == null) {
				feSlipInstructionHelper = new FeeDemandSlipInstructionHelper();
			}
			return feSlipInstructionHelper;
		}

		public DemandSlipInstruction populateFormToBO(
				FeeDemandSlipInstructionForm feSlipInstructionForm) {
			DemandSlipInstruction deSlipInstruction = new DemandSlipInstruction();
			if(feSlipInstructionForm.getId()>0){
				deSlipInstruction.setId(feSlipInstructionForm.getId());
			}
			if(feSlipInstructionForm.getCourseId()!=null && !feSlipInstructionForm.getCourseId().isEmpty()){
				Course course = new Course();
				course.setId(Integer.parseInt(feSlipInstructionForm.getCourseId()));
				deSlipInstruction.setCourse(course);
			}
			if(feSlipInstructionForm.getSchemeNo()!=null && !feSlipInstructionForm.getSchemeNo().isEmpty()){
				deSlipInstruction.setSchemeNo(Integer.parseInt(feSlipInstructionForm.getSchemeNo()));
			}
			deSlipInstruction.setInstruction(feSlipInstructionForm.getInstruction());
			deSlipInstruction.setIsActive(true);
			return deSlipInstruction;
		}

		public void convertBOToForm(FeeDemandSlipInstructionForm feForm,
				DemandSlipInstruction deSlipInstruction) {
			feForm.setId(deSlipInstruction.getId());
			if(deSlipInstruction.getCourse()!=null){
				feForm.setCourseId(Integer.toString(deSlipInstruction.getCourse().getId()));
				feForm.setOldCourseId(Integer.toString(deSlipInstruction.getCourse().getId()));
			}
			if(deSlipInstruction.getSchemeNo()>0){
				feForm.setSchemeNo(Integer.toString(deSlipInstruction.getSchemeNo()));
				feForm.setOldSchemeNo(Integer.toString(deSlipInstruction.getSchemeNo()));
			}
			feForm.setInstruction(deSlipInstruction.getInstruction());
			feForm.setOldCreatedBy(deSlipInstruction.getCreatedBy());
			feForm.setOldCreatedDate(deSlipInstruction.getCreatedDate());
		}

		public List<DemandSlipInstructionTO> convertTOToBO(List<DemandSlipInstruction> deList) {
			List<DemandSlipInstructionTO> deTosList = new ArrayList<DemandSlipInstructionTO>();
			DemandSlipInstructionTO deSlipInstructionTO = null;
			Iterator<DemandSlipInstruction> iterator = deList.iterator();
			while (iterator.hasNext()) {
				DemandSlipInstruction demandSlipInstruction = (DemandSlipInstruction) iterator.next();
				deSlipInstructionTO = new DemandSlipInstructionTO();
				if(demandSlipInstruction.getId()>0){
					deSlipInstructionTO.setId(demandSlipInstruction.getId());
				}
				if(demandSlipInstruction.getCourse()!=null){
					deSlipInstructionTO.setCourseName(demandSlipInstruction.getCourse().getName());
				}
				if(demandSlipInstruction.getSchemeNo()>0){
					deSlipInstructionTO.setSchemeNo(demandSlipInstruction.getSchemeNo());
				}
				deSlipInstructionTO.setInstruction(demandSlipInstruction.getInstruction());
				deTosList.add(deSlipInstructionTO);
			}
			return deTosList;
		}
	
	}
