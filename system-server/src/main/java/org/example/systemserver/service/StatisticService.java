package org.example.systemserver.service;

import org.example.systemserver.result.NameValue;
import org.example.systemserver.result.SummaryData;

import java.util.List;

public interface StatisticService {
    SummaryData loadSummaryData();

    List<NameValue> loadSaleFunnelData();

    List<NameValue> loadSourcePieData();
}
