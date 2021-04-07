package com.kp.cms.handlers.hostel;

import java.util.List;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.hostel.AvailableSeatsBo;
import com.kp.cms.bo.hostel.BiometricBo;
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.forms.hostel.BiometricForm;
import com.kp.cms.helpers.employee.BiometricDetailsHelper;
import com.kp.cms.helpers.hostel.AvailableSeatsHelper;
import com.kp.cms.helpers.hostel.BiometricHelper;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.transactions.employee.IBiometricDetailsTxn;
import com.kp.cms.transactions.hostel.IAvailableSeatsTransaction;
import com.kp.cms.transactions.hostel.IBiometricTransaction;
import com.kp.cms.transactionsimpl.employee.BiometricDetailsTxnImpl;
import com.kp.cms.transactionsimpl.hostel.AvalilableTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.BiometricTransactionImpl;

public class BiometricHandler {
	IBiometricTransaction iBiometricTransaction = BiometricTransactionImpl.getInstance();
	/**
	 * instance()
	 */
	public static volatile BiometricHandler biometricHandler = null;

	public static BiometricHandler getInstance() {
		if (biometricHandler == null) {
			biometricHandler = new BiometricHandler();
		}
		return biometricHandler;
	}
	/**
	 * addBiometricDetails
	 * @param availableSeatsForm
	 * @return
	 * @throws Exception
	 */
	public boolean addBiometricDetails(BiometricForm biometricForm) throws Exception{
		boolean isAdded = false;
			BiometricBo biometricBo = BiometricHelper.getInstance().copyFormToBiometricBo(biometricForm);
			isAdded = iBiometricTransaction.addBiometricDetails(biometricBo);
		return isAdded;
	}
	/**
	 * duplicate check
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck(BiometricForm biometricForm) throws Exception{
		boolean isCheckDuplicate = iBiometricTransaction.checkDuplicate(biometricForm);
		return isCheckDuplicate;
	}
	/**
	 * get required list
	 * @return
	 * @throws Exception
	 */
	public List<BiometricDetailsTO> getBiometricList()throws Exception{
		List<BiometricBo> biometricBos=iBiometricTransaction.getBiometricDetails();
		List<BiometricDetailsTO> biometricTO=BiometricHelper.getInstance().convertBosToTOs(biometricBos);
		return biometricTO;
	}
	/**
	 * edit hostel biometric details
	 * @param biometricDetailsForm
	 * @throws Exception
	 */
	public void editBiometricDetails(BiometricForm biometricForm)throws Exception{
		BiometricBo biometricBo=iBiometricTransaction.getBiometricDetailsById(biometricForm.getId());
		BiometricHelper.getInstance().setBotoForm(biometricForm, biometricBo);
	}
	/**
	 * update biometric details
	 * @param biometricDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateBiometricDetails(BiometricForm biometricForm)throws Exception{
		BiometricBo biometricBo=BiometricHelper.getInstance().convertEditFormToBo(biometricForm);
		boolean isUpdated=iBiometricTransaction.updateBiometricDetails(biometricBo);
		return isUpdated;
	}
public boolean deleteBiometricDetails(BiometricForm biometricForm)throws Exception{
				boolean isDeleted=iBiometricTransaction.deleteBiometricDetails(biometricForm.getId());
				return isDeleted;
			}
}
