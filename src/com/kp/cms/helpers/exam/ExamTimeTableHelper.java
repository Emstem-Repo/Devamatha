package com.kp.cms.helpers.exam;

/**
 * Feb 2, 2010 Created By 9Elements Team
 */

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamTimeTableBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.exam.ExamSubjectTimeTableTO;
import com.kp.cms.to.exam.ExamTimeTableTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("deprecation")
public class ExamTimeTableHelper extends ExamGenHelper {

	// To get the exam name for a particular exam type
	public ArrayList<KeyValueTO> convertBOToTO_ExamNameList(
			ArrayList<ExamDefinitionBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamDefinitionBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));

		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	// To get the first page details
	@SuppressWarnings("unchecked")
	public ArrayList<ExamTimeTableTO> convertBOToTO_details(ArrayList<Object[]> select_details) throws BusinessException, ApplicationException {
		ExamTimeTableTO to;
		ArrayList<ExamTimeTableTO> retutnList = new ArrayList<ExamTimeTableTO>();
		Iterator itr = select_details.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamTimeTableTO();
			if (row[0] != null) {
				to.setId((Integer) row[0]);
			}
			to.setExamType(row[1].toString());
			if (row[2] != null) 
			{
				StringTokenizer str=new StringTokenizer(row[2].toString(),"-");
				if(str.hasMoreTokens())
				{
					to.setBatch(str.nextToken());
				}
				else
				{
					if (Integer.parseInt(row[2].toString()) <= 0)
						to.setBatch("");
					else {
						to.setBatch(row[2].toString());
					}
				}	
			} 
			to.setProgram(row[3].toString());
			to.setCourse(row[4].toString());
			to.setScheme(row[5].toString());
			if (row[6] != null) {
				to.setCourseId((Integer) row[6]);
			}
			if (row[7] != null) {
				to.setSchemeId((Integer) row[7]);
			}
			if (row[8] != null) {
				to.setAcademicyear((Integer) row[8]);
			}
			if (row[9] != null) {
				to.setExamId((Integer) row[9]);
			}
// commented by manu.(two times hit database for each iteration then check status=pending condition)
		/*	to.setStatus(h.decide_status((Integer) row[0], (Integer) row[6],
					(Integer) row[7], (Integer) row[5], (Integer) row[8]));*/
			
			String status = "Pending";
			if (row[0] != null && row[6]!= null && row[7] != null && row[5] != null && row[8] != null) 
			{
				        if(((BigInteger) row[11]).intValue()== 0)
				       {
						status="No Curriculum Subject";
						
					   }else if ( ((BigInteger) row[10]).intValue() == ((BigInteger) row[11]).intValue()) 
					   {
						status = "Completed";
					   }
					   else if (((BigInteger) row[10]).intValue()== 0) 
					   {
						status = "Pending";
					   }
					   else 
					   {
						status = "Created";
					  }
			  
			}
			if (row[12] != null) {
				to.setJoiningBatch(row[12].toString());
			}
			to.setStatus(status);
			
			if(!to.getStatus().equalsIgnoreCase("No Curriculum Subject")){
				retutnList.add(to);
			}
		}
		return retutnList;
	}

	// To get the subjects for a particular course and scheme

	@SuppressWarnings("unchecked")
	public ArrayList<ExamSubjectTimeTableTO> convertBOToTO_SubjectsList(
			List<Object[]> select_Subjects) throws BusinessException {
		ExamSubjectTimeTableTO to;
		ArrayList<ExamSubjectTimeTableTO> retutnList = new ArrayList<ExamSubjectTimeTableTO>();
		Iterator itr = select_Subjects.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamSubjectTimeTableTO();
			to.setSubid(Integer.parseInt(row[0].toString()));
			to.setSubjectName((String) row[1]);
			to.setSubjectCode((String) row[2]);
			if (row[4] != null) {

				String startTime = row[4].toString().substring(0,
						row[4].toString().indexOf("."));
				// 2010-02-27 05:05:00
				String formatDate = CommonUtil.ConvertStringToDateFormat(
						startTime, "yyyy-MM-dd hh:mm:ss", "M/d/yyyy h:mm:ss a");
				Date d = new Date(formatDate);
				StringTokenizer s = new StringTokenizer(formatDate);
				String amOrPm = "";
				int addAmorPm = 0;
				while (s.hasMoreElements()) {
					amOrPm = (String) s.nextElement();

				}

				SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat pattern1 = new SimpleDateFormat("hh");
				SimpleDateFormat pattern2 = new SimpleDateFormat("mm");

				String date1 = pattern.format(d);
				String hr = pattern1.format(d);
				String min = pattern2.format(d);
				if (amOrPm.equalsIgnoreCase("PM")) {
					if (hr != null && hr.trim().length() > 0)
						addAmorPm = Integer.parseInt(hr) + 12;
				}
				to.setDate(date1);
				if (addAmorPm > 0) {
					to.setStartTimeHour(Integer.toString(addAmorPm));
				} else {
					to.setStartTimeHour(hr);
				}
				to.setStartTimeMin(min);
			}
			if (row[3] != null) {
				String endTime = row[3].toString().substring(0,
						row[3].toString().indexOf("."));
				String formatDate = CommonUtil.ConvertStringToDateFormat(
						endTime, "yyyy-MM-dd hh:mm:ss", "M/d/yyyy h:mm:ss a");
				Date d = new Date(formatDate);
				StringTokenizer s = new StringTokenizer(formatDate);
				String amOrPm = "";
				int addAmorPm = 0;
				while (s.hasMoreElements()) {
					amOrPm = (String) s.nextElement();

				}
				SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat pattern1 = new SimpleDateFormat("hh");
				SimpleDateFormat pattern2 = new SimpleDateFormat("mm");

				String date1 = pattern.format(d);
				String hr = pattern1.format(d);
				String min = pattern2.format(d);
				if (amOrPm.equalsIgnoreCase("PM")) {
					if (hr != null && hr.trim().length() > 0)
						addAmorPm = Integer.parseInt(hr) + 12;
				}
				to.setDate(date1);
				if (addAmorPm > 0) {
					to.setEndTimeHour(Integer.toString(addAmorPm));
				} else {
					to.setEndTimeHour(hr);
				}
				to.setEndTimeMin(min);
			}
			to.setId((Integer) row[5]);
			retutnList.add(to);
		}
		Collections.sort(retutnList);
		return retutnList;
	}
	
	
	
	public ArrayList<ExamSubjectTimeTableTO> convertBOToTOSubjectsList(List<Subject> subjectList,Map<Integer,ExamTimeTableBO> timeTableMap) throws BusinessException {
		ExamSubjectTimeTableTO to;
		ArrayList<ExamSubjectTimeTableTO> retutnList = new ArrayList<ExamSubjectTimeTableTO>();
		Iterator<Subject> itr=subjectList.iterator();
		while (itr.hasNext()) {
			Subject subject = (Subject) itr.next();
			to=new ExamSubjectTimeTableTO();
			to.setSubid(subject.getId());
			to.setSubjectCode(subject.getCode());
			to.setSubjectName(subject.getName());
			if(timeTableMap.containsKey(subject.getId())){
				ExamTimeTableBO bo=timeTableMap.get(subject.getId());
				
				if (bo.getDateStarttime() != null) {
					String startDate=bo.getDateStarttime().toString();
					String startTime = startDate.substring(0,
							startDate.indexOf("."));
					// 2010-02-27 05:05:00
					String formatDate = CommonUtil.ConvertStringToDateFormat(
							startTime, "yyyy-MM-dd hh:mm:ss", "M/d/yyyy h:mm:ss a");
					Date d = new Date(formatDate);
					StringTokenizer s = new StringTokenizer(formatDate);
					String amOrPm = "";
					int addAmorPm = 0;
					while (s.hasMoreElements()) {
						amOrPm = (String) s.nextElement();

					}

					SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat pattern1 = new SimpleDateFormat("hh");
					SimpleDateFormat pattern2 = new SimpleDateFormat("mm");

					String date1 = pattern.format(d);
					String hr = pattern1.format(d);
					String min = pattern2.format(d);
					if (amOrPm.equalsIgnoreCase("PM")) {
						if (hr != null && hr.trim().length() > 0)
							addAmorPm = Integer.parseInt(hr) + 12;
					}
					to.setDate(date1);
					if (addAmorPm > 0) {
						to.setStartTimeHour(Integer.toString(addAmorPm));
					} else {
						to.setStartTimeHour(hr);
					}
					to.setStartTimeMin(min);
				}
				if (bo.getDateEndtime() != null) {
					String endDate=bo.getDateEndtime().toString();
					String endTime = endDate.substring(0,
							endDate.indexOf("."));
					String formatDate = CommonUtil.ConvertStringToDateFormat(
							endTime, "yyyy-MM-dd hh:mm:ss", "M/d/yyyy h:mm:ss a");
					Date d = new Date(formatDate);
					StringTokenizer s = new StringTokenizer(formatDate);
					String amOrPm = "";
					int addAmorPm = 0;
					while (s.hasMoreElements()) {
						amOrPm = (String) s.nextElement();
					}
					SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat pattern1 = new SimpleDateFormat("hh");
					SimpleDateFormat pattern2 = new SimpleDateFormat("mm");

					String date1 = pattern.format(d);
					String hr = pattern1.format(d);
					String min = pattern2.format(d);
					if (amOrPm.equalsIgnoreCase("PM")) {
						if (hr != null && hr.trim().length() > 0)
							addAmorPm = Integer.parseInt(hr) + 12;
					}
					to.setDate(date1);
					if (addAmorPm > 0) {
						to.setEndTimeHour(Integer.toString(addAmorPm));
					} else {
						to.setEndTimeHour(hr);
					}
					to.setEndTimeMin(min);
				}
				if(bo.getExaminationSessions() != null){
					to.setSessionId(String.valueOf(bo.getExaminationSessions().getId()));
				}
				to.setId(bo.getId());
			}
			retutnList.add(to);
		}
		Collections.sort(retutnList);
		return retutnList;
	}
}
