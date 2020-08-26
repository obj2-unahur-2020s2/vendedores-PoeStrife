package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)

  describe("Vendedor fijo") {
    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)

    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }
  }
  describe("esInfluyente"){
      it ("no es influyente"){
        vendedorFijo.vendedorInfluyente().shouldBeFalse()
      }
  }

  describe("Viajante") {
    val cordoba = Provincia(2000000)
    val villaDolores = Ciudad(cordoba)
    val viajante = Viajante(listOf(misiones))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }
    describe("esInfluyente"){
      it ("no es influyente"){
        viajante.vendedorInfluyente().shouldBeFalse()
      }
    }
  }


  describe("Comercio corresponsal"){
    val entreRios = Provincia(3000000)
    val diamante = Ciudad(entreRios)
    val sucursal1 = ComercioCorresponsal(listOf(diamante))
    
    val santaFe = Provincia(45000000)
    val rosario = Ciudad(santaFe)
    val sucursal2 = ComercioCorresponsal(listOf(rosario))
    
    describe("puedeTrabajarEn"){
      it("una ciudad donde tenga sucursal"){
        ComercioCorresponsal.puedeTrabajarEn(diamante).shouldBeTrue()
      }
    }
    describe("puedeTranajarEn"){
      it ("una ciudad donde tenga sucursal") {
        ComercioCorresponsal.puedeTrabajarEn(rosario).shouldBeTrue()
      }
    }
    describe("puedeTranajarEn"){
      it ("una ciudad donde tenga sucursal") {
        ComercioCorresponsal.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }
  }
  describe("esInfluyente"){
    it ("no es influyente"){

      ComercioCorresponsal.vendedorInfluyente().shouldBeFalse()
    }
  }

})
