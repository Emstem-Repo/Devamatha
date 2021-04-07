package com.kp.cms.helpers.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.StuCocurrLeaveDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ExtraCocurricularLeaveEntryForm;
import com.kp.cms.to.admin.PeriodTONew;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.CocurricularAttendnaceEntryTo;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.utilities.CommonUtil;

public class ExtraCocurricularLeaveEntryHelper {
	private static volatile ExtraCocurricularLeaveEntryHelper extraCocurricularLeaveEntryHelper = null;
	private static final Log log = LogFactory.getLog(ExtraCocurricularLeaveEntryHelper.class);
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	public static ExtraCocurricularLeaveEntryHelper getInstance()
	{
		if(extraCocurricularLeaveEntryHelper == null)
		{
			extraCocurricularLeaveEntryHelper = new ExtraCocurricularLeaveEntryHelper();
		}
		return extraCocurricularLeaveEntryHelper;
	}
	public String getAttendanceDuplicateQuery(ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm,int studentId, int classId, String applicationLimit) throws Exception {
		log.debug("call of getAttendanceDuplicateQuery method in ExtraCocurricularLeaveEntryHelper.class");
		String query = "SELECT stu_cocurr_leave.id,stu_cocurr_leave.start_date," +
				" stu_cocurr_leave.end_date,stu_cocurr_leave.start_period, stu_cocurr_leave.cocurricular_leavetype_id, " +
				" stu_cocurr_leave.end_period, stu_cocurr_leave.is_cocurr_leave_cancelled," +
				" stu_cocurr_leave_details.id as cid, stu_cocurr_leave_details.stu_cocurr_leave_id," +
				" stu_cocurr_leave_details.student_id, " +
				" stu_cocurr_leave.approved_by_teacher,stu_cocurr_leave.cancelled_by_teacher"+
				" FROM    stu_cocurr_leave_details " +
				" stu_cocurr_leave_details   INNER JOIN     stu_cocurr_leave stu_cocurr_leave  " +
				" ON (stu_cocurr_leave_details.stu_cocurr_leave_id = stu_cocurr_leave.id)" +
				" where stu_cocurr_leave.start_date   >= DATE_SUB(curdate(), INTERVAL "+applicationLimit+" DAY) "+
				" and stu_cocurr_leave.is_cocurr_leave_cancelled =0 " +
				" and stu_cocurr_leave_details.student_id= "+studentId+
			    " and stu_cocurr_leave.class_schemewise_id=" +classId+
				" order by stu_cocurr_leave.start_date desc";
		log.debug("call of getAttendanceDuplicateQuery method in ExtraCocurricularLeaveEntryHelper.class");
		return query;
	}
	public String getAttendanceQuery(ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm,int studentId, int classId, int currentYear, String applicationLimit)  throws Exception{
		log.debug("call of getAttendanceQuery method in ExtraCocurricularLeaveEntryHelper.class");
		String attQuery="SELECT attendance.attendance_date, attendance.attendance_type_id as " +
				" typeId,subject.id as subjectId,attendance_period.period_id,attendance_class.class_schemewise_id," +
				" attendance_student.student_id,curriculum_scheme.year,attendance_student.id as attStudentId,attendance.id as  attId FROM " +
				" ((((((( student student INNER JOIN class_schemewise class_schemewise     " +
				" ON (student.class_schemewise_id =class_schemewise.id)) INNER JOIN " +
				" attendance_class attendance_class ON (attendance_class.class_schemewise_id =class_schemewise.id))" +
				" INNER JOIN attendance attendance ON (attendance_class.attendance_id = attendance.id))" +
				" INNER JOIN attendance_period attendance_period ON (attendance_period.attendance_id = attendance.id))" +
				" INNER JOIN attendance_student attendance_student ON " +
				" (attendance_student.attendance_id = attendance.id) AND " +
				" (attendance_student.student_id = student.id))INNER JOIN " +
				" curriculum_scheme_duration curriculum_scheme_duration ON " +
				" (class_schemewise.curriculum_scheme_duration_id =curriculum_scheme_duration.id)) " +
				" INNER JOIN period  On (period.id= attendance_period.period_id) "+
				" INNER JOIN curriculum_scheme curriculum_scheme ON " +
				" (curriculum_scheme_duration.curriculum_scheme_id =curriculum_scheme.id)) " +
				" INNER JOIN subject subject ON (attendance.subject_id = subject.id)  where   " +
				" attendance_student.is_present=0  and student.class_schemewise_id=" +classId+
				" and curriculum_scheme_duration.academic_year="+currentYear +
				" and (attendance_student.is_on_leave=0 " +
				" and period.class_schemewise_id="+classId+
				" or attendance_student.is_on_leave is null) and attendance.is_canceled=0" +
				" and attendance.attendance_date  >= DATE_SUB(curdate(), INTERVAL "+applicationLimit+" DAY) "+
				" and attendance_student.student_id="+studentId+ "  order by attendance.attendance_date desc ";
		
		log.debug("end of getAttendanceQuery method in ExtraCocurricularLeaveEntryHelper.class");
		return attQuery;
	}
	public Map<Date, CocurricularAttendnaceEntryTo> getAttendancePeriodMap(List<Object[]> attendanceObjectsList,ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm,
			List<PeriodTO> periodTOList, List<Object[]> duplicateList,
			Map<Integer, String> subjectMap) throws Exception{
		
		
		log.debug("call of getAttendancePeriodMap method in ExtraCocurricularLeaveEntryHelper.class");
		Map<Date, CocurricularAttendnaceEntryTo> finalMap = new HashMap<Date, CocurricularAttendnaceEntryTo>();
		CocurricularAttendnaceEntryTo cocurricularAttendnaceEntryTo = null;
		if(attendanceObjectsList!=null && !attendanceObjectsList.isEmpty())
		{
			if(attendanceObjectsList.size()>duplicateList.size())
			{
				extraCocurricularLeaveEntryForm.setDislaySubmitButton(true);
			}
			else
			{
				extraCocurricularLeaveEntryForm.setDislaySubmitButton(false);
			}
			Iterator<Object[]> iterator  = attendanceObjectsList.iterator();
			while(iterator.hasNext())
			{
				Object[] row= iterator.next();
				List<PeriodTO> newPeriodList= new ArrayList<PeriodTO>();
				newPeriodList=periodTOList;
				Set<String> tempPeriod = new HashSet<String>();
				if(finalMap.containsKey((Date)row[0]))
				{
					//cocurricularAttendnaceEntryTo = finalMap.remove(row[0].toString());
					if(row[0]!=null)
					{
						cocurricularAttendnaceEntryTo.setAttendanceDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)row[0]),ExtraCocurricularLeaveEntryHelper.SQL_DATEFORMAT,ExtraCocurricularLeaveEntryHelper.FROM_DATEFORMAT));
					}
					if(row[3]!=null)
					{
						List<PeriodTONew> periodList=cocurricularAttendnaceEntryTo.getPeriodToList();
						PeriodTONew periodTONew=new PeriodTONew();
						periodTONew.setId(Integer.parseInt(row[3].toString()));
						Iterator<PeriodTO> iteratorPeriod = periodTOList.iterator();
						while(iteratorPeriod.hasNext())
						{
							PeriodTO periodTO = iteratorPeriod.next();
							if(periodTO.getId()==periodTONew.getId())
							{
								periodTONew.setClassSchemewiseId(periodTO.getClassSchemewiseId());
								periodTONew.setClassSchemewiseId(periodTO.getClassSchemewiseId());
								periodTONew.setStartTime(periodTO.getStartTime());
								periodTONew.setEndTime(periodTO.getEndTime());
								periodTONew.setPeriodName(periodTO.getPeriodName());
								periodTONew.setName(periodTO.getName());
								periodTONew.setSubjectId(row[2].toString());
								periodTONew.setSubjectName(subjectMap.get(Integer.parseInt(row[2].toString())));
								if(row[8]!=null)
								{
									periodTONew.setMailAttId(row[8].toString());
								}
								if(row[7]!=null)
								{
									periodTONew.setAttStudentId(row[7].toString());
								}
							}
						}
						
						periodTONew.setIsAbsent(true);
						periodList.add(periodTONew);
						cocurricularAttendnaceEntryTo.setPeriodToList(periodList);
						
						
					}
					if(row[1]!=null)
					{
						cocurricularAttendnaceEntryTo.setCocurricularLeavetypeId(row[1].toString());
					}

					if(row[2]!=null)
					{
						cocurricularAttendnaceEntryTo.setSubjectId(row[2].toString());
					}
					if(row[4]!=null)
					{
						cocurricularAttendnaceEntryTo.setClassSchemewiseId(row[4].toString());
					}
					if(row[5]!=null)
					{
						cocurricularAttendnaceEntryTo.setStudentId(row[5].toString());
					}
					if(row[6]!=null)
					{
						cocurricularAttendnaceEntryTo.setYear(row[6].toString());
					}
					if(row[7]!=null)
					{
						cocurricularAttendnaceEntryTo.setAttendanceStudentId(row[7].toString());
					}
					// duplicate checking
					if(duplicateList!=null && duplicateList.size()>0)
					{
						Iterator<Object[]> duplicatedIteration= duplicateList.iterator();

						while(duplicatedIteration.hasNext())
						{
							Object[] duprow = duplicatedIteration.next();
							String ckckdate=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)duprow[1]),
									ExtraCocurricularLeaveEntryHelper.SQL_DATEFORMAT,
									ExtraCocurricularLeaveEntryHelper.FROM_DATEFORMAT); 
							if(ckckdate.equals(cocurricularAttendnaceEntryTo.getAttendanceDate()) && ckckdate.equals(cocurricularAttendnaceEntryTo.getAttendanceDate()))
							{
								Iterator<PeriodTONew> perIterator= cocurricularAttendnaceEntryTo.getPeriodToList().iterator();
								while(perIterator.hasNext())
								{
									PeriodTONew periodTONew= perIterator.next();
									if((Integer.parseInt(duprow[3].toString()))==(periodTONew.getId()))
									{
										periodTONew.setTempChecked(true);
										periodTONew.setChecked(false);
										periodTONew.setActivity(duprow[4].toString());
										periodTONew.setAttendanceId(duprow[0].toString());
										if(duprow[7]!=null)
										{
										periodTONew.setCocurricularDetailId(duprow[7].toString());
										}
										if(duprow[10]!= null)
										{
											if(duprow[10].toString().equalsIgnoreCase("false"))
											{
												periodTONew.setAppStatus("pending");
												
											}
											if(duprow[10].toString().equalsIgnoreCase("true"))
											{
												periodTONew.setAppStatus("approved");
												
											}
										}
										if(duprow[11]!= null)
										{
											if(duprow[11].toString().equalsIgnoreCase("true"))
											{
												periodTONew.setAppStatus("rejected");
												
											}
										}
										
										// remove object 
										
									}
								}
							}

						}
					}
					
				  finalMap.put((Date)row[0], cocurricularAttendnaceEntryTo);
				}
				else
				{
					cocurricularAttendnaceEntryTo= new CocurricularAttendnaceEntryTo();
					if(row[0]!=null)
					{
						cocurricularAttendnaceEntryTo.setAttendanceDate(
								CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)row[0]),
										ExtraCocurricularLeaveEntryHelper.SQL_DATEFORMAT,
										ExtraCocurricularLeaveEntryHelper.FROM_DATEFORMAT));
					}


					if(row[3]!=null)
					{
						List<PeriodTONew> periodList=new ArrayList<PeriodTONew>();
						PeriodTONew periodTONew=new PeriodTONew();
						periodTONew.setId(Integer.parseInt(row[3].toString()));
						Iterator<PeriodTO> iteratorPeriod = periodTOList.iterator();
						while(iteratorPeriod.hasNext())
						{
							PeriodTO periodTO = iteratorPeriod.next();
							if(periodTO.getId()==periodTONew.getId())
							{
								periodTONew.setClassSchemewiseId(periodTO.getClassSchemewiseId());
								periodTONew.setClassSchemewiseId(periodTO.getClassSchemewiseId());
								periodTONew.setStartTime(periodTO.getStartTime());
								periodTONew.setEndTime(periodTO.getEndTime());
								periodTONew.setPeriodName(periodTO.getPeriodName());
								periodTONew.setName(periodTO.getName());
								periodTONew.setSubjectId(row[2].toString());
								periodTONew.setSubjectName(subjectMap.get(Integer.parseInt(row[2].toString())));
								if(row[8]!=null)
								{
									periodTONew.setMailAttId(row[8].toString());
								}
								if(row[7]!=null)
								{
									periodTONew.setAttStudentId(row[7].toString());
								}
							}
						}
						
						
						periodTONew.setIsAbsent(true);
						periodList.add(periodTONew);
						cocurricularAttendnaceEntryTo.setPeriodToList(periodList);
					}
					if(row[1]!=null)
					{
						cocurricularAttendnaceEntryTo.setCocurricularLeavetypeId(row[1].toString());
					}

					if(row[2]!=null)
					{
						cocurricularAttendnaceEntryTo.setSubjectId(row[2].toString());
					}
					if(row[4]!=null)
					{
						cocurricularAttendnaceEntryTo.setClassSchemewiseId(row[4].toString());
					}
					if(row[5]!=null)
					{
						cocurricularAttendnaceEntryTo.setStudentId(row[5].toString());
					}
					if(row[6]!=null)
					{
						cocurricularAttendnaceEntryTo.setYear(row[6].toString());
					}
					if(row[7]!=null)
					{
						cocurricularAttendnaceEntryTo.setAttendanceStudentId(row[7].toString());
					}
					//  duplicated check
					if(duplicateList!=null && duplicateList.size()>0)
					{
						Iterator<Object[]> duplicatedIteration= duplicateList.iterator();

						while(duplicatedIteration.hasNext())
						{
							Object[] duprow = duplicatedIteration.next();
							String ckckdate=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate((Date)duprow[1]),
									ExtraCocurricularLeaveEntryHelper.SQL_DATEFORMAT,
									ExtraCocurricularLeaveEntryHelper.FROM_DATEFORMAT); 
							if(ckckdate.equals(cocurricularAttendnaceEntryTo.getAttendanceDate()) && ckckdate.equals(cocurricularAttendnaceEntryTo.getAttendanceDate()))
							{
								Iterator<PeriodTONew> perIterator= cocurricularAttendnaceEntryTo.getPeriodToList().iterator();
								while(perIterator.hasNext())
								{
									PeriodTONew periodTONew= perIterator.next();
									if((Integer.parseInt(duprow[3].toString()))==(periodTONew.getId()))
									{
										periodTONew.setTempChecked(true);
										periodTONew.setChecked(false);
										periodTONew.setActivity(duprow[4].toString());
										periodTONew.setAttendanceId(duprow[0].toString());
										if(duprow[7]!=null)
										{
										periodTONew.setCocurricularDetailId(duprow[7].toString());
										}
										if(duprow[10]!= null)
										{
											if(duprow[10].toString().equalsIgnoreCase("false"))
											{
												periodTONew.setAppStatus("pending");
												
											}
											if(duprow[10].toString().equalsIgnoreCase("true"))
											{
												periodTONew.setAppStatus("approved");
												
											}
										}
										if(duprow[11]!= null)
										{
											if(duprow[11].toString().equalsIgnoreCase("true"))
											{
												periodTONew.setAppStatus("rejected");
												
											}
										}
										
									}
								}
							}

						}
					}
					 finalMap.put((Date)row[0], cocurricularAttendnaceEntryTo);
				}
				
			}
		}
		log.debug("end of getAttendancePeriodMap method in ExtraCocurricularLeaveEntryHelper.class");
		return finalMap;
	}
	public List<StuCocurrLeave> createBoObject(ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm) throws Exception{
		log.debug("call of createBoObject method in ExtraCocurricularLeaveEntryHelper.class");
		List<StuCocurrLeave> cocurricularList = new ArrayList<StuCocurrLeave>();
		List<ApproveLeaveTO> approveLeaveTOList = new ArrayList<ApproveLeaveTO>();
		try
		{
			List<CocurricularAttendnaceEntryTo> cocuricularList = extraCocurricularLeaveEntryForm.getList();
			Iterator<CocurricularAttendnaceEntryTo> iterator = cocuricularList.iterator(); 
			while(iterator.hasNext())
			{
				CocurricularAttendnaceEntryTo to = iterator.next();
				List<PeriodTONew> periodToList = to.getPeriodToList();
				Iterator<PeriodTONew> iterator2 = periodToList.iterator();
				while(iterator2.hasNext())
				{
					PeriodTONew periodTO = iterator2.next();
					if(periodTO.isChecked())
					{
						if(periodTO.getActivity()!=null && !periodTO.getActivity().isEmpty())
						{
							StuCocurrLeave cocurrLeave = new StuCocurrLeave();
							ApproveLeaveTO approveLeaveTO= new ApproveLeaveTO();
							if(periodTO.getAttendanceId()!=null)
							{
								cocurrLeave.setId(Integer.parseInt(periodTO.getAttendanceId()));
								cocurrLeave.setModifiedBy(extraCocurricularLeaveEntryForm.getUserId());
								cocurrLeave.setLastModifiedDate(new Date());
							}
							Activity activity = new Activity();
							activity.setId(Integer.parseInt(periodTO.getActivity()));
							cocurrLeave.setActivity(activity);
							cocurrLeave.setStartDate(CommonUtil.ConvertStringToSQLDate(to.getAttendanceDate()));
							cocurrLeave.setEndDate(CommonUtil.ConvertStringToSQLDate(to.getAttendanceDate()));
							Period period = new Period();
							period.setId(periodTO.getId());
							cocurrLeave.setStartPeriod(period);
							cocurrLeave.setEndPeriod(period);
							cocurrLeave.setCreatedBy(extraCocurricularLeaveEntryForm.getUserId());
							cocurrLeave.setCreatedDate(new Date());
							cocurrLeave.setIsCocurrLeaveCancelled(false);
							ClassSchemewise classSchemewise = new ClassSchemewise();
							classSchemewise.setId(Integer.parseInt(to.getClassSchemewiseId()));
							cocurrLeave.setClassSchemewise(classSchemewise);
							AttendanceType type = new AttendanceType();
							type.setId(Integer.parseInt(to.getCocurricularLeavetypeId()));
							cocurrLeave.setAttendanceType(type);
							Set<StuCocurrLeaveDetails> cocurricularLeavedetailsSet = new HashSet<StuCocurrLeaveDetails>();
							StuCocurrLeaveDetails cocurrLeaveDetails = new StuCocurrLeaveDetails();
							Student student = new Student();
							student.setId(Integer.parseInt(to.getStudentId()));
							if(periodTO.getCocurricularDetailId()!=null && !periodTO.getCocurricularDetailId().isEmpty())
							{
								cocurrLeaveDetails.setId(Integer.parseInt(periodTO.getCocurricularDetailId()));
							}
							cocurrLeaveDetails.setStudent(student);
							cocurricularLeavedetailsSet.add(cocurrLeaveDetails);
							cocurrLeave.setStuCocurrLeaveDetailses(cocurricularLeavedetailsSet);
							// new modification
							cocurrLeave.setApprovedByTeacher(Boolean.FALSE);
						
							cocurrLeave.setCancelledByTeacher(Boolean.FALSE);
							Subject subject = new Subject();
							subject.setId(Integer.parseInt(periodTO.getSubjectId()));
							cocurrLeave.setSubject(subject);
							if(periodTO.getMailAttId()!=null && periodTO.getAttStudentId()!=null&& !periodTO.getMailAttId().isEmpty() && !periodTO.getAttStudentId().isEmpty() )
							{
								approveLeaveTO.setAttMainId(periodTO.getMailAttId());
								approveLeaveTO.setAttStudentId(periodTO.getAttStudentId());
								approveLeaveTO.setIsCocurricularAttendance(true);
								approveLeaveTOList.add(approveLeaveTO);
							}
							cocurricularList.add(cocurrLeave);
						}
					}
					else
					{
						if(periodTO.getAttendanceId()!=null && periodTO.getCocurricularDetailId()!=null && !periodTO.getCocurricularDetailId().isEmpty() && !periodTO.isTempChecked())
						{
							StuCocurrLeave cocurrLeave = new StuCocurrLeave();
							ApproveLeaveTO approveLeaveTO= new ApproveLeaveTO();
							cocurrLeave.setIsCocurrLeaveCancelled(true);
							if(periodTO.getAttendanceId()!=null)
							{
								cocurrLeave.setId(Integer.parseInt(periodTO.getAttendanceId()));
								cocurrLeave.setModifiedBy(extraCocurricularLeaveEntryForm.getUserId());
								cocurrLeave.setLastModifiedDate(new Date());
							}
							Activity activity = new Activity();
							if(periodTO.getActivity()!=null && !periodTO.getActivity().isEmpty())
							{
								activity.setId(Integer.parseInt(periodTO.getActivity()));
								cocurrLeave.setActivity(activity);
							}
							
							cocurrLeave.setStartDate(CommonUtil.ConvertStringToSQLDate(to.getAttendanceDate()));
							cocurrLeave.setEndDate(CommonUtil.ConvertStringToSQLDate(to.getAttendanceDate()));
							Period period = new Period();
							period.setId(periodTO.getId());
							cocurrLeave.setStartPeriod(period);
							cocurrLeave.setEndPeriod(period);
							cocurrLeave.setCreatedBy(extraCocurricularLeaveEntryForm.getUserId());
							cocurrLeave.setCreatedDate(new Date());
							cocurrLeave.setIsCocurrLeaveCancelled(true);
							ClassSchemewise classSchemewise = new ClassSchemewise();
							classSchemewise.setId(Integer.parseInt(to.getClassSchemewiseId()));
							cocurrLeave.setClassSchemewise(classSchemewise);
							AttendanceType type = new AttendanceType();
							type.setId(Integer.parseInt(to.getCocurricularLeavetypeId()));
							cocurrLeave.setAttendanceType(type);
							Set<StuCocurrLeaveDetails> cocurricularLeavedetailsSet = new HashSet<StuCocurrLeaveDetails>();
							StuCocurrLeaveDetails cocurrLeaveDetails = new StuCocurrLeaveDetails();
							Student student = new Student();
							student.setId(Integer.parseInt(to.getStudentId()));
							if(periodTO.getCocurricularDetailId()!=null && !periodTO.getCocurricularDetailId().isEmpty())
							{
								cocurrLeaveDetails.setId(Integer.parseInt(periodTO.getCocurricularDetailId()));
							}
							cocurrLeaveDetails.setStudent(student);
							cocurricularLeavedetailsSet.add(cocurrLeaveDetails);
							cocurrLeave.setStuCocurrLeaveDetailses(cocurricularLeavedetailsSet);
							if(periodTO.getMailAttId()!=null && periodTO.getAttStudentId()!=null&& !periodTO.getMailAttId().isEmpty() && !periodTO.getAttStudentId().isEmpty() )
							{
								approveLeaveTO.setAttMainId(periodTO.getMailAttId());
								approveLeaveTO.setAttStudentId(periodTO.getAttStudentId());
								approveLeaveTO.setIsCocurricularAttendance(false);
								approveLeaveTOList.add(approveLeaveTO);
							}
							// new modification
							cocurrLeave.setApprovedByTeacher(Boolean.FALSE);
						
							cocurrLeave.setCancelledByTeacher(Boolean.FALSE);
							Subject subject = new Subject();
							subject.setId(Integer.parseInt(periodTO.getSubjectId()));
							cocurrLeave.setSubject(subject);
							cocurricularList.add(cocurrLeave);
						}
					}
				}
				
				
			}
			
			
		}
		catch (Exception e) {
			log.error("Error in createBoObject  method method in CocurricularAttendnaceEntryHelper.class");
			log.error("Error is"+e.getMessage());
			throw new ApplicationException(e);
		}
		log.debug("end of createBoObject  method method in CocurricularAttendnaceEntryHelper.class");
		if(approveLeaveTOList.size()>0 && approveLeaveTOList!=null)
		{
			extraCocurricularLeaveEntryForm.setApproveLeaveTOs(approveLeaveTOList);
		}
		return cocurricularList;
	}

}
