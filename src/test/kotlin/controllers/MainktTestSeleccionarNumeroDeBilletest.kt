import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.io.ByteArrayInputStream
import java.util.Scanner

class MainktTestSeleccionarNumeroDeBilletest {

    // Función auxiliar para simular la entrada
    fun crearScannerSimulado(entrada: String): Scanner {
        val inputStream = ByteArrayInputStream(entrada.toByteArray())
        return Scanner(inputStream)

    }

    @Test
    fun seleccionarNumeroDeBilletesCorrecto() {
        // Simulamos la entrada del usuario seleccionando la opción 2
        val scanner = crearScannerSimulado("2\n")

        // Llamamos a la función con la entrada simulada
        val resultado = seleccionarNumeroDeBilletes(scanner, null)

        // Verificamos que la selección sea correcta
        assertEquals(2, resultado)
    }

    @Test
    fun seleccionarNumeroDeBilletesInvalido() {
        // Simulamos la entrada de una opción inválida, por ejemplo, "4"
        val scanner = crearScannerSimulado("4\n")

        // Llamamos a la función con la entrada inválida
        val resultado = seleccionarNumeroDeBilletes(scanner, null)

        // Verificamos que la función siga pidiendo la entrada hasta que sea válida
        assertEquals(4, resultado)
    }

    @Test
    fun seleccionarNumeroDeBilletesVolverAtras() {
        // Simulamos la entrada del usuario seleccionando la opción "0" para volver atrás
        val scanner = crearScannerSimulado("0\n")

        // Llamamos a la función con la entrada para volver atrás
        val resultado = seleccionarNumeroDeBilletes(scanner, null)

        // Verificamos que el resultado sea -1, ya que el usuario seleccionó "0"
        assertEquals(-1, resultado)
    }
}
