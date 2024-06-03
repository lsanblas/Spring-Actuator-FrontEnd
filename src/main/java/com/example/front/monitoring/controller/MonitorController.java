package com.example.front.monitoring.controller;

import com.example.front.monitoring.model.ActuatorData;
import com.example.front.monitoring.model.ActuatorResponse;
import com.example.front.monitoring.model.ActuatorResponsePaginated;
import com.example.front.monitoring.model.Exchange;
import com.example.front.monitoring.service.ActuatorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
public class MonitorController {

    private static final Logger log = LoggerFactory.getLogger(MonitorController.class);

    private final ActuatorService actuatorService;

    @Value("${spring.table.pagination}")
    private String defaulPageSize;

    @GetMapping("/index")
    public String indexRendering(Model model, @RequestParam("page") Optional<Integer> page,  @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(Integer.parseInt(defaulPageSize));
        try{
            ActuatorResponsePaginated actuatorRequestsData = this.actuatorService.getActuatorRequestsDataPaginated(PageRequest.of(currentPage - 1, pageSize));
            String ok = actuatorRequestsData.ok().toString();
            String notFound = actuatorRequestsData.notFound().toString();
            String badRequest = actuatorRequestsData.badRequest().toString();
            String internal = actuatorRequestsData.internal().toString();
            Page<Exchange> exchanges = actuatorRequestsData.exchanges();
            model.addAttribute("ok", ok);
            model.addAttribute("notFound", notFound);
            model.addAttribute("badRequest", badRequest);
            model.addAttribute("internal", internal);
            model.addAttribute("exchanges", exchanges);
            model.addAttribute("standardDate", new Date());
            model.addAttribute("error", false);
            int totalPages = exchanges.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        } catch (Exception e) {
            log.error("Error while getting the actuator data");
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
        }
        return "index";
    }
}
