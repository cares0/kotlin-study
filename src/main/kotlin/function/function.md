
# 1. 함수 기초
간단한 예제를 개선해 나가면서 코틀린의 함수 사용 방법을 알아보자.

## 예제
기본적으로 Java의 컬렉션에는 toString이 구현되어 있다. 하지만 이 toString 대신에 뭔가 출력 형식을 바꿔야 한다고 해보자.
```kotlin
fun<T> joinToString(
    collection: Collection<T>,
    separator: String,
    prefix: String,
    postfix: String
): String {
    val result = StringBuilder(prefix)
    
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    
    result.append(postfix)
    return result.toString()
}
```
separator, prefix, postfix를 인자로 받아 맨 처음에는 prefix, 맨 마지막에는 postfix, 요소마다 separator를 추가하여 String으로 반환하는 코드이다.
이제 이 함수를 점점 개선해 나가면서 Kotlin 함수의 특징을 알아보자.

## 1) 이름 붙인 인자
첫 번째로 해결하고자 하는 것은 가독성이다.  
함수에 넘겨야 하는 인자도 많고, 타입도 같은 것이 많기 때문에 어떤 인자가 어떤 역할을 수행하는지 구분하기 어렵다.
```kotlin
joinToString(collection, separator = " - ", prefix = "[", postfix = "]")
```
- Kotlin은 함수의 인자를 넘겨줄때 `파라미터 이름 = 값`을 명시하여 보다 가독성 있게 호출 가능
- 자바로 작성된 메서드는 이름 붙인 인자를 사용할 수 없음

## 2) 디폴트 파라미터 값
Java에서는 오버로딩한 메서드가 많이 포함되어 있는 일부 클래스를 볼 수 있다. 
코틀린에서는 디폴트 파라미터를 통해 굳이 오버로딩을 사용하지 않고 함수를 비슷한 함수를 분리할 수 있다.
```kotlin
fun<T> joinToString(
    collection: Collection<T>,
    separator: String = ", ",
    prefix: String = "[",
    postfix: String = "]"
): String {
    ...
}

fun main(args: Array<String>) {
    val collection = arrayListOf(1, 2, 3)

    println(joinToString(collection)) // [1, 2, 3]
    println(joinToString(collection, " - ")) // [1 - 2 - 3]
    println(joinToString(collection, prefix = "{", postfix = "}")) // {1, 2, 3}
    println(joinToString(collection, "{", "}")) // }1{2{3]
}
```
- 이름 있는 인자로 선언하지 않으면 순서대로 디폴트 파라미터가 적용됨 (4번째 호출 참고)
- Java에서 Kotlin의 디폴트 파라미터 함수를 호출하는 것은 불가능, 모든 인자를 명시해야 함
  - `@JvmOverloads`를 사용하면 컴파일 시 디폴트 파라미터에 따른 메서드를 Java 방식으로 오버로딩 해줌

## 3) 최상위 함수
Java에서는 딱히 상태 유지가 필요 없는, 유틸성 메서드를 제공하기 위해 정적 메서드로 만들곤 한다. (Java의 Collections 클래스 등)
하지만 코틀린은 함수를 굳이 클래스 내에 선언할 필요가 없기 때문에, 굳이 정적 메서드를 제공하기 위해서 클래스를 생성할 필요는 없다.
```kotlin
package function.importtest

import function.joinToString

fun main(args: Array<String>) {
    val arrayList = arrayListOf("a", "b", "c", "d")
    joinToString(arrayList)
}
```
이전에 joinToString 함수는 클래스 내에 작성하진 않았다. 단지 패키지만 import 해준다면 해당 메서드를 정적 메서드처럼 사용할 수 있다.
- 자바에서 사용할 경우, `Kotlin파일명(확장자포함).함수명` 으로 해당 메서드를 호출할 수 있음
  - 위와 같은 경우는 `FunctionKt.joinToString()`를 통해 호출 가능
  - `@JvmName(val name: String)`을 통해 파일명 대신 호출하기 원하는 이름으로 변경 가능

## 4) 최상위 프로퍼티
최상위 함수와 마찬가지로 프로퍼티 역시 클래스 외부에 선언 가능하다. 
```kotlin
var UNIX_LINE_SEPARATOR = "\n"
```
하지만 이는 var로 선언되었기 때문에 public setter 역시 만들어 진다. 값이 변하지 않는 상수로 설계하기에는 의도와 맞지 않다. 

```kotlin
const val UNIX_LINE_SEPARATOR = "\n"
```
따라서 위와 같이 const 변경자와 val를 통해서 public static final한 변수를 만들 수 있다.
- const는 원시타입과 String만 사용 가능함


# 2. 확장 함수와 확장 프로퍼티

## 1) 확장 함수
기존 코드와 코틀린 코드를 자연스럽게 통합하는 것은 코틀린의 핵심 목표이다. 
따라서 코틀린은 기존의 자바 코드를 확장할 수 있도록 확장 함수라는 편리한 기능을 제공한다.

### 확장 함수 문법
```kotlin
fun 수신객체.함수명([매개변수선언부])[: 리턴타입선언부] {
    함수본문
}
```
- 확장하려는 외부의 클래스(Java 표준 라이브러리 등)를 수신객체로 선언
- 확장 함수가 캡슐화를 깨지는 않음
  - 확장함수는 클래스의 private, protected 멤버에 접근하지 못함
  - 반면 클래스 내에 선언할 함수의 경우에는 모두 접근이 가능
- 확장 함수 내에서 수신 객체의 모든 메서드는 호출 가능(private, protected 필드만 불가능)

### 확장 함수 예시
```kotlin
fun String.lastChar(): Char = this[this.length - 1]

fun main(args: Array<String>) {
    println("Test".lastChar()) // t
}
```
위와 같이 String 타입인 다른 객체에서 lastChar()라는 사용자 정의 확장 함수를 사용할 수 있게 된다.
결국 Java의 String이라는 클래스를 직접 수정하지 않으면서(수정할 수도 없다) 기능을 확장할 수 있게 되었다.

### 확장 함수의 충돌
만약 확장 함수의 이름이 기존의 수신 객체에 선언된 함수와 동일한 이름을 가진다면 충돌이 발생할 수밖에 없다.
따라서 동일한 kt파일이 아니라면 import를 통해서만 확장함수를 호출할 수 있으며, 만약 이름이 동일한 메서드 두 개를 import할 경우에는 as 키워드를 통해 해결할 수 있다.

#### function 패키지 소속 ExtendFunction.kt
```kotlin
package function

fun String.lastChar(): Char = this[this.length - 1]
```

#### function.importtest 패키지 소속 ImportFunction.kt
```kotlin
package function.importtest

import function.lastChar

fun main(args: Array<String>) {
    "test".lastChar();
}
```
위와 같이 확장 함수가 정의된 패키지에 대해서 import를 해야 사용할 수 있다.

#### as
```kotlin
package function.importtest

import function.lastChar as last

fun main(args: Array<String>) {
  "test".last();
}
```
만약 String 클래스에 원래 lastChar()라는 메서드가 정의되어 있었다면, as를 사용해서 함수명을 지정할 수 있다.

### 자바에서 확장 함수 호출
내부적으로 확장 함수는 수신 객체를 첫 번째 인자로 받는 정적 메서드이다. 
따라서 Java에서는 다음과 같이 사용할 수 있다.
```java
char c = ExtendFunctionKt.lastChar("Java") 
```
이전에 ExtendFunction.kt 파일에 lastChar라는 확장 함수를 정의했기 때문에 위와 같이 사용할 수 있다.

### 확장 함수의 오버라이드
확장 함수는 오버라이딩을 할 수 없다. 기본적으로 정적으로 결정되기 때문이다.
확장 함수는 클래스의 일부가 아니다. 클래스 외부에 선언된다. 
따라서 확장 함수는 자바의 static 메서드 처럼 정적으로 결정이 되며, 이는 항상 동일한 메서드를 호출하기 때문에 오버라이딩이라는 개념 자체를 적용할 수가 없다.

## 2) 확장 프로퍼티
확장 함수와 마찬가지로 확장 프로퍼티도 일반 프로퍼티와 같은데, 수신 객체만 추가됐을 뿐이다.
다만 애초에 확장 프로퍼티는 상태를 저장할 수가 없다. 클래스 외부에 선언된 프로퍼티이기 때문에 뒷받침하는 필드가 없다.
따라서 기본 게터 구현 역시 제공하지 않는다. 커스텀 게터를 정의해주어야 한다.

# 3. 컬렉션 처리
## 1) 가변 길이 인자

### vararg
Java는 ... 을 통해 가변 인자 기능을 구현할 수 있었다. Kotlin은 vararg 키워드를 통해 가능하다.
예시로 Kotlin에서 리스트를 생성하는 함수를 살펴보자.
```kotlin
public fun <T> arrayListOf(vararg elements: T): ArrayList<T> =
  if (elements.size == 0) ArrayList() else ArrayList(ArrayAsCollection(elements, isVarargs = true))
```
보면 vararg를 통해 요소 여러 개를 가변적으로 받을 수 있도록 설계가 되어 있다.

### 스프레드 연산자
```kotlin
fun main(args: Array<String>) {
  val postArray = arrayOf(5, 6, 7, 8)
  val list = listOf(*postArray)

  for (i in list){
    println(i) // 5, 6, 7, 8
  }
}
```
코틀린은 가변 인자를 스프레드 연산자 `*`과 함께 지정해 주어야 한다.

```kotlin
  val postArray = arrayOf(5, 6, 7, 8)
  val list = listOf(postArray)

  for (i in list){
    println(i) // [Ljava.lang.Integer;@53d8d10a
  }
```
만약 스프레드 연산자를 사용하지 않는다면 가변 인자로 인식하지 않는다. 따라서 위와 같이 Array타입의 List를 생성해버린다.
그래서 Array의 주소 하나가 출력이 되어버리는 것이다.

```kotlin
  val postArray = arrayOf(5, 6, 7, 8)
  val list = listOf(1, 2, 3, 4, *postArray)

  for (i in list){
    println(i) // 1, 2, 3, 4, 5, 6, 7, 8
  }
```
하지만 자바에서는 불가능한 일도 가능하다. 기본적으로 자바의 경우 가변 인자로 지정한 경우 어떤 건 가변 인자로, 어떤 건 배열로 넘겨줄 수가 없다. 
우선 가변 인자로 넘겨주었다면 배열로 가변 인자로 넘겨주는 것은 불가능하다. 무조건 둘 중에 하나를 선택해야 한다.
하지만 코틀린에서는 스프레드 연산자를 통해 중간에 가변 인자로 배열을 넘겨줄 수가 있다.
따라서 가변 인자를 사용하다가 스프레드 연산자로 배열의 요소를 펼치는 것이 가능하다.

## 2) 중위 호출과 구조 분해 선언
코틀린에서 Map을 만들기 위해서 아래와 같이 작성할 수 있다.
```kotlin
val map = mapOf(1 to "one", 7 to "seven",)
```
여기서 `to`는 특별한 키워드가 아니다. 사실 중위 호출(infix call)이라는 방식으로 일반 메서드 to를 호출한 것이다. 이 코드는 아래와 같다.

```kotlin
public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

val map = mapOf(1.to("one"), 7.to("seven"))
```
- 인자가 하나뿐인 일반 메서드나 확장 함수에 중위 호출을 사용할 수 있음
- 중위 호출을 허용하려면 함수에 infix 변경자를 선언해야 함
- 이런 기능을 구조 분해 선언(destructuring declaration)이라고 함

# 4. 로컬 함수

## 1) 기본 로컬 함수
로컬 함수는 함수 내에 선언된 함수를 의미한다. 이를 사용하면 함수 내의 중복적인 코드를 줄일 수 있다.
예시로 DB에 저장하기 전에 필드 값을 검증하는 메서드를 작성해보자.
```kotlin
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
```
로컬 함수로 validate를 선언하지 않았다면, validate 함수 내에 작성된 로직이 두 배로 늘었을 것이다. 지금은 필드가 두 개이지만, 필드가 늘어난다면 중복 역시 증가할 것이다.
그리고 로컬 함수는 외부 함수의 파라미터에도 접근이 가능하다. 따라서 로컬 함수 내부에서 User타입의 파라미터를 받지 않았지만, 예외 메시지에서 사용할 수 있게 되었다.

## 2) 확장 함수의 로컬 함수
```kotlin
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

fun saveUser(user: User) {
  user.validateBeforeSave()
}
```
확장 함수 안에도 역시 로컬 함수를 선언할 수 있다.
확장 함수는 해당 클래스 내에 선언하지 않아도 클래스의 함수처럼 사용할 수 있도록 하는 함수였다. 
따라서 실제로 해당 클래스 멤버에 접근할 수 있었고, 이제 validateBeforeSave() 메서드는 더 이상 User를 파라미터로 받지 않아도 된다.
확장 함수를 로컬 함수로 정의할 수도 있다. 즉, saveUser(User)내에 User.validateBeforeSave() 메서드를 로컬 함수로 정의할 수도 있지만, 이는 중첩된 함수의 깊이가 깊어지기 때문에 권장하지 않는 방법이다.

## 정리
- 로컬 함수: 함수 내에 선언된 함수
- 로컬 함수는 외부 함수의 파라미터에 접근할 수 있음
- 확장 함수 내에 로컬 함수를 선언할 수 있음
