package com.bootcamp.demo.service;

import com.bootcamp.demo.model.RentalHistory;
import com.bootcamp.demo.repository.RentalHistoryRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RentalHistoryService {
    public final RentalHistoryRepository rentalHistoryRepository;

    public RentalHistoryService(RentalHistoryRepository rentalHistoryRepository) {
        this.rentalHistoryRepository = rentalHistoryRepository;
    }

    public void saveRentalHistory(RentalHistory rentalHistory) {
        this.rentalHistoryRepository.saveRentalHistory(rentalHistory);
    }

    public List<RentalHistory> getRentalHistoryForUser(String email) {
        return this.rentalHistoryRepository.getRentalHistoryForUser(email);
    }

    private BigDecimal getCostPerDay(Date date, String email) {
        List<RentalHistory> history = this.rentalHistoryRepository.getRentalHistoryForUser(email).stream().filter(h -> DateUtils.isSameDay(h.getStartDate(), date)).collect(Collectors.toList());
        return history.stream().map(RentalHistory::getTripCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<RentalHistory> getSortedRentalHistoryForUser(String email, String field, String order) {
        List<RentalHistory> history = this.rentalHistoryRepository.getRentalHistoryForUser(email);
        switch (field) {
            case "scooterDocumentName":
                if ((order.equals("asc"))) {
                    history.sort(Comparator.comparing(RentalHistory::getScooterDocumentName));
                } else {
                    history.sort(Comparator.comparing(RentalHistory::getScooterDocumentName).reversed());
                }
                break;
            case "startDate":
                if ((order.equals("asc"))) {
                    history.sort(Comparator.comparing(RentalHistory::getStartDate));
                } else {
                    history.sort(Comparator.comparing(RentalHistory::getStartDate).reversed());
                }
                break;
            case "endDate":
                if ((order.equals("asc"))) {
                    history.sort(Comparator.comparing(RentalHistory::getEndDate));
                } else {
                    history.sort(Comparator.comparing(RentalHistory::getEndDate).reversed());
                }
                break;
            case "tripCost":
                if ((order.equals("asc"))) {
                    history.sort(Comparator.comparing(RentalHistory::getTripCost));
                } else {
                    history.sort(Comparator.comparing(RentalHistory::getTripCost).reversed());
                }
                break;
        }
        return history;
    }

    public List<Object> getStatistics(String email) {
        List<Object> data = new ArrayList<>();
        Date date = new Date();
        long oneDay = 1000 * 3600 * 24;
        for (int i = 6; i >= 0; i--) {
            Date tmp = new Date();
            tmp.setTime(date.getTime() - oneDay * i);
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            data.add(List.of(formatter.format(tmp), getCostPerDay(tmp, email)));
        }
        return data;
    }
}
