package scrapper.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.model.Apartment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

@Component
public class EmailGenerator {

    @Autowired
    private Configuration freemarkerConfig;

    public String generateEmailContent(List<Apartment> apartmentList) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate("apartments.ftl");
        Map<String, Object> map = new HashMap<>();
        map.put("apartments", apartmentList);
        return processTemplateIntoString(template, map);
    }
}
