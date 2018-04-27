package Cryptofriends.GUI;

import java.util.Timer;

import core.Data.Player;
import core.Managers.TimeManager;
import javafx.scene.control.Label;

public class CgramTimer extends Timer {
	private TimeManager timeMan;
	private Player player;
	private CgramTimeTask timeTask;
	private Label lblTime;
	private int delay = 0;
	private int period = 10*100;
	
	public CgramTimer(TimeManager timeMan, Player player, Label lblTime) {
		super(true);
		this.timeMan = timeMan;
		this.player = player;
		this.lblTime = lblTime;
		
		setupTimeTask();
	}
	
	public CgramTimeTask getTimeTask() { return timeTask; }
	private void setupTimeTask() {
		timeTask = new CgramTimeTask(lblTime);
		timeTask.setTimeManager(this.timeMan);
		timeTask.setPlayer(this.player);
	}
	
	public void schedule() {
		this.scheduleAtFixedRate(timeTask, delay, period);
	}
}
