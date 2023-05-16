import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

import javax.swing.*;

public class dot13 extends JFrame implements MouseListener, MouseMotionListener {

    private JPanel panel;
    private Point[] dots = new Point[10];
    private int dotSize = 10;
    private Point lineStart, lineEnd;
    private Point mousePos;
    private BasicStroke stroke = new BasicStroke(5);
    private int linesDrawn = 0;
    private ArrayList<Line2D> lines = new ArrayList<Line2D>();
    private ArrayList<Point[]> connectedDots = new ArrayList<Point[]>();
    private JButton submitButton, resetButton;
    
    public dot13() {
        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(stroke);
                for (int i = 0; i < dots.length; i++) {
                    Color dotColor = Color.BLACK;
                    for (Point[] connectedDotPair : connectedDots) {
                        if (connectedDotPair[0].equals(dots[i]) || connectedDotPair[1].equals(dots[i])) {
                            dotColor = Color.GREEN;
                            break;
                        }
                    }
                    g.setColor(dotColor);
                    g.fillOval(dots[i].x - dotSize / 2, dots[i].y - dotSize / 2, dotSize, dotSize);
                }
                g.setColor(Color.RED); // set the line color here
                for (Line2D line : lines) {
                    g2.draw(line);
                }
                if (lineStart != null && lineEnd != null) {
                    g.drawLine(lineStart.x, lineStart.y, lineEnd.x, lineEnd.y);
                }
                if (mousePos != null && lineStart != null && lineEnd == null) {
                    g.setColor(Color.BLUE);
                    g.drawLine(lineStart.x, lineStart.y, mousePos.x, mousePos.y);
                }
            }
        };

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (linesDrawn == 10 && checkAllDotsConnected()) {
                    JOptionPane.showMessageDialog(dot13.this, "Successful");
                } else {
                    JOptionPane.showMessageDialog(dot13.this, "Try Again");
                    connectedDots.clear();
                    lines.clear();
                    linesDrawn = 0;
                    repaint();
                }
            }
            
        });
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connectedDots.clear();
                lines.clear();
                linesDrawn = 0;
                repaint();
                generateRandom(getWidth()/2, getHeight()/2);
            }
            
        });

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
        add(resetButton, BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        // Create 10 points to make a circle
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        generateRandom(centerX, centerY);

        

        }
        public void generateRandom(int centerX, int centerY){
            int[] shapeTypes = { 0, 1, 2 }; // 0 for triangle, 1 for rectangle, 2 for circle
        Random rand = new Random();
       
            switch (shapeTypes[rand.nextInt(3)]) {
                case 0:
                    makeTriangle(centerX, centerY);
                    break;
                case 1:
                    makeRectangle(centerX, centerY);
                    break;
                case 2:
                    makeCircle(centerX, centerY);
                    break;
         }
        
        }

        public void makeRectangle(int centerX, int centerY){
            for(int i=0; i<=3; i++){
                int x = (int)(centerX + i*20);
                int y = (int)(centerY);
                dots[i]= new Point(x, y);
            }
       
            dots[4]= new Point(centerX+60, centerY+20);
            dots[5]= new Point(centerX+60, centerY+40);
            dots[6] = new Point(centerX+40, centerY+40);
            dots[7] = new Point(centerX+20, centerY+40);
            dots[8] = new Point(centerX, centerY+40);
        
            dots[9] = new Point(centerX,centerY+20);
        }

        public void makeTriangle(int centerX, int centerY){
            for(int i=0; i<10; i++){
                if(i<4){
                    int x = (int) (centerX+ (i)*30);
                    int y = (int) (centerY-(i)*30);
                    System.out.println(x+ " " + y);
                    dots[i]= new Point(x, y);
                // int x = (int) (centerX+ i*30);
                // int y = (int) (centerY);
                // // System.out.println(x+ " " + y);
                // dots[i]= new Point(x, y);
                }
                else if(i>=4 && i<7){
                    //     int x = (int) (centerX+ (i-4)*30);
                    // int y = (int) (centerY-(i-4)*30);
                    // // System.out.println(x+ " " + y);
                    // dots[i]= new Point(x, y);
                //      int x = (int) (centerX+ (i)*30);
                // int y = (int) (centerY +  ((i)*30 - (i-3)*30));
                // System.out.println(x+ " " + y);

                dots[4]= new Point(290, 140);
                dots[5] = new Point(290, 170);
                dots[6] = new Point(290, 200);
                }
                else {
                    dots[7] = new Point(270, 200);
                    dots[8] = new Point(250, 200);
                    dots[9] = new Point(230, 200);
                    dots[10] = new Point(210, 200);
                }
                
            }
           
        }
        public void makeCircle(int centerX, int centerY){
            int radius = 100;
            for (int i = 0; i < 10; i++) {
                double angle = Math.toRadians(i * 36);
                int x = (int) (centerX + radius * Math.cos(angle));
                int y = (int) (centerY + radius * Math.sin(angle));
                System.out.println(x+ " " + y);
                dots[i]= new Point(x, y);
            }
        }
        public static void main(String[] args) {
            new dot13();
        }
        
        private boolean checkAllDotsConnected() {
            // Check if all dots are connected to at least one other dot
            for (Point dot : dots) {
                boolean isConnected = false;
                for (Point[] connectedDotPair : connectedDots) {
                    if (connectedDotPair[0].equals(dot) || connectedDotPair[1].equals(dot)) {
                        isConnected = true;
                        break;
                    }
                }
                if (!isConnected) {
                    return false;
                }
            }
            return true;
        }
        
        public void mouseClicked(MouseEvent e) {}
        
        public void mousePressed(MouseEvent e) {
            for (int i = 0; i < dots.length; i++) {
                if (Math.abs(e.getX() - dots[i].x) < dotSize / 2 && Math.abs(e.getY() - dots[i].y) < dotSize / 2) {
                    if (lineStart == null && lineEnd == null) {
                        mousePos = new Point(e.getX(), e.getY());
                        lineStart = dots[i];
                    } else if (lineStart != null && lineEnd == null && lineStart != dots[i]) {
                        lineEnd = dots[i];
                        lines.add(new Line2D.Double(lineStart, lineEnd));
                        connectedDots.add(new Point[] { lineStart, lineEnd });
                        linesDrawn++;
                        lineStart = null;
                        lineEnd = null;
                    }
                    break;
                }
            }
            repaint();
        }
        public void mouseReleased(MouseEvent e) {}
        
        public void mouseEntered(MouseEvent e) {}
        
        public void mouseExited(MouseEvent e) {}
        
        public void mouseDragged(MouseEvent e) {
            if (lineStart != null && lineEnd == null) {
                mousePos = new Point(e.getX(), e.getY());
                repaint();
            }
        }
        
        public void mouseMoved(MouseEvent e) {
            if (lineStart != null && lineEnd == null) {
                mousePos = new Point(e.getX(), e.getY());
                for (int i = 0; i < dots.length; i++) {
                    if (dots[i] != lineStart && Math.abs(mousePos.x - dots[i].x) < dotSize / 2 && Math.abs(mousePos.y - dots[i].y) < dotSize / 2) {
                        lineEnd = dots[i];
                        lines.add(new Line2D.Double(lineStart, lineEnd));
                        connectedDots.add(new Point[] { lineStart, lineEnd });
                        linesDrawn++;
                        lineStart = null;
                        lineEnd = null;
                        break;
                    }
                }
                repaint();
            }
        }
    }