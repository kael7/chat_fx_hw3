package hw4;

public class WaitNotify {
    static Object mon = new Object();
    static volatile char currentLetter = 'C';
    static int n = 5;

    public static void main(String[] args) {
        new Thread(new task1('A', 'B')).start();
        new Thread(new task1('B', 'C')).start();
        new Thread(new task1('C', 'A')).start();
    }

    static class task1 implements Runnable {
        private char c1;
        private char c2;

        public task1(char c1, char c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (mon){
                        while (currentLetter != c1){
                            mon.wait();
                        }
                        System.out.print(c1);
                        currentLetter = c2;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
