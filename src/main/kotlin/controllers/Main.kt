package controllers
import java.util.Scanner

fun main() {
    val scanner = abrirScanner() // Abrir el scanner
    var continuar = true

    while (continuar) {
        continuar = iniciarMaquinaDeBilletes(scanner) // Iniciar la máquina para cada nueva compra
    }

    cerrarScanner(scanner) // Cerrar el scanner
}

/**
 * Abrir Scanner
 * @author Bryan
 * @version 1.3
 * @since 17/12/2024
 * @param scanner abre scanner
 * @return
 * */
fun abrirScanner(): Scanner {
    val scanner = Scanner(System.`in`)

    return scanner
}

/**
 * Cerrar Scanner
 * @author Bryan
 * @version 1.3
 * @since 17/12/2024
 * @param scanner cierra el scanner
 * */
fun cerrarScanner(scanner: Scanner) {
    scanner.close()
}

/**
 * Funcion que contiene el bucle principal de la maquina de billetes
 * @author Michelle
 * @version 1.5
 * @since 17/12/2024
 * @param scanner funcion abrir scanner
 * */
fun iniciarMaquinaDeBilletes(scanner: Scanner): Boolean {
    var tipoDeBillete: String? = null
    var zonas: Int
    var numBilletes: Int
    var precioTotal: Double
    var pagoRealizado: Boolean = false  // Inicializamos la variable false

    tipoDeBillete = seleccionarTipoDeBillete(scanner)

    if (tipoDeBillete == null) {
        println("Operación cancelada. Volviendo al inicio.")
        return true
    }
    // Selección de zonas
    zonas = seleccionarZonas(scanner)
    if (zonas == -1) {
        println("Volviendo al paso anterior...")
        return true
    }

    // Selección de número de billetes
    numBilletes = seleccionarNumeroDeBilletes(scanner)

    // Cálculo de precio total
    precioTotal = calcularPrecio(tipoDeBillete, zonas, numBilletes)
    println("Precio total: %.2f€".format(precioTotal))

    // Realiza pago
    pagoRealizado = realizarPago(scanner, precioTotal)

    if (pagoRealizado) {
        println("\n¡Compra realizada correctamente!\n")
    } else {
        println("Pago cancelado o insuficiente. Por favor, vuelve a intentarlo.")
        return true  // Si el pago no se realizó, regresa y permite al usuario intentar nuevamente
    }

    // Preguntar si desea un tiquet
    println("¿Deseas un tiquet? (si/no)")
    val respuesta = scanner.nextLine().trim().lowercase()

    if (respuesta == "si") {
        imprimirTiquet(tipoDeBillete, zonas, numBilletes, precioTotal)
    } else {
        println("No se generará el tiquet.")
    }

    // Preguntar si desea continuar con otra compra
    println("\n¿Deseas realizar otra compra? (si/no)")
    val continuarCompra = scanner.nextLine().trim().lowercase()

    return continuarCompra == "si"  // Si el usuario desea continuar, retorna true, sino, false
}

/**
 * Funcion para seleccionar el tipo de billete
 * @author Bryan
 * @version 1.5
 * @since 17/12/2024
 * @param scanner funcion abrir scanner
 * */
fun seleccionarTipoDeBillete(scanner: Scanner): String? {
    val billetes = listOf("Billete sencillo", "TCasual", "TUsual", "TFamiliar", "TJoven")

    var opcion: Int?

    do {
        println("\nSelecciona el tipo de billete:")
        billetes.forEachIndexed { index, billete -> println("${index + 1}. $billete") }
        println("0. Volver atrás")

        opcion = scanner.nextLine().toIntOrNull()

        // Si se elige la opción 0, regresa al inicio
        if (opcion == 0) return null

        // Si la opción es válida, se retorna el billete seleccionado
        if (opcion != null && opcion in 1..billetes.size) {
            return billetes[opcion - 1]
        }

        println("Opción inválida. Por favor, inténtalo de nuevo.")

    } while (opcion == null || opcion !in 0..billetes.size)  // Continúa mientras la opción sea inválida

    return null
}

/**
 * Funcion para seleccionar el numero de zonas
 * @author Michel
 * @version 1.5
 * @since 17/12/2024
 * @param scanner funcion abrir scanner
 * */
fun seleccionarZonas(scanner: Scanner): Int {
    var zonas: Int?

    println("\nSelecciona las zonas de distancia (1, 2 o 3):")
    println("0. Volver atrás")
    zonas = scanner.nextLine().toIntOrNull()

    // Si el usuario quiere volver atrás
    if (zonas == 0) {
        return -1  // Retorna un valor especial para indicar que se desea volver atrás
    }

    if (zonas != null && zonas in 1..3) {
        return zonas
    }

    println("Opción inválida. Por favor, inténtalo de nuevo.")
    return seleccionarZonas(scanner)  // Vuelve a preguntar si la entrada es inválida
}

/**
 * Funcion para seleccionar el numero de billetes
 * @author Bryan
 * @version 1.5
 * @since 17/12/2024
 * @param scanner funcion abrir scanner
 * */
fun seleccionarNumeroDeBilletes(scanner: Scanner): Int {
    var numero: Int?

    println("\n¿Cuántos billetes deseas comprar? (1-3):")
    numero = scanner.nextLine().toIntOrNull()

    if (numero != null && numero in 1..3) {
        return numero
    }

    println("Opción inválida. Por favor, inténtalo de nuevo.")
    return seleccionarNumeroDeBilletes(scanner)  // Vuelve a preguntar si la entrada es inválida
}

/**
 * Funcion para calcular el precio total
 * @author Bryan
 * @version 1.5
 * @since 17/12/2024
 * @param tipoDeBillete billete seleccionado por el usuario
 * @param zonas numero de zona elegido
 * @param numBilletes pago realizado por el usuario
 *
 * */
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
/**
 * Funcion para realizar los pagos
 * @author Bryan
 * @version 1.1
 * @since 17/12/2024
 * @param scanner abrir scanner
 * @param precioTotal suma el precio de las tarjetas elegidas
 *
 * */
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
/**
 * Funcion para imprimir el tiquet
 * @author Michelle
 * @version 1.1
 * @since 17/12/2024
 * @param tipoDeBillete opcion elegida por el usuario
 * @param zonas zona seleccionada por el usuario
 * @param numBilletes cantidad de billetes seleccionados
 * @param precioTotal suma del precio
*/
fun imprimirTiquet(tipoDeBillete: String, zonas: Int, numBilletes: Int, precioTotal: Double) {
    println("\n--- Tiquet de compra ---")
    println("Tipo de billete: $tipoDeBillete")
    println("Zonas: $zonas")
    println("Número de billetes: $numBilletes")
    println("Precio total: %.2f€".format(precioTotal))
    println("------------------------")
}
