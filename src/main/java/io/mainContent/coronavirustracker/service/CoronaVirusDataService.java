package io.mainContent.coronavirustracker.service;

import io.mainContent.coronavirustracker.models.LocationStats;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service  //service is a singleton, the application will create one instance of it.
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStats = new ArrayList<>();

    @PostConstruct // @PostConstruct means construct this when the instance of this class is done
    @Scheduled(cron = "* * 1 * * *")  // run this on a schedule (second minute hour day..)
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        List<LocationStats> newStats = new ArrayList<>();

        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int secondLatestCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setDiffFromPrevDay(latestCases - secondLatestCases);
            System.out.println(locationStat);
            newStats.add(locationStat);
        }
        this.allStats = newStats;
    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }
}
