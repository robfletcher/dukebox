
<%@ page import="dukebox.Track" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="track.enterDetails" default="Track Information" /></title>
    </head>
    <body>
        <g:applyLayout name="menu">
            <li><g:link class="list" action="list"><g:message code="track.list" default="Track List" /></g:link></li>
        </g:applyLayout>
        <div class="body">
            <h1><g:message code="track.enterDetails" default="Track Information" /></h1>
            <g:if test="${flash.message}">
            	<div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
            </g:if>
            <g:hasErrors bean="${trackInstance}">
				<div class="errors">
					<g:renderErrors bean="${trackInstance}" as="list" />
				</div>
            </g:hasErrors>
            <g:form action="create" method="post" >
                <div class="dialog">
                    <fieldset>
                        <legend><g:message code="track.enterDetails.legend" default="Enter Track Details"/></legend>
                        
                        <div class="prop mandatory ${hasErrors(bean: trackInstance, field: 'title', 'error')}">
                            <label for="title">
                                <g:message code="track.title" default="Title" />
                                <span class="indicator">*</span>
                            </label>
                            <input type="text" id="title" name="title" value="${fieldValue(bean:trackInstance,field:'title')}"/>
                        </div>
                        
                        <div class="prop mandatory ${hasErrors(bean: trackInstance, field: 'artist', 'error')}">
                            <label for="artist">
                                <g:message code="track.artist" default="Artist" />
								<span class="indicator">*</span>
                            </label>
                            <input type="text" id="artist" name="artist" value="${fieldValue(bean:trackInstance,field:'artist')}"/>
                        </div>
                        
                        <div class="prop ${hasErrors(bean: trackInstance, field: 'album', 'error')}">
                            <label for="album">
                                <g:message code="track.album" default="Album" />
                                
                            </label>
                            <input type="text" id="album" name="album" value="${fieldValue(bean:trackInstance,field:'album')}"/>
                        </div>
                        
                        <div class="prop ${hasErrors(bean: trackInstance, field: 'trackNo', 'error')}">
                            <label for="trackNo">
                                <g:message code="track.trackNo" default="Track No" />
                                
                            </label>
                            <input type="text" id="trackNo" name="trackNo" value="${fieldValue(bean:trackInstance,field:'trackNo')}" />
                        </div>
                        
                        <div class="prop ${hasErrors(bean: trackInstance, field: 'year', 'error')}">
                            <label for="year">
                                <g:message code="track.year" default="Year" />
                                
                            </label>
                            <input type="text" id="year" name="year" value="${fieldValue(bean:trackInstance,field:'year')}" />
                        </div>
                        
                    </fieldset>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="next" class="save" value="${message(code: 'update', 'default': 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
