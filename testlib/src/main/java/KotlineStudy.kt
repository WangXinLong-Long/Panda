import jdk.nashorn.internal.objects.Global.println

/**
 * @author wxl
 * @date  on 2017/10/27.
 */
//master
class Runoob {
    //TODO:Kotlin的主函数在哪里
    fun main(args: Array<String>) {
        val a = arrayOf(1, 2, 3)
        val b = Array(3, { i -> (i * 2) })
        val x: IntArray = intArrayOf(2, 3, 4)
        x[0] = x[1] + x[2]
    }

    fun xin(){
        print("hao")
    }

    //哥们新添加了一行注释，指针就向前了
    //巧了，新的注释就放在这里
}

