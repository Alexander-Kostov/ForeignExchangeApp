package ForeignExchange.ForeignExchangeApp.controller;

import ForeignExchange.ForeignExchangeApp.model.ConversionHistory;
import ForeignExchange.ForeignExchangeApp.repository.CurrencyMySQLRepository;
import ForeignExchange.ForeignExchangeApp.service.ConversionHistoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/conversion-history")
public class CurrencyHistoryController {
    private ConversionHistoryService conversionHistoryService;

    public CurrencyHistoryController(ConversionHistoryService conversionHistoryService) {
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

        model.addAttribute("history" , allOffers);
        return "conversion-history";
    }

}
