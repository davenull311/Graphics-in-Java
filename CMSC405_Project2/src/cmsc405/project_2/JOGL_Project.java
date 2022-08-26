/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsc405.project_2;

/**
 *
 * @author davenull
 */

import com.jogamp.opengl.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JOGL_Project extends GLJPanel implements GLEventListener, KeyListener {

    private double rotateX = 30;
    private double rotateY = -30;
    private double rotateZ = 0;
    private double translateX = 0;
    private double translateY = 0;
    private double translateZ = 0;
    private double scale = 1.2;

    private JOGL_Project() {
        super(new GLCapabilities(null));
        setPreferredSize(new Dimension(640, 480));
        addGLEventListener(this);
        addKeyListener(this);
    }

    public static void main(String[] args) {
        JFrame window =
                new JFrame(
                        "Gems Galore | WASD -> Translate | +,- -> Zoom | Arrows -> Rotate | HOME -> Reset");
        JOGL_Project panel = new JOGL_Project();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50, 50);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
    }


    private void drawObj(GL2 gl2, Object3D obj, double scale, double x, double y, double z) {
        gl2.glPushMatrix();
        gl2.glScaled(scale, scale, scale);
        gl2.glTranslated(x, y, z);

        for (int i = 0; i < obj.faces.length; i++) {
            gl2.glPushMatrix();
            double r = obj.rgb[i][0];
            double g = obj.rgb[i][1];
            double b = obj.rgb[i][2];

            gl2.glColor3d(r, g, b);
            gl2.glBegin(GL2.GL_TRIANGLE_FAN);
            for (int j = 0; j < obj.faces[i].length; j++) {
                int v = obj.faces[i][j];
                gl2.glVertex3dv(obj.vertices[v], 0);
            }
            gl2.glEnd();

            gl2.glColor3d(0, 0, 0);
            gl2.glBegin(GL2.GL_LINE_LOOP);
            for (int j = 0; j < obj.faces[i].length; j++) {
                int v = obj.faces[i][j];
                gl2.glVertex3dv(obj.vertices[v], 0);
            }
            gl2.glEnd();
            gl2.glPopMatrix();
        }
        gl2.glPopMatrix();
    }

    public void display(GLAutoDrawable drawable) {

        GL2 gl2 = drawable.getGL().getGL2();

        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl2.glLoadIdentity();
        gl2.glRotated(rotateZ, 0, 0, 1);
        gl2.glRotated(rotateY, 0, 1, 0);
        gl2.glRotated(rotateX, 1, 0, 0);
        gl2.glScaled(scale, scale, scale);
        gl2.glTranslated(translateX, translateY, translateZ);

        drawObj(gl2, Object3D.ground, 0.5, 0, -0.05, 0);
        drawObj(gl2, Object3D.gemGreen, 0.125, 0, 1.5, 0);
        drawObj(gl2, Object3D.gemBlue, 0.125, 1.5, 1.5, 1.5);
        drawObj(gl2, Object3D.gemRed, 0.125, -1.5, 1.5, -1.5);
        drawObj(gl2, Object3D.gemYellow, 0.125, 1.5, 1.5, -1.5);
        drawObj(gl2, Object3D.gemPurple, 0.125, -1.5, 1.5, 1.5);
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-1, 1, -1, 1, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor(0, 0, 0, 1);
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glLineWidth(2);
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void keyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_LEFT) rotateY -= 15;
        else if (key == KeyEvent.VK_RIGHT) rotateY += 15;
        else if (key == KeyEvent.VK_DOWN) rotateX -= 15;
        else if (key == KeyEvent.VK_UP) rotateX += 15;
        else if (key == KeyEvent.VK_PLUS || key == KeyEvent.VK_EQUALS) scale += .1;
        else if (key == KeyEvent.VK_MINUS) scale -= .1;
        else if (key == KeyEvent.VK_W) translateY += .1;
        else if (key == KeyEvent.VK_A) translateX -= .1;
        else if (key == KeyEvent.VK_S) translateY -= .1;
        else if (key == KeyEvent.VK_D) translateX += .1;
        else if (key == KeyEvent.VK_HOME) {
            rotateX = 30;
            rotateY = -30;
            rotateZ = translateX = translateY = translateZ = 0;
            scale = 1.2;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }
}
