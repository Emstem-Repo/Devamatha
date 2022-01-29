package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.phd.PhdSynopsisDefenseBO;
import com.kp.cms.forms.phd.PhdSynopsisDefenseForm;
import com.kp.cms.to.phd.PhdSynopsisDefenseTO;

public class PhdSynopsisDefenseHelper {
	private static final Log log = LogFactory.getLog(PhdSynopsisDefenseHelper.class);
	public static volatile PhdSynopsisDefenseHelper examCceFactorHelper = null;

	public static PhdSynopsisDefenseHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PhdSynopsisDefenseHelper();
		}
		return examCceFactorHelper;
	}


	public PhdSynopsisDefenseBO convertFormToBos(PhdSynopsisDefenseForm objForm) {
		
		PhdSynopsisDefenseBO synopsisDefenseBO = new PhdSynopsisDefenseBO();
	try{
		Student student=new Student();
		Classes classes=new Classes();
		Course course= new Course();
		student.setId(Integer.parseInt(objForm.getStudentId()));
		classes.setId(Integer.parseInt(objForm.getClassId()));
		course.setId(Integer.parseInt(objForm.getCourseId()));
		synopsisDefenseBO.setId(objForm.getId());
		synopsisDefenseBO.setStudentId(student);
		synopsisDefenseBO.setClassId(classes);
		synopsisDefenseBO.setCourseId(course);
		synopsisDefenseBO.setName(objForm.getName());
		synopsisDefenseBO.setType(objForm.getType());
		synopsisDefenseBO.setContactNo(objForm.getContactNo());
		synopsisDefenseBO.setEmail(objForm.getEmail());
		synopsisDefenseBO.setAddressLine1(objForm.getAddressLine1());
		synopsisDefenseBO.setAddressLine2(objForm.getAddressLine2());
		synopsisDefenseBO.setAddressLine3(objForm.getAddressLine3());
		synopsisDefenseBO.setAddressLine4(objForm.getAddressLine4());
		synopsisDefenseBO.setRemarks(objForm.getRemarks());
		synopsisDefenseBO.setPinCode(objForm.getPinCode());
		if(objForm.getSelectedMember()!=null && !objForm.getSelectedMember().isEmpty()&& objForm.getSelectedMember().equalsIgnoreCase("on")){
		synopsisDefenseBO.setSelectedMember(true);
		}else{
			synopsisDefenseBO.setSelectedMember(false);
		}
		synopsisDefenseBO.setCreatedBy(objForm.getUserId());
		synopsisDefenseBO.setCreatedDate(new Date());
		synopsisDefenseBO.setLastModifiedDate(new Date());
		synopsisDefenseBO.setModifiedBy(objForm.getUserId());
		synopsisDefenseBO.setIsActive(Boolean.valueOf(true));
	}catch (Exception e) {
			e.printStackTrace();
		}
		return synopsisDefenseBO;
			
   }

	public void setDataBoToForm(PhdSynopsisDefenseForm objForm,PhdSynopsisDefenseBO synopsisDefenseBO) {
	  if(synopsisDefenseBO != null)
        {
		  objForm.setId(synopsisDefenseBO.getId());
		  objForm.setStudentName(synopsisDefenseBO.getStudentId().getAdmAppln().getPersonalData().getFirstName());
		  objForm.setStudentId(Integer.toString(synopsisDefenseBO.getStudentId().getId()));
		  objForm.setClassName(synopsisDefenseBO.getClassId().getName());
		  objForm.setClassId(Integer.toString(synopsisDefenseBO.getClassId().getId()));
		  objForm.setCourseName(synopsisDefenseBO.getCourseId().getName());
		  objForm.setCourseId(Integer.toString(synopsisDefenseBO.getCourseId().getId()));
		  objForm.setType(synopsisDefenseBO.getType());
		  objForm.setName(synopsisDefenseBO.getName());
		  objForm.setContactNo(synopsisDefenseBO.getContactNo());
		  objForm.setEmail(synopsisDefenseBO.getEmail());
		  objForm.setAddressLine1(synopsisDefenseBO.getAddressLine1());
		  objForm.setAddressLine2(synopsisDefenseBO.getAddressLine2());
		  objForm.setAddressLine3(synopsisDefenseBO.getAddressLine3());
		  objForm.setAddressLine4(synopsisDefenseBO.getAddressLine4());
		  objForm.setRemarks(synopsisDefenseBO.getRemarks());
		  objForm.setSelectedMember(synopsisDefenseBO.getSelectedMember() ? "on" : "null");
		  objForm.setPinCode(synopsisDefenseBO.getPinCode());
        }
    }


	public PhdSynopsisDefenseBO convertToBos(PhdSynopsisDefenseForm objForm) {
		PhdSynopsisDefenseBO synopsisDefenseBO = new PhdSynopsisDefenseBO();
		try{
			Student student=new Student();
			Classes classes=new Classes();
			Course course= new Course();
			student.setId(Integer.parseInt(objForm.getStudentId()));
			classes.setId(Integer.parseInt(objForm.getClassId()));
			course.setId(Integer.parseInt(objForm.getCourseId()));
			synopsisDefenseBO.setId(objForm.getId());
			synopsisDefenseBO.setStudentId(student);
			synopsisDefenseBO.setClassId(classes);
			synopsisDefenseBO.setCourseId(course);
			synopsisDefenseBO.setType(objForm.getType());
			synopsisDefenseBO.setName(objForm.getName());
			synopsisDefenseBO.setContactNo(objForm.getContactNo());
			synopsisDefenseBO.setEmail(objForm.getEmail());
			synopsisDefenseBO.setAddressLine1(objForm.getAddressLine1());
			synopsisDefenseBO.setAddressLine2(objForm.getAddressLine2());
			synopsisDefenseBO.setAddressLine3(objForm.getAddressLine3());
			synopsisDefenseBO.setAddressLine4(objForm.getAddressLine4());
			synopsisDefenseBO.setRemarks(objForm.getRemarks());
			synopsisDefenseBO.setPinCode(objForm.getPinCode());
			if(objForm.getSelectedMember().equalsIgnoreCase("off")){
				synopsisDefenseBO.setSelectedMember(true);
				}else{
					synopsisDefenseBO.setSelectedMember(false);
				}
			synopsisDefenseBO.setCreatedBy(objForm.getUserId());
			synopsisDefenseBO.setCreatedDate(new Date());
			synopsisDefenseBO.setLastModifiedDate(new Date());
			synopsisDefenseBO.setModifiedBy(objForm.getUserId());
			synopsisDefenseBO.setIsActive(Boolean.valueOf(true));
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return synopsisDefenseBO;
   }


	public List<PhdSynopsisDefenseTO> setdatatolist(List<Object[]> studentdetails, PhdSynopsisDefenseForm objForm) {
		List<PhdSynopsisDefenseTO> details=new ArrayList<PhdSynopsisDefenseTO>();
		if(studentdetails!=null && !studentdetails.isEmpty()){
			Iterator itr=studentdetails.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				PhdSynopsisDefenseTO PhdTo=new PhdSynopsisDefenseTO();
				if(object[0]!=null && object[1]!=null && object[2]!=null){
					objForm.setStudentName(object[0].toString()+" "+object[1].toString()+""+object[2].toString());
				}if(object[0]!=null &&  object[2]!=null){
					objForm.setStudentName(object[0].toString()+""+object[2].toString());
				}if(object[0]!=null &&  object[2]==null){
					objForm.setStudentName(object[0].toString());
				}if(object[3]!=null){
					objForm.setStudentId(object[3].toString());
				}if(object[4]!=null){
					objForm.setClassName(object[4].toString());
				}if(object[5]!=null){
					objForm.setClassId(object[5].toString());
				}if(object[6]!=null){
					objForm.setCourseName(object[6].toString());
				}if(object[7]!=null){
					objForm.setCourseId(object[7].toString());
				}
				details.add(PhdTo);
			}
		}
		return details;
	}


	public List<PhdSynopsisDefenseTO> setdatatogrid(List<PhdSynopsisDefenseBO> phdSynopsis) {
		List<PhdSynopsisDefenseTO> listto=new ArrayList<PhdSynopsisDefenseTO>();
		Iterator<PhdSynopsisDefenseBO> itr=phdSynopsis.iterator();
		while (itr.hasNext()) {
			PhdSynopsisDefenseBO phdSynopsisDefenseBO = (PhdSynopsisDefenseBO) itr.next();
			PhdSynopsisDefenseTO PhdTo=new PhdSynopsisDefenseTO();
			PhdTo.setId(phdSynopsisDefenseBO.getId());
			if(phdSynopsisDefenseBO.getStudentId().getRegisterNo()!=null){
				PhdTo.setRegisterNo(phdSynopsisDefenseBO.getStudentId().getRegisterNo());
			}if(phdSynopsisDefenseBO.getName()!=null){
				PhdTo.setName(phdSynopsisDefenseBO.getName());
			}if(phdSynopsisDefenseBO.getContactNo()!=null){
				PhdTo.setContactNo(phdSynopsisDefenseBO.getContactNo());
			}if(phdSynopsisDefenseBO.getEmail()!=null){
				PhdTo.setEmail(phdSynopsisDefenseBO.getEmail());
			}if(phdSynopsisDefenseBO.getAddressLine1()!=null){
				PhdTo.setAddressLine1(phdSynopsisDefenseBO.getAddressLine1());
			}if(phdSynopsisDefenseBO.getSelectedMember()){
				PhdTo.setSelectedMember("Yes");
			}if(phdSynopsisDefenseBO.getType()!=null && !phdSynopsisDefenseBO.getType().isEmpty()){
				PhdTo.setType(phdSynopsisDefenseBO.getType());
			}
			listto.add(PhdTo);
			
		}
		return listto;
	}
}
