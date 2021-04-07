package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamRoomMasterBO;
import com.kp.cms.to.exam.ExamAssignStuInvTO;
import com.kp.cms.to.exam.ExamAssignStudentsToRoomTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("unchecked")
public class ExamAssignStudentsToRoomHelper {

	public List<KeyValueTO> convertMapToList_ExamType(Map<Integer, String> map) throws Exception {
		List<KeyValueTO> examTypeList = new ArrayList<KeyValueTO>();
		Iterator<Integer> ids = map.keySet().iterator();
		String name;
		while (ids.hasNext()) {
			Integer id = ids.next();
			name = map.remove(id);
			examTypeList.add(new KeyValueTO(id, name));
		}
		Collections.sort(examTypeList,new KeyValueTOComparator());
		return examTypeList;
	}

	public List<KeyValueTO> convertBOToTO_Room_List(
			List<ExamRoomMasterBO> listBO) throws Exception {
		ArrayList<KeyValueTO> list = new ArrayList<KeyValueTO>();
		Iterator<ExamRoomMasterBO> itr = listBO.iterator();
		while (itr.hasNext()) {
			ExamRoomMasterBO bo = (ExamRoomMasterBO) itr.next();
			list.add(new KeyValueTO(bo.getId(), bo.getRoomNo()));
		}
		Collections.sort(list,new KeyValueTOComparator());
		return list;
	}

	// display search details

	public ArrayList<ExamAssignStudentsToRoomTO> convertBOToTO_Room_ListValues(
			List listBO, int examTypeId, int examId, String examName,
			String date, String timeStr) throws Exception {
		ArrayList<ExamAssignStudentsToRoomTO> list = new ArrayList<ExamAssignStudentsToRoomTO>();

		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			int capacity = 0;
			if (examTypeId == 5 || examTypeId == 6) {
				capacity = Integer.parseInt(row[5].toString());
			} else {
				capacity = Integer.parseInt(row[3].toString());
			}

			list.add(new ExamAssignStudentsToRoomTO(Integer.parseInt(row[0]
					.toString()), row[2].toString(), examId, row[1].toString(),
					capacity, Integer.parseInt(row[4].toString()), date,
					timeStr));

		}
		Collections.sort(list);
		return list;

	}

	public List<ExamAssignStudentsToRoomTO> convertBOToTO_Examinars_List(
			List listExaminers) throws Exception {
		ArrayList<ExamAssignStudentsToRoomTO> list = new ArrayList<ExamAssignStudentsToRoomTO>();
		Iterator itr = listExaminers.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			int id = 0;
			if (row[0] != null) {
				id = Integer.parseInt(row[0].toString());
			}
			String name1 = "";
			if (row[1] != null) {
				name1 = row[1].toString();
			}
			String name2 = "";
			if (row[2] != null) {
				name2 = row[2].toString();
			}
			list.add(new ExamAssignStudentsToRoomTO(id, name1, name2));
		}
		Collections.sort(list);
		return list;
	}

	public List<ExamAssignStudentsToRoomTO> convertBOToTO_Students_List(
			List listStudent,int examId) throws Exception {
		ArrayList<ExamAssignStudentsToRoomTO> list = new ArrayList<ExamAssignStudentsToRoomTO>();
		if(listStudent!=null && !listStudent.isEmpty()){
			Map<String,String> oldRegMap=getOldRegMapForExam(examId);
			Iterator itr = listStudent.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				boolean flag = false;
				int id = 0,schemeNo=0,studentId=0;
				String className = "", subject = "", registerNo = "", rollNo = "", name = "";
				if (row[0] != null) {
					id = Integer.parseInt(row[0].toString());
				}
				if (row[1] != null) {
					className = row[1].toString();
				}
				if (row[2] != null) {
					subject = row[2].toString();
				}
				if (row[3] != null) {
					registerNo = row[3].toString();
				}
				if (row[4] != null) {
					rollNo = row[4].toString();
				}
				if (row[5] != null) {
					name = row[5].toString();
				}
				if ((row[6]).toString().equals("1")) {
					flag = true;
				}
				if ((row[8])!=null) {
					schemeNo=Integer.parseInt(row[8].toString());
				}
				if ((row[9])!=null) {
					studentId=Integer.parseInt(row[9].toString());
				}
				if(oldRegMap.containsKey(studentId+"_"+schemeNo))
					registerNo=oldRegMap.get(studentId+"_"+schemeNo);
				
				list.add(new ExamAssignStudentsToRoomTO(id, className, subject,
						registerNo, rollNo, name, flag));
			}
		}
		Collections.sort(list);
		return list;
	}

	/**
	 * @param examId
	 * @return
	 */
	private Map<String, String> getOldRegMapForExam(int examId)  throws Exception{
		String detainQuery="select e.schemeNo from ExamExamCourseSchemeDetailsBO e where e.examDefinitionBO.examTypeUtilBO.name  like '%Suppl%' and e.examId="+examId+" group by e.schemeNo";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Integer> schemeList=transaction.getDataForQuery(detainQuery);
		return transaction.getOldRegMap(schemeList);
	}

	/**
	 * @param subjects
	 * @return
	 */
	public Map<Integer, String> convertBOToTO(List subjects) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator itr = subjects.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			int id = 0;
			if (row[0] != null) {
				id = Integer.parseInt(row[0].toString());
			}
			String name1 = "";
			if (row[1] != null) {
				name1 = row[1].toString();
			}
			String name2 = "";
			if (row[2] != null) {
				name2 = row[2].toString();
			}
			String name = name1 + " " + name2;
			map.put(id, name);

		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	public List<ExamAssignStuInvTO> convertBOToTOInvList(List listInv,
			int examid) throws Exception {
		ArrayList<ExamAssignStuInvTO> invigilatorList = new ArrayList<ExamAssignStuInvTO>();

		Iterator itr = listInv.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			boolean flag = false;
			if ((row[3]).toString().equals("1")) {
				flag = true;

			}
			invigilatorList.add(new ExamAssignStuInvTO(Integer.parseInt(row[0]
					.toString()), Integer.parseInt(row[1].toString()), examid,
					row[2].toString(), flag, ""));
		}
		Collections.sort( invigilatorList);
		return invigilatorList;
	}

	/**
	 * @param studentList
	 * @param noOfStudents
	 * @param listClass
	 * @return
	 * @throws Exception
	 */
	public List<ExamAssignStudentsToRoomTO> convertBOToTO_Students_forSubjects( List studentList, int noOfStudents,List<Integer> listClass) throws Exception {
		ArrayList<ExamAssignStudentsToRoomTO> list = new ArrayList<ExamAssignStudentsToRoomTO>();
		int i = 0;
		if (studentList != null && studentList.size() > 0) {
			Map<String,String> oldRegMap=getOldRegMap(listClass);
			Iterator itr = studentList.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (noOfStudents == i) {
					break;
				}

				int id = 0, subjectId = 0, classId = 0,schemeNo=0;
				String className = "", registerNo = "", rollNo = "", name = "", subjectName = "";

				if (row[3] != null) {
					id = Integer.parseInt(row[3].toString());
				}
				if (row[1] != null) {
					subjectId = Integer.parseInt(row[1].toString());
				}
				if (row[0] != null) {

					className = row[0].toString();
				}
				if (row[2] != null) {

					subjectName = row[2].toString();
				}
				if (row[4] != null) {
					registerNo = row[4].toString();
				}
				if (row[5] != null) {
					rollNo = row[5].toString();
				}
				if (row[6] != null) {
					name = row[6].toString();
				}
				if (row[7] != null) {
					classId = Integer.parseInt(row[7].toString());
				}
				if (row[8] != null) {
					schemeNo = Integer.parseInt(row[8].toString());
				}
				if(oldRegMap.containsKey(id+"_"+schemeNo)){
					registerNo=oldRegMap.get(id+"_"+schemeNo);
				}
				list.add(new ExamAssignStudentsToRoomTO(id, classId, className,
						subjectId, subjectName, registerNo, rollNo, name));

				i = i + 1;
			}
		}
		Collections.sort(list);
		return list;

	}

	/**
	 * @param listClass
	 * @return
	 */
	private Map<String, String> getOldRegMap(List<Integer> listClass) throws Exception {
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		String cid="";
		for (Iterator iterator = listClass.iterator(); iterator.hasNext();) {
			Integer id= (Integer) iterator.next();
			if(cid.isEmpty())
				cid=String.valueOf(id);
			else
				cid=cid+","+id;
		}
		String query="select c.termNumber from Classes c where c.id in ("+cid+") group by c.termNumber";
		List<Integer> schemeList=transaction.getDataForQuery(query);
		return transaction.getOldRegMap(schemeList);
	}

	public List<ExamAssignStudentsToRoomTO> convertBOToTO_Students(
			List studentList, int noOfStudents) throws Exception {
		ArrayList<ExamAssignStudentsToRoomTO> list = new ArrayList<ExamAssignStudentsToRoomTO>();
		int i = 0;
		if (studentList != null && studentList.size() > 0) {

			Iterator itr = studentList.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				if (noOfStudents == i) {
					break;
				}
				boolean flag = false;

				int id = 0, classId = 0;
				String className = "", registerNo = "", rollNo = "", name = "";
				if (row[0] != null) {
					id = Integer.parseInt(row[0].toString());
				}
				if (row[1] != null) {
					classId = Integer.parseInt(row[1].toString());
				}
				if (row[2] != null) {

					className = row[2].toString();
				}
				if (row[3] != null) {
					registerNo = row[3].toString();
				}
				if (row[4] != null) {
					rollNo = row[4].toString();
				}
				if (row[5] != null) {
					name = row[5].toString();
				}

				list.add(new ExamAssignStudentsToRoomTO(id, classId, className,
						registerNo, rollNo, name, flag, ""));

				i = i + 1;
			}
		}
		Collections.sort(list);
		return list;
	}

	public ArrayList<Integer> convertToList(List roomIds) throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator itr = roomIds.iterator();
		while (itr.hasNext()) {
			Integer a = (Integer) itr.next();
			list.add(a);
		}
		Collections.sort(list);
		return list;
	}

	public Map<Integer, String> convertBoToToExam(List examName) throws Exception {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		Iterator itr = examName.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			map.put(Integer.parseInt(row[0].toString()), row[1].toString());

		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

}
