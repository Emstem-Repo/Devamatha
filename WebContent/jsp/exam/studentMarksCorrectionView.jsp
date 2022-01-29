<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<head>
<title>:: CMS ::</title>
<SCRIPT>
function fun()
{
	window.close();
}

</SCRIPT>

</head>
<html:form action="/studentMarksCorrection">
<html:hidden property="formName" value="newStudentMarksCorrectionForm"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Marks Entry&gt;&gt;</span></span></td>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
          <td background="images/Tcenter.gif" class="heading_white">Student
					Marks Entry - Single Student All Subjects</td>
          <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
        </tr>
      
        <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         <br></br>
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          
         <tr>

							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" height="86" border="0" cellpadding="0"
								cellspacing="1">
										<tr class="heading_tablehead">
											<td height="20"  class="bodytext">Subject</td>
											<td class="bodytext">Theory</td>
											<td  class="bodytext">Practical</td>
											<td class="bodytext">Comments</td>
											<td class="bodytext">Corrected Date</td>
										</tr>

										<logic:iterate indexId="count" property="oldMarksList" id="list" name="newStudentMarksCorrectionForm">
											
											<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-white">
										</c:when>
										<c:otherwise>
											<tr class="row-even">
										</c:otherwise>
									</c:choose>
											
											
												<td height="25" class="row-even"><bean:write
													property="subjectName" name="list" /></td>
												<td class="row-even">
											
													<bean:write property="stuTheoryRegMark" name="list"/>
												
												</td>
												<td class="row-even">
														<bean:write property="stuPracRegMark" name="list"/>
												</td>
												<td class="row-even">
												<bean:write
													property="comment" name="list"/></td>
													<td class="row-even">
												<bean:write
													property="correctedDate" name="list"/></td>
											</tr>
										</logic:iterate>
										
									</table>
									</td>
								  <td width="5" background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
        
        <tr>
          <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
          <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
              <tr>
                <td width="49%" height="35" align="right">&nbsp;</td>
                <td width="2%" height="35" align="center">&nbsp;</td>
                <td width="49%" height="35" align="left"><input name="Submit8" type="button" class="formbutton" value="Cancel" onclick="fun()"/></td>
              </tr>              
              <tr><td style="height: 10px" align="center" colspan="3"></td></tr>              
          </table></td>
          <td valign="top" background="images/Tright_3_3.gif" ></td>
        </tr>
        <tr>
          <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
          <td width="0" background="images/TcenterD.gif"></td>
          <td><img src="images/Tright_02.gif" width="9" height="29"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

</html:form>
