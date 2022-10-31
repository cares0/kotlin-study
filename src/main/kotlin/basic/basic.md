# 1. 함수와 변수

## 1) 함수

### 문법
```kotlin
fun 함수명(파라미터명 :타입, ...): 반환타입 {
    함수본문
}
```
- fun 키워드를 통해 함수를 선언
- 파라미터명 뒤에 파라미터 타입을 지정
- 자바와 달리 클래스 안에 함수가 있지 않아도 됨

### 예시
```kotlin
fun main(args: Array<String>) {
    println("Hello, world!")
}
```
- 자바와 달리 배열 처리를 위한 문법이 따로 존재하지 않음
- System.out.println 대신 자바 표준 라이브러리 함수를 간결하게 사용할 수 있게 감싼 레퍼인 println 함수 사용 가능
- 세미콜론을 붙이지 않아도 됨

### 문과 식의 구분
- 식(expression): 값을 만들어 내며 다른 식의 하위 요소로 계산에 참여할 수 있다.
- 문(statement): 둘러싸고 있는 가장 안쪽 블록의 최상위 요소로 존재하며 아무런 값을 만들어내지 않는다.
- 자반에서는 모든 제어 구조가 `문`이지만 코틀린에서는 루프를 제외한 대부분의 제어 구조가 `식`이다.

```kotlin
// 블록이 본문인 함수
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

// 식이 본문인 함수
fun max(a: Int, b: Int) = if (a > b) a else b
```
- 블록이 본문인 함수: 본문이 중괄호로 둘러싸인 함수
- 식이 본문인 함수: 등호와 식으로 이루어진 함수
  - 식이 본문인 함수의 경우에는 반환타입, return 키워드 생략 가능
  - 기본적으로 식은 결과 타입이 하나이기에 타입 추론이 가능하기 때문

## 2) 변수

### 문법
```kotlin
var/val 변수명[: 변수타입] [= 초기화식]
```
- var: 변경 가능한 변수
- val: 변경 불가능한 변수(자바의 final 변수)
- 변수 선언과 동시에 초기화를 할 경우 타입은 생략 가능(타입 추론)
- 변수를 선언만 할 경우 타입 추론이 불가능하기에 타입 생략 불가능

### 예시
```kotlin
val name = "cares"
val age = 26

var number: Int
number = 50
```
- 기본 전략은 val 변수를 사용하되, 변경이 필요한 변수는 var로 수정하는 것을 권장
- val는 참조 자체만 불변, 객체의 내부 값을 변경하는 것은 가능함

## 3) 문자열 템플릿
### 문법
```kotlin
변수명 만으로 해결되는 경우: $변수명
변수명 만으로 해결되지 않는 경우: ${식}
```

### 예시
```kotlin
// 변수명 만으로 해결되는 경우
val name = "name"
println("hello $name")

// 변수명 만으로는 해결되지 않을 경우
val age = 10
println("age ${age + 1}")
```
- 변수명 만으로 해결되더라도 ${}로 묶는 방식을 권장(코드의 가독성 증가)
- 문자열 템플릿 안에 또 다른 문자열 템플릿 사용 가능

## 4) 클래스와 프로퍼티

### 클래스 선언
간단하게 자바에서 선언한 클래스를 코틀린의 클래스로 바꾸어보자.

#### Java
```java
public class Person {
    private String name;
    private Boolean isMarried;
    
    public String getName() {
        return this.name;
    }
    
    public void setMarried(Boolean isMarried) {
        this.isMarried = isMarried;
    }
    
    public Boolean isMarried() {
        return this.isMarried;
    }
}
```

#### Kotlin
```kotlin
class Person(
  val name: String,
  var isMarried: Boolean
)
```
위 두 코드는 동일한 코드이다.
- 프로퍼티: 자바에서 필드와 접근자(게터, 세터)를 묶어 지칭하는 말
- 코틀린에서는 접근자를 통해 클래스 프로퍼티를 선언할 수 있음
  - val: private 필드, public getter 생성
  - var: private 필드, public getter/setter 생성

### 프로퍼티 사용
```kotlin
val person = Person("Cares", false)
person.name = "Jun"
println(person.name) // "Jun"

person.isMarried = true
```
- Java에서 마치 public 필드에 접근하듯이 getter/setter를 호출할 수 있음
- Java에서는 불린타입일 경우 isMarried/setMarried를 통해 접근/수정하지만, 코틀린은 그냥 필드 명으로 접근/수정이 가능

### 커스텀 접근자
만약 getter/setter 내에 로직이 필요하다면 커스텀 접근자를 통해 해결할 수 있다.
```kotlin
class Rectangle(val height: Int, val width: Int) {
  val isSquare: Boolean
    get() { // isSquare 필드의 커스텀 getter 선언
      return height == width
    }

  // 일반 함수로 선언하는 것도 하나의 방법
  fun isSquare() = height == width
}
```
- 커스텀 게터, 다른 함수로 정의하는 방법 간의 차이는 가독성밖에 없음 (성능 차이는 없음)
  - 일반적으로 클래스의 특성을 정의하고 싶다면 커스텀 게터를 정의하는 것으 좋음

### 코틀린의 디렉터리와 패키지

#### package, import
```kotlin
package sample.test
import java.util.Random

class NumberStore(val num1: Int, val num2: Int) {
    val isEqual: Boolean
      get() = num1 == num2
}

fun createRandomNums(): Test {
  val random = Random()
  return NumberStore(random.nextInt(), random.nextInt())
}
```
- package 문을 통해 해당 파일의 모든 선언(클래스, 함수, 프로퍼티)를 패키지에 포함시킬 수 있음
- 자바는 기본적으로 파일 하나에 파일 명과 동일한 하나의 클래스(내부 클래스 제외)만 선언할 수 있지만, 코틀린은 파일명과 상관없이 여러 클래스, 함수, 프로퍼티를 선언할 수 있음
- import 문을 통해 해당 패키지 안의 클래스, 함수, 프로퍼티에 접근할 수 있음 (자바 라이브러리도 가능함)

#### 예시
```kotlin
package sample.util

import sample.test.createRandomNums //(* 사용 가능)

fun testFunction() {
    println(createRandomNums().isEqual)
}
```
- 코틀린은 하나의 파일에 하나의 클래스 개념이 아님
- 패키지에 포함된 클래스, 함수, 프로퍼티명을 import 문을 통해 접근 가능
- 다만 Java와의 상호운용성을 위해 자바의 디렉터리, 패키지, 클래스 구조를 사용하는 것을 권장함

---

# 2. Enum과 When

## 1) Enum

### Enum 클래스 정의
```kotlin
enum class Color {
    RED, ORANGE, YELLOW, GREEN
}
```
- enum: 소프트 키워드 중 하나, class 앞에 선언될 경우에만 특별한 의미를 가짐
  - class는 키워드, enum은 소프트 키워드
  - 따라서 class는 다른 곳에서 함수명, 변수명 등으로 사용이 불가능하지만 enum은 사용 가능

### Enum 클래스에 프로퍼티, 메서드 선언
```kotlin
enum class Color(
  val r: Int, val g: Int, val b: Int
) {
  RED(255, 0, 0), ORANGE(255, 165, 0), 
  YELLOW(255, 255, 0), GREEN(0, 255, 0);
  // 상수 목록과 메서드 사이에는 ;가 들어가야 함
  fun rgb() = (r * 256 + g) * 256 + b
}
```

## 2) When
- Java의 switch문과 비슷하지만 훨씬 더 많은 기능을 제공해주는 코틀린 구성 요소

### when 기본 문법
```kotlin
fun getInitial(color: Color) =
  when (color) {
    Color.RED -> "R"
    Color.ORANGE -> "O"
    Color.YELLOW -> "Y"
    Color.GREEN -> "G"
  }
```
- Java와 달리 break 문을 사용하지 않음
- when 역시 식이기 때문에 식이 본문인 함수로 선언 가능

### when 분기의 다중 조건
```kotlin
fun getInitial(color: Color) =
    when (color) {
        Color.RED, Color.ORANGE -> "RO"
        Color.YELLOW -> "Y"
    }
```
- `,` 를 통해 분기 조건을 다중으로 설정할 수 있음

### when의 인자, 조건이 객체인 경우
```kotlin
fun getMix(c1: Color, c2: Color) =
    // setOf: Set 타입으로 만들어주는 메서드
    when (setOf(c1, c2)) {
        // 두 색의 순서 상관 없이 요소가 모두 동일하다면 분기에 맞게 반환함
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        else -> throw Exception("Illegal Color")
    }
```
- Java는 상수만 인자, 조건이 가능하지만 Kotlin은 객체(식)도 가능함
- Java의 default 처럼 나머지 경우를 지정하고 싶은 경우에는 else 사용

### when의 인자를 파라미터로 선언할 경우
```kotlin
fun Request.getBody() =
    when (val response = executeRequest()) {
        is Success -> response.body
        is HttpError -> throw HttpException(response.status)
    }
```
- when의 인자를 파라미터로 선언할 수도 있음

### when의 인자가 없는 경우
```kotlin
fun mixOptimized(c1: Color, c2: Color) =
    when {
        (c1 == RED && c2 = YELLOW) ||
        (c1 == YELLOW && c2 = RED) 
            -> ORANGE
    }
```
- 인자가 없기 때문에 각 분기의 조건은 Boolean을 계산하는 식이여야 함

### if, when에서 블록 사용
```kotlin
fun getInitial(color: Color) =
    when (color) {
        Color.RED, Color.ORANGE -> {
          print("RED, ORANGE")
          "RO"
        }
        Color.YELLOW -> {
          print("YELLOW")
          "Y"
        }
    }
```
- if, when 삭에 블록을 사용할 수 있음
- 블록인 경우에는 항상 맨 마지막 코드 라인이 반환값이

---

# 3. 타입 캐스팅

## 1) 스마트 캐스트

### 타입 검사와 타입 캐스트의 조합
자바는 기본적으로 `instanceof` 연산자와 결합하여 안전하게 타입 캐스팅을 진행한다.
```java
public interface Car {}

public class SportsCar implements Car {
    private Integer powerSpeed;
}

public class NormalCar implements Car {
  private Integer speed;
}

public class Test {
    public void printCarSpeed(Car car) {
        if (car instanceof SportsCar) {
            SportsCar sportsCar = (SportsCar) car;
            System.out.println(sportsCar.getPowerSpeed());
        } else if (car instanceof NormalCar) {
            NormalCar sportsCar = (NormalCar) car;
            System.out.println(sportsCar.getSpeed());
        }
    }
}
```

반면 코틀린은 `is`를 통해 타입 확인 + 캐스팅까지 한번에 가능하다.

```kotlin
interface Car

class SportsCar(val powerSpeed: Int) : Car
class NormalCar(val speed: Int) : Car

fun calculateSpeed(car: Car): Int =
  when (car) {
    is SportsCar -> {
      println("is Sports Car")
      car.powerSpeed = 1000
    }
    is NormalCar -> {
      println("is Normal Car")
      car.speed = 100
    }
  }
```
when 블럭 안에 캐스팅을 진행하는 부분이 없음에도 각각의 타입 확인을 한 클래스의 프로퍼티에 접근할 수 있음을 확인할 수 있다.
- 클래스의 프로퍼티에 대해 스마트 캐스트를 사용하기 위해서 해당 프로퍼티는 반드시 val로 선언해야 함
- val이지만 커스텀 접근자를 사용할 경우 프로퍼티에 대한 접근이 항상 같은 값을 내놓는다고 확신할 수 없기에 스마트 캐스트 사용 불가능

## 2) 명시적 캐스팅
타입 확인을 하지 않고 명시적으로 캐스팅할 경우 `as` 키워드를 사용하면 된다.
```kotlin
val normalCar: NormalCar = car as NormalCar
```

---

# 4. 이터레이션

## 1) while
기본적으로 Java의 while, do-while문과 문법이 동일하다.

## 2) for

### range
Java의 기본적인 for 루프는 변수 초기화, 변수 갱신, 루프 조건이 거짓이 될 때까지 반복하는데, Kotlin은 이에 해당하는 요소가 없다.
대신 Kotlin에서는 범위(range)를 사용한다.

```kotlin
var range = 1..10

for (i in range) {
  println(i) // 1, 2, 3, ..., 10
}
```
- `..` 연산자를 통해 범위(수열)를 생성할 수 있음

### downTo
역방향 범위를 만들기 위해서는 .. 연산 대신 downTo를 사용하면 된다.
```kotlin
for (i in 10 downTo 1) {
    println(i) // 10, 9, 8, ..., 1
}
```

### step
수열의 공차를 1이 아닌 다른 값으로 만들기 위해서 step을 사용할 수 있다.
```kotlin
for (i in 1..10 step 2) {
    println(i) // 1, 3, 5, 7, 9
}
```

### 컬렉션의 반복
기본적으로 Java의 foreach문과 동일하다.
```kotlin
data class Test(val name: String, val age: Int)

fun main(args: Array<String>) {
  val t1 = Test ("name1", 10)
  val t2 = Test ("name2", 20)
  
  for (i in listOf(t1, t2)) {
    println("i = ${i}")
  }
}
```
Java처럼 컬렉션의 요소 하나씩 변수 i에 들어간다.

### 맵에 대한 이터레이션
```kotlin
fun main(args: Array<String>) {

  val map = TreeMap<Char, String>();

  for (c in 'A'..'F') {
    val binary = Integer.toBinaryString(c.code)
    map[c] = binary;
  }

  for ((letter, binary) in map) {
    println("$letter = $binary") 
    // A = 1000001
    // B = 1000010
    // C = 1000011
    // D = 1000100
    // E = 1000101
    // F = 1000110
  }
}
```
- `..` 연산자는 숫자 뿐만 아니라 문자 타입의 값에도 적용 가능함
- Kotlin은 Map타입의 요소에 접근할 때 map[key], map[key] = value를 통해 put과 get을 대신할 수 있음
- in에 Map타입을 지정할 경우 for문 변수로 key, value를 선언할 수 있음

### List의 인덱스와 함께 사용

```kotlin
fun withIndex() {
  val list = arrayListOf("A", "B", "C")

  for ((index, element) in list.withIndex()) {
      println("${index} = ${element}")
  }
}

```
- `withIndex()` 함수를 통해 리스트의 인덱스를 for문에서 사용 가능

### in으로 컬렉션이나 범위의 원소 검사
```kotlin
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A' .. 'Z'
fun isNotDigit(c: Char) = c !in '0' .. '9'

fun main(args: Array<String>) {
    println(isLetter('K')) // true
    println(isNotDigit('f')) // true

}
```
- in은 범위를 나타내기 때문에 해당 범위에 속하는지 판단도 가능
- 범위는 숫자, 문자에만 국한되지 않음, Comparable을 구현한 클래스라면 사용 가능

```kotlin
println("Kotlin" in "Java".."Scala") // true
// "Java" <= "Kotlin" && "Kotlin" <= "Scala" 와 같음
```

# 5. 예외 처리

## 1) try, catch, finally

```kotlin
fun readNumber(reader: BufferedReader) {
  val number = try {
    Integer.parseInt(reader.readLine())
  } catch (e: NumberFormatException) {
    return
  } finally {
    println("exit")
  }
}
```
- 기본적으로 Java의 try-catch-finally 문과 실행 방식이 동일함
- Kotlin은 모든 예외가 Unchecked Exception임(Java의 Checked Exception도 포함), 따라서 메서드 뒤에 throws문 자체가 없음
- try-catch-finally는 식임, 따라서 문에서 마지막으로 선언된 부분이 식의 결과값이 되고, 직접 변수에 대입도 가능함
  - try문에서 예외가 발생하지 않았다면 try문의 마지막 값이, 예외가 발생했다면 catch문의 마지막 값이 식의 결과값이 됨
  - try문에서 예외가 발생했지만 catch에서 따로 값을 반환하지 않으면 식의 결과는 없음

