package com.kp.cms.helpers.exam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.util.StringTokenizer;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.exam.ClassSchemewiseUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionProgramBO;
import com.kp.cms.bo.exam.ExamPublishHallTicketMarksCardBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamPublishHallTicketForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.SendSmsToClassHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.to.exam.ExamPublishHallTicketTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamPublishHallTicketImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;
import com.kp.cms.utilities.PropertyUtil;

@SuppressWarnings("unchecked")
public class ExamPublishHallTicketHelper extends ExamGenHelper {
	private static final Log log = LogFactory.getLog(ExamPublishHallTicketHelper.class);
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	ExamPublishHallTicketImpl impl = new ExamPublishHallTicketImpl();

	// To get Exam Type List
	public ArrayList<KeyValueTO> convertBOToTO_ExamType(ArrayList<ExamTypeUtilBO> listBO,ExamPublishHallTicketForm objform) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamTypeUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
			// setting examType property in form
			if((objform.getExamType()==null || objform.getExamType().isEmpty()) && bo.getName().equalsIgnoreCase("Regular")){
				objform.setExamType(String.valueOf(bo.getId()));
			}
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	// To get the Main Grid

	public ArrayList<ExamPublishHallTicketTO> convertBOToTO_mainList(
			List<Object> lbo) throws Exception {
		ArrayList<ExamPublishHallTicketTO> listTO = new ArrayList<ExamPublishHallTicketTO>();

		Iterator it = lbo.iterator();
		ExamPublishHallTicketMarksCardBO bo = null;
		ExamPublishHallTicketTO to = null;
		while (it.hasNext()) {
			bo = (ExamPublishHallTicketMarksCardBO) it.next();

			to = new ExamPublishHallTicketTO();
			if (bo.getId() != 0) {
				to.setId(bo.getId());
			}

			if (bo.getExamId() != 0) {
				to.setExamId(bo.getExamId());
			}
			if (bo.getAgreementId() != null) {
				to.setAgreementId(bo.getAgreementId());

			}
			if (bo.getFooterId() != null) {
				to.setFooterId(bo.getFooterId());
			}
			if (bo.getClassId() != 0) {
				to.setClassId(bo.getClassId());
			}
			if (bo.getPublishFor() != null) {
				to.setPublishFor(bo.getPublishFor());
			}

			if (bo.getDownloadStartDate() != null) {
				to.setDownloadStartDate(CommonUtil.formatSqlDate(bo
						.getDownloadStartDate().toString()));

			}
			if (bo.getDownloadEndDate() != null) {
				to.setDownloadEndDate(CommonUtil.formatSqlDate(bo
						.getDownloadEndDate().toString()));

			}
			to.setProgramType(bo.getExamProgramTypeUtilBO().getProgramType());
			int year=0;
			if(bo.getClassUtilBO()!= null){
				if(bo.getClassUtilBO().getClassSchemewiseUtilBOSet()!=null){
					Set<ClassSchemewiseUtilBO> classSchemewiseUtilBOsSet = bo.getClassUtilBO().getClassSchemewiseUtilBOSet();
					Iterator<ClassSchemewiseUtilBO> iterator = classSchemewiseUtilBOsSet.iterator();
					while (iterator.hasNext()) {
						ClassSchemewiseUtilBO classSchemewiseUtilBO = (ClassSchemewiseUtilBO) iterator.next();
						if(classSchemewiseUtilBO.getCurriculumSchemeDurationUtilBO()!=null){
							year = classSchemewiseUtilBO.getCurriculumSchemeDurationUtilBO().getCurriculumSchemeUtilBO().getYear();
						}
					}
				}
			}
			to.setClasscode(bo.getClassUtilBO().getName()+"("+year+")");
			to.setExamName(bo.getExamDefinitionBO().getName());
			to.setExamType(bo.getExamDefinitionBO().getExamTypeUtilBO()
					.getName());
			if(bo.getRevaluationEndDate()!=null)
			to.setRevaluationEndDate(CommonUtil.formatSqlDate(bo.getRevaluationEndDate().toString()));

			listTO.add(to);
		}
		Collections.sort(listTO);
		return listTO;
	}

	public ExamPublishHallTicketTO getPublishDetail(Object publishDetails) throws Exception {
		ExamPublishHallTicketTO to = (ExamPublishHallTicketTO) publishDetails;
		ExamPublishHallTicketMarksCardBO lbo = new ExamPublishHallTicketMarksCardBO();
		to.setClassId(lbo.getClassId());
		return to;
	}

	public ExamPublishHallTicketForm convertBOToForm(
			ExamPublishHallTicketMarksCardBO objBO,
			ExamPublishHallTicketForm objform) throws Exception {
		objform.setId(objBO.getId());

		return objform;
	}

	// On - EDIT to set the values to form
	public ExamPublishHallTicketForm createFormObjcet(
			ExamPublishHallTicketForm form,
			ExamPublishHallTicketMarksCardBO objBO,
			List<KeyValueTO> programTypeList) throws Exception {
		ExamPublishHallTicketHandler handler = new ExamPublishHallTicketHandler();

		if (objBO.getId() != null)
			form.setId(objBO.getId());

		int examTypeId = objBO.getExamDefinitionBO().getExamTypeUtilBO()
				.getId();
		if (examTypeId != 0)
			form.setExamType(Integer.toString(examTypeId));
		form.setListExamtype(handler.getExamTypeList(form));

		int examNameId = objBO.getExamDefinitionBO().getId();
		form.setExamName(Integer.toString(objBO.getExamDefinitionBO().getId()));
		int year=objBO.getExamDefinitionBO().getAcademicYear();
		form.setYear(String.valueOf(year));
	//	form.setListExamName(new ExamTimeTableHandler().getExamName(examTypeId));
// added by Smitha
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(String.valueOf(examTypeId),year); 
		form.setExamNameMap(examMap);
		int programId = 0;

		Set<ExamDefinitionProgramBO> programDefinitionSet = objBO
				.getExamDefinitionBO().getExamDefinitionProgramBOSet();
		Iterator<ExamDefinitionProgramBO> itr = programDefinitionSet.iterator();
		while (itr.hasNext()) {
			ExamDefinitionProgramBO eDPBO = (ExamDefinitionProgramBO) itr
					.next();
			programId = eDPBO.getExamProgramUtilBO().getProgramID();
		}

		if (programId != 0)
			form.setProgramType(Integer.toString(objBO
					.getExamProgramTypeUtilBO().getId()));
		form.setProgramTypeList(handler.getProgramTypeList());
		
		if(form.getProgramType()!=null && !form.getProgramType().isEmpty())
		form.setMapClass(ExamGenHandler.getInstance().getclassesMap(form.getExamName(), form.getExamType(), form.getProgramTypeId(), form.getDeanaryName()));

		String[] classids = { Integer.toString(objBO.getClassId()) };
		form.setClassCodeIdsTo(classids);

		Map<Integer, String> mapClas = form.getMapClass();
		mapClas.remove(objBO.getClassId());
		form.setMapClass(mapClas);

		Map<Integer, String> mapSelectedClass = new HashMap<Integer, String>();
		ClassSchemewise c = ((ClassSchemewise) impl.getClassNameYearById(objBO.getClassId(),
				objBO.getClassUtilBO().getClass()));
		if (c != null)
		mapSelectedClass.put(objBO.getClassId(), c.getClasses().getName()+"("+c.getCurriculumSchemeDuration().getCurriculumScheme().getYear()+")");
		form.setMapSelectedClass(mapSelectedClass);

		ArrayList<Integer> classidList = new ArrayList<Integer>();
		classidList.add(objBO.getClassId());
		if (objBO.getAgreementId() != null) {

			form.setAgreementName(Integer.toString(objBO.getAgreementId()));
		}else
			form.setAgreementName(null);
		if (objBO.getFooterId() != null) {

			form.setFooterName(Integer.toString(objBO.getFooterId()));
		}else
			form.setFooterName(null);
		form.setMapFooter(handler.getFooterListByClassId(classidList));

		form.setPublishFor(objBO.getPublishFor());
		form.setPublishForList(handler.getPublishForList());
		form.setProgramTypeId(form.getProgramType());
		
		if(form.getProgramType()!=null && !form.getProgramType().isEmpty()){
		form.setFooterList(handler.getFooterListByProgramTypeId(Integer.parseInt(form.getProgramType())));
		form.setAgreementList(handler.getAgreementByProgramTypeId(Integer.parseInt(form.getProgramType())));
		}
		form.setDownLoadStartDate(CommonUtil.formatSqlDate(objBO
				.getDownloadStartDate().toString()));
		form.setDownLoadEndDate(CommonUtil.formatSqlDate(objBO
				.getDownloadEndDate().toString()));
		form.setExamCenterCode(objBO.getExamCenterCode());
		form.setRevaluationEndDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(objBO.getRevaluationEndDate()), ExamPublishHallTicketHelper.SQL_DATEFORMAT,ExamPublishHallTicketHelper.FROM_DATEFORMAT));
		form.setExamCenter(objBO.getExamCenter());
		return form;
	}

	// On - EDIT (To retain all values)
	public ExamPublishHallTicketForm createFormObjcet(
			ExamPublishHallTicketForm form, List<KeyValueTO> programTypeList) throws Exception {
		ExamPublishHallTicketHandler handler = new ExamPublishHallTicketHandler();

		/*if (form.getExamType() != null && form.getExamType().length() > 0) {
			form.setListExamName(new ExamTimeTableHandler().getExamName(Integer
					.parseInt(form.getExamType())));

		} else {
			form.setListExamName(new ArrayList<KeyValueTO>());
		}*/
		if (form.getExamName() != null && form.getExamName().length() > 0 && form.getProgramTypeId()!=null && !form.getProgramTypeId().isEmpty()) {
			//by giri
			/*form.setMapClass(handler.getClassCodeByExamNameProgramType(Integer
					.parseInt(form.getExamName()),Integer.parseInt(form.getProgramTypeId()),form.getDeanaryName()));*/
			form.setMapClass(ExamGenHandler.getInstance().getclassesMap(form.getExamName(), form.getExamType(), form.getProgramTypeId(), form.getDeanaryName()));
			//end by giri
		} else {
			form.setMapClass(new HashMap<Integer, String>());
		}
		form.setPublishForList(handler.getPublishForList());

		if (form.getProgramTypeId() != null && !form.getProgramTypeId().isEmpty()) {
			form.setFooterList(handler.getFooterListByProgramTypeId(Integer
					.parseInt(form.getProgramTypeId())));
		}

		if (form.getProgramTypeId() != null && !form.getProgramTypeId().isEmpty()) {
			form.setAgreementList(handler.getAgreementByProgramTypeId(Integer
					.parseInt(form.getProgramTypeId())));
		}
		Map<Integer, String> mapSelectedClass = new HashMap<Integer, String>();
		String classIds[] = form.getStayClass();
		if (classIds != null && classIds.length > 0)
			for (String classId : classIds) {
				if (classId != null) {
					StringTokenizer tokens = new StringTokenizer(classId, ",");
					while (tokens.hasMoreElements()) {
						try{
						int val = Integer.parseInt(tokens.nextElement().toString());
						mapSelectedClass.put(val, handler.getClassNameById(val));
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}

		form.setMapSelectedClass(mapSelectedClass);
		return form;
	}
	
	
	
	public boolean sendSMSToStudent(Set<ExamPublishHallTicketTO> studentMobileNos,ExamPublishHallTicketForm objform,String examType) throws Exception{
		// TODO Auto-generated method stub
		log.info("Entered SendSmsToClassHandler-sendSMSToStudent");
		Properties prop = new Properties();
		try {
			InputStream in = SendSmsToClassHandler.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
		
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		
		List<SMSTemplate> list=null;
		String desc="";
		int coiuseId=0;
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		if(examType.equalsIgnoreCase("Regular")){
			if(objform.getPublishFor().equalsIgnoreCase("Hall Ticket")){
			 list= temphandle.getDuplicateCheckList(coiuseId,CMSConstants.PUBLIS_REGULAR_EXAM_HALL_TICKET);
			}else if(objform.getPublishFor().equalsIgnoreCase("Marks Card")){
			 list= temphandle.getDuplicateCheckList(CMSConstants.PUBLIS_REGULAR_EXAM_MARKS_CARD);
			}
		}else if(examType.equalsIgnoreCase("Supplementary")){
			if(objform.getPublishFor().equalsIgnoreCase("Marks Card")){
					list= temphandle.getDuplicateCheckList(coiuseId,CMSConstants.PUBLIS_SUPPLEMENTARY_EXAM_MARKS_CARD);
			}
		}
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		if(studentMobileNos!=null && !studentMobileNos.isEmpty()){
			Iterator<ExamPublishHallTicketTO> itr=studentMobileNos.iterator();
			while (itr.hasNext()) {
				ExamPublishHallTicketTO to = (ExamPublishHallTicketTO) itr.next();
				
				String mobileNo="";
				if(to.getMobileNo1()!=null && !to.getMobileNo1().isEmpty()){
					if(to.getMobileNo1().trim().equals("0091") || to.getMobileNo1().trim().equals("+91")
							|| to.getMobileNo1().trim().equals("091") || to.getMobileNo1().trim().equals("0"))
						mobileNo = "91";
					else
						mobileNo=to.getMobileNo1();
				}else{
					mobileNo="91";
				}
				mobileNo=mobileNo+to.getMobileNo2();
				if(mobileNo !=null &&  !mobileNo.isEmpty() && StringUtils.isNumeric(mobileNo) && mobileNo.length()==12 && 
						desc!=null && !desc.isEmpty() && desc.length()<=160 ){
					
						MobileMessaging mob=new MobileMessaging();
						mob.setDestinationNumber(mobileNo);
						mob.setMessageBody(desc);
						mob.setMessagePriority(3);
						mob.setSenderName(senderName);
						mob.setSenderNumber(senderNumber);
						mob.setMessageEnqueueDate(new Date());
						mob.setIsMessageSent(false);
						smsList.add(mob);
					
				}
			}
		}
		log.info("Exists SendSmsToClassHandler-sendSMSToStudent");
		return PropertyUtil.getInstance().saveSMSList(smsList);
	}

}
