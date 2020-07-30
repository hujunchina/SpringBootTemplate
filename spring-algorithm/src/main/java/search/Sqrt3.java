package search;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/19 9:18 下午
 * @Version 1.0
 * 使用算法思想求根号3， 二分法思想，不停的找diff和3中间的值，直到小于end
 */
public class Sqrt3 {

    // 牛顿迭代法
    public void solution() throws InterruptedException {
        double end = 0.0000001;
        double diff = 3.0;
        diff = diff/2 + 3.0/(2 * diff);
        while (Math.abs(diff*diff-3.0) > end) {
            diff = diff/2 + 3.0/(2 * diff);
            System.out.println(diff);
            Thread.sleep(500);
        }
    }

    public void binary() throws InterruptedException {
        double end = 0.0000001;
        double left = 1.0 , right = 3.0;
        while (left < right) {
            double middle = (right - left) / 2;

            if (Math.abs(middle*middle - 3.0) < end) {
                right = middle-end;
            } else {
                left = middle+end;
            }

            System.out.println(middle);
            Thread.sleep(500);
        }
        System.out.println(Math.abs(right-left));
    }

    public static void main(String[] args) throws InterruptedException {
        Sqrt3 sqrt3 = new Sqrt3();
//        sqrt3.solution();
        sqrt3.binary();
    }
}
