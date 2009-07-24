<head>
	<meta name="layout" content="main"/>
	<title><g:message code="user.edit" default="Edit User"/></title>
</head>

<body>

	<g:applyLayout name="menu">
		<li><g:link class="list" action="list"><g:message code="user.list" default="User List"/></g:link></li>
		<li><g:link class="create" action="create"><g:message code="user.create" default="New User"/></g:link></li>
	</g:applyLayout>

	<div class="body">
		<h1><g:message code="user.edit" default="Edit User"/></h1>
		<g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${person}">
			<div class="errors">
				<g:renderErrors bean="${person}" as="list"/>
			</div>
		</g:hasErrors>

		<g:form>
			<input type="hidden" name="id" value="${person.id}"/>
			<input type="hidden" name="version" value="${person.version}"/>
			<div class="dialog">
				<fieldset>

					<div class='prop mandatory ${hasErrors(bean: person, field: 'username', 'error')}'>
						<label for='username'>
							<g:message code="user.username" default="Login Name"/>
							<span class="indicator">*</span>
						</label>
						<input type="text" name='username' value="${person?.username?.encodeAsHTML()}"/>
					</div>

					<div class='prop mandatory ${hasErrors(bean: person, field: 'userRealName', 'error')}'>
						<label for='userRealName'>
							<g:message code="user.userRealName" default="Full Name"/>
							<span class="indicator">*</span>
						</label>
						<input type="text" name='userRealName' value="${person.userRealName?.encodeAsHTML()}"/>
					</div>

					<div class='prop mandatory ${hasErrors(bean: person, field: 'passwd', 'error')}'>
						<label for='passwd'>
							<g:message code="user.passwd" default="Password"/>
							<span class="indicator">*</span>
						</label>
						<input type="password" name='passwd' value=""/>
					</div>

					<div class='prop ${hasErrors(bean: person, field: 'enabled', 'error')}'>
						<label for='enabled'><g:message code="user.enabled" default="Enabled"/></label>
						<g:checkBox name='emailShow' value="${person.enabled}"></g:checkBox>
					</div>

					<div class='prop mandatory ${hasErrors(bean: person, field: 'email', 'error')}'>
						<label for='email'>
							<g:message code="user.email" default="Email"/>
							<span class="indicator">*</span>
						</label>
						<input type="text" name='email' value="${person.email?.encodeAsHTML()}"/>
					</div>

					<div class='prop ${hasErrors(bean: person, field: 'emailShow', 'error')}'>
						<label for='emailShow'><g:message code="user.emailShow" default="Show Email"/></label>
						<g:checkBox name='emailShow' value="${person.emailShow}"></g:checkBox>
					</div>

				</fieldset>

				<fieldset>
					<legend><g:message code="user.edit.roles.legend" default="Assign Roles"/></legend>
					<g:each var="entry" in="${roleMap}">
						<div class="prop ${hasErrors(bean: person, field: 'authorities', 'errors')}">
							<label for="${entry.key.authority}">${entry.key.authority.encodeAsHTML()}</label>
							<g:checkBox name="${entry.key.authority}" value="${entry.value}"></g:checkBox>
						</div>
					</g:each>
				</fieldset>
			</div>

			<div class="buttons">
				<span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'update', 'default': 'Update')}"/></span>
				<span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'delete', 'default': 'Delete')}" onclick="return confirm('${message(code: 'delete.confirm', 'default': 'Are you sure?')}');"/></span>
			</div>

		</g:form>

	</div>
</body>
