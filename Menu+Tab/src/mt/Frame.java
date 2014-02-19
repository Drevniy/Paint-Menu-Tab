package mt;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;


public class Frame extends JFrame
{

	public Frame()
	{
		setBounds(320, 200, 600, 400);
		
		StatusBar statusBar =  new StatusBar();
		JTabbedPane tabbedPane = new JTabbedPane();
		JMenuBar mBar = new Menu(tabbedPane, statusBar, this);
		ToolsBar toolBar = new ToolsBar(tabbedPane, statusBar, this);
		add(statusBar,BorderLayout.SOUTH);
		toolBar.addSeparator();
		add(toolBar,BorderLayout.PAGE_START);
		
		add(tabbedPane,BorderLayout.CENTER);
	 	setJMenuBar(mBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

}
