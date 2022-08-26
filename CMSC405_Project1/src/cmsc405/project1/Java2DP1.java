package cmsc405.project1;

/**
 *
 * @author davenull
 */

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Java2DP1 extends JFrame {

  private Java2DP1() {

    Animation animation = new Animation(); 
    add(animation); 

    setSize(800, 600); 
    setTitle("Java2D Animation"); 

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
    setLocationRelativeTo(null); 
    setResizable(false); 
  }

  public static void main(String[] args) {
    Java2DP1 java2DP1 = new Java2DP1();
    java2DP1.setVisible(true);
  }
}
