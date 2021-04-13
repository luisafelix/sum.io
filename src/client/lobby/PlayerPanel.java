package client.lobby;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PlayerPanel extends LobbyPanel
{
	
	private BufferedImage image;
	
	private JPanel informations;
	private JPanel imageContainer;
	private JPanel buttons;
	
	private AddButton addButton;
	private JLabel name;
	
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
		addButton = new AddButton(this);
		
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
		buttons.add(addButton);
		
		//addBot.setBorderPainted(false);
	}
	
	public void addBot()
	{
		callback.addBotToLobby();
	}
	
	private void setupImages()
	{
		imageContainer = new JPanel();
		imageContainer.setOpaque(false);
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

}
