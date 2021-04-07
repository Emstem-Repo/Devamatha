package com.kp.cms.handlers.attendance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.attendance.CopyPeriodsForm;
import com.kp.cms.helpers.attendance.CopyPeriodsHelpers;
import com.kp.cms.to.attendance.ClassSchemewiseTO;
import com.kp.cms.transactions.attandance.ICopyPeriodsTransaction;
import com.kp.cms.transactionsimpl.attendance.CopyPeriodsTxnImpl;

public class CopyPeriodsHandler {
	private CopyPeriodsHandler() {

	}

	private static volatile CopyPeriodsHandler copyPeriodsHandler = null;

	public static CopyPeriodsHandler getInstance() {
		if (copyPeriodsHandler == null) {
			copyPeriodsHandler = new CopyPeriodsHandler();
			return copyPeriodsHandler;
		}
		return copyPeriodsHandler;
	}

	ICopyPeriodsTransaction itransaction = CopyPeriodsTxnImpl.getInstance();

	/**
	 * @param fromYear
	 * @return
	 * @throws Exception
	 */
	public List<ClassSchemewiseTO> getClassesByYear(int fromYear)
			throws Exception {
		List<ClassSchemewise> classSchemewisesList = itransaction
				.getClassesByYear(fromYear);
		List<ClassSchemewiseTO> tos = CopyPeriodsHelpers.getInstance()
				.convertToTOBo(classSchemewisesList);
		return tos;
	}

	/**
	 * @param classSchemeWiseList
	 * @param toYear
	 * @param copyPeriodsForm
	 * @return
	 * @throws Exception
	 */
	public boolean savePeriods(List<ClassSchemewiseTO> classSchemeWiseList,
			int toYear, CopyPeriodsForm copyPeriodsForm) throws Exception {
		boolean isCopied = false;
		Map<String, ClassSchemewise> map = itransaction .getClassesByToYear(toYear);
		List<Period> periods = CopyPeriodsHelpers.getInstance().convertToTOBo( map, classSchemeWiseList, toYear, copyPeriodsForm);
		if (periods != null) {
			isCopied = itransaction.savePeriods(periods);
		}
		return isCopied;
	}
}
