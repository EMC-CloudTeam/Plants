// THIS PRODUCT CONTAINS RESTRICTED MATERIALS OF IBM
// 5724-I63, 5724-H88 (C) COPYRIGHT International Business Machines Corp. 1997, 2004
// All Rights Reserved * Licensed Materials - Property of IBM
// US Government Users Restricted Rights - Use, duplication or disclosure
// restricted by GSA ADP Schedule Contract with IBM Corp.

var isNav4 = 0;
var isIE   = 0;

var multiplier = 0.7; 
var browserPlatform = "";
var browserPlatformM = 1.0;
var browserJava = "";
var browserJavaM = 1.0;
var browserLocale = "";
var browserLocaleM = 1.0;

var theagent = window.navigator.userAgent.toLowerCase();


//Determining what Operating System
if (-1 != theagent.indexOf("windows", 0)) {
        browserPlatform = "NT";
        browserPlatformM = 1.0;

}
else if (-1 != theagent.indexOf("aix", 0)) {
        browserPlatform = "AIX";        
        browserPlatformM = 1.2;
}
else if (-1 != theagent.indexOf("sunos", 0)) {
        browserPlatform = "SOLARIS";        
        browserPlatformM = 1.3;
}
else if (-1 != theagent.indexOf("linux", 0)) {
        browserPlatform = "LINUX";        
        browserPlatformM = 1.5;
}
else if (-1 != theagent.indexOf("hp_ux", 0)) {
        browserPlatform = "HP_UX";        
        browserPlatformM = 1.2;
}
else {
        browserPlatform = "NT";        
        browserPlatformM = 1.0;
}


//Determining what browser
if (-1 != theagent.indexOf("msie", 0)) {
        browserJava = "IE";        
        browserJavaM = 1.0;
}
else if (-1 != theagent.indexOf("gecko", 0)) {
        browserJava = "GECKO";        
        browserJavaM = 1.0;
}
else if (-1 != theagent.indexOf("opera", 0)) {
        browserJava = "OPERA";        
        browserJavaM = 1.0;
}
else {
        browserJava = "NETSCAPE";        
        browserJavaM = 1.0;
}

var contextRoot = document.URL;

//determine what Language
if (contextRoot.search("/zh_CN/") != -1) {
    browserLocale = "zh_CN";        
    browserLocaleM = 1.33;
}

else if (contextRoot.search("/zh_TW/") != -1) {
        browserLocale = "zh_TW";        
        browserLocaleM = 1.33;
}

else if (contextRoot.search("/fr/") != -1) {
        browserLocale = "fr";        
        browserLocaleM = 1.0;
}

else if (contextRoot.search("/de/") != -1) {
        browserLocale = "de";        
        browserLocaleM = 1.0;
}

else if (contextRoot.search("/en/") != -1) {
        browserLocale = "en";        
        browserLocaleM = 1.0;
}

else if (contextRoot.search("/it/") != -1) {
        browserLocale = "it";        
        browserLocaleM = 1.0;
}
		
else if (contextRoot.search("/ja/") != -1) {
        browserLocale = "ja";        
        browserLocaleM = 1.1;
}

else if (contextRoot.search("/ko/") != -1) {
        browserLocale = "ko";        
        browserLocaleM = 1.1;
}

else if (contextRoot.search("/pt_BR/") != -1) {
        browserLocale = "pt_BR";        
        browserLocaleM = 1.0;  
}

else if (contextRoot.search("/es/") != -1) {
        browserLocale = "es";        
        browserLocaleM = 1.0;  
}


else if (contextRoot.search("/cs/") != -1) {
        browserLocale = "cs";        
        browserLocaleM = 1.0;  
}


else if (contextRoot.search("/hu/") != -1) {
        browserLocale = "hu";        
        browserLocaleM = 1.0;  
}


else if (contextRoot.search("/pl/") != -1) {
        browserLocale = "pl";        
        browserLocaleM = 1.0;  
}

else if (contextRoot.search("/ru/") != -1) {
        browserLocale = "ru";        
        browserLocaleM = 1.0;  
}

else {
        browserLocale = "en";        
        browserLocaleM = 1.0;  
}


multiplier = browserLocaleM * browserJavaM * browserPlatformM;

document.write("<style type=\"text/css\">");

document.write("/* The agent is "+theagent+" */");
document.write("/* The browser agent is "+browserJava+" */");
document.write("/* The agent locale is "+browserLocale+" */");
document.write("/* The agent OS is "+browserPlatform+" */");
document.write("* The font size multiplier is "+multiplier+" */");


/***********   SamplesMaster.css *************/


/* classes for the BANNER */

document.write("BODY.banner    { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0; padding: 0; width: 100%; }");
document.write("TABLE.banner   { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0; padding: 0; width: 100%; }");


/* classes for the CONTENT area */
document.write("BODY		{ font-size: " + 7.5*multiplier + "pt; background-color: #FFFFFF; font-family: Helvetica, Arial, Verdana, sans-serif; line-height: 1.5em; margin-left: 0px; letter-spacing: .01em; border-left: #cccccc 1px solid; }");
if (browserJava == "GECKO") {
   document.write("BODY		{ border-left: #cccccc 0px solid; }");
}
document.write("P       { font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em;}");
document.write("TD	{ font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; }");
document.write("LABEL       { color: #000000; letter-spacing: .02em; font-weight: bold; }");
document.write("TH			{ font-weight: bold; font-size: " + 7.0*multiplier + "pt; color: #000000; background-color: #96D1FF; padding-top: 4px; padding-bottom: 4px; padding-left: 6px; padding-right: 12px; text-align: left; border: 1 solid #333333; }");
document.write("B           { font-size: " + 7.5*multiplier + "pt; padding-left: 2px; padding-right: 2px; letter-spacing: .03em; }");
document.write("LEGEND { color: #336699; font-weight: bold; font-size: " + 7.5*multiplier + "pt;}");
document.write("FIELDSET { border: 1px solid #E7E7E7; width: 80%; margin-left: 1.5em; padding-top: 0; }");
if (browserJava != "GECKO") {
   document.write("FIELDSET {  padding-bottom: 7px; padding-left: 5px; }");
}

//Double Byte Charcter Sets
if (browserLocale == "zh_cn" || browserLocale == "zh_tw" || browserLocale == "ja" || browserLocale == "ko") {
   //None of these styles existed in the SamplesMaster.css
   document.write("TABLE { font-size: 100%; width: auto; height: auto; display: block; border: 0; margin-top: 3em; margin: 2px; padding: 2px; float: none; clear: none; }");
   document.write("P    { margin-top: 8px; margin-bottom: 0; font-size: 100%; }");
   document.write("BODY.sampcont { margin-top : 10px; margin-bottom : 0px; margin-left : 15px; font-size: 85%; }");
   document.write("BODY.topmenu  { background-color: #000000; color: #99ccff; vertical-align: top; margin: 1px 3px; font-size: 100%; }");
}


/* classes pertaining to the MENUBAR underneath the banner */
document.write(".top-navigation         { color: #FFFFFF; font-size: " + 7.5*multiplier + "pt; background-color:#6B7A92; padding-left: 10px; padding-right: 5px; }");
document.write(".top-nav-item           { color: #ffffff; font-weight:bold; text-decoration: none; font-size: " + .75*multiplier + "em; line-height: 1.5em; }");
if (browserJava == "GECKO") {
   document.write(".top-nav-item {font-size: .85em; line-height: 1.7em}");
}
document.write("a.top-nav-item          { color: #FFFFFF; font-weight:bold; }");
document.write("a:active.top-nav-item   { color: #FFFFFF; }");
document.write("a:hover.top-nav-item    { text-decoration: underline; }");


/* classes pertaining to the NAVIGATION TREE in the leftmost frame */
document.write("BODY.sampnav  { color: #000000; background-color: #ffffff; font-size: " + 7.0*multiplier + "pt; border-right: 1px ridge black; margin-right: 0px; }");
document.write("TABLE.sampnav { margin-top: 0; margin-left: 0; margin: 0; padding-top: 2px; padding-bottom: 2px; }");
document.write("TD.sampnav    { font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; color: #000000; }");
document.write("H2.sampnav    { font-size: " + 8.0*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; font-weight: bold; margin-bottom: 0; color: #000000}");
document.write("H3.sampnav    { font-size: " + 8.0*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; font-weight: bold; margin-bottom: 0;}");

/*styles headings in technotes, configrun, buildit & wsadbuildit
*/
document.write("A.sampnav            { font-size: " + 8.0*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; color: #000000; text-decoration: none; font-size: " + 8.0*multiplier + "pt; }");
document.write("A:link.sampnav       { color: #000000; }");
document.write("A:visited.sampnav    { color: #000000; }");
document.write("A:active.sampnav     { color: #9933cc; }");
document.write("A:hover.sampnav      { color: #000000; text-decoration: underline; }");
//Double Byte Charcter Sets
if (browserLocale == "zh_cn" || browserLocale == "zh_tw" || browserLocale == "ja" || browserLocale == "ko") {
   document.write("BODY.sampnav  { font-size: 100%; }");
   document.write("TABLE.sampnav { padding: 0; }");
   document.write("TR.sampnav    { color: #FFFFFF }");
   document.write("TH.sampnav	 { font-weight: bold; text-align: left; font-color: #000000; text-align: center; color: #FFFFF; }");
   document.write("TD.sampnav    { font-size: 85%; color: #000000; }");
   document.write("H1.sampnav    { color: #000000; font-size: 10pt; }");
   document.write("H2.sampnav    { font-size: 100%; }");
}

						
/* Trademark Properties */
//This style may not be used anymore
document.write("a.trade	{ font-size: " + 8*multiplier + "pt; }");
				
/* Header Properties */
document.write("H1.samp { text-align: left; border: #000000 1px solid; FONT-SIZE: " + 7.5*multiplier + "pt; COLOR: #ffffff; FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif; BACKGROUND-COLOR: #5495d5; }");
document.write("H1              { font-size: " + 13*multiplier + "pt; font-weight: bold; margin-bottom: 0; }");
document.write("H2			    { font-size: " + 8.0*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; font-weight: bold; margin-bottom: 0; }");
document.write("H3			    { font-size: " + 8.0*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; font-weight: bold; font-style: italic; margin-bottom: 0; }");
document.write("H4			    { font-size: " + 11*multiplier + "pt; font-weight: bold; margin-bottom: 0; }");
document.write("H5			    { font-size: " + 11*multiplier + "pt; font-weight: bold; font-style: italic; margin-bottom: 0; }");


/* Anchor Properties */
document.write("A			    { text-decoration: none; }");
document.write("A:link			{ color: #006699; }");
document.write("A:visited		{ color: #996699; }");
document.write("A:active		{ color: #006699; }");
document.write("A:hover			{ color: #006699; text-decoration: underline; }");
		  
document.write("A.submenu           { color: #006699; text-decoration: none; }");
document.write("A:link.submenu      { color: #006699; }");
document.write("A:visited.submenu   { color: #996699; }");
document.write("A:active.submenu    { color: #006699; }");
document.write("A:hover.submenu     { text-decoration: underline; color: #006699; }");
 		
document.write("A.activesubmenu	    { color: #000000; text-decoration:none; font-weight: bold; }");

document.write("A.title           {	text-decoration: none; color: #ffffff; margin-top: auto; }");
document.write("A:link.title	  { color: #ffffff;  }");
document.write("A:visited.title   { color: #ffffff;  }");
document.write("A:active.title    { color: #ffffff;  }");
document.write("A:hover.title	  { color: #ffffff; text-decoration: none; }");

document.write("A.breadcrumb          { text-decoration: none; color: #666666; margin-top: auto; }");
document.write("A:link.breadcrumb	  { color: #666666; }");
document.write("A:visited.breadcrumb  { color: #666666; }");
document.write("A:active.breadcrumb   { color: #666666; }");
document.write("A:hover.breadcrumb    { color: #666666; text-decoration: none; }");


/* List Properties */
document.write("OL	{ text-align: left; margin-top: 0; margin-bottom: 6px; list-style-type: decimal; }");
document.write("OL OL		{ list-style-type: lower-alpha; }");
document.write("UL	{ text-align: left; margin-top: 0; margin-bottom: 6px; list-style-type: disc; }");
document.write("DL			{ padding: 0; margin: 0; margin-top: 6px; margin-bottom: 6px; margin-left: 23px; list-style-type: none; }");
document.write("DT			{ padding: 0; margin: 0; margin-top: 6px; margin-bottom: 6px; list-style-type: none; font-weight: bold; }");
document.write("DD			{ padding: 0; margin: 0; margin-top: 6px; margin-bottom: 6px; list-style-type: none; text-indent: 0; }");
document.write("LI			{ font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; margin-bottom: 3px; }");


/* TD Properties */
document.write("TD.breadcrumb    { color: #999999; font-size: " + 8.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; border-bottom: #cccccc 1px solid; }");
document.write("TD.sampcont   { color: #000000; font-size: medium; }");
document.write("TD.sampnav    { font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; color: #000000; }");
document.write("TD.submenu	  { font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; color: #000000; }");
document.write("TD.topmenu    { font-size: " + 7.5*multiplier + "pt; text-decoration: none; color: #000000; margin-top: auto; }");



/* Text Area Properties */
document.write("BLOCKQUOTE		{ font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; display: block; margin: 1.17em 0; margin-left: 20px; margin-right: 20px; }");
document.write("CAPTION			{ text-align: left; font-style: italic; }");
document.write("EM, CITE		{ font-style: italic; }");
document.write("SMALL			{ font-size: " + 8*multiplier + "pt; }");
document.write("PRE         { font-family: monospace, courier; font-weight: normal; font-size: " + 8.5*multiplier + "pt; color: #000000; white-space: pre; margin-top: 4px; margin-bottom: 4px; }");
document.write("VAR			    { font-size: " + 8.0*multiplier + "pt; }");
document.write("CODE			{ font-size: " + 8.0*multiplier + "pt; }");
document.write("SPAN			{ font-size: " + 10*multiplier + "pt; }");
document.write("SPAN.white		{ color: white; }");
document.write("BODY.sampcont { margin-top: 10px; margin-bottom : 0px; margin-left : 15px; font-size: " + 7.5*multiplier + "pt; }");


/* Text Formatting Properties */
document.write("STRONG			{ font-size: " + 7.5*multiplier + "pt; font-family: Verdana, Helvetica, sans-serif; line-height: 1.2em; font-weight: bold; }");
document.write("TT			    { font-family: Courier; }");
document.write("SAMP        { font-family: monospace, courier; font-weight: normal; font-size: " + 8*multiplier + "pt; color: #000000; }");
document.write("SUP			    { vertical-align: super; font-size: " + 8*multiplier + "pt; }");
document.write("CENTER			{ text-align: center; }");
document.write(".heading1	    { font-size: " + 16*multiplier + "pt; font-weight: bold; line-height: 1.2ex; background-color: #C0C0C0; letter-spacing: normal; text-align: left; width: auto; height: auto; }");
document.write("#section        { font-weight: bold; }");
document.write("#sample-list    { margin-left: 10px; }");
document.write(".afterp         { margin-bottom: 3px; }");                          
document.write("LI.gallery      { margin-bottom: 3px; }");


/* STYLE for Samples HTML Menus */
document.write("TH.pagemenu { font-weight: bold; text-align: center; color: #FFFFFF; background-color: #9999ff; }");


 /* Tags for Sample's JSP pages */
document.write("H1.sampjsp  { font-size: " + 16*multiplier + "pt; font-weight: bold; line-height: 2ex; }");


document.write("</style>");
