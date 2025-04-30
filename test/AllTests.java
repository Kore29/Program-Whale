import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import Utils.UtilsApp;
import Utils.UtilsCheck;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;


public class AllTests {
}

class UtilsCheckTest {
    @DisplayName("checkInt: únicamente dígitos válidos")
    @ParameterizedTest(name = "{0} → {1}")
    @CsvSource({
            "12345, ''",
            "abc123, Error: Caracter invalido.",
            "12a34, Error: Caracter invalido."
    })
    void testCheckInt(String input, String expectedError) {
        String result = UtilsCheck.checkInt(input);
        if (expectedError.isEmpty()) {
            assertEquals("", result);
        } else {
            assertTrue(result.contains(expectedError));
        }
    }


    @Test @DisplayName("checkHashtagText extrae primer hashtag")
    void testCheckHashtagText() {
        String texto = "Esto es un #test de #JUnit";
        assertEquals("#test", UtilsCheck.checkHashtagText(texto));
        assertEquals("", UtilsCheck.checkHashtagText("Sin etiquetas"));
    }

}

class UtilsAppTest {
    @Test @DisplayName("removeHashTag elimina correctamente todas las etiquetas")
    void testRemoveHashTag() {
        String entrada = "Hola #mundo , probando #JUnit y #testing";
        String esperado = "Hola , probando y";
        assertEquals(esperado.trim(), UtilsApp.removeHashTag(entrada));
    }
}


class PublicacionTest {
    @Test @DisplayName("añadir y obtener comentarios")
    void testAddComentario() {
        Publicacion pub = new Publicacion(1, "Texto", "12/3/2025", "si", "Prueba", 12, "#lol",new ArrayList<>());
        Comentario com = new Comentario(1, "autor", "si", "null","hola", 2);
        pub.getComentarios().add(com);

        List<Comentario> lista = pub.getComentarios();
        assertEquals(1, lista.size());
        assertEquals("hola", lista.get(0).getTexto());
    }
}
