package com.example.demo.dto;

import com.example.demo.entity.Seat;
import com.example.demo.entity.SeatDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SeatContainer {
    private List<String> indicators;
    private List<SeatDetail> seats;
    public SeatContainer(List<SeatDetail> seatDetails) {
        indicators = new ArrayList<>();
        seats = new ArrayList<>();
        indicators.add("A");
        indicators.add("B");
        indicators.add("C");
        indicators.add("");
        indicators.add("D");
        indicators.add("E");
        indicators.add("F");
        seats.add(null);
        seats.add(null);
        seats.add(null);
        seats.add(null);
        seats.add(null);
        seats.add(null);
        seats.add(null);
        int rows = seatDetails.size() / 6;
        int addedIndex = 0;
        for (int i = 0; i < seatDetails.size() + rows; i++) {
            if (i % 7 == 3) {
                indicators.add(String.valueOf(i / 7 + 1));
                seats.add(null);
            }
            else {
                indicators.add("");
                seats.add(seatDetails.get(addedIndex++));
            }
        }
    }
}
