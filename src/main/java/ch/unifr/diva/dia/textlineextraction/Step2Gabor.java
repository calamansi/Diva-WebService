package ch.unifr.diva.dia.textlineextraction;

import ch.unifr.diva.dia.linesegmentation.Image;
import ch.unifr.diva.dia.webservices.helper.WebServiceBase;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;


/**
 * @author Hao Wei
 *
 */
public class Step2Gabor {
	public HashMap<String, Object> resultsTextline;
	public ArrayList<Polygon> polygonsGT = new ArrayList<Polygon>();
	
	public HashMap<String, Object> getResults(){
		GaborClustering gaborClustering = new GaborClustering();
		gaborClustering.start(System.getProperty("user.dir") + "/images" + "/d-008.0.1091.205.507.2337.png"
				, System.getProperty("user.dir") + "/images" + "/GaborOutput.png");
		    
		TextLineExtraction tle = new TextLineExtraction();
		polygonsGT = tle.start();
		resultsTextline = new HashMap<String, Object>();	
		List<int[][]> textLinesList = new ArrayList<int[][]>();

		for (Polygon polygon : polygonsGT){
			int[][] points = new int[polygon.npoints][2]; 
			for (int i = 0; i < polygon.npoints; i++){
				points[i][0] = polygon.xpoints[i];
				points[i][1] = polygon.ypoints[i];
			}
			textLinesList.add(points);
		}		
		resultsTextline.put("textLines", textLinesList);	
		return resultsTextline;
	}

	public static void main(String[] args) throws IOException {
		BufferedImage bi = ImageIO.read(new URL("http://diuf.unifr.ch/diva/divadiaweb/d-008.0.1091.205.507.2337.png"));
        Image img = new Image(bi);
        Image sub = img.getSubImage(img, Integer.valueOf(0)
        		, Integer.valueOf(0), 
        		Integer.valueOf(507), 
        		Integer.valueOf(2337));
        sub.write(System.getProperty("user.dir") + "/images" + "/d-008.0.1091.205.507.2337.png");
    	
        Step2Gabor step2Gabor = new Step2Gabor();
		HashMap<String, Object> resultsTextline = step2Gabor.getResults();
		
        List<Map<String,Object>> results = new LinkedList<Map<String, Object>>();
        Map<String,Object> values = resultsTextline;
        results.add(values);       
        System.out.println(WebServiceBase.createJsonArray(results).toString());
	}
}
