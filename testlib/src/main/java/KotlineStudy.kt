import jdk.nashorn.internal.objects.Global.println

/**
 * @author wxl
 * @date  on 2017/10/27.
 */
class Runoob{
    fun main(args:Array<String>){
        val a = arrayOf(1,2,3)
        val b = Array(3,{i->(i*2)})
        val x:IntArray= intArrayOf(2,3,4)
        x[0] = x[1]+x[2]
    }
}

