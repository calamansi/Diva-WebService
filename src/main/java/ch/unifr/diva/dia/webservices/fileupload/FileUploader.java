package ch.unifr.diva.dia.webservices.fileupload;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/file")
    public class FileUploader {

        private final String UPLOADED_FILE_PATH = "D:\\uploaded\\";

        @POST
        @Path("/upload")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        public Response uploadFile(
                @FormDataParam("file") InputStream uploadedInputStream,
                @FormDataParam("file") FormDataContentDisposition fileDetail) {


            //recommand to test call arguments here (null, empty) in order to throw errors
            if (fileDetail.getName()==null || uploadedInputStream==null) return Response.status(400).build();


            String location = UPLOADED_FILE_PATH + fileDetail.getFileName() + ".png";
            System.out.println("before write ["+location+"]");
            saveFile(uploadedInputStream, location );
            String output = "File saved to  server location : " + location;
            System.out.println("file written ["+location+"]");
            return Response.ok().entity(output).build();

        }

        /**
         * save File v2 with apache.commons.io
         **/
        private void saveFile(InputStream uploadedInputStream,String serverLocation) {
            try {
                byte[] bytes = IOUtils.toByteArray(uploadedInputStream);
                OutputStream outputStream= new FileOutputStream(new File(serverLocation));
                IOUtils.write(bytes, outputStream);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }