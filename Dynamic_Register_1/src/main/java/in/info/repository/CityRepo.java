package in.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.info.entity.City;

public interface CityRepo extends JpaRepository<City, Integer> {
	
	public List<City> findByStateId(Integer stateId);

}
