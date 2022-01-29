package com.kp.cms.transactionsimpl.exam;


/**
 * Dec 19, 2009 Created By 9Elements Team
 * No longer used
 */
public class CourseUtilImpl {
//	private static final Log log = LogFactory.getLog(CourseUtilImpl.class);
//	private static CourseUtilImpl courseUtilImpl = null;
//
//	public static CourseUtilImpl getInstance() {
//		if (courseUtilImpl == null) {
//			courseUtilImpl = new CourseUtilImpl();
//		}
//		return courseUtilImpl;
//	}

	/*
	 * select only the active ExamCourseUtilBO for a given programTypeId
	 */
//	public ArrayList<ExamCourseUtilBO> select_ActiveOnly(int programTypeId) {
//		Session session = null;
//		ArrayList<ExamCourseUtilBO> list = null;
//		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
//
//			Query query = (Query) session
//					.createQuery("from ExamCourseUtilBO e where ExamProgramTypeUtilBO.id = :programTypeId");
//
//			Iterator it = ((org.hibernate.Query) query).iterate();
//
//			while (it.hasNext()) {
//				ExamCourseUtilBO e = new ExamCourseUtilBO();
//			}
//
//			session.flush();
//			session.close();
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			if (session != null) {
//				session.flush();
//				session.close();
//			}
//			list = new ArrayList<ExamCourseUtilBO>();
//		}
//		return list;
//	}
//
//	/*
//	 * select all the active ExamCourseUtilBO
//	 */
//	public ArrayList<ExamCourseUtilBO> select_ActiveOnly() {
//		Session session = null;
//		ArrayList<ExamCourseUtilBO> list;
//		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//			session = sessionFactory.openSession();
//
//			Criteria crit = session.createCriteria(ExamCourseUtilBO.class);
//			// crit.add(Restrictions.eq("isActive", false));
//			// crit.add(Restrictions.eq("courseID", 1));
//
//			list = (ArrayList<ExamCourseUtilBO>) crit.list();
//			session.flush();
//			session.close();
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			if (session != null) {
//				session.flush();
//				session.close();
//			}
//			list = new ArrayList<ExamCourseUtilBO>();
//		}
//		return list;
//	}

}
