package com.i7gk.recipes.netService;

import com.i7gk.recipes.bean.SearchKeyWord;
import com.i7gk.recipes.bean.SearchType;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 64886 on 2017/2/24 0024.
 */

public interface NetService {
    //http://apicloud.mob.com/v1/cook/category/query?key=520520test
    //按分类标签查询
    @GET("category/query")
    Observable<SearchType> searchByType(@Query("key") String key);

    //http://apicloud.mob.com/v1/cook/menu/search?key=520520test&name=牛肉&page=1
    //按关键字查询
    @GET("menu/search")
    @Headers("Cache-Control: public, max-age=60")
    Observable<SearchKeyWord> searchByKeyWord(@Query("key") String key, @Query("name") String name, @Query("page") int page);

    //http://apicloud.mob.com/v1/cook/menu/search?key=520520test&cid=000000&page=1
    //按cid查询
    @GET("menu/search")
    Observable<SearchKeyWord> searchByCid(@Query("key") String key, @Query("cid") String cid, @Query("page") int page);

    //http://apicloud.mob.com/v1/cook/menu/query?key=520520test&id=00100010070000000001
    //按id查询
    @GET("menu/query")
    Observable<SearchKeyWord.ResultBean.ListBean> searchById(@Query("id") String id, @Query("key") String key);
}
