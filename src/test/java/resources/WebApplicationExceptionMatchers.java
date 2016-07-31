package resources;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import utils.OperationStatus;

import javax.ws.rs.WebApplicationException;

import static java.lang.String.format;
import static org.hamcrest.core.AllOf.allOf;

public class WebApplicationExceptionMatchers {

    public static Matcher<WebApplicationException> webApplicationExceptionWith(Matcher<WebApplicationException>... matchers) {
        return allOf(matchers);
    }

    public static Matcher<WebApplicationException> status(final int statusCode) {
        return new TypeSafeMatcher<WebApplicationException>() {
            @Override
            public boolean matchesSafely(WebApplicationException e) {
                return statusCode == e.getResponse().getStatus();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("With status code " + statusCode);
            }
        };
    }

    public static Matcher<WebApplicationException> message(final String message) {
        return new TypeSafeMatcher<WebApplicationException>(){
            @Override
            public boolean matchesSafely(WebApplicationException e) {
                OperationStatus operationStatus = (OperationStatus) e.getResponse().getEntity();
                return StringUtils.equals(message, operationStatus.getStatusMessage());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(format("With description \'%s\'",message));
            }
        };
    }

}
