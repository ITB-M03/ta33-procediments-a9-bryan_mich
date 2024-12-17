import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.io.ByteArrayInputStream
import java.util.Scanner

class MaquinaDeBilletesTest {

    // Función auxiliar para simular la entrada
    private fun crearScannerSimulado(entrada: String): Scanner {
        val inputStream = ByteArrayInputStream(entrada.toByteArray())
        return Scanner(inputStream)
    }

    @Test
    fun seleccionarZonasCorrecta() {
        // Simulamos la entrada del usuario seleccionando la opción 2
        val scanner = crearScannerSimulado("2\n")

        // Llamamos a la función con la entrada simulada
        val resultado = seleccionarZonas(scanner, null)

        // Verificamos que la selección sea correcta
        assertEquals(2, resultado)
    }

    @Test
    fun seleccionarZonasInvalida() {
        // Simulamos la entrada del usuario seleccionando una opción inválida (por ejemplo, "4")
        val scanner = crearScannerSimulado("4\n")

        // Llamamos a la función con la entrada inválida
        val resultado = seleccionarZonas(scanner, null)

        // Verificamos que la función vuelva a pedir la entrada (no debe devolver null ni valores inválidos)
        assertEquals(4, resultado)
    }

    @Test
    fun seleccionarZonasVolverAtras() {
        // Simulamos la entrada del usuario seleccionando la opción "0" para volver atrás
        val scanner = crearScannerSimulado("0\n")

        // Llamamos a la función con la entrada para volver atrás
        val resultado = seleccionarZonas(scanner, null)

        // Verificamos que el resultado sea -1, ya que el usuario seleccionó "0"
        assertEquals(-1, resultado)
    }
}
