public class PrinterTask extends Thread implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < 10; i++) {
                    System.out.print(".");
                    Thread.sleep(6000);
                }
                System.out.println("|");
            }
        } catch (InterruptedException e) {

        }
    }
}
