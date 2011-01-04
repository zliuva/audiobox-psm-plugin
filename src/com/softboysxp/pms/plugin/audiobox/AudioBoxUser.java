package com.softboysxp.pms.plugin.audiobox;

import net.pms.PMS;
import net.pms.dlna.virtual.VirtualFolder;

import fm.audiobox.core.models.AudioBoxClient;
import fm.audiobox.core.models.User;

public class AudioBoxUser extends VirtualFolder {
	private AudioBoxClient client;
	private User user = null;
	
	public AudioBoxUser(String email, String password) {
		super("AudioBox.fm", null);
		
		client = new AudioBoxClient();
		try {
			user = client.login(email, password);
		} catch (Exception ex) {
			// todo: handle exception
			PMS.error("Error logging in: ", ex);
			user = null;
		}
		
		addSubFolders();
	}
	
	public void addSubFolders() {
		if (user == null) {
			return;
		}
		
		addChild(new AudioBoxPlaylists(user, "Playlists"));
		addChild(new AudioBoxPlaylists(user, "Artists"));
		addChild(new AudioBoxPlaylists(user, "Albums"));
		addChild(new AudioBoxPlaylists(user, "Genres"));
	}
}
