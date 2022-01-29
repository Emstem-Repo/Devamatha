package com.kp.cms.handlers.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.forms.employee.OnlineResumerSubmissionForm;
import com.kp.cms.helpers.employee.OnlineResumerSubmissionHelper;
import com.kp.cms.to.employee.AchievementsTO;
import com.kp.cms.to.employee.EdicationDetailsTO;
import com.kp.cms.to.employee.JobTypeTO;
import com.kp.cms.to.employee.ProfessionalExperienceTO;
import com.kp.cms.transactions.employee.IOnlineResumerSubmissionTransaction;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.employee.OnlineResumerSubmissionTransactionImpl;

public class OnlineResumerSubmissionHandler {
	@SuppressWarnings("unused")
	private static final Log log = LogFactory
			.getLog(OnlineResumerSubmissionHandler.class);
	public static volatile OnlineResumerSubmissionHandler objHandler = null;

	public static OnlineResumerSubmissionHandler getInstance() {
		if (objHandler == null) {
			objHandler = new OnlineResumerSubmissionHandler();
			return objHandler;
		}
		return objHandler;
	}

	public Map<Integer, String> getListNationalityMap() throws Exception {
		return OnlineResumerSubmissionHelper.getInstance()
				.getListNationalityMap();
	}

	public Map<Integer, String> getListCountryMap() throws Exception {
		return OnlineResumerSubmissionHelper.getInstance().getCountryMap();
	}

	public List<JobTypeTO> getListOfJobType() throws Exception {
		return OnlineResumerSubmissionHelper.getInstance().getJobType();
	}

	public Map<Integer, String> getDepartmentMap() throws Exception {
		return OnlineResumerSubmissionHelper.getInstance().getDepartmentMap();
	}

	public Map<Integer, String> getDesignationMap() throws Exception {
		return OnlineResumerSubmissionHelper.getInstance().getDesignationMap();
	}

	public Map<Integer, String> getQualificationMap() throws Exception {
		return OnlineResumerSubmissionHelper.getInstance()
				.getQualificationMap();
	}

	public List<EdicationDetailsTO> getEdicationDetails(Map<Integer, String> map)
			throws Exception {
		return OnlineResumerSubmissionHelper.getInstance().getEdicationDetails(
				map);
	}

	public Map<Integer, String> getFunctionalAreaMap() throws Exception {
		return OnlineResumerSubmissionHelper.getInstance().getFunctionalArea();
	}

	public List<ProfessionalExperienceTO> getProfessionalExperienceList() {
		ArrayList<ProfessionalExperienceTO> list = new ArrayList<ProfessionalExperienceTO>();
		list.add(new ProfessionalExperienceTO());
		return list;
	}

	public List<ProfessionalExperienceTO> getListOfProfessional(
			List<ProfessionalExperienceTO> listOfProfessionalExperience) {
		ArrayList<ProfessionalExperienceTO> list = new ArrayList<ProfessionalExperienceTO>();

		for (ProfessionalExperienceTO objTO : listOfProfessionalExperience) {
			if (objTO.getQualificationLevel() != null
					&& objTO.getQualificationLevel().trim().length() > 0) {
				int id = Integer.parseInt(objTO.getQualificationLevel());

				objTO.setEducationMap(CommonAjaxImpl.getInstance()
						.getEducationByQualificationId(id));
			}

			list.add(objTO);
		}
		return list;
	}

	public List<AchievementsTO> getAchievementsList() {
		ArrayList<AchievementsTO> list = new ArrayList<AchievementsTO>();
		list.add(new AchievementsTO());
		return list;

	}

	public List<JobTypeTO> getListOfJobs(List<JobTypeTO> listJob) {
		ArrayList<JobTypeTO> list = new ArrayList<JobTypeTO>();
		for (JobTypeTO To : listJob) {
			To.getValue();
			list.add(To);
		}
		return list;
	}

	public boolean saveOnlineResume(OnlineResumerSubmissionForm objform)
			throws Exception {
		EmpOnlineResume objBO=OnlineResumerSubmissionHelper.getInstance().convertFormToBO(objform);
		IOnlineResumerSubmissionTransaction txn = new OnlineResumerSubmissionTransactionImpl();
		boolean result = txn.saveOnlineResume(objBO);
		return result;

	}
}
