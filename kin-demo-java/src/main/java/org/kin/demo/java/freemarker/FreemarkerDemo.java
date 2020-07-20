package org.kin.demo.java.freemarker;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangjianqin
 * @date 2020-07-17
 */
public class FreemarkerDemo {
    public static void main(String[] args) throws IOException, TemplateException {
        String dir = Thread.currentThread().getContextClassLoader().getResource("ftls").getPath();
        String tName = "test1.ftl";
        Map<String, Object> datas = new HashMap<>();
        datas.put("v", 1);
        datas.put("items", Arrays.asList("ab", "bc", "cd", "df", "fg"));

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_20);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_20));
        cfg.setDirectoryForTemplateLoading(new File(dir));

        Template template = cfg.getTemplate(tName);
        StringWriter sw = new StringWriter();
        template.process(datas, sw);

        System.out.println(sw.toString());
    }
}
