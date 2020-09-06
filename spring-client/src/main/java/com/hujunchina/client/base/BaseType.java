package com.hujunchina.client.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/3 5:04 下午
 * @Version 1.0
 * Java基本的数据类型
 */
public class BaseType {
    public static void main(String[] args) {
//        enumType();
        parameterVariable(3, "hujun", "love", "liujihong");
    }

    public static void simpleTime(){
        int hour=0, minute=0, second=0;
        LocalDateTime dateAndTime = LocalDateTime.now();
        LocalDate currentDate = LocalDate.from(dateAndTime);
        LocalTime timeToSet = LocalTime.of(hour, minute, second);
        dateAndTime = LocalDateTime.of(currentDate, timeToSet);

        int day=1,month=1,year=1;
        LocalDate dateToSet = LocalDate.of(day, month, year);
        LocalTime currentTime = LocalTime.from(dateAndTime);
        dateAndTime = LocalDateTime.of(dateToSet, currentTime);

        //同时设定时间和日期
        dateAndTime = LocalDateTime.of(dateToSet, timeToSet);
    }

    enum WEEKS {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
    } //类似语句块，不用分号结尾

    public static void enumType() {
        EnumMap<WEEKS, String> map = new EnumMap<>(WEEKS.class);
        map.put(WEEKS.Monday, "星期一");
        EnumSet<WEEKS> set = EnumSet.allOf(WEEKS.class);
        System.out.println(set.contains(WEEKS.Thursday));
    }

    public static void parameterVariable(int t, String... args) {
        System.out.println(Arrays.toString(args));
    }

    public static void Localizer(){
        double num = 123456.78;
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.SIMPLIFIED_CHINESE);
//        123456.780000 的本地化（zh_CN）结果: ￥123,456.78
        System.out.format("%f 的本地化（%s）结果: %s%n", num, Locale.SIMPLIFIED_CHINESE, format.format(num));

        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.SIMPLIFIED_CHINESE);

//        Fri Apr 10 10:43:36 CST 2020 的本地化（zh_CN）结果: Apr 10, 2020
        System.out.format("%s 的本地化（%s）结果: %s%n", date, Locale.SIMPLIFIED_CHINESE, df.format(date));
//        Fri Apr 10 10:43:36 CST 2020 的本地化（zh_CN）结果: 2020年4月10日
        System.out.format("%s 的本地化（%s）结果: %s%n", date, Locale.SIMPLIFIED_CHINESE, df2.format(date));

        String pattern1 = "{0}，你好！你于  {1} 消费  {2} 元。";
        String pattern2 = "At {1,time,short} On {1,date,long}，{0} paid {2,number, currency}.";
        Object[] params = {"Jack", new GregorianCalendar().getTime(), 8888};
        String msg1 = MessageFormat.format(pattern1, params);
        MessageFormat mf = new MessageFormat(pattern2, Locale.US);
        String msg2 = mf.format(params);
        System.out.println(msg1);
        System.out.println(msg2);
    }

    public static void regex(){
        String str = "2019-11-14";
        String pat = "\\d{4}-\\d{2}-\\d{2}";

        Pattern p = Pattern.compile(pat);
        Matcher m = p.matcher(str);
        if(m.matches()){
            System.out.println(m.replaceAll("1"));
        }else{
            System.out.printf("no");
        }

        if(Pattern.compile("[0-9]+").matcher("123456").matches()){
            System.out.println("all numbers");
        }else{
            System.out.println("not pure");
        }
    }

    public static void stringBuild(){
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher("hujun");
        if(matcher.lookingAt()){
            System.out.println("找到了");
        }else{
            System.out.println("未找到");
        }

        if(matcher.find()){
            System.out.println("找到了");
        }

//        final byte[Integer.MAX_VALUE] , append,直接加入字符数组中，初始化字节数组大小为16
//        为什么线程安全？ 方法加了Synchronized，同步容器一种
        StringBuffer sb = new StringBuffer();
//        两者继承的都是AbstractStringBuilder父类
//        int oldCapacity = this.value.length >> this.coder;
//        if (minimumCapacity - oldCapacity > 0) {
//            this.value = Arrays.copyOf(this.value, this.newCapacity(minimumCapacity) << this.coder);
//        }
//        使用arrayscopyof来重新声明一个新长度的字节数组，复制到这个数组
//        byte[] copy = new byte[newLength];
//        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
//        return copy;
//        底层是systemarraycopy
        StringBuilder sbd = new StringBuilder();
        while(matcher.find()){
            matcher.appendTail(sb);
        }
        System.out.println(sb);
    }

    public static void command() throws IOException {
        Process p = Runtime.getRuntime().exec("ping " + "127.0.0.1");
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("gbk")));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.length() != 0)
                sb.append(line + "\r\n");
        }
        System.out.println("本次指令返回的消息是：");
        System.out.println(sb.toString());
    }

    public static void simpleDate(){
        Date date = new Date(0);
        System.out.format("Original time: %s%n", date.toString());
        date = new Date();
        System.out.format("And now time: %s%n", date.toString());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));

        Calendar calendar = Calendar.getInstance();
        // 下个月的今天
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        System.out.println("下个月的今天:\t" +sdf.format(calendar.getTime()));

        // 去年的今天
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        System.out.println("去年的今天:\t" +sdf.format(calendar.getTime()));

        // 上个月的第三天
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 3);
        System.out.println("上个月的第三天:\t" +sdf.format(calendar.getTime()));

        calendar.setTime(date);
        calendar.add(Calendar.MONTH, +2);
        calendar.set(Calendar.DATE, -3);
        System.out.println(sdf.format(calendar.getTime()));
    }

    /**
     * equals方法相同，hashcode不相同，两个对象相同吗？,
     * 看比较的方式，如果用==表示比较两个引用的地址，是不相同的，+
     * 如果用equals方法且equals里面没有调用hash，则相同+
     * 面试官说equals方法相同则用equals一定是相同的，但是在一些集合中则有问题+
     * 如hashmap中，因为用hashcode存数据，所以如果未重写hashCode方法，则put和get有可能不同+
     * get时，如果散列码不一样就不必比较等同性了
     */
    static class Person{
        private int id;
        private int age;
        public Person(int id, int age){
            this.id = id;
            this.age = age;
        }
        @Override
        public String toString(){
            return  this.id+","+this.age;
        }
        @Override
        public boolean equals(Object o){
            if(this==o){
                return true;
            }
            if(!(o instanceof Person)){
                return false;
            }
            Person p = (Person)o;
            return p.id==this.id && p.age==this.age;
        }
        // 重写hash code有规律讲究,把每个属性都打在不重合的result上
        @Override
        public int hashCode(){
            int result = 17;        // 选一个素数达到较好的散列
            result = 31*result + id;    // 31为素数， 31*i==(i<<5)-i,编译器会优化
            result = 31*result + age;
            return result;
        }
    }

    /**
     * 同步和互斥是一回事吗？,
     * 不是，同步有两层含义：1.当一个线程修改对象时，另一个线程无法访问，这是互斥，阻止线程看到对象处于不一致状态中+
     * 2.修改完后，还要确保其他线程能够看到修改后的对象，这是同步，保证线程都能看到由同一个锁保护的之前所有的修改状态+
     * 所以，当需要对共享变量互斥时，我们需要互斥方法；当多线程需要通过变量交互通信时，我们使用同步方法。+
     * 我们可以使用synchronized保证同步和互斥，是重量级锁+
     * 也可以使用volatile轻量级锁，让修改的变量对所有线程立即可见。+
     * 可见性：保证一个线程的修改能够被另一个线程获知（通信得到）
     */
    private static boolean stopRequested;

    public static void neverStopThread() throws InterruptedException {
        Thread backgroud = new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(!stopRequested){
                    i++;
                }
            }
        });
        backgroud.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;       // 主线程修改后，要保证子线程能看到修改，需要同步才行
    }

    public static synchronized void requestStop(){
        stopRequested = true;
    }

    public static synchronized boolean stopRequested(){
        return stopRequested;
    }

    public static void stopUsingSynchronized() throws InterruptedException {
        Thread backgroud = new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(!stopRequested()){
                    i++;
                }
            }
        });
        backgroud.start();
        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }

}
