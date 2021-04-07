package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.BlockHallTicketProcessForm;
import com.kp.cms.to.reports.StudentsAttendanceReportTO;

public class BlockHallTicketProcessHelper {
	/**
	 * Singleton object of BlockHallTicketProcessHelper
	 */
	private static volatile BlockHallTicketProcessHelper blockHallTicketProcessHelper = null;
	private static final Log log = LogFactory.getLog(BlockHallTicketProcessHelper.class);
	private BlockHallTicketProcessHelper() {
		
	}
	/**
	 * return singleton object of BlockHallTicketProcessHelper.
	 * @return
	 */
	public static BlockHallTicketProcessHelper getInstance() {
		if (blockHallTicketProcessHelper == null) {
			blockHallTicketProcessHelper = new BlockHallTicketProcessHelper();
		}
		return blockHallTicketProcessHelper;
	}
	/**
	 * @param blockHallTicketProcessForm
	 * @return
	 * @throws Exception
	 */
	public String getClassesQueryForExam( BlockHallTicketProcessForm blockHallTicketProcessForm) throws Exception {
		String query="select classes.id" +
				" from ExamDefinition e" +
				" join e.courseSchemeDetails courseDetails" +
				" join courseDetails.course.classes classes" +
				" join classes.classSchemewises classwise" +
				" join classwise.curriculumSchemeDuration.curriculumScheme c" +
				" where e.delIsActive=1 and e.id="+blockHallTicketProcessForm.getExamId()+
				" and classwise.curriculumSchemeDuration.academicYear=e.academicYear" +
				" and classes.termNumber=courseDetails.schemeNo" +
				" and classes.isActive=1 and courseDetails.course.program.programType.id="+blockHallTicketProcessForm.getProgramTypeId();
		if(blockHallTicketProcessForm.getProgramId()!=null && !blockHallTicketProcessForm.getProgramId().trim().isEmpty()){
			query=query+" and courseDetails.course.program.id="+blockHallTicketProcessForm.getProgramId();
		}
		if(blockHallTicketProcessForm.getCourseId()!=null && !blockHallTicketProcessForm.getCourseId().trim().isEmpty()){
			query=query+" and courseDetails.course.id="+blockHallTicketProcessForm.getCourseId();
		}
		if(blockHallTicketProcessForm.getSchemeNo()!=null && !blockHallTicketProcessForm.getSchemeNo().trim().isEmpty()){
			query=query+" and classes.termNumber="+blockHallTicketProcessForm.getSchemeNo();
		}
		return query;
	}
	/**
	 * @param list
	 * @param blockHallTicketProcessForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentsAttendanceReportTO> convertBoListtoToList( List<Object[]> list, BlockHallTicketProcessForm blockHallTicketProcessForm,Map<Integer,ExamBlockUnblockHallTicketBO> blockMap,List<Integer> listOfDetainedStudents/*,Map<Integer,Double> leaveMap*/) throws Exception{
		double reqPercentage=Double.parseDouble(blockHallTicketProcessForm.getReqPercentage());
		List<StudentsAttendanceReportTO> toList=new ArrayList<StudentsAttendanceReportTO>();
		try {
			if(list!=null && !list.isEmpty()){
				Iterator<Object[]> itr=list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if(obj[0]!=null && obj[1]!=null){
						Student student=(Student)obj[1];
						if(!listOfDetainedStudents.contains(student.getId())){
							StudentsAttendanceReportTO to=new StudentsAttendanceReportTO();
							to.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
							to.setRegisterNumber(student.getRegisterNo());
							to.setStudentId(student.getId());
							Classes classes=(Classes)obj[0];
							to.setClassName(classes.getName());
							to.setClassId(String.valueOf(classes.getId()));
							double classHeld=0;
							double classAtt=0;
							if(obj[2]!=null)
								classHeld=Integer.parseInt(obj[2].toString());
							if(obj[3]!=null)
								classAtt=Integer.parseInt(obj[3].toString());
							if(obj[4]!=null)
								classAtt=classAtt+Integer.parseInt(obj[4].toString());
							
							double percentage=0;
							if(classHeld>0 && classAtt>0){
								percentage=Math.round((classAtt/classHeld)*100);
								if(percentage>=75 && percentage<=85){
									if(obj[5]!=null){
										double leave=Double.parseDouble(obj[5].toString());
										percentage=Math.round(((classAtt+leave)/classHeld)*100);
									}
								}
								to.setPercentage(String.valueOf(percentage));
							}
							if(blockMap.containsKey(student.getId())){
								ExamBlockUnblockHallTicketBO bo=blockMap.get(student.getId());
								if(bo.getBlockReason().contains(blockHallTicketProcessForm.getComments()))
									to.setRemarks(bo.getBlockReason());
								else
									to.setRemarks(bo.getBlockReason()+","+blockHallTicketProcessForm.getComments());
								
								to.setId(bo.getId());
								to.setTempChecked(true);
							}else{
								to.setRemarks(blockHallTicketProcessForm.getComments());
								to.setTempChecked(false);
							}
							if(percentage<reqPercentage)
								toList.add(to);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		return toList;
	}
	/**
	 * @param blockHallTicketProcessForm
	 * @return
	 * @throws Exception
	 */
	public String blockQuery( BlockHallTicketProcessForm blockHallTicketProcessForm) throws Exception{
		String query=" select e.studentId,e from ExamBlockUnblockHallTicketBO e where e.examId="+blockHallTicketProcessForm.getExamId()+" and e.hallTktOrMarksCard='H' ";
		return query;
	}
	/**
	 * @param blockHallTicketProcessForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamBlockUnblockHallTicketBO> convertTOtoBOList( BlockHallTicketProcessForm blockHallTicketProcessForm) throws Exception {
		List<StudentsAttendanceReportTO> list=blockHallTicketProcessForm.getStudentList();
		List<ExamBlockUnblockHallTicketBO> boList=new ArrayList<ExamBlockUnblockHallTicketBO>();
		Iterator<StudentsAttendanceReportTO> itr=list.iterator();
		while (itr.hasNext()) {
			StudentsAttendanceReportTO to = itr .next();
			if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
				ExamBlockUnblockHallTicketBO bo=new ExamBlockUnblockHallTicketBO();
				if(to.getId()>0)
					bo.setId(to.getId());
				
				bo.setBlockReason(to.getRemarks());
				bo.setHallTktOrMarksCard("H");
				bo.setIsActive(true);
				bo.setCreatedBy(blockHallTicketProcessForm.getUserId());
				bo.setModifiedBy(blockHallTicketProcessForm.getUserId());
				bo.setLastModifiedDate(new Date());
				bo.setCreatedDate(new Date());
				bo.setStudentId(to.getStudentId());
				bo.setClassId(Integer.parseInt(to.getClassId()));
				bo.setExamId(Integer.parseInt(blockHallTicketProcessForm.getExamId()));
				boList.add(bo);
			}
		}
		return boList;
	}
	/**
	 * @param blockList
	 * @return
	 */
	public Map<Integer, ExamBlockUnblockHallTicketBO> convertBotoMap(List<Object[]> blockList) throws Exception {
		Map<Integer, ExamBlockUnblockHallTicketBO> map=new HashMap<Integer, ExamBlockUnblockHallTicketBO>();
		if(blockList!=null && !blockList.isEmpty()){
			Iterator<Object[]> itr=blockList.iterator();
			while (itr.hasNext()) {
				Object[] obj=itr.next();
				map.put(Integer.parseInt(obj[0].toString()),(ExamBlockUnblockHallTicketBO)obj[1]);
			}
		}
		return map;
	}
}
