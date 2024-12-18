package controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import realizarPago
import java.util.*

class MainKtTestRealizarPago {

    @Test
    fun realizarPagoPagoCorrecto() {
        val scanner = Scanner("5.00\n5.00\n") // Simula la entrada de 5.00€ y luego 5.00€ más
        val precioTotal = 9.50 // Total a pagar

        val resultado = realizarPago(scanner, precioTotal)

        assertTrue(resultado, "El pago debería haberse realizado correctamente")

    }

    @Test
    fun realizarPagoPagoInsuficiente() {

        val scanner = Scanner("5.00\n") // Simula que el usuario paga 5.00€ solo
        val precioTotal = 9.50 // El total es mayor que lo pagado

        val resultado = realizarPago(scanner, precioTotal)

        assertFalse(resultado, "El pago debería haber fallado porque el total es insuficiente")

    }

    @Test
    fun realizarPagoPagoConBilletesInvalidos() {
        val scanner = Scanner("0.10\n0.20\n5.00\n") // Simula pagos con monedas válidas
        val precioTotal = 1.00 // El total a pagar es 1.00€

        val resultado = realizarPago(scanner, precioTotal)

        assertTrue(resultado, "El pago debería haberse realizado correctamente")
    }

    @Test
    fun realizarPagoEntradaInvalida() {
        val scanner = Scanner("abc\n5.00\n") // Simula entrada inválida seguida de una entrada válida
        val precioTotal = 5.00

        val resultado = realizarPago(scanner, precioTotal)

        assertTrue(resultado, "El pago debería haberse realizado correctamente después de una entrada inválida")
    }

    @Test
    fun realizarPagoConCambio() {
        val scanner = Scanner("10.00\n10.00\n") // El usuario paga 20.00€ para un total de 15.00€
        val precioTotal = 15.00

        val resultado = realizarPago(scanner, precioTotal)

        assertTrue(resultado, "El pago debería haberse realizado correctamente y el cambio debe ser devuelto")
    }
}