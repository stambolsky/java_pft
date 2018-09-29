package ru.stqa.pft.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;


import java.io.IOException;

public class TestBase {
    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues/" + issueId + ".json?limit=500")).returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonObject parsedJsonObject = parsed.getAsJsonObject();

        System.out.println(parsedJsonObject);

        String statusIssue = parsedJsonObject.get("issues").getAsJsonArray().get(0).getAsJsonObject().
                get("state_name").toString();

        System.out.println("IssueStatus = " + statusIssue);

        if (statusIssue.equals("Resolved")) {
            return false;
        } else {
            return true;
        }
    }

    public Executor getExecutor(){
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }
}
