import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private final List<Rectangle> rectangles = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();
    private Rectangle currentRect = null;
    private Point startPoint;
    Random random = new Random();

    public DrawingPanel() {
        setBackground(Color.decode("#DC667C"));
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                for (Rectangle rect : rectangles) {
                    if (rect.contains(point)) {
                        currentRect = rect;
                        requestFocusInWindow(); // Upewnij się, że panel ma focus po kliknięciu
                        return;
                    }
                }
                startPoint = point;
                currentRect = new Rectangle(startPoint.x, startPoint.y, 0, 0);
                rectangles.add(currentRect);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentRect != null) {
                    Thread thread = createRectangleThread(currentRect);
                    threads.add(thread);
                    thread.start();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentRect != null) {
                    Point endPoint = e.getPoint();
                    int x = Math.min(startPoint.x, endPoint.x);
                    int y = Math.min(startPoint.y, endPoint.y);
                    int width = Math.abs(endPoint.x - startPoint.x);
                    int height = Math.abs(endPoint.y - startPoint.y);
                    currentRect.setBounds(x, y, width, height);
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (currentRect != null) {
                    int keyCode = e.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            currentRect.y -= 5;
                            break;
                        case KeyEvent.VK_DOWN:
                            currentRect.y += 5;
                            break;
                        case KeyEvent.VK_LEFT:
                            currentRect.x -= 5;
                            break;
                        case KeyEvent.VK_RIGHT:
                            currentRect.x += 5;
                            break;
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        for (Rectangle rect : rectangles) {
            if (rect == currentRect) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLUE);
            }
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
        }
    }

    private Thread createRectangleThread(Rectangle rectangle) {
        return new Thread(() -> {
            while (true) {
                repaint();
            }
        });
    }

    public void addFigSmall() {
        int x = random.nextInt(this.getWidth()) - 25;
        int y = random.nextInt(this.getHeight()) - 25;
        Rectangle rect = new Rectangle(x, y, 50, 50);
        rectangles.add(rect);
        Thread thread = createRectangleThread(rect);
        threads.add(thread);
        thread.start();
    }

    public void addFigBig() {
        int x = random.nextInt(this.getWidth()) - 35;
        int y = random.nextInt(this.getHeight()) - 40;
        Rectangle rect = new Rectangle(x, y, 70, 80);
        rectangles.add(rect);
        Thread thread = createRectangleThread(rect);
        threads.add(thread);
        thread.start();
    }
}