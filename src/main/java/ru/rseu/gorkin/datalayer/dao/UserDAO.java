package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.Statuses;
import ru.rseu.gorkin.datalayer.dto.User;

import java.util.List;

public interface UserDAO {
	void block(String login);
	void unblock(String login);
	void delete(String login);
	List<User> getAll();
	List<User> getAllByRole(Roles role);
	List<User> getAllByStatus(Statuses status);
	User get(int id);
	User get(String login);
	AuthenticationResults authenticate(String login, String password);
	boolean isLoginExist(String login);
	void createUser(String login, String password, String name, Roles role);
    void editUser(int userId, String name, String password);
}
