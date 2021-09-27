package mx.tec.atomictracker



data class HabitDTO(var nombre: String, var descripcion: String, var frecuencia: String, var inicio: String, var fin: String, var recordar: Boolean) {

    constructor() : this("undefined", "undefined", "undefined", "undefined", "undefined", false)



}