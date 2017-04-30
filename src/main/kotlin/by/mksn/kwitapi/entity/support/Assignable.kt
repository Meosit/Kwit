package by.mksn.kwitapi.entity.support

interface IdAssignable<ID> {
    val id: ID?
    fun assignID(id: ID?)
}

interface IdAndUserIdAssignable<ID> : IdAssignable<ID> {
    val userId: ID?
    fun assignUserID(id: ID?)
}