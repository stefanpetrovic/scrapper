package scrapper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import scrapper.model.RecommenderConfig;
import scrapper.service.RecommenderConfigService;

import java.util.List;

@RestController
@RequestMapping("/recommenderConfig")
public class RecommenderConfigREST {

    @Autowired
    private RecommenderConfigService recommenderConfigService;

    @GetMapping
    public List<RecommenderConfig> getAllConfig() {
        return recommenderConfigService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public RecommenderConfig updateConfig(@RequestBody RecommenderConfig recommenderConfig) {
        return recommenderConfigService.save(recommenderConfig);
    }
}
