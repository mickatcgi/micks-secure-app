<!-- calling this _displayWrapper.gsp caused the spaHome1.html page to show garbage too.
     The goal was only to alter the display of show.gsp.
-->

<li class="fieldcontain">
    <span id="${label}" class="${label}"><g:message code="${label}" default="${label}" /></span>
    <span class="property-value" aria-labelledby="${label}">
        <g:fieldValue bean="${project}" field="${property}"/>
    </span>
</li>