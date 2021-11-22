package com.example.parkinglotintellij.ejb;

import com.example.parkinglotintellij.common.CarDetails;
import com.example.parkinglotintellij.entity.Car;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarBean {

    private static final Logger LOG = Logger.getLogger(CarBean.class.getName());

    @PersistenceContext
    private EntityManager em;

    public List<CarDetails> getAllCars() {
        LOG.info("getAllCars");
        List<Car> cars = null;
        try {
            TypedQuery<Car> typedQuery = em.createQuery("SELECT c FROM Car c", Car.class);
            cars = typedQuery.getResultList();
            return copyCarsToDetails(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDetails> copyCarsToDetails(List<Car> cars) {
        List<CarDetails> detailsList = new ArrayList<>();

        for (Car car : cars) {
            CarDetails carDetails = new CarDetails(car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getUser().getUsername());

            detailsList.add(carDetails);
        }

        return detailsList;
    }
}
