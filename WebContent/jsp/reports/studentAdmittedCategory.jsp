<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="inventory.stockReport.main.label"/>  </title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
<script type="text/javascript">
function submitMe(method){
	document.studentAdmittedCategoryForm.method.value=method;
	document.studentAdmittedCategoryForm.submit();
}
</script>

<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>
<body>
<html:form action="/studentCategoryReport">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="studentAdmittedCategoryForm"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission"/> </span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.report.studentcategory.title"/>  </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
	<tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td align="left"><div id="errorMessage"><html:errors/></div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
     
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				
                <tr >
                  <td align="center" class="heading" colspan="4"> STUDENTS ADMITTED IN  * <bean:write property="programName" name="studentAdmittedCategoryForm"/> * OF CATEGORY  * <bean:write property="casteName" name="studentAdmittedCategoryForm"/> *  AS ON <bean:write property="reportdate" name="studentAdmittedCategoryForm"/>  </td>
                 
                  </tr>
				<tr>
			<td colspan="4" align="left">
		<display:table export="true" id="reqList" name="sessionScope.studentList" requestURI="" defaultorder="descending" pagesize="10" style="width:100%" >
		
		<display:setProperty name="export.excel.filename" value="StudentCategoryWiseReport.xls"/>
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv.filename" value="StudentCategoryWiseReport.csv"/>
			
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="slNo" sortable="true" title="Sl.No" class="row-even" headerClass="row-odd"></display:column>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="applnNo" sortable="true" title="Application No" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="studentName" sortable="false" title="Name" class="row-even" headerClass="row-odd"/>
			
			
			
		
		</display:table></td>
		</tr>

              </table>
                </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table>      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%" height="35" align="right">&nbsp;</td>
            <td width="3" height="35" align="center"><html:button property="" styleClass="formbutton" value="Cancel" onclick="submitMe('initStudentReport')"/></td>
            <td width="50%" height="35" align="left">&nbsp;</td>
          </tr>
        </table>      
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"> <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>