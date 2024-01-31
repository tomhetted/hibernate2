package org.example;

import lombok.AllArgsConstructor;
import org.example.dao.*;
import org.example.entity.*;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class Main {

    private final SessionFactory sessionFactory;


    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;
    private final TextDAO textDAO;

    public Main() {
        this.sessionFactory = HibernateUtil.getSessionFactory();

        this.actorDAO = new ActorDAO(sessionFactory);
        this.addressDAO = new AddressDAO(sessionFactory);
        this.categoryDAO = new CategoryDAO(sessionFactory);
        this.cityDAO = new CityDAO(sessionFactory);
        this.countryDAO = new CountryDAO(sessionFactory);
        this.customerDAO = new CustomerDAO(sessionFactory);
        this.filmDAO = new FilmDAO(sessionFactory);
        this.inventoryDAO = new InventoryDAO(sessionFactory);
        this.languageDAO = new LanguageDAO(sessionFactory);
        this.paymentDAO = new PaymentDAO(sessionFactory);
        this.rentalDAO = new RentalDAO(sessionFactory);
        this.staffDAO = new StaffDAO(sessionFactory);
        this.storeDAO = new StoreDAO(sessionFactory);
        this.textDAO = new TextDAO(sessionFactory);
    }

    public static void main(String[] args) {
        Main main = new Main();
//        CustomerEntity customer = main.createCustomer();
//        main.removeCustomerById((short) 603);
//        main.returnToStore();
//        main.getFilmParams();
//        main.getAddress();
//        main.rent(customer);

    }

    private void addNewFilm() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            List<CategoryEntity> categories = categoryDAO.getAll(0,4);
            List<ActorEntity> actors = actorDAO.getAll(30,10);

            FilmEntity film = new FilmEntity();
            film.setTitle("NEW HORIZONS");
            film.setLanguage(languageDAO.getAll(0,20).stream().unordered().findAny().get());
            film.setOriginalLanguage(languageDAO.getAll(0,20).stream().unordered().findAny().get());
            film.setRentalDuration((byte) 6);
            film.setRentalRate(BigDecimal.valueOf(4.99));
            film.setReplacementCost(BigDecimal.valueOf(14.99));
            film.setRating(FilmEntity.FilmRating.PG_13);
            film.setActors(new HashSet<>(actors));
            film.setCategories(new HashSet<>(categories));
            film.setSpecialFeatures(Set.of(FilmEntity.SpecialFeature.COMMENTARIES, FilmEntity.SpecialFeature.TRAILERS));
            film.setDescription("An unbelievable journey of human strength through the space and time");
            film.setLength((short)115);
            film.setReleaseYear(2023);
            filmDAO.saveOrUpdate(film);

            TextEntity text = new TextEntity();
            text.setFilm(film);
            text.setTitle(film.getTitle());
            text.setDescription(film.getDescription());
            textDAO.saveOrUpdate(text);

            transaction.commit();
        }
    }

    public ActorEntity createActor(String name, String lastName) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            ActorEntity actor = new ActorEntity();
            actor.setFirstName(name);
            actor.setLastName(lastName);
            session.persist(actor);
            session.getTransaction().commit();
            return actor;
        }
    }

    public void removeCustomerById(Short id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            CustomerEntity customer = session.get(CustomerEntity.class, id);
            session.delete(customer);
            session.getTransaction().commit();
        }
    }

    public CustomerEntity createCustomer() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            StoreEntity store = storeDAO.getAll(0,1).get(0);
            CityEntity city = cityDAO.getByName("Okinawa");
            AddressEntity address = new AddressEntity();
            address.setAddress("13 Hell Drive");
            address.setCity(city);
            address.setDistrict("Kupchino");
            address.setPhone("+4(555)777-77-77");
            addressDAO.saveOrUpdate(address);

            CustomerEntity customer = new CustomerEntity();
            customer.setStore(store);
            customer.setAddress(address);
            customer.setFirstName("Ito");
            customer.setLastName("Yoshimiro");
            customer.setIsActive(true);
            customerDAO.saveOrUpdate(customer);

            transaction.commit();
            return customer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void returnToStore() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            RentalEntity rental = rentalDAO.getAnyUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            rentalDAO.saveOrUpdate(rental);
            transaction.commit();
        }
    }

    public void getFilmParams() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            FilmEntity film = filmDAO.getByIdUsingQuery((short)27);
            transaction.commit();
            System.out.println(film.getRating());
            System.out.println(film.getSpecialFeatures());
        }
    }

    public void getAddress() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            AddressEntity address = addressDAO.getAll(0, 1).get(0);
            transaction.commit();
            System.out.println(address.getAddress());
        }
    }

    public void rent(CustomerEntity customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            FilmEntity film = filmDAO.getFirstAvailable();

            InventoryEntity inventory = new InventoryEntity();
            inventory.setFilm(film);
            StoreEntity store = storeDAO.getAll(0,1).get(0);
            inventory.setStore(store);
            inventoryDAO.saveOrUpdate(inventory);

            StaffEntity staff = store.getStaff();

            RentalEntity rental = new RentalEntity();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            rentalDAO.saveOrUpdate(rental);

            PaymentEntity payment = new PaymentEntity();
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setStaff(staff);
            payment.setRental(rental);
            payment.setAmount(BigDecimal.TEN);
            paymentDAO.saveOrUpdate(payment);

            transaction.commit();
        }
    }

}
