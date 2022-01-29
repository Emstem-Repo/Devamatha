package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.StudentTO;

public class BulkSMStoStudentHelper {
	private static volatile BulkSMStoStudentHelper bulkSMStoStudentHelper = null;
	private static final Log log = LogFactory.getLog(SmsExamMarksToStudentHelper.class);
	public BulkSMStoStudentHelper()
	{
		
	}
	public static BulkSMStoStudentHelper getInstance()
	{
		if(bulkSMStoStudentHelper==null)
		{
			bulkSMStoStudentHelper = new BulkSMStoStudentHelper();
		}
		return bulkSMStoStudentHelper;
	}
	public List<StudentTO> convertBOtoTOList(List<Student> studentsBO) throws Exception{
        log.debug("call of convertBOtoTOList method in SmsExamMarksToStudentHelper.class");
        List<StudentTO> studentList =  new ArrayList<StudentTO>();
        try
        {
        	if(studentsBO!=null && !studentsBO.isEmpty())
        	{
        		Iterator<Student> iterator = studentsBO.iterator();
        		while(iterator.hasNext())
        		{
        			Student student = iterator.next();
        			StudentTO studentTO=new StudentTO();
        			studentTO.setId(student.getId());
        			studentTO.setClassId(student.getClassSchemewise().getId());
        			studentTO.setClassName(student.getClassSchemewise().getClasses().getName());
    				PersonalData data=student.getAdmAppln().getPersonalData();
    				if(data.getParentMob1()!=null){
    					studentTO.setMobileNo1(data.getParentMob1());
    				}
    				if(data.getParentMob2()!=null){
    					studentTO.setMobileNo2(data.getParentMob2());
    				}
    				String name="";
    				if(data.getFirstName()!=null){
    					name=name+data.getFirstName();
    				}
    				if(data.getMiddleName()!=null){
    					name=name+data.getMiddleName();
    				}
    				if(data.getLastName()!=null){
    					name=name+data.getLastName();
    				}
    				studentTO.setStudentName(name);
    				studentTO.setRegisterNo(student.getRegisterNo());
    				studentTO.setRollNo(student.getRollNo());
    				studentList.add(studentTO);
        		}
        	}
        	
        }
        catch (Exception e) {
			log.error("Error in convertBOtoTOList");
			log.error("Error is.."+e.getMessage());
			throw new ApplicationException(e);
		}
        log.debug("end of convertBOtoTOList method in SmsExamMarksToStudentHelper.class");
		return studentList;
	}
}
