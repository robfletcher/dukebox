<g:set var="c" value="${grailsApplication.getArtefactByLogicalPropertyName('Controller', it)}"/>
<li class="controller <g:if test="${controllerName == c.logicalPropertyName}">selected</g:if>">
	<g:link controller="${c.logicalPropertyName}" class="${c.logicalPropertyName}">
		<g:message code="${c.logicalPropertyName}.link" default="${c.name}"/>
	</g:link>
</li>