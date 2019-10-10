package com.levi.manager.util;

import com.levi.manager.domain.parent.LocalizedEntity;

public class DistanceCalculatorUtil {

    private static final int EARTH_RADIUS = 6371;

    /**
     * Calculates distance between two localizedEntities using the Haversine formula
     *
     * @param firstLocalizedEntity first localizedEntity
     * @param secondLocalizedEntity second localizedEntity
     * @return distance between the 2 given localizedEntities in meters
     */
    public static Double calculateDistanceBetweenPoints(LocalizedEntity firstLocalizedEntity,
                                                        LocalizedEntity secondLocalizedEntity) {
        double startLat = firstLocalizedEntity.getLatitude();
        double startLong = firstLocalizedEntity.getLongitude();
        double endLat = secondLocalizedEntity.getLatitude();
        double endLong = secondLocalizedEntity.getLongitude();

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(startLat) * Math.cos(endLat) * Math.pow(Math.sin(dLong / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
