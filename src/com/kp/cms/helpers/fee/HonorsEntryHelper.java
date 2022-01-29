package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.HonorsEntryBo;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.fee.HonorsEntryForm;
import com.kp.cms.to.admin.ProgramTO;

public class HonorsEntryHelper 
{
	private static volatile HonorsEntryHelper helper=null;
	
	private HonorsEntryHelper()
	{
		
	}
	
	public static HonorsEntryHelper getInstance()
	{
		if(helper==null)
			helper=new HonorsEntryHelper();
		return helper;
	}
	
	public HonorsEntryBo convertFormToBo(HonorsEntryForm form, Student student)
	{
		HonorsEntryBo entryBo=new HonorsEntryBo();
		entryBo.setAcademicYear(Integer.parseInt(form.getAcademicYear()));
		entryBo.setSemister(Integer.parseInt(form.getSemister()));
		entryBo.setStudent(student);
		
		Course course=new Course();
		course.setId(Integer.parseInt(form.getCourseId()));
		entryBo.setCourse(course);
		entryBo.setCreatedBy(form.getUserId());
		entryBo.setModifiedBy(form.getUserId());
		entryBo.setCreatedDate(new Date());
		entryBo.setModifiedDate(new Date());
		entryBo.setIsActive(true);
		
		return entryBo;
	}

	public List<ProgramTO> convertPrograms(List<Program> programs) 
	{
		List<ProgramTO>programTOs=new ArrayList<ProgramTO>();
		for(Program program:programs)
		{
			ProgramTO to=new ProgramTO();
			to.setId(program.getId());
			to.setName(program.getName());
			programTOs.add(to);
		}
		return programTOs;
	}

	public HonorsEntryBo convertBOToBO(HonorsEntryBo honorsEntryBo,HonorsEntryForm form) {
		
		HonorsEntryBo entryBo=new HonorsEntryBo();
		if(honorsEntryBo!=null){
		entryBo.setId(honorsEntryBo.getId()); 
		entryBo.setAcademicYear(honorsEntryBo.getAcademicYear());
		entryBo.setSemister(honorsEntryBo.getSemister());
		entryBo.setStudent(honorsEntryBo.getStudent());
		
		Course course=new Course();
		course.setId(honorsEntryBo.getCourse().getId());
		entryBo.setCourse(course);
		entryBo.setCreatedBy(honorsEntryBo.getCreatedBy());
		entryBo.setModifiedBy(form.getUserId());
		entryBo.setCreatedDate(honorsEntryBo.getCreatedDate());
		entryBo.setModifiedDate(new Date());
		entryBo.setIsActive(false);
		}
		return entryBo;
	}
}
