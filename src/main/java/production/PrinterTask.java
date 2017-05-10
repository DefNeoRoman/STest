package production;

public class PrinterTask extends Thread implements Runnable {
//Этот Thread печатает точки и палочки
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
