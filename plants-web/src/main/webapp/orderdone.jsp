<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!-- 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2002
All Rights Reserved * Licensed Materials - Property of IBM
--> 

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="DC.LANGUAGE" scheme="rfc1766"/>

<link href="/plants-web/PlantMaster.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/plants-web/applycss.js" language="JavaScript"></script>

</head>
<body class="work" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4')">
<%
	Long orderID = (Long) request.getAttribute(com.emc.plants.utils.Util.ATTR_ORDERID);
%>

<table border="0" cellpadding="0" cellspacing="5" width="100%">
  <tr>
    <td><p class="trail"><a class="trail" class="footer" href="/plants-web/promo.html" target="work">Home</a></p></td>
  </tr>
  <tr>
  <td width="100%">
     <table border="0" cellpadding="5" cellspacing="5" width="600">
        <tr>
          <td width="100%" valign="middle"><H1>Order Completion</H1></td>
          <td align="right" valign="middle" nowrap></td>
	       <td valign="middle"></td>
     	  </tr>
        <tr>
          <td>
            <p>Thank you for making your Plants By Spring Source purchase!</p><br>
            <p>Order number <%= orderID%></p><br>
            <p>Expected arrival in 5-7 business days.</p><br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table><br>
<table bgcolor="#669966" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td width="1"><img border="0" src="/plants-web/images/1x1_trans.gif" width="1" height="1" alt=""></td>
  </tr>
</table>
<br>
<table border="0" cellpadding="5" cellspacing="0" width="100%">
  <tr>
    <td>
      <img src="/plants-web/images/poweredby_WebSphere.gif" alt="Powered by WebSphere">
    </td> 
    <td>
      <p class="footer"><a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=0" target="work">Flowers</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=1" target="work">Fruits &amp; Vegetables</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=2" target="work">Trees</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=shopping&category=3" target="work">Accessories</a><br>
                  <a class="footer" href="/plants-web/index.html" target="_top">Home</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/ShoppingServlet?action=gotocart" target="work">Shopping Cart</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/servlet/AccountServlet?action=account" target="work">My Account</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/login.jsp" target="work">Login</a>&nbsp;&nbsp;:&nbsp;
                  <a class="footer" href="/plants-web/help.jsp" target="_blank">Help</a></p>
    </td>
  </tr>
</table>
</body>
</html>