package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ServicesConfiguration extends Configuration {
    @JsonProperty
    public String mongohost = "ds139425.mlab.com";

    @Min(1)
    @Max(65535)
    @JsonProperty
    public int mongoport = 39425;

    @JsonProperty @NotEmpty
    public String mongodb = "orderdb";

    @JsonProperty
    public String dbUri = "mongodb://root:root@ds139425.mlab.com:39425/orderdb";

    protected static ServicesConfiguration instance = null;

    public ServicesConfiguration (){
//        instance = this;
    }

    public static ServicesConfiguration getInstance(){
        if(instance == null) {
            instance = new ServicesConfiguration();
        }
        return instance;
    }
}
