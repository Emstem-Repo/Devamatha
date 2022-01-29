<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type="text/javascript">

function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function cancelAction()
{
	document.location.href = "certificateRequest.do?method=ShowStatusList";
}
function submitMarksCardAdd(method,mode){
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.certificateRequestOnlineForm.submit();
}

function openHtml(certificateId) {
	var url = "certificateRequest.do?method=getDescription&certDescId="+certificateId;
	win2 = window.open(url, "Description","width=500,height=500,scrollbars=yes"); 
}

$(document).ready(function(){
    $("#report1 .tdIMG");
    $("#report1 .data").hide();
    $("#report1 tr:first-child").show();
    
    $("#report1 .tdIMG").click(function(){
        $(this).parent().parent().next("tr").toggle();
    });

    var focusField=document.getElementById("focusValue").value;
	 if(focusField != null){  
		 if(document.getElementById(focusField)!=null)   
	if(focusField == "marksCardHide")
    { 
   $("#marksCardHide").toggle();
    }
 }   
});
</script>
<html:form action="/certificateRequest" >
	<html:hidden property="formName" value="certificateRequestOnlineForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="certDescId" styleId="certDescId" value=""/>
	
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.certificateRequestOnlineTO"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.certificateRequestOnlineTO" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td align="left">
							<div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	   				<div id="errorMessage" style="color:red;font-family:arial;font-size:11px;">
	                       				<p>
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
										<span id="err"><html:errors/></span> 
										</p>
	                       					<FONT color="green">
											<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
											</html:messages>
						 				 </FONT>
						  			</div>
							
							</td>
						</tr>
						<tr>
							<!-- <td width="5" background="images/st_left.gif"></td>-->
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" class="studentrow-white" >
							
							<tr height="25px">
								<td  align="center" width="80%" class="studentrow-white"> <b> Requested Certificate Status</b> </td>
							</tr>
							
	<tr>
				<td colspan="2">
					<table width="100%" height="30" border="1" cellpadding="2" cellspacing="1" id="report1">
					<tr height="30px">
							<td width="40" class="studentrow-odd" align="center">Sl No:</td>
							<td width="60" class="studentrow-odd" align="center">Applied Date</td>
           					<td width="270" class="studentrow-odd" align="center"><bean:message key="knowledgepro.certificate.name"/></td>
           					<td width="150" class="studentrow-odd" align="center">Status</td>
           					<td width="8%" class="studentrow-odd" align="left">Description</td>
   					</tr>
		    <logic:notEmpty name="certificateRequestOnlineForm" property="studentToList">
			<nested:iterate id="cert" property="studentToList" name="certificateRequestOnlineForm" indexId="count">
			
			<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										</c:when>
										<c:otherwise>
											<tr class="studentrow-white">
										</c:otherwise>
									</c:choose>
					<td  align="center"><c:out value="${count + 1}" /></td>
					<td   align="center"><nested:write name="cert" property="appliedDate"/></td>
					<td   align="left"><nested:write name="cert" property="certificateName"/></td>
					<td  align="left"><nested:write name="cert" property="certificateStatus"/></td>
					
					<td   align="center">
					<logic:equal value="true" property="certificateDescription" name="cert">
					<img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
						 onclick="openHtml('<nested:write property="certificateId"/>')" title="Description">
				   </logic:equal></td>
		
		</nested:iterate>
		</logic:notEmpty>
	</table>
								</td>
							</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
				
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<!-- <tr class="studentrow-white">
                   	<td colspan="2"><div align="center">
						<html:button property="" styleClass="formbutton" value="Close" onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>-->
            <tr>
            <td colspan="2" align="center"> <html:button property=""  styleClass="btnbg" value="Close" onclick="goToHomePage()"></html:button> </td>
            </tr>
                 <tr><td>&nbsp;</td></tr>
					</table>
					</td>
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
</html:form>
<script language="JavaScript" >
	var focusField=document.getElementById("focusValue").value;
	if(focusField != 'null'){  
   	if(document.getElementById(focusField)!=null)      
        document.getElementById(focusField).focus();
	}
</script>