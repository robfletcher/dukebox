<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<title><g:layoutTitle default="Grails"/></title>
		<jawr:style src="/bundles/dukebox.css"/>
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
		<g:layoutHead/>
		<jawr:script src="/bundles/dukebox.js"/>
	</head>
	<body>
		<div id="spinner" class="spinner" style="display:none;">
			<img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/>
		</div>
		<div class="logo"><img src="${resource(dir: 'images', file: 'grails_logo.jpg')}" alt="Grails"/></div>
		<g:layoutBody/>
	</body>
</html>