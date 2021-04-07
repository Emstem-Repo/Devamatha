<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script>
	function cancelAction() {
		closeConfirm = confirm("Are you sure you want to close the Faculty Evaluation?");
		if (closeConfirm == true) {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	}
	function cancelAction1() {
		closeConfirm = confirm("Are you sure you want to close the Faculty Evaluation?\n Already Submitted feedback will not be saved");
		if (closeConfirm == true) {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	}
	</script>
<html:form action="/EvaluationStudentFeedback" >
<html:hidden property="method" styleId="method" value="getAnsweringCheck" />
<html:hidden property="formName" value="evaluationStudentFeedbackForm"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Faculty Evaluation</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
						 <logic:notEmpty name="evaluationStudentFeedbackForm" property="teacherClassSubjectToList">
            <td valign="top" class="news">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/st_01.gif" width="5" height="5" /></td>
                <td width="914" background="images/st_02.gif"></td>
                <td><img src="images/st_03.gif" width="5" height="5" /></td>
              </tr>
              
              <tr>
                <td width="5"  background="images/st_left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                
                  <tr >
                    <td height="20" class="studentrows-odd" style="font-size: 14px;"><div align="left"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="20" class="studentrows-odd" align="left" style="font-size: 14px;text-align:left;"><div align="left">Faculty Name</div></td>
                    <td height="20" class="studentrows-odd" align="left" style="font-size: 14px;text-align:left;"><div align="left">Subject</div></td>
                   <!--<td height="20" class="studentrows-odd" align="center" style="font-size: 13px;">Status</td>
                  --></tr>
             
               
                <logic:iterate id="eva" name="evaluationStudentFeedbackForm" property="teacherClassSubjectToList" type="com.kp.cms.to.admin.TeacherClassSubjectTO" indexId="count">
                <% if(eva.isDone()){ %>
                   	<tr>
                   		<td width="6%" height="40" class="row-white" ><div align="left"><c:out value="${count + 1}"/></div></td>
                   		<td width="40%" height="40" class="row-white" align="left"><bean:write name="eva" property="employeeName"/></td>
                   		<td width="40%" height="40" class="row-white" align="left"><bean:write name="eva" property="subject"/></td>
                   		<!--<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/done.png"  height="20"/></div></td>
					--></tr>
                    <%}else { %>
                    <tr>
                    	<td width="6%" height="40" class="row-white" ><div align="left"><c:out value="${count + 1}"/></div></td>
                   		<td width="40%" height="40" class="row-white" align="left"><bean:write name="eva" property="employeeName"/></td>
                   		<td width="40%" height="40" class="row-white" align="left"><bean:write name="eva" property="subject"/></td>
                   		<!--<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/undone.png"  height="20"/></div></td>
               		--></tr>
                    <%} %>
                </logic:iterate>
               
                <tr class="row-white" >
                 				
			                   <td colspan="3" height="50"><div align="center">
								<html:submit value="Start" styleClass="classname"></html:submit>&nbsp; <html:button property="" value="Close" styleClass="classname" onclick="cancelAction()"></html:button>
								</div></td>
								
			                 </tr>
                </table></td>
                <td width="5" height="30"  background="images/st_right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
                <td background="images/st_05.gif"></td>
                <td><img src="images/st_06.gif" /></td>
              </tr>
            </table></td>
        	</logic:notEmpty>
        	<logic:empty name="evaluationStudentFeedbackForm" property="teacherClassSubjectToList">
       		
       		<tr height="25"><td ></td>
       			<td align="center" class="navmenu">No Records Found</td>
       			<td  ></td>
       			
       		</tr>
       		
          <tr>
				<td height="26"></td>
				 <td  height="60"><div align="center">
								 <html:button property="" styleClass="classname" value="Close"  onclick="cancelAction()"></html:button>
								</div></td>
								<td  ></td>
			</tr>
				
       </logic:empty>
          </tr>
          
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>

</html:form>
