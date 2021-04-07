<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>


<body>
<html:form action="PRCstudentEdit" method="post">
<html:hidden property="method" styleId="method" value="updateStudentHistory"/>
<html:hidden property="formName" value="studentEditForm" />
<html:hidden property="pageType" value="0"/>

<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt;<bean:message key="admissionForm.studentedit.mainedit.label"/> &gt;&gt;</span></span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="admissionForm.studentedit.mainedit.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr bgcolor="#FFFFFF">
				<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>	
	            <td colspan="6" class="body" align="left">
	             <div id="errorMessage">
	            <FONT color="red"><html:errors/></FONT>
	             <FONT color="green">
						<html:messages id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out><br>
						</html:messages>
	            </FONT>
	            </div>
	            </td>
	          </tr>

            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="22%" height="25" class="row-odd" ><div align="right">
                  <bean:message key="admissionForm.edit.subgrp.label" /> </div></td>
                  <td width="24%" height="25" class="row-even" >					
					<nested:select property="subjGroupHistId"
												styleId="subjGroupHistId" styleClass="row-even"
												multiple="multiple" size="4" style="width:200px;height:100px">
												<logic:notEmpty name="studentEditForm" property="subGroupHistoryList">
												<nested:optionsCollection property="subGroupHistoryList"
													label="name" value="id" />
													</logic:notEmpty>
											</nested:select>
					
					
				  </td>
                  <td width="7%" class="row-even" ><div align="center" class="heading"></div></td>
                  <td width="21%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendance.class.col" /></div></td>
                  <td width="26%" height="25" colspan="2" class="row-even" >
                  <c:if test="${studentEditForm.classHistId!=null && studentEditForm.classHistId!=''}">
                 <nested:select property="classHistId"
												styleId="classHistId" styleClass="combo" disabled="true">
												<html:option value="0">-Select-</html:option>
												<logic:notEmpty name="studentEditForm" property="classesMap">
												<nested:optionsCollection property="classesHistMap"
													label="value" value="key" />
													</logic:notEmpty>
											</nested:select>
                  </c:if>
                   <c:if test="${studentEditForm.classHistId==null || studentEditForm.classHistId==''}">
                 <nested:select property="classHistId"
												styleId="classHistId" styleClass="combo">
												<html:option value="0">-Select-</html:option>
												<logic:notEmpty name="studentEditForm" property="classesMap">
												<nested:optionsCollection property="classesHistMap"
													label="value" value="key" />
													</logic:notEmpty>
											</nested:select>
                  </c:if>
					
					
				  </td>
                </tr>
                
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
		
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="center"><html:submit styleClass="formbutton">
								Update
							</html:submit></div>
							</td>
						</tr>
					</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="931" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>