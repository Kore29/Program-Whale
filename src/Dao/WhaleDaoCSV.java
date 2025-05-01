package Dao;

import PageModelNew.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhaleDaoCSV implements WhaleDao {

    private static final Path basePath = Paths.get("src", "DataBase", "CSV");
    private static final Path usuariosPath = basePath.resolve("usuarios.csv");
    private static final Path contenidoPath = basePath.resolve("contenido.csv");
    private static final Path amigosPath   = basePath.resolve("amigos.csv");


    private void initializeCSVFiles() {
        try {
            Files.createDirectories(basePath);  // Esto crea la carpeta CSV si no existe

            if (!Files.exists(usuariosPath)) {
                writeToCSV(usuariosPath, "nombre,contrasena,email,creacion", "", false);
            }

            if (!Files.exists(contenidoPath)) {
                writeToCSV(contenidoPath, "id_contenido,autor,creacion,multimedia,texto,likes,hashtag,id_referencia", "", false);
            }

            if (!Files.exists(amigosPath)) {
                writeToCSV(amigosPath, "usuario,amigo", "", false);
            }

        } catch (IOException e) {
            System.out.println("Error al inicializar archivos CSV");
            e.printStackTrace();
        }
    }

    private void writeToCSV(Path path, String header, String data, boolean append) {
        try {
            File file = new File(path.toString());
            boolean fileExists = file.exists();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
                if ((!fileExists || file.length() == 0) && header != null) {
                    bw.write(header);
                    bw.newLine();
                }

                if (!data.isEmpty()) {
                    bw.write(data);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + path);
            e.printStackTrace();
        }
    }



    public WhaleDaoCSV() {initializeCSVFiles();}

    public void clearAllData() {
        writeToCSV(usuariosPath, "nombre,contrasena,email,creacion", "", false);
        writeToCSV(contenidoPath, "id_contenido,autor,creacion,multimedia,texto,likes,hashtag,id_referencia", "", false);
        writeToCSV(amigosPath, "usuario,amigo", "", false);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosPath.toString()))) {
            reader.readLine(); // Saltar cabecera

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                usuarios.add(new Usuario(
                        campos[0], // nombre
                        campos[1], // contrasena
                        campos[2], // email
                        campos[3], // creacion
                        getAllAmigos(campos[0]), // amigos
                        getPublicacionesByUsuario(campos[0]) // publicaciones
                ));
            }
        } catch (IOException e) {
            System.out.println("Error al leer todos los usuarios desde CSV");
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public List<Publicacion> getAllContenido() {
        List<Publicacion> contenido = new ArrayList<>();
        Map<Integer, Publicacion> mapaPublicaciones = new HashMap<>();
        List<Comentario> comentariosPendientes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",", -1);

                int id_contenido = Integer.parseInt(campos[0]);
                String autor = campos[1];
                String creacion = campos[2];
                String multimedia = campos[3].isEmpty() ? null : campos[3];
                String texto = campos[4];

                if (campos[7].isEmpty()) {
                    int likes = campos[5].isEmpty() ? 0 : Integer.parseInt(campos[5]);
                    String hashtag = campos[6];

                    Publicacion pub = new Publicacion(id_contenido, autor, creacion, multimedia, texto, likes, hashtag, new ArrayList<>());
                    contenido.add(pub);
                    mapaPublicaciones.put(id_contenido, pub);
                } else {
                    int id_referencia = Integer.parseInt(campos[7]);
                    Comentario comentario = new Comentario(id_contenido, autor, creacion, multimedia, texto, id_referencia);
                    comentariosPendientes.add(comentario);
                }
            }

            // asociar comentarios a publicaciones
            for (Comentario comentario : comentariosPendientes) {
                Publicacion pub = mapaPublicaciones.get(comentario.getIdReferencia());
                if (pub != null) {
                    pub.getComentarios().add(comentario);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer todo el contenido desde CSV");
            e.printStackTrace();
        }

        return contenido;
    }



    @Override
    public List<String> getAllAmigos(String nombre) {
        List<String> amigos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(amigosPath.toString()))) {

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
                            getAllAmigos(campos[0]), // amigos
                            getPublicacionesByUsuario(campos[0]) // publicaciones
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
                            getAllAmigos(campos[0]), // amigos
                            getPublicacionesByUsuario(campos[0]) // publicaciones
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
    public void insertAmigo(String usuario, String amigo) {
        String data = String.join(",", usuario, amigo);
        writeToCSV(amigosPath, null, data, true);
    }

    @Override
    public void removeAmigo(String nombre, String amigo) {
        List<String> nuevasLineas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(amigosPath.toString()))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.equals(nombre + "," + amigo)) {
                    nuevasLineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo amigos.csv para eliminar amigo");
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(amigosPath.toString(), false))) {
            for (String linea : nuevasLineas) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo amigos.csv tras eliminar amigo");
            e.printStackTrace();
        }
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
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {
            String header = reader.readLine();
            lines.add(header);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",", -1);
                if (Integer.parseInt(campos[0]) == id && campos[7].isEmpty()) {
                    int likes = Integer.parseInt(campos[5]) + 1;
                    campos[5] = String.valueOf(likes);
                    line = String.join(",", campos);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar likes");
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(contenidoPath.toString(), false))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir contenido actualizado");
            e.printStackTrace();
        }
    }

    @Override
    public List<Publicacion> getSixPublicaciones(int page) {
        List<Publicacion> publicaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {

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
    public List<Publicacion> getPublicacionesByHashTag(String hashtag) {
        List<Publicacion> publicaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {

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
    public List<Publicacion> getPublicacionesByUsuario(String nombre) {
        List<Publicacion> publicaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {

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
    public int getSizePublicaciones() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(contenidoPath.toString()))) {

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