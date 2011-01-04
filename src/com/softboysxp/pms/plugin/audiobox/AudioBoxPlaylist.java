package com.softboysxp.pms.plugin.audiobox;

import net.pms.PMS;
import net.pms.dlna.virtual.VirtualFolder;

import fm.audiobox.core.api.ModelItem;
import fm.audiobox.core.models.Track;

public class AudioBoxPlaylist extends VirtualFolder {
	private ModelItem playlist;
	
	public AudioBoxPlaylist(ModelItem playlist) {
		super(playlist.getName(), null);
		
		this.playlist = playlist;
	}
	
	@Override
	public void discoverChildren() {
		if (playlist == null) {
			return;
		}
		
		try {
			for (Track track : playlist.getTracks().getCollection()) {
				addChild(new AudioBoxTrack(track));
			}
		} catch (Exception ex) {
			PMS.error("Error fetching tracks: ", ex);
		}
	}
}
