package com.cayden.domain;

public class JDSku {
    private  long id;

    private  long skuId;

    private  String catId;

    private  long brandId;

    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    private  long venderId;

    private  long jdzyShopId;

    private  String shopName;

    private  String productName;

    private  String productTitle;

    private  String productDesc;

    private  double salePrice;

    private  double shopPrice;

    private  double originalPrice;

    private  String imgUrl;

    private  long commentNum;

    private  String attrIds;

    private  int status;

    private  long createdAt;

    private  long updatedAt;



    public  long  getId(){
        return  this.id;
    };
    public  void  setId(long id){
        this.id=id;
    }

    public  long  getSkuId(){
        return  this.skuId;
    };
    public  void  setSkuId(long skuId){
        this.skuId=skuId;
    }

    public  String  getCatId(){
        return  this.catId;
    };
    public  void  setCatId(String catId){
        this.catId=catId;
    }

    public  long  getBrandId(){
        return  this.brandId;
    };
    public  void  setBrandId(long brandId){
        this.brandId=brandId;
    }

    public  long  getVenderId(){
        return  this.venderId;
    };
    public  void  setVenderId(long venderId){
        this.venderId=venderId;
    }

    public  long  getJdzyShopId(){
        return  this.jdzyShopId;
    };
    public  void  setJdzyShopId(long jdzyShopId){
        this.jdzyShopId=jdzyShopId;
    }

    public  String  getShopName(){
        return  this.shopName;
    };
    public  void  setShopName(String shopName){
        this.shopName=shopName;
    }

    public  String  getProductName(){
        return  this.productName;
    };
    public  void  setProductName(String productName){
        this.productName=productName;
    }

    public  String  getProductTitle(){
        return  this.productTitle;
    };
    public  void  setProductTitle(String productTitle){
        this.productTitle=productTitle;
    }

    public  String  getProductDesc(){
        return  this.productDesc;
    };
    public  void  setProductDesc(String productDesc){
        this.productDesc=productDesc;
    }

    public  double  getSalePrice(){
        return  this.salePrice;
    };
    public  void  setSalePrice(double salePrice){
        this.salePrice=salePrice;
    }

    public  double  getShopPrice(){
        return  this.shopPrice;
    };
    public  void  setShopPrice(double shopPrice){
        this.shopPrice=shopPrice;
    }

    public  double  getOriginalPrice(){
        return  this.originalPrice;
    };
    public  void  setOriginalPrice(double originalPrice){
        this.originalPrice=originalPrice;
    }

    public  String  getImgUrl(){
        return  this.imgUrl;
    };
    public  void  setImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }

    public  long  getCommentNum(){
        return  this.commentNum;
    };
    public  void  setCommentNum(long commentNum){
        this.commentNum=commentNum;
    }

    public  String  getAttrIds(){
        return  this.attrIds;
    };
    public  void  setAttrIds(String attrIds){
        this.attrIds=attrIds;
    }

    public  int  getStatus(){
        return  this.status;
    };
    public  void  setStatus(int status){
        this.status=status;
    }

    public  long  getCreatedAt(){
        return  this.createdAt;
    };
    public  void  setCreatedAt(long createdAt){
        this.createdAt=createdAt;
    }

    public  long  getUpdatedAt(){
        return  this.updatedAt;
    };
    public  void  setUpdatedAt(long updatedAt){
        this.updatedAt=updatedAt;
    }
}
