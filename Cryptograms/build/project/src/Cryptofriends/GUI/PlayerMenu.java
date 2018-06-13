package Cryptofriends.GUI;

import Cryptofriends.CgramController;
import core.Data.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class PlayerMenu extends Menu {
	private CgramController controller;
	private Player player = null;
	private int numOfPlayer = 0;
	private MenuItem renamePlayer = new MenuItem("Rename Player");
	private MenuItem removePlayer = new MenuItem("Remove Player");
	
	public PlayerMenu(CgramController controller, Player player) {
		this.controller = controller;
		this.player = player;
		numOfPlayer = this.player.getPlayerNum();
		this.setText(player.getName());
		
		
		renamePlayer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.renamePlayer(numOfPlayer);
				setText(player.getName());
			}
			
		});
		
		removePlayer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.removePlayer(numOfPlayer);
				
				if (player.getRemoved()) {
					setVisible(false);
				}
			}
			
		});
		
		this.getItems().add(renamePlayer);
		this.getItems().add(removePlayer);
	}
	
	public int getNumOfPlayer() { return numOfPlayer; }
}
