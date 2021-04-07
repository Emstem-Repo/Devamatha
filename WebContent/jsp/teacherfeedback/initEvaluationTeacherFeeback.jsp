<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>   
<script type="text/javascript">
	function getClassByTeacherAndYear(year){
		window.onbeforeunload=null;	
		document.location.href = "EvaluationTeacherFeedback.do?method=getClassesDataToForm&year="+year;
	}
	function getSubjectsByClass(classSchemewiseId){	
		window.onbeforeunload=null;	
		document.location.href = "EvaluationTeacherFeedback.do?method=getSubjectsByClass&classSchemewiseId="+classSchemewiseId;
	}
</script>
<html:form action="/EvaluationTeacherFeedback" method="post">

	<html:hidden property="formName" value="evaluationTeacherFeedbackForm" />
	<html:hidden property="method" styleId="method" value="getDetailsForFeedback"/>
	<html:hidden property="pageType" value="1"/>
	<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendanceentry.attendance"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendanceentry.entry"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.attendanceentry.entry"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td >
	       <div align="right" style="color:red" class="heading"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
	       <font color="red" size="4"><div id="errorMessage" style="font-family: verdana;font-size: 10px">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
	  	   </div></font>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" >
        
        
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="1">
            
                  
                <tr style="height: 20px">
                
                  <td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		          <td class="row-even" align="left" width="25%">
		                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="evaluationTeacherFeedbackForm" property="year"/>"/>
		                           <html:select property="year" styleId="academicYear" name="evaluationTeacherFeedbackForm" styleClass="combo" onchange="getClassByTeacherAndYear(this.value)">
                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       	   				<cms:renderAcademicYear></cms:renderAcademicYear>
                       			   </html:select>
		        	</td>	
		        	<td class="row-odd" width="25%">
			                 	 	 <div id="classsdiv" align="right"><bean:message key="knowledgepro.attendanceentry.class"/>:</div>
			                  </td>
			                  <td  class="row-even" width="25%">
			                  <html:select  name="evaluationTeacherFeedbackForm" styleId="classSchemewiseId" property="classSchemewiseId" onchange="getSubjectsByClass(this.value)">
                       	   		    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
			       		    		<logic:notEmpty name="evaluationTeacherFeedbackForm" property="classMapForFeedback">
			       		    		<html:optionsCollection name="evaluationTeacherFeedbackForm" property="classMapForFeedback" label="value" value="key"/>
			                  		</logic:notEmpty>
			                  </html:select>
			                  </td>	        	
                </tr>
               
                <tr style="height: 20px">
                  <td class="row-odd" width="25%"><div id="classsdiv" align="right">
                  <bean:message key="knowledgepro.attendanceentry.subject"/>:</div>
                  </td>
                  <td  class="row-even" width="25%">
                    <html:select property="subjectId" styleId="subjectId" styleClass="comboLarge">
                      <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                      <logic:notEmpty name="evaluationTeacherFeedbackForm" property="subjectMap">
                      <html:optionsCollection name="evaluationTeacherFeedbackForm" property="subjectMap" label="value" value="key"/>
                      </logic:notEmpty>
                    </html:select>
                  </td>
                  <td  class="row-odd" width="25%"></td>
                  <td  class="row-even" width="25%"></td>
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
        
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <tr >
            <td style="height: 10px;" align="center">
            
           		
            </td>
          </tr>
          <tr>
            <td height="35" align="center">
           		<html:submit value="Continue" styleClass="formbutton" property="" onclick="submitData()"></html:submit>
            </td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
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
</table>

</html:form>

<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("academicYear").value = year;
	}
	
	document.getElementById("academicYear").value=document.getElementById("year").value;	
	
</script>