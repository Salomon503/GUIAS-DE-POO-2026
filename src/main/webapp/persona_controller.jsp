<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="persona" class="com.example.guia11bean.PersonaBean" scope="request"/>
<jsp:setProperty name="persona" property="*"/>
<%
    String accion = request.getParameter("accion");
    boolean ok = false;
    String mensaje;

    if ("guardar".equals(accion)) {
        if (persona.getIdPersona() > 0) {
            ok = persona.actualizar();
            mensaje = ok ? "Persona actualizada correctamente." : persona.getError();
        } else {
            ok = persona.insertar();
            mensaje = ok ? "Persona registrada correctamente." : persona.getError();
        }
    } else if ("eliminar".equals(accion)) {
        ok = persona.eliminar();
        mensaje = ok ? "Persona eliminada correctamente." : persona.getError();
    } else {
        mensaje = "Acción no válida.";
    }

    session.setAttribute("flashMsg", mensaje);
    session.setAttribute("flashTipo", ok ? "success" : "danger");
    response.sendRedirect("personas.jsp");
%>