package umm3601.OAUTH;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.sparkjava.DefaultHttpActionAdapter;
import org.pac4j.sparkjava.SparkWebContext;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.HashMap;

import static spark.Spark.halt;

public class DDGHttpActionAdapter extends DefaultHttpActionAdapter {


    public DDGHttpActionAdapter() {

    }

    @Override
    public Object adapt(int code, SparkWebContext context) {
        if (code == HttpConstants.UNAUTHORIZED) {
            halt(401, "401 Error");
        } else if (code == HttpConstants.FORBIDDEN) {
            halt(403, "403 Error");
        } else {
            return super.adapt(code, context);
        }
        return null;
    }
}
