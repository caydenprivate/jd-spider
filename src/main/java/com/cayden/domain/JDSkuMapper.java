package com.cayden.domain;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by CaydenPrivate on 17/10/12.
 */
@Mapper
public interface JDSkuMapper {

    @Insert("insert into jd_sku (`sku_id`,`cat_id`,`brand_name`,`shop_name`,`product_name`,`product_title`,`product_desc`,`img_url`,`created_at`,`brand_id`,`vender_id`,`jdzy_shop_id`) " +
            "values (#{skuId},#{catId},#{brandName},#{shopName},#{productName},#{productTitle},#{productDesc},#{imgUrl},#{createdAt},#{brandId},#{venderId},#{jdzyShopId})")
    void insert(JDSku jdSku);

    @Select("select `sku_id` from jd_sku")
    List<Long> getAllSKUId();


}
