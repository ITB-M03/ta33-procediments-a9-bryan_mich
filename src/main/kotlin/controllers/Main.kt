import java.util.Scanner

fun main() {
    val scanner = abrirScanner()
    gestionarMaquina(scanner)
    cerrarScanner(scanner)
}

/**
 * Funcion para gestionar la máquina
 * @author Mich
 * @version 1.0
 */
fun gestionarMaquina(scanner: Scanner) {
    println("¡Bienvenido a la máquina de billetes!")
    var maquinaActiva = true

    while (maquinaActiva) {
        // Solicitar el código del operario o continuar sin código
        println("\nIntroduce el código de operario o pulsa Enter para continuar:")
        val entrada = scanner.nextLine().trim()

        // Si el código es correcto, detener la máquina
        if (entrada == "4321") {
            println("Código correcto. La máquina de billetes detendrá su funcionamiento.")
            maquinaActiva = false
        } else {
            // Si el código es incorrecto o no se introduce, iniciar el proceso de venta de billetes
            iniciarMaquinaDeBilletes(scanner)
            println("\nPreparada para el siguiente usuario...\n")
        }
    }
}

// Función para abrir el scanner
fun abrirScanner(): Scanner = Scanner(System.`in`)

// Función para cerrar el scanner
fun cerrarScanner(scanner: Scanner) = scanner.close()

/**
 * Funcion para iniciar la Máquina de billetes de metro ITB
 * @author Mich-Bryan
 * @version 2.0
 */
fun iniciarMaquinaDeBilletes(scanner: Scanner) {
    var tipoDeBillete: String? = null
    var zonas: Int? = null
    var numBilletes: Int? = null
    var precioTotal: Double? = null

    var pasoActual = 1
    var operacionActiva = true

    while (operacionActiva) {
        when (pasoActual) {
            1 -> {
                tipoDeBillete = seleccionarTipoDeBillete(scanner, tipoDeBillete)
                if (tipoDeBillete == null) {
                    println("¿Quieres finalizar? (si/no)")
                    if (scanner.nextLine().trim().equals("si", ignoreCase = true)) {
                        operacionActiva = false
                    }
                } else {
                    pasoActual = 2
                }
            }

            2 -> {
                zonas = seleccionarZonas(scanner, zonas)
                if (zonas == -1) {
                    println("Volviendo al paso anterior...")
                    pasoActual = 1
                } else {
                    pasoActual = 3
                }
            }

            3 -> {
                numBilletes = seleccionarNumeroDeBilletes(scanner, numBilletes)
                if (numBilletes == -1) {
                    println("Volviendo al paso anterior...")
                    pasoActual = 2
                } else {
                    pasoActual = 4
                }
            }

            4 -> {
                precioTotal = calcularPrecio(tipoDeBillete!!, zonas!!, numBilletes!!)
                println("Precio total: %.2f€".format(precioTotal))
                val pagoRealizado = realizarPago(scanner, precioTotal)
                if (!pagoRealizado) {
                    println("Pago cancelado o insuficiente. Volviendo al inicio del proceso...")
                    operacionActiva = false
                } else {
                    pasoActual = 5
                }
            }

            5 -> {
                println("¿Deseas un tiquet? (si/no)")
                val respuesta = scanner.nextLine().trim().lowercase()
                if (respuesta == "si") {
                    imprimirTiquet(tipoDeBillete!!, zonas!!, numBilletes!!, precioTotal!!)
                } else {
                    println("No se generará el tiquet.")
                }
                operacionActiva = false // Finaliza esta sesión
            }
        }
    }
}

/**
 * Funcion para selecionar el tipo de billete
 * @author Bryan
 * @version 1.0
 *
 */
fun seleccionarTipoDeBillete(scanner: Scanner, valorAnterior: String?): String? {
    val billetes = listOf("Billete sencillo", "TCasual", "TUsual", "TFamiliar", "TJoven")
    println("\nSelecciona el tipo de billete:")
    billetes.forEachIndexed { index, billete -> println("${index + 1}. $billete") }
    println("0. Finalizar operación")
    println("Seleccionado previamente: ${valorAnterior ?: "Ninguno"}")

    val opcion = scanner.nextLine().toIntOrNull()
    if (opcion == 0) return null
    if (opcion != null && opcion in 1..billetes.size) {
        val seleccion = billetes[opcion - 1]
        println("Ha seleccionado la opción $opcion: $seleccion")
        return billetes[opcion - 1]
    }
    println("Opción inválida. Por favor, inténtalo de nuevo.")
    return seleccionarTipoDeBillete(scanner, valorAnterior)
}

/**
 * Funcion para seleccionar las zonas
 * @author Bryan
 * @version 1.0
 */
fun seleccionarZonas(scanner: Scanner, valorAnterior: Int?): Int {
    println("\nSelecciona las zonas de distancia (1, 2 o 3):")
    println("0. Volver atrás")
    println("Seleccionado previamente: ${valorAnterior ?: "Ninguna"}")

    val zonas = scanner.nextLine().toIntOrNull()
    if (zonas == 0) return -1 // Indica que el usuario desea regresar al paso anterior
    if (zonas != null && zonas in 1..3) {
        println("Ha seleccionado la opción $zonas: $zonas zona(s)")
        return zonas
    }
    println("Opción inválida. Por favor, inténtalo de nuevo.")
    return seleccionarZonas(scanner, valorAnterior)
}


/**
 * Funcion para selecccionar Cantidad de billetes de Metro
 * @author Bryan
 * @version1.0
 */
fun seleccionarNumeroDeBilletes(scanner: Scanner, valorAnterior: Int?): Int {
    println("\n¿Cuántos billetes deseas comprar? (1-2-3):")
    println("0. Volver atrás")
    println("Seleccionado previamente: ${valorAnterior ?: "No se ha seleccionado nada"}")

    val numero = scanner.nextLine().toIntOrNull()
    if (numero == 0) return -1 // Indica que el usuario desea regresar al paso anterior
    if (numero != null && numero in 1..3) {
        println("Ha seleccionado la opción $numero: $numero billete(s)")
        return numero
    }
    println("Opción inválida. Por favor, inténtalo de nuevo.")
    return seleccionarNumeroDeBilletes(scanner, valorAnterior)
}

/**
 * Funcion para Calcular el precio
 * @Bryan
 * @version1.0
 */
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
 * Funcion para realizar el pago
 * @author Bryan
 * @version 1.0
 */
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
 * funcion para imprimir ticket
 * @author Mich
 * @version 1.0
 */
fun imprimirTiquet(tipoDeBillete: String, zonas: Int, numBilletes: Int, precioTotal: Double) {
    println("\n--- Tiquet de compra ---")
    println("Tipo de billete: $tipoDeBillete")
    println("Zonas: $zonas")
    println("Número de billetes: $numBilletes")
    println("Precio total: %.2f€".format(precioTotal))
    println("------------------------")
}
