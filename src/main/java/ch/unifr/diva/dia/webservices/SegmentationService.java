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
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Marcel Würsch on 24.06.2014.
 */
@Path("/segmentation")
public class SegmentationService {

    @javax.ws.rs.core.Context
    ServletContext context;

    @GET
    @Produces("application/json")
    public String getSegmentationInformation() throws IOException{

        byte[] encoded = Files.readAllBytes(Paths.get(new File(context.getRealPath("/json/segmentation.json")).toURI()));
        return new String(encoded);
    }

    @GET
    @Path("/line")
    @Produces("application/json")
    public String getLineSegmentInfo() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(new File(context.getRealPath("/json/lineSegmentation.json")).toURI()));
        return new String(encoded);
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
