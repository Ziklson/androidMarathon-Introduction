fun start(): String = "OK"

fun joinOptions(options: Collection<String>) =
        options.joinToString(separator=", ", prefix="[", postfix="]")
        

fun foo(name: String, number: Int = 42, toUpperCase: Boolean = false) =
        (if (toUpperCase) name.uppercase() else name) + number

fun useFoo() = listOf(
        foo("a"),
        foo("b", number = 1),
        foo("c", toUpperCase = true),
        foo(name = "d", number = 2, toUpperCase = true)
)

const val question = "life, the universe, and everything"
const val answer = 42

val tripleQuotedString = """
    #question = "$question"
    #answer = $answer""".trimMargin("#")

fun main() {
    println(tripleQuotedString)
}


val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern(): String = """\d{2} ${month} \d{4}"""


fun sendMessageToClient(
        client: Client?, message: String?, mailer: Mailer
) {
    if (client == null || message == null)
    	return
    val personalInfo = client.personalInfo
    val email = personalInfo?.email
    email?.let{it ->
        mailer.sendMessage(it, message)
    }
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}



import java.lang.IllegalArgumentException

fun failWithWrongAge(age: Int?): Nothing {
    throw IllegalArgumentException("Wrong age: $age")
}

fun checkAge(age: Int?) {
    if (age == null || age !in 0..150) failWithWrongAge(age)
    println("Congrats! Next year you'll be ${age + 1}.")
}

fun main() {
    checkAge(10)
}



fun containsEven(collection: Collection<Int>): Boolean =
        collection.any { it -> it % 2 == 0 }
        
        
data class Person(
	val name: String, 
    val age: Int
)

fun getPeople(): List<Person> {
    return listOf(Person("Alice", 29), Person("Bob", 31))
}

fun comparePeople(): Boolean {
    val p1 = Person("Alice", 29)
    val p2 = Person("Alice", 29)
    return p1 == p2  // should be true
}


fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
            else -> throw IllegalArgumentException("Unknown expression")
        }

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr



fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
        }

sealed interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr






import kotlin.random.Random as KRandom
import java.util.Random as JRandom

fun useDifferentRandomClasses(): String {
    return "Kotlin random: " +
            KRandom.nextInt(2) +
            " Java random:" +
            JRandom().nextInt(2) +
            "."
}


fun Int.r(): RationalNumber = RationalNumber(this, 1)

fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(first, second)

data class RationalNumber(val numerator: Int, val denominator: Int)



data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
	override operator fun compareTo(other:MyDate):Int{
      if(this.year < other.year){
          return -1
      } else if(this.year > other.year){
          return 1
      }
      if(this.month < other.month){
          return -1
      } else if(this.month > other.month){
          return 1
      }
      if(this.dayOfMonth < other.dayOfMonth){
          return -1
      } else if(this.dayOfMonth > other.dayOfMonth){
          return 1
      } else
        return 0
    }
}

fun test(date1: MyDate, date2: MyDate) {
    // this code should compile:
    println(date1 < date2)
}


fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in first..last
}


class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate>{
    override fun iterator(): Iterator<MyDate>{
       return object : Iterator<MyDate> {
            var current_date: MyDate = start
            override fun next(): MyDate {
                if (!hasNext()) throw NoSuchElementException()
                val result = current_date
                current_date = current_date.followingDate()
                return result
            }
            override fun hasNext(): Boolean = current_date <= end
        }
    }
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

import TimeInterval.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

// Supported intervals that might be added to dates:
enum class TimeInterval { DAY, WEEK, YEAR }

data class CompositeTimeInterval(val timeInterval: TimeInterval, val amount: Int)

operator fun TimeInterval.times(amount: Int): CompositeTimeInterval = CompositeTimeInterval(this, amount)

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = this.addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(compositeTimeInterval: CompositeTimeInterval): MyDate = this.addTimeIntervals(compositeTimeInterval.timeInterval, compositeTimeInterval.amount)

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}


class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
        numberOfInvocations++
        return this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()


fun Shop.getSetOfCustomers(): Set<Customer> = customers.toSet()

// Return a list of customers, sorted in the descending by number of orders they have made
fun Shop.getCustomersSortedByOrders(): List<Customer> = 
    customers.sortedByDescending{it.orders.size}
    
    

// Find all the different cities the customers are from
fun Shop.getCustomerCities(): Set<City> =
        this.customers.map { 
            it.city 
        }.toSet()

// Find the customers living in a given city
fun Shop.getCustomersFrom(city: City): List<Customer> =
          this.customers.filter { 
            it.city == city 
        }
          
          
          
// Return true if all customers are from a given city
fun Shop.checkAllCustomersAreFrom(city: City): Boolean =
        customers.all { it.city == city }

// Return true if there is at least one customer from a given city
fun Shop.hasCustomerFrom(city: City): Boolean =
        customers.any { it.city == city }

// Return the number of customers from a given city
fun Shop.countCustomersFrom(city: City): Int =
        customers.count { it.city == city }

// Return a customer who lives in a given city, or null if there is none
fun Shop.findCustomerFrom(city: City): Customer? =
        customers.find { it.city == city }



// Build a map from the customer name to the customer
fun Shop.nameToCustomerMap(): Map<String, Customer> =
         this.customers.associateBy {
            it.name
        }

// Build a map from the customer to their city
fun Shop.customerToCityMap(): Map<Customer, City> =
           this.customers.associateWith {
            it.city
        }

// Build a map from the customer name to their city
fun Shop.customerNameToCityMap(): Map<String, City> =
         this.customers.associate {
            it.name to it.city
        }
         
         // Build a map that stores the customers living in a given city
fun Shop.groupCustomersByCity(): Map<City, List<Customer>> =
          this.customers.groupBy{
            it.city
        }
          
          
          // Return customers who have more undelivered orders than delivered
fun Shop.getCustomersWithMoreUndeliveredOrders(): Set<Customer> =  
    this.customers.filter {
        val (delivered, undelivered) = it.orders.partition { it.isDelivered }
        undelivered.size > delivered.size
    }.toSet()
         
      
      
      // Return all products the given customer has ordered
fun Customer.getOrderedProducts(): List<Product> =
        this.orders.flatMap {
            it.products
        }

// Return all products that were ordered by at least one customer
fun Shop.getOrderedProducts(): Set<Product> =
        this.customers.flatMap{
            it.getOrderedProducts()
        }.toSet()
        
        
        
        // Return a customer who has placed the maximum amount of orders
fun Shop.getCustomerWithMaxOrders(): Customer? =
        this.customers.maxByOrNull{
            it.orders.size
        }

// Return the most expensive product that has been ordered by the given customer
fun getMostExpensiveProductBy(customer: Customer): Product? =
        customer.orders.flatMap {
            it.products
        }.maxByOrNull{
            it.price
        }
        
       
       
       // Return the sum of prices for all the products ordered by a given customer
fun moneySpentBy(customer: Customer): Double =
        customer.orders.flatMap {
            it.products
        }.sumOf{
            it.price
        }
        
        
        
        // Return the set of products that were ordered by all customers
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val allProducts = customers.flatMap {
        it.getOrderedProducts() 
    }.toSet()
    
    return customers.fold(allProducts) { orderedByAll, customer ->
        orderedByAll.intersect(customer.getOrderedProducts())
    }
}
fun Customer.getOrderedProducts(): List<Product> =
    this.orders.flatMap {
            it.products
    }
    
    
    // Find the most expensive product among all the delivered products
// ordered by the customer. Use `Order.isDelivered` flag.
fun findMostExpensiveProductBy(customer: Customer): Product? {
    return customer.orders.filter{
       it.isDelivered
   }.flatMap{
       it.products
   }.maxByOrNull{
       it.price
   }
}

// Count the amount of times a product was ordered.
// Note that a customer may order the same product several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int {
    return customers.flatMap { 
        it.orders.flatMap { 
            it.products 
        }
    }.count{
        it == product
    }
}

fun Customer.getOrderedProducts(): List<Product> =
        this.orders.flatMap {
            it.products
        }
        
        
        
        // Find the most expensive product among all the delivered products
// ordered by the customer. Use `Order.isDelivered` flag.
fun findMostExpensiveProductBy(customer: Customer): Product? {
    return customer.orders.filter{
       it.isDelivered
   }.flatMap{
       it.products
   }.maxByOrNull{
       it.price
   }
}

// Count the amount of times a product was ordered.
// Note that a customer may order the same product several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int {
    return customers.flatMap { 
        it.orders.flatMap { 
            it.products 
        }
    }.count{
        it == product
    }
}

fun Customer.getOrderedProducts(): List<Product> =
        this.orders.flatMap {
            it.products
        }
        
        
        
       fun doSomethingWithCollection(collection: Collection<String>): Collection<String>? {

    val groupsByLength = collection.groupBy { s -> s.length }

    val maximumSizeOfGroup = groupsByLength.values.map { group -> group.size }.maxOrNull()

    return groupsByLength.values.firstOrNull { group -> group.size == maximumSizeOfGroup  }
}
       
       class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value){
            field = value
            counter += 1
        }
}
       
       class LazyProperty(val initializer: () -> Int) {
    var value: Int? = null
    val lazy: Int
        get() {
           if (value == null) {
                value = initializer()
            }
            return value!!
        }
}
       
       class LazyProperty(val initializer: () -> Int) {
    val lazyValue: Int by lazy(initializer)}
       
       
       import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class D {
    var date: MyDate by EffectiveDate()
}

class EffectiveDate<R> : ReadWriteProperty<R, MyDate> {

    var timeInMillis: Long? = null

    override fun getValue(thisRef: R, property: KProperty<*>): MyDate {
                return timeInMillis!!.toDate()
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: MyDate) {
                timeInMillis = value.toMillis()
    }
}

fun task(): List<Boolean> {
      val isEven: Int.() -> Boolean = { this % 2 == 0}
    val isOdd: Int.() -> Boolean = { this % 2 == 1}

    return listOf(42.isOdd(), 239.isOdd(), 294823098.isEven())
}

import java.util.HashMap

fun <K, V> buildMutableMap(build: HashMap<K, V>.() -> Unit): Map<K, V> {
    val map = HashMap<K, V>()
    map.build()
    return map
}

fun usage(): Map<Int, String> {
    return buildMutableMap {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}


fun <T> T.myApply(f: T.() -> Unit): T {
    f()
    return this
}

fun createString(): String {
    return StringBuilder().myApply {
        append("Numbers: ")
        for (i in 1..10) {
            append(i)
        }
    }.toString()
}

fun createMap(): Map<Int, String> {
    return hashMapOf<Int, String>().myApply {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}
fun <T> T.myApply(f: T.() -> Unit): T {
    f()
    return this
}

fun createString(): String {
    return StringBuilder().myApply {
        append("Numbers: ")
        for (i in 1..10) {
            append(i)
        }
    }.toString()
}

fun createMap(): Map<Int, String> {
    return hashMapOf<Int, String>().myApply {
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}

import Answer.*

enum class Answer { a, b, c }

val answers = mapOf<Int, Answer?>(
    1 to c, 2 to b, 3 to b, 4 to c
)
     
     
     
     open class Tag(val name: String) {
    protected val children = mutableListOf<Tag>()

    override fun toString() =
            "<$name>${children.joinToString("")}</$name>"
}

fun table(init: TABLE.() -> Unit): TABLE {
    val table = TABLE()
    table.init()
    return table
}

class TABLE : Tag("table") {
    fun tr(init: TR.() -> Unit) {
        val tr = TR()
        tr.init()
        children += tr
    }
}

class TR : Tag("tr") {
    fun td(init: TD.() -> Unit) {
        children += TD().apply(init)
    }
}

class TD : Tag("td")

fun createTable() =
        table {
            tr {
                repeat(2) {
                    td {
                    }
                }
            }
        }

fun main() {
    println(createTable())
    //<table><tr><td></td><td></td></tr></table>
}

import java.util.*

fun <T, C : MutableCollection<T>> Collection<T>.partitionTo(first: C, second: C, predicate: (T) -> Boolean): Pair<C, C> {
    for (element in this) {
        if (predicate(element)) {
            first.add(element)
        } else {
            second.add(element)
        }
    }
    return Pair(first, second)
}

fun partitionWordsAndLines() {
    val (words, lines) = listOf("a", "a b", "c", "d e")
            .partitionTo(ArrayList(), ArrayList()) { s -> !s.contains(" ") }
    check(words == listOf("a", "c"))
    check(lines == listOf("a b", "d e"))
}

fun partitionLettersAndOtherSymbols() {
    val (letters, other) = setOf('a', '%', 'r', "}")
            .partitionTo(HashSet(), HashSet()) { c -> c in 'a'..'z' || c in 'A'..'Z' }
    check(letters == setOf('a', 'r'))
    check(other == setOf('%', "}"))
}
        
        
        
        
        
        