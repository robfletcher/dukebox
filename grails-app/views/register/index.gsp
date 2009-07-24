<head>
	<meta name="layout" content="main"/>
	<title><g:message code="register.index" default="User Registration"/></title>
</head>

<body>

	<g:applyLayout name="menu">
	</g:applyLayout>

	<div class="body">
		<h1><g:message code="register.index" default="User Registration"/></h1>
		<g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${person}">
			<div class="errors">
				<g:renderErrors bean="${person}" as="list"/>
			</div>
		</g:hasErrors>

		<g:form action="save">
			<div class="dialog">
				<fieldset>
					<legend><g:message code="register.index.legend" default="Enter Registration Details"/></legend>

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

					<div class='prop mandatory ${hasErrors(bean: person, field: 'passwd', 'error')}'>
						<label for='enabled'>
							<g:message code="user.repasswd" default="Confirm Password"/>
							<span class="indicator">*</span>
						</label>
						<input type="password" name='repasswd'/>
					</div>

					<div class='prop mandatory ${hasErrors(bean: person, field: 'email', 'error')}'>
						<label for='email'>
							<g:message code="user.email" default="Email"/>
							<span class="indicator">*</span>
						</label>
						<input type="text" name='email' value="${person.email?.encodeAsHTML()}"/>
					</div>

					<div class='prop'>
						<label for='code'>
							<g:message code="register.captcha" default="Enter Code"/>
						</label>
						<input type="text" name="captcha" size="8"/>
						<img src="${createLink(controller: 'captcha', action: 'index')}" align="absmiddle"/>
					</div>

				</fieldset>
			</div>

			<div class="buttons">
				<span class="button"><g:submitButton class="save" name="create" value="${message(code: 'create', 'default': 'Create')}"/></span>
			</div>

		</g:form>
	</div>
</body>
