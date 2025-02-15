package it.ipzs.cieid.Firma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PdfPreview {
    private JPanel prPanel;
    private String filePath;
    private String signImagePath;
    private int pdfPageIndex;
    private int pdfNumPages;
	private List<Image> images = new ArrayList<>();
	private JLabel imgLabel;
    private ImageIcon imgIcon;
    private MoveablePicture signImage;
    private JPanel imgPanel;
    
    public PdfPreview(JPanel panelPdfPreview, String pdfFilePath, String signImagePath)
    {
    	this.prPanel = panelPdfPreview;
    	this.filePath = pdfFilePath;
    	this.signImagePath = signImagePath;
    	this.pdfPageIndex = 0;
    	imgIcon = new ImageIcon();
    	imgLabel = new JLabel();
    	imgPanel = new JPanel();
		imgPanel.setLayout(new BorderLayout(0,0));
		imgPanel.setBackground(Color.white);
		signImage = new MoveablePicture(signImagePath);
		imgPanel.add(signImage );
		imgPanel.add(imgLabel);

		renderImages(filePath);
		showPreview();
    }

	public static Image convertToImage(BufferedImage bufferedImage) {
		/* Set the dimension of the image */
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		/* And convert to java.awt.Image */
		return bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	private void renderImages(String pdfFilePath) {
		prPanel.removeAll();

		try (final PDDocument document = PDDocument.load(new File(pdfFilePath))){
			pdfNumPages = document.getNumberOfPages();
			System.out.println("Pdf page: " + pdfNumPages);
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			for (int page = 0; page < pdfNumPages; ++page) {
				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
				images.add(convertToImage(bim));
			}
		} catch (IOException e){
			System.err.println("Exception while trying to create pdf document - " + e);
			throw new RuntimeException();
		}
	}
    
    private void showPreview()
    {
    	Image tmpImg = images.get(pdfPageIndex);
    	
    	int width = prPanel.getWidth();
    	int height = prPanel.getHeight();
    	
    	int tmpImgWidth = tmpImg.getWidth(null);
    	int tmpImgHeight =  tmpImg.getHeight(null);
    	
    	int imgHeigth = height;
    	int imgWidth = width;
    
    	if( tmpImgWidth > tmpImgHeight)
    	{
    		imgHeigth  = (int)(width*tmpImgHeight)/tmpImgWidth;
    		
    		if(imgHeigth > height)
    		{
    			imgWidth = (int)(height*tmpImgWidth)/tmpImgHeight;
				imgHeigth = (int)(imgWidth*tmpImgHeight)/tmpImgWidth;
    		}
    	}else
    	{
    		imgWidth = (int)(height*tmpImgWidth)/tmpImgHeight;
                    
            if(imgWidth > width)
            {
            	imgHeigth = (int)(width*imgHeigth)/tmpImgWidth;
        		imgWidth = (int)(imgHeigth*tmpImgWidth)/imgHeigth;
            }
    	}
    	
    	
		imgIcon.setImage(tmpImg.getScaledInstance(imgWidth, imgHeigth, Image.SCALE_AREA_AVERAGING));
		imgLabel.setIcon(imgIcon);
		imgLabel.setHorizontalAlignment(JLabel.CENTER);
		imgLabel.setVerticalAlignment(JLabel.CENTER);
	    imgLabel.revalidate();
	    imgLabel.repaint();
		
		//imgPanel.removeAll();
		imgPanel.setMaximumSize(new Dimension(imgWidth, imgHeigth));
		imgPanel.updateUI();
		
		prPanel.removeAll();
		prPanel.add(imgPanel);
		prPanel.updateUI();
    }
    
    public void prevImage()
    {
		if((pdfPageIndex -1) >= 0)
		{
			pdfPageIndex -= 1;
		}
		
		showPreview();
    }
    
    public void nextImage()
    {
		if((pdfPageIndex + 1) < pdfNumPages)
		{
			pdfPageIndex += 1;
		}
		
		showPreview();
    }
    
    public int getSelectedPage()
    {
    	return pdfPageIndex;
    }
    
    public float[] signImageInfos()
    {
    	float infos[] = new float[4];
    	
    	float x = ((float)signImage.getX() / (float)imgPanel.getWidth());
    	float y = ((float)(signImage.getY() + signImage.getHeight())/ (float)imgPanel.getHeight());
    	float w = ((float)signImage.getWidth() / (float)imgPanel.getWidth());
    	float h = ((float)signImage.getHeight() / (float)imgPanel.getHeight());
    	
    	infos[0] = x;
    	infos[1] = y;
    	infos[2] = w;
    	infos[3] = h;
    	
    	return infos;
    }
    
}
