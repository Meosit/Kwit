package by.mksn.kwitapi.entity.support

interface BaseEntity<ID> {
    var id: ID?
}

interface UserBaseEntity<ID> : BaseEntity<ID> {
    var userId: ID?
}