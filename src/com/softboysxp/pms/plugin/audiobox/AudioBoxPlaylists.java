package com.softboysxp.pms.plugin.audiobox;

import net.pms.PMS;
import net.pms.dlna.virtual.VirtualFolder;

import fm.audiobox.core.api.ModelsCollection;
import fm.audiobox.core.api.ModelItem;
import fm.audiobox.core.models.User;

public class AudioBoxPlaylists extends VirtualFolder {
	private User user = null;
	private String type = null;
	
	public AudioBoxPlaylists(User user, String type) {
		super(type, null);
		
		this.user = user;
		this.type = type;
	}
	
	@Override
	public void discoverChildren() {
		if (user == null) {
			return;
		}
		
		try {
			ModelsCollection playlists = null;
			
			if ("Playlists".equalsIgnoreCase(type)) {
				playlists = user.getPlaylists();
			} else if ("Artists".equalsIgnoreCase(type)) {
				playlists = user.getArtists();
			} else if ("Albums".equalsIgnoreCase(type)) {
				playlists = user.getAlbums();
			} else if ("Genres"	.equalsIgnoreCase(type)) {
				playlists = user.getGenres();
			}
			
			for (Object playlist : playlists.getCollection()) {
				addChild(new AudioBoxPlaylist((ModelItem) playlist));
			}
		} catch (Exception ex) {
			PMS.error("Error fetching tracks: ", ex);
		}
	}
}
