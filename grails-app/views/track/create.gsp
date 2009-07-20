<%@ page import="dukebox.Track" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="layout" content="main"/>
		<title>Create Track</title>
	</head>
	<body>
		<div class="nav">
			<span class="menuButton"><a class="home" href="${resource(dir: '')}">Home</a></span>
			<span class="menuButton"><g:link class="list" action="list">Track List</g:link></span>
		</div>
		<div class="body">
			<h1>Create Track</h1>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${command}">
				<div class="errors">
					<g:renderErrors bean="${command}" as="list"/>
				</div>
			</g:hasErrors>
			<g:form action="save" method="post" enctype="multipart/form-data">
				<div class="dialog">
					<table>
						<tbody>

							<tr class="prop">
								<td valign="top" class="name">
									<label for="title">Title:</label>
								</td>
								<td valign="top" class="value ${hasErrors(bean: command, field: 'title', 'errors')}">
									<input type="text" id="title" name="title" value="${fieldValue(bean: command, field: 'title')}"/>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name">
									<label for="artist">Artist:</label>
								</td>
								<td valign="top" class="value ${hasErrors(bean: command, field: 'artist', 'errors')}">
									<input type="text" id="artist" name="artist" value="${fieldValue(bean: command, field: 'artist')}"/>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name">
									<label for="album">Album:</label>
								</td>
								<td valign="top" class="value ${hasErrors(bean: command, field: 'album', 'errors')}">
									<input type="text" id="album" name="album" value="${fieldValue(bean: command, field: 'album')}"/>
								</td>
							</tr>

							<tr class="prop">
								<td valign="top" class="name">
									<label for="album">File:</label>
								</td>
								<td valign="top" class="value ${hasErrors(bean: command, field: 'file', 'errors')}">
									<input type="file" id="file" name="file"/>
								</td>
							</tr>

						</tbody>
					</table>
				</div>
				<div class="buttons">
					<span class="button"><input class="save" type="submit" value="Create"/></span>
				</div>
			</g:form>
		</div>
	</body>
</html>
