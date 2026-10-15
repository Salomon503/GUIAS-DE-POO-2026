<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="ocupacion" class="com.example.guia11bean.OcupacionBean" scope="request"/>
<jsp:setProperty name="ocupacion" property="*"/>
<%
    String accion = request.getParameter("accion");
    boolean ok = false;
    String mensaje;

    if ("guardar".equals(accion)) {
        if (ocupacion.getIdOcupacion() > 0) {
            ok = ocupacion.actualizar();
            mensaje = ok ? "Ocupación actualizada correctamente." : ocupacion.getError();
        } else {
            ok = ocupacion.insertar();
            mensaje = ok ? "Ocupación registrada correctamente." : ocupacion.getError();
        }
    } else if ("eliminar".equals(accion)) {
        ok = ocupacion.eliminar();
        mensaje = ok ? "Ocupación eliminada correctamente." : ocupacion.getError();
    } else {
        mensaje = "Acción no válida.";
    }

    session.setAttribute("flashMsg", mensaje);
    session.setAttribute("flashTipo", ok ? "success" : "danger");
    response.sendRedirect("ocupaciones.jsp");
%>
