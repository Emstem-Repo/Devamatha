package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamValuationRemuneration;
import com.kp.cms.forms.exam.ExamValuationRemuPaymentForm;
import com.kp.cms.to.exam.ExamValuationRemuPaymentTo;


public class ExamValuationRemuPaymentHelper {
	public static volatile ExamValuationRemuPaymentHelper examValuationRemuPaymentHelper = null;
	
	public static ExamValuationRemuPaymentHelper getInstance() {
		if (examValuationRemuPaymentHelper == null) {
			examValuationRemuPaymentHelper = new ExamValuationRemuPaymentHelper();
			return examValuationRemuPaymentHelper;
		}
		return examValuationRemuPaymentHelper;
	}
	
	public String getQuery(ExamValuationRemuPaymentForm examValuationRemuPaymentForm) throws Exception{
		String query="SELECT exam_valuation_remuneration.id as id,exam_valuation_remuneration.bill_no as vocherNo,exam_valuation_remuneration.user_id as userId," +
				" exam_valuation_remuneration.external_id as externalUserId,date_Format(exam_valuation_remuneration.created_date, '%d/%m/%y')as date," +
				" exam_valuation_remuneration.travel_allowance as ta,exam_valuation_remuneration.dearness_allowance as da," +
				" exam_valuation_remuneration.any_other_cost as otherCost,exam_valuation_remuneration.board_meetings as boardMeetings," +
				" exam_valuation_remuneration.conveyance_charges,users.user_name as username," +
				" sum(if((exam_remuneration_details.type='Valuator') or (exam_remuneration_details.type='Re-Valuator'), exam_remuneration_details.total_scripts* (select valuation_charges.valuator_charge" +
				" from subject inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
				" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id " +
				" inner join course ON subject_group.course_id = course.id " +
				" inner join program ON course.program_id = program.id " +
				" inner join program_type ON program.program_type_id = program_type.id " +
				" inner join valuation_charges on valuation_charges.program_type_id=program_type.id" +
				" and valuation_charges.is_active=1 where subject.id = exam_remuneration_details.subject_id" +
				" group by subject.id),if(exam_remuneration_details.type='Reviewer', exam_remuneration_details.total_scripts* (select valuation_charges.reviewer_charge" +
				" from subject inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id" +
				" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id" +
				" inner join course ON subject_group.course_id = course.id " +
				" inner join program ON course.program_id = program.id " +
				" inner join program_type ON program.program_type_id = program_type.id" +
				" inner join valuation_charges on valuation_charges.program_type_id=program_type.id and valuation_charges.is_active=1" +
				" where subject.id = exam_remuneration_details.subject_id group by subject.id),if(exam_remuneration_details.type='Project Major', exam_remuneration_details.total_scripts* (select valuation_charges.project_evaluation_major" +
				" from subject inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
				" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id " +
				" inner join course ON subject_group.course_id = course.id " +
				" inner join program ON course.program_id = program.id " +
				" inner join program_type ON program.program_type_id = program_type.id" +
				" inner join valuation_charges on valuation_charges.program_type_id=program_type.id " +
				" and valuation_charges.is_active=1 where subject.id = exam_remuneration_details.subject_id " +
				" group by subject.id),if(exam_remuneration_details.type='Project Minor', exam_remuneration_details.total_scripts* (select valuation_charges.project_evaluation_minor " +
				" from subject inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
				" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id " +
				" inner join course ON subject_group.course_id = course.id " +
				" inner join program ON course.program_id = program.id " +
				" inner join program_type ON program.program_type_id = program_type.id " +
				" inner join valuation_charges on valuation_charges.program_type_id=program_type.id " +
				" and valuation_charges.is_active=1 where subject.id = exam_remuneration_details.subject_id" +
				" group by subject.id),0))))) as scriptamount,exam_external_evaluators.name as name," +
				" ifnull(employee.first_name, ifnull(ifnull(guest_faculty.first_name, users.user_name),exam_external_evaluators.name))as name1," +
				" (select valuation_charges.board_meeting_charge from subject " +
				" inner join subject_group_subjects on subject_group_subjects.subject_id = subject.id " +
				" inner join subject_group ON subject_group_subjects.subject_group_id = subject_group.id " +
				" inner join course ON subject_group.course_id = course.id " +
				" inner join program ON course.program_id = program.id" +
				" inner join program_type ON program.program_type_id = program_type.id" +
				" inner join valuation_charges on valuation_charges.program_type_id=program_type.id and valuation_charges.is_active=1" +
				" where subject.id = exam_remuneration_details.subject_id group by subject.id) as boardcharge," +
				" exam_remuneration_details.type,(select valuation_meeting_charges.conveyance_charge from valuation_meeting_charges) as conveyance," +
				" ifnull(department.name,exam_external_evaluators.department) as department,exam_external_evaluators.bank_acc_no as external_acc," +
				" concat(exam_external_evaluators.bank_name, ',', ifnull(exam_external_evaluators.bank_branch, '')) as external_bank," +
				" exam_external_evaluators.bank_ifsc_code as external_ifsc,exam_external_evaluators.pan_no as panNo,employee.bank_account_no as employee_acc," +
				" guest_faculty.bank_account_no as guest_acc,exam_external_evaluators.mobile_no as externalEvaluatorMobileNo," +
				" guest_faculty.mobile as guestMobileNo,employee.current_address_mobile1 as emploeeMobileNo," +
				" guest_faculty.pan_no AS guest_pan, guest_faculty.bank_branch AS guest_bank, guest_faculty.bank_ifsc_code AS guest_ifsc," +
				" concat(exam_external_evaluators.addr_line1, ',', ifnull(exam_external_evaluators.addr_line2, ''), ',', ifnull(exam_external_evaluators.city, ''), ',', ifnull(state.name, ''), ',', ifnull(country.name, ''),'-', ifnull(exam_external_evaluators.pin, '')) AS external_address, " +
				" employee.pan_no AS emp_pan,exam_valuation_remuneration.mode_of_payment " +
				" FROM exam_remuneration_details" +
				" INNER JOIN exam_valuation_remuneration ON exam_remuneration_details.remuneration_id = exam_valuation_remuneration.id" +
				" and exam_valuation_remuneration.is_active=1 and exam_remuneration_details.is_active=1 " +
				" left join exam_external_evaluators ON exam_valuation_remuneration.external_id = exam_external_evaluators.id and exam_external_evaluators.is_active=1 " +
				" left join users ON exam_valuation_remuneration.user_id = users.id and users.is_active=1 " +
				" left join employee ON users.employee_id = employee.id " +
				" left join guest_faculty on guest_faculty.id=users.guest_id" +
				" left JOIN department ON ifnull(employee.department_id, users.department_id) = department.id " +
				" left JOIN state ON exam_external_evaluators.state_id = state.id" +
				" left JOIN country ON exam_external_evaluators.country_id = country.id" +
				" where exam_valuation_remuneration.is_active=1 " ;
				
				if(examValuationRemuPaymentForm.getPaidStauts().equalsIgnoreCase("paid")){
					query=query+" and exam_valuation_remuneration.is_paid=1";
				}else{
					query=query+" and (exam_valuation_remuneration.is_paid=0 or exam_valuation_remuneration.is_paid is null)";
				}
				if(examValuationRemuPaymentForm.getValuatorsType().equalsIgnoreCase("I")){
					query=query+" and exam_valuation_remuneration.user_id is not null";
				}else if(examValuationRemuPaymentForm.getValuatorsType().equalsIgnoreCase("E")){
					query=query+" and exam_valuation_remuneration.user_id is null";
				}
				query=query+" group by exam_valuation_remuneration.bill_no,exam_valuation_remuneration.created_date,exam_valuation_remuneration.user_id,exam_valuation_remuneration.external_id" +
				" order by exam_valuation_remuneration.bill_no";
		return query;
	}
	
	public List<ExamValuationRemuPaymentTo> convertObjectsToTos(
			List<Object[]> objects, ExamValuationRemuPaymentForm examValuationRemuPaymentForm, Map<Integer, Map<Integer, Double>> boardCharge) throws Exception{
		List<ExamValuationRemuPaymentTo> list=null;
		if(objects!=null && !objects.isEmpty()){
			Map<Integer,ExamValuationRemuPaymentTo> tosMap=new HashMap<Integer, ExamValuationRemuPaymentTo>();
			Map<String,String> modeOfPaymentMap= new HashMap<String, String>();
			modeOfPaymentMap.put("Electronic transfer","Electronic transfer");
			modeOfPaymentMap.put("Cheque", "Cheque");
			list=new ArrayList<ExamValuationRemuPaymentTo>();
			ExamValuationRemuPaymentTo examValuationRemuPaymentTo=null;
			Iterator<Object[]> iterator=objects.iterator();
			while (iterator.hasNext()) {
				Object[] object = (Object[]) iterator.next();
				examValuationRemuPaymentTo=new ExamValuationRemuPaymentTo();
				if(object[13]!=null){
					if(object[0]!=null){
						examValuationRemuPaymentTo.setId(Integer.parseInt(object[0].toString()));
					}
					if(object[1]!=null){
						examValuationRemuPaymentTo.setVocherNo(Integer.parseInt(object[1].toString()));
					}
					if(object[3]!=null){
						examValuationRemuPaymentTo.setOtherEmpId(object[3].toString());
					}
					if(object[4]!=null){
						examValuationRemuPaymentTo.setDate(object[4].toString());
					}
					if(object[5]!=null){
						examValuationRemuPaymentTo.setTa(object[5].toString());
					}
					if(object[6]!=null){
						examValuationRemuPaymentTo.setDa(object[6].toString());
					}
					if(object[7]!=null){
						examValuationRemuPaymentTo.setOtherCost(object[7].toString());
					}
					if(object[8]!=null){
						examValuationRemuPaymentTo.setTotalBoardMeetings(object[8].toString());
					}
					if(object[9]!=null){
						examValuationRemuPaymentTo.setTotalNoOfConveyance(object[9].toString());
					}
					if(object[14]!=null){
						examValuationRemuPaymentTo.setBoardMeeetingRate(object[14].toString());
					}
					if(boardCharge != null && !boardCharge.isEmpty()){
						if(object[2] != null){
							if(boardCharge.get(Integer.parseInt(object[2].toString())) != null){
								Double amount=0.0;
								if(boardCharge.get(Integer.parseInt(object[2].toString())).get(Integer.parseInt(object[1].toString()))!=null ){
								 amount=(Double.valueOf(object[11].toString())+(boardCharge.get(Integer.parseInt(object[2].toString())).get(Integer.parseInt(object[1].toString())))+
										(Integer.parseInt(object[9].toString())*Double.valueOf(object[16].toString())));
								}else{
									 amount=(Double.valueOf(object[11].toString())+
											(Integer.parseInt(object[9].toString())*Double.valueOf(object[16].toString())));
								}
								examValuationRemuPaymentTo.setTotalAmount(String.valueOf(amount));
							}else{
								Double amount=(Double.valueOf(object[11].toString())+
										(Integer.parseInt(object[9].toString())*Double.valueOf(object[16].toString())));
								examValuationRemuPaymentTo.setTotalAmount(String.valueOf(amount));
							}
						}else if(object[3] != null){
							if(boardCharge.get(Integer.parseInt(object[3].toString())) != null){
								Double amount=0.0;
								if(boardCharge.get(Integer.parseInt(object[3].toString())).get(Integer.parseInt(object[1].toString()))!=null){
									amount=(Double.valueOf(object[11].toString())+(boardCharge.get(Integer.parseInt(object[3].toString())).get(Integer.parseInt(object[1].toString())))+
											(Integer.parseInt(object[9].toString())*Double.valueOf(object[16].toString())));
								}else{
									 amount=(Double.valueOf(object[11].toString())+
											(Integer.parseInt(object[9].toString())*Double.valueOf(object[16].toString())));
								}
								examValuationRemuPaymentTo.setTotalAmount(String.valueOf(amount));
							}else{

								Double amount=(Double.valueOf(object[11].toString())+
										(Integer.parseInt(object[9].toString())*Double.valueOf(object[16].toString())));
								examValuationRemuPaymentTo.setTotalAmount(String.valueOf(amount));
							
							}
						}
					}
					if(object[13]!=null){
						examValuationRemuPaymentTo.setName(object[13].toString());
					}
					if(object[17]!=null){
						examValuationRemuPaymentTo.setDepartment(object[17].toString());
					}
					if(object[22]!=null){
						examValuationRemuPaymentTo.setAccountNo(object[22].toString());
					}else if(object[23]!=null ){
						examValuationRemuPaymentTo.setAccountNo(object[23].toString());
					}else if(object[18]!=null ){
						examValuationRemuPaymentTo.setAccountNo(object[18].toString());
					}
					if(object[19]!=null){
						examValuationRemuPaymentTo.setBankName(object[19].toString());
					}else if(object[28]!=null){
						examValuationRemuPaymentTo.setBankName(object[28].toString());
					}
					if(object[20]!=null){
						examValuationRemuPaymentTo.setIfscCode(object[20].toString());
					}else if(object[29]!=null){
						examValuationRemuPaymentTo.setIfscCode(object[29].toString());
					}
					if(object[21]!=null){
						examValuationRemuPaymentTo.setPanNo(object[21].toString());
					}else if(object[27]!=null){
						examValuationRemuPaymentTo.setPanNo(object[27].toString());
					}else if(object[31]!=null){
						examValuationRemuPaymentTo.setPanNo(object[31].toString());
					}
					if(object[24]!=null){
						examValuationRemuPaymentTo.setMobileNo(object[24].toString());
					}else if(object[25]!=null){
						examValuationRemuPaymentTo.setMobileNo(object[25].toString());
					}else if(object[26]!=null){
						examValuationRemuPaymentTo.setMobileNo(object[26].toString());
					}
					if(object[30]!=null)
						examValuationRemuPaymentTo.setAddress(object[30].toString());
					//added by giri
					if(examValuationRemuPaymentForm.getPaidStauts().equalsIgnoreCase("paid")){
						if(object[32]!=null){
							if(object[32].toString().equalsIgnoreCase("E")){
								examValuationRemuPaymentTo.setModeOfPayment("Electronic transfer");
							}else{
								examValuationRemuPaymentTo.setModeOfPayment("Cheque");
							}
						}
					}
					//end by giri
					examValuationRemuPaymentTo.setModeOfPaymentMap(modeOfPaymentMap);
					if(!tosMap.containsKey(Integer.parseInt(object[0].toString()))){
						tosMap.put(Integer.parseInt(object[0].toString()), examValuationRemuPaymentTo);
					}
					list.add(examValuationRemuPaymentTo);
				}
				
			}
			examValuationRemuPaymentForm.setTosMap(tosMap);
		}
		return list;
	}
	
	public List<ExamValuationRemuneration> convertToToBos(
			List<ExamValuationRemuPaymentTo> list, String userId, Map<Integer, ExamValuationRemuneration> map) throws Exception{
		List<ExamValuationRemuneration> examValuationRemunerations=new ArrayList<ExamValuationRemuneration>();
		Iterator<ExamValuationRemuPaymentTo> iterator=list.iterator();
		ExamValuationRemuneration examValuationRemuneration=null;
		while (iterator.hasNext()) {
			ExamValuationRemuPaymentTo examValuationRemuPaymentTo = (ExamValuationRemuPaymentTo) iterator.next();
			examValuationRemuneration=map.get(examValuationRemuPaymentTo.getId());
			examValuationRemuneration.setId(examValuationRemuPaymentTo.getId());
			examValuationRemuneration.setIsPaid(true);
			if(examValuationRemuPaymentTo.getModeOfPayment().equalsIgnoreCase("Electronic transfer")){
				examValuationRemuneration.setModeOfPayment("E");
			}else if(examValuationRemuPaymentTo.getModeOfPayment().equalsIgnoreCase("Cheque")){
				examValuationRemuneration.setModeOfPayment("C");
			}
			examValuationRemuneration.setModifiedBy(userId);
			examValuationRemuneration.setLastModifiedDate(new Date());
			examValuationRemunerations.add(examValuationRemuneration);
		}
		return examValuationRemunerations;
	}

	public Map<Integer, ExamValuationRemuneration> convertListToMap(
			List<ExamValuationRemuneration> examList) throws Exception{
		Map<Integer, ExamValuationRemuneration> map=new HashMap<Integer, ExamValuationRemuneration>();
		Iterator<ExamValuationRemuneration> iterator=examList.iterator();
		while (iterator.hasNext()) {
			ExamValuationRemuneration examValuationRemuneration = (ExamValuationRemuneration) iterator.next();
			map.put(examValuationRemuneration.getId(), examValuationRemuneration);
		}
		return map;
	}
}
