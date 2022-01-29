package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.to.exam.ExamUnvSubCodeTO;
public class ExamUnvSubCodeHelper {

	public ArrayList<ExamUnvSubCodeTO> convertBOToForm(
			List<Object[]> listSelect_UnvSubCodeRows,String mode) {

		ArrayList<ExamUnvSubCodeTO> retutnList = new ArrayList<ExamUnvSubCodeTO>();

		Iterator<Object[]> itr = listSelect_UnvSubCodeRows.iterator();
		while (itr.hasNext()) {
			Object[] row = itr.next();
			int subjectId = (row[0] != null ? (Integer) row[0] : 0);
			String name = (row[1] != null ? (String) row[1] : "");
			String subjectCode = (row[2] != null ? (String) row[2] : "");
			String unvSubjectCode = (row[3] != null ? (String) row[3] : "");
			if(mode.equalsIgnoreCase("error"))
			{
				unvSubjectCode="";
			}
			retutnList.add(new ExamUnvSubCodeTO(subjectId, name, subjectCode,
					unvSubjectCode));
		}
		Collections.sort(retutnList);
		return retutnList;
	}

	public int convertBOToTO_SchemeNo_usingMaxValue(
			List<CurriculumSchemeUtilBO> listBO) {

		int schemeId = 0;
		for (CurriculumSchemeUtilBO bo : listBO) {

			schemeId = bo.getCourseSchemeId();

		}

		return schemeId;
	}

}
