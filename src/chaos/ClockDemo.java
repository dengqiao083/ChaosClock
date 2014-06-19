/*
 * This class is just for learning JavaFX and practises.
 */

package chaos;

import java.util.Calendar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author dengqiao
 */
public class ClockDemo extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Group clock = new Group();
        
        //Draw a back ground circle
        Circle bgCircle = new Circle(0, 0, 120);
        bgCircle.setStroke(Color.GREY);
        bgCircle.setStrokeWidth(5d);
        bgCircle.setFill(Color.SILVER);
        clock.getChildren().add(bgCircle);
        
        //Draw the time ticks
        for (int i = 0; i < 60; i++) {
            Shape tick = null;
            if (i % 5 == 0) {//brick shape
                tick = new Line(0, 100, 0, 110);
                tick.setStroke(Color.GREY);
                tick.setStrokeWidth(3d);
            } else {//dot shape
                tick = new Circle(0, 110, 1);
                tick.setStroke(Color.GREY);
            }
            tick.getTransforms().add(new Rotate(i * (360 / 60), 0, 0));
            clock.getChildren().add(tick);
        }
        
        //Define hour hand
        Line hourHand = new Line(0d, 0d, 0d, -70d);
        hourHand.setStroke(Color.GREY);
        hourHand.setStrokeWidth(5d);
        hourHand.setStrokeLineCap(StrokeLineCap.ROUND);
        
        //Define minute hand
        Line minuteHand = new Line(0d, 0d, 0d, -90d);
        minuteHand.setStroke(Color.GREY);
        minuteHand.setStrokeWidth(2d);
        minuteHand.setStrokeLineCap(StrokeLineCap.ROUND);
        
        //Define second hand
        Line secondHand = new Line(0d, 0d, 0d, -90d);
        secondHand.setStroke(Color.GREY);
        secondHand.setStrokeWidth(1d);
        secondHand.setStrokeLineCap(StrokeLineCap.ROUND);
        
        //All join into the clock group
        clock.getChildren().addAll(hourHand, minuteHand, secondHand);
        
        Calendar cal = Calendar.getInstance();
        
        int intHour = cal.get(Calendar.HOUR_OF_DAY);
        int intMinute = cal.get(Calendar.MINUTE);
        int intSecond = cal.get(Calendar.SECOND);
        
        
        // define the rotate of hourHand
        // minutes will affect hour angle 1/60 of an hour
        Rotate hourRotate = new Rotate(intHour * 720 / 24 + intMinute * 30 / 60, 0, 0);
        // define the rotate of minuteHand
        // seconds will affect minute angle 1/60 of an minute
        Rotate minuteRotate = new Rotate(intMinute * 360 / 60 + intSecond * 6 / 60, 0, 0);
        // define the rotate of secondHand
        Rotate secondRotate = new Rotate(intSecond * 360 / 60, 0, 0);
        
        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.hours(24),
                    new KeyValue(hourRotate.angleProperty(), 360), //hourHand will rotate 360 degree in 24 hours
                    new KeyValue(minuteRotate.angleProperty(), 360 * 24 ), //minuteHand will rotate 360 degree 24 times in 24 hours
                    new KeyValue(secondRotate.angleProperty(), 360 * 24 * 60) //secondHand will rotate 360 degree 24 * 60 times in 24 hours
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        StackPane root = new StackPane();
        root.getChildren().add(clock);
        
        Scene scene = new Scene(root, 300, 300);
        
        primaryStage.setTitle("DqClock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
