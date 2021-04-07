package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.phd.PhdCompletionDetailsBO;
import com.kp.cms.forms.phd.PhdCompletionDetailsForm;
import com.kp.cms.to.phd.PhdCompletionDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdCompletionDetailsHelper {
	
	private static final Log log = LogFactory.getLog(PhdCompletionDetailsHelper.class);
	public static volatile PhdCompletionDetailsHelper examCceFactorHelper = null;

	public static PhdCompletionDetailsHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PhdCompletionDetailsHelper();
		}
		return examCceFactorHelper;
	}
	
	public List<PhdCompletionDetailsTO> setdatatolist(List<Object[]> studentdetails, PhdCompletionDetailsForm objForm) {
		List<PhdCompletionDetailsTO> details=new ArrayList<PhdCompletionDetailsTO>();
		if(studentdetails!=null && !studentdetails.isEmpty()){
			Iterator itr=studentdetails.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				PhdCompletionDetailsTO PhdTo=new PhdCompletionDetailsTO();
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
				}if(object[9]!=null){
					objForm.setTitle(object[9].toString());
				}if(object[10]!=null){
					objForm.setDiscipline(object[10].toString());
				}if(object[7]!=null){
					objForm.setVivaVoice(object[7].toString());
				}else{
					objForm.setVivaVoice(null);
				}if(object[8]!=null){
					objForm.setId(Integer.parseInt(object[8].toString()));
					PhdTo.setId(Integer.parseInt(object[8].toString()));
				}
				details.add(PhdTo);
			}
		}
		return details;
	}
	
	public PhdCompletionDetailsBO convertFormToBos(PhdCompletionDetailsForm objForm) {
		
		PhdCompletionDetailsBO completionBo=new PhdCompletionDetailsBO();
		
			if(objForm.getId()!=0){
				completionBo.setId(objForm.getId());
			}
			Student student=new Student();
			Course course= new Course();
			
			student.setId(Integer.parseInt(objForm.getStudentId()));
			completionBo.setStudentId(student);
			course.setId(Integer.parseInt(objForm.getCourseId()));
			completionBo.setDiscipline(objForm.getDiscipline());
			completionBo.setTitle(objForm.getTitle());
			completionBo.setVivaVoice(CommonUtil.ConvertStringToDate(objForm.getVivaVoice()));
			completionBo.setCreatedBy(objForm.getUserId());
			completionBo.setCreatedDate(new Date());
			completionBo.setLastModifiedDate(new Date());
			completionBo.setModifiedBy(objForm.getUserId());
			completionBo.setIsActive(Boolean.valueOf(true));
			return completionBo;
   }

}
