package com.itheima.travel.service;

import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.User; /**
 * 包名:com.itheima.travel.service
 * 作者:Leevi
 * 日期2019-05-31  10:20
 */
public interface IFavoriteService {
    Favorite findFavoriteByUidAndRid(User user, String rid);

    Integer addFavorite(User user, String rid);

    Integer romeFavorite(User user, String rid);
}
