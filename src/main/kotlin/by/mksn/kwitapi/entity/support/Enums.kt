package by.mksn.kwitapi.entity.support

import java.beans.PropertyEditorSupport

enum class UserRole {
    USER, ADMIN
}

enum class CategoryType {
    INCOME, OUTGO
}

class UserRoleBinder : PropertyEditorSupport() {

    override fun setAsText(text: String?) {
        if (text != null) {
            value = UserRole.valueOf(text.toUpperCase())
        } else {
            throw IllegalArgumentException()
        }
    }

}

class CategoryTypeBinder : PropertyEditorSupport() {

    override fun setAsText(text: String?) {
        if (text != null) {
            value = CategoryType.valueOf(text.toUpperCase())
        } else {
            throw IllegalArgumentException()
        }
    }

}