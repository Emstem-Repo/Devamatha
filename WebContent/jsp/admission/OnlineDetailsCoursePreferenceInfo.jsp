<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script type="text/javascript" src="jquery/collaps/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link rel="stylesheet" href="css/admission/css/stylesheet-pure-css.css"/>  

 <script type="text/javascript">
		setStartDate();
		setInterval ( "checkSession()", 300000 );


		function submitAddMorePreferences(method){
		document.getElementById("method").value=method;
		document.onlineApplicationForm.submit();
		}

		function addCourseId(id,preNo){
			if(preNo==1){
				document.getElementById("courseId").value=id;
			}
			var b=document.getElementById("ptype").value;
		/* if(b==2){	
			if (id==16 || id==17) {
				console.log(id);
				document.getElementById("swtch").style.display="block";
				if (preNo==2) {
					//document.getElementById("coursePreference1").value=17;
					console.log("second pref");
				}
			}
			else{
				document.getElementById("swtch").style.display="none";
				document.getElementById("method").value="removePreferences";
				document.onlineApplicationForm.submit();
				
				
			}
		} */
			/* if(id==18){
				document.getElementById("corecourse").style.display="block";
				
			}else{
				document.getElementById("corecourse").style.display="none";
				document.getElementById("Less than 14").checked=false;
				document.getElementById("14 or more").checked=false;
				document.getElementById("Less than 14").value=null;
				document.getElementById("14 or more").value=null;
			}
			 */
			
			
		}

		function resetCourcePreferenceForm() {

			//Educational info
			
			var preferenceSize=  $('#preferenceSize').val();
			if( parseInt(preferenceSize) > 0){
				
				for(var i=0;i<parseInt(preferenceSize);i++){
					
					
					$('#coursePreference'+i).val("");
						
					
				}
				
			}//errors check over

		}
		
		
		
			
				
</script>



 <style type="text/css">
 input[type="radio"]:focus, input[type="radio"]:active {
    -webkit-box-shadow:inset 2px 1px 1px , 1px 1px 3px #008000;
    -moz-box-shadow:inset 2px 1px 1px #008000, 1px 1px 3px #008000;
}
 </style>
 
 
<style type="text/css">
	
		.tooltip{
   			display: inline;
    		position: relative;
		}
		
		.tooltip:hover:after{
    		background: #333;
    		background: rgba(0,0,0,.8);
    		border-radius: 5px;
    		bottom: 26px;
    		color: #fff;
    		content: attr(title);
    		left: 20%;
    		padding: 5px 15px;
    		position: absolute;
    		z-index: 98;
    		width: 220px;
		}
		
		.tooltip:hover:before{
    		border: solid;
    		border-color: #333 transparent;
    		border-width: 6px 6px 0 6px;
    		bottom: 20px;
    		content: "";
    		left: 50%;
    		position: absolute;
    		z-index: 99;
		}
		.stformth{
		display: none;;
		}
		.prefContent{
		  display: none; 
		}
		
	
	</style>
	
 
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/studentdetails.js"></script>

 	<!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>		
 			
<html:hidden property="courseId" styleId="courseId" name="onlineApplicationForm" />
<html:hidden property="preferenceSize" styleId="preferenceSize" name="onlineApplicationForm" />
<html:hidden property="programTypeId" styleId="ptype"/>
<html:hidden property="courseListed" styleId="courseListed"/>
<html:hidden property="aidedCourse" styleId="courseAided"/>
	
<table width="80%" style="background-color: #F0F8FF" align="center">
	<%-- 	
	<tr>
    <td >
	
	<table width="100%" align="center" border="0">
	<tr>
	<td align="center">
	<div id="nav-menu">
	<ul>
	<li class="ac">Terms & Conditions</li>
	<li class="ac">Payment</li>
	<li class="current">Course Options</li>
    <li class="">Personal Details</li>
    <li class="">Education Dtails</li>
	<li  class="">Upload Photo</li>
    </ul>
    </div></td>
    </tr>
    </table>
    
    </td>
   </tr>
  --%>
  <tr><td>
  <table style="background-color: #F0F8FF; width: 100%;" align="center">
  <tr ><td height="10"></td></tr>
   
   <!-- errors display -->
  <tr >
	<td  align="center">
							<div id="errorMessage" align="center">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"	property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages>
							</FONT>
							</div>
							<div id="errorMessage1" style="font-size: 11px; color: red"></div>
	</td>
	</tr>
  
      
   
  
 		<tr ><td height="10"></td></tr>
  
	
  
   <tr>
    <td width="100%"><table align="center" width="100%" border="0" style="border-collapse:collapse">
      <tr>
        <td height="23" align="center" class="subheading">Programme Preferences</td>
      </tr>
    </table></td>
  </tr>
   
  
      <tr>
        <td><table width="100%" border="0" cellpadding="7"  align="center" class="subtable w"  >
        
        <nested:iterate id="admissionpreference" name="onlineApplicationForm" property="prefcourses" indexId="count">
	        <%
				String dynaid="coursePreference"+count;;
			%>
          <tr>
            <td width="30%"><div align="right"><span class="Mandatory">*</span>
            <bean:write name="admissionpreference" property="prefName"></bean:write>:</div>
            </td>
            <td width="70%">
            <bean:define id="prefNo" name="admissionpreference" property="prefNo"></bean:define>
                    <nested:select  property="id" styleClass="dropdown"  styleId="<%=dynaid %>" onchange='<%="addCourseId(this.value,"+prefNo+")"%>'>
					
					<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<html:optionsCollection name="onlineApplicationForm" property="courseMap" label="value" value="key"/>	
					</nested:select>
					<script>
					$(document).ready(function(){
						
						$(<%=dynaid %>).change(function(){
							var crs=document.getElementById('<%=dynaid %>').value;
							var ptype=document.getElementById("ptype").value;
							console.log(crs);
  							/*ajax  */
  							getAidedOrNot(crs,ptype,dispAlert);
  							function dispAlert(req) {
  								var result = req.responseText;
  								alert(result);
							}
  							/* ajax end */
						});
					});
		
</script>
	
			 <a href="#" title="Selcet Cources preferences wise" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/Tooltip_QIcon.png"/></span></a>
           		
            </td>
			
		 </tr>
		
		</nested:iterate> 
		
		 
		 <tr ><td height="20" colspan="2"></td></tr>
 
  
   <tr ><td height="20"></td></tr>
  
		 
		  <logic:equal value="1" name="onlineApplicationForm" property="programTypeId">
		 <tr>
		 
            <td colspan="2" width="100%" align="center" style="text-align:center">
            <a href="url" style="text-decoration:none; vertical-align:middle; color:#007700; font-weight:bold" onclick="submitAddMorePreferences('addPrefereneces'); return false;"> 
			Click here &nbsp;<img src="images/admission/images/12673199831854453770medical cross button.svg.med.png" width="20px"; height="20px"; style="vertical-align:middle;">
			</a> to Add More Preferences.
         	 
         	
			 
			</td>
            
			
		 </tr>
		 
		  <tr>
		 
            <td colspan="2" width="100%" align="center" style="text-align:center">
        
			 <c:if test="${onlineApplicationForm.preferenceSize>1}">      
			  <%-- 
			<a href="url" style="text-decoration:none; vertical-align:middle; color:#B91A1A; font-weight:bold" onclick="submitAddMorePreferences('removePreferences'); return false;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Remove &nbsp;<img src="images/admission/images/15107-illustration-of-a-red-close-button-pv.png" width="20px"; height="20px"; style="vertical-align:middle;"></a>
			--%>
			<a href="url" style="text-decoration:none; vertical-align:middle; color:#B91A1A; font-weight:bold" onclick="submitAddMorePreferences('removePreferences'); return false;">
			Click here &nbsp;<img src="images/admission/images/15107-illustration-of-a-red-close-button-pv.png" width="20px"; height="20px"; style="vertical-align:middle;">
			
			</a> to Remove Preference.&nbsp;  &nbsp; &nbsp;
			 
			
			 </c:if>
			 
			</td>
            
			
		 </tr>
         
         </logic:equal>
        </table>
        </td>
      </tr>
   
   
     <!--<logic:equal value="1" name="onlineApplicationForm" property="programTypeId">
     <tr><td align="center">Only a maximum of <b><%=CMSConstants.MAX_CANDIDATE_PREFERENCES %></b> preferences allowed.</td></tr>
     </logic:equal>-->
     
     
     <tr>
		 
            <td colspan="2" width="100%" align="center" style="text-align:center">
            <div class="stformth" id="swtch">
            <a href="url" style="text-decoration:none; vertical-align:middle; color:#007700; font-weight:bold" onclick="submitAddMorePreferences('addPrefereneces'); return false;"> 
			Click here &nbsp;<img src="images/admission/images/12673199831854453770medical cross button.svg.med.png" width="20px"; height="20px"; style="vertical-align:middle;">
			</a> to Add More Preferences.
         	 
         	
			 </div>
			</td>
            
			
		 </tr>
		 
		  
     
     
     <logic:equal value="2" name="onlineApplicationForm" property="programTypeId">
     <tr>
		 
            <td colspan="2" width="100%" align="center" style="text-align:center">
        
			 <c:if test="${onlineApplicationForm.preferenceSize>1}">      
			  <%-- 
			<a href="url" style="text-decoration:none; vertical-align:middle; color:#B91A1A; font-weight:bold" onclick="submitAddMorePreferences('removePreferences'); return false;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Remove &nbsp;<img src="images/admission/images/15107-illustration-of-a-red-close-button-pv.png" width="20px"; height="20px"; style="vertical-align:middle;"></a>
			--%>
			<a href="url" style="text-decoration:none; vertical-align:middle; color:#B91A1A; font-weight:bold" onclick="submitAddMorePreferences('removePreferences'); return false;">
			Click here &nbsp;<img src="images/admission/images/15107-illustration-of-a-red-close-button-pv.png" width="20px"; height="20px"; style="vertical-align:middle;">
			
			</a> to Remove Preference.&nbsp;  &nbsp; &nbsp;
			 
			
			 </c:if>
			 
			</td>
            
			
		 </tr>
     <tr><td align="center"><%-- Only <b><%=CMSConstants.MAX_CANDIDATE_PREFERENCES_PG %></b> preferences allowed. --%></td></tr>
     </logic:equal>

      <!-- <tr>
	     <td style="color: red">
	     	 <ul>
	     		 <li>Candidates need to submit seperate applications for both Aided as well as Unaided courses. That means you have to register twice and will need to submit your applications in two diffrent accounts.</li>
	     	</ul>	     				
	     </td>
     </tr>    -->   
     						   
 <tr ><td height="20"></td></tr>
 
  
  
  <tr>
  <td  width="100%" align="center">
    <html:button property="" onclick="submitAdmissionForm('submitPriferencePageOnline')" styleClass="cntbtn" value="Save & Continue to Personal Details">  </html:button>
   
  </td>
  </tr>
  
  <tr>
  <td  width="100%" align="center">
  <br/>
   <html:button property="" value="Clear" styleClass="btn1" onclick="resetCourcePreferenceForm();" /> 
   &nbsp; <html:button property="" onclick="cancel()" styleClass="cancelbtn" value="Logout"></html:button>
   
  </td>
  </tr>
  
   <tr ><td height="40px"></td></tr>
   
   
</table>
</td>
</tr>
</table>
