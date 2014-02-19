package mt;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SizeDialog extends JDialog implements ActionListener
{
	int res = -1;
	int width = 300;
	int height = 300;
	JTextField textWidth;
	JTextField textHeight;
	
	public SizeDialog(JFrame frame)
	{
		super(frame, true);
		setSize(170, 120);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3, 2));
		setLocationRelativeTo(frame);
		
		JLabel lblWidth = new JLabel("Width:");
		JLabel lblHeight = new JLabel("Height:");
		
		JButton btnOk = new JButton("Ok");
		JButton btnCancel = new JButton("Cancel");
		
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
		
		textWidth = new JTextField("300");
		textHeight = new JTextField("300");
		
		add(lblWidth);
		add(lblHeight);
		add(textWidth);
		add(textHeight);
		add(btnOk);
		add(btnCancel);
		
		
	}
	
	public int getWidthPanel()
	{
		return width;
	}
	
	public int getHeightPanel()
	{
		return height;
	}
	
	public int showDialog()
	{
		setVisible(true);
		
		return res;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();
		
		if(btn.getText().equals("Ok"))
		{
			res = 0;
			width = Integer.parseInt(textWidth.getText());
			height = Integer.parseInt(textHeight.getText());
			dispose();
		}
		else
		{
			res = 1;
			dispose();
		}
	}
}
