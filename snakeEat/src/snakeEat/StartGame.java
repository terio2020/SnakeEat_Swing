package snakeEat;

import javax.swing.*;

/**
 *    游戏的主启动类
 */
public class StartGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setBounds(300,100, 900,720);
        frame.setResizable(false);  // 窗口大小不变
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(new GamePanel());

        frame.setVisible(true);
    }
}
