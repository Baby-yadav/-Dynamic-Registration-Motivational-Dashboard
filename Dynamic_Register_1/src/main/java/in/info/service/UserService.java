package in.info.service;

import java.util.Map;

import in.info.bindings.LoginForm;
import in.info.bindings.RegisterForm;
import in.info.bindings.ResetPwdForm;
import in.info.entity.User;

public interface UserService {

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public User getUser(String email);

	public boolean saveUser(RegisterForm formObj);

	public User login(LoginForm formObj);

	public boolean resetPwd(ResetPwdForm formObj);

}
