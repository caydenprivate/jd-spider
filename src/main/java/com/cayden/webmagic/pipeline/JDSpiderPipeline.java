package com.cayden.webmagic.pipeline;

import com.cayden.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * Created by CaydenPrivate on 17/10/6.
 */
@Component
public class JDSpiderPipeline implements Pipeline{


    @Autowired
    private JDSkuMapper jdSkuMapper;

    @Autowired
    private JDCatMapper jdCatMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {

        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {

            if(entry.getKey().equals("jdSkuInfo")) {
                JDSku jdSku = (JDSku) entry.getValue();
                if(jdSku != null) {
                    try {
                        jdSkuMapper.insert(jdSku);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            if(entry.getKey().equals("jdCatInfo")) {
                List<JDCat> jdCatList = (List<JDCat>) entry.getValue();
                if(jdCatList != null) {
                    try {
                        for (int i = 0; i < jdCatList.size(); i++) {
                            JDCat jdCat =  jdCatList.get(i);
                            jdCatMapper.insert(jdCat);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
