package ForeignExchange.ForeignExchangeApp.controller;

import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.service.ConversionHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/conversion-history")
public class ConversionHistoryController {
    private final ConversionHistoryService conversionHistoryService;

    public ConversionHistoryController(ConversionHistoryService conversionHistoryService) {
        this.conversionHistoryService = conversionHistoryService;
    }

    @GetMapping("/all")
    public String showConversionHistory(Model model,
                                        @PageableDefault(
                                                sort = "id",
                                                direction = Sort.Direction.ASC,
                                                size = 10
                                        )
                                        Pageable pageable) {

        var allOffers = this.conversionHistoryService.getAllConversions(pageable);

        model.addAttribute("history", allOffers);
        return "conversion-history";
    }

    @GetMapping("/filter")
    public String filterConversionHistory() {

        return "filter-history";
    }

    @PostMapping("/filter")
    public String showFilteredConversions(@RequestParam(name = "fromDate", required = false) String fromDate,
                                          @RequestParam(name = "toDate", required = false) String toDate,
                                          @RequestParam(name = "fromId", required = false) Integer fromId,
                                          @RequestParam(name = "toId", required = false) Integer toId,
                                          RedirectAttributes redirectAttributes, Pageable pageable) {
        Page<ConversionHistory> allConversionsFiltered = null;

        if (!fromDate.isEmpty() && !toDate.isEmpty()) {
            allConversionsFiltered = conversionHistoryService.getAllConversionsFilteredByDate(LocalDate.parse(fromDate), LocalDate.parse(toDate), pageable);
        }
        System.out.println();
        if (fromId != null && toId != null) {
            if (fromId != 0 && toId != 0) {
                allConversionsFiltered = conversionHistoryService.getAllConversionsFilteredById(fromId, toId, pageable);
            }
        }


        redirectAttributes.addFlashAttribute("history", allConversionsFiltered);
        return "redirect:filtered-data";
    }


    @GetMapping("/filtered-data")
    public String getFilteredResults(Model model, @PageableDefault(
            sort = "id",
            direction = Sort.Direction.ASC,
            size = 10
    ) Pageable pageable, @ModelAttribute("history") Page<ConversionHistory> filteredConversions) {

        model.addAttribute("history", filteredConversions);
        return "filtered-results";
    }

}
