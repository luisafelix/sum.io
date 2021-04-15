package client.lobby;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class PlayerPanel extends LobbyPanel
{
	
	private JPanel informations;
	private JPanel imageContainer;
	private JPanel buttons;
	
	private JButton addButton;
	private JLabel name;
	private JLabel image;
	
	public PlayerPanel(LobbyHandler callback) {
		super(callback);
		
		setupButtons();
		setupInformations();
		setupImages();
		super.add(imageContainer);
		super.add(informations);
		super.add(buttons);
	}
	
	private void setupButtons()
	{	
		buttons = new JPanel(new BorderLayout());
		addButton = new JButton();
		
		addButton.setContentAreaFilled(false);
		addButton.addActionListener(new ActionListener() 
									{
										@Override
										public void actionPerformed(ActionEvent e) 
										{
											addBot();
										}
									}
								   );
		buttons.setOpaque(false);
		buttons.setBorder(new EmptyBorder(BORDER_SIZE,BORDER_SIZE,BORDER_SIZE,BORDER_SIZE));
		addButton.setBorderPainted(false);
		addButton.setIcon(new ImageIcon( callback.getImageMap().get("add").getScaledInstance(50, 50, 1)));
		addButton.setRolloverIcon(new ImageIcon( callback.getImageMap().get("add2").getScaledInstance(50, 50, 1)));
		addButton.setDisabledIcon(new ImageIcon( callback.getImageMap().get("add1").getScaledInstance(50, 50, 1)));
		
		buttons.add(addButton);
		
		
	}
	
	public void addBot()
	{
		callback.addBotToLobby();
	}
	
	private void setupImages()
	{
		imageContainer = new JPanel();
		
		image = new JLabel();
		imageContainer.setBorder(new EmptyBorder(BORDER_SIZE-15,BORDER_SIZE-15,BORDER_SIZE+15,BORDER_SIZE+15));
		
		imageContainer.setOpaque(false);
		imageContainer.add(image);
	}
	
	private void setupInformations()
	{
		informations = new JPanel(new BorderLayout());
		informations.setOpaque(false);
		name = new JLabel("< EMPTY >"); //10 Characters MAX
		name.setFont(FONT);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBackground(DARK_BLUE);
		informations.add(name,BorderLayout.CENTER);
	}
	
	public void setName(String value)
	{
		name.setText(value);
	}
	
	public void disableButton()
	{
		addButton.setEnabled(false);
	}
	
	public void setImage(BufferedImage bi)
	{
		if(bi != null)
		{
			image.setIcon(new ImageIcon(bi.getScaledInstance(100, 100, 1)));
		}
	}

	public void enableButton() 
	{
		addButton.setEnabled(true);
	}

}
