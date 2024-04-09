package ForeignExchange.ForeignExchangeApp.controller;

import ForeignExchange.ForeignExchangeApp.model.mysql.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.service.ConversionHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                                          @RequestParam(name = "fromId", required = false) String fromId,
                                          @RequestParam(name = "toId", required = false) String toId,
                                          RedirectAttributes redirectAttributes
    ) {

        if (!fromDate.isEmpty() && !toDate.isEmpty()) {
            redirectAttributes.addFlashAttribute("fromDate", fromDate);
            redirectAttributes.addFlashAttribute("toDate", toDate);
        }
        if (fromId != null && toId != null) {
            if (!fromId.isEmpty() && !toId.isEmpty()) {
                int idFrom = Integer.parseInt(fromId);
                int idTo = Integer.parseInt(toId);
                if (idFrom != 0 && idTo != 0) {
                    redirectAttributes.addFlashAttribute("fromId", fromId);
                    redirectAttributes.addFlashAttribute("toId", toId);
                }
            }
        }
        System.out.println();

        return "redirect:filtered-data";
    }

    @GetMapping("/filtered-data")
    public String getFilteredResults(Model model,
                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 10)
                                     Pageable pageable,
                                     @ModelAttribute("fromId") String fromId,
                                     @ModelAttribute("toId") String toId,
                                     @ModelAttribute("fromDate") String fromDate,
                                     @ModelAttribute("toDate") String toDate) {

        Page<ConversionHistory> allConversionsFiltered = null;

        if (fromId != null && toId != null) {
            if (!fromId.isEmpty() && !toId.isEmpty()) {
                Integer idFrom = Integer.parseInt(fromId);
                Integer idTo = Integer.parseInt(toId);
                allConversionsFiltered = conversionHistoryService.getAllConversionsFilteredById(idFrom, idTo, pageable);
            }
        }

        if (fromDate != null && toDate != null && !fromDate.isEmpty() && !toDate.isEmpty()) {
            LocalDate from = LocalDate.parse(fromDate);
            LocalDate to = LocalDate.parse(toDate);
            allConversionsFiltered = conversionHistoryService.getAllConversionsFilteredByDate(from, to, pageable);
        }

        model.addAttribute("filtered", allConversionsFiltered);

        System.out.println();

        return "filtered-results";
    }

}
