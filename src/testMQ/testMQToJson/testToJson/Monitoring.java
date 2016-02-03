package testMQ.testMQToJson.testToJson;

public class Monitoring {
    private static ThreadLocal<Long> begin = new ThreadLocal<Long>();
 
    public static void begin() {
        begin.set(System.currentTimeMillis());//.currentTimeMillis());
    }
 
    public static void end(String name) {
        double time = (System.currentTimeMillis() - begin.get()) / 1000.0;
        System.out.println(name + "所用时间（秒）\t\t\t\t\t\t:" + time);
/*        System.out.print(name + "所用时间（秒）\t\t\t");
        System.out.printf("%14.4f", time);// "-"表示输出的数左对齐（默认为右对齐）。
        System.out.print("ns");
        System.out.println();*/
    }

}
