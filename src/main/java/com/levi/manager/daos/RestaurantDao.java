package com.levi.manager.daos;

import com.levi.manager.dtos.RestaurantFilteredDTO;
import com.levi.manager.dtos.RestaurantSearchDTO;
import com.levi.manager.dtos.enuns.SortSearch;
import com.levi.manager.entities.Restaurant;
import com.levi.manager.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.internal.util.collections.CollectionHelper.isNotEmpty;


@Repository
public class RestaurantDao {

    //TODO Atualizar hibernate


    private static Criteria getCriteriaQuery() {
        return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Restaurant.class);
    }

    public List<RestaurantFilteredDTO> findUserCityRestaurants(RestaurantSearchDTO restaurantSearchDTO) {
        Criteria criteria = getCriteriaQuery();

        criteria.setProjection(Projections.property("name"));
        criteria.setProjection(Projections.property("category"));
        criteria.setProjection(Projections.property("cost"));
        criteria.setProjection(Projections.property("latitude"));
        criteria.setProjection(Projections.property("longitude"));
        criteria.setProjection(Projections.property("rateCount"));
        criteria.setProjection(Projections.property("rateSum"));


        criteria.add(Restrictions.eq("city", restaurantSearchDTO.getUserCity()));

        if(restaurantSearchDTO.getSearchedName() != null) {
            criteria.add(Restrictions.like("name", "%" + restaurantSearchDTO.getSearchedName() + "%"));
        }

        return null;
    }

}
