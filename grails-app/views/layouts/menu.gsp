<div class="nav">
    <ul>
        <li><a class="home" href="${resource(dir: '')}"><g:message code="home.link" default="Home"/></a></li>
		<g:render template="/menuItem" bean="player"/>
		<g:ifAllGranted role="ROLE_USER">
			<g:render template="/menuItem" bean="track"/>
		</g:ifAllGranted>
		<g:ifAllGranted role="ROLE_ADMIN">
			<g:render template="/menuItem" bean="user"/>
		</g:ifAllGranted>
		<g:isLoggedIn>
			<g:render template="/menuItem" bean="logout"/>
		</g:isLoggedIn>
		<g:isNotLoggedIn>
			<g:render template="/menuItem" bean="login"/>
			<g:render template="/menuItem" bean="register"/>
		</g:isNotLoggedIn>
        <g:layoutBody/>
    </ul>
</div>
