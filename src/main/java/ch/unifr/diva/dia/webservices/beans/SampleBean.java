package ch.unifr.diva.dia.webservices.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Marcel Würsch on 10.10.2014.
 */
@XmlRootElement
public class SampleBean {

    public SampleBean(){}

    @XmlElement
    public String name;

    @XmlElement
    public String value;

}
