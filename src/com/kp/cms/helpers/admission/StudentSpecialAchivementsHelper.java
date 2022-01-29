package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.SpecialAchievement;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.admission.StudentSpecialAchivementsForm;
import com.kp.cms.to.admission.SpecialAchivementsTo;

	public class StudentSpecialAchivementsHelper {
		private static final Log log = LogFactory.getLog(StudentSpecialAchivementsHelper.class);
		public static volatile StudentSpecialAchivementsHelper objHandler = null;

		public static StudentSpecialAchivementsHelper getInstance() {
			if (objHandler == null) {
				objHandler = new StudentSpecialAchivementsHelper();
				return objHandler;
			}
			return objHandler;
		}

	public SpecialAchievement convertFormTOToBO(StudentSpecialAchivementsForm objForm,Student student) throws Exception {
			
			SpecialAchievement achivementBO = new SpecialAchievement();
			if(objForm != null){
				achivementBO.setAchievement(objForm.getAchivements());
				achivementBO.setCreatedBy(objForm.getUserId());
				achivementBO.setCreatedDate(new Date());
				achivementBO.setIsActive(true);
				achivementBO.setLastModifiedDate(new Date());
				achivementBO.setModifiedBy(objForm.getUserId());
				achivementBO.setStudent(student);
				achivementBO.setTermNumber(Integer.parseInt(objForm.getTermNumber()));
			}
			return achivementBO;
		}

	public List<SpecialAchivementsTo> convertBoToTo(List<SpecialAchievement> specialAchievementList) 
	{
		List<SpecialAchivementsTo>list=new ArrayList<SpecialAchivementsTo>();
		for(SpecialAchievement achievement:specialAchievementList)
		{
			SpecialAchivementsTo to=new SpecialAchivementsTo();
			to.setId(achievement.getId());
			to.setAchivement(achievement.getAchievement().replaceAll("\n", "<br>"));
			to.setRegNo(achievement.getStudent().getRegisterNo());
			if(achievement.getTermNumber() != null){
				to.setTermNumber(String.valueOf(achievement.getTermNumber()));
			}
			list.add(to);
		}
		return list;
	}

	public void convetBoToForm(SpecialAchievement specialAchievement,StudentSpecialAchivementsForm objForm) 
	{
		objForm.setRegisterNo(specialAchievement.getStudent().getRegisterNo());
		objForm.setAchivements(specialAchievement.getAchievement());
		objForm.setDupRegNo(specialAchievement.getStudent().getRegisterNo());
		objForm.setTermNumber(String.valueOf(specialAchievement.getTermNumber()));
		objForm.setDupTermNumber(String.valueOf(specialAchievement.getTermNumber()));
	}

	public SpecialAchievement convertFormTOBO(StudentSpecialAchivementsForm objForm, Student student,SpecialAchievement oldBo) 
	{
		oldBo.setAchievement(objForm.getAchivements());
		oldBo.setLastModifiedDate(new Date());
		oldBo.setModifiedBy(objForm.getUserId());
		oldBo.setIsActive(true);
		oldBo.setStudent(student);
		oldBo.setTermNumber(Integer.parseInt(objForm.getTermNumber()));
		return oldBo;
	}

}
