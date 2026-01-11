package Config;

public interface Parametros {
    // Para desarrollo local, usa localhost
    // Para Railway, usará las variables de entorno
    String URL = System.getenv("DATABASE_URL") != null 
        ? System.getenv("DATABASE_URL") 
        : "jdbc:mysql://localhost:3307/FutbolRetro";
    
    String DRIVER = "com.mysql.cj.jdbc.Driver"; // Actualizado para MySQL 8.x
    
    String USER = System.getenv("DB_USER") != null 
        ? System.getenv("DB_USER") 
        : "root";
    
    String CLAVE = System.getenv("DB_PASSWORD") != null 
        ? System.getenv("DB_PASSWORD") 
        : "";
}
