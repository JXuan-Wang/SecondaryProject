package org.example.systemserver.service.impl;

import jakarta.annotation.Resource;
import org.example.systemserver.manager.StatisticManager;
import org.example.systemserver.result.NameValue;
import org.example.systemserver.result.SummaryData;
import org.example.systemserver.service.StatisticService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Resource
    private StatisticManager statisticManager;

    @Override
    public SummaryData loadSummaryData() {
        return statisticManager.loadSummaryData();
    }

    @Override
    public List<NameValue> loadSaleFunnelData() {
        return statisticManager.loadSaleFunnelData();
    }

    @Override
    public List<NameValue> loadSourcePieData() {
        return statisticManager.loadSourcePieData();
    }
}
