<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
 <script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    // Tooltip only Text
    $('.dropt').tooltipster();
});
function syllabusEntry(subId){
	document.getElementById("method").value = "initAdminSyllabusEntrySecondPage";
	document.getElementById("subjectId").value = subId;
	document.syllabusEntryForm.submit();
}
function resetGrid(){
	alert("hai");
	document.getElementById("hDetails").innerHTML = "";
}
function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function semester(){
	document.getElementById("semister").value="";
}
function subjectAndStatus(){
	document.getElementById("method").value = "getsubjectAndStatus";
	document.syllabusEntryForm.submit();
	}
function sujectAndStatus111(semesterNo){
	document.getElementById("hDetails").innerHTML = "";
	var deptId=document.getElementById("departmentId").value;
	var year=document.getElementById("batchYear").value;
	var url = "syllabusEntry.do";
	var args = "method=getsubjectAndStatus&batchYear="+year+"&departmentId="+deptId+"&semisterId="+semesterNo;
	requestOperationProgram(url, args, displayList);
	}
function displayList(req){
	var responseObj = req.responseXML.documentElement;
	var year = document.getElementById("batchYear").value;
	var TList = responseObj.getElementsByTagName("TList");
	var htm="<tr><td height='19' valign='top' background='images/Tright_03_03.gif'></td><td valign='top' class='news'>";
	htm=htm+"<table width='100%' border='0' align='center' cellpadding='0' 	cellspacing='0'><tr>";
	htm=htm+"<td><img src='images/01.gif' width='5' height='5'/></td><td width='914' background='images/02.gif'></td>";
	htm=htm+"<td><img src='images/03.gif' width='5' height='5'/></td></tr><tr><td width='5' background='images/left.gif'></td>";
	htm=htm+"<td valign='top'><table width='100%' cellspacing='1' cellpadding='2'><tr>";
	htm=htm+"<td width='5%'  height='25%' class='row-odd' ><div align='center'><bean:message key='knowledgepro.slno'/></div></td>";
	htm=htm+"<td width='75%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admisn.subject.Name'/></div></td>";
	htm=htm+"<td width='20%'  height='25' class='row-odd' ><div align='center'><bean:message key='employee.info.job.achievement.status'/></div></td></tr>";
 				
	 for ( var I = 0; I < TList.length; I++) {
			if(TList[I]!=null){
				var subId= TList[I].getElementsByTagName("subId")[0].firstChild.nodeValue;
				var subName = TList[I].getElementsByTagName("subName")[0].firstChild.nodeValue;
				var status = TList[I].getElementsByTagName("status")[0].firstChild.nodeValue;
				
				htm=htm+"<tr><td  height='25' class='row-even' align='center'>"+(parseInt(I)+1)+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'>"+subName+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'>"+status+"</td></tr>";
			}
		}
		htm=htm+"</table></td><td width='5' height='30' background='images/right.gif'></td>";
		htm=htm+"</tr><tr><td height='5'><img src='images/04.gif' width='5' height='5' /></td>";
		htm=htm+"<td background='images/05.gif'></td><td><img src='images/06.gif'/></td></tr>";
		htm=htm+"</table></td><td valign='top' background='images/Tright_3_3.gif' class='news'></td></tr>";
	 document.getElementById("hDetails").innerHTML = htm;
	}
function preview(subId,status){
	if(status=='Pending'){
		 $.confirm({
				'message'	: "Syllabus Entry status is pending",
				'buttons'	: {
	     		'Ok'	:  {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
		}else{
			var year=document.getElementById("batchYear").value;
			url = "syllabusEntry.do?method=preview&subjectId="+subId+"&batchYear="+year;
			myRef = window.open(url,"collectionLedger","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
}
</script>
<html:form action="/syllabusEntry" method="post">
<html:hidden property="formName" value="syllabusEntryForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" value="getsubjectAndStatus" styleId="method"/>
<html:hidden property="subjectId" value="" styleId="subjectId"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;Admin Syllabus Entry&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Admin Syllabus Entry</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
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
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       <table width="100%" cellspacing="1" cellpadding="2">
                       	  <tr>
                       	  	<td width="25%" class="row-odd" ><div align="right">
                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.cancelattendance.batches"/>:</div>
                             </td>
                             <td width="25%" height="25" class="row-odd">
                             <html:hidden property="tempBatchYear" styleId="tempBatchYear" name="syllabusEntryForm"/>
                           		<html:select property="batchYear" styleClass="combo" styleId="batchYear" name="syllabusEntryForm">
									<html:option value=""> <bean:message key="knowledgepro.admin.select" />	</html:option>
									<logic:notEmpty property="batchMap" name="syllabusEntryForm">
		   								<html:optionsCollection property="batchMap" label="value" value="key"/>
		   							</logic:notEmpty>
								</html:select>
                             </td>
                             <td width="25%" class="row-odd" ><div align="right">
                             	<span class="Mandatory">*</span><bean:message key="knowledgepro.usermanagement.userinfo.department"/>:</div>
                             </td>
                             <td width="25%" height="25" class="row-odd" >
                           		<html:select property="departmentId" styleId="departmentId">
                 					<html:option value="">--Select--</html:option>
                 					<logic:notEmpty property="departmentMap" name="syllabusEntryForm">
		   								<html:optionsCollection property="departmentMap" label="value" value="key"/>
		   							</logic:notEmpty>
		   						</html:select>
                             </td>
                           </tr>
                           <tr>
                             <td width="25%" class="row-odd" ><div align="right">
                             	<span class="Mandatory">*</span><bean:message key="studentView.Semester.link"/>:</div>
                             </td>
                             <td width="25%" height="25" class="row-odd" >
                            	 <html:select property="semisterId" styleId="semister" styleClass="comboSmall" onchange="subjectAndStatus()">
                            	 	<html:option value="">--Select--</html:option>
                            	 	<html:option value="1">1</html:option>
                            	 	<html:option value="2">2</html:option>
                            	 	<html:option value="3">3</html:option>
                            	 	<html:option value="4">4</html:option>
                            	 	<html:option value="5">5</html:option>
                            	 	<html:option value="6">6</html:option>
                            	 	<html:option value="7">7</html:option>
                            	 	<html:option value="8">8</html:option>
                            	 	<html:option value="9">9</html:option>
                            	 	<html:option value="10">10</html:option>
	   							 </html:select>
                             </td>
                             <td width="25%" class="row-odd" >
                             </td>
                             <td width="25%" height="25" class="row-odd">
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
                 </tr>
                 <tr><td height="10"></td></tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
		                      		<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                <!--
		                <tr>
							<td width="100%" id="hDetails" colspan="6"></td>
						</tr>
                 -->
                 <logic:notEmpty  name="syllabusEntryForm" property="list1">
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>

							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr >
                    				<td width="5%"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
			               				<td width="20%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.code"/></td>
                    				<td width="75%" height="30%" class="row-odd" align="center" ><bean:message key="knowledgepro.admisn.subject.Name"/></td>
                    				<td width="20%" height="30%" class="row-odd" align="center" ><bean:message key="employee.info.job.achievement.status"/></td>
                    				<td width="9%" height="30%" class="row-odd" align="center" >Preview</td>
                 				</tr>
								<nested:iterate id="CME" name="syllabusEntryForm" property="list1" indexId="count">
                					<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
                   					<td  height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
		                  			<td  height="25" align="left"><bean:write name="CME" property="subjectCode"/></td>
                   					<td  height="25"  align="left"><a href="#" onclick="syllabusEntry('<bean:write name="CME" property="subjId"/>')"><span class="dropt" title="Click here for syllabus entry."><bean:write name="CME" property="subjectName"/></span></a></td>
                   					<td  height="25" align="center">
                  						<nested:equal value="Pending" property="status">
                  							<b><bean:write name="CME" property="status"/></b>
                  						</nested:equal>
                  						<nested:equal value="In-progress" property="status">
                  							<font color="#E036DA "><b><bean:write name="CME" property="status"/></b></font>
                  						</nested:equal>
                  						<nested:equal value="Rejected" property="status">
                  							<font color="#1E9C83"><b><bean:write name="CME" property="status"/></b></font>
                  						</nested:equal>
                  						<nested:equal value="HOD Rejected" property="status">
                  							<font color="#F20D42"><b><bean:write name="CME" property="status"/></b></font>
                  						</nested:equal>
                  						<nested:equal value="Approved By HOD" property="status">
                  							<font color="#05A959"><b><bean:write name="CME" property="status"/></b></font>
                  						</nested:equal>
                  						<nested:equal value="Approved" property="status">
                  							<font color="0DF24E"><b><bean:write name="CME" property="status"/></b></font>
                  						</nested:equal>
                  						<nested:equal value="Completed" property="status">
                  							<font color="#200DF2"><b><bean:write name="CME" property="status"/></b></font>
                  						</nested:equal>
                  						
                  					</td>
                   					<td  height="25" align="center"> <img src="images/View_icon.gif"
						 					height="18" style="cursor:pointer" onclick="preview('<bean:write name="CME" property="subjId"/>','<bean:write name="CME" property="status"/>')"></td>
                				</nested:iterate>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				</logic:notEmpty>
				
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
     <tr>
       <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
       <td width="100%" background="images/TcenterD.gif"></td>
       <td><img src="images/Tright_02.gif" width="9" height="29"></td>
     </tr>
   </table>
   </td>
 </tr>
</table>
<script type="text/javascript">
document.getElementById("batchYear").value=document.getElementById("tempBatchYear").value;
</script>
</html:form>