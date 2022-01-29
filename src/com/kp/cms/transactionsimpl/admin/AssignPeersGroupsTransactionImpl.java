package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AssignPeersGroups;
import com.kp.cms.bo.admin.PeersEvaluationGroups;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.AssignPeersGroupsForm;
import com.kp.cms.to.admin.PeersEvaluationGroupsTO;
import com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AssignPeersGroupsTransactionImpl implements IAssignPeersGroupsTransaction{
	public static volatile AssignPeersGroupsTransactionImpl impl = null;
	public static AssignPeersGroupsTransactionImpl getInstance(){
		if(impl == null){
			impl = new AssignPeersGroupsTransactionImpl();
			return impl;
		}
		return impl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#getPeersEvaluationGroups()
	 */
	@Override
	public List<PeersEvaluationGroups> getPeersEvaluationGroups() throws Exception {
		Session session = null;
		List<PeersEvaluationGroups> groupsTOs = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from PeersEvaluationGroups groups where groups.isActive = 1";
			Query query = session.createQuery(str);
			groupsTOs = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
		}
		}
		return groupsTOs;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#saveFaculty(java.util.List, java.lang.String)
	 */
	@Override
	public boolean saveFaculty(List<AssignPeersGroups> assignPeersGroups) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				if(assignPeersGroups!=null && !assignPeersGroups.isEmpty()){
					Iterator<AssignPeersGroups> iterator = assignPeersGroups.iterator();
					while (iterator.hasNext()) {
						AssignPeersGroups peersGroups = (AssignPeersGroups) iterator .next();
						session.save(peersGroups);
					}
				}
			tx.commit();
			session.flush();
			isAdded = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#checkFacultyDuplicates(com.kp.cms.forms.admin.AssignPeersGroupsForm)
	 */
	@Override
	public boolean checkFacultyDuplicatesForAdd(String facultyId,List<String> duplicateList,AssignPeersGroupsForm assignPeersGroupsForm) throws Exception {
		Session session = null;
		boolean isDuplicate = false;
		try{
			session = HibernateUtil.getSession();
			String str = "from AssignPeersGroups groups where groups.isActive = 1 and groups.users.id="+Integer.parseInt(facultyId)+
			/* code modified by sudhir*/
			             " and groups.peersEvaluationGroups.id="+Integer.parseInt(assignPeersGroupsForm.getGroupId());
			/*------------------------ */
			Query query = session.createQuery(str);
			AssignPeersGroups assignPeersGroups = (AssignPeersGroups) query.uniqueResult();
			if(assignPeersGroups!=null && !assignPeersGroups.toString().isEmpty()){
					isDuplicate = true;
					String duplicateName ="";
					if(assignPeersGroups.getUsers().getEmployee()!=null && assignPeersGroups.getUsers().getEmployee().getFirstName()!=null){
						duplicateName = assignPeersGroups.getUsers().getEmployee().getFirstName();
					}else if(assignPeersGroups.getUsers()!=null && !assignPeersGroups.getUsers().toString().isEmpty()){
						if(assignPeersGroups.getUsers().getUserName()!=null && !assignPeersGroups.getUsers().getUserName().isEmpty()){
							duplicateName = assignPeersGroups.getUsers().getUserName();
						}
					}
					duplicateList.add(duplicateName);
					assignPeersGroupsForm.setDupFacultyIds(duplicateList);
				}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDuplicate;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#getAssignPeersToGroups()
	 */
	@Override
	public List<AssignPeersGroups> getAssignPeersToGroups() throws Exception {
		Session session = null;
		List<AssignPeersGroups> assignPeersGroups = null;
		try{
			session = HibernateUtil.getSession();
			String str = "from AssignPeersGroups grps where grps.isActive = 1 group by grps.peersEvaluationGroups.id,grps.department.id";
			Query query = session.createQuery(str);
			assignPeersGroups = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
		}
		}
		return assignPeersGroups;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#editAssignPeersGrps(int, int)
	 */
	@Override
	public List<AssignPeersGroups> editAssignPeersGrps(AssignPeersGroupsForm assignPeersGroupsForm) throws Exception {
		Session session = null;
		List<AssignPeersGroups> assignPeersGroups = null;
		try{
			int deptId  = Integer.parseInt(assignPeersGroupsForm.getDepartmentId().trim());
			int groupId = Integer.parseInt(assignPeersGroupsForm.getGroupId().trim());
			session = HibernateUtil.getSession();
			String str = "from AssignPeersGroups grps where grps.isActive = 1 and grps.department.id="+deptId+" and grps.peersEvaluationGroups.id="+groupId;
			Query query = session.createQuery(str);
			assignPeersGroups = query.list();
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
		}
		}
		return assignPeersGroups;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#deleteAssignPeersToGroups(com.kp.cms.forms.admin.AssignPeersGroupsForm)
	 */
	@Override
	public boolean deleteAssignPeersToGroups( AssignPeersGroupsForm assignPeersGroupsForm) throws Exception {
		Session session = null;
		Transaction tx =null;
		boolean isDelete = false;
		List<AssignPeersGroups> assignPeersGroups =null;
		try{
			int deptId  = Integer.parseInt(assignPeersGroupsForm.getDepartmentId().trim());
			int groupId = Integer.parseInt(assignPeersGroupsForm.getGroupId().trim());
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			String str = "from AssignPeersGroups grps where grps.isActive = 1 and grps.department.id="+deptId+" and grps.peersEvaluationGroups.id="+groupId;
			Query query = session.createQuery(str);
			assignPeersGroups = query.list();
			if(assignPeersGroups!=null && !assignPeersGroups.toString().isEmpty()){
				Iterator<AssignPeersGroups> iterator = assignPeersGroups.iterator();
				while (iterator.hasNext()) {
					AssignPeersGroups bo = (AssignPeersGroups) iterator .next();
					bo.setIsActive(false);
					session.update(bo);
				}
				tx.commit();
				isDelete = true;
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDelete;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#checkFacultyDuplicatesForForUpdate(java.lang.String, java.util.List, com.kp.cms.forms.admin.AssignPeersGroupsForm)
	 */
	@Override
	public boolean checkFacultyDuplicatesForForUpdate(String facultyId, List<String> duplicateList, AssignPeersGroupsForm assignPeersGroupsForm) throws Exception {
		Session session = null;
		boolean isDuplicate = false;
		try{
			session = HibernateUtil.getSession();
			String str = "from AssignPeersGroups groups where groups.isActive = 1 and groups.users.id="+Integer.parseInt(facultyId)+
						/* code modified by sudhir*/
							" and groups.peersEvaluationGroups.id="+Integer.parseInt(assignPeersGroupsForm.getGroupId());
						/*------------------------ */
			Query query = session.createQuery(str);
			AssignPeersGroups assignPeersGroups = (AssignPeersGroups) query.uniqueResult();
			if(assignPeersGroups!=null && !assignPeersGroups.toString().isEmpty()){
				if(assignPeersGroupsForm.getAssignPeersIds().containsValue((assignPeersGroups.getId()))){
					isDuplicate = false;
				}else{
					isDuplicate = true;
					String duplicateName ="";
					if(assignPeersGroups.getUsers().getEmployee()!=null && assignPeersGroups.getUsers().getEmployee().getFirstName()!=null){
						duplicateName = assignPeersGroups.getUsers().getEmployee().getFirstName();
					}else if(assignPeersGroups.getUsers()!=null && !assignPeersGroups.getUsers().toString().isEmpty()){
						if(assignPeersGroups.getUsers().getUserName()!=null && !assignPeersGroups.getUsers().getUserName().isEmpty()){
							duplicateName = assignPeersGroups.getUsers().getUserName();
						}
					}
					duplicateList.add(duplicateName);
					assignPeersGroupsForm.setDupFacultyIds(duplicateList);
				}
			}
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isDuplicate;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IAssignPeersGroupsTransaction#updateFaculty(java.util.List)
	 */
	@Override
	public boolean updateFaculty(List<AssignPeersGroups> assignPeersGroups) throws Exception {
		Session session = null;
		boolean isUpdate = false;
		Transaction tx =null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				if(assignPeersGroups!=null && !assignPeersGroups.isEmpty()){
					Iterator<AssignPeersGroups> iterator = assignPeersGroups.iterator();
					while (iterator.hasNext()) {
						AssignPeersGroups peersGroups = (AssignPeersGroups) iterator .next();
						if(peersGroups.getId()!=0){
							session.update(peersGroups);
						}else{
							session.save(peersGroups);
						}
						
					}
				}
			tx.commit();
			session.flush();
			isUpdate = true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
		return isUpdate;
	}
}
