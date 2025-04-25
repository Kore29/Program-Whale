package Dao;

import PageModelNew.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WhaleCSV implements WhaleDao {
    private static Path usuariosPath = Paths.get("files", "usuarios.csv");
    private static Path contenidoPath = Paths.get("files", "contenido.csv");
    private static Path amigosPath = Paths.get("files", "amigos.csv");

    // Metodo para inicializar archivos CSV con encabezados
    private void initializeCSVFiles() {
        try {
            // Crear directorio si no existe
            new File("files").mkdirs();

            // Inicializar archivo de usuarios
            if (!new File(usuariosPath.toString()).exists()) {
                writeToCSV(usuariosPath, "nombre,contrasena,email,creacion", "", false);
            }

            // Inicializar archivo de contenido
            if (!new File(contenidoPath.toString()).exists()) {
                writeToCSV(contenidoPath, "id_contenido,autor,creacion,multimedia,texto,likes,hashtag,id_referencia", "", false);
            }

            // Inicializar archivo de amigos
            if (!new File(amigosPath.toString()).exists()) {
                writeToCSV(amigosPath, "usuario,amigo", "", false);
            }
        } catch (Exception e) {
            System.out.println("Error al inicializar archivos CSV");
            e.printStackTrace();
        }
    }

    // Metodo generico para escribir en CSV
    private void writeToCSV(Path path, String header, String data, boolean append) {
        try {
            File file = new File(path.toString());
            boolean fileExists = file.exists();

            FileWriter writer = new FileWriter(file, append);
            BufferedWriter bw = new BufferedWriter(writer);

            // Escribir encabezado si el archivo está vacío o no existe
            if ((!fileExists || file.length() == 0) && header != null) {
                bw.write(header);
                bw.newLine();
            }

            if (!data.isEmpty()) {
                bw.write(data);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + path);
            e.printStackTrace();
        }
    }

    // Constructor
    public WhaleCSV() {
        initializeCSVFiles();
    }

    // Implementación de los metodos de WhaleDao

    @Override
    public void insertUsuario(Usuario usuario) {
        String data = String.join(",",
                usuario.getNombre(),
                usuario.getContrasena(),
                usuario.getEmail(),
                usuario.getCreacion()
        );
        writeToCSV(usuariosPath, null, data, true);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (campos[2].equals(email)) {
                    return new Usuario(
                            campos[0], // nombre
                            campos[1], // contrasena
                            campos[2], // email
                            campos[3], // creacion
                            getAmigos(campos[0]), // amigos
                            getUsuarioPublicaciones(campos[0]) // publicaciones
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios desde CSV");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (campos[0].equals(name)) {
                    return new Usuario(
                            campos[0], // nombre
                            campos[1], // contrasena
                            campos[2], // email
                            campos[3], // creacion
                            getAmigos(campos[0]), // amigos
                            getUsuarioPublicaciones(campos[0]) // publicaciones
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios desde CSV");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void changeName(Usuario usuario, String newName) {
        // Implementación compleja para CSV - necesitaríamos reescribir todo el archivo
        System.out.println("Cambiar nombre no implementado para CSV");
    }

    @Override
    public List<String> getAmigos(String nombre) {
        List<String> amigos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(amigosPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (campos[0].equals(nombre)) {
                    amigos.add(campos[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer amigos desde CSV");
            e.printStackTrace();
        }
        return amigos;
    }

    @Override
    public void insertAmigo(Usuario usuario, String amigo) {
        String data = String.join(",", usuario.getNombre(), amigo);
        writeToCSV(amigosPath, null, data, true);
    }

    @Override
    public void removeAmigo(Usuario usuario, String amigo) {
        // Implementación compleja para CSV - necesitaríamos reescribir todo el archivo
        System.out.println("Eliminar amigo no implementado para CSV");
    }

    @Override
    public void insertPublicacion(Publicacion publicacion) {
        String data = String.join(",",
                String.valueOf(publicacion.getId()),
                publicacion.getAutor(),
                publicacion.getCreacion(),
                publicacion.getMultimedia() != null ? publicacion.getMultimedia() : "",
                publicacion.getTexto(),
                String.valueOf(publicacion.getLikes()),
                publicacion.getHashtag(),
                "" // id_referencia vacío para publicaciones
        );
        writeToCSV(contenidoPath, null, data, true);
    }

    @Override
    public void updateLikes(int id) {
        // Implementación compleja para CSV - necesitaríamos reescribir todo el archivo
        System.out.println("Actualizar likes no implementado para CSV");
    }

    @Override
    public List<Publicacion> getSixPublicaciones(int page) {
        List<Publicacion> publicaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            int start = (page - 1) * 6;
            int end = start + 6;
            int current = 0;

            String line;
            while ((line = reader.readLine()) != null && current < end) {
                String[] campos = line.split(",");
                // Solo publicaciones (id_referencia vacío)
                if (campos[7].isEmpty()) {
                    if (current >= start) {
                        publicaciones.add(createPublicacionFromCSV(campos));
                    }
                    current++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer publicaciones desde CSV");
            e.printStackTrace();
        }
        return publicaciones;
    }

    @Override
    public List<Publicacion> getFilterPublicaciones(String hashtag) {
        List<Publicacion> publicaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                // Solo publicaciones (id_referencia vacío) con el hashtag buscado
                if (campos[7].isEmpty() && campos[6].equals(hashtag)) {
                    publicaciones.add(createPublicacionFromCSV(campos));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer publicaciones filtradas desde CSV");
            e.printStackTrace();
        }
        return publicaciones;
    }

    @Override
    public List<Publicacion> getUsuarioPublicaciones(String nombre) {
        List<Publicacion> publicaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                // Solo publicaciones (id_referencia vacío) del usuario
                if (campos[7].isEmpty() && campos[1].equals(nombre)) {
                    publicaciones.add(createPublicacionFromCSV(campos));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer publicaciones de usuario desde CSV");
            e.printStackTrace();
        }
        return publicaciones;
    }

    @Override
    public Publicacion getPublicacionById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (Integer.parseInt(campos[0]) == id && campos[7].isEmpty()) {
                    return createPublicacionFromCSV(campos);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer publicación por ID desde CSV");
            e.printStackTrace();
        }
        return null;
    }

    private Publicacion createPublicacionFromCSV(String[] campos) {
        return new Publicacion(
                Integer.parseInt(campos[0]), // id_contenido
                campos[1], // autor
                campos[2], // creacion
                campos[3].isEmpty() ? null : campos[3], // multimedia
                campos[4], // texto
                Integer.parseInt(campos[5]), // likes
                campos[6], // hashtag
                getComentariosById(Integer.parseInt(campos[0])) // comentarios
        );
    }

    @Override
    public int sizePublicaciones() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (campos[7].isEmpty()) { // Solo contar publicaciones
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al contar publicaciones desde CSV");
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void insertComentario(Comentario comentario) {
        String data = String.join(",",
                String.valueOf(comentario.getId()),
                comentario.getAutor(),
                comentario.getCreacion(),
                comentario.getMultimedia() != null ? comentario.getMultimedia() : "",
                comentario.getTexto(),
                "0", // likes (no aplicable para comentarios)
                "", // hashtag (no aplicable para comentarios)
                String.valueOf(comentario.getIdReferencia())
        );
        writeToCSV(contenidoPath, null, data, true);
    }

    @Override
    public List<Comentario> getComentariosById(int id) {
        List<Comentario> comentarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            // Saltar encabezado
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (!campos[7].isEmpty() && Integer.parseInt(campos[7]) == id) {
                    comentarios.add(new Comentario(
                            Integer.parseInt(campos[0]), // id_contenido
                            campos[1], // autor
                            campos[2], // creacion
                            campos[3].isEmpty() ? null : campos[3], // multimedia
                            campos[4], // texto
                            Integer.parseInt(campos[7]) // id_referencia
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer comentarios desde CSV");
            e.printStackTrace();
        }
        return comentarios;
    }
}