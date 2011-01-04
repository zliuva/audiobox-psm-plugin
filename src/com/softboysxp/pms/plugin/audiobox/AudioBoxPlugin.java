package com.softboysxp.pms.plugin.audiobox;

import net.pms.PMS;

import net.pms.external.AdditionalFolderAtRoot;
import net.pms.dlna.DLNAResource;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class AudioBoxPlugin implements AdditionalFolderAtRoot {
	private static final String EMAIL = "com.softboysxp.pms.plugin.audiobox.email";
	private static final String PASSWORD = "com.softboysxp.pms.plugin.audiobox.password";
	
	private AudioBoxUser user;
	
	public AudioBoxPlugin() {
	}
	
	public JComponent config() {
		JPanel configPane = new JPanel(new GridBagLayout());
		
		final JTextField emailField = new JTextField(20);
		final JPasswordField passwordField = new JPasswordField(20);
		
		emailField.setFont(passwordField.getFont());
		
		emailField.setText((String) PMS.getConfiguration().getCustomProperty(EMAIL));
		passwordField.setText((String) PMS.getConfiguration().getCustomProperty(PASSWORD));
		
		configPane.add(new JLabel("Email"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		configPane.add(emailField, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		configPane.add(new JLabel("Password"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		configPane.add(passwordField, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		
		configPane.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {}
			public void ancestorMoved(AncestorEvent event) {}
			
			public void ancestorRemoved(AncestorEvent event) {
				PMS.getConfiguration().setCustomProperty(EMAIL, emailField.getText());
				PMS.getConfiguration().setCustomProperty(PASSWORD, new String(passwordField.getPassword()));
				try {
					PMS.getConfiguration().save();
				} catch (Exception ex) {
					PMS.error("Error saving password: ", ex);
				}
			}	
		});
		
		return configPane;
	}
	
	public String name() {
		return "AudioBox.fm";
	}
	
	public void shutdown() {
		
	}
	
	public DLNAResource getChild() {
		if (user == null) {
			user = new AudioBoxUser((String) PMS.getConfiguration().getCustomProperty(EMAIL),
									(String) PMS.getConfiguration().getCustomProperty(PASSWORD));
		}
		
		return user;
	}	
}
