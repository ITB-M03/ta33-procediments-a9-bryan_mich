package controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import seleccionarTipoDeBillete
import java.io.ByteArrayInputStream
import java.util.*

class MainKtTestSeleccionarTipoDeBillete {

    private fun crearScannerSimulado(entrada: String): Scanner {
        val inputStream = ByteArrayInputStream(entrada.toByteArray())
        return Scanner(inputStream)

    }

    @Test
    fun seleccionarTipoDeBilleteCorrecto() {
        // Simulamos la entrada del usuario seleccionando la opción 1
        val scanner = crearScannerSimulado("1\n")

        // Llamamos a la función con la entrada simulada
        val resultado = seleccionarTipoDeBillete(scanner, null)

        // Verificamos que la selección sea correcta
        assertEquals("Billete sencillo", resultado)
    }

    @Test
    fun seleccionarTipoDeBilleteInvalido() {
        // Simulamos la entrada del usuario seleccionando una opción inválida (por ejemplo, "10")
        val scanner = crearScannerSimulado("10\n")

        // Llamamos a la función con la entrada inválida
        val resultado = seleccionarTipoDeBillete(scanner, null)

        // Verificamos que la función pida de nuevo la entrada
        assertNull(resultado)
    }

    @Test
    fun seleccionarTipoDeBilleteVolverAtras() {
        // Simulamos la entrada del usuario seleccionando la opción "0" para volver atrás
        val scanner = crearScannerSimulado("0\n")

        // Llamamos a la función con la entrada para volver atrás
        val resultado = seleccionarTipoDeBillete(scanner, null)

        // Verificamos que el resultado sea null, ya que el usuario seleccionó "0"
        assertNull(resultado)
    }
}
