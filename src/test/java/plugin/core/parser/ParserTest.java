package plugin.core.parser;

import io.vertx.core.json.JsonObject;
import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import java.util.Objects;
import java.util.Arrays;

import plugin.core.parser.GetParser;
import plugin.core.rest.Sender;
import plugin.gui.KotoedContext;
import static plugin.core.util.Address.*;
import static plugin.gui.Utils.PsiKeys.*;
import plugin.core.parser.CreateParser;

public class ParserTest {

    final String FIELD_ID = "id";
    final String EMPTY_STRING = "";
    final String FIELD_FIND = "find";
    final String FIELD_TEXT = "text";
    final String FIELD_PAGE_SIZE = "page_size";
    final String FIELD_COURSE_ID = "course_id";
    final String FIELD_CURRENT_PAGE = "current_page";

    @Getter
    String jsonMyself;

    @Getter
    String cookie;

//    @Test
    public void parseCreateSubmissionTest() {
//        int projectId = ;
//        String revision = ;
//
//        JsonObject json = new JsonObject()
//                .put(PROJECT_ID, projectId)
//                .put(REVISION, revision);
//
//        assert CreateParser.parseCreateSubmission(jsonjson.toString()) != null;
    }

//    @Test
    public void parseCreateProjectTest() {

//        String projectName = ;
//        // TODO: 23/12/18 Fix, denizenId has long type
//        long denizenId = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_DENIZEN_ID));
//        int courseId = 8;
//        String repoType = ;
//        String repoUrl = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_REPO_URL));
//
//        JsonObject json = new JsonObject()
//                .put(NAME, projectName)
//                .put(DENIZEN_ID, denizenId)
//                .put(COURSE_ID, courseId)
//                .put(REPO_TYPE, repoType)
//                .put(REPO_URL, repoUrl);
//
//        assert CreateParser.parseCreateProject(jsonjson.toString()) != null;
    }

    @Test
    public void parseCreateCommentTest() {
        long submissionId = 9255;
        long authorId = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_DENIZEN_ID));
        String sourceFile = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SOURCEFILE));
        long sourceLine = Objects.requireNonNull(KotoedContext.project.getUserData(PSI_KEY_CURRENT_SOURCELINE));
        String text = "Some text!";

        JsonObject json = new JsonObject()
                .put(SUBMISSION_ID, submissionId)
                .put(AUTHOR_ID, authorId)
                .put(SOURCEFILE, sourceFile)
                .put(SOURCELINE, sourceLine)
                .put(TEXT, text);

        assert CreateParser.parseCreateComment(json.toString()) != null;
    }

//    @Test
    public void parseCookieFromHeadersTest() {
//        String urlSignIn = URL_GLOBAL_SIGN_IN;
//        final String FIELD_COOKIE = "Cookie";
//        final HttpClient client = HttpClientBuilder.create().build();
//
//        HttpPost post = new HttpPost(urlSignIn);
//        post.setEntity(new StringEntity(jsonMyself));
//        if (!cookie.isEmpty()) {
//            post.setHeader(FIELD_COOKIE, cookie);
//        }
//        else cookie = null;
//        client.execute(post);
//
//        HttpResponse responseWithCookies = post(urlSignIn, jsonMyself, cookie);
//        String stringOfHeaders = Arrays.toString(responseWithCookies.getAllHeaders());
//        cookie = GetParser.parseCookieFromHeaders(stringOfHeaders);
//
//        assert !cookie.isEmpty();
    }

    @Test
    public void parseCoursesTest() {
        JsonObject json = new JsonObject().put(FIELD_TEXT, EMPTY_STRING);

        assert GetParser.parseCourses(json.toString()) != null;
    }

//    @Test
    public void parseProjectsTest() {
        JsonObject json = new JsonObject().put(FIELD_TEXT, EMPTY_STRING);

        assert GetParser.parseProjects(json.toString()) != null;
    }

    @Test
    public void parseCommentsTest() {
        long submissionId = 9255;
        JsonObject json = new JsonObject().put(FIELD_ID, submissionId);

        assert GetParser.parseComments(json.toString()) != null;
    }

    @Test
    public void parseSubmissionsTest() {
        int pageSize = 20;
        int currentPage = 0;
        int courseId = 8; // FP

        JsonObject json = new JsonObject()
                .put(FIELD_TEXT, "")
                .put(FIELD_PAGE_SIZE, pageSize)
                .put(FIELD_CURRENT_PAGE, currentPage)
                .put(FIELD_FIND, new JsonObject().put(FIELD_COURSE_ID, courseId));

        assert GetParser.parseSubmissions(json.toString()) != null;
    }

    @Test
    public void getDenizenIdTest() {
        Sender sender = new Sender("GLOBAL");
        sender.signIn("mikhailme", "qwerty12345");
        String whoAmI = sender.getWhoAmI();
        long denizenId = GetParser.getDenizenId(whoAmI);

        assert denizenId == 441;
    }
}
