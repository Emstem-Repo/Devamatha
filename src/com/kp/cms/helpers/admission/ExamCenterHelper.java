package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admission.ExamCenterForm;
import com.kp.cms.handlers.admission.ExamCenterHandler;
import com.kp.cms.to.admission.ExamCenterTO;

	public class ExamCenterHelper {
		
			private static final Logger log = Logger.getLogger(ExamCenterHelper.class);	
			public static volatile ExamCenterHelper examCenterHelper = null;
			
			public static ExamCenterHelper getInstance(){
				if(examCenterHelper == null){
					examCenterHelper = new ExamCenterHelper();
					return examCenterHelper;
				}
				return examCenterHelper;
			}

			public List<ExamCenterTO> convertBOsToTOs(List<ExamCenter> examCenterBO) {
				List<ExamCenterTO> examCenterTOs= new ArrayList<ExamCenterTO>();
				Iterator<ExamCenter> iterator = examCenterBO.iterator();
				ExamCenterTO examCenterTO;
				while (iterator.hasNext()) {
					ExamCenter examCenter = (ExamCenter) iterator.next();
					examCenterTO=new ExamCenterTO();
					examCenterTO.setId(examCenter.getId());
					examCenterTO.setProgramName(examCenter.getProgram().getName());
					examCenterTO.setCenter(examCenter.getCenter());
					if(examCenter.getSeatNoFrom()!=null)
					examCenterTO.setSeatNoFrom(examCenter.getSeatNoFrom());
					if(examCenter.getSeatNoTo()!=null)
					examCenterTO.setSeatNoTo(examCenter.getSeatNoTo());
					if(examCenter.getSeatNoPrefix()!=null)
					examCenterTO.setSeatNoPrefix(examCenter.getSeatNoPrefix());
					examCenterTOs.add(examCenterTO);
				}
				return examCenterTOs;
			}

			public ExamCenter populateFormToBO(ExamCenterForm examCenterForm) {
				ExamCenter examCenter = new ExamCenter();
				Program program = new Program();
				if(examCenterForm.getProgramId()!=null){
					program.setId(Integer.parseInt(examCenterForm.getProgramId()));
					examCenter.setProgram(program);
				}
				if(examCenterForm.getId()>0){
					examCenter.setId(examCenterForm.getId());
				}
				examCenter.setCenter(examCenterForm.getCenter());
			//	if(examCenterForm.isSeatNoValidationRequired())
			//	{
				if(examCenterForm.getSeatNoFrom()!=null && !examCenterForm.getSeatNoFrom().isEmpty())
					examCenter.setSeatNoFrom(Integer.parseInt(examCenterForm.getSeatNoFrom()));
				if(examCenterForm.getSeatNoTo()!=null && !examCenterForm.getSeatNoTo().isEmpty())
					examCenter.setSeatNoTo(Integer.parseInt(examCenterForm.getSeatNoTo()));
				if(examCenterForm.getSeatNoPrefix()!=null && !examCenterForm.getSeatNoPrefix().isEmpty())
					examCenter.setSeatNoPrefix(examCenterForm.getSeatNoPrefix());
				if(examCenterForm.getCurrentSeatNo()!=null && !examCenterForm.getCurrentSeatNo().isEmpty())
					examCenter.setCurrentSeatNo(Integer.parseInt(examCenterForm.getCurrentSeatNo()));
			//	}
				examCenter.setAddress1(examCenterForm.getAddress1());
				examCenter.setAddress2(examCenterForm.getAddress2());
				examCenter.setAddress3(examCenterForm.getAddress3());
				examCenter.setAddress4(examCenterForm.getAddress4());
				examCenter.setIsActive(true);
				return examCenter;
			}

			public void setBOsToTOs(ExamCenterForm examCenterForm,ExamCenter examCenterBO) throws NumberFormatException, Exception {
				examCenterForm.setProgramId(Integer.toString(examCenterBO.getProgram().getId()));
				if(examCenterForm.getProgramId()!=null && !examCenterForm.getProgramId().isEmpty()){
				boolean isexamCentreRequired=ExamCenterHandler.getInstance().getExamCenterDefinedInProgram(Integer.parseInt(examCenterForm.getProgramId()));
				if(isexamCentreRequired)
					examCenterForm.setIsSeatNoValidationRequired("true");
				else
					examCenterForm.setIsSeatNoValidationRequired("false");
				}
				examCenterForm.setId(examCenterBO.getId());
				examCenterForm.setCenter(examCenterBO.getCenter());
				if(examCenterBO.getSeatNoFrom()!=null){
					examCenterForm.setSeatNoFrom(Integer.toString(examCenterBO.getSeatNoFrom()));
				}
				if(examCenterBO.getSeatNoPrefix()!=null){
					examCenterForm.setSeatNoPrefix(examCenterBO.getSeatNoPrefix());
				}
				if(examCenterBO.getSeatNoTo()!=null){
					examCenterForm.setSeatNoTo(Integer.toString(examCenterBO.getSeatNoTo()));
				}
				if(examCenterBO.getCurrentSeatNo()!=null){
					examCenterForm.setCurrentSeatNo(Integer.toString(examCenterBO.getCurrentSeatNo()));
				}
				examCenterForm.setAddress1(examCenterBO.getAddress1());
				examCenterForm.setAddress2(examCenterBO.getAddress2());
				if(examCenterBO.getAddress3()!=null || examCenterBO.getAddress4()!=null){
					examCenterForm.setAddress3(examCenterBO.getAddress3());
					examCenterForm.setAddress4(examCenterBO.getAddress4());
				}
				examCenterForm.setOrigCenter(examCenterBO.getCenter());
				examCenterForm.setOrigProgId(Integer.toString(examCenterBO.getProgram().getId()));
				examCenterForm.setOrigCreatedBy(examCenterBO.getCreatedBy());
				examCenterForm.setOrigCreatedDate(examCenterBO.getCreatedDate());
			}
	}
	
	
