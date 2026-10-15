<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.example.guia11bean.*" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="ocupacion" class="com.example.guia11bean.OcupacionBean" scope="request"/>
<%
    String titulo = "Ocupaciones";
    String activo = "ocupaciones";

    List<OcupacionBean> lista = ocupacion.listar();
    String errorBD = ocupacion.getError();

    boolean editando = false;
    String pEditar = request.getParameter("editar");
    if (pEditar != null && pEditar.matches("\\d{1,9}")) {
        editando = ocupacion.cargar(Integer.parseInt(pEditar));
        if (!editando && errorBD == null) {
            errorBD = ocupacion.getError();
        }
    }
%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<% if (errorBD != null) { %>
<div class="alert alert-danger"><i class="fa-solid fa-triangle-exclamation me-2"></i><%= Util.h(errorBD) %></div>
<% } %>

<div class="row g-4">
    <div class="col-12 col-lg-4">
        <div class="card border-0 shadow-sm">
            <div class="card-header <%= editando ? "bg-warning" : "bg-success text-white" %>">
                <h1 class="h5 mb-0">
                    <i class="fa-solid <%= editando ? "fa-pen" : "fa-plus" %> me-2"></i>
                    <%= editando ? "Editar ocupación #" + ocupacion.getIdOcupacion() : "Nueva ocupación" %>
                </h1>
            </div>
            <div class="card-body">
                <form action="ocupacion_controller.jsp" method="POST">
                    <input type="hidden" name="accion" value="guardar">
                    <input type="hidden" name="idOcupacion" value="<%= editando ? ocupacion.getIdOcupacion() : 0 %>">
                    <div class="mb-3">
                        <label for="ocupacion" class="form-label">Nombre de la ocupación</label>
                        <input type="text" class="form-control" id="ocupacion" name="ocupacion"
                               maxlength="50" required placeholder="Ej. Ingeniero"
                               value="<%= editando ? Util.h(ocupacion.getOcupacion()) : "" %>">
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-success">
                            <i class="fa-solid fa-floppy-disk me-2"></i><%= editando ? "Actualizar" : "Guardar" %>
                        </button>
                        <% if (editando) { %>
                        <a href="ocupaciones.jsp" class="btn btn-outline-secondary">Cancelar</a>
                        <% } %>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-12 col-lg-8">
        <div class="card border-0 shadow-sm">
            <div class="card-header bg-dark text-white">
                <h2 class="h5 mb-0"><i class="fa-solid fa-list me-2"></i>Lista de ocupaciones</h2>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>Id</th>
                            <th>Ocupación</th>
                            <th class="text-center">Personas</th>
                            <th class="text-end">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% if (lista.isEmpty()) { %>
                        <tr><td colspan="4" class="text-center py-3 text-muted">No hay ocupaciones registradas.</td></tr>
                    <% } else {
                           for (OcupacionBean o : lista) { %>
                        <tr>
                            <td><%= o.getIdOcupacion() %></td>
                            <td><%= Util.h(o.getOcupacion()) %></td>
                            <td class="text-center"><span class="badge text-bg-secondary"><%= o.getTotalPersonas() %></span></td>
                            <td class="text-end">
                                <a href="ocupaciones.jsp?editar=<%= o.getIdOcupacion() %>" class="btn btn-sm btn-outline-primary">
                                    <i class="fa-solid fa-pen"></i></a>
                                <form action="ocupacion_controller.jsp" method="POST" class="d-inline"
                                      onsubmit="return confirm('¿Desea eliminar esta ocupación?');">
                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="hidden" name="idOcupacion" value="<%= o.getIdOcupacion() %>">
                                    <button type="submit" class="btn btn-sm btn-outline-danger"><i class="fa-solid fa-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    <%     }
                       } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
