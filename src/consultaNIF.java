import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class consultaNIF {
    public static void main(String[] args) {
        Query con;
        ponente h;
        ObjectSet cons;

        // Crear conexión a la base de datos
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "congreso.db4o");
    
        try {
            // Obtener todos los ponentes con queryByExample
            System.out.println("\n\nVamos a visualizar los datos de un ponente determinado por su nif");

            /*
             * -> queryByExample(Object template):Es un método para realizar consultas en
             * DB4O mediante un "ejemplo".
             * Se pasa un objeto de ejemplo (en este caso, un objeto de la clase ponente)
             * con ciertos valores nulos o predeterminados.
             * DB4O devolverá todos los objetos que coincidan con este ejemplo.
             */

            ponente busqueda = new ponente();
            busqueda.setNif("11A"); // Especifica el NIF del ponente que quieres buscar
 
            ObjectSet res = db.queryByExample(busqueda);
            // Se crea un objeto de ejemplo de la clase ponente donde todos los atributos
            // están vacíos o inicializados al valor predeterminado.
            // Esto significa que se recuperarán todos los objetos de la clase ponente
            // almacenados en la base de datos.
            while (res.hasNext()) {
                ponente p = (ponente) res.next(); // Recupera el siguiente objeto del conjunto.
                System.out.println(p.toString());// Llama a un método para mostrar información.
            }

            // Obtener todos los ponentes con query
            System.out.println("\n\nVisualizacion de todos los ponentes");

            // Query: Es la interfaz utilizada para construir consultas en DB4O.
            // Crea una nueva consulta vacía.
            con = db.query();

            // Restringe la consulta a objetos de la clase ponente. Solo los objetos de este
            // tipo serán considerados en los resultados.
            con.constrain(ponente.class);

            // Ejecuta la consulta y devuelve una lista de resultados (un objeto ObjectSet).
            cons = con.execute();
            while (cons.hasNext()) {
                h = (ponente) cons.next();
                System.out.println(h.toString());
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        } finally {
            db.close();// Asegúrate de cerrar la conexión
        }

    }
}
