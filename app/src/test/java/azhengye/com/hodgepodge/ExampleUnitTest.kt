package azhengye.com.hodgepodge

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    
    @Test
    fun selectSort(){
        val data = intArrayOf(4, 2, 1, 20, 5, 9, 7, 3)
        for (i in 0 until data.size){
           var minIndex = i
            for (j in i+1 until data.size){
                if (data[j]<data[minIndex]){
                    minIndex = j
                }
            }
            if (minIndex!=i){
                var temp = data[i]
                data[i] = data[minIndex]
                data[minIndex]=temp
            }
            
        }
        System.out.println("data===" + Arrays.toString(data))
    }
    
    @Test
    fun insertSort(){
        val data = intArrayOf(4, 2, 1, 20, 5, 9, 7, 3)
        for (i in 1 until data.size){
            var j = i -1
            var temp = data[i]
            while (j>=0 && temp<data[j]){
                data[j+1] = data[j]
                j--
            }
            
            data[j+1] = temp
        }
        

        System.out.println("data===" + Arrays.toString(data))
    }
    
    @Test
    fun fastSort(){
        val data = intArrayOf(1,2,3,4)
        fastsort(data,0,data.size-1)
        
        System.out.println("data===" + Arrays.toString(data))
    }

    private fun fastsort(data: IntArray, start: Int, end: Int) {
            if (start<end){
                var left = start
                var right = end
                var value = data[left]
                while (left<right) {
                    while (left < right && data[right] >= value) {
                        right--
                    }
                    if (left < right) {
                        data[left] = data[right]
                        left++
                    }

                    while (left < right && data[left] < value) {
                        left++
                    }

                    if (left < right) {
                        data[right] = data[left]
                        right--
                    }
                }
                
                data[left] =value
                
                fastsort(data,start,left-1)
                fastsort(data,left+1,end)
            }
    }
    
    @Test
    public fun testMode() {
        val num = 1383;
        var a = num / 1000;
        System.out.println("a=="+a)
        
        System.out.println("num/100=="+num/100F)
        
        var b = num/100%10
        System.out.println("b=="+b)
        var c = num/10%10
        System.out.println("c=="+c)
        var d = num%10;
        System.out.println("d=="+d)

    }
}
