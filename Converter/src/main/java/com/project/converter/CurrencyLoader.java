package com.project.converter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class CurrencyLoader {
    public static ArrayList<Currency> currencies;
    private static final File res = new File("currencies.json");

    public static void loadCurrs() throws IOException {
        Gson gson = new Gson();
        if (res.createNewFile() || res.length() == 0) {
            updateCurrs();
        } else {
            try (FileReader reader = new FileReader(res)) {
                currencies = gson.fromJson(reader, new TypeToken<ArrayList<Currency>>() {
                }.getType());
            }
        }
    }

    public static void updateCurrs() throws IOException {
        int timeout = 5;
        JsonObject result;
        BufferedReader reader;
        HttpGet dest = new HttpGet("https://api.exchangerate.host/symbols");
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        try {
            CloseableHttpResponse response = client.execute(dest);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity ent = response.getEntity();
                reader = new BufferedReader(new InputStreamReader(ent.getContent(), StandardCharsets.UTF_8));
                result = JsonParser.parseReader(reader).getAsJsonObject().get("symbols").getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = result.entrySet();
                currencies = new ArrayList<>();
                for (Map.Entry<String, JsonElement> entry : entries) {
                    String desc = entry.getValue().getAsJsonObject().get("description").getAsString();
                    currencies.add(new Currency(desc, entry.getKey()));
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(res, false), StandardCharsets.UTF_8)) {
                    gson.toJson(currencies, writer);
                    writer.flush();
                }
            }
        } catch (SocketTimeoutException exception) {
            Logging.errorLog("Currency data couldn't be fetched! Possible API provider's issues!");
        } catch (UnknownHostException exception) {
            Logging.errorLog("Host unreachable! Possible cause might be lack of client's internet connection!");
        }
        client.close();
    }

    public static String obtainName(String code) {
        Currency curr = currencies.stream().filter(p -> p.getCode().equals(code)).findAny().orElse(null);
        if (curr != null)
            return curr.getName();
        else return "";
    }
}
