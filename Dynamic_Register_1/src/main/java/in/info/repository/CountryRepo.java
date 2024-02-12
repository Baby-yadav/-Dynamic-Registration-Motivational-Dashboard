package in.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.info.entity.Country;

public interface CountryRepo extends JpaRepository<Country, Integer>{

}
