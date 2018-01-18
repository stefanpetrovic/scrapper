package scrapper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import scrapper.model.Apartment;
import scrapper.model.ApartmentPurpose;
import scrapper.repo.ApartmentRepository;

import javax.validation.ValidationException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final Logger log = LoggerFactory.getLogger(ApartmentServiceImpl.class);

    @Autowired
    private ApartmentRepository repository;

    @Override
    public Page<Apartment> findAllApartments(boolean onlyRecommended, Pageable pageable) {
        if (onlyRecommended) {
            return repository.findByRecommendedTrueOrderByCreatedDateDesc(pageable);
        }

        return repository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public List<Apartment> findAllApartments(ApartmentPurpose purpose) {
        return repository.findByPurposeOrderByCreatedDateDesc(purpose);
    }

    @Scheduled(fixedDelay = 1000*60*60*24)
    @Override
    public void cleanUpStaleRecords() {
        log.info("Started cleaning up stale records.");
        //current date minus 7 days
        Instant date7DaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        List<Apartment> apartmentsToDelete = repository.findByToBeSavedFalseAndCreatedDateBefore(new Date(date7DaysAgo.getEpochSecond()));

        int numberOfRecordsToRemove = apartmentsToDelete.size();
        if (numberOfRecordsToRemove > 0) {
            repository.delete(apartmentsToDelete);
            log.info("Removed {} records.", numberOfRecordsToRemove);
        } else {
            log.info("No records found for clean up.");
        }
    }

    @Override
    public Apartment updateApartment(Apartment apartment) {
        Apartment apartmentToUpdate = repository.findOne(apartment.getId());

        if (apartmentToUpdate == null) {
            throw new ValidationException("Apartment not found for updating.");
        }

        apartmentToUpdate.setToBeSaved(apartment.isToBeSaved());

        return repository.save(apartmentToUpdate);
    }
}
