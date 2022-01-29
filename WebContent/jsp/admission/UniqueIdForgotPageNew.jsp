<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <meta content="utf-8" http-equiv="encoding">
 
 <script type="text/javascript">


function resetForgot(){   
	document.getElementById("registerDateOfBirth").value = "";
	document.getElementById("email").value = "";
	
}

</script>

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
 
<%-- 
<link rel="stylesheet" href="css/admission/OnlineApplicationFormNew.css"/>
<LINK REL=StyleSheet HREF= "css/admission/AdmissionResponsiveTabs.css" TYPE="text/css">

--%>
 
  
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
		
		
	.text-label{
	color:#cdcdcd;
	font-weight:bold;
	}
	
	</style>
	
 
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>


<script src="js/AC_RunActiveContent.js" type="text/javascript"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/ajax/Ajax.js"></script>
<script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
<script type="text/javascript" src="js/common.js"> </script>
<script type='text/javascript' src="jquery/Zebra/javascript/zebra_dialog.js"></script>
<link rel="stylesheet" href="jquery/Zebra/css/default/zebra_dialog.css"  />
<style type="text/css">
.ui-datepicker {
        font-family:Garamond;
        font-size: 14px;
        margin-left:10px
     }
</style>

 
<title>Online Application Form</title>
</head>



<html:form action="/uniqueIdRegistration" method="post">
<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="uniqueIdRegistrationForm" />
	<html:hidden property="mode" name="uniqueIdRegistrationForm" styleId="mode" />
	<html:hidden property="pageType" name="uniqueIdRegistrationForm" styleId="pageType" />
	<html:hidden property="dateRange" name="uniqueIdRegistrationForm" styleId="dateRange" />
	<html:hidden property="offlinePage" name="uniqueIdRegistrationForm" styleId="offlinePage"/>
	<body >	
	<!--Start Body  -->
	
	
	
	<table width="80%" style="background-color: #F0F8FF" align="center">

	
 	
	 <tr>
	<td>
	<table width="100%" align="center">
	<tr>
	  <td colspan="2" width="100%"></td>
	 </tr>
	 
	 
	 
	 
	 <tr>
	
	  <td  width="35%" >
	  
	  
      
	  </td>
	  
	  <td align="center"  width="25%">
	  
	  
	   <!-- errors display -->
    <div align="center" class="subheading">
		<div id="errorMessage" align="center">
			<FONT color="red"><html:errors /></FONT>
		</div>
	<div id="errorMessage1" style="font-size: 13px; color: red"></div>
	</div>
	
	  <div align="center" class="st_login">FORGOT PASSWORD</div>
	 <div style="background-image:url(images/admission/images/blue.jpg);  height:220px; width:270px; border-bottom-right-radius:12px;border-bottom-left-radius:12px;">
	   <div style="height:10px"></div>
	  
          <br/>
            
         <div class="login_input"> Date of Birth: 
       <%--  <html:text  property="registerDateOfBirth" styleClass="textboxmedium"  name="uniqueIdRegistrationForm" styleId="registerDateOfBirth" 
		 	onkeypress="return checkNumeric(event)" size="10" maxlength="10"/>
		 	--%>
		 	 <html:text  property="registerDateOfBirth" styleClass="textboxmedium"  name="uniqueIdRegistrationForm" styleId="registerDateOfBirth" 
		 	 size="10" maxlength="10"/>
		
			<script language="JavaScript">
										$(function(){
											var d = new Date();
											var year = d.getFullYear() - 15 ;
											  $("#registerDateOfBirth").datepicker({dateFormat:"dd/mm/yy",
												changeMonth: true,
												changeYear: true,
												yearRange: '1950:' + year, 
												defaultDate: new Date(year, 0, 1),
												reverseYearRange: true
												});
											});
			</script>
		
            
         <a href="#" title="Enter date of birth as your user id" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/tooltip_icon1.png"/></span></a>
           
        </div>
		
		
     <br/>
     
      
      <div class="login_input">&nbsp; &nbsp; &nbsp; &nbsp;Email Id:
    	 <html:text  property="emailId" name="uniqueIdRegistrationForm" styleClass="textboxmedium" styleId="email" size="30" onclick="checkMail(this.value)" onchange="checkMail(this.value)"/>
     <a href="#" title="Enter registered mailId" class="tooltip"><span title="Help"><img alt="" src="images/admission/images/tooltip_icon1.png"/></span></a>
        
      </div>
     
     
     
     <br/>
       &nbsp; &nbsp;<html:submit value="Submit"  styleClass="formbutton" styleId="forgot_uniqueId_validate"/>
  		 &nbsp;  &nbsp; &nbsp; 
   	 <html:button value="Clear" property="" styleClass="formbutton" onclick="resetForgot()" />
      <br/>
      <br/>
	 &nbsp; &nbsp;<html:button value="Cancel" property="" styleClass="formbutton" onclick="loginPage()" />	
      
      </div>
      
      
	  
	  
	  
	  
	  </td>
	 
	  <td  width="40%" >
	  
	  
      
	  </td>
	  
	 </tr>
	 
	 
	  
  	 
	 
	 
	</table>
	</td>
	</tr>
	
   
   <tr height="40px"><td></td></tr>
   
   
   
     
	</table>
	
	
		
		
		
	<!--End login form -->
	<!--End of body -->
	
	</body>
	
</html:form>
<script src="js/admission/UniqueIdForgotPage.js" type="text/javascript"></script>

 <script type="text/javascript">

 $("#registerDateOfBirth").attr("placeholder","dd/mm/yyyy");

 
 /*
 $('input[type="text"]').each(function(){

	 this.value=$(this).attr('title');
	 $(this).addClass('text-label');
	 
		$(this).focus(function(){
		if(this.value==$(this).attr('title')){
		this.value='';
		$(this).removeClass('text-label');
		}
 		});

		$(this).blur(function(){
			if(this.value==''){
				this.value==$(this).attr('title');
			$(this).addClass('text-label');
			}
	 	});

 });
 */
 </script>

</html>
