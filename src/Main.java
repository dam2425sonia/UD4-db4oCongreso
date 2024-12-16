
//paquetes necesarios del API Db4o para Java
import java.io.File;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Main {

  public static void main(String[] args) {
    // Borramos la BBDD db4o cada vez
    File fichero = new File("congreso.db4o");
    fichero.delete();
    
    // Crear conexión a la base de datos
    ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "congreso.db4o");
    // La base de datos física es el fichero "congreso.db4o" almacenado en la
    // carpeta raíz del proyecto creado
    System.out.println("Conexión a la base de datos creada.");
    try {
      almacenarPonentes(db);
      System.out.println("Objetos guardados en la BBDDOO");
      consultar(db);
    } finally {
      db.close(); // cerrar la conexión a la base de datos
    }
    
  }

  // Método para almacenar datos en la Base de Objetos.
  public static void almacenarPonentes(ObjectContainer db) {
    // se crean cuatro objetos tipo alumno con valores asignados
    ponente p1 = new ponente("11A", "Antonio Camaco", "acamacho@gmail.es", 300);
    ponente p2 = new ponente("22B", "Isabel Pérez", "iperez@hotmail.es", 100);
    ponente p3 = new ponente("33C", "Ana Navarro", "anavarro@yahoo.com", 200);
    ponente p4 = new ponente("44D", "Pedro Sánchez", "psanchez@mixmail.com", 90);

    // Persistir Objetos: almacenamos los objetos con el método store()
    db.store(p1);
    db.store(p2);
    db.store(p3);
    db.store(p4);
  }

  public static void consultar(ObjectContainer db) {
    Query con;
    ponente h;
    ObjectSet cons;

    try {
      // Obtener todos los ponentes con  queryByExample
      System.out.println("\n\nVamos a visualizar los datos de todos los ponentes");

      /*
       * -> queryByExample(Object template):Es un método para realizar consultas en
       * DB4O mediante un "ejemplo".
       * Se pasa un objeto de ejemplo (en este caso, un objeto de la clase ponente)
       * con ciertos valores nulos o predeterminados.
       * DB4O devolverá todos los objetos que coincidan con este ejemplo.
       */

      ObjectSet res = db.queryByExample(new ponente(null,null,null,0));
      // Se crea un objeto de ejemplo de la clase ponente donde todos los atributos
      // están vacíos o inicializados al valor predeterminado.
      // Esto significa que se recuperarán todos los objetos de la clase ponente
      // almacenados en la base de datos.
      while (res.hasNext()) {
        ponente p = (ponente) res.next(); // Recupera el siguiente objeto del conjunto.
        System.out.println(p);// Llama a un método para mostrar información.
      }

      // Obtener todos los ponentes con query
      System.out.println("\n\nVisualizacion de los ponentes");

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
        System.out.println(h);// Llama a un método para mostrar información.
      }
      

    }  catch (Exception e) {
      // TODO: handle exception
      System.out.println(e);
    }finally {
      db.close();// Asegúrate de cerrar la conexión
    }

    
  }
}
