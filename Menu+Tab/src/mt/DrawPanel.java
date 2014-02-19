package mt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.RepaintManager;

@SuppressWarnings("serial")
class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener
{
	private Color color;
	private int last_x, last_y;
	private int x, y;
	BufferedImage image = null;
	BasicStroke pen1;
	AffineTransform at;

	
	
	DrawPanel(StatusBar statusBar, int panelWidth, int panelHeight) {
		
		//RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
		
		this.setPreferredSize(new Dimension(panelWidth,panelHeight));
		
		pen1 = new BasicStroke(20, BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL); 
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addComponentListener(this);
		this.addMouseListener(new PopClickListener(this));
		at = new AffineTransform();

		color = Color.BLACK;
	}

	public void setScale(double x, double y)
	{
		at.scale(x, y);
		
		DrawPanel.this.setPreferredSize(new Dimension((int)(getWidth()*x),(int)(getHeight()*y)));
		repaint();
		invalidate();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(at);
		super.paintComponent(g2);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (image == null)
		{
			image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D im =  image.createGraphics();
			im.fillRect(0, 0, getWidth(), getHeight());
			
		}
		//g2.drawImage(image, 0, 0, this);
		g2.drawImage(image,0, 0,image.getWidth(),image.getHeight(),this);
		//repaint();
		
	}


	public void drawOnTheImage(BufferedImage image) {

		Graphics2D g = image.createGraphics();
		g.setColor(color);
		pen1.createStrokedShape(new Ellipse2D.Double());
		g.setStroke(pen1);
		g.drawLine(x, y, last_x, last_y);
	}
	
	

	@Override
	public void mouseDragged(MouseEvent e) {

		if (e.isPopupTrigger())
        {
        }
		else
		{
			x = e.getX();
			y = e.getY();
			StatusBar.setXY( e.getX(),  e.getY());
			drawOnTheImage(image);

			last_x = x;
			last_y = y;

			this.repaint();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		last_x = x;
		last_y = y;

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.isPopupTrigger())
        {
        }
		else
		{
			drawOnTheImage(image);
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		StatusBar.setXY( e.getX(),  e.getY());
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setModePaint(int i) {
		pen1 = new BasicStroke(i, BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL); 
	
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		/*double notches = e.getWheelRotation();
		
		if(notches<0)
		{
		setScale(notches*(-2),notches*(-2));
		}
		else
		{
			setScale(notches/2,notches/2);
		}*/

	}
	
	class PopClickListener extends MouseAdapter
	{
		DrawPanel p;
		JMenu lineSizeMenu;
		JMenuItem lineSize;
		
		PopClickListener(DrawPanel p) 
		{
			this.p = p;
		}
		
	    public void mousePressed(MouseEvent e){
	        if (e.isPopupTrigger())
	        {
	            doPop(e);
	        }
	        DrawPanel.this.repaint();
	    }

	    public void mouseReleased(MouseEvent e){
	        if (e.isPopupTrigger())
	        {
	            doPop(e);
	        }
	        DrawPanel.this.repaint();
	    }

	    private void doPop(MouseEvent e)
	    {
	    	JPopupMenu popUpMenu = new JPopupMenu();
	    	JMenuItem colorMenu = new JMenuItem("color");
	    	lineSizeMenu = new JMenu("line size");
	    	colorMenu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					//JPanel colorPanel = new JPanel();
					Color color = JColorChooser.showDialog(DrawPanel.this.getParent(), "Choose color", Color.BLACK);
					p.setColor(color);
				}
			});
	    	
	    	for (int i = 1; i < 6; i++)
	    	{
	    		final int size = i*(5/2);
	    		lineSize = new JMenuItem()
	    		{
	    			@Override
	    			protected void paintComponent(Graphics g)
	    			{
	    				super.paintComponent(g);
	    				Graphics2D g2D = (Graphics2D) g;
	    	    		g2D.setStroke(new BasicStroke(size, 0,0));
	    	    		g2D.drawLine(5, 10, 65, 10);
	    	    		
	    			}
	    		};
	    		lineSize.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e)
					{
						setModePaint(size);
					}
				});
	    		
	    		lineSize.setPreferredSize(new Dimension(70, 20));
	    		lineSizeMenu.add(lineSize);
			}
	    	popUpMenu.add(colorMenu);
	    	popUpMenu.add(lineSizeMenu);
	    	popUpMenu.show(e.getComponent(), e.getX(), e.getY());
	    	
	    }
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, this);
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, this);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, this);
	}

}

