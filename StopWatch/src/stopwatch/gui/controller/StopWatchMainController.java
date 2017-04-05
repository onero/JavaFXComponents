/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stopwatch.gui.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static stopwatch.be.EStopWatch.*;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class StopWatchMainController implements Initializable {

    @FXML
    private Button btnStart;

    @FXML
    private Label lblTime;

    private final StringProperty mStringTimer;

    private final DateFormat timeFormatter;

    private String timeAsString;

    private Timer timer;

    private long millis = 0;

    public StopWatchMainController() {
        mStringTimer = new SimpleStringProperty();
        timeFormatter = new SimpleDateFormat("mm:ss.SSS");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblTime.textProperty().bind(mStringTimer);
        formatAndSetTime();
    }

    @FXML
    private void handleStart() {
        if (btnStart.getText().equalsIgnoreCase(PLAY.toString())) {
            startTimer();
        } else {
            purgeTimer();
            btnStart.setText(PLAY.toString());
        }
    }

    private void startTimer() {
        btnStart.setText(PAUSE.toString());
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    millis++;
                    formatAndSetTime();
                });
            }
        };
        timer.schedule(task, 1, 1);

    }

    @FXML
    private void handleStop(ActionEvent event) {
        millis = 0;
        formatAndSetTime();
        purgeTimer();

    }

    private void purgeTimer() {
        timer.cancel();
        timer.purge();
    }

    private void formatAndSetTime() {
        timeAsString = timeFormatter.format(millis);
        mStringTimer.setValue(timeAsString);
    }

}
