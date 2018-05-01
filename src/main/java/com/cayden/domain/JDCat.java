package com.cayden.domain;

public class JDCat {

    private  int id;

    private  String catId;

    private  String catUrl;

    private  String catName;

    private  int catStage;

    private  int sort;

    private  int status;

    private  long createAt;

    private  long updateAt;



    public  long  getId(){
        return  this.id;
    };
    public  void  setId(int id){
        this.id=id;
    }

    public  String  getCatId(){
        return  this.catId;
    };
    public  void  setCatId(String catId){
        this.catId=catId;
    }

    public  String  getCatUrl(){
        return  this.catUrl;
    };
    public  void  setCatUrl(String catUrl){
        this.catUrl=catUrl;
    }

    public  String  getCatName(){
        return  this.catName;
    };
    public  void  setCatName(String catName){
        this.catName=catName;
    }

    public  int  getCatStage(){
        return  this.catStage;
    };
    public  void  setCatStage(int catStage){
        this.catStage=catStage;
    }

    public  long  getSort(){
        return  this.sort;
    };
    public  void  setSort(int sort){
        this.sort=sort;
    }

    public  int  getStatus(){
        return  this.status;
    };
    public  void  setStatus(int status){
        this.status=status;
    }

    public  long  getCreateAt(){
        return  this.createAt;
    };
    public  void  setCreateAt(long createAt){
        this.createAt=createAt;
    }

    public  long  getUpdateAt(){
        return  this.updateAt;
    };
    public  void  setUpdateAt(long updateAt){
        this.updateAt=updateAt;
    }


}
