package mt;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class Menu extends JMenuBar
{
	
	public Menu(final JTabbedPane tabbedPane,final StatusBar statusBar, final JFrame frame)
	{
		
		JMenu menuFile = new JMenu("File");
		JMenuItem newTab = new JMenuItem("New");
		JMenu menuHelp = new JMenu("Help");
		JMenuItem help = new JMenuItem("Help");
		JMenuItem about = new JMenuItem("About");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		
		newTab.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ToolsAction.newDrawPanel(tabbedPane, statusBar, frame);
			}
		});
		about.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JDialog  aboutFrame = new JDialog(frame,"About");
				aboutFrame.setSize(300,200);
				aboutFrame.setLocationRelativeTo(Menu.this.getParent());
				aboutFrame.setModal(true);
				aboutFrame.setVisible(true);
				
			}
		});
		
		help.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JDialog  aboutFrame = new JDialog(frame,"Help");
				aboutFrame.setSize(300,200);
				aboutFrame.setLocationRelativeTo(Menu.this.getParent());
				aboutFrame.setModal(true);
				aboutFrame.setVisible(true);
			}
		});
		
		save.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ToolsAction.saveImage(tabbedPane, frame);
			}
		});
		
		load.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ToolsAction.loadImage(tabbedPane, frame);
			}
		});
		
		
		menuFile.add(newTab);
		menuFile.add(save);
		menuFile.add(load);
		
		menuHelp.add(help);
		menuHelp.add(about);
		
		add(menuFile);
		add(menuHelp);
	}
	
	
	
}
