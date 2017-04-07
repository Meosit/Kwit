package by.mksn.kwitapi.entity

interface IdSetable<in ID> {
    fun setID(id: ID?)
}