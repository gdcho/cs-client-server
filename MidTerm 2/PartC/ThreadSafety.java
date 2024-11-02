public class ThreadSafety implements Runnable{
   int shared = 2;
   public static void main(String[] args) {
      ThreadSafety ts = new ThreadSafety();
      Thread t1 = new Thread(ts, "T1");
      Thread t2 = new Thread(ts, "T2");
      t1.start();
      t2.start();
   }
   public void run() {
      int copy = shared;
      try { 
         Thread.sleep((int)(Math.random() * 5000)); 
      }  catch (InterruptedException e) { }
      shared = copy - 1;
      System.out.println(Thread.currentThread().getName() + ": " +shared);
   }  
}