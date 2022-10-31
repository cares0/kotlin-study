package function

class User(val id: Long, val name: String, val address: String)

fun saveUser(user: User) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                "값 검증에 실패했습니다. 유저 아이디: [${user.id}] 실패 필드: [$fieldName]"
            )
        }
    }
    validate(user.name, "name")
    validate(user.address, "address")

    // DB에 저장한다.
}

fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                "값 검증에 실패했습니다. 유저 아이디: [${id}] 실패 필드: [$fieldName]"
            )
        }
    }
    validate(name, "name")
    validate(address, "address")
}

fun saveUser2(user: User) {
    user.validateBeforeSave()
}