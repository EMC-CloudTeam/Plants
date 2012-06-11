<!doctype HTML PUBLIC "-//W3C//DTD HTML 4.0//EN">

<!-- 
"This sample program is provided AS IS and may be used, executed, copied and modified without royalty payment by customer (a) for its own instruction and study, (b) in order to develop applications designed to run with an IBM WebSphere product, either for customer's own internal use or for redistribution by customer, as part of such an application, in customer's own products."

Product 5630-A36,  (C) COPYRIGHT International Business Machines Corp., 2001, 2002
All Rights Reserved * Licensed Materials - Property of IBM
--> 

<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Shopping</title>
<link rel=StyleSheet href="/plants-web/PlantMaster.css" type="text/css" title="PlantsByWebSphere' Style">
</head>

<%
	int count = 0; 

java.util.List invitems = (java.util.List) 
      request.getAttribute(com.emc.plants.utils.Util.ATTR_INVITEMS);
      System.out.println(invitems.size());
com.emc.plants.persistence.Inventory inv = 
      (com.emc.plants.persistence.Inventory) (invitems.get(count));

String categoryName = com.emc.plants.utils.Util.getCategoryString(inv.getCategory());  

String productID = "/plants-web/servlet/ShoppingServlet?action=productdetail&itemID=" + inv.getInventoryId();

//based on the categoryName, select the correct menu item to highlight

String menu=null;

if (categoryName.equals("Flowers")) 
   menu="menu1";
else
   if (categoryName.equals("Fruits & Vegetables"))
       menu="menu2";
   else
       if (categoryName.equals("Trees"))
           menu="menu3";
       else
           menu="menu4";
%>

<!-- will need to use catgory names instead of using menu -->
<!-- <BODY bgcolor="#ffffff" marginwidth="0" leftmargin="0" onload="top.selectMenu('menu2');top.deselectMenu('menu1');top.deselectMenu('menu3');top.deselectMenu('menu4')"> -->
<body marginwidth="0" leftmargin="0" onload="top.deselectMenu('menu1');top.deselectMenu('menu2');top.deselectMenu('menu3');top.deselectMenu('menu4');top.selectMenu('<%=menu%>')"> 

<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td class="trail"><p class="trail"><a class="trail" class="footer" href="/plants-web/promo.html" target="work">Home</a></p></td>
  </tr>
  <tr>
    <td colspan="5" width="100%">
      <table cellpadding="5" cellspacing="5" border="0" width="600">
        <tr>
          <td width="100%" valign="middle"><h1><%=categoryName%></h1></td>
          <td align="right" valign="middle" nowrap><p>Page 1 of 1 </td>
        </tr>
      </table>
     </td>
  </tr>
</table>
<table cellpadding="5" cellspacing="5" border="0" width="600">
  <tr>
    <td valign="top" align="center"><a href="<%=productID%>"><img src="/plants-web/servlet/ImageServlet?action=getimage&inventoryID=<%=inv.getInventoryId()%>"
        border="0" height="95" width="80" hspace="6" alt="<%=inv.getName()%> "></a><br><p><a href="<%=productID%>"><%=inv.getName()%></a><br></td>

<%
	++count;
while (count < invitems.size())
{
   inv = (com.emc.plants.persistence.Inventory) (invitems.get(count));
   categoryName = com.emc.plants.utils.Util.getCategoryString(inv.getCategory());  
   productID = "/plants-web/servlet/ShoppingServlet?action=productdetail&itemID=" + inv.getInventoryId();
%>


    <td valign="top" align="center"><a href="<%= productID %>"><img src="/plants-web/servlet/ImageServlet?action=getimage&inventoryID=<%=inv.getInventoryId()%>"
        border="0" height="95" width="80" hspace="6" alt="<%= inv.getName()%> "></a><br><p><a href="<%= productID %>"><%= inv.getName()%></a><br></td>
      
<% if ( ((count + 1) % 5) == 0 ) {
%>
  </tr>
  <tr>
<%
} 
%>


<!-- end while --> 
<%
++count;
}
%>

  </tr>
</table>

<table cellpadding="5" cellspacing="5" border="0" width="600">
  <tr>
    <td width="100%">&nbsp;</td>
    <td align="right" valign="middle" nowrap><p>Page 1 of 1 </td>
  </tr>
</table>

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
