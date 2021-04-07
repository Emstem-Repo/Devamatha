<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" buffer="64kb"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>


<tiles:importAttribute scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Knowledge Pro</title>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
	
	
	<script language="JavaScript" src="js/admission/onlineApplicationFunctions.js"></script>

    <link rel="stylesheet" href="css/admission/css/style.css"/>
      
<script type="text/javascript">
	
//prevent browser back button
//history.pushState(null, null, 'OnlineApplication');
//window.addEventListener('popstate', function(event) {
//history.pushState(null, null, 'OnlineApplication');
//});


</script>


<script>
  function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>

<script type="text/javascript">
	//javascript:window.history.forward(1);
</script>


</head>

<body >
<div >    
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
		 
		 <tr><td valign="top">
       		   <tiles:insert attribute="header" flush="false"/>
         </td>
         </tr>
		
		
		<tr valign="top" height="100">
			 <td  valign="top">
				 
				<tiles:insert attribute="body" flush="true"/>
						
			 </td>
		</tr>
		
		
		 <tr><td valign="top">
       		   <tiles:insert attribute="footer" flush="false"/>
         </td>
         </tr>
         
	</table>
</div>



</body>
</html>
