<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!-- 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2002
All Rights Reserved * Licensed Materials - Property of IBM
--> 

<html>
<head>
<title>Help : Plants by WebSphere</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="DC.LANGUAGE" scheme="rfc1766"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>
<script>
window.innerWidth=600;
window.innerHeight=500;
function goodbye()
{
    document.forms[0].submit();
}
</script>

</head>
<%
	String checkedParm = " ";
if (com.emc.plants.utils.Util.debugOn())
{
   checkedParm = "checked";
}
%>
<body>
<h1>More Information</h1>
<br>
<p>For more information about Plants by WebSphere,
<!-- <META name="links-collection-enabled" content="false"> --> 
<a href="/docs/en/techNotes.html" target="_blank">see the TechNotes</a>.
<!-- <META name="links-collection-enabled" content="true"> --> 
</p>
<br>
<table bgcolor="#669966" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="1"><img border="0" src="/plants-web/images/1x1_trans.gif" width="1" height="1" alt=""></td>
  </tr>
</table>
<h1>Populate Database</h1>
<table bgcolor="#ffffff" cellpadding="4" cellspacing="0" border="0" width="100%">
<tr>
   <td width="100%">
      <A href="/plants-web/servlet/AdminServlet?admintype=populate">(Re)Populate DataBase</A> - Populate the Plants By WebSphere Database.
   </td>
</tr>
<tr>
   <td></td>
</tr>
</table>
<h1>Logging Option</h1>
<br>
<p>Select Logging to have Plants by WebSphere debug messages appear in stdout.</p>
<br>
<form onsubmit="return goodbye();" target="_self" method="post" action="/plants-web/servlet/AccountServlet?action=SetLogging">
<table border="0" cellpadding="15" cellspacing="5">
   <tr>
     <td align="center">
        <input type="checkbox" name="logging" value="debug" <%=checkedParm%>>Logging
     </td>
   <tr>
     <td align="center">
        <input type="submit" value="Save Setting">
     </td>
   </tr>
</table>

<table bgcolor="#669966" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="1"><img border="0" src="/plants-web/images/1x1_trans.gif" width="1" height="1" alt=""></td>
  </tr>
</table>
</form>
</body>
</html>