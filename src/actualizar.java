import com.db4o.*;
import com.db4o.query.Query;

public class actualizar {

    public static void main(String[] args) {
        ponente p;
        ObjectSet res;

        // Crear conexión a la base de datos
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "congreso.db4o");
        try {
            // Query: Es la interfaz utilizada para construir consultas en DB4O.
            // Crea una nueva consulta vacía.
            Query cons = db.query();

            // Restringe la consulta a objetos de la clase ponente. Solo los objetos de este
            // tipo serán considerados en los resultados.
            cons.constrain(ponente.class);

            // Especifica que se está consultando el campo nif de los objetos de tipo
            // ponente. Filtra los resultados para que solo incluyan objetos cuyo campo nif
            // tenga el valor "11A".
            cons.descend("nif").constrain("11A");

            // Ejecuta la consulta y devuelve una lista de resultados (un objeto ObjectSet).
            res = cons.execute();

            // res.hasNext(): Verifica si hay más resultados en el conjunto.
            while (res.hasNext()) {
                p = (ponente) res.next(); // Obtiene el siguiente objeto de los resultados.
                p.setNombre("Antonio Prueba"); // Llama al método `setNombre()` del objeto `ponente` para modificar el nombre
                db.store(p); // Actualiza el objeto en la base de datos.
                System.out.println("Ponente actualizado");
            }

            System.out.println("Información de los ponentes");
            cons = db.query(); // Crea una nueva consulta vacía.
            cons.constrain(ponente.class); // Restringe la consulta a objetos de la clase Jefe. Solo los objetos de este
                                        // tipo serán considerados en los resultados.
            // Ejecuta la consulta y devuelve una lista de resultados (un objeto ObjectSet).
            res = cons.execute();
            while (res.hasNext()) {
                p = (ponente) res.next(); // Obtiene el siguiente objeto de los resultados.
                System.out.println(p); // Llama al método `visDatosEmpleados()` del objeto `Jefe` para mostrar la info
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            db.close();
        }

    }
}
