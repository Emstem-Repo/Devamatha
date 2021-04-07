<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	
	
	
	<script type="text/javascript">
	function selectAll(obj) {
		value = obj.checked;
		var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
		    inputObj = inputs[count1];
		    var type = inputObj.getAttribute("type");
		   	if (type == 'checkbox') {
		   		inputObj.checked = value;
			}
	    }
	}

	function unCheckSelectAll() {
		var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
		    inputObj = inputs[count1];
		    var type = inputObj.getAttribute("type");
		   	if (type == 'checkbox' && inputObj.id != "checkAll") {
		   		checkBoxOthersCount++;
		   		if(inputObj.checked) {
		   			checkBoxOthersSelectedCount++;
		   		}	
			}
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	    	document.getElementById("checkAll").checked = false;
	    } else {
	    	document.getElementById("checkAll").checked = true;
	    }        
	}
	function validateCheckBox() {
		var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
		    inputObj = inputs[count1];
		    var type = inputObj.getAttribute("type");
		   	if (type == 'checkbox') {
		   		if(inputObj.checked){
		   			checkBoxselectedCount++;
			   	}
			}
	    }
	        document.getElementById("err").innerHTML = "Number of Cocurricular Present is:"+checkBoxselectedCount;
	            
	}

	function cancelAction()
	{
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	</script>
	<style>
	.tooltip {
	display:none;
	position:absolute;
	border:1px solid #333;
	background-color:#161616;
	border-radius:5px;
	padding:10px;
	color:#fff;
	font-size:12px Arial;
}
.colorbox
{
 height: 10px;
 width: 10px;
}
.pending { background-color: #FFBA00;}
.approved{background-color: #009933;}
.rejected{background-color: #FF0000;}
.statusBox{width: 80%;padding:10px;}
.notify{font-size: 12px;padding-left: 20px;}
.status{text-align: left;font-family: sans-serif;font-size: 12px;}
	</style>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		// Tooltip only Text
			$('.masterTooltip').hover(function() {
				// Hover over code
					var title = $(this).attr('title');
					$(this).data('tipText', title).removeAttr('title');
					$('<p class="tooltip"></p>').text(title).appendTo('body')
							.fadeIn('slow');
				}, function() {
					// Hover out code
					$(this).attr('title', $(this).data('tipText'));
					$('.tooltip').remove();
				}).mousemove(function(e) {
				var mousex = e.pageX + 20; //Get X coordinates
					var mousey = e.pageY + 10; //Get Y coordinates
					$('.tooltip').css( {
						top : mousey,
						left : mousex
					})
				});
		});
</script>
<html:form action="/ExtraCocurricularLeaveEntry" >

<html:hidden property="formName" value="extraCocurricularLeaveEntryForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="method" styleId="method" value="saveCocurricularDetails"/>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    
  </tr>
  <tr>
    <td valign="top"></td>
   
    
    <td colspan="3" valign="top">
    <table width="100%" border="0">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.attendance.studentLogin.extra.curricular.leave.entry"/></strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
			
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
					
						<tr>
							
							<td valign="top">
							  <span class='MandatoryMark notify'>* Please Check the box (&#10004; )  and select the activity to give co-curricular Attendance </span>
							  
							  <div class="notify"> For applying extra curricular attendance ,click on check box and Select the corresponding activities
							 <p> Note: Type of activity can be selected only once and you cannot change the activity once it is applied</p></div>
								<div id="errorMessage" align="left">
	                       			<FONT color="black" size="2px">
									<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
									</html:messages>
						  			</FONT></div>
							
							  <div class="statusBox"> <!-- Start Color notification -->
							  <table border="0" cellpadding="0" cellspacing="1" width="500">
							  <tr>
							  
							  <td width="15"><div class="colorbox pending"></div></td> <td><div class="status">PENDING / APPLIED </div> </td>
							   <td width="15"><div class="colorbox approved"></div></td> <td><div class="status">APPROVED </div> </td>
							    <td width="15"><div class="colorbox rejected"></div></td> <td><div class="status">REJECTED </div> </td>
							  </tr>
							 
							  
							  </table>
							  
							  </div> <!-- End Color Notification -->
							
							<table width="100%" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/st_01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/st_02.gif"></td>
	                       <td><img src="images/st_03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/st_left.gif"></td>
	                       <td valign="top">
	                       
	                       <nested:notEmpty  name="extraCocurricularLeaveEntryForm" property="list">
	                       <table width="100%" cellpadding="1" cellspacing="2">
	                       <tr align="center" class="studentrow-odd">
	                       <td width="50">Attendance Date</td>
	                       <td>Period</td>
	                       </tr>
	                       
	                       <nested:iterate name="extraCocurricularLeaveEntryForm" property="list" indexId="cd">
	                       	<tr class="studentrow-even">
	                       	<td width="15%"><nested:write property="attendanceDate"/> </td>
	                       	<td width="85%" class="row-green"> 
	                       	<table >
	                       	<nested:notEmpty property="periodToList">
	                       	<nested:iterate  id="period" property="periodToList" indexId="count" type="com.kp.cms.to.admin.PeriodTONew">
	                       
	                       <c:choose>
								<c:when test="${count%2 == 0}">
									<td class="studentrow-even" style="border: 2px solid gray;" id="list<c:out value='${cd}'/>.stus.<c:out value='${count}'/>">
								</c:when>
									<c:otherwise>
									<td class="row-blue" style="border: 2px solid gray;" id="list<c:out value='${cd}'/>.stus.<c:out value='${count}'/>">
								</c:otherwise>
					        </c:choose>
							
							<nested:equal value="true" name="period" property="isAbsent" >
							 <input
							type="hidden"
							name="list[<c:out value='${cd}'/>].periodToList[<c:out value='${count}'/>].appStatus"
							id="list_<c:out value='${cd}'/>.status_<c:out value='${count}'/>"
							value="<nested:write name='period' property='appStatus'/>" />
							
							   <input
							type="hidden"
							name="list[<c:out value='${cd}'/>].periodToList[<c:out value='${count}'/>].tempChecked"
							id="list_<c:out value='${cd}'/>.hidden_<c:out value='${count}'/>"
							value="<nested:write name='period' property='tempChecked'/>" />
							<input 
							type="checkbox" 
							name="list[<c:out value='${cd}'/>].periodToList[<c:out value='${count}'/>].checked"
							id="list<c:out value='${cd}'/>.<c:out value='${count}'/>" onclick="validateCheckBox()"/>
							<span   class="masterTooltip " title="<nested:write property="subjectName"/>"> <nested:write property="periodName" /> </span>
							<nested:select  styleId="activity" property="activity"  styleClass="comboSmall"  >
					                  <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					       		    		<html:optionsCollection property="cocurriculartActivityMap" label="value" value="key" name="extraCocurricularLeaveEntryForm"/>
					                  </nested:select>
						 	
						 	</nested:equal>
						 	<script type="text/javascript">
							var studentId1 = document.getElementById("list_<c:out value='${cd}'/>.hidden_<c:out value='${count}'/>").value;
							if(studentId1 == "true") {
								document.getElementById("list<c:out value='${cd}'/>.<c:out value='${count}'/>").checked = true;
								document.getElementById("list<c:out value='${cd}'/>.<c:out value='${count}'/>").disabled = true;
								
							}
							var status = document.getElementById("list_<c:out value='${cd}'/>.status_<c:out value='${count}'/>").value;
							document.getElementById("list<c:out value='${cd}'/>.stus.<c:out value='${count}'/>").className = status;		
						</script>
						 	<nested:equal value="false" name="period" property="isAbsent">
						 	
						 	 
						 	</nested:equal>   
					        </td>     
	                       	</nested:iterate>
	                       	</nested:notEmpty>
	                       	</table>
	                       	</td>
	                       	</tr>
	                       </nested:iterate>
	                       
	                       
	                       
	                       </table>
	                       
	                       </nested:notEmpty>
	                       
	                       </td>
	                       <td width="5" height="30"  background="images/st_right.gif"></td>
	                     </tr>
                     <tr>
                       <td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
                       <td background="images/st_05.gif"></td>
                       <td><img src="images/st_06.gif" /></td>
                     </tr>
                     
                   </table>
							
						
							<table align="center" width="100%">
									<tr class="row-white">
										<td  width="50%"><div align="right" >&nbsp;
										<logic:equal value="true" name="extraCocurricularLeaveEntryForm" property="dislaySubmitButton">
											<html:submit property="" value="Apply Now" styleClass="btnbg"  ></html:submit>
										</logic:equal>
									
										
										
										</div></td>
                   						<td ><div align="left">
										<html:button property="" value="Cancel" styleClass="btnbg" onclick="cancelAction()"></html:button>
										</div></td>
                 					</tr>
							</table>
							</td>
							
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
			</td>

		</tr>
	</table>
   
</td>
</tr>
</table>

</html:form>
