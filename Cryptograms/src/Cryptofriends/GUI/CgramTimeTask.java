package Cryptofriends.GUI;

import java.util.TimerTask;

import core.Data.Player;
import core.Managers.TimeManager;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class CgramTimeTask extends TimerTask {
	private TimeManager timeMan;
	private Player player;
	private long time;
	private Label lblTime = null;
	
	public CgramTimeTask(Label lblTime) {
		this.lblTime = lblTime;
	}
	
	public void setTimeManager(TimeManager timeMan) {
		this.timeMan = timeMan;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		Platform.runLater(new Runnable() {
			@Override public void run() {
				try {
				System.out.println("Running task - Player " + player.getPlayerNum());
				
				if (timeMan != null) {
				time = timeMan.getTimeElapsed();
				player.getPlayerTime().updateRoundTime(time);
				}
				
				int hours = (int) time / 3600;
				int minutes = (int) ((time % 3600) / 60);
				int seconds = (int) time % 60;
				String text = null;
				         		
				if (hours > 0) {
					text = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				} else if (minutes > 0) {
					text = String.format("%02d:%02d", minutes, seconds);
				} else {
					text = String.format("%02d", seconds);
				}
					
				lblTime.setText(text + "s");
				} catch (NullPointerException nullEx) {
					System.out.println("NullPointerException: Probably no player");
					//System.out.println(nullEx.printStackTrace(););
					nullEx.printStackTrace();
				}
			}
		});
	}

}
