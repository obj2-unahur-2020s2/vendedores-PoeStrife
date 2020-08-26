package ar.edu.unahur.obj2.vendedores

class Certificacion(val esDeProducto: Boolean, val puntaje: Int)

abstract class Vendedor {
  // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
  // Además, a una MutableList se le pueden agregar elementos
  val certificaciones = mutableListOf<Certificacion>()
  val nombre = toString()
  // Definimos el método abstracto.
  // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
  fun puedeTrabajarEn(ciudad: Ciudad): Boolean{}

  // En las funciones declaradas con = no es necesario explicitar el tipo
  fun esVersatil() =
    certificaciones.size >= 3
            && this.certificacionesDeProducto() >= 1
            && this.otrasCertificaciones() >= 1

  // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
  fun agregarCertificacion(certificacion: Certificacion) {
    certificaciones.add(certificacion)
  }

  fun esFirme() = this.puntajeCertificaciones() >= 30

  fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto }
  fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto }

  fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje }

  open fun vendedorInfluyente() {}
}

// En los parámetros, es obligatorio poner el tipo
class VendedorFijo(val ciudadOrigen: Ciudad) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudad == ciudadOrigen
  }
  override fun vendedorInfluyente(): Unit = false
}

// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(val provinciasHabilitadas: List<Provincia>) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return provinciasHabilitadas.contains(ciudad.provincia)
  }
  override fun vendedorInfluyente() = provinciasHabilitadas.sumBy {p -> p.poblacion } > 10000000

}

class ComercioCorresponsal(val ciudades: List<Ciudad>) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudades.contains(ciudad)
  }

  fun tamañoDeListaSinRepetidos(): Int {
    val listaSinRep = mutableListOf<Ciudad>()
    val listaOriginal = mutableListOf<Ciudad>()
    listaOriginal == ciudades
    val primerElemento = listaOriginal.first()

    while (listaOriginal.isEmpty().not()){
      if (listaSinRep.contains(primerElemento).not()){
        listaSinRep.add(primerElemento)
      }
      listaOriginal.remove(primerElemento)
    }
    return listaOriginal.size

  }


  override fun vendedorInfluyente(): Boolean {
    return tamañoDeListaSinRepetidos() > 2 || ciudades.size > 4
  }
}
abstract class centrosDeDistribucion(val ciudades: List<Ciudad>): Vendedor() {
  val vendedores = mutableListOf<Vendedor>()
  fun agregarVendedor(vendedor: Vendedor) {
    if ((vendedores.any { v -> v.nombre == vendedor.toString() }).not()) {
      vendedores.add(vendedor)
    } else {
      error("Ya esta el vendedor")
    }

    fun vendedorEstrella() = vendedores.map { v -> v.puntajeCertificaciones() }.max()

    fun puedeCubrir(ciudad: Ciudad) = vendedores.filter { v -> v.puedeTrabajarEn(ciudad) }

    fun vendedoresGenericos() = vendedores.filter { v -> v.certificacionesDeProducto() > 0 }

    fun esRobusto() = vendedores.filter { v -> v.esFirme() }.size > 2
  }
}
