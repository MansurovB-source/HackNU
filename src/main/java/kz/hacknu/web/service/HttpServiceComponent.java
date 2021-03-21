package kz.hacknu.web.service;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@Component
public class HttpServiceComponent {

    public Response get(String link, Map<String, String> params) throws IOException {
        URL url = new URL(link.concat("?")
                .concat(ParameterStringBuilder
                        .getParamsString(params)));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        String errInputLine;
        StringBuilder errContent = new StringBuilder();
        if (con.getErrorStream()!=null)
        {
            BufferedReader errIn = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
            errContent = new StringBuilder();
            while ((errInputLine = errIn.readLine()) != null) {
                errContent.append(errInputLine);
            }
            errIn.close();
        }

        in.close();
        con.disconnect();
        return new Response(status, content.toString(), errContent.toString());
    }

    public Response post(String link, Map<String, String> params, String body) throws IOException {
        URL url = new URL(link.concat("?")
                .concat(ParameterStringBuilder
                        .getParamsString(params)));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = body.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        String errInputLine;
        StringBuilder errContent = new StringBuilder();
        if (con.getErrorStream()!=null)
        {
            BufferedReader errIn = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
            errContent = new StringBuilder();
            while ((errInputLine = errIn.readLine()) != null) {
                errContent.append(errInputLine);
            }
            errIn.close();
        }

        in.close();
        con.disconnect();
        return new Response(status, content.toString(), errContent.toString());
    }


}
class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}

