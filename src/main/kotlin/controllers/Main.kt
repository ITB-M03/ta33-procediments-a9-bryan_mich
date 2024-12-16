import java.util.Scanner

fun main() {
    val scanner = abrirScanner() // Abrir el scanner

    iniciarMaquinaDeBilletes(scanner) // Iniciar la máquina

    cerrarScanner(scanner) // Cerrar el scanner
}

// Función para abrir el scanner
fun abrirScanner(): Scanner {
    return Scanner(System.`in`)
}

// Función para cerrar el scanner
fun cerrarScanner(scanner: Scanner) {
    scanner.close()
}

// Función que contiene el bucle principal de la máquina de billetes
fun iniciarMaquinaDeBilletes(scanner: Scanner) {
    while (true) {
        println("\n--- MÁQUINA DE BILLETES ---")
        val tipoDeBillete = seleccionarTipoDeBillete(scanner) ?: continue
        val zonas = seleccionarZonas(scanner)
        val numBilletes = seleccionarNumeroDeBilletes(scanner)

        val precioTotal = calcularPrecio(tipoDeBillete, zonas, numBilletes)
        println("Precio total: %.2f€".format(precioTotal))

        if (realizarPago(scanner, precioTotal)) {
            println("\n¡Compra realizada correctamente!\n")
        } else {
            println("Pago cancelado o insuficiente. Por favor, vuelve a intentarlo.")
        }
    }
}

// Función para seleccionar el tipo de billete
fun seleccionarTipoDeBillete(scanner: Scanner): String? {
    val billetes = listOf("Billete sencillo", "TCasual", "TUsual", "TFamiliar", "TJoven")

    while (true) {
        println("\nSelecciona el tipo de billete:")
        billetes.forEachIndexed { index, billete -> println("${index + 1}. $billete") }
        println("0. Volver atrás")

        val opcion = scanner.nextLine().toIntOrNull()
        if (opcion == 0) return null
        if (opcion != null && opcion in 1..billetes.size) {
            return billetes[opcion - 1]
        }
        println("Opción inválida. Por favor, inténtalo de nuevo.")
    }
}

// Función para seleccionar el número de zonas
fun seleccionarZonas(scanner: Scanner): Int {
    while (true) {
        println("\nSelecciona las zonas de distancia (1, 2 o 3):")
        val zonas = scanner.nextLine().toIntOrNull()
        if (zonas != null && zonas in 1..3) {
            return zonas
        }
        println("Opción inválida. Por favor, inténtalo de nuevo.")
    }
}

// Función para seleccionar el número de billetes
fun seleccionarNumeroDeBilletes(scanner: Scanner): Int {
    while (true) {
        println("\n¿Cuántos billetes deseas comprar? (1-3):")
        val numero = scanner.nextLine().toIntOrNull()
        if (numero != null && numero in 1..3) {
            return numero
        }
        println("Opción inválida. Por favor, inténtalo de nuevo.")
    }
}

// Función para calcular el precio total
fun calcularPrecio(tipoDeBillete: String, zonas: Int, numBilletes: Int): Double {
    val preciosZona1 = mapOf(
        "Billete sencillo" to 2.40,
        "TCasual" to 11.35,
        "TUsual" to 40.00,
        "TFamiliar" to 10.00,
        "TJoven" to 80.00
    )

    val multiplicador = when (zonas) {
        1 -> 1.0
        2 -> 1.3125
        3 -> 1.8443
        else -> throw IllegalArgumentException("Zonas no válidas")
    }

    val precioPorBillete = preciosZona1[tipoDeBillete]
        ?: throw IllegalArgumentException("Tipo de billete no válido")
    return precioPorBillete * multiplicador * numBilletes
}

// Función para realizar el pago
fun realizarPago(scanner: Scanner, precioTotal: Double): Boolean {
    var pagado = 0.0
    val monedasValidas = listOf(0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 20.00, 50.00)

    while (pagado < precioTotal) {
        println("\nIntroduce dinero (monedas y billetes válidos):")
        val entrada = scanner.nextLine().toDoubleOrNull()
        if (entrada != null && entrada in monedasValidas) {
            pagado += entrada
            println("Total pagado: %.2f€".format(pagado))
        } else {
            println("Cantidad inválida. Por favor, inténtalo de nuevo.")
        }
    }

    val cambio = pagado - precioTotal
    if (cambio >= 0) {
        println("\nCambio a devolver: %.2f€".format(cambio))
        return true
    }
    return false
}
