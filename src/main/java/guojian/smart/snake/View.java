/**
 * Author by guojian, Email guojian_k@qq.com, Date on 2018/6/19 8:39 AM
 * PS: Not easy to write code, please indicate.
 */
package guojian.smart.snake;

import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


/**
 * 视图，负责画面显示
 */
public class View extends Stage {
    /**
     * 画布
     */
    Canvas canvas;
    /**
     * 画笔
     */
    GraphicsContext pen;

    /**
     * 窗口大小
     */
    double width, height;

    Model model;


    public View(Model model, double width, double height) {
        this.model = model;
        this.width = width;
        this.height = height;
        canvas = new Canvas();
        pen = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        canvas.widthProperty().bind(widthProperty());//绑定canvas的长宽，保持与stage的长宽一致
        canvas.heightProperty().bind(heightProperty());
        setScene(new Scene(root));
        setTitle("smart-snake");//标题
        setHeight(height);
        setWidth(width);
        getIcons().add(new Image(this.getClass().getResourceAsStream("/me.jpg")));
        setResizable(false);
        show();//显示
    }

    /**
     * 使用白色 清理画布
     */
    protected void clearCanvas() {
        pen.setFill(Color.WHITE);
        pen.fillRect(0, 0, width, height);
    }


    Map<Integer, Color> colorMap = new HashMap<Integer, Color>() {{
        put(Model.SNAKE, Color.BURLYWOOD);
        put(Model.BLANK, Color.BLACK);
        put(Model.APPLE, Color.GREEN);
        put(Model.WALL, Color.DARKCYAN);
    }};

    int cellSize = 20;

    protected void draw() {
        for (int row = 0; row < Model.ROWS; row++) {
            for (int col = 0; col < Model.COLS; col++) {
                pen.setFill(colorMap.get((model.getWorld())[row][col]));
                pen.fillRect(col * cellSize, row * cellSize, cellSize , cellSize );
            }
        }

        for(int i=0;i<model.snakes.size()-1;i++){
            int[] begin= model.snakes.get(i);
            int[] end = model.snakes.get(i+1);
            pen.setFill(Color.BLACK);
            pen.strokeLine(begin[1]*cellSize+cellSize/2,begin[0]*cellSize+cellSize/2,end[1]*cellSize+cellSize/2,end[0]*cellSize+cellSize/2);
        }

        int[] head = model.getHead();
        pen.strokeOval(head[1]*cellSize,head[0]*cellSize,cellSize,cellSize);
    }


    /**
     * 渲染画面到屏幕
     */
    public void render() {
        clearCanvas();
        draw();
    }
}
