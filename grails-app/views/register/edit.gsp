<head>
	<meta name="layout" content="main"/>
	<title><g:message code="register.edit" default="Edit Profile"/></title>
</head>

<body>

	<g:applyLayout name="menu">
	</g:applyLayout>

	<div class="body">
		<h1><g:message code="register.edit" default="Edit Profile"/></h1>
	<%--	TODO: i18n of flash message --%>
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
					<legend><g:message code="register.edit.legend" default="Enter Updated Details"/></legend>

					<div class='prop ${hasErrors(bean: person, field: 'username', 'error')}'>
						<label for='username'><g:message code="user.username" default="Login Name"/></label>
						<input type="hidden" name='username' value="${person.username?.encodeAsHTML()}"/>
						<div style="margin:3px">${person.username?.encodeAsHTML()}</div>
					</div>

					<div class='prop mandatory ${hasErrors(bean: person, field: 'userRealName', 'error')}'>
						<label for='userRealName'>
							<g:message code="user.userRealName" default="Full Name"/>
							<span class="indicator">*</span>
						</label>
						<input type="text" name='userRealName' value="${person.userRealName?.encodeAsHTML()}"/>
					</div>

					<div class='prop ${hasErrors(bean: person, field: 'passwd', 'error')}'>
						<label for='passwd'><g:message code="user.passwd" default="Password"/></label>
						<input type="password" name='passwd' value=""/>
					</div>

					<div class='prop ${hasErrors(bean: person, field: 'passwd', 'error')}'>
						<label for='enabled'><g:message code="user.repasswd" default="Confirm Password"/></label>
						<input type="password" name='repasswd'/>
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
			</div>

			<div class="buttons">
				<span class="button"><g:actionSubmit class='save' value="Update"/></span>
			</div>

		</g:form>

	</div>
</body>
