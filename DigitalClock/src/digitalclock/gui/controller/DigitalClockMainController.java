/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalclock.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class DigitalClockMainController implements Initializable {

    @FXML
    private Label lblClock;

    private final StringProperty mTimerString;

    private Calendar mCalendar;

    private final Timer mTimer;

    private TimerTask mTimerTask;

    private static final String HOUR_MINUTE_SECONDS = "HH:mm:ss";

    private static final String HOUR_MINUTE_SECONDS_MILLIS = "HH:mm:ss.SS";

    private SimpleDateFormat sdf;

    public DigitalClockMainController() {
        mTimerString = new SimpleStringProperty();
        mTimer = new Timer(true);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblClock.textProperty().bind(mTimerString);
        handleNormal();
    }

    @FXML
    private void handleNormal() {
        sdf = new SimpleDateFormat(HOUR_MINUTE_SECONDS);

        createTimerTask(1000, 1000);
    }

    @FXML
    private void handleMillis() {
        sdf = new SimpleDateFormat(HOUR_MINUTE_SECONDS_MILLIS);

        createTimerTask(1, 1);
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    /**
     * Create a timer task for the clock
     *
     * @param delayInMillis
     * @param periodInMillis
     */
    private void createTimerTask(int delayInMillis, int periodInMillis) {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    mCalendar = Calendar.getInstance();
                    mTimerString.setValue(sdf.format(mCalendar.getTime()));
                });
            }
        };
        mTimer.schedule(mTimerTask, delayInMillis, periodInMillis);
    }

}
