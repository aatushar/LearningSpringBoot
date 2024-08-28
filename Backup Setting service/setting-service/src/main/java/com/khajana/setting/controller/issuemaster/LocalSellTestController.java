package com.khajana.setting.controller.issuemaster;

import com.khajana.setting.entity.issuemaster.LocalSellTest;
import com.khajana.setting.service.issuemaster.LocalSellTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class LocalSellTestController {
    //    private final LocalSellTestController localSellTestRepository;
//
//    @Autowired
//    public LocalSellTestController(LocalSellTestRepository localSellTestRepository) {
//        this.localSellTestRepository = localSellTestRepository;
//    }
    @Autowired
    LocalSellTestService localSellTestService;

    @GetMapping("/list")
    public List<LocalSellTest> getStoreList() {
        List<LocalSellTest> stores = localSellTestService.executeStoredProcedure();
        return stores;
    }

//    @GetMapping("/local-sell-test")
//    public Map<String, List<LocalSellTest>> groupByType() {
//        List<LocalSellTest> localSellTests = localSellTestRepository.executeStoredProcedure();
//        Map<String, List<LocalSellTest>> groupedByType = localSellTests.stream()
//                .collect(Collectors.groupingBy(LocalSellTest::getType));
//
//        return groupedByType;
//    }
}
