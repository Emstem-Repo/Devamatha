package com.kp.cms.handlers.admission;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.CurriculumSchemeForm;
import com.kp.cms.helpers.admission.CurriculumSchemeHelper;
import com.kp.cms.to.admission.CurriculumSchemeTO;
import com.kp.cms.transactions.admission.ICurriculumSchemeTransaction;
import com.kp.cms.transactionsimpl.admission.CurriculumSchemeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;



public class CurriculumSchemeHandler {
	private static final Log log = LogFactory.getLog(CurriculumSchemeHandler.class);
	public static volatile CurriculumSchemeHandler curriculumSchemeHandler = null;
	private CurriculumSchemeHandler()
	{		
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */

	public static CurriculumSchemeHandler getInstance() {
		if (curriculumSchemeHandler == null) {
			curriculumSchemeHandler = new CurriculumSchemeHandler();
			return curriculumSchemeHandler;
		}
		return curriculumSchemeHandler;
	}
/**
 * 
 * @param Returns coursename, program name & programtype name based on the courseId
 */
	ICurriculumSchemeTransaction transaction = new CurriculumSchemeTransactionImpl();
	
	public CurriculumSchemeTO getCourseProgrmProgramType(int courseId) throws Exception{
		log.info("Entering into getCourseProgrmProgramType of CurriculumSchemeHandler");
		Course course=transaction.getCourseProgrmProgramType(courseId);
		return CurriculumSchemeHelper.getInstance().populateCourseBOtoTo(course);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method saves a curricular scheme based on the startdate and
	 *         end date
	 * @throws Exception
	 */
	
	public boolean setCurriculumScheme(List<CurriculumSchemeTO> list1, HttpSession httpSession, String userId)throws Exception
	{
		log.info("Entering into setCurriculumScheme of CurriculumSchemeHandler");
		/**
		 * Getting the Form Bean parameters in a list and a CurriculumSchemeBo object from session
		 * Constructs the BO object for CurriculumScheme
		 */
		
		CurriculumScheme scheme = (CurriculumScheme) httpSession.getAttribute("curriculumScheme");	
		try{	
			CurriculumSchemeDuration curriculumSchemeDuration;
			CurriculumSchemeSubject curriculumSchemeSubject;
			Set<CurriculumSchemeDuration> durationSet = new HashSet<CurriculumSchemeDuration>();
			Iterator<CurriculumSchemeTO> it = list1.iterator();
			while (it.hasNext()) {
				CurriculumSchemeTO curriculumSchemeTO = it.next();
				curriculumSchemeDuration = new CurriculumSchemeDuration();
				curriculumSchemeDuration.setAcademicYear(curriculumSchemeTO.getYear()!=0 ? curriculumSchemeTO.getYear():0);
				curriculumSchemeDuration.setSemesterYearNo(curriculumSchemeTO.getSemister()!=0 ? curriculumSchemeTO.getSemister():0);
				curriculumSchemeDuration.setStartDate(CommonUtil.ConvertStringToSQLDate(curriculumSchemeTO.getStartDate()!=null ? curriculumSchemeTO.getStartDate():null));
				curriculumSchemeDuration.setEndDate(CommonUtil.ConvertStringToSQLDate(curriculumSchemeTO.getEndDate()!=null ? curriculumSchemeTO.getEndDate():null));
				curriculumSchemeDuration.setCreatedDate(new Date());
				curriculumSchemeDuration.setLastModifiedDate(new Date());
				curriculumSchemeDuration.setCreatedBy(userId);
				curriculumSchemeDuration.setModifiedBy(userId);
				Set<CurriculumSchemeSubject> subjectset = new HashSet<CurriculumSchemeSubject>();
				
				if(curriculumSchemeTO.getSubjectGroup()!=null){
				int arraySize = curriculumSchemeTO.getSubjectGroup().length;
				String[] array = curriculumSchemeTO.getSubjectGroup();
				for (int j = 0; j < arraySize; j++) {
					curriculumSchemeSubject = new CurriculumSchemeSubject();
					SubjectGroup subjectGroup = new SubjectGroup();
					subjectGroup.setId(Integer.parseInt(array[j]));
					curriculumSchemeSubject.setSubjectGroup(subjectGroup);
					curriculumSchemeSubject.setCreatedDate(new Date());
					curriculumSchemeSubject.setLastModifiedDate(new Date());
					curriculumSchemeSubject.setCreatedBy(userId);
					curriculumSchemeSubject.setModifiedBy(userId);
					subjectset.add(curriculumSchemeSubject);
					}
			}
				curriculumSchemeDuration.setCurriculumSchemeSubjects(subjectset);
				durationSet.add(curriculumSchemeDuration);

			}
			scheme.setCurriculumSchemeDurations(durationSet);
			scheme.setCreatedBy(userId);
			scheme.setModifiedBy(userId);
			if (scheme != null) {
				transaction.setCurriculumScheme(scheme);
			}
			log.info("Leaving into setCurriculumScheme of CurriculumSchemeHandler");
			return true;			
	}
			catch(Exception e){
				log.error("Unable to process.in add while constructing BO..."+e);
				throw  new ApplicationException(e);
				
			} 
				
			}
		

	/**
	 * 
	 * @param 
	 * @param Checking in database for duplicate record based on the courseID and year
	 * @return
	 */
	public List<CurriculumScheme> isExistCourseId(int courseId, int year)throws Exception
	{
		log.info("Inside of isExistCourseId of CurriculumSchemeHandler");		
		List<CurriculumScheme> isExistCourseId=transaction.isExistCourseId(courseId,year);
		return isExistCourseId;		
	}
	
	/**
	 * @param 
	 * @param Gets all the records of curriculumscheme
	 * @return
	 */
	
	public List<CurriculumSchemeTO> getCurriculumSchemeDetails()throws Exception
	{
		log.info("Entering into getCurriculumSchemeDetails of CurriculumSchemeHandler");		
		List<CurriculumScheme> curriculumSchemeDetailsList=transaction.getCurriculumSchemeDetails();
		List<CurriculumSchemeTO> curriculumSchemeDetails=CurriculumSchemeHelper.getInstance().populateCurriculumSchemeBOtoTO(curriculumSchemeDetailsList);
		log.info("leaving into getCurriculumSchemeDetails of CurriculumSchemeHandler");
		return curriculumSchemeDetails;
	}
	public List<CurriculumSchemeTO> getCurriculumSchemeDetailsYearwise(int year)throws Exception
	{
		log.info("Entering into getCurriculumSchemeDetails of CurriculumSchemeHandler");		
		List<CurriculumScheme> curriculumSchemeDetailsList=transaction.getCurriculumSchemeDetailsYearwise(year);
		List<CurriculumSchemeTO> curriculumSchemeDetails=CurriculumSchemeHelper.getInstance().populateCurriculumSchemeYearwiseBOtoTO(curriculumSchemeDetailsList);
		log.info("leaving into getCurriculumSchemeDetails of CurriculumSchemeHandler");
		return curriculumSchemeDetails;
	}
	/**
	 * 
	 * @param id
	 * @return Deletes a record in Curriculumscheme based on the ID
	 */
	public boolean deleteCurriculumScheme(int curriculumId, String userId) throws Exception
	{
		log.info("Entering into deleteCurriculumScheme of CurriculumSchemeHandler");
		CurriculumScheme curriculumScheme=new CurriculumScheme();
		curriculumScheme.setId(curriculumId);
		curriculumScheme.setModifiedBy(userId);
		curriculumScheme.setLastModifiedDate(new Date());
		if (transaction != null) {
			return transaction.deleteCurriculumScheme(curriculumScheme);
		}
		log.info("Leaving into deleteCurriculumScheme of CurriculumSchemeHandler");
		return false;	
	}
	
	/**
	 * 
	 * @param id
	 * @return updates a curriculumscheme
	 */
	public boolean updateCurriculumScheme(CurriculumSchemeForm curriculumSchemeForm) throws Exception
	{
	log.info("Entering into updateCurriculumScheme of CurriculumSchemeHandler");
	CurriculumScheme curriculumScheme=CurriculumSchemeHelper.getInstance().populateCurriculumSchemeTOtoBO(curriculumSchemeForm);
	return transaction.updateCurriculumScheme(curriculumScheme);	
	}
	/**
	 * 
	 * @returns currciculumscheme details based on id passed from UI
	 */
	
	public CurriculumSchemeTO getCurriculumSchemeDetailsOnId(int curriculumId, CurriculumSchemeForm curriculumSchemeForm, boolean edit)throws Exception
	{
		log.info("Entering into getCurriculumSchemeDetailsOnId of CurriculumSchemeHandler");
		CurriculumScheme curriculumSchemeBO=transaction.getCurriculumSchemeDetailsOnId(curriculumId);
		CurriculumSchemeTO curriculumSchemeTO=CurriculumSchemeHelper.getInstance().populateCurriculumSchemeBOtoTOOnId(curriculumSchemeBO, curriculumSchemeForm, edit);
		log.info("Leaving into getCurriculumSchemeDetailsOnId of CurriculumSchemeHandler");
		return curriculumSchemeTO;
	}
	
	public CurriculumSchemeDuration getCurriculumSchemeDurationByClassId(int classId)
	{
		log.info("Entering into getCurriculumSchemeDurationByClassId of CurriculumSchemeHandler");
		CurriculumSchemeDuration bo=transaction.getCurriculumSchemeDurationByClassId(classId);
		log.info("Leaving into getCurriculumSchemeDurationByClassId of CurriculumSchemeHandler");
		return bo;
	}
}
