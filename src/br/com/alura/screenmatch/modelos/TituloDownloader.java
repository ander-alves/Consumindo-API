package br.com.alura.screenmatch.modelos;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TituloDownloader {

    private final Gson gson;

    public TituloDownloader() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
    }

    public Titulo baixarTitulo(String busca) throws IOException, InterruptedException {
        String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=fe9c837b";


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
        return new Titulo(meuTituloOmdb);
    }


    public String serializarTitulos(List<Titulo> titulos) {
        return gson.toJson(titulos);
    }
}

