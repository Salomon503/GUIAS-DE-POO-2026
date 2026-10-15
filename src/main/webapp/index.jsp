<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.example.guia11bean.*" %>
<jsp:useBean id="personaBean" class="com.example.guia11bean.PersonaBean" scope="request"/>
<jsp:useBean id="ocupacionBean" class="com.example.guia11bean.OcupacionBean" scope="request"/>
<%
    String titulo = "Inicio";
    String activo = "inicio";
    List<PersonaBean> personas = personaBean.listar();
    String errorBD = personaBean.getError();
    List<OcupacionBean> ocupaciones = ocupacionBean.listar();
    if (errorBD == null) {
        errorBD = ocupacionBean.getError();
    }
%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="text-center mb-4">
    <h1 class="h3"><i class="fa-solid fa-database me-2"></i>Mantenimiento de datos con JavaBeans</h1>
    <p class="text-muted mb-0">Guía 11 &middot; Programación Orientada a Objetos &middot; JSP y JavaBeans</p>
</div>

<% if (errorBD != null) { %>
<div class="alert alert-danger"><i class="fa-solid fa-triangle-exclamation me-2"></i><%= Util.h(errorBD) %></div>
<% } %>

<div class="row g-4 mb-4">
    <div class="col-md-6">
        <div class="card border-0 shadow-sm h-100">
            <div class="card-body text-center p-4">
                <i class="fa-solid fa-user fa-3x text-primary mb-3"></i>
                <h2 class="h4">Personas</h2>
                <p class="display-6 mb-3"><%= personas.size() %></p>
                <a href="personas.jsp" class="btn btn-primary"><i class="fa-solid fa-pen-to-square me-2"></i>Administrar personas</a>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card border-0 shadow-sm h-100">
            <div class="card-body text-center p-4">
                <i class="fa-solid fa-briefcase fa-3x text-success mb-3"></i>
                <h2 class="h4">Ocupaciones</h2>
                <p class="display-6 mb-3"><%= ocupaciones.size() %></p>
                <a href="ocupaciones.jsp" class="btn btn-success"><i class="fa-solid fa-pen-to-square me-2"></i>Administrar ocupaciones</a>
            </div>
        </div>
    </div>
</div>




<%@ include file="/WEB-INF/jspf/footer.jspf" %>