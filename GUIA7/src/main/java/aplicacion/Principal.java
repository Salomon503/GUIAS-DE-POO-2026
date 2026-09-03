package aplicacion;

import modelos.AlumnoDAO;
import modelos.MateriaDAO;
import modelos.AlumnoMateriaDAO;
import javax.swing.JOptionPane;

/**
 * Clase principal: muestra un menu con JOptionPane y delega cada
 * operacion a la clase DAO correspondiente (mismo enfoque de
 * "clase Principal + new NombreClase()" usado en la guia).
 */
public class Principal {

    public static void main(String[] args) throws Exception {

        AlumnoDAO alumnoDAO = new AlumnoDAO();
        MateriaDAO materiaDAO = new MateriaDAO();
        AlumnoMateriaDAO matriculaDAO = new AlumnoMateriaDAO();

        String[] opciones = {
            "1. Crear Alumno",
            "2. Listar Alumnos",
            "3. Actualizar Alumno",
            "4. Eliminar Alumno",
            "5. Crear Materia",
            "6. Listar Materias",
            "7. Actualizar Materia",
            "8. Eliminar Materia",
            "9. Matricular Alumno en Materia",
            "10. Reporte: Materias por Alumno",
            "11. Salir"
        };

        int opcion = 0;
        do {
            String seleccion = (String) JOptionPane.showInputDialog(null,
                    "Seleccione una opcion:", "Menu Principal - Gestion Academica",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == null) break; // el usuario cerro el dialogo

            opcion = Integer.parseInt(seleccion.substring(0, seleccion.indexOf(".")));

            switch (opcion) {
                case 1: alumnoDAO.crear(); break;
                case 2: alumnoDAO.listar(); break;
                case 3: alumnoDAO.actualizar(); break;
                case 4: alumnoDAO.eliminar(); break;
                case 5: materiaDAO.crear(); break;
                case 6: materiaDAO.listar(); break;
                case 7: materiaDAO.actualizar(); break;
                case 8: materiaDAO.eliminar(); break;
                case 9: matriculaDAO.matricular(); break;
                case 10: matriculaDAO.reporteMateriasPorAlumno(); break;
                case 11: JOptionPane.showMessageDialog(null, "Hasta luego!"); break;
                default: break;
            }
        } while (opcion != 11);
    }
}
