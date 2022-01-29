<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Employee Resume Submission</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
	function addEmp(){
		document.location.href = "EmployeeResumeSubmission.do?method=addEmployeeInfo";
	}
	function searchEmp(){
		document.location.href = "EmployeeResumeSubmission.do?method=searchEmployeeInfo";
	}
</script>

<script type="text/javascript">
$(document).ready(function(){
$(".designation").hide();
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".Working").click(function(){
$(".designation").show();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".NotWorking").click(function(){
$(".designation").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".remove").hide();
});


$(document).ready(function(){
$(".morerows").click(function(){
$(".remove").show();
});
});

$(document).ready(function(){
$(".remove").click(function(){
$(".remove").hide();
});
});
</script>
<script type="text/javascript">
$(document).ready(function(){
$(".remove1").hide();
});


$(document).ready(function(){
$(".morerows1").click(function(){
$(".remove1").show();
});
});

$(document).ready(function(){
$(".remove1").click(function(){
$(".remove1").hide();
});
});
</script>


<script type="text/javascript">
$(document).ready(function(){
$(".educationshow").hide();
});

$(document).ready(function(){
$(".educationAddMoreRows").click(function(){
$(".educationshow").show();
});
});

$(document).ready(function(){
$(".educationremove").click(function(){
$(".educationshow").hide();
});
});

</script>
</head>
<html:form action="/EmployeeInfoDisplay"
	enctype="multipart/form-data">
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="EmployeeInfoForm" />
<body>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">Employee Info &gt;&gt;Employee Information  
			<bean:message key="knowledgepro.employee.resume.submission"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
<td width="1271" background="images/Tcenter.gif" class="body" >
<div align="left">
<strong class="boxheader">Employee Info </strong>
</div>
</td>
<td width="15" >
<img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
</tr>
<tr>
<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
<td valign="top" class="news"><div align="center">
<table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
<tr>
<td height="20" colspan="6" align="left">
<div align="right" style="color:red"> <span class='MandatoryMark'>mandatoryfields</span></div>
</td>
</tr>
<tr>
<td height="20" colspan="6" valign="top" class="body" >
<table width="100%" cellspacing="1" cellpadding="2">
	
	<tr>

      
      
        <td colspan="3" valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="28%" height="25" class="row-odd"><div align="right" >Department: </div></td>
                <td width="22%" class="row-even">
				 <select>
                <option>Account Departments</option>
                <option>Education Departments</option>
                <option>MBA Departments</option>
                <option>Admission Office</option>
                </select>
				</td>
                <td width="18%" class="row-odd"><div align="right" > Employee Name
                  : </div></td>
                <td width="32%" class="row-even"><span class="star">
                  <input type="text" name="joinedDate" />
                </span> </td>
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
</table>

<table>
<tr>
							
							<td width="100%" height="29" valign="top">
							<table width="100%"  cellspacing="1" cellpadding="2">
							<tr>
							
							<td align="right"> 
							<html:button property="" styleId="print" styleClass="formbutton" value="Search" onclick="searchEmp()"></html:button>
							</td>
							<td align="left">
							 <html:button property="" styleClass="formbutton" value="Add" onclick="addEmp()"></html:button>
							&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Reset"></html:button>
							</td>
							<td align="left">
							
							</td>
							</tr>
							</table> 								
							</td>
							<td background="images/right.gif" width="5" height="29"></td>
						</tr>
</table>
</td>
</tr>
<tr>
<td height="10" colspan="6" class="body" ></td>
</tr>
</table>
</div></td>
<td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
</tr>
<tr>
<td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
<td width="100%" background="images/TcenterD.gif"></td>
<td><img src="images/Tright_02.gif" width="9" height="29"/></td>
</tr>
</table>
</td>
</tr>
</table>
</body>
</html:form>
</html>