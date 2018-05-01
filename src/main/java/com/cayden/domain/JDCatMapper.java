package com.cayden.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by CaydenPrivate on 17/10/12.
 */
@Mapper
public interface JDCatMapper {

    @Insert("insert into jd_cat (`cat_id`,`cat_url`,`cat_name`,`cat_stage`,`create_at`) " +
            "values (#{catId},#{catUrl},#{catName},#{catStage},#{createAt})")
    void insert(JDCat jdCat);

}
