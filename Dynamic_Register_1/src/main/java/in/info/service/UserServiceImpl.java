package in.info.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.info.bindings.LoginForm;
import in.info.bindings.RegisterForm;
import in.info.bindings.ResetPwdForm;
import in.info.entity.City;
import in.info.entity.Country;
import in.info.entity.State;
import in.info.entity.User;
import in.info.repository.CityRepo;
import in.info.repository.CountryRepo;
import in.info.repository.StateRepo;
import in.info.repository.UserRepo;
import in.info.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepo countryRepo;

	@Autowired
	private StateRepo stateRepo;

	@Autowired
	private CityRepo cityRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public Map<Integer, String> getCountries() {

		Map<Integer, String> countries = new HashMap<>();

		List<Country> findAll = countryRepo.findAll();

		findAll.forEach(c -> {
			countries.put(c.getCountryId(), c.getCountryName());
		});

		return countries;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {

		Map<Integer, String> statesMap = new HashMap<>();

		List<State> statesList = stateRepo.findByCountryId(countryId);

		statesList.forEach(s -> {
			statesMap.put(s.getStateId(), s.getStateName());
		});

		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {

		Map<Integer, String> citiesMap = new HashMap<>();

		List<City> citiesList = cityRepo.findByStateId(stateId);

		citiesList.forEach(c -> {
			citiesMap.put(c.getCityId(), c.getCityName());
		});

		return citiesMap;
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public boolean saveUser(RegisterForm formObj) {

		formObj.setPwd(generateRandomPwd());
		formObj.setPwdUpdated("NO");

		User userEntity = new User();
		BeanUtils.copyProperties(formObj, userEntity);

		userRepo.save(userEntity);

		String subject = "Your account created - Info Baby";
		String body = "Your Pwd : " + formObj.getPwd();

		return emailUtils.sendEmail(subject, body, formObj.getEmail());
	}

	private String generateRandomPwd() {
		String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";

		StringBuffer randomString = new StringBuffer(5);
		Random random = new Random();

		for (int i = 0; i < 5; i++) {
			int randomIndex = random.nextInt(alphanumericCharacters.length() - 1);
			char randomChar = alphanumericCharacters.charAt(randomIndex);
			randomString.append(randomChar);
		}

		return randomString.toString();
	}

	@Override
	public User login(LoginForm formObj) {
		return userRepo.findByEmailAndPwd(formObj.getEmail(), formObj.getPwd());
	}

	@Override
	public boolean resetPwd(ResetPwdForm formObj) {
		
		Optional<User> findById = userRepo.findById(formObj.getUserId());
		if (findById.isPresent()) {
			User user = findById.get();
			user.setPwd(formObj.getNewPwd());
			user.setPwdUpdated("YES");
			userRepo.save(user);
			return true;
		}
		return false;
	}

}