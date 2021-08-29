package snakeEat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.Random;

/**
 *      游戏的面板
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {

    // 定义蛇的数据结构
    int length;
    int[] snakeX = new int[600];    // 蛇的x坐标25*25
    int[] snakeY = new int[500];    // 蛇的y坐标25*25
    String fx;    // 初始方向向右
    int foodx;                            // 食物的坐标
    int foody;
    Random random;
    boolean isStart = false;    // 游戏当前的状态,默认关闭
    boolean isFail = false;     // 游戏是否失败

    int score;      // 成绩

    // 定时器
    Timer timer = new Timer(100, this);   // 100毫秒执行一次

    public GamePanel() {
        init();
        // 获得键盘和焦点事件
        this.setFocusable(true);    // 获得焦点事件
        this.addKeyListener(this);
        timer.start();
    }

    public void init(){
        length = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;    // 初始脑袋坐标
        snakeX[1] = 75;
        snakeY[1] = 100;     // 初始第一个身体坐标
        snakeX[2] = 50;
        snakeY[2] = 100;     // 初始第二 个身体坐标
        fx = "R";

        random = new Random();
        foodx = 25 + 25 * random.nextInt(34);
        foody = 75 + 25 * random.nextInt(24);

        score = 0;
    }


    // 绘制面板,游戏中的所有东西，都使用这个画笔来画
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    // 清屏
        this.setBackground(Color.WHITE);
        Data.header.paintIcon(this, g, 25, 11); // 头部广告栏
        g.fillRect(25, 75, 850, 600);   // 默认游戏界面

        // 画积分
        g.setColor(Color.GREEN);
        g.setFont(new Font("幼圆",Font.BOLD, 18));
        g.drawString("当前分数：" + score, 750, 35);

        // 画食物
        Data.food.paintIcon(this, g, foodx, foody);

        // 画小蛇
        if (fx.equals("R")) {
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("L")) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("U")) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("D")){
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }


        // 游戏状态
        if (!isStart) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("宋体", Font.BOLD, 40));
            g.drawString("按下空格开始游戏", 300, 350);
        }

        if (isFail) {
            g.setColor(Color.RED);
            g.setFont(new Font("宋体", Font.BOLD, 40));
            g.drawString("失败，按下空格重新开始游戏", 300, 350);
            isStart = !isStart;
        } else {
            repaint();
        }
    }


// 键盘监听器
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isFail) {
                // 重新开始
                isFail = false;
                init();
            } else {
                isStart = !isStart;
            }
            repaint();
        }

        // 小蛇移动
        if (!fx.equals("D") && keyCode == KeyEvent.VK_UP) {
            fx = "U";
        } else if (!fx.equals("U") && keyCode == KeyEvent.VK_DOWN) {
            fx = "D";
        } else if (!fx.equals("R") && keyCode == KeyEvent.VK_LEFT) {
            fx = "L";
        } else if (!fx.equals("L") && keyCode == KeyEvent.VK_RIGHT){
            fx = "R";
        }
    }

// 定时器的监听
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (isStart) {
            // 吃食物
            if (snakeX[0] == foodx && snakeY[0] == foody) {
                length++;   // 身体长度加一
                score += 10;
                foodx = 25 + random.nextInt(34) * 25;
                foody = 75 + random.nextInt(24) * 25;
            }

            // 身体移动
            for (int i = length-1; i > 0; i--) {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            // 头的方向
            if (fx.equals("R")) {
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850) { snakeX[0] = 25; }
            } else if (fx.equals("L")) {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) { snakeX[0] = 850; }
            } else if (fx.equals("U")) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) { snakeY[0] = 650; }
            } else if (fx.equals("D")){
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) { snakeY[0] = 75; }
            }

            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                }
            }
            repaint();
        }
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyReleased(KeyEvent keyEvent) {}

}
