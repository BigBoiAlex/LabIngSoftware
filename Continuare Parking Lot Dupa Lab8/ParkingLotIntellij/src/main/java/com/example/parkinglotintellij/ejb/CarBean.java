package com.example.parkinglotintellij.ejb;

import com.example.parkinglotintellij.common.CarDetails;
import com.example.parkinglotintellij.entity.Car;
import com.example.parkinglotintellij.entity.User;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        try {
            Query query = em.createQuery("SELECT c FROM Car c");
            List<Car> cars = (List<Car>) query.getResultList();
            return copyCarsToDetails(cars);
        }
        catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDetails> copyCarsToDetails(List<Car> cars){
        List<CarDetails> detailsList = new ArrayList<>();
        for(Car car : cars){
            CarDetails carDetails = new CarDetails(car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getUser().getUsername());
            detailsList.add(carDetails);
        }
        return detailsList;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void createCar(String licensePlate, String parkinSpot, Integer userId){
        LOG.info("createCar");
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkinSpot);

        User user = em.find(User.class, userId);
        user.getCars().add(car);
        car.setUser(user);

        em.persist(car);
    }

    public CarDetails findById(Integer carId){
        Car car = em.find(Car.class, carId);
        return new CarDetails(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getUser().getUsername());
    }

    public void updateCar(int carId, String licensePlate, String parkingSpot, int ownerId) {
        LOG.info("updateCar");
        Car car = em.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User oldUser = car.getUser();
        oldUser.getCars().remove(car);

        User user = em.find(User.class, ownerId);
        user.getCars().add(car);
        car.setUser(user);
    }

    public void deleteCarsByIds(List<Integer> carIds) {
        LOG.info("deleteCarsByIds");
        for(Integer id : carIds){
            Car car = em.find(Car.class, id);
            em.remove(car);
        }
    }


}
