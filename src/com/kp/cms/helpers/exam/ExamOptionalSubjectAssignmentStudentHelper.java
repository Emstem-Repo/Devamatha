package com.kp.cms.helpers.exam;

/**
 * Feb 18, 2010 Created By 9Elements
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.forms.exam.ExamOptionalSubjectAssignmentToStudentForm;
import com.kp.cms.to.exam.ExamOptAssSubTypeTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamOptionalSubjectAssignmentStudentImpl;

public class ExamOptionalSubjectAssignmentStudentHelper extends ExamGenHelper {

	/*
	 * Fields and Meanings:
	 * 
	 * impl.select_students_spec(classIdList) - returns student info and check
	 * mark
	 * 
	 * impl.select_specId_subGrpId(classIdList) - returns specialization id and
	 * optional group id
	 * 
	 * impl.select_subGrpId_SubGrpName(classIdList) - returns subjectgroup id
	 * and name
	 */

	public ArrayList<ExamOptAssSubTypeTO> convertBOToTO_getStudent_Details(
			List<Object[]> select_students_spec,
			List<Object[]> select_specId_subGrpId,
			List<Object[]> select_subGrpId_SubGrpName, ExamOptionalSubjectAssignmentToStudentForm objForm) throws Exception {

		HashMap<Integer, ArrayList<ExamOptAssSubTypeTO>> mapStuId_ToConvertToTO = getMapToConvertToTO(select_students_spec, objForm);
		ExamOptionalSubjectAssignmentStudentImpl impl = new ExamOptionalSubjectAssignmentStudentImpl();
		
		
		HashMap<Integer, ArrayList<KeyValueTO>> mapSpecId_SubGrp_KeyTO = getMapSpecId_SubGrp_KeyTO(
				select_specId_subGrpId,
				getMapSubGrpId_Name(select_subGrpId_SubGrpName));

		StringBuffer admApplnIds = new StringBuffer();
		ArrayList<ExamOptAssSubTypeTO> finalReturnList = new ArrayList<ExamOptAssSubTypeTO>();
		Set<Integer> listStudentId = mapStuId_ToConvertToTO.keySet();
		for (Integer studentId : listStudentId) {
			ArrayList<ExamOptAssSubTypeTO> listForStu = mapStuId_ToConvertToTO
					.get(studentId);
			
			boolean N_N_case = false;
			Integer spzId = null;
			for (int i = 0; !N_N_case && i < listForStu.size(); i++) {
				ExamOptAssSubTypeTO oTO = listForStu.get(i);
				spzId = oTO.getSpecializationId();
				ArrayList<KeyValueTO> listSubgroup_KeyVTO = mapSpecId_SubGrp_KeyTO
						.get(spzId);
				
				

				if (listSubgroup_KeyVTO != null
						&& listSubgroup_KeyVTO.size() == 1) {
					// case stu 1 or N subgr 1
					
					finalReturnList.add(new ExamOptAssSubTypeTO(oTO,
							listSubgroup_KeyVTO.get(0)));
					admApplnIds.append(oTO.getAdmApplnId() + ",");
				} else {
					if (listForStu != null && listForStu.size() == 1) {

						// case stu 1/ subgr N
						if (listSubgroup_KeyVTO != null
								&& listSubgroup_KeyVTO.size() > 0)
							for (KeyValueTO keyValueTO : listSubgroup_KeyVTO) {
								finalReturnList.add(new ExamOptAssSubTypeTO(
										oTO, keyValueTO));
								admApplnIds.append(oTO.getAdmApplnId() + ",");

							}
					} else {// case stu N/ subgr N

						N_N_case = true;
					}
				}
			}
			if (N_N_case && null != spzId) {

				ArrayList<KeyValueTO> listKeyVTO = mapSpecId_SubGrp_KeyTO
						.get(spzId);
				if (listKeyVTO != null && listKeyVTO.size() > 0)
					for (KeyValueTO keyValueTO : listKeyVTO) {
						
						ExamOptAssSubTypeTO eTO_ForIsAdded = null;
						Integer subGrpId = keyValueTO.getId();
						Iterator<ExamOptAssSubTypeTO> itr = listForStu
								.iterator();
						boolean idAdded = false;
						while (itr.hasNext() && !idAdded) {
							ExamOptAssSubTypeTO examOptAssSubTypeTO = (ExamOptAssSubTypeTO) itr
									.next();

							
							eTO_ForIsAdded = examOptAssSubTypeTO;
							if (subGrpId.equals(examOptAssSubTypeTO
									.getCheckId())) {
								finalReturnList.add(new ExamOptAssSubTypeTO(
										examOptAssSubTypeTO, keyValueTO));
								admApplnIds.append(examOptAssSubTypeTO.getAdmApplnId() + ",");
								idAdded = true;
							}
						}
						if (!idAdded) {
							eTO_ForIsAdded.setCheckId(null);
							finalReturnList.add(new ExamOptAssSubTypeTO(
									eTO_ForIsAdded, keyValueTO));
							admApplnIds.append(eTO_ForIsAdded.getAdmApplnId() + ",");

						}
					}
			}
		}
		String admApp = "";
		if (admApplnIds.toString().endsWith(",") == true) {
			admApp = StringUtils.chop(admApplnIds.toString());
		}
		Map<Integer, Map<Integer, Integer>> admMap = impl.getApplicantSubjectGroup(admApp);;
		Iterator<ExamOptAssSubTypeTO> itr =  finalReturnList.iterator();
		List<ExamOptAssSubTypeTO> updatedfinalReturnList = new ArrayList<ExamOptAssSubTypeTO>();
		while (itr.hasNext()) {
			ExamOptAssSubTypeTO examOptAssSubTypeTO = (ExamOptAssSubTypeTO) itr
					.next();
			Map<Integer, Integer> subGroupMap = admMap.get(examOptAssSubTypeTO.getAdmApplnId());
			
			if(subGroupMap == null || !subGroupMap.containsKey(examOptAssSubTypeTO.getOptSubGroupId())){
				updatedfinalReturnList.add(examOptAssSubTypeTO);
			}
			
		}
		Collections.sort(updatedfinalReturnList);
		return (ArrayList<ExamOptAssSubTypeTO>) updatedfinalReturnList;

	}

	/**
	 * @param select_students_spec
	 * @return
	 */
	private HashMap<Integer, ArrayList<KeyValueTO>> getMapSpecId_SubGrp_KeyTO(
			List<Object[]> select_specId_subGrpId,
			HashMap<Integer, String> mapSubGrpId_Name) {

		HashMap<Integer, ArrayList<KeyValueTO>> mapSpecId_SubGrp_KeyTO = new HashMap<Integer, ArrayList<KeyValueTO>>();

		Iterator<Object[]> itr = select_specId_subGrpId.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			Integer specId = (Integer) row[0];
			Integer subGrpId = (Integer) row[1];

			if (mapSpecId_SubGrp_KeyTO.containsKey(specId)) {
				ArrayList<KeyValueTO> list = mapSpecId_SubGrp_KeyTO.get(specId);

				list.add(new KeyValueTO(subGrpId, mapSubGrpId_Name
						.get(subGrpId)));
				mapSpecId_SubGrp_KeyTO.put(specId, list);
			} else {
				ArrayList<KeyValueTO> list = new ArrayList<KeyValueTO>();
				list.add(new KeyValueTO(subGrpId, mapSubGrpId_Name
						.get(subGrpId)));
				mapSpecId_SubGrp_KeyTO.put(specId, list);
			}
		}

		return mapSpecId_SubGrp_KeyTO;
	}

	private HashMap<Integer, String> getMapSubGrpId_Name(
			List<Object[]> select_subGrpId_SubGrpName) {
		// create map for subGrpId-SubGrpName; subGrpId as id
		HashMap<Integer, String> mapSubGrpId_Name = new HashMap<Integer, String>();
		Iterator<Object[]> itr = select_subGrpId_SubGrpName.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			mapSubGrpId_Name.put((Integer) obj[0], obj[1].toString());
		}
		return mapSubGrpId_Name;
	}

	private HashMap<Integer, ArrayList<ExamOptAssSubTypeTO>> getMapToConvertToTO(
			List<Object[]> select_students_spec, ExamOptionalSubjectAssignmentToStudentForm objForm) {
		// create map for select_students_spec; with student id as id
		ArrayList<ExamOptAssSubTypeTO> list;
		HashMap<Integer, ArrayList<ExamOptAssSubTypeTO>> mapToConvertToTO = new HashMap<Integer, ArrayList<ExamOptAssSubTypeTO>>();
		Iterator<Object[]> itr1 = select_students_spec.iterator();
		Integer studentId;
		StringBuffer admApplnIds = new StringBuffer();
		
		int size = 0;
		if(select_students_spec!= null){
			size = select_students_spec.size();
		}
		int i = 0;
		while (itr1.hasNext()) {
			Object[] row = (Object[]) itr1.next();

			ExamOptAssSubTypeTO to = new ExamOptAssSubTypeTO();
			if (row[0] != null) {
				to.setId((Integer) row[0]);
			}
			if (row[1] != null) {
				to.setStudentId((Integer) row[1]);
			}
			studentId = (Integer) row[1];
			if (row[2] != null) {
				to.setRollNo(row[2].toString());
			}
			if (row[3] != null) {
				to.setRegisterNo(row[3].toString());
			}
			if (row[4] != null /*&& row[5] != null*/) {
				to.setStudentname(row[4].toString()/* + " " + row[5].toString()*/);
			}
			if (row[6] != null) {
				to.setSpecialization(row[6].toString());
			}

			if (row[7] != null) {
				to.setOptSubGroup(row[7].toString());
			}

			if (row[8] != null) {
				to.setIsCheckedDummy(true);
				to.setCheckId((Integer) row[8]);
			} else {
				to.setIsCheckedDummy(false);
				to.setCheckId(null);
			}
			if (row[9] != null) {
				to.setSpecializationId((Integer) row[9]);
			}
			i++;
			if (row[10] != null) {
				to.setAdmApplnId((Integer) row[10]);
				admApplnIds.append(to.getAdmApplnId());
				if(i < size){
					admApplnIds.append(",");
				}
			}
			if (!mapToConvertToTO.containsKey(studentId)) {
				list = new ArrayList<ExamOptAssSubTypeTO>();
				list.add(to);
				mapToConvertToTO.put(studentId, list);
			} else {
				list = mapToConvertToTO.remove(studentId);
				list.add(to);
				mapToConvertToTO.put(studentId, list);
			}
		}
		return mapToConvertToTO;
	}
}
