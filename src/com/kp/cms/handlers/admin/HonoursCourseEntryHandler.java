package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.HonoursCourse;
import com.kp.cms.bo.admin.HonoursCourseApplication;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.forms.admin.HonoursCourseEntryForm;
import com.kp.cms.helpers.admin.HonoursCourseEntryHelper;
import com.kp.cms.helpers.exam.ConsolidatedMarksCardHelper;
import com.kp.cms.to.admin.HonoursCourseEntryTo;
import com.kp.cms.transactions.admin.IHonoursCourseEntryTransaction;
import com.kp.cms.transactions.exam.IConsolidatedMarksCardTransaction;
import com.kp.cms.transactionsimpl.admin.HonoursCourseEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.ConsolidatedMarksCardTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HonoursCourseEntryHandler {
	private static final Log log = LogFactory.getLog(HonoursCourseEntryHandler.class);
	public static volatile HonoursCourseEntryHandler courseEntryHandler = null;
	public static HonoursCourseEntryHandler getInstance(){
		if(courseEntryHandler == null){
			courseEntryHandler = new HonoursCourseEntryHandler();
			return courseEntryHandler;
		}
		return courseEntryHandler;
	}
	IHonoursCourseEntryTransaction transaction = HonoursCourseEntryTransactionImpl.getInstance();
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseMapDetails() throws Exception{
		Map<Integer,String> courseMap = transaction.getCourseMapDetails();
		courseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(courseMap);
		return courseMap;
	}
	/**
	 * @param honoursCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public List<HonoursCourseEntryTo> getHonoursCourseList() throws Exception{
		List<HonoursCourse> honoursCourses = transaction.getHonoursCourseList();
		List<HonoursCourseEntryTo> tos = HonoursCourseEntryHelper.getInstance().convertBOListToTOList(honoursCourses);
		return tos;
	}
	/**
	 * @param honoursCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateResult( HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		boolean isDuplicate = transaction.getDuplicateResult(honoursCourseEntryForm);
		return isDuplicate;
	}
	/**
	 * @param honoursCourseEntryForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addHonoursCourseEntry( HonoursCourseEntryForm honoursCourseEntryForm, String mode) throws Exception{
		HonoursCourse honoursCourse = HonoursCourseEntryHelper.getInstance().convertFormToTO(honoursCourseEntryForm,mode);
		boolean isAdded = transaction.saveHonoursCourse(honoursCourse,mode);
		return isAdded;
	}
	/**
	 * @param honoursCourseEntryForm
	 * @throws Exception
	 */
	public void editHonoursCourse(HonoursCourseEntryForm honoursCourseEntryForm)throws Exception {
		HonoursCourse honoursCourse = transaction.editHonoursCourse(honoursCourseEntryForm.getId());
		honoursCourseEntryForm.setHonoursCourseId(String.valueOf(honoursCourse.getHonoursCourse().getId()));
		honoursCourseEntryForm.setEligibleCourseId(String.valueOf(honoursCourse.getEligibleCourse().getId()));
		honoursCourseEntryForm.setOrgHonoursCourseId(String.valueOf(honoursCourse.getHonoursCourse().getId()));
		honoursCourseEntryForm.setOrgEligibleCourseId(String.valueOf(honoursCourse.getEligibleCourse().getId()));
	}
	/**
	 * @param id
	 * @param activate
	 * @param honoursCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteHonoursCourse(int id, boolean activate, HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		boolean isDeleted = transaction.deleteHonoursCourse(id,activate,honoursCourseEntryForm);
		return isDeleted;
	}
	/**
	 * @param courseId 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getHonoursCourseMap(String courseId) throws Exception{
		return transaction.getHonoursCourseMap(courseId);
	}
	/**
	 * @param honoursCourseEntryForm
	 * @return
	 */
	public Map<Integer, HonoursCourseEntryTo> getDetailsForInput(HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		Map<Integer, HonoursCourseEntryTo> map = new HashMap<Integer, HonoursCourseEntryTo>();
		if(honoursCourseEntryForm.getStudentId() != null && !honoursCourseEntryForm.getStudentId().trim().isEmpty()){
			List<Object[]> attendanceData = transaction.getAttendancePercentage(Integer.parseInt(honoursCourseEntryForm.getStudentId()));
			Map<Integer, String> attendanceMap = HonoursCourseEntryHelper.getInstance().getAttendanceMap(attendanceData);
			List<Integer> honourSubjectIds = transaction.getSubjectList(honoursCourseEntryForm.getEligibleCourseId());
			String query  = HonoursCourseEntryHelper.getInstance().getMarksQuery(Integer.parseInt(honoursCourseEntryForm.getStudentId()));
			List<Object[]> marksList = transaction.getDataForQuery(query);
			Map<Integer,Boolean> certificateMap=transaction.getCertificateMap(honoursCourseEntryForm.getStudentId());
			map = HonoursCourseEntryHelper.getInstance().getAcademicDetails(marksList,honoursCourseEntryForm,honourSubjectIds,attendanceMap,certificateMap);
		}
		return map;
	}
	/**
	 * @param honoursCourseEntryForm
	 * @throws Exception
	 */
	public void setStudentDetailsToForm(HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		Student student = transaction.getStudentDetails(honoursCourseEntryForm.getStudentId());
		if(student != null){
			honoursCourseEntryForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			honoursCourseEntryForm.setRegNo(student.getRegisterNo());
			honoursCourseEntryForm.setCombination(student.getAdmAppln().getCourseBySelectedCourseId().getCode());
			honoursCourseEntryForm.setMobileNo(student.getAdmAppln().getPersonalData().getMobileNo2());
			honoursCourseEntryForm.setEmailId(student.getAdmAppln().getPersonalData().getEmail());
			honoursCourseEntryForm.setGender(student.getAdmAppln().getPersonalData().getGender());
			honoursCourseEntryForm.setContactNo(student.getAdmAppln().getPersonalData().getPhNo2()+"-"+student.getAdmAppln().getPersonalData().getPhNo3());
			honoursCourseEntryForm.setPermanentAdd(student.getAdmAppln().getPersonalData().getPermanentAddressLine1()
					+", "+student.getAdmAppln().getPersonalData().getPermanentAddressLine2()+", "+student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId());
			honoursCourseEntryForm.setPresentAdd(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()
					+", "+student.getAdmAppln().getPersonalData().getCurrentAddressLine2()+", "+student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId());
			honoursCourseEntryForm.setAcademicYear(String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()));
			honoursCourseEntryForm.setSemister(String.valueOf(student.getClassSchemewise().getClasses().getTermNumber()));
			honoursCourseEntryForm.setStudentId(String.valueOf(student.getId()));
		}
	}
	/**
	 * @param honoursCourseEntryForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, HonoursCourseEntryTo> getDetails(HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		Map<Integer, HonoursCourseEntryTo> map = new HashMap<Integer, HonoursCourseEntryTo>();
		if(honoursCourseEntryForm.getStudentId() != null && !honoursCourseEntryForm.getStudentId().trim().isEmpty()){
			List<Object[]> attendanceData = transaction.getAttendancePercentage(Integer.parseInt(honoursCourseEntryForm.getStudentId()));
			Map<Integer, String> attendanceMap = HonoursCourseEntryHelper.getInstance().getAttendanceMap(attendanceData);
			List<Integer> honourSubjectIds = transaction.getSubjectList(honoursCourseEntryForm.getEligibleCourseId());
			String consolidateQuery=HonoursCourseEntryHelper.getInstance().getMarksCardQuery(Integer.parseInt(honoursCourseEntryForm.getStudentId()));
			List<Object[]> list=transaction.getDataForQuery(consolidateQuery);
			String certificateCourseQuery="select subject_id,is_optional,scheme_no,student_id from student_certificate_course  where is_cancelled=0 and  student_id ="+honoursCourseEntryForm.getStudentId();
			List certificateList=transaction.getDataForQuery(certificateCourseQuery);
			Map<Integer,Map<Integer,Map<Integer,Boolean>>> certificateMap=getCertificateSubjectMap(certificateList);
			List<String> appearedList=transaction.getSupplimentaryAppeared(Integer.parseInt(honoursCourseEntryForm.getStudentId()));
			Map<Integer,Map<String, ConsolidateMarksCard>> boMap = HonoursCourseEntryHelper.getInstance().getStudentAcademicDetails(list,honoursCourseEntryForm,certificateMap,appearedList);
			map = getAcademicDetailMap(boMap,honourSubjectIds,attendanceMap,honoursCourseEntryForm);
			
		}
		return map;
	}
	/**
	 * @param boMap
	 * @param attendanceMap 
	 * @param honourSubjectIds 
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, HonoursCourseEntryTo> getAcademicDetailMap(Map<Integer, Map<String, ConsolidateMarksCard>> boMap,
			List<Integer> honourSubjectIds, Map<Integer, String> attendanceMap,HonoursCourseEntryForm honoursCourseEntryForm) throws Exception{
		Map<Integer, HonoursCourseEntryTo> map =new HashMap<Integer, HonoursCourseEntryTo>();
		Iterator tcIterator = boMap.entrySet().iterator();
		ConsolidateMarksCard bo;
		int arrears = 0;
		while(tcIterator.hasNext()){
			Map.Entry<Integer,Map<String,ConsolidateMarksCard>> pairs = (Map.Entry)tcIterator.next();
			Iterator itr=pairs.getValue().entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String,ConsolidateMarksCard> mapPair= (Map.Entry)itr.next();
				bo=mapPair.getValue();
				HonoursCourseEntryTo to =null;
				int totalMaxMarks=0;
				int totalMarksAwarded=0;
				boolean isAdd=true;
				if(bo.getTermNumber() !=0){
					if(map.containsKey(bo.getTermNumber())){
						to = map.remove(bo.getTermNumber());
					}else{
						to = new HonoursCourseEntryTo();
					}
					if(bo.getTermNumber()==1){
						to.setSemester("Semester I");
					}else if(bo.getTermNumber()==2){
						to.setSemester("Semester II");
					}else{
						to.setSemester("Semester III");
					}
					if(to.getYear() != null && !to.getYear().trim().isEmpty()){
						if(Integer.parseInt(to.getYear())>=bo.getAcademicYear()){
							to.setYear(String.valueOf(bo.getAcademicYear()));
						}
					}else{
						to.setYear(String.valueOf(bo.getAcademicYear()));
					}
					String attpercentage = attendanceMap.get(bo.getTermNumber());
					to.setAttPercentage(attpercentage);
					if(bo.getSubType().equalsIgnoreCase("Theory")){
						if((bo.getDontAddInTotal()!=null && bo.getDontAddInTotal()) || (bo.getSection().equalsIgnoreCase("Add On Course"))){
							isAdd=false;
						}
						
						if(bo.getTheoryMax()!=null && CommonUtil.isValidDecimal(bo.getTheoryMax().toString()) && isAdd){
							if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty()){
								totalMaxMarks = Integer.parseInt(to.getTotalMaxmarks());
							}
							totalMaxMarks+=bo.getTheoryMax().intValue();
							to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
						}
						if(isAdd){
							if(to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
								totalMarksAwarded = Integer.parseInt(to.getTotalMarksAwarded());
							}
							totalMarksAwarded+=(int)Math.round(bo.getTheoryObtain());
							to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
						}
						if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty() &&
								to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
							double percentage = 0.0;
							percentage = Double.parseDouble(to.getTotalMarksAwarded())* 100;
							percentage = percentage /Integer.parseInt(to.getTotalMaxmarks()) ;
							to.setPercentage(String.valueOf((percentage)));
							if(to.getPercentage() != null && to.getPercentage().length() >4){
								to.setPercentage(to.getPercentage().substring(0, 5));
							}
						}
						if(honourSubjectIds != null && honourSubjectIds.contains(bo.getSubject().getId())){
							int marksAwarded=0;
							int maxMarks=0;
							double percentage = 0.0;
							if(bo.getTheoryObtain() != 0.0){
								if(to.getHonourSubjectMarksAwarded() != null && !to.getHonourSubjectMarksAwarded().trim().isEmpty()){
									marksAwarded = Integer.parseInt(to.getHonourSubjectMarksAwarded());
								}
								marksAwarded = marksAwarded + (int)Math.round(bo.getTheoryObtain());
								to.setHonourSubjectMarksAwarded(String.valueOf(marksAwarded));
							}
							if(bo.getTheoryMax()!=null && CommonUtil.isValidDecimal(bo.getTheoryMax().toString()) && isAdd){
								if(to.getHonourSubjectMaxmarks() != null && !to.getHonourSubjectMaxmarks().trim().isEmpty()){
									maxMarks = Integer.parseInt(to.getHonourSubjectMaxmarks());
								}
								maxMarks = maxMarks +(int)Math.round(bo.getTheoryMax().doubleValue());
								to.setHonourSubjectMaxmarks(String.valueOf(maxMarks));
							}
							percentage = ((double)marksAwarded/(double)maxMarks)*100;
							to.setHonourSubjectPercentage(String.valueOf((percentage)));
							if(to.getHonourSubjectPercentage() != null && to.getHonourSubjectPercentage().length() >4){
								to.setHonourSubjectPercentage(to.getHonourSubjectPercentage().substring(0, 5));
							}
							if(percentage != 0.0){
								to.setHonourSubPercentage(String.valueOf(percentage));
							}
							if(to.getHonourSubPercentage() != null && to.getHonourSubPercentage().length()>4){
								to.setHonourSubPercentage(to.getHonourSubPercentage().substring(0, 5));
							}
						}
						
					}else{
						if(bo.getPracticalMax()!=null && CommonUtil.isValidDecimal(bo.getPracticalMax().toString()) && isAdd){
							if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty()){
								totalMaxMarks = Integer.parseInt(to.getTotalMaxmarks());
							}
							totalMaxMarks+=bo.getPracticalMax().intValue();
							to.setTotalMaxmarks(String.valueOf(totalMaxMarks));
						}
						if(isAdd){
							if(to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
								totalMarksAwarded = Integer.parseInt(to.getTotalMarksAwarded());
							}
							totalMarksAwarded+=(int)Math.round(bo.getPracticalObtain());
							to.setTotalMarksAwarded(String.valueOf(totalMarksAwarded));
						}
						if(to.getTotalMaxmarks() != null && !to.getTotalMaxmarks().trim().isEmpty() &&
								to.getTotalMarksAwarded() != null && !to.getTotalMarksAwarded().trim().isEmpty()){
							double percentage = 0.0;
							percentage = Double.parseDouble(to.getTotalMarksAwarded())* 100;
							percentage = percentage /Integer.parseInt(to.getTotalMaxmarks()) ;
							to.setPercentage(String.valueOf((percentage)));
							if(to.getPercentage() != null && to.getPercentage().length() >4){
								to.setPercentage(to.getPercentage().substring(0, 5));
							}
						}
						
						if(honourSubjectIds != null && honourSubjectIds.contains(bo.getSubject().getId())){
							int marksAwarded=0;
							int maxMarks=0;
							double percentage = 0.0;
							if(bo.getPracticalObtain() != 0.0){
								if(to.getHonourSubjectMarksAwarded() != null && !to.getHonourSubjectMarksAwarded().trim().isEmpty()){
									marksAwarded = Integer.parseInt(to.getHonourSubjectMarksAwarded());
								}
								marksAwarded = marksAwarded + (int)Math.round(bo.getPracticalObtain());
								to.setHonourSubjectMarksAwarded(String.valueOf(marksAwarded));
							}
							if(bo.getPracticalMax()!=null && CommonUtil.isValidDecimal(bo.getPracticalMax().toString()) && isAdd){
								if(to.getHonourSubjectMaxmarks() != null && !to.getHonourSubjectMaxmarks().trim().isEmpty()){
									maxMarks = Integer.parseInt(to.getHonourSubjectMaxmarks());
								}
								maxMarks = maxMarks +(int)Math.round(bo.getPracticalMax().doubleValue());
								to.setHonourSubjectMaxmarks(String.valueOf(maxMarks));
							}
							percentage = ((double)marksAwarded/(double)maxMarks)*100;
							to.setHonourSubjectPercentage(String.valueOf((percentage)));
							if(to.getHonourSubjectPercentage() != null && to.getHonourSubjectPercentage().length() >4){
								to.setHonourSubjectPercentage(to.getHonourSubjectPercentage().substring(0, 5));
							}
							if(percentage != 0.0){
								to.setHonourSubPercentage(String.valueOf(percentage));
							}
							if(to.getHonourSubPercentage() != null && to.getHonourSubPercentage().length()>4){
								to.setHonourSubPercentage(to.getHonourSubPercentage().substring(0, 5));
							}
						}
					}
					map.put(bo.getTermNumber(), to);
					if(!bo.getDontConsiderFailureTotalResult() && bo.getPassOrFail().equalsIgnoreCase("Fail")){
						arrears++;
					}
				}
			}
		}
		honoursCourseEntryForm.setArrears(String.valueOf(arrears));
		return map;
	}
	/**
	 * @param certificateList
	 * @return
	 */
	private Map<Integer, Map<Integer, Map<Integer, Boolean>>> getCertificateSubjectMap( List certificateList)  throws Exception{
		Map<Integer,Map<Integer,Map<Integer,Boolean>>> map=new HashMap<Integer,Map<Integer, Map<Integer,Boolean>>>();
		Map<Integer,Map<Integer,Boolean>> outerMap;
		Map<Integer,Boolean> innerMap;
		if(certificateList!=null && !certificateList.isEmpty()){
			Iterator<Object[]> itr=certificateList.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				if(objects[0]!=null && objects[1]!=null && objects[2]!=null  && objects[3]!=null){
					if(map.containsKey(Integer.parseInt(objects[3].toString())))
						outerMap=map.get(Integer.parseInt(objects[3].toString()));
					else
						outerMap=new HashMap<Integer, Map<Integer,Boolean>>();
						
							
					if(outerMap.containsKey(Integer.parseInt(objects[2].toString()))){
						innerMap=outerMap.remove(Integer.parseInt(objects[2].toString()));
					}else
						innerMap=new HashMap<Integer, Boolean>();
					
					innerMap.put(Integer.parseInt(objects[0].toString()),(Boolean)objects[1]);
					outerMap.put(Integer.parseInt(objects[2].toString()),innerMap);
					map.put(Integer.parseInt(objects[3].toString()), outerMap);
				}
			}
		}
		return map;
	}
	/**
	 * @param honoursCourseEntryForm
	 * @throws Exception
	 */
	public void saveApplicationDetails(HonoursCourseEntryForm honoursCourseEntryForm)  throws Exception{
		HonoursCourseApplication bo = HonoursCourseEntryHelper.getInstance().getCourseApplication(honoursCourseEntryForm);
		transaction.saveAppliedCourse(bo);
	}
	/**
	 * @param courseId 
	 * @param year 
	 * @return
	 * @throws Exception
	 */
	public List<HonoursCourseEntryTo> getAppliedStudentCourseDetails(String year, String courseId) throws Exception{
		List<HonoursCourseApplication> boList = transaction.getAppliedStudentCourseDetails(year,courseId);
		
		return HonoursCourseEntryHelper.getInstance().convertBOTOTO(boList);
	}
	/**
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public String saveCourseDetails(HonoursCourseEntryForm form) throws Exception{
		List<HonorsEntryBo> boList = HonoursCourseEntryHelper.getInstance().convertFormToBO(form);
		return transaction.saveDetals(boList);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCourseMap() throws Exception{
		return transaction.getHonoursCourseMap();
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getHonoursCourseMap() throws Exception{
		return transaction.getHonoursApplicationCourseMap();
	}
	/**
	 * @param appliedDetails
	 * @return
	 * @throws Exception
	 */
	public List<HonoursCourseEntryTo> resetTheData(List<HonoursCourseEntryTo> appliedDetails) throws Exception {
		List<HonoursCourseEntryTo> tos = new ArrayList<HonoursCourseEntryTo>();
		if(appliedDetails != null){
			Iterator<HonoursCourseEntryTo> iterator = appliedDetails.iterator();
			while (iterator.hasNext()) {
				HonoursCourseEntryTo to = (HonoursCourseEntryTo) iterator.next();
				to.setChecked(null);
				tos.add(to);
			}
		}
		return tos;
	}
}
