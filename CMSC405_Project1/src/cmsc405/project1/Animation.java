package cmsc405.project1;

/**
 *
 * @author davenull
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

class Animation extends JPanel {

  private static int translateX = 0;
  private static int translateY = 0;
  private static double rotation = 0.0;
  private static double scaleX = 1.0;
  private static double scaleY = 1.0;

  private Images images = new Images();
  private BufferedImage redSquare = images.getImage(Images.redSquare);
  private BufferedImage cross = images.getImage(Images.cross);
  private BufferedImage letterF = images.getImage(Images.letterE);

  private int frameNumber;

  Animation() {
    Timer animationTimer =
        new Timer(
            1000,
            arg0 -> {
              if (frameNumber >= 6) {
                frameNumber = 0;
              } else {
                frameNumber++;
              }
              repaint();
            });
    animationTimer.start(); 
  }

  
  public void paintComponent(Graphics g) {

    Graphics2D g2 = (Graphics2D) g.create(); 

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setPaint(Color.WHITE);
    g2.fillRect(0, 0, getWidth(), getHeight());

    applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);
    AffineTransform savedTransform = g2.getTransform();

    switch (frameNumber) {
      case 0: 
        translateX = 0;
        translateY = 0;
        scaleX = 1.0;
        scaleY = 1.0;
        rotation = 0;
        break;
      case 1: 
        translateX += -5;
        break;
      case 2: 
        translateY += 7;
        break;
      case 3: 
        rotation += 45 * Math.PI / 180.0;
        break;
      case 4: 
        rotation += -90 * Math.PI / 180.0;
        break;
      case 5: 
        scaleX = 2.0;
        break;
      case 6: 
        scaleY = 0.5;
        break;
    }

    g2.translate(translateX, translateY); 
    g2.rotate(rotation); 
    g2.scale(scaleX, scaleY); 
    g2.drawImage(redSquare, 0, 0, this); 
    g2.setTransform(savedTransform);

    g2.translate(translateX, translateY); 
    g2.translate(-50, -25); 
    g2.rotate(rotation); 
    g2.scale(scaleX, scaleY); 
    g2.drawImage(cross, 0, 0, this); 
    g2.setTransform(savedTransform);

    g2.translate(translateX, translateY); 
    g2.translate(50, 25); 
    g2.rotate(rotation); 
    g2.scale(scaleX, scaleY); 
    g2.drawImage(letterF, 0, 0, this); 
    g2.setTransform(savedTransform);
  }

  private void applyWindowToViewportTransformation(
      Graphics2D g2, double left, double right, double bottom, double top, boolean preserveAspect) {

    int width = getWidth(); 
    int height = getHeight(); 

    if (preserveAspect) {

      double displayAspect = Math.abs((double) height / width);
      double requestedAspect = Math.abs((bottom - top) / (right - left));
      if (displayAspect > requestedAspect) {
        double excess = (bottom - top) * (displayAspect / requestedAspect - 1);
        bottom += excess / 2;
        top -= excess / 2;
      } else if (displayAspect < requestedAspect) {
        double excess = (right - left) * (requestedAspect / displayAspect - 1);
        right += excess / 2;
        left -= excess / 2;
      }
    }

    g2.scale(width / (right - left), height / (bottom - top));
    g2.translate(-left, -top);
  }
}
