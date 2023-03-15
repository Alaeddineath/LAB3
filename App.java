import java.awt.EventQueue;
import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        
    
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        addexpense frame = new addexpense();
                        frame.setVisible(true);
                       
                             // Wait for 1 second
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            
    }
}
