package controllers

import calcularPrecio
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainKtTestCalcularPrecio {

    @Test
    fun calcularPrecioBilleteSencilloZona1() {
        // Prueba con un billete sencillo, zona 1 y 2 billetes
        val precio = calcularPrecio("Billete sencillo", 1, 2)
        assertEquals(4.80, precio, "El precio debería ser 4.80€")
    }

    @Test
    fun calcularPrecioTCasualZona2() {
        // Prueba con TCasual, zona 2 y 1 billete
        val precio = calcularPrecio("TCasual", 2, 1)
        assertEquals(14.91, precio, "El precio debería ser 14.91€")
    }

    @Test
    fun calcularPrecioTUsualZona3() {
        // Prueba con TUsual, zona 3 y 3 billetes
        val precio = calcularPrecio("TUsual", 3, 3)
        assertEquals(220.008, precio, "El precio debería ser 220.008€")
    }

    @Test
    fun calcularPrecioConZonaInvalida() {
        // Prueba con una zona inválida
        val exception = assertThrows(IllegalArgumentException::class.java) {
            calcularPrecio("Billete sencillo", 4, 1)
        }
        assertEquals("Zonas no válidas", exception.message)
    }

    @Test
    fun calcularPrecioConTipoDeBilleteInvalido() {
        // Prueba con un tipo de billete no válido
        val exception = assertThrows(IllegalArgumentException::class.java) {
            calcularPrecio("Billete Inexistente", 1, 1)
        }
        assertEquals("Tipo de billete no válido", exception.message)
    }
}