package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admission.TcDetailsOldStudents;
import com.kp.cms.forms.admission.TcDetailsOldStudentsForm;
import com.kp.cms.helpers.admission.TcDetailsOldStudentsHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.transactions.admission.ITcDetailsOldStudents;
import com.kp.cms.transactionsimpl.admission.TcDetailsOldStudentsImpl;

public class TcDetailsOldStudentsHandler {
	public ITcDetailsOldStudents iTcDetailsOldStudents=TcDetailsOldStudentsImpl.getInstance();
	
	private static TcDetailsOldStudentsHandler tcDetailsOldStudentsHandler=new TcDetailsOldStudentsHandler();
	private TcDetailsOldStudentsHandler(){}
	public static TcDetailsOldStudentsHandler getInstance(){
		return tcDetailsOldStudentsHandler;
	}

	/**
	 * @param year
	 * @param tcDetailsOldStudetnsForm
	 * @throws Exception
	 */
	public void getOldStudentsList(String year, TcDetailsOldStudentsForm tcDetailsOldStudetnsForm)throws Exception { 
		List<TcDetailsOldStudents> oldStudetntsListByYear=null;
		String query=null;
		List<TCNumber> tcNumber=null;
		if(year!=null && !year.isEmpty())
		 tcNumber=iTcDetailsOldStudents.getTcNo(year);
		 if(tcNumber!=null){
			 int i=0;
			 StringBuilder tcno = new StringBuilder();
			 while(i<tcNumber.size()){
				TCNumber tc=tcNumber.get(i);
				if(tc!=null && tc.getTcFor()!=null && !tc.getTcFor().isEmpty())
				tcno.append("'"+tc.getTcFor()+"'");
				if(++i<tcNumber.size()){
					tcno.append(",");
				}
			 }
			 if(tcno!=null && !tcno.toString().isEmpty())
				query=iTcDetailsOldStudents.getTcDetails(year,tcno.toString());
				if(query!=null)
				oldStudetntsListByYear=iTcDetailsOldStudents.getOldStudentsList(query);
				if(oldStudetntsListByYear!=null)
				Collections.sort(oldStudetntsListByYear);
				TcDetailsOldStudentsHelper.getInstance().convertTcDetailsOldStudentsBoToTo(oldStudetntsListByYear,tcDetailsOldStudetnsForm);
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<CharacterAndConductTO> getCharacterAndConductList()throws Exception {
		// TODO Auto-generated method stub
		List<CharacterAndConductTO> characterAndConductToList=new ArrayList<CharacterAndConductTO>();
		String query=iTcDetailsOldStudents.getCharacterAndConductQuery();
		if(query!=null && !query.isEmpty())
		characterAndConductToList=TcDetailsOldStudentsHelper.getInstance().convertCharacterAndConductBotoTo(iTcDetailsOldStudents.getCharacterAndConductList(query));
		return characterAndConductToList;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<CasteTO> getCategoryList()throws Exception {
		// TODO Auto-generated method stub
		List<CasteTO> categoryToList=new ArrayList<CasteTO>();
		String query=iTcDetailsOldStudents.getCategoryQuery();
		if(query!=null && !query.isEmpty())
		categoryToList=TcDetailsOldStudentsHelper.getInstance().convertCategoryBotoTo(iTcDetailsOldStudents.getCategoryList(query));
		return categoryToList;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<ReligionTO> getReligionList()throws Exception {
		// TODO Auto-generated method stub
		List<ReligionTO> religionToList=new ArrayList<ReligionTO>();
		String query=iTcDetailsOldStudents.getReligionQuery();
		if(query!=null && !query.isEmpty())
		religionToList=TcDetailsOldStudentsHelper.getInstance().convertReligionBoToTo(iTcDetailsOldStudents.getReligionList(query));
		return religionToList;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<NationalityTO> getNationalityList()throws Exception {
		// TODO Auto-generated method stub
		List<NationalityTO> nationalityToList=new ArrayList<NationalityTO>();
		String query=iTcDetailsOldStudents.getNationalityQuery();
		if(query!=null && !query.isEmpty())
		nationalityToList=TcDetailsOldStudentsHelper.getInstance().convertNationalityBotoTo(iTcDetailsOldStudents.getNationalityList(query));
		return nationalityToList;
	}

	/**
	 * @param tcDetailsOldStudentsForm
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public int addOldStudent(TcDetailsOldStudentsForm tcDetailsOldStudentsForm) throws NumberFormatException, Exception {
		// TODO Auto-generated method stub
		int flag=0;
		
		List<TcDetailsOldStudents> registerNoList=iTcDetailsOldStudents.getOldStudentsList();
		List<String> regnoList=new ArrayList<String>();
		Map<String,TcDetailsOldStudents> stuMap=new HashMap<String, TcDetailsOldStudents>();
		if(registerNoList!=null){
			Iterator<TcDetailsOldStudents> registerNoListIterator=registerNoList.iterator();
			while(registerNoListIterator.hasNext()){
				TcDetailsOldStudents tcDetailsOldStudents=registerNoListIterator.next();
				regnoList.add(tcDetailsOldStudents.getRegisterNo());
				stuMap.put(String.valueOf(tcDetailsOldStudents.getId()),tcDetailsOldStudents);
			}
		}
		TCNumber tcNumber=null;
		
		 tcNumber=iTcDetailsOldStudents.getTcNoByYear(tcDetailsOldStudentsForm.getYear(),tcDetailsOldStudentsForm.getTcFor());
		
		if(tcNumber!=null){
		TcDetailsOldStudents tcDetailsOldStudents=TcDetailsOldStudentsHelper.getInstance().addOldStudentToBo(tcDetailsOldStudentsForm,stuMap,tcNumber,regnoList);
		if(tcDetailsOldStudents!=null){
			if(tcDetailsOldStudentsForm.getId()==null || (!tcDetailsOldStudentsForm.getTcFor().equalsIgnoreCase(tcDetailsOldStudentsForm.getTempTcFor()))
					|| !tcDetailsOldStudentsForm.getTempYear().equalsIgnoreCase(tcDetailsOldStudentsForm.getYear())){
				iTcDetailsOldStudents.updateTcSLNO(tcNumber);
				flag=iTcDetailsOldStudents.saveOldStudentTcDetails(tcDetailsOldStudents);
			}else if(tcDetailsOldStudentsForm.getId()!=null && tcDetailsOldStudentsForm.getRegNo().equalsIgnoreCase(tcDetailsOldStudentsForm.getRegisterNo())){
				if(tcDetailsOldStudentsForm.getButton()!=null && !tcDetailsOldStudentsForm.getButton().isEmpty() && tcDetailsOldStudentsForm.getButton().equalsIgnoreCase("oldprint"))
					flag=1;
				else
					flag=iTcDetailsOldStudents.saveOldStudentTcDetails(tcDetailsOldStudents);
			}else
				flag=3;
		}else{
			flag=3;
		}
		}else
			flag=2;
		return flag;
	}

	/**
	 * @param tcDetailsOldStudentsForm
	 * @throws Exception
	 */
	public void editTcDetails(TcDetailsOldStudentsForm tcDetailsOldStudentsForm)throws Exception {
		// TODO Auto-generated method stub
		TcDetailsOldStudents tcDetailsOldStudents=iTcDetailsOldStudents.editTcDetailsOldStudents(tcDetailsOldStudentsForm.getId());
		TcDetailsOldStudentsHelper.getInstance().convertOldStudentBoToForm(tcDetailsOldStudents,tcDetailsOldStudentsForm);
	}
	/**
	 * @param tcDetailsOldStudentsForm
	 * @return
	 * @throws Exception
	 */
	public  boolean deleteTc(TcDetailsOldStudentsForm tcDetailsOldStudentsForm)throws Exception {
		// TODO Auto-generated method stub
		boolean flag=false;
		flag=iTcDetailsOldStudents.deleteTc(tcDetailsOldStudentsForm.getId());
		return flag;
	}
	/**
	 * @param registerNo
	 * @return
	 * @throws Exception
	 */
	public TcDetailsOldStudents checkForStudent(String registerNo)throws Exception {
		return iTcDetailsOldStudents.getTcOldStudentByRegNo(registerNo);
	}
	/**
	 * @param tcDetailsOldStudentsForm
	 * @param tcDetailsOldStudents
	 * @throws Exception
	 */
	public int getTcOldPrint(TcDetailsOldStudentsForm tcDetailsOldStudentsForm,TcDetailsOldStudents tcDetailsOldStudents)throws Exception {
		TcDetailsOldStudentsHelper.getInstance().convertOldStudentBoToForm(tcDetailsOldStudents, tcDetailsOldStudentsForm);
		return 1;
	}

}
