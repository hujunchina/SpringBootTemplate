package com.hujunchina.client.base;

import com.hujunchina.common.ComUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/6 2:15 下午
 * @Version 1.0
 */
public class JVM {

    public static void main(String[] args) {

    }

    /**
     * java内存模型，java内存组件：堆内存，栈内存，本地内存
     * 运行时数据区域（五大）：线程栈，堆，方法区，程序计数器，本地方法栈
     * PCR,当前线程执行的字节码的行号指示器，虚拟机字节码指令的地址（Native方法为空）
     * 栈帧：一个方法的某一片，包括局部变量，操作数栈，常量池引用
     * 如果线程请求的栈深度超过最大值，就会抛出StackOverflowError异常；\n
     * 如果虚拟机栈进行动态扩展时，无法申请到足够内存，就会抛出OutOfMemoryError异常。
     * 可以通过-Xss512M这个虚拟机参数来指定一个程序的Java虚拟机栈内存大小
     * 本地方法栈：虚拟机栈为Java方法服务；本地方法栈为Native方法服务
     * 堆：对象实例，gc主要区域，分代新生代，老年代，metaspace（元数据）
     * 可以通过-Xms100M和-Xmx200M两个虚拟机参数来指定一个程序的Java堆内存大小，
     * 第一个参数设置初始值，第二个参数设置最大值
     * 方法区：类信息，常量，静态变量，gc常量池的回收和对类的卸载
     * 常量池：常量，字面值，符号引用，String
     * 直接内存：NIO可通过DirectByteBuffer申请的额外内存空间
     * 直接内存容量可通过-XX:MaxDirectMemorySize指定，如果不指定，则默认与Java堆最大值（-Xmx指定）一样,
     * Outofmemoryerror:堆空间溢出有可能是**内存泄漏（MemoryLeak）**或内存溢出（MemoryOverflow）
     * 使用jstack和jmap生成threaddump和heapdump，然后用内存分析工具（如：MAT）进行分析
     * 还可以使用visualVM来分析，根据分析图，重点是确认内存中的对象是否是必要的，分清究竟是是内存泄漏（MemoryLeak）还是内存溢出（MemoryOverflow）
     * 内存泄漏是指由于疏忽或错误造成程序未能释放已经不再使用的内存的情况。
     * 简单讲就是gc无法回收垃圾，失去了对该段内存的控制，因而造成了内存的浪费
     * 导致内存泄漏的常见原因是使用容器，且不断向容器中添加元素，但没有清理，导致容器内存不断膨胀。
     * 静态容器:声明为静态（static）的HashMap、Vector等集合\n
     * 通俗来讲A中有B，当前只把B设置为空，A没有设置为空，回收时B无法回收。因为被A引用。\n
     * 监听器:监听器被注册后释放对象时没有删除监听器\n
     * 物理连接：各种连接池建立了连接，必须通过close()关闭链接\n
     * 内部类和外部模块等的引用：发现它的方式同内存溢出，可再加个实时观察\n
     * jstat-gcutil7362250070
     * 如果是内存溢出：可以增大堆内存，
     * 或从代码上检查是否存在某些对象生命周期过长、持有时间过长的情况，尝试减少程序运行期的内存消耗。
     * java.lang.OutOfMemoryError:GCoverheadlimitexceeded这个错误，
     * 官方给出的定义是：超过98%的时间用来做GC并且回收了不到2%的堆内存时会抛出此异常。
     * 这意味着，发生在GC占用大量时间为释放很小空间的时候发生的，是一种保护机制。
     * 导致异常的原因：一般是因为堆太小，没有足够的内存。
     * Exceptioninthreadmainjava.lang.OutOfMemoryError:Metaspace
     * 元数据区的内存不足，即方法区和运行时常量池的空间不足。在经常动态生成大量Class的应用中
     * -XX:MaxMetaspaceSize=512m设置元空间大小
     * java.lang.OutOfMemoryError:Unabletocreatenewnativethread
     * 这个错误意味着：Java应用程序已达到其可以启动线程数的限制。
     */
    /**
     * 内存泄漏示例
     * 错误现象：java.lang.OutOfMemoryError: Java heap space
     * VM Args：-verbose:gc -Xms10M -Xmx10M -XX:+HeapDumpOnOutOfMemoryError
     */
    public static void MemoryLeak(){
        List<JVM> arrayList = new ArrayList<>();
        while(true){
            arrayList.add(new JVM());
        }
    }

    /**
     * 堆溢出示例
     * <p>
     * 错误现象：java.lang.OutOfMemoryError: Java heap space
     * <p>
     * VM Args：-verbose:gc -Xms10M -Xmx10M
     */
    public static void MemoryOverflow(){
        Double[] array = new Double[999999999];
        System.out.println("array length = [" + array.length + "]");
    }
    public static void MetaSapce(){
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(JVM.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

    /**
     * GC垃圾回收机制，两种找对象算法，四种引用类型，三种回收算法，两个主要回收器
     * 引用计数法会导致相互引用无法消除，可达性分析算法通过有向图遍历对象实例，边是对象的引用
     * 通过GCRoots作为起始点进行搜索，能够到达到的对象都是存活的，不可达的对象可被回收
     * GCROOTs:虚拟机栈中引用的对象,本地方法栈中引用的对象,方法区中类静态属性引用的对象,方法区中的常量引用的对象
     * 1.强引用，new出来的，2.软引用SoftReference创建，3.弱引用WeakReference创建，4.虚引用PhantomReference
     * 1.标记清除，2.标记整理，3.复制
     * 1.CMS：concurrentmarksweep并发标记清除，用于老年代，采用标记清除算法
     * 开启选项：-XX:UseConcMarkSweepGC\n
     * 打开此开关后，使用CMSParNewSerialOld收集器组合来进行内存回收
     * 并发标记清除收集器是以获取最短停顿时间为目标。\n
     * 开启后，年轻代使用ParNew收集器；老年代使用CMS收集器，
     * 如果CMS产生的碎片过多，导致无法存放浮动垃圾，JVM会出现ConcurrentModeFailure，
     * 此时使用SerialOld收集器来替代CMS收集器清理碎片。
     * CMS分为四个阶段：初始标记，并发标记，重新标记，并发清除
     * CMS缺点：吞吐量低，无法处理浮动垃圾，导致的空间碎片
     * 2.G1收集器：面向服务器，最少停顿时间，引入分区的思路，弱化了分代的概念
     * G1分为四个阶段：初始标记，并发标记，最终标记，筛选回收
     * G1特点：空间整理，可预测停顿
     */


    /**
     * 类加载机制,加载-连接-初始化-调用-销毁，其中连接可细分为验证、准备、解析
     * 类加载过程是指加载、验证、准备、解析和初始化这5个阶段。
     * 加载：通过一个类的全限定名来获取定义此类的二进制字节流。
     * 将这个字节流所代表的静态存储结构转化为方法区的运行时存储结构。
     * 在内存中生成一个代表这个类的Class对象，作为方法区这个类的各种数据的访问入口
     * 验证：确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。
     * 文件格式验证-验证字节流是否符合Class文件格式的规范，并且能被当前版本的虚拟机处理。
     * 元数据验证-对字节码描述的信息进行语义分析，以保证其描述的信息符合Java语言规范的要求。
     * 字节码验证-通过数据流和控制流分析，确保程序语义是合法、符合逻辑的。
     * 符号引用验证-发生在虚拟机将符号引用转换为直接引用的时候，对类自身以外（常量池中的各种符号引用）的信息进行匹配性校验
     * 准备：类变量是被static修饰的变量，准备阶段为类变量分配内存并设置初始值，使用的是方法区的内存。
     * 解析：将常量池的符号引用替换为直接引用的过程。
     * 符号引用（SymbolicReferences）-符号引用以一组符号来描述所引用的目标，符号可以是任何形式的字面量，只要使用时能无歧义地定位到目标即可。
     * 直接引用（DirectReference）-直接引用可以是直接指向目标的指针、相对偏移量或是一个能间接定位到目标的句柄
     * 初始化：虚拟机执行类构造器<clinit>()方法的过程,
     * 类加载器
     * 加载有两种方式隐式加载-JVM自动加载需要的类到内存中。
     * 显示加载-通过使用ClassLoader来加载一个类到内存中
     * BootstrapClassLoader，即启动类加载器，负责加载JVM自身工作所需要的类
     * ExtClassLoader，即扩展类加载器<JAVA_HOME>\\lib\\ext
     * AppClassLoader，即应用程序类加载器classpath
     * 双亲委派原则：一个类加载器首先将类加载请求传送到父类加载器，只有当父类加载器无法完成类加载请求时才尝试加载。
     * 好处：使得Java类随着它的类加载器一起具有一种带有优先级的层次关系
     * 容器-典型应用：Servlet容器（如：Tomcat、Jetty）、udf（Mysql、Hive）等。
     * 加载解压jar包或war包后，加载其Class到指定的类加载器中运行（通常需要考虑空间隔离）。
     * 热部署、热插拔-应用启动后，动态获得某个类信息，然后加载到JVM中工作。
     * 很多著名的容器软件、框架（如：Spring等），都使用ClassLoader来实现自身的热部署。
     */


    /**
     *实战：JVM调优
     * 吞吐量-指不考虑GC引起的停顿时间或内存消耗，垃圾收集器能支撑应用达到的最高性能指标。
     * 延迟-其度量标准是缩短由于垃圾收集引起的停顿时间或者完全消除因垃圾收集所引起的停顿，避免应用运行时发生抖动。
     * 内存占用-垃圾收集器流畅运行所需要的内存数量
     * 1.GC优化：1.1将进入老年代的对象数量降到最低
     * 1.2减少FullGC的执行时间
     * 少进入老年代的对象数量可以显著降低FullGC的频率
     * 分析结果显示运行GC的时间只有0.1-0.3秒，那么就不需要把时间浪费在GC优化上，
     * 但如果运行GC的时间达到1-3秒，甚至大于10秒，那么GC优化将是很有必要的。
     * 调整-XX:NewRatio依据是什么？
     */
    /**
     * 模拟FullGC问题，并调优,
     *  从数据库中读取信用数据，套用模型，并把结果进行记录和传输,
     *  java -Xms:30MB -Xmx:30MB -XX:+PrintGC file
     */
    private static class CardInfo{
        BigDecimal price = new BigDecimal(0.0);
        String name = "张三";
        int age = 30;
        Date birthDate = new Date();
        public void m(){}
    }
    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
            50,
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );
    private static void modelFit(){
        List<CardInfo> takeCard = getAllCardInfo();
        takeCard.forEach( cardInfo -> {
            executor.scheduleWithFixedDelay(
                    ()->{ cardInfo.m();},  // Lambda runnable
                    2, 3, TimeUnit.SECONDS
            );
        });
    }
    private static List<CardInfo> getAllCardInfo() {
        List<CardInfo> cardInfos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CardInfo cardInfo = new CardInfo();
            cardInfos.add(cardInfo);
        }
        return cardInfos;
    }
    public static void fullGC(){
        executor.setMaximumPoolSize(50);
        for(;;){
            modelFit();
            ComUtils.getUtils().sleep(100);
        }
    }
}
