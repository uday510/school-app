package com.app.school.controller;

import com.app.school.model.Holiday;
import com.app.school.repository.HolidayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
public class HolidaysController {

    private final HolidayRepository holidayRepository;

    public HolidaysController(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display, Model model) {

        if (null != display && display.equals("all")) {
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        } else if (null != display && display.equals("federal")) {
            model.addAttribute("federal",true);
        } else if (null != display && display.equals("festival")) {
            model.addAttribute("festival",true);
        }

        Iterable<Holiday> holidays = holidayRepository.findAll();
        List<Holiday> list = StreamSupport.stream(holidays.spliterator(), false).toList();
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(
                    type.toString(),
                    (
                            list.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())
                    )
            );
        }

        return "holidays.html";
    }

}