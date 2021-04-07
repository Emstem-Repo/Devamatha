<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type="text/javascript">
$(function()
		{

		$("div span:first-child").click(function(event) {
		event.preventDefault();
		$(this).parent().find("div").andSelf().slideDown();
		});

		$("div a.close").click(function(event) {
		event.preventDefault();
		$(this).parent().slideUp();
		});
		});


function cancelAction(){
	document.location.href="timeTableForClass.do?method=goToMainPage";
}
</script>
<script type="text/javascript">  
        $(document).ready(function(){
            $("#report .tdIMG").addClass("odd");
            $("#report .data").hide();
            $("#report tr:first-child").show();
            
            $("#report tr.odd").click(function(){
                $(this).next("tr").toggle();
                $(this).find(".arrow").toggleClass("up");
            });
            //$("#report").jExpand();
        });
    </script>
<style type="text/css">
        body { font-family:Arial, Helvetica, Sans-Serif; font-size:0.8em;}
    </style>
<html:form action="/timeTableForClass" method="post">
	<html:hidden property="formName" value="timeTableForClassForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.timetable.module" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.timetable.for.class"/> &gt;&gt;</span></span></td>
	  </tr>
	  <tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.timetable.for.class" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
						<td colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="report" class="ui-state-error ui-corner-all">
							<tr>
					            <th>Modified Dates</th>
					            <th></th>
					        </tr>
							
								<logic:notEmpty name="timeTableForClassForm" property="historyMap">
	            			<logic:iterate id="dateMap" name="timeTableForClassForm" property="historyMap">
	            			<tr class="tdIMG" height="25px">
					            <td ><font size="1px" color="black"><B><bean:write name="dateMap" property="key"/></B></font> 
					            </td>
					            <td align="right"> <div class="arrow"></div></td>
					        </tr>
							<tr class="data">
							<td colspan="2">
								<table width="100%" cellspacing="1" cellpadding="2" id="notReport">
						<tr class="row-odd">
			            	<td>Day </td>
			            	<logic:notEmpty name="timeTableForClassForm" property="periodList">
			            		<logic:iterate id="bo" name="timeTableForClassForm" property="periodList">
			            		<td><bean:write name="bo" property="periodName"/> </td>
			            		</logic:iterate>
			            	</logic:notEmpty>
			            </tr>
			            <logic:iterate id="to" name="dateMap" property="value">
			            <tr class="row-even" height="50px">
							<td><bean:write name="to" property="week"/> </td>
							<logic:notEmpty name="to" property="timeTablePeriodTos">
			            		<logic:iterate id="pto" name="to" property="timeTablePeriodTos">
			            		<td>
			            		<table height="100%">
			            		<logic:notEmpty name="pto" property="subjectList">
			            		<logic:iterate id="sto" name="pto" property="subjectList">
			            			<tr>
			            			<td>
				            		<logic:notEmpty name="sto" property="roomNo">
				            		<bean:write name="sto" property="roomNo"/>-
			            			</logic:notEmpty>
				            		<logic:notEmpty name="sto" property="subjectName">
				            		<bean:write name="sto" property="subjectName"/>-
			            			</logic:notEmpty>
				            		<logic:notEmpty name="sto" property="attTypeName">
				            		<bean:write name="sto" property="attTypeName"/>-
			            			</logic:notEmpty>
				            		<logic:notEmpty name="sto" property="activityName">
				            		<bean:write name="sto" property="activityName"/>-
			            			</logic:notEmpty>
				            		<logic:notEmpty name="sto" property="batchName">
				            		<bean:write name="sto" property="batchName"/>-
			            			</logic:notEmpty>
				            		<logic:notEmpty name="sto" property="userName">
				            		<bean:write name="sto" property="userName"/>
			            			</logic:notEmpty>
			            			</td>
			            			</tr>
			            		</logic:iterate>
			            		</logic:notEmpty>
			            		</table>
			            		</td>
			            		</logic:iterate>
			            	</logic:notEmpty>						            
			            </tr>
			            </logic:iterate>	            		
	            		</table>
							</td>
							</tr>
							</logic:iterate>
						</logic:notEmpty>
							</table>
						</td>
						</tr>
						
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="3" height="35" align="center">
							<div align="center"><html:button property="" styleClass="formbutton" value="Back" onclick="cancelAction()"></html:button></div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
	$(".display").bind("mouseenter mouseleave", function(event){
		$(this).toggleClass("over");
		if ($(this).find(".content").is(":hidden")){
			$(this).find(".date").hide();
			$(this).find(".content").show();
		}else {
			$(this).find(".content").hide();
			$(this).find(".date").show();
	    }
		});
			
	</script>
</html:form>
