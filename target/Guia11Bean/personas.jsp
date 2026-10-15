<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.example.guia11bean.*" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="persona" class="com.example.guia11bean.PersonaBean" scope="request"/>
<jsp:useBean id="ocupacionBean" class="com.example.guia11bean.OcupacionBean" scope="request"/>
<%
    String titulo = "Personas";
    String activo = "personas";

    List<PersonaBean> personas = persona.listar();
    String errorBD = persona.getError();
    List<OcupacionBean> ocupaciones = ocupacionBean.listar();
    if (errorBD == null) {
        errorBD = ocupacionBean.getError();
    }

    boolean editando = false;
    String pEditar = request.getParameter("editar");
    if (pEditar != null && pEditar.matches("\\d{1,9}")) {
        editando = persona.cargar(Integer.parseInt(pEditar));
        if (!editando && errorBD == null) {
            errorBD = persona.getError();
        }
    }
    boolean sinOcupaciones = ocupaciones.isEmpty();
%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<% if (errorBD != null) { %>
<div class="alert alert-danger"><i class="fa-solid fa-triangle-exclamation me-2"></i><%= Util.h(errorBD) %></div>
<% } %>
<% if (sinOcupaciones && errorBD == null) { %>
<div class="alert alert-warning">
    Primero debe registrar al menos una ocupación.
    <a href="ocupaciones.jsp" class="alert-link">Ir a Ocupaciones</a>
</div>
<% } %>

<div class="row justify-content-center">
    <div class="col-12 col-lg-10">

        <div class="card border-0 shadow-sm">
            <div class="card-header <%= editando ? "bg-warning" : "bg-primary text-white" %> text-center">
                <h1 class="h3 mb-0">
                    <i class="fa-solid <%= editando ? "fa-user-pen" : "fa-user-plus" %> me-2"></i>
                    <%= editando ? "Editar persona #" + persona.getIdPersona() : "Gestión de personas" %>
                </h1>
            </div>
            <div class="card-body p-4">
                <form action="persona_controller.jsp" method="POST">
                    <input type="hidden" name="accion" value="guardar">
                    <input type="hidden" name="idPersona" value="<%= editando ? persona.getIdPersona() : 0 %>">
                    <div class="row g-3">
                        <div class="col-12 col-md-6">
                            <label for="nombre" class="form-label">Nombre de la persona</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-user"></i></span>
                                <input type="text" class="form-control" id="nombre" name="nombrePersona"
                                       maxlength="100" placeholder="Ingrese el nombre" required
                                       value="<%= editando ? Util.h(persona.getNombrePersona()) : "" %>">
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <label for="edad" class="form-label">Edad de la persona</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-calendar"></i></span>
                                <input type="number" class="form-control" id="edad" name="edadPersona"
                                       min="1" max="120" placeholder="Ingrese la edad" required
                                       value="<%= editando ? String.valueOf(persona.getEdadPersona()) : "" %>">
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <label for="telefono" class="form-label">Teléfono de la persona</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-phone"></i></span>
                                <input type="tel" class="form-control" id="telefono" name="telefonoPersona"
                                       pattern="\d{4}-\d{4}" maxlength="9" placeholder="0000-0000"
                                       title="Formato: 0000-0000" required
                                       value="<%= editando ? Util.h(persona.getTelefonoPersona()) : "" %>">
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <label for="sexo" class="form-label">Sexo de la persona</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-venus-mars"></i></span>
                                <select name="sexoPersona" id="sexo" class="form-select" required>
                                    <option value="" disabled <%= editando ? "" : "selected" %>>Seleccione una opción</option>
                                    <option value="Masculino" <%= editando && "Masculino".equals(persona.getSexoPersona()) ? "selected" : "" %>>Masculino</option>
                                    <option value="Femenino" <%= editando && "Femenino".equals(persona.getSexoPersona()) ? "selected" : "" %>>Femenino</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <label for="ocupacion" class="form-label">Ocupación de la persona</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-briefcase"></i></span>
                                <select name="idOcupacion" id="ocupacion" class="form-select" required>
                                    <% for (OcupacionBean o : ocupaciones) { %>
                                    <option value="<%= o.getIdOcupacion() %>"
                                        <%= editando && persona.getIdOcupacion() == o.getIdOcupacion() ? "selected" : "" %>>
                                        <%= Util.h(o.getOcupacion()) %></option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <label for="fecha" class="form-label">Fecha de nacimiento</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fa-solid fa-calendar-days"></i></span>
                                <input type="date" class="form-control" id="fecha" name="fechaNac" required
                                       value="<%= editando ? Util.h(persona.getFechaNac()) : "" %>">
                            </div>
                        </div>
                        <div class="col-12 text-center mt-4">
                            <button type="submit" class="btn btn-success px-5" <%= sinOcupaciones ? "disabled" : "" %>>
                                <i class="fa-solid fa-floppy-disk me-2"></i><%= editando ? "Actualizar persona" : "Guardar persona" %>
                            </button>
                            <% if (editando) { %>
                            <a href="personas.jsp" class="btn btn-outline-secondary px-5 ms-2">Cancelar</a>
                            <% } else { %>
                            <button type="reset" class="btn btn-outline-secondary px-5 ms-2">
                                <i class="fa-solid fa-eraser me-2"></i>Limpiar
                            </button>
                            <% } %>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="card border-0 shadow-sm mt-4">
            <div class="card-header bg-dark text-white">
                <h2 class="h5 mb-0"><i class="fa-solid fa-list me-2"></i>Lista de personas registradas</h2>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>Id</th><th>Nombre</th><th>Edad</th><th>Teléfono</th>
                            <th>Sexo</th><th>Ocupación</th><th>Fecha de nacimiento</th>
                            <th class="text-end">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% if (personas.isEmpty()) { %>
                        <tr><td colspan="8" class="text-center py-3 text-muted">No hay personas registradas.</td></tr>
                    <% } else {
                           for (PersonaBean p : personas) { %>
                        <tr>
                            <td><%= p.getIdPersona() %></td>
                            <td><%= Util.h(p.getNombrePersona()) %></td>
                            <td><%= p.getEdadPersona() %></td>
                            <td><%= Util.h(p.getTelefonoPersona()) %></td>
                            <td><%= Util.h(p.getSexoPersona()) %></td>
                            <td><%= Util.h(p.getNombreOcupacion()) %></td>
                            <td><%= Util.h(p.getFechaNac()) %></td>
                            <td class="text-end text-nowrap">
                                <a href="personas.jsp?editar=<%= p.getIdPersona() %>" class="btn btn-sm btn-outline-primary">
                                    <i class="fa-solid fa-pen"></i></a>
                                <form action="persona_controller.jsp" method="POST" class="d-inline"
                                      onsubmit="return confirm('¿Desea eliminar a esta persona?');">
                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="hidden" name="idPersona" value="<%= p.getIdPersona() %>">
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