package ch.unifr.diva.dia.webservices;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ch.unifr.diva.dia.linesegmentation.Image;
import ch.unifr.diva.dia.linesegmentation.Line;
import ch.unifr.diva.dia.textlineextraction.Step2Gabor;
import ch.unifr.diva.dia.webservices.beans.SampleBean;
import ch.unifr.diva.dia.webservices.helper.WebServiceBase;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.imageio.ImageIO;
import javax.ws.rs.*;

/**
 * Created by Hao Wei on 10.10.2014.
 */
@Path("/textline")
public class TextLineExtractionService {

    @GET
    @Produces("application/json")
    public String getGeneralInfo() throws JSONException {	
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("info","GeneralInfos about the methods");
        jsonObject.put("secondInfo","Second GeneralInfos about the methods");
        return jsonObject.toString();
    }

    @GET
    @Path("/gabor")
    @Produces("application/json")
    public String getMethodInformation() throws MalformedURLException, IOException, JSONException{
    	
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
        return WebServiceBase.createJsonArray(results).toString();
    }

    @POST
    @Path("/myMethod")
    @Consumes("application/json")
    @Produces("application/json")
    public String performMyMethod(SampleBean data) throws JSONException{

        //Perform your method
        //Build your data into a List<Map<String,Object>>
        //return WebServiceBase.createJsonArray(results).toString();
    	return "";
    	
    }

}
