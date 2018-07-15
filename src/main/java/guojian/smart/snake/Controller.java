/**
 * Author by guojian, Email guojian_k@qq.com, Date on 2018/6/19 8:32 AM
 * PS: Not easy to write code, please indicate.
 */
package guojian.smart.snake;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 处理键盘事件
 */
public class Controller implements EventHandler {
    Model model;

    public Controller(Model model) {
        this.model = model;
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            KeyCode keyCode = ((KeyEvent) event).getCode();
            switch (keyCode) {
                case ENTER:
                    model.stopOrRun();
                    model.onOrOffAuto();
                    break;
                case SPACE:
                    break;
                case UP:
                    model.moveUP();
                    break;
                case DOWN:
                    model.moveDown();
                    break;
                case LEFT:
                    model.moveLeft();
                    break;
                case RIGHT:
                    model.moveRight();
                    break;
                default:
                    System.out.println(keyCode);
            }

        }
    }
}
