package Cryptofriends.GUI;

import core.Data.Player;
import javafx.scene.control.MenuItem;

public class PlayerMenuItem extends MenuItem {
	private Player player = null;
	
	public PlayerMenuItem(Player player) {
		this.player = player;
		this.setText(player.getName());
	}
	
	public void renamePlayer() {
		this.setText(player.getName());
	}
}
