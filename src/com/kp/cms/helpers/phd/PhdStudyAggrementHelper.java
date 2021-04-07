package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.bo.phd.PhdStudyAggrementBO;
import com.kp.cms.forms.phd.PhdStudyAggrementForm;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdGuideDetailsTO;
import com.kp.cms.to.phd.PhdStudyAggrementTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdStudyAggrementHelper {
	private static final Log log = LogFactory.getLog(PhdStudyAggrementHelper.class);
	public static volatile PhdStudyAggrementHelper examCceFactorHelper = null;

	public static PhdStudyAggrementHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PhdStudyAggrementHelper();
		}
		return examCceFactorHelper;
	}


	/**
	 * @param guideBOs
	 * @return
	 */
	public List<PhdGuideDetailsTO> convertBOsToTO(List<PhdGuideDetailsBO> guideBOs) {
		List<PhdGuideDetailsTO> guideDetailsTOList=new ArrayList<PhdGuideDetailsTO>();
	    Iterator<PhdGuideDetailsBO> iterator=guideBOs.iterator();
	    while(iterator.hasNext())
	    {
	    	PhdGuideDetailsBO guideDetailsBO=(PhdGuideDetailsBO) iterator.next();
	    	PhdGuideDetailsTO guideDetailsTO=new PhdGuideDetailsTO();
	    	guideDetailsTO.setId(guideDetailsBO.getId());
	    	guideDetailsTO.setName(guideDetailsBO.getName());
	    	guideDetailsTO.setEmpanelmentNo(guideDetailsBO.getEmpanelmentNo());
	    	guideDetailsTOList.add(guideDetailsTO);
	    }
		return guideDetailsTOList;
     }
	/**
	 * @param studentdetails
	 * @param objForm
	 * @return
	 */
	public List<PhdStudyAggrementTO> setdatatolist(List<Object[]> studentdetails, PhdStudyAggrementForm objForm) {
		List<PhdStudyAggrementTO> details=new ArrayList<PhdStudyAggrementTO>();
		if(studentdetails!=null && !studentdetails.isEmpty()){
			Iterator itr=studentdetails.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				PhdStudyAggrementTO PhdTo=new PhdStudyAggrementTO();
				if(object[0]!=null && object[1]!=null && object[2]!=null){
					objForm.setStudentName(object[0].toString()+" "+object[1].toString()+""+object[2].toString());
				}if(object[0]!=null &&  object[2]!=null){
					objForm.setStudentName(object[0].toString()+""+object[2].toString());
				}if(object[0]!=null &&  object[2]==null){
					objForm.setStudentName(object[0].toString());
				}if(object[3]!=null){
					objForm.setStudentId(object[3].toString());
				}if(object[4]!=null){
					objForm.setBatch(object[4].toString());
				}if(object[5]!=null){
					objForm.setCourseName(object[5].toString());
				}if(object[6]!=null){
					objForm.setCourseId(object[6].toString());
				}if(object[7]!=null){
					objForm.setGuideId(object[7].toString());
				}if(object[8]!=null){
					objForm.setGuideEmpaneNo(object[8].toString());
				}if(object[9]!=null){
					objForm.setCoGuideId(object[9].toString());
				}if(object[10]!=null){
					objForm.setCoGuideEmpaneNo(object[10].toString());
				}if(object[11]!=null){
					objForm.setSignedOn(object[11].toString());
				}else{
					objForm.setSignedOn(null);
				}if(object[12]!=null){
					objForm.setId(Integer.parseInt(object[12].toString()));
					PhdTo.setId(Integer.parseInt(object[12].toString()));
				}
				details.add(PhdTo);
			}
		}
		return details;
	}
	/**
	 * @param objForm
	 * @return
	 */
	public PhdStudyAggrementBO convertFormToBos(PhdStudyAggrementForm objForm) {
		PhdStudyAggrementBO aggrementBo=new PhdStudyAggrementBO();
		
			if(objForm.getId()!=0){
			aggrementBo.setId(objForm.getId());
			}if(objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty() || objForm.getCoGuideId()!=null && !objForm.getCoGuideId().isEmpty()){
			Student student=new Student();
			Course course= new Course();
			PhdGuideDetailsBO guide=new PhdGuideDetailsBO();
			PhdGuideDetailsBO coguide=new PhdGuideDetailsBO();
			
			student.setId(Integer.parseInt(objForm.getStudentId()));
			aggrementBo.setStudentId(student);
			course.setId(Integer.parseInt(objForm.getCourseId()));
			aggrementBo.setCourseId(course);
			if(objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty()){
				guide.setId(Integer.parseInt(objForm.getGuideId()));
				aggrementBo.setGuideId(guide);
			}if(objForm.getCoGuideId()!=null && !objForm.getCoGuideId().isEmpty()){
				coguide.setId(Integer.parseInt(objForm.getCoGuideId()));
				aggrementBo.setCoGuideId(coguide);
			}
			
			aggrementBo.setSignedOn(CommonUtil.ConvertStringToDate(objForm.getSignedOn()));
			aggrementBo.setCreatedBy(objForm.getUserId());
			aggrementBo.setCreatedDate(new Date());
			aggrementBo.setLastModifiedDate(new Date());
			aggrementBo.setModifiedBy(objForm.getUserId());
			aggrementBo.setIsActive(Boolean.valueOf(true));
			return aggrementBo;
			}
			return null;
			
   }
	/**
	 * @param guideList
	 * @return
	 */
	public List<PhdDocumentSubmissionScheduleTO> copyBotoToguide(List<Object[]> guideList) {
		log.debug("Helper : Entering copyBotoTo");
		List<PhdDocumentSubmissionScheduleTO> guideToList = new ArrayList<PhdDocumentSubmissionScheduleTO>();
		Iterator<Object[]> itr = guideList.iterator();
	while (itr.hasNext()) {
		Object[] object = (Object[]) itr.next();
		PhdDocumentSubmissionScheduleTO PhdTo=new PhdDocumentSubmissionScheduleTO();
		if(object[0]!=null){
			PhdTo.setRegisterNo(object[0].toString());
		}if(object[1]!=null && object[2]!=null && object[3]!=null){
			PhdTo.setStudentName(object[1].toString()+" "+object[2].toString()+""+object[3].toString());
		}if(object[1]!=null &&  object[3]!=null){
			PhdTo.setStudentName(object[1].toString()+""+object[3].toString());
		}if(object[1]!=null &&  object[3]==null){
			PhdTo.setStudentName(object[1].toString());
		}if(object[4]!=null){
			PhdTo.setCourseName(object[4].toString());
		}if(object[5]!=null){
			PhdTo.setDocumentName(object[5].toString());
		}if(object[6]!=null){
			PhdTo.setSubmittedDate(object[6].toString());
		}if(object[7]!=null){
			PhdTo.setGuide(object[7].toString());
		}if(object[8]!=null){
			PhdTo.setCoGuide(object[8].toString());
		}
		if(object[9]!=null){
			PhdTo.setStudentId(Integer.parseInt(object[9].toString()));
		}if(object[10]!=null){
			PhdTo.setCourseId(object[10].toString());
		}if(object[11]!=null){
			PhdTo.setDocumentId(object[11].toString());
		}if(object[12]!=null){
		//	PhdTo.setChecked(null);
		//	PhdTo.setTempChecked("on");
			PhdTo.setPrintornot("Yes");
		}else{
			PhdTo.setPrintornot("No");
		}
		guideToList.add(PhdTo);
	}
		log.debug("Helper : Leaving copyBotoTo");
	   return guideToList;
	}

}
