package ch.unifr.diva.dia.webservices;

import ch.unifr.diva.dia.linesegmentation.Image;
import ch.unifr.diva.dia.linesegmentation.Line;
import ch.unifr.diva.dia.linesegmentation.LineSegmentation;
import ch.unifr.diva.dia.webservices.beans.SegmentationBean;
import ch.unifr.diva.dia.webservices.helper.WebServiceBase;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Marcel Würsch on 24.06.2014.
 */
@Path("/segmentation")
public class SegmentationService {
    @GET
    @Path("/word")
    @Produces("application/json")
    public String getWordSegmentInfo() throws JSONException {
        JSONArray segmentInfos = new JSONArray();
        JSONObject segmentInfo = new JSONObject();
        JSONObject infos = new JSONObject();
        infos.put("shortInfo","Segments a text line into words");
        segmentInfo.put("infos",infos);

        JSONObject neededValues = new JSONObject();
        JSONObject values = new JSONObject();
        values.put("url","URL to the image");
        values.put("top","the y-location of the uppermost pixel in the image to process (set to 0 if the whole image should be processed).");
        values.put("bottom","the y-location of the lowermost pixel in the image to process (set to 'image-height' if the whole image should be processed).");
        values.put("left","the x-location of the leftmost pixel in the image to process (set to 0 if the whole image should be processed).");
        values.put("right","the x-location of the rightmost pixel in the image to process (set to 'image-width' if the whole image should be processed).");
        neededValues.put("inputValues",values);
        segmentInfos.put(segmentInfo);
        segmentInfos.put(neededValues);

        return segmentInfos.toString();
    }

    @POST
    @Path("/line")
    @Consumes("application/json")
    @Produces("application/json")
    public String segmentImageLine(SegmentationBean data) throws IOException, JSONException{
        BufferedImage bi = ImageIO.read(new URL(data.url));
        Image img = new Image(bi);
        LineSegmentation seg = new LineSegmentation(img,Integer.valueOf(data.left),
                Integer.valueOf(data.top),
                Integer.valueOf(data.right),
                Integer.valueOf(data.bottom));
        List<Map<String,Object>> results = new LinkedList<Map<String, Object>>();

        for(Line l : seg.getLines()){
            Map<String,Object> values = new TreeMap<String, Object>();
            values.put("top",l.top);
            values.put("bottom",l.bottom);
            values.put("left",l.left);
            values.put("right",l.right);
            results.add(values);
        }
        return WebServiceBase.createJsonArray(results).toString();
    }
}
