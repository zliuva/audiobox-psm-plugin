package com.softboysxp.pms.plugin.audiobox;

import net.pms.PMS;

import net.pms.formats.Format;

import net.pms.configuration.RendererConfiguration;

import net.pms.dlna.DLNAResource;
import net.pms.dlna.DLNAMediaInfo;
import net.pms.dlna.DLNAMediaAudio;

import fm.audiobox.core.models.Track;

import java.io.IOException;
import java.io.InputStream;

public class AudioBoxTrack extends DLNAResource {
	private Track track;
	
	public AudioBoxTrack(Track track) {
		super(Format.AUDIO);
		this.track = track;
		
		this.media=new DLNAMediaInfo();
		DLNAMediaAudio audio=new DLNAMediaAudio();
		audio.album = track.getAlbum().getName();
		audio.artist = track.getArtist().getName();
		audio.songname = track.getTitle();
		audio.year = track.getYear();
		
		// the audio will be transcoded, this is not the value of the real stream
		audio.nrAudioChannels = 2;
		audio.codecA = "LPCM";
		
		media.audioCodes.add(audio);
		
		media.duration = DLNAMediaInfo.getDurationString(track.getDurationInSeconds());
		
		media.mediaparsed = true; // this flag must be set manually here
		
		this.ext = PMS.get().getAssociatedExtension("http://");
		checktype();
		this.player = PMS.get().getPlayer(net.pms.encoders.MPlayerWebAudio.class, this.ext);
	}
	
	@Override
	protected String getThumbnailURL() {
		return track.getAlbum().getCovers().getMedium();
	}

	@Override
	public String getThumbnailContentType() {
		if (getThumbnailURL() != null && getThumbnailURL().endsWith(".png"))
			return PNG_TYPEMIME;
		else
			return super.getThumbnailContentType();
	}

	public InputStream getInputStream() throws IOException {	
		return null;
	}

	public String getName() {
		return track.getTitle();
	}

	public boolean isFolder() {
		return false;
	}

	public long length() {
		// this value will actually be ignored, actual length will be calculated
		return DLNAMediaInfo.TRANS_SIZE;
	}

	public long lastModified() {
		return 0;
	}

	@Override
	public void discoverChildren() {
		
	}

	@Override
	public String getSystemName() {
		String url = null;
		try {
			url = track.getStreamUrl(true);
		} catch (Exception ex) {
			PMS.error("Error getting stream url", ex);
		}
		return url;
	}

	@Override
	public boolean isValid() {
		checktype();
		return true;
	}

}
