package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.OpenInternalExamForClasses;
import com.kp.cms.bo.exam.OpenInternalMark;
import com.kp.cms.forms.exam.InternalMarksEntryForm;
import com.kp.cms.helpers.exam.InternalMarksEntryHelper;
import com.kp.cms.to.exam.InternalMarksEntryTO;
import com.kp.cms.transactionsimpl.exam.InternalMarksEntryImpl;

@SuppressWarnings("unchecked")
public class InternalMarksEntryHandler extends ExamGenHandler {

	InternalMarksEntryHelper helper = new InternalMarksEntryHelper();
	

	private static volatile InternalMarksEntryHandler handler = null;
	InternalMarksEntryImpl impl = InternalMarksEntryImpl.getInstance();
	private static final Log log = LogFactory.getLog(InternalMarksEntryHandler.class);
	/**
	 * return singleton object of examMarksEntryHandler.
	 * @return
	 */
	public static InternalMarksEntryHandler getInstance() {
		if (handler == null) {
			handler = new InternalMarksEntryHandler();
		}
		return handler;
	}

	/**
	 * @param objform 
	 * @return
	 * @throws Exception
	 */
	public void getCurrentExamDetails(InternalMarksEntryForm objform) throws Exception{
		List<OpenInternalExamForClasses> opendExamList = impl.getOpendExamDetails();
		List<Object[]> examList = impl.getCurrentExamDetails(objform);
		if(examList != null && !examList.isEmpty()){
			List<InternalMarksEntryTO> listOfDetails = helper.convertBoListTOTolist(examList);
			//helper.convertBOtoTO(examList,objform,"Theory",opendExamList);
			helper.convertBOtoTO1(listOfDetails,objform,"Theory",opendExamList);
		}
		/*objform.setExamDeatails(list);
		// for practical 
		List<InternalMarksEntryTO> practicalList = new ArrayList<InternalMarksEntryTO>();
		List<Object[]> practicalExamList = impl.getPracticalCurrentExamDetails(objform);
		if(practicalExamList != null && !practicalExamList.isEmpty()){
			practicalList = helper.convertBOtoTO(practicalExamList,objform,"Practical",opendExamList);
		}
		objform.setPracticalDeatails(practicalList);*/
	}
	/**
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveMarks(InternalMarksEntryForm objform) throws Exception {
		return impl.saveMarks(objform);
	}
	/**
	 * @param newExamMarksEntryForm
	 * @return
	 * @throws Exception
	 */
	public Double getMaxMarkOfSubject(InternalMarksEntryForm objform) throws Exception {
		return impl.getMaxMarkOfSubject(objform);
	}

	/**
	 * @param objform
	 * @throws Exception
	 */
	public void setDataTOForm(InternalMarksEntryForm objform) throws Exception{
		Map<Integer, String> teachersMap = impl.getTeachersMap(objform.getUserId());
		objform.setTeachersMap(teachersMap);
		objform.setTeacherId(null);
		objform.setForTeachers(true);
	}

	/**
	 * @param objform
	 * @throws Exception
	 */
	public void setRequiredDataToForm(InternalMarksEntryForm objform) throws Exception{
		List<OpenInternalExamForClasses> opendExamList = impl.getOpendExamDetails();
		if(opendExamList != null && !opendExamList.isEmpty()){
			List<Object[]> examList = impl.getCurrentExamDetailsForOtherTeacher(objform,"Teacher");
			if(examList != null && !examList.isEmpty()){
			//	helper.convertBOtoTO(examList,objform,"Theory",opendExamList);
				List<InternalMarksEntryTO> listOfDetails = helper.convertBoListTOTolist(examList);
				helper.convertBOtoTO1(listOfDetails,objform,"Theory",opendExamList);
			}else{
				objform.setExamMap(null);
				objform.setExamDeatails(null);
				objform.setPracticalDeatails(null);
			}
			/*objform.setExamDeatails(list);
			// for practical 
			List<InternalMarksEntryTO> practicalList = new ArrayList<InternalMarksEntryTO>();
			List<Object[]> practicalExamList = impl.getPracticalCurrentExamDetailsForOtherTeacher(objform);
			if(practicalExamList != null && !practicalExamList.isEmpty()){
				helper.convertBOtoTO(practicalExamList,objform,"Practical",opendExamList);
			}
			objform.setPracticalDeatails(practicalList);*/
		}
	}

	
	/**
	 * @param objform
	 * @throws Exception
	 */
	public void getInputDetails(InternalMarksEntryForm objform) throws Exception{
		Map<Integer, String> teachersMap = impl.getEmployees(objform.getUserId());
		objform.setEmployeeMap(teachersMap);			
	}
	/**
	 * @param objform
	 * @throws Exception
	 */
	public void setTeacherDataToForm(InternalMarksEntryForm objform) throws Exception{
		List<OpenInternalExamForClasses> opendExamList = impl.getOpendExamDetails();
		if(opendExamList != null && !opendExamList.isEmpty()){
			List<Object[]> examList = impl.getCurrentExamDetailsForOtherTeacher(objform,"HOD");
			if(examList != null && !examList.isEmpty()){
				List<InternalMarksEntryTO> listOfDetails = helper.convertBoListTOTolist(examList);
				//helper.convertBOtoTOForHODView(examList,objform,"Theory",opendExamList);
				helper.convertBOtoTOForHODView1(listOfDetails,objform,"Theory",opendExamList);
				objform.setHodView(true);
			}
		}
	}

}
