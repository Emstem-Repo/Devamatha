package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.SpecialAchievement;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.StudentSpecialAchivementsForm;
import com.kp.cms.helpers.admission.StudentSpecialAchivementsHelper;
import com.kp.cms.to.admission.SpecialAchivementsTo;
import com.kp.cms.transactions.admission.IStudentSpecialAchivementsTransaction;
import com.kp.cms.transactionsimpl.admission.StudentSpecialAchivementsTxnImpl;

		public class StudentSpecialAchivementsHandlers {
			private static final Log log = LogFactory.getLog(StudentSpecialAchivementsHandlers.class);
				IStudentSpecialAchivementsTransaction transaction = new StudentSpecialAchivementsTxnImpl();
				public static volatile StudentSpecialAchivementsHandlers objHandler = null;
		
			public static StudentSpecialAchivementsHandlers getInstance() {
					if (objHandler == null) {
						objHandler = new StudentSpecialAchivementsHandlers();
						return objHandler;
					}
					return objHandler;
				}

			/** to add the achivements
			 * @param objForm
			 * @param student
			 * @return
			 * @throws Exception
			 */
			public boolean addAchivements(StudentSpecialAchivementsForm objForm,Student student) throws Exception 
			{
				SpecialAchievement specialAchievement=transaction.duplicateCheck(student,objForm.getTermNumber());
				if(specialAchievement!=null)
				{	
					if(specialAchievement.getIsActive())
					{
						throw new DuplicateException();
					}
					else
					{
						objForm.setReactivateId(specialAchievement.getId());
						throw new ReActivateException();
					}
				}	
				SpecialAchievement achivementBO = StudentSpecialAchivementsHelper.getInstance().convertFormTOToBO(objForm,student);
				return transaction.addAchivements(achivementBO);
			}

			/** to check valid reg No or not
			 * @param regNo
			 * @return
			 * @throws Exception
			 */
			public Student validRegNo(String regNo)throws Exception {
					
					return transaction.validRegNo(regNo);
				}

			public List<SpecialAchivementsTo> getAchivementList()throws Exception 
			{
				List<SpecialAchievement> specialAchievementList=transaction.getSpecialAchivementList();
				List<SpecialAchivementsTo>specialAchivementsTos=StudentSpecialAchivementsHelper.getInstance().convertBoToTo(specialAchievementList);
				return specialAchivementsTos;
			}

			public boolean deleteStudentAchivements(Integer id) throws Exception
			{
				return transaction.deleteStudentAchivements(id);
			}
			
			public boolean editAchivement(StudentSpecialAchivementsForm objForm)throws Exception
			{
				SpecialAchievement specialAchievement=transaction.getSpecialAchivement(objForm.getId());
				if(specialAchievement!=null)
				{
					StudentSpecialAchivementsHelper.getInstance().convetBoToForm(specialAchievement,objForm);
					return true;
				}
				else
					return false;
			}

			public boolean updateAchivements(StudentSpecialAchivementsForm objForm, Student student) throws Exception
			{
					
				if(!objForm.getRegisterNo().equalsIgnoreCase(objForm.getDupRegNo()) || !objForm.getTermNumber().equalsIgnoreCase(objForm.getDupTermNumber()) || objForm.getDupTermNumber() == null)
				{	
					SpecialAchievement specialAchievement=transaction.duplicateCheck(student, objForm.getTermNumber());
					if(specialAchievement!=null)
					{	
						if(specialAchievement.getIsActive())
						{
							throw new DuplicateException();
						}
						else
						{
							objForm.setReactivateId(specialAchievement.getId());
							throw new ReActivateException();
						}
					}
				}
				SpecialAchievement oldBo=transaction.getSpecialAchivement(objForm.getId());
				SpecialAchievement achievement = StudentSpecialAchivementsHelper.getInstance().convertFormTOBO(objForm,student,oldBo);
				return transaction.updateAchivements(achievement);
			}

			public boolean reActivateStudentAchivements(Integer id)throws Exception 
			{
				return transaction.reActivateAchivement(id);
			}

			/**
			 * @param regNo
			 * @return
			 * @throws Exception
			 */
			public Map<Integer, String> getTermNumbers() throws Exception{
				
				Map<Integer, String> map = transaction.getTermNumberMap();
				return map;
			}
			public String getCurrentTermNumbers(String regNo) throws Exception{
				
				String termNumber = transaction.getCurrentTermNumber(regNo);
				return termNumber;
			}
		
		}
