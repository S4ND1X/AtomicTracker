package mx.tec.atomictracker



data class HabitDTO(var nombre: String, var descripcion: String, var frecuencia: String, var inicio: String, var fin: String, var recordar: Boolean, var id: String, var latitud: Double, var longitud: Double) {

    constructor() : this("undefined", "undefined", "undefined", "undefined", "undefined", false, "undefined", 0.0, 0.0)



}