package com.project.converter;

import com.google.gson.JsonElement;
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
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class RatesLoader {
    public ArrayList<Rate> rates;
    private static final HttpGet root = new HttpGet("http://api.exchangerate.host/latest");

    public void loadRates(String baseCurrency) throws URISyntaxException, IOException {
        int timeout = 5;
        JsonObject result;
        BufferedReader reader;
        HttpGet dest = new HttpGet();
        URI uri = new URIBuilder(root.getURI())
                .addParameter("base", baseCurrency)
                .build();
        dest.setURI(uri);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        try {
            CloseableHttpResponse response = client.execute(dest);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity ent = response.getEntity();
                reader = new BufferedReader(new InputStreamReader(ent.getContent()));
                result = JsonParser.parseReader(reader).getAsJsonObject().get("rates").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = result.entrySet();
                rates = new ArrayList<>();
                for (Map.Entry<String, JsonElement> entry : entries) {
                    rates.add(new Rate(entry.getKey(), entry.getValue().getAsDouble(), CurrencyLoader.obtainName(entry.getKey())));
                }
            }
        } catch (SocketTimeoutException exception) {
            Logging.errorLog("Rates couldn't be fetched! Possible API provider's issues!");
        } catch (UnknownHostException exception) {
            Logging.errorLog("Host unreachable! Possible cause might be lack of client's internet connection!");
        }
        client.close();
    }
}
