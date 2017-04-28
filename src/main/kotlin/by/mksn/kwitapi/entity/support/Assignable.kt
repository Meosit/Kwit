package by.mksn.kwitapi.entity.support

interface IdAssignable<in ID> {
    fun assignID(id: ID?)
}

interface IdAndUserIdAssignable<in ID> : IdAssignable<ID> {
    fun assignUserID(id: ID)
}