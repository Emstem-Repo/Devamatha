package com.kp.cms.handlers.examallotment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.bo.examallotment.StudentClassGroup;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentTO;
import com.kp.cms.to.studentfeedback.RoomEndMidSemRowsTo;
import com.kp.cms.transactions.examallotment.IExamRoomAllotment;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamRoomAllotmentHandler {
	
	private static volatile ExamRoomAllotmentHandler handler=null;
	
	public static ExamRoomAllotmentHandler getInstance(){
		if(handler == null){
			handler = new ExamRoomAllotmentHandler();
		}
		return handler;
	}
	private ExamRoomAllotmentHandler(){
		
	}
	IExamRoomAllotment transaction = ExamRoomAllotmentImpl.getInstance();
	/**
	 * @param allotmentForm 
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentTO> getCourcesForAllotment(ExamRoomAllotmentForm allotmentForm) throws Exception{
		List<ExamRoomAllotmentTO> tos = new ArrayList<ExamRoomAllotmentTO>();
		List<Object[]> courses = transaction.getCourses(allotmentForm.getCycleId(),allotmentForm);
		
		if(courses != null){
			for (Object[] bo : courses) {
				if(bo[0] != null && bo[1] != null && bo[2] != null && bo[3] != null){
					ExamRoomAllotmentTO to = new ExamRoomAllotmentTO();
					to.setCourseId(Integer.parseInt(bo[0].toString()));
					to.setCourseName(bo[1].toString());
					to.setPoolId(Integer.parseInt(bo[2].toString()));
					to.setSemNo(bo[3].toString());
					tos.add(to);
					if(bo[4] != null)
						allotmentForm.setMidOrEndSem(bo[4].toString());
					if(bo[5] != null)
						allotmentForm.setSessionTime(bo[5].toString());
					if(bo[6] != null)
						allotmentForm.setExamSessionId(bo[6].toString());
				}
			}
		}
		return tos;
	}
	/**
	 * @param allotmentForm
	 * @throws Exception
	 */
	public void allotRoomsForStudent(ExamRoomAllotmentForm allotmentForm) throws Exception{
		if(allotmentForm.getAllotmentList() != null){
			Map<Integer, List<String>> poolWiseMap = new HashMap<Integer, List<String>>();
			Map<String, Integer> coursePoolMap = new HashMap<String, Integer>();
			Set<Integer> totalPools = new HashSet<Integer>();
			Map<String, List<Integer>> semWiseCourses = new HashMap<String, List<Integer>>();
			for (ExamRoomAllotmentTO to : allotmentForm.getAllotmentList()) {
				if(to.getChecked() != null && to.getChecked().equalsIgnoreCase("on")){
					List<Integer> totalCourses = new ArrayList<Integer>();
					if(semWiseCourses.containsKey(to.getSemNo())){
						totalCourses = semWiseCourses.get(to.getSemNo());
					}
					totalCourses.add(to.getCourseId());
					semWiseCourses.put(to.getSemNo(), totalCourses);
					coursePoolMap.put(to.getCourseId()+"_"+to.getSemNo(), to.getPoolId());
					totalPools.add(to.getPoolId());
				}
			}
			
			List<Student> students = transaction.getStudentsForCourse(allotmentForm,semWiseCourses);
			
			if(students != null){
				Map<String, Map<String, ExamRoomAllotmentDetailsTO>> courseWiseMap = new HashMap<String, Map<String, ExamRoomAllotmentDetailsTO>>();
				List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
				Map<String, Integer> totalStudentCourseWise = new HashMap<String, Integer>();
				Iterator<Student> iterator = students.iterator();
				while (iterator.hasNext()) {
					Student student = (Student) iterator.next();
					if(!listOfDetainedStudents.contains(student.getId())){
						if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null 
								&& student.getClassSchemewise().getClasses().getCourse() != null){
							Map<String, ExamRoomAllotmentDetailsTO> studentList = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							int count=0;
							if(totalStudentCourseWise.containsKey(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getTermNumber())){
								if(totalStudentCourseWise.get(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getTermNumber()) != null)
									count = totalStudentCourseWise.get(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getTermNumber());
							}
							count = count + 1;
							totalStudentCourseWise.put(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getTermNumber(), count);
							if(courseWiseMap.containsKey(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getTermNumber())){
								studentList = courseWiseMap.get(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getTermNumber());
							}
							ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
							to.setStudentId(student.getId());
							to.setClassId(student.getClassSchemewise().getClasses().getId());
							studentList.put(student.getClassSchemewise().getClasses().getName()+student.getRegisterNo(), to);
							courseWiseMap.put(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getTermNumber(), studentList);
						}
					}
				}
				List<Object[]> examDetailsCourseWise = transaction.getMostNoOfExamCourses(allotmentForm,semWiseCourses);
				if(examDetailsCourseWise != null && !examDetailsCourseWise.isEmpty()){
					if(allotmentForm.getProgramType() != null && allotmentForm.getProgramType().equalsIgnoreCase("Yes")){
						
						Map<Integer, Map<Integer, Map<Integer, Map<Integer,Integer>>>>	 programTypeWiseMap = new TreeMap<Integer, Map<Integer, Map<Integer, Map<Integer,Integer>>>>();
						
						for (Object[] objects : examDetailsCourseWise) {
							if(objects[0] != null && objects[1] != null && objects[2] != null && objects[3] != null){
								
								Map<Integer, Map<Integer, Map<Integer,Integer>>> examWiseMap = new HashMap<Integer, Map<Integer,Map<Integer,Integer>>>();
								
								if(programTypeWiseMap.containsKey(Integer.parseInt(objects[3].toString()))){
									examWiseMap = programTypeWiseMap.get(Integer.parseInt(objects[3].toString()));
								}
								Map<Integer, Map<Integer,Integer>> semWiseMap = new HashMap<Integer, Map<Integer,Integer>>();
								if(examWiseMap.containsKey(Integer.parseInt(objects[2].toString()))){
									semWiseMap = examWiseMap.get(Integer.parseInt(objects[2].toString()));
								}
								Map<Integer,Integer> subMap = new HashMap<Integer, Integer>();
								if(semWiseMap.containsKey(Integer.parseInt(objects[1].toString())))
									subMap = semWiseMap.get(Integer.parseInt(objects[1].toString()));
								if(totalStudentCourseWise.get(objects[0].toString()+"_"+objects[1].toString()) != null){
									subMap.put(Integer.parseInt(objects[0].toString()), totalStudentCourseWise.get(objects[0].toString()+"_"+objects[1].toString()));
								}else{
									subMap.put(Integer.parseInt(objects[0].toString()), 0);
								}
								subMap = CommonUtil.sortMapByValueDesc(subMap);
								semWiseMap.put(Integer.parseInt(objects[1].toString()), subMap);
								semWiseMap = CommonUtil.sortMapByKeyDesc(semWiseMap);
								examWiseMap.put(Integer.parseInt(objects[2].toString()), semWiseMap);
								examWiseMap = CommonUtil.sortMapByKeyDesc(examWiseMap);
								programTypeWiseMap.put(Integer.parseInt(objects[3].toString()), examWiseMap);
							}
						}
						Map<Integer, List<String>> poolWise = new HashMap<Integer, List<String>>();
						if(programTypeWiseMap != null){
							for (Entry<Integer, Map<Integer, Map<Integer, Map<Integer, Integer>>>> programTypeMap : programTypeWiseMap.entrySet()) {
								for (Entry<Integer, Map<Integer, Map<Integer, Integer>>> exammap : programTypeMap.getValue().entrySet()) {
									for (Entry<Integer, Map<Integer, Integer>> semMap : exammap.getValue().entrySet()) {
										for (Entry<Integer, Integer> courseMap : semMap.getValue().entrySet()) {
											
											List<String> courseList = new ArrayList<String>();
											if(poolWise.containsKey(coursePoolMap.get(courseMap.getKey()+"_"+semMap.getKey()))){
												courseList = poolWise.get(coursePoolMap.get(courseMap.getKey()+"_"+semMap.getKey()));
											}
											courseList.add(courseMap.getKey()+"_"+semMap.getKey());
											poolWise.put(coursePoolMap.get(courseMap.getKey()+"_"+semMap.getKey()), courseList);
										}
									}
								}
							}
						}
						if(totalPools != null){
							int poolCount=1;
							for (Integer poolId : totalPools) {
								poolWiseMap.put(poolCount, poolWise.get(poolId));
								poolCount=poolCount+1;
							}
						}
					}else{
						if(examDetailsCourseWise != null){
							Map<Integer, Map<Integer, Map<Integer,Integer>>> examWiseMap = new HashMap<Integer, Map<Integer,Map<Integer,Integer>>>();
							
							for (Object[] objects : examDetailsCourseWise) {
								if(objects[0] != null && objects[1] != null && objects[2] != null){
									Map<Integer, Map<Integer,Integer>> semWiseMap = new HashMap<Integer, Map<Integer,Integer>>();
									if(examWiseMap.containsKey(Integer.parseInt(objects[2].toString()))){
										semWiseMap = examWiseMap.get(Integer.parseInt(objects[2].toString()));
									}
									Map<Integer,Integer> subMap = new HashMap<Integer, Integer>();
									if(semWiseMap.containsKey(Integer.parseInt(objects[1].toString())))
										subMap = semWiseMap.get(Integer.parseInt(objects[1].toString()));
									subMap.put(Integer.parseInt(objects[0].toString()), totalStudentCourseWise.get(objects[0].toString()+"_"+objects[1].toString()));
									subMap = CommonUtil.sortMapByValueDesc(subMap);
									semWiseMap.put(Integer.parseInt(objects[1].toString()), subMap);
									examWiseMap.put(Integer.parseInt(objects[2].toString()), semWiseMap);
								}
							}
							Map<Integer, List<String>> poolWise = new HashMap<Integer, List<String>>();
							if(examWiseMap != null){
								for (Entry<Integer, Map<Integer, Map<Integer, Integer>>> exammap : examWiseMap.entrySet()) {
									for (Entry<Integer, Map<Integer, Integer>> semMap : exammap.getValue().entrySet()) {
										for (Entry<Integer, Integer> courseMap : semMap.getValue().entrySet()) {
											
											List<String> courseList = new ArrayList<String>();
											if(poolWise.containsKey(coursePoolMap.get(courseMap.getKey()+"_"+semMap.getKey()))){
												courseList = poolWise.get(coursePoolMap.get(courseMap.getKey()+"_"+semMap.getKey()));
											}
											courseList.add(courseMap.getKey()+"_"+semMap.getKey());
											poolWise.put(coursePoolMap.get(courseMap.getKey()+"_"+semMap.getKey()), courseList);
										}
									}
								}
							}
							if(totalPools != null){
								int poolCount=1;
								for (Integer poolId : totalPools) {
									poolWiseMap.put(poolCount, poolWise.get(poolId));
									poolCount=poolCount+1;
								}
							}
						}
					}
				}
				List<ExamRoomAllotment> allotmentList = studentAllotmentCourseWise(courseWiseMap,poolWiseMap,allotmentForm);
				transaction.saveAllotment(allotmentList,allotmentForm);
			}
		}
	}
	/**
	 * @param courseWiseMap
	 * @param totalStudentCourseWise
	 * @param allotmentForm 
	 * @return
	 * @throws Exception
	 */
	private List<ExamRoomAllotment> studentAllotmentCourseWise(Map<String, Map<String, ExamRoomAllotmentDetailsTO>> courseWiseMap,
			Map<Integer, List<String>> poolWiseMap, ExamRoomAllotmentForm allotmentForm) throws Exception{
		List<ExamRoomAllotment> boList = new ArrayList<ExamRoomAllotment>();
		if(poolWiseMap != null && courseWiseMap != null){
			transaction.removeRoomAllotment(allotmentForm);
			List<RoomMaster> roomsList = transaction.getAvailableRooms(allotmentForm);
			List<ExamRoomAllotmentDetailsTO> roomList = createMapForRooms(roomsList,allotmentForm);
			allotmentForm.setRoomList(roomList);
			int allotmentCount = transaction.getNoOfStudentAllotInSameRoom();
			int poolCount = 1;
			List<String> poolOneCourses = poolWiseMap.get(poolCount);
			poolCount = poolCount +1;
			List<String> poolTwoCourses = new ArrayList<String>();
			if(poolWiseMap.get(poolCount) != null && !poolWiseMap.get(poolCount).isEmpty()){
				poolTwoCourses = poolWiseMap.get(poolCount);
				poolCount = poolCount +1;
			}
			
			int poolOneCourseCount =0;
			int poolTwoCourseCount =0;
			
			// students for one course 
			Map<String, ExamRoomAllotmentDetailsTO> studentMapP1 = courseWiseMap.get(poolOneCourses.get(poolOneCourseCount));
			poolOneCourseCount = poolOneCourseCount +1;
			Map<String, ExamRoomAllotmentDetailsTO> studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
			if(!poolTwoCourses.isEmpty() && courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null && !courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)).isEmpty()){
				studentMapP2 = courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount));
				poolTwoCourseCount = poolTwoCourseCount+1;
			}else{
				if(poolOneCourses.size()>poolOneCourseCount){
					studentMapP2 = courseWiseMap.get(poolOneCourses.get(poolOneCourseCount));
					poolOneCourseCount = poolOneCourseCount +1;
				}
			}
			List<Integer> allotedRooms = new ArrayList<Integer>();
			for (ExamRoomAllotmentDetailsTO room : roomList) {
				boolean breakLoop = false;
				if(!(poolOneCourses.size()>poolOneCourseCount) && !(poolTwoCourses.size()>poolTwoCourseCount)){
					if(!(poolWiseMap.size()>=poolCount)){
						breakLoop = true;
					}
				}
				
				if(studentMapP1.isEmpty() && studentMapP2.isEmpty() &&  breakLoop){
					break;
				}
				allotedRooms.add(room.getId());
				// Bo creation
				ExamRoomAllotment roomAllotment = new ExamRoomAllotment();
				Set<ExamRoomAllotmentDetails> roomAllotmentDetailsSet = new HashSet<ExamRoomAllotmentDetails>();
				
				ExamRoomAllotmentCycle cycle = new ExamRoomAllotmentCycle();
				cycle.setId(Integer.parseInt(allotmentForm.getCycleId()));
				roomAllotment.setCycle(cycle );
				ExamDefinition examDefinition = new ExamDefinition();
				examDefinition.setId(Integer.parseInt(allotmentForm.getExamId()));
				roomAllotment.setExamDefinition(examDefinition );
				roomAllotment.setMidOrEndSem(allotmentForm.getMidOrEndSem());
				RoomMaster roomMaster = new RoomMaster();
				roomMaster.setId(room.getId());
				roomAllotment.setRoom(roomMaster);
				roomAllotment.setCreatedBy(allotmentForm.getUserId());
				roomAllotment.setCreatedDate(new Date());
				roomAllotment.setModifiedBy(allotmentForm.getUserId());
				roomAllotment.setLastModifiedDate(new Date());
				roomAllotment.setIsActive(true);
				
				Boolean isPoolOne= true;
				boolean allotInSameRoom = false;
				boolean repeateMap1 = false;
				boolean repeateMap2 = false;
				boolean allotInSameRoomForMap2 = false;
				boolean isLastRow = false;
				for (RoomEndMidSemRowsTo roomDetailsTo : room.getColumnWiseRows()) {
					if(roomDetailsTo.isLastColumns() && allotmentCount != 0){
						// if studentMap1 or studentMap2 contains less student then allocate in same room .
						if(allotInSameRoom){
							allotInSameRoom = false;
							isPoolOne = null;
							if(!studentMapP1.isEmpty()){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
							}else if(!studentMapP2.isEmpty()){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
								
							}
						}
						
						// repeating Map1 in same room if student map contains less students .
						if(repeateMap1){
							repeateMap1 = false;
							isPoolOne = null;
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP1.putAll(studentMapP1);
							studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
							int studentsCount =0;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP1.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMapP1 = tempstudentMapP1;
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP2.putAll(studentMapP2);
							studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP2.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMapP2 = tempstudentMapP2;
						}
						
						// repeating Map2 in same room if studentMap2 contains less students .
						if(allotInSameRoomForMap2){
							allotInSameRoomForMap2 = false;
							isPoolOne = null;
							int takeFromMap1 = roomDetailsTo.getNoOfRows()-(roomDetailsTo.getNoOfRows()-studentMapP2.size());
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP1.putAll(studentMapP1);
							studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
							int studentsCount =0;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
								if(studentsCount >= (roomDetailsTo.getNoOfRows()-takeFromMap1)){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP1.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMapP1 = tempstudentMapP1;
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP2.putAll(studentMapP2);
							studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
								repeateMap2 = true;
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP2.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMapP2 = tempstudentMapP2;
						}
						// repeat Map2 if studentMap2 contains less students.
						if(repeateMap2){
							repeateMap2 = false;
							isPoolOne = null;
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP2.putAll(studentMapP2);
							studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
							int studentsCount =0;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP2.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMapP2 = tempstudentMapP2;
						}
						// if studentMap1 students are less than studentMap2 allocate studentMap1 students in same room  
						if(studentMapP1.size()<studentMapP2.size() && !isLastRow){
							isLastRow = true;
							if(!studentMapP1.isEmpty()){
								if(roomDetailsTo.getNoOfRows()>studentMapP1.size()){
									if((roomDetailsTo.getNoOfRows()-studentMapP1.size()<allotmentCount)){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP1.putAll(studentMapP1);
										studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
										int studentsCount =0;
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP1.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP1 = tempstudentMapP1;
										if(studentMapP1.isEmpty()){
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP2.putAll(studentMapP2);
											studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
												allotInSameRoom = true;
												if(studentsCount >= roomDetailsTo.getNoOfRows()){
													break;
												}
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP2.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP2 = tempstudentMapP2;
										}
									}
								}else{
									if((studentMapP1.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){								
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP1.putAll(studentMapP1);
										studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
										int studentsCount =0;
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
											repeateMap1 = true;
											if(studentsCount >= roomDetailsTo.getNoOfRows()){
												break;
											}
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP1.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP1 = tempstudentMapP1;
									}
								}
							}
						}else if(!isLastRow){
							isLastRow = true;
							if(!studentMapP2.isEmpty()){
								if(roomDetailsTo.getNoOfRows()>studentMapP2.size()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMapP1);
									studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
										allotInSameRoomForMap2 = true;
										if(studentsCount >= roomDetailsTo.getNoOfRows()){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP1 = tempstudentMapP1;
								}else{
									if((studentMapP2.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){
										int takeFromMap1 = roomDetailsTo.getNoOfRows()-(studentMapP2.size()-roomDetailsTo.getNoOfRows());
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP1.putAll(studentMapP1);
										studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
										int studentsCount =0;
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
											if(studentsCount >= takeFromMap1){
												break;
											}
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP1.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP1 = tempstudentMapP1;
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP2.putAll(studentMapP2);
										studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
											repeateMap2 = true;
											if(studentsCount >= roomDetailsTo.getNoOfRows()){
												break;
											}
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP2.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP2 = tempstudentMapP2;
									}
								}
							}
						}
					}
					if(!allotInSameRoom && !allotInSameRoomForMap2 && !repeateMap1 && !repeateMap2){
						if(isPoolOne != null && isPoolOne){
							int noOfRows = 0;
							boolean isPoolCompleated = false;
							if(studentMapP1.size()<roomDetailsTo.getNoOfRows()){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									noOfRows = noOfRows + 1;
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
								
								if(poolOneCourses.size()>poolOneCourseCount){
									if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
										studentMapP1.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
									poolOneCourseCount = poolOneCourseCount +1;
								}else{
									if(poolWiseMap.size()>=poolCount){
										poolOneCourses = poolWiseMap.get(poolCount);
										poolCount = poolCount +1;
										poolOneCourseCount = 0;
										if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
											studentMapP1.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
										poolOneCourseCount = poolOneCourseCount +1;
									}else{
										if(poolTwoCourses.size()>poolTwoCourseCount){
											if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
												studentMapP1.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
											poolTwoCourseCount = poolTwoCourseCount +1;
										}else{
											studentMapP1.putAll(studentMapP2);
											isPoolCompleated = true;
										}
									}
								}
								if(studentMapP1.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP4 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP4.putAll(studentMapP1);
									studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP4.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP1 = tempstudentMapP4;
									if(isPoolCompleated){
										studentMapP2 = tempstudentMapP4;
										studentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									}
									if(poolOneCourses.size()>poolOneCourseCount){
										if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
											studentMapP1.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
										poolOneCourseCount = poolOneCourseCount +1;
									}else{
										if(poolWiseMap.size()>=poolCount){
											poolOneCourses = poolWiseMap.get(poolCount);
											poolCount = poolCount +1;
											poolOneCourseCount = 0;
											if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
												studentMapP1.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
											poolOneCourseCount = poolOneCourseCount +1;
										}else{
											if(poolTwoCourses.size()>poolTwoCourseCount){
												if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
													studentMapP1.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
												poolTwoCourseCount = poolTwoCourseCount +1;
											}else{
												studentMapP1.putAll(studentMapP2);
												isPoolCompleated = true;
											}
										}
									}
								}
								if(studentMapP1.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP4 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP4.putAll(studentMapP1);
									studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP4.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP1 = tempstudentMapP4;
									if(isPoolCompleated){
										studentMapP2 = tempstudentMapP4;
										studentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									}
										
									if(poolOneCourses.size()>poolOneCourseCount){
										if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
											studentMapP1.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
										poolOneCourseCount = poolOneCourseCount +1;
									}else{
										if(poolWiseMap.size()>=poolCount){
											poolOneCourses = poolWiseMap.get(poolCount);
											poolCount = poolCount +1;
											poolOneCourseCount = 0;
											if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
												studentMapP1.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
											poolOneCourseCount = poolOneCourseCount +1;
										}else{
											if(poolTwoCourses.size()>poolTwoCourseCount){
												if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
													studentMapP1.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
												poolTwoCourseCount = poolTwoCourseCount +1;
											}else{
												studentMapP1.putAll(studentMapP2);
												isPoolCompleated = true;
											}
										}
									}
								}
							}
							if(studentMapP1.isEmpty() && studentMapP2.isEmpty() && !isPoolCompleated){
								break;
							}
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP1.putAll(studentMapP1);
							studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
							int studentsCount = noOfRows;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP1.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMapP1 = tempstudentMapP1;
							if(isPoolCompleated){
								studentMapP2 = tempstudentMapP1;
								studentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							}
							isPoolOne = false;
						}else if(isPoolOne != null && !isPoolOne){
							int noOfRows = 0;
							boolean isPoolCompleated = false;
							if(studentMapP2.size()<roomDetailsTo.getNoOfRows()){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									noOfRows = noOfRows + 1;
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
								
								
								if(poolTwoCourses.size()>poolTwoCourseCount){
									if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
										studentMapP2.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
									poolTwoCourseCount = poolTwoCourseCount +1;
								}else{
									if(poolWiseMap.size()>=poolCount){
										poolTwoCourses = poolWiseMap.get(poolCount);
										poolCount = poolCount +1;
										poolTwoCourseCount = 0;
										if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
											studentMapP2.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
										poolTwoCourseCount = poolTwoCourseCount +1;
									}else{
										if(poolOneCourses.size()>poolOneCourseCount){
											if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
												studentMapP2.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
											poolOneCourseCount = poolOneCourseCount +1;
										}else{
											studentMapP2.putAll(studentMapP1);
											isPoolCompleated = true;
										}
									}
								}
								if(studentMapP2.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP3 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP3.putAll(studentMapP2);
									studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP3.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP2 = tempstudentMapP3;
									if(isPoolCompleated){
										studentMapP1 = tempstudentMapP3;
										studentMapP2 =  new HashMap<String, ExamRoomAllotmentDetailsTO>();
									}
									
									if(poolTwoCourses.size()>poolTwoCourseCount){
										if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
											studentMapP2.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
										poolTwoCourseCount = poolTwoCourseCount +1;
									}else{
										if(poolWiseMap.size()>=poolCount){
											poolTwoCourses = poolWiseMap.get(poolCount);
											poolCount = poolCount +1;
											poolTwoCourseCount = 0;
											if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
												studentMapP2.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
											poolTwoCourseCount = poolTwoCourseCount +1;
										}else{
											if(poolOneCourses.size()>poolOneCourseCount){
												if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
													studentMapP2.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
												poolOneCourseCount = poolOneCourseCount +1;
											}else{
												studentMapP2.putAll(studentMapP1);
												isPoolCompleated = true;
											}
										}
									}
								}
								if(studentMapP2.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP3 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP3.putAll(studentMapP2);
									studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP3.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP2 = tempstudentMapP3;
									if(isPoolCompleated){
										studentMapP1 = tempstudentMapP3;
										studentMapP2 =  new HashMap<String, ExamRoomAllotmentDetailsTO>();
									}
									
									if(poolTwoCourses.size()>poolTwoCourseCount){
										if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
											studentMapP2.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
										poolTwoCourseCount = poolTwoCourseCount +1;
									}else{
										if(poolWiseMap.size()>=poolCount){
											poolTwoCourses = poolWiseMap.get(poolCount);
											poolCount = poolCount +1;
											poolTwoCourseCount = 0;
											if(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)) != null)
												studentMapP2.putAll(courseWiseMap.get(poolTwoCourses.get(poolTwoCourseCount)));
											poolTwoCourseCount = poolTwoCourseCount +1;
										}else{
											if(poolOneCourses.size()>poolOneCourseCount){
												if(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)) != null)
													studentMapP2.putAll(courseWiseMap.get(poolOneCourses.get(poolOneCourseCount)));
												poolOneCourseCount = poolOneCourseCount +1;
											}else{
												studentMapP2.putAll(studentMapP1);
												isPoolCompleated = true;
											}
										}
									}
								}
							}
							if(studentMapP1.isEmpty() && studentMapP2.isEmpty() && !isPoolCompleated){
								break;
							}
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP2.putAll(studentMapP2);
							studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
							int studentsCount =noOfRows;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP2.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMapP2 = tempstudentMapP2;
							if(isPoolCompleated){
								studentMapP1 = tempstudentMapP2;
								studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							}
							isPoolOne = true;
						}
					}
				}
				roomAllotment.setRoomAllotmentDetails(roomAllotmentDetailsSet);
				boList.add(roomAllotment);
			}
		allotmentForm.setAllotedRooms(allotedRooms);
		}
		return boList;
	}
	/**
	 * @param allotmentForm
	 * @param roomDetailsTo
	 * @param studentsCount
	 * @param studentids
	 * @return
	 * @throws Exception
	 */
	private ExamRoomAllotmentDetails createRoomAllotmentDetailsBO(ExamRoomAllotmentForm allotmentForm,RoomEndMidSemRowsTo roomDetailsTo, 
			int studentsCount,	Entry<String, ExamRoomAllotmentDetailsTO> studentids) throws Exception{
		ExamRoomAllotmentDetails roomAllotmentDetails = new ExamRoomAllotmentDetails();
		roomAllotmentDetails.setIsActive(true);
		roomAllotmentDetails.setCreatedBy(allotmentForm.getUserId());
		roomAllotmentDetails.setModifiedBy(allotmentForm.getUserId());
		roomAllotmentDetails.setCreatedDate(new Date());
		roomAllotmentDetails.setLastModifiedDate(new Date());
		roomAllotmentDetails.setColumnNO(roomDetailsTo.getNoOfColumns());
		roomAllotmentDetails.setRowNO(studentsCount+1);
		Student stu = new Student();
		stu.setId(studentids.getValue().getStudentId());
		roomAllotmentDetails.setStudent(stu);
		Classes classes = new Classes();
		classes.setId(studentids.getValue().getClassId());
		roomAllotmentDetails.setClasses(classes );
		roomAllotmentDetails.setSeatingPosition(roomDetailsTo.getSeatingPosition());
		return roomAllotmentDetails;
	}
	/**
	 * @param roomsList
	 * @param allotmentForm 
	 * @return
	 * @throws Exception
	 */
	private List<ExamRoomAllotmentDetailsTO> createMapForRooms(
			List<RoomMaster> roomsList, ExamRoomAllotmentForm allotmentForm) throws Exception{
		List<ExamRoomAllotmentDetailsTO> list = new ArrayList<ExamRoomAllotmentDetailsTO>();
		Iterator<RoomMaster> iterator = roomsList.iterator();
		while (iterator.hasNext()) {
			RoomMaster bo = (RoomMaster) iterator.next();
			if(bo.getEndMidSemRows() != null && !bo.getEndMidSemRows().isEmpty()){
				ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
				if(bo.getBlockId() != null){
					to.setBlockId(bo.getBlockId().getId());
					to.setBlockName(bo.getBlockId().getBlockName());
				}
				to.setEndSemCapacity(bo.getEndSemCapacity());
				to.setEndSemSeatInDesk(bo.getEndSemSeatInDesk());
				to.setEndSemTotalColumn(bo.getEndSemTotalColumn());
				to.setFloor(bo.getFloor());
				to.setId(bo.getId());
				to.setMidSemCapacity(bo.getMidSemCapacity());
				to.setMidSemSeatInDesk(bo.getMidSemSeatInDesk());
				to.setMidSemTotalColumn(bo.getMidSemTotalColumn());
				to.setRoomNo(bo.getRoomNo());
				if(allotmentForm.getMidOrEndSem().equalsIgnoreCase("M"))
					to.setTotalColumns(Integer.parseInt(bo.getMidSemTotalColumn()));
				else
					to.setTotalColumns(Integer.parseInt(bo.getEndSemTotalColumn()));
				List<RoomEndMidSemRowsTo> columnWiseRows = new ArrayList<RoomEndMidSemRowsTo>();
				List<RoomEndMidSemRows> roomDetails = new ArrayList<RoomEndMidSemRows>(bo.getEndMidSemRows());
				Collections.sort(roomDetails);
				for (RoomEndMidSemRows details : roomDetails) {
					if(details.getNoOfRows() != null && details.getColumnNumber() != null && details.getNoOfSetInDesk() != null){
						if(details.getEndMidSem().equalsIgnoreCase(allotmentForm.getMidOrEndSem())){
							for (int i = 1; i <= details.getNoOfSetInDesk(); i++) {
								RoomEndMidSemRowsTo to1 = new RoomEndMidSemRowsTo();
								to1.setNoOfSeats(details.getNoOfSetInDesk());
								to1.setNoOfRows(Integer.parseInt(details.getNoOfRows()));
								to1.setNoOfColumns(Integer.parseInt(details.getColumnNumber()));
								to1.setSeatingPosition(i);
								if(to.getTotalColumns()==to1.getNoOfColumns()){
									if(details.getNoOfSetInDesk()>2){
										if(to1.getSeatingPosition() != 1)
											to1.setLastColumns(true);
										else
											to1.setLastColumns(false);
									}else{
										to1.setLastColumns(true);
									}
								}
								columnWiseRows.add(to1);
							}
						}
					}
				}
				
				to.setColumnWiseRows(columnWiseRows);
				list.add(to);
			}
		}
		
		return list;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCycleMap() throws Exception{
		List<ExamRoomAllotmentCycle> bos = transaction.getExamRoomCycles();
		Map<Integer, String> map = new HashMap<Integer, String>();
		if(bos != null){
			for (ExamRoomAllotmentCycle examRoomAllotmentCycle : bos) {
				map.put(examRoomAllotmentCycle.getId(), examRoomAllotmentCycle.getCycle());
			}
			map = CommonUtil.sortMapByValue(map);
		}
		return map;
	}
	/**
	 * @param allotmentForm
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentTO> getCourcesForAllotmentGroupWise(ExamRoomAllotmentForm allotmentForm) throws Exception{
		List<ExamRoomAllotmentTO> tos = new ArrayList<ExamRoomAllotmentTO>();
		List<Object[]> courses = transaction.getCoursesFromGroups(allotmentForm.getCycleId(),allotmentForm);
		if(courses != null){
			for (Object[] bo : courses) {
				if(bo[0] != null && bo[1] != null && bo[2] != null && bo[3] != null){
					ExamRoomAllotmentTO to = new ExamRoomAllotmentTO();
					to.setCourseId(Integer.parseInt(bo[0].toString()));
					to.setCourseName(bo[1].toString());
					to.setSemNo(bo[2].toString());
					tos.add(to);
					if(bo[3] != null)
						allotmentForm.setMidOrEndSem(bo[3].toString());
					if(bo[4] != null)
						allotmentForm.setSessionTime(bo[4].toString());
				}
			}
		}
		return tos;
	}
	/**
	 * @param allotmentForm
	 * @param multipleAllotment 
	 * @throws Exception
	 */
	public void allotRoomsForStudentFromGroup(ExamRoomAllotmentForm allotmentForm, boolean multipleAllotment) throws Exception{
		if(allotmentForm.getAllotmentList() != null){
			Map<String, List<Integer>> semWiseCourses = new HashMap<String, List<Integer>>();
			for (ExamRoomAllotmentTO to : allotmentForm.getAllotmentList()) {
				if(to.getChecked() != null && to.getChecked().equalsIgnoreCase("on")){
					List<Integer> totalCourses = new ArrayList<Integer>();
					if(semWiseCourses.containsKey(to.getSemNo())){
						totalCourses = semWiseCourses.get(to.getSemNo());
					}
					totalCourses.add(to.getCourseId());
					semWiseCourses.put(to.getSemNo(), totalCourses);
				}
			}
			
			List<StudentClassGroup> students = transaction.getStudentsForGroupCourse(allotmentForm,semWiseCourses);
			
			if(students != null && !students.isEmpty()){
				Map<String, Map<String, ExamRoomAllotmentDetailsTO>> groupSemWiseMap = new TreeMap<String, Map<String, ExamRoomAllotmentDetailsTO>>();
				List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
				Iterator<StudentClassGroup> iterator = students.iterator();
				while (iterator.hasNext()) {
					StudentClassGroup studentClassGroup = (StudentClassGroup) iterator.next();
					if(!listOfDetainedStudents.contains(studentClassGroup.getStudent().getId()));
					Map<String, ExamRoomAllotmentDetailsTO> subMap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
					if(groupSemWiseMap.containsKey(studentClassGroup.getStudent().getClassSchemewise().getClasses().getTermNumber()+studentClassGroup.getGroupClasses().getClassGroup().getName())){
						subMap = groupSemWiseMap.get(studentClassGroup.getStudent().getClassSchemewise().getClasses().getTermNumber()+studentClassGroup.getGroupClasses().getClassGroup().getName());
					}
					ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
					to.setStudentId(studentClassGroup.getStudent().getId());
					to.setClassId(studentClassGroup.getStudent().getClassSchemewise().getClasses().getId());
					subMap.put(studentClassGroup.getStudent().getRegisterNo(), to);
					groupSemWiseMap.put(studentClassGroup.getStudent().getClassSchemewise().getClasses().getTermNumber()+studentClassGroup.getGroupClasses().getClassGroup().getName(), subMap);
				}
				Map<Integer, Map<String, ExamRoomAllotmentDetailsTO>> groupWiseStudentsMap = new HashMap<Integer, Map<String, ExamRoomAllotmentDetailsTO>>();
				int count=1; 
				for (Entry<String, Map<String, ExamRoomAllotmentDetailsTO>> map : groupSemWiseMap.entrySet()) {
					groupWiseStudentsMap.put(count, map.getValue());
					count = count+1;
				}
				List<ExamRoomAllotment> allotmentList = studentAllotmentGroupWise(groupWiseStudentsMap,allotmentForm,multipleAllotment);
				transaction.saveAllotment(allotmentList, allotmentForm);
			}
		}
	}
	/**
	 * @param groupWiseMap 
	 * @param allotmentForm
	 * @return
	 * @throws Exception
	 */
	private List<ExamRoomAllotment> studentAllotmentGroupWise(Map<Integer, Map<String, ExamRoomAllotmentDetailsTO>> groupWiseMap,
			ExamRoomAllotmentForm allotmentForm,boolean multipleAllotment) throws Exception{
		List<ExamRoomAllotment> boList = new ArrayList<ExamRoomAllotment>();
		if(!groupWiseMap.isEmpty()){
			List<ExamRoomAllotmentDetailsTO> roomList = new ArrayList<ExamRoomAllotmentDetailsTO>();
			if(multipleAllotment){
				roomList = allotmentForm.getRoomList();
			}else{
				transaction.removeRoomAllotment(allotmentForm);
				List<RoomMaster> roomsList = transaction.getAvailableRooms(allotmentForm);
				roomList = createMapForRooms(roomsList,allotmentForm);
				allotmentForm.setAllotedRooms(new ArrayList<Integer>());
			}
			
			int allotmentCount = transaction.getNoOfStudentAllotInSameRoom();
			
			int groupOneCount =1;
			
			Map<String, ExamRoomAllotmentDetailsTO> studentMapP1 = groupWiseMap.get(groupOneCount);
			groupOneCount = groupOneCount +1;
			Map<String, ExamRoomAllotmentDetailsTO> studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
			if(groupWiseMap.size()>=groupOneCount){
				studentMapP2 = groupWiseMap.get(groupOneCount);
				groupOneCount = groupOneCount+1;
			}
			List<Integer> allotedRooms = allotmentForm.getAllotedRooms();
			for (ExamRoomAllotmentDetailsTO room : roomList) {
				if(!allotmentForm.getAllotedRooms().contains(room.getId())){
					if(studentMapP1.isEmpty() && studentMapP2.isEmpty()){
						break;
					}
					if(allotedRooms != null)
						allotedRooms.add(room.getId());
					
					// Bo creation
					ExamRoomAllotment roomAllotment = new ExamRoomAllotment();
					Set<ExamRoomAllotmentDetails> roomAllotmentDetailsSet = new HashSet<ExamRoomAllotmentDetails>();
					
					ExamRoomAllotmentCycle cycle = new ExamRoomAllotmentCycle();
					cycle.setId(Integer.parseInt(allotmentForm.getCycleId()));
					roomAllotment.setCycle(cycle );
					ExamDefinition examDefinition = new ExamDefinition();
					examDefinition.setId(Integer.parseInt(allotmentForm.getExamId()));
					roomAllotment.setExamDefinition(examDefinition );
					roomAllotment.setMidOrEndSem(allotmentForm.getMidOrEndSem());
					RoomMaster roomMaster = new RoomMaster();
					roomMaster.setId(room.getId());
					roomAllotment.setRoom(roomMaster);
					roomAllotment.setCreatedBy(allotmentForm.getUserId());
					roomAllotment.setCreatedDate(new Date());
					roomAllotment.setModifiedBy(allotmentForm.getUserId());
					roomAllotment.setLastModifiedDate(new Date());
					roomAllotment.setIsActive(true);
					Boolean isGroupOne = true;
					boolean allotInSameRoom = false;
					boolean repeateMap1 = false;
					boolean repeateMap2 = false;
					boolean allotInSameRoomForMap2 = false;
					boolean isLastRow = false;
					for (RoomEndMidSemRowsTo roomDetailsTo : room.getColumnWiseRows()) {
						if(roomDetailsTo.isLastColumns() && allotmentCount != 0){
							
							// if studentMap1 or studentMap2 contains less student then allocate in same room .
							if(allotInSameRoom){
								allotInSameRoom = false;
								isGroupOne = null;
								if(!studentMapP1.isEmpty()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMapP1);
									studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
										if(studentsCount >= roomDetailsTo.getNoOfRows()){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP1 = tempstudentMapP1;
								}else if(!studentMapP2.isEmpty()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP2.putAll(studentMapP2);
									studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
										if(studentsCount >= roomDetailsTo.getNoOfRows()){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP2.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP2 = tempstudentMapP2;
									
								}
							}
							
							// repeating Map1 in same room if student map contains less students .
							if(repeateMap1){
								repeateMap1 = false;
								isGroupOne = null;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
							}
							
							// repeating Map2 in same room if studentMap2 contains less students .
							if(allotInSameRoomForMap2){
								allotInSameRoomForMap2 = false;
								isGroupOne = null;
								int takeFromMap1 = roomDetailsTo.getNoOfRows()-(roomDetailsTo.getNoOfRows()-studentMapP2.size());
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									if(studentsCount >= (roomDetailsTo.getNoOfRows()-takeFromMap1)){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									repeateMap2 = true;
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
							}
							// repeat Map2 if studentMap2 contains less students.
							if(repeateMap2){
								repeateMap2 = false;
								isGroupOne = null;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
							}
							if(studentMapP1.size()<studentMapP2.size() && !isLastRow){
								isLastRow = true;
								
								if(!studentMapP1.isEmpty()){
									if(roomDetailsTo.getNoOfRows()>studentMapP1.size()){
										if((roomDetailsTo.getNoOfRows()-studentMapP1.size()<allotmentCount)){
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP1.putAll(studentMapP1);
											studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
											int studentsCount =0;
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP1.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP1 = tempstudentMapP1;
											if(studentMapP1.isEmpty()){
												Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
												tempstudentMapP2.putAll(studentMapP2);
												studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
												for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
													allotInSameRoom = true;
													if(studentsCount >= roomDetailsTo.getNoOfRows()){
														break;
													}
													ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
													roomAllotmentDetailsSet.add(roomAllotmentDetails);
													tempstudentMapP2.remove(studentids.getKey());
													studentsCount = studentsCount + 1;
												}
												studentMapP2 = tempstudentMapP2;
											}
										}
									}else{
										if((studentMapP1.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){								
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP1.putAll(studentMapP1);
											studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
											int studentsCount =0;
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
												repeateMap1 = true;
												if(studentsCount >= roomDetailsTo.getNoOfRows()){
													break;
												}
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP1.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP1 = tempstudentMapP1;
										}
									}
								}
							}else if(!isLastRow){
								isLastRow = true;
								if(!studentMapP2.isEmpty()){
									if(roomDetailsTo.getNoOfRows()>studentMapP2.size()){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP1.putAll(studentMapP1);
										studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
										int studentsCount =0;
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
											allotInSameRoomForMap2 = true;
											if(studentsCount >= roomDetailsTo.getNoOfRows()){
												break;
											}
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP1.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP1 = tempstudentMapP1;
									}else{
										if((studentMapP2.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){
											int takeFromMap1 = roomDetailsTo.getNoOfRows()-(studentMapP2.size()-roomDetailsTo.getNoOfRows());
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP1.putAll(studentMapP1);
											studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
											int studentsCount =0;
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
												if(studentsCount >= takeFromMap1){
													break;
												}
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP1.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP1 = tempstudentMapP1;
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP2.putAll(studentMapP2);
											studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
												repeateMap2 = true;
												if(studentsCount >= roomDetailsTo.getNoOfRows()){
													break;
												}
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP2.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP2 = tempstudentMapP2;
										}
									}
								}
							}
						}
						if(!allotInSameRoom && !allotInSameRoomForMap2 && !repeateMap1 && !repeateMap2){
							if(isGroupOne != null && isGroupOne){
								int noOfRows = 0;
								boolean isPoolCompleated = false;
								if(studentMapP1.size()<roomDetailsTo.getNoOfRows()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMapP1);
									studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP1 = tempstudentMapP1;
									
									if(groupWiseMap.size()>=groupOneCount){
										studentMapP1.putAll(groupWiseMap.get(groupOneCount));
										groupOneCount = groupOneCount +1;
									}else{
										studentMapP1.putAll(studentMapP2);
										isPoolCompleated = true;
									}
									if(studentMapP1.size()<roomDetailsTo.getNoOfRows()){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP4 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP4.putAll(studentMapP1);
										studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
											noOfRows = noOfRows + 1;
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP4.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP1 = tempstudentMapP4;
										if(isPoolCompleated){
											studentMapP2 =  tempstudentMapP4;
											studentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										}
									}
								}
								if(studentMapP1.isEmpty() && !isPoolCompleated){
									break;
								}
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount = noOfRows;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
								if(isPoolCompleated){
									studentMapP2 = tempstudentMapP1;
									studentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
								isGroupOne = false;
							}else if(isGroupOne != null){
								int noOfRows = 0;
								boolean isPoolCompleated = false;
								if(studentMapP2.size()<roomDetailsTo.getNoOfRows()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP2.putAll(studentMapP2);
									studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP2.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP2 = tempstudentMapP2;
									if(groupWiseMap.size()>=groupOneCount){
										studentMapP2.putAll(groupWiseMap.get(groupOneCount));
										groupOneCount = groupOneCount +1;
									}else{
										studentMapP2.putAll(studentMapP1);
										isPoolCompleated = true;
									}
									if(studentMapP2.size()<roomDetailsTo.getNoOfRows()){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP3 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP3.putAll(studentMapP2);
										studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
											noOfRows = noOfRows + 1;
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP3.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP2 = tempstudentMapP3;
										if(isPoolCompleated){
											studentMapP1 = tempstudentMapP3;
											studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										}
									}
								}
								if(studentMapP2.isEmpty() && !isPoolCompleated){
									break;
								}
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								int studentsCount =noOfRows;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
								if(isPoolCompleated){
									studentMapP1 = tempstudentMapP2;
									studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
								isGroupOne = true;
							}
						}
					}
					roomAllotment.setRoomAllotmentDetails(roomAllotmentDetailsSet);
					boList.add(roomAllotment);
				}
			}
			allotmentForm.setAllotedRooms(allotedRooms);
		}
		return boList;
	}
	/**
	 * @param allotmentForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamRoomAllotmentTO> getCoursesForAllotmentSpecialization( ExamRoomAllotmentForm allotmentForm) throws Exception{
		List<ExamRoomAllotmentTO> tos = new ArrayList<ExamRoomAllotmentTO>();
		List<Object[]> courses = transaction.getCoursesForSpecialization(allotmentForm.getCycleId(),allotmentForm.getCampus());
		if(courses!=null && !courses.isEmpty()){
			for (Object[] bo : courses) {
				if(bo[0]!=null && bo[1]!=null && bo[2]!=null && bo[3]!=null ){
					ExamRoomAllotmentTO to = new ExamRoomAllotmentTO();
					to.setCourseId(Integer.parseInt(bo[0].toString()));
					to.setCourseName(bo[1].toString());
					to.setSpecializationId(bo[2].toString());
					to.setSemNo(bo[3].toString());
					if(bo[4] != null)
						allotmentForm.setMidOrEndSem(bo[4].toString());
					if(bo[5] != null)
						allotmentForm.setSessionTime(bo[5].toString());
					tos.add(to);
				}
			}
		}
		return tos;
	}
	/**
	 * @param allotmentForm
	 * @throws Exception
	 */
	public void allotRoomsForStudentFromSpecialization(ExamRoomAllotmentForm allotmentForm ,boolean multipleAllotment) throws Exception{
		if(allotmentForm.getAllotmentList() != null){
			Map<String, List<Integer>> semWiseCourses = new HashMap<String, List<Integer>>();
			for (ExamRoomAllotmentTO to : allotmentForm.getAllotmentList()) {
				if(to.getChecked() != null && to.getChecked().equalsIgnoreCase("on")){
					List<Integer> totalCourses = new ArrayList<Integer>();
					if(semWiseCourses.containsKey(to.getSemNo())){
						totalCourses = semWiseCourses.get(to.getSemNo());
					}
					totalCourses.add(to.getCourseId());
					semWiseCourses.put(to.getSemNo(), totalCourses);
				}
			}
			List<Student> students = transaction.getStudentsForCourse(allotmentForm,semWiseCourses);
			Map<Integer, String>  specializationMap = transaction.getSpecializationForStudentsByCourses(allotmentForm,semWiseCourses);
			if(students!=null && !students.isEmpty()){
				Map<String, Map<String, ExamRoomAllotmentDetailsTO>>  specializationWiseMap = new TreeMap<String, Map<String,ExamRoomAllotmentDetailsTO>>();
				List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
				Iterator<Student> iterator = students.iterator();
				while (iterator.hasNext()) {
					Student student = (Student) iterator.next();
					if(!listOfDetainedStudents.contains(student.getId())){
						Map<String, ExamRoomAllotmentDetailsTO> studentMap = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						String specializationName = "";
						if(specializationMap.containsKey(student.getId())){
							 specializationName = specializationMap.get(student.getId());
							if(specializationWiseMap.containsKey(specializationName)){
								studentMap = specializationWiseMap.get(specializationName);
							}
							ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
							to.setStudentId(student.getId());
							to.setClassId(student.getClassSchemewise().getClasses().getId());
							studentMap.put(student.getRegisterNo(), to);
							specializationWiseMap.put(specializationName, studentMap);
						}else{
							specializationName = "NoSpecialization";
							if(specializationWiseMap.containsKey(specializationName)){
								studentMap = specializationWiseMap.get(specializationName);
							}
							ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
							to.setStudentId(student.getId());
							to.setClassId(student.getClassSchemewise().getClasses().getId());
							studentMap.put(student.getRegisterNo(), to);
							specializationWiseMap.put(specializationName, studentMap);
						}
					}
				}
				Map<Integer, Map<String, ExamRoomAllotmentDetailsTO>> specializationWiseStudentMap = new HashMap<Integer, Map<String,ExamRoomAllotmentDetailsTO>>();
				int count = 1;
				if(specializationWiseMap!=null && !specializationWiseMap.isEmpty()){
					/*for (Entry<String, Map<String, ExamRoomAllotmentDetailsTO>> specializationIter : specializationWiseMap.entrySet()) {
						if(count ==2){
							specializationWiseStudentMap.put(count, specializationWiseMap.get("NoSpecialization"));
							count++;
						}
						specializationWiseStudentMap.put(count, specializationIter.getValue());
						count++;
					}*/
					if(specializationWiseMap!=null && !specializationWiseMap.isEmpty()){
//						for (Entry<String, Map<String, ExamRoomAllotmentDetailsTO>> specializationIter : specializationWiseMap.entrySet()) {
						Iterator<Entry<String, Map<String, ExamRoomAllotmentDetailsTO>>> specializationIter = specializationWiseMap.entrySet().iterator();
						while (specializationIter.hasNext()) {
							Map.Entry<java.lang.String, java.util.Map<java.lang.String, com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO>> entry = (Map.Entry<java.lang.String, java.util.Map<java.lang.String, com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO>>) specializationIter
									.next();
							if(count ==2){
								if(specializationWiseMap.get("NoSpecialization")!=null && !specializationWiseMap.get("NoSpecialization").isEmpty()){
									specializationWiseStudentMap.put(count, specializationWiseMap.get("NoSpecialization"));
									count++;
								}
							}else if(count!=2){
								if(entry.getKey().equalsIgnoreCase("NoSpecialization")){
									continue;
								}
							}
							specializationWiseStudentMap.put(count, entry.getValue());
							count++;
						}
							
//						}
					}
				}
				List<ExamRoomAllotment> allotmentList = studentAllotmentSpecializationWise(specializationWiseStudentMap,allotmentForm,multipleAllotment);
				transaction.saveAllotment(allotmentList,allotmentForm);
			}
		}
	}
	/**
	 * @param specializationWiseStudentMap
	 * @param allotmentForm
	 * @param multipleAllotment 
	 * @return
	 * @throws Exception 
	 */
	private List<ExamRoomAllotment> studentAllotmentSpecializationWise(Map<Integer, Map<String, ExamRoomAllotmentDetailsTO>> specializationWiseStudentMap,
			ExamRoomAllotmentForm allotmentForm, boolean multipleAllotment) throws Exception {
		List<ExamRoomAllotment> boList = new ArrayList<ExamRoomAllotment>();
		if(specializationWiseStudentMap!=null && !specializationWiseStudentMap.isEmpty()){
			List<ExamRoomAllotmentDetailsTO> roomList = new ArrayList<ExamRoomAllotmentDetailsTO>();
			if(multipleAllotment){
				roomList = allotmentForm.getRoomList();
			}else{
				transaction.removeRoomAllotment(allotmentForm);
				List<RoomMaster> roomsList = transaction.getAvailableRooms(allotmentForm);
				roomList = createMapForRooms(roomsList,allotmentForm);
				allotmentForm.setAllotedRooms(new ArrayList<Integer>());
			}
			int allotmentCount = transaction.getNoOfStudentAllotInSameRoom();
			
			int specializationOneCount = 1; 
			
			Map<String, ExamRoomAllotmentDetailsTO> studentMapP1 = specializationWiseStudentMap.get(specializationOneCount);
			specializationOneCount = specializationOneCount +1;
			Map<String, ExamRoomAllotmentDetailsTO> studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
			if(specializationWiseStudentMap.size()>=specializationOneCount){
				studentMapP2 = specializationWiseStudentMap.get(specializationOneCount);
				specializationOneCount = specializationOneCount+1;
			}
			for (ExamRoomAllotmentDetailsTO room : roomList) {
				if(!allotmentForm.getAllotedRooms().contains(room.getId())){
					if(studentMapP1.isEmpty() && studentMapP2.isEmpty()){
						break;
					}
					// Bo creation
					ExamRoomAllotment roomAllotment = new ExamRoomAllotment();
					Set<ExamRoomAllotmentDetails> roomAllotmentDetailsSet = new HashSet<ExamRoomAllotmentDetails>();
					ExamRoomAllotmentCycle cycle = new ExamRoomAllotmentCycle();
					cycle.setId(Integer.parseInt(allotmentForm.getCycleId()));
					roomAllotment.setCycle(cycle );
					ExamDefinition examDefinition = new ExamDefinition();
					examDefinition.setId(Integer.parseInt(allotmentForm.getExamId()));
					roomAllotment.setExamDefinition(examDefinition );
					roomAllotment.setMidOrEndSem(allotmentForm.getMidOrEndSem());
					RoomMaster roomMaster = new RoomMaster();
					roomMaster.setId(room.getId());
					roomAllotment.setRoom(roomMaster);
					roomAllotment.setCreatedBy(allotmentForm.getUserId());
					roomAllotment.setCreatedDate(new Date());
					roomAllotment.setModifiedBy(allotmentForm.getUserId());
					roomAllotment.setLastModifiedDate(new Date());
					roomAllotment.setIsActive(true);
					Boolean isSpecializationOne = true;
					boolean allotInSameRoom = false;
					boolean repeateMap1 = false;
					boolean repeateMap2 = false;
					boolean allotInSameRoomForMap2 = false;
					boolean isLastRow = false;
					for (RoomEndMidSemRowsTo roomDetailsTo : room.getColumnWiseRows()) {
						if(roomDetailsTo.isLastColumns() && allotmentCount != 0){
							if(allotInSameRoom){
								allotInSameRoom = false;
								isSpecializationOne = null;
								if(!studentMapP1.isEmpty()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMapP1);
									studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
										if(studentsCount >= roomDetailsTo.getNoOfRows()){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP1 = tempstudentMapP1;
								}else if(!studentMapP2.isEmpty()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP2.putAll(studentMapP2);
									studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
										if(studentsCount >= roomDetailsTo.getNoOfRows()){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP2.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP2 = tempstudentMapP2;
								}
							}
							// repeating Map1 in same room if student map contains less students .
							if(repeateMap1){
								repeateMap1 = false;
								isSpecializationOne = null;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
							}
							
							// repeating Map2 in same room if studentMap2 contains less students .
							if(allotInSameRoomForMap2){
								allotInSameRoomForMap2 = false;
								isSpecializationOne = null;
								int takeFromMap1 = roomDetailsTo.getNoOfRows()-(roomDetailsTo.getNoOfRows()-studentMapP2.size());
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									if(studentsCount >= (roomDetailsTo.getNoOfRows()-takeFromMap1)){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									repeateMap2 = true;
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
							}
							// repeat Map2 if studentMap2 contains less students.
							if(repeateMap2){
								repeateMap2 = false;
								isSpecializationOne = null;
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
							}
							if(studentMapP1.size()<studentMapP2.size() && !isLastRow){
								isLastRow = true;
								if(!studentMapP1.isEmpty()){
									if(roomDetailsTo.getNoOfRows()>studentMapP1.size()){
										if((roomDetailsTo.getNoOfRows()-studentMapP1.size()<allotmentCount)){
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP1.putAll(studentMapP1);
											studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
											int studentsCount =0;
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP1.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP1 = tempstudentMapP1;
											if(studentMapP1.isEmpty()){
												Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
												tempstudentMapP2.putAll(studentMapP2);
												studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
												for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
													allotInSameRoom = true;
													if(studentsCount >= roomDetailsTo.getNoOfRows()){
														break;
													}
													ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
													roomAllotmentDetailsSet.add(roomAllotmentDetails);
													tempstudentMapP2.remove(studentids.getKey());
													studentsCount = studentsCount + 1;
												}
												studentMapP2 = tempstudentMapP2;
											}
										}
									}else{
										if((studentMapP1.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){								
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP1.putAll(studentMapP1);
											studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
											int studentsCount =0;
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
												repeateMap1 = true;
												if(studentsCount >= roomDetailsTo.getNoOfRows()){
													break;
												}
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP1.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP1 = tempstudentMapP1;
										}
									}
								}
							}else if(!isLastRow){
								isLastRow = true;
								if(!studentMapP2.isEmpty()){
									if(roomDetailsTo.getNoOfRows()>studentMapP2.size()){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP1.putAll(studentMapP1);
										studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
										int studentsCount =0;
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
											allotInSameRoomForMap2 = true;
											if(studentsCount >= roomDetailsTo.getNoOfRows()){
												break;
											}
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP1.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP1 = tempstudentMapP1;
									}else{
										if((studentMapP2.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){
											int takeFromMap1 = roomDetailsTo.getNoOfRows()-(studentMapP2.size()-roomDetailsTo.getNoOfRows());
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP1.putAll(studentMapP1);
											studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
											int studentsCount =0;
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
												if(studentsCount >= takeFromMap1){
													break;
												}
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP1.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP1 = tempstudentMapP1;
											Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
											tempstudentMapP2.putAll(studentMapP2);
											studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
											for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
												repeateMap2 = true;
												if(studentsCount >= roomDetailsTo.getNoOfRows()){
													break;
												}
												ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
												roomAllotmentDetailsSet.add(roomAllotmentDetails);
												tempstudentMapP2.remove(studentids.getKey());
												studentsCount = studentsCount + 1;
											}
											studentMapP2 = tempstudentMapP2;
										}
									}
								}
							}
						}
						if(!allotInSameRoom && !allotInSameRoomForMap2 && !repeateMap1 && !repeateMap2){
							if(isSpecializationOne != null && isSpecializationOne){
								int noOfRows = 0;
								boolean isPoolCompleated = false;
								if(studentMapP1.size()<roomDetailsTo.getNoOfRows()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMapP1);
									studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP1 = tempstudentMapP1;
									
									if(specializationWiseStudentMap.size()>=specializationOneCount){
										studentMapP1.putAll(specializationWiseStudentMap.get(specializationOneCount));
										specializationOneCount = specializationOneCount +1;
									}else{
										studentMapP1.putAll(studentMapP2);
										isPoolCompleated = true;
									}
									if(studentMapP1.size()<roomDetailsTo.getNoOfRows()){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP4 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP4.putAll(studentMapP1);
										studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
											noOfRows = noOfRows + 1;
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP4.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP1 = tempstudentMapP4;
										if(isPoolCompleated){
											studentMapP2 = tempstudentMapP4;
											studentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										}
									}
								}
								if(studentMapP1.isEmpty() && !isPoolCompleated){
									break;
								}
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMapP1);
								studentMapP1 = CommonUtil.sortMapByKey(studentMapP1);
								int studentsCount = noOfRows;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP1.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP1 = tempstudentMapP1;
								if(isPoolCompleated){
									studentMapP2 = tempstudentMapP1;
									studentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
								isSpecializationOne = false;
							}else if(isSpecializationOne !=null){
								int noOfRows = 0;
								boolean isPoolCompleated = false;
								if(studentMapP2.size()<roomDetailsTo.getNoOfRows()){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP2.putAll(studentMapP2);
									studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
										noOfRows = noOfRows + 1;
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP2.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMapP2 = tempstudentMapP2;
									if(specializationWiseStudentMap.size()>=specializationOneCount){
										studentMapP2.putAll(specializationWiseStudentMap.get(specializationOneCount));
										specializationOneCount = specializationOneCount +1;
									}else{
										studentMapP2.putAll(studentMapP1);
										isPoolCompleated = true;
									}
									if(studentMapP2.size()<roomDetailsTo.getNoOfRows()){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP3 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP3.putAll(studentMapP2);
										studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
											noOfRows = noOfRows + 1;
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP3.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMapP2 = tempstudentMapP3;
										if(isPoolCompleated){
											studentMapP1 = tempstudentMapP3;
											studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										}
									}
								}
								if(studentMapP2.isEmpty() && !isPoolCompleated){
									break;
								}
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP2.putAll(studentMapP2);
								studentMapP2 = CommonUtil.sortMapByKey(studentMapP2);
								int studentsCount =noOfRows;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMapP2.entrySet()) {
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP2.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMapP2 = tempstudentMapP2;
								if(isPoolCompleated){
									studentMapP1 = tempstudentMapP2;
									studentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
								isSpecializationOne = true;
							}
						}
					}
					roomAllotment.setRoomAllotmentDetails(roomAllotmentDetailsSet);
					boList.add(roomAllotment);
				}
			}
		}
		return boList;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getSessionMap() throws Exception{
		List<ExaminationSessions> examinationSessionsList = transaction.getSessionsList();
		Map<Integer,String> sessionMap = new HashMap<Integer, String>();
		if(examinationSessionsList!=null && !examinationSessionsList.isEmpty()){
			Iterator<ExaminationSessions> iterator = examinationSessionsList.iterator();
			while (iterator.hasNext()) {
				ExaminationSessions examinationSessions = (ExaminationSessions) iterator .next();
				if(examinationSessions.getSession()!=null && !examinationSessions.getSession().isEmpty()){
					sessionMap.put(examinationSessions.getId(), examinationSessions.getSession());
				}
			}
		}
		return sessionMap;
	}
	/**
	 * @param allotmentForm
	 * @param classList 
	 * @throws Exception
	 */
	public void roomAllotmentBasedOnClasses(ExamRoomAllotmentForm allotmentForm, List<Integer> classesList) throws Exception{
		if(allotmentForm.getSelectedClasses()!=null && !allotmentForm.getSelectedClasses().toString().isEmpty()){
			/*List<Integer> classesList = new ArrayList<Integer>();
			for(String classId :allotmentForm.getSelectedClasses()){
				classesList.add(Integer.parseInt(classId));
			}*/
			Map<Integer,List<String>> courseWiseClassesMap = transaction.getcourseWiseClassesDetails(classesList);
			List<Student> stuList = transaction.getStudentsForSelectedClasses(classesList);
			if(stuList!=null && !stuList.isEmpty()){
				Map<String, Map<String, ExamRoomAllotmentDetailsTO>>  studentDetailsMap = new TreeMap<String, Map<String,ExamRoomAllotmentDetailsTO>>();
				List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
				Iterator<Student> iterator = stuList.iterator();
				while (iterator.hasNext()) {
					Student student = (Student) iterator.next();
					if(!listOfDetainedStudents.contains(student.getId())){
						if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null && student.getClassSchemewise().getClasses().getCourse()!=null){
							Map<String, ExamRoomAllotmentDetailsTO> studentList = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							if(studentDetailsMap.containsKey(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getId())){
								studentList = studentDetailsMap.get(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getId());
							}
							ExamRoomAllotmentDetailsTO to = new ExamRoomAllotmentDetailsTO();
							to.setStudentId(student.getId());
							to.setClassId(student.getClassSchemewise().getClasses().getId());
							studentList.put(student.getRegisterNo(), to);
							studentDetailsMap.put(student.getClassSchemewise().getClasses().getCourse().getId()+"_"+student.getClassSchemewise().getClasses().getId(), studentList);
						}
					}
				}
				Map<Integer,List<String>> courseClassWiseMap = new HashMap<Integer, List<String>>();
				if(courseWiseClassesMap!=null && !courseWiseClassesMap.isEmpty()){
					int count =1;
					for(Entry<Integer,List<String>> entry :courseWiseClassesMap.entrySet()){
						courseClassWiseMap.put(count, entry.getValue());
						count =count+1;
					}
				}
				if(studentDetailsMap!=null && !studentDetailsMap.isEmpty() && courseClassWiseMap!=null && !courseClassWiseMap.isEmpty()){
					List<ExamRoomAllotment> roomAllotments = examRoomAllotmentClassWiseForStudents(studentDetailsMap,courseClassWiseMap,allotmentForm);
					if(allotmentForm.getRemainingStudentsCount()==0){
						transaction.saveAllotment(roomAllotments,allotmentForm);
					}
				}
			}
		}
	}
	/**
	 * @param studentDetailsMap
	 * @param courseWiseClassesMap
	 * @param allotmentForm
	 * @return
	 * @throws Exception
	 */
	private List<ExamRoomAllotment> examRoomAllotmentClassWiseForStudents( Map<String, Map<String, ExamRoomAllotmentDetailsTO>> studentDetailsMap,
			Map<Integer, List<String>> courseWiseClassesMap, ExamRoomAllotmentForm allotmentForm)throws Exception {
		List<ExamRoomAllotment> roomAllotments = new ArrayList<ExamRoomAllotment>();
		List<ExamRoomAllotmentDetailsTO> roomList = new ArrayList<ExamRoomAllotmentDetailsTO>();
		transaction.getExamTypeByExamName(allotmentForm);
		List<Integer> roomListIds = new ArrayList<Integer>();
		if(allotmentForm.getSelectedRooms()!=null && !allotmentForm.getSelectedRooms().toString().isEmpty()){
			for(String room : allotmentForm.getSelectedRooms()){
				roomListIds.add(Integer.parseInt(room));
			}
		}
		transaction.removeRoomAllotmentForDateAndSession(allotmentForm,roomListIds);
		List<RoomMaster> roomsList = transaction.getSelectedRoomsDetails(allotmentForm,roomListIds);
		roomList = createMapForRooms(roomsList,allotmentForm);
		allotmentForm.setRoomList(roomList);
		int allotmentCount = transaction.getNoOfStudentAllotInSameRoom();
		int count = 1;
		List<String> oneCourseClassesList = courseWiseClassesMap.get(count);
		count = count +1;
		List<String> secondCourseClassesList = new ArrayList<String>();
		if(courseWiseClassesMap.get(count)!= null && !courseWiseClassesMap.get(count).isEmpty()){
			secondCourseClassesList = courseWiseClassesMap.get(count);
			count = count +1;
		}else{
			List<String> list = new ArrayList<String>();
			list.addAll(oneCourseClassesList);
			int listSize = list.size()/2;
			oneCourseClassesList = null;
			oneCourseClassesList = list.subList(0, listSize);
			secondCourseClassesList = list.subList(listSize, list.size());
		}
		int firstCount = 0;
		int secondCount =0;
		// students for one course 
		Map<String, ExamRoomAllotmentDetailsTO> studentMap1 =new HashMap<String, ExamRoomAllotmentDetailsTO>();
		if(!oneCourseClassesList.isEmpty() && studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null && !studentDetailsMap.get(oneCourseClassesList.get(firstCount)).isEmpty()){
			studentMap1 = studentDetailsMap.get(oneCourseClassesList.get(firstCount));
			firstCount = firstCount +1;
		}
		Map<String, ExamRoomAllotmentDetailsTO> studentMap2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
		if(!secondCourseClassesList.isEmpty() && studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null && !studentDetailsMap.get(secondCourseClassesList.get(secondCount)).isEmpty()){
			studentMap2 = studentDetailsMap.get(secondCourseClassesList.get(secondCount));
			secondCount = secondCount+1;
		}else{
			if(oneCourseClassesList.size()>firstCount){
				studentMap2 = studentDetailsMap.get(oneCourseClassesList.get(firstCount));
				firstCount = firstCount +1;
			}
		}
		for (ExamRoomAllotmentDetailsTO room : roomList) {
			boolean breakLoop = false;
			if(!(oneCourseClassesList.size()>firstCount) && !(secondCourseClassesList.size()>secondCount)){
				if(!(courseWiseClassesMap.size()>=count)){
					breakLoop = true;
				}
			}
			if(studentMap1.isEmpty() && studentMap2.isEmpty() &&  breakLoop){
				break;
			}
			// Bo creation
			ExamRoomAllotment roomAllotment = new ExamRoomAllotment();
			Set<ExamRoomAllotmentDetails> roomAllotmentDetailsSet = new HashSet<ExamRoomAllotmentDetails>();
			roomAllotment.setDate(CommonUtil.ConvertStringToDate(allotmentForm.getDate()));
			ExaminationSessions examinationSessions = new ExaminationSessions();
			examinationSessions.setId(Integer.parseInt(allotmentForm.getSession()));
			roomAllotment.setExaminationSessions(examinationSessions);
			ExamDefinition examDefinition = new ExamDefinition();
			examDefinition.setId(Integer.parseInt(allotmentForm.getExamId()));
			roomAllotment.setExamDefinition(examDefinition );
			roomAllotment.setMidOrEndSem(allotmentForm.getMidOrEndSem());
			RoomMaster roomMaster = new RoomMaster();
			roomMaster.setId(room.getId());
			roomAllotment.setRoom(roomMaster);
			roomAllotment.setCreatedBy(allotmentForm.getUserId());
			roomAllotment.setCreatedDate(new Date());
			roomAllotment.setModifiedBy(allotmentForm.getUserId());
			roomAllotment.setLastModifiedDate(new Date());
			roomAllotment.setIsActive(true);
			
			Boolean isPoolOne= true;
			boolean allotInSameRoom = false;
			boolean repeateMap1 = false;
			boolean repeateMap2 = false;
			boolean allotInSameRoomForMap2 = false;
			boolean isLastRow = false;
			for (RoomEndMidSemRowsTo roomDetailsTo : room.getColumnWiseRows()) {
				if(roomDetailsTo.isLastColumns() && allotmentCount != 0){
					// if studentMap1 or studentMap2 contains less student then allocate in same room .
					if(allotInSameRoom){
						allotInSameRoom = false;
						isPoolOne = null;
						if(!studentMap1.isEmpty()){
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP1.putAll(studentMap1);
							studentMap1 = CommonUtil.sortMapByKey(studentMap1);
							int studentsCount =0;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP1.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMap1 = tempstudentMapP1;
						}else if(!studentMap2.isEmpty()){
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP2.putAll(studentMap2);
							studentMap2 = CommonUtil.sortMapByKey(studentMap2);
							int studentsCount =0;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
								if(studentsCount >= roomDetailsTo.getNoOfRows()){
									break;
								}
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP2.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMap2 = tempstudentMapP2;
							
						}
					}
					
					// repeating Map1 in same room if student map contains less students .
					if(repeateMap1){
						repeateMap1 = false;
						isPoolOne = null;
						Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						tempstudentMapP1.putAll(studentMap1);
						studentMap1 = CommonUtil.sortMapByKey(studentMap1);
						int studentsCount =0;
						for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
							if(studentsCount >= roomDetailsTo.getNoOfRows()){
								break;
							}
							ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
							roomAllotmentDetailsSet.add(roomAllotmentDetails);
							tempstudentMapP1.remove(studentids.getKey());
							studentsCount = studentsCount + 1;
						}
						studentMap1 = tempstudentMapP1;
						Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						tempstudentMapP2.putAll(studentMap2);
						studentMap2 = CommonUtil.sortMapByKey(studentMap2);
						for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
							if(studentsCount >= roomDetailsTo.getNoOfRows()){
								break;
							}
							ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
							roomAllotmentDetailsSet.add(roomAllotmentDetails);
							tempstudentMapP2.remove(studentids.getKey());
							studentsCount = studentsCount + 1;
						}
						studentMap2 = tempstudentMapP2;
					}
					
					// repeating Map2 in same room if studentMap2 contains less students .
					if(allotInSameRoomForMap2){
						allotInSameRoomForMap2 = false;
						isPoolOne = null;
						int takeFromMap1 = roomDetailsTo.getNoOfRows()-(roomDetailsTo.getNoOfRows()-studentMap2.size());
						Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						tempstudentMapP1.putAll(studentMap1);
						studentMap1 = CommonUtil.sortMapByKey(studentMap1);
						int studentsCount =0;
						for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
							if(studentsCount >= (roomDetailsTo.getNoOfRows()-takeFromMap1)){
								break;
							}
							ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
							roomAllotmentDetailsSet.add(roomAllotmentDetails);
							tempstudentMapP1.remove(studentids.getKey());
							studentsCount = studentsCount + 1;
						}
						studentMap1 = tempstudentMapP1;
						Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						tempstudentMapP2.putAll(studentMap2);
						studentMap2 = CommonUtil.sortMapByKey(studentMap2);
						for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
							repeateMap2 = true;
							if(studentsCount >= roomDetailsTo.getNoOfRows()){
								break;
							}
							ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
							roomAllotmentDetailsSet.add(roomAllotmentDetails);
							tempstudentMapP2.remove(studentids.getKey());
							studentsCount = studentsCount + 1;
						}
						studentMap2 = tempstudentMapP2;
					}
					// repeat Map2 if studentMap2 contains less students.
					if(repeateMap2){
						repeateMap2 = false;
						isPoolOne = null;
						Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						tempstudentMapP2.putAll(studentMap2);
						studentMap2 = CommonUtil.sortMapByKey(studentMap2);
						int studentsCount =0;
						for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
							if(studentsCount >= roomDetailsTo.getNoOfRows()){
								break;
							}
							ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
							roomAllotmentDetailsSet.add(roomAllotmentDetails);
							tempstudentMapP2.remove(studentids.getKey());
							studentsCount = studentsCount + 1;
						}
						studentMap2 = tempstudentMapP2;
					}
					// if studentMap1 students are less than studentMap2 allocate studentMap1 students in same room  
					if(studentMap1.size()<studentMap2.size() && !isLastRow){
						isLastRow = true;
						if(!studentMap1.isEmpty()){
							if(roomDetailsTo.getNoOfRows()>studentMap1.size()){
								if((roomDetailsTo.getNoOfRows()-studentMap1.size()<allotmentCount)){
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMap1);
									studentMap1 = CommonUtil.sortMapByKey(studentMap1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMap1 = tempstudentMapP1;
									if(studentMap1.isEmpty()){
										Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
										tempstudentMapP2.putAll(studentMap2);
										studentMap2 = CommonUtil.sortMapByKey(studentMap2);
										for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
											allotInSameRoom = true;
											if(studentsCount >= roomDetailsTo.getNoOfRows()){
												break;
											}
											ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
											roomAllotmentDetailsSet.add(roomAllotmentDetails);
											tempstudentMapP2.remove(studentids.getKey());
											studentsCount = studentsCount + 1;
										}
										studentMap2 = tempstudentMapP2;
									}
								}
							}else{
								if((studentMap1.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){								
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMap1);
									studentMap1 = CommonUtil.sortMapByKey(studentMap1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
										repeateMap1 = true;
										if(studentsCount >= roomDetailsTo.getNoOfRows()){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMap1 = tempstudentMapP1;
								}
							}
						}
					}else if(!isLastRow){
						isLastRow = true;
						if(!studentMap2.isEmpty()){
							if(roomDetailsTo.getNoOfRows()>studentMap2.size()){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP1.putAll(studentMap1);
								studentMap1 = CommonUtil.sortMapByKey(studentMap1);
								int studentsCount =0;
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
									allotInSameRoomForMap2 = true;
									if(studentsCount >= roomDetailsTo.getNoOfRows()){
										break;
									}
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP1.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMap1 = tempstudentMapP1;
							}else{
								if((studentMap2.size()-roomDetailsTo.getNoOfRows()) <allotmentCount){
									int takeFromMap1 = roomDetailsTo.getNoOfRows()-(studentMap2.size()-roomDetailsTo.getNoOfRows());
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP1.putAll(studentMap1);
									studentMap1 = CommonUtil.sortMapByKey(studentMap1);
									int studentsCount =0;
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
										if(studentsCount >= takeFromMap1){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP1.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMap1 = tempstudentMapP1;
									Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
									tempstudentMapP2.putAll(studentMap2);
									studentMap2 = CommonUtil.sortMapByKey(studentMap2);
									for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
										repeateMap2 = true;
										if(studentsCount >= roomDetailsTo.getNoOfRows()){
											break;
										}
										ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
										roomAllotmentDetailsSet.add(roomAllotmentDetails);
										tempstudentMapP2.remove(studentids.getKey());
										studentsCount = studentsCount + 1;
									}
									studentMap2 = tempstudentMapP2;
								}
							}
						}
					}
				}
				if(!allotInSameRoom && !allotInSameRoomForMap2 && !repeateMap1 && !repeateMap2){
					if(isPoolOne != null && isPoolOne){
						int noOfRows = 0;
						boolean isPoolCompleated = false;
						if(studentMap1.size()<roomDetailsTo.getNoOfRows()){
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP1.putAll(studentMap1);
							studentMap1 = CommonUtil.sortMapByKey(studentMap1);
							int studentsCount =0;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
								noOfRows = noOfRows + 1;
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP1.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMap1 = tempstudentMapP1;
							
							if(oneCourseClassesList.size()>firstCount){
								if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
									studentMap1.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
								firstCount = firstCount +1;
							}else{
								if(courseWiseClassesMap.size()>=count){
									oneCourseClassesList = courseWiseClassesMap.get(count);
									count = count +1;
									firstCount = 0;
									if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
										studentMap1.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
									firstCount = firstCount +1;
								}else{
									if(secondCourseClassesList.size()>secondCount){
										if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
											studentMap1.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
										secondCount = secondCount +1;
									}else{
										studentMap1.putAll(studentMap2);
										isPoolCompleated = true;
									}
								}
							}
							if(studentMap1.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP4 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP4.putAll(studentMap1);
								studentMap1 = CommonUtil.sortMapByKey(studentMap1);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
									noOfRows = noOfRows + 1;
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP4.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMap1 = tempstudentMapP4;
								studentMap1 = tempstudentMapP4;
								if(isPoolCompleated){
									studentMap2 = tempstudentMapP4;
									studentMap1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
								if(oneCourseClassesList.size()>firstCount){
									if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
										studentMap1.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
									firstCount = firstCount +1;
								}else{
									if(courseWiseClassesMap.size()>=count){
										oneCourseClassesList = courseWiseClassesMap.get(count);
										count = count +1;
										firstCount = 0;
										if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
											studentMap1.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
										firstCount = firstCount +1;
									}else{
										if(secondCourseClassesList.size()>secondCount){
											if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
												studentMap1.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
											secondCount = secondCount +1;
										}else{
											studentMap1.putAll(studentMap2);
											isPoolCompleated = true;
										}
									}
								}
							}
							if(studentMap1.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP4 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP4.putAll(studentMap1);
								studentMap1 = CommonUtil.sortMapByKey(studentMap1);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
									noOfRows = noOfRows + 1;
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP4.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMap1 = tempstudentMapP4;
								if(isPoolCompleated){
									studentMap2 = tempstudentMapP4;
									studentMap1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
									
								if(oneCourseClassesList.size()>firstCount){
									if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
										studentMap1.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
									firstCount = firstCount +1;
								}else{
									if(courseWiseClassesMap.size()>=count){
										oneCourseClassesList = courseWiseClassesMap.get(count);
										count = count +1;
										firstCount = 0;
										if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
											studentMap1.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
										firstCount = firstCount +1;
									}else{
										if(secondCourseClassesList.size()>secondCount){
											if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
												studentMap1.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
											secondCount = secondCount +1;
										}else{
											studentMap1.putAll(studentMap2);
											isPoolCompleated = true;
										}
									}
								}
							}
						}
						if(studentMap1.isEmpty() && studentMap2.isEmpty() && !isPoolCompleated){
							break;
						}
						Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						tempstudentMapP1.putAll(studentMap1);
						studentMap1 = CommonUtil.sortMapByKey(studentMap1);
						int studentsCount = noOfRows;
						for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap1.entrySet()) {
							if(studentsCount >= roomDetailsTo.getNoOfRows()){
								break;
							}
							ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
							roomAllotmentDetailsSet.add(roomAllotmentDetails);
							tempstudentMapP1.remove(studentids.getKey());
							studentsCount = studentsCount + 1;
						}
						studentMap1 = tempstudentMapP1;
						if(isPoolCompleated){
							studentMap2 = tempstudentMapP1;
							studentMap1 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						}
						isPoolOne = false;
					}else if(isPoolOne != null && !isPoolOne){
						int noOfRows = 0;
						boolean isPoolCompleated = false;
						if(studentMap2.size()<roomDetailsTo.getNoOfRows()){
							Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
							tempstudentMapP2.putAll(studentMap2);
							studentMap2 = CommonUtil.sortMapByKey(studentMap2);
							int studentsCount =0;
							for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
								noOfRows = noOfRows + 1;
								ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
								roomAllotmentDetailsSet.add(roomAllotmentDetails);
								tempstudentMapP2.remove(studentids.getKey());
								studentsCount = studentsCount + 1;
							}
							studentMap2 = tempstudentMapP2;
							
							
							if(secondCourseClassesList.size()>secondCount){
								if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
									studentMap2.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
								secondCount = secondCount +1;
							}else{
								if(courseWiseClassesMap.size()>=count){
									secondCourseClassesList = courseWiseClassesMap.get(count);
									count = count +1;
									secondCount = 0;
									if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
										studentMap2.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
									secondCount = secondCount +1;
								}else{
									if(oneCourseClassesList.size()>firstCount){
										if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
											studentMap2.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
										firstCount = firstCount +1;
									}else{
										studentMap2.putAll(studentMap1);
										isPoolCompleated = true;
									}
								}
							}
							if(studentMap2.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP3 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP3.putAll(studentMap2);
								studentMap2 = CommonUtil.sortMapByKey(studentMap2);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
									noOfRows = noOfRows + 1;
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP3.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMap2 = tempstudentMapP3;
								if(isPoolCompleated){
									studentMap1 = tempstudentMapP3;
									studentMap2 =  new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
								
								if(secondCourseClassesList.size()>secondCount){
									if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
										studentMap2.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
									secondCount = secondCount +1;
								}else{
									if(courseWiseClassesMap.size()>=count){
										secondCourseClassesList = courseWiseClassesMap.get(count);
										count = count +1;
										secondCount = 0;
										if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
											studentMap2.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
										secondCount = secondCount +1;
									}else{
										if(oneCourseClassesList.size()>firstCount){
											if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
												studentMap2.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
											firstCount = firstCount +1;
										}else{
											studentMap2.putAll(studentMap1);
											isPoolCompleated = true;
										}
									}
								}
							}
							if(studentMap2.size()<(roomDetailsTo.getNoOfRows()-noOfRows)){
								Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP3 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
								tempstudentMapP3.putAll(studentMap2);
								studentMap2 = CommonUtil.sortMapByKey(studentMap2);
								for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
									noOfRows = noOfRows + 1;
									ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
									roomAllotmentDetailsSet.add(roomAllotmentDetails);
									tempstudentMapP3.remove(studentids.getKey());
									studentsCount = studentsCount + 1;
								}
								studentMap2 = tempstudentMapP3;
								if(isPoolCompleated){
									studentMap1 = tempstudentMapP3;
									studentMap2 =  new HashMap<String, ExamRoomAllotmentDetailsTO>();
								}
								
								if(secondCourseClassesList.size()>secondCount){
									if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
										studentMap2.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
									secondCount = secondCount +1;
								}else{
									if(courseWiseClassesMap.size()>=count){
										secondCourseClassesList = courseWiseClassesMap.get(count);
										count = count +1;
										secondCount = 0;
										if(studentDetailsMap.get(secondCourseClassesList.get(secondCount)) != null)
											studentMap2.putAll(studentDetailsMap.get(secondCourseClassesList.get(secondCount)));
										secondCount = secondCount +1;
									}else{
										if(oneCourseClassesList.size()>firstCount){
											if(studentDetailsMap.get(oneCourseClassesList.get(firstCount)) != null)
												studentMap2.putAll(studentDetailsMap.get(oneCourseClassesList.get(firstCount)));
											firstCount = firstCount +1;
										}else{
											studentMap2.putAll(studentMap1);
											isPoolCompleated = true;
										}
									}
								}
							}
						}
						if(studentMap1.isEmpty() && studentMap2.isEmpty() && !isPoolCompleated){
							break;
						}
						Map<String, ExamRoomAllotmentDetailsTO> tempstudentMapP2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						tempstudentMapP2.putAll(studentMap2);
						studentMap2 = CommonUtil.sortMapByKey(studentMap2);
						int studentsCount =noOfRows;
						for (Entry<String, ExamRoomAllotmentDetailsTO> studentids : studentMap2.entrySet()) {
							if(studentsCount >= roomDetailsTo.getNoOfRows()){
								break;
							}
							ExamRoomAllotmentDetails roomAllotmentDetails = createRoomAllotmentDetailsBO(allotmentForm,roomDetailsTo,studentsCount,studentids);
							roomAllotmentDetailsSet.add(roomAllotmentDetails);
							tempstudentMapP2.remove(studentids.getKey());
							studentsCount = studentsCount + 1;
						}
						studentMap2 = tempstudentMapP2;
						if(isPoolCompleated){
							studentMap1 = tempstudentMapP2;
							studentMap2 = new HashMap<String, ExamRoomAllotmentDetailsTO>();
						}
						isPoolOne = true;
					}
				}
			}
			roomAllotment.setRoomAllotmentDetails(roomAllotmentDetailsSet);
			roomAllotments.add(roomAllotment);
		}
		/* get the count of students ,for whom the room allotment is not done . display the count as a message.*/ 
		int remainingStudentSize=0;
		if(!studentMap1.isEmpty() || !studentMap2.isEmpty()){
			remainingStudentSize = remainingStudentSize + studentMap1.size();
			remainingStudentSize = remainingStudentSize + studentMap2.size();
			if(oneCourseClassesList.size()>firstCount){
				for(int i= firstCount;oneCourseClassesList.size()>i;i++){
					remainingStudentSize = remainingStudentSize+ studentDetailsMap.get(oneCourseClassesList.get(firstCount)).size();
				}
			}
			if(secondCourseClassesList.size()>secondCount){
				for(int i = secondCount;secondCourseClassesList.size()>i;i++){
					remainingStudentSize = remainingStudentSize+ studentDetailsMap.get(secondCourseClassesList.get(secondCount)).size();
				}
			}
			if(courseWiseClassesMap.size()>=count){
				 oneCourseClassesList = courseWiseClassesMap.get(count);
				 count = count +1;
				 secondCourseClassesList = new ArrayList<String>();
				if(courseWiseClassesMap.get(count)!= null && !courseWiseClassesMap.get(count).isEmpty()){
					secondCourseClassesList = courseWiseClassesMap.get(count);
					count = count +1;
				}
				firstCount = 0;
				secondCount = 0;
				if(oneCourseClassesList.size()>firstCount){
					for(int i= firstCount;oneCourseClassesList.size()>i;i++){
						remainingStudentSize = remainingStudentSize+ studentDetailsMap.get(oneCourseClassesList.get(firstCount)).size();
					}
				}
				if(secondCourseClassesList.size()>secondCount){
					for(int i = secondCount;secondCourseClassesList.size()>i;i++){
						remainingStudentSize = remainingStudentSize+ studentDetailsMap.get(secondCourseClassesList.get(secondCount)).size();
					}
				}
			}
		}else{
			if(courseWiseClassesMap.size()>=count){
				 oneCourseClassesList = courseWiseClassesMap.get(count);
				 count = count +1;
				 secondCourseClassesList = new ArrayList<String>();
				if(courseWiseClassesMap.get(count)!= null && !courseWiseClassesMap.get(count).isEmpty()){
					secondCourseClassesList = courseWiseClassesMap.get(count);
					count = count +1;
				}
				firstCount = 0;
				secondCount = 0;
				if(oneCourseClassesList.size()>firstCount){
					for(int i= firstCount;oneCourseClassesList.size()>i;i++){
						remainingStudentSize = remainingStudentSize+ studentDetailsMap.get(oneCourseClassesList.get(firstCount)).size();
					}
				}
				if(secondCourseClassesList.size()>secondCount){
					for(int i = secondCount;secondCourseClassesList.size()>i;i++){
						remainingStudentSize = remainingStudentSize+ studentDetailsMap.get(secondCourseClassesList.get(secondCount)).size();
					}
				}
			}
		}
		allotmentForm.setRemainingStudentsCount(remainingStudentSize);
		return roomAllotments;
	}
	/**
	 * @param classList
	 * @param academicYear 
	 * @return
	 * @throws Exception
	 */
	public int getTotalNOofStudentsForSelectedClasses(List<Integer> classList, int academicYear) throws Exception{
		String hqlQuery = getTotalStudentQuery(academicYear);
		int totalStudents = transaction.getTotalStudentForClasses(classList,hqlQuery);
		return totalStudents;
	}
	/**
	 * @param academicYear
	 * @return
	 * @throws Exception
	 */
	private String getTotalStudentQuery(int academicYear) throws Exception{
		String hqlQuery = "select count(s.id) from Student s " +
		" left join s.examStudentDetentionRejoinDetails exam" +
	    " where s.isAdmitted=1 and s.isActive=1 and (s.isHide=0 or s.isHide is null)" +
		" and  s.classSchemewise.classes.id in (:classList)" +
	    " and s.admAppln.isCancelled=0  " +
		" and s.classSchemewise.curriculumSchemeDuration.academicYear="+academicYear+
		" and (exam.detain is null or exam.discontinued is null)" +
		" and (exam.rejoin = 1 or exam.rejoin is null)" +
		" order by s.registerNo";
		return hqlQuery;
	}
	/**
	 * @param allotmentForm
	 * @param roomIds
	 * @return
	 * @throws Exception
	 */
	public int getTotalRoomsCapacity(ExamRoomAllotmentForm allotmentForm, String roomIds) throws Exception{
			int totalRoomsCapcity =0;
			transaction.getExamTypeByExamName(allotmentForm);
			String[] rooms = new String[]{};
			rooms = roomIds.split(",");
			List<Integer> roomList =new ArrayList<Integer>();
			for(int i =0;rooms.length>i;i++){
				roomList.add(Integer.parseInt(rooms[i]));
			}
			List<RoomMaster> list = transaction.getSelectedRoomsDetails(allotmentForm,roomList);
			for (RoomMaster roomMaster : list) {
				if(allotmentForm.getMidOrEndSem().equalsIgnoreCase("M")){
					totalRoomsCapcity = totalRoomsCapcity + Integer.parseInt(roomMaster.getMidSemCapacity());
				}else if(allotmentForm.getMidOrEndSem().equalsIgnoreCase("E")){
					totalRoomsCapcity = totalRoomsCapcity + Integer.parseInt(roomMaster.getEndSemCapacity());
				}
			}
		return totalRoomsCapcity;
	}
	/**
	 * @param allotmentForm
	 * @param classList 
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicateAllotment(ExamRoomAllotmentForm allotmentForm, List<Integer> classList) throws Exception{
		boolean isDuplicate = transaction.getDuplicateAllotment( allotmentForm, classList);
		return isDuplicate;
	}
	/**
	 * @param allotmentForm
	 * @param classList 
	 * @return
	 * @throws Exception
	 */
	public boolean isTimeTableDefine(ExamRoomAllotmentForm allotmentForm, List<Integer> classList) throws Exception{
		boolean isTimeTableDefine  = transaction.isTimeTableDefineForClasses(allotmentForm,classList);
		return isTimeTableDefine;
	}
	/**
	 * @param allotmentForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkRoomIsAlreadyAlloted(ExamRoomAllotmentForm allotmentForm) throws Exception{
		String[] rooms =allotmentForm.getSelectedRooms();
		List<Integer> roomList =new ArrayList<Integer>();
		for(int i =0;rooms.length>i;i++){
			roomList.add(Integer.parseInt(rooms[i]));
		}
		List<Integer> classList = transaction.checkSelectedRoomsAlreadyAlloted(allotmentForm,roomList);
		boolean flag = false;
		if(classList!=null && !classList.isEmpty()){
			flag  = transaction.isTimeTableDefineForClasses1(allotmentForm,classList);
		}
		return flag;
	}
}
