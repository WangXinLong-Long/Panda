package test

/**
 * @author wxl
 * @date  on 2017/11/2.
 */
//dev
class Person{
    var lastName:String="zhang"
    get() { return field.toUpperCase() }
    set

    var no:Int = 100
    get() = field
    set(value) {
        if (value <10){
            field = value
        }else{
            field = -1
        }
    }

    var height:Float = 145.4f
    private set
}
fun main(agrs:Array<String>){
    var person:Person = Person()
    person.lastName = "wang"
    println("lastName:${person.lastName}")

    person.no = 9
    println("no:${person.no}")

    person.no=20
    println("no:${person.no}")
}