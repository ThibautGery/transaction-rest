

import org.junit.Test;
import play.twirl.api.Content;

import static org.assertj.core.api.Assertions.assertThat;


/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        Content html = views.html.documentation.render("Swagger Documentation");
        assertThat(html.contentType()).isEqualTo("text/html");
        assertThat(html.body()).contains("swagger");
    }

}
