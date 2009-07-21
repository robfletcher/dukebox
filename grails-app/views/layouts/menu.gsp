<div class="nav">
    <ul>
        <li><a class="home" href="${resource(dir: '')}">Home</a></li>
        <g:each var="c" in="${grailsApplication.controllerClasses}">
            <li class="controller <g:if test="${controllerName == c.logicalPropertyName}">selected</g:if>"><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></li>
        </g:each>
        <g:layoutBody/>
    </ul>
</div>
