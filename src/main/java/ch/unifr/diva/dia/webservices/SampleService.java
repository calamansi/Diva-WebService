package ch.unifr.diva.dia.webservices;

import ch.unifr.diva.dia.webservices.beans.SampleBean;

import javax.ws.rs.*;

/**
 * Created by Marcel Würsch on 10.10.2014.
 */
@Path("/myPath")
public class SampleService {

    @GET
    @Path("/myMethod")
    @Produces("application/json")
    public String getMethodInformation(){
        return "";
    }

    @POST
    @Path("/myMethod")
    @Consumes("application/json")
    @Produces("application/json")
    public String performMyMethod(SampleBean data){

        //Perform your method
        //Build your data into a List<Map<String,Object>>
        //return WebServiceBase.createJsonArray(results).toString();
        return "";
    }

}
