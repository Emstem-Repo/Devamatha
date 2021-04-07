package com.kp.cms.helpers.hostel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.EvaStudentFeedbackOpenConnection;
import com.kp.cms.bo.admin.EvaluationStudentFeedbackSession;
import com.kp.cms.bo.admin.HlBlocks;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlUnits;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.exam.ExamSpecializationBO;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;
import com.kp.cms.forms.hostel.HolidaysForm;
import com.kp.cms.to.admin.EvaStudentFeedbackOpenConnectionTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;
import com.kp.cms.transactions.hostel.IHolidaysTransaction;
import com.kp.cms.transactionsimpl.hostel.HolidaysTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelHolidaysHelper {
	IHolidaysTransaction iTransaction=HolidaysTransactionImpl.getInstance();
	/**
	 * instance()
	 */
	public static volatile HostelHolidaysHelper holidaysHelper = null;

	public static HostelHolidaysHelper getInstance() {
		if (holidaysHelper == null) {
			holidaysHelper = new HostelHolidaysHelper();
		}
		return holidaysHelper;
	}
	/**
	 * convert form to HostelHolidaysBo
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public List<HostelHolidaysBo> copyFromFormToBO( HolidaysForm holidaysForm) throws Exception{
		List<HostelHolidaysBo> hList = new ArrayList<HostelHolidaysBo>();
		if(holidaysForm.getProgramsId()!=null && holidaysForm.getProgramsId().length !=0 ){
			String[] programId = holidaysForm.getProgramsId();
			int j =0;
			for(j=0;j<programId.length;j++){
				String pId = programId[j];
				if(Integer.parseInt(pId)==0){
					List<Integer> progIdList=iTransaction.getProgIdList();
						Iterator<Integer> iterator1=progIdList.iterator();
						while (iterator1.hasNext()) {
							Integer prId = (Integer) iterator1.next();
							if(holidaysForm.getCoursesId()!=null && holidaysForm.getCoursesId().length !=0){
								String[] courseIds=holidaysForm.getCoursesId();
								List<Integer> courseIdList=iTransaction.getCourseIdList(prId);
								Iterator<Integer> iterator=courseIdList.iterator();
								while (iterator.hasNext()) {
									int courseId =Integer.valueOf(iterator.next());
										for ( j = 0; j < courseIds.length; j++) {
											int cId = Integer.valueOf(courseIds[j]);
											if(courseId==cId){
												HostelHolidaysBo holidaysBo = new HostelHolidaysBo();
												Program program=new Program();
												Course course=new Course();
												program.setId(prId);
												holidaysBo.setProgramId(program);
												course.setId(cId);
												holidaysBo.setCourseId(course);
												holidaysBo.setHolidaysFrom(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()));
												holidaysBo.setHolidaysTo(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()));
												holidaysBo.setHolidaysFromSession(holidaysForm.getHolidaysFromSession());
												holidaysBo.setHolidaysToSession(holidaysForm.getHolidaysToSession());
												holidaysBo.setDescription(holidaysForm.getDescription());
												holidaysBo.setCreatedDate(new Date());
												holidaysBo.setLastModifiedDate(new Date());
												holidaysBo.setCreatedBy(holidaysForm.getUserId());
												holidaysBo.setModifiedBy(holidaysForm.getUserId());
												holidaysBo.setIsActive(true);
												holidaysBo.setHolidaysOrVaction(holidaysForm.getHolidaysOrVacation());
													HlHostel hlHostel=new HlHostel();
													hlHostel.setId(Integer.parseInt(holidaysForm.getHostelId()));
												holidaysBo.setHostelId(hlHostel);
													HlBlocks hlBlocks=new HlBlocks();
													hlBlocks.setId(Integer.parseInt(holidaysForm.getBlockId()));
												holidaysBo.setBlockId(hlBlocks);
													HlUnits hlUnits=new HlUnits();
													hlUnits.setId(Integer.parseInt(holidaysForm.getUnitId()));
												holidaysBo.setUnitId(hlUnits);
												hList.add(holidaysBo);
											}
										}
									
									
								}
								
							}
							
						}
				}else{
					String[] progId = holidaysForm.getProgramsId();
					int i =0;
					for(i=0;i<progId.length;i++){
						String progrId = progId[i];
						if(holidaysForm.getCoursesId()!=null && holidaysForm.getCoursesId().length !=0){
							String[] courseIds=holidaysForm.getCoursesId();
							List<Integer> courseIdList=iTransaction.getCourseIdList(Integer.parseInt(progrId));
							Iterator<Integer> iterator=courseIdList.iterator();
							while (iterator.hasNext()) {
								int courseId =iterator.next();
									for ( j = 0; j < courseIds.length; j++) {
										int cId = Integer.valueOf(courseIds[j]);
										if(courseId==cId){
											HostelHolidaysBo holidaysBo = new HostelHolidaysBo();
											Program program=new Program();
											Course course=new Course();
											program.setId(Integer.parseInt(progrId));
											holidaysBo.setProgramId(program);
											course.setId(cId);
											holidaysBo.setCourseId(course);
											holidaysBo.setHolidaysFrom(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()));
											holidaysBo.setHolidaysTo(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()));
											holidaysBo.setHolidaysFromSession(holidaysForm.getHolidaysFromSession());
											holidaysBo.setHolidaysToSession(holidaysForm.getHolidaysToSession());
											holidaysBo.setDescription(holidaysForm.getDescription());
											holidaysBo.setCreatedDate(new Date());
											holidaysBo.setLastModifiedDate(new Date());
											holidaysBo.setCreatedBy(holidaysForm.getUserId());
											holidaysBo.setModifiedBy(holidaysForm.getUserId());
											holidaysBo.setIsActive(true);
											holidaysBo.setHolidaysOrVaction(holidaysForm.getHolidaysOrVacation());
											holidaysBo.setHolidaysOrVaction(holidaysForm.getHolidaysOrVacation());
											HlHostel hlHostel=new HlHostel();
											hlHostel.setId(Integer.parseInt(holidaysForm.getHostelId()));
											holidaysBo.setHostelId(hlHostel);
												HlBlocks hlBlocks=new HlBlocks();
												hlBlocks.setId(Integer.parseInt(holidaysForm.getBlockId()));
											holidaysBo.setBlockId(hlBlocks);
												HlUnits hlUnits=new HlUnits();
												hlUnits.setId(Integer.parseInt(holidaysForm.getUnitId()));
											holidaysBo.setUnitId(hlUnits);
											hList.add(holidaysBo);
										}
									}
								
								
							}
							
						}
					}
				}
			}
		}
			
			
		return hList;
	}
	/**
	 * convert HostelHolidaysBo to HostelHolidaysTo
	 * @param connections
	 * @return
	 * @throws Exception
	 */
	public List<HostelHolidaysTo> copyBOToToList( List<HostelHolidaysBo> hostelHolidaysBos) throws Exception{
		List<HostelHolidaysTo> list = new ArrayList<HostelHolidaysTo>();
		if(hostelHolidaysBos!=null && !hostelHolidaysBos.toString().isEmpty()){
			Iterator<HostelHolidaysBo> iterator = hostelHolidaysBos.iterator();
			while (iterator.hasNext()) {
				HostelHolidaysBo holidaysBo = (HostelHolidaysBo) iterator .next();
				HostelHolidaysTo holidaysTo = new HostelHolidaysTo();
				if(holidaysBo.getId()!=0){
					holidaysTo.setId(holidaysBo.getId());
				}
				if(holidaysBo.getProgramId()!=null && holidaysBo.getProgramId().getId()!=0){
					holidaysTo.setProgramName(holidaysBo.getProgramId().getName());
				}
				if(holidaysBo.getCourseId()!=null && holidaysBo.getCourseId().getId()!=0){
					holidaysTo.setCourseName( holidaysBo.getCourseId().getName());
				}
				if(holidaysBo.getHolidaysFrom()!=null && !holidaysBo.getHolidaysFrom().toString().isEmpty()){
					holidaysTo.setHolidaysFrom(formatDate(holidaysBo.getHolidaysFrom()));
				}
				if(holidaysBo.getHolidaysTo()!=null && !holidaysBo.getHolidaysTo().toString().isEmpty()){
					holidaysTo.setHolidaysTo(formatDate(holidaysBo.getHolidaysTo()));
				}
				if(holidaysBo.getHolidaysFromSession()!=null && !holidaysBo.getHolidaysFromSession().isEmpty()){
					holidaysTo.setHolidaysFromSession(holidaysBo.getHolidaysFromSession());
				}
				if(holidaysBo.getHolidaysToSession()!=null && !holidaysBo.getHolidaysToSession().isEmpty()){
					holidaysTo.setHolidaysToSession(holidaysBo.getHolidaysToSession());
				}
				if(holidaysBo.getHolidaysOrVaction()!=null && !holidaysBo.getHolidaysOrVaction().isEmpty()){
					holidaysTo.setHolidaysOrVaction(holidaysBo.getHolidaysOrVaction());
				}
				list.add(holidaysTo);
			}
		}
		return list;
	}
	/**
	 * convert date to string date
	 * @param date
	 * @return
	 */
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	/**
	 *convert  editholidaysForm to hostel holidays bo
	 * @param holidaysForm
	 * @return
	 * @throws Exception
	 */
	public HostelHolidaysBo copyFromholidaysFormToBO(HolidaysForm holidaysForm) throws Exception{
		HostelHolidaysBo holidaysBo=iTransaction.getHostelHolidaysDetails(holidaysForm.getId());
		if(holidaysBo!=null){
			holidaysBo.setHolidaysFrom(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()));
			holidaysBo.setHolidaysTo(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()));
			holidaysBo.setHolidaysFromSession(holidaysForm.getHolidaysFromSession());
			holidaysBo.setHolidaysToSession(holidaysForm.getHolidaysToSession());
			holidaysBo.setLastModifiedDate(new Date());
			holidaysBo.setModifiedBy(holidaysForm.getUserId());
			holidaysBo.setDescription(holidaysForm.getDescription());
		}
		return holidaysBo;
	
	}
public boolean checkDuplicateForUpdate(List<HostelHolidaysBo> hList,HolidaysForm holidaysForm)throws Exception{
	boolean flag=false;
	if(hList!=null && !hList.isEmpty()){
		String fSess=holidaysForm.getHolidaysFromSession();
		String tSess=holidaysForm.getHolidaysToSession();
		Iterator<HostelHolidaysBo> iterator=hList.iterator();
		while (iterator.hasNext()) {
			HostelHolidaysBo hostelHolidaysBo = (HostelHolidaysBo) iterator.next();
			String fromSession=hostelHolidaysBo.getHolidaysFromSession();
			String toSession=hostelHolidaysBo.getHolidaysToSession();
			if(holidaysForm.getId()!=hostelHolidaysBo.getId()){
				if(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()).compareTo(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()))==0){
						if(fromSession.equalsIgnoreCase(fSess) && toSession.equalsIgnoreCase(tSess)){
							flag=true;
							break;
						}else if(fromSession.equalsIgnoreCase("morning") && toSession.equalsIgnoreCase("evening")){
							flag=true;
							break;
						}
				}else{
					if(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()).compareTo(hostelHolidaysBo.getHolidaysFrom())==0
							|| CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()).compareTo(hostelHolidaysBo.getHolidaysFrom())==0){
						if(fromSession.equalsIgnoreCase(fSess) && toSession.equalsIgnoreCase(tSess)){
							flag=true;
							break;
						}else if(fromSession.equalsIgnoreCase("morning")){
							flag=true;
							break;
						}
					}else if(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()).compareTo(hostelHolidaysBo.getHolidaysTo())==0
							|| CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()).compareTo(hostelHolidaysBo.getHolidaysTo())==0){
						if(fromSession.equalsIgnoreCase(fSess) && toSession.equalsIgnoreCase(tSess)){
							flag=true;
							break;
						}else if(fromSession.equalsIgnoreCase("evening")){
							flag=true;
							break;
						}
					}else if(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()).compareTo(hostelHolidaysBo.getHolidaysFrom())>0
							&& CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysFrom()).compareTo(hostelHolidaysBo.getHolidaysTo())<0){
						flag=true;
						break;
					}else if(CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()).compareTo(hostelHolidaysBo.getHolidaysFrom())>0
							&& CommonUtil.ConvertStringToDate(holidaysForm.getHolidaysTo()).compareTo(hostelHolidaysBo.getHolidaysTo())<0){
						flag=true;
						break;
					}
				}
			}
		}
	}
return flag;
}
public boolean checkDuplicate(List<HostelHolidaysBo> hList,HolidaysForm holidaysForm)throws Exception{
	boolean flag=false;
	String progId[]=holidaysForm.getProgramsId();
		for(int i=0;i<progId.length;i++){
		int prId=Integer.parseInt(progId[i]);
		if(prId!=0){
			String courseId[]=holidaysForm.getCoursesId();
			String holidaysFromDate=holidaysForm.getHolidaysFrom();
			String holidaysToDate=holidaysForm.getHolidaysTo();
			String hsFrom=holidaysForm.getHolidaysFromSession();
			String hsTo=holidaysForm.getHolidaysToSession();
			String hlOrVaction=holidaysForm.getHolidaysOrVacation();
			Iterator<HostelHolidaysBo> iterator=hList.iterator();
			while (iterator.hasNext()) {
				HostelHolidaysBo hostelHolidaysBo = (HostelHolidaysBo) iterator.next();
				int pId=hostelHolidaysBo.getProgramId().getId();
				int cId=hostelHolidaysBo.getCourseId().getId();
				String hFromDate=formatDate(hostelHolidaysBo.getHolidaysFrom());
				String hToDate=formatDate(hostelHolidaysBo.getHolidaysTo());
				String hFromSesssion=hostelHolidaysBo.getHolidaysFromSession();
				String hToSession=hostelHolidaysBo.getHolidaysToSession();
				String holidaysOrVaction=hostelHolidaysBo.getHolidaysOrVaction();
				for(int j=0;j<courseId.length;j++){
				int csId=Integer.parseInt(courseId[j]);
				if(prId==pId && csId==cId && holidaysFromDate.equalsIgnoreCase(hFromDate)
						&& holidaysToDate.equalsIgnoreCase(hToDate) && hsFrom.equalsIgnoreCase(hFromSesssion)
						&& hsTo.equalsIgnoreCase(hToSession) && hlOrVaction.equalsIgnoreCase(holidaysOrVaction)
						&& Integer.parseInt(holidaysForm.getHostelId())==hostelHolidaysBo.getHostelId().getId()
						&& Integer.parseInt(holidaysForm.getBlockId())==hostelHolidaysBo.getBlockId().getId()
						&& Integer.parseInt(holidaysForm.getUnitId())==hostelHolidaysBo.getUnitId().getId()){
					flag=true;
				}
			}
		}
		}else{
			List<Integer> progIdList=iTransaction.getProgIdList();
			Iterator<Integer> iterator=progIdList.iterator();
			while (iterator.hasNext()) {
				int  prgId = (Integer) iterator.next();
				String courseId[]=holidaysForm.getCoursesId();
				String holidaysFromDate=holidaysForm.getHolidaysFrom();
				String holidaysToDate=holidaysForm.getHolidaysTo();
				String hsFrom=holidaysForm.getHolidaysFromSession();
				String hsTo=holidaysForm.getHolidaysToSession();
				String hlOrVaction=holidaysForm.getHolidaysOrVacation();
				Iterator<HostelHolidaysBo> iterator1=hList.iterator();
				while (iterator1.hasNext()) {
					HostelHolidaysBo hostelHolidaysBo = (HostelHolidaysBo) iterator1.next();
					int pId=hostelHolidaysBo.getProgramId().getId();
					int cId=hostelHolidaysBo.getCourseId().getId();
					String hFromDate=formatDate(hostelHolidaysBo.getHolidaysFrom());
					String hToDate=formatDate(hostelHolidaysBo.getHolidaysTo());
					String hFromSesssion=hostelHolidaysBo.getHolidaysFromSession();
					String hToSession=hostelHolidaysBo.getHolidaysToSession();
					String holidaysOrVaction=hostelHolidaysBo.getHolidaysOrVaction();
					for(int j=0;j<courseId.length;j++){
					int csId=Integer.parseInt(courseId[j]);
					if(prgId==pId && csId==cId && holidaysFromDate.equalsIgnoreCase(hFromDate)
							&& holidaysToDate.equalsIgnoreCase(hToDate) && hsFrom.equalsIgnoreCase(hFromSesssion)
							&& hsTo.equalsIgnoreCase(hToSession) && hlOrVaction.equalsIgnoreCase(holidaysOrVaction)
							&& Integer.parseInt(holidaysForm.getHostelId())==hostelHolidaysBo.getHostelId().getId()
							&& Integer.parseInt(holidaysForm.getBlockId())==hostelHolidaysBo.getBlockId().getId()
							&& Integer.parseInt(holidaysForm.getUnitId())==hostelHolidaysBo.getUnitId().getId()){
						flag=true;
					}
				}
			}
			
				
			}
			
			}
		}
	
	return flag;
}
}
