package com.project.converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.function.Consumer;


public class Converter {
    private static final HttpGet root = new HttpGet("http://api.exchangerate.host/convert");

    public static double Req(String from, String to) throws IOException, URISyntaxException {
        int timeout = 5;
        JsonObject result;
        BufferedReader in;
        HttpGet dest = new HttpGet();
        double ret = -1;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        URI uri = new URIBuilder(root.getURI())
                .addParameter("from", from)
                .addParameter("to", to)
                .build();
        dest.setURI(uri);
        try {
            CloseableHttpResponse response = client.execute(dest);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity ent = response.getEntity();
                in = new BufferedReader(new InputStreamReader(ent.getContent()));
                result = JsonParser.parseReader(in).getAsJsonObject();
                ret = Double.parseDouble(result.get("result").getAsString());
            }
        } catch (SocketTimeoutException exception) {
            Logging.errorLog("Conversion request timed out! Possible API provider issues!");
        } catch (UnknownHostException exception) {
            Logging.errorLog("Host unreachable! Possible cause might be lack of client's internet connection!");
        }
        client.close();
        return ret;
    }

    public static void Convert(String from, String to, Consumer<Double> callback) {
        try {
            double val = Req(from, to);
            callback.accept(val);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
