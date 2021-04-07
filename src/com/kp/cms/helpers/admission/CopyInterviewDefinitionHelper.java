package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.admission.CopyInterviewDefinitionForm;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.transactionsimpl.admission.CopyInterviewDefinitionTransactionImpl;

public class CopyInterviewDefinitionHelper {
	/**
	 * Singleton object of CopyInterviewDefinitionHelper
	 */
	private static volatile CopyInterviewDefinitionHelper copyInterviewDefinitionHelper = null;
	private static final Log log = LogFactory.getLog(CopyInterviewDefinitionHelper.class);
	private CopyInterviewDefinitionHelper() {
		
	}
	/**
	 * return singleton object of CopyInterviewDefinitionHelper.
	 * @return
	 */
	public static CopyInterviewDefinitionHelper getInstance() {
		if (copyInterviewDefinitionHelper == null) {
			copyInterviewDefinitionHelper = new CopyInterviewDefinitionHelper();
		}
		return copyInterviewDefinitionHelper;
	}
	/**
	 * converting the interviewProgramCourseBO to InterviewProgramCOurseTO to display in the form
	 * @param interviewProgramCourse
	 * @return
	 * @throws Exception
	 */
	public List<InterviewProgramCourseTO> convertBOstoInterviewTOs(List<InterviewProgramCourse> interviewProgramCourse) throws Exception {

		List<InterviewProgramCourseTO> intlist = new ArrayList<InterviewProgramCourseTO>();
		if(interviewProgramCourse!=null && !interviewProgramCourse.isEmpty()){
		Iterator<InterviewProgramCourse> iter = interviewProgramCourse.iterator();
		InterviewProgramCourseTO intTO;
		while (iter.hasNext()) {
			InterviewProgramCourse interview = (InterviewProgramCourse) iter.next();
			intTO = new InterviewProgramCourseTO();
			
			if(interview.getProgram()!=null && interview.getProgram().getProgramType()!=null){
			intTO.setProgramTypeName(interview.getProgram().getProgramType().getName());
			}
			if(interview.getProgram()!=null){
				intTO.setProgramName(interview.getProgram().getName());
				intTO.setProgramId(interview.getProgram().getId());
			}
			
			if(interview.getCourse()!=null){
				intTO.setCourseName(interview.getCourse().getName());
				intTO.setCourseId(interview.getCourse().getId());
			}
			if(interview.getName()!=null && !interview.getName().isEmpty()){
				intTO.setName(interview.getName());
			}
			if(interview.getSequence()!=null && !interview.getSequence().isEmpty()){
				intTO.setSequence(interview.getSequence());
			}
			if(interview.getNoOfInterviewsPerPanel()!=null && interview.getNoOfInterviewsPerPanel()>0){
				intTO.setInterviewsPerPanel(String.valueOf(interview.getNoOfInterviewsPerPanel()));
			}
			String year1 = String.valueOf(interview.getYear() + 1);
			intTO.setCombinedYear(String.valueOf(interview.getYear())+"-"+year1);
			intTO.setYear(interview.getYear());
			intlist.add(intTO);
		}
	
		}
		return intlist;
	}
	/**
	 * Converting the interviewProgramCourseTO obtained from form to InterviewProgramCourse BO
 	 * @param copyForm
	 * @return
	 * @throws Exception
	 */
	public List<InterviewProgramCourse> convertTOToBO(CopyInterviewDefinitionForm copyForm) throws Exception{

		List<InterviewProgramCourse> intPrgCourseBOList= new ArrayList<InterviewProgramCourse>();
		List<InterviewProgramCourseTO> dispList = copyForm.getDisplayInterviewDefinition();
		for(InterviewProgramCourseTO disp:dispList){
			if(disp.isIntDefinitionSel())
			{
				InterviewProgramCourse intPrgCourse = new InterviewProgramCourse();
						Course course = new Course();
						Program program = new Program();
						int progId= disp.getProgramId();
						int courseId= disp.getCourseId();
						int year=Integer.parseInt(copyForm.getToYear());
						String query=" from InterviewProgramCourse interviewProgramCourse where interviewProgramCourse.course.id = " +courseId+
								" and interviewProgramCourse.sequence = "+disp.getSequence()+" and interviewProgramCourse.year = "+year;
						
						boolean isDup;
						isDup = CopyInterviewDefinitionTransactionImpl.getInstance().checkDuplicate(query);
						if(!isDup){
						course.setId(courseId);
						intPrgCourse.setCourse(course);
						program.setId(progId);	
						intPrgCourse.setProgram(program);
						intPrgCourse.setYear(year);
						intPrgCourse.setIsActive(true);
						intPrgCourse.setCreatedBy(copyForm.getUserId());
						intPrgCourse.setCreatedDate(new Date());
						intPrgCourse.setModifiedBy(copyForm.getUserId());
						intPrgCourse.setLastModifiedDate(new Date());
						intPrgCourse.setName(disp.getName());
						intPrgCourse.setSequence(disp.getSequence());
						intPrgCourse.setNoOfInterviewsPerPanel(Integer.parseInt(disp.getInterviewsPerPanel()));
						
						intPrgCourseBOList.add(intPrgCourse);
						}
					
				
			}
		}
		return intPrgCourseBOList;
	}
	
}
