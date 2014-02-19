package mt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.TIFFEncodeParam;

public class ToolsAction
{
	public static void newDrawPanel(final JTabbedPane tabbedPane,final StatusBar statusBar,JFrame frame)
	{
		int i = 0;
		int panelWidth;
		int panelHeight;

		if(tabbedPane.getComponentCount()==0)
			{
				i=0;
			}
		else
		{
			i = tabbedPane.getComponentCount()-1;
		}
			
		SizeDialog sizeDialog = new SizeDialog(frame);
		if(sizeDialog.showDialog() == 0)
		{
			panelWidth = sizeDialog.getWidthPanel();
			panelHeight = sizeDialog.getHeightPanel();
		}
		else
		{
			return;
		}
		
		final DrawPanel panelDraw = new DrawPanel(statusBar,panelWidth,panelHeight);
		//panel.setSize(new Dimension(500,500));
		JScrollPane scrollPane = new JScrollPane(panelDraw,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		
		
		tabbedPane.addTab("new", scrollPane);
		tabbedPane.setTabComponentAt(i, new CloseButton(tabbedPane));
	}
	
	public static void saveImage(final JTabbedPane tabbedPane, final JFrame frame)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new CustomFileFilter("jpg", "*.jpg,*.JPG"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("jpeg", "*.jpeg,*.JPEG"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("png", "*.png,*.PNG"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("gif", "*.gif,*.GIF"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("bmp", "*.bmp,*.BMP"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("tiff", "*.tiff,*.TIFF"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("raw", "*.raw,*.RAW"));
		
		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
		{
			
			 String ext="";

		        String extension=fileChooser.getFileFilter().getDescription();

		      if(extension.equals("*.jpg,*.JPG"))
		      { 
		          ext="jpg";
		      }
		      if(extension.equals("*.jpeg,*.JPEG"))
		      { 
		          ext="jpeg";
		      }
		      if(extension.equals("*.png,*.PNG"))
		      { 
		          ext="png";
		      }
		      if(extension.equals("*.gif,*.GIF"))
		      { 
		          ext="gif";
		      }
		      if(extension.equals("*.bmp,*.BMP"))
		      { 
		          ext="bmp";
		      }
		      if(extension.equals("*.tiff,*.TIFF"))
		      { 
		    	  ext="tiff";
		      }
		      if(extension.equals("*.raw,*.RAW"))
		      { 
		    	  ext="raw";
		      }
		      
		      
		  File file = fileChooser.getSelectedFile();
		  DrawPanel p = (DrawPanel) tabbedPane.getSelectedComponent();
		  
		  if(ext.equals("tiff"))
		  {
			  TIFFEncodeParam params = new TIFFEncodeParam();
	          params.setCompression(TIFFEncodeParam.COMPRESSION_DEFLATE);
	          JAI.create("filestore", p.image, file.getPath(), "TIFF", params);
		  }
		  if(ext.equals("raw"))
		  {
				
				try {
					BufferedImage bui = p.image;
					
					DataBuffer byteBuffer = bui.getData().getDataBuffer();
					byte[] imageBytes = ((DataBufferByte) byteBuffer).getData();
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(imageBytes);
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
		  }
		  if(!ext.equals("tiff")||!ext.equals("raw"))
		  {
			  try
			  {
					ImageIO.write(p.image, ext, file);
			  }
			  catch (IOException e1)
				{
					e1.printStackTrace();
				}
		  }
		   
		}
	}
	
	public static void loadImage(final JTabbedPane tabbedPane, final JFrame frame)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new CustomFileFilter("jpg", "*.jpg,*.JPG"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("jpeg", "*.jpeg,*.JPEG"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("png", "*.png,*.PNG"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("gif", "*.gif,*.GIF"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("bmp", "*.bmp,*.BMP"));
		fileChooser.addChoosableFileFilter(new CustomFileFilter("tiff", "*.tiff,*.TIFF"));
		
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		{
			  File file = fileChooser.getSelectedFile();
			  DrawPanel p = (DrawPanel) tabbedPane.getSelectedComponent();
			  String extension=fileChooser.getFileFilter().getDescription();
			  
			  if(extension.equals("*.tiff,*.TIFF"))
			  {
				  FileSeekableStream stream;
				try {
					stream = new FileSeekableStream(file);
					  TIFFDecodeParam decodeParam = new TIFFDecodeParam();
					  decodeParam.setDecodePaletteAsShorts(true);
					  ParameterBlock params = new ParameterBlock();
					  params.add(stream);
					  RenderedOp imageTiff= JAI.create("tiff", params);
					  p.image = imageTiff.getAsBufferedImage();
					  p.repaint();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			  }
			  else
			  {
				  try
				  {
					p.image = ImageIO.read(file);
					p.repaint();
				  }
				  catch (IOException e1)
				  {
					e1.printStackTrace();
				  }
			  }
		}
	}
}

class CustomFileFilter extends FileFilter
{
    private String extension;
    private String description;
 
    public CustomFileFilter(String extension,String description)
    {
        this.extension = extension;
        this.description = description;
    }
 
    public boolean accept(File file)
    {
    	
    	String ext = getExtension(file);
        return extension.equals(ext);
    }
 
    public String getExtension(File file)
    {
    	String filename = file.getPath();
        int i = filename.lastIndexOf('.');
        if ( i>0 && i<filename.length()-1 ) {
            return filename.substring(i+1).toLowerCase();
        }
        return "";
    }

	@Override
	public String getDescription()
	{
		return description;
	}
}
