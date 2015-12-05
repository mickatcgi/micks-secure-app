<!doctype html>
<html lang="en" class="no-js" ng-app="micks-todos">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>

    <g:layoutHead/>
</head>
<body>
    <div id="grailsLogo" role="banner">
        <a href="http://grails.org">
            <asset:image src="grails_logo.png" alt="Grails"/>
        </a>
        <span style="float:right; font-size:80%; margin:5px;">
            <g:set var="user" value="${sec.username()}" />
            <g:if test="${user}">
                Signed in as: ${user}
                <g:link controller="logout">(logout)</g:link>
            </g:if>
            <g:else>
                User: anonymous
            </g:else>
        </span>
    </div>

    <g:layoutBody/>

    <div class="footer" role="contentinfo"></div>
    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>
</body>
</html>
