<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<head>
<script type="text/javascript">
function resetFields(){	
	document.getElementById("hostel").selectedIndex = 0;
	document.getElementById("fromDate").value="";
	document.getElementById("toDate").value="";
	document.getElementById("academicYear").selectedIndex = 0;
	resetErrMsgs();
}

</script>
</head>
<html:form action="/HostelStudentEvaluation">
<html:hidden property="method" styleId="method" value="getStudentEvaluationDetails"/>
<html:hidden property="formName" value="hostelStudentEvaluationForm"/>
<html:hidden property="pageType" value="1"/>


<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.studentEvaluation"/>&gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.hostel.studentEvaluation"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="72" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            
		 <tr>
			<td colspan="6" align="left">
			<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
			<FONT color="green"><html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
			</html:messages></FONT></div>
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
                  <td width="23%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">* </span>
                  <bean:message key="knowledgepro.hostel.entry.hostel.name" /></div></td>
				  <td width="23%" height="25"class="row-even" >
					<html:select property="hostelId" styleClass="TextBox" styleId="hostel" name="hostelStudentEvaluationForm">
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						<logic:notEmpty name="hostelStudentEvaluationForm" property="hostelTOList">
						<html:optionsCollection property="hostelTOList" name="hostelStudentEvaluationForm" label="name" value="id" />
						</logic:notEmpty>
					</html:select>
					</td>
                   
				<td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
				 <td class="row-even">
				  <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="hostelStudentEvaluationForm" property="academicYear"/>"/>
				  <html:select property="academicYear" styleId="academicYear"  styleClass="combo">
				     <html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
				    <cms:renderYear></cms:renderYear>
				</html:select>
				</td>	
                </tr >

				   <tr>
                    <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.studentEvaluation.fromDate" /></div></td>
                          <td  class="row-even">
                          <html:text name="hostelStudentEvaluationForm" property="fromDate" styleId="fromDate" size="10" maxlength="16"/>
							  <script
								language="JavaScript">
								new tcal( {
									// form name
									'formname' :'hostelStudentEvaluationForm',
									// input name
									'controlname' :'fromDate'
								});
							  </script>
                          </td>

						
                     <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.studentEvaluation.toDate" /></div></td>
                        <td colspan="2" class="row-even">
                          <html:text name="hostelStudentEvaluationForm" property="toDate" styleId="toDate" size="10" maxlength="16"/>
							  <script
								language="JavaScript">
								new tcal( {
									// form name
									'formname' :'hostelStudentEvaluationForm',
									// input name
									'controlname' :'toDate'
								});
							  </script>
                         </td>
                       
                     </tr>                   
               
              </table></td>
              <td width="5" height="30"  background="images/right.gif"></td>
           
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif" /></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="38" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="49%" height="35"><div align="right">
               <input type="submit" class="formbutton" value="Submit">
              </div></td>
              <td width="3%"></td>
              <td width="48%"><input type="button" class="formbutton" value="Reset" onclick="resetFields()" /></td>
            </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" ></td>
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





