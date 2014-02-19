package mt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;

public class ToolsBar extends JToolBar
{
	public ToolsBar(final JTabbedPane tabbedPane,final StatusBar statusBar, final JFrame frame)
	{
		JButton newButton = new JButton("new");
		JButton saveButton = new JButton("save");
		JButton loadButton = new JButton("load");
		
		JButton zoomMinus = new JButton(" - ");
		JButton zoomPlus = new JButton(" + ");
		
		
		newButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolsAction.newDrawPanel(tabbedPane, statusBar, frame);
			}
		});
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolsAction.saveImage(tabbedPane, frame);
				
			}
		});
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ToolsAction.loadImage(tabbedPane, frame);
				
			}
		});
		
		zoomMinus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JScrollPane scroll = (JScrollPane) tabbedPane.getSelectedComponent();
				
				JViewport viewport = scroll.getViewport(); 
				DrawPanel drawPanel = (DrawPanel)viewport.getView();
				drawPanel.setScale(0.5, 0.5);
			}
		});
		
		zoomPlus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JScrollPane scroll = (JScrollPane) tabbedPane.getSelectedComponent();
				
				JViewport viewport = scroll.getViewport(); 
				DrawPanel drawPanel = (DrawPanel)viewport.getView();
				drawPanel.setScale(2, 2);
			}
		});
		
		add(newButton);
		add(saveButton);
		add(loadButton);
		
		//add(zoomMinus);
		//add(zoomPlus);
		
		
	}
}
