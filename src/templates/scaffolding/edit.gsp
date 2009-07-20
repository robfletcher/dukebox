<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="${domainClass.propertyName}.edit" default="Edit ${className}" /></title>
    </head>
    <body>
        <g:applyLayout name="menu">
            <li><g:link class="list" action="list"><g:message code="${domainClass.propertyName}.list" default="${className} List" /></g:link></li>
            <li><g:link class="create" action="create"><g:message code="${domainClass.propertyName}.new" default="New ${className}" /></g:link></li>
        </g:applyLayout>
        <div class="body">
            <h1><g:message code="${domainClass.propertyName}.edit" default="Edit ${className}" /></h1>
            <g:if test="\${flash.message}">
            	<div class="message"><g:message code="\${flash.message}" args="\${flash.args}" default="\${flash.defaultMessage}" /></div>
            </g:if>
            <g:hasErrors bean="\${${propertyName}}">
				<div class="errors">
					<g:renderErrors bean="\${${propertyName}}" as="list" />
				</div>
            </g:hasErrors>
            <g:form method="post" <%= multiPart ? " enctype=\"multipart/form-data\"" : "" %>>
                <g:hiddenField name="id" value="\${${propertyName}?.id}" />
                <g:hiddenField name="version" value="\${${propertyName}?.version}" />
                <div class="dialog">
                    <fieldset>
                        <legend><g:message code="${domainClass.propertyName}.edit.legend" default="Update ${className} Details"/></legend>
                        <%  excludedProps = ["version",
                                             "id",
                                             "dateCreated",
                                             "lastUpdated",
                                             Events.ONLOAD_EVENT,
                                             Events.BEFORE_INSERT_EVENT,
                                             Events.BEFORE_UPDATE_EVENT,
                                             Events.BEFORE_DELETE_EVENT]
                            props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
                            Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                            props.each { p ->
                                cp = domainClass.constrainedProperties[p.name]
                                display = (cp ? cp.display : true)
                                optional = (cp ? cp.nullable || (p.type == String && cp.blank) : true)
                                if (display) { %>
                        <div class="prop<%if(!optional){%> mandatory<%}%> \${hasErrors(bean: ${propertyName}, field: '${p.name}', 'error')}">
                            <label for="${p.name}">
                                <g:message code="${domainClass.propertyName}.${p.name}" default="${p.naturalName}" />
                                <%if(!optional){%><span class="indicator">*</span><%}%>
                            </label>
                            ${renderEditor(p)}
                        </div>
                        <%  }   } %>
                    </fieldset>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="\${message(code: 'update', 'default': 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="\${message(code: 'delete', 'default': 'Delete')}" onclick="return confirm('\${message(code: 'delete.confirm', 'default': 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
