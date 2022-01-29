<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<script language="JavaScript" >
	var print = "<c:out value='${studentCertificateCourseForm.printCourse}'/>";
	if(print.length != 0 && print == "true") {
		var url ="StudentCertificateCourse.do?method=showStudentDetails";
		myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
		
	}
</script>