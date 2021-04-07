package com.kp.cms.transactions.usermanagement;

public interface ICreateUserTransaction {

	boolean isEmployeeDuplicated(String employeeId, int id) throws Exception;
    boolean isGuestDuplicated(String guestId, int id) throws Exception;

}
