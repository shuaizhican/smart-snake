package guojian.smart.snake;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Model {

    public static final int APPLE = 100;
    public static final int WALL = -1;
    public static final int SNAKE = 1;
    public static final int BLANK = 0;

    public static final int COLS = 20, ROWS = 20;

    boolean running = false;
    boolean auto;

    List<int[]> snakes; // 存放蛇的坐标
    List<int[]> walls;  // 存放墙的坐标
    int[] app;

    int[] defaultNextStep;
    int[] nextStep;


    public int[] getHead() {
        return snakes.get(snakes.size() - 1);
    }

    private int[] getNeck() {
        return snakes.get(snakes.size() - 2);
    }

    public int[] getTail() {
        return snakes.get(0);
    }

    public Model() throws Exception {
        snakes = new LinkedList<>();
        snakes.add(new int[]{ROWS / 2 + 1, COLS / 2});
        snakes.add(new int[]{ROWS / 2, COLS / 2});
        snakes.add(new int[]{ROWS / 2 - 1, COLS / 2});
        walls = new ArrayList<int[]>(ROWS * 2 + COLS * 2) {
            {
                for (int col = 0; col < COLS; col++) {
                    add(new int[]{0, col});
                    add(new int[]{ROWS - 1, col});
                }
                for (int row = 0; row < ROWS; row++) {
                    add(new int[]{row, 0});
                    add(new int[]{row, COLS - 1});
                }
            }
        };
        app = randomApple(snakes, walls);
    }


    private int[] randomApple(List<int[]> snakes, List<int[]> walls) throws Exception {
        int[][] world = getNoAppleWorld(snakes, walls);
        List<int[]> list = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (world[row][col] == BLANK) {
                    list.add(new int[]{row, col});
                }
            }
        }
        try {
            int index = new Random().nextInt(list.size());
            return list.get(index);
        } catch (Exception e) {
            throw new Exception("恭喜游戏完美通过!");
        }
    }


    private int[][] getNoAppleWorld(List<int[]> snakes, List<int[]> walls) {
        int[][] world = new int[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                world[row][col] = BLANK;
            }
        }
        for (int[] snake : snakes) {
            world[snake[0]][snake[1]] = SNAKE;
        }
        for (int[] wall : walls) {
            world[wall[0]][wall[1]] = WALL;
        }
        return world;
    }

    public int[][] getWorld(List<int[]> snakes, List<int[]> walls, int[] app) {
        int[][] world = getNoAppleWorld(snakes, walls);
        world[app[0]][app[1]] = APPLE;
        return world;
    }

    public int[][] getWorld() {
        return getWorld(snakes, walls, app);
    }

    public void stopOrRun() {
        running = !running;
    }

    public void update() {
        if (!running) return;

        if (auto) {
            nextStep = new BFS(this).search();
        }

        int[] head = getHead();
        int[] neck = getNeck();
        defaultNextStep = new int[]{2 * head[0] - neck[0], 2 * head[1] - neck[1]};
        if (nextStep == null || (nextStep[0] == neck[0] && nextStep[1] == neck[1])) {
            nextStep = defaultNextStep;
        }

        int type = getWorld()[nextStep[0]][nextStep[1]];
        switch (type) {
            case APPLE:
                try {
                    eat();
                } catch (Exception e) {
                    e.printStackTrace();
                    stop();
                }
                break;
            case BLANK:
                move();
                break;
            default:
                stop();
        }

    }

    private void stop() {
        running = false;
    }

    private void move() {
        snakes.add(nextStep.clone());
        snakes.remove(0);
        nextStep = null;
    }

    private void eat() throws Exception {
        snakes.add(nextStep.clone());
        app = randomApple(snakes, walls);
        nextStep = null;
    }

    public void moveUP() {
        if (auto) return;
        int[] head = getHead();
        nextStep = new int[]{head[0] - 1, head[1]};
    }

    public void moveDown() {
        if (auto) return;
        int[] head = getHead();
        nextStep = new int[]{head[0] + 1, head[1]};
    }

    public void moveLeft() {
        if (auto) return;
        int[] head = getHead();
        nextStep = new int[]{head[0], head[1] - 1};
    }

    public void moveRight() {
        if (auto) return;
        int[] head = getHead();
        nextStep = new int[]{head[0], head[1] + 1};
    }

    public void onOrOffAuto() {
        auto=!auto;
    }
}
