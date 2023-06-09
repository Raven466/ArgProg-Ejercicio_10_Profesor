import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Materia> materias = new ArrayList<>();

        // Agrego Matemáticas I
        Materia matematicas1 = new Materia("Matemática I");
        materias.add(matematicas1);

        // Agrego Matemáticas II
        Materia matematicas2 = new Materia("Matemática II");
        matematicas2.correlativas.add(matematicas1);
        materias.add(matematicas2);

        // Agrego Matemáticas III
        Materia matematicas3 = new Materia("Matemática III");
        matematicas3.correlativas.add(matematicas2);
        materias.add(matematicas3);

        // Agrego Programación I
        Materia programacion1 = new Materia("Programación I");
        materias.add(programacion1);

        // Agrego Programación II
        Materia programacion2 = new Materia("Programación II");
        programacion2.correlativas.add(programacion1);
        materias.add(programacion2);

        // Agrego Programación III
        Materia programacion3 = new Materia("Programación III");
        programacion3.correlativas.add(programacion2);
        materias.add(programacion3);

        // Agrego Base de datos I
        Materia baseDeDatos1 = new Materia("Base de datos I");
        materias.add(baseDeDatos1);

        // Agrego Base de datos II
        Materia baseDeDatos2 = new Materia("Base de datos II");
        baseDeDatos2.correlativas.add(baseDeDatos1);
        baseDeDatos2.correlativas.add(programacion1);
        materias.add(baseDeDatos2);

        // Agrego Base de datos III
        Materia baseDeDatos3 = new Materia("Base de datos III");
        baseDeDatos3.correlativas.add(baseDeDatos2);
        baseDeDatos3.correlativas.add(programacion2);
        materias.add(baseDeDatos3);

        List<Alumno> alumnos = new ArrayList<>();

        Alumno joseRodriguez = new Alumno("José Rodriguez", "001");
        alumnos.add(joseRodriguez);

        Alumno vanesaSosa = new Alumno("Vanesa Sosa", "002");
        vanesaSosa.materiasAprobadas.add(matematicas1);
        alumnos.add(vanesaSosa);

        Alumno robertoGomez = new Alumno("Roberto Gomez", "004");
        robertoGomez.materiasAprobadas.add(matematicas1);
        robertoGomez.materiasAprobadas.add(matematicas2);
        robertoGomez.materiasAprobadas.add(matematicas3);
        robertoGomez.materiasAprobadas.add(programacion1);
        robertoGomez.materiasAprobadas.add(baseDeDatos1);
        alumnos.add(robertoGomez);

//        List<Inscripcion> inscripciones = cargarInscripciones(materias, alumnos);



        /*
        Lista<Clase1> -> Lista<Clase2>
        Comparar con Lista<Clase3> -> Clase2

        Clase1 : Ronda      -> Alumno
        Clase2 : Partido    -> Materia
        Clase3 : Pronostico -> Profesor
         */

        // Cuantos alumnos tuvo cada profesor

        List<Profesor> profesores = new ArrayList<>();
        profesores.add(new Profesor("Pepe", "Matemática II", 2));
        profesores.add(new Profesor("Roberto", "Matemática I", 2));
        profesores.add(new Profesor("Mariana", "Base de datos I", 2));
        profesores.add(new Profesor("Agustina", "Base de datos III", 2));
        profesores.add(new Profesor("Rogelio", "Programación I", 2));
        profesores.add(new Profesor("Francisco", "Programación III", 2));

        for (int p = 0; p < profesores.size(); p++) {
            Profesor esteProfesor = profesores.get(p);
            int sumaAlumnos = 0;
            for (int a = 0; a < alumnos.size(); a++) {
                Alumno esteAlumno = alumnos.get(a);
                for (int m = 0; m < esteAlumno.materiasAprobadas.size(); m++) {
                    Materia estaMateria = esteAlumno.materiasAprobadas.get(m);

                    if (esteProfesor.materia.equals(estaMateria.nombre)) {
                        sumaAlumnos++;
                    }
                }
            }
            System.out.println("El profesor " + esteProfesor.nombre + " tuvo " + sumaAlumnos + " alumnos.");
        }





        /*
Clase del 18/04:

if (quierenPresentar) {
    Presentan();
    if (estaTodoBien) {
        Aprueban();
    }
    else {
        Devolucion();
    }
}


         */




    }

    public static List<Inscripcion> cargarInscripciones(List<Materia> materias, List<Alumno> alumnos) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        try {
            String deDondeCargar = "src/inscripciones2.scv";
            for (String linea : Files.readAllLines(Paths.get(deDondeCargar))) {
                String[] lineaSeparada = linea.split(";");//  { "José Rodriguez","001","Programación I" }

                int posicionAlumno = -1;
                for (int i = 0; i < alumnos.size(); i++) {
                    Alumno alumno = alumnos.get(i);
                    if (alumno.nombre.equals(lineaSeparada[0])) {
                        posicionAlumno = i;
                    }
                }

                int posicionMateria = -1;
                for (int i = 0; i < materias.size(); i++) {
                    Materia materia = materias.get(i);
                    if (materia.nombre.equals(lineaSeparada[2])) {
                        posicionMateria = i;
                    }
                }

                if (posicionAlumno == -1) {
                    System.out.println(lineaSeparada[0] + "\t" + lineaSeparada[2] + "\t" + "No se encuentra el alumno");
                } else if (posicionMateria == -1) {
                    System.out.println(lineaSeparada[0] + "\t" + lineaSeparada[2] + "\t" + "No se encuentra la materia");
                } else {

                    Materia materia = materias.get(posicionMateria);
                    Alumno alumno = alumnos.get(posicionAlumno);

                    boolean elAlumnoPuedeCursarLaMateria = materia.puedeCursar(alumno);
                    if (elAlumnoPuedeCursarLaMateria) {
                        inscripciones.add(new Inscripcion(alumno, materia));
                        System.out.println(lineaSeparada[0] + "\t" + lineaSeparada[2] + "\t" + "Inscripción aprobada");
                    } else {
                        System.out.println(lineaSeparada[0] + "\t" + lineaSeparada[2] + "\t" + "Inscripción rechazada");
                    }
                }

            }

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo");
        }
        return inscripciones;
    }
}
